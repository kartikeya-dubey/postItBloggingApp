package com.postit.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private int postId;
	
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	@NotNull
	private String imageName;
	@NotNull
	private Date addDate;
	@NotBlank
	private UserDto user;
	@NotBlank
	private CategoryDto category;
	
	private Set<CommentDto> comments=new HashSet<>();
	
}
