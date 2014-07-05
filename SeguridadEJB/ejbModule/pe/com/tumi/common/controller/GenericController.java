package pe.com.tumi.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import pe.com.tumi.adminCuenta.domain.PersonaJuridica;
import pe.com.tumi.adminCuenta.service.AdminCuentaService;
import pe.com.tumi.common.service.GenericService;
import pe.com.tumi.common.util.BDHelper;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.common.util.DateHelper;
import pe.com.tumi.common.util.FileHelper;
import pe.com.tumi.common.util.ServiceException;
import pe.com.tumi.creditos.service.CreditosService;
import pe.com.tumi.popup.service.PopupService;
import pe.com.tumi.seguridad.domain.Usuario;


public class GenericController {

	protected static Logger log = Logger.getLogger(GenericController.class);
	private Map accion;
	private Object beanBusqueda;
	private Object bean;
	//private PersonaJuridica beanPerJuridica = new PersonaJuridica();
	private List beanList;
	private GenericService service;
	private CreditosService creditosService;
	private AdminCuentaService aperCuentaService;
	private PopupService popupService;
	protected Map map = new HashMap();
	private String pathToFile;
	private String fileName;
	protected static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	protected static SimpleDateFormat sdfshort = new SimpleDateFormat("dd/MM/yyyy");

