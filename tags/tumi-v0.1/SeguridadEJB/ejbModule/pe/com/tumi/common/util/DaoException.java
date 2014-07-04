package pe.com.tumi.common.util;

public class DaoException extends Exception {
	public DaoException() {
	}

	public DaoException(Exception e) {
		super(e);
	}
	
	public DaoException(String message) {
		super(message);
	}
}
