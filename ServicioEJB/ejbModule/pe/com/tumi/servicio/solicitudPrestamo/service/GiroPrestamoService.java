package pe.com.tumi.servicio.solicitudPrestamo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

//import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
//import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePagoId;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPagoId;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CronogramaId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.InteresCanceladoId;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CancelacionCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CronogramaCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.EstadoCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.ExpedienteCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CapacidadCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CronogramaCreditoComp;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeRemote;

public class GiroPrestamoService {
	
	protected static Logger log = Logger.getLogger(GiroPrestamoService.class);
	
	private ExpedienteCreditoBO boExpedienteCredito = (ExpedienteCreditoBO)TumiFactory.get(ExpedienteCreditoBO.class);
	private EstadoCreditoBO boEstadoCredito = (EstadoCreditoBO)TumiFactory.get(EstadoCreditoBO.class);
	private CronogramaCreditoBO boCronogramaCredito = (CronogramaCreditoBO)TumiFactory.get(CronogramaCreditoBO.class);
	private CancelacionCreditoBO boCancelacionCredito = (CancelacionCreditoBO)TumiFactory.get(CancelacionCreditoBO.class);
	
	
	public List<ExpedienteCredito> buscarExpedienteParaGiro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,
			EstadoCredito estadoCreditoFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException{
		
		List<ExpedienteCredito> listaExpedienteCredito = new ArrayList<ExpedienteCredito>();
		try{
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			List<ExpedienteCredito> listaTemp = new ArrayList<ExpedienteCredito>();
			List<Cuenta> listaCuenta = new ArrayList<Cuenta>();			
			List<CuentaIntegrante> listaCuentaIntegrante = new ArrayList<CuentaIntegrante>();
			
			Integer intIdEmpresa = estadoCreditoFiltro.getId().getIntPersEmpresaPk();
			Integer intParaTipoCreditoFiltro = intTipoCreditoFiltro;
			Integer intParaEstadoCreditoFiltro = estadoCreditoFiltro.getIntParaEstadoCreditoCod();
			
			
			if(listaPersonaFiltro != null && !listaPersonaFiltro.isEmpty()){
				for(Persona persona : listaPersonaFiltro){
					listaCuentaIntegrante = cuentaFacade.getCuentaIntegrantePorIdPersona(persona.getIntIdPersona(), intIdEmpresa);
					if(listaCuentaIntegrante == null)	listaCuentaIntegrante = new ArrayList<CuentaIntegrante>();
				}
			}else{
				listaCuentaIntegrante = cuentaFacade.getCuentaIntegrantePorIdPersona(null, intIdEmpresa);
			}
			
			HashSet<Integer> hashSetIntCuenta = new HashSet<Integer>();
			for(CuentaIntegrante cuentaIntegrante : listaCuentaIntegrante){
				//log.info("CI per:"+cuentaIntegrante.getId().getIntPersonaIntegrante());
				hashSetIntCuenta.add(cuentaIntegrante.getId().getIntCuenta());
			}
			
			for(Integer intCuenta : hashSetIntCuenta){
				//log.info(intCuenta);
				CuentaId cuentaId = new CuentaId();
				cuentaId.setIntPersEmpresaPk(intIdEmpresa);
				cuentaId.setIntCuenta(intCuenta);
				Cuenta cuenta = new Cuenta();
				cuenta.setId(cuentaId);
				cuenta = cuentaFacade.getCuentaPorIdCuenta(cuenta);
				if(cuenta!=null && cuenta.getIntParaTipoCuentaCod().equals(Constante.PARAM_T_TIPOCUENTAREQUISITOS_SOCIO)){
					//log.info("size:"+cuenta.getListaIntegrante().size());
					listaCuenta.add(cuenta);
				}				
			}
			
			
			log.info("intParaTipoCreditoFiltro:"+intParaTipoCreditoFiltro);
			for(Cuenta cuenta : listaCuenta){
				//log.info(cuenta.getStrNumeroCuenta());				
				//log.info("size:"+cuenta.getListaIntegrante().size());
				List<ExpedienteCredito> listaExpedienteCreditoTemp = boExpedienteCredito.getListaPorCuenta(cuenta);
				for(ExpedienteCredito expedienteCreditoTemp : listaExpedienteCreditoTemp){
					boolean pasaFiltroItem = Boolean.FALSE;
					//log.info(expedienteCreditoTemp);
					expedienteCreditoTemp.setCuenta(cuenta);
					if(intItemExpedienteFiltro!=null && expedienteCreditoTemp.getId().getIntItemExpediente().equals(intItemExpedienteFiltro)){
						pasaFiltroItem = Boolean.TRUE;
					}else if(intItemExpedienteFiltro==null){
						pasaFiltroItem = Boolean.TRUE;
					}
					
					if(pasaFiltroItem){
						if(intParaTipoCreditoFiltro!=null && expedienteCreditoTemp.getIntParaTipoCreditoCod().equals(intParaTipoCreditoFiltro)){
							listaExpedienteCredito.add(expedienteCreditoTemp);
						}else if(intParaTipoCreditoFiltro==null){
							listaExpedienteCredito.add(expedienteCreditoTemp);
						}
					}
				}
			}
			
			
			for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
				//log.info(expedienteCredito);
				//solo obtenemos los docs tipo prestamos
				if(expedienteCredito.getIntParaDocumentoGeneralCod()==null 
				||!expedienteCredito.getIntParaDocumentoGeneralCod().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
					continue;
				}
				
				EstadoCredito estadoCreditoUltimo = obtenerUltimoEstadoCredito(expedienteCredito);
				//log.info("estadoCreditoUltimo:"+estadoCreditoUltimo);
				
				boolean pasaFiltroEstado = Boolean.FALSE;
				//Si se ha seleccionado un intParaEstadoCreditoFiltro en la busqueda
				if(intParaEstadoCreditoFiltro!=null && estadoCreditoUltimo.getIntParaEstadoCreditoCod().equals(intParaEstadoCreditoFiltro)){
					pasaFiltroEstado = Boolean.TRUE;
					
				//si no se a seleccionado un intParaEstadoCreditoFiltro en la busqueda, solo podemos traer registros en estado
				//aprobado o girado
				}else if(intParaEstadoCreditoFiltro==null){
					if(estadoCreditoUltimo.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO) 
					|| estadoCreditoUltimo.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)){
						pasaFiltroEstado = Boolean.TRUE;
					}					
				}
				
				if(pasaFiltroEstado){
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntIdSucursal(estadoCreditoUltimo.getIntIdUsuSucursalPk());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);
					//log.info(sucursal);
					estadoCreditoUltimo.setSucursal(sucursal);
					estadoCreditoUltimo.setSubsucursal(empresaFacade.getSubSucursalPorIdSubSucursal(estadoCreditoUltimo.getIntIdUsuSubSucursalPk()));
					expedienteCredito.setEstadoCreditoUltimo(estadoCreditoUltimo);

