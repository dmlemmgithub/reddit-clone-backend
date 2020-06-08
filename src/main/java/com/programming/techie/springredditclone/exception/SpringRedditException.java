package com.programming.techie.springredditclone.exception;

public class SpringRedditException extends RuntimeException {
	
	public SpringRedditException(String exMessage, Exception exception) {
	    super(exMessage, exception);
	}
	 
    public SpringRedditException(String message) {
        super(message);
    }
}