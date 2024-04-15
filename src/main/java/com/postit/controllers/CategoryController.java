package com.postit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postit.payloads.ApiResponse;
import com.postit.payloads.CategoryDto;
import com.postit.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//POST - create
	
	/*@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto)
	{
		CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
	}*/
	
	@PostMapping("/")
	
	//Optimized: can add multiple users at a time
	public ResponseEntity<List<CategoryDto>> createCategory(@Valid @RequestBody List<CategoryDto> newCategory)
	{
		for (CategoryDto categoryDto : newCategory) 
		{
			this.categoryService.createCategory(categoryDto);
		}
		return new ResponseEntity<List<CategoryDto>>(newCategory, HttpStatus.CREATED);
	}
	
	//PUT - update
	
	@PutMapping("/{categoryId}")
	
	//If API URI variable(userID) and crud method call variable(uid) are different then 
	//we add the URI placeholder next to @PathVariable to make right mapping of value
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("categoryId") int cid)
	{
		CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto, cid);
		return ResponseEntity.ok(updatedCategoryDto);
	}
	
	//DELETE - delete
	@DeleteMapping("/{categoryId}")
	
	//ResponseEntity<?> : Add ? if we dont know the return type of CRUD method in service class
	
	//ResponseEntity<ApiResponse> : if return type in service class delete is void but
	//we want to return something (custom message) from helper class in our project
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int categoryId)
	{
		this.categoryService.deleteCategory(categoryId);
		
		//return ResponseEntity.ok(Map.of("message", "Category Deleted Successfully!"));
		//if return type is ResponseEntity<?>
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully!", true),
				HttpStatus.OK); 
		//>> Create an API Response class in payload package with Value: String message and
		//boolean deletion success status.
	}
	
	//GET - fetch
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		
		//List<UserDto> allUsers = this.userService.getAllUsers();
		//return ResponseEntity.ok(allUsers);
		
		return new ResponseEntity<List<CategoryDto>>(this.categoryService.getAllCategories(), HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int categoryId) {
		
		//User user = this.userService.getUserById(userId);
		//return ResponseEntity.ok(user);
		
		return new ResponseEntity<CategoryDto>(this.categoryService.getCategory(categoryId), HttpStatus.OK);
	}

}
