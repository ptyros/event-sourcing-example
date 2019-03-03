package com.paris.eventsourcingexample.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = -6551431803754141835L;

	public EntityNotFoundException() {
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityNotFoundException(Throwable cause) {
		super(cause);
	}

	public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression,
								   boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
