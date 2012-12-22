package org.bjdrgs.bjwt.core.exception;

public class ApplicationException extends BaseException {
	
	static final long serialVersionUID = -1L;

	public ApplicationException() {
		super();
	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

}
