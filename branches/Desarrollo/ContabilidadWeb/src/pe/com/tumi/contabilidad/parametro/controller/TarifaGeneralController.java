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
import pe.com.tumi.parametro.general.domain.Tarifa;
import pe.com.tumi.parametro.general.domain.TarifaId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;

public class TarifaGeneralController {
	protected static Logger log = Logger.getLogger(TipoCambioController.class);
	private static final Integer IDMENU_TIPOCAMBIO = 149;
	private Boolean blnPermisoMenu;
	private List<Tarifa> listTarifa = null;
	private Tarifa tarifaBusq = null;
	private Tarifa beanTarifa = null;
	private Boolean blnShowDivFormTarifa = null;
	private Tarifa tarifaSelected = null;
	private Boolean isValidTarifa = null;
	private Boolean blnUpdating = null;
	
	//atributos de Sesión
	private Integer intEmpresaSesion;
	private Integer intUsuarioSesion;
	
	public TarifaGeneralController(){
		tarifaBusq = new Tarifa();
		tarifaBusq.setId(new TarifaId());
		cleanBeanTarifa();
		isValidTarifa = true;
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
	
	public void buscarTarifa(ActionEvent event) throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging TarifaGeneralController.buscarTarifa-------------------------------------");
		busquedaTarifa();
	}
	
	public void busquedaTarifa() throws EJBFactoryException, BusinessException{
		List<Tarifa> lista = null;
		
		if(getTarifaBusq().getId().getIntParaTipoTarifaCod()==0)getTarifaBusq().getId().setIntParaTipoTarifaCod(null);
		
		GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		lista = generalFacade.getListaTarifaBusqueda(getTarifaBusq());
		System.out.println("listTarifa.size: "+lista.size());
		setListTarifa(lista);
	}
	
	public void obtenerTarifa(ActionEvent event){
		log.info("-------------------------------------Debugging TipoCambioController.obtenerTarifa-------------------------------------");
		if(getTarifaSelected()!=null){
			setBlnShowDivFormTarifa(true);
			setBeanTarifa(getTarifaSelected());
			setBlnUpdating(true);
		}
	}
	
