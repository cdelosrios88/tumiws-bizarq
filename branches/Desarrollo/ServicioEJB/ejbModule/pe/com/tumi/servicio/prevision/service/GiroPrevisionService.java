package pe.com.tumi.servicio.prevision.service;

//import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.servicio.prevision.bo.BeneficiarioPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.EstadoPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.ExpedientePrevisionBO;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeRemote;

public class GiroPrevisionService {
	
	protected static Logger log = Logger.getLogger(GiroPrevisionService.class);
	
	private ExpedientePrevisionBO boExpedientePrevision = (ExpedientePrevisionBO)TumiFactory.get(ExpedientePrevisionBO.class);
	private BeneficiarioPrevisionBO boBeneficiarioPrevision = (BeneficiarioPrevisionBO)TumiFactory.get(BeneficiarioPrevisionBO.class);
	private EstadoPrevisionBO boEstadoPrevision = (EstadoPrevisionBO)TumiFactory.get(EstadoPrevisionBO.class);

	
	
	public List<ExpedientePrevision> buscarExpedienteParaGiro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,
			EstadoPrevision estadoPrevisionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException{
		
		List<ExpedientePrevision> listaExpedientePrevision = new ArrayList<ExpedientePrevision>();
		try{
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);

			List<Cuenta> listaCuenta = new ArrayList<Cuenta>();			
			List<CuentaIntegrante> listaCuentaIntegrante = new ArrayList<CuentaIntegrante>();

			Integer intIdEmpresa = estadoPrevisionFiltro.getId().getIntPersEmpresaPk();
			Integer intParaTipoCreditoFiltro = intTipoCreditoFiltro;
			Integer intParaEstadoCreditoFiltro = estadoPrevisionFiltro.getIntParaEstado();
			
			//Validación por filtro de persona; Se carga datos Cuenta Integrante
			if(listaPersonaFiltro != null && !listaPersonaFiltro.isEmpty()){
				for(Persona persona : listaPersonaFiltro){
					List<CuentaIntegrante> listaCuentaIntegranteTemp = cuentaFacade.getCuentaIntegrantePorIdPersona(persona.getIntIdPersona(), intIdEmpresa);
					if(listaCuentaIntegranteTemp != null)	listaCuentaIntegrante.addAll(listaCuentaIntegranteTemp);
				}
			}else{
				listaCuentaIntegrante = cuentaFacade.getCuentaIntegrantePorIdPersona(null, intIdEmpresa);
			}
			
			HashSet<Integer> hashSetIntCuenta = new HashSet<Integer>();
			for(CuentaIntegrante cuentaIntegrante : listaCuentaIntegrante){
				if(cuentaIntegrante.getIntParaTipoIntegranteCod().equals(Constante.TIPOINTEGRANTE_ADMINISTRADOR)){
					//log.info("CI per:"+cuentaIntegrante.getId().getIntPersonaIntegrante());
					hashSetIntCuenta.add(cuentaIntegrante.getId().getIntCuenta());
				}
			}
			
			for(Integer intCuenta : hashSetIntCuenta){
				//log.info(intCuenta);
				CuentaId cuentaId = new CuentaId();
				cuentaId.setIntPersEmpresaPk(intIdEmpresa);
				cuentaId.setIntCuenta(intCuenta);
				Cuenta cuenta = new Cuenta();
				cuenta.setId(cuentaId);
				cuenta = cuentaFacade.getCuentaPorIdCuenta(cuenta);
				if(cuenta!=null && cuenta.getId()!=null){
					//log.info("size:"+cuenta.getListaIntegrante().size());
					listaCuenta.add(cuenta);					
				}				
			}
			
			
			//log.info("intParaTipoCreditoFiltro:"+intParaTipoCreditoFiltro);
			for(Cuenta cuenta : listaCuenta){
				//log.info(cuenta.getStrNumeroCuenta());
				//log.info(cuenta.getId().getIntCuenta()+" "+cuenta.getId().getIntPersEmpresaPk());
				//log.info("size:"+cuenta.getListaIntegrante().size());
				List<ExpedientePrevision> listaExpedientePrevisionTemp = boExpedientePrevision.getListaPorCuenta(cuenta);
				for(ExpedientePrevision expedientePrevisionTemp : listaExpedientePrevisionTemp){
					boolean pasaFiltroItem = Boolean.FALSE;
					//log.info(expedientePrevisionTemp);
					expedientePrevisionTemp.setCuenta(cuenta);
					if(intItemExpedienteFiltro!=null && expedientePrevisionTemp.getId().getIntItemExpediente().equals(intItemExpedienteFiltro)){
						pasaFiltroItem = Boolean.TRUE;
					}else if(intItemExpedienteFiltro==null){
						pasaFiltroItem = Boolean.TRUE;
					}
					
					if(pasaFiltroItem){
						if(intParaTipoCreditoFiltro!=null && expedientePrevisionTemp.getIntParaDocumentoGeneral().equals(intParaTipoCreditoFiltro)){
							listaExpedientePrevision.add(expedientePrevisionTemp);
						}else if(intParaTipoCreditoFiltro==null){
							listaExpedientePrevision.add(expedientePrevisionTemp);
						}
					}
				}
			}
			
			List<ExpedientePrevision> listaTemp = new ArrayList<ExpedientePrevision>();
			for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
				//log.info(expedientePrevision);				
				
				EstadoPrevision estadoPrevisionUltimo = obtenerUltimoEstadoPrevision(expedientePrevision);
				//log.info("estadoCreditoUltimo:"+estadoPrevisionUltimo);
				
				boolean pasaFiltroEstado = Boolean.FALSE;
				//Si se ha seleccionado un intParaEstadoCreditoFiltro en la busqueda
				if(intParaEstadoCreditoFiltro!=null && estadoPrevisionUltimo.getIntParaEstado().equals(intParaEstadoCreditoFiltro)){
					pasaFiltroEstado = Boolean.TRUE;
					
				//si no se a seleccionado un intParaEstadoCreditoFiltro en la busqueda, solo podemos traer registros en estado
				//aprobado o girado
				}else if(intParaEstadoCreditoFiltro==null){
					if(estadoPrevisionUltimo.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO) 
					|| estadoPrevisionUltimo.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)){
						pasaFiltroEstado = Boolean.TRUE;
					}
				}
				
				if(pasaFiltroEstado){
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntIdSucursal(estadoPrevisionUltimo.getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);
					//log.info(sucursal);
					estadoPrevisionUltimo.setSucursal(sucursal);
					estadoPrevisionUltimo.setSubsucursal(empresaFacade.getSubSucursalPorIdSubSucursal(estadoPrevisionUltimo.getIntSucuIdSucursal()));
					expedientePrevision.setEstadoPrevisionUltimo(estadoPrevisionUltimo);
					
					//Necesitamos agregar para la IU el estado en el que se aprobo el expediente
					/*EstadoPrevision estadoPrevisionAprobado = null;
					if(estadoPrevisionUltimo.equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
						estadoPrevisionAprobado = estadoPrevisionUltimo;
					}else{
						estadoPrevisionAprobado = obtenerUltimoEstadoPrevision(expedientePrevision, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
					}
					expedientePrevision.setEstadoPrevisionAprobado(estadoPrevisionAprobado);
					*/
					listaTemp.add(expedientePrevision);
				}
			}
			
			listaExpedientePrevision = listaTemp;
			
			if(intTipoBusquedaSucursal != null && intIdSucursalFiltro != null){
				log.info("--busqueda sucursal");
				listaExpedientePrevision = manejarBusquedaSucursal(intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro, 
						intIdEmpresa, listaExpedientePrevision);
			}
			
			if(estadoPrevisionFiltro.getDtFechaEstadoDesde()!=null || estadoPrevisionFiltro.getDtFechaEstadoHasta()!=null){
				log.info("--busqueda fechas");
				listaExpedientePrevision = manejarFiltroFechas(listaExpedientePrevision, estadoPrevisionFiltro);
			}
			
		}catch(BusinessException e){
			log.error(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return listaExpedientePrevision;
	}
	
	private List<ExpedientePrevision> manejarFiltroFechas(List<ExpedientePrevision> listaExpedientePrevision, EstadoPrevision estado)throws Exception{		
		if(estado.getDtFechaEstadoDesde()!=null){
			List<ExpedientePrevision> listaTemp = new ArrayList<ExpedientePrevision>();
			for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){				
				if(expedientePrevision.getEstadoPrevisionUltimo().getTsFechaEstado().compareTo(estado.getDtFechaEstadoDesde())>=0){
					listaTemp.add(expedientePrevision);
				}
			}
			listaExpedientePrevision = listaTemp;
		}		
		if(estado.getDtFechaEstadoHasta()!=null){
			List<ExpedientePrevision> listaTemp = new ArrayList<ExpedientePrevision>();
			for(ExpedientePrevision expedienteCredito : listaExpedientePrevision){				
				if(expedienteCredito.getEstadoPrevisionUltimo().getTsFechaEstado().compareTo(estado.getDtFechaEstadoHasta())<=0){
					listaTemp.add(expedienteCredito);
				}
			}
			listaExpedientePrevision = listaTemp;
		}
		return listaExpedientePrevision;
	}
	
	private List<ExpedientePrevision> manejarBusquedaSucursal(Integer intTipoBusquedaSucursal, Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro, 
			Integer intIdEmpresa, List<ExpedientePrevision> listaExpedientePrevision) throws Exception{
		
		SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
		List<ExpedientePrevision> listaTemp = new ArrayList<ExpedientePrevision>();
		
		if(intTipoBusquedaSucursal.equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_SOCIO) && intIdSucursalFiltro!=null){
			for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
				for(CuentaIntegrante cuentaIntegrante : expedientePrevision.getCuenta().getListaIntegrante()){
					SocioEstructura socioEstructura = (socioFacade.getListaSocioEstrucuraPorIdPersona(
							cuentaIntegrante.getIntPersonaUsuario(), intIdEmpresa)).get(0);
					if(intIdSucursalFiltro.intValue()>0){
						if(socioEstructura.getIntIdSucursalAdministra().equals(intIdSucursalFiltro)){
							if(intIdSubsucursalFiltro!=null && intIdSubsucursalFiltro.equals(socioEstructura.getIntIdSubsucurAdministra())){
								listaTemp.add(expedientePrevision);
							}else if(intIdSubsucursalFiltro==null){
								listaTemp.add(expedientePrevision);
							}
						}
					}else{
						Integer intTotalSucursal = intIdSucursalFiltro;
						Sucursal sucursal = new Sucursal();
						sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
						sucursal.getId().setIntIdSucursal(socioEstructura.getIntIdSucursalAdministra());
						sucursal = empresaFacade.getSucursalPorPK(sucursal);						
						if(validarTotalSucursal(sucursal.getIntIdTipoSucursal(), intTotalSucursal)){
							listaTemp.add(expedientePrevision);
						}
					}
				}
			}
		
		}else if(intTipoBusquedaSucursal.equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_USUARIO) && intIdSucursalFiltro!=null){
			for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
				EstadoPrevision estadoPrevisionUltimo = expedientePrevision.getEstadoPrevisionUltimo();
				if(intIdSucursalFiltro.intValue()>0){
					if(estadoPrevisionUltimo.getIntSucuIdSucursal().equals(intIdSucursalFiltro)){
						if(intIdSubsucursalFiltro!=null && intIdSubsucursalFiltro.equals(estadoPrevisionUltimo.getIntSucuIdSucursal())){
							listaTemp.add(expedientePrevision);
						}else if(intIdSubsucursalFiltro==null){
							listaTemp.add(expedientePrevision);
						}
					}
				}else{
					Integer intTotalSucursal = intIdSucursalFiltro;
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
					sucursal.getId().setIntIdSucursal(estadoPrevisionUltimo.getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);						
					if(validarTotalSucursal(sucursal.getIntIdTipoSucursal(), intTotalSucursal)){
						listaTemp.add(expedientePrevision);
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
	
	public List<ExpedientePrevision> buscarExpedienteParaGiroDesdeFondo(Integer IntIdPersona, Integer intIdEmpresa, Integer intTipoDocumento) throws BusinessException{
		
		List<ExpedientePrevision> listaExpedientePrevision = new ArrayList<ExpedientePrevision>();
		try{
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);	
			
			List<Cuenta> listaCuenta = new ArrayList<Cuenta>();			
			List<CuentaIntegrante> listaCuentaIntegrante = cuentaFacade.getCuentaIntegrantePorIdPersona(IntIdPersona, intIdEmpresa);
			
			Integer intParaTipoCreditoFiltro = intTipoDocumento;
			Integer intParaEstadoCreditoFiltro = Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO;
			
			
			HashSet<Integer> hashSetIntCuenta = new HashSet<Integer>();
			for(CuentaIntegrante cuentaIntegrante : listaCuentaIntegrante){
				if(cuentaIntegrante.getIntParaTipoIntegranteCod().equals(Constante.TIPOINTEGRANTE_ADMINISTRADOR)){
					log.info("CI per:"+cuentaIntegrante.getId().getIntPersonaIntegrante());
					hashSetIntCuenta.add(cuentaIntegrante.getId().getIntCuenta());
				}
			}
			
			for(Integer intCuenta : hashSetIntCuenta){
				log.info(intCuenta);
				CuentaId cuentaId = new CuentaId();
				cuentaId.setIntPersEmpresaPk(intIdEmpresa);
				cuentaId.setIntCuenta(intCuenta);
				Cuenta cuenta = new Cuenta();
				cuenta.setId(cuentaId);
				cuenta = cuentaFacade.getCuentaPorIdCuenta(cuenta);
				if(cuenta!=null && cuenta.getId()!=null){
					log.info("size:"+cuenta.getListaIntegrante().size());
					listaCuenta.add(cuenta);					
				}				
			}
			
			
			log.info("intParaTipoCreditoFiltro:"+intParaTipoCreditoFiltro);
			for(Cuenta cuenta : listaCuenta){
				log.info(cuenta.getStrNumeroCuenta());
				log.info(cuenta.getId().getIntCuenta()+" "+cuenta.getId().getIntPersEmpresaPk());
				log.info("size:"+cuenta.getListaIntegrante().size());
				List<ExpedientePrevision> listaExpedientePrevisionTemp = boExpedientePrevision.getListaPorCuenta(cuenta);
				for(ExpedientePrevision expedientePrevisionTemp : listaExpedientePrevisionTemp){
					log.info(expedientePrevisionTemp);
					expedientePrevisionTemp.setCuenta(cuenta);										
					if(expedientePrevisionTemp.getIntParaDocumentoGeneral().equals(intParaTipoCreditoFiltro)){
						listaExpedientePrevision.add(expedientePrevisionTemp);
					}
				}
			}
			
			List<ExpedientePrevision> listaTemp = new ArrayList<ExpedientePrevision>();
			for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
				log.info(expedientePrevision);				
				
				EstadoPrevision estadoPrevisionUltimo = obtenerUltimoEstadoPrevision(expedientePrevision);
				log.info("estadoCreditoUltimo:"+estadoPrevisionUltimo);
				
				//Si se ha seleccionado un intParaEstadoCreditoFiltro en la busqueda
				
				if(estadoPrevisionUltimo.getIntParaEstado().equals(intParaEstadoCreditoFiltro)){
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntIdSucursal(estadoPrevisionUltimo.getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);
					log.info(sucursal);
					estadoPrevisionUltimo.setSucursal(sucursal);
					estadoPrevisionUltimo.setSubsucursal(empresaFacade.getSubSucursalPorIdSubSucursal(estadoPrevisionUltimo.getIntSucuIdSucursal()));
					expedientePrevision.setEstadoPrevisionUltimo(estadoPrevisionUltimo);					
					listaTemp.add(expedientePrevision);
				}
			}
			
			listaExpedientePrevision = listaTemp;
			
			
			//Se añade persona administra la cuenta del expediente
			for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
				CuentaIntegrante cuentaIntegrante = expedientePrevision.getCuenta().getListaIntegrante().get(0);
				Persona persona = personaFacade.getPersonaPorPK(cuentaIntegrante.getId().getIntPersonaIntegrante());
				log.info(persona);
				expedientePrevision.setPersonaAdministra(persona);
			}
		}catch(BusinessException e){
			log.error(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return listaExpedientePrevision;
	}
	
	private EstadoPrevision obtenerUltimoEstadoPrevision(ExpedientePrevision expedientePrevision)throws BusinessException{
		EstadoPrevision estadoPrevisionUltimo = new EstadoPrevision();
		try{
			List<EstadoPrevision> listaEstadoPrevision = boEstadoPrevision.getPorExpediente(expedientePrevision);
			
			estadoPrevisionUltimo.getId().setIntItemEstado(0);
			for(EstadoPrevision estadoPrevision : listaEstadoPrevision){
				if(estadoPrevision.getId().getIntItemEstado().compareTo(estadoPrevisionUltimo.getId().getIntItemEstado())>0){
					estadoPrevisionUltimo = estadoPrevision;
				}
			}
			
			expedientePrevision.setListaEstadoPrevision(listaEstadoPrevision);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estadoPrevisionUltimo;
	}
	
	public EstadoPrevision obtenerUltimoEstadoPrevision(ExpedientePrevision expedientePrevision, Integer intTipoEstado)throws Exception{
		EstadoPrevision estadoPrevisionUltimo = new EstadoPrevision();
		try{
			//tomamos en cuenta que ya se llega con una listaEstadoPrevision cargada
			//List<EstadoPrevision> listaEstadoPrevision = boEstadoPrevision.getPorExpediente(expedientePrevision);
			
			estadoPrevisionUltimo.getId().setIntItemEstado(0);
			for(EstadoPrevision estadoPrevision : expedientePrevision.getListaEstadoPrevision()){
				if(estadoPrevision.getIntParaEstado().equals(intTipoEstado)
				&& estadoPrevision.getId().getIntItemEstado().compareTo(estadoPrevisionUltimo.getId().getIntItemEstado())>0){
					estadoPrevisionUltimo = estadoPrevision;
				}
			}
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estadoPrevisionUltimo;
	}
	
	
	private EstadoPrevision generarEstadoPrevision(ExpedientePrevision expedientePrevision) throws Exception {
		EstadoPrevision estadoPrevision = new EstadoPrevision();
		estadoPrevision.getId().setIntPersEmpresaPk(expedientePrevision.getId().getIntPersEmpresaPk());
		estadoPrevision.getId().setIntCuentaPk(expedientePrevision.getId().getIntCuentaPk());
		estadoPrevision.getId().setIntItemExpediente(expedientePrevision.getId().getIntItemExpediente());
		estadoPrevision.getId().setIntItemEstado(null);
		estadoPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO);
		estadoPrevision.setTsFechaEstado(expedientePrevision.getEgreso().getTsFechaProceso());
		estadoPrevision.setIntPersEmpresaEstado(expedientePrevision.getId().getIntPersEmpresaPk());
		estadoPrevision.setIntSucuIdSucursal(expedientePrevision.getEgreso().getIntSucuIdSucursal());
		estadoPrevision.setIntSudeIdSubsucursal(expedientePrevision.getEgreso().getIntSudeIdSubsucursal());
		estadoPrevision.setIntPersUsuarioEstado(expedientePrevision.getEgreso().getIntPersPersonaUsuario());		
		
		return estadoPrevision;
	}
	
	private Movimiento generarMovimiento(ExpedientePrevision expediente, Egreso egreso) throws Exception{
		Movimiento movimiento = new Movimiento();
		movimiento.setTsFechaMovimiento(expediente.getEgreso().getTsFechaProceso());
		movimiento.setIntPersEmpresa(expediente.getId().getIntPersEmpresaPk());
		movimiento.setIntCuenta(expediente.getCuenta().getId().getIntCuenta());
		movimiento.setIntPersPersonaIntegrante(expediente.getEgreso().getIntPersPersonaGirado());
		movimiento.setIntItemCuentaConcepto(null);
		movimiento.setIntItemExpediente(expediente.getId().getIntItemExpediente());
		movimiento.setIntItemExpedienteDetalle(null);
		if(expediente.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES)){
			movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_CONCEPTOGENERAL_AMORTIZACION_SEPELIO);
		}else if(expediente.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)){
			movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_CONCEPTOGENERAL_AMORTIZACION_SEPELIO);
		}else if(expediente.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
			movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_CONCEPTOGENERAL_AMORTIZACION_RETIRO);
		}
		
		movimiento.setIntParaTipoMovimiento(Constante.PARAM_T_MOVIMIENTOCTACTE_EGRESOCAJA);
		movimiento.setIntParaDocumentoGeneral(expediente.getIntParaDocumentoGeneral());
		movimiento.setStrSerieDocumento(null);
		movimiento.setStrNumeroDocumento(""+expediente.getId().getIntItemExpediente());
		movimiento.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_CARGO);
		movimiento.setBdMontoMovimiento(expediente.getBdMontoNetoBeneficio());//21.05.2014 SE MODIFICA EL MONTO BRUTO POR EL NETO. JCHAVEZ		
		movimiento.setIntPersEmpresaUsuario(expediente.getEgreso().getIntPersEmpresaUsuario());
		movimiento.setIntPersPersonaUsuario(expediente.getEgreso().getIntPersPersonaUsuario());
		//agregado 19.05.2014 jchavez 
		movimiento.setIntItemMovimientoRel(expediente.getMovimiento()!=null?expediente.getMovimiento().getIntItemMovimiento():null);
		//jchavez 20.05.2014
		movimiento.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
		movimiento.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
		return movimiento;
	}
	
