package com.postit.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

	String resourceName;
	String resourceFieldName;
	long resourceFieldValue;
	
	public ResourceNotFoundException(String resourceName, String resourceFieldName, long resourceFieldValue) {
		
		super(String.format("%s not found with %s : %S", resourceName, resourceFieldName, resourceFieldValue));
		
		this.resourceName = resourceName;
		this.resourceFieldName = resourceFieldName;
		this.resourceFieldValue = resourceFieldValue;
	}
}
