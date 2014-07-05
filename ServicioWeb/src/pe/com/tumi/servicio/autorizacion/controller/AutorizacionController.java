package pe.com.tumi.servicio.autorizacion.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.domain.composite.UsuarioComp;
import pe.com.tumi.seguridad.login.facade.LoginFacadeRemote;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;
import pe.com.tumi.servicio.configuracion.domain.ConfServCancelado;
import pe.com.tumi.servicio.configuracion.domain.ConfServCreditoEmpresa;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;
import pe.com.tumi.servicio.configuracion.domain.ConfServRol;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuario;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeLocal;

public class AutorizacionController {

	protected static Logger log = Logger.getLogger(AutorizacionController.class);	
	
	private TablaFacadeRemote tablaFacade;
	private PermisoFacadeRemote permisoFacade;
	private LoginFacadeRemote loginFacade;
	private ConfSolicitudFacadeLocal confSolicitudFacade;
	
	private ConfServSolicitud confServSolicitudNuevo;
	private ConfServSolicitud confServSolicitudFiltro;
	private ConfServSolicitud registroSeleccionado;
	
	private List listaConfServSolicitud;
	private List listaTipoRelacion;
	private List listaTipoPrestamo;
	private List listaSuboperacion;
	private List listaPerfil;
	private List listaUsuarioComp;
	private List listaConfServPerfil;
	private List listaConfServUsuario;
	private List listaConfServCancelado;
	
	private List<Perfil> listaPerfilPersiste;
	private List<UsuarioComp> listaUsuarioCompPersiste;
	
	private ConfServCancelado confServCancelado;
	private Perfil perfilFiltro;
	private UsuarioComp usuarioCompFiltro;
	
	private Integer tipoCuentaFiltro;
	private Integer selecciona = 1;
	private Integer noSelecciona = 0;
	private String mensajeOperacion;
		
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean habilitarArchivoAdjunto;
	private boolean habilitarComboEstado;
	private boolean seleccionaIndeterminado;
	private boolean habilitarFechaFin;
	private boolean habilitarTipoOperacion;
	private boolean habilitarAgregarPerfil;
	private boolean habilitarAgregarUsuario;
	private boolean habilitarTipoPrestamo;
	private boolean habilitarRangoMontos;
	private boolean habilitarNumeroCuotas;
	private boolean habilitarCancelado;
	private boolean habilitarFiltroFecha;
	private Usuario usuario;
	
	public AutorizacionController(){
		cargarValoresIniciales();
	}
	
