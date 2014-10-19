package pe.com.tumi.tesoreria.ingreso.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DocumentoGeneral;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;
import pe.com.tumi.movimiento.concepto.domain.BloqueoPeriodo;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePagoId;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPagoId;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.InteresCanceladoId;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoDetalle;
import pe.com.tumi.riesgo.cartera.facade.CarteraFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.bo.BancofondoBO;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeLocal;
import pe.com.tumi.tesoreria.ingreso.bo.DepositoIngresoBO;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoBO;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoDetalleBO;
import pe.com.tumi.tesoreria.ingreso.bo.ReciboManualBO;
import pe.com.tumi.tesoreria.ingreso.bo.ReciboManualDetalleBO;
import pe.com.tumi.tesoreria.ingreso.domain.DepositoIngreso;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalleId;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoId;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;

public class IngresoService {

	protected static Logger log = Logger.getLogger(IngresoService.class);
	
	IngresoBO boIngreso = (IngresoBO)TumiFactory.get(IngresoBO.class);
	IngresoDetalleBO boIngresoDetalle = (IngresoDetalleBO)TumiFactory.get(IngresoDetalleBO.class);
	BancofondoBO boBancoFondo = (BancofondoBO)TumiFactory.get(BancofondoBO.class);
	DepositoIngresoBO boDepositoIngreso = (DepositoIngresoBO)TumiFactory.get(DepositoIngresoBO.class);
	ReciboManualBO boReciboManual = (ReciboManualBO)TumiFactory.get(ReciboManualBO.class);
	ReciboManualDetalleBO boReciboManualDetalle = (ReciboManualDetalleBO)TumiFactory.get(ReciboManualDetalleBO.class);
	
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
	
