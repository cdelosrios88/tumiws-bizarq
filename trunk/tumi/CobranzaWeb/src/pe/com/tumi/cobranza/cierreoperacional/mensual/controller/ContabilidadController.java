package pe.com.tumi.cobranza.cierreoperacional.mensual.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.cierremensual.facade.CierreCobranzaFacadeLocal;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalle;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class ContabilidadController {

	protected static Logger log = Logger.getLogger(ContabilidadController.class);	
	
	
	//private CierreFacadeRemote cierreFacade;
	private PersonaFacadeRemote personaFacade;
	
	private List listaLibroMayor;
	private List<Tabla> listaAnios;
	
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
	private boolean habilitarCerrarOperaciones;
	private boolean habilitarAperturarOperaciones;
	
	CierreCobranzaFacadeLocal cierreFacade;
	
	public ContabilidadController(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	public void cargarValoresIniciales(){
		try{
			listaLibroMayor = new ArrayList<LibroMayor>();
			libroMayorFiltro = new LibroMayor();
			libroMayorNuevo = new LibroMayor();
			libroMayorFiltro.setIntParaEstadoCierreCod(Constante.PARAM_T_TIPOESTADOCIERRE_TODOS);
			//cierreFacade = (CierreFacadeRemote) EJBFactory.getRemote(CierreFacadeRemote.class);
			cierreFacade = (CierreCobranzaFacadeLocal)EJBFactory.getLocal(CierreCobranzaFacadeLocal.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			cargarListaAnios();
			
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
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			libroMayorNuevo = new LibroMayor();
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;			
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			habilitarCerrarOperaciones = Boolean.TRUE;
			habilitarAperturarOperaciones = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
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
			cargarNombreUsuario();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void modificarRegistro(){
		try {
			cargarRegistro();	
			//habilitarGrabar = Boolean.TRUE;
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
			habilitarCerrarOperaciones = Boolean.TRUE;
			habilitarAperturarOperaciones = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
	}
	
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (LibroMayor)event.getComponent().getAttributes().get("item");
			log.info("reg selec:"+registroSeleccionado);
			mostrarBtnEliminar = Boolean.TRUE;
			habilitarGrabar = Boolean.TRUE;			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
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
	
	public void grabar(){
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try{
			
			if(registrarNuevo){
				libroMayorNuevo.getId().setIntPersEmpresaMayor(usuario.getPerfil().getId().getIntPersEmpresaPk());
				libroMayorNuevo.setIntPersEmpresaUsuario(usuario.getPerfil().getId().getIntPersEmpresaPk());
				libroMayorNuevo.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
				libroMayorNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
				cierreFacade.grabarLibroMayor(libroMayorNuevo);				
				mensaje = "Se registró correctamente el libro mayor.";
			}else{
				cierreFacade.modificarLibroMayor(libroMayorNuevo);
				mensaje = "Se modificó correctamente el libro mayor.";				
			}
			buscar();
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		}catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de modificación.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
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
	
	public void seleccionarCerrarOperaciones(){
		String mensaje = "";	
		try{
			habilitarCerrarOperaciones = Boolean.TRUE;
			habilitarAperturarOperaciones = Boolean.FALSE;
			libroMayorNuevo.setIntParaEstadoCierreCod(Constante.PARAM_T_TIPOESTADOCIERRE_CERRADO);
			if(registrarNuevo){
				if(existeLibroMayor()){
					habilitarGrabar = Boolean.FALSE;
					mostrarMensaje(Boolean.FALSE, "Ya existe un Libro Mayor para el Mes-Año seleccionado.");
					habilitarAperturarOperaciones = Boolean.TRUE;
				}else{
					habilitarGrabar = Boolean.TRUE;
				}
			}
		}catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de modificación.";
			mostrarMensaje(Boolean.FALSE, mensaje);
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarAperturarOperaciones(){
		String mensaje = "";	
		try{
			habilitarCerrarOperaciones = Boolean.FALSE;
			habilitarAperturarOperaciones = Boolean.TRUE;
			libroMayorNuevo.setIntParaEstadoCierreCod(Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO);
			if(registrarNuevo){
				if(existeLibroMayor()){
					habilitarGrabar = Boolean.FALSE;
					mostrarMensaje(Boolean.FALSE, "Ya existe un Libro Mayor para el Mes-Año seleccionado.");
					habilitarCerrarOperaciones = Boolean.TRUE;
				}else{				
					habilitarGrabar = Boolean.TRUE;				
				}
			}else{
				List<LibroMayorDetalle> listaLibroMayorDetalle = cierreFacade.getListaLibroMayorDetallePorLibroMayor(registroSeleccionado);
				if(listaLibroMayorDetalle!=null && !listaLibroMayorDetalle.isEmpty()){
					habilitarGrabar = Boolean.FALSE;
					mensaje = "El periodo "+registroSeleccionado.getId().getIntContMesMayor()+"-"+registroSeleccionado.getId().getIntContPeriodoMayor()+" se encuentra mayorizado.";					
					mostrarMensaje(Boolean.FALSE, mensaje);
					habilitarAperturarOperaciones = Boolean.TRUE;					
				}else{
					habilitarGrabar = Boolean.TRUE;
				}
			}
		}catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de modificación.";
			mostrarMensaje(Boolean.FALSE, mensaje);
			log.error(e.getMessage(),e);
		}
	}
	
	
	private boolean existeLibroMayor(){
		try{
			List<LibroMayor> listaTodosLibroMayor = cierreFacade.getLibroMayorTodos();
			boolean existeLibroMayor = Boolean.FALSE;
			for(LibroMayor libroMayor : listaTodosLibroMayor){
				if(libroMayor.getId().getIntContMesMayor().equals(libroMayorNuevo.getId().getIntContMesMayor()) && 
					libroMayor.getId().getIntContPeriodoMayor().equals(libroMayorNuevo.getId().getIntContPeriodoMayor())){
					existeLibroMayor = Boolean.TRUE;
					break;
				}
			}
			return existeLibroMayor;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Boolean.FALSE;
		}
	}
	

	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
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
	public List getListaLibroMayor() {
		return listaLibroMayor;
	}
	public void setListaLibroMayor(List listaLibroMayor) {
		this.listaLibroMayor = listaLibroMayor;
	}
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}
	public List<Tabla> getListaAnios() {
		return listaAnios;
	}
	public void setListaAnios(List<Tabla> listaAnios) {
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
	public boolean isHabilitarCerrarOperaciones() {
		return habilitarCerrarOperaciones;
	}
	public void setHabilitarCerrarOperaciones(boolean habilitarCerrarOperaciones) {
		this.habilitarCerrarOperaciones = habilitarCerrarOperaciones;
	}
	public boolean isHabilitarAperturarOperaciones() {
		return habilitarAperturarOperaciones;
	}
	public void setHabilitarAperturarOperaciones(boolean habilitarAperturarOperaciones) {
		this.habilitarAperturarOperaciones = habilitarAperturarOperaciones;
	}	
}