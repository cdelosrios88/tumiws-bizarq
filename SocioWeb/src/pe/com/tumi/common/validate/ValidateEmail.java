package pe.com.tumi.common.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class ValidateEmail implements Validator{
	
	public ValidateEmail(){}
	
	public void validate(FacesContext facesContext, UIComponent uIComponent,
			Object object) throws ValidatorException {
		String enteredEmail = (String) object;
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(enteredEmail);
		boolean matchFound = m.matches();

		if (!matchFound) {
			FacesMessage message = new FacesMessage();
			message.setSummary("Invalid Email ID.");
			throw new ValidatorException(message);
		}
	}
}