	public void grabarTarifa(ActionEvent event) throws EJBFactoryException {
		log.info("-------------------------------------Debugging TipoCambioController.grabarTarifa-------------------------------------");
		Tarifa tipoCambio = null;
		
		if(!validarTarifa()){
			MessageController message = (MessageController)FacesContextUtil.getSessionBean("messageController");
			message.setWarningMessage("Datos no válidos ingresados. Verifque los mensajes de error y corrija.");
			return;
		}
		
		GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		if(getBeanTarifa()!=null && getBeanTarifa().getId()!=null){
						
			if(getBeanTarifa().getId().getIntPersEmpresaTarifa()==null && getBeanTarifa().getId().getIntParaTipoTarifaCod()!=null &&
					getBeanTarifa().getId().getDtParaTarifaDesde()!=null){
				//Grabar Tipo de Cambio
				getBeanTarifa().getId().setIntPersEmpresaTarifa(Constante.PARAM_EMPRESASESION);
				getBeanTarifa().setIntPersEmpresaUsuario(Constante.PARAM_EMPRESASESION);
				getBeanTarifa().setIntPersPersonaUsuario(Constante.PARAM_USUARIOSESION);
				try {
					tipoCambio = generalFacade.grabarTarifa(getBeanTarifa());
				} catch (BusinessException e) {
					FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
					log.error(e);
				}
			}else if (getBeanTarifa().getId().getIntPersEmpresaTarifa()!=null && getBeanTarifa().getId().getIntParaTipoTarifaCod()!=null &&
					getBeanTarifa().getId().getDtParaTarifaDesde()!=null){
				//Actualizar Tipo de Cambio
				try {
					tipoCambio = generalFacade.modificarTarifa(getBeanTarifa());
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
			busquedaTarifa();
		} catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			log.error(e);
		}
		setBlnShowDivFormTarifa(false);
	}
	
	public Boolean validarTarifa(){
		log.info("-------------------------------------Debugging TipoCambioController.validarTarifa-------------------------------------");
		isValidTarifa = true;
		
		if(getBeanTarifa()!=null){
			if(getBeanTarifa().getId()!=null && getBeanTarifa().getId().getIntParaTipoTarifaCod()!=null
					&& getBeanTarifa().getId().getIntParaTipoTarifaCod().equals(0)){
				FacesContextUtil.setMessageError("Seleccione un tipo de tarifa.");
				isValidTarifa = false;
			}
			
			if(getBeanTarifa().getId().getDtParaTarifaDesde()==null || getBeanTarifa().getDtTarifaHasta()==null ||
					getBeanTarifa().getId().getDtParaTarifaDesde().compareTo(getBeanTarifa().getDtTarifaHasta())>0){
				FacesContextUtil.setMessageError("Rango de fechas inválido.");
				isValidTarifa = false;
			}
			
			if(getBeanTarifa().getIntParaFormaAplicar()==null
					&& getBeanTarifa().getIntParaFormaAplicar().equals(0)){
				FacesContextUtil.setMessageError("Seleccione un tipo de Forma de Aplicar.");
				isValidTarifa = false;
			}
			
			if(getBeanTarifa().getBdValor()==null || getBeanTarifa().getBdValor().equals(0)){
				FacesContextUtil.setMessageError("El Valor no puede ser 0 ni quedar vacío");
				isValidTarifa = false;
			}
		}
		
		return isValidTarifa;
	}
	
	public void nuevaTarifa(ActionEvent event){
		setBlnShowDivFormTarifa(true);
		cleanBeanTarifa();
	}

	public void cancelarNuevo(ActionEvent event){
		setBlnShowDivFormTarifa(false);
	}
	
	public void cleanBeanTarifa(){
		setBeanTarifa(new Tarifa());
		getBeanTarifa().setId(new TarifaId());
		setBlnUpdating(false);
	}
	
	public void setSelectedTarifa(ActionEvent event){
		log.info("-------------------------------------Debugging TipoCambioController.setSelectedTarifa-------------------------------------");
		log.info("activeRowKey: "+FacesContextUtil.getRequestParameter("rowTarifa"));
		String selectedRow = FacesContextUtil.getRequestParameter("rowTarifa");
		Tarifa tarifa = null;
		for(int i=0; i<listTarifa.size(); i++){
			tarifa = listTarifa.get(i);
			if(i == Integer.parseInt(selectedRow)){;
				setTarifaSelected(tarifa);
				break;
			}
		}
	}
	
	//Getters && Setters
	public List<Tarifa> getListTarifa() {
		return listTarifa;
	}
	public void setListTarifa(List<Tarifa> listTarifa) {
		this.listTarifa = listTarifa;
	}
	public Tarifa getTarifaBusq() {
		return tarifaBusq;
	}
	public void setTarifaBusq(Tarifa tarifaBusq) {
		this.tarifaBusq = tarifaBusq;
	}
	public Tarifa getBeanTarifa() {
		return beanTarifa;
	}
	public void setBeanTarifa(Tarifa beanTarifa) {
		this.beanTarifa = beanTarifa;
	}
	public Boolean getBlnShowDivFormTarifa() {
		return blnShowDivFormTarifa;
	}
	public void setBlnShowDivFormTarifa(Boolean blnShowDivFormTarifa) {
		this.blnShowDivFormTarifa = blnShowDivFormTarifa;
	}
	public Tarifa getTarifaSelected() {
		return tarifaSelected;
	}
	public void setTarifaSelected(Tarifa tarifaSelected) {
		this.tarifaSelected = tarifaSelected;
	}
	public Boolean getIsValidTarifa() {
		return isValidTarifa;
	}
	public void setIsValidTarifa(Boolean isValidTarifa) {
		this.isValidTarifa = isValidTarifa;
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
