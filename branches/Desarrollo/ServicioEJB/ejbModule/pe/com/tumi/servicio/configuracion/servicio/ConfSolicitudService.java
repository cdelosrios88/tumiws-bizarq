package pe.com.tumi.servicio.configuracion.servicio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.domain.composite.UsuarioComp;
import pe.com.tumi.seguridad.login.facade.LoginFacadeRemote;
import pe.com.tumi.servicio.configuracion.bo.ConfServCanceladoBO;
import pe.com.tumi.servicio.configuracion.bo.ConfServCaptacionBO;
import pe.com.tumi.servicio.configuracion.bo.ConfServCreditoBO;
import pe.com.tumi.servicio.configuracion.bo.ConfServCreditoEmpresaBO;
import pe.com.tumi.servicio.configuracion.bo.ConfServDetalleBO;
import pe.com.tumi.servicio.configuracion.bo.ConfServEstructuraDetalleBO;
import pe.com.tumi.servicio.configuracion.bo.ConfServGrupoCtaBO;
import pe.com.tumi.servicio.configuracion.bo.ConfServPerfilBO;
import pe.com.tumi.servicio.configuracion.bo.ConfServRolBO;
import pe.com.tumi.servicio.configuracion.bo.ConfServSolicitudBO;
import pe.com.tumi.servicio.configuracion.bo.ConfServUsuarioBO;
import pe.com.tumi.servicio.configuracion.domain.ConfServCancelado;
import pe.com.tumi.servicio.configuracion.domain.ConfServCaptacion;
import pe.com.tumi.servicio.configuracion.domain.ConfServCredito;
import pe.com.tumi.servicio.configuracion.domain.ConfServCreditoEmpresa;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServGrupoCta;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;
import pe.com.tumi.servicio.configuracion.domain.ConfServRol;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuario;


public class ConfSolicitudService {

	protected static Logger log = Logger.getLogger(ConfSolicitudService.class);
	
	ConfServSolicitudBO boConfServSolicitud = (ConfServSolicitudBO)TumiFactory.get(ConfServSolicitudBO.class);
	ConfServRolBO boConfServRol = (ConfServRolBO)TumiFactory.get(ConfServRolBO.class);
	ConfServGrupoCtaBO boConfServGrupoCta = (ConfServGrupoCtaBO)TumiFactory.get(ConfServGrupoCtaBO.class);
	ConfServEstructuraDetalleBO boConfServEstructuraDetalle = (ConfServEstructuraDetalleBO)TumiFactory.get(ConfServEstructuraDetalleBO.class);
	ConfServDetalleBO boConfServDetalle = (ConfServDetalleBO)TumiFactory.get(ConfServDetalleBO.class);
	ConfServCreditoBO boConfServCredito = (ConfServCreditoBO)TumiFactory.get(ConfServCreditoBO.class);
	ConfServCaptacionBO boConfServCaptacion = (ConfServCaptacionBO)TumiFactory.get(ConfServCaptacionBO.class);
	ConfServPerfilBO boConfServPerfil = (ConfServPerfilBO)TumiFactory.get(ConfServPerfilBO.class);
	ConfServUsuarioBO boConfServUsuario = (ConfServUsuarioBO)TumiFactory.get(ConfServUsuarioBO.class);
	ConfServCreditoEmpresaBO boConfServCreditoEmpresa = (ConfServCreditoEmpresaBO)TumiFactory.get(ConfServCreditoEmpresaBO.class);
	ConfServCanceladoBO boConfServCancelado = (ConfServCanceladoBO)TumiFactory.get(ConfServCanceladoBO.class);
	
