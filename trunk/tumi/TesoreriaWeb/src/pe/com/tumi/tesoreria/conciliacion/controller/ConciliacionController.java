package pe.com.tumi.tesoreria.conciliacion.controller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;
import pe.com.tumi.tesoreria.logistica.facade.LogisticaFacadeLocal;


public class ConciliacionController {

	protected static Logger log = Logger.getLogger(ConciliacionController.class);
	
	PersonaFacadeRemote 	personaFacade;
	EmpresaFacadeRemote		empresaFacade;
	LogisticaFacadeLocal	logisticaFacade;
	GeneralFacadeRemote		generalFacade;
	ContactoFacadeRemote	contactoFacade;
	TablaFacadeRemote		tablaFacade;
	PlanCuentaFacadeRemote	planCuentaFacade;
	EgresoFacadeLocal		egresoFacade;
	BancoFacadeLocal		bancoFacade;
	
	private	Conciliacion	conciliacionNuevo;
	private Conciliacion	conciliacionFiltro;
	private Conciliacion	registroSeleccionado;
	private Bancocuenta		bancoCuentaFiltro;
	
	private List<Conciliacion>	listaConciliacion;
	private List<Bancocuenta>	listaBancoCuenta;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean datosValidados;
	
	
	public ConciliacionController(){
		cargarUsuario();
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
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
			tablaFacade  = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			planCuentaFacade  = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			egresoFacade  = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);			
			bancoFacade  = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			
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
			
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro de la Conciliacion Bancaria.");
			log.error(e.getMessage(),e);
		}
	}	
	
	public void buscar(){
		try{
			cargarUsuario();

			
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			cargarUsuario();
			registroSeleccionado = (Conciliacion)event.getComponent().getAttributes().get("item");
			log.info(registroSeleccionado);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verRegistro(){
		try{
			if(registroSeleccionado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				deshabilitarNuevo = Boolean.FALSE;
				habilitarGrabar = Boolean.TRUE;
			}else{
				deshabilitarNuevo = Boolean.TRUE;
				habilitarGrabar = Boolean.FALSE;
			}
			mostrarPanelInferior = Boolean.TRUE;
			
			
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
	
	public void eliminarRegistro(){
		try{
			Conciliacion conciliacionEliminar = registroSeleccionado;			
			
			buscar();
			mostrarMensaje(Boolean.TRUE, "Se elimin� correctamente la Solicitud Personal.");
		}catch (Exception e){
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la eliminaci�n de la Solicitud Personal.");
			log.error(e.getMessage(),e);
		}
	}
	
	public void habilitarPanelInferior(){
		try{
			cargarUsuario();
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			datosValidados = Boolean.FALSE;
			
			conciliacionNuevo = new Conciliacion();
			conciliacionNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			conciliacionNuevo.setTsFechaConciliacion(MyUtil.obtenerFechaActual());
			
			
			habilitarGrabar = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
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
	
	public void abrirPopUpBuscarBancoCuenta(){
		try{
			bancoCuentaFiltro = new Bancocuenta();
			bancoCuentaFiltro.getId().setIntEmpresaPk(EMPRESA_USUARIO);
			bancoCuentaFiltro.setIntEmpresacuentaPk(EMPRESA_USUARIO);
			bancoCuentaFiltro.setBancofondo(new Bancofondo());
			bancoCuentaFiltro.setIntPeriodocuenta(MyUtil.obtenerA�oActual()-1);
			bancoCuentaFiltro.getBancofondo().setIntBancoCod(Constante.PARAM_T_BANCOS_BANCOCREDITO);
			
			buscarBancoCuenta();
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarBancoCuenta(){
		try{
			if(bancoCuentaFiltro.getStrNumerocuenta()!=null && bancoCuentaFiltro.getStrNumerocuenta().isEmpty())
				bancoCuentaFiltro.setStrNumerocuenta(null);
			
			listaBancoCuenta = bancoFacade.buscarListaBancoCuenta(bancoCuentaFiltro);
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
	}	
	
	public void seleccionarBancoCuenta(ActionEvent event){
		try{
			Bancocuenta bancoCuentaSeleccionado = (Bancocuenta)event.getComponent().getAttributes().get("item");
			conciliacionNuevo.setBancoCuenta(bancoCuentaSeleccionado);
			log.info(bancoCuentaSeleccionado);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void validarDatos(){
		try{
			datosValidados = Boolean.TRUE;
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
	public Conciliacion getConciliacionNuevo() {
		return conciliacionNuevo;
	}
	public void setConciliacionNuevo(Conciliacion conciliacionNuevo) {
		this.conciliacionNuevo = conciliacionNuevo;
	}
	public Conciliacion getConciliacionFiltro() {
		return conciliacionFiltro;
	}
	public void setConciliacionFiltro(Conciliacion conciliacionFiltro) {
		this.conciliacionFiltro = conciliacionFiltro;
	}
	public List<Conciliacion> getListaConciliacion() {
		return listaConciliacion;
	}
	public void setListaConciliacion(List<Conciliacion> listaConciliacion) {
		this.listaConciliacion = listaConciliacion;
	}
	public List<Bancocuenta> getListaBancoCuenta() {
		return listaBancoCuenta;
	}
	public void setListaBancoCuenta(List<Bancocuenta> listaBancoCuenta) {
		this.listaBancoCuenta = listaBancoCuenta;
	}
	public Bancocuenta getBancoCuentaFiltro() {
		return bancoCuentaFiltro;
	}
	public void setBancoCuentaFiltro(Bancocuenta bancoCuentaFiltro) {
		this.bancoCuentaFiltro = bancoCuentaFiltro;
	}
	public boolean isDatosValidados() {
		return datosValidados;
	}
	public void setDatosValidados(boolean datosValidados) {
		this.datosValidados = datosValidados;
	}
}