					//Necesitamos agregar para la IU el estado en el que se aprobo el expediente
					EstadoCredito estadoCreditoAprobado = null;
					if(estadoCreditoUltimo.equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
						estadoCreditoAprobado = estadoCreditoUltimo;
					}else{
						estadoCreditoAprobado = obtenerUltimoEstadoCredito(expedienteCredito, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
					}
					expedienteCredito.setEstadoCreditoAprobado(estadoCreditoAprobado);

					//jchavez 25.06.2014 buscamos si el expediente tiene un cancelacion credito
					
					List<CancelacionCredito> lstCancelacionCredito = boCancelacionCredito.getListaPorExpedienteCredito(expedienteCredito);
					if (lstCancelacionCredito!=null && !lstCancelacionCredito.isEmpty()) {
						expedienteCredito.setListaCancelacionCredito(new ArrayList<CancelacionCredito>());
						for (CancelacionCredito cancelacionCredito : lstCancelacionCredito) {
							expedienteCredito.getListaCancelacionCredito().add(cancelacionCredito);
						}
					}
					listaTemp.add(expedienteCredito);
				}
			}
			
			listaExpedienteCredito = listaTemp;
			
			/*//Este paso se realiza en el controller
			//Se añade persona administra la cuenta del expediente
			for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
				log.info(expedienteCredito);
				for(CuentaIntegrante cuentaIntegrante : expedienteCredito.getCuenta().getListaIntegrante()){
					log.info(cuentaIntegrante.getIntParaTipoIntegranteCod());
					//Parametro integrante administra = 1
					if(cuentaIntegrante.getIntParaTipoIntegranteCod().equals(new Integer(1))){
						Persona persona = personaFacade.getPersonaPorPK(cuentaIntegrante.getId().getIntPersonaIntegrante());
						log.info(persona);
						expedienteCredito.setPersonaAdministra(persona);
					}
				}
			}*/
			
			if(intTipoBusquedaSucursal != null && intIdSucursalFiltro != null){
				log.info("--busqueda sucursal");
				listaExpedienteCredito = manejarBusquedaSucursal(intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro, 
						intIdEmpresa, listaExpedienteCredito);
			}
			
			if(estadoCreditoFiltro.getDtFechaEstadoDesde()!=null || estadoCreditoFiltro.getDtFechaEstadoHasta()!=null){
				log.info("--busqueda fechas");
				listaExpedienteCredito = manejarFiltroFechas(listaExpedienteCredito, estadoCreditoFiltro);
			}
			
			
		}catch(BusinessException e){
			log.error(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return listaExpedienteCredito;
	}
	
	private List<ExpedienteCredito> manejarFiltroFechas(List<ExpedienteCredito> listaExpedienteCredito, EstadoCredito estado)throws Exception{		
		if(estado.getDtFechaEstadoDesde()!=null){
			List<ExpedienteCredito> listaTemp = new ArrayList<ExpedienteCredito>();
			for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){				
				if(expedienteCredito.getEstadoCreditoUltimo().getTsFechaEstado().compareTo(estado.getDtFechaEstadoDesde())>=0){
					listaTemp.add(expedienteCredito);
				}
			}
			listaExpedienteCredito = listaTemp;
		}		
		if(estado.getDtFechaEstadoHasta()!=null){
			List<ExpedienteCredito> listaTemp = new ArrayList<ExpedienteCredito>();
			for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){				
				if(expedienteCredito.getEstadoCreditoUltimo().getTsFechaEstado().compareTo(estado.getDtFechaEstadoHasta())<=0){
					listaTemp.add(expedienteCredito);
				}
			}
			listaExpedienteCredito = listaTemp;
		}
		return listaExpedienteCredito;
	}
	
	private List<ExpedienteCredito> manejarBusquedaSucursal(Integer intTipoBusquedaSucursal, Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro, 
			Integer intIdEmpresa, List<ExpedienteCredito> listaExpedienteCredito) throws Exception{
		
		SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
		List<ExpedienteCredito> listaTemp = new ArrayList<ExpedienteCredito>();
		
		if(intTipoBusquedaSucursal.equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_SOCIO) && intIdSucursalFiltro!=null){
			for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
				for(CuentaIntegrante cuentaIntegrante : expedienteCredito.getCuenta().getListaIntegrante()){
					SocioEstructura socioEstructura = (socioFacade.getListaSocioEstrucuraPorIdPersona(
							cuentaIntegrante.getIntPersonaUsuario(), intIdEmpresa)).get(0);
					if(intIdSucursalFiltro.intValue()>0){
						if(socioEstructura.getIntIdSucursalAdministra().equals(intIdSucursalFiltro)){
							if(intIdSubsucursalFiltro!=null && intIdSubsucursalFiltro.equals(socioEstructura.getIntIdSubsucurAdministra())){
								listaTemp.add(expedienteCredito);
							}else if(intIdSubsucursalFiltro==null){
								listaTemp.add(expedienteCredito);
							}
						}
					}else{
						Integer intTotalSucursal = intIdSucursalFiltro;
						Sucursal sucursal = new Sucursal();
						sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
						sucursal.getId().setIntIdSucursal(socioEstructura.getIntIdSucursalAdministra());
						sucursal = empresaFacade.getSucursalPorPK(sucursal);						
						if(validarTotalSucursal(sucursal.getIntIdTipoSucursal(), intTotalSucursal)){
							listaTemp.add(expedienteCredito);
						}
					}
				}
			}
		
		}else if(intTipoBusquedaSucursal.equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_USUARIO) && intIdSucursalFiltro!=null){
			for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
				EstadoCredito estadoCreditoUltimo = expedienteCredito.getEstadoCreditoUltimo();
				if(intIdSucursalFiltro.intValue()>0){
					if(estadoCreditoUltimo.getIntIdUsuSucursalPk().equals(intIdSucursalFiltro)){
						if(intIdSubsucursalFiltro!=null && intIdSubsucursalFiltro.equals(estadoCreditoUltimo.getIntIdUsuSubSucursalPk())){
							listaTemp.add(expedienteCredito);
						}else if(intIdSubsucursalFiltro==null){
							listaTemp.add(expedienteCredito);
						}
					}
				}else{
					Integer intTotalSucursal = intIdSucursalFiltro;
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
					sucursal.getId().setIntIdSucursal(estadoCreditoUltimo.getIntIdUsuSucursalPk());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);						
					if(validarTotalSucursal(sucursal.getIntIdTipoSucursal(), intTotalSucursal)){
						listaTemp.add(expedienteCredito);
					}
				}
			}
		}
		
		return listaTemp;
	}
	
	private boolean validarTotalSucursal(Integer intTipoSucursal, Integer intTotalSucursal){
		boolean exito = Boolean.FALSE;
		
		if(intTipoSucursal.equals(Constante.PARAM_T_TIPOSUCURSAL_AGENCIA)
		&& intTotalSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES_AGENCIAS)){
			exito = Boolean.TRUE;
		
		}else if(intTipoSucursal.equals(Constante.PARAM_T_TIPOSUCURSAL_FILIAL)
			&& intTotalSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES_FILIALES)){
			exito = Boolean.TRUE;
		
		}else if(intTipoSucursal.equals(Constante.PARAM_T_TIPOSUCURSAL_SEDECENTRAL)
			&& intTotalSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES_SEDE)){
			exito = Boolean.TRUE;
		
		}else if(intTipoSucursal.equals(Constante.PARAM_T_TIPOSUCURSAL_OFICINAPRINCIPAL)
			&& intTotalSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES_OFICINAPRINCIPAL)){
			exito = Boolean.TRUE;
		
		}else if(intTotalSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES_SUCURSALES)){
			exito = Boolean.TRUE;
		}
		
		return exito;
	}
	
	private EstadoCredito obtenerUltimoEstadoCredito(ExpedienteCredito expedienteCredito)throws BusinessException{
		EstadoCredito estadoCreditoUltima = new EstadoCredito();
		try{
			List<EstadoCredito> listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
			
			estadoCreditoUltima.setId(new EstadoCreditoId());
			estadoCreditoUltima.getId().setIntItemEstado(0);
			for(EstadoCredito estadoCredito : listaEstadoCredito){
				if(estadoCredito.getId().getIntItemEstado().compareTo(estadoCreditoUltima.getId().getIntItemEstado())>0){
					estadoCreditoUltima = estadoCredito;
				}
			}
			
			expedienteCredito.setListaEstadoCredito(listaEstadoCredito);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estadoCreditoUltima;
	}
	
	public EstadoCredito obtenerUltimoEstadoCredito(ExpedienteCredito expedienteCredito, Integer intTipoEstado)throws BusinessException{
		EstadoCredito estadoCreditoUltima = new EstadoCredito();
		try{
			List<EstadoCredito> listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
			
			estadoCreditoUltima.setId(new EstadoCreditoId());
			estadoCreditoUltima.getId().setIntItemEstado(0);
			for(EstadoCredito estadoCredito : listaEstadoCredito){
				if(estadoCredito.getIntParaEstadoCreditoCod().equals(intTipoEstado)
				&& estadoCredito.getId().getIntItemEstado().compareTo(estadoCreditoUltima.getId().getIntItemEstado())>0){
					estadoCreditoUltima = estadoCredito;
				}
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estadoCreditoUltima;
	}
	
	
	private EstadoCredito generarEstadoCredito(ExpedienteCredito expedienteCredito, Egreso egreso) throws Exception {
		EstadoCredito estadoCredito = new EstadoCredito();
		estadoCredito.getId().setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
		estadoCredito.getId().setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
		estadoCredito.getId().setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
		estadoCredito.getId().setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
		estadoCredito.getId().setIntItemEstado(null);
		estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO);
		estadoCredito.setTsFechaEstado(expedienteCredito.getEgreso().getTsFechaProceso());
		estadoCredito.setIntPersEmpresaEstadoCod(expedienteCredito.getId().getIntPersEmpresaPk());
		estadoCredito.setIntIdUsuSucursalPk(expedienteCredito.getEgreso().getIntSucuIdSucursal());
		estadoCredito.setIntIdUsuSubSucursalPk(expedienteCredito.getEgreso().getIntSudeIdSubsucursal());
		estadoCredito.setIntPersUsuarioEstadoPk(expedienteCredito.getEgreso().getIntPersPersonaUsuario());
		//Llave del Libro Diario extorno
		estadoCredito.setIntPersEmpresaLibro(egreso.getIntPersEmpresaLibroExtorno());
		estadoCredito.setIntPeriodoLibro(egreso.getIntContPeriodoLibroExtorno());
		estadoCredito.setIntCodigoLibro(egreso.getIntContCodigoLibroExtorno());
		
		return estadoCredito;
	}
	
	private Expediente generarExpedienteMovimiento(ExpedienteCredito expedienteCredito,List<CronogramaCredito> listaCronogramaServicio) throws Exception {
		log.info("---- GiroPrestamoService.generarExpedienteMovimiento ----");
		BigDecimal bdSaldoInteres = new BigDecimal(0);
		// Recorre el cronograma de servicio y se realiza sumatoria del monto concepto que sean INTERES
		if (listaCronogramaServicio!=null && !listaCronogramaServicio.isEmpty()) {
			for(CronogramaCredito cronogramaCredito : listaCronogramaServicio){
				if(cronogramaCredito.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
				&& cronogramaCredito.getIntParaTipoConceptoCod().equals(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_INTERES) ){
					bdSaldoInteres = bdSaldoInteres.add(cronogramaCredito.getBdMontoConcepto());
				}
			}
		}		
		
		Expediente expedienteMovimiento = new Expediente();
		expedienteMovimiento.setId(new ExpedienteId());
		expedienteMovimiento.getId().setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
		expedienteMovimiento.getId().setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
		expedienteMovimiento.getId().setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
		expedienteMovimiento.getId().setIntItemExpedienteDetalle(expedienteCredito.getId().getIntItemDetExpediente());
		expedienteMovimiento.setIntPersEmpresaCreditoPk(expedienteCredito.getIntPersEmpresaCreditoPk());
		expedienteMovimiento.setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod());
		expedienteMovimiento.setIntItemCredito(expedienteCredito.getIntItemCredito());
		expedienteMovimiento.setBdPorcentajeInteres(expedienteCredito.getBdPorcentajeInteres());
		expedienteMovimiento.setBdPorcentajeGravamen(expedienteCredito.getBdPorcentajeGravamen());
		expedienteMovimiento.setBdPorcentajeMora(expedienteCredito.getCuenta().getCredito().getBdTasaMoratoriaMensual());
		expedienteMovimiento.setBdPorcentajeAporte(expedienteCredito.getBdPorcentajeAporte());
		expedienteMovimiento.setBdMontoInteresAtrazado(expedienteCredito.getBdMontoInteresAtrasado());
		expedienteMovimiento.setBdMontoMoraAtrazado(expedienteCredito.getBdMontoMoraAtrasada());
		expedienteMovimiento.setBdMontoSolicitado(expedienteCredito.getBdMontoSolicitado());
		expedienteMovimiento.setBdMontoTotal(expedienteCredito.getBdMontoTotal());
		expedienteMovimiento.setIntNumeroCuota(expedienteCredito.getIntNumeroCuota());
		expedienteMovimiento.setBdSaldoCredito(expedienteCredito.getBdMontoTotal());
		expedienteMovimiento.setBdSaldoInteres(bdSaldoInteres);
		expedienteMovimiento.setBdSaldoMora(null);
		
		// 18.09.2013 - CGD
		expedienteMovimiento.setIntPersEmpresaSucAdministra(expedienteCredito.getIntPersEmpresaSucAdministra());
		expedienteMovimiento.setIntSucuIdSucursalAdministra(expedienteCredito.getIntSucuIdSucursalAdministra());
		expedienteMovimiento.setIntSudeIdSubSucursalAdministra(expedienteCredito.getIntSudeIdSubSucursalAdministra());

		/*EstadoConceptoExpediente estadoConceptoExpediente = new EstadoConceptoExpediente();
		estadoConceptoExpediente.getId().setIntPersEmpresa(expedienteCredito.getId().getIntPersEmpresaPk());
		estadoConceptoExpediente.setIntPersEmpresa(expedienteCredito.getId().getIntPersEmpresaPk());
		estadoConceptoExpediente.setIntCuenta(expedienteCredito.getCuenta().getId().getIntCuenta());
		estadoConceptoExpediente.setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
		estadoConceptoExpediente.setIntItemExpedienteDetalle(expedienteCredito.getId().getIntItemDetExpediente());
		estadoConceptoExpediente.setIntParaEstadoConceptoExpediente(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		expedienteMovimiento.setEstadoExpedienteConcepto(estadoConceptoExpediente);*/
		
		return expedienteMovimiento;
	}
	
	private Movimiento generarMovimiento(ExpedienteCredito expediente, Egreso egreso) throws Exception{
		//JCHAVEZ 07.01.2014
		log.info("---- GiroPrestamoService.generarMovimiento ----");
		Movimiento movimiento = new Movimiento();
		movimiento.setTsFechaMovimiento(expediente.getEgreso().getTsFechaProceso());
		movimiento.setIntPersEmpresa(expediente.getId().getIntPersEmpresaPk());
		movimiento.setIntCuenta(expediente.getCuenta().getId().getIntCuenta());
		movimiento.setIntPersPersonaIntegrante(expediente.getEgreso().getIntPersPersonaGirado());
		movimiento.setIntItemCuentaConcepto(null);
		movimiento.setIntItemExpediente(expediente.getId().getIntItemExpediente());
		movimiento.setIntItemExpedienteDetalle(expediente.getId().getIntItemDetExpediente());
		movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_CONCEPTOGENERAL_AMORTIZACION_EXPEDIENTE);
		movimiento.setIntParaTipoMovimiento(Constante.PARAM_T_MOVIMIENTOCTACTE_EGRESOCAJA);
		//Se graba el mismo que se grabo en el Egreso
		movimiento.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
		movimiento.setStrSerieDocumento(null);
		movimiento.setStrNumeroDocumento(""+expediente.getId().getIntItemExpediente()+"-"+expediente.getId().getIntItemDetExpediente());
		movimiento.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_CARGO);
		movimiento.setBdMontoMovimiento(expediente.getBdMontoTotal());
		movimiento.setBdMontoSaldo(expediente.getBdMontoTotal());
		movimiento.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
		movimiento.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
		movimiento.setIntPersEmpresaUsuario(expediente.getEgreso().getIntPersEmpresaUsuario());
		movimiento.setIntPersPersonaUsuario(expediente.getEgreso().getIntPersPersonaUsuario());
		
		return movimiento;
	}
	
	private Integer	obtenerPeriodoActual() throws Exception{
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
	
	
	private LibroDiario generarLibroDiarioExtorno(ExpedienteCredito expedienteCredito) throws Exception{
		log.info("---- GiroPrestamoService.generarLibroDiarioExtorno ----");
//		ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
		LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
		
		Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
		Integer intIdUsuario = expedienteCredito.getEgreso().getIntPersPersonaUsuario();
		LibroDiario libroDiario = null;
		try {
			//Recupero el estado credito aprobado
			EstadoCredito estadoCreditoSolicitudUltimo = obtenerUltimoEstadoCredito(expedienteCredito, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
			log.info("Ultimo estado credito registrado con estado aprobado ---> "+estadoCreditoSolicitudUltimo);
			LibroDiarioId libroDiarioId = new LibroDiarioId();
			libroDiarioId.setIntPersEmpresaLibro(estadoCreditoSolicitudUltimo.getIntPersEmpresaLibro());
			libroDiarioId.setIntContPeriodoLibro(estadoCreditoSolicitudUltimo.getIntPeriodoLibro());
			libroDiarioId.setIntContCodigoLibro(estadoCreditoSolicitudUltimo.getIntCodigoLibro());
			//Recupero el Libro Diario del estado credito aprobado
			LibroDiario libroDiarioSolicitud = libroDiarioFacade.getLibroDiarioPorPk(libroDiarioId);
			log.info(libroDiarioSolicitud);
			//Recupero el Libro Diario Detalle del estado credito aprobado
			List<LibroDiarioDetalle> listaLibroDiarioDetalleSolicitud = libroDiarioFacade.getListaLibroDiarioDetallePorLibroDiario(libroDiarioSolicitud);
			//Se generan los nuevos registros del libro diario y libro diario detalle
			LibroDiarioDetalle libroDiarioDetalleSolicitudDebe = new LibroDiarioDetalle();
			LibroDiarioDetalle libroDiarioDetalleSolicitudHaber = new LibroDiarioDetalle();
			for(LibroDiarioDetalle libroDiarioDetalleSolicitud : listaLibroDiarioDetalleSolicitud){
				if(libroDiarioDetalleSolicitud.getBdDebeSoles()!=null || libroDiarioDetalleSolicitud.getBdDebeExtranjero()!=null){
//					libroDiarioDetalleSolicitudHaber = new LibroDiarioDetalle();
					libroDiarioDetalleSolicitudHaber.setId(new LibroDiarioDetalleId());
					//Seteamos valores ID
					libroDiarioDetalleSolicitudHaber.getId().setIntPersEmpresaLibro(libroDiarioDetalleSolicitud.getId().getIntPersEmpresaLibro());
					libroDiarioDetalleSolicitudHaber.getId().setIntContPeriodoLibro(libroDiarioDetalleSolicitud.getId().getIntContPeriodoLibro());
					libroDiarioDetalleSolicitudHaber.getId().setIntContCodigoLibro(libroDiarioDetalleSolicitud.getId().getIntContCodigoLibro());
					libroDiarioDetalleSolicitudHaber.getId().setIntContItemLibro(libroDiarioDetalleSolicitud.getId().getIntContItemLibro());
					//Seteamos valorescuerpo
					libroDiarioDetalleSolicitudHaber.setIntPersEmpresaCuenta(libroDiarioDetalleSolicitud.getIntPersEmpresaCuenta());
					libroDiarioDetalleSolicitudHaber.setIntContPeriodo(libroDiarioDetalleSolicitud.getIntContPeriodo());
					libroDiarioDetalleSolicitudHaber.setStrContNumeroCuenta(libroDiarioDetalleSolicitud.getStrContNumeroCuenta());
					libroDiarioDetalleSolicitudHaber.setIntPersPersona(libroDiarioDetalleSolicitud.getIntPersPersona());
					libroDiarioDetalleSolicitudHaber.setIntParaDocumentoGeneral(libroDiarioDetalleSolicitud.getIntParaDocumentoGeneral());
					libroDiarioDetalleSolicitudHaber.setStrSerieDocumento(libroDiarioDetalleSolicitud.getStrSerieDocumento());
					libroDiarioDetalleSolicitudHaber.setStrNumeroDocumento(libroDiarioDetalleSolicitud.getStrNumeroDocumento());
					libroDiarioDetalleSolicitudHaber.setIntPersEmpresaSucursal(libroDiarioDetalleSolicitud.getIntPersEmpresaSucursal());
					libroDiarioDetalleSolicitudHaber.setIntSucuIdSucursal(libroDiarioDetalleSolicitud.getIntSucuIdSucursal());
					libroDiarioDetalleSolicitudHaber.setIntSudeIdSubSucursal(libroDiarioDetalleSolicitud.getIntSudeIdSubSucursal());
					libroDiarioDetalleSolicitudHaber.setIntParaMonedaDocumento(libroDiarioDetalleSolicitud.getIntParaMonedaDocumento());
					libroDiarioDetalleSolicitudHaber.setIntTipoCambio(libroDiarioDetalleSolicitud.getIntTipoCambio());
					libroDiarioDetalleSolicitudHaber.setBdDebeSoles(null);
					libroDiarioDetalleSolicitudHaber.setBdHaberSoles(libroDiarioDetalleSolicitud.getBdDebeSoles());
					libroDiarioDetalleSolicitudHaber.setBdDebeExtranjero(null);
					libroDiarioDetalleSolicitudHaber.setBdHaberExtranjero(libroDiarioDetalleSolicitud.getBdDebeExtranjero());
					libroDiarioDetalleSolicitudHaber.setStrComentario(libroDiarioDetalleSolicitud.getStrComentario());

				}
				if(libroDiarioDetalleSolicitud.getBdHaberSoles()!=null || libroDiarioDetalleSolicitud.getBdHaberExtranjero()!=null){
					libroDiarioDetalleSolicitudDebe.setId(new LibroDiarioDetalleId());
					//Seteamos valores ID
					libroDiarioDetalleSolicitudDebe.getId().setIntPersEmpresaLibro(libroDiarioDetalleSolicitud.getId().getIntPersEmpresaLibro());
					libroDiarioDetalleSolicitudDebe.getId().setIntContPeriodoLibro(libroDiarioDetalleSolicitud.getId().getIntContPeriodoLibro());
					libroDiarioDetalleSolicitudDebe.getId().setIntContCodigoLibro(libroDiarioDetalleSolicitud.getId().getIntContCodigoLibro());
					libroDiarioDetalleSolicitudDebe.getId().setIntContItemLibro(libroDiarioDetalleSolicitud.getId().getIntContItemLibro());
					//Seteamos valorescuerpo
					libroDiarioDetalleSolicitudDebe.setIntPersEmpresaCuenta(libroDiarioDetalleSolicitud.getIntPersEmpresaCuenta());
					libroDiarioDetalleSolicitudDebe.setIntContPeriodo(libroDiarioDetalleSolicitud.getIntContPeriodo());
					libroDiarioDetalleSolicitudDebe.setStrContNumeroCuenta(libroDiarioDetalleSolicitud.getStrContNumeroCuenta());
					libroDiarioDetalleSolicitudDebe.setIntPersPersona(libroDiarioDetalleSolicitud.getIntPersPersona());
					libroDiarioDetalleSolicitudDebe.setIntParaDocumentoGeneral(libroDiarioDetalleSolicitud.getIntParaDocumentoGeneral());
					libroDiarioDetalleSolicitudDebe.setStrSerieDocumento(libroDiarioDetalleSolicitud.getStrSerieDocumento());
					libroDiarioDetalleSolicitudDebe.setStrNumeroDocumento(libroDiarioDetalleSolicitud.getStrNumeroDocumento());
					libroDiarioDetalleSolicitudDebe.setIntPersEmpresaSucursal(libroDiarioDetalleSolicitud.getIntPersEmpresaSucursal());
					libroDiarioDetalleSolicitudDebe.setIntSucuIdSucursal(libroDiarioDetalleSolicitud.getIntSucuIdSucursal());
					libroDiarioDetalleSolicitudDebe.setIntSudeIdSubSucursal(libroDiarioDetalleSolicitud.getIntSudeIdSubSucursal());
					libroDiarioDetalleSolicitudDebe.setIntParaMonedaDocumento(libroDiarioDetalleSolicitud.getIntParaMonedaDocumento());
					libroDiarioDetalleSolicitudDebe.setIntTipoCambio(libroDiarioDetalleSolicitud.getIntTipoCambio());
					libroDiarioDetalleSolicitudDebe.setBdDebeSoles(libroDiarioDetalleSolicitud.getBdHaberSoles());
					libroDiarioDetalleSolicitudDebe.setBdHaberSoles(null);
					libroDiarioDetalleSolicitudDebe.setBdDebeExtranjero(libroDiarioDetalleSolicitud.getBdHaberExtranjero());
					libroDiarioDetalleSolicitudDebe.setBdHaberExtranjero(null);
					libroDiarioDetalleSolicitudDebe.setStrComentario(libroDiarioDetalleSolicitud.getStrComentario());
				}
			}
			
			log.info("Solicitud DEBE: "+libroDiarioDetalleSolicitudDebe);
			log.info("Solicitud HABER: "+libroDiarioDetalleSolicitudHaber);
			
//			Modelo modeloExtorno = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_EXTORNO, intIdEmpresa).get(0);
//			ModeloDetalle modeloDetalleExtornoDebe = null;
//			ModeloDetalle modeloDetalleExtornoHaber = null;
//			
//			for(ModeloDetalle modeloDetalle : modeloExtorno.getListModeloDetalle()){
//				if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
//					modeloDetalleExtornoDebe = new ModeloDetalle();
//					modeloDetalleExtornoDebe = modeloDetalle;
//				}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
//					modeloDetalleExtornoHaber = new ModeloDetalle();
//					modeloDetalleExtornoHaber = modeloDetalle;
//				}
//			}
			
			libroDiario = new LibroDiario();
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiario.setStrGlosa("Asiento de extorno - "+libroDiarioSolicitud.getStrGlosa());
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(intIdUsuario);
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
//			
//			LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
//			libroDiarioDetalleHaber.setId(new LibroDiarioDetalleId());
//			libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
//			libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
//			libroDiarioDetalleHaber.setIntPersEmpresaCuenta(modeloDetalleExtornoHaber.getId().getIntPersEmpresaCuenta());
//			libroDiarioDetalleHaber.setIntContPeriodo(modeloDetalleExtornoHaber.getId().getIntContPeriodoCuenta());
//			libroDiarioDetalleHaber.setStrContNumeroCuenta(modeloDetalleExtornoHaber.getId().getStrContNumeroCuenta());
//			libroDiarioDetalleHaber.setIntPersPersona(expedienteCredito.getEgreso().getIntPersPersonaGirado());
//			libroDiarioDetalleHaber.setIntParaDocumentoGeneral(libroDiarioDetalleSolicitudHaber.getIntParaDocumentoGeneral());
//			libroDiarioDetalleHaber.setStrSerieDocumento(libroDiarioDetalleSolicitudHaber.getStrSerieDocumento());
//			libroDiarioDetalleHaber.setStrNumeroDocumento(libroDiarioDetalleSolicitudHaber.getStrNumeroDocumento());
//			libroDiarioDetalleHaber.setIntPersEmpresaSucursal(libroDiarioDetalleSolicitudHaber.getIntPersEmpresaSucursal());
//			libroDiarioDetalleHaber.setIntSucuIdSucursal(libroDiarioDetalleSolicitudHaber.getIntSucuIdSucursal());
//			libroDiarioDetalleHaber.setIntSudeIdSubSucursal(libroDiarioDetalleSolicitudHaber.getIntSudeIdSubSucursal());
//			libroDiarioDetalleHaber.setIntParaMonedaDocumento(libroDiarioDetalleSolicitudHaber.getIntParaMonedaDocumento());
//			if(libroDiarioDetalleHaber.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
//				libroDiarioDetalleHaber.setBdHaberSoles(expedienteCredito.getBdMontoTotal());
//				libroDiarioDetalleHaber.setBdDebeSoles(null);			
//			}else{
//				libroDiarioDetalleHaber.setBdHaberExtranjero(expedienteCredito.getBdMontoTotal());
//				libroDiarioDetalleHaber.setBdDebeExtranjero(null);			
//			}
//			
//			LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
//			libroDiarioDetalleDebe.setId(new LibroDiarioDetalleId());
//			libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
//			libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
//			libroDiarioDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleExtornoDebe.getId().getIntPersEmpresaCuenta());
//			libroDiarioDetalleDebe.setIntContPeriodo(modeloDetalleExtornoDebe.getId().getIntContPeriodoCuenta());
//			libroDiarioDetalleDebe.setStrContNumeroCuenta(modeloDetalleExtornoDebe.getId().getStrContNumeroCuenta());
//			libroDiarioDetalleDebe.setIntPersPersona(expedienteCredito.getEgreso().getIntPersPersonaGirado());
//			libroDiarioDetalleDebe.setIntParaDocumentoGeneral(libroDiarioDetalleSolicitudDebe.getIntParaDocumentoGeneral());
//			libroDiarioDetalleDebe.setStrSerieDocumento(libroDiarioDetalleSolicitudDebe.getStrSerieDocumento());
//			libroDiarioDetalleDebe.setStrNumeroDocumento(libroDiarioDetalleSolicitudDebe.getStrNumeroDocumento());
//			libroDiarioDetalleDebe.setIntPersEmpresaSucursal(libroDiarioDetalleSolicitudDebe.getIntPersEmpresaSucursal());
//			libroDiarioDetalleDebe.setIntSucuIdSucursal(libroDiarioDetalleSolicitudDebe.getIntSucuIdSucursal());
//			libroDiarioDetalleDebe.setIntSudeIdSubSucursal(libroDiarioDetalleSolicitudDebe.getIntSudeIdSubSucursal());
//			libroDiarioDetalleDebe.setIntParaMonedaDocumento(libroDiarioDetalleSolicitudDebe.getIntParaMonedaDocumento());
//			if(libroDiarioDetalleDebe.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
//				libroDiarioDetalleDebe.setBdHaberSoles(null);
//				libroDiarioDetalleDebe.setBdDebeSoles(expedienteCredito.getBdMontoTotal());
//			}else{
//				libroDiarioDetalleDebe.setBdHaberExtranjero(null);
//				libroDiarioDetalleDebe.setBdDebeExtranjero(expedienteCredito.getBdMontoTotal());			
//			}
			
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleSolicitudHaber);
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleSolicitudDebe);
		} catch (Exception e) {
			log.error("Error en generarLibroDiarioExtorno ---> "+e);
		}
		
		
		return libroDiario;		
	}
	
	private List<Cronograma> generarCronogramaMovimiento(ExpedienteCredito expedienteCredito)throws Exception{
		List<Cronograma> listaCronograma = new ArrayList<Cronograma>();
		log.info("---- GiroPrestamoService.generarCronogramaMovimiento ----");
		for(CronogramaCredito cronogramaCredito : expedienteCredito.getListaCronogramaCredito()){
			log.info("Cronograma Servicio obtenido ---> "+cronogramaCredito);
			if(cronogramaCredito.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				Cronograma cronograma = new Cronograma();
				cronograma.setId(new CronogramaId());
				cronograma.getId().setIntPersEmpresaPk(cronogramaCredito.getId().getIntPersEmpresaPk());
				cronograma.getId().setIntCuentaPk(cronogramaCredito.getId().getIntCuentaPk());
				cronograma.getId().setIntItemExpediente(cronogramaCredito.getId().getIntItemExpediente());
				cronograma.getId().setIntItemExpedienteDetalle(cronogramaCredito.getId().getIntItemDetExpediente());
				cronograma.getId().setIntItemCronograma(cronogramaCredito.getId().getIntItemCronograma());
				cronograma.setIntNumeroCuota(cronogramaCredito.getIntNroCuota());
				cronograma.setIntParaTipoCuotaCod(cronogramaCredito.getIntParaTipoCuotaCod());
				cronograma.setIntParaFormaPagoCod(cronogramaCredito.getIntParaFormaPagoCod());
				cronograma.setIntParaTipoConceptoCreditoCod(cronogramaCredito.getIntParaTipoConceptoCod());
				cronograma.setBdMontoConcepto(cronogramaCredito.getBdMontoConcepto());
				cronograma.setBdMontoCapital(cronogramaCredito.getBdMontoCapital());
				cronograma.setTsFechaVencimiento(cronogramaCredito.getTsFechaVencimiento());
				cronograma.setIntPeriodoPlanilla(cronogramaCredito.getIntPeriodoPlanilla());
				cronograma.setIntParaEstadoCod(cronogramaCredito.getIntParaEstadoCod());
				cronograma.setBdSaldoDetalleCredito(cronogramaCredito.getBdMontoConcepto());
				log.info("Cronograma Movimiento generado ---> "+cronograma);				
				listaCronograma.add(cronograma);
			}
		}
		
		return listaCronograma;
	}
	
	private Credito obtenerCreditoPorExpediente(ExpedienteCredito expedienteCredito) throws Exception{
		CreditoFacadeRemote creditoFacade = (CreditoFacadeRemote) EJBFactory.getRemote(CreditoFacadeRemote.class);
		
		CreditoId creditoId = new CreditoId();
		creditoId.setIntPersEmpresaPk(expedienteCredito.getIntPersEmpresaCreditoPk());
		creditoId.setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod());
		creditoId.setIntItemCredito(expedienteCredito.getIntItemCredito());
		
		return creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
	}
	
	public ExpedienteCredito grabarGiroPrestamo(ExpedienteCredito expedienteCredito)throws BusinessException{
		EstructuraDetalle estructuraDetalle = null;
		CuentaId cuentaId = new CuentaId();
		EstructuraId estructuraId = new EstructuraId();
		List<CuentaIntegrante> lstCuentaIntegrante = null;
		List<SocioEstructura> lstSocioEstructura = null;
		Integer intCasoPlanilla = 1;
		Calendar miCal = Calendar.getInstance();
		SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
		List<CuentaConcepto> lstCuentaConcepto = null;
		List<ConceptoPago> lstConceptoPago = null;
		List<ConceptoPago> lstConceptoPagoGenerado = new ArrayList<ConceptoPago>();
		Movimiento movGenerado = null;
		List<CronogramaCredito> listaCronogramaServicio = null;
		List<Cronograma> listaCronogramaMovimiento = null;
		BigDecimal bdMontoNuevoSaldoCredito = null;
		
		BigDecimal bdMontoAntiguoSaldoCredito = BigDecimal.ZERO;
		BigDecimal bdMontoCancelacionCredito = BigDecimal.ZERO;
		try{
			EgresoFacadeRemote egresoFacade = (EgresoFacadeRemote) EJBFactory.getRemote(EgresoFacadeRemote.class);
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			EstructuraFacadeRemote estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			//Seteamos Pk de la cuenta 			
			cuentaId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
			cuentaId.setIntCuenta(expedienteCredito.getId().getIntCuentaPk());	
			
			// Recupera CSO_CONFCREDITOS porempresa, tipo crédito e item crédito.
			expedienteCredito.getCuenta().setCredito(obtenerCreditoPorExpediente(expedienteCredito));
			
			Egreso egreso = expedienteCredito.getEgreso();
				
			log.info("---- GiroPrestamoService.grabarGiroPrestamo ----");
			log.info("Expediente credito obtenido ---> "+expedienteCredito);
			log.info("Egreso obtenido ---> "+egreso);
			log.info("Expediente credito obtenido ---> "+expedienteCredito.getExpedienteCreditoCancelacion());
			log.info("Expediente credito a cancelar  ---> "+expedienteCredito.getExpedienteCreditoCancelacion());

			//Obtenemos cancelacion credito... jchavez 24.06.2014
			List<CancelacionCredito> cancelaCred = boCancelacionCredito.getListaPorExpedienteCredito(expedienteCredito);
			
			log.info("Cancelacion Credito : "+cancelaCred);
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				log.info("Egreso detalle obtenidos ---> "+egresoDetalle);
			}

			log.info("Libro Diario obtenido ---> "+egreso.getLibroDiario());
			for(LibroDiarioDetalle libroDiarioDetalle : egreso.getLibroDiario().getListaLibroDiarioDetalle()){
				log.info("Libro Diario Detalle obtenidos ---> "+libroDiarioDetalle);
			}
			//Se procede a la grabación del Egreso, Egreso Detalle, Libro Diario y Libro Diario Detalle
			egreso.setBlnEsGiroPorSedeCentral(false);
			egreso = egresoFacade.grabarEgresoParaGiroPrestamo(egreso);
			if (egreso.getIntErrorGeneracionEgreso().equals(1)) {
				expedienteCredito.getEgreso().setIntErrorGeneracionEgreso(1);
				expedienteCredito.getEgreso().setStrMsgErrorGeneracionEgreso(egreso.getStrMsgErrorGeneracionEgreso());
				return expedienteCredito;
			}
		
			//Generación y grabación de Cronograma Movimiento (CMO_CRONOGRAMACREDITO)
			
			// 1. Recupera los cronogramas de crédito segun expediente crédito.
			expedienteCredito.setListaCronogramaCredito(boCronogramaCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId()));
			// Si la fecha de giro y la fecha de generacion del cronograma son iguales, 
			if (expedienteCredito.getListaCronogramaCredito()==null || expedienteCredito.getListaCronogramaCredito().isEmpty()) {
				expedienteCredito.getEgreso().setIntErrorGeneracionEgreso(1);
				expedienteCredito.getEgreso().setStrMsgErrorGeneracionEgreso("El cronograma credito retorna nulo");
				return expedienteCredito;
			}
			
			log.info("Fecha cronograma generado en la Solicitud: "+sdfFecha.format(expedienteCredito.getListaCronogramaCredito().get(0).getTsFechaEnvioView()));
			log.info("Fecha cronograma generado en e Giro: "+sdfFecha.format(miCal.getTimeInMillis()));
			if (sdfFecha.format(expedienteCredito.getListaCronogramaCredito().get(0).getTsFechaEnvioView()).equals(sdfFecha.format(miCal.getTimeInMillis()))) {
				listaCronogramaServicio = new ArrayList<CronogramaCredito>();
				listaCronogramaServicio.addAll(expedienteCredito.getListaCronogramaCredito());
				listaCronogramaMovimiento = generarCronogramaMovimiento(expedienteCredito);
				for(Cronograma cronograma : listaCronogramaMovimiento){
					log.info("Cronograma Movimiento generado ---> "+cronograma);
					conceptoFacade.grabarCronograma(cronograma);
				}
			}else { // Si la fecha de giro y la fecha de generacion del cronograma son diferentes, 
				// 2. Se anula el cronograma generado en la solicitud.
				if (expedienteCredito.getListaCronogramaCredito()!=null && !expedienteCredito.getListaCronogramaCredito().isEmpty()) {
					for (CronogramaCredito cronogramaAnterior : expedienteCredito.getListaCronogramaCredito()) {
						cronogramaAnterior.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						boCronogramaCredito.modificar(cronogramaAnterior);
					}
				}
				// 3. Se regenera el cronograma de crédito.				
				lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaId);
				if (lstCuentaIntegrante!=null && !lstCuentaIntegrante.isEmpty()) {
					for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
						lstSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(cuentaIntegrante.getId().getIntPersonaIntegrante(), cuentaIntegrante.getId().getIntPersEmpresaPk());
						if (lstSocioEstructura!=null && !lstSocioEstructura.isEmpty()) {
							for (SocioEstructura socioEstructura : lstSocioEstructura) {
								estructuraId.setIntNivel(socioEstructura.getIntNivel());
								estructuraId.setIntCodigo(socioEstructura.getIntCodigo());
								estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYCasoYTipoSocioYModalidad(estructuraId, intCasoPlanilla, socioEstructura.getIntTipoSocio(), socioEstructura.getIntModalidad());
								expedienteCredito.setListaCronogramaCreditoRegenerado(regenerarCronograma(estructuraDetalle, expedienteCredito));
								break;
							}
						}
					}
				}
				// 4. Grabamos el cronograma credito regenerado en servicio.
				Integer intTotalCuotasAPagar = 0;
				listaCronogramaServicio = regenerarCronogramaServicio(expedienteCredito);
				List<CronogramaCredito> listaCronogramaServicioRegenerado = new ArrayList<CronogramaCredito>();
				for (CronogramaCredito cronogramaCredito : listaCronogramaServicio) {
					log.info("Cronograma Servicio regenerado ---> "+cronogramaCredito);
					listaCronogramaServicioRegenerado.add(boCronogramaCredito.grabar(cronogramaCredito));
					//Obtenemos el total de cuotas a pagar
					if (intTotalCuotasAPagar < cronogramaCredito.getIntNroCuota()) intTotalCuotasAPagar = cronogramaCredito.getIntNroCuota();
				}
				// 5. Modificamos el numero de cuotas en el expediente credito
				expedienteCredito.setIntNumeroCuota(intTotalCuotasAPagar);
				boExpedienteCredito.modificar(expedienteCredito);
				
				// 6. Grabamos el cronograma regenerado en movimiento.
				listaCronogramaMovimiento = regenerarCronogramaMovimiento(listaCronogramaServicioRegenerado);
				for(Cronograma cronograma : listaCronogramaMovimiento){
					log.info("Cronograma Movimiento regenerado ---> "+cronograma);
					conceptoFacade.grabarCronograma(cronograma);
				}
			}
			
			//Generación y grabación de Expediente Movimiento (CMO_EXPEDIENTECREDITO)
			Expediente expedienteMovimiento = generarExpedienteMovimiento(expedienteCredito, listaCronogramaServicio);
			log.info("Expediente Movimiento generado ---> "+expedienteMovimiento);
			conceptoFacade.grabarExpediente(expedienteMovimiento);
			
			//Agregado 14.04.2014 
			if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
				if (expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()!=null) {
					List<Cronograma> cronogCred = null;			
					log.info("Expediente credito a cancelar ----> "+expedienteCredito.getExpedienteCreditoCancelacion());
					//modificamos el expediente credito de movimiento
					Expediente expMov = new Expediente();
					expMov.setId(new ExpedienteId());
					expMov.getId().setIntPersEmpresaPk(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
					expMov.getId().setIntCuentaPk(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntCuentaPk());
					expMov.getId().setIntItemExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente());
					expMov.getId().setIntItemExpedienteDetalle(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					expMov = conceptoFacade.getExpedientePorPK(expMov.getId());
					//jachevz 25.06.2014 guardamos el antiguo monto de saldo credito
					bdMontoAntiguoSaldoCredito = expMov.getBdSaldoCredito();
					
					//23.06.2014 jchavez El monto a cancelar es el monto que se encuentra en la tabla de SERVICIO.CANCELACION_CREDITO
					if (cancelaCred!=null && !cancelaCred.isEmpty()) {
						bdMontoCancelacionCredito = cancelaCred.get(0).getBdMontoCancelado();
						expMov.setBdSaldoCredito(expMov.getBdSaldoCredito().subtract(cancelaCred.get(0).getBdMontoCancelado())); //SU SALDO - MONTO DE CANCELACREDITO
					}
					bdMontoNuevoSaldoCredito = expMov.getBdSaldoCredito();
	
					conceptoFacade.modificarExpediente(expMov);
					//modificamos cronograma movimiento, saldos a cero
					cronogCred = conceptoFacade.getListaCronogramaPorPkExpediente(expMov.getId());
					if (cronogCred!=null && !cronogCred.isEmpty()) {
						for (Cronograma cronogramaMov : cronogCred) {
							//jchavez 24.06.2014
							//hasta q se acabe el saldo de cancelacion credito
							if (cronogramaMov.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
								&& cronogramaMov.getIntParaTipoConceptoCreditoCod().compareTo(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION)==0
								&& cronogramaMov.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)==1){
									if (cronogramaMov.getBdSaldoDetalleCredito().compareTo(bdMontoCancelacionCredito)==-1) {
										bdMontoCancelacionCredito = bdMontoCancelacionCredito.subtract(cronogramaMov.getBdSaldoDetalleCredito());
										cronogramaMov.setBdSaldoDetalleCredito(BigDecimal.ZERO);
										conceptoFacade.modificarCronograma(cronogramaMov);										
									}else{
										cronogramaMov.setBdSaldoDetalleCredito(cronogramaMov.getBdSaldoDetalleCredito().subtract(bdMontoCancelacionCredito));
										conceptoFacade.modificarCronograma(cronogramaMov);
										bdMontoCancelacionCredito = BigDecimal.ZERO;
										break;
									}
							}					
						}
					}					
				}				
			}
						
			//Generación y grabación de Movimiento (CMO_MOVIMIENTOCTACTE)
			Movimiento movimiento = generarMovimiento(expedienteCredito, egreso);
			log.info("Movimiento generado ---> "+movimiento);
			conceptoFacade.grabarMovimiento(movimiento);
			//Agregado 14.04.2014
			//Generacion del movimiento del prestamo cancelado..
			if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
				if (expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()!=null) {
					//1. Movimiento del saldo
					Movimiento movExpCancelado = new Movimiento();
					movExpCancelado.setTsFechaMovimiento(expedienteCredito.getEgreso().getTsFechaProceso());
					movExpCancelado.setIntPersEmpresa(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
					movExpCancelado.setIntCuenta(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntCuentaPk());
					movExpCancelado.setIntPersPersonaIntegrante(expedienteCredito.getEgreso().getIntPersPersonaGirado());
					movExpCancelado.setIntItemCuentaConcepto(null);
					movExpCancelado.setIntItemExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente());
					movExpCancelado.setIntItemExpedienteDetalle(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					movExpCancelado.setIntParaTipoConceptoGeneral(Constante.PARAM_T_CONCEPTOGENERAL_AMORTIZACION_EXPEDIENTE);
					movExpCancelado.setIntParaTipoMovimiento(Constante.PARAM_T_MOVIMIENTOCTACTE_EGRESOCAJA);
					//Se graba el mismo que se grabo en el Egreso
					movExpCancelado.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
					movExpCancelado.setStrSerieDocumento(null);
					movExpCancelado.setStrNumeroDocumento(""+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()+"-"+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					movExpCancelado.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
					//saldos despues de la cancelacion del prestamo
					//jchavez 25.06.2014 va el monto que esta en cancelacion credito
					movExpCancelado.setBdMontoMovimiento( bdMontoAntiguoSaldoCredito);
					//jchavez 25.06.2014 va el saldo de la diferencia entre el monto saldo del expMov y el monto de cancela credito
					movExpCancelado.setBdMontoSaldo(bdMontoNuevoSaldoCredito);
					//egreso del prestamo nuevo
					movExpCancelado.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
					movExpCancelado.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
					
					movExpCancelado.setIntPersEmpresaUsuario(expedienteCredito.getEgreso().getIntPersEmpresaUsuario());
					movExpCancelado.setIntPersPersonaUsuario(expedienteCredito.getEgreso().getIntPersPersonaUsuario());
					conceptoFacade.grabarMovimiento(movExpCancelado);
					//2. Movimiento del interes
					Movimiento movExpCanceladoInteres = new Movimiento();
					movExpCanceladoInteres.setTsFechaMovimiento(expedienteCredito.getEgreso().getTsFechaProceso());
					movExpCanceladoInteres.setIntPersEmpresa(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
					movExpCanceladoInteres.setIntCuenta(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntCuentaPk());
					movExpCanceladoInteres.setIntPersPersonaIntegrante(expedienteCredito.getEgreso().getIntPersPersonaGirado());
					movExpCanceladoInteres.setIntItemCuentaConcepto(null);
					movExpCanceladoInteres.setIntItemExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente());
					movExpCanceladoInteres.setIntItemExpedienteDetalle(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					movExpCanceladoInteres.setIntParaTipoConceptoGeneral(Constante.PARAM_T_CONCEPTOGENERAL_INTERES);
					movExpCanceladoInteres.setIntParaTipoMovimiento(Constante.PARAM_T_MOVIMIENTOCTACTE_EGRESOCAJA);
					//Se graba el mismo que se grabo en el Egreso
					movExpCanceladoInteres.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
					movExpCanceladoInteres.setStrSerieDocumento(null);
					movExpCanceladoInteres.setStrNumeroDocumento(""+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()+"-"+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					movExpCanceladoInteres.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
					//interes despues de la cancelacion del prestamo
					movExpCanceladoInteres.setBdMontoMovimiento(expedienteCredito.getBdMontoInteresAtrasado());
					movExpCanceladoInteres.setBdMontoSaldo(null);
					//egreso del prestamo nuevo
					movExpCanceladoInteres.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
					movExpCanceladoInteres.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
					
					movExpCanceladoInteres.setIntPersEmpresaUsuario(expedienteCredito.getEgreso().getIntPersEmpresaUsuario());
					movExpCanceladoInteres.setIntPersPersonaUsuario(expedienteCredito.getEgreso().getIntPersPersonaUsuario());
					movExpCanceladoInteres = conceptoFacade.grabarMovimiento(movExpCanceladoInteres);
					
					//Agregado 15.04.2014
					ExpedienteId expCancelado = new ExpedienteId();
					expCancelado.setIntCuentaPk(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntCuentaPk());
					expCancelado.setIntPersEmpresaPk(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
					expCancelado.setIntItemExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente());
					expCancelado.setIntItemExpedienteDetalle(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					InteresCancelado canceladoUltimo = conceptoFacade.getMaxInteresCanceladoPorExpediente(expCancelado);
					//-------------------------------------------------------------------
					InteresCancelado interesCancelado = new InteresCancelado();
					interesCancelado.setId(new InteresCanceladoId());
					interesCancelado.getId().setIntCuentaPk(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntCuentaPk());
					//interesCancelado.getId().setIntItemCancelaInteres(expedienteCreditoViejoMovimiento.getId().get);
					interesCancelado.getId().setIntItemExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente());
					interesCancelado.getId().setIntItemExpedienteDetalle(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					interesCancelado.getId().setIntItemMovCtaCte(movExpCanceladoInteres.getIntItemMovimiento());
					interesCancelado.getId().setIntPersEmpresaPk(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
					interesCancelado.setBdMontoInteres(expedienteCredito.getBdMontoInteresAtrasado());
					//jchavez 25.06.2014 - va el saldo del expediente antes del movimiento
					interesCancelado.setBdSaldoCredito(bdMontoAntiguoSaldoCredito);
					interesCancelado.setBdTasa(expedienteCredito.getExpedienteCreditoCancelacion().getBdPorcentajeInteres());
					
					interesCancelado.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					//Parametro 159 - 3 (CAJA)
					interesCancelado.setIntParaTipoFormaPago(Constante.PARAM_T_FORMADEPAGO_CAJA);
					//
					Calendar calendar = Calendar.getInstance();
					//jchavez 26.06.2014 caso ultimo interes calculado, su monto sea cero
					if (canceladoUltimo.getBdMontoInteres().equals(BigDecimal.ZERO)) {
						calendar.setTimeInMillis(canceladoUltimo.getTsFechaInicio().getTime());
						interesCancelado.setTsFechaInicio(new Timestamp(calendar.getTimeInMillis()));
					}else{
						calendar.setTimeInMillis(canceladoUltimo.getTsFechaMovimiento().getTime());
				        calendar.add(Calendar.DATE, 1);
						interesCancelado.setTsFechaInicio(new Timestamp(calendar.getTimeInMillis()));
					}
			        
					//
					Calendar fecHoy = Calendar.getInstance();
					Date dtHoy = fecHoy.getTime();
					//
					interesCancelado.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
					interesCancelado.setIntDias(obtenerDiasEntreFechas(convertirTimestampToDate(new Timestamp(calendar.getTimeInMillis())), dtHoy));
					interesCancelado=conceptoFacade.grabarInteresCancelado(interesCancelado);
				}				
			}		
			
			//Generación y grabación de Estado Concepto Cuenta Expediente (CMO_ESTADOCPTOCEXPE)
			EstadoExpediente estadoExpediente = generarEstadoExpedienteGiro(expedienteMovimiento);
			log.info("Estado Expediente Generado ---> "+estadoExpediente);
			conceptoFacade.grabarEstado(estadoExpediente);
			//Agregado 14.04.2014 
			//Generacion del estado para el prestamo cancelado
			if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
				//AGREGAR VALIDACION SALDO A 0 si el saldo de cancelacion credito y el saldo movimiento del exp cancelado son =
				if (bdMontoNuevoSaldoCredito.compareTo(BigDecimal.ZERO)==0) {
					if (expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()!=null) {
						EstadoExpediente estadoExpedienteCancelado = new EstadoExpediente();
						estadoExpedienteCancelado.setId(new EstadoExpedienteId());
						//Seteando valores...
						estadoExpedienteCancelado.getId().setIntEmpresaEstado(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
						estadoExpedienteCancelado.setIntEmpresa(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
						estadoExpedienteCancelado.setIntCuenta(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntCuentaPk());
						estadoExpedienteCancelado.setIntItemCuentaConcepto(null);
						estadoExpedienteCancelado.setIntItemExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente());
						estadoExpedienteCancelado.setIntItemDetExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
						estadoExpedienteCancelado.setIntParaEstadoExpediente(2); //Estado Vigente
						estadoExpedienteCancelado.setIntIndicadorEntrega(null);
						conceptoFacade.grabarEstado(estadoExpedienteCancelado);
					}
				}				
			}		
			
			//Si el préstamo genera aporte...
			if (expedienteCredito.getBdMontoAporte()!=null && expedienteCredito.getBdMontoAporte().compareTo(BigDecimal.ZERO)>0) {
				//1. Vamos a CuentaConcepto y filtramos por cuenta
				Integer intNroRegistrosConceptoPago = 0;
				lstCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuentaId);
				if (lstCuentaConcepto!=null && !lstCuentaConcepto.isEmpty()) {
					for (CuentaConcepto cuentaConcepto : lstCuentaConcepto) {
						if (cuentaConcepto.getListaCuentaConceptoDetalle()!=null && !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()) {
							for (CuentaConceptoDetalle cuentaConceptoDetalle : cuentaConcepto.getListaCuentaConceptoDetalle()) {
								if (cuentaConceptoDetalle.getIntParaTipoConceptoCod().equals(Constante.PARAM_T_TIPOCUENTA_APORTES) && cuentaConceptoDetalle.getTsFin()==null) {
									//1.1 Modificando el Saldo de Aporte en Cuenta Concepto
									cuentaConcepto.setBdSaldo(cuentaConcepto.getBdSaldo().add(expedienteCredito.getBdMontoAporte()));
									conceptoFacade.modificarCuentaConcepto(cuentaConcepto);	
									//1.2 Modificando el Saldo de Aporte en Cuenta Concepto Detalle
									cuentaConceptoDetalle.setBdSaldoDetalle(cuentaConceptoDetalle.getBdSaldoDetalle().add(expedienteCredito.getBdMontoAporte()));
									conceptoFacade.modificarCuentaConceptoDetalle(cuentaConceptoDetalle);
									//1.3 Generar y grabar movimiento aporte
									Movimiento mov = new Movimiento();
									mov.setTsFechaMovimiento(expedienteCredito.getEgreso().getTsFechaProceso());
									mov.setIntParaTipoMovimiento(Constante.PARAM_T_MOVIMIENTOCTACTE_EGRESOCAJA);
									//Se graba el mismo que se grabo en el Egreso
									mov.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
									mov.setIntPersPersonaUsuario(expedienteCredito.getEgreso().getIntPersPersonaUsuario());
									mov.setIntPersEmpresaUsuario(expedienteCredito.getEgreso().getIntPersEmpresaUsuario());	
									mov.setIntPersEmpresa(expedienteCredito.getId().getIntPersEmpresaPk());
									mov.setIntCuenta(expedienteCredito.getCuenta().getId().getIntCuenta());
									mov.setIntItemCuentaConcepto(cuentaConceptoDetalle.getId().getIntItemCuentaConcepto());
									mov.setIntPersPersonaIntegrante(expedienteCredito.getEgreso().getIntPersPersonaGirado());
									mov.setIntParaTipoConceptoGeneral(Constante.PARAM_T_CONCEPTOGENERAL_AMORTIZACION_APORTE);
									mov.setIntItemExpediente(null);
									mov.setIntItemExpedienteDetalle(null);
									mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
									mov.setStrSerieDocumento(null);								
									mov.setStrNumeroDocumento(""+expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
									mov.setBdMontoMovimiento(expedienteCredito.getBdMontoAporte());
									mov.setBdMontoSaldo(cuentaConcepto.getBdSaldo());
									mov.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
									mov.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
									movGenerado = conceptoFacade.grabarMovimiento(mov);
									//1.4 Generando Concepto detalle a grabar
									//Obtenemos la cantidad de registros a grabar, dicvidiedo el monto aporte de expediente credito entre el monto moncepto de Cuenta Concepto Detalle.
									intNroRegistrosConceptoPago = expedienteCredito.getBdMontoAporte().divide(cuentaConceptoDetalle.getBdMontoConcepto(),0,RoundingMode.UP).intValue();
									log.info("Cantidad de registros a grabar: "+intNroRegistrosConceptoPago);
									//Generamos el Concepto Pago
									ConceptoPago cptoPagoGenerado = null;

									Integer intPeriodoGenerado = 0;
									lstConceptoPago = conceptoFacade.getUltimoCptoPagoPorCuentaConceptoDet(cuentaConceptoDetalle.getId());
									if (lstConceptoPago!=null && !lstConceptoPago.isEmpty()) {
										for (ConceptoPago conceptoPago : lstConceptoPago) {
											intPeriodoGenerado = aumentarPeriodoInicial(conceptoPago.getIntPeriodo());
											log.info("Periodo generado para la grabación: "+intPeriodoGenerado);
											for (int i = 1; i <= intNroRegistrosConceptoPago; i++) {
												cptoPagoGenerado = new ConceptoPago();
												cptoPagoGenerado.setId(new ConceptoPagoId());
												cptoPagoGenerado.getId().setIntPersEmpresaPk(conceptoPago.getId().getIntPersEmpresaPk());
												cptoPagoGenerado.getId().setIntCuentaPk(conceptoPago.getId().getIntCuentaPk());
												cptoPagoGenerado.getId().setIntItemCuentaConcepto(conceptoPago.getId().getIntItemCuentaConcepto());
												cptoPagoGenerado.getId().setIntItemCtaCptoDet(conceptoPago.getId().getIntItemCtaCptoDet());
												cptoPagoGenerado.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

												if (i!=intNroRegistrosConceptoPago) {
													cptoPagoGenerado.setIntPeriodo(intPeriodoGenerado);
													cptoPagoGenerado.setBdMontoPago(cuentaConceptoDetalle.getBdMontoConcepto());
													cptoPagoGenerado.setBdMontoSaldo(BigDecimal.ZERO);
												}else {
													cptoPagoGenerado.setIntPeriodo(intPeriodoGenerado);
													cptoPagoGenerado.setBdMontoPago(expedienteCredito.getBdMontoAporte().subtract(cuentaConceptoDetalle.getBdMontoConcepto().multiply(new BigDecimal(i-1))));
													cptoPagoGenerado.setBdMontoSaldo(cuentaConceptoDetalle.getBdMontoConcepto().subtract(cptoPagoGenerado.getBdMontoPago()));												
												}				
												cptoPagoGenerado = conceptoFacade.grabarConceptoPago(cptoPagoGenerado);
												lstConceptoPagoGenerado.add(cptoPagoGenerado);
												intPeriodoGenerado = aumentarPeriodoInicial(cptoPagoGenerado.getIntPeriodo());
											}					
										}
									}else{
										intPeriodoGenerado = aumentarPeriodoInicial(Integer.parseInt(sdfPeriodo.format(Calendar.getInstance().getTime())));
										log.info("Periodo generado para la grabación: "+intPeriodoGenerado);
										for (int i = 1; i <= intNroRegistrosConceptoPago; i++) {
											cptoPagoGenerado = new ConceptoPago();
											cptoPagoGenerado.setId(new ConceptoPagoId());
											cptoPagoGenerado.getId().setIntPersEmpresaPk(cuentaConceptoDetalle.getId().getIntPersEmpresaPk());
											cptoPagoGenerado.getId().setIntCuentaPk(cuentaConceptoDetalle.getId().getIntCuentaPk());
											cptoPagoGenerado.getId().setIntItemCuentaConcepto(cuentaConceptoDetalle.getId().getIntItemCuentaConcepto());
											cptoPagoGenerado.getId().setIntItemCtaCptoDet(cuentaConceptoDetalle.getId().getIntItemCtaCptoDet());
											cptoPagoGenerado.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

											if (i!=intNroRegistrosConceptoPago) {
												cptoPagoGenerado.setIntPeriodo(intPeriodoGenerado);
												cptoPagoGenerado.setBdMontoPago(cuentaConceptoDetalle.getBdMontoConcepto());
												cptoPagoGenerado.setBdMontoSaldo(BigDecimal.ZERO);
											}else {
												cptoPagoGenerado.setIntPeriodo(intPeriodoGenerado);
												cptoPagoGenerado.setBdMontoPago(expedienteCredito.getBdMontoAporte().subtract(cuentaConceptoDetalle.getBdMontoConcepto().multiply(new BigDecimal(i-1))));
												cptoPagoGenerado.setBdMontoSaldo(cuentaConceptoDetalle.getBdMontoConcepto().subtract(cptoPagoGenerado.getBdMontoPago()));												
											}				
											cptoPagoGenerado = conceptoFacade.grabarConceptoPago(cptoPagoGenerado);
											lstConceptoPagoGenerado.add(cptoPagoGenerado);
											intPeriodoGenerado = aumentarPeriodoInicial(cptoPagoGenerado.getIntPeriodo());
											cptoPagoGenerado = new ConceptoPago();
											cptoPagoGenerado.setId(new ConceptoPagoId());
										}
									}
									//Obtenemos los Concepto pagos grabados para generar 
									ConceptoDetallePago conceptoDetallePagoGenerado = null;
									if (lstConceptoPagoGenerado!=null && !lstConceptoPagoGenerado.isEmpty()) {
										for (ConceptoPago conceptoPagoGenerado : lstConceptoPagoGenerado) {
											conceptoDetallePagoGenerado = new ConceptoDetallePago();
											conceptoDetallePagoGenerado.setId(new ConceptoDetallePagoId());
											conceptoDetallePagoGenerado.getId().setIntPersEmpresaPk(conceptoPagoGenerado.getId().getIntPersEmpresaPk());
											conceptoDetallePagoGenerado.getId().setIntCuentaPk(conceptoPagoGenerado.getId().getIntCuentaPk());
											conceptoDetallePagoGenerado.getId().setIntItemCuentaConcepto(conceptoPagoGenerado.getId().getIntItemCuentaConcepto()); 
											conceptoDetallePagoGenerado.getId().setIntItemCtaCptoDet(conceptoPagoGenerado.getId().getIntItemCtaCptoDet()); 
											conceptoDetallePagoGenerado.getId().setIntItemConceptoPago(conceptoPagoGenerado.getId().getIntItemConceptoPago()); 
											conceptoDetallePagoGenerado.getId().setIntItemMovCtaCte(movGenerado.getIntItemMovimiento());
											conceptoDetallePagoGenerado.setIntTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
											conceptoDetallePagoGenerado.setBdMonto(conceptoPagoGenerado.getBdMontoPago());
											conceptoFacade.grabarConceptoDetallePago(conceptoDetallePagoGenerado);										
										}
									}
								}
							}
						}					
					}
				}
			}			
			
			log.info("---- Extorno ----");
			/* La aprobación de un préstamo genera un asiento, una vez girado, este asiento debe de extornarse.
			   En cuanto al registro, el asiento a generar debe ser gemelo al asiento de la aprobación, la unica 
			   diferencia es que si en la aprobacion la cuenta xxxxxx graba DEBE y la cuenta zzzzzz HABER, se
			   invertirán los montos del DEBE y del HABER. Para obtener el asiento de la aprobacion, en CSE_ESTADOCREDITO
			   se graba el asiento generado.
			*/
			LibroDiario libroDiarioExtorno =  generarLibroDiarioExtorno(expedienteCredito);
			log.info(libroDiarioExtorno);
			for(LibroDiarioDetalle libroDiarioDetalle : libroDiarioExtorno.getListaLibroDiarioDetalle()){
				log.info(libroDiarioDetalle);
			}
			libroDiarioExtorno = libroDiarioFacade.grabarLibroDiario(libroDiarioExtorno);
			System.out.println("libroDiarioExtorno ---> "+libroDiarioExtorno);
			egreso.setIntPersEmpresaLibroExtorno(libroDiarioExtorno.getId().getIntPersEmpresaLibro());
			egreso.setIntContPeriodoLibroExtorno(libroDiarioExtorno.getId().getIntContPeriodoLibro());
			egreso.setIntContCodigoLibroExtorno(libroDiarioExtorno.getId().getIntContCodigoLibro());
			
			log.info("Egreso usado en la generación de Estado Credito ---> "+egreso);
			//Generación y grabación de Estado Credito (CSE_ESTADOCREDITO)
			//Se genera unregistro mas con Estado de Solicitud GIRADO
			EstadoCredito estadoCreditoGirado = generarEstadoCredito(expedienteCredito, egreso);
			log.info("Estado Credito Generado ---> "+estadoCreditoGirado);
			boEstadoCredito.grabar(estadoCreditoGirado);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}		
		expedienteCredito.getEgreso().setIntErrorGeneracionEgreso(0);
		expedienteCredito.getEgreso().setStrMsgErrorGeneracionEgreso("Gabación satisfactoria del Giro de Préstamo");
		return expedienteCredito;
	}

	public Cuenta getCuentaActualPorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
		Cuenta cuentaActual = null;
		try{

			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			List<CuentaIntegrante> listaCuentaIntegrante = cuentaFacade.getCuentaIntegrantePorIdPersona(intIdPersona, intIdEmpresa);
			List<Cuenta> listaCuenta = new ArrayList<Cuenta>();
		
			HashSet<Integer> hashSetIntCuenta = new HashSet<Integer>();
			for(CuentaIntegrante cuentaIntegrante : listaCuentaIntegrante){
				if(cuentaIntegrante.getIntParaTipoIntegranteCod().equals(Constante.TIPOINTEGRANTE_ADMINISTRADOR)){
					hashSetIntCuenta.add(cuentaIntegrante.getId().getIntCuenta());
				}
			}
			
			for(Integer intCuenta : hashSetIntCuenta){
				CuentaId cuentaId = new CuentaId();
				cuentaId.setIntPersEmpresaPk(intIdEmpresa);
				cuentaId.setIntCuenta(intCuenta);
				Cuenta cuenta = new Cuenta();
				cuenta.setId(cuentaId);
				cuenta = cuentaFacade.getCuentaPorId(cuentaId);
				if(cuenta!=null){
					listaCuenta.add(cuenta);					
				}
			}
			
			for(Cuenta cuenta : listaCuenta){
				if(cuenta.getIntParaSituacionCuentaCod().equals(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)){
					cuentaActual = cuenta;
					break;
				}
			}			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuentaActual;
	}
	
	public ExpedienteCredito obtenerExpedientePorEgreso(Egreso egreso)throws Exception{
		EgresoDetalle egresoDetalle = egreso.getListaEgresoDetalle().get(0);
		
		String strNumeroDocumento = egresoDetalle.getStrNumeroDocumento();
		String strPartes[] = strNumeroDocumento.split("-");
		Integer intItemExpediente = Integer.parseInt(strPartes[0]);
		Integer intItemDetExpediente = Integer.parseInt(strPartes[1]);
		
		ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
		expedienteCreditoId.setIntPersEmpresaPk(egreso.getId().getIntPersEmpresaEgreso());
		expedienteCreditoId.setIntCuentaPk(egreso.getIntCuentaGirado());
		expedienteCreditoId.setIntItemExpediente(intItemExpediente);
		expedienteCreditoId.setIntItemDetExpediente(intItemDetExpediente);		
		
		return boExpedienteCredito.getPorPk(expedienteCreditoId);
	}
	
	/**
	 * JCHAVEZ 03.01.2014
	 * Metodo que genera el Estado Expediente de movimiento para el Giro de Prestamos
	 * @param expedienteMovimiento
	 * @return
	 * @throws Exception
	 */
	private EstadoExpediente generarEstadoExpedienteGiro(Expediente expedienteMovimiento)throws Exception{
		log.info("---- GiroPrestamoService.generarEstadoExpediente ----");
		EstadoExpediente estadoExpediente = new EstadoExpediente();
		estadoExpediente.setId(new EstadoExpedienteId());
		//Seteando valores...
		estadoExpediente.getId().setIntEmpresaEstado(expedienteMovimiento.getId().getIntPersEmpresaPk());
		estadoExpediente.setIntEmpresa(expedienteMovimiento.getId().getIntPersEmpresaPk());
		estadoExpediente.setIntCuenta(expedienteMovimiento.getId().getIntCuentaPk());
		estadoExpediente.setIntItemCuentaConcepto(null);
		estadoExpediente.setIntItemExpediente(expedienteMovimiento.getId().getIntItemExpediente());
		estadoExpediente.setIntItemDetExpediente(expedienteMovimiento.getId().getIntItemExpedienteDetalle());
		estadoExpediente.setIntParaEstadoExpediente(1); //Estado Vigente
		estadoExpediente.setIntIndicadorEntrega(null);
				
		return estadoExpediente;
	}	
	
//	/**
//	 * Proceso de regeneración del cronograma de credito
//	 * @param creditoSeleccionado
//	 * @param estructuraDetalle
//	 */
//	public List<CronogramaCredito> regenerarCronograma(ExpedienteCredito expedienteCredito, EstructuraDetalle estructuraDetalle) {
//
//		List<CronogramaCredito> lstNuevoCronograma = new ArrayList<CronogramaCredito>();
//		Envioconcepto envioConcepto = null;
//		Calendar miCal = Calendar.getInstance();
//		String strPeriodoPlla = "";
//		CronogramaCredito cronogramaCredito = new CronogramaCredito();
//		CronogramaCreditoComp cronogramaCreditoComp = new CronogramaCreditoComp();
//		List<CronogramaCredito> listaCronogramaCredito = new ArrayList<CronogramaCredito>();
//		BigDecimal bdPorcentajeInteres = expedienteCredito.getBdPorcentajeInteres()!=null?expedienteCredito.getBdPorcentajeInteres():BigDecimal.ZERO;
//		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
//		/// globales
//		List<CronogramaCreditoComp> listaCronogramaCreditoComp;
//		PlanillaFacadeRemote planillaFacade = null;
//		Integer intNroCuotas = 0;
//		BigDecimal bdMontoTotalSolicitado = expedienteCredito.getBdMontoTotal()!=null?expedienteCredito.getBdMontoTotal():BigDecimal.ZERO;
//		BigDecimal bdCuotaMensual = BigDecimal.ZERO;
//		BigDecimal bdAportes = BigDecimal.ZERO;
//		List<CuentaConcepto> lstCtaCto = null;
//		CuentaConcepto ctaCtoAporte = null;
//		//Agregado para el caso de Giro 15.01.2014
//		SocioComp beanSocioComp = new SocioComp();
//		beanSocioComp.setCuenta(new Cuenta());
//		beanSocioComp.getCuenta().setId(new CuentaId());
//		
//		try {
//			listaCronogramaCreditoComp= new ArrayList<CronogramaCreditoComp>();
//			intNroCuotas = expedienteCredito.getIntNumeroCuota();
//			
//			planillaFacade = (PlanillaFacadeRemote) EJBFactory.getRemote(PlanillaFacadeRemote.class);
//			envioConcepto = planillaFacade.getEnvioConceptoPorEmpPerCta(Constante.PARAM_EMPRESASESION, 
//					obtenerPeriodoActual(),
//					expedienteCredito.getId().getIntCuentaPk());
//
//			lstCtaCto = recuperarCuentasConceptoSocio(expedienteCredito);
//			if(lstCtaCto != null && !lstCtaCto.isEmpty()){
//				ctaCtoAporte = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_APORTES, lstCtaCto);
//				bdAportes = recuperarAportes(ctaCtoAporte);	
//			}
//			
//			// detalle movimiento
//			Calendar fec1erEnvio = Calendar.getInstance();
//			Calendar envio = Calendar.getInstance();
//			int dia = miCal.get(Calendar.DATE);
//			int mes = miCal.get(Calendar.MONTH);
//			int anno = miCal.get(Calendar.YEAR);
//
//			Calendar fecHoy = Calendar.getInstance();
////			Calendar fecEnvioTemp = Calendar.getInstance();
////			String miFechaPrimerVenc = null;
//			Calendar fec1erVenc = Calendar.getInstance();
////			Calendar fecVenc1 = Calendar.getInstance();
////			Calendar fecVenc2 = Calendar.getInstance();
////			Calendar fecVenc3 = Calendar.getInstance();
//
//			// SI HAY ENVIO 
//			if (envioConcepto != null){
//				strPeriodoPlla = "" + envioConcepto.getIntPeriodoplanilla();
//
//				// substring x---o x--->
//				Calendar calendarTemp = Calendar.getInstance();
//				int intUltimoMesPlla = Integer.parseInt(strPeriodoPlla.substring(4, 6));
//				int intUltimoAnnoPlla = Integer.parseInt(strPeriodoPlla.substring(0,4));
//				int intPrimerDiaPlla = Integer.parseInt("01");
//
//				if (intUltimoMesPlla == 12) {
//					intUltimoAnnoPlla = intUltimoAnnoPlla + 1;
//					intUltimoMesPlla = 0;
//				}
//
//				// Definiendo fecha de 1er envio y 1er vencimiento
//				fec1erEnvio.clear();
//				fec1erEnvio.set(intUltimoAnnoPlla, intUltimoMesPlla + 1,intPrimerDiaPlla);
//
//				calendarTemp.set(fec1erEnvio.get(Calendar.YEAR),
//				fec1erEnvio.get(Calendar.MONTH),
//				fec1erEnvio.get(Calendar.DATE));
//				fec1erVenc.setTime(getUltimoDiaDelMes(calendarTemp));
//
//			} else {
//				fecHoy.set(anno, mes, dia);
//				List listaEnvioVencimiento = new ArrayList();
//
//				listaEnvioVencimiento.addAll(calcular1raFechaEnvioVencimiento(estructuraDetalle, fecHoy));
//				fec1erEnvio = (Calendar) listaEnvioVencimiento.get(0);
//				fec1erVenc = (Calendar) listaEnvioVencimiento.get(1);
//			}
//
//			//-----------------------------------------------------------------
//			// Calculamos el resto de dias de envio y vencimiento en base a
//			// fec1erEnvio y fec1erVenc
//			//-----------------------------------------------------------------
//			envio.set(fec1erEnvio.get(Calendar.YEAR),
//			fec1erEnvio.get(Calendar.MONTH),
//			fec1erEnvio.get(Calendar.DATE));
//
//			List listaDiasEnvio = new ArrayList();
//			List listaDiasVencimiento = new ArrayList();
//			int vencDia, vencMes, vencAnno;
//			int envDia, envMes, envAnno;
//
//			envDia = envio.get(Calendar.DATE);
//			envMes = envio.get(Calendar.MONTH);
//			envAnno = envio.get(Calendar.YEAR);
//
//			vencDia = fec1erVenc.get(Calendar.DATE);
//			vencMes = fec1erVenc.get(Calendar.MONTH);
//			vencAnno = fec1erVenc.get(Calendar.YEAR);
//			Integer vencMesEsp = vencMes;
//			// SE GENENERA LA LISTA DE LOS DIAS DE VENCIMIENTO listaDiasVencimiento
//			for (int i = 0; i < intNroCuotas; i++) {
//				Calendar nuevoDia = Calendar.getInstance();
//				//CGD-07.01.2014 - envMes++;
//				vencMesEsp++;
//				
//				if(i==0){
//					if (envMes == 12) {
//						//listaDiasEnvio.add(i, envDia + "/" + envMes + "/"+ envAnno);
//						listaDiasEnvio.add(i, envDia + "/" + vencMes + "/"+ envAnno);
//						envAnno = envAnno + 1;
//						envMes = 0;
//					} else {
//						listaDiasEnvio.add(i, envDia + "/" + vencMes + "/"+ envAnno);
//					}
//
//					nuevoDia.set(vencAnno, vencMes, 15);
//					if (vencMes == 12) {
//						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
//						vencAnno = vencAnno + 1;
//						vencMes = 0;
//					} else {
//						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
//					}
//				}else{
//					if (envMes == 12) {
//						listaDiasEnvio.add(i, "01" + "/" + vencMesEsp + "/"+ envAnno);
//						envAnno = envAnno + 1;
//						envMes = 0;
//					} else {
//						listaDiasEnvio.add(i, "01" + "/" + vencMesEsp + "/"+ envAnno);
//					}
//
//					nuevoDia.set(vencAnno, vencMes, 15);
//					if (vencMes == 12) {
//						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
//						vencAnno = vencAnno + 1;
//						vencMes = 0;
//					} else {
//						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
//					}
//				}
//				
//				
//				vencMes++;
//			}
//
//			BigDecimal bdCuotaFinal = new BigDecimal(0);
//
//			BigDecimal bdTea = BigDecimal.ZERO;
//			if(bdPorcentajeInteres.compareTo(BigDecimal.ZERO)==0){
//				bdTea = (((BigDecimal.ONE.add(bdPorcentajeInteres)).pow(12)).subtract(BigDecimal.ONE).setScale(4,
//				RoundingMode.HALF_UP));
//			}else{
//				bdTea = (((BigDecimal.ONE.add(bdPorcentajeInteres.divide(new BigDecimal(100)))).pow(12)).subtract(BigDecimal.ONE).setScale(4,
//				RoundingMode.HALF_UP));
//			}
//
//			// SE GENENERA LA LISTA DE LA DIEFERENICA DE DIAS diferenciaEntreDias
//			List listaDias = new ArrayList(); // Lista que guarda la
//			List listaSumaDias = new ArrayList(); // Lista que guarda la
//			// sumatoria de dias.
//			int diferenciaEntreDias = 0;
//			int sumaDias = 0;
//
//			for (int i = 0; i < intNroCuotas; i++) {
//				// calculando diferencia entre el 1er vencimeitno y la fecha de hoy
//				if (i == 0) {
//					diferenciaEntreDias = fechasDiferenciaEnDias(fec1erEnvio.getTime(), fec1erVenc.getTime());
//					listaDias.add(i, diferenciaEntreDias);
//					/*
//					 * 
//					 * fec1erEnvio
//					
//					diferenciaEntreDias = fechasDiferenciaEnDias(fec1erEnvio.getTime(), fec1erVenc.getTime());
//					listaDias.add(i, diferenciaEntreDias);
//					 */
//				} else {
//					Calendar calendario = Calendar.getInstance();
//					Calendar calend = new GregorianCalendar(
//					getPrimerDiaDelMes(	StringToCalendar(listaDiasEnvio.get(i).toString())).getYear(),
//					getPrimerDiaDelMes(	StringToCalendar(listaDiasEnvio.get(i).toString())).getMonth(), 1);
//
//					calendario.set(calend.get(Calendar.YEAR),calend.get(Calendar.MONTH),calend.get(Calendar.DATE));
//					diferenciaEntreDias = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
//					listaDias.add(i, diferenciaEntreDias);
//				}
//				sumaDias = sumaDias + diferenciaEntreDias;
//				listaSumaDias.add(i, sumaDias);
//			}
//
//			// Calculamos el valor de la cuota en base a la formula:
//			// ----------------------------------------------------------//
//			// monto //
//			// cuota = _____________________________________ //
//			// //
//			// 1/ (1+tea)^(sumdias/360) + ... n //
//			// ----------------------------------------------------------//
//
//			BigDecimal bdSumatoria = new BigDecimal(0);
//			System.out.println("TEA" + bdTea);
//
//			for (int i = 0; i < intNroCuotas; i++) {
//				BigDecimal bdCalculo1 = new BigDecimal(0);
//				BigDecimal bdCalculo2 = new BigDecimal(0);
//				BigDecimal bdCalculo3 = new BigDecimal(0);
//				BigDecimal bdCalculo4 = new BigDecimal(0);
//				BigDecimal bdResultado = new BigDecimal(0);
//				BigDecimal bdUno = BigDecimal.ONE;
//				double dbResultDenom = 0;
//				bdCalculo1 = new BigDecimal(listaSumaDias.get(i).toString()); // suma de dias
//				bdCalculo2 = new BigDecimal(360); // 360
//				bdCalculo3 = bdTea.add(bdUno); // tea + 1
//				bdCalculo4 = bdCalculo1.divide(bdCalculo2, 4,RoundingMode.HALF_UP);
//				dbResultDenom = Math.pow(bdCalculo3.doubleValue(),bdCalculo4.doubleValue());
//				bdResultado = bdUno.divide(new BigDecimal(dbResultDenom),4, RoundingMode.HALF_UP);
//				bdSumatoria = bdSumatoria.add(bdResultado);
//			}
//
//			bdCuotaFinal = bdMontoTotalSolicitado.divide(bdSumatoria, 4,RoundingMode.HALF_UP);
//			System.out.println("CUOTA FINAL " + bdCuotaFinal);
//
//			// Calculando Interes, Amortizacion, Saldo y la cuota mensual total y se conforma el cronograma
//			BigDecimal bdAmortizacion = new BigDecimal(0);
//			BigDecimal bdSaldo = new BigDecimal(0);
//			BigDecimal bdSaldoMontoCapital = new BigDecimal(0);
//			// BigDecimal bdMontoCocepto = new BigDecimal(0);
//			BigDecimal bdSumaAmortizacion = new BigDecimal(0);
//			bdSaldo = bdMontoTotalSolicitado; // se inicializa el saldo con
//			// el monto solicitado
//			BigDecimal bdSaldoTemp = new BigDecimal(0);
//			List listaSaldos = new ArrayList();
//
//			//SE FORMA LA LISTA DEL CRONOGRAMA
//			for (int i = 0; i < intNroCuotas; i++) {
//				BigDecimal bdCalculo1 = new BigDecimal(0);
//				BigDecimal bdCalculo2 = new BigDecimal(0);
//				BigDecimal bdCalculo3 = new BigDecimal(0);
//				BigDecimal bdCalculo4 = new BigDecimal(0);
//				BigDecimal bdCalculo5 = new BigDecimal(0);
//				BigDecimal bdInteresCuota = new BigDecimal(0);
//				BigDecimal bdUno = BigDecimal.ONE;
//				double dbResultDenom = 0;
//				bdCalculo1 = new BigDecimal(listaDias.get(i).toString()); // suma de dias
//				bdCalculo2 = new BigDecimal(360); // 360
//				bdCalculo3 = bdTea.add(bdUno); // tea + 1
//
//				bdCalculo4 = bdCalculo1.divide(bdCalculo2, 4,RoundingMode.HALF_UP);
//				dbResultDenom = Math.pow(bdCalculo3.doubleValue(),bdCalculo4.doubleValue());
//				bdCalculo5 = new BigDecimal(dbResultDenom).subtract(bdUno);
//
//				// modificar el bdSaldoCapital para que vaya reduciendose
//				bdInteresCuota = bdSaldo.multiply(bdCalculo5);
//				bdInteresCuota = bdInteresCuota.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
//				bdInteresCuota.setScale(4, RoundingMode.HALF_UP);// setScale(4,
//				// BigDecimal.ROUND_HALF_UP);
//				System.out.println("======================= CUOTA NRO " + i	+ " ========================");
//				System.out.println("INTERES CUOTA " + bdInteresCuota);
//				bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
//				bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
//				bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
//				System.out.println("AMORTIZACION  " + bdAmortizacion);
//
//
//				if(bdSaldo.compareTo(bdAmortizacion)<0){
//					bdSaldo = BigDecimal.ZERO;
//					//bdSaldo = bdSaldo.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
//				}else{
//					bdSaldo = bdSaldo.subtract(bdAmortizacion);
//					bdSaldo = bdSaldo.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
//				}
//
//				listaSaldos.add(i, bdSaldo);
//				System.out.println("SALDO   " + bdSaldo);
//
//				if (i + 1 == intNroCuotas) {
//					bdSaldo = BigDecimal.ZERO;
//					BigDecimal bdSaldoRed = new BigDecimal(0);
//
//					if (intNroCuotas == 1) {
//						bdAmortizacion = bdMontoTotalSolicitado;
//					} else {
//						bdSaldoRed = new BigDecimal(listaSaldos.get(i - 1).toString());
//						bdAmortizacion = bdSaldoRed;
//					}
//				} else {
//					bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
//					bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE,4, RoundingMode.HALF_UP);
//				}
//
//				bdCuotaMensual = bdAmortizacion.add(bdInteresCuota);
//				bdAportes = bdAportes.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
//
//				// Formando el cronograma comp que es lo que se visualiza en popup
//				cronogramaCreditoComp = new CronogramaCreditoComp();
//				cronogramaCreditoComp.setStrFechaEnvio(Constante.sdf.format((StringToCalendar(listaDiasEnvio.get(i)	.toString())).getTime()));
//				cronogramaCreditoComp.setStrFechaVencimiento(listaDiasVencimiento.get(i).toString());
//				cronogramaCreditoComp.setIntDiasTranscurridos(new Integer(listaDias.get(i).toString()));
//				cronogramaCreditoComp.setBdSaldoCapital(bdSaldo);
//				cronogramaCreditoComp.setBdAmortizacion(bdAmortizacion);
//				cronogramaCreditoComp.setBdInteres(bdInteresCuota);
//				cronogramaCreditoComp.setBdCuotaMensual(bdCuotaMensual);
//				cronogramaCreditoComp.setBdAportes(bdAportes);
//				cronogramaCreditoComp.setBdTotalCuotaMensual(bdCuotaMensual.add(bdAportes));
//				listaCronogramaCreditoComp.add(cronogramaCreditoComp);
//				
//				
//				// Agregando el tipo de Concepto - "Amortización"
//				cronogramaCredito = new CronogramaCredito();
//				cronogramaCredito.setId(new CronogramaCreditoId());
//				cronogramaCredito.setIntNroCuota(i + 1);
//				cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
//				cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
//				cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
//				if (i == 0) {
//					bdSaldoMontoCapital = bdMontoTotalSolicitado;
//				} else {
//					bdSaldoMontoCapital = bdSaldoTemp;
//				}
//				bdSaldoTemp = bdSaldo;
//				cronogramaCredito.setBdMontoCapital(bdSaldoMontoCapital);
//				if (i + 1 == intNroCuotas) {
//					cronogramaCredito.setBdMontoConcepto(bdSaldoMontoCapital);
//				} else {
//					cronogramaCredito.setBdMontoConcepto(i + 1 == intNroCuotas ? 
//					bdAmortizacion.add(bdMontoTotalSolicitado.subtract(bdAmortizacion)): bdAmortizacion);
//				}
//				Date fechaVenc = new Date();
//				fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();
//				cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
//				cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime()).toString()));
//				cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//				cronogramaCredito.setBdAmortizacionView(cronogramaCreditoComp.getBdAmortizacion());
//				cronogramaCredito.setBdAportesView(cronogramaCreditoComp.getBdAportes());
//				cronogramaCredito.setBdInteresView(cronogramaCreditoComp.getBdInteres());
//				cronogramaCredito.setBdSaldoCapitalView(cronogramaCreditoComp.getBdSaldoCapital());
//				Calendar clEnvio= Calendar.getInstance();
//				clEnvio = (StringToCalendar(cronogramaCreditoComp.getStrFechaEnvio()));
//				cronogramaCredito.setTsFechaEnvioView(new Timestamp(clEnvio.getTime().getTime()));
//				lstNuevoCronograma.add(cronogramaCredito);
//
//				// Agregando el tipo de Concepto - "Interés"
//				cronogramaCredito = new CronogramaCredito();
//				cronogramaCredito.setId(new CronogramaCreditoId());
//				cronogramaCredito.setIntNroCuota(i + 1);
//				cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
//				cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
//				cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_INTERES);
//				cronogramaCredito.setBdMontoConcepto(bdInteresCuota);
//				cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
//				cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime())));
//				cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//				// CGD-12.11.2013 - nuevos atributos
//				cronogramaCredito.setBdAmortizacionView(cronogramaCreditoComp.getBdAmortizacion());
//				cronogramaCredito.setBdAportesView(cronogramaCreditoComp.getBdAportes());
//				cronogramaCredito.setBdInteresView(cronogramaCreditoComp.getBdInteres());
//				cronogramaCredito.setBdSaldoCapitalView(cronogramaCreditoComp.getBdSaldoCapital());
//				Calendar clEnvio2= Calendar.getInstance();
//				clEnvio2 = (StringToCalendar(cronogramaCreditoComp.getStrFechaEnvio()));
//				cronogramaCredito.setTsFechaEnvioView(new Timestamp(clEnvio2.getTime().getTime()));
//				lstNuevoCronograma.add(cronogramaCredito);
//			}
//		} catch (Exception e) {
//			log.error("Error en reCalcularCronograma ---> "+e);
//		}
//		return lstNuevoCronograma;
//	}
//	
	/**
	 * Carga la variable global: listaCuentaConcepto.
	 * @param socioComp
	 */
	public List<CuentaConcepto> recuperarCuentasConceptoSocio (ExpedienteCredito expedienteCredito){
		List<CuentaConcepto> listaCtaCto = null;
		List<CuentaConcepto> listaCuentaConcepto = null;
		//Agregado 15.01.2014
		CuentaId cId = new CuentaId();
		ConceptoFacadeRemote conceptoFacade = null;
		try {
			conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			//Seteando valores a CuentaId
			cId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
			cId.setIntCuenta(expedienteCredito.getId().getIntCuentaPk());
		
			listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(cId);
			if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){
				listaCtaCto = new ArrayList<CuentaConcepto>();
				listaCtaCto = listaCuentaConcepto ;
			}
		} catch (Exception e) {
			log.error("Error en recuperarCuentasConceptoSocio ---> "+e);
		}
		return listaCtaCto;
	}
	
	/**
	 * Recupera Cuenta COncepto segun parametro.
	 * @param intTipoCuentaConcepto
	 * @param listaCuentaConcepto
	 * @return
	 * @throws BusinessException
	 */
	public CuentaConcepto recuperarCuentasConceptoXCuentaYTipo(Integer intTipoCuentaConcepto, List<CuentaConcepto> listaCuentaConcepto)throws BusinessException{
		CuentaConcepto cuentaConceptoReturn = null;
		
		try {
			if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){
					
					for (CuentaConcepto cuentaConcepto : listaCuentaConcepto) {
						CuentaConceptoDetalle detalle = null;
						
						if(cuentaConcepto.getListaCuentaConceptoDetalle() != null 
						&& !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()){
							detalle = new CuentaConceptoDetalle();
							detalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);
			
							if(detalle.getIntParaTipoConceptoCod().compareTo(intTipoCuentaConcepto)==0){
								cuentaConceptoReturn = new CuentaConcepto();
								cuentaConceptoReturn = cuentaConcepto ;
								break;
							}
						}		
					}
			}
			
		} catch (Exception e) {
			log.error("Error en recuperarCuentasConceptoXCuentaYTipo ---> "+e);
		}
		
		return cuentaConceptoReturn;
	}	
	
	/**
	 * 
	 * @param ctaCtoAporte
	 * @return
	 */
	public BigDecimal recuperarAportes(CuentaConcepto ctaCtoAporte){
		BigDecimal bdAportes= BigDecimal.ZERO;
		try {
			
			if(ctaCtoAporte != null){
				if(ctaCtoAporte.getListaCuentaConceptoDetalle() != null && !ctaCtoAporte.getListaCuentaConceptoDetalle().isEmpty()){
					
					
					for (CuentaConceptoDetalle ctaCtoDet : ctaCtoAporte.getListaCuentaConceptoDetalle()) {
						Boolean blnContinua = Boolean.TRUE;
						
						// se valida el inicio
						if(ctaCtoDet.getTsInicio()!= null){
							if(ctaCtoDet.getTsInicio().before(new Timestamp(new Date().getTime()))){
								blnContinua = Boolean.TRUE;
							}else{
								blnContinua = Boolean.FALSE;
							}
							if(blnContinua){
								if(ctaCtoDet.getTsFin()!= null){
									if(ctaCtoDet.getTsFin().after(new Timestamp(new Date().getTime()))){
										blnContinua = Boolean.FALSE;	
									}
								}else{
									blnContinua = Boolean.TRUE;
								}
							}
						}else{
							blnContinua = Boolean.FALSE;
						}

						if(blnContinua){
							bdAportes = bdAportes.add(ctaCtoDet.getBdMontoConcepto());
						}
					}
				}
			}
			
		} catch (Exception e) {
			log.error("Error en recuperarAportes ---> "+e);
		}
		return bdAportes;
	}
	
	/**
	 * Retorna el primer dia del mes
	 * @param Calendar
	 * @return Date
	 */
	public Date getPrimerDiaDelMes(Calendar fecha) {

		fecha.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH),
				fecha.getActualMinimum(Calendar.DAY_OF_MONTH),
				fecha.getMinimum(Calendar.HOUR_OF_DAY),
				fecha.getMinimum(Calendar.MINUTE),
				fecha.getMinimum(Calendar.SECOND));

		return fecha.getTime();
	}
	
	/**
	 * Retorna la fecha del ultimo dia del mes
	 * @param Calendar
	 * @return Date
	 */
	public Date getUltimoDiaDelMes(Calendar fecha) {
		fecha.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH),
				fecha.getActualMaximum(Calendar.DAY_OF_MONTH),
				fecha.getMaximum(Calendar.HOUR_OF_DAY),
				fecha.getMaximum(Calendar.MINUTE),
				fecha.getMaximum(Calendar.SECOND));

		return fecha.getTime();
	}	
	
