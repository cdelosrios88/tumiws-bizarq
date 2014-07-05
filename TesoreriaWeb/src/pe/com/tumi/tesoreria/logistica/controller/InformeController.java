package pe.com.tumi.tesoreria.logistica.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;
import pe.com.tumi.tesoreria.logistica.domain.InformeGerencia;
import pe.com.tumi.tesoreria.logistica.domain.Proveedor;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorDetalle;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorId;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionId;
import pe.com.tumi.tesoreria.logistica.facade.LogisticaFacadeLocal;


public class InformeController {

	protected static Logger log = Logger.getLogger(InformeController.class);
	
	PersonaFacadeRemote 	personaFacade;
	EmpresaFacadeRemote		empresaFacade;
	LogisticaFacadeLocal	logisticaFacade;
	GeneralFacadeRemote		generalFacade;
	TablaFacadeRemote		tablaFacade;
	
	private	InformeGerencia		informeGerenciaNuevo;
	private InformeGerencia		informeGerenciaFiltro;
	private InformeGerencia		registroSeleccionado;
	private List<Sucursal>		listaSucursal;
	private List<InformeGerencia>	listaInformeGerencia;
	private List<Persona>		listaPersona;
	private List<Requisicion>	listaRequisicion;
	private List<Proveedor>		listaProveedor;
	private List<Tabla>			listaTablaProveedor;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private Integer		intTipoPersona;
	private String		strFiltroTextoPersona;
	
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean poseePermiso;
	
	
	
