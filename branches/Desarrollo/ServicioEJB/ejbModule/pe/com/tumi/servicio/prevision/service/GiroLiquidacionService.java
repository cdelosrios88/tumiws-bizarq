package pe.com.tumi.servicio.prevision.service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.servicio.prevision.bo.BeneficiarioLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.EstadoLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.ExpedienteLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.ExpedienteLiquidacionDetalleBO;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeRemote;

public class GiroLiquidacionService {
	
	protected static Logger log = Logger.getLogger(GiroLiquidacionService.class);
	
	private ExpedienteLiquidacionDetalleBO boExpedienteLiquidacionDetalle = (ExpedienteLiquidacionDetalleBO)TumiFactory.get(ExpedienteLiquidacionDetalleBO.class);
	private ExpedienteLiquidacionBO boExpedienteLiquidacion = (ExpedienteLiquidacionBO)TumiFactory.get(ExpedienteLiquidacionBO.class);
	private EstadoLiquidacionBO boEstadoLiquidacion = (EstadoLiquidacionBO)TumiFactory.get(EstadoLiquidacionBO.class);
	private BeneficiarioLiquidacionBO boBeneficiarioLiquidacion = (BeneficiarioLiquidacionBO)TumiFactory.get(BeneficiarioLiquidacionBO.class);
	
