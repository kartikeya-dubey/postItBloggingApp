package com.postit.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.postit.entities.Category;
import com.postit.entities.Post;
import com.postit.entities.User;
import com.postit.exceptions.ResourceNotFoundException;
import com.postit.payloads.PostDto;
import com.postit.payloads.PostResponse;
import com.postit.repositories.CategoryRepo;
import com.postit.repositories.PostRepo;
import com.postit.repositories.UserRepo;
import com.postit.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, int userId, int categoryID) {
		
		User user=this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
		Category category=this.categoryRepo.findById(categoryID)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryID));
		
		Post createdPost=this.modelMapper.map(postDto, Post.class);
		
		createdPost.setImageName("default.png");
		createdPost.setAddedDate(new Date());
		createdPost.setCategory(category);
		createdPost.setUser(user);
		
		Post newPost=this.postRepo.save(createdPost);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		
		Post post=this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		
		post.setTitle(postDto.getTitle()==null ? post.getTitle() : postDto.getTitle());
		post.setContent(postDto.getContent()==null ? post.getContent() : postDto.getContent());
		post.setImageName(postDto.getImageName()==null ? post.getImageName() : postDto.getImageName()); 
		
		/*if(postDto.getImageName()==null)
		{
			String string=post.getImageName();
			post.setImageName(string);
		}
		else {
			post.setImageName(postDto.getImageName());
		}*/
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(int postId) {

		Post post=this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		
		this.postRepo.delete(post);
		
	}

	@Override
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy) {
		
		//Implementing pagination on viewing all posts
		PageRequest p=PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<Post> pagePosts=this.postRepo.findAll(p);
		
		List<Post> allPosts=pagePosts.getContent();
		
		List<PostDto> allPostsDtos=allPosts.stream().map((post) -> this.modelMapper.map(
				post, PostDto.class)).collect(Collectors.toList());
		
		//To send pagination details as response from this method		
		PostResponse postResponse=new PostResponse();
		
		postResponse.setContent(allPostsDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostByID(int postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getPostByUser(int userID, int pageNumber, int pageSize, String sortBy) {
		
		//Implementing pagination on viewing all posts
		PageRequest p=PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		
		User user=this.userRepo.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userID));
		
		Page<Post> pagePosts=this.postRepo.findByUser(user, p);
		
		List<Post> allPosts=pagePosts.getContent();
		
		List<PostDto> postsDto=allPosts.stream().map((post) -> this.modelMapper.map(
				post, PostDto.class)).collect(Collectors.toList());
		
		//To send pagination details as response from this method		
		PostResponse postResponse=new PostResponse();
		
		postResponse.setContent(postsDto);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
		
	}

	@Override
	public PostResponse getPostByCategory(int categoryId, int pageNumber, int pageSize, String sortBy) {
		
		//Implementing pagination on viewing all posts
		PageRequest p=PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", categoryId));
		
		Page<Post> pagePosts=this.postRepo.findByCategory(category, p);
		
		List<Post> allPosts=pagePosts.getContent();
		
		List<PostDto> postsDto=allPosts.stream().map((post) -> this.modelMapper.map(
				post, PostDto.class)).collect(Collectors.toList());
		
		//To send pagination details as response from this method		
		PostResponse postResponse=new PostResponse();
				
		postResponse.setContent(postsDto);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
				
		return postResponse;
	}
	
	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		List<Post> postSearched=this.postRepo.searchByTitle("%"+keyword+"%");
		
		//postSearched.forEach((post) -> System.out.println(post.getPostId()));
		
		List<PostDto> postSearchedDto = postSearched.stream().map(
				(post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		//postSearchedDto.forEach((post) -> System.out.println(post.getPostId()));
		
		return postSearchedDto;
	}
	

}
