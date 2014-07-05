package pe.com.tumi.message;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

public class MessageController {
	
	protected   static Logger log = Logger.getLogger(MessageController.class);
	private 	String 		strTypeMessage;
	private 	String 		strMessage;
	private 	String 		strCloseIconPath;
	private 	String 		strInfoIconPath;
	private 	String 		strWarningIconPath;
	private 	String 		strErrorIconPath;
	private 	String 		strMessageIcon;
	private 	String 		strAcceptBtn;
	private 	String 		strCancelBtn;
	private 	Boolean		blnAccepted;
	private 	Boolean		blnShowMessage;
	private 	String 		strFunctionValidator;
	
	public MessageController(){
		clean(); //valores por defecto
		blnAccepted = true;
	}
	
	public void setInfoMessage(String message){
		setStrTypeMessage("Información");
		setStrMessage(message);
		setStrMessageIcon(strInfoIconPath);
		showMessage(true);
	}
	
	public void setWarningMessage(String message){
		setStrTypeMessage("Advertencia");
		setStrMessage(message);
		setStrMessageIcon(strWarningIconPath);
		showMessage(true);
	}
	
	public void setErrorMessage(String message){
		setStrTypeMessage("Error");
		setStrMessage(message);
		setStrMessageIcon(strErrorIconPath);
		showMessage(true);
	}
	
	public void acceptMessage(ActionEvent event){
		log.info("-------------------------------------Debugging MessageController.acceptMessage-------------------------------------");
		blnAccepted = true;
	}
	
	public void rejectMessage(ActionEvent event){
		log.info("-------------------------------------Debugging MessageController.rejectMessage-------------------------------------");
		blnAccepted = false; 
	}
	
	public void showMessage(Boolean b){
		log.info("-------------------------------------Debugging MessageController.showMessage-------------------------------------");
		blnShowMessage = b; 
	}
	
	public void clean(){
		log.info("-------------------------------------Debugging MessageController.clean-------------------------------------");
		strCloseIconPath = "/images/icons/remove_20.png";
		strInfoIconPath = "/images/icons/icon_info.png";
		strWarningIconPath = "/images/icons/icon_warning.png";
		strErrorIconPath = "/images/icons/icon_error.png";
		strAcceptBtn = "Aceptar";
		strCancelBtn = "Cancelar";
		this.blnShowMessage = false;
		strFunctionValidator = "validarMensaje(),validarMensajeBeneficiario()"; //funcion javascript por defecto para validar
	}

	//Getters & Setters
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	public String getStrAcceptBtn() {
		return strAcceptBtn;
	}
	public void setStrAcceptBtn(String strAcceptBtn) {
		this.strAcceptBtn = strAcceptBtn;
	}
	public String getStrCancelBtn() {
		return strCancelBtn;
	}
	public void setStrCancelBtn(String strCancelBtn) {
		this.strCancelBtn = strCancelBtn;
	}
	public String getStrTypeMessage() {
		return strTypeMessage;
	}
	public void setStrTypeMessage(String strTypeMessage) {
		this.strTypeMessage = strTypeMessage;
	}
	public String getStrMessageIcon() {
		return strMessageIcon;
	}
	public void setStrMessageIcon(String strMessageIcon) {
		this.strMessageIcon = strMessageIcon;
	}
	public String getStrCloseIconPath() {
		return strCloseIconPath;
	}
	public void setStrCloseIconPath(String strCloseIconPath) {
		this.strCloseIconPath = strCloseIconPath;
	}
	public String getStrInfoIconPath() {
		return strInfoIconPath;
	}
	public void setStrInfoIconPath(String strInfoIconPath) {
		this.strInfoIconPath = strInfoIconPath;
	}
	public String getStrWarningIconPath() {
		return strWarningIconPath;
	}
	public void setStrWarningIconPath(String strWarningIconPath) {
		this.strWarningIconPath = strWarningIconPath;
	}
	public String getStrErrorIconPath() {
		return strErrorIconPath;
	}
	public void setStrErrorIconPath(String strErrorIconPath) {
		this.strErrorIconPath = strErrorIconPath;
	}
	public Boolean getBlnAccepted() {
		return blnAccepted;
	}
	public void setBlnAccepted(Boolean blnAccepted) {
		this.blnAccepted = blnAccepted;
	}
	public Boolean getBlnShowMessage() {
		return blnShowMessage;
	}
	public void setBlnShowMessage(Boolean blnShowMessage) {
		this.blnShowMessage = blnShowMessage;
	}
	public String getStrFunctionValidator() {
		return strFunctionValidator;
	}
	public void setStrFunctionValidator(String strFunctionValidator) {
		this.strFunctionValidator = strFunctionValidator;
	}	
}
