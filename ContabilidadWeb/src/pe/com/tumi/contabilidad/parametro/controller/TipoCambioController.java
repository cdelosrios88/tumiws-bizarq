package pe.com.tumi.contabilidad.parametro.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.message.controller.MessageController;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.domain.TipoCambioId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;

public class TipoCambioController {
	protected static Logger log = Logger.getLogger(TipoCambioController.class);
	private static final Integer IDMENU_TIPOCAMBIO = 148;
	private Boolean blnPermisoMenu;
	private List<TipoCambio> listTipoCambio = null;
	private TipoCambio tipoCambioBusq = null;
	private TipoCambio beanTipoCambio = null;
	private Boolean blnShowDivFormTipoCambio = null;
	private TipoCambio tipoCambioSelected = null;
	private Boolean isValidTipoCambio = null;
	private Boolean blnUpdating = null;
	
	//atributos de Sesión
	private Integer intEmpresaSesion;
	private Integer intUsuarioSesion;
	
	public TipoCambioController(){
		tipoCambioBusq = new TipoCambio();
		tipoCambioBusq.setId(new TipoCambioId());
		cleanBeanTipoCambio();
		blnUpdating = false;
		
		Usuario usuarioSesion = (Usuario)FacesContextUtil.getRequest().getSession().getAttribute("usuario");
		intUsuarioSesion = usuarioSesion.getIntPersPersonaPk();
		intEmpresaSesion = usuarioSesion.getEmpresa().getIntIdEmpresa();
		getPermisoPerfil(usuarioSesion);
	}
	
	public void getPermisoPerfil(Usuario usuarioSesion){
		PermisoPerfil permiso = null;
		try{
			PermisoPerfilId id = new PermisoPerfilId();
			id.setIntPersEmpresaPk(usuarioSesion.getPerfil().getId().getIntPersEmpresaPk());
			id.setIntIdTransaccion(IDMENU_TIPOCAMBIO);
			id.setIntIdPerfil(usuarioSesion.getPerfil().getId().getIntIdPerfil());
			PermisoFacadeRemote localPermiso = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnPermisoMenu = (permiso == null)?true:false;
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}
	}
	
