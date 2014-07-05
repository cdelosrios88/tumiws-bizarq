package pe.com.tumi.common.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FacesContextUtil {
	//Mensajes
	public static final String MESSAGE_SUCCESS_ONSAVE = "El registro se guard� de manera exitosa.";
	public static final String MESSAGE_ERROR_ONSAVE = "Error no controlado al momento de guardar el registro. Comun�quese con el �rea de soporte t�cnico.";
	public static final String MESSAGE_ERROR_ONSEARCH = "Error no controlado al momento de obtener los registros de b�squeda. Comun�quese con el �rea de soporte t�cnico.";
	public static final String MESSAGE_WARNING_ONVALIDATE = "Datos no v�lidos ingresados. Verifque los mensajes de error y corrija.";
	
	//Metodos Utilitarios
	public static final String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	public static final HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public static final Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	
	public static final void setMessageError(String error) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
	}
	
	public static final void setMessageSuccess(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(msg));
	}
}
