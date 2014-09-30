/**
* Resumen.
* Objeto: MayorizacionController
* Descripción:  Controller principal para los el manejo del formulario de mayorización
* Fecha de Creación: 17/09/2014.
* Requerimiento de Creación: REQ14-004
* Autor: Bizarq
*/
package pe.com.tumi.contabilidad.cierre.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.facade.MayorizacionFacadeLocal;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
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
	private PlanCuentaFacadeLocal planCuentaFacade;
	private CierreFacadeLocal cierreFacade;
	private String strErrorValidateMsg;
	private List<String> lstResultMsgValidation;
	private Integer intValidResultAccounts;
	
	public Integer INT_ID_EMPRESA;
	public Integer INT_ID_USER;
	
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
			planCuentaFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
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
				strErrorValidateMsg = null;
				lstResultMsgValidation = new ArrayList<String>();
				INT_ID_USER = usuario.getPersona().getIntIdPersona();
				INT_ID_EMPRESA = usuario.getEmpresa().getIntIdEmpresa();
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
		String strPeriodo = null;
		try {
			isValidProcess = isValidateMayorizadoProcess();
			if(!isValidProcess){
				strPeriodo = String.valueOf(CommonUtils.concatPeriodo(libroMayorNuevo.getId().getIntContPeriodoMayor(), libroMayorNuevo.getId().getIntContMesMayor()));
				libroMayorNuevo.setIntPersEmpresaUsuario(INT_ID_EMPRESA);
				libroMayorNuevo.setIntPersPersonaUsuario(INT_ID_USER);
				intReturnResp = mayorizacionFacade.processMayorizacion(libroMayorNuevo, strPeriodo);
				if(intReturnResp!=null && intReturnResp.equals(Constante.ON_SUCCESS)){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("éxito!"));
				}
				buscarMayorizado();
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
			if(libroMayor!=null && 
			(libroMayor.getIntEstadoCod().equals(Constante.PARAM_T_TIPOESTADOMAYORIZACION_REGISTRADO) || 
			 libroMayor.getIntEstadoCod().equals(Constante.PARAM_T_TIPOESTADOMAYORIZACION_PROCESADO) )){
				strErrorValidateMsg = "El proceso a ejecutar existe con el mismo periodo, favor verificar!";
				return true;
			}
			//Si el mes anterior ya posee un proceso mayorizado.
			SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
			calDate = CommonUtils.getPreviousMonth(
					libroMayorNuevo.getId().getIntContPeriodoMayor(),
					libroMayorNuevo.getId().getIntContMesMayor());
			log.info(format.format(calDate.getTime()));
			String strPeriod = format.format(calDate.getTime());
			Integer intYear = Integer.parseInt(strPeriod.substring(Constante.INT_ZERO,Constante.INT_FOUR));
			Integer intMonth = Integer.parseInt(strPeriod.substring(Constante.INT_FOUR));
			
			libroMayor = new LibroMayor();
			libroMayor.getId().setIntPersEmpresaMayor(usuario.getEmpresa().getIntIdEmpresa());
			libroMayor.getId().setIntContMesMayor(intMonth);
			libroMayor.getId().setIntContPeriodoMayor(intYear);
			libroMayor = cierreFacade.getLibroMayorPorPk(libroMayor.getId());
			if(libroMayor==null){
				strErrorValidateMsg = "El Periodo ingresado no puede ser procesado debido a que no se ha procesado el mes anterior";
				return true;
			}
			//Validar cuentas padre
			//isReturn = (validateBookAccounts(libroMayorNuevo.getId().getIntContPeriodoMayor()));
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isReturn;
	}
	
	/**
	 * @author Bizarq
	 * 
	 * */
	private boolean validateBookAccounts(Integer intAnioPeriodo){
		boolean isValid = false;
		List<PlanCuenta> lstPlanCuenta = null;
		String strNroCuenta = null;
		String strNroCuentaSup = null;
		int intSizeValidAccounts = 0;
		lstResultMsgValidation = new ArrayList<String>();
		try {
			lstPlanCuenta = planCuentaFacade.getListaPlanCuentaPorEmpresaCuentaYPeriodoCuenta(INT_ID_EMPRESA, intAnioPeriodo);
			if(lstPlanCuenta!=null && !lstPlanCuenta.isEmpty()){
				intSizeValidAccounts = lstPlanCuenta.size();
				for(PlanCuenta i : lstPlanCuenta){
					strNroCuenta = i.getId().getStrNumeroCuenta();
					if(strNroCuenta!=null && strNroCuenta.trim().length()>Constante.INT_TWO){
						int cont = 0;
						strNroCuentaSup = strNroCuenta.substring(0,(strNroCuenta.length()-2));
						for(PlanCuenta j : lstPlanCuenta){
							if(!strNroCuentaSup.equals(j.getId().getStrNumeroCuenta())){
								cont++;
							}else{
								break;
							}
						}
						if(intSizeValidAccounts==cont){
							lstResultMsgValidation.add("La cuenta "+ strNroCuenta + " no tiene una cuenta Padre.");
						}
					}
				}
			}
			if(lstResultMsgValidation.size()>Constante.INT_ZERO){
				intValidResultAccounts = lstResultMsgValidation.size();
				isValid = true;
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return isValid;
	}
	
	public void buscarMayorizado(){
		try {
			libroMayorFiltro.getId().setIntPersEmpresaMayor(INT_ID_EMPRESA);
			listaLibroMayor = mayorizacionFacade.buscarLibroMayoreHistorico(libroMayorFiltro);
			System.out.println("entro a este metodo :::");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void deleteMayorizado(ActionEvent event){
		LibroMayor itemLibroMayor = (LibroMayor)event.getComponent().getAttributes().get("item");
		String strIPAddress = null;
		try {
			if(!isValidDeleteMayorizacion(itemLibroMayor)){
				itemLibroMayor.setIntEstadoCod(Constante.PARAM_T_TIPOESTADOMAYORIZACION_ELIMINADO);
				strIPAddress = CommonUtils.getClientIpAddress(getRequest());
				log.info("strIp: " + strIPAddress);
				itemLibroMayor.setStrIpAddress(strIPAddress);
				itemLibroMayor.setIntPersPersonaUsuarioElimina(INT_ID_USER);
				itemLibroMayor.setTsFechaRegistroElimina(new Timestamp(new Date().getTime()));
				mayorizacionFacade.deleteMayorizado(itemLibroMayor);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private boolean isValidDeleteMayorizacion(LibroMayor o){
		boolean isReturn = false;
		List<LibroMayor> listLibroMayor = null;
		
		try {
			listLibroMayor = mayorizacionFacade.getListAfterProcessedMayorizado(o);
			if(listLibroMayor!=null && !listLibroMayor.isEmpty()){
				strErrorValidateMsg = "No puede eliminar un periodo que mantiene un periodo mayorizado posterior. Favor eliminar desde el último periodo mayorizado";
				isReturn = true;
			}else {
				strErrorValidateMsg = null;
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return isReturn;
	}

	public void habilitarPanelInferior(ActionEvent event){
		cleanScreen();
		mostrarPanelInferior = Boolean.TRUE;
	}
	
	public void deshabilitarPanelInferior(ActionEvent event){
		cleanScreen();
		mostrarPanelInferior = Boolean.FALSE;
	}
	
	public void cleanScreen(){
		strErrorValidateMsg = null;
		strMsgError = null;
		lstResultMsgValidation = new ArrayList<String>();
		libroMayorNuevo = new LibroMayor();
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

	public List<String> getLstResultMsgValidation() {
		return lstResultMsgValidation;
	}

	public void setLstResultMsgValidation(List<String> lstResultMsgValidation) {
		this.lstResultMsgValidation = lstResultMsgValidation;
	}

	public Integer getIntValidResultAccounts() {
		return intValidResultAccounts;
	}

	public void setIntValidResultAccounts(Integer intValidResultAccounts) {
		this.intValidResultAccounts = intValidResultAccounts;
	}
}