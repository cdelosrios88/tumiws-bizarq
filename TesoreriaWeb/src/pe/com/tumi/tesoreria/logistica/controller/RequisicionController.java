package pe.com.tumi.tesoreria.logistica.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DocumentoRequisicion;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.common.util.UtilManagerReport;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionDetalle;
import pe.com.tumi.tesoreria.logistica.facade.LogisticaFacadeLocal;


public class RequisicionController {

	protected static Logger log = Logger.getLogger(RequisicionController.class);
	
	PersonaFacadeRemote 	personaFacade;
	EmpresaFacadeRemote		empresaFacade;
	LogisticaFacadeLocal	logisticaFacade;
	TablaFacadeRemote		tablaFacade;
	
	private List<Requisicion>	listaRequisicion;
	private List<Sucursal>		listaSucursal;
	private List<Tabla>			listaTablaAprobacion;
	
	private	Requisicion			requisicionNuevo;
	private RequisicionDetalle	requisicionDetalleNuevo;
	private Requisicion			requisicionFiltro;
	private Requisicion			registroSeleccionado;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	private Integer		intAccion;
	private final Integer	ACCION_AGREGAR = 1;
	private final Integer	ACCION_EDITAR = 2;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean poseePermiso;
	private boolean habilitarSolicitante;
	private boolean habilitarJefatura;
	private boolean habilitarLogistica;
	private boolean habilitarVer;
	private boolean casoEspecialAprobacion;
	
	
	public RequisicionController(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REQUISICION);
		//poseePermiso = Boolean.TRUE;
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
	}
	
	public String getLimpiarRequisicion(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REQUISICION);
		log.info("POSEE PERMISO" + poseePermiso);
		//poseePermiso = Boolean.TRUE;
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
			deshabilitarPanelInferior();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		
		return "";
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");		
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();	
	}
	
	public void cargarValoresIniciales(){
		try{
			personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			empresaFacade =  (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			logisticaFacade =  (LogisticaFacadeLocal) EJBFactory.getLocal(LogisticaFacadeLocal.class);
			tablaFacade =  (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			
			requisicionFiltro = new Requisicion();
			cargarListaSucursal();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}	
	
	public void grabar(){
		log.info("--grabar");
		try {
			cargarUsuario();
			if(requisicionNuevo.getBdMontoEstimado()==null){
				mostrarMensaje(Boolean.FALSE, "Debe ingresar un monto estimado."); return;
			}
			if(requisicionNuevo.getBdMontoEstimado()!=null && requisicionNuevo.getBdMontoEstimado().signum()<=0){
				mostrarMensaje(Boolean.FALSE, "El monto estimado debe ser mayor a cero."); return;
			}
			if(requisicionNuevo.getStrObservacion()==null || requisicionNuevo.getStrObservacion().isEmpty()){
				mostrarMensaje(Boolean.FALSE, "Debe ingresar una observación."); return;
			}
			if(requisicionNuevo.getListaRequisicionDetalle().isEmpty()){
				mostrarMensaje(Boolean.FALSE, "Debe ingresar al menos un detalle de requisición."); return;
			}
			if(requisicionNuevo.getIntParaTipoAprobacion().equals(Constante.PARAM_T_APROBACION_CAJACHICA)){
				requisicionNuevo.setBdMontoLogistica(requisicionNuevo.getBdMontoEstimado());
				requisicionNuevo.setIntPersEmpresaLogistica(requisicionNuevo.getIntPersEmpresaSolicitante());
				requisicionNuevo.setIntPersPersonaLogistica(requisicionNuevo.getIntPersPersonaLogistica());
				requisicionNuevo.setIntSucuIdSucursalLogistica(requisicionNuevo.getIntSucuIdSucursal());
				requisicionNuevo.setStrFundamentoLogistica(requisicionNuevo.getStrObservacion());
				requisicionNuevo.setIntParaEstadoAprobacionLogistica(Constante.PARAM_T_ESTADOREQUISICION_APROBADO);
			}
			
			
			if(requisicionNuevo.getId().getIntItemRequisicion()==null){
				requisicionNuevo = logisticaFacade.grabarRequisicion(requisicionNuevo);
				buscar();
				mostrarMensaje(Boolean.TRUE, "Se registró correctamente la requisición.");
			
			}else{
				if(habilitarJefatura){
					if(requisicionNuevo.getBdMontoJefatura()==null){
						mostrarMensaje(Boolean.FALSE, "Debe de ingresar un monto de jefatura"); return;
					}
					if(requisicionNuevo.getBdMontoJefatura()!=null && requisicionNuevo.getBdMontoJefatura().signum()<=0){
						mostrarMensaje(Boolean.FALSE, "El monto de jefatura debe ser mayor a cero."); return;
					}
					if(requisicionNuevo.getStrFundamentoJefatura()==null || requisicionNuevo.getStrFundamentoJefatura().isEmpty()){
						mostrarMensaje(Boolean.FALSE, "Debe ingresar un fundamento de la decisión de jefatura."); return;
					}
					
				}else if(habilitarLogistica){
					if(requisicionNuevo.getBdMontoLogistica()==null){
						mostrarMensaje(Boolean.FALSE, "Debe de ingresar un monto de logística"); return;
					}
					if(requisicionNuevo.getBdMontoLogistica()!=null && requisicionNuevo.getBdMontoLogistica().signum()<=0){
						mostrarMensaje(Boolean.FALSE, "El monto de logistica debe ser mayor a cero."); return;
					}
					if(requisicionNuevo.getStrFundamentoLogistica()==null || requisicionNuevo.getStrFundamentoLogistica().isEmpty()){
						mostrarMensaje(Boolean.FALSE, "Debe ingresar un fundamento de la decisión de logistica."); return;
					}
				}				
				
				requisicionNuevo = logisticaFacade.modificarRequisicion(requisicionNuevo, usuario);
				buscar();
				mostrarMensaje(Boolean.TRUE, "Se modificó correctamente la requisición.");
			}
			
			
			
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro de requisición.");
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscar(){
		try{
			requisicionFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			
			if(requisicionFiltro.getIntSucuIdSucursal().equals(new Integer(0))){
				requisicionFiltro.setIntSucuIdSucursal(null);
			}
			
			//Agregado por cdelosrios, 03/11/2013
			if(requisicionFiltro.getIntParaTipoAprobacion().equals(new Integer(0))){
				requisicionFiltro.setIntParaTipoAprobacion(null);
			}
			//Fin agregado por cdelosrios, 03/11/2013
			
			listaRequisicion = logisticaFacade.buscarRequisicion(requisicionFiltro);
			
			for(Requisicion requisicion : listaRequisicion){
				log.info(requisicion);
				agregarSolcittante(requisicion);
			}
			
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private Requisicion agregarSolcittante(Requisicion requisicion)throws Exception{
		Persona personaSolicitante = personaFacade.devolverPersonaCargada(requisicion.getIntPersPersonaSolicitante());
		personaSolicitante.setStrEtiqueta(personaSolicitante.getIntIdPersona()+" "+personaSolicitante.getNatural().getStrNombreCompleto()+
				" DNI : "+personaSolicitante.getDocumento().getStrNumeroIdentidad());
		requisicion.setPersonaSolicitante(personaSolicitante);
		
		for(Sucursal sucursal : listaSucursal){
			if(requisicion.getIntSucuIdSucursal().equals(sucursal.getId().getIntIdSucursal())){
				requisicion.setSucursalSolicitante(sucursal);
				for(Area area : sucursal.getListaArea()){
					if(area.getId().getIntIdArea().equals(requisicion.getIntIdArea())){
						requisicion.setArea(area);
					}
				}
			}
		}
		return requisicion;
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			cargarUsuario();
			registroSeleccionado = (Requisicion)event.getComponent().getAttributes().get("item");
			registroSeleccionado = logisticaFacade.obtenerRequisicionPorId(registroSeleccionado.getId());
			log.info(registroSeleccionado);
			habilitarSolicitante = Boolean.FALSE;
			habilitarJefatura = Boolean.FALSE;
			habilitarLogistica = Boolean.FALSE;
			habilitarVer = Boolean.FALSE;
			
			if(registroSeleccionado.getIntParaEstadoAprobacionJefatura()==null 
			&& registroSeleccionado.getIntParaEstadoAprobacionLogistica()==null){				
				if(registroSeleccionado.getIntParaTipoAprobacion().equals(Constante.PARAM_T_APROBACION_CONTRATO)
				|| registroSeleccionado.getIntParaTipoAprobacion().equals(Constante.PARAM_T_APROBACION_INFORME)){
					habilitarLogistica = Boolean.TRUE;
				}else{
					habilitarJefatura = Boolean.TRUE;
				}
			}
			
			if(registroSeleccionado.getIntParaEstadoAprobacionJefatura()!=null
			&& registroSeleccionado.getIntParaEstadoAprobacionJefatura().equals(Constante.PARAM_T_ESTADOREQUISICION_APROBADO)			
			&& registroSeleccionado.getIntParaEstadoAprobacionLogistica()==null){
				habilitarLogistica = Boolean.TRUE;
			}
			
			log.info("habilitarJefatura:"+habilitarJefatura);
			log.info("habilitarLogistica:"+habilitarLogistica);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verRegistro(){
		try{
			habilitarVer = Boolean.TRUE;
			
			cargarRegistro();
			
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private boolean evaluarCasoAprobacion(Requisicion requisicion){
		if(requisicion.getIntParaTipoAprobacion().equals(Constante.PARAM_T_APROBACION_CONTRATO)
		|| requisicion.getIntParaTipoAprobacion().equals(Constante.PARAM_T_APROBACION_INFORME)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private boolean validarPermisos(){
		Integer intIdPerfil = usuario.getPerfil().getId().getIntIdPerfil();
		
		if(habilitarJefatura){
			if(intIdPerfil.equals(Constante.ID_PERFIL_TESORERO_)  
			|| intIdPerfil.equals(Constante.ID_PERFIL_ASOCIATIVO)
			|| intIdPerfil.equals(Constante.ID_PERFIL_COBRANZA)
			|| intIdPerfil.equals(Constante.ID_PERFIL_CONTABILIDAD)
			|| intIdPerfil.equals(Constante.ID_PERFIL_CONTROLINTERNO)
			|| intIdPerfil.equals(Constante.ID_PERFIL_CREDITOS)
			|| intIdPerfil.equals(Constante.ID_PERFIL_GERENTEGENERAL)
			|| intIdPerfil.equals(Constante.ID_PERFIL_LOGISTICA)
			|| intIdPerfil.equals(Constante.ID_PERFIL_MARKETING)
			|| intIdPerfil.equals(Constante.ID_PERFIL_ORGANIZACIONMETODOS)
			|| intIdPerfil.equals(Constante.ID_PERFIL_SISTEMAS)
			|| intIdPerfil.equals(Constante.ID_PERFIL_OPERACIONESLIMA)
			|| intIdPerfil.equals(Constante.ID_PERFIL_OPERACIONESFILIAL)
			|| intIdPerfil.equals(Constante.ID_PERFIL_OPERACIONESCENTRAL)
			|| intIdPerfil.equals(Constante.ID_PERFIL_RECURSOSHUMANOS)
			|| intIdPerfil.equals(Constante.ID_PERFIL_ASESORLEGAL)
			|| intIdPerfil.equals(Constante.ID_PERFIL_RIESGOS)
			){
				return Boolean.TRUE;
			}
		}
		if(habilitarLogistica){
			if(intIdPerfil.equals(Constante.ID_PERFIL_LOGISTICA)  
			|| intIdPerfil.equals(Constante.ID_PERFIL_ASISTENTELOGISTICA)
			|| intIdPerfil.equals(Constante.ID_PERFIL_SISTEMAS)
			){
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;		
	}
	
	private void cargarRegistro()throws Exception{
		requisicionNuevo = registroSeleccionado;
		requisicionNuevo.setListaRequisicionDetalle(logisticaFacade.obtenerListaRequisicionDetallePorRequisicion(requisicionNuevo));
		agregarSolcittante(requisicionNuevo);
		casoEspecialAprobacion = evaluarCasoAprobacion(requisicionNuevo);
		
		
		if(habilitarJefatura){
			requisicionNuevo.setIntPersEmpresaJefatura(EMPRESA_USUARIO);
			requisicionNuevo.setIntPersPersonaJefatura(PERSONA_USUARIO);
			Persona personaJefatura = personaFacade.devolverPersonaCargada(PERSONA_USUARIO);
			personaJefatura.setStrEtiqueta(personaJefatura.getIntIdPersona()+" "+personaJefatura.getNatural().getStrNombreCompleto()+
					" DNI : "+personaJefatura.getDocumento().getStrNumeroIdentidad());
			requisicionNuevo.setPersonaJefatura(personaJefatura);
			
			requisicionNuevo.setIntSucuIdSucursalJefatura(usuario.getSucursal().getId().getIntIdSucursal());
			requisicionNuevo.setSucursalJefatura(usuario.getSucursal());
		
		}else if(habilitarLogistica || (habilitarVer && requisicionNuevo.getIntParaEstadoAprobacionLogistica()!=null)){
			habilitarJefatura = Boolean.FALSE;
			if(!casoEspecialAprobacion && !requisicionNuevo.getIntParaTipoAprobacion().equals(Constante.PARAM_T_APROBACION_CAJACHICA)){
				Persona personaJefatura = personaFacade.devolverPersonaCargada(requisicionNuevo.getIntPersPersonaJefatura());
				personaJefatura.setStrEtiqueta(personaJefatura.getIntIdPersona()+" "+personaJefatura.getNatural().getStrNombreCompleto()+
						" DNI : "+personaJefatura.getDocumento().getStrNumeroIdentidad());
				requisicionNuevo.setPersonaJefatura(personaJefatura);
				log.info(personaJefatura.getStrEtiqueta());
				
				for(Sucursal sucursal : listaSucursal){
					if(sucursal.getId().getIntIdSucursal().equals(requisicionNuevo.getIntSucuIdSucursalJefatura()))
						requisicionNuevo.setSucursalJefatura(sucursal);
				}
				habilitarJefatura = Boolean.TRUE;
			}
			
			requisicionNuevo.setIntPersEmpresaLogistica(EMPRESA_USUARIO);
			requisicionNuevo.setIntPersPersonaLogistica(PERSONA_USUARIO);
			Persona personaLogistica = personaFacade.devolverPersonaCargada(PERSONA_USUARIO);
			personaLogistica.setStrEtiqueta(personaLogistica.getIntIdPersona()+" "+personaLogistica.getNatural().getStrNombreCompleto()+
					" DNI : "+personaLogistica.getDocumento().getStrNumeroIdentidad());
			requisicionNuevo.setPersonaLogistica(personaLogistica);
			log.info(personaLogistica.getStrEtiqueta());
			
			requisicionNuevo.setIntSucuIdSucursalLogistica(usuario.getSucursal().getId().getIntIdSucursal());
			requisicionNuevo.setSucursalLogistica(usuario.getSucursal());
		}
		
		ocultarMensaje();
		cargarListaTablaAprobacion();
	}
	
	public void modificarRegistro(){
		try{
			log.info("--modificarRegistro");			
			
			if(!validarPermisos()){
				mostrarMensaje(Boolean.FALSE, "El perfil del usuario no tiene permiso para realizar esta acción."); return;
			}
			
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			
			cargarRegistro();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpAgregarRequisicionDetalle(){
		try{
			intAccion = ACCION_AGREGAR;
			requisicionDetalleNuevo = new RequisicionDetalle();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public void abrirPopUpEditarRequisicionDetalle(ActionEvent event){
		try{
			intAccion = ACCION_EDITAR;
			requisicionDetalleNuevo = (RequisicionDetalle)event.getComponent().getAttributes().get("item");			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarRegistro(){
		try{
			Requisicion requisicionEliminar = registroSeleccionado;
			List<DocumentoRequisicion> listaDocumentoRequisicion = logisticaFacade.obtenerListaDocumentoRequisicionPorRequisicion(
					EMPRESA_USUARIO, requisicionEliminar, Constante.DOCUMENTOREQUISICION_LLAMADO_DESDEREQUISICION);
			
			if(listaDocumentoRequisicion!=null && !listaDocumentoRequisicion.isEmpty()){
				mostrarMensaje(Boolean.FALSE, "No se puede eliminar la requisición, está siendo referenciada desde otro documento.");
				return;
			}
			
			requisicionEliminar.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			requisicionEliminar.setTsFechaAnula(obtenerFechaActual());
			requisicionEliminar.setIntPersEmpresaAnula(EMPRESA_USUARIO);
			requisicionEliminar.setIntPersPersonaAnula(PERSONA_USUARIO);
			
			logisticaFacade.modificarRequisicionDirecto(requisicionEliminar);
			
			mostrarMensaje(Boolean.TRUE, "Se eliminó correctamente la requisición.");
			buscar();
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la eliminación de la requisición.");
			log.error(e.getMessage(),e);
		}
	}

	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	public void habilitarPanelInferior(){
		try{
			cargarUsuario();
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			
			habilitarSolicitante = Boolean.TRUE;
			habilitarJefatura = Boolean.FALSE;
			habilitarLogistica = Boolean.FALSE;
			habilitarVer = Boolean.FALSE;
			casoEspecialAprobacion = Boolean.FALSE;
			
			requisicionNuevo = new Requisicion();
			requisicionNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			requisicionNuevo.setTsFechaRequisicion(obtenerFechaActual());
			requisicionNuevo.setIntPersEmpresaSolicitante(EMPRESA_USUARIO);
			requisicionNuevo.setIntPersPersonaSolicitante(PERSONA_USUARIO);			
			requisicionNuevo.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			requisicionNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);			
			
			Persona persona = personaFacade.devolverPersonaCargada(PERSONA_USUARIO);
			persona.setStrEtiqueta(persona.getIntIdPersona()+" - "+persona.getNatural().getStrNombreCompleto()+
					" - DNI : "+persona.getDocumento().getStrNumeroIdentidad());
			requisicionNuevo.setPersonaSolicitante(persona);
			
			Sucursal sucursal = null;
			for(Sucursal sucursalTemp : listaSucursal){
				if(sucursalTemp.getId().getIntIdSucursal().equals(usuario.getSucursal().getId().getIntIdSucursal())){
					sucursal = sucursalTemp;
				}
			}
			requisicionNuevo.setSucursalSolicitante(sucursal);
			
			requisicionDetalleNuevo = new RequisicionDetalle();
			
			cargarListaTablaAprobacion();
			habilitarGrabar = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private Tabla agregarTablaAprobacion(Integer intIdTipoAprobacion)throws Exception{
		return tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_APROBACION), intIdTipoAprobacion);
	}
	
	private void cargarListaTablaAprobacion()throws Exception{
		//Modificado por cdelosrios, 13/10/2013
		
		listaTablaAprobacion = new ArrayList<Tabla>();
		Integer intIdPerfil = usuario.getPerfil().getId().getIntIdPerfil();
		
		if(intIdPerfil.equals(Constante.ID_PERFIL_LOGISTICA)  
		|| intIdPerfil.equals(Constante.ID_PERFIL_ASISTENTELOGISTICA)
		|| intIdPerfil.equals(Constante.ID_PERFIL_SISTEMAS)){
			listaTablaAprobacion.add(agregarTablaAprobacion(Constante.PARAM_T_APROBACION_CONTRATO));		
		}
		if(intIdPerfil.equals(Constante.ID_PERFIL_ASISTENTEGERENCIAGENERAL)  
		|| intIdPerfil.equals(Constante.ID_PERFIL_SISTEMAS)){
			listaTablaAprobacion.add(agregarTablaAprobacion(Constante.PARAM_T_APROBACION_INFORME));		
		}
		if(intIdPerfil.equals(Constante.ID_PERFIL_LOGISTICA)
		|| intIdPerfil.equals(Constante.ID_PERFIL_SISTEMAS)){
			listaTablaAprobacion.add(agregarTablaAprobacion(Constante.PARAM_T_APROBACION_LOGISTICA));		
		}
		if(!intIdPerfil.equals(Constante.ID_PERFIL_ADMINZONACENTRAL)  
		&& !intIdPerfil.equals(Constante.ID_PERFIL_ADMINZONAFILIAL)
		&& !intIdPerfil.equals(Constante.ID_PERFIL_ADMINZONALIMA)){
			listaTablaAprobacion.add(agregarTablaAprobacion(Constante.PARAM_T_APROBACION_EVALUACIONPROVEEDORES));		
		}
		
		listaTablaAprobacion.add(agregarTablaAprobacion(Constante.PARAM_T_APROBACION_ORDENCOMPRA));
		listaTablaAprobacion.add(agregarTablaAprobacion(Constante.PARAM_T_APROBACION_CAJACHICA));		
		
		
		if(requisicionNuevo.getId().getIntItemRequisicion()!=null){
			List<Tabla> listaTemp = new ArrayList<Tabla>();
			if(casoEspecialAprobacion){
				for(Tabla tablaAprobacion : listaTablaAprobacion){
					if(tablaAprobacion.getIntIdDetalle().equals(Constante.PARAM_T_APROBACION_CONTRATO)
					|| tablaAprobacion.getIntIdDetalle().equals(Constante.PARAM_T_APROBACION_INFORME))
						listaTemp.add(tablaAprobacion);
				}
			}else{
				for(Tabla tablaAprobacion : listaTablaAprobacion){
					if(!tablaAprobacion.getIntIdDetalle().equals(Constante.PARAM_T_APROBACION_CONTRATO)
					&& !tablaAprobacion.getIntIdDetalle().equals(Constante.PARAM_T_APROBACION_INFORME))
						listaTemp.add(tablaAprobacion);
				}
			}
			listaTablaAprobacion = listaTemp;
		}
		
		if(intIdPerfil.equals(Constante.ID_PERFIL_LOGISTICA)){
			listaTablaAprobacion = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_APROBACION));
		}
		
		//Fin modificado por cdelosrios, 13/10/2013
	}
	
	private void cargarListaSucursal() throws Exception{
		listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(EMPRESA_USUARIO);
		for(Sucursal sucursal : listaSucursal){
			sucursal.setListaArea(empresaFacade.getListaAreaPorSucursal(sucursal));			
		}
		//Ordenamos por nombre
		Collections.sort(listaSucursal, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});	
	}
	
	public void mostrarMensaje(boolean exito, String mensaje){
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
	
	public void ocultarMensaje(){
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;		
	}
	
	public void agregarRequisicionDetalle(){
		try{
			if(requisicionDetalleNuevo.getIntCantidad()==null  || requisicionDetalleNuevo.getIntCantidad().intValue()<=0){
				mostrarMensaje(Boolean.FALSE, "Debe ingresar una cantidad correcta."); return;
			}
			if(requisicionDetalleNuevo.getStrDetalle()==null || requisicionDetalleNuevo.getStrDetalle().isEmpty()){
				mostrarMensaje(Boolean.FALSE, "Debe ingresar una descripción."); return;
			}
			
			if(intAccion.equals(ACCION_EDITAR)){
				return;
			}
			
			requisicionDetalleNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			requisicionDetalleNuevo.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			requisicionDetalleNuevo.setIntPersPersonaUsuario(PERSONA_USUARIO);
			requisicionDetalleNuevo.setTsFechaRegistro(obtenerFechaActual());
			
			requisicionNuevo.getListaRequisicionDetalle().add(requisicionDetalleNuevo);
			requisicionDetalleNuevo = new RequisicionDetalle();
			
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void quitarRequisicionDetalle(ActionEvent event){
		try{
			RequisicionDetalle requisicionDetalleQuitar = (RequisicionDetalle)event.getComponent().getAttributes().get("item");
			requisicionNuevo.getListaRequisicionDetalle().remove(requisicionDetalleQuitar);
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}		
	}
	
	//Agregado por cdelosrios, 26/11/2013
	public void imprimirRequisicion(){
		String strNombreReporte = "";
		//Requisicion requisicion = (Requisicion)event.getComponent().getAttributes().get("requisicion");
		Requisicion requisicion = registroSeleccionado;
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Natural natural = null;
		String strPersonaSolicitante =null;
		
		try {
			natural = personaFacade.getNaturalPorPK(requisicion.getIntPersPersonaSolicitante());
			
			strPersonaSolicitante = (natural.getStrApellidoPaterno() + " " + 
									 natural.getStrApellidoMaterno() + " " +
									 natural.getStrNombres());
			//Año Actual
			parametro.put("P_ITEMREQUISICION", requisicion.getId().getIntItemRequisicion());
			parametro.put("P_AREA", requisicion.getArea().getStrDescripcion());
			parametro.put("P_SOLICITANTE", strPersonaSolicitante);
			parametro.put("P_FECHA", Constante.sdf.format(requisicion.getTsFechaRequisicion()));
			parametro.put("P_REQUISICION", requisicion.getIntParaTipoRequisicion());
			
			strNombreReporte = "requisicion";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(requisicion.getListaRequisicionDetalle()), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			log.error("Error en imprimirReporteCaptaciones ---> "+e);
		}
	}
	//Fin agregado por cdelosrios, 26/11/2013
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
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
	public List<Requisicion> getListaRequisicion() {
		return listaRequisicion;
	}
	public void setListaRequisicion(List<Requisicion> listaRequisicion) {
		this.listaRequisicion = listaRequisicion;
	}
	public Requisicion getRequisicionNuevo() {
		return requisicionNuevo;
	}
	public void setRequisicionNuevo(Requisicion requisicionNuevo) {
		this.requisicionNuevo = requisicionNuevo;
	}
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}
	public RequisicionDetalle getRequisicionDetalleNuevo() {
		return requisicionDetalleNuevo;
	}
	public void setRequisicionDetalleNuevo(RequisicionDetalle requisicionDetalleNuevo) {
		this.requisicionDetalleNuevo = requisicionDetalleNuevo;
	}
	public Requisicion getRequisicionFiltro() {
		return requisicionFiltro;
	}
	public void setRequisicionFiltro(Requisicion requisicionFiltro) {
		this.requisicionFiltro = requisicionFiltro;
	}
	public Requisicion getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(Requisicion registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public boolean isHabilitarSolicitante() {
		return habilitarSolicitante;
	}
	public void setHabilitarSolicitante(boolean habilitarSolicitante) {
		this.habilitarSolicitante = habilitarSolicitante;
	}
	public boolean isHabilitarJefatura() {
		return habilitarJefatura;
	}
	public void setHabilitarJefatura(boolean habilitarJefatura) {
		this.habilitarJefatura = habilitarJefatura;
	}
	public boolean isHabilitarLogistica() {
		return habilitarLogistica;
	}
	public void setHabilitarLogistica(boolean habilitarLogistica) {
		this.habilitarLogistica = habilitarLogistica;
	}
	public boolean isHabilitarVer() {
		return habilitarVer;
	}
	public void setHabilitarVer(boolean habilitarVer) {
		this.habilitarVer = habilitarVer;
	}
	public List<Tabla> getListaTablaAprobacion() {
		return listaTablaAprobacion;
	}
	public void setListaTablaAprobacion(List<Tabla> listaTablaAprobacion) {
		this.listaTablaAprobacion = listaTablaAprobacion;
	}
	public boolean isCasoEspecialAprobacion() {
		return casoEspecialAprobacion;
	}
	public void setCasoEspecialAprobacion(boolean casoEspecialAprobacion) {
		this.casoEspecialAprobacion = casoEspecialAprobacion;
	}
}