//	/**
//	 * Calcula la fecha del ler Envio y 1er Vencimiento.
//	 * Toma en cuenta la existencia de salto al siguiente mes.
//	 * @param estructuraDetalle
//	 * @param fecHoy
//	 * @return listaEnvioVencimiento (0, fec1erEnvio)(1, fec1erVenc);
//	 */
//	public List calcular1raFechaEnvioVencimiento(EstructuraDetalle estructuraDetalle, Calendar fecHoy) {
//		Calendar fecEnvioTemp = Calendar.getInstance();
//		String miFechaPrimerVenc = null;
//		Calendar fec1erVenc  = Calendar.getInstance();
//		Calendar fecVenc1 	 = Calendar.getInstance();
//		Calendar fecVenc2 	 = Calendar.getInstance();
//		Calendar fecVenc3 	 = Calendar.getInstance();
//		Calendar fec1erEnvio = Calendar.getInstance();
//		List listaEnvioVencimiento = new ArrayList();
//		Integer intMesDummy = 0;
//		
//		intMesDummy = fecHoy.get(Calendar.MONTH);
//		
//		if ((fecHoy.get(Calendar.DATE)) < estructuraDetalle.getIntDiaEnviado()) {
//			fecVenc1.clear();
//			fecVenc1.set(fecHoy.get(Calendar.YEAR), 
//						intMesDummy,
//						fecHoy.get(Calendar.DATE));
//			fecEnvioTemp.clear();
//			fecEnvioTemp.set(fecHoy.get(Calendar.YEAR),
//							fecHoy.get(Calendar.MONTH),
//							fecHoy.get(Calendar.DATE));
//						//estructuraDetalle.getIntDiaEnviado());
//
//		} else { // Salta al mes siguiente
//			fecVenc1.clear();
//			fecVenc1.set(fecHoy.get(Calendar.YEAR),
//					intMesDummy + 1, fecHoy.get(Calendar.DATE));
//			fecEnvioTemp.clear();
//			fecEnvioTemp.set(fecHoy.get(Calendar.YEAR),
//					intMesDummy,
//					//fecHoy.get(Calendar.MONTH) + 1,
//					fecHoy.get(Calendar.DATE));
//					//estructuraDetalle.getIntDiaEnviado());
//		}
//		fec1erEnvio = fecHoy;
//		//fec1erEnvio = fecEnvioTemp;
//
//		// fecEnvioTemp;
//		// Se recalcula fecha de 1er Vencimiento - Se agrega un mes si intSalto
//		// != 1
//		if (estructuraDetalle.getIntSaltoEnviado().compareTo(1) == 0) {
//			miFechaPrimerVenc = Constante.sdf.format(getUltimoDiaDelMes(
//					fecVenc1).getTime());
//			fecVenc3 = StringToCalendar(miFechaPrimerVenc);
//		} else {
//			int dd = fecVenc1.get(Calendar.DATE); // captura día actual
//			int mm = fecVenc1.get(Calendar.MONTH); // captura mes actual
//			int aa = fecVenc1.get(Calendar.YEAR); // captura año actual
//
//			fecVenc2.set(aa, mm + 1, dd);
//			miFechaPrimerVenc = Constante.sdf.format(getUltimoDiaDelMes(
//					fecVenc2).getTime());
//			fecVenc3 = StringToCalendar(miFechaPrimerVenc);
//		}
//		fec1erVenc.clear();
//		fec1erVenc = fecVenc3;
//		//fec1erEnvio.set(fec1erEnvio.get(Calendar.YEAR),	fec1erEnvio.get(Calendar.MONTH),estructuraDetalle.getIntDiaEnviado());
//		//fec1erEnvio.set(fecHoy.get(Calendar.YEAR),	fecHoy.get(Calendar.MONTH),fecHoy.get(Calendar.DATE));
//
//		listaEnvioVencimiento.add(0, fec1erEnvio);
//		listaEnvioVencimiento.add(1, fec1erVenc);
//
//		return listaEnvioVencimiento;
//	}
	
	/**
	 * Convierte una cadena a Calendar
	 * @param fecha
	 * @return Calendar cal
	 */
	public Calendar StringToCalendar(String fecha) {
		DateFormat formatter;
		Date date;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();

		try {
			date = (Date) formatter.parse(fecha);
			cal.setTime(date);
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
		}

		return cal;
	}
	
	/**
	 * Calcula el nro de dias entre 2 fechas Calendar
	 * @param Date fechaInicial
	 * @param Date fechaFinal
	 * @return int dias
	 */
	public int fechasDiferenciaEnDias(Date fechaInicial, Date fechaFinal) {

		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		String fechaInicioString = df.format(fechaInicial);
		double dias = new Double(0);
		
		try {
			fechaInicial = df.parse(fechaInicioString);

			String fechaFinalString = df.format(fechaFinal);
			fechaFinal = df.parse(fechaFinalString);
			long fechaInicialMs = fechaInicial.getTime();
			long fechaFinalMs = fechaFinal.getTime();
			long diferencia = fechaFinalMs - fechaInicialMs;
			dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		
		} catch (ParseException ex) {
			log.error("Error ParseException en fechasDiferenciaEnDias ---> " + ex);
		}
		
		return ((int) dias);
	}
	
	/**
	 * JCHAVEZ 09.01.2014
	 * Regenera el cronograma en servicio
	 */
	private List<CronogramaCredito> regenerarCronogramaServicio(ExpedienteCredito expedienteCredito)throws Exception{
		List<CronogramaCredito> listaCronograma = new ArrayList<CronogramaCredito>();
		log.info("---- GiroPrestamoService.generarCronogramaMovimiento ----");
		for(CronogramaCredito cronogramaCredito : expedienteCredito.getListaCronogramaCreditoRegenerado()){
			log.info("Cronograma Servicio obtenido ---> "+cronogramaCredito);
			if(cronogramaCredito.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				CronogramaCredito cronograma = new CronogramaCredito();
				cronograma.setId(new CronogramaCreditoId());
				cronograma.getId().setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
				cronograma.getId().setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
				cronograma.getId().setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
				cronograma.getId().setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
				cronograma.setIntNroCuota(cronogramaCredito.getIntNroCuota());
				cronograma.setIntParaTipoCuotaCod(cronogramaCredito.getIntParaTipoCuotaCod());
				cronograma.setIntParaFormaPagoCod(cronogramaCredito.getIntParaFormaPagoCod());
				cronograma.setIntParaTipoConceptoCod(cronogramaCredito.getIntParaTipoConceptoCod());
				cronograma.setBdMontoConcepto(cronogramaCredito.getBdMontoConcepto());
				cronograma.setBdMontoCapital(cronogramaCredito.getBdMontoCapital());
				cronograma.setTsFechaVencimiento(cronogramaCredito.getTsFechaVencimiento());
				cronograma.setIntPeriodoPlanilla(cronogramaCredito.getIntPeriodoPlanilla());
				cronograma.setIntParaEstadoCod(cronogramaCredito.getIntParaEstadoCod());
				cronograma.setBdMontoConcepto(cronogramaCredito.getBdMontoConcepto());
				//NUEVOS CAMPOS 17.01.2014
				cronograma.setTsFechaEnvioView(cronogramaCredito.getTsFechaEnvioView());
				cronograma.setBdSaldoCapitalView(cronogramaCredito.getBdSaldoCapitalView());
				cronograma.setBdAmortizacionView(cronogramaCredito.getBdAmortizacionView());
				cronograma.setBdInteresView(cronogramaCredito.getBdInteresView());
				cronograma.setBdAportesView(cronogramaCredito.getBdAportesView());
				log.info("Cronograma Movimiento generado ---> "+cronograma);				
				listaCronograma.add(cronograma);
			}
		}
		
		return listaCronograma;
	}
	
	private List<Cronograma> regenerarCronogramaMovimiento(List<CronogramaCredito> listaCronogramaServicioRegenerado)throws Exception{
		List<Cronograma> listaCronograma = new ArrayList<Cronograma>();
		log.info("---- GiroPrestamoService.generarCronogramaMovimiento ----");
		for(CronogramaCredito cronogramaCredito : listaCronogramaServicioRegenerado){
			log.info("Cronograma Servicio obtenido ---> "+cronogramaCredito);
			if(cronogramaCredito.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				Cronograma cronograma = new Cronograma();
				cronograma.setId(new CronogramaId());
				cronograma.getId().setIntPersEmpresaPk(cronogramaCredito.getId().getIntPersEmpresaPk());
				cronograma.getId().setIntCuentaPk(cronogramaCredito.getId().getIntCuentaPk());
				cronograma.getId().setIntItemExpediente(cronogramaCredito.getId().getIntItemExpediente());
				cronograma.getId().setIntItemExpedienteDetalle(cronogramaCredito.getId().getIntItemDetExpediente());
				cronograma.getId().setIntItemCronograma(cronogramaCredito.getId().getIntItemCronograma());
				cronograma.setIntNumeroCuota(cronogramaCredito.getIntNroCuota());
				cronograma.setIntParaTipoCuotaCod(cronogramaCredito.getIntParaTipoCuotaCod());
				cronograma.setIntParaFormaPagoCod(cronogramaCredito.getIntParaFormaPagoCod());
				cronograma.setIntParaTipoConceptoCreditoCod(cronogramaCredito.getIntParaTipoConceptoCod());
				cronograma.setBdMontoConcepto(cronogramaCredito.getBdMontoConcepto());
				cronograma.setBdMontoCapital(cronogramaCredito.getBdMontoCapital());
				cronograma.setTsFechaVencimiento(cronogramaCredito.getTsFechaVencimiento());
				cronograma.setIntPeriodoPlanilla(cronogramaCredito.getIntPeriodoPlanilla());
				cronograma.setIntParaEstadoCod(cronogramaCredito.getIntParaEstadoCod());
				cronograma.setBdSaldoDetalleCredito(cronogramaCredito.getBdMontoConcepto());
				log.info("Cronograma Movimiento generado ---> "+cronograma);				
				listaCronograma.add(cronograma);
			}
		}
		
		return listaCronograma;
	}
	
	public Integer aumentarPeriodoInicial(Integer intPeriodoInicial){
		log.debug("aumentarPeriodoInicial");
		String strPeriodoAumentado = null;
		Integer intPeriodo=0;
		
		Integer intMes = Integer.parseInt(intPeriodoInicial.toString().substring(4));
		Integer intAnio = Integer.parseInt(intPeriodoInicial.toString().substring(0,4));
		try
		{
			if(intMes.compareTo(12) == 0){
				intMes = 1;
				intAnio = intAnio+1;				
				strPeriodoAumentado = intAnio.toString()+ ""+String.format("%02d",intMes);
				intPeriodo = Integer.parseInt(strPeriodoAumentado);
			}
			else{
				intMes = intMes+1;
				strPeriodoAumentado = intAnio.toString()+ ""+String.format("%02d",intMes);
				intPeriodo = Integer.parseInt(strPeriodoAumentado);				
			}			
			log.debug("intPeriodo: "+intPeriodo);
			log.debug("aumentarPeriodoInicial FIN");
		}catch(Exception e)	{
			log.error(e.getMessage(), e);
		}
		return intPeriodo;
	}
	
	/**
	 * Genera el cronograma del credito validado.
	 * @param creditoSeleccionado
	 * @param estructuraDetalle
	 * socioComp = 
	 */
	public  List<CronogramaCredito>  regenerarCronograma(EstructuraDetalle estructuraDetalle,ExpedienteCredito expedienteCredito) {
		Calendar miCal = Calendar.getInstance();
		CronogramaCredito cronogramaCredito = null;
		CronogramaCreditoComp cronogramaCreditoComp = null;
		List<CronogramaCredito> listaCronogramaCredito = null;
//		Integer intNroCuotasOld = 0;
//		BigDecimal bdPorcentajeInteres = BigDecimal.ZERO;
		BigDecimal bdPorcentajeInteres = expedienteCredito.getBdPorcentajeInteres()!=null?expedienteCredito.getBdPorcentajeInteres():BigDecimal.ZERO;
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
		String strFechaRegistro = "";
		Integer intNroCuotas = expedienteCredito.getIntNumeroCuota();
//		Boolean blnSeGeneroCronogramaCorrectamente = true;
		SocioComp beanSocioComp = null;
		BigDecimal bdMontoTotalSolicitado = expedienteCredito.getBdMontoTotal()!=null?expedienteCredito.getBdMontoTotal():BigDecimal.ZERO;
		BigDecimal bdCuotaMensual = BigDecimal.ZERO;
		BigDecimal bdAportes = BigDecimal.ZERO;
		BigDecimal bdTotalCuotaMensual = BigDecimal.ZERO;
		List<CronogramaCreditoComp> listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
		CapacidadCreditoComp capacidadCreditoCompAddList = null;
		List<CapacidadCreditoComp> listaCapacidadCreditoComp = new ArrayList<CapacidadCreditoComp>();
		List<CuentaConcepto> lstCtaCto = null;
		CuentaConcepto ctaCtoAporte = null;
//		String strPorcInteres = "" + expedienteCredito.getBdPorcentajeInteres().divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
//		String strPorcAportes = ""+expedienteCredito.getBdPorcentajeAporte().divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
//		BigDecimal bdMontoPrestamo = expedienteCredito.getBdMontoTotal();
//		BigDecimal bdPorcSeguroDesgravamen = expedienteCredito.getBdMontoGravamen();
//		BigDecimal bdTotalDstos = expedienteCredito.getBdMontoAporte();
//		bdTotalCuotaMensual = bdTotalCuotaMensualCronograma;
		
		try {			
			SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
//			CreditoFacadeRemote creditoFacade = (CreditoFacadeRemote) EJBFactory.getRemote(CreditoFacadeRemote.class);
			
			if (expedienteCredito.getEstadoCreditoUltimo()==null) {
				expedienteCredito.setEstadoCreditoUltimo(obtenerUltimoEstadoCredito(expedienteCredito));
			}
			
			strFechaRegistro = Constante.sdf.format(miCal.getTimeInMillis()); //expedienteCredito.getEstadoCreditoUltimo().getTsFechaEstado()
			
			SocioPK o = new SocioPK();
			o.setIntIdEmpresa(expedienteCredito.getId().getIntPersEmpresaPk());
			o.setIntIdPersona(expedienteCredito.getPersonaGirar().getIntIdPersona());
			beanSocioComp = socioFacade.getSocioNatural(o);
			// Se trabajara sobre la fercha de registro
			miCal.clear();
			miCal.setTime(Constante.sdf.parse(strFechaRegistro));
			cronogramaCredito = new CronogramaCredito();
			cronogramaCreditoComp = new CronogramaCreditoComp();
			listaCronogramaCredito = new ArrayList<CronogramaCredito>();
			//Cargamos el Socio Estructura Origen para validacion de fecha de vencimiento de la 1era cuota
			for (SocioEstructura socioEstructura : beanSocioComp.getSocio().getListSocioEstructura()) {
				if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
					beanSocioComp.getSocio().setSocioEstructura(socioEstructura);
				}
			}
			
			//cargamos la lista de sociestructura
			if (beanSocioComp.getSocio().getListSocioEstructura() != null && !beanSocioComp.getSocio().getListSocioEstructura().isEmpty()) {
				for (SocioEstructura socioEstructura : beanSocioComp.getSocio().getListSocioEstructura()) {
					if(socioEstructura.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						capacidadCreditoCompAddList = new CapacidadCreditoComp();
						capacidadCreditoCompAddList.setSocioEstructura(socioEstructura);
						listaCapacidadCreditoComp.add(capacidadCreditoCompAddList);
					}
				}
			}
			
			lstCtaCto = recuperarCuentasConceptoSocio(expedienteCredito);
			if(lstCtaCto != null && !lstCtaCto.isEmpty()){
				ctaCtoAporte = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_APORTES, lstCtaCto);
				bdAportes = recuperarAportes(ctaCtoAporte);	
			}
			
