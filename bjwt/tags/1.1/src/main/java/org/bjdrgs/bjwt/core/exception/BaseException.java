package org.bjdrgs.bjwt.core.exception;

public class BaseException extends RuntimeException {
	
	static final long serialVersionUID = -1L;

	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

}
