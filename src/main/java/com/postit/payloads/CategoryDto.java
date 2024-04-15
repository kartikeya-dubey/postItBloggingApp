package com.postit.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private int categoryId;
	
	@NotBlank
	@Size(min = 3, message = "Category should be atleast 3 chars long!")
	private String categoryTitle;
	@NotBlank
	private String categoryDescription;

}