//			Credito credito = creditoFacade.getCreditoPorIdCredito(creditoComp.getCredito().getId());
//			Credito creditoSeleccionado = null;
//			creditoSeleccionado = validarConfiguracionCredito(beanSocioComp,credito);
			
//			Credito creditoSeleccionado,
//			if (creditoSeleccionado.getListaCreditoInteres() != null) {
//				for (CreditoInteres creditoInteres : creditoSeleccionado.getListaCreditoInteres()){
//					if(blnSeGeneroCronogramaCorrectamente){
//						limpiarCronograma();
//						if(creditoInteres.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
//							if(creditoInteres.getIntMesMaximo() != null){
//								if(creditoInteres.getBdTasaInteres() == null || creditoInteres.getBdTasaInteres().compareTo(BigDecimal.ZERO)==0){
//									bdPorcentajeInteres = BigDecimal.ZERO;
//								}else{
//									bdPorcentajeInteres = creditoInteres.getBdTasaInteres();
//								}
								// Se valida si el socio es Activo o cesante
//								if(creditoInteres.getIntParaTipoSocio().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio())==0){
//									if(blnSeGeneroCronogramaCorrectamente){
//										intCuotasMaximas = creditoInteres.getIntMesMaximo();
//
//										if (intNroCuotas == null || intNroCuotas == 0 || (intNroCuotas < 0)){
//											intNroCuotas = creditoInteres.getIntMesMaximo();
//											msgTxtExesoCuotasCronograma = "";
//										}else{
//											intNroCuotasOld = intNroCuotas;
//											msgTxtExesoCuotasCronograma = "";
//											if(intCuotasMaximas.compareTo(intNroCuotas)<0){
//												intNroCuotas = creditoInteres.getIntMesMaximo();
//												msgTxtExesoCuotasCronograma = "El número de Cuotas ingresada("+ intNroCuotasOld+") " + "excede al número configurado para este Crédito ("+
//																			  creditoInteres.getIntMesMaximo()+ "). Se colocará el nro por defecto.";
//											}
//										}

										// CGD-21.01.2014-YT
										List listaDiasEnvio = new ArrayList();
										List listaDiasVencimiento = new ArrayList();
										int vencMes, vencAnno; // vencDia, 
										int envAnno; // envDia, envMes, 

