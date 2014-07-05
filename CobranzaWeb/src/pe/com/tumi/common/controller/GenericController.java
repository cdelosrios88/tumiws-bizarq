package pe.com.tumi.common.controller;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.ClassUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class GenericController {

	
	//private GenericService service;
	
	public void init(){
//		try {
//			setBeanList(getService().findAll());
//		} catch (DaoException e) {
//			setMessageError("No se pudo realizar la operacion.");
//			log.error("No se pudo cargar la lista.");
//			log.error(e.getMessage());
//			e.printStackTrace();
//		}
	}
	public void forward(String rule) {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getApplication().getNavigationHandler().handleNavigation(fc, null,rule);
	}

	protected HttpSession getSession(boolean create) {
		return getRequest().getSession(create);
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
	
	protected void setMessageSuccess(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(msg));
	}

	protected void setMessageSuccess(String zone, String msg) {
		FacesContext.getCurrentInstance().addMessage(zone,new FacesMessage(msg));
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
		return WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	}

	public ServletContext getServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

	protected void setMessageError(String clientId, String error) {
		FacesContext.getCurrentInstance().addMessage(clientId,new FacesMessage(FacesMessage.SEVERITY_FATAL, error, error));
	}

	protected void setMessageError(Exception e) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getLocalizedMessage()));
	}

	protected void setMessageError(String error) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
	}
	
	public void changeTabSet(ActionEvent event) {
		event.getComponent();
	}

	public UIComponent getUIComponent(String name) {  
		FacesContext facesCtx = FacesContext.getCurrentInstance();  
		return facesCtx.getViewRoot().findComponent(name);  
	}  
	
}
