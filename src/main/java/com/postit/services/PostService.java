package com.postit.services;

import java.util.List;

import com.postit.payloads.PostDto;
import com.postit.payloads.PostResponse;

public interface PostService {

	//POST - Create post
	PostDto createPost(PostDto postDto, int userId, int categoryId);
	
	//PUT - Update Post
	PostDto updatePost(PostDto postDto, int postId);
	
	//DELETE - Delete Post
	void deletePost(int postId);
	
	//GET - Fetch Post
	PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy); //Pagination implemented here
	
	PostDto getPostByID(int postId);

	PostResponse getPostByUser(int userID, int pageNumber, int pageSize, String sortBy); //Pagination implemented here
	PostResponse getPostByCategory(int categoryId, int pageNumber, int pageSize, String sortBy); //Pagination implemented here
	
	//Search posts
	List<PostDto> searchPosts(String keyword);
	
}