//										envDia = new Integer(strFechaRegistro.substring(0,2));
//										envMes = new Integer(strFechaRegistro.substring(3,5));
										envAnno = new Integer(strFechaRegistro.substring(6,10));
										//Calculo de la 1era Fecha de Vencimiento
										String strVencimiento = calcular1raFechaVencimiento(estructuraDetalle, strFechaRegistro, beanSocioComp, listaCapacidadCreditoComp);
//										vencDia = new Integer(strVencimiento.substring(0,2));
										vencMes = new Integer(strVencimiento.substring(3,5));
										vencAnno = new Integer(strVencimiento.substring(6,10));
										
										// SE GENENERA LA LISTA DE LOS DIAS DE VENCIMIENTO listaDiasVencimiento
										for (int i = 0; i < intNroCuotas; i++) {
											Calendar nuevoDia = Calendar.getInstance();
											if(i==0){
												listaDiasEnvio.add(i, strFechaRegistro);
												listaDiasVencimiento.add(i,strVencimiento);
											}else{
												if (vencMes == 12) {
													listaDiasEnvio.add(i, "01" + "/" + vencMes + "/"+ envAnno);
													nuevoDia.set(vencAnno, vencMes-1, 15);
													listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
													vencAnno = vencAnno + 1;
													vencMes = 0;
													envAnno ++;
												} else {
													listaDiasEnvio.add(i, "01" + "/" + vencMes + "/"+ envAnno);
													nuevoDia.set(vencAnno, vencMes-1, 15);
													listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
												}
											}
											vencMes++;
										}

										BigDecimal bdCuotaFinal = new BigDecimal(0);

										BigDecimal bdTea = BigDecimal.ZERO;
										if(bdPorcentajeInteres.compareTo(BigDecimal.ZERO)==0){
											bdTea = (((BigDecimal.ONE.add(bdPorcentajeInteres)).pow(12)).subtract(BigDecimal.ONE).setScale(4,
											RoundingMode.HALF_UP));
										}else{
											bdTea = (((BigDecimal.ONE.add(bdPorcentajeInteres.divide(new BigDecimal(100)))).pow(12)).subtract(BigDecimal.ONE).setScale(4,
											RoundingMode.HALF_UP));
										}

										// SE GENENERA LA LISTA DE LA DIEFERENICA DE DIAS diferenciaEntreDias
										List listaDias = new ArrayList(); // Lista que guarda la
										List listaSumaDias = new ArrayList(); // Lista que guarda la
										// sumatoria de dias.
										int diferenciaEntreDias = 0;
										int sumaDias = 0;

										for (int i = 0; i < intNroCuotas; i++) {
											// calculando diferencia entre el 1er vencimeitno y la fecha de hoy
											if (i == 0) {
												diferenciaEntreDias = fechasDiferenciaEnDias((StringToCalendar(strFechaRegistro)).getTime(), 
																							 (StringToCalendar(strVencimiento)).getTime());
												listaDias.add(i, diferenciaEntreDias);
											} else {
												Calendar calendario = Calendar.getInstance();
												Calendar calend = new GregorianCalendar(
												getPrimerDiaDelMes(	StringToCalendar(listaDiasEnvio.get(i).toString())).getYear(),
												getPrimerDiaDelMes(	StringToCalendar(listaDiasEnvio.get(i).toString())).getMonth(), 1);

												calendario.set(calend.get(Calendar.YEAR),calend.get(Calendar.MONTH),calend.get(Calendar.DATE));
												diferenciaEntreDias = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
												listaDias.add(i, diferenciaEntreDias);
											}
											sumaDias = sumaDias + diferenciaEntreDias;
											listaSumaDias.add(i, sumaDias);
										}

										// Calculamos el valor de la cuota en base a la formula:
										// ----------------------------------------------------------//
										// monto //
										// cuota = _____________________________________ //
										// //
										// 1/ (1+tea)^(sumdias/360) + ... n //
										// ----------------------------------------------------------//

										BigDecimal bdSumatoria = new BigDecimal(0);
										System.out.println("TEA" + bdTea);

										for (int i = 0; i < intNroCuotas; i++) {
											BigDecimal bdCalculo1 = new BigDecimal(0);
											BigDecimal bdCalculo2 = new BigDecimal(0);
											BigDecimal bdCalculo3 = new BigDecimal(0);
											BigDecimal bdCalculo4 = new BigDecimal(0);
											BigDecimal bdResultado = new BigDecimal(0);
											BigDecimal bdUno = BigDecimal.ONE;
											double dbResultDenom = 0;
											bdCalculo1 = new BigDecimal(listaSumaDias.get(i).toString()); // suma de dias
											bdCalculo2 = new BigDecimal(360); // 360
											bdCalculo3 = bdTea.add(bdUno); // tea + 1
											bdCalculo4 = bdCalculo1.divide(bdCalculo2, 4,RoundingMode.HALF_UP);
											dbResultDenom = Math.pow(bdCalculo3.doubleValue(),bdCalculo4.doubleValue());
											bdResultado = bdUno.divide(new BigDecimal(dbResultDenom),4, RoundingMode.HALF_UP);
											bdSumatoria = bdSumatoria.add(bdResultado);
										}

										bdCuotaFinal = bdMontoTotalSolicitado.divide(bdSumatoria, 4,RoundingMode.HALF_UP);
										System.out.println("CUOTA FINAL " + bdCuotaFinal);

										// Calculando Interes, Amortizacion, Saldo y la cuota mensual total y se conforma el cronograma
										BigDecimal bdAmortizacion = new BigDecimal(0);
										BigDecimal bdSaldo = new BigDecimal(0);
										BigDecimal bdSaldoMontoCapital = new BigDecimal(0);
										// BigDecimal bdMontoCocepto = new BigDecimal(0);
										BigDecimal bdSumaAmortizacion = new BigDecimal(0);
										bdSaldo = bdMontoTotalSolicitado; // se inicializa el saldo con
										// el monto solicitado
										BigDecimal bdSaldoTemp = new BigDecimal(0);
										List listaSaldos = new ArrayList();

										//SE FORMA LA LISTA DEL CRONOGRAMA
										for (int i = 0; i < intNroCuotas; i++) {
											BigDecimal bdCalculo1 = new BigDecimal(0);
											BigDecimal bdCalculo2 = new BigDecimal(0);
											BigDecimal bdCalculo3 = new BigDecimal(0);
											BigDecimal bdCalculo4 = new BigDecimal(0);
											BigDecimal bdCalculo5 = new BigDecimal(0);
											BigDecimal bdInteresCuota = new BigDecimal(0);
											BigDecimal bdUno = BigDecimal.ONE;
											double dbResultDenom = 0;
											bdCalculo1 = new BigDecimal(listaDias.get(i).toString()); // suma de dias
											bdCalculo2 = new BigDecimal(360); // 360
											bdCalculo3 = bdTea.add(bdUno); // tea + 1

											bdCalculo4 = bdCalculo1.divide(bdCalculo2, 4,RoundingMode.HALF_UP);
											dbResultDenom = Math.pow(bdCalculo3.doubleValue(),bdCalculo4.doubleValue());
											bdCalculo5 = new BigDecimal(dbResultDenom).subtract(bdUno);

											// modificar el bdSaldoCapital para que vaya reduciendose
											bdInteresCuota = bdSaldo.multiply(bdCalculo5);
											bdInteresCuota = bdInteresCuota.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
											bdInteresCuota.setScale(4, RoundingMode.HALF_UP);// setScale(4,
											System.out.println("======================= CUOTA NRO " + i	+ " ========================");
											System.out.println("INTERES CUOTA " + bdInteresCuota);
											bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
											bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
											bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
											System.out.println("AMORTIZACION  " + bdAmortizacion);

											if(bdSaldo.compareTo(bdAmortizacion)<0){
												bdSaldo = BigDecimal.ZERO;
											}else{
												bdSaldo = bdSaldo.subtract(bdAmortizacion);
												bdSaldo = bdSaldo.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
											}

											listaSaldos.add(i, bdSaldo);
											System.out.println("SALDO   " + bdSaldo);

											if (i + 1 == intNroCuotas) {
												bdSaldo = BigDecimal.ZERO;
												BigDecimal bdSaldoRed = new BigDecimal(0);

												if (intNroCuotas == 1) {
													bdAmortizacion = bdMontoTotalSolicitado;
												} else {
													bdSaldoRed = new BigDecimal(listaSaldos.get(i - 1).toString());
													bdAmortizacion = bdSaldoRed;
												}
											} else {
												bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
												bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE,4, RoundingMode.HALF_UP);
											}
											bdCuotaMensual = bdAmortizacion.add(bdInteresCuota);
											bdAportes = bdAportes.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
											
						// Formando el cronograma comp que es lo que se visualiza en popup
											cronogramaCreditoComp = new CronogramaCreditoComp();
											cronogramaCreditoComp.setStrFechaEnvio(Constante.sdf.format((StringToCalendar(listaDiasEnvio.get(i)	.toString())).getTime()));
											cronogramaCreditoComp.setStrFechaVencimiento(listaDiasVencimiento.get(i).toString());
											cronogramaCreditoComp.setIntDiasTranscurridos(new Integer(listaDias.get(i).toString()));
											cronogramaCreditoComp.setBdSaldoCapital(bdSaldo);
											cronogramaCreditoComp.setBdAmortizacion(bdAmortizacion);
											cronogramaCreditoComp.setBdInteres(bdInteresCuota);
											cronogramaCreditoComp.setBdCuotaMensual(bdCuotaMensual);
											cronogramaCreditoComp.setBdAportes(bdAportes);
											cronogramaCreditoComp.setBdTotalCuotaMensual(bdCuotaMensual.add(bdAportes));
											listaCronogramaCreditoComp.add(cronogramaCreditoComp);

											
						// Agregando el tipo de Concepto - "Amortización"
											cronogramaCredito = new CronogramaCredito();
											cronogramaCredito.setId(new CronogramaCreditoId());
											cronogramaCredito.setIntNroCuota(i + 1);
											cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
											cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
											cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
											if (i == 0) {
												bdSaldoMontoCapital = bdMontoTotalSolicitado;
											} else {
												bdSaldoMontoCapital = bdSaldoTemp;
											}
											bdSaldoTemp = bdSaldo;
											cronogramaCredito.setBdMontoCapital(bdSaldoMontoCapital);
											if (i + 1 == intNroCuotas) {
												cronogramaCredito.setBdMontoConcepto(bdSaldoMontoCapital);
											} else {
												cronogramaCredito.setBdMontoConcepto(i + 1 == intNroCuotas ? 
												bdAmortizacion.add(bdMontoTotalSolicitado.subtract(bdAmortizacion)): bdAmortizacion);
											}
											Date fechaVenc = new Date();
											fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();
											cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
											cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime()).toString()));
											cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
											// CGD-12.11.2013 - nuevos atributos
											cronogramaCredito.setBdAmortizacionView(cronogramaCreditoComp.getBdAmortizacion());
											cronogramaCredito.setBdAportesView(cronogramaCreditoComp.getBdAportes());
											cronogramaCredito.setBdInteresView(cronogramaCreditoComp.getBdInteres());
											cronogramaCredito.setBdSaldoCapitalView(cronogramaCreditoComp.getBdSaldoCapital());
											Calendar clEnvio= Calendar.getInstance();
											clEnvio = (StringToCalendar(cronogramaCreditoComp.getStrFechaEnvio()));
											cronogramaCredito.setTsFechaEnvioView(new Timestamp(clEnvio.getTime().getTime()));
											listaCronogramaCredito.add(cronogramaCredito);

						// Agregando el tipo de Concepto - "Interés"
											cronogramaCredito = new CronogramaCredito();
											cronogramaCredito.setId(new CronogramaCreditoId());
											cronogramaCredito.setIntNroCuota(i + 1);
											cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
											cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
											cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_INTERES);
											cronogramaCredito.setBdMontoConcepto(bdInteresCuota);
											cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
											cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime())));
											cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
											// CGD-12.11.2013 - nuevos atributos
											cronogramaCredito.setBdAmortizacionView(cronogramaCreditoComp.getBdAmortizacion());
											cronogramaCredito.setBdAportesView(cronogramaCreditoComp.getBdAportes());
											cronogramaCredito.setBdInteresView(cronogramaCreditoComp.getBdInteres());
											cronogramaCredito.setBdSaldoCapitalView(cronogramaCreditoComp.getBdSaldoCapital());
											Calendar clEnvio2= Calendar.getInstance();
											clEnvio2 = (StringToCalendar(cronogramaCreditoComp.getStrFechaEnvio()));
											cronogramaCredito.setTsFechaEnvioView(new Timestamp(clEnvio2.getTime().getTime()));
											listaCronogramaCredito.add(cronogramaCredito);
										}

										bdTotalCuotaMensual = bdCuotaFinal.add(bdAportes);
