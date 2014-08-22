/************************************************************************
/* Nombre de componente: FacesContextUtil
 * Descripción: Utilitario para manejar los mensajes de la aplicación hacia
 * 				el Usuario.
 * Cod. Req.: REQ14-003   
 * Autor : Christian De los Ríos Fecha:20/08/2014 20:00:00
 * Versión : v1.0 - Creacion de componente 
 * Fecha creación : 20/08/2014
/* ********************************************************************* */
package pe.com.tumi.common.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FacesContextUtil {
	//Mensajes
	public static final String MESSAGE_SUCCESS_ONSAVE = "El registro se guardó de manera exitosa.";
	public static final String MESSAGE_ERROR_ONSAVE = "Error no controlado al momento de guardar el registro. Comuníquese con el área de soporte técnico.";
	public static final String MESSAGE_ERROR_ONSEARCH = "Error no controlado al momento de obtener los registros de búsqueda. Comuníquese con el área de soporte técnico.";
	public static final String MESSAGE_ERROR_ONVALIDATION = "Error no controlado al momento de obtener los registros de validación." +
			"											 Comuníquese con el área de soporte técnico.";
	public static final String MESSAGE_SUCCESS_ONDISABLESESSION = "Se desabilitó la sesión del usuario seleccionado.";
	public static final String MESSAGE_SUCCESS_ONKILLBLOCKDB = "Se realizó el bloqueo de la opción seleccionada.";
	public static final String MESSAGE_SUCCESS_ONKILLSESSIONDB = "Se eliminó la sesión seleccionada.";
	
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