	public ConfServSolicitud grabarAutorizacion(ConfServSolicitud confServSolicitud) throws BusinessException {
		try{
			log.info(confServSolicitud);
			List<ConfServRol> listaConfServRol = confServSolicitud.getListaRol();
			List<ConfServPerfil> listaConfServPerfil = confServSolicitud.getListaPerfil();
			List<ConfServUsuario> listaConfServUsuario = confServSolicitud.getListaUsuario();
			List<ConfServCreditoEmpresa> listaConfServCreditoEmpresa = confServSolicitud.getListaCreditoEmpresa();
			List<ConfServCancelado> listaConfServCancelado = confServSolicitud.getListaCancelado();
			
			confServSolicitud = boConfServSolicitud.grabar(confServSolicitud);
			
			for(ConfServRol confServRol : listaConfServRol){
				confServRol.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
				log.info(confServRol);
				boConfServRol.grabar(confServRol);
			}
			
			if(listaConfServPerfil != null){
				for(ConfServPerfil confServPerfil : listaConfServPerfil){
					confServPerfil.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
					log.info(confServPerfil);
					boConfServPerfil.grabar(confServPerfil);
				}
				
			}
			
			if(listaConfServUsuario != null){
				for(ConfServUsuario confServUsuario : listaConfServUsuario){
					confServUsuario.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
					log.info(confServUsuario);
					boConfServUsuario.grabar(confServUsuario);
				}
				
			}
			
			if(listaConfServCreditoEmpresa != null){
				for(ConfServCreditoEmpresa confServCreditoEmpresa : listaConfServCreditoEmpresa){
					confServCreditoEmpresa.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
					log.info(confServCreditoEmpresa);
					boConfServCreditoEmpresa.grabar(confServCreditoEmpresa);
				}
			}
			
			if(listaConfServCancelado != null){
				for(ConfServCancelado confServCancelado : listaConfServCancelado){
					confServCancelado.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
					log.info(confServCancelado);
					boConfServCancelado.grabar(confServCancelado);
				}
			}
			
		}catch(BusinessException e){
			log.error(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return confServSolicitud;
	}
	
	public ConfServSolicitud grabarLiquidacionCuenta(ConfServSolicitud confServSolicitud) throws BusinessException {
		try{
			log.info(confServSolicitud);
			List<ConfServRol> listaConfServRol = confServSolicitud.getListaRol();
			List<ConfServGrupoCta> listaConfServGrupoCta = confServSolicitud.getListaGrupoCta();
			List<ConfServEstructuraDetalle> listaConfServEstructuraDetalle = confServSolicitud.getListaEstructuraDetalle();
			List<ConfServDetalle> listaConfServDetalle = confServSolicitud.getListaDetalle();
			confServSolicitud = boConfServSolicitud.grabar(confServSolicitud);
			
			for(ConfServRol confServRol : listaConfServRol){
				confServRol.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
				log.info(confServRol);
				boConfServRol.grabar(confServRol);
			}
			
			for(ConfServGrupoCta confServGrupoCta : listaConfServGrupoCta){
				confServGrupoCta.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
				log.info(confServGrupoCta);
				boConfServGrupoCta.grabar(confServGrupoCta);
			}
			
			for(ConfServEstructuraDetalle confServEstructuraDetalle : listaConfServEstructuraDetalle){
				confServEstructuraDetalle.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
				log.info(confServEstructuraDetalle);
				boConfServEstructuraDetalle.grabar(confServEstructuraDetalle);
			}
			
			for(ConfServDetalle confServDetalle : listaConfServDetalle){
				confServDetalle.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
				log.info(confServDetalle);
				boConfServDetalle.grabar(confServDetalle);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return confServSolicitud;
	}
	
	public ConfServSolicitud grabarCredito(ConfServSolicitud confServSolicitud) throws BusinessException {
		try{
			confServSolicitud = grabarLiquidacionCuenta(confServSolicitud);
			List<ConfServCredito> listaConfServCredito = confServSolicitud.getListaCredito();
			for(ConfServCredito confServCredito : listaConfServCredito){
				confServCredito.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
				log.info(confServCredito);
				boConfServCredito.grabar(confServCredito);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return confServSolicitud;
	}
	
	public ConfServSolicitud grabarCaptacion(ConfServSolicitud confServSolicitud) throws BusinessException {
		try{
			confServSolicitud = grabarLiquidacionCuenta(confServSolicitud);
			List<ConfServCaptacion> listaConfServCaptacion = confServSolicitud.getListaCaptacion();
			for(ConfServCaptacion confServCaptacion: listaConfServCaptacion){
				confServCaptacion.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
				log.info(confServCaptacion);
				boConfServCaptacion.grabar(confServCaptacion);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return confServSolicitud;
	}
	
	
	
	public ConfServSolicitud modificarLiquidacionCuenta(ConfServSolicitud confServSolicitud) throws BusinessException {
		try{
			log.info(confServSolicitud);
			Integer intPersPersonaPk = null;
			List<ConfServRol> listaConfServRol = confServSolicitud.getListaRol();
			List<ConfServGrupoCta> listaConfServGrupoCta = confServSolicitud.getListaGrupoCta();
			List<ConfServEstructuraDetalle> listaConfServEstructuraDetalle = confServSolicitud.getListaEstructuraDetalle();
			List<ConfServDetalle> listaConfServDetalle = confServSolicitud.getListaDetalle();
			confServSolicitud = boConfServSolicitud.modificar(confServSolicitud);
			
			for(ConfServRol confServRol : listaConfServRol){
				confServRol.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
				//log.info(confServRol);
				boConfServRol.modificar(confServRol);
			}
			
			for(ConfServGrupoCta confServGrupoCta : listaConfServGrupoCta){
				confServGrupoCta.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
				//log.info(confServGrupoCta);
				boConfServGrupoCta.modificar(confServGrupoCta);
			}

			//Manejamos listaConfServEstructura
			//1ero grabamos todos las confServEstructura nuevas que hemos agregado 
			List<ConfServEstructuraDetalle> listaConfServEstructuraAux = new ArrayList<ConfServEstructuraDetalle>();
			List<ConfServEstructuraDetalle> listaConfServEstructuraBD = getListaConfServEstructuraDetallePorCabecera(confServSolicitud);
			for(ConfServEstructuraDetalle confServEstructura : listaConfServEstructuraDetalle){
				if(confServEstructura.getId().getIntItemEstructura()==null){
					confServEstructura.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
					boConfServEstructuraDetalle.grabar(confServEstructura);
				}else{
					listaConfServEstructuraAux.add(confServEstructura);
				}				
			}
			listaConfServEstructuraDetalle = listaConfServEstructuraAux;			
			//2do actualizamos las confServEstructura que han sido deseleccionados en la interfaz (IU)
			boolean seEncuentraEnIU = Boolean.FALSE;
			for(ConfServEstructuraDetalle confServEstructuraBD : listaConfServEstructuraBD){
				seEncuentraEnIU = Boolean.FALSE;
				for(ConfServEstructuraDetalle confServEstructura : listaConfServEstructuraDetalle){
					if(confServEstructuraBD.getId().getIntItemEstructura().equals(confServEstructura.getId().getIntItemEstructura())){
						seEncuentraEnIU = Boolean.TRUE;
						break;
					}else{
						seEncuentraEnIU = Boolean.FALSE;
					}
				}
				if(!seEncuentraEnIU){
					confServEstructuraBD.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
					boConfServEstructuraDetalle.modificar(confServEstructuraBD);
				}
			}

			//Manejamos listaConfServDetalle
			//1ero grabamos todos las confServDetalle nuevas que hemos agregado 
			List<ConfServDetalle> listaConfServDetalleAux = new ArrayList<ConfServDetalle>();
			List<ConfServDetalle> listaConfServDetalleBD = getListaConfServDetallePorCabecera(confServSolicitud);			
			for(ConfServDetalle confServDetalle : listaConfServDetalle){
				if(confServDetalle.getId().getIntItemDetalle()==null){
					confServDetalle.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
					intPersPersonaPk = confServDetalle.getIntPersPersonaUsuarioPk();
					boConfServDetalle.grabar(confServDetalle);
				}else{
					listaConfServDetalleAux.add(confServDetalle);
				}
			}
			listaConfServDetalle = listaConfServDetalleAux;			
			//2do actualizamos las ConfServDetalle que han sido deseleccionados en la interfaz (IU)
			for(ConfServDetalle confServDetalleBD : listaConfServDetalleBD){
				seEncuentraEnIU = Boolean.FALSE;
				for(ConfServDetalle confServDetalle : listaConfServDetalle){
					if(confServDetalleBD.getId().getIntItemDetalle().equals(confServDetalle.getId().getIntItemDetalle())){
						seEncuentraEnIU = Boolean.TRUE;
						break;
					}else{
						seEncuentraEnIU = Boolean.FALSE;
					}
				}
				if(!seEncuentraEnIU){
					confServDetalleBD.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
					confServDetalleBD.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
					confServDetalleBD.setIntPersPersonaEliminaPk(intPersPersonaPk);
					boConfServDetalle.modificar(confServDetalleBD);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return confServSolicitud;
	}
	
	public ConfServSolicitud modificarCredito(ConfServSolicitud confServSolicitud) throws BusinessException {
		try{
			Integer intPersPersonaPk = null;
			confServSolicitud = modificarLiquidacionCuenta(confServSolicitud);
			List<ConfServCredito> listaConfServCredito = confServSolicitud.getListaCredito();
			//Manejamos listaConfServCredito
			//1ero grabamos todos las confServCredito nuevas que hemos agregado 
			List<ConfServCredito> listaConfServCreditoAux = new ArrayList<ConfServCredito>();
			List<ConfServCredito> listaConfServCreditBD = getListaConfServCreditoPorCabecera(confServSolicitud);			
			for(ConfServCredito confServCredito : listaConfServCredito){
				if(confServCredito.getId().getIntItemSolicitud()==null){
					confServCredito.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
					intPersPersonaPk = confServCredito.getIntPersPersonaUsuarioPk();
					boConfServCredito.grabar(confServCredito);
				}else{
					listaConfServCreditoAux.add(confServCredito);
				}
			}
			listaConfServCredito = listaConfServCreditoAux;			
			//2do actualizamos las ConfServCredito que han sido deseleccionados en la interfaz (IU)
			
			boolean seEncuentraEnIU = Boolean.FALSE;
			for(ConfServCredito confServCreditoBD : listaConfServCreditBD){
				seEncuentraEnIU = Boolean.FALSE;
				for(ConfServCredito confServCredito : listaConfServCredito){
					if(confServCreditoBD.getId().getIntItemCredito().equals(confServCredito.getId().getIntItemCredito())){
						seEncuentraEnIU = Boolean.TRUE;
						break;
					}else{
						seEncuentraEnIU = Boolean.FALSE;
					}
				}
				if(!seEncuentraEnIU){
					confServCreditoBD.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
					confServCreditoBD.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
					confServCreditoBD.setIntPersPersonaEliminaPk(intPersPersonaPk);
					boConfServCredito.modificar(confServCreditoBD);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return confServSolicitud;
	}
	
	public ConfServSolicitud modificarCaptacion(ConfServSolicitud confServSolicitud) throws BusinessException {
		try{
			Integer intPersPersonaPk = null;
			confServSolicitud = modificarLiquidacionCuenta(confServSolicitud);
			List<ConfServCaptacion> listaConfServCaptacion = confServSolicitud.getListaCaptacion();
			//Manejamos listaConfServCaptacion
			//1ero grabamos todos las confServCaptacion nuevas que hemos agregado 
			List<ConfServCaptacion> listaConfServCaptacionAux = new ArrayList<ConfServCaptacion>();
			List<ConfServCaptacion> listaConfServCaptacionBD = getListaConfServCaptacionPorCabecera(confServSolicitud);			
			for(ConfServCaptacion confServCaptacion : listaConfServCaptacion){
				if(confServCaptacion.getId().getIntItemSolicitud()==null){
					confServCaptacion.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
					intPersPersonaPk = confServCaptacion.getIntPersPersonaUsuarioPk();
					boConfServCaptacion.grabar(confServCaptacion);
				}else{
					listaConfServCaptacionAux.add(confServCaptacion);
				}
			}
			listaConfServCaptacion = listaConfServCaptacionAux;			
			//2do actualizamos las ConfServCaptacion que han sido deseleccionados en la interfaz (IU)			
			boolean seEncuentraEnIU = Boolean.FALSE;
			for(ConfServCaptacion confServCaptacionBD : listaConfServCaptacionBD){
				seEncuentraEnIU = Boolean.FALSE;
				for(ConfServCaptacion confServCaptacion : listaConfServCaptacion){
					if(confServCaptacionBD.getId().getIntItemCaptacion().equals(confServCaptacion.getId().getIntItemCaptacion())){
						seEncuentraEnIU = Boolean.TRUE;
						break;
					}else{
						seEncuentraEnIU = Boolean.FALSE;
					}
				}
				if(!seEncuentraEnIU){
					confServCaptacionBD.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
					confServCaptacionBD.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
					confServCaptacionBD.setIntPersPersonaEliminaPk(intPersPersonaPk);
					boConfServCaptacion.modificar(confServCaptacionBD);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return confServSolicitud;
	}
	
	public List<ConfServSolicitud> buscarConfSolicitudRequisito(ConfServSolicitud confServSolicitud, Date fechaFiltroInicio, Date fechaFiltroFin,
			Integer tipoCuentaFiltro) 
		throws BusinessException {
		
		List<ConfServSolicitud> listaConfServSolicitud = null;
		try{
			//log.info("tipoCuentaFiltro:"+tipoCuentaFiltro);
			List<ConfServSolicitud> listaConfServSolicitudAux = new ArrayList<ConfServSolicitud>();
			List<ConfServEstructuraDetalle> listaConfServEstructuraDetalle = null;
			List<ConfServDetalle> listaConfServDetalle = null;
			List <ConfServGrupoCta> listaConfServGrupoCta = null;
			List <String> listaRazonSocialEstructura = new ArrayList<String>();
			String razonSocialEstructura = "";
			
			listaConfServSolicitud = boConfServSolicitud.buscarRequisito(confServSolicitud,fechaFiltroInicio,fechaFiltroFin);
			boolean cumpleFiltroTipoCuenta = Boolean.FALSE;
			for(ConfServSolicitud confServSolicitudAux : listaConfServSolicitud){
				//log.info("encuentra : "+confServSolicitudAux);
				cumpleFiltroTipoCuenta = Boolean.FALSE;
				listaConfServGrupoCta = boConfServGrupoCta.getPorCabecera(confServSolicitudAux.getId().getIntPersEmpresaPk(),confServSolicitudAux.getId().getIntItemSolicitud());
				for(ConfServGrupoCta confServGrupoCta : listaConfServGrupoCta){
					if(confServGrupoCta.getId().getIntParaTipoCuentaCod().equals(tipoCuentaFiltro) && confServGrupoCta.getIntValor().equals(1)){
						cumpleFiltroTipoCuenta = Boolean.TRUE;
						break;
					}
				}
				//log.info("cumpleFiltroTipoCuenta:"+cumpleFiltroTipoCuenta);
				if(cumpleFiltroTipoCuenta){
					listaConfServEstructuraDetalle = boConfServEstructuraDetalle.getPorCabecera(confServSolicitudAux.getId().getIntPersEmpresaPk(),confServSolicitudAux.getId().getIntItemSolicitud());
					listaConfServDetalle = boConfServDetalle.getPorCabecera(confServSolicitudAux.getId().getIntPersEmpresaPk(),confServSolicitudAux.getId().getIntItemSolicitud());
					confServSolicitudAux.setListaEstructuraDetalle(listaConfServEstructuraDetalle);
					confServSolicitudAux.setListaDetalle(listaConfServDetalle);
					confServSolicitudAux = cargarEstructuras(confServSolicitudAux);
					listaRazonSocialEstructura = new ArrayList<String>();
					//Agrega la listaRazonSocialEstructura asegurandose que no se repita la razon social. Se muestra en la grilla de busqueda.
					for(Object o : confServSolicitudAux.getListaEstructuraDetalle()){
						ConfServEstructuraDetalle confServEstructuraDetalle = (ConfServEstructuraDetalle)o;						
						razonSocialEstructura = confServEstructuraDetalle.getEstructura().getJuridica().getStrRazonSocial();
						//log.info("razonSocialEstructura:"+razonSocialEstructura);
						if(!listaRazonSocialEstructura.contains(razonSocialEstructura)){
							listaRazonSocialEstructura.add(razonSocialEstructura);							
						}
					}
					confServSolicitudAux.setListaRazonSocialEstructura(listaRazonSocialEstructura);
					
					confServSolicitudAux.setListaGrupoCta(listaConfServGrupoCta);
					listaConfServSolicitudAux.add(confServSolicitudAux);
				}				
			}
			listaConfServSolicitud = listaConfServSolicitudAux;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServSolicitud;
	}
	
	public List<ConfServSolicitud> buscarConfSolicitudAutorizacion(ConfServSolicitud confServSolicitud, Date fechaFiltroInicio, Date fechaFiltroFin,
			Integer tipoCuentaFiltro) 
		throws BusinessException, EJBFactoryException {
		
		List<ConfServSolicitud> listaConfServSolicitud = null;
		List<ConfServUsuario> listaConfServUsuario = new ArrayList<ConfServUsuario>();
		LoginFacadeRemote loginFacade = (LoginFacadeRemote) EJBFactory.getRemote(LoginFacadeRemote.class);
		try{
			//LoginFacadeRemote loginFacade = (LoginFacadeRemote)EJBFactory.getRemote(LoginFacadeRemote.class);
			UsuarioComp usuarioComp = new UsuarioComp();
			usuarioComp.setUsuario(new Usuario());
			
			List<ConfServSolicitud> listaConfServSolicitudAux = new ArrayList<ConfServSolicitud>();
			
			listaConfServSolicitud = boConfServSolicitud.buscar(confServSolicitud,fechaFiltroInicio,fechaFiltroFin);
			for(ConfServSolicitud confServSolicitudAux : listaConfServSolicitud){
				confServSolicitudAux.setListaPerfil(boConfServPerfil.getPorCabecera(confServSolicitudAux.getId().getIntPersEmpresaPk(), confServSolicitudAux.getId().getIntItemSolicitud()));
				confServSolicitudAux.setListaUsuario(boConfServUsuario.getPorCabecera(confServSolicitudAux.getId().getIntPersEmpresaPk(), confServSolicitudAux.getId().getIntItemSolicitud()));
				confServSolicitudAux.setListaRol(boConfServRol.getPorCabecera(confServSolicitudAux.getId().getIntPersEmpresaPk(), confServSolicitudAux.getId().getIntItemSolicitud()));
				confServSolicitudAux.setListaCreditoEmpresa(boConfServCreditoEmpresa.getPorCabecera(confServSolicitudAux.getId().getIntPersEmpresaPk(), confServSolicitudAux.getId().getIntItemSolicitud()));
				confServSolicitudAux.setListaCredito(boConfServCredito.getPorCabecera(confServSolicitudAux.getId().getIntPersEmpresaPk(), confServSolicitudAux.getId().getIntItemSolicitud()));
				
				System.out.println("CONF PERFIL ---> "+ confServSolicitudAux.getListaPerfil().size());
				System.out.println("CONF USUARIO ---> "+ confServSolicitudAux.getListaUsuario().size());
				System.out.println("CONF ROL ---> "+ confServSolicitudAux.getListaRol().size());
				System.out.println("CONF CREDITO EMPRESA ---> "+ confServSolicitudAux.getListaCreditoEmpresa().size());
				System.out.println("CONF CREDITO ---> "+ confServSolicitudAux.getListaCredito().size());
				/*
				if(confServSolicitudAux.getListaUsuario()!=null){
					//listaConfServUsuario.clear();
					for(ConfServUsuario confServUsuario : confServSolicitudAux.getListaUsuario()){
						usuarioComp.getUsuario().setIntPersPersonaPk(confServUsuario.getIntPersUsuarioPk());
						confServUsuario.setUsuarioComp((loginFacade.getListaUsuarioCompDeBusqueda(usuarioComp)).get(0));
						listaConfServUsuario.add(confServUsuario);
					}
					confServSolicitudAux.setListaUsuario(listaConfServUsuario);
				}
				if(confServSolicitudAux.getListaPerfil()!=null){
					//listaConfServUsuario.clear();
					for(ConfServPerfil confServPerfil : confServSolicitudAux.getListaPerfil()){
						usuarioComp.getUsuario().setIntPersPersonaPk(confServUsuario.getIntPersUsuarioPk());
						confServUsuario.setUsuarioComp((loginFacade.getListaUsuarioCompDeBusqueda(usuarioComp)).get(0));
						listaConfServUsuario.add(confServUsuario);
					}
					confServSolicitudAux.setListaUsuario(listaConfServUsuario);
				}
				*/
				log.info("setListaPerfil:"+confServSolicitudAux.getListaPerfil().size());
				log.info("setListaUsuario:"+confServSolicitudAux.getListaUsuario().size());
				listaConfServSolicitudAux.add(confServSolicitudAux);
			}
			listaConfServSolicitud = listaConfServSolicitudAux;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServSolicitud;
	}
	
	private ConfServSolicitud cargarEstructuras(ConfServSolicitud confServSolicitud) throws BusinessException{
		try{
			List<ConfServEstructuraDetalle> listaConfServEstructura = new ArrayList<ConfServEstructuraDetalle>();
			EstructuraDetalle estructuraDetalle = new EstructuraDetalle();
			estructuraDetalle.setId(new EstructuraDetalleId());
			EstructuraFacadeRemote estructuraFacade =(EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			Estructura estructura = null;
			for(ConfServEstructuraDetalle confServEstructura : confServSolicitud.getListaEstructuraDetalle()){
				estructura = (estructuraFacade.getListaEstructuraPorNivelYCodigo(confServEstructura.getIntNivelPk(), confServEstructura.getIntCodigoPk())).get(0);
				estructura.setDtFechaRegistro(null);
				confServEstructura.setEstructura(estructura);
				listaConfServEstructura.add(confServEstructura);
			}
			confServSolicitud.setListaEstructuraDetalle(listaConfServEstructura);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return confServSolicitud;
	}
	
	
	public List<ConfServDetalle> getListaConfServDetallePorCabecera(ConfServSolicitud confServSolicitud) throws BusinessException{
		List<ConfServDetalle> listaConfServDetalle = null;
		try{
			listaConfServDetalle = boConfServDetalle.getPorCabecera(confServSolicitud.getId().getIntPersEmpresaPk(),confServSolicitud.getId().getIntItemSolicitud());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServDetalle;
	}
	
	
	public List<ConfServRol> getListaConfServRolPorCabecera(ConfServSolicitud confServSolicitud) throws BusinessException{
		List<ConfServRol> listaConfServRol = null;
		try{
			listaConfServRol = boConfServRol.getPorCabecera(confServSolicitud.getId().getIntPersEmpresaPk(),confServSolicitud.getId().getIntItemSolicitud());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServRol;
	}
	
	public List<ConfServGrupoCta> getListaConfServGrupoCtaPorCabecera(ConfServSolicitud confServSolicitud) throws BusinessException{
		List<ConfServGrupoCta> listaConfServGrupoCta = null;
		try{
			listaConfServGrupoCta = boConfServGrupoCta.getPorCabecera(confServSolicitud.getId().getIntPersEmpresaPk(),confServSolicitud.getId().getIntItemSolicitud());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServGrupoCta;
	}
	
	public List<ConfServEstructuraDetalle> getListaConfServEstructuraDetallePorCabecera(ConfServSolicitud confServSolicitud) throws BusinessException{
		List<ConfServEstructuraDetalle> listaConfServEstructura = null;
		try{
			listaConfServEstructura = boConfServEstructuraDetalle.getPorCabecera(confServSolicitud.getId().getIntPersEmpresaPk(),confServSolicitud.getId().getIntItemSolicitud());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServEstructura;
	}
	
	public List<ConfServCredito> getListaConfServCreditoPorCabecera(ConfServSolicitud confServSolicitud) throws BusinessException{
		List<ConfServCredito> listaConfServCredito = null;
		try{
			listaConfServCredito = boConfServCredito.getPorCabecera(confServSolicitud.getId().getIntPersEmpresaPk(),confServSolicitud.getId().getIntItemSolicitud());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServCredito;
	}
	
	public List<ConfServCaptacion> getListaConfServCaptacionPorCabecera(ConfServSolicitud confServSolicitud) throws BusinessException{
		List<ConfServCaptacion> listaConfServCaptacion = null;
		try{
			listaConfServCaptacion = boConfServCaptacion.getPorCabecera(confServSolicitud.getId().getIntPersEmpresaPk(),confServSolicitud.getId().getIntItemSolicitud());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServCaptacion;
	}
	
	public List<ConfServCreditoEmpresa> getListaConfServCreditoEmpresaPorCabecera(ConfServSolicitud confServSolicitud) throws BusinessException{
		List<ConfServCreditoEmpresa> listaConfServCreditoEmpresa = null;
		try{
			listaConfServCreditoEmpresa = boConfServCreditoEmpresa.getPorCabecera(confServSolicitud.getId().getIntPersEmpresaPk(),confServSolicitud.getId().getIntItemSolicitud());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServCreditoEmpresa;
	}
	
	public List<ConfServCancelado> getListaConfServCanceladoPorCabecera(ConfServSolicitud confServSolicitud) throws BusinessException{
		List<ConfServCancelado> listaConfServCancelado = null;
		try{
			listaConfServCancelado = boConfServCancelado.getPorCabecera(confServSolicitud.getId().getIntPersEmpresaPk(),confServSolicitud.getId().getIntItemSolicitud());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServCancelado;
	}
	
	public List<ConfServPerfil> getListaConfServPerfilPorCabecera(ConfServSolicitud confServSolicitud) throws BusinessException{
		List<ConfServPerfil> listaConfServPerfil = null;
		try{
			listaConfServPerfil = boConfServPerfil.getPorCabecera(confServSolicitud.getId().getIntPersEmpresaPk(),confServSolicitud.getId().getIntItemSolicitud());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServPerfil;
	}
	
	public List<ConfServUsuario> getListaConfServUsuarioPorCabecera(ConfServSolicitud confServSolicitud) throws BusinessException{
		List<ConfServUsuario> listaConfServUsuario = null;
		try{
			listaConfServUsuario = boConfServUsuario.getPorCabecera(confServSolicitud.getId().getIntPersEmpresaPk(),confServSolicitud.getId().getIntItemSolicitud());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServUsuario;
	}
	
	public ConfServSolicitud modificarAutorizacion(ConfServSolicitud confServSolicitud) throws BusinessException {
		try{
			log.info(confServSolicitud);
			Integer intPersPersonaPk = null;
			List<ConfServRol> listaConfServRol = confServSolicitud.getListaRol();
			List<ConfServPerfil> listaConfServPerfil = confServSolicitud.getListaPerfil();
			List<ConfServUsuario> listaConfServUsuario = confServSolicitud.getListaUsuario();
			List<ConfServCreditoEmpresa> listaConfServCreditoEmpresa = confServSolicitud.getListaCreditoEmpresa();
			List<ConfServCancelado> listaConfServCancelado = confServSolicitud.getListaCancelado();
			confServSolicitud = boConfServSolicitud.modificar(confServSolicitud);
			
			for(ConfServRol confServRol : listaConfServRol){
				confServRol.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
				//log.info(confServRol);
				boConfServRol.modificar(confServRol);
			}
			
			//Manejamos listaConfServPerfil
			if(listaConfServPerfil != null){
				List<ConfServPerfil> listaConfServPerfilAux = new ArrayList<ConfServPerfil>();
				List<ConfServPerfil> listaConfServPerfilBD = getListaConfServPerfilPorCabecera(confServSolicitud);
				for(ConfServPerfil confServPerfil : listaConfServPerfil){
					if(confServPerfil.getId().getIntItemPerfil()==null){
						confServPerfil.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
						intPersPersonaPk = confServPerfil.getIntPersPersonaUsuarioPk();
						boConfServPerfil.grabar(confServPerfil);
					}else{
						listaConfServPerfilAux.add(confServPerfil);
					}
				}
				listaConfServPerfil = listaConfServPerfilAux;			
				//2do actualizamos las ConfServPerfil que han sido deseleccionados en la interfaz (IU)
				boolean seEncuentraEnIU = Boolean.FALSE;
				for(ConfServPerfil confServPerfilBD : listaConfServPerfilBD){
					seEncuentraEnIU = Boolean.FALSE;
					for(ConfServPerfil confServPerfil : listaConfServPerfil){
						if(confServPerfilBD.getId().getIntItemPerfil().equals(confServPerfil.getId().getIntItemPerfil())){
							seEncuentraEnIU = Boolean.TRUE;
							break;
						}else{
							seEncuentraEnIU = Boolean.FALSE;
						}
					}
					if(!seEncuentraEnIU){
						confServPerfilBD.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
						confServPerfilBD.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
						confServPerfilBD.setIntPersPersonaEliminaPk(intPersPersonaPk);
						boConfServPerfil.modificar(confServPerfilBD);
					}
				}
			}else{
				List<ConfServPerfil> listaConfServPerfilAux = getListaConfServPerfilPorCabecera(confServSolicitud);
				for(ConfServPerfil confServPerfilBD : listaConfServPerfilAux){
					confServPerfilBD.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
					confServPerfilBD.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
					confServPerfilBD.setIntPersPersonaEliminaPk(intPersPersonaPk);
					boConfServPerfil.modificar(confServPerfilBD);
				}
			}
			
			//Manejamos listaConfServUsuario
			if(listaConfServUsuario != null){
				List<ConfServUsuario> listaConfServUsuarioAux = new ArrayList<ConfServUsuario>();
				List<ConfServUsuario> listaConfServUsuarioBD = getListaConfServUsuarioPorCabecera(confServSolicitud);
				for(ConfServUsuario confServUsuario : listaConfServUsuario){
					if(confServUsuario.getId().getIntItemUsuario()==null){
						confServUsuario.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
						intPersPersonaPk = confServUsuario.getIntPersPersonaUsuarioPk();
						boConfServUsuario.grabar(confServUsuario);
					}else{
						listaConfServUsuarioAux.add(confServUsuario);
					}
				}
				listaConfServUsuario = listaConfServUsuarioAux;			
				//2do actualizamos las ConfServUsuario que han sido deseleccionados en la interfaz (IU)
				boolean seEncuentraEnIU = Boolean.FALSE;
				for(ConfServUsuario confServUsuarioBD : listaConfServUsuarioBD){
					seEncuentraEnIU = Boolean.FALSE;
					for(ConfServUsuario confServUsuario : listaConfServUsuario){
						if(confServUsuarioBD.getId().getIntItemUsuario().equals(confServUsuario.getId().getIntItemUsuario())){
							seEncuentraEnIU = Boolean.TRUE;
							break;
						}else{
							seEncuentraEnIU = Boolean.FALSE;
						}
					}
					if(!seEncuentraEnIU){
						confServUsuarioBD.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
						confServUsuarioBD.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
						confServUsuarioBD.setIntPersPersonaEliminaPk(intPersPersonaPk);
						boConfServUsuario.modificar(confServUsuarioBD);
					}
				}
			}else{
				List<ConfServUsuario> listaConfServUsuarioAux = getListaConfServUsuarioPorCabecera(confServSolicitud);
				for(ConfServUsuario confServUsuarioBD : listaConfServUsuarioAux){
					confServUsuarioBD.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
					confServUsuarioBD.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
					confServUsuarioBD.setIntPersPersonaEliminaPk(intPersPersonaPk);
					boConfServUsuario.modificar(confServUsuarioBD);
				}
			}
			
			//Manejamos listaConfServCreditoEmpresa
			if(listaConfServCreditoEmpresa != null){
				List<ConfServCreditoEmpresa> listaConfServCreditoEmpresaBD = getListaConfServCreditoEmpresaPorCabecera(confServSolicitud);
				for(ConfServCreditoEmpresa confServCreditoEmpresa : listaConfServCreditoEmpresa){
					for(ConfServCreditoEmpresa confServCreditoEmpresaBD : listaConfServCreditoEmpresaBD){
						if(confServCreditoEmpresa.getId().getIntParaTipoCreditoEmpresaCod().equals(confServCreditoEmpresaBD.getId().getIntParaTipoCreditoEmpresaCod())){
							confServCreditoEmpresaBD.setIntValor(confServCreditoEmpresa.getIntValor());
							//log.info(confServCreditoEmpresaBD);
							boConfServCreditoEmpresa.modificar(confServCreditoEmpresaBD);
							break;
						}
					}					
				}
			}else{
				listaConfServCreditoEmpresa = getListaConfServCreditoEmpresaPorCabecera(confServSolicitud);
				for(ConfServCreditoEmpresa confServCreditoEmpresa : listaConfServCreditoEmpresa){
					confServCreditoEmpresa.setIntValor(0);
					boConfServCreditoEmpresa.modificar(confServCreditoEmpresa);
				}
			}
			
			//Manejamos listaConfServCancelado
			if(listaConfServCancelado != null){
				List<ConfServCancelado> listaConfServCanceladoAux = new ArrayList<ConfServCancelado>();
				List<ConfServCancelado> listaConfServCanceladoBD = getListaConfServCanceladoPorCabecera(confServSolicitud);
				for(ConfServCancelado confServCancelado : listaConfServCancelado){
					if(confServCancelado.getId().getIntItemCancelado()==null){
						confServCancelado.getId().setIntItemSolicitud(confServSolicitud.getId().getIntItemSolicitud());
						intPersPersonaPk = confServCancelado.getIntPersPersonaUsuarioPk();
						boConfServCancelado.grabar(confServCancelado);
					}else{
						listaConfServCanceladoAux.add(confServCancelado);
					}
				}
				listaConfServCancelado = listaConfServCanceladoAux;			
				//2do actualizamos las ConfServUsuario que han sido deseleccionados en la interfaz (IU)
				boolean seEncuentraEnIU = Boolean.FALSE;
				for(ConfServCancelado confServCanceladoBD : listaConfServCanceladoBD){
					seEncuentraEnIU = Boolean.FALSE;
					for(ConfServCancelado confServUsuario : listaConfServCancelado){
						if(confServCanceladoBD.getId().getIntItemCancelado().equals(confServUsuario.getId().getIntItemCancelado())){
							seEncuentraEnIU = Boolean.TRUE;
							break;
						}else{
							seEncuentraEnIU = Boolean.FALSE;
						}
					}
					if(!seEncuentraEnIU){
						confServCanceladoBD.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
						confServCanceladoBD.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
						confServCanceladoBD.setIntPersPersonaEliminaPk(intPersPersonaPk);
						boConfServCancelado.modificar(confServCanceladoBD);
					}
				}
			}else{
				List<ConfServCancelado> listaConfServCanceladoAux = getListaConfServCanceladoPorCabecera(confServSolicitud);
				for(ConfServCancelado confServCanceladoBD : listaConfServCanceladoAux){
					confServCanceladoBD.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
					confServCanceladoBD.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
					confServCanceladoBD.setIntPersPersonaEliminaPk(intPersPersonaPk);
					boConfServCancelado.modificar(confServCanceladoBD);
				}
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return confServSolicitud;
	}
	
	public List<ConfServSolicitud> buscarConfSolicitudRequisitoOptimizado(ConfServSolicitud confServSolicitud, Integer tipoCuentaFiltro, Credito credito) throws BusinessException {
		
		List<ConfServSolicitud> listaConfServSolicitud = null;
		try{
			//log.info("tipoCuentaFiltro:"+tipoCuentaFiltro);
			List<ConfServSolicitud> listaConfServSolicitudAux = new ArrayList<ConfServSolicitud>();
			List<ConfServEstructuraDetalle> listaConfServEstructuraDetalle = null;
			List<ConfServDetalle> listaConfServDetalle = null;
			List <ConfServGrupoCta> listaConfServGrupoCta = null;
			//List <ConfServCredito> listaConfServCreditoTemp = null;
			List <ConfServCredito> listaConfServCredito = null;
			List <String> listaRazonSocialEstructura = new ArrayList<String>();
			String razonSocialEstructura = "";
			
			listaConfServSolicitud = boConfServSolicitud.getListaPorTipoOperacionTipoRequisito(confServSolicitud);
			boolean cumpleFiltroTipoCuenta = Boolean.FALSE;
			for(ConfServSolicitud confServSolicitudAux : listaConfServSolicitud){
				//log.info("encuentra : "+confServSolicitudAux);
				cumpleFiltroTipoCuenta = Boolean.FALSE;
				listaConfServGrupoCta = boConfServGrupoCta.getPorCabecera(confServSolicitudAux.getId().getIntPersEmpresaPk(),confServSolicitudAux.getId().getIntItemSolicitud());
				for(ConfServGrupoCta confServGrupoCta : listaConfServGrupoCta){
					if(confServGrupoCta.getId().getIntParaTipoCuentaCod().equals(tipoCuentaFiltro) && confServGrupoCta.getIntValor().equals(1)){
						cumpleFiltroTipoCuenta = Boolean.TRUE;
						break;
					}
				}
				//log.info("cumpleFiltroTipoCuenta:"+cumpleFiltroTipoCuenta);
				if(cumpleFiltroTipoCuenta){
					listaConfServEstructuraDetalle = boConfServEstructuraDetalle.getPorCabecera(confServSolicitudAux.getId().getIntPersEmpresaPk(),confServSolicitudAux.getId().getIntItemSolicitud());
					listaConfServDetalle = boConfServDetalle.getPorCabecera(confServSolicitudAux.getId().getIntPersEmpresaPk(),confServSolicitudAux.getId().getIntItemSolicitud());
					listaConfServCredito = boConfServCredito.getPorCabecera(confServSolicitudAux.getId().getIntPersEmpresaPk(), confServSolicitudAux.getId().getIntItemSolicitud());

					confServSolicitudAux.setListaEstructuraDetalle(listaConfServEstructuraDetalle);
					confServSolicitudAux.setListaDetalle(listaConfServDetalle);
					confServSolicitudAux = cargarEstructuras(confServSolicitudAux);
					listaRazonSocialEstructura = new ArrayList<String>();
					//Agrega la listaRazonSocialEstructura asegurandose que no se repita la razon social. Se muestra en la grilla de busqueda.
					for(Object o : confServSolicitudAux.getListaEstructuraDetalle()){
						ConfServEstructuraDetalle confServEstructuraDetalle = (ConfServEstructuraDetalle)o;						
						razonSocialEstructura = confServEstructuraDetalle.getEstructura().getJuridica().getStrRazonSocial();
						//log.info("razonSocialEstructura:"+razonSocialEstructura);
						if(!listaRazonSocialEstructura.contains(razonSocialEstructura)){
							listaRazonSocialEstructura.add(razonSocialEstructura);							
						}
					}
					confServSolicitudAux.setListaRazonSocialEstructura(listaRazonSocialEstructura);
					
					// se agregaron los reqautCredito
					if(listaConfServCredito != null){
						//for (ConfServCredito confServCredito : listaConfServCredito) {
							//listaConfServCredito = new ArrayList<ConfServCredito>();
							//if((confServCredito.getId().getIntItemCredito().compareTo(credito.getId().getIntItemCredito())==0)){
								//listaConfServCredito.add(confServCredito);
							//}	
						//}
						//if(listaConfServCredito != null && !listaConfServCredito.isEmpty()){
							confServSolicitudAux.setListaCredito(listaConfServCredito);
						//}
					}

					confServSolicitudAux.setListaGrupoCta(listaConfServGrupoCta);
					listaConfServSolicitudAux.add(confServSolicitudAux);

				}				
			}
			listaConfServSolicitud = listaConfServSolicitudAux;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfServSolicitud;
	}
}