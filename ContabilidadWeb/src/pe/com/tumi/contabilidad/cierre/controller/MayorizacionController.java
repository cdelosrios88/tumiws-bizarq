package pe.com.tumi.contabilidad.cierre.controller;

import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.CommonUtils;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.facade.CierreFacadeLocal;
import pe.com.tumi.contabilidad.core.facade.MayorizacionFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.Password;
import pe.com.tumi.seguridad.permiso.domain.PasswordId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;

public class MayorizacionController {

	protected static Logger log;
	private Usuario usuario;
	private String strPassword;
	private String strMsgError;
	private List<SelectItem> listYears;
	private Integer intAnio;
	private Integer intMes;
	private List<LibroMayor> listaLibroMayor;
	private LibroMayor libroMayorFiltro;
	private LibroMayor libroMayorNuevo;
	private List<SelectItem> listaAnios;
	private MayorizacionFacadeLocal mayorizacionFacade;
	private CierreFacadeLocal cierreFacade;
	private String strErrorValidateMsg;
	
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

	public List getListaAnios() {
		return listaAnios;
	}

	public void setListaAnios(List listaAnios) {
		this.listaAnios = listaAnios;
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

	public boolean isSeleccionaRegistro() {
		return seleccionaRegistro;
	}

	public void setSeleccionaRegistro(boolean seleccionaRegistro) {
		this.seleccionaRegistro = seleccionaRegistro;
	}

	public PermisoFacadeRemote getPermisoFacade() {
		return permisoFacade;
	}

	public void setPermisoFacade(PermisoFacadeRemote permisoFacade) {
		this.permisoFacade = permisoFacade;
	}

	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean seleccionaRegistro;
	
	private PermisoFacadeRemote permisoFacade;
	
	public MayorizacionController(){
		log = Logger.getLogger(this.getClass());
		try{
			mayorizacionFacade = (MayorizacionFacadeLocal) EJBFactory.getLocal(MayorizacionFacadeLocal.class);
			cierreFacade = (CierreFacadeLocal) EJBFactory.getLocal(CierreFacadeLocal.class);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		cargarValoresIniciales();
	}
	
	public void cargarValoresIniciales(){
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!=null){
				permisoFacade = (PermisoFacadeRemote) EJBFactory.getRemote(PermisoFacadeRemote.class);
				libroMayorFiltro = new LibroMayor();
				libroMayorNuevo = new LibroMayor();
				listaLibroMayor = null;
				strMsgError = null;
				mostrarPanelInferior = Boolean.FALSE;
				cargarListaAnios();
			}else{
				log.error("--Usuario obtenido es NULL.");
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaAnios(){
		listYears = CommonUtils.getListAnios();
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public String autenticar(){
		String outcome = null;
		Password password = null;
		strMsgError = null;
		try {
			password = new Password();
			password.setId(new PasswordId());
			password.getId().setIntEmpresaPk(usuario.getEmpresa().getIntIdEmpresa());
			password.getId().setIntIdTransaccion(Constante.INT_IDTRANSACCION_MAYORIZACION);
			password.setStrContrasena(strPassword);
			password = permisoFacade.getPasswordPorPkYPass(password);
			log.info("password: " + password);
			if(password!=null){
				cargarValoresIniciales();
				outcome = "mayorizacion.formulario";
			}else {
				strMsgError = "Clave incorrecta. Favor verificar";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return outcome;
	}
	
	public void procesarMayorizado(){
		boolean isValidProcess;
		Integer intReturnResp;
		try {
			isValidProcess = isValidateMayorizadoProcess();
			if(!isValidProcess){
				intReturnResp = mayorizacionFacade.processMayorizacion(libroMayorNuevo);
				if(intReturnResp!=null && intReturnResp.equals(Constante.ON_SUCCESS)){
					FacesContext.getCurrentInstance().addMessage("", new FacesMessage("éxito!"));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private boolean isValidateMayorizadoProcess(){
		boolean isReturn=false;
		LibroMayor libroMayor = null;
		Calendar calDate = null;
		strErrorValidateMsg = null;
		try {
			libroMayorNuevo.getId().setIntPersEmpresaMayor(usuario.getEmpresa().getIntIdEmpresa());
			libroMayor = cierreFacade.getLibroMayorPorPk(libroMayorNuevo.getId());
			if(libroMayor!=null){
				strErrorValidateMsg = "El proceso a ejecutar existe con el mismo periodo, favor verificar!";
				return true;
			}
			//Si el mes anterior ya posee un proceso mayorizado.
			calDate = pe.com.tumi.common.util.CommonUtils.getPreviousMonth(
					libroMayorNuevo.getId().getIntContMesMayor(), 
					libroMayorNuevo.getId().getIntContPeriodoMayor());
			log.info("mes: " + calDate.get(Calendar.getInstance().MONTH)+1);
			log.info("año: " + calDate.get(Calendar.getInstance().YEAR));
			libroMayor = new LibroMayor();
			libroMayor.getId().setIntPersEmpresaMayor(usuario.getEmpresa().getIntIdEmpresa());
			libroMayor.getId().setIntContMesMayor(calDate.get(Calendar.getInstance().MONTH)+1);
			libroMayor.getId().setIntContPeriodoMayor(calDate.get(Calendar.getInstance().YEAR));
			libroMayor = cierreFacade.getLibroMayorPorPk(libroMayor.getId());
			if(libroMayor!=null){
				strErrorValidateMsg = "El Periodo ingresado no puede ser procesado debido a que no se ha procesado el mes anterior";
				return true;
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isReturn;
	}
	
	public void buscarMayorizado(){
		try {
			libroMayorFiltro.getId().setIntPersEmpresaMayor(usuario.getEmpresa().getIntIdEmpresa());
			listaLibroMayor = mayorizacionFacade.buscarLibroMayoreHistorico(libroMayorFiltro);
			System.out.println("entro a este metodo :::");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}

	public void habilitarPanelInferior(ActionEvent event){
		mostrarPanelInferior = Boolean.TRUE;
		libroMayorNuevo = new LibroMayor();
		
	}
	public void deshabilitarPanelInferior(ActionEvent event){
		mostrarPanelInferior = Boolean.FALSE;
	}
	
	public String getStrPassword() {
		return strPassword;
	}

	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}

	public String getStrMsgError() {
		return strMsgError;
	}

	public void setStrMsgError(String strMsgError) {
		this.strMsgError = strMsgError;
	}

	public List<SelectItem> getListYears() {
		return listYears;
	}

	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}

	public Integer getIntAnio() {
		return intAnio;
	}

	public void setIntAnio(Integer intAnio) {
		this.intAnio = intAnio;
	}

	public Integer getIntMes() {
		return intMes;
	}

	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}

	public String getStrErrorValidateMsg() {
		return strErrorValidateMsg;
	}

	public void setStrErrorValidateMsg(String strErrorValidateMsg) {
		this.strErrorValidateMsg = strErrorValidateMsg;
	}
}