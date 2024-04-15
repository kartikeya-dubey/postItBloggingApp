package com.postit.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postit.entities.Category;
import com.postit.exceptions.ResourceNotFoundException;
import com.postit.payloads.CategoryDto;
import com.postit.repositories.CategoryRepo;
import com.postit.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		//modelMapper = new ModelMapper();
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category addedCategory = this.categoryRepo.save(category);
		
		return this.modelMapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
		
		//modelMapper = new ModelMapper();
		
		Optional<Category> optionalCategory=categoryRepo.findById(categoryId);				
		Category category=optionalCategory.orElseThrow(()->new ResourceNotFoundException("Category","Category ID", categoryId));
		
		//category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryDescription(categoryDto.getCategoryDescription()==null ? category.getCategoryDescription() : categoryDto.getCategoryDescription());
		//category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryTitle(categoryDto.getCategoryTitle()==null ? category.getCategoryTitle() : categoryDto.getCategoryTitle());
		
		Category updatCategory = this.categoryRepo.save(category);
		
		return this.modelMapper.map(updatCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(int categoryId) {
		Optional<Category> optionalCategory = categoryRepo.findById(categoryId);
		Category category=optionalCategory.orElseThrow(()->new ResourceNotFoundException("Category","Category ID", categoryId));
		this.categoryRepo.delete(category);
		
	}

	@Override
	public CategoryDto getCategory(int categoryId) {
		Optional<Category> optionalCategory = categoryRepo.findById(categoryId);
		Category category=optionalCategory.orElseThrow(()->new ResourceNotFoundException("Category","Category ID", categoryId));

		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		
		List<Category> allCategories=categoryRepo.findAll();
		List<CategoryDto> allCategoryDtos=allCategories.stream().map(
				cat -> modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return allCategoryDtos;
	}
}