//										Boolean bln90Por = Boolean.FALSE;

										if (listaCapacidadCreditoComp != null && !listaCapacidadCreditoComp.isEmpty()) {
											if (listaCapacidadCreditoComp.size() == 1) {
												for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
												capacidadCreditoComp.getCapacidadCredito().setBdCuotaFija(bdTotalCuotaMensual);
												}
											}else{
												// en el caso que existan mas capacidades
											}
										}

										// Se se calcula y definen los valores de: strPorcInteres ,  PorcentajeGravamen, strPorcAportes, 
//										strPorcInteres = "" + bdPorcentajeInteres.divide(	BigDecimal.ONE, 2,RoundingMode.HALF_UP);
										// seteamos ultimos valores
//										beanExpedienteCredito.setBdPorcentajeInteres(new BigDecimal(strPorcInteres));
//										beanExpedienteCredito.setBdPorcentajeGravamen(beanCredito.getBdTasaSeguroDesgravamen());
//										beanExpedienteCredito.setBdPorcentajeAporte(new BigDecimal(strPorcAportes));
//										beanExpedienteCredito.setBdMontoTotal(bdMontoPrestamo);
//										beanExpedienteCredito.setBdMontoGravamen(bdPorcSeguroDesgravamen);
//										beanExpedienteCredito.setBdMontoAporte(bdTotalDstos); // % de monto solicitado
//										beanExpedienteCredito.setIntNumeroCuota(intNroCuotas);
										
//										blnSeGeneroCronogramaCorrectamente = false;
										Boolean blnValidaMontosCap = Boolean.FALSE;
										blnValidaMontosCap = validarMontosCapacidadesCredito(bdTotalCuotaMensual, listaCapacidadCreditoComp);

										if(blnValidaMontosCap){
//											msgTxtCuotaMensual = "";
											expedienteCredito.setListaCronogramaCredito(listaCronogramaCredito);
//											blnEvaluacionCredito = true;
										}else{
//											blnEvaluacionCredito = true;
//											listaCronogramaCreditoComp.clear();
//											listaCronogramaCredito.clear();
										}
