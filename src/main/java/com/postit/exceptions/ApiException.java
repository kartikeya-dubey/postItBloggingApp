package com.postit.exceptions;

public class ApiException extends RuntimeException {

	//Generate personalised runtime exception prompt eg. when wrong password is entered
	public ApiException(String message) {
		super(message);

	}

	public ApiException() {
		super();

	}

}
