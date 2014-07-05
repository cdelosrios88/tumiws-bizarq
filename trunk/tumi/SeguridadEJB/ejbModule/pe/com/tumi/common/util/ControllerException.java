package pe.com.tumi.common.util;

public class ControllerException extends Exception {
	public ControllerException(String message){
		super(message);
	}
	public ControllerException(String message , Exception e){
		super(message, e);
	}
	public ControllerException(Exception e){
		super(e);
	}
}
