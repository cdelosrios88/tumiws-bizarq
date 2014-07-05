package pe.com.tumi.common.util;

import org.apache.log4j.Logger;

public class UtilException extends Exception{
	
	private static Logger log = Logger.getLogger(UtilException.class);
	
	public UtilException(String message , Exception e){
		super(message, e);
		log.debug(message);
		e.printStackTrace();
	}
	public UtilException(Exception e){
		super(e);
		log.debug(e.getMessage());
		e.printStackTrace();
	}
}
