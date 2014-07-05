package pe.com.tumi.common.validate;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

public class Validacion implements Validator {

	// Variables locales
	protected static Logger log = Logger.getLogger(Validacion.class);

	public void validate(FacesContext facesContext, UIComponent component,
			Object newValue) throws ValidatorException {
		log.info("----------------Debugging Validacion.validate(FacesContext facesContext,UIComponent component, Object newValue)----------------");

		String txtEmpresa = (String) newValue;
		log.info("txtEmpresa = " + txtEmpresa);
		log.info("UIComponent component = "
				+ component.getClientId(facesContext));
		log.info("FacesContext facesContext = " + facesContext.toString());

		if (txtEmpresa.equals("")) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage(
					"Debe ingresar la Razón Social.");
			facesContext.addMessage(component.getClientId(facesContext),
					message);
		}
	}
}