	private Archivo obtenerArchivo(Ingreso ingreso)throws BusinessException{
		try {
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			
			if(ingreso.getIntParaTipoDeposito()==null){
				return null;
			}				
			ArchivoId archivoId = new ArchivoId();
			archivoId.setIntParaTipoCod(ingreso.getIntParaTipoDeposito());
			archivoId.setIntItemArchivo(ingreso.getIntItemArchivoDeposito());
			archivoId.setIntItemHistorico(ingreso.getIntItemHistoricoDeposito());
			return generalFacade.getArchivoPorPK(archivoId);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}		
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
			//jchavez 23.07.2014 seteamos los valores del libro grabado
			ingreso.setLibroDiario(libroDiario);
			
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
	public DocumentoGeneral generarIngresoSocio(List<ExpedienteComp> listaIngresosSocio, DocumentoGeneral documentoGeneral, Bancofondo bancoFondo, Usuario usuario, Integer intModalidadC, Integer intPersonaRolC) throws BusinessException{
//		ExpedienteComp expedienteComp = null;
		Ingreso ingreso = new Ingreso();
		try{			
			Integer intIdEmpresa = listaIngresosSocio.get(0).getIntIngCajaIdEmpresa();
			
			Sucursal sucursal = usuario.getSucursal();
			Subsucursal	subsucursal = usuario.getSubSucursal();
			String strObservacion = documentoGeneral.getStrObservacionIngreso();
			Modelo modelo = obtenerModeloSocio(intIdEmpresa);
			//ModeloDetalle modeloDetalle = modelo.getListModeloDetalle().get(0);
			BigDecimal bdMontoIngresoTotal = documentoGeneral.getBdMontoAIngresar();
			Archivo archivoAdjuntoCheque = documentoGeneral.getArchivoCheque();
			
			ingreso.setId(new IngresoId());
			ingreso.getId().setIntIdEmpresa(intIdEmpresa);
			ingreso.getId().setIntIdIngresoGeneral(null);
			ingreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_INGRESO);
			ingreso.setIntParaFormaPago(documentoGeneral.getIntParaFormaPago());
			ingreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			ingreso.setIntItemPeriodoIngreso(obtenerPeriodoActual());
			ingreso.setIntItemIngreso(null);
			ingreso.setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
			ingreso.setIntSudeIdSubsucursal(subsucursal.getId().getIntIdSubSucursal());
			ingreso.setIntParaTipoSuboperacion(null); 
			ingreso.setTsFechaProceso(obtenerFechaActual());
			ingreso.setDtFechaIngreso(obtenerFechaActual());
			
			ingreso.setIntParaFondoFijo(bancoFondo.getIntTipoFondoFijo());
			ingreso.setIntItemBancoFondo(bancoFondo.getId().getIntItembancofondo());
			ingreso.setIntItemBancoCuenta(null);
			ingreso.setStrNumeroCheque(documentoGeneral.getStrNumeroCheque());
			ingreso.setIntPersEmpresaGirado(intIdEmpresa);
			ingreso.setIntPersPersonaGirado(documentoGeneral.getSocioIngreso().getIntIngCajaIdPersona()); //jchavez 04.07.2014
			ingreso.setIntCuentaGirado(documentoGeneral.getSocioIngreso().getIntIngCajaIdCta());
			ingreso.setIntParaTipoMoneda(bancoFondo.getIntMonedaCod());
			ingreso.setBdTipoCambio(null);
			ingreso.setBdMontoTotal(bdMontoIngresoTotal);
			ingreso.setStrObservacion(strObservacion);
			ingreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			ingreso.setTsFechaRegistro(obtenerFechaActual());
			ingreso.setIntPersEmpresaUsuario(intIdEmpresa);
			ingreso.setIntPersPersonaUsuario(usuario.getPersona().getIntIdPersona());
			ingreso.setIntParaEstadoDepositado(Constante.PARAM_T_ESTADODEPOSITADO_PENDIENTE);
			//jchavez 08.07.2014 nuevos campos
			ingreso.setIntParaModalidadPago(documentoGeneral.getIntParaModalidadPago());
			ingreso.setIntPeriodoSocio(documentoGeneral.getIntPeriodoPlanilla());
			
			if (documentoGeneral.getIntParaFormaPago().equals(Constante.PARAM_T_FORMAPAGOEGRESO_CHEQUE)) {
				ingreso.setIntParaTipoIngreso(archivoAdjuntoCheque.getId().getIntParaTipoCod());
				ingreso.setIntItemArchivoIngreso(archivoAdjuntoCheque.getId().getIntItemArchivo());
				ingreso.setIntHistoricoIngreso(archivoAdjuntoCheque.getId().getIntItemHistorico());
			}
			
			ingreso.setListaIngresoDetalle(generarIngresoDetalle(listaIngresosSocio, documentoGeneral, bancoFondo, usuario, intModalidadC, intPersonaRolC, modelo));
			
			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiario.setStrGlosa(strObservacion);
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			
			libroDiario.getListaLibroDiarioDetalle().addAll(generarLibroDiarioDetalleSocioHaber(listaIngresosSocio, documentoGeneral, modelo, bancoFondo, usuario, intModalidadC, intPersonaRolC));
			
			LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
			libroDiarioDetalleDebe.setId(new LibroDiarioDetalleId());
			libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiarioDetalleDebe.setIntPersEmpresaCuenta(bancoFondo.getFondoDetalleUsar().getIntEmpresacuentaPk());
			libroDiarioDetalleDebe.setIntContPeriodo(bancoFondo.getFondoDetalleUsar().getIntPeriodocuenta());
			libroDiarioDetalleDebe.setStrContNumeroCuenta(bancoFondo.getFondoDetalleUsar().getStrNumerocuenta());
			
			libroDiarioDetalleDebe.setIntPersPersona(documentoGeneral.getSocioIngreso().getIntIngCajaIdPersona());
			libroDiarioDetalleDebe.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			libroDiarioDetalleDebe.setStrSerieDocumento(null);
			libroDiarioDetalleDebe.setStrNumeroDocumento(null);
			libroDiarioDetalleDebe.setIntPersEmpresaSucursal(intIdEmpresa);
			libroDiarioDetalleDebe.setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
			libroDiarioDetalleDebe.setIntSudeIdSubSucursal(subsucursal.getId().getIntIdSubSucursal());
			libroDiarioDetalleDebe.setIntParaMonedaDocumento(bancoFondo.getIntMonedaCod());
			libroDiarioDetalleDebe.setBdHaberSoles(null);
			libroDiarioDetalleDebe.setBdDebeSoles(bdMontoIngresoTotal);			
			libroDiarioDetalleDebe.setBdHaberExtranjero(null);
			libroDiarioDetalleDebe.setBdDebeExtranjero(null);
			libroDiarioDetalleDebe.setStrComentario(bancoFondo.getStrObservacion());
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);
			
			ingreso.setLibroDiario(libroDiario);
			documentoGeneral.setIngresoCaja(ingreso);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return documentoGeneral;
	}

	
	private List<IngresoDetalle> generarIngresoDetalle(List<ExpedienteComp> listaIngresosSocio,  DocumentoGeneral documentoGeneral, Bancofondo bancoFondo, Usuario usuario, Integer intModalidadC, Integer intPersonaRolC, Modelo modelo)throws Exception{
		List<IngresoDetalle> lstIngresoDetalle = new ArrayList<IngresoDetalle>();
		Integer intIdEmpresa = listaIngresosSocio.get(0).getIntIngCajaIdEmpresa();		
		CarteraCreditoDetalle cartera = null;
		
		try {
			CarteraFacadeRemote carteraFacade = (CarteraFacadeRemote) EJBFactory.getRemote(CarteraFacadeRemote.class);
			
			//generacion del modelo detalle
			for (ExpedienteComp ingresoDetSocio : listaIngresosSocio) {
				//Caso Servicios
				if (ingresoDetSocio.getStrIngCajaNroSolicitud()!=null && ingresoDetSocio.getIntIngCajaFlagAmortizacionInteres()!=null) {
					ExpedienteId pId = new ExpedienteId();
					pId.setIntPersEmpresaPk(ingresoDetSocio.getIntIngCajaIdEmpresa());
					pId.setIntCuentaPk(ingresoDetSocio.getIntIngCajaIdCuenta());
					pId.setIntItemExpediente(ingresoDetSocio.getIntIngCajaId1());
					pId.setIntItemExpedienteDetalle(ingresoDetSocio.getIntIngCajaId2());
					cartera = carteraFacade.getListaPorMaxPerYPKExpediente(pId);
					if (ingresoDetSocio.getIntIngCajaFlagAmortizacionInteres().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION)) {
						if (modelo!=null) {							
							if (modelo.getListModeloDetalle()!=null && !modelo.getListModeloDetalle().isEmpty()) {
								for (ModeloDetalle md : modelo.getListModeloDetalle()) {
									Boolean blnConceptoGeneral = false;
									Boolean blnConfCredito = false;
									Boolean blnCategoriaRiesgo = false;
									if (md.getListModeloDetalleNivel()!=null && !md.getListModeloDetalleNivel().isEmpty()) {
										for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
											//validamos que el modelo corresponda a una amortizacion
											if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_CONCEPTOGRAL)) {
												if (mdn.getIntDatoArgumento().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION)) {
													blnConceptoGeneral = true;
													break;
												}
											}
										}
										if (blnConceptoGeneral) {
											for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
												//validamos que el modelo corresponda a un expediente
												if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_CONFCREDITO)) {
													if (mdn.getIntDatoTablas().equals(ingresoDetSocio.getIntIngCajaParaTipoX()) && mdn.getIntDatoArgumento().equals(ingresoDetSocio.getIntIngCajaItemX())) {
														blnConfCredito = true;
														break;
													}
												}
											}
											if (blnConfCredito) {
												for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
													//validamos que le corresponda una categoria de riesgo													
													if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_CATEGORIARIESGO)) {
														if (cartera!=null) {
															if (mdn.getIntDatoArgumento().equals(cartera.getIntParaTipocategoriariesgo())) {
																blnCategoriaRiesgo = true;
																break;
															}
														}else{
															if (mdn.getIntDatoArgumento().equals(1)) { //vigente
																blnCategoriaRiesgo = true;
																break;
															}
														}														
													}
												}
												if (blnCategoriaRiesgo) {
													lstIngresoDetalle.add(seteoIngresoDetalle(md, intIdEmpresa, documentoGeneral, bancoFondo, ingresoDetSocio, ingresoDetSocio.getBdIngCajaMontoPagado(), usuario));
												}
											}											
										}
									}
								}
							}
						}
					}else if (ingresoDetSocio.getIntIngCajaFlagAmortizacionInteres().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES)) {
						if (modelo!=null) {							
							if (modelo.getListModeloDetalle()!=null && !modelo.getListModeloDetalle().isEmpty()) {
								for (ModeloDetalle md : modelo.getListModeloDetalle()) {
									Boolean blnConceptoGeneral = false;
									Boolean blnConfCredito = false;
									Boolean blnCategoriaRiesgo = false;
									if (md.getListModeloDetalleNivel()!=null && !md.getListModeloDetalleNivel().isEmpty()) {
										for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
											//validamos que el modelo corresponda a una amortizacion
											if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_CONCEPTOGRAL)) {
												if (mdn.getIntDatoArgumento().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES)) {
													blnConceptoGeneral = true;
													break;
												}
											}
										}
										if (blnConceptoGeneral) {
											for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
												//validamos que el modelo corresponda a un expediente
												if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_CONFCREDITO)) {
													if (mdn.getIntDatoTablas().equals(ingresoDetSocio.getIntIngCajaParaTipoX()) && mdn.getIntDatoArgumento().equals(ingresoDetSocio.getIntIngCajaItemX())) {
														blnConfCredito = true;
														break;
													}
												}
											}
											if (blnConfCredito) {
												for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
													//validamos que le corresponda una categoria de riesgo													
													if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_CATEGORIARIESGO)) {
														if (cartera!=null) {
															if (mdn.getIntDatoArgumento().equals(cartera.getIntParaTipocategoriariesgo())) {
																blnCategoriaRiesgo = true;
																break;
															}
														}else{
															if (mdn.getIntDatoArgumento().equals(1)) { //vigente
																blnCategoriaRiesgo = true;
																break;
															}
														}														
													}
												}
												if (blnCategoriaRiesgo) {
													lstIngresoDetalle.add(seteoIngresoDetalle(md, intIdEmpresa, documentoGeneral, bancoFondo, ingresoDetSocio, ingresoDetSocio.getBdIngCajaMontoPagado(), usuario));
												}
											}											
										}
									}
								}
							}
						}
					}
				}else if (ingresoDetSocio.getStrIngCajaNroSolicitud()==null && ingresoDetSocio.getIntIngCajaFlagAmortizacionInteres()==null) {//Caso Beneficios
					if (modelo!=null) {							
						if (modelo.getListModeloDetalle()!=null && !modelo.getListModeloDetalle().isEmpty()) {
							for (ModeloDetalle md : modelo.getListModeloDetalle()) {
								Boolean blnParaTipoConcepto = false;
								Boolean blnItemConcepto = false;
								Boolean blnModeloRol = false;
								Boolean blnPersonaRol = false;
								if (md.getListModeloDetalleNivel()!=null && !md.getListModeloDetalleNivel().isEmpty()) {
									if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PAGOMESSGTE) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_APORTACIONES) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PLANILLA_ENVIADA)) {
										//validamos el para_tipo_concepto
										for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
											//validamos que el modelo corresponda para tipo concepto
											if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_TIPOCONCEPTO)) {
												if (mdn.getIntValor().equals(ingresoDetSocio.getIntIngCajaParaTipoX())) {
													blnParaTipoConcepto = true;
													break;
												}
											}
										}
										if (blnParaTipoConcepto) {
											for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
												//validamos que le corresponda item concepto											
												if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_ITEMCONCEPTO)) {
													if (mdn.getIntValor().equals(ingresoDetSocio.getIntIngCajaItemX())) {
														blnItemConcepto = true;
														break;
													}
												}
											}
											if (blnItemConcepto) {
												//validamos que el modelo tenga rol
												for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {																									
													if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_ROL)) {
														blnModeloRol = true;
														break;
													}
												}
												if (blnModeloRol) {
													//Si tuviera rol, se valida que sea el rol buscado
													for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {																									
														if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_ROL)) {
															if (mdn.getIntDatoArgumento().equals(intPersonaRolC)) {
																blnPersonaRol = true;
																break;
															}
														}
													}
													if (blnPersonaRol) {
														lstIngresoDetalle.add(seteoIngresoDetalle(md, intIdEmpresa, documentoGeneral, bancoFondo, ingresoDetSocio, ingresoDetSocio.getBdIngCajaMontoPagado(), usuario));
													}													
												}else {
													lstIngresoDetalle.add(seteoIngresoDetalle(md, intIdEmpresa, documentoGeneral, bancoFondo, ingresoDetSocio, ingresoDetSocio.getBdIngCajaMontoPagado(), usuario));
												}
											}
										}
									} else if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_REGULARIZACION)) {
										//validamos el para_tipo_concepto
										if (ingresoDetSocio.getLstCajaCuentaConceptoDetalle()!=null && !ingresoDetSocio.getLstCajaCuentaConceptoDetalle().isEmpty()) {
											for (CuentaConceptoDetalle ccd : ingresoDetSocio.getLstCajaCuentaConceptoDetalle()) {
												for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
													//validamos que el modelo corresponda para tipo concepto
													if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_TIPOCONCEPTO)) {
														if (mdn.getIntValor().equals(ccd.getIntParaTipoConceptoCod())) {
															blnParaTipoConcepto = true;
															break;
														}
													}
												}
												if (blnParaTipoConcepto) {
													for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
														//validamos que le corresponda item concepto											
														if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_ITEMCONCEPTO)) {
															if (mdn.getIntValor().equals(ccd.getIntItemConcepto())) {
																blnItemConcepto = true;
																break;
															}
														}
													}
													if (blnItemConcepto) {
														//validamos que el modelo tenga rol
														for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {																									
															if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_ROL)) {
																blnModeloRol = true;
																break;
															}
														}
														if (blnModeloRol) {
															//Si tuviera rol, se valida que sea el rol buscado
															for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {																									
																if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_ROL)) {
																	if (mdn.getIntDatoArgumento().equals(intPersonaRolC)) {
																		blnPersonaRol = true;
																		break;
																	}
																}
															}
															if (blnPersonaRol) {
																lstIngresoDetalle.add(seteoIngresoDetalle(md, intIdEmpresa, documentoGeneral, bancoFondo, ingresoDetSocio, ccd.getBdIngCajaMontoRegularizarCtaCptoDet(), usuario));
															}
														}else {
															lstIngresoDetalle.add(seteoIngresoDetalle(md, intIdEmpresa, documentoGeneral, bancoFondo, ingresoDetSocio, ccd.getBdIngCajaMontoRegularizarCtaCptoDet(), usuario));
														}
																												
													}
												}
											}
										}										
									}																		
								}
							}
						}
					}
				}				
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lstIngresoDetalle;
	}
	
	private IngresoDetalle seteoIngresoDetalle(ModeloDetalle modeloDetalle, Integer intIdEmpresa, DocumentoGeneral documentoGeneral, Bancofondo bancoFondo, ExpedienteComp ingresoDetSocio, BigDecimal bdMontoHaber, Usuario usuario){
		IngresoDetalle ingresoDetalle = new IngresoDetalle();
		ingresoDetalle.setId(new IngresoDetalleId());
		ingresoDetalle.getId().setIntPersEmpresa(ingresoDetSocio.getIntIngCajaIdEmpresa());
		ingresoDetalle.getId().setIntIdIngresoGeneral(null);
		ingresoDetalle.getId().setIntIdIngresoDetalle(null);
		ingresoDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
		ingresoDetalle.setIntParaTipoComprobante(null);
		ingresoDetalle.setStrSerieDocumento(null);
		ingresoDetalle.setStrNumeroDocumento(null);
		
		ingresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		ingresoDetalle.setIntPersPersonaGirado(documentoGeneral.getSocioIngreso().getIntIngCajaIdPersona());
		ingresoDetalle.setIntCuentaGirado(documentoGeneral.getSocioIngreso().getIntIngCajaIdCta());
		
		ingresoDetalle.setIntSucuIdSucursalIn(ingresoDetSocio.getIntIngCajaIdSucursalAdministra());
		ingresoDetalle.setIntSucuIdSubsucursalIn(ingresoDetSocio.getIntIngCajaIdSubSucursalAdministra());
		ingresoDetalle.setIntParaTipoMoneda(bancoFondo.getIntMonedaCod());
		ingresoDetalle.setBdAjusteDeposito(null);
		ingresoDetalle.setBdMontoCargo(null);
		
		ingresoDetalle.setIntParaTipoPagoCuenta(ingresoDetSocio.getIntIngCajaParaTipoPagoCuenta());
		ingresoDetalle.setBdMontoAbono(ingresoDetSocio.getBdIngCajaMontoPagado());
		
		//del modelo...
		ingresoDetalle.setStrDescripcionIngreso(modeloDetalle.getPlanCuenta().getStrDescripcion());
		ingresoDetalle.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntEmpresaPk());
		ingresoDetalle.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
		ingresoDetalle.setIntContPeriodoCuenta(modeloDetalle.getId().getIntContPeriodoCuenta());
		//fin
		ingresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		ingresoDetalle.setTsFechaRegistro(obtenerFechaActual());
		ingresoDetalle.setIntPersEmpresaUsuario(intIdEmpresa);
		ingresoDetalle.setIntPersPersonaUsuario(usuario.getPersona().getIntIdPersona());
		
		return ingresoDetalle;		
	}
	
	//jchavez 21.07.2014
	private List<LibroDiarioDetalle> generarLibroDiarioDetalleSocioHaber(List<ExpedienteComp> listaIngresosSocio, DocumentoGeneral documentoGeneral, Modelo modelo, Bancofondo bancoFondo, Usuario usuario, Integer intModalidadC, Integer intPersonaRolC) throws Exception{
		Integer intIdEmpresa = listaIngresosSocio.get(0).getIntIngCajaIdEmpresa();
		List<LibroDiarioDetalle> lstLibroDiarioDetalleHaber = new ArrayList<LibroDiarioDetalle>();
		CarteraCreditoDetalle cartera = null;
//		ModeloDetalle modeloDetalle = null;
//		BigDecimal bdMontoHaber = BigDecimal.ZERO;
		try {
			CarteraFacadeRemote carteraFacade = (CarteraFacadeRemote) EJBFactory.getRemote(CarteraFacadeRemote.class);
			
			//generacion del modelo detalle
			for (ExpedienteComp ingresoDetSocio : listaIngresosSocio) {
				//Caso Servicios
				if (ingresoDetSocio.getStrIngCajaNroSolicitud()!=null && ingresoDetSocio.getIntIngCajaFlagAmortizacionInteres()!=null) {
					ExpedienteId pId = new ExpedienteId();
					pId.setIntPersEmpresaPk(ingresoDetSocio.getIntIngCajaIdEmpresa());
					pId.setIntCuentaPk(ingresoDetSocio.getIntIngCajaIdCuenta());
					pId.setIntItemExpediente(ingresoDetSocio.getIntIngCajaId1());
					pId.setIntItemExpedienteDetalle(ingresoDetSocio.getIntIngCajaId2());
					cartera = carteraFacade.getListaPorMaxPerYPKExpediente(pId);
					if (ingresoDetSocio.getIntIngCajaFlagAmortizacionInteres().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION)) {
						if (modelo!=null) {							
							if (modelo.getListModeloDetalle()!=null && !modelo.getListModeloDetalle().isEmpty()) {
								for (ModeloDetalle md : modelo.getListModeloDetalle()) {
									Boolean blnConceptoGeneral = false;
									Boolean blnConfCredito = false;
									Boolean blnCategoriaRiesgo = false;
									if (md.getListModeloDetalleNivel()!=null && !md.getListModeloDetalleNivel().isEmpty()) {
										for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
											//validamos que el modelo corresponda a una amortizacion
											if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_CONCEPTOGRAL)) {
												if (mdn.getIntDatoArgumento().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION)) {
													blnConceptoGeneral = true;
													break;
												}
											}
										}
										if (blnConceptoGeneral) {
											for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
												//validamos que el modelo corresponda a un expediente
												if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_CONFCREDITO)) {
													if (mdn.getIntDatoTablas().equals(ingresoDetSocio.getIntIngCajaParaTipoX()) && mdn.getIntDatoArgumento().equals(ingresoDetSocio.getIntIngCajaItemX())) {
														blnConfCredito = true;
														break;
													}
												}
											}
											if (blnConfCredito) {
												for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
													//validamos que le corresponda una categoria de riesgo													
													if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_CATEGORIARIESGO)) {
														if (cartera!=null) {
															if (mdn.getIntDatoArgumento().equals(cartera.getIntParaTipocategoriariesgo())) {
																blnCategoriaRiesgo = true;
																break;
															}
														}else{
															if (mdn.getIntDatoArgumento().equals(1)) { //vigente
																blnCategoriaRiesgo = true;
																break;
															}
														}														
													}
												}
												if (blnCategoriaRiesgo) {
//													ingresoDetSocio.setIngCajaModeloDetalle(md);
													lstLibroDiarioDetalleHaber.add(seteoLibroDiarioDetalle(md, intIdEmpresa, documentoGeneral, bancoFondo, ingresoDetSocio, ingresoDetSocio.getBdIngCajaMontoPagado()));
												}
											}											
										}
									}
								}
							}
						}
					}else if (ingresoDetSocio.getIntIngCajaFlagAmortizacionInteres().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES)) {
						if (modelo!=null) {							
							if (modelo.getListModeloDetalle()!=null && !modelo.getListModeloDetalle().isEmpty()) {
								for (ModeloDetalle md : modelo.getListModeloDetalle()) {
									Boolean blnConceptoGeneral = false;
									Boolean blnConfCredito = false;
									Boolean blnCategoriaRiesgo = false;
									if (md.getListModeloDetalleNivel()!=null && !md.getListModeloDetalleNivel().isEmpty()) {
										for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
											//validamos que el modelo corresponda a una amortizacion
											if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_CONCEPTOGRAL)) {
												if (mdn.getIntDatoArgumento().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES)) {
													blnConceptoGeneral = true;
													break;
												}
											}
										}
										if (blnConceptoGeneral) {
											for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
												//validamos que el modelo corresponda a un expediente
												if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_CONFCREDITO)) {
													if (mdn.getIntDatoTablas().equals(ingresoDetSocio.getIntIngCajaParaTipoX()) && mdn.getIntDatoArgumento().equals(ingresoDetSocio.getIntIngCajaItemX())) {
														blnConfCredito = true;
														break;
													}
												}
											}
											if (blnConfCredito) {
												for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
													//validamos que le corresponda una categoria de riesgo													
													if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_CATEGORIARIESGO)) {
														if (cartera!=null) {
															if (mdn.getIntDatoArgumento().equals(cartera.getIntParaTipocategoriariesgo())) {
																blnCategoriaRiesgo = true;
																break;
															}
														}else{
															if (mdn.getIntDatoArgumento().equals(1)) { //vigente
																blnCategoriaRiesgo = true;
																break;
															}
														}														
													}
												}
												if (blnCategoriaRiesgo) {
//													ingresoDetSocio.setIngCajaModeloDetalle(md);
													lstLibroDiarioDetalleHaber.add(seteoLibroDiarioDetalle(md, intIdEmpresa, documentoGeneral, bancoFondo, ingresoDetSocio, ingresoDetSocio.getBdIngCajaMontoPagado()));
												}
											}											
										}
									}
								}
							}
						}
					}
				}else if (ingresoDetSocio.getStrIngCajaNroSolicitud()==null && ingresoDetSocio.getIntIngCajaFlagAmortizacionInteres()==null) {//Caso Beneficios
					if (modelo!=null) {							
						if (modelo.getListModeloDetalle()!=null && !modelo.getListModeloDetalle().isEmpty()) {
							for (ModeloDetalle md : modelo.getListModeloDetalle()) {
								Boolean blnParaTipoConcepto = false;
								Boolean blnItemConcepto = false;
								Boolean blnPersonaRol = false;
								Boolean blnModeloRol = false;
								if (md.getListModeloDetalleNivel()!=null && !md.getListModeloDetalleNivel().isEmpty()) {
									if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PAGOMESSGTE) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_APORTACIONES) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PLANILLA_ENVIADA)) {
										//validamos el para_tipo_concepto
										for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
											//validamos que el modelo corresponda a un expediente
											if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_TIPOCONCEPTO)) {
												if (mdn.getIntValor().equals(ingresoDetSocio.getIntIngCajaParaTipoX())) {
													blnParaTipoConcepto = true;
													break;
												}
											}
										}
										if (blnParaTipoConcepto) {
											for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
												//validamos que le corresponda una categoria de riesgo													
												if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_ITEMCONCEPTO)) {
													if (mdn.getIntValor().equals(ingresoDetSocio.getIntIngCajaItemX())) {
														blnItemConcepto = true;
														break;
													}
												}
											}
											if (blnItemConcepto) {
												//validamos que el modelo tenga rol
												for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {																									
													if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_ROL)) {
														blnModeloRol = true;
														break;
													}
												}
												if (blnModeloRol) {
													//Si tuviera rol, se valida que sea el rol buscado
													for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {																									
														if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_ROL)) {
															if (mdn.getIntDatoArgumento().equals(intPersonaRolC)) {
																blnPersonaRol = true;
																break;
															}
														}
													}
													if (blnPersonaRol) {
														//ingresoDetSocio.setIngCajaModeloDetalle(md);
														lstLibroDiarioDetalleHaber.add(seteoLibroDiarioDetalle(md, intIdEmpresa, documentoGeneral, bancoFondo, ingresoDetSocio, ingresoDetSocio.getBdIngCajaMontoPagado()));
													}
												}else {
													lstLibroDiarioDetalleHaber.add(seteoLibroDiarioDetalle(md, intIdEmpresa, documentoGeneral, bancoFondo, ingresoDetSocio, ingresoDetSocio.getBdIngCajaMontoPagado()));
												}
											}
										}
									} else if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_REGULARIZACION)) {
										//validamos el para_tipo_concepto
										if (ingresoDetSocio.getLstCajaCuentaConceptoDetalle()!=null && !ingresoDetSocio.getLstCajaCuentaConceptoDetalle().isEmpty()) {
											for (CuentaConceptoDetalle ccd : ingresoDetSocio.getLstCajaCuentaConceptoDetalle()) {
												for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
													//validamos que el modelo corresponda a un expediente
													if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_TIPOCONCEPTO)) {
														if (mdn.getIntValor().equals(ccd.getIntParaTipoConceptoCod())) {
															blnParaTipoConcepto = true;
															break;
														}
													}
												}
												if (blnParaTipoConcepto) {
													for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {
														//validamos que le corresponda una categoria de riesgo													
														if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_ITEMCONCEPTO)) {
															if (mdn.getIntValor().equals(ccd.getIntItemConcepto())) {
																blnItemConcepto = true;
																break;
															}
														}
													}
													if (blnItemConcepto) {
														//validamos que el modelo tenga rol
														for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {																									
															if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_ROL)) {
																blnModeloRol = true;
																break;
															}
														}
														if (blnModeloRol) {
															//Si tuviera rol, se valida que sea el rol buscado
															for (ModeloDetalleNivel mdn : md.getListModeloDetalleNivel()) {																									
																if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_INGRESOCAJA_ROL)) {
																	if (mdn.getIntDatoArgumento().equals(intPersonaRolC)) {
																		blnPersonaRol = true;
																		break;
																	}
																}
															}
															if (blnPersonaRol) {	
//																ccd.setIngCajaModeloDetalle(md);															
																lstLibroDiarioDetalleHaber.add(seteoLibroDiarioDetalle(md, intIdEmpresa, documentoGeneral, bancoFondo, ingresoDetSocio, ccd.getBdIngCajaMontoRegularizarCtaCptoDet()));																
															}
														}else{
															lstLibroDiarioDetalleHaber.add(seteoLibroDiarioDetalle(md, intIdEmpresa, documentoGeneral, bancoFondo, ingresoDetSocio, ccd.getBdIngCajaMontoRegularizarCtaCptoDet()));
														}
													}
												}
											}
										}										
									}																		
								}
							}
						}
					}
				}				
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		
		return lstLibroDiarioDetalleHaber;
	}
	
	private LibroDiarioDetalle seteoLibroDiarioDetalle(ModeloDetalle modeloDetalle, Integer intIdEmpresa, DocumentoGeneral documentoGeneral, Bancofondo bancoFondo, ExpedienteComp ingresoDetSocio, BigDecimal bdMontoHaber){
		LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
		libroDiarioDetalleHaber.setId(new LibroDiarioDetalleId());
		libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
		libroDiarioDetalleHaber.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
		libroDiarioDetalleHaber.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
		libroDiarioDetalleHaber.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
		libroDiarioDetalleHaber.setIntPersPersona(documentoGeneral.getSocioIngreso().getIntIngCajaIdPersona());
		libroDiarioDetalleHaber.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
		libroDiarioDetalleHaber.setStrSerieDocumento(null);
		libroDiarioDetalleHaber.setStrNumeroDocumento(null);
		libroDiarioDetalleHaber.setIntPersEmpresaSucursal(intIdEmpresa);
		libroDiarioDetalleHaber.setIntSucuIdSucursal(ingresoDetSocio.getIntIngCajaIdSucursalAdministra());
		libroDiarioDetalleHaber.setIntSudeIdSubSucursal(ingresoDetSocio.getIntIngCajaIdSubSucursalAdministra());
		libroDiarioDetalleHaber.setIntParaMonedaDocumento(bancoFondo.getIntMonedaCod());
		libroDiarioDetalleHaber.setStrComentario(modeloDetalle.getPlanCuenta().getStrDescripcion());
		libroDiarioDetalleHaber.setBdDebeSoles(null);
		libroDiarioDetalleHaber.setBdHaberExtranjero(null);
		libroDiarioDetalleHaber.setBdDebeExtranjero(null);
		libroDiarioDetalleHaber.setBdHaberSoles(bdMontoHaber);
		
		return libroDiarioDetalleHaber;		
	}
	
	/**
     * Autor: jchavez / Tarea: Creación / Fecha: 13.07.2014 / 
   	 * Funcionalidad: Método que realiza las grabaciones a las diferentes tablas vinculadas al Ingreso Caja - Socio
   	 * @author jchavez
   	 * @version 1.0
   	 * @param listaIngresosSocio
   	 * @param documentoGeneral
   	 * @param usuario
   	 * @param intModalidadC
   	 * @return ingreso - ingreso grabado.
   	 * @throws BusinessException
     */
	public Ingreso grabarIngresoSocio(List<ExpedienteComp> listaIngresosSocio, DocumentoGeneral documentoGeneral, Usuario usuario, Integer intModalidadC) throws BusinessException{
		Ingreso ingreso = documentoGeneral.getIngresoCaja();
		try{
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);

			//1. Grabacion del ingreso.
			ingreso = grabarIngresoGeneral(ingreso);
			
			//2. Generacion y grabación de Recibo Manual Detalle.
			ReciboManual reciboManual = documentoGeneral.getReciboManualIngreso();
			if(reciboManual != null){
				GestorCobranza gestorCobranza = documentoGeneral.getGestorCobranza();
				ReciboManualDetalle reciboManualDetalle = generarReciboManualDetalle(reciboManual, ingreso, gestorCobranza);
				log.info(reciboManualDetalle);
				reciboManualDetalle = boReciboManualDetalle.grabar(reciboManualDetalle);
				reciboManual.setReciboManualDetalleUltimo(reciboManualDetalle);
			}
			
			
			//3. Generación y grabación de Movimiento (CMO_MOVIMIENTOCTACTE)
			List<Movimiento> lstMovimiento = generarMovimiento(listaIngresosSocio,documentoGeneral, ingreso, reciboManual, usuario, intModalidadC);
			log.info("Movimiento generado ---> "+lstMovimiento);
			if (lstMovimiento!=null && !lstMovimiento.isEmpty()) {
				for (Movimiento mov : lstMovimiento) {
					mov = conceptoFacade.grabarMovimiento(mov);
					//3.1 Si el tipo concepto general del movimiento es Interes, se procede a registrar el interes cancelado
					if (mov.getIntParaTipoConceptoGeneral().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES)) {
						//a. Generación del objeto
						InteresCancelado interesCancelado = new InteresCancelado();
						interesCancelado.setId(new InteresCanceladoId());
						interesCancelado.getId().setIntCuentaPk(mov.getIntCuenta());
						interesCancelado.getId().setIntItemExpediente(mov.getIntItemExpediente());
						interesCancelado.getId().setIntItemExpedienteDetalle(mov.getIntItemExpedienteDetalle());
						interesCancelado.getId().setIntItemMovCtaCte(mov.getIntItemMovimiento());
						interesCancelado.getId().setIntPersEmpresaPk(mov.getIntPersEmpresa());
						interesCancelado.setBdMontoInteres(mov.getBdMontoMovimiento());
						interesCancelado.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						interesCancelado.setIntParaTipoFormaPago(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
						interesCancelado.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
						for (ExpedienteComp x : listaIngresosSocio) {
							if (x.getIntIngCajaFlagAmortizacionInteres()!=null && x.getIntIngCajaFlagAmortizacionInteres().equals(2)
									&& mov.getIntItemExpediente().equals(x.getIntIngCajaId1())
									&& mov.getIntItemExpedienteDetalle().equals(x.getIntIngCajaId2())) {
								interesCancelado.setBdTasa(x.getBdIngCajaPorcentajeInteres());
								interesCancelado.setTsFechaInicio(convertirDateToTimeStamp(x.getDtFechaInicioInteresCancelado()));
								interesCancelado.setIntDias(x.getIntDiasEntreFechasInteresCancelado());
								interesCancelado.setBdSaldoCredito(x.getBdIngCajaSaldoAnteriorAlPago());
							}
						}
						//b. Grabación del Interes Cancelado
						interesCancelado=conceptoFacade.grabarInteresCancelado(interesCancelado);
					}
					//3.2 Si el tipo concepto general del movimiento corresponde a un beneficio...
					if (mov.getIntParaTipoConceptoGeneral().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_APORTACION)
							|| mov.getIntParaTipoConceptoGeneral().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO)
							|| mov.getIntParaTipoConceptoGeneral().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO)
							|| mov.getIntParaTipoConceptoGeneral().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO)
							|| mov.getIntParaTipoConceptoGeneral().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AHORRO)
							|| mov.getIntParaTipoConceptoGeneral().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_DEPOSITO)) {
						//3.2.1 Caso Pago Mes Siguiente
						if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PAGOMESSGTE) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PLANILLA_ENVIADA)) {
							for (ExpedienteComp x : listaIngresosSocio) {
								if (mov.getIntItemCuentaConcepto().equals(x.getIntIngCajaId1())) {
									//a. Recuperamos los Conceptos Pago por CtaCptoDetalle y periodo
									ConceptoPago cptoPgo = new ConceptoPago();
									cptoPgo.setId(new ConceptoPagoId());									
									CuentaConceptoDetalleId pId2 = new CuentaConceptoDetalleId();
									pId2.setIntPersEmpresaPk(x.getIntIngCajaIdEmpresa());
									pId2.setIntCuentaPk(documentoGeneral.getSocioIngreso().getIntIngCajaIdCta());
									pId2.setIntItemCuentaConcepto(x.getIntIngCajaId1());
									pId2.setIntItemCtaCptoDet(x.getIntIngCajaId2());									
									List<ConceptoPago> lstCptoPgo = conceptoFacade.getListaConceptoPagoPorCtaCptoDetYPeriodo(pId2, documentoGeneral.getIntPeriodoPlanilla(), documentoGeneral.getIntPeriodoPlanilla());
									
									//b. Grabamos el Concepto Pago
									//b.1. SI EXISTIERA, se actualiza Concepto Pago.
									if (lstCptoPgo!=null && !lstCptoPgo.isEmpty()) {
										for (ConceptoPago conceptoPago : lstCptoPgo) {
											conceptoPago.setBdMontoPago(conceptoPago.getBdMontoPago().add(mov.getBdMontoMovimiento()));
											conceptoPago.setBdMontoSaldo(conceptoPago.getBdMontoSaldo().subtract(mov.getBdMontoMovimiento()));
											cptoPgo = conceptoFacade.modificarConceptoPago(conceptoPago);
										}
									}
									//b.2. SI NO EXISTIERA, se graba Concepto Pago.
									else{
										cptoPgo.getId().setIntPersEmpresaPk(mov.getIntPersEmpresa());
										cptoPgo.getId().setIntCuentaPk(mov.getIntCuenta());
										cptoPgo.getId().setIntItemCuentaConcepto(mov.getIntItemCuentaConcepto());
										cptoPgo.getId().setIntItemCtaCptoDet(x.getIntIngCajaId2());
//										CuentaConceptoDetalle ctaCptoDet = conceptoFacade.getCuentaConceptoDetallePorPK(pId2);
										cptoPgo.setBdMontoSaldo(conceptoFacade.getCuentaConceptoDetallePorPK(pId2).getBdMontoConcepto().subtract(mov.getBdMontoMovimiento()));
										cptoPgo.setIntPeriodo(documentoGeneral.getIntPeriodoPlanilla());
										cptoPgo.setBdMontoPago(mov.getBdMontoMovimiento());
										cptoPgo.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
										cptoPgo = conceptoFacade.grabarConceptoPago(cptoPgo);
									}
									
									//c. Grabamos el Concepto Pago Detalle.
									ConceptoDetallePago cptoPgoDet = new ConceptoDetallePago();
									cptoPgoDet.setId(new ConceptoDetallePagoId());
									cptoPgoDet.getId().setIntPersEmpresaPk(cptoPgo.getId().getIntPersEmpresaPk());
									cptoPgoDet.getId().setIntCuentaPk(cptoPgo.getId().getIntCuentaPk());
									cptoPgoDet.getId().setIntItemCuentaConcepto(cptoPgo.getId().getIntItemCuentaConcepto());
									cptoPgoDet.getId().setIntItemCtaCptoDet(cptoPgo.getId().getIntItemCtaCptoDet());
								    cptoPgoDet.getId().setIntItemConceptoPago(cptoPgo.getId().getIntItemConceptoPago());
								    cptoPgoDet.getId().setIntItemMovCtaCte(mov.getIntItemMovimiento());
								    cptoPgoDet.setIntTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
								    cptoPgoDet.setBdMonto(cptoPgo.getBdMontoPago());
								    cptoPgoDet = conceptoFacade.grabarConceptoDetallePago(cptoPgoDet);
								}
							}
						}
						//3.2.2 Caso Regularización
						else if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_REGULARIZACION)) {
							for (ExpedienteComp x : listaIngresosSocio) {
								if (mov.getIntItemCuentaConcepto().equals(x.getIntIngCajaId1())) {
									ConceptoPago cptoPgo = new ConceptoPago();
									cptoPgo.setId(new ConceptoPagoId());
									//Por cada Cuenta Concepto Detalle a regularizar , se realiza su registro de Concepto Pago
									if (x.getLstCajaCuentaConceptoDetalle()!=null && !x.getLstCajaCuentaConceptoDetalle().isEmpty()) {
										for (CuentaConceptoDetalle ctaCptoDet : x.getLstCajaCuentaConceptoDetalle()) {
											if (ctaCptoDet.getListaConceptoPago()!=null && !ctaCptoDet.getListaConceptoPago().isEmpty()) {
												Collections.sort(ctaCptoDet.getListaConceptoPago(), new Comparator<ConceptoPago>() {
										            public int compare(ConceptoPago o1, ConceptoPago o2) {
										            	ConceptoPago e1 = (ConceptoPago) o1;
										            	ConceptoPago e2 = (ConceptoPago) o2;
										                return e1.getIntPeriodo().compareTo(e2.getIntPeriodo());
										            }
										        });
												Boolean blnGrabOk = false;
												for (ConceptoPago o : ctaCptoDet.getListaConceptoPago()) {
													blnGrabOk = false;
													List<ConceptoPago> lstCptoPgo = conceptoFacade.getListaConceptoPagoPorCtaCptoDetYPeriodo(ctaCptoDet.getId(), o.getIntPeriodo(), o.getIntPeriodo());
													
													//a.Grabamos el Concepto Pago
													//a.1 SI EXISTIERA, se actualiza Concepto Pago.
													if (lstCptoPgo!=null && !lstCptoPgo.isEmpty()) {
														for (ConceptoPago conceptoPago : lstCptoPgo) {
															conceptoPago.setBdMontoPago(o.getBdMontoPago());
															conceptoPago.setBdMontoSaldo(o.getBdMontoSaldo());
															cptoPgo = conceptoFacade.modificarConceptoPago(conceptoPago);
															blnGrabOk = true;
														}
													}
													//a.2 SI NO EXISTIERA, se graba Concepto Pago.
													else{
														if (o.getBdMontoPago().compareTo(BigDecimal.ZERO)==1 ) {
															cptoPgo.getId().setIntPersEmpresaPk(ctaCptoDet.getId().getIntPersEmpresaPk());
															cptoPgo.getId().setIntCuentaPk(ctaCptoDet.getId().getIntCuentaPk());
															cptoPgo.getId().setIntItemCuentaConcepto(ctaCptoDet.getId().getIntItemCuentaConcepto());
															cptoPgo.getId().setIntItemCtaCptoDet(ctaCptoDet.getId().getIntItemCtaCptoDet());
															cptoPgo.setBdMontoPago(o.getBdMontoPago());
															cptoPgo.setBdMontoSaldo(o.getBdMontoSaldo());													
															cptoPgo.setIntPeriodo(o.getIntPeriodo());														
															cptoPgo.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
															cptoPgo = conceptoFacade.grabarConceptoPago(cptoPgo);
															blnGrabOk = true;
														}														
													}
													//b. Grabamos Concepto Pago Detalle
													if (blnGrabOk) {
														ConceptoDetallePago cptoPgoDet = new ConceptoDetallePago();
														cptoPgoDet.setId(new ConceptoDetallePagoId());
														cptoPgoDet.getId().setIntPersEmpresaPk(cptoPgo.getId().getIntPersEmpresaPk());
														cptoPgoDet.getId().setIntCuentaPk(cptoPgo.getId().getIntCuentaPk());
														cptoPgoDet.getId().setIntItemCuentaConcepto(cptoPgo.getId().getIntItemCuentaConcepto());
														cptoPgoDet.getId().setIntItemCtaCptoDet(cptoPgo.getId().getIntItemCtaCptoDet());
													    cptoPgoDet.getId().setIntItemConceptoPago(cptoPgo.getId().getIntItemConceptoPago());
													    cptoPgoDet.getId().setIntItemMovCtaCte(mov.getIntItemMovimiento());
													    cptoPgoDet.setIntTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);												    
													    cptoPgoDet.setBdMonto(cptoPgo.getBdMontoPago());
													    cptoPgoDet = conceptoFacade.grabarConceptoDetallePago(cptoPgoDet);
													}													
												}
											}
										}
									}
								}
							}
						}
						//3.2.3 Caso Aportaciones
						else if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_APORTACIONES)) {
							for (ExpedienteComp x : listaIngresosSocio) {
								if (mov.getIntItemCuentaConcepto().equals(x.getIntIngCajaId1())) {
									//Por cada Cuenta Concepto Detalle a regularizar , se realiza su registro de Concepto Pago
									if (x.getLstCajaCuentaConceptoDetalle()!=null && !x.getLstCajaCuentaConceptoDetalle().isEmpty()) {
										for (CuentaConceptoDetalle ctaCptoDet : x.getLstCajaCuentaConceptoDetalle()) {
											if (ctaCptoDet.getListaConceptoPago()!=null && !ctaCptoDet.getListaConceptoPago().isEmpty()) {
												for (ConceptoPago o : ctaCptoDet.getListaConceptoPago()) {
													List<ConceptoPago> lstCptoPgo = conceptoFacade.getListaConceptoPagoPorCtaCptoDetYPeriodo(ctaCptoDet.getId(), o.getIntPeriodo(), o.getIntPeriodo());
													//a.Grabamos el Concepto Pago
													//a.1 SI EXISTIERA, se actualiza Concepto Pago.
													if (lstCptoPgo!=null && !lstCptoPgo.isEmpty()) {
														conceptoFacade.modificarConceptoPago(o);
													}
													//a.2 SI NO EXISTIERA, se graba Concepto Pago.
													else{
														o = conceptoFacade.grabarConceptoPago(o);
													}
													//b. Grabamos Concepto Pago Detalle.
													ConceptoDetallePago cptoPgoDet = new ConceptoDetallePago();
													cptoPgoDet.setId(new ConceptoDetallePagoId());
													cptoPgoDet.getId().setIntPersEmpresaPk(o.getId().getIntPersEmpresaPk());
													cptoPgoDet.getId().setIntCuentaPk(o.getId().getIntCuentaPk());
													cptoPgoDet.getId().setIntItemCuentaConcepto(o.getId().getIntItemCuentaConcepto());
													cptoPgoDet.getId().setIntItemCtaCptoDet(o.getId().getIntItemCtaCptoDet());
												    cptoPgoDet.getId().setIntItemConceptoPago(o.getId().getIntItemConceptoPago());
												    cptoPgoDet.getId().setIntItemMovCtaCte(mov.getIntItemMovimiento());
												    cptoPgoDet.setIntTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);												    
												    cptoPgoDet.setBdMonto(o.getBdMontoPago());
												    cptoPgoDet = conceptoFacade.grabarConceptoDetallePago(cptoPgoDet);
												}
											}
										}
									}
								}
							}
						}				
					}
				}
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ingreso;
	}
	
    public static Timestamp convertirDateToTimeStamp(Date fecha){
        return new Timestamp(fecha.getTime());
    }
    
    /**
     * Autor: jchavez / Tarea: Creación / Fecha: 13.07.2014 / 
   	 * Funcionalidad: Método que genera movimientos, y realiza grabaciones y modificaciones en
   	 * tablas cronograma, estadoCredito, expediente, CtaCptoDetalle, bloqueoCta y bloqueoPeriodo.
   	 * @author jchavez
   	 * @version 1.0
     * @param listaIngresosSocio
     * @param documentoGeneral
     * @param ingreso
     * @param reciboManual
     * @param usuario
     * @param intModalidadC
     * @return lstMovimientos - lista de movimientos generados.
     * @throws Exception
     */
	private List<Movimiento> generarMovimiento(List<ExpedienteComp> listaIngresosSocio, DocumentoGeneral documentoGeneral, Ingreso ingreso, ReciboManual reciboManual, Usuario usuario, Integer intModalidadC) throws Exception{
		List<Movimiento> lstMovimientos = new ArrayList<Movimiento>();

		try {
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			for (ExpedienteComp expedienteComp : listaIngresosSocio) {
				
				Movimiento movimiento = new Movimiento();
				movimiento.setTsFechaMovimiento(ingreso.getTsFechaProceso());
				movimiento.setIntPersEmpresa(expedienteComp.getIntIngCajaIdEmpresa());
				movimiento.setIntCuenta(documentoGeneral.getSocioIngreso().getIntIngCajaIdCta());
				movimiento.setIntPersPersonaIntegrante(documentoGeneral.getSocioIngreso().getIntIngCajaIdPersona());
				//1. Caso Amortización - Interés
				if (expedienteComp.getIntIngCajaFlagAmortizacionInteres()!=null) {
					movimiento.setIntItemCuentaConcepto(null);
					movimiento.setIntItemExpediente(expedienteComp.getIntIngCajaId1());
					movimiento.setIntItemExpedienteDetalle(expedienteComp.getIntIngCajaId2());
					//Recuperamos el expediente a ser modificado
					ExpedienteId pId = new ExpedienteId();
					pId.setIntPersEmpresaPk(expedienteComp.getIntIngCajaIdEmpresa());
					pId.setIntCuentaPk(documentoGeneral.getSocioIngreso().getIntIngCajaIdCta());
					pId.setIntItemExpediente(expedienteComp.getIntIngCajaId1());
					pId.setIntItemExpedienteDetalle(expedienteComp.getIntIngCajaId2());
					Expediente exp = conceptoFacade.getExpedientePorPK(pId);					
					//1.2 Si es de tipo amortización...
					if (expedienteComp.getIntIngCajaFlagAmortizacionInteres().equals(1)) {
						movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION);
						//a. Recuperamos el ultimo movimiento
						Movimiento ultimoMovimiento = conceptoFacade.getMaxXExpYCtoGral(expedienteComp.getIntIngCajaIdEmpresa(),documentoGeneral.getSocioIngreso().getIntIngCajaIdCta(),expedienteComp.getIntIngCajaId1(),expedienteComp.getIntIngCajaId2(),Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION);
						movimiento.setBdMontoMovimiento(expedienteComp.getBdIngCajaMontoPagado());
						movimiento.setBdMontoSaldo(ultimoMovimiento.getBdMontoSaldo().subtract(expedienteComp.getBdIngCajaMontoPagado()));
						//b. Actualización del expediente movimiento
						exp.setBdSaldoCredito(movimiento.getBdMontoSaldo());
						conceptoFacade.modificarExpediente(exp);
						//c. Actualización del cronograma
						//c.1 Si la modalidad es DIFERENTE a Adelanto - Cancelación
						if (!intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_ADELANTO_CANCELACION)) {
							List<Cronograma> lstCronograma = conceptoFacade.getListaCronogramaPorPkExpediente(pId);
							if (lstCronograma!=null && !lstCronograma.isEmpty()) {
								for (Cronograma cronograma : lstCronograma) {
									if (cronograma.getIntPeriodoPlanilla().equals(documentoGeneral.getIntPeriodoPlanilla()) && cronograma.getIntParaTipoConceptoCreditoCod().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION)) {
										cronograma.setBdSaldoDetalleCredito(cronograma.getBdSaldoDetalleCredito().subtract(expedienteComp.getBdIngCajaMontoPagado()));
										/* Autor: jchavez / Tarea: Modificacion / Fecha: 15.07.2014 
										 * Funcionalidad: Se agrega método de modificación del cronograma */										
										cronograma = conceptoFacade.modificarCronograma(cronograma);
									}
								}
							}
						}
						//c.2 Si la modalidad es Adelanto - Cancelación
						else if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_ADELANTO_CANCELACION)) {
							BigDecimal bdMonto = expedienteComp.getBdIngCajaMontoPagado();
							List<Cronograma> lstCronograma = conceptoFacade.getListaCronogramaPorPkExpediente(pId);
							//Ordenamos la lista cronograma de manera descendente.
							Collections.sort(lstCronograma, new Comparator<Cronograma>() {
					            public int compare(Cronograma o1, Cronograma o2) {
					            	Cronograma e1 = (Cronograma) o1;
					            	Cronograma e2 = (Cronograma) o2;
					                return e2.getId().getIntItemCronograma().compareTo(e1.getId().getIntItemCronograma());
					            }
					        });
							log.info("Cronograma ordenado descendentemente: "+lstCronograma);
							//Se realiza modificación del cronograma
							if (lstCronograma!=null && !lstCronograma.isEmpty()) {
								for (Cronograma cronograma : lstCronograma) {
									if (cronograma.getIntParaTipoConceptoCreditoCod().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION) && !bdMonto.equals(BigDecimal.ZERO) && !cronograma.getBdSaldoDetalleCredito().equals(BigDecimal.ZERO)) {
										if (cronograma.getBdSaldoDetalleCredito().compareTo(bdMonto)==1) {
											cronograma.setBdSaldoDetalleCredito(cronograma.getBdSaldoDetalleCredito().subtract(bdMonto));
											bdMonto = BigDecimal.ZERO;
											cronograma = conceptoFacade.modificarCronograma(cronograma);
											break;
										}else if (cronograma.getBdSaldoDetalleCredito().compareTo(bdMonto)==0 || cronograma.getBdSaldoDetalleCredito().compareTo(bdMonto)==-1) {
											bdMonto = bdMonto.subtract(cronograma.getBdSaldoDetalleCredito());
											cronograma.setBdSaldoDetalleCredito(BigDecimal.ZERO);
											cronograma = conceptoFacade.modificarCronograma(cronograma);
										}
									}
								}
							}
						}
						
						//c.2.1 Registrar Estado Credito
						if (movimiento.getBdMontoSaldo().compareTo(BigDecimal.ZERO)==0) {
							EstadoExpediente estadoExpedienteCancelado = new EstadoExpediente();
							estadoExpedienteCancelado.setId(new EstadoExpedienteId());
							//Seteando valores...
							estadoExpedienteCancelado.getId().setIntEmpresaEstado(expedienteComp.getIntIngCajaIdEmpresa());
							estadoExpedienteCancelado.setIntEmpresa(expedienteComp.getIntIngCajaIdEmpresa());
							estadoExpedienteCancelado.setIntCuenta(documentoGeneral.getSocioIngreso().getIntIngCajaIdCta());
							estadoExpedienteCancelado.setIntItemCuentaConcepto(null);
							estadoExpedienteCancelado.setIntItemExpediente(expedienteComp.getIntIngCajaId1());
							estadoExpedienteCancelado.setIntItemDetExpediente(expedienteComp.getIntIngCajaId2());
							estadoExpedienteCancelado.setIntParaEstadoExpediente(2); //Estado Vigente
							estadoExpedienteCancelado.setIntIndicadorEntrega(null);
							conceptoFacade.grabarEstado(estadoExpedienteCancelado);
						}
					}
					//1.3 Si es de tipo interés...
					else {
						//a. Recuperamos el ultimo movimiento
						Movimiento ultimoMovimiento = conceptoFacade.getMaxXExpYCtoGral(expedienteComp.getIntIngCajaIdEmpresa(),documentoGeneral.getSocioIngreso().getIntIngCajaIdCta(),expedienteComp.getIntIngCajaId1(),expedienteComp.getIntIngCajaId2(),Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES);
						if (ultimoMovimiento == null) {
							movimiento.setBdMontoSaldo(exp.getBdSaldoInteres().subtract(expedienteComp.getBdIngCajaMontoPagado()));
						}else{
							movimiento.setBdMontoSaldo(ultimoMovimiento.getBdMontoSaldo().subtract(expedienteComp.getBdIngCajaMontoPagado()));
						}
						movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES);
						movimiento.setBdMontoMovimiento(expedienteComp.getBdIngCajaMontoPagado());
						//b. Actualización del expediente movimiento
						exp.setBdSaldoInteres(movimiento.getBdMontoSaldo());
						conceptoFacade.modificarExpediente(exp);
					}
					movimiento.setStrNumeroDocumento(""+expedienteComp.getIntIngCajaId1()+"-"+expedienteComp.getIntIngCajaId2());
				}
				//2. Caso Beneficio
				else{
					movimiento.setIntItemCuentaConcepto(expedienteComp.getIntIngCajaId1());
					movimiento.setIntItemExpediente(null);
					movimiento.setIntItemExpedienteDetalle(null);
					
					if (expedienteComp.getIntIngCajaParaTipoX().equals(1)) {
						movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_APORTACION);
					}else if (expedienteComp.getIntIngCajaParaTipoX().equals(2)) {
						movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO);
					}else if (expedienteComp.getIntIngCajaParaTipoX().equals(3)) {
						movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
					}else if (expedienteComp.getIntIngCajaParaTipoX().equals(4)) {
						movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AHORRO);
					}else if (expedienteComp.getIntIngCajaParaTipoX().equals(5)) {
						movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_DEPOSITO);
					}else if (expedienteComp.getIntIngCajaParaTipoX().equals(10)) {
						movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO);
					}
					movimiento.setBdMontoMovimiento(expedienteComp.getBdIngCajaMontoPagado());
					List<Movimiento> lstUltimoMovimiento = conceptoFacade.getListaMaximoMovimientoPorCuentaConcepto(expedienteComp.getIntIngCajaIdEmpresa(), documentoGeneral.getSocioIngreso().getIntIngCajaIdCta(), expedienteComp.getIntIngCajaId1(),movimiento.getIntParaTipoConceptoGeneral());
					if (lstUltimoMovimiento!=null && !lstUltimoMovimiento.isEmpty()) {
						Movimiento ultimoMovimiento = lstUltimoMovimiento.get(0);						
						movimiento.setBdMontoSaldo(ultimoMovimiento.getBdMontoSaldo().add(expedienteComp.getBdIngCajaMontoPagado()));
					}else{
						movimiento.setBdMontoSaldo(expedienteComp.getBdIngCajaMontoPagado());
					}
					//a. Actualización de Cuenta Concepto
					CuentaConceptoId pId = new CuentaConceptoId();
					pId.setIntPersEmpresaPk(expedienteComp.getIntIngCajaIdEmpresa());
					pId.setIntCuentaPk(documentoGeneral.getSocioIngreso().getIntIngCajaIdCta());
					pId.setIntItemCuentaConcepto(expedienteComp.getIntIngCajaId1());
					CuentaConcepto ctaCpto = conceptoFacade.getCuentaConceptoPorPK(pId);
					ctaCpto.setBdSaldo(movimiento.getBdMontoSaldo());
					ctaCpto = conceptoFacade.modificarCuentaConcepto(ctaCpto);
					if (!intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_REGULARIZACION)) {
						//Actualización de Cuenta Concepto Detalle
						CuentaConceptoDetalleId pId2 = new CuentaConceptoDetalleId();
						pId2.setIntPersEmpresaPk(expedienteComp.getIntIngCajaIdEmpresa());
						pId2.setIntCuentaPk(documentoGeneral.getSocioIngreso().getIntIngCajaIdCta());
						pId2.setIntItemCuentaConcepto(expedienteComp.getIntIngCajaId1());
						pId2.setIntItemCtaCptoDet(expedienteComp.getIntIngCajaId2());
						CuentaConceptoDetalle ctaCptoDet = conceptoFacade.getCuentaConceptoDetallePorPK(pId2);
						ctaCptoDet.setBdSaldoDetalle(movimiento.getBdMontoSaldo());
						ctaCptoDet = conceptoFacade.modificarCuentaConceptoDetalle(ctaCptoDet);
						//Registrar Bloqueo Cuenta //solo lo genera Pago mes siguiente (por el momento)
						if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PAGOMESSGTE)) {
							BloqueoCuenta bloqueoCuenta = new BloqueoCuenta();
							bloqueoCuenta.setIntItemBloqueoCuenta(null);
							bloqueoCuenta.setIntPersEmpresaPk(expedienteComp.getIntIngCajaIdEmpresa());
							bloqueoCuenta.setIntCuentaPk(documentoGeneral.getSocioIngreso().getIntIngCajaIdCta());
							bloqueoCuenta.setIntItemCuentaConcepto(expedienteComp.getIntIngCajaId1());
							bloqueoCuenta.setIntItemExpediente(null);
							bloqueoCuenta.setIntItemExpedienteDetalle(null);
							bloqueoCuenta.setIntParaTipoBloqueoCod(Constante.PARAM_T_TIPOBLOQUEOCONCEPTO_PAGOPORCAJA);
							bloqueoCuenta.setTsFechaInicio(convertirDateToTimeStamp(getPrimerDiaDelMes(documentoGeneral.getIntPeriodoPlanilla())));
							bloqueoCuenta.setTsFechaFin(convertirDateToTimeStamp(getUltimoDiaDelMes(documentoGeneral.getIntPeriodoPlanilla())));
							//jchavez 20.07.2014
							if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PAGOMESSGTE)) bloqueoCuenta.setIntParaCodigoMotivoCod(Constante.PARAM_T_MOTIVOSOLICITUDCTACTE_PAGOMESSGTE);
							bloqueoCuenta.setStrObservacion("Bloqueado ingreso por caja");
							bloqueoCuenta.setTsFechaRegistro(new Timestamp(new Date().getTime()));
							bloqueoCuenta.setIntPersEmpresaUsuarioPk(usuario.getEmpresa().getIntIdEmpresa());
							bloqueoCuenta.setIntPersPersonaUsuarioPk(usuario.getPersona().getIntIdPersona());
							bloqueoCuenta.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							bloqueoCuenta.setIntParaTipoConceptoCre(expedienteComp.getIntIngCajaParaTipoX());
							bloqueoCuenta = conceptoFacade.grabarBloqueoCuenta(bloqueoCuenta);
							//Registrar Bloqueo Periodo
							BloqueoPeriodo bloqueoPeriodo = new BloqueoPeriodo();
							bloqueoPeriodo.setIntItemBloqueCuenta(bloqueoCuenta.getIntItemBloqueoCuenta());
							bloqueoPeriodo.setIntPeriodo(documentoGeneral.getIntPeriodoPlanilla());
							bloqueoPeriodo.setBdMontoCuota(expedienteComp.getBdIngCajaSumCapitalInteres());
							bloqueoPeriodo.setBdMontoPagado(expedienteComp.getBdIngCajaMontoPagado());
							bloqueoPeriodo = conceptoFacade.grabarBloqueoPeriodo(bloqueoPeriodo);
						}						
					}else if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_REGULARIZACION)) {
						if (expedienteComp.getLstCajaCuentaConceptoDetalle()!=null && !expedienteComp.getLstCajaCuentaConceptoDetalle().isEmpty()) {
							//Actualización de Cuenta Concepto Detalle
							for (CuentaConceptoDetalle o : expedienteComp.getLstCajaCuentaConceptoDetalle()) {
//								CuentaConceptoDetalleId pId2 = new CuentaConceptoDetalleId();
//								pId2.setIntPersEmpresaPk(o.getId().getIntPersEmpresaPk());
//								pId2.setIntCuentaPk(o.getId().getIntCuentaPk());
//								pId2.setIntItemCuentaConcepto(o.getId().getIntItemCuentaConcepto());
//								pId2.setIntItemCtaCptoDet(o.getId().getIntItemCtaCptoDet());
								if (o.getBdIngCajaMontoRegularizarCtaCptoDet()!=null) {
									CuentaConceptoDetalle ctaCptoDet = conceptoFacade.getCuentaConceptoDetallePorPK(o.getId());
									//jchavez 21.07.2014 - Monto ingresado para la regularización
									ctaCptoDet.setBdSaldoDetalle(ctaCptoDet.getBdSaldoDetalle().add(o.getBdIngCajaMontoRegularizarCtaCptoDet()));
									ctaCptoDet = conceptoFacade.modificarCuentaConceptoDetalle(ctaCptoDet);
								}								
							}
						}
					}
				}
				movimiento.setIntParaTipoMovimiento(Constante.PARAM_T_TIPO_MOVIMIENTO_INGRESO_POR_CAJA);
				movimiento.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
				movimiento.setStrSerieDocumento(reciboManual!=null?""+reciboManual.getIntSerieRecibo():null);