//									}else{
//										blnSeGeneroCronogramaCorrectamente = true;
//										log.error("El creditoInteres.getIntParaTipoSocio() ---> "+creditoInteres.getIntParaTipoSocio());
//										strPorcInteres = "0.00";
//									}
//								}
//							}else{
//								log.error("creditoInteres.getIntMesMaximo() es nulo --> "+ creditoInteres.getIntMesMaximo());
//							}
//						}else{
//							log.error("NO HAY NADA EN creditoSeleccionado.getListaCreditoInteres() "+ creditoSeleccionado.getListaCreditoInteres().size());
//						}
//					}
//				}
//			} else {
////				strPorcInteres = "0.00";
//				log.error("NO HAY NADA EN creditoSeleccionado.getListaCreditoInteres() "+ creditoSeleccionado.getListaCreditoInteres().size());
//			}
		} catch (Exception e) {
			log.error("Error BusinessException en generarCronograma ---> "+e);
			e.printStackTrace();
		}
		return listaCronogramaCredito;
	}
	
	/**
	 * Calcula la fecha del ler Envio y 1er Vencimiento.
	 * Toma en cuenta la existencia de salto al siguiente mes.
	 * @param estructuraDetalle
	 * @param fecHoy
	 * @return listaEnvioVencimiento (0, fec1erEnvio)(1, fec1erVenc);
	 */
	public String calcular1raFechaVencimiento(EstructuraDetalle estructuraDetalle, String strFechaRegistro, SocioComp beanSocioComp, List<CapacidadCreditoComp> listaCapacidadCreditoComp) {
//		Calendar fecEnvioTemp = Calendar.getInstance();
//		String miFechaPrimerVenc = null;
//		Calendar fec1erVenc  = Calendar.getInstance();
		Calendar fecVenc1 	 = Calendar.getInstance();
//		Calendar fecVenc2 	 = Calendar.getInstance();
//		Calendar fecVenc3 	 = Calendar.getInstance();
		//Calendar fec1erEnvio = Calendar.getInstance();
//		List listaEnvioVencimiento = new ArrayList();

		Calendar miFecha = Calendar.getInstance();		
		CapacidadCreditoComp capacidadMaximoEnvio =null;		
		String strFecha1erVencimiento = "";
		try {
			miFecha.clear();
			int intFechRegDia = new Integer(strFechaRegistro.substring(0,2));
			int intFechRegMes = new Integer(strFechaRegistro.substring(3,5))-1;
			int intFechRegAno = new Integer(strFechaRegistro.substring(6,10));
			
			// En la clase calendar enero es 0
//			if(intFechRegMes == 1){
//				intFechRegMes = 0;
//			}
			
			miFecha.set(intFechRegAno, intFechRegMes, intFechRegDia);
			//Integer intPeridoEnvio = 0;		
			capacidadMaximoEnvio = recuperarPerdiodoUltimoEnvio(listaCapacidadCreditoComp, beanSocioComp);			
			// SI NO HAY ENVIOS para todas las u.e. del soccio
			if(capacidadMaximoEnvio == null){
				// vencimiento
				//Date dtFechaVenc = new Date();
				Calendar clFechaVencTemp = Calendar.getInstance();
				clFechaVencTemp.setTime(Constante.sdf.parse(strFechaRegistro));
				clFechaVencTemp = getUltimoDiaDelMesCal(clFechaVencTemp);
				fecVenc1.clear();
				fecVenc1= clFechaVencTemp;
				
				if ((new Integer(strFechaRegistro.substring(0,2))) > estructuraDetalle.getIntDiaEnviado()) {
					fecVenc1.add(Calendar.MONTH, 1);
					fecVenc1 = getUltimoDiaDelMesCal(fecVenc1);
				}
				if(estructuraDetalle.getIntSaltoEnviado().compareTo(2)==0){
					fecVenc1.add(Calendar.MONTH, 1);
					fecVenc1 = getUltimoDiaDelMesCal(fecVenc1);
				}
			}else{	
				// existe envios
				// vencimiento
				int intAnno = Integer.parseInt(capacidadMaximoEnvio.getSocioEstructura().getIntUltimoPeriodo().toString().substring(0,4));
				int intMes = Integer.parseInt(capacidadMaximoEnvio.getSocioEstructura().getIntUltimoPeriodo().toString().substring(4,6));
				int intDia = 15;
				String strFechaPeriodo = "";
				strFechaPeriodo = intDia+"/"+intMes+"/"+intAnno;
				
				Calendar clFechaVencTemp = Calendar.getInstance();
				clFechaVencTemp.setTime(Constante.sdf.parse(strFechaPeriodo));
				clFechaVencTemp.add(Calendar.MONTH, 1);
				
				clFechaVencTemp = getUltimoDiaDelMesCal(clFechaVencTemp);
				fecVenc1.clear();
				fecVenc1= clFechaVencTemp;
			}
			strFecha1erVencimiento = Constante.sdf.format(fecVenc1.getTime());
		} catch (NumberFormatException e) {
			log.error("Error NumberFormatException en calcular1raFechaEnvioVencimiento ---> "+e);
			e.printStackTrace();
		} catch (ParseException e) {
			log.error("Error ParseException en calcular1raFechaEnvioVencimiento---> "+e);
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Error general en calcular1raFechaEnvioVencimiento---> "+e);
		}
		return strFecha1erVencimiento;
	}

	/**
	 * Recupera el maximo periodo de los envios realizados por la unidades ejecutoras existenrtes en la solicitud.
	 * @return
	 */
	public CapacidadCreditoComp recuperarPerdiodoUltimoEnvio(List<CapacidadCreditoComp> listaCapacidadCreditoComp, SocioComp socioComp){
		CapacidadCreditoComp capacidadMaximoEnvio = null;
		//List<Integer> lstUltimosPeridos=null;
		List<CapacidadCreditoComp> lstCapacidadesComp = null;
		try {
			PlanillaFacadeRemote planillaFacade = (PlanillaFacadeRemote) EJBFactory.getRemote(PlanillaFacadeRemote.class);
			
			if(listaCapacidadCreditoComp != null && !listaCapacidadCreditoComp.isEmpty()){
				lstCapacidadesComp = new ArrayList<CapacidadCreditoComp>();
				for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
					EstructuraId estructuraId = new EstructuraId();
					Integer intPeriodoRecuperado= 0;
					Integer intTipoSocio = 0;
					Integer intModalidad = 0;
					Integer intEmpresa = 0;
					
					intEmpresa = capacidadCreditoComp.getSocioEstructura().getIntEmpresaUsuario();
					intTipoSocio = socioComp.getSocio().getSocioEstructura().getIntTipoSocio();
					intModalidad = socioComp.getSocio().getSocioEstructura().getIntModalidad();
					estructuraId.setIntCodigo(capacidadCreditoComp.getSocioEstructura().getIntCodigo());
					estructuraId.setIntNivel(capacidadCreditoComp.getSocioEstructura().getIntNivel());
					
					intPeriodoRecuperado = planillaFacade.getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioM(intEmpresa, estructuraId, intTipoSocio, intModalidad);
					if(intPeriodoRecuperado != null){
						capacidadCreditoComp.getSocioEstructura().setIntUltimoPeriodo(intPeriodoRecuperado);
						lstCapacidadesComp.add(capacidadCreditoComp);
					}
				}
				
				if(lstCapacidadesComp != null && !lstCapacidadesComp.isEmpty()){
					int intTam = lstCapacidadesComp.size();
					capacidadMaximoEnvio = new CapacidadCreditoComp();
					capacidadMaximoEnvio = lstCapacidadesComp.get(0);
					for (int j = 0; j < intTam; j++) {
						if (lstCapacidadesComp.get(j).getSocioEstructura().getIntUltimoPeriodo() > capacidadMaximoEnvio.getSocioEstructura().getIntUltimoPeriodo()) {
							capacidadMaximoEnvio = lstCapacidadesComp.get(j);
						}						
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en recuperarPerdiodoUltimoEnvio ---> "+e);
		}
		return capacidadMaximoEnvio;
		
	}
	
	/**
	 * Valida la suma de las cuotas fijas de las capacidadaes sean menor al 90/100
	 * Y la suma de estas se igual a la cuotra mensual calculada.
	 * Recalcula los montos de las cuotas fijas.
	 * Retorna TRUE cuando pasa y FALSE cuando incumple.
	 * @param listaCapacidadComp
	 * @param bdMontoCuotaFija
	 * @return
	 */
	public Boolean validarMontosCapacidadesCredito(BigDecimal bdCuotaMensualCalc, List<CapacidadCreditoComp> listaCapacidadCreditoComp){
		Boolean blnContinua = Boolean.TRUE;
//		Boolean blnEsElUltimo = Boolean.FALSE;
		Integer intTamannoLista = 0;
		Integer intContador = 0;
		Integer intRegistrosOk = 0; // nro de capapcidades con cuota fija ingresada
		Integer intValor = 0;
		
		try {
			if( listaCapacidadCreditoComp != null && ! listaCapacidadCreditoComp.isEmpty() && bdCuotaMensualCalc   != null && bdCuotaMensualCalc   != BigDecimal.ZERO ){
//				msgTxtEstrucActivoRepetido = "";
				
				for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
					if(capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija() != null  
						&& capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija().compareTo(BigDecimal.ZERO)!=0){
						intRegistrosOk ++;
					}
				}

				intTamannoLista = listaCapacidadCreditoComp.size();
				intValor = intTamannoLista - intRegistrosOk;
	
				// 1. Validamos que no existan nulos ni ceros
				if(intValor.compareTo(new Integer(1))==0){
					recalculoCuotasFijasCapacidades(listaCapacidadCreditoComp, bdCuotaMensualCalc);
				}else{
					// base
					for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
						if(capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija() == null  
							|| capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija().compareTo(BigDecimal.ZERO)==0){
							log.info("Registrar valor de Cuota Fija para cada Capacidad de Crédito. ");
							//blnAprueba = Boolean.FALSE;
							blnContinua = Boolean.FALSE;
							intContador++;
							break;	
						}
					}	
				}

				// 2. Validamos que cada cuota fija sea menor al 0.9 de capacidad 
				if(blnContinua){
					for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
						BigDecimal bdCapacidad		 = BigDecimal.ZERO;
						BigDecimal bdCapacidad90Porc = BigDecimal.ZERO;
						BigDecimal bdCapacidadCF = BigDecimal.ZERO;
						
						bdCapacidad = capacidadCreditoComp.getCapacidadCredito().getBdCapacidadPago();
						bdCapacidad90Porc = bdCapacidad.multiply(new BigDecimal(0.9));
						bdCapacidad90Porc = bdCapacidad90Porc.divide(BigDecimal.ONE,2, RoundingMode.HALF_UP);
						bdCapacidadCF = capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija();
						
						if(bdCapacidad90Porc.compareTo(bdCapacidadCF)<0){
							log.info("La cuota Fija (S/. "+ bdCapacidadCF+"  ) excede al 90% de la Capacidad (S/. "+ bdCapacidad90Porc +" ). ");
							//blnAprueba = Boolean.FALSE;
							blnContinua = Boolean.FALSE;
							break;
						}
					}
					
					// 3. Validamos la suma de las Cuotas Fijas vs la cuota mensual calculada
					if(blnContinua){
						BigDecimal bdSumaCuotasFijas = BigDecimal.ZERO;
						
						for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
							bdSumaCuotasFijas  = bdSumaCuotasFijas.add(capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija());
							bdSumaCuotasFijas = bdSumaCuotasFijas.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
						}

						if(!bdSumaCuotasFijas.equals(bdCuotaMensualCalc)){
							log.info("La Sumatoria de las Cuotas Fijas (S/. "+ bdSumaCuotasFijas + " ) debe ser igual al Valor de la Cuota Mensual Calculada (S/. "+bdCuotaMensualCalc+"). ");
							blnContinua = Boolean.FALSE;
						}
					}	
				}
			}
		} catch (Exception e) {
			log.error("Error envalidarCuotaFijaVSCapacidad --->  "+e);
		}
		return blnContinua;
	}
	
	/**
	 * Recalcula el monto de las cuotas fijas de cada capacidad 
	 * En el caso que exista n-1 capacidades con Cuota Fija ingresada.
	 */
	public void recalculoCuotasFijasCapacidades(List<CapacidadCreditoComp> listaCapacidadCreditoComp, BigDecimal bdTotalCuotaMensual){
		BigDecimal bdMontoCuotaFinal = BigDecimal.ZERO;
		BigDecimal bdSumatoriaCuotasFijas = BigDecimal.ZERO;
		List<CapacidadCreditoComp> listaTempCapComp = new ArrayList<CapacidadCreditoComp>();
		List<CapacidadCreditoComp> listaTempCapFinal = new ArrayList<CapacidadCreditoComp>();
		
		try {
			if(listaCapacidadCreditoComp != null && !listaCapacidadCreditoComp.isEmpty()){
				for (CapacidadCreditoComp capacidadComp : listaCapacidadCreditoComp) {
					if(capacidadComp.getCapacidadCredito().getBdCuotaFija() != null
						&& capacidadComp.getCapacidadCredito().getBdCuotaFija().compareTo(BigDecimal.ZERO)!=0){
						bdSumatoriaCuotasFijas = bdSumatoriaCuotasFijas.add(capacidadComp.getCapacidadCredito().getBdCuotaFija());
					}	
				}
				
				bdMontoCuotaFinal = bdTotalCuotaMensual.subtract(bdSumatoriaCuotasFijas);
				bdMontoCuotaFinal = bdMontoCuotaFinal.divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP);
				listaTempCapComp = listaCapacidadCreditoComp;
				
				for (CapacidadCreditoComp capacidadComp : listaTempCapComp) {
					if(capacidadComp.getCapacidadCredito().getBdCuotaFija() == null
						|| capacidadComp.getCapacidadCredito().getBdCuotaFija().compareTo(BigDecimal.ZERO)==0){
						
						capacidadComp.getCapacidadCredito().setBdCuotaFija(bdMontoCuotaFinal);
						bdSumatoriaCuotasFijas = bdSumatoriaCuotasFijas.add(capacidadComp.getCapacidadCredito().getBdCuotaFija());
						listaTempCapFinal.add(capacidadComp);
					}else{	
						BigDecimal bdMontoFormateado = BigDecimal.ZERO;
						bdMontoFormateado = capacidadComp.getCapacidadCredito().getBdCuotaFija();
						bdMontoFormateado = bdMontoFormateado.divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP);
						capacidadComp.getCapacidadCredito().setBdCuotaFija(bdMontoFormateado);
						
						listaTempCapFinal.add(capacidadComp);
					}
				}
				listaCapacidadCreditoComp.clear();
				listaCapacidadCreditoComp = listaTempCapFinal;
			}
		} catch (Exception e) {
			log.error("Error en recalculoCuotasFijasCapacidades "+e);
		}
	}
	
	/**
	 * Retorna la fecha del ultimo dia del mes
	 * @param Calendar
	 * @return Date
	 */
	public Calendar getUltimoDiaDelMesCal(Calendar fecha) {
		fecha.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH),
				fecha.getActualMaximum(Calendar.DAY_OF_MONTH),
				fecha.getMaximum(Calendar.HOUR_OF_DAY),
				fecha.getMaximum(Calendar.MINUTE),
				fecha.getMaximum(Calendar.SECOND));
		return fecha;
	}
    
	public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
		return (int)( (dtFechaFin.getTime() - dtFechaInicio.getTime()) / (1000 * 60 * 60 * 24) );
	}
    
    private static Date convertirTimestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
    
    
    /**
     * Agregado jchavez 24.04.2014
     * Grabacion del giro de prestamo por Fondo Fijo, Cheque y transferencia (funciona igual que el giro normal pero sin la validacion
     * de monto que exceda al ya configurado)
     * @param expedienteCredito
     * @return
     * @throws BusinessException
     */
    public ExpedienteCredito grabarGiroPrestamoPorTesoreria(ExpedienteCredito expedienteCredito)throws BusinessException{
		EstructuraDetalle estructuraDetalle = null;
		CuentaId cuentaId = new CuentaId();
		EstructuraId estructuraId = new EstructuraId();
		List<CuentaIntegrante> lstCuentaIntegrante = null;
		List<SocioEstructura> lstSocioEstructura = null;
		Integer intCasoPlanilla = 1;
		Calendar miCal = Calendar.getInstance();
		SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
		List<CuentaConcepto> lstCuentaConcepto = null;
		List<ConceptoPago> lstConceptoPago = null;
		List<ConceptoPago> lstConceptoPagoGenerado = new ArrayList<ConceptoPago>();
		Movimiento movGenerado = null;
		List<CronogramaCredito> listaCronogramaServicio = null;
		List<Cronograma> listaCronogramaMovimiento = null;
		BigDecimal bdMontoNuevoSaldoCredito = null;
		
		BigDecimal bdMontoAntiguoSaldoCredito = BigDecimal.ZERO;
		BigDecimal bdMontoCancelacionCredito = BigDecimal.ZERO;
		try{
			EgresoFacadeRemote egresoFacade = (EgresoFacadeRemote) EJBFactory.getRemote(EgresoFacadeRemote.class);
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			EstructuraFacadeRemote estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			//Seteamos Pk de la cuenta 			
			cuentaId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
			cuentaId.setIntCuenta(expedienteCredito.getId().getIntCuentaPk());	
			
			// Recupera CSO_CONFCREDITOS porempresa, tipo crédito e item crédito.
			expedienteCredito.getCuenta().setCredito(obtenerCreditoPorExpediente(expedienteCredito));
			
			Egreso egreso = expedienteCredito.getEgreso();
				
			log.info("---- GiroPrestamoService.grabarGiroPrestamo ----");
			log.info("Expediente credito obtenido ---> "+expedienteCredito);
			log.info("Expediente credito a cancelar  ---> "+expedienteCredito.getExpedienteCreditoCancelacion());
			log.info("Egreso obtenido ---> "+egreso);
			log.info("Expediente credito obtenido ---> "+expedienteCredito.getExpedienteCreditoCancelacion());
			
			//Obtenemos cancelacion credito... jchavez 24.06.2014
			List<CancelacionCredito> cancelaCred = boCancelacionCredito.getListaPorExpedienteCredito(expedienteCredito);
			log.info("Cancelacion Credito : "+cancelaCred);
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				log.info("Egreso detalle obtenidos ---> "+egresoDetalle);
			}

			log.info("Libro Diario obtenido ---> "+egreso.getLibroDiario());
			for(LibroDiarioDetalle libroDiarioDetalle : egreso.getLibroDiario().getListaLibroDiarioDetalle()){
				log.info("Libro Diario Detalle obtenidos ---> "+libroDiarioDetalle);
			}
			//Se procede a la grabación del Egreso, Egreso Detalle, Libro Diario y Libro Diario Detalle
			egreso.setBlnEsGiroPorSedeCentral(true);
			egreso = egresoFacade.grabarEgresoParaGiroPrestamo(egreso);
			if (egreso.getIntErrorGeneracionEgreso().equals(1)) {
				expedienteCredito.getEgreso().setIntErrorGeneracionEgreso(1);
				expedienteCredito.getEgreso().setStrMsgErrorGeneracionEgreso(egreso.getStrMsgErrorGeneracionEgreso());
				return expedienteCredito;
			}
		
			//Generación y grabación de Cronograma Movimiento (CMO_CRONOGRAMACREDITO)
			
			// 1. Recupera los cronogramas de crédito segun expediente crédito.
			expedienteCredito.setListaCronogramaCredito(boCronogramaCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId()));
			// Si la fecha de giro y la fecha de generacion del cronograma son iguales, 
			if (expedienteCredito.getListaCronogramaCredito()==null || expedienteCredito.getListaCronogramaCredito().isEmpty()) {
				expedienteCredito.getEgreso().setIntErrorGeneracionEgreso(1);
				expedienteCredito.getEgreso().setStrMsgErrorGeneracionEgreso("El cronograma credito retorna nulo");
				return expedienteCredito;
			}
			
			log.info("Fecha cronograma generado en la Solicitud: "+sdfFecha.format(expedienteCredito.getListaCronogramaCredito().get(0).getTsFechaEnvioView()));
			log.info("Fecha cronograma generado en e Giro: "+sdfFecha.format(miCal.getTimeInMillis()));
			if (sdfFecha.format(expedienteCredito.getListaCronogramaCredito().get(0).getTsFechaEnvioView()).equals(sdfFecha.format(miCal.getTimeInMillis()))) {
				listaCronogramaServicio = new ArrayList<CronogramaCredito>();
				listaCronogramaServicio.addAll(expedienteCredito.getListaCronogramaCredito());
				listaCronogramaMovimiento = generarCronogramaMovimiento(expedienteCredito);
				for(Cronograma cronograma : listaCronogramaMovimiento){
					log.info("Cronograma Movimiento generado ---> "+cronograma);
					conceptoFacade.grabarCronograma(cronograma);
				}
			}else { // Si la fecha de giro y la fecha de generacion del cronograma son diferentes, 
				// 2. Se anula el cronograma generado en la solicitud.
				if (expedienteCredito.getListaCronogramaCredito()!=null && !expedienteCredito.getListaCronogramaCredito().isEmpty()) {
					for (CronogramaCredito cronogramaAnterior : expedienteCredito.getListaCronogramaCredito()) {
						cronogramaAnterior.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						boCronogramaCredito.modificar(cronogramaAnterior);
					}
				}
				// 3. Se regenera el cronograma de crédito.				
				lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaId);
				if (lstCuentaIntegrante!=null && !lstCuentaIntegrante.isEmpty()) {
					for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
						lstSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(cuentaIntegrante.getId().getIntPersonaIntegrante(), cuentaIntegrante.getId().getIntPersEmpresaPk());
						if (lstSocioEstructura!=null && !lstSocioEstructura.isEmpty()) {
							for (SocioEstructura socioEstructura : lstSocioEstructura) {
								estructuraId.setIntNivel(socioEstructura.getIntNivel());
								estructuraId.setIntCodigo(socioEstructura.getIntCodigo());
								estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYCasoYTipoSocioYModalidad(estructuraId, intCasoPlanilla, socioEstructura.getIntTipoSocio(), socioEstructura.getIntModalidad());
								expedienteCredito.setListaCronogramaCreditoRegenerado(regenerarCronograma(estructuraDetalle, expedienteCredito));
								break;
							}
						}
					}
				}
				// 4. Grabamos el cronograma credito regenerado en servicio.
				Integer intTotalCuotasAPagar = 0;
				listaCronogramaServicio = regenerarCronogramaServicio(expedienteCredito);
				List<CronogramaCredito> listaCronogramaServicioRegenerado = new ArrayList<CronogramaCredito>();
				for (CronogramaCredito cronogramaCredito : listaCronogramaServicio) {
					log.info("Cronograma Servicio regenerado ---> "+cronogramaCredito);
					listaCronogramaServicioRegenerado.add(boCronogramaCredito.grabar(cronogramaCredito));
					//Obtenemos el total de cuotas a pagar
					if (intTotalCuotasAPagar < cronogramaCredito.getIntNroCuota()) intTotalCuotasAPagar = cronogramaCredito.getIntNroCuota();
				}
				// 5. Modificamos el numero de cuotas en el expediente credito
				expedienteCredito.setIntNumeroCuota(intTotalCuotasAPagar);
				boExpedienteCredito.modificar(expedienteCredito);
				
				// 6. Grabamos el cronograma regenerado en movimiento.
				listaCronogramaMovimiento = regenerarCronogramaMovimiento(listaCronogramaServicioRegenerado);
				for(Cronograma cronograma : listaCronogramaMovimiento){
					log.info("Cronograma Movimiento regenerado ---> "+cronograma);
					conceptoFacade.grabarCronograma(cronograma);
				}
			}
			
			//Generación y grabación de Expediente Movimiento (CMO_EXPEDIENTECREDITO)
			Expediente expedienteMovimiento = generarExpedienteMovimiento(expedienteCredito, listaCronogramaServicio);
			log.info("Expediente Movimiento generado ---> "+expedienteMovimiento);
			conceptoFacade.grabarExpediente(expedienteMovimiento);
			
			//Agregado 14.04.2014 
			if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
				if (expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()!=null) {
					List<Cronograma> cronogCred = null;			
					log.info("Expediente credito a cancelar ----> "+expedienteCredito.getExpedienteCreditoCancelacion());
					//modificamos el expediente credito de movimiento
					Expediente expMov = new Expediente();
					expMov.setId(new ExpedienteId());
					expMov.getId().setIntPersEmpresaPk(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
					expMov.getId().setIntCuentaPk(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntCuentaPk());
					expMov.getId().setIntItemExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente());
					expMov.getId().setIntItemExpedienteDetalle(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					expMov = conceptoFacade.getExpedientePorPK(expMov.getId());
					//jachevz 25.06.2014 guardamos el antiguo monto de saldo credito
					bdMontoAntiguoSaldoCredito = expMov.getBdSaldoCredito();

					//23.06.2014 jchavez El monto a cancelar es el monto que se encuentra en la tabla de SERVICIO.CANCELACION_CREDITO
					if (cancelaCred!=null && !cancelaCred.isEmpty()) {
						bdMontoCancelacionCredito = cancelaCred.get(0).getBdMontoCancelado();
						expMov.setBdSaldoCredito(expMov.getBdSaldoCredito().subtract(cancelaCred.get(0).getBdMontoCancelado())); //SU SALDO - MONTO DE CANCELACREDITO
					}
					bdMontoNuevoSaldoCredito = expMov.getBdSaldoCredito();

					conceptoFacade.modificarExpediente(expMov);
					//modificamos cronograma movimiento, saldos a cero
					cronogCred = conceptoFacade.getListaCronogramaPorPkExpediente(expMov.getId());
					if (cronogCred!=null && !cronogCred.isEmpty()) {
						for (Cronograma cronogramaMov : cronogCred) {
							//jchavez 24.06.2014
							//hasta q se acabe el saldo de cancelacion credito
							if (cronogramaMov.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
								&& cronogramaMov.getIntParaTipoConceptoCreditoCod().compareTo(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION)==0
								&& cronogramaMov.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)==1){
									if (cronogramaMov.getBdSaldoDetalleCredito().compareTo(bdMontoCancelacionCredito)==-1) {
										bdMontoCancelacionCredito = bdMontoCancelacionCredito.subtract(cronogramaMov.getBdSaldoDetalleCredito());
										cronogramaMov.setBdSaldoDetalleCredito(BigDecimal.ZERO);
										conceptoFacade.modificarCronograma(cronogramaMov);										
									}else{
										cronogramaMov.setBdSaldoDetalleCredito(cronogramaMov.getBdSaldoDetalleCredito().subtract(bdMontoCancelacionCredito));
										conceptoFacade.modificarCronograma(cronogramaMov);
										bdMontoCancelacionCredito = BigDecimal.ZERO;
										break;
									}
							}					
						}
					}
				}				
			}
						
			//Generación y grabación de Movimiento (CMO_MOVIMIENTOCTACTE)
			Movimiento movimiento = generarMovimiento(expedienteCredito, egreso);
			log.info("Movimiento generado ---> "+movimiento);
			conceptoFacade.grabarMovimiento(movimiento);
			//Agregado 14.04.2014
			//Generacion del movimiento del prestamo cancelado..
			if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
				if (expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()!=null) {
					//1. Movimiento del saldo
					Movimiento movExpCancelado = new Movimiento();
					movExpCancelado.setTsFechaMovimiento(expedienteCredito.getEgreso().getTsFechaProceso());
					movExpCancelado.setIntPersEmpresa(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
					movExpCancelado.setIntCuenta(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntCuentaPk());
					movExpCancelado.setIntPersPersonaIntegrante(expedienteCredito.getEgreso().getIntPersPersonaGirado());
					movExpCancelado.setIntItemCuentaConcepto(null);
					movExpCancelado.setIntItemExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente());
					movExpCancelado.setIntItemExpedienteDetalle(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					movExpCancelado.setIntParaTipoConceptoGeneral(Constante.PARAM_T_CONCEPTOGENERAL_AMORTIZACION_EXPEDIENTE);
					movExpCancelado.setIntParaTipoMovimiento(Constante.PARAM_T_MOVIMIENTOCTACTE_EGRESOCAJA);
					//Se graba el mismo que se grabo en el Egreso
					movExpCancelado.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
					movExpCancelado.setStrSerieDocumento(null);
					movExpCancelado.setStrNumeroDocumento(""+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()+"-"+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					movExpCancelado.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
					//saldos despues de la cancelacion del prestamo
					//jchavez 25.06.2014 va el monto que esta en cancelacion credito
					movExpCancelado.setBdMontoMovimiento( bdMontoAntiguoSaldoCredito);
					//jchavez 25.06.2014 va el saldo de la diferencia entre el monto saldo del expMov y el monto de cancela credito
					movExpCancelado.setBdMontoSaldo(bdMontoNuevoSaldoCredito);
					//egreso del prestamo nuevo
					movExpCancelado.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
					movExpCancelado.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
					
					movExpCancelado.setIntPersEmpresaUsuario(expedienteCredito.getEgreso().getIntPersEmpresaUsuario());
					movExpCancelado.setIntPersPersonaUsuario(expedienteCredito.getEgreso().getIntPersPersonaUsuario());
					conceptoFacade.grabarMovimiento(movExpCancelado);
					//2. Movimiento del interes
					Movimiento movExpCanceladoInteres = new Movimiento();
					movExpCanceladoInteres.setTsFechaMovimiento(expedienteCredito.getEgreso().getTsFechaProceso());
					movExpCanceladoInteres.setIntPersEmpresa(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
					movExpCanceladoInteres.setIntCuenta(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntCuentaPk());
					movExpCanceladoInteres.setIntPersPersonaIntegrante(expedienteCredito.getEgreso().getIntPersPersonaGirado());
					movExpCanceladoInteres.setIntItemCuentaConcepto(null);
					movExpCanceladoInteres.setIntItemExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente());
					movExpCanceladoInteres.setIntItemExpedienteDetalle(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					movExpCanceladoInteres.setIntParaTipoConceptoGeneral(Constante.PARAM_T_CONCEPTOGENERAL_INTERES);
					movExpCanceladoInteres.setIntParaTipoMovimiento(Constante.PARAM_T_MOVIMIENTOCTACTE_EGRESOCAJA);
					//Se graba el mismo que se grabo en el Egreso
					movExpCanceladoInteres.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
					movExpCanceladoInteres.setStrSerieDocumento(null);
					movExpCanceladoInteres.setStrNumeroDocumento(""+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()+"-"+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					movExpCanceladoInteres.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
					//interes despues de la cancelacion del prestamo
					movExpCanceladoInteres.setBdMontoMovimiento(expedienteCredito.getBdMontoInteresAtrasado());
					movExpCanceladoInteres.setBdMontoSaldo(null);
					//egreso del prestamo nuevo
					movExpCanceladoInteres.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
					movExpCanceladoInteres.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
					movExpCanceladoInteres.setIntPersEmpresaUsuario(expedienteCredito.getEgreso().getIntPersEmpresaUsuario());
					movExpCanceladoInteres.setIntPersPersonaUsuario(expedienteCredito.getEgreso().getIntPersPersonaUsuario());
					movExpCanceladoInteres = conceptoFacade.grabarMovimiento(movExpCanceladoInteres);
					
					//Agregado 15.04.2014
					ExpedienteId expCancelado = new ExpedienteId();
					expCancelado.setIntCuentaPk(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntCuentaPk());
					expCancelado.setIntPersEmpresaPk(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
					expCancelado.setIntItemExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente());
					expCancelado.setIntItemExpedienteDetalle(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					InteresCancelado canceladoUltimo = conceptoFacade.getMaxInteresCanceladoPorExpediente(expCancelado);
					//-------------------------------------------------------------------
					InteresCancelado interesCancelado = new InteresCancelado();
					interesCancelado.setId(new InteresCanceladoId());
					interesCancelado.getId().setIntCuentaPk(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntCuentaPk());
					//interesCancelado.getId().setIntItemCancelaInteres(expedienteCreditoViejoMovimiento.getId().get);
					interesCancelado.getId().setIntItemExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente());
					interesCancelado.getId().setIntItemExpedienteDetalle(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
					interesCancelado.getId().setIntItemMovCtaCte(movExpCanceladoInteres.getIntItemMovimiento());
					interesCancelado.getId().setIntPersEmpresaPk(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
					interesCancelado.setBdMontoInteres(expedienteCredito.getBdMontoInteresAtrasado());
					//jchavez 25.06.2014 - va el saldo del expediente antes del movimiento
					interesCancelado.setBdSaldoCredito(bdMontoAntiguoSaldoCredito);
					interesCancelado.setBdTasa(expedienteCredito.getExpedienteCreditoCancelacion().getBdPorcentajeInteres());
					
					interesCancelado.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					//Parametro 159 - 3 (CAJA)
					interesCancelado.setIntParaTipoFormaPago(Constante.PARAM_T_FORMADEPAGO_CAJA);
					//
					Calendar calendar = Calendar.getInstance();
					//jchavez 26.06.2014 caso ultimo interes calculado, su monto sea cero
					if (canceladoUltimo.getBdMontoInteres().equals(BigDecimal.ZERO)) {
						calendar.setTimeInMillis(canceladoUltimo.getTsFechaInicio().getTime());
						interesCancelado.setTsFechaInicio(new Timestamp(calendar.getTimeInMillis()));
					}else{
						calendar.setTimeInMillis(canceladoUltimo.getTsFechaMovimiento().getTime());
				        calendar.add(Calendar.DATE, 1);
						interesCancelado.setTsFechaInicio(new Timestamp(calendar.getTimeInMillis()));
					}
					//
					Calendar fecHoy = Calendar.getInstance();
					Date dtHoy = fecHoy.getTime();
					//
					interesCancelado.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
					interesCancelado.setIntDias(obtenerDiasEntreFechas(convertirTimestampToDate(new Timestamp(calendar.getTimeInMillis())), dtHoy));
					interesCancelado=conceptoFacade.grabarInteresCancelado(interesCancelado);
				}				
			}		
			
			//Generación y grabación de Estado Concepto Cuenta Expediente (CMO_ESTADOCPTOCEXPE)
			EstadoExpediente estadoExpediente = generarEstadoExpedienteGiro(expedienteMovimiento);
			log.info("Estado Expediente Generado ---> "+estadoExpediente);
			conceptoFacade.grabarEstado(estadoExpediente);
			//Agregado 14.04.2014 
			//Generacion del estado para el prestamo cancelado
			if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
				//AGREGAR VALIDACION SALDO A 0 si el saldo de cancelacion credito y el saldo movimiento del exp cancelado son =
				if (bdMontoNuevoSaldoCredito.compareTo(BigDecimal.ZERO)==0) {
					if (expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()!=null) {
						EstadoExpediente estadoExpedienteCancelado = new EstadoExpediente();
						estadoExpedienteCancelado.setId(new EstadoExpedienteId());
						//Seteando valores...
						estadoExpedienteCancelado.getId().setIntEmpresaEstado(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
						estadoExpedienteCancelado.setIntEmpresa(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntPersEmpresaPk());
						estadoExpedienteCancelado.setIntCuenta(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntCuentaPk());
						estadoExpedienteCancelado.setIntItemCuentaConcepto(null);
						estadoExpedienteCancelado.setIntItemExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente());
						estadoExpedienteCancelado.setIntItemDetExpediente(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
						estadoExpedienteCancelado.setIntParaEstadoExpediente(2); //Estado Vigente
						estadoExpedienteCancelado.setIntIndicadorEntrega(null);
						conceptoFacade.grabarEstado(estadoExpedienteCancelado);
					}
				}
			}		
			
			//Si el préstamo genera aporte...
			if (expedienteCredito.getBdMontoAporte()!=null && expedienteCredito.getBdMontoAporte().compareTo(BigDecimal.ZERO)>0) {
				//1. Vamos a CuentaConcepto y filtramos por cuenta
				Integer intNroRegistrosConceptoPago = 0;
				lstCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuentaId);
				if (lstCuentaConcepto!=null && !lstCuentaConcepto.isEmpty()) {
					for (CuentaConcepto cuentaConcepto : lstCuentaConcepto) {
						if (cuentaConcepto.getListaCuentaConceptoDetalle()!=null && !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()) {
							for (CuentaConceptoDetalle cuentaConceptoDetalle : cuentaConcepto.getListaCuentaConceptoDetalle()) {
								if (cuentaConceptoDetalle.getIntParaTipoConceptoCod().equals(Constante.PARAM_T_TIPOCUENTA_APORTES) && cuentaConceptoDetalle.getTsFin()==null) {
									//1.1 Modificando el Saldo de Aporte en Cuenta Concepto
									cuentaConcepto.setBdSaldo(cuentaConcepto.getBdSaldo().add(expedienteCredito.getBdMontoAporte()));
									conceptoFacade.modificarCuentaConcepto(cuentaConcepto);	
									//1.2 Modificando el Saldo de Aporte en Cuenta Concepto Detalle
									cuentaConceptoDetalle.setBdSaldoDetalle(cuentaConceptoDetalle.getBdSaldoDetalle().add(expedienteCredito.getBdMontoAporte()));
									conceptoFacade.modificarCuentaConceptoDetalle(cuentaConceptoDetalle);
									//1.3 Generar y grabar movimiento aporte
									Movimiento mov = new Movimiento();
									mov.setTsFechaMovimiento(expedienteCredito.getEgreso().getTsFechaProceso());
									mov.setIntParaTipoMovimiento(Constante.PARAM_T_MOVIMIENTOCTACTE_EGRESOCAJA);
									//Se graba el mismo que se grabo en el Egreso
									mov.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
									mov.setIntPersPersonaUsuario(expedienteCredito.getEgreso().getIntPersPersonaUsuario());
									mov.setIntPersEmpresaUsuario(expedienteCredito.getEgreso().getIntPersEmpresaUsuario());	
									mov.setIntPersEmpresa(expedienteCredito.getId().getIntPersEmpresaPk());
									mov.setIntCuenta(expedienteCredito.getCuenta().getId().getIntCuenta());
									mov.setIntItemCuentaConcepto(cuentaConceptoDetalle.getId().getIntItemCuentaConcepto());
									mov.setIntPersPersonaIntegrante(expedienteCredito.getEgreso().getIntPersPersonaGirado());
									mov.setIntParaTipoConceptoGeneral(Constante.PARAM_T_CONCEPTOGENERAL_AMORTIZACION_APORTE);
									mov.setIntItemExpediente(null);
									mov.setIntItemExpedienteDetalle(null);
									mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
									mov.setStrSerieDocumento(null);								
									mov.setStrNumeroDocumento(""+expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
									mov.setBdMontoMovimiento(expedienteCredito.getBdMontoAporte());
									mov.setBdMontoSaldo(cuentaConcepto.getBdSaldo());
									mov.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
									mov.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
									movGenerado = conceptoFacade.grabarMovimiento(mov);
									//1.4 Generando Concepto detalle a grabar
									//Obtenemos la cantidad de registros a grabar, dicvidiedo el monto aporte de expediente credito entre el monto moncepto de Cuenta Concepto Detalle.
									intNroRegistrosConceptoPago = expedienteCredito.getBdMontoAporte().divide(cuentaConceptoDetalle.getBdMontoConcepto(),0,RoundingMode.UP).intValue();
									log.info("Cantidad de registros a grabar: "+intNroRegistrosConceptoPago);
									//Generamos el Concepto Pago
									ConceptoPago cptoPagoGenerado = null;

									Integer intPeriodoGenerado = 0;
									lstConceptoPago = conceptoFacade.getUltimoCptoPagoPorCuentaConceptoDet(cuentaConceptoDetalle.getId());
									if (lstConceptoPago!=null && !lstConceptoPago.isEmpty()) {
										for (ConceptoPago conceptoPago : lstConceptoPago) {
											intPeriodoGenerado = aumentarPeriodoInicial(conceptoPago.getIntPeriodo());
											log.info("Periodo generado para la grabación: "+intPeriodoGenerado);
											for (int i = 1; i <= intNroRegistrosConceptoPago; i++) {
												cptoPagoGenerado = new ConceptoPago();
												cptoPagoGenerado.setId(new ConceptoPagoId());
												cptoPagoGenerado.getId().setIntPersEmpresaPk(conceptoPago.getId().getIntPersEmpresaPk());
												cptoPagoGenerado.getId().setIntCuentaPk(conceptoPago.getId().getIntCuentaPk());
												cptoPagoGenerado.getId().setIntItemCuentaConcepto(conceptoPago.getId().getIntItemCuentaConcepto());
												cptoPagoGenerado.getId().setIntItemCtaCptoDet(conceptoPago.getId().getIntItemCtaCptoDet());
												cptoPagoGenerado.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

												if (i!=intNroRegistrosConceptoPago) {
													cptoPagoGenerado.setIntPeriodo(intPeriodoGenerado);
													cptoPagoGenerado.setBdMontoPago(cuentaConceptoDetalle.getBdMontoConcepto());
													cptoPagoGenerado.setBdMontoSaldo(BigDecimal.ZERO);
												}else {
													cptoPagoGenerado.setIntPeriodo(intPeriodoGenerado);
													cptoPagoGenerado.setBdMontoPago(expedienteCredito.getBdMontoAporte().subtract(cuentaConceptoDetalle.getBdMontoConcepto().multiply(new BigDecimal(i-1))));
													cptoPagoGenerado.setBdMontoSaldo(cuentaConceptoDetalle.getBdMontoConcepto().subtract(cptoPagoGenerado.getBdMontoPago()));												
												}				
												cptoPagoGenerado = conceptoFacade.grabarConceptoPago(cptoPagoGenerado);
												lstConceptoPagoGenerado.add(cptoPagoGenerado);
												intPeriodoGenerado = aumentarPeriodoInicial(cptoPagoGenerado.getIntPeriodo());
											}					
										}
									}else{
										intPeriodoGenerado = aumentarPeriodoInicial(Integer.parseInt(sdfPeriodo.format(Calendar.getInstance().getTime())));
										log.info("Periodo generado para la grabación: "+intPeriodoGenerado);
										for (int i = 1; i <= intNroRegistrosConceptoPago; i++) {
											cptoPagoGenerado = new ConceptoPago();
											cptoPagoGenerado.setId(new ConceptoPagoId());
											cptoPagoGenerado.getId().setIntPersEmpresaPk(cuentaConceptoDetalle.getId().getIntPersEmpresaPk());
											cptoPagoGenerado.getId().setIntCuentaPk(cuentaConceptoDetalle.getId().getIntCuentaPk());
											cptoPagoGenerado.getId().setIntItemCuentaConcepto(cuentaConceptoDetalle.getId().getIntItemCuentaConcepto());
											cptoPagoGenerado.getId().setIntItemCtaCptoDet(cuentaConceptoDetalle.getId().getIntItemCtaCptoDet());
											cptoPagoGenerado.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

											if (i!=intNroRegistrosConceptoPago) {
												cptoPagoGenerado.setIntPeriodo(intPeriodoGenerado);
												cptoPagoGenerado.setBdMontoPago(cuentaConceptoDetalle.getBdMontoConcepto());
												cptoPagoGenerado.setBdMontoSaldo(BigDecimal.ZERO);
											}else {
												cptoPagoGenerado.setIntPeriodo(intPeriodoGenerado);
												cptoPagoGenerado.setBdMontoPago(expedienteCredito.getBdMontoAporte().subtract(cuentaConceptoDetalle.getBdMontoConcepto().multiply(new BigDecimal(i-1))));
												cptoPagoGenerado.setBdMontoSaldo(cuentaConceptoDetalle.getBdMontoConcepto().subtract(cptoPagoGenerado.getBdMontoPago()));												
											}				
											cptoPagoGenerado = conceptoFacade.grabarConceptoPago(cptoPagoGenerado);
											lstConceptoPagoGenerado.add(cptoPagoGenerado);
											intPeriodoGenerado = aumentarPeriodoInicial(cptoPagoGenerado.getIntPeriodo());
											cptoPagoGenerado = new ConceptoPago();
											cptoPagoGenerado.setId(new ConceptoPagoId());
										}
									}
									//Obtenemos los Concepto pagos grabados para generar 
									ConceptoDetallePago conceptoDetallePagoGenerado = null;
									if (lstConceptoPagoGenerado!=null && !lstConceptoPagoGenerado.isEmpty()) {
										for (ConceptoPago conceptoPagoGenerado : lstConceptoPagoGenerado) {
											conceptoDetallePagoGenerado = new ConceptoDetallePago();
											conceptoDetallePagoGenerado.setId(new ConceptoDetallePagoId());
											conceptoDetallePagoGenerado.getId().setIntPersEmpresaPk(conceptoPagoGenerado.getId().getIntPersEmpresaPk());
											conceptoDetallePagoGenerado.getId().setIntCuentaPk(conceptoPagoGenerado.getId().getIntCuentaPk());
											conceptoDetallePagoGenerado.getId().setIntItemCuentaConcepto(conceptoPagoGenerado.getId().getIntItemCuentaConcepto()); 
											conceptoDetallePagoGenerado.getId().setIntItemCtaCptoDet(conceptoPagoGenerado.getId().getIntItemCtaCptoDet()); 
											conceptoDetallePagoGenerado.getId().setIntItemConceptoPago(conceptoPagoGenerado.getId().getIntItemConceptoPago()); 
											conceptoDetallePagoGenerado.getId().setIntItemMovCtaCte(movGenerado.getIntItemMovimiento());
											conceptoDetallePagoGenerado.setIntTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
											conceptoDetallePagoGenerado.setBdMonto(conceptoPagoGenerado.getBdMontoPago());
											conceptoFacade.grabarConceptoDetallePago(conceptoDetallePagoGenerado);										
										}
									}
								}
							}
						}					
					}
				}
			}			
			
			log.info("---- Extorno ----");
			/* La aprobación de un préstamo genera un asiento, una vez girado, este asiento debe de extornarse.
			   En cuanto al registro, el asiento a generar debe ser gemelo al asiento de la aprobación, la unica 
			   diferencia es que si en la aprobacion la cuenta xxxxxx graba DEBE y la cuenta zzzzzz HABER, se
			   invertirán los montos del DEBE y del HABER. Para obtener el asiento de la aprobacion, en CSE_ESTADOCREDITO
			   se graba el asiento generado.
			*/
			LibroDiario libroDiarioExtorno =  generarLibroDiarioExtorno(expedienteCredito);
			log.info(libroDiarioExtorno);
			for(LibroDiarioDetalle libroDiarioDetalle : libroDiarioExtorno.getListaLibroDiarioDetalle()){
				log.info(libroDiarioDetalle);
			}
			libroDiarioExtorno = libroDiarioFacade.grabarLibroDiario(libroDiarioExtorno);
			System.out.println("libroDiarioExtorno ---> "+libroDiarioExtorno);
			egreso.setIntPersEmpresaLibroExtorno(libroDiarioExtorno.getId().getIntPersEmpresaLibro());
			egreso.setIntContPeriodoLibroExtorno(libroDiarioExtorno.getId().getIntContPeriodoLibro());
			egreso.setIntContCodigoLibroExtorno(libroDiarioExtorno.getId().getIntContCodigoLibro());
			
			log.info("Egreso usado en la generación de Estado Credito ---> "+egreso);
			//Generación y grabación de Estado Credito (CSE_ESTADOCREDITO)
			//Se genera unregistro mas con Estado de Solicitud GIRADO
			EstadoCredito estadoCreditoGirado = generarEstadoCredito(expedienteCredito, egreso);
			log.info("Estado Credito Generado ---> "+estadoCreditoGirado);
			boEstadoCredito.grabar(estadoCreditoGirado);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}		
		expedienteCredito.getEgreso().setIntErrorGeneracionEgreso(0);
		expedienteCredito.getEgreso().setStrMsgErrorGeneracionEgreso("Gabación satisfactoria del Giro de Préstamo");
		return expedienteCredito;
	}
}