	private List<ExpedienteLiquidacionDetalle> obtenerExpedienteLiquidacionDetalle(ExpedienteLiquidacion expedienteLiquidacion)throws Exception{
		CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
		
		List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle = boExpedienteLiquidacionDetalle.getPorExpedienteLiquidacion(expedienteLiquidacion);
		for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaExpedienteLiquidacionDetalle){
			Cuenta cuenta = new Cuenta();
			cuenta.setId(new CuentaId());
			cuenta.getId().setIntPersEmpresaPk(expedienteLiquidacionDetalle.getId().getIntPersEmpresa());
			cuenta.getId().setIntCuenta(expedienteLiquidacionDetalle.getId().getIntCuenta());
			expedienteLiquidacionDetalle.setCuenta(cuentaFacade.getCuentaPorIdCuenta(cuenta));
		}
		return listaExpedienteLiquidacionDetalle;
	}

	public List<ExpedienteLiquidacion> buscarExpedienteParaGiro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,
			EstadoLiquidacion estadoLiquidacionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException{
		List<ExpedienteLiquidacion> listaExpedienteLiquidacionTemp = new ArrayList<ExpedienteLiquidacion>();
		List<ExpedienteLiquidacion> listaExpedienteLiquidacion = new ArrayList<ExpedienteLiquidacion>();
		try{
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			Integer INT_SI_AÑADIR = 1;
//			Integer INT_NO_AÑADIR = 0;
			
			List<Cuenta> listaCuenta = new ArrayList<Cuenta>();			
			List<CuentaIntegrante> listaCuentaIntegrante = new ArrayList<CuentaIntegrante>();
			List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle = new ArrayList<ExpedienteLiquidacionDetalle>();
			
			Integer intIdEmpresa = estadoLiquidacionFiltro.getId().getIntPersEmpresaPk();
//			Integer intParaTipoCreditoFiltro = intTipoCreditoFiltro;
			Integer intParaEstadoCreditoFiltro = estadoLiquidacionFiltro.getIntParaEstado();
			
			
			//Si se ha hecho un filtro de personas
			if(listaPersonaFiltro != null && !listaPersonaFiltro.isEmpty()){
				for(Persona persona : listaPersonaFiltro){
					List<CuentaIntegrante> listaCuentaIntegranteTemp = cuentaFacade.getCuentaIntegrantePorIdPersona(persona.getIntIdPersona(), intIdEmpresa);
					if(listaCuentaIntegranteTemp != null)	listaCuentaIntegrante.addAll(listaCuentaIntegranteTemp);
				}
			//Si no se ha hecho un filtro de personas
			}else{
				//listaCuentaIntegrante = cuentaFacade.getCuentaIntegrantePorIdPersona(null, intIdEmpresa);
				if (intItemExpedienteFiltro!=null) {
//					listaExpedienteLiquidacionTemp = new ArrayList<ExpedienteLiquidacion>();
					listaExpedienteLiquidacionTemp.add(boExpedienteLiquidacion.getPorPk(intIdEmpresa, intItemExpedienteFiltro));
				}else listaExpedienteLiquidacionTemp = boExpedienteLiquidacion.getPorIdEmpresa(intIdEmpresa);
				//Autor: jchavez / Tarea: Modificación / Fecha: 08.08.2014 /
				//Funcionalidad: Solucion provisional filtros de busqueda
				if (listaExpedienteLiquidacionTemp!=null && !listaExpedienteLiquidacionTemp.isEmpty()) {
					for (ExpedienteLiquidacion expLiqTemp : listaExpedienteLiquidacionTemp) {
						if (intTipoCreditoFiltro!=null && !intTipoCreditoFiltro.equals(0) && expLiqTemp.getIntParaSubTipoOperacion().compareTo(intTipoCreditoFiltro)==0) {
							listaExpedienteLiquidacion.add(expLiqTemp);
						}else if (intTipoCreditoFiltro==null || intTipoCreditoFiltro.equals(0)) {
							listaExpedienteLiquidacion.add(expLiqTemp);
						}
					}
				}
				for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
					
					expedienteLiquidacion.setListaExpedienteLiquidacionDetalle(obtenerExpedienteLiquidacionDetalle(expedienteLiquidacion));					
				}
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
			
			List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalleTemp = null;
			//log.info("intParaTipoCreditoFiltro:"+intParaTipoCreditoFiltro);
			for(Cuenta cuenta : listaCuenta){
				//log.info(cuenta);
				//log.info("size:"+cuenta.getListaIntegrante().size());
				listaExpedienteLiquidacionDetalleTemp = boExpedienteLiquidacionDetalle.getPorCuentaId(cuenta.getId());
				for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaExpedienteLiquidacionDetalleTemp){
					expedienteLiquidacionDetalle.setCuenta(cuenta);
					listaExpedienteLiquidacionDetalle.add(expedienteLiquidacionDetalle);
				}
				
			}
			
			HashSet<Integer> hashSetIntItemExpediente = new HashSet<Integer>();			
			for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaExpedienteLiquidacionDetalle){
				boolean pasaFiltroItem = Boolean.FALSE;
				//log.info(expedienteLiquidacionDetalle);
				if(intItemExpedienteFiltro!=null && expedienteLiquidacionDetalle.getId().getIntItemExpediente().equals(intItemExpedienteFiltro)){
					pasaFiltroItem = Boolean.TRUE;
				}else if(intItemExpedienteFiltro==null){
					pasaFiltroItem = Boolean.TRUE;
				}
				
				if(pasaFiltroItem){
					/*if(intParaTipoCreditoFiltro!=null && expedientePrevisionTemp.getIntParaDocumentoGeneral().equals(intParaTipoCreditoFiltro)){
						listaExpedienteLiquidacion.add(expedientePrevisionTemp);
					}else if(intParaTipoCreditoFiltro==null){
						listaExpedienteLiquidacion.add(expedientePrevisionTemp);
					}*/
					hashSetIntItemExpediente.add(expedienteLiquidacionDetalle.getId().getIntItemExpediente());
				}
			}
			
			if(listaExpedienteLiquidacion.isEmpty()){
				for(Integer intItemExpediente : hashSetIntItemExpediente){
					//log.info("intIdEmpresa:"+intIdEmpresa+" intItemExpediente:"+intItemExpediente);
					ExpedienteLiquidacion expedienteLiquidacion = boExpedienteLiquidacion.getPorPk(intIdEmpresa, intItemExpediente);
					List<ExpedienteLiquidacionDetalle> listaTemp = boExpedienteLiquidacionDetalle.getPorExpedienteLiquidacion(expedienteLiquidacion);
					for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaTemp){
						for(Cuenta cuenta : listaCuenta){
							if(cuenta.getId().getIntCuenta().equals(expedienteLiquidacionDetalle.getId().getIntCuenta())
							&& cuenta.getId().getIntPersEmpresaPk().equals(expedienteLiquidacionDetalle.getId().getIntPersEmpresa())){
								expedienteLiquidacionDetalle.setCuenta(cuenta);
								break;
							}
						}
					}
					expedienteLiquidacion.setListaExpedienteLiquidacionDetalle(listaTemp);
					listaExpedienteLiquidacion.add(expedienteLiquidacion);
				}
			}			
			
			List<ExpedienteLiquidacion> listaTemp = new ArrayList<ExpedienteLiquidacion>();
			for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
				//log.info(expedienteLiquidacion);
				
				EstadoLiquidacion estadoLiquidacionUltimo = obtenerUltimoEstadoLiquidacion(expedienteLiquidacion, INT_SI_AÑADIR);
				//log.info("estadoLiquidacionUltimo:"+estadoLiquidacionUltimo);
				
				boolean pasaFiltroEstado = Boolean.FALSE;
				if (estadoLiquidacionUltimo.getIntParaEstado()!=null) {
					//Si se ha seleccionado un intParaEstadoCreditoFiltro en la busqueda
					if(intParaEstadoCreditoFiltro!=null && estadoLiquidacionUltimo.getIntParaEstado().equals(intParaEstadoCreditoFiltro)){
						pasaFiltroEstado = Boolean.TRUE;
						
					//si no se a seleccionado un intParaEstadoCreditoFiltro en la busqueda, solo podemos traer registros en estado
					//aprobado o girado
					}else if(intParaEstadoCreditoFiltro==null){
						if (estadoLiquidacionUltimo.getIntParaEstado()!=null) {
							if(estadoLiquidacionUltimo.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO) 
									|| estadoLiquidacionUltimo.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)){
										pasaFiltroEstado = Boolean.TRUE;
							}
						}					
					}
				}
				
				
				if(pasaFiltroEstado){
					Sucursal sucursal = new Sucursal();
					sucursal.setId(new SucursalId());
					sucursal.getId().setIntIdSucursal(estadoLiquidacionUltimo.getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);
					//log.info(sucursal);
					estadoLiquidacionUltimo.setSucursal(sucursal);
					estadoLiquidacionUltimo.setSubsucursal(empresaFacade.getSubSucursalPorIdSubSucursal(estadoLiquidacionUltimo.getIntSucuIdSucursal()));
					expedienteLiquidacion.setEstadoLiquidacionUltimo(estadoLiquidacionUltimo);
					
					//Necesitamos agregar para la IU el estado en el que se aprobo el expediente
					/*EstadoPrevision estadoPrevisionAprobado = null;
					if(estadoPrevisionUltimo.equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
						estadoPrevisionAprobado = estadoPrevisionUltimo;
					}else{
						estadoPrevisionAprobado = obtenerUltimoEstadoPrevision(expedientePrevision, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
					}
					expedientePrevision.setEstadoPrevisionAprobado(estadoPrevisionAprobado);
					*/
					listaTemp.add(expedienteLiquidacion);
				}
			}
			log.info("CERRAMOS FOR DE SUCURSAL");
			listaExpedienteLiquidacion = listaTemp;		
			
			
			
			if(intTipoBusquedaSucursal != null && intIdSucursalFiltro != null){
				log.info("--busqueda sucursal");
				listaExpedienteLiquidacion = manejarBusquedaSucursal(intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro, 
						intIdEmpresa, listaExpedienteLiquidacion);
			}
			
			for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
				CuentaIntegrante cuentaIntegrante = expedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getCuenta().getListaIntegrante().get(0);
				Persona persona = personaFacade.getPersonaPorPK(cuentaIntegrante.getId().getIntPersonaIntegrante());
				//log.info(persona);
				expedienteLiquidacion.setPersonaAdministra(persona);
			}
			
			if(estadoLiquidacionFiltro.getDtFechaEstadoDesde()!=null || estadoLiquidacionFiltro.getDtFechaEstadoHasta()!=null){
				log.info("--busqueda fechas");
				listaExpedienteLiquidacion = manejarFiltroFechas(listaExpedienteLiquidacion, estadoLiquidacionFiltro);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaExpedienteLiquidacion;
	}
	
	private List<ExpedienteLiquidacion> manejarFiltroFechas(List<ExpedienteLiquidacion> listaExpedienteLiquidacion, EstadoLiquidacion estado)throws Exception{		
		if(estado.getDtFechaEstadoDesde()!=null){
			List<ExpedienteLiquidacion> listaTemp = new ArrayList<ExpedienteLiquidacion>();
			for(ExpedienteLiquidacion expedientePrevision : listaExpedienteLiquidacion){				
				if(expedientePrevision.getEstadoLiquidacionUltimo().getTsFechaEstado().compareTo(estado.getDtFechaEstadoDesde())>=0){
					listaTemp.add(expedientePrevision);
				}
			}
			listaExpedienteLiquidacion = listaTemp;
		}		
		if(estado.getDtFechaEstadoHasta()!=null){
			List<ExpedienteLiquidacion> listaTemp = new ArrayList<ExpedienteLiquidacion>();
			for(ExpedienteLiquidacion expedienteCredito : listaExpedienteLiquidacion){				
				if(expedienteCredito.getEstadoLiquidacionUltimo().getTsFechaEstado().compareTo(estado.getDtFechaEstadoHasta())<=0){
					listaTemp.add(expedienteCredito);
				}
			}
			listaExpedienteLiquidacion = listaTemp;
		}
		return listaExpedienteLiquidacion;
	}
	
	private List<ExpedienteLiquidacion> manejarBusquedaSucursal(Integer intTipoBusquedaSucursal, Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro, 
			Integer intIdEmpresa, List<ExpedienteLiquidacion> listaExpedienteLiquidacion) throws Exception{
		
		SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
		List<ExpedienteLiquidacion> listaTemp = new ArrayList<ExpedienteLiquidacion>();
		
		if(intTipoBusquedaSucursal.equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_SOCIO) && intIdSucursalFiltro!=null){
			for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
				for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()){
					for(CuentaIntegrante cuentaIntegrante : expedienteLiquidacionDetalle.getCuenta().getListaIntegrante()){
						SocioEstructura socioEstructura = (socioFacade.getListaSocioEstrucuraPorIdPersona(
								cuentaIntegrante.getIntPersonaUsuario(), intIdEmpresa)).get(0);
						if(intIdSucursalFiltro.intValue()>0){
							if(socioEstructura.getIntIdSucursalAdministra().equals(intIdSucursalFiltro)){
								if(intIdSubsucursalFiltro!=null && intIdSubsucursalFiltro.equals(socioEstructura.getIntIdSubsucurAdministra())){
									listaTemp.add(expedienteLiquidacion);
								}else if(intIdSubsucursalFiltro==null){
									listaTemp.add(expedienteLiquidacion);
								}
							}
						}else{
							Integer intTotalSucursal = intIdSucursalFiltro;
							Sucursal sucursal = new Sucursal();
							sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
							sucursal.getId().setIntIdSucursal(socioEstructura.getIntIdSucursalAdministra());
							sucursal = empresaFacade.getSucursalPorPK(sucursal);						
							if(validarTotalSucursal(sucursal.getIntIdTipoSucursal(), intTotalSucursal)){
								listaTemp.add(expedienteLiquidacion);
							}
						}
					}					
				}
			}
		
		}else if(intTipoBusquedaSucursal.equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_USUARIO) && intIdSucursalFiltro!=null){
			for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
				EstadoLiquidacion estadoLiquidacionUltimo = expedienteLiquidacion.getEstadoLiquidacionUltimo();
				if(intIdSucursalFiltro.intValue()>0){
					if(estadoLiquidacionUltimo.getIntSucuIdSucursal().equals(intIdSucursalFiltro)){
						if(intIdSubsucursalFiltro!=null && intIdSubsucursalFiltro.equals(estadoLiquidacionUltimo.getIntSucuIdSucursal())){
							listaTemp.add(expedienteLiquidacion);
						}else if(intIdSubsucursalFiltro==null){
							listaTemp.add(expedienteLiquidacion);
						}
					}
				}else{
					Integer intTotalSucursal = intIdSucursalFiltro;
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
					sucursal.getId().setIntIdSucursal(estadoLiquidacionUltimo.getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);						
					if(validarTotalSucursal(sucursal.getIntIdTipoSucursal(), intTotalSucursal)){
						listaTemp.add(expedienteLiquidacion);
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
	
	public List<ExpedienteLiquidacion> buscarExpedienteParaGiroDesdeFondo(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
		
		List<ExpedienteLiquidacion> listaExpedienteLiquidacion = new ArrayList<ExpedienteLiquidacion>();
		try{
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			Integer INT_SI_AÑADIR = 1;
//			Integer INT_NO_AÑADIR = 0;
			
			List<Cuenta> listaCuenta = new ArrayList<Cuenta>();			
			List<CuentaIntegrante> listaCuentaIntegrante = new ArrayList<CuentaIntegrante>();
			List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle = new ArrayList<ExpedienteLiquidacionDetalle>();

			
			listaCuentaIntegrante = cuentaFacade.getCuentaIntegrantePorIdPersona(intIdPersona, intIdEmpresa);
			
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
			
			List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalleTemp = null;
			//log.info("intParaTipoCreditoFiltro:"+intParaTipoCreditoFiltro);
			for(Cuenta cuenta : listaCuenta){
				//log.info(cuenta);
				//log.info("size:"+cuenta.getListaIntegrante().size());
				listaExpedienteLiquidacionDetalleTemp = boExpedienteLiquidacionDetalle.getPorCuentaId(cuenta.getId());
				for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaExpedienteLiquidacionDetalleTemp){
					expedienteLiquidacionDetalle.setCuenta(cuenta);
					listaExpedienteLiquidacionDetalle.add(expedienteLiquidacionDetalle);
				}
				
			}
			
			HashSet<Integer> hashSetIntItemExpediente = new HashSet<Integer>();			
			for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaExpedienteLiquidacionDetalle){
				hashSetIntItemExpediente.add(expedienteLiquidacionDetalle.getId().getIntItemExpediente());				
			}
			
			for(Integer intItemExpediente : hashSetIntItemExpediente){
				ExpedienteLiquidacion expedienteLiquidacion = boExpedienteLiquidacion.getPorPk(intIdEmpresa, intItemExpediente);
				List<ExpedienteLiquidacionDetalle> listaTemp = boExpedienteLiquidacionDetalle.getPorExpedienteLiquidacion(expedienteLiquidacion);
				for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaTemp){
					for(Cuenta cuenta : listaCuenta){
						if(cuenta.getId().getIntCuenta().equals(expedienteLiquidacionDetalle.getId().getIntCuenta())
						&& cuenta.getId().getIntPersEmpresaPk().equals(expedienteLiquidacionDetalle.getId().getIntPersEmpresa())){
							expedienteLiquidacionDetalle.setCuenta(cuenta);
							break;
						}
					}
				}
				expedienteLiquidacion.setEstadoLiquidacionUltimo(obtenerUltimoEstadoLiquidacion(expedienteLiquidacion, INT_SI_AÑADIR));
				expedienteLiquidacion.setListaExpedienteLiquidacionDetalle(listaTemp);
				listaExpedienteLiquidacion.add(expedienteLiquidacion);
			}

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaExpedienteLiquidacion;
	}
	
	

	
	private EstadoLiquidacion obtenerUltimoEstadoLiquidacion(ExpedienteLiquidacion expedienteLiquidacion, Integer intAñadirLista)throws BusinessException{
		EstadoLiquidacion estadoLiquidacionUltimo = new EstadoLiquidacion();
		try{
			Integer INT_SI_AÑADIR = 1;
//			Integer INT_NO_AÑADIR = 0;
			if(intAñadirLista.equals(INT_SI_AÑADIR)){
				expedienteLiquidacion.setListaEstadoLiquidacion(boEstadoLiquidacion.getPorExpediente(expedienteLiquidacion));
			}			
			
			estadoLiquidacionUltimo.getId().setIntItemEstado(0);
			for(EstadoLiquidacion estadoLiquidacion : expedienteLiquidacion.getListaEstadoLiquidacion()){
				if(estadoLiquidacion.getId().getIntItemEstado().compareTo(estadoLiquidacionUltimo.getId().getIntItemEstado())>0){
					estadoLiquidacionUltimo = estadoLiquidacion;
				}
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estadoLiquidacionUltimo;
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
	
	
	private EstadoLiquidacion generarEstadoLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) throws Exception {
		EstadoLiquidacion estadoLiquidacion = new EstadoLiquidacion();
		estadoLiquidacion.getId().setIntPersEmpresaPk(expedienteLiquidacion.getId().getIntPersEmpresaPk());
		estadoLiquidacion.getId().setIntItemExpediente(expedienteLiquidacion.getId().getIntItemExpediente());
		estadoLiquidacion.getId().setIntItemEstado(null);
		estadoLiquidacion.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO);
		estadoLiquidacion.setTsFechaEstado(expedienteLiquidacion.getEgreso().getTsFechaProceso());
		estadoLiquidacion.setIntPersEmpresaEstado(expedienteLiquidacion.getId().getIntPersEmpresaPk());
		estadoLiquidacion.setIntSucuIdSucursal(expedienteLiquidacion.getEgreso().getIntSucuIdSucursal());
		estadoLiquidacion.setIntSudeIdSubsucursal(expedienteLiquidacion.getEgreso().getIntSudeIdSubsucursal());
		estadoLiquidacion.setIntPersUsuarioEstado(expedienteLiquidacion.getEgreso().getIntPersPersonaUsuario());		
		
		return estadoLiquidacion;
	}
	
	private Movimiento generarMovimiento(ExpedienteLiquidacion expediente, BigDecimal bdMontoCtaXPagar, Cuenta cuenta, Egreso egreso) throws Exception{
		Movimiento movimiento = new Movimiento();		
		movimiento.setTsFechaMovimiento(expediente.getEgreso().getTsFechaProceso());
		movimiento.setIntPersEmpresa(expediente.getId().getIntPersEmpresaPk());
		movimiento.setIntCuenta(cuenta.getId().getIntCuenta());
		movimiento.setIntPersPersonaIntegrante(expediente.getEgreso().getIntPersPersonaGirado());
		movimiento.setIntItemCuentaConcepto(null);
		movimiento.setIntItemExpediente(expediente.getId().getIntItemExpediente());
		movimiento.setIntItemExpedienteDetalle(null);
		movimiento.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_CUENTAS_POR_PAGAR);		
		movimiento.setIntParaTipoMovimiento(Constante.PARAM_T_MOVIMIENTOCTACTE_EGRESOCAJA);
		movimiento.setIntParaDocumentoGeneral(expediente.getIntParaDocumentoGeneral());
		movimiento.setStrSerieDocumento(null);
		movimiento.setStrNumeroDocumento(""+expediente.getId().getIntItemExpediente());
		movimiento.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_CARGO);
		movimiento.setBdMontoMovimiento(bdMontoCtaXPagar);
		movimiento.setBdMontoSaldo(BigDecimal.ZERO);
		movimiento.setIntPersEmpresaUsuario(expediente.getEgreso().getIntPersEmpresaUsuario());
		movimiento.setIntPersPersonaUsuario(expediente.getEgreso().getIntPersPersonaUsuario());
		//jchavez 20.05.2014
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
//
//	
//	private BeneficiarioPrevision obtenerBeneficiarioSeleccionado(ExpedienteLiquidacion expedienteLiquidacion){
//		/*for(BeneficiarioPrevision beneficiarioPrevision : expedienteLiquidacion.getListaBeneficiarioPrevision()){
//			if(beneficiarioPrevision.getIntPersPersonaBeneficiario().equals(expedienteLiquidacion.getEgreso().getIntPersPersonaBeneficiario())){
//				return beneficiarioPrevision;
//			}
//		}*/
//		return null;
//	}
	
	
	public ExpedienteLiquidacion grabarGiroLiquidacion(ExpedienteLiquidacion expedienteLiquidacion)throws BusinessException{
		try{
			EgresoFacadeRemote egresoFacade = (EgresoFacadeRemote) EJBFactory.getRemote(EgresoFacadeRemote.class);
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			Egreso egreso = expedienteLiquidacion.getEgreso();
			log.info("Expediente Liquidacion recibido: "+expedienteLiquidacion);
			log.info("Egreso recibido: "+egreso);
			
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				log.info("Egreso Detalle recibido: "+egresoDetalle);
			}
			log.info("Libro Diario recibido: "+egreso.getLibroDiario());
			for(LibroDiarioDetalle libroDiarioDetalle : egreso.getLibroDiario().getListaLibroDiarioDetalle()){
				log.info("Libro Diario Detalle recibido: "+libroDiarioDetalle);
			}
			
			egreso.setBlnEsGiroPorSedeCentral(false);
			egreso = egresoFacade.grabarEgresoParaGiroPrestamo(egreso);
			BigDecimal bdMontoCtaXPagar = BigDecimal.ZERO;
			for(EgresoDetalleInterfaz egresoDetalleInterfaz : expedienteLiquidacion.getListaEgresoDetalleInterfaz()){
				bdMontoCtaXPagar = bdMontoCtaXPagar.add(egresoDetalleInterfaz.getBdSubTotal()!=null?egresoDetalleInterfaz.getBdSubTotal():BigDecimal.ZERO);
			}
			//para giro por cheque
			if (expedienteLiquidacion.getCuentaLiquidar()==null) {
				expedienteLiquidacion.setCuentaLiquidar(expedienteLiquidacion.getListaEgresoDetalleInterfaz().get(0).getExpedienteLiquidacionDetalle().getCuenta());
			}
			Movimiento movimiento = generarMovimiento(expedienteLiquidacion, bdMontoCtaXPagar, expedienteLiquidacion.getCuentaLiquidar(), egreso);
			log.info(movimiento);
			movimiento = conceptoFacade.grabarMovimiento(movimiento);
			//Agregado 20.05.2014 jchavez Modifica el valor del saldo de la cuenta por pagar
			Movimiento movCtaPorPagar = conceptoFacade.getMovimientoPorPK(movimiento.getIntItemMovimientoRel());
			movCtaPorPagar.setBdMontoSaldo(movCtaPorPagar.getBdMontoSaldo().subtract(movimiento.getBdMontoMovimiento()));
			conceptoFacade.modificarMovimiento(movCtaPorPagar);
			
			boolean existeBeneficiarioPorGirar = Boolean.FALSE;
			for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()){
				for(BeneficiarioLiquidacion beneficiarioLiquidacion : expedienteLiquidacionDetalle.getListaBeneficiarioLiquidacion()){
					if(beneficiarioLiquidacion.getIntPersPersonaBeneficiario().equals(egreso.getIntPersPersonaBeneficiario())){
						beneficiarioLiquidacion.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
						beneficiarioLiquidacion.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
						log.info(boBeneficiarioLiquidacion);
						boBeneficiarioLiquidacion.modificar(beneficiarioLiquidacion);
					}
					if(beneficiarioLiquidacion.getIntItemEgresoGeneral()==null){
						existeBeneficiarioPorGirar = Boolean.TRUE;
					}
				}
			}
			
			if(!existeBeneficiarioPorGirar){
				EstadoLiquidacion estadoLiquidacionGirar = generarEstadoLiquidacion(expedienteLiquidacion);
				log.info(estadoLiquidacionGirar);
				boEstadoLiquidacion.grabar(estadoLiquidacionGirar);
				
				for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()){
					Cuenta cuenta = expedienteLiquidacionDetalle.getCuenta();
					cuenta.setIntParaSituacionCuentaCod(Constante.PARAM_T_SITUACIONCUENTA_LIQUIDADO);
					cuentaFacade.modificarCuenta(cuenta);
				}
			}
			
		 	
		}catch(Exception e){
			throw new BusinessException(e);
		}		
		return expedienteLiquidacion;
	}
	
	/**
	 * grabacion del giro de liquidacion por fondo fijo o banco (TESORERIA)
	 * @param expedienteLiquidacion
	 * @return
	 * @throws BusinessException
	 */
	public ExpedienteLiquidacion grabarGiroLiquidacionPorTesoreria(ExpedienteLiquidacion expedienteLiquidacion)throws BusinessException{
		try{
			EgresoFacadeRemote egresoFacade = (EgresoFacadeRemote) EJBFactory.getRemote(EgresoFacadeRemote.class);
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			Egreso egreso = expedienteLiquidacion.getEgreso();
			log.info("Expediente Liquidacion recibido: "+expedienteLiquidacion);
			log.info("Egreso recibido: "+egreso);
			
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				log.info("Egreso Detalle recibido: "+egresoDetalle);
			}
			log.info("Libro Diario recibido: "+egreso.getLibroDiario());
			for(LibroDiarioDetalle libroDiarioDetalle : egreso.getLibroDiario().getListaLibroDiarioDetalle()){
				log.info("Libro Diario Detalle recibido: "+libroDiarioDetalle);
			}
			
			egreso.setBlnEsGiroPorSedeCentral(true);
			egreso = egresoFacade.grabarEgresoParaGiroPrestamo(egreso);

			/* Autor: jchavez / Tarea: Modificación / Fecha: 11.09.2014
			   Se setea  el egreso grabado en el expediente liquidacion*/
			expedienteLiquidacion.setEgreso(egreso);
			
			BeneficiarioLiquidacion beneficiarioLiquidacionSeleccionado = expedienteLiquidacion.getBeneficiarioLiquidacionGirar(); //obtenerBeneficiarioSeleccionado(expedienteLiquidacion);
			beneficiarioLiquidacionSeleccionado.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
			beneficiarioLiquidacionSeleccionado.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
			log.info(beneficiarioLiquidacionSeleccionado);
			boBeneficiarioLiquidacion.modificar(beneficiarioLiquidacionSeleccionado);
			
			BigDecimal bdMontoCtaXPagar = BigDecimal.ZERO;
			for(EgresoDetalleInterfaz egresoDetalleInterfaz : expedienteLiquidacion.getListaEgresoDetalleInterfaz()){
				bdMontoCtaXPagar = bdMontoCtaXPagar.add(egresoDetalleInterfaz.getBdSubTotal()!=null?egresoDetalleInterfaz.getBdSubTotal():BigDecimal.ZERO);
			}
			//para giro por cheque
			if (expedienteLiquidacion.getCuentaLiquidar()==null) {
				expedienteLiquidacion.setCuentaLiquidar(expedienteLiquidacion.getListaEgresoDetalleInterfaz().get(0).getExpedienteLiquidacionDetalle().getCuenta());
			}
			Movimiento movimiento = generarMovimiento(expedienteLiquidacion, bdMontoCtaXPagar, expedienteLiquidacion.getCuentaLiquidar(),egreso);
			log.info(movimiento);
//			conceptoFacade.grabarMovimiento(movimiento);
			movimiento = conceptoFacade.grabarMovimiento(movimiento);
			
			Movimiento movCtaPorPagar = conceptoFacade.getMovimientoPorPK(movimiento.getIntItemMovimientoRel());
			movCtaPorPagar.setBdMontoSaldo(movCtaPorPagar.getBdMontoSaldo().subtract(movimiento.getBdMontoMovimiento()));
			conceptoFacade.modificarMovimiento(movCtaPorPagar);
			
			boolean existeBeneficiarioPorGirar = Boolean.FALSE;
			for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()){
				for(BeneficiarioLiquidacion beneficiarioLiquidacion : expedienteLiquidacionDetalle.getListaBeneficiarioLiquidacion()){
					if(beneficiarioLiquidacion.getIntPersPersonaBeneficiario().equals(egreso.getIntPersPersonaBeneficiario())){
						beneficiarioLiquidacion.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
						beneficiarioLiquidacion.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
						log.info(boBeneficiarioLiquidacion);
						boBeneficiarioLiquidacion.modificar(beneficiarioLiquidacion);
					}
					if(beneficiarioLiquidacion.getIntItemEgresoGeneral()==null){
						existeBeneficiarioPorGirar = Boolean.TRUE;
					}
				}
			}
			
			if(!existeBeneficiarioPorGirar){
				EstadoLiquidacion estadoLiquidacionGirar = generarEstadoLiquidacion(expedienteLiquidacion);
				log.info(estadoLiquidacionGirar);
				boEstadoLiquidacion.grabar(estadoLiquidacionGirar);
				
				for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()){
					Cuenta cuenta = expedienteLiquidacionDetalle.getCuenta();
					cuenta.setIntParaSituacionCuentaCod(Constante.PARAM_T_SITUACIONCUENTA_LIQUIDADO);
					cuentaFacade.modificarCuenta(cuenta);
				}
			}	
			

			
