package org.bjdrgs.bjwt.core.exception;

public class BusinessLogicException extends BaseException {
	
	static final long serialVersionUID = -1L;
	
	private String errorCode;
	private String message;
	private String systemException;

	public BusinessLogicException() {
		super();	
	}

	public BusinessLogicException(String message) {
		super(message);	
		this.message = message;
	}
	
	public BusinessLogicException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public BusinessLogicException(String errorCode, String message, String systemException) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
		this.systemException = systemException;
	}

	public BusinessLogicException(Throwable cause) {
		super(cause);
	}

	public BusinessLogicException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSystemException() {
		return systemException;
	}

	public void setSystemException(String systemException) {
		this.systemException = systemException;
	}
	
}