package com.postit.config;

public class AppConstants {

	public static final String PAGE_NUMBER="0";
	public static final String PAGE_SIZE="10";
	public static final String SORT_BY="postId";
	
	//JWT Token validity in milliseconds
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	//JWT Token credentials
	public static final String SECRETS = "jwtTokenKeyAddingMoreCharactersToMakeItLongerForExecution";
	
	//Role ID
	public static final Integer ADMIN_USER= 1;
	public static final Integer NORMAL_USER = 2;
	
}