	public void init(){
		try {
			setBeanList(getService().findAll());
		} catch (DaoException e) {
			setMessageError("No se pudo realizar la operacion.");
			log.error("No se pudo cargar la lista.");
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void beforeUpdate(ActionEvent event){
		Usuario usuario = (Usuario) getSpringBean(Constante.SESSION_USER);
		log.info("Start of Transaction: 'Update' at " + DateHelper.getFechaActual());
		log.info("User initiating transaction" + usuario.getCodigo());
		try {
			BeanUtils.setProperty(getBean(), Constante.USUARIO_MODIFICACION,usuario.getId());
			BeanUtils.setProperty(getBean(), "fechaModificacion" , new Date());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void afterUpdate(ActionEvent event) throws DaoException{
		log.info("End of Transaction: 'Update'");
		try {
			refreshBeanList();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void doUpdate(ActionEvent event)throws DaoException{
		try {
			log.debug("Bean antes de ser actualizado: " + getBean());
			getService().update(getBean());
			setBean(getBeanInstance());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
/*
	public void update(ActionEvent event){
		try {
			beforeUpdate(event);
			if (validate()) {
				doUpdate(event);
				afterUpdate(event);
				setMessageSuccess("Se realizo la operación exitosamente");
			}
		} catch (Exception ex) {
			setMessageError("No se pudo realizar la transacción");
			setCatchError(ex);
			log.error("No se pudo actualizar el registro");
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
*/

	public void beforeDelete(ActionEvent event){
		Usuario usuario = (Usuario) getSpringBean(Constante.SESSION_USER);
		log.info("Start of Transaction: 'Delete' at " + DateHelper.getFechaActual());
		log.info("User initiating transaction" + usuario.getCodigo());
	}

	public void doDelete(ActionEvent event)throws DaoException{
		try {
			log.debug("Bean antes de ser eliminado: " + getBean());
			getService().delete(Long.valueOf(getRequestParameter("sid")));
			setBean(getBeanInstance());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void afterDelete(ActionEvent event)throws DaoException{
		log.info("End of Transaction: 'Delete'");
		try {
			refreshBeanList();
		} catch (Exception e) {
			throw new DaoException(e);
		}	
	}

	public void delete(ActionEvent event){
		try {
			beforeDelete(event);
			doDelete(event);
			afterDelete(event);
			setMessageSuccess("Se realizo la operación exitosamente");
		} catch (Exception ex) {
			setMessageError("No se pudo realizar la transacción");
			setCatchError(ex);
			log.error("No se pudo eliminar el registro");
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void beforeSave(ActionEvent event){
		Usuario usuario = (Usuario) getSpringBean(Constante.SESSION_USER);
		log.info("Start of Transaction: 'Save' at " + DateHelper.getFechaActual());
		log.info("User initiating transaction " + usuario.getCodigo());
		try {
			if (BeanUtils.getProperty(getBean(), Constante.PERSISTENCE_FIELD_ID)== null){
				BeanUtils.setProperty(getBean(), Constante.USUARIO_CREACION, usuario.getId());
				BeanUtils.setProperty(getBean(), "fechaCreacion" , new Date());
			}else{
				BeanUtils.setProperty(getBean(), Constante.USUARIO_MODIFICACION,usuario.getId());
				BeanUtils.setProperty(getBean(), "fechaModificacion" , new Date());
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public void doSave(ActionEvent event)throws DaoException{
		log.debug("Guardando bean: " + getBean());
		try {
			getService().save(getBean());
			setBean(getBean());
			//setBean(getBeanInstance());
		}catch (Exception e) {
			throw new DaoException(e);
		}		
	}
/*
	public void save(ActionEvent event){
		try {
			beforeSave(event);
			if (validate()) {
				doSave(event);
				afterSave(event);
				setMessageSuccess("Se realizo la operación exitosamente");
			}
		} catch (Exception ex) {
			setMessageError("No se pudo realizar la transacción");
			setCatchError(ex);
			log.error("No se pudo guardar el registro");
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
*/
	public void afterSave(ActionEvent event)throws DaoException{
		log.info("End of Transaction: 'Save'");
		try {
			refreshBeanList();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void clean(ActionEvent event){
		setBean(getBeanInstance());
	}

	public void findById(ActionEvent event){
		try {
			Long id = Long.valueOf(getRequestParameter("sid"));
			bean = getService().findById(id);
		} catch (Exception e) {
			setMessageError("No se pudo realizar la transacción");
			setCatchError(e);
			log.error("No se pudo buscar el registro");
			log.error(e.getMessage());			
			e.printStackTrace();
		}
	}

	public void search(ActionEvent event){
		setBeanBusqueda(getBeanInstance());
	}

	public void refreshBeanList()throws DaoException{
		try {
			setBeanList(getService().findAll());
		} catch (DaoException e) {
			throw new DaoException(e);
		}
	}

	protected Object findById(Integer id){
		return doLoad(id);
	}

	protected Object getBeanInstance(){
		Class clazz;
		Object tmpObject;
		clazz = getBean().getClass();
		try {
			tmpObject = Class.forName(clazz.getName()).newInstance();
			return tmpObject;
		} catch (InstantiationException e) {
			try {
				throw new ServiceException(e);
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}
		} catch (IllegalAccessException e) {
			try {
				throw new ServiceException(e);
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			try {
				throw new ServiceException(e);
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	public void load(ActionEvent event){
		Object bean = doLoad(getRequestParameter("sid"));
		setBean(bean);
	}

	protected Object doLoad(Object propertyValue){
		try {
			return getService().findById(Long.valueOf((String) propertyValue));
		} catch (Exception e) {
			try {
				throw new ServiceException(e);
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/*public void downloadFile(ActionEvent event) {
		final HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	    log.info("getFileName(): "+getFileName());
	    response.setHeader("Content-Disposition", "attachment;filename=" + getFileName());
	    try {
	    	AdmFormDoc file = new AdmFormDoc(getPathToFile());
	        response.getOutputStream().write(FileHelper.getBytesFromFile(file));
	        response.setContentLength((int) file.length());
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
	    } catch (IOException e) {
	        log.error(e, e);
	    }
	    FacesContext.getCurrentInstance().responseComplete();
	}*/
	
	public void downloadFile(ActionEvent event) {
		try {
			if(getPathToFile() == null && getFileName() == null){
				setMessageError("Se debe definir la ruta y nombre del archivo para descarga.");
				return;
			}
			File file = new File(getPathToFile());
			log.info("getPathToFile(): "+getPathToFile());
			HttpServletResponse httpServletResponse = getResponse();
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			httpServletResponse.setContentLength((int) file.length());
			httpServletResponse.addHeader("Content-Disposition","attachment; filename=" + getFileName());
			servletOutputStream.write(FileHelper.getBytesFromFile(file));
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (IOException e) {
			log.error(e);
			setMessageError("Error al momento de la descarga.");
		}
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public Map getAccion() {
		if (accion == null)accion = new HashMap();
		return accion;
	}

	public void forward(String rule) {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getApplication().getNavigationHandler().handleNavigation(fc, null,
				rule);
	}

	protected HttpSession getSession(boolean create) {
		return getRequest().getSession(create);
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
	}

	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}

	protected void setMessageSuccess(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(msg));
	}

	protected void setMessageSuccess(String zone, String msg) {
		FacesContext.getCurrentInstance().addMessage(zone,
				new FacesMessage(msg));
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public List getBeanList(){
		return this.beanList;
	}

	public void setBeanList(List beanList) {
		this.beanList = beanList;
	}

	public Object getSpringBean(String name) {
		return getWebContext().getBean(name);
	}

	public Object getSpringBean(Class nameObj) {
		return getSpringBean(ClassUtils.getShortName(nameObj));
	}

	public Object getSpringBean(Object nameObj) {
		return getSpringBean(nameObj.getClass());
	}

	public WebApplicationContext getWebContext() {
		return WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
	}

	public ServletContext getServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();
	}

	protected void setMessageError(String clientId, String error) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, error, error));
	}

	protected void setMessageError(Exception e) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e
						.getLocalizedMessage()));
	}

	protected void setMessageError(String error) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
	}

	public void setCatchError(Exception ex){
		setMessageError(BDHelper.getSQLError(ex));
	}
	
	public boolean validate(){
		return true;
	}

	public Object getBeanBusqueda() {
		return beanBusqueda;
	}

	public void setBeanBusqueda(Object beanBusqueda) {
		this.beanBusqueda = beanBusqueda;
	}

	public void setAccion(Map accion) {
		this.accion = accion;
	}

	public GenericService getService() {
		return service;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

	public void changeTabSet(ActionEvent event) {
		event.getComponent();
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getPathToFile() {
		return pathToFile;
	}

	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public CreditosService getCreditosService() {
		return creditosService;
	}

	public void setCreditosService(CreditosService creditosService) {
		this.creditosService = creditosService;
	}
	    
	public UIComponent getUIComponent(String name) {  
		FacesContext facesCtx = FacesContext.getCurrentInstance();  
		return facesCtx.getViewRoot().findComponent(name);  
	}  
	
	public String getSelectOneLabel(UIComponent uicomponent) {
		log.info("------------------------------------------Debugging getSelectOneLabel------------------------------------------");
		log.info("uicomponent.getId(): "+uicomponent.getId());
		log.info("uicomponent.getChildCount(): "+uicomponent.getChildCount());
		List<SelectItem> itemlist=(List<SelectItem>)((UISelectItems)(uicomponent.getChildren().get(1))).getValue();
		log.info("itemlist.size(): "+itemlist.size());
		log.info("uicomponent.getAttributes().get(value): "+uicomponent.getAttributes().get("value"));
		Integer valCboTipoSocio = Integer.valueOf(""+uicomponent.getAttributes().get("value"));
		String lbl = "";
		
		for(int i=0; i<itemlist.size(); i++){
			log.info("itemlist.get("+i+").getValue(): "+itemlist.get(i).getValue());
			if(valCboTipoSocio.equals(itemlist.get(i).getValue())){
				log.info("itemlist.get("+i+").getLabel(): "+itemlist.get(i).getLabel());
				lbl = itemlist.get(i).getLabel();
				break;
			}
		}
		return lbl;
	}

	public AdminCuentaService getAperCuentaService() {
		return aperCuentaService;
	}

	public void setAperCuentaService(AdminCuentaService aperCuentaService) {
		this.aperCuentaService = aperCuentaService;
	}

	public PopupService getPopupService() {
		return popupService;
	}

	public void setPopupService(PopupService popupService) {
		this.popupService = popupService;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
	
}