//				movimiento.setStrNumeroDocumento(reciboManual!=null?""+reciboManual.getReciboManualDetalleUltimo().getIntNumeroRecibo():null);
				movimiento.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
				
				movimiento.setIntPersEmpresaIngreso(ingreso.getId().getIntIdEmpresa());
				movimiento.setIntItemIngresoGeneral(ingreso.getId().getIntIdIngresoGeneral());
				movimiento.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa());
				movimiento.setIntPersPersonaUsuario(usuario.getPersona().getIntIdPersona());
				
				lstMovimientos.add(movimiento);
			}
			
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		
		return lstMovimientos;
	}
	
	private Integer	obtenerPeriodoActual(){
		String strPeriodo = "";
		Calendar cal = Calendar.getInstance();
		int año = cal.get(Calendar.YEAR);
		int mes = cal.get(Calendar.MONTH);
		mes = mes + 1; 
		if(mes<10){
			strPeriodo = año + "0" + mes;
		}else{
			strPeriodo  = año + "" + mes;
		}		
		return Integer.parseInt(strPeriodo);		
	}
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	private Modelo obtenerModeloSocio(Integer intIdEmpresa)throws Exception{
		ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
		List<Modelo> listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_INGRESOSOCIO_CAJA, intIdEmpresa);
		
		return listaModelo.get(0);
	}	
	
	private ReciboManualDetalle generarReciboManualDetalle(ReciboManual reciboManual, Ingreso ingreso, GestorCobranza gestorCobranza)throws Exception{
		ReciboManualDetalle reciboManualDetalle = new ReciboManualDetalle();
		reciboManualDetalle.getId().setIntPersEmpresa(reciboManual.getId().getIntPersEmpresa());
		reciboManualDetalle.getId().setIntItemReciboManual(reciboManual.getId().getIntItemReciboManual());
		reciboManualDetalle.setIntNumeroRecibo(reciboManual.getReciboManualDetalleUltimo().getIntNumeroRecibo());
		reciboManualDetalle.setIntPersEmpresaIngreso(ingreso.getId().getIntIdEmpresa());
		reciboManualDetalle.setIntItemIngresoGeneral(ingreso.getId().getIntIdIngresoGeneral());
		reciboManualDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);		
		reciboManualDetalle.setIntPersPersonaGestor(gestorCobranza.getId().getIntPersPersonaGestorPk());
		reciboManualDetalle.setIntPersonaRegistro(ingreso.getIntPersPersonaUsuario());

		return reciboManualDetalle;
	}
	
	public static Date getPrimerDiaDelMes(Integer intPeriodo) {
		Calendar cal = Calendar.getInstance();
		//seteo el mes y año del periodo solicitado
		cal.set(Calendar.YEAR, Integer.valueOf(intPeriodo.toString().substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.valueOf(intPeriodo.toString().substring(4, 6))-1);
		//
		cal.set(cal.get(Calendar.YEAR),
		cal.get(Calendar.MONTH),
		cal.getActualMinimum(Calendar.DAY_OF_MONTH),
		cal.getMinimum(Calendar.HOUR_OF_DAY),
		cal.getMinimum(Calendar.MINUTE),
		cal.getMinimum(Calendar.SECOND));
		return cal.getTime();

	}

	public static Date getUltimoDiaDelMes(Integer intPeriodo) {
		Calendar cal = Calendar.getInstance();
		//seteo el mes y año del periodo soliictado
		cal.set(Calendar.YEAR, Integer.valueOf(intPeriodo.toString().substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.valueOf(intPeriodo.toString().substring(4, 6))-1);
		//
		cal.set(cal.get(Calendar.YEAR),
		cal.get(Calendar.MONTH),
		cal.getActualMaximum(Calendar.DAY_OF_MONTH),
		cal.getMaximum(Calendar.HOUR_OF_DAY),
		cal.getMaximum(Calendar.MINUTE),
		cal.getMaximum(Calendar.SECOND));
		return cal.getTime();
	}
	
	public Archivo getArchivoPorIngreso(Ingreso ingreso)throws BusinessException{
		Archivo archivo = null;
		try{
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);			
			if(ingreso.getStrNumeroCheque()==null){
				return archivo;
			}
			
			ArchivoId archivoId = new ArchivoId();
			archivoId.setIntParaTipoCod(ingreso.getIntParaTipoIngreso());
			archivoId.setIntItemArchivo(ingreso.getIntItemArchivoIngreso());
			archivoId.setIntItemHistorico(ingreso.getIntHistoricoIngreso());
			archivo = generalFacade.getArchivoPorPK(archivoId);			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return archivo;
	}

}

/*
//for(EfectuadoResumen efectuadoResumen : efectResumen.getListaDetallePlanillaEfectuada()){
//CobroPlanillas cobroPlanillas = generarCobroPlanillas(ingreso, efectuadoResumen);
//log.info(cobroPlanillas);
//planillaFacade.grabarCobroPlanillas(cobroPlanillas);
//}

Boolean blnCancelado = true;
for(EfectuadoResumen efectuadoResumen : efectResumen.getListaDetallePlanillaEfectuada()){
//if(efectuadoResumen.getBdMontIngresar().equals(efectuadoResumen.getBdMontoDisponibelIngresar())){
//	efectuadoResumen.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
if (efectuadoResumen.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)) {
	planillaFacade.modificarEfectuadoResumen(efectuadoResumen);
}else blnCancelado = false;
log.info(efectuadoResumen);					
//}
}*/