package pe.com.tumi.contabilidad.cierre.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MayorizacionException;
import pe.com.tumi.contabilidad.cierre.bo.CuentaCierreBO;
import pe.com.tumi.contabilidad.cierre.bo.CuentaCierreDetalleBO;
import pe.com.tumi.contabilidad.cierre.bo.LibroDiarioDetalleBO;
import pe.com.tumi.contabilidad.cierre.bo.LibroMayorBO;
import pe.com.tumi.contabilidad.cierre.bo.LibroMayorDetalleBO;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierre;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierreDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalle;
import pe.com.tumi.contabilidad.cierre.facade.CierreFacadeLocal;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeLocal;
import pe.com.tumi.empresa.domain.SubSucursalPK;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;


public class CierreService {

	protected static Logger log = Logger.getLogger(CierreService.class);
	
	LibroMayorBO boLibroMayor = (LibroMayorBO)TumiFactory.get(LibroMayorBO.class);
	LibroDiarioDetalleBO boLibroDiarioDetalle = (LibroDiarioDetalleBO)TumiFactory.get(LibroDiarioDetalleBO.class);
	LibroMayorDetalleBO boLibroMayorDetalle = (LibroMayorDetalleBO)TumiFactory.get(LibroMayorDetalleBO.class);
	
