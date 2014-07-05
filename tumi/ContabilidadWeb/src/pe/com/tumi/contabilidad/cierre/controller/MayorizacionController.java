package pe.com.tumi.contabilidad.cierre.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MayorizacionException;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalle;
import pe.com.tumi.contabilidad.cierre.facade.CierreFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class MayorizacionController {

	protected static Logger log = Logger.getLogger(MayorizacionController.class);
	
	
	private CierreFacadeLocal cierreFacade;
	private PersonaFacadeRemote personaFacade;
	
	private List listaLibroMayor;
	private List listaAnios;
	
	private LibroMayor libroMayorFiltro;
	private LibroMayor libroMayorNuevo;
	private LibroMayor registroSeleccionado;
	
	private Usuario usuario;
	private String mensajeOperacion;
	private final int cantidadAñosLista = 4;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean seleccionaRegistro;
	
	public MayorizacionController(){		
		cargarValoresIniciales();
	}
	
	public void cargarValoresIniciales(){
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!=null){
				listaLibroMayor = new ArrayList<LibroMayor>();
				libroMayorFiltro = new LibroMayor();
				libroMayorNuevo  = new LibroMayor();
				libroMayorFiltro.setIntParaEstadoCierreCod(Constante.PARAM_T_TIPOESTADOCIERRE_TODOS);
				seleccionaRegistro = Boolean.FALSE;
				
				cierreFacade = (CierreFacadeLocal) EJBFactory.getLocal(CierreFacadeLocal.class);
				personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
				cargarListaAnios();
				
			}else{
				log.error("--Usuario obtenido es NULL.");
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaAnios(){
		listaAnios = new ArrayList<Tabla>();
		Calendar cal=Calendar.getInstance();
		Tabla tabla = null;
		for(int i=0;i<cantidadAñosLista;i++){
			tabla = new Tabla();
			int year = cal.get(Calendar.YEAR);
			cal.add(Calendar.YEAR, 1);
			tabla.setIntIdDetalle(year);
			tabla.setStrDescripcion(""+year);
			listaAnios.add(tabla);
		}		
	}
	
	public void buscar(){
		try{
			if(libroMayorFiltro.getId().getIntContMesMayor().equals(Constante.PARAM_T_MES_CALENDARIO_TODOS)){
				libroMayorFiltro.getId().setIntContMesMayor(null);
			}
			if(libroMayorFiltro.getIntParaEstadoCierreCod().equals(Constante.PARAM_T_TIPOESTADOCIERRE_TODOS)){
				libroMayorFiltro.setIntParaEstadoCierreCod(null);
			}
			listaLibroMayor = cierreFacade.buscarLibroMayor(libroMayorFiltro);
			cargarLibroMayorDetalle();
			cargarNombreUsuario();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	private void cargarLibroMayorDetalle() throws Exception{		
		LibroMayor libroMayor = null;
		List<LibroMayorDetalle> listaLibroMayorDetalle = null ;
		for(Object o : listaLibroMayor){
			libroMayor = (LibroMayor)o;
			listaLibroMayorDetalle = cierreFacade.getListaLibroMayorDetallePorLibroMayor(libroMayor);
			if(listaLibroMayorDetalle!=null && !listaLibroMayorDetalle.isEmpty()){
				libroMayor.setListaLibroMayorDetalle(listaLibroMayorDetalle);
				libroMayor.setEsProcesado(Boolean.TRUE);
			}else{
				libroMayor.setListaLibroMayorDetalle(null);
				libroMayor.setEsProcesado(Boolean.FALSE);
			}
		}
	}
	
	private void cargarNombreUsuario() throws Exception{		
		LibroMayor libroMayor = null;
		Natural natural = null ;
		for(Object o : listaLibroMayor){
			libroMayor = (LibroMayor)o;
			natural = personaFacade.getNaturalPorPK(libroMayor.getIntPersPersonaUsuario());
			libroMayor.setNaturalPersonaUsuario(natural);
		}
	}
	
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			libroMayorNuevo = new LibroMayor();
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;			
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (LibroMayor)event.getComponent().getAttributes().get("item");
			seleccionaRegistro = Boolean.TRUE;
			log.info("reg selec:"+registroSeleccionado);						
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarProcesar(ActionEvent event){
		boolean exito = Boolean.FALSE;
		String mensaje = "";
		try{
			registroSeleccionado = (LibroMayor)event.getComponent().getAttributes().get("item");
			log.info("reg selec:"+registroSeleccionado);
			
			cierreFacade.mayorizar(registroSeleccionado);
			
			registroSeleccionado.setEsProcesado(Boolean.TRUE);
			seleccionaRegistro = Boolean.FALSE;
			exito = Boolean.TRUE;
			mensaje = "Se realizó el proceso de mayorización correctamente para el periodo "+registroSeleccionado.getId().getIntContMesMayor()+"-"+registroSeleccionado.getId().getIntContPeriodoMayor()+".";
		}catch (MayorizacionException e) {
			mensaje = e.getMessage();
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			mensaje = "Hubo un error durante el proceso de mayorización para el periodo "+registroSeleccionado.getId().getIntContMesMayor()+"-"+registroSeleccionado.getId().getIntContPeriodoMayor()+".";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito, mensaje);
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
	
	public void eliminarRegistro(){
		boolean exito = Boolean.FALSE;
		String mensaje = "";
		try{
			cierreFacade.eliminarMayorizacion(registroSeleccionado);
			registroSeleccionado.setEsProcesado(Boolean.FALSE);
			exito = Boolean.TRUE;
			mensaje = "Se ha eliminado correctamente la mayorización del periodo "+registroSeleccionado.getId().getIntContMesMayor()+"-"+registroSeleccionado.getId().getIntContPeriodoMayor();
		}catch (Exception e) {
			mensaje = "Ha ocurrido un error durante la eliminación de la mayorización del periodo "+registroSeleccionado.getId().getIntContMesMayor()+"-"+registroSeleccionado.getId().getIntContPeriodoMayor();
			log.error(e.getMessage(),e);
		}finally{
			buscar();
			mostrarMensaje(exito, mensaje);
		}
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
	
	private void cargarRegistro()throws BusinessException{
		try{
			libroMayorNuevo = registroSeleccionado;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
	}
	
	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public List getListaLibroMayor() {
		return listaLibroMayor;
	}
	public void setListaLibroMayor(List listaLibroMayor) {
		this.listaLibroMayor = listaLibroMayor;
	}
	public List getListaAnios() {
		return listaAnios;
	}
	public void setListaAnios(List listaAnios) {
		this.listaAnios = listaAnios;
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
	public LibroMayor getLibroMayorFiltro() {
		return libroMayorFiltro;
	}
	public void setLibroMayorFiltro(LibroMayor libroMayorFiltro) {
		this.libroMayorFiltro = libroMayorFiltro;
	}
	public LibroMayor getLibroMayorNuevo() {
		return libroMayorNuevo;
	}
	public void setLibroMayorNuevo(LibroMayor libroMayorNuevo) {
		this.libroMayorNuevo = libroMayorNuevo;
	}
	public LibroMayor getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(LibroMayor registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public boolean isSeleccionaRegistro() {
		return seleccionaRegistro;
	}
	public void setSeleccionaRegistro(boolean seleccionaRegistro) {
		this.seleccionaRegistro = seleccionaRegistro;
	}	
}