	private void cargarValoresIniciales(){
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!=null){
				habilitarFiltroFecha = Boolean.FALSE;
				confServCancelado = new ConfServCancelado();
				perfilFiltro = new Perfil();
				usuarioCompFiltro = new UsuarioComp();
				usuarioCompFiltro.setUsuario(new Usuario());
				perfilFiltro.setIntTipoPerfil(Constante.PARAM_COMBO_TODOS);
				confServSolicitudNuevo = new ConfServSolicitud();
				confServSolicitudFiltro = new ConfServSolicitud();
				listaConfServCancelado = new ArrayList<ConfServCancelado>();
				listaConfServPerfil = new ArrayList<ConfServPerfil>();
				listaConfServUsuario = new ArrayList<ConfServUsuario>();
				listaConfServSolicitud = new ArrayList<ConfServSolicitud>();
				listaTipoRelacion = new ArrayList<Tabla>();
				
				tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
				permisoFacade = (PermisoFacadeRemote) EJBFactory.getRemote(PermisoFacadeRemote.class);
				loginFacade = (LoginFacadeRemote) EJBFactory.getRemote(LoginFacadeRemote.class);
				confSolicitudFacade = (ConfSolicitudFacadeLocal) EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
				
				listaTipoRelacion = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL),"E");
				listaTipoPrestamo = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCREDITOEMPRESA));
				
				cargarListaPerfilPersiste();
				cargarListaUsuarioPersiste();
			}else{
				log.error("--Usuario obtenido es NULL.");
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaPerfilPersiste(){
		try{
			Perfil perfil = new Perfil();
			perfil.setId(new PerfilId());
			perfil.setBlnIndeterminado(Boolean.TRUE);
			listaPerfilPersiste = permisoFacade.getListaPerfilDeBusqueda(perfil);
			listaPerfil = listaPerfilPersiste;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaUsuarioPersiste(){
		try{
			UsuarioComp usuarioComp = new UsuarioComp();
			EmpresaUsuario empresaUsuario = new EmpresaUsuario();
			empresaUsuario.setId(new EmpresaUsuarioId());
			usuarioComp.setEmpresaUsuario(empresaUsuario);
			usuarioComp.setUsuario(new Usuario());
			listaUsuarioCompPersiste = loginFacade.getListaUsuarioCompDeBusqueda(usuarioComp);
			/*for(UsuarioComp uc : listaUsuarioCompPersiste){
				for(Perfil perf : uc.getListaPerfil()){
					log.info("perf:"+perf.getStrDescripcion());
				}
				//log.info("uc.getIntIdPerfil:"+uc.getIntIdPerfil());
			}*/
			listaUsuarioComp = listaUsuarioCompPersiste;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			habilitarTipoOperacion = Boolean.TRUE;
			habilitarFechaFin = Boolean.TRUE;
			seleccionaIndeterminado = Boolean.FALSE;
			habilitarComboEstado = Boolean.FALSE;
			habilitarArchivoAdjunto = Boolean.FALSE;
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;			
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			habilitarTipoPrestamo = Boolean.FALSE;
			habilitarRangoMontos = Boolean.FALSE;
			habilitarNumeroCuotas = Boolean.FALSE;
			habilitarCancelado = Boolean.FALSE;
			confServSolicitudNuevo = new ConfServSolicitud();
			limpiarChecks(listaTipoRelacion);
			limpiarChecks(listaTipoPrestamo);
			listaConfServPerfil.clear();
			listaConfServUsuario.clear();
			listaConfServCancelado.clear();
			mostrarMensaje(Boolean.TRUE,null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void limpiarChecks(List lista){
		for(int i=0;i<lista.size();i++){
			((Tabla)lista.get(i)).setChecked(Boolean.FALSE);
		}
	}
	
	public void grabar(){
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try{
			
			//Validación general
			if(habilitarTipoOperacion){
				mensaje = "Ocurrió un error durante el registro. Debe seleccionar un tipo de operación.";
				return;
			}
			
			boolean seleccionoTipoRelacion = Boolean.FALSE;
			for(Object o : listaTipoRelacion){
				if(((Tabla)o).getChecked()){
					seleccionoTipoRelacion = Boolean.TRUE;
					break;
				}
			}
			if(!seleccionoTipoRelacion){
				mensaje = "Ocurrió un error durante el registro. Debe seleccionar al menos un tipo de relación.";
				return;
			}			
			
			//Para los roles
			List<ConfServRol> listaConfServRol = new ArrayList<ConfServRol>();
			for(Object o : listaTipoRelacion){
				Tabla tabla = (Tabla)o;
				ConfServRol confServRol = new ConfServRol();
				confServRol.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				confServRol.getId().setIntParaRolCod(tabla.getIntIdDetalle());
				if(tabla.getChecked()){
					confServRol.setIntValor(selecciona);
				}else{
					confServRol.setIntValor(noSelecciona);
				}
				listaConfServRol.add(confServRol);					
			}
			confServSolicitudNuevo.setListaRol(listaConfServRol);
			
			confServSolicitudNuevo.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION);
						
			if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_PRESTAMO)
					|| confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO)){
			
				//Validaciones
				if(confServSolicitudNuevo.getDtDesde()==null){
					mensaje = "Ocurrió un error durante el registro. Debe ingresar el inicio del Rango de Fecha.";
					return;
				}
				if(!seleccionaIndeterminado && confServSolicitudNuevo.getDtHasta()==null){
					mensaje = "Ocurrió un error durante el registro. Debe ingresar el fin del Rango de Fecha.";
					return;
				}
				if(!seleccionaIndeterminado && confServSolicitudNuevo.getDtDesde().compareTo(confServSolicitudNuevo.getDtHasta())>0){
					mensaje = "Ocurrió un error durante el registro. La fecha de inicio es mayor a la fecha de fin.";
					return;
				}
				if(!habilitarAgregarUsuario && !habilitarAgregarPerfil){
					mensaje = "Ocurrió un error durante el registro. Debe seleccionar un tipo de Entidad de Autorización.";
					return;
				}
				if(habilitarAgregarPerfil && listaConfServPerfil.isEmpty()){
					mensaje = "Ocurrió un error durante el registro. Debe agregar al menos un perfil en Entidad de Autorización.";
					return;
				}
				if(habilitarAgregarUsuario && listaConfServUsuario.isEmpty()){
					mensaje = "Ocurrió un error durante el registro. Debe agregar al menos un usuario en Entidad de Autorización.";
					return;
				}
				if(habilitarTipoPrestamo){
					boolean seleccionoTipoPrestamo = Boolean.FALSE;
					for(Object o : listaTipoPrestamo){
						if(((Tabla)o).getChecked()){
							seleccionoTipoPrestamo = Boolean.TRUE;
							break;
						}
					}
					if(!seleccionoTipoPrestamo){
						mensaje = "Ocurrió un error durante el registro. Debe seleccionar al menos un tipo de préstamo.";
						return;
					}
				}
				if(habilitarRangoMontos){
					if(confServSolicitudNuevo.getBdMontoDesde()==null || confServSolicitudNuevo.getBdMontoHasta()==null){
						mensaje = "Ocurrió un error durante el registro. Debe ingresar correctamente el rango de monto.";
						return;
					}
					if(confServSolicitudNuevo.getBdMontoDesde().compareTo(confServSolicitudNuevo.getBdMontoHasta())>0){
						mensaje = "Ocurrió un error durante el registro. El rango incial del monto es mayor al final.";
						return;
					}
				}
				if(habilitarNumeroCuotas){
					if(confServSolicitudNuevo.getBdCuotaDesde()==null || confServSolicitudNuevo.getBdCuotaHasta()==null){
						mensaje = "Ocurrió un error durante el registro. Debe ingresar correctamente el número de cuotas.";
						return;
					}
					if(confServSolicitudNuevo.getBdCuotaDesde().compareTo(confServSolicitudNuevo.getBdCuotaHasta())>0){
						mensaje = "Ocurrió un error durante el registro. El número de cuotas incial es mayor al número final.";
						return;
					}
				}
				if(confServSolicitudNuevo.getIntParaSubtipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO) && habilitarCancelado){
					if(listaConfServCancelado.isEmpty()){
						mensaje = "Ocurrió un error durante el registro. Debe agregar al menos un % de cancelado.";
						return;
					}
				}
				
				//Grabar
				//log.info("habilitarAgregarPerfil:"+habilitarAgregarPerfil);
				if(habilitarAgregarPerfil){
					//log.info("listaConfServPerfil:"+listaConfServPerfil.size());
					confServSolicitudNuevo.setListaPerfil(listaConfServPerfil);					
				}else{
					confServSolicitudNuevo.setListaPerfil(null);
				}
				
				//log.info("habilitarAgregarUsuario:"+habilitarAgregarUsuario);
				if(habilitarAgregarUsuario){
					//log.info("listaConfServUsuario:"+listaConfServUsuario.size());
					confServSolicitudNuevo.setListaUsuario(listaConfServUsuario);
				}else{
					confServSolicitudNuevo.setListaUsuario(null);
				}
				
				if(habilitarTipoPrestamo){					
					List<ConfServCreditoEmpresa> listaConfServCreditoEmpresa = new ArrayList<ConfServCreditoEmpresa>();
					for(Object o : listaTipoPrestamo){
						Tabla tabla = (Tabla)o;
						ConfServCreditoEmpresa confServCreditoEmpresa = new ConfServCreditoEmpresa();
						confServCreditoEmpresa.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
						confServCreditoEmpresa.getId().setIntParaTipoCreditoEmpresaCod(tabla.getIntIdDetalle());
						if(tabla.getChecked()){
							confServCreditoEmpresa.setIntValor(selecciona);
						}else{
							confServCreditoEmpresa.setIntValor(noSelecciona);
						}
						listaConfServCreditoEmpresa.add(confServCreditoEmpresa);
					}					
					confServSolicitudNuevo.setListaCreditoEmpresa(listaConfServCreditoEmpresa);			
				}
				
				if(!habilitarRangoMontos){
					confServSolicitudNuevo.setBdMontoDesde(null);
					confServSolicitudNuevo.setBdMontoHasta(null);
				}
				
				if(!habilitarNumeroCuotas){
					confServSolicitudNuevo.setBdCuotaDesde(null);
					confServSolicitudNuevo.setBdCuotaHasta(null);
				}
				
				if(confServSolicitudNuevo.getIntParaSubtipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)){
					if(habilitarCancelado){
						confServSolicitudNuevo.setListaCancelado(listaConfServCancelado);
					}else{
						confServSolicitudNuevo.setListaCancelado(null);
					}
				}
				
				if(registrarNuevo){
					confServSolicitudNuevo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					confServSolicitudNuevo.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
					//Cambiar para pruebas en QC con 93
					//confServSolicitudNuevo.setIntPersPersonaUsuarioPk(Constante.PARAM_USUARIOSESION);
					confServSolicitudNuevo.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
					confServSolicitudNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));								
					
					confSolicitudFacade.grabarAutorizacion(confServSolicitudNuevo);					
					mensaje = "Se registró correctamente la configuración de autorizaciones.";
				}else{
					confSolicitudFacade.modificarAutorizacion(confServSolicitudNuevo);
					mensaje = "Se modificó correctamente la configuración de autorizaciones.";
					
				}
				exito = Boolean.TRUE;
				deshabilitarNuevo = Boolean.TRUE;
				habilitarGrabar = Boolean.FALSE;
				buscar();
			}else{
				mensaje = "Ocurrio un error durante el proceso de registro de autorizaciones. La operación seleccionada no se encuentra soportada.";
			}
			
			
		}catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de registro de autorizaciones.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	private void mostrarMensaje(boolean exito, String mensaje){
		if(exito){
			mostrarMensajeExito = Boolean.TRUE;
			mostrarMensajeError = Boolean.FALSE;
			mensajeOperacion = mensaje;
		}else{
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.TRUE;
			mensajeOperacion = mensaje;
		}
	}
	
	public void seleccionarRegistrarTipo(){
		try{
			habilitarGrabar = Boolean.TRUE;			
			habilitarTipoOperacion = Boolean.FALSE;
			habilitarComboEstado = Boolean.TRUE;			
			habilitarAgregarUsuario = Boolean.FALSE;
			habilitarAgregarPerfil = Boolean.FALSE;
			mostrarMensaje(Boolean.TRUE,null);
			
			log.info(confServSolicitudNuevo);
			
			if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_PRESTAMO)){
				listaSuboperacion = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_SUBOPERACIONPRESTAMO));
			
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO)){
				listaSuboperacion = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_SUBOPERACIONREFINANCIAMIENTO));
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public void eliminarRegistro(){
		boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {
			listaConfServSolicitud.remove(registroSeleccionado);
			//Para pruebas en QC ser usa el usuario 93
			//registroSeleccionado.setIntPersPersonaEliminaPk(Constante.PARAM_USUARIOSESION);
			registroSeleccionado.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
			registroSeleccionado.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
			registroSeleccionado.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			registroSeleccionado = confSolicitudFacade.modificarConfiguracion(registroSeleccionado);
		} catch (BusinessException e) {
			log.error(e.getMessage(),e);
		} catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(registroSeleccionado.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
			exito = Boolean.TRUE;
			mensaje = "El proceso de eliminación se realizo correctamente";			
		}else{
			mensaje = "Ocurrio un error durante el proceso de eliminación";			
		}
		mostrarMensaje(exito, mensaje);
		mostrarPanelInferior = Boolean.FALSE;
	}
	
	public void modificarRegistro(){
		try {
			cargarRegistro();	
			habilitarGrabar = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
		} catch (BusinessException e) {			
			log.error(e.getMessage(),e);
		} catch (Exception e) {			
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (ConfServSolicitud)event.getComponent().getAttributes().get("item");
			log.info("reg selec:"+registroSeleccionado);
			if(registroSeleccionado.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				cargarRegistro();
				mostrarBtnEliminar= Boolean.FALSE;
				habilitarGrabar = Boolean.FALSE;
				registrarNuevo = Boolean.FALSE;
				mostrarPanelInferior = Boolean.TRUE;
				mostrarMensajeExito = Boolean.FALSE;
				mostrarMensajeError = Boolean.FALSE;
				deshabilitarNuevo = Boolean.TRUE;
			}else{
				mostrarBtnEliminar = Boolean.TRUE;
				habilitarGrabar = Boolean.TRUE;
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarRegistro()throws BusinessException{
		try{
			confServSolicitudNuevo = registroSeleccionado;
			seleccionarRegistrarTipo();
			
			//Carga la tabla Tipo de relacion / listaConfServRol 
			List<ConfServRol> listaConfServRol = confSolicitudFacade.getListaConfServRolPorCabecera(confServSolicitudNuevo);
			List<Tabla> listaTipoRelacionAux = new ArrayList<Tabla>();
			for(Object o : listaTipoRelacion){
				Tabla tabla = (Tabla)o;
				for(ConfServRol confServRol : listaConfServRol){
					if(tabla.getIntIdDetalle().equals(confServRol.getId().getIntParaRolCod())){
						if(confServRol.getIntValor().equals(selecciona)){
							tabla.setChecked(Boolean.TRUE);
						}else{
							tabla.setChecked(Boolean.FALSE);
						}
						listaTipoRelacionAux.add(tabla);
						break;
					}
				}
			}
			listaTipoRelacion = listaTipoRelacionAux;
			
			//Para el check de Indeterminado
			if(confServSolicitudNuevo.getDtHasta()==null){
				habilitarFechaFin = Boolean.FALSE;
				seleccionaIndeterminado = Boolean.TRUE;
			}else{
				habilitarFechaFin = Boolean.TRUE;
				seleccionaIndeterminado = Boolean.FALSE;
			}
			
			//Para la entidad de autorizacion
			listaConfServPerfil = confServSolicitudNuevo.getListaPerfil();
			if(listaConfServPerfil != null && !listaConfServPerfil.isEmpty()){
				habilitarAgregarPerfil = Boolean.TRUE;
				habilitarAgregarUsuario = Boolean.FALSE;
			}
			listaConfServUsuario = confServSolicitudNuevo.getListaUsuario();
			if(listaConfServUsuario != null && !listaConfServUsuario.isEmpty()){
				habilitarAgregarPerfil = Boolean.FALSE;
				habilitarAgregarUsuario = Boolean.TRUE;
			}
			
			//Para la tabla de Tipo de Prestamo
			List<ConfServCreditoEmpresa> listaConfServCreditoEmpresa = confSolicitudFacade.getListaConfServCreditoEmpresaPorCabecera(confServSolicitudNuevo);
			if(listaConfServCreditoEmpresa!=null){
				habilitarTipoPrestamo = Boolean.TRUE;
				List<Tabla> listaTipoPrestamoAux = new ArrayList<Tabla>();
				for(Object o : listaTipoPrestamo){
					Tabla tabla = (Tabla)o;
					for(ConfServCreditoEmpresa confServCreditoEmpresa : listaConfServCreditoEmpresa){
						if(tabla.getIntIdDetalle().equals(confServCreditoEmpresa.getId().getIntParaTipoCreditoEmpresaCod())){
							if(confServCreditoEmpresa.getIntValor().equals(selecciona)){
								tabla.setChecked(Boolean.TRUE);
							}else{
								tabla.setChecked(Boolean.FALSE);
							}
							listaTipoPrestamoAux.add(tabla);
							break;
						}
					}
				}
				if(!listaConfServCreditoEmpresa.isEmpty()){
					listaTipoPrestamo = listaTipoPrestamoAux;
				}else{
					habilitarTipoPrestamo = Boolean.FALSE;
				}
			}else{
				habilitarTipoPrestamo = Boolean.FALSE;
			}
			
			//Para la seccion de montos
			if(confServSolicitudNuevo.getBdMontoDesde()!=null && confServSolicitudNuevo.getBdMontoHasta() != null){
				habilitarRangoMontos = Boolean.TRUE;
			}else{
				habilitarRangoMontos = Boolean.FALSE;
			}
			
			//Para la seccion de cuotas
			if(confServSolicitudNuevo.getBdCuotaDesde()!=null && confServSolicitudNuevo.getBdCuotaHasta() != null){
				habilitarNumeroCuotas = Boolean.TRUE;
			}else{
				habilitarNumeroCuotas = Boolean.FALSE;
			}
			
			//Para la tabla de cancelados
			if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_PRESTAMO) && 
					confServSolicitudNuevo.getIntParaSubtipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)){
				listaConfServCancelado = confSolicitudFacade.getListaConfServCanceladoPorCabecera(confServSolicitudNuevo);
				if(listaConfServCancelado != null){
					habilitarCancelado = Boolean.TRUE;
				}else{
					habilitarCancelado = Boolean.FALSE;
				}
			}
			habilitarComboEstado = Boolean.TRUE;			
			registrarNuevo = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
	}
	
	public void buscar(){
		String mensaje = null;
		try{
			/*if(confServSolicitudFiltro.getIntParaEstadoCod().equals(Constante.PARAM_COMBO_TODOS)){
				confServSolicitudFiltro.setIntParaEstadoCod(null);
			}*/
			if(habilitarFiltroFecha && confServSolicitudFiltro.getDtDesde().compareTo(confServSolicitudFiltro.getDtHasta())>0){
				mensaje = "Error en la búsqueda. La fecha de inicio es mayor a la fecha de fin.";
				mostrarMensaje(Boolean.FALSE,mensaje);
				return;
			}
			confServSolicitudFiltro.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			listaConfServSolicitud = confSolicitudFacade.buscarConfSolicitudAutorizacion(confServSolicitudFiltro, confServSolicitudFiltro.getDtDesde(), confServSolicitudFiltro.getDtHasta(), tipoCuentaFiltro);
			
			List<ConfServPerfil> listaPerfilAux = new ArrayList<ConfServPerfil>();
			List<ConfServUsuario> listaUsuarioAux = new ArrayList<ConfServUsuario>();
			List<ConfServSolicitud> listaConfServSolicitudAux = new ArrayList<ConfServSolicitud>();
			for(Object o : listaConfServSolicitud){
				ConfServSolicitud confServSolicitud = (ConfServSolicitud)o;
				
				if(confServSolicitud.getListaPerfil() != null){
					listaPerfilAux = new ArrayList<ConfServPerfil>();
					for(ConfServPerfil confServPerfil : confServSolicitud.getListaPerfil() ){
						confServPerfil = buscarListaPerfilPersiste(confServPerfil);
						listaPerfilAux.add(confServPerfil);
					}
					confServSolicitud.setListaPerfil(listaPerfilAux);
				}
				if(confServSolicitud.getListaUsuario() != null){
					listaUsuarioAux = new ArrayList<ConfServUsuario>();
					for(ConfServUsuario confServUsuario : confServSolicitud.getListaUsuario() ){
						confServUsuario = buscarListaUsuarioCompPersiste(confServUsuario);
						listaUsuarioAux.add(confServUsuario);
					}
					confServSolicitud.setListaUsuario(listaUsuarioAux);
				}
				listaConfServSolicitudAux.add(confServSolicitud);
			}
			listaConfServSolicitud = listaConfServSolicitudAux;
			
			mostrarMensaje(Boolean.TRUE,null);			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private ConfServPerfil buscarListaPerfilPersiste(ConfServPerfil confServPerfil){
		for(Perfil perfil : listaPerfilPersiste){
			if(perfil.getId().getIntIdPerfil().equals(confServPerfil.getIntIdPerfilPk())){
				confServPerfil.setStrDescripcion(perfil.getStrDescripcion());
				return confServPerfil;
			}
		}
		return confServPerfil;
	}
	
	private ConfServUsuario buscarListaUsuarioCompPersiste(ConfServUsuario confServUsuario){
		for(UsuarioComp usuarioComp : listaUsuarioCompPersiste){
			if(usuarioComp.getUsuario().getIntPersPersonaPk().equals(confServUsuario.getIntPersUsuarioPk())){
				confServUsuario.setUsuarioComp(usuarioComp);
				return confServUsuario;
			}
		}
		return confServUsuario;
	}
	
	public void buscarPerfil(){
		try{
			listaPerfil = listaPerfilPersiste;
			List<Perfil> listaAux = new ArrayList<Perfil>();
			
			if(!perfilFiltro.getIntTipoPerfil().equals(Constante.PARAM_COMBO_TODOS)){
				for(Object o : listaPerfil){
					Perfil perfil = (Perfil)o;
					if(perfil.getIntTipoPerfil().equals(perfilFiltro.getIntTipoPerfil())){
						listaAux.add(perfil);
					}
				}
				listaPerfil = listaAux;
			}

			if(perfilFiltro.getStrDescripcion()!=null && !perfilFiltro.getStrDescripcion().isEmpty()){
				listaAux = new ArrayList<Perfil>();
				for(Object o : listaPerfil){
					Perfil perfil = (Perfil)o;
					if(perfil.getStrDescripcion().toUpperCase().contains(perfilFiltro.getStrDescripcion().toUpperCase())){
						listaAux.add(perfil);
					}
				}
				listaPerfil = listaAux;
			}

		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private boolean usuarioCompPoseeIdPerfil(UsuarioComp usuarioComp,Integer intIdPerfil){
		List<Perfil> listaPerfilAux = usuarioComp.getListaPerfil();
		for(Perfil perfil : listaPerfilAux){
			if(perfil.getId().getIntIdPerfil().equals(intIdPerfil)){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public void buscarUsuarioComp(){
		try{
			listaUsuarioComp = listaUsuarioCompPersiste;
			List<UsuarioComp> listaAux = new ArrayList<UsuarioComp>();
			
			if(!usuarioCompFiltro.getIntIdPerfil().equals(Constante.PARAM_COMBO_TODOS)){
				for(Object o : listaUsuarioComp){
					UsuarioComp usuarioComp = (UsuarioComp)o;
					if(usuarioCompPoseeIdPerfil(usuarioComp,usuarioCompFiltro.getIntIdPerfil())){
						listaAux.add(usuarioComp);
					}
				}
				listaUsuarioComp = listaAux;
			}

			if(usuarioCompFiltro.getUsuario().getStrUsuario()!=null && !usuarioCompFiltro.getUsuario().getStrUsuario().isEmpty()){
				listaAux = new ArrayList<UsuarioComp>();
				for(Object o : listaUsuarioComp){
					UsuarioComp usuarioComp = (UsuarioComp)o;
					if(usuarioComp.getUsuario().getStrUsuario().toUpperCase().contains(usuarioCompFiltro.getUsuario().getStrUsuario().toUpperCase())){
						listaAux.add(usuarioComp);
					}
				}
				listaUsuarioComp = listaAux;
			}

		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarEntidadAutorizacionPerfil(){
		try{
			if(habilitarAgregarPerfil==Boolean.TRUE){
				habilitarAgregarUsuario = Boolean.FALSE;
				listaConfServUsuario.clear();
			}else{
				habilitarAgregarUsuario = Boolean.TRUE;
			}			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarEntidadAutorizacionUsuario(){
		try{
			if(habilitarAgregarUsuario==Boolean.TRUE){
				habilitarAgregarPerfil = Boolean.FALSE;
				listaConfServPerfil.clear();
			}else{
				habilitarAgregarPerfil = Boolean.TRUE;
			}			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private boolean verificarPerfil(Perfil perfil){
		boolean verificado = Boolean.TRUE;
		for(Object o : listaConfServPerfil){
			ConfServPerfil confServPerfil = (ConfServPerfil)o;
			if(confServPerfil.getIntIdPerfilPk().equals(perfil.getId().getIntIdPerfil())){
				verificado = Boolean.FALSE;
				break;
			}
		}
		return verificado;
	}
	
	private boolean verificarUsuarioComp(UsuarioComp usuarioComp){
		boolean verificado = Boolean.TRUE;
		for(Object o : listaConfServUsuario){
			ConfServUsuario confServUsuario = (ConfServUsuario)o;
			if(confServUsuario.getUsuarioComp().getUsuario().getIntPersPersonaPk().equals(usuarioComp.getUsuario().getIntPersPersonaPk())){
				verificado = Boolean.FALSE;
				break;
			}
		}
		return verificado;
	}
	
	private void agregarListaConfServPerfil(Perfil perfil){
		ConfServPerfil confServPerfil = new ConfServPerfil();
		confServPerfil.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
		confServPerfil.setIntIdPerfilPk(perfil.getId().getIntIdPerfil());
		confServPerfil.setStrDescripcion(perfil.getStrDescripcion());
		confServPerfil.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		confServPerfil.setTsFechaRegistro(new Timestamp(new Date().getTime()));
		confServPerfil.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
		listaConfServPerfil.add(confServPerfil);
	}
	
	private void agregarListaConfServUsuario(UsuarioComp usuarioComp){
		ConfServUsuario confServUsuario = new ConfServUsuario();
		confServUsuario.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
		confServUsuario.setIntPersUsuarioPk(usuarioComp.getUsuario().getIntPersPersonaPk());
		confServUsuario.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		confServUsuario.setTsFechaRegistro(new Timestamp(new Date().getTime()));
		confServUsuario.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
		confServUsuario.setUsuarioComp(usuarioComp);
		listaConfServUsuario.add(confServUsuario);
	}
	
	public void agregarListaConfServCancelado(){
		ConfServCancelado confServCanceladoAux = new ConfServCancelado();
		confServCanceladoAux.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
		confServCanceladoAux.setIntParaModalidadPagoCod(confServCancelado.getIntParaModalidadPagoCod());
		confServCanceladoAux.setBdPorcentajeCancelado(confServCancelado.getBdPorcentajeCancelado());
		confServCanceladoAux.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		confServCanceladoAux.setTsFechaRegistro(new Timestamp(new Date().getTime()));
		confServCanceladoAux.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
		listaConfServCancelado.add(confServCanceladoAux);
	}
	
	public void agregarConfServPerfil(){
		try{
			for(int i=0;i<listaPerfil.size();i++){
				Perfil perfil = (Perfil)(listaPerfil.get(i));
				if(perfil.getChecked()==Boolean.TRUE){
					if(verificarPerfil(perfil)){
						agregarListaConfServPerfil(perfil);
					}
				}
				((Perfil)(listaPerfil.get(i))).setChecked(Boolean.FALSE);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void agregarConfServUsuario(){
		try{
			for(int i=0;i<listaUsuarioComp.size();i++){
				UsuarioComp usuarioComp = (UsuarioComp)(listaUsuarioComp.get(i));
				if(usuarioComp.getChecked()==Boolean.TRUE){
					if(verificarUsuarioComp(usuarioComp)){
						agregarListaConfServUsuario(usuarioComp);
					}
				}
				((UsuarioComp)(listaUsuarioComp.get(i))).setChecked(Boolean.FALSE);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarConfServPerfil(ActionEvent event){
		ConfServPerfil confServPerfilAux = (ConfServPerfil)event.getComponent().getAttributes().get("item");
		listaConfServPerfil.remove(confServPerfilAux);		
	}
	
	public void eliminarConfServUsuario(ActionEvent event){
		ConfServUsuario confServUsuarioAux = (ConfServUsuario)event.getComponent().getAttributes().get("item");
		listaConfServUsuario.remove(confServUsuarioAux);		
	}
	
	public void eliminarConfServCancelado(ActionEvent event){
		ConfServCancelado confServCanceladoAux = (ConfServCancelado)event.getComponent().getAttributes().get("item");
		listaConfServCancelado.remove(confServCanceladoAux);		
	}
	
	public void manejarIndeterminado(){
		if(seleccionaIndeterminado==Boolean.TRUE){
			habilitarFechaFin = Boolean.FALSE;
			confServSolicitudNuevo.setDtHasta(null);
		}else{
			habilitarFechaFin = Boolean.TRUE;
		}
	}
	
	public void seleccionaFiltroFecha(){
		confServSolicitudFiltro.setDtDesde(null);
		confServSolicitudFiltro.setDtHasta(null);
	}
	
	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	public ConfServSolicitud getConfServSolicitudNuevo() {
		return confServSolicitudNuevo;
	}
	public void setConfServSolicitudNuevo(ConfServSolicitud confServSolicitudNuevo) {
		this.confServSolicitudNuevo = confServSolicitudNuevo;
	}
	public ConfServSolicitud getConfServSolicitudFiltro() {
		return confServSolicitudFiltro;
	}
	public void setConfServSolicitudFiltro(ConfServSolicitud confServSolicitudFiltro) {
		this.confServSolicitudFiltro = confServSolicitudFiltro;
	}
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}
	public boolean isMostrarBtnEliminar() {
		return mostrarBtnEliminar;
	}
	public void setMostrarBtnEliminar(boolean mostrarBtnEliminar) {
		this.mostrarBtnEliminar = mostrarBtnEliminar;
	}
	public boolean isMostrarMensajeExito() {
		return mostrarMensajeExito;
	}
	public void setMostrarMensajeExito(boolean mostrarMensajeExito) {
		this.mostrarMensajeExito = mostrarMensajeExito;
	}
	public boolean isMostrarMensajeError() {
		return mostrarMensajeError;
	}
	public void setMostrarMensajeError(boolean mostrarMensajeError) {
		this.mostrarMensajeError = mostrarMensajeError;
	}
	public boolean isDeshabilitarNuevo() {
		return deshabilitarNuevo;
	}
	public void setDeshabilitarNuevo(boolean deshabilitarNuevo) {
		this.deshabilitarNuevo = deshabilitarNuevo;
	}
	public boolean isMostrarPanelInferior() {
		return mostrarPanelInferior;
	}
	public void setMostrarPanelInferior(boolean mostrarPanelInferior) {
		this.mostrarPanelInferior = mostrarPanelInferior;
	}
	public boolean isRegistrarNuevo() {
		return registrarNuevo;
	}
	public void setRegistrarNuevo(boolean registrarNuevo) {
		this.registrarNuevo = registrarNuevo;
	}
	public boolean isHabilitarGrabar() {
		return habilitarGrabar;
	}
	public void setHabilitarGrabar(boolean habilitarGrabar) {
		this.habilitarGrabar = habilitarGrabar;
	}
	public boolean isHabilitarArchivoAdjunto() {
		return habilitarArchivoAdjunto;
	}
	public void setHabilitarArchivoAdjunto(boolean habilitarArchivoAdjunto) {
		this.habilitarArchivoAdjunto = habilitarArchivoAdjunto;
	}
	public boolean isHabilitarComboEstado() {
		return habilitarComboEstado;
	}
	public void setHabilitarComboEstado(boolean habilitarComboEstado) {
		this.habilitarComboEstado = habilitarComboEstado;
	}
	public boolean isSeleccionaIndeterminado() {
		return seleccionaIndeterminado;
	}
	public void setSeleccionaIndeterminado(boolean seleccionaIndeterminado) {
		this.seleccionaIndeterminado = seleccionaIndeterminado;
	}
	public boolean isHabilitarFechaFin() {
		return habilitarFechaFin;
	}
	public void setHabilitarFechaFin(boolean habilitarFechaFin) {
		this.habilitarFechaFin = habilitarFechaFin;
	}
	public boolean isHabilitarTipoOperacion() {
		return habilitarTipoOperacion;
	}
	public void setHabilitarTipoOperacion(boolean habilitarTipoOperacion) {
		this.habilitarTipoOperacion = habilitarTipoOperacion;
	}
	public List getListaConfServSolicitud() {
		return listaConfServSolicitud;
	}
	public void setListaConfServSolicitud(List listaConfServSolicitud) {
		this.listaConfServSolicitud = listaConfServSolicitud;
	}
	public ConfServSolicitud getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(ConfServSolicitud registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public List getListaTipoRelacion() {
		return listaTipoRelacion;
	}
	public void setListaTipoRelacion(List listaTipoRelacion) {
		this.listaTipoRelacion = listaTipoRelacion;
	}
	public Integer getTipoCuentaFiltro() {
		return tipoCuentaFiltro;
	}
	public void setTipoCuentaFiltro(Integer tipoCuentaFiltro) {
		this.tipoCuentaFiltro = tipoCuentaFiltro;
	}
	public boolean isHabilitarAgregarPerfil() {
		return habilitarAgregarPerfil;
	}
	public void setHabilitarAgregarPerfil(boolean habilitarAgregarPerfil) {
		this.habilitarAgregarPerfil = habilitarAgregarPerfil;
	}
	public boolean isHabilitarAgregarUsuario() {
		return habilitarAgregarUsuario;
	}
	public void setHabilitarAgregarUsuario(boolean habilitarAgregarUsuario) {
		this.habilitarAgregarUsuario = habilitarAgregarUsuario;
	}
	public List getListaTipoPrestamo() {
		return listaTipoPrestamo;
	}
	public void setListaTipoPrestamo(List listaTipoPrestamo) {
		this.listaTipoPrestamo = listaTipoPrestamo;
	}
	public List getListaPerfil() {
		return listaPerfil;
	}
	public void setListaPerfil(List listaPerfil) {
		this.listaPerfil = listaPerfil;
	}
	public List getListaConfServPerfil() {
		return listaConfServPerfil;
	}
	public void setListaConfServPerfil(List listaConfServPerfil) {
		this.listaConfServPerfil = listaConfServPerfil;
	}
	public Perfil getPerfilFiltro() {
		return perfilFiltro;
	}
	public void setPerfilFiltro(Perfil perfilFiltro) {
		this.perfilFiltro = perfilFiltro;
	}
	public List getListaUsuarioComp() {
		return listaUsuarioComp;
	}
	public void setListaUsuarioComp(List listaUsuarioComp) {
		this.listaUsuarioComp = listaUsuarioComp;
	}
	public List getListaConfServUsuario() {
		return listaConfServUsuario;
	}
	public void setListaConfServUsuario(List listaConfServUsuario) {
		this.listaConfServUsuario = listaConfServUsuario;
	}
	public List<Perfil> getListaPerfilPersiste() {
		return listaPerfilPersiste;
	}
	public void setListaPerfilPersiste(List<Perfil> listaPerfilPersiste) {
		this.listaPerfilPersiste = listaPerfilPersiste;
	}
	public UsuarioComp getUsuarioCompFiltro() {
		return usuarioCompFiltro;
	}
	public void setUsuarioCompFiltro(UsuarioComp usuarioCompFiltro) {
		this.usuarioCompFiltro = usuarioCompFiltro;
	}
	public boolean isHabilitarTipoPrestamo() {
		return habilitarTipoPrestamo;
	}
	public void setHabilitarTipoPrestamo(boolean habilitarTipoPrestamo) {
		this.habilitarTipoPrestamo = habilitarTipoPrestamo;
	}
	public boolean isHabilitarRangoMontos() {
		return habilitarRangoMontos;
	}
	public void setHabilitarRangoMontos(boolean habilitarRangoMontos) {
		this.habilitarRangoMontos = habilitarRangoMontos;
	}
	public boolean isHabilitarNumeroCuotas() {
		return habilitarNumeroCuotas;
	}
	public void setHabilitarNumeroCuotas(boolean habilitarNumeroCuotas) {
		this.habilitarNumeroCuotas = habilitarNumeroCuotas;
	}
	public boolean isHabilitarCancelado() {
		return habilitarCancelado;
	}
	public void setHabilitarCancelado(boolean habilitarCancelado) {
		this.habilitarCancelado = habilitarCancelado;
	}
	public List getListaConfServCancelado() {
		return listaConfServCancelado;
	}
	public void setListaConfServCancelado(List listaConfServCancelado) {
		this.listaConfServCancelado = listaConfServCancelado;
	}
	public ConfServCancelado getConfServCancelado() {
		return confServCancelado;
	}
	public void setConfServCancelado(ConfServCancelado confServCancelado) {
		this.confServCancelado = confServCancelado;
	}
	public List getListaSuboperacion() {
		return listaSuboperacion;
	}
	public void setListaSuboperacion(List listaSuboperacion) {
		this.listaSuboperacion = listaSuboperacion;
	}
	public boolean isHabilitarFiltroFecha() {
		return habilitarFiltroFecha;
	}
	public void setHabilitarFiltroFecha(boolean habilitarFiltroFecha) {
		this.habilitarFiltroFecha = habilitarFiltroFecha;
	}
}