	public InformeController(){
		cargarUsuario();
		//poseePermiso = Boolean.TRUE;
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_INFORME);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
	}
	
	public String getLimpiarInforme(){
		cargarUsuario();
		//poseePermiso = Boolean.TRUE;
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_INFORME);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
			//Agregado por cdelosrios, 25/11/2013
			deshabilitarPanelInferior();
			//Agregado por cdelosrios, 25/11/2013
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
			generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			tablaFacade =  (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			
			listaTablaProveedor = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOPROVEEDOR));
			informeGerenciaFiltro = new InformeGerencia();
			listaInformeGerencia = new ArrayList<InformeGerencia>();
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

			if(informeGerenciaNuevo.getRequisicion()==null){
				mostrarMensaje(Boolean.FALSE, "Debe agregar una Requisición."); return;
			}
			if(informeGerenciaNuevo.getEmpresaServicio()==null){
				mostrarMensaje(Boolean.FALSE, "Debe agregar una Empresa Servicio."); return;
			}
			if(informeGerenciaNuevo.getPersonaAutoriza()==null){
				mostrarMensaje(Boolean.FALSE, "Debe agregar una Persona Autoriza."); return;
			}
			if(informeGerenciaNuevo.getIntIdArea()==null || informeGerenciaNuevo.getIntIdArea().equals(new Integer(0))){
				mostrarMensaje(Boolean.FALSE, "Debe seleccionar un área."); return;
			}
			if(informeGerenciaNuevo.getDtFechaInforme()==null){
				mostrarMensaje(Boolean.FALSE, "Debe seleccionar una fecha de informe."); return;
			}
			if(informeGerenciaNuevo.getBdMontoAutorizado()==null || informeGerenciaNuevo.getBdMontoAutorizado().signum()<=0){
				mostrarMensaje(Boolean.FALSE, "Debe ingresar un monto autorizado."); return;
			}
			if(informeGerenciaNuevo.getArchivoDocumento()==null){
				mostrarMensaje(Boolean.FALSE, "Debe adjuntar un documento."); return;
			}
			if(informeGerenciaNuevo.getStrObservacion()==null || informeGerenciaNuevo.getStrObservacion().isEmpty()){
				mostrarMensaje(Boolean.FALSE, "Debe ingresar una observación."); return;
			}
			
			logisticaFacade.grabarInformeGerencia(informeGerenciaNuevo);			
			
			buscar();
			mostrarMensaje(Boolean.TRUE, "Se registró correctamente el Informe de Gerencia.");
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro del Informe de Gerencia.");
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void buscar(){
		try{
			informeGerenciaFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);			
			
			if(informeGerenciaFiltro.getIntParaTipoInforme().equals(new Integer(0))){
				informeGerenciaFiltro.setIntParaTipoInforme(null);
			}
			if(informeGerenciaFiltro.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS)){
				informeGerenciaFiltro.setIntParaEstado(null);
			}
			if(informeGerenciaFiltro.getIntSucuIdSucursal().equals(new Integer(0))){
				informeGerenciaFiltro.setIntSucuIdSucursal(null);
			}
			
			listaInformeGerencia = logisticaFacade.buscarInformeGerencia(informeGerenciaFiltro);
			
			for(InformeGerencia informeGerencia : listaInformeGerencia){				
				Persona empresaServicio = personaFacade.getPersonaJuridicaPorIdPersona(informeGerencia.getIntPersPersonaServicio());
				empresaServicio.setStrEtiqueta(empresaServicio.getIntIdPersona()+"-"+empresaServicio.getJuridica().getStrRazonSocial()
						+"-RUC:"+empresaServicio.getStrRuc());
				informeGerencia.setEmpresaServicio(empresaServicio);
				for(Sucursal sucursal : listaSucursal){
					if(sucursal.getId().getIntIdSucursal().equals(informeGerencia.getIntSucuIdSucursal()))
						informeGerencia.setSucursal(sucursal);
				}
				
				informeGerencia.setOrdenCompra(logisticaFacade.obtenerOrdenCompraPorInformeGerencia(informeGerencia));
			}
			
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	
	public void seleccionarRegistro(ActionEvent event){
		try{
			cargarUsuario();
			registroSeleccionado = (InformeGerencia)event.getComponent().getAttributes().get("item");
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verRegistro(){
		try{
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;			
			mostrarPanelInferior = Boolean.TRUE;
			
			informeGerenciaNuevo = registroSeleccionado;
			
			RequisicionId requisicionId = new RequisicionId();
			requisicionId.setIntPersEmpresa(informeGerenciaNuevo.getIntPersEmpresaRequisicion());
			requisicionId.setIntItemRequisicion(informeGerenciaNuevo.getIntItemRequisicion());
			Requisicion requisicion = logisticaFacade.obtenerRequisicionPorId(requisicionId);
			añadirEtiquetaRequisicion(requisicion);
			informeGerenciaNuevo.setRequisicion(requisicion);
			
			Persona personaAutoriza = personaFacade.getPersonaNaturalPorIdPersona(informeGerenciaNuevo.getIntPersPersonaAutoriza());
			agregarDocumentoDNI(personaAutoriza);
			agregarNombreCompleto(personaAutoriza);
			informeGerenciaNuevo.setPersonaAutoriza(personaAutoriza);
			
			seleccionarSucursal();
			
			ArchivoId archivoId = new ArchivoId();
			archivoId.setIntParaTipoCod(informeGerenciaNuevo.getIntParaTipo());
			archivoId.setIntItemArchivo(informeGerenciaNuevo.getIntItemArchivo());
			archivoId.setIntItemHistorico(informeGerenciaNuevo.getIntItemHistorico());			
			informeGerenciaNuevo.setArchivoDocumento(generalFacade.getArchivoPorPK(archivoId));			
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void modificarRegistro(){
		try{
			log.info("--modificarRegistro");			
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			

			
			ocultarMensaje();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}

	public void abrirPopUpBuscarRequisicion(){
		try{
			listaRequisicion = logisticaFacade.obtenerListaRequisicionReferencia(EMPRESA_USUARIO, Constante.PARAM_T_APROBACION_INFORME);			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public void seleccionarRequisicion(ActionEvent event){
		try{
			Requisicion requisicionAgregar = (Requisicion)event.getComponent().getAttributes().get("item");
			
			añadirEtiquetaRequisicion(requisicionAgregar);
			informeGerenciaNuevo.setIntPersEmpresaRequisicion(requisicionAgregar.getId().getIntPersEmpresa());
			informeGerenciaNuevo.setIntItemRequisicion(requisicionAgregar.getId().getIntItemRequisicion());
			informeGerenciaNuevo.setRequisicion(requisicionAgregar);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	

	private void añadirEtiquetaRequisicion(Requisicion requisicionAgregar)throws Exception{
		TablaFacadeRemote tablaFacade =  (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
		Tabla tablaRequisicion = tablaFacade.getTablaPorIdMaestroYIdDetalle(
				Integer.parseInt(Constante.PARAM_T_REQUISICION), requisicionAgregar.getIntParaTipoRequisicion());
		Tabla tablaAprobacion = tablaFacade.getTablaPorIdMaestroYIdDetalle(
				Integer.parseInt(Constante.PARAM_T_APROBACION), requisicionAgregar.getIntParaTipoAprobacion());
		String strEtiqueta = requisicionAgregar.getId().getIntItemRequisicion() + " "+tablaRequisicion.getStrDescripcion()
			+" "+tablaAprobacion.getStrDescripcion();
		
		requisicionAgregar.setStrEtiqueta(strEtiqueta);
	}
	
	public void abrirPopUpBuscarEmpresaServicio(){
		try{
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_JURIDICA;
			strFiltroTextoPersona = "";
			listaPersona = new ArrayList<Persona>();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpBuscarPersonaAutoriza(){
		try{
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			strFiltroTextoPersona = "";
			listaPersona = new ArrayList<Persona>();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarAdjuntarDocumento(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			Archivo archivoDocumento = fileUploadController.getArchivoDocumento();
			
			informeGerenciaNuevo.setIntParaTipo(archivoDocumento.getId().getIntParaTipoCod());
			informeGerenciaNuevo.setIntItemArchivo(archivoDocumento.getId().getIntItemArchivo());
			informeGerenciaNuevo.setIntItemHistorico(archivoDocumento.getId().getIntItemHistorico());
			informeGerenciaNuevo.setArchivoDocumento(archivoDocumento);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarPersona(){
		try{
			listaProveedor = new ArrayList<Proveedor>();
			log.info("--buscarPersona");
			Persona persona = null;
			boolean proveedorValido = Boolean.TRUE;
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
						Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
						strFiltroTextoPersona, 
						EMPRESA_USUARIO);
				proveedorValido = MyUtil.poseeRol(persona,Constante.PARAM_T_TIPOROL_PERSONAL);
				
			}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				persona = personaFacade.getPersonaJuridicaYListaPersonaPorRucYIdEmpresa2(strFiltroTextoPersona,EMPRESA_USUARIO);				
				proveedorValido = MyUtil.poseeRol(persona,Constante.PARAM_T_TIPOROL_PROVEEDOR)
					&& (persona.getJuridica().getIntCondContribuyente()!=null 
						&& !persona.getJuridica().getIntCondContribuyente().equals(Constante.PARAM_T_TIPOCONDCONTRIBUYENTE_NOHABIDO))
					&& (persona.getJuridica().getIntEstadoContribuyenteCod() !=null 
						&& !persona.getJuridica().getIntEstadoContribuyenteCod().equals(Constante.PARAM_T_TIPOESTADOCONTRIB_INACTIVO));		
			}
			
			log.info(persona);
			if(proveedorValido){
				if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
					listaPersona.add(persona);
				
				}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
					ProveedorId proveedorId = new ProveedorId();
					proveedorId.setIntPersEmpresa(EMPRESA_USUARIO);
					proveedorId.setIntPersPersona(persona.getIntIdPersona());
					Proveedor proveedor = logisticaFacade.getProveedorPorPK(proveedorId);
					String strListaProveedorDetalle = "";
					for(ProveedorDetalle proveedorDetalle : proveedor.getListaProveedorDetalle()){
						strListaProveedorDetalle = strListaProveedorDetalle + " / " +obtenerEtiquetaTipoProveedor(proveedorDetalle.getIntParaTipoProveedor());
					}
					proveedor.setStrListaProveedorDetalle(strListaProveedorDetalle);
					proveedor.setPersona(persona);
					listaProveedor.add(proveedor);
				}
				
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	private String obtenerEtiquetaTipoProveedor(Integer intTipoProveedor){
		for(Tabla tabla : listaTablaProveedor){
			if(tabla.getIntIdDetalle().equals(intTipoProveedor)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}	
	
	private void agregarNombreCompleto(Persona persona){
		persona.getNatural().setStrNombreCompleto(
				persona.getNatural().getStrNombres()+" "+
				persona.getNatural().getStrApellidoPaterno()+" "+
				persona.getNatural().getStrApellidoMaterno());
	}
	
	private void agregarDocumentoDNI(Persona persona){
		for(Documento documento : persona.getListaDocumento()){
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
				persona.setDocumento(documento);
			}
		}
	}
	
	public void seleccionarPersona(ActionEvent event){
		try{
			Persona personaSeleccionada = (Persona)event.getComponent().getAttributes().get("item");
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				agregarNombreCompleto(personaSeleccionada);
				agregarDocumentoDNI(personaSeleccionada);
				
				informeGerenciaNuevo.setIntPersPersonaAutoriza(personaSeleccionada.getIntIdPersona());
				informeGerenciaNuevo.setPersonaAutoriza(personaSeleccionada);
			
			}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){				
				
				informeGerenciaNuevo.setIntPersPersonaServicio(personaSeleccionada.getIntIdPersona());				
				informeGerenciaNuevo.setEmpresaServicio(personaSeleccionada);
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarRegistro(){
		try{
			InformeGerencia informeGerenciaEliminar = registroSeleccionado;
			
			if(informeGerenciaEliminar.getOrdenCompra()!=null){
				mostrarMensaje(Boolean.TRUE, "No se puede eliminar el Informe de Gerencia porque esta asociado a una Orden de Compra.");
			}
			
			cargarUsuario();
			informeGerenciaEliminar.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			informeGerenciaEliminar.setTsFechaAnula(obtenerFechaActual());
			informeGerenciaEliminar.setIntPersEmpresaAnula(EMPRESA_USUARIO);
			informeGerenciaEliminar.setIntPersPersonaAnula(PERSONA_USUARIO);
			
			logisticaFacade.modificarInformeGerenciaDirecto(informeGerenciaEliminar);
			buscar();
			mostrarMensaje(Boolean.TRUE, "Se eliminó correctamente el informe de gerencia.");
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
			
			informeGerenciaNuevo = new InformeGerencia();
			
			informeGerenciaNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			informeGerenciaNuevo.setTsFechaRegistro(obtenerFechaActual());
			informeGerenciaNuevo.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			informeGerenciaNuevo.setIntPersPersonaUsuario(PERSONA_USUARIO);
			informeGerenciaNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			//añadimos a informeGerenciaNuevo la sucursal del usuario cargada con listaArea
			informeGerenciaNuevo.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			seleccionarSucursal();
			
			habilitarGrabar = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarSucursal(){
		try{
			for(Sucursal sucursal : listaSucursal){
				if(sucursal.getId().getIntIdSucursal().equals(informeGerenciaNuevo.getIntSucuIdSucursal())){
					informeGerenciaNuevo.setSucursal(sucursal);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
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

			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		return sesion.getAttribute(beanName);
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
	public InformeGerencia getInformeGerenciaNuevo() {
		return informeGerenciaNuevo;
	}
	public void setInformeGerenciaNuevo(InformeGerencia informeGerenciaNuevo) {
		this.informeGerenciaNuevo = informeGerenciaNuevo;
	}
	public InformeGerencia getInformeGerenciaFiltro() {
		return informeGerenciaFiltro;
	}
	public void setInformeGerenciaFiltro(InformeGerencia informeGerenciaFiltro) {
		this.informeGerenciaFiltro = informeGerenciaFiltro;
	}
	public List<InformeGerencia> getListaInformeGerencia() {
		return listaInformeGerencia;
	}
	public void setListaInformeGerencia(List<InformeGerencia> listaInformeGerencia) {
		this.listaInformeGerencia = listaInformeGerencia;
	}
	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}
	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
	}
	public String getStrFiltroTextoPersona() {
		return strFiltroTextoPersona;
	}
	public void setStrFiltroTextoPersona(String strFiltroTextoPersona) {
		this.strFiltroTextoPersona = strFiltroTextoPersona;
	}
	public List<Persona> getListaPersona() {
		return listaPersona;
	}
	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}
	public List<Requisicion> getListaRequisicion() {
		return listaRequisicion;
	}
	public void setListaRequisicion(List<Requisicion> listaRequisicion) {
		this.listaRequisicion = listaRequisicion;
	}
	public InformeGerencia getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(InformeGerencia registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public List<Proveedor> getListaProveedor() {
		return listaProveedor;
	}
	public void setListaProveedor(List<Proveedor> listaProveedor) {
		this.listaProveedor = listaProveedor;
	}
}