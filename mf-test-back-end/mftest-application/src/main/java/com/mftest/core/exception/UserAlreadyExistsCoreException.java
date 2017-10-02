package com.mftest.core.exception;

/**
 * Core class for user already exists exception.
 *
 */
public class UserAlreadyExistsCoreException extends CoreException {
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsCoreException(String msg) {
		super(msg);
	}
}
