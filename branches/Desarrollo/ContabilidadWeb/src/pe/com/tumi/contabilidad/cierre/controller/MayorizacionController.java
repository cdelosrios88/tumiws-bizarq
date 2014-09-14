package pe.com.tumi.contabilidad.cierre.controller;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class MayorizacionController {

	protected static Logger log;
	private Usuario usuario;
	private List listaLibroMayor;
	private LibroMayor libroMayorFiltro;
	private LibroMayor libroMayorNuevo;
	private List listaAnios;
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean seleccionaRegistro;
	
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

	public boolean isSeleccionaRegistro() {
		return seleccionaRegistro;
	}

	public void setSeleccionaRegistro(boolean seleccionaRegistro) {
		this.seleccionaRegistro = seleccionaRegistro;
	}

	public List getListaAnios() {
		return listaAnios;
	}

	public void setListaAnios(List listaAnios) {
		this.listaAnios = listaAnios;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List getListaLibroMayor() {
		return listaLibroMayor;
	}

	public void setListaLibroMayor(List listaLibroMayor) {
		this.listaLibroMayor = listaLibroMayor;
	}

	public MayorizacionController(){
		log = Logger.getLogger(this.getClass());
		cargarValoresIniciales();
	}
	
	public void cargarValoresIniciales(){
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!=null){
				
			}else{
				log.error("--Usuario obtenido es NULL.");
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public String autenticar(){
		String outcome = null;
		libroMayorFiltro = new LibroMayor();
		libroMayorNuevo = new LibroMayor();
		outcome = "mayorizacion.formulario";
		return outcome;
	}
}