//			//Agregado 20.05.2014 jchavez Modifica el valor del saldo de la cuenta por pagar
//			BigDecimal bdPorcentaje = beneficiarioLiquidacionSeleccionado.getBdPorcentajeBeneficio().divide(new BigDecimal(100));
//			movimiento.setBdMontoMovimiento(movimiento.getBdMontoMovimiento().multiply(bdPorcentaje).setScale(2,BigDecimal.ROUND_HALF_UP));
//			Movimiento movCtaPorPagar = conceptoFacade.getMovimientoPorPK(movimiento.getIntItemMovimientoRel());
//			movCtaPorPagar.setBdMontoSaldo(movCtaPorPagar.getBdMontoSaldo().subtract(movimiento.getBdMontoMovimiento()));
//			conceptoFacade.modificarMovimiento(movCtaPorPagar);
//			
//			boolean existeBeneficiarioPorGirar = Boolean.FALSE;
//			for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()){
//				for(BeneficiarioLiquidacion beneficiarioLiquidacion : expedienteLiquidacionDetalle.getListaBeneficiarioLiquidacion()){
//					if(beneficiarioLiquidacion.getIntPersPersonaBeneficiario().equals(egreso.getIntPersPersonaBeneficiario())){
//						beneficiarioLiquidacion.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
//						beneficiarioLiquidacion.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
//						log.info(boBeneficiarioLiquidacion);
//						boBeneficiarioLiquidacion.modificar(beneficiarioLiquidacion);
//					}
//					if(beneficiarioLiquidacion.getIntItemEgresoGeneral()==null){
//						existeBeneficiarioPorGirar = Boolean.TRUE;
//					}
//				}
//			}
//			
//			if(!existeBeneficiarioPorGirar){
//				EstadoLiquidacion estadoLiquidacionGirar = generarEstadoLiquidacion(expedienteLiquidacion);
//				log.info(estadoLiquidacionGirar);
//				boEstadoLiquidacion.grabar(estadoLiquidacionGirar);
//				
//				for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()){
//					Cuenta cuenta = expedienteLiquidacionDetalle.getCuenta();
//					cuenta.setIntParaSituacionCuentaCod(Constante.PARAM_T_SITUACIONCUENTA_LIQUIDADO);
//					cuentaFacade.modificarCuenta(cuenta);
//				}
//			}
			
		 	
		}catch(Exception e){
			throw new BusinessException(e);
		}		
		return expedienteLiquidacion;
	}
	
//	private BeneficiarioLiquidacion obtenerBeneficiarioSeleccionado(ExpedienteLiquidacion expedienteLiquidacion){
//		for(BeneficiarioLiquidacion beneficiarioLiquidacion : expedienteLiquidacion.getbe ListaBeneficiarioLiquidacion()){
//			if(beneficiarioLiquidacion.getIntPersPersonaBeneficiario().equals(expedienteLiquidacion.getEgreso().getIntPersPersonaBeneficiario())){
//				return beneficiarioLiquidacion;
//			}
//		}
//		return null;
//	}
}