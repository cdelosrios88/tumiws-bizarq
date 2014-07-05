package pe.com.tumi.tesoreria.ingreso.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.tesoreria.banco.bo.BancofondoBO;
import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeLocal;
import pe.com.tumi.tesoreria.ingreso.bo.DepositoIngresoBO;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoBO;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoDetalleBO;
import pe.com.tumi.tesoreria.ingreso.domain.DepositoIngreso;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoId;

public class IngresoService {

	protected static Logger log = Logger.getLogger(IngresoService.class);
	
	IngresoBO boIngreso = (IngresoBO)TumiFactory.get(IngresoBO.class);
	IngresoDetalleBO boIngresoDetalle = (IngresoDetalleBO)TumiFactory.get(IngresoDetalleBO.class);
	BancofondoBO boBancoFondo = (BancofondoBO)TumiFactory.get(BancofondoBO.class);
	DepositoIngresoBO boDepositoIngreso = (DepositoIngresoBO)TumiFactory.get(DepositoIngresoBO.class);
	
	
	public LibroDiario obtenerLibroDiarioPorIngreso(Ingreso ingreso)throws BusinessException{
		LibroDiario libroDiario = null;
		try{
			LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			
			LibroDiarioId libroDiarioId = new LibroDiarioId();
			libroDiarioId.setIntPersEmpresaLibro(ingreso.getIntPersEmpresaLibro());
			libroDiarioId.setIntContPeriodoLibro(ingreso.getIntContPeriodoLibro());
			libroDiarioId.setIntContCodigoLibro(ingreso.getIntContCodigoLibro());
			
			libroDiario = libroDiarioFacade.getLibroDiarioPorPk(libroDiarioId);
			procesarItems(libroDiario);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return libroDiario;
	}
	
	public List<Ingreso> buscarIngresoParaCaja(Ingreso ingresoFiltro, List<Persona> listaPersonaFiltro)throws BusinessException{
		List<Ingreso> listaIngreso = new ArrayList<Ingreso>();
		try{
			Integer intIdEmpresa = ingresoFiltro.getId().getIntIdEmpresa();
			
			log.info(ingresoFiltro);
			listaIngreso = boIngreso.getListaParaBuscar(ingresoFiltro);			
			
			if(listaPersonaFiltro!=null && !listaPersonaFiltro.isEmpty()){
				List<Ingreso> listaIngresoTemp = new ArrayList<Ingreso>();
				for(Ingreso ingreso : listaIngreso){
					for(Persona persona : listaPersonaFiltro){
						if(ingreso.getIntPersPersonaGirado().equals(persona.getIntIdPersona())){
							listaIngresoTemp.add(ingreso);
							break;
						}
					}	
				}
				listaIngreso = listaIngresoTemp;
			}
			
			if(ingresoFiltro.getIntSucuIdSucursal()!=null){
				List<Ingreso> listaIngresoTemp = new ArrayList<Ingreso>();
				if(Integer.signum(ingresoFiltro.getIntSucuIdSucursal())>0){
					for(Ingreso ingreso : listaIngreso){
						if(ingreso.getIntSucuIdSucursal().equals(ingresoFiltro.getIntSucuIdSucursal())){
							listaIngresoTemp.add(ingreso);
						}
					}
				}else{
					EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
					for(Ingreso ingreso : listaIngreso){
						Sucursal sucursal = new Sucursal();
						sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
						sucursal.getId().setIntIdSucursal(ingreso.getIntSucuIdSucursal());
						sucursal = empresaFacade.getSucursalPorPK(sucursal);
						if(empresaFacade.validarTotalSucursal(sucursal.getIntIdTipoSucursal(), ingresoFiltro.getIntSucuIdSucursal())){
							listaIngresoTemp.add(ingreso);
						}
					}
				}
				listaIngreso = listaIngresoTemp;
			}
			
			if(ingresoFiltro.getIntSucuIdSucursal()!=null && ingresoFiltro.getIntSudeIdSubsucursal()!=null){
				List<Ingreso> listaIngresoTemp = new ArrayList<Ingreso>();
				for(Ingreso ingreso : listaIngreso){
					if(ingreso.getIntSudeIdSubsucursal().equals(ingresoFiltro.getIntSudeIdSubsucursal())){
						listaIngresoTemp.add(ingreso);
					}
				}
				listaIngreso = listaIngresoTemp;
			}
			
			
			for(Ingreso ingreso : listaIngreso){
				procesarItems(ingreso);
				ingreso.setListaIngresoDetalle(boIngresoDetalle.getPorIngreso(ingreso));
				if(ingreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO)){
					ingreso.setListaDepositoIngreso(cargarListaDepositoIngreso(ingreso));
					ingreso.setBancoFondo(boBancoFondo.getPorIngreso(ingreso));
					ingreso.setArchivoVoucher(obtenerArchivo(ingreso));					
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaIngreso;
	}
	
	public List<DepositoIngreso> cargarListaDepositoIngreso(Ingreso ingreso)throws BusinessException{
		List<DepositoIngreso> listaDepositoIngreso = null;
		try{
			listaDepositoIngreso = boDepositoIngreso.getPorIngresoDeposito(ingreso);
			for(DepositoIngreso depositoIngreso : listaDepositoIngreso){
				IngresoId ingresoId = new IngresoId();
				ingresoId.setIntIdEmpresa(depositoIngreso.getIntIdEmpresa());
				ingresoId.setIntIdIngresoGeneral(depositoIngreso.getIntIdIngresoGeneral());
				Ingreso ingresoDepositado = boIngreso.getPorId(ingresoId);
				ingresoDepositado.setBdMontoDepositar(depositoIngreso.getBdMontoCancelado());
				procesarItems(ingresoDepositado);
				depositoIngreso.setIngreso(ingresoDepositado);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaDepositoIngreso;
	}
	
	private Archivo obtenerArchivo(Ingreso ingreso)throws Exception{
		if(ingreso.getIntParaTipoDeposito()==null)
			return null;
		GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		ArchivoId archivoId = new ArchivoId();
		archivoId.setIntParaTipoCod(ingreso.getIntParaTipoDeposito());
		archivoId.setIntItemArchivo(ingreso.getIntItemArchivoDeposito());
		archivoId.setIntItemHistorico(ingreso.getIntItemHistoricoDeposito());
		return generalFacade.getArchivoPorPK(archivoId);
	}
	
	public void procesarItems(Ingreso ingreso){
		ingreso.setStrNumeroIngreso(
				obtenerPeriodoItem(	ingreso.getIntItemPeriodoIngreso(), 
									ingreso.getIntItemIngreso(), 
									"000000"));
			
		if(ingreso.getLibroDiario()!=null){
			procesarItems(ingreso.getLibroDiario());
		}		
	}
	
	private void procesarItems(LibroDiario libroDiario){
		if(libroDiario!=null){
			libroDiario.setStrNumeroAsiento(
					obtenerPeriodoItem(	libroDiario.getId().getIntContPeriodoLibro(),
										libroDiario.getId().getIntContCodigoLibro(), 
										"000000"));
		}		
	}
	
	private String obtenerPeriodoItem(Integer intPeriodo, Integer item, String patron){
		try{
			DecimalFormat formato = new DecimalFormat(patron);
			return intPeriodo+"-"+formato.format(Double.parseDouble(""+item));
		}catch (Exception e) {
			log.error(intPeriodo+" "+item+" "+patron+e.getMessage());
			return intPeriodo+"-"+item;
		}
	}	
	
	private Ingreso grabarIngreso(Ingreso ingreso)throws BusinessException{
		try{
			List<Ingreso> listaIngreso = boIngreso.getListaParaItem(ingreso);
			
			Integer intMayorItemEgreso = 0;
			
			if(listaIngreso!=null  && !listaIngreso.isEmpty()){
				//Ordenamos los ingresos previos dependiendo de intItemIngreso
				Collections.sort(listaIngreso, new Comparator<Ingreso>(){
					public int compare(Ingreso uno, Ingreso otro) {
						return otro.getIntItemIngreso().compareTo(uno.getIntItemIngreso());
					}
				});
				intMayorItemEgreso = listaIngreso.get(0).getIntItemIngreso();
			}
			
			intMayorItemEgreso = intMayorItemEgreso + 1;			
			
			ingreso.setIntItemIngreso(intMayorItemEgreso);
			log.info(ingreso);
			boIngreso.grabar(ingreso);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ingreso;
	}
	
	public Ingreso grabarIngresoGeneral(Ingreso ingreso) throws BusinessException{		
		try{
			CierreDiarioArqueoFacadeLocal cierreDiarioArqueoFacadeLocal = (CierreDiarioArqueoFacadeLocal) EJBFactory.getLocal(CierreDiarioArqueoFacadeLocal.class);			
			if(!cierreDiarioArqueoFacadeLocal.validarMovimientoCaja(ingreso)){
				throw new Exception("El fondo fijo tiene un problema de cierre y arqueo");
			}
			
			LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			LibroDiario libroDiario = ingreso.getLibroDiario();
			
			log.info(libroDiario);
			for(LibroDiarioDetalle libroDiarioDetalle : libroDiario.getListaLibroDiarioDetalle()){
				log.info(libroDiarioDetalle);
			}
			libroDiario = libroDiarioFacade.grabarLibroDiario(libroDiario);
			
			ingreso.setIntPersEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
			ingreso.setIntContPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
			ingreso.setIntContCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
			
			ingreso = grabarIngreso(ingreso);
			
			for(IngresoDetalle ingresoDetalle : ingreso.getListaIngresoDetalle()){
				ingresoDetalle.getId().setIntIdIngresoGeneral(ingreso.getId().getIntIdIngresoGeneral());
				log.info(ingresoDetalle);
				boIngresoDetalle.grabar(ingresoDetalle);
			}
			
			procesarItems(ingreso);
			
			for(LibroDiarioDetalle libroDiarioDetalle : libroDiario.getListaLibroDiarioDetalle()){
				//Para el LDD asociado al ingreso se le coloca el numero de ingreso en strNumeroDocumento
				if(libroDiarioDetalle.getStrNumeroDocumento() == null){
					libroDiarioDetalle.setStrNumeroDocumento(ingreso.getStrNumeroIngreso());
					libroDiarioFacade.modificarLibroDiarioDetalle(libroDiarioDetalle);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ingreso;
	}
	/**
	 * Agregado 13.12.2013 JCHAVEZ 
	 * Busca ingresos y depositos cuando se va a realizar un cierre diario y arqueo por primera vez.(no existe ningun cierre previo)
	 * @param ingresoFiltro
	 * @return
	 * @throws BusinessException
	 */
	public List<Ingreso> buscarIngresoParaCaja(Ingreso ingresoFiltro)throws BusinessException{
		List<Ingreso> listaIngreso = new ArrayList<Ingreso>();
		try{
			
			log.info(ingresoFiltro);
			listaIngreso = boIngreso.getListaParaBuscar(ingresoFiltro);			
			
			if(ingresoFiltro.getIntSucuIdSucursal()!=null){
				List<Ingreso> listaIngresoTemp = new ArrayList<Ingreso>();
				if(Integer.signum(ingresoFiltro.getIntSucuIdSucursal())>0){
					for(Ingreso ingreso : listaIngreso){
						if(ingreso.getIntSucuIdSucursal().equals(ingresoFiltro.getIntSucuIdSucursal())){
							listaIngresoTemp.add(ingreso);
						}
					}
				}
				listaIngreso = listaIngresoTemp;
			}
			
			if(ingresoFiltro.getIntSucuIdSucursal()!=null && ingresoFiltro.getIntSudeIdSubsucursal()!=null){
				List<Ingreso> listaIngresoTemp = new ArrayList<Ingreso>();
				for(Ingreso ingreso : listaIngreso){
					if(ingreso.getIntSudeIdSubsucursal().equals(ingresoFiltro.getIntSudeIdSubsucursal())){
						listaIngresoTemp.add(ingreso);
					}
				}
				listaIngreso = listaIngresoTemp;
			}
			
			
			for(Ingreso ingreso : listaIngreso){
				procesarItems(ingreso);
				ingreso.setListaIngresoDetalle(boIngresoDetalle.getPorIngreso(ingreso));
				if(ingreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO)){
					ingreso.setListaDepositoIngreso(cargarListaDepositoIngreso(ingreso));
					ingreso.setBancoFondo(boBancoFondo.getPorIngreso(ingreso));
					ingreso.setArchivoVoucher(obtenerArchivo(ingreso));					
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaIngreso;
	}
}