//	private Integer	obtenerPeriodoActual() throws Exception{
//		String strPeriodo = "";
//		Calendar cal = Calendar.getInstance();
//		int año = cal.get(Calendar.YEAR);
//		int mes = cal.get(Calendar.MONTH);
//		mes = mes + 1; 
//		if(mes<10){
//			strPeriodo = año + "0" + mes;
//		}else{
//			strPeriodo  = año + "" + mes;
//		}		
//		return Integer.parseInt(strPeriodo);		
//	}
//	
//	private Timestamp obtenerFechaActual(){
//		return new Timestamp(new Date().getTime());
//	}

	
	private BeneficiarioPrevision obtenerBeneficiarioSeleccionado(ExpedientePrevision expedientePrevision){
		for(BeneficiarioPrevision beneficiarioPrevision : expedientePrevision.getListaBeneficiarioPrevision()){
			if(beneficiarioPrevision.getIntPersPersonaBeneficiario().equals(expedientePrevision.getEgreso().getIntPersPersonaBeneficiario())){
				return beneficiarioPrevision;
			}
		}
		return null;
	}
	
	/**
	 * modificado 15.02.2014
	 * @param expedientePrevision
	 * @return
	 * @throws BusinessException
	 */
	public ExpedientePrevision grabarGiroPrevision(ExpedientePrevision expedientePrevision)throws BusinessException{
		try{
			EgresoFacadeRemote egresoFacade = (EgresoFacadeRemote) EJBFactory.getRemote(EgresoFacadeRemote.class);
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			
			Egreso egreso = expedientePrevision.getEgreso();
			
			log.info("Expediente prevision recibido: "+expedientePrevision);
			log.info("Egreso recibido: "+egreso);
			
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				log.info("Egreso Detalle recibido: "+egresoDetalle);
			}
			log.info("Libro Diario recibido: "+egreso.getLibroDiario());
			for(LibroDiarioDetalle libroDiarioDetalle : egreso.getLibroDiario().getListaLibroDiarioDetalle()){
				log.info("Libro Diario Detalle recibido: "+libroDiarioDetalle);
			}
			//Se procede a la grabación del Egreso, Egreso Detalle, Libro Diario y Libro Diario Detalle
			egreso.setBlnEsGiroPorSedeCentral(false);
			egreso = egresoFacade.grabarEgresoParaGiroPrestamo(egreso);
						
			BeneficiarioPrevision beneficiarioPrevisionSeleccionado = obtenerBeneficiarioSeleccionado(expedientePrevision);
			beneficiarioPrevisionSeleccionado.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
			beneficiarioPrevisionSeleccionado.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
			log.info(beneficiarioPrevisionSeleccionado);
			boBeneficiarioPrevision.modificar(beneficiarioPrevisionSeleccionado);
			
			if(expedientePrevision.getListaBeneficiarioPrevision().size()==1){
				EstadoPrevision estadoPrevision = generarEstadoPrevision(expedientePrevision);
				log.info(estadoPrevision);
				boEstadoPrevision.grabar(estadoPrevision);
			}
		 	
			if (!(expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES))) {
				//Generacion del registro en movimiento
				Movimiento movimiento = generarMovimiento(expedientePrevision, egreso);
				log.info(movimiento);
				//Modificado 12.05.2014 jchavez
				BigDecimal bdPorcentaje = beneficiarioPrevisionSeleccionado.getBdPorcentajeBeneficio().divide(new BigDecimal(100));
				movimiento.setBdMontoMovimiento(movimiento.getBdMontoMovimiento().multiply(bdPorcentaje).setScale(2,BigDecimal.ROUND_HALF_UP));
						
				movimiento = conceptoFacade.grabarMovimiento(movimiento);
				//Agregado 20.05.2014 jchavez Modifica el valor del saldo de la cuenta por pagar
				Movimiento movCtaPorPagar = conceptoFacade.getMovimientoPorPK(movimiento.getIntItemMovimientoRel());
				movCtaPorPagar.setBdMontoSaldo(movCtaPorPagar.getBdMontoSaldo().subtract(movimiento.getBdMontoMovimiento()));
				conceptoFacade.modificarMovimiento(movCtaPorPagar);
			}

		}catch(Exception e){
			throw new BusinessException(e);
		}		
		return expedientePrevision;
	}
	/**
	 * jchavez 15.05.2014
	 * grabacion del egreso por fondo fijo o banco (TESORERIA)
	 * @param expedientePrevision
	 * @return
	 * @throws BusinessException
	 */
	public ExpedientePrevision grabarGiroPrevisionPorTesoreria(ExpedientePrevision expedientePrevision)throws BusinessException{
		try{
			EgresoFacadeRemote egresoFacade = (EgresoFacadeRemote) EJBFactory.getRemote(EgresoFacadeRemote.class);
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			
			Egreso egreso = expedientePrevision.getEgreso();
			
			log.info("Expediente prevision recibido: "+expedientePrevision);
			log.info("Egreso recibido: "+egreso);
			
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				log.info("Egreso Detalle recibido: "+egresoDetalle);
			}
			log.info("Libro Diario recibido: "+egreso.getLibroDiario());
			for(LibroDiarioDetalle libroDiarioDetalle : egreso.getLibroDiario().getListaLibroDiarioDetalle()){
				log.info("Libro Diario Detalle recibido: "+libroDiarioDetalle);
			}
			//Se procede a la grabación del Egreso, Egreso Detalle, Libro Diario y Libro Diario Detalle
			egreso.setBlnEsGiroPorSedeCentral(true);
			egreso = egresoFacade.grabarEgresoParaGiroPrestamo(egreso);
			/* Autor: jchavez / Tarea: Modificación / Fecha: 11.09.2014
			   Se setea  el egreso grabado en el expediente prevision*/
			expedientePrevision.setEgreso(egreso);
			
			BeneficiarioPrevision beneficiarioPrevisionSeleccionado = obtenerBeneficiarioSeleccionado(expedientePrevision);
			beneficiarioPrevisionSeleccionado.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
			beneficiarioPrevisionSeleccionado.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
			log.info(beneficiarioPrevisionSeleccionado);
			boBeneficiarioPrevision.modificar(beneficiarioPrevisionSeleccionado);
			
			if(expedientePrevision.getListaBeneficiarioPrevision().size()==1){
				EstadoPrevision estadoPrevision = generarEstadoPrevision(expedientePrevision);
				log.info(estadoPrevision);
				boEstadoPrevision.grabar(estadoPrevision);
			}
		 	
			//Generacion del registro en movimiento
			Movimiento movimiento = generarMovimiento(expedientePrevision,egreso);
			log.info(movimiento);
			//Modificado 12.05.2014 jchavez
			BigDecimal bdPorcentaje = beneficiarioPrevisionSeleccionado.getBdPorcentajeBeneficio().divide(new BigDecimal(100));
			movimiento.setBdMontoMovimiento(movimiento.getBdMontoMovimiento().multiply(bdPorcentaje).setScale(2,BigDecimal.ROUND_HALF_UP));
			
			movimiento = conceptoFacade.grabarMovimiento(movimiento);
			//Agregado 20.05.2014 jchavez Modifica el valor del saldo de la cuenta por pagar
			Movimiento movCtaPorPagar = conceptoFacade.getMovimientoPorPK(movimiento.getIntItemMovimientoRel());
			movCtaPorPagar.setBdMontoSaldo(movCtaPorPagar.getBdMontoSaldo().subtract(movimiento.getBdMontoMovimiento()));
			conceptoFacade.modificarMovimiento(movCtaPorPagar);

		}catch(Exception e){
			throw new BusinessException(e);
		}		
		return expedientePrevision;
	}
}