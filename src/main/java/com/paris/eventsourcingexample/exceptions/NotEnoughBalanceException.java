package com.paris.eventsourcingexample.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughBalanceException extends Exception {

	private static final long serialVersionUID = -6551431803754141835L;

	public NotEnoughBalanceException() {
	}

	public NotEnoughBalanceException(String message) {
		super(message);
	}

	public NotEnoughBalanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughBalanceException(Throwable cause) {
		super(cause);
	}

	public NotEnoughBalanceException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
