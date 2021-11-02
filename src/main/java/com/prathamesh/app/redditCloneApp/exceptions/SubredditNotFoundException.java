package com.prathamesh.app.redditCloneApp.exceptions;

public class SubredditNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SubredditNotFoundException(String message) {
		super(message);
	}

}
