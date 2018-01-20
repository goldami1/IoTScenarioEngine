package DataBase.CustomizedExceptions;

public class SessionFactoryException extends Exception {

	public SessionFactoryException() {
		super();
	}

	public SessionFactoryException(String message) {
		super(message);
	}

	public SessionFactoryException(Throwable cause) {
		super(cause);
	}

	public SessionFactoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessionFactoryException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}