	public void buscarTipoCambio(ActionEvent event) throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging TipoCambioController.buscarTipoCambio-------------------------------------");
		busquedaTipoCambio();
	}
	
	public void busquedaTipoCambio() throws EJBFactoryException, BusinessException{
		List<TipoCambio> lista = null;
		
		if(getTipoCambioBusq().getId().getIntParaClaseTipoCambio()==0)getTipoCambioBusq().getId().setIntParaClaseTipoCambio(null);
		if(getTipoCambioBusq().getId().getIntParaMoneda()==0)getTipoCambioBusq().getId().setIntParaMoneda(null);
		
		GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		lista = generalFacade.getListaTipoCambioBusqueda(getTipoCambioBusq());
		System.out.println("listTipoCambio.size: "+lista.size());
		setListTipoCambio(lista);
	}
	
	public void obtenerTipoCambio(ActionEvent event){
		log.info("-------------------------------------Debugging TipoCambioController.obtenerTipoCambio-------------------------------------");
		if(getTipoCambioSelected()!=null){
			setBlnShowDivFormTipoCambio(true);
			setBeanTipoCambio(getTipoCambioSelected());
			setBlnUpdating(true);
		}
	}
	
	public void grabarTipoCambio(ActionEvent event) throws EJBFactoryException {
		log.info("-------------------------------------Debugging TipoCambioController.grabarTipoCambio-------------------------------------");
		TipoCambio tipoCambio = null;
		
		if(!validarTipoCambio()){
			MessageController message = (MessageController)FacesContextUtil.getSessionBean("messageController");
			message.setWarningMessage("Datos no válidos ingresados. Verifque los mensajes de error y corrija.");
			return;
		}
		
		GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		if(getBeanTipoCambio()!=null && getBeanTipoCambio().getId()!=null){
						
			if(getBeanTipoCambio().getId().getIntPersEmpresa()==null && getBeanTipoCambio().getId().getIntParaClaseTipoCambio()!=null &&
					getBeanTipoCambio().getId().getDtParaFecha()!=null && getBeanTipoCambio().getId().getIntParaMoneda()!=null){
				//Grabar Tipo de Cambio
				getBeanTipoCambio().getId().setIntPersEmpresa(Constante.PARAM_EMPRESASESION);
				getBeanTipoCambio().setIntEmpresaUsuario(Constante.PARAM_EMPRESASESION);
				getBeanTipoCambio().setIntPersonaUsuario(Constante.PARAM_USUARIOSESION);
				try {
					tipoCambio = generalFacade.grabarTipoCambio(getBeanTipoCambio());
				} catch (BusinessException e) {
					FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
					log.error(e);
				}
			}else if (getBeanTipoCambio().getId().getIntPersEmpresa()!=null && getBeanTipoCambio().getId().getIntParaClaseTipoCambio()!=null &&
				getBeanTipoCambio().getId().getDtParaFecha()!=null && getBeanTipoCambio().getId().getIntParaMoneda()!=null){
				//Actualizar Tipo de Cambio
				try {
					tipoCambio = generalFacade.modificarTipoCambio(getBeanTipoCambio());
				} catch (BusinessException e) {
					FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
					log.error(e);
				}
			}
		}
		
		if(tipoCambio!=null){
			FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
		}
		
		//Refrescar la busqueda
		try {
			busquedaTipoCambio();
		} catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			log.error(e);
		} 
		setBlnShowDivFormTipoCambio(false);
	}
	
	public Boolean validarTipoCambio(){
		log.info("-------------------------------------Debugging TipoCambioController.validarTipoCambio-------------------------------------");
		isValidTipoCambio = true;
		
		if(getBeanTipoCambio()!=null){
			if(getBeanTipoCambio().getId()!=null && getBeanTipoCambio().getId().getIntParaMoneda()!=null
					&& getBeanTipoCambio().getId().getIntParaMoneda().equals(0)){
				FacesContextUtil.setMessageError("Seleccione un tipo de moneda.");
				isValidTipoCambio = false;
			}
			
			if(getBeanTipoCambio().getId()!=null && getBeanTipoCambio().getId().getIntParaClaseTipoCambio()!=null
					&& getBeanTipoCambio().getId().getIntParaClaseTipoCambio().equals(0)){
				FacesContextUtil.setMessageError("Seleccione un tipo de cambio.");
				isValidTipoCambio = false;
			}
			
			if(getBeanTipoCambio().getBdCompra()==null || getBeanTipoCambio().getBdCompra().equals(0)){
				FacesContextUtil.setMessageError("El valor de Compra no puede ser 0 o quedar vacío.");
				isValidTipoCambio = false;
			}
			
			if(getBeanTipoCambio().getBdVenta()==null || getBeanTipoCambio().getBdVenta().equals(0)){
				FacesContextUtil.setMessageError("El valor de Venta no puede ser 0 o quedar vacío");
				isValidTipoCambio = false;
			}
			
			if(getBeanTipoCambio().getBdPromedio()==null || getBeanTipoCambio().getBdPromedio().equals(0)){
				FacesContextUtil.setMessageError("El valor Promedio no puede ser 0 o quedar vacío.");
				isValidTipoCambio = false;
			}
		}
		
		return isValidTipoCambio;
	}
	
	public void nuevoTipoCambio(ActionEvent event){
		setBlnShowDivFormTipoCambio(true);
		cleanBeanTipoCambio();
	}

	public void cancelarNuevo(ActionEvent event){
		setBlnShowDivFormTipoCambio(false);
	}
	
	public void cleanBeanTipoCambio(){
		setBeanTipoCambio(new TipoCambio());
		getBeanTipoCambio().setId(new TipoCambioId());
		setBlnUpdating(false);
	}
	
	public void setSelectedTipoCambio(ActionEvent event){
		log.info("-------------------------------------Debugging TipoCambioController.setSelectedTipoCambio-------------------------------------");
		log.info("activeRowKey: "+FacesContextUtil.getRequestParameter("rowTipoCambio"));
		String selectedRow = FacesContextUtil.getRequestParameter("rowTipoCambio");
		TipoCambio tipoCambio = null;
		for(int i=0; i<listTipoCambio.size(); i++){
			tipoCambio = listTipoCambio.get(i);
			if(i == Integer.parseInt(selectedRow)){;
				setTipoCambioSelected(tipoCambio);
				break;
			}
		}
	}
	
	//Getters && Setters
	public List<TipoCambio> getListTipoCambio() {
		return listTipoCambio;
	}
	public void setListTipoCambio(List<TipoCambio> listTipoCambio) {
		this.listTipoCambio = listTipoCambio;
	}
	public TipoCambio getTipoCambioBusq() {
		return tipoCambioBusq;
	}
	public void setTipoCambioBusq(TipoCambio tipoCambioBusq) {
		this.tipoCambioBusq = tipoCambioBusq;
	}
	public TipoCambio getBeanTipoCambio() {
		return beanTipoCambio;
	}
	public void setBeanTipoCambio(TipoCambio beanTipoCambio) {
		this.beanTipoCambio = beanTipoCambio;
	}
	public Boolean getBlnShowDivFormTipoCambio() {
		return blnShowDivFormTipoCambio;
	}
	public void setBlnShowDivFormTipoCambio(Boolean blnShowDivFormTipoCambio) {
		this.blnShowDivFormTipoCambio = blnShowDivFormTipoCambio;
	}
	public TipoCambio getTipoCambioSelected() {
		return tipoCambioSelected;
	}
	public void setTipoCambioSelected(TipoCambio tipoCambioSelected) {
		this.tipoCambioSelected = tipoCambioSelected;
	}
	public Boolean getIsValidTipoCambio() {
		return isValidTipoCambio;
	}
	public void setIsValidTipoCambio(Boolean isValidTipoCambio) {
		this.isValidTipoCambio = isValidTipoCambio;
	}
	public Boolean getBlnUpdating() {
		return blnUpdating;
	}
	public void setBlnUpdating(Boolean blnUpdating) {
		this.blnUpdating = blnUpdating;
	}
	public Integer getIntEmpresaSesion() {
		return intEmpresaSesion;
	}
	public void setIntEmpresaSesion(Integer intEmpresaSesion) {
		this.intEmpresaSesion = intEmpresaSesion;
	}
	public Integer getIntUsuarioSesion() {
		return intUsuarioSesion;
	}
	public void setIntUsuarioSesion(Integer intUsuarioSesion) {
		this.intUsuarioSesion = intUsuarioSesion;
	}
	public Boolean getBlnPermisoMenu() {
		return blnPermisoMenu;
	}
	public void setBlnPermisoMenu(Boolean blnPermisoMenu) {
		this.blnPermisoMenu = blnPermisoMenu;
	}
}