	CuentaCierreBO boCuentaCierre = (CuentaCierreBO)TumiFactory.get(CuentaCierreBO.class);
	CuentaCierreDetalleBO boCuentaCierreDetalle = (CuentaCierreDetalleBO)TumiFactory.get(CuentaCierreDetalleBO.class);
	
	
	public List<LibroMayor> buscarLibroMayor(LibroMayor libroMayor) 
		throws BusinessException{
		List<LibroMayor> listaLibroMayor = null;
		try{
			listaLibroMayor = boLibroMayor.buscar(libroMayor);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaLibroMayor;
	}
	
	public LibroMayor mayorizar(LibroMayor libroMayor)throws BusinessException, MayorizacionException{
		try{
			PlanCuentaFacadeLocal planCuentaFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
			log.info(libroMayor);
			
			List<LibroMayorDetalle> listaLibroMayorDetalle = new ArrayList<LibroMayorDetalle>();
			
			List<PlanCuenta> listaPlanCuenta = planCuentaFacade.getListaPlanCuentaPorEmpresaCuentaYPeriodoCuenta
				(libroMayor.getId().getIntPersEmpresaMayor(), libroMayor.getId().getIntContPeriodoMayor());
			
			if(listaPlanCuenta == null || listaPlanCuenta.isEmpty()){
				throw new MayorizacionException("No existe un plan de cuenta para el periodo "+libroMayor.getId().getIntContPeriodoMayor());
			}
			
			List<LibroDiarioDetalle> listaLibroDiarioDetalle = new ArrayList<LibroDiarioDetalle>();
			List<PlanCuenta> listaPlanCuentaSinLibroDiarioDetalle = new ArrayList<PlanCuenta>();
			
			//Creamos los libroMayorDetalle de acuerdo a la cantidad de cuentas y oficinas que poseen
			//el conjunto de libroDiarioDetalle para un determinado periodo(mes-año)
			for(PlanCuenta planCuenta : listaPlanCuenta){
				listaLibroDiarioDetalle = boLibroDiarioDetalle.getListaPorLibroMayorYPlanCuenta(libroMayor, planCuenta);
				log.info("proc:"+planCuenta);
				
				//Si no existe ningun libroDiarioDetalle para la combinacion libroMayor + planCuenta, se guarda el planCuenta
				//para procesarlo despues
				if(listaLibroDiarioDetalle==null || listaLibroDiarioDetalle.isEmpty()){
					log.info("PlanCuentaSinLibroDiarioDetalle:"+planCuenta);
					listaPlanCuentaSinLibroDiarioDetalle.add(planCuenta);
				}
				
				HashSet<List<Integer>> hashsetOficinas = obtenerOficinas(listaLibroDiarioDetalle);
				for(List<Integer> oficina: hashsetOficinas){
					log.info("oficina:"+oficina.get(0)+" "+oficina.get(1)+" "+oficina.get(2));
					LibroMayorDetalle libroMayorDetalle = new LibroMayorDetalle();
					libroMayorDetalle.getId().setIntPersEmpresaMayor(libroMayor.getId().getIntPersEmpresaMayor());
					libroMayorDetalle.getId().setIntContPeriodoMayor(libroMayor.getId().getIntContPeriodoMayor());
					libroMayorDetalle.getId().setIntContMesMayor(libroMayor.getId().getIntContMesMayor());
					libroMayorDetalle.getId().setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
					libroMayorDetalle.getId().setIntContPeriodoCuenta(planCuenta.getId().getIntPeriodoCuenta());
					libroMayorDetalle.getId().setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());
					
					libroMayorDetalle.getId().setIntPersEmpresaSucursal(oficina.get(0));
					libroMayorDetalle.getId().setIntSucuIdSucursal(oficina.get(1));
					libroMayorDetalle.getId().setIntSudeIdSubSucursal(oficina.get(2));
					
					for(LibroDiarioDetalle libroDiarioDetalle : listaLibroDiarioDetalle){
						log.info(libroDiarioDetalle);
						if(libroDiarioDetallePerteneceOficina(libroDiarioDetalle,oficina)){
							libroMayorDetalle.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
							log.info("pertenece");
						}
					}
					
					listaLibroMayorDetalle.add(libroMayorDetalle);
				}
			}
			
			log.info("//");
			
			//Flujo Normal
			//Procesamos la lista de libroMayorDetalle, sumando los debe y haber de los libroDiarioDetalles asociados a cada libroMayorDetalle.
			//Esto resulta en el calculo de los debe y haber para el mes seleccionado
			BigDecimal sumaDebe = null;
			BigDecimal sumaHaber = null;
			BigDecimal sumaDebeExtranjero = null;
			BigDecimal sumaHaberExtranjero = null;
			LibroMayorDetalle libroMayorDetalleAnterior = null;
			
			for(LibroMayorDetalle libroMayorDetalle : listaLibroMayorDetalle){
				listaLibroDiarioDetalle = libroMayorDetalle.getListaLibroDiarioDetalle();
				sumaDebe = new BigDecimal(0);
				sumaHaber = new BigDecimal(0);
				sumaDebeExtranjero = new BigDecimal(0);
				sumaHaberExtranjero = new BigDecimal(0);
				//Inicia la suma de los libroDiarioDetalle
				for(LibroDiarioDetalle libroDiarioDetalle : listaLibroDiarioDetalle){
					log.info(libroDiarioDetalle);
					if(libroDiarioDetalle.getBdDebeSoles()!=null){
						sumaDebe = sumaDebe.add(libroDiarioDetalle.getBdDebeSoles());
					}
					if(libroDiarioDetalle.getBdHaberSoles()!=null){
						sumaHaber =  sumaHaber.add(libroDiarioDetalle.getBdHaberSoles());						
					}
					log.info("sumaDebe:"+sumaDebe+"  sumaHaber:"+sumaHaber);
					if(!libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
						if(libroDiarioDetalle.getBdDebeExtranjero()!=null)
							sumaDebeExtranjero = sumaDebeExtranjero.add(libroDiarioDetalle.getBdDebeExtranjero());						
						
						if(libroDiarioDetalle.getBdHaberExtranjero()!=null)
							sumaHaberExtranjero = sumaHaberExtranjero.add(libroDiarioDetalle.getBdHaberExtranjero());						
					}
				}
				libroMayorDetalle.setBdDebeSoles(sumaDebe);
				libroMayorDetalle.setBdHaberSoles(sumaHaber);
				libroMayorDetalle.setBdDebeExtranjero(sumaDebeExtranjero);
				libroMayorDetalle.setBdHaberExtranjero(sumaHaberExtranjero);
				//Buscamos los libroMayorDetalle (periodo-cuenta-oficina) anteriores para calculas los debe y haber acumulados
				if(libroMayorDetalle.getId().getIntContMesMayor().equals(Constante.PARAM_T_MES_ENERO)){
					libroMayorDetalle.setBdDebeSolesSaldo(sumaDebe);
					libroMayorDetalle.setBdHaberSolesSaldo(sumaHaber);
					libroMayorDetalle.setBdDebeExtranjeroSaldo(sumaDebeExtranjero);
					libroMayorDetalle.setBdHaberExtranjeroSaldo(sumaHaberExtranjero);
				}else{
					//se obtiene el libroMayorDetalle (periodo-cuenta-oficina) del mes anterior
					libroMayorDetalle.getId().setIntContMesMayor(libroMayorDetalle.getId().getIntContMesMayor()-1);
					libroMayorDetalleAnterior = boLibroMayorDetalle.getPorPk(libroMayorDetalle.getId());
					libroMayorDetalle.getId().setIntContMesMayor(libroMayorDetalle.getId().getIntContMesMayor()+1);
					log.info("libroMayorDetalleAnterior:"+libroMayorDetalleAnterior);
					
					//si no existe un libroMayorDetalleAnterior se arroja una exepcion
					if(libroMayorDetalleAnterior==null){
						log.error(libroMayorDetalle);						
						String mensaje = generarMensajerErrorMayorizacion(libroMayorDetalle);
						throw new MayorizacionException(mensaje);
					}
					//se calcula los montos acumulados en base al libroMayorDetalleAnterior
					libroMayorDetalle.setBdDebeSolesSaldo(libroMayorDetalleAnterior.getBdDebeSolesSaldo().add(sumaDebe));
					libroMayorDetalle.setBdHaberSolesSaldo(libroMayorDetalleAnterior.getBdHaberSolesSaldo().add(sumaHaber));
					libroMayorDetalle.setBdDebeExtranjeroSaldo(libroMayorDetalleAnterior.getBdDebeExtranjeroSaldo().add(sumaDebeExtranjero));
					libroMayorDetalle.setBdHaberExtranjeroSaldo(libroMayorDetalleAnterior.getBdHaberExtranjeroSaldo().add(sumaHaberExtranjero));
				}
				log.info(libroMayorDetalle);
				boLibroMayorDetalle.grabar(libroMayorDetalle);
			}
			
			
			//FLujo Alternativo
			//Procesamos los planCuenta que no poseen ningun libroDiarioDetalle
			for(PlanCuenta planCuenta : listaPlanCuentaSinLibroDiarioDetalle){
				log.info("PlanCuentaSinLibroDiarioDetalle:"+planCuenta);
				//obtenemos los libroMayorDetalle del mes anterior
				libroMayor.getId().setIntContMesMayor(libroMayor.getId().getIntContMesMayor()-1);
				listaLibroMayorDetalle = boLibroMayorDetalle.getPorLibroMayorYPlanCuenta(libroMayor, planCuenta);
				libroMayor.getId().setIntContMesMayor(libroMayor.getId().getIntContMesMayor()+1);
				if(listaLibroMayorDetalle == null || listaLibroMayorDetalle.isEmpty()){
					throw new MayorizacionException("No existe una mayorización previa para el periodo "
							+libroMayor.getId().getIntContMesMayor()+"-"+libroMayor.getId().getIntContPeriodoMayor()+
							" con la cuenta "+planCuenta.getId().getStrNumeroCuenta());
				}else{
					for(LibroMayorDetalle libroMayorDetAnterior : listaLibroMayorDetalle){
						libroMayorDetAnterior.getId().setIntContMesMayor(libroMayor.getId().getIntContMesMayor());
						libroMayorDetAnterior.setBdDebeSoles(new BigDecimal(0));
						libroMayorDetAnterior.setBdHaberSoles(new BigDecimal(0));
						libroMayorDetAnterior.setBdDebeExtranjero(new BigDecimal(0));
						libroMayorDetAnterior.setBdHaberExtranjero(new BigDecimal(0));
						log.info(libroMayorDetAnterior);
						boLibroMayorDetalle.grabar(libroMayorDetAnterior);
					}
				}
			}
			
		}catch(MayorizacionException e){
			throw e;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return libroMayor;
	}
	
	private String generarMensajerErrorMayorizacion(LibroMayorDetalle libroMayorDetalle) throws BusinessException, EJBFactoryException{
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
		Subsucursal subSucursal = new Subsucursal();
		Sucursal sucursal = new Sucursal();
		String mensaje = "";
		
		sucursal.setId(new SucursalId());
		sucursal.getId().setIntIdSucursal(libroMayorDetalle.getId().getIntPersEmpresaSucursal());
		sucursal.getId().setIntPersEmpresaPk(libroMayorDetalle.getId().getIntSucuIdSucursal());
		sucursal = empresaFacade.getSucursalPorPK(sucursal);
		
		mensaje = "No existe una mayorizacion previa para el " +
		"periodo "+libroMayorDetalle.getId().getIntContMesMayor()+"-"+libroMayorDetalle.getId().getIntContPeriodoMayor()
		+" con la cuenta "+libroMayorDetalle.getId().getStrContNumeroCuenta()+" para la oficina "+sucursal.getJuridica().getStrRazonSocial();
		
		subSucursal.setId(new SubSucursalPK());
		subSucursal.getId().setIntPersEmpresaPk(libroMayorDetalle.getId().getIntPersEmpresaSucursal());
		subSucursal.getId().setIntIdSucursal(libroMayorDetalle.getId().getIntSucuIdSucursal());
		subSucursal.getId().setIntIdSubSucursal(libroMayorDetalle.getId().getIntSudeIdSubSucursal());
		subSucursal = empresaFacade.getSubSucursalPorPk(subSucursal.getId());
		if(subSucursal != null){
			mensaje = mensaje +"-"+subSucursal.getStrDescripcion()+".";
		}else{
			mensaje = mensaje +".";
		}		
		
		log.error(mensaje);
		return mensaje;
	}
	
	private boolean libroDiarioDetallePerteneceOficina(LibroDiarioDetalle libroDiarioDetalle, List<Integer> oficina){		
		if(libroDiarioDetalle.getIntPersEmpresaSucursal().equals(oficina.get(0)) && 
			libroDiarioDetalle.getIntSucuIdSucursal().equals(oficina.get(1)) &&
			libroDiarioDetalle.getIntSudeIdSubSucursal().equals(oficina.get(2))){
			return true;
		}	
		return false;
	}
	
	
	private HashSet<List<Integer>> obtenerOficinas(List<LibroDiarioDetalle> listaLibroDiarioDetalle){
		
		HashSet<List<Integer>> hashsetOficinas = new HashSet<List<Integer>>();
		List<Integer> listaIdSucursal;
		for(LibroDiarioDetalle libroDiarioDetalle : listaLibroDiarioDetalle){
			//log.info(libroDiarioDetalle);
			listaIdSucursal = new ArrayList<Integer>();
			listaIdSucursal.add(libroDiarioDetalle.getIntPersEmpresaSucursal());
			listaIdSucursal.add(libroDiarioDetalle.getIntSucuIdSucursal());
			listaIdSucursal.add(libroDiarioDetalle.getIntSudeIdSubSucursal());
			hashsetOficinas.add(listaIdSucursal);
			log.info("*:"+libroDiarioDetalle.getIntPersEmpresaSucursal()+" "+libroDiarioDetalle.getIntSucuIdSucursal()+" "+libroDiarioDetalle.getIntSudeIdSubSucursal());			
		}
		return hashsetOficinas;
	}
	
	public boolean eliminarMayorizacion(LibroMayor libroMayor) throws BusinessException{
		boolean exito = Boolean.FALSE;
		try{
			//Se eliminan todos los LibroMayorDetalle relacionados al libroMayor
			int mesAEliminar = libroMayor.getId().getIntContMesMayor();
			List<LibroMayorDetalle> listaLibroMayorDetalles = boLibroMayorDetalle.getPorLibroMayor(libroMayor);
			for(LibroMayorDetalle libroMayorDetalle : listaLibroMayorDetalles){
				boLibroMayorDetalle.eliminar(libroMayorDetalle.getId());
			}
			
			//Buscamos todos los LibroMayor posteriores y eliminamos sus LibroMayorDetalle 
			log.info("mesAEliminar:"+mesAEliminar);
			for(int i = mesAEliminar+1;i<=12;i++){
				LibroMayor libroMayorAbrir = new LibroMayor();
				libroMayorAbrir.getId().setIntPersEmpresaMayor(libroMayor.getId().getIntPersEmpresaMayor());
				libroMayorAbrir.getId().setIntContPeriodoMayor(libroMayor.getId().getIntContPeriodoMayor());
				libroMayorAbrir.getId().setIntContMesMayor(i);
				//log.info(libroMayorAbrir);
				libroMayorAbrir = boLibroMayor.getPorPk(libroMayorAbrir.getId());
				if(libroMayorAbrir!=null){
					log.info("Existe:"+libroMayorAbrir);
					if(libroMayorAbrir.getIntParaEstadoCierreCod().equals(Constante.PARAM_T_TIPOESTADOCIERRE_CERRADO)){
						listaLibroMayorDetalles = boLibroMayorDetalle.getPorLibroMayor(libroMayorAbrir);
						for(LibroMayorDetalle libroMayorDetalle : listaLibroMayorDetalles){
							//log.info("a eliminar:"+libroMayorDetalle);
							boLibroMayorDetalle.eliminar(libroMayorDetalle.getId());
						}						
					}
				}else{
					log.info("NoExis:"+libroMayorAbrir);
				}
			}
			exito = Boolean.TRUE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return exito;		
	}
	
	public CuentaCierre grabarCuentaCierre(CuentaCierre cuentaCierre) throws BusinessException{
		try{
			List<CuentaCierreDetalle> listaCuentaCierreDetalle = cuentaCierre.getListaCuentaCierreDetalle();
			boCuentaCierre.grabar(cuentaCierre);
			
			for(CuentaCierreDetalle cuentaCierreDetalle : listaCuentaCierreDetalle){
				cuentaCierreDetalle.getId().setIntPersEmpresaCierre(cuentaCierre.getId().getIntPersEmpresaCierre());
				cuentaCierreDetalle.getId().setIntParaTipoCierre(cuentaCierre.getId().getIntParaTipoCierre());
				cuentaCierreDetalle.getId().setIntContPeriodoCierre(cuentaCierre.getId().getIntContPeriodoCierre());
				cuentaCierreDetalle.getId().setIntContCodigoCierre(cuentaCierre.getId().getIntContCodigoCierre());
				boCuentaCierreDetalle.grabar(cuentaCierreDetalle);
			}
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return cuentaCierre;
	}
	
	public List<CuentaCierre> getListaCuentaCierrePorBusqueda(CuentaCierre cuentaCierre) throws BusinessException{
		List<CuentaCierre> lista = null;
		try{
			lista = boCuentaCierre.getPorBusqueda(cuentaCierre);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<CuentaCierreDetalle> getListaCuentaCierreDetallePorCuentaCierre(CuentaCierre cuentaCierre) throws BusinessException{
		List<CuentaCierreDetalle> lista = null;
		try{
			lista = boCuentaCierreDetalle.getPorCuentaCierre(cuentaCierre);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public CuentaCierre modificarCuentaCierre(CuentaCierre cuentaCierre) throws BusinessException{
		try{
			List<CuentaCierreDetalle> listaCuentaCierreDetalle = cuentaCierre.getListaCuentaCierreDetalle();
			List<CuentaCierreDetalle> listaCuentaCierreDetalleBD = getListaCuentaCierreDetallePorCuentaCierre(cuentaCierre);
			CuentaCierreDetalle cuentaCierreDetalleBDPersiste = null;
			
			boCuentaCierre.modificar(cuentaCierre);
			boolean seEncuentraEnBD = Boolean.FALSE;
			for(CuentaCierreDetalle cuentaCierreDetalle : listaCuentaCierreDetalle){
				seEncuentraEnBD = Boolean.FALSE;
				for(CuentaCierreDetalle cuentaCierreDetalleBD : listaCuentaCierreDetalleBD){
					if( cuentaCierreDetalle.getIntPersEmpresaCuenta().equals(cuentaCierreDetalleBD.getIntPersEmpresaCuenta()) &&
						cuentaCierreDetalle.getIntContPeriodoCuenta().equals(cuentaCierreDetalleBD.getIntContPeriodoCuenta()) &&
						cuentaCierreDetalle.getStrContNumeroCuenta().equals(cuentaCierreDetalleBD.getStrContNumeroCuenta())){
						seEncuentraEnBD = Boolean.TRUE;
						cuentaCierreDetalleBDPersiste = cuentaCierreDetalleBD;
						break;
					}
				}
				if(!seEncuentraEnBD){
					cuentaCierreDetalle.getId().setIntPersEmpresaCierre(cuentaCierre.getId().getIntPersEmpresaCierre());
					cuentaCierreDetalle.getId().setIntParaTipoCierre(cuentaCierre.getId().getIntParaTipoCierre());
					cuentaCierreDetalle.getId().setIntContPeriodoCierre(cuentaCierre.getId().getIntContPeriodoCierre());
					cuentaCierreDetalle.getId().setIntContCodigoCierre(cuentaCierre.getId().getIntContCodigoCierre());
					log.info("nuevo:"+cuentaCierreDetalle);
					boCuentaCierreDetalle.grabar(cuentaCierreDetalle);
				}else{
					log.info("viejo:"+cuentaCierreDetalleBDPersiste);
					listaCuentaCierreDetalleBD.remove(cuentaCierreDetalleBDPersiste);
				}
			}
			for(CuentaCierreDetalle cuentaCierreDetalleNoSelec : listaCuentaCierreDetalleBD){
				log.info("eliminar:"+cuentaCierreDetalleNoSelec);
				boCuentaCierreDetalle.eliminar(cuentaCierreDetalleNoSelec);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return cuentaCierre;
	}
}