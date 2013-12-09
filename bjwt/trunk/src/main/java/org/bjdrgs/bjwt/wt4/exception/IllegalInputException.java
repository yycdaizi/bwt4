package org.bjdrgs.bjwt.wt4.exception;

import org.bjdrgs.bjwt.core.exception.BaseException;

public class IllegalInputException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalInputException() {
		super();
	}

	public IllegalInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalInputException(String message) {
		super(message);
	}

	public IllegalInputException(Throwable cause) {
		super(cause);
	}

}
