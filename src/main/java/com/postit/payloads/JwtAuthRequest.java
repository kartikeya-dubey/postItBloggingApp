package com.postit.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
	
	//user email is username for authentication
	private String username;
	
	private String password;

}
