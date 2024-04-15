package com.postit.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.postit.config.AppConstants;
import com.postit.payloads.ApiResponse;
import com.postit.payloads.PostDto;
import com.postit.payloads.PostResponse;
import com.postit.services.FileService;
import com.postit.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}") //From properties
	private String path;
	
	
	//POST - Create post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> creatPost(
			@RequestBody PostDto postDto,
			@PathVariable int userId,
			@PathVariable int categoryId)
	{
		PostDto createdPost=this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
	//PUT - Update post
	@PutMapping("/posts/{postID}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable int postID)
	{
		PostDto updatedPost =this.postService.updatePost(postDto, postID);
		
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	//DELETE - delete post
	@DeleteMapping("posts/{postID}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postID)
	{
		this.postService.deletePost(postID);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted succesfully", true),
				HttpStatus.OK);
	}
	
	
	//GET - Fetch post
	
	//get all posts by a user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@PathVariable int userId){
		
		PostResponse postResponse = this.postService.getPostByUser(userId, pageNumber, pageSize, sortBy);
		
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
		
	}
	
	//get all posts by a category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@PathVariable int categoryId){
			
		PostResponse postResponse = this.postService.getPostByCategory(categoryId, pageNumber, pageSize, sortBy);
			
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
			
	}
	
	//get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy)
	{
				
		PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy);
				
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
				
	}
	
	//get all posts by ID
	@GetMapping("/posts/{postID}")
	public ResponseEntity<PostDto> getPostByID(@PathVariable int postID){
				
		PostDto postsDtos = this.postService.getPostByID(postID);
				
		return new ResponseEntity<PostDto>(postsDtos, HttpStatus.OK);
				
	}
	
	
	//Searching
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle( @PathVariable String keywords){
		
		List<PostDto> searchResult=this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(searchResult, HttpStatus.OK);
		
	}
	
	//Uploading image for post
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable(value = "postId") int postID) throws IOException
	{
		//Fetch post details mapped to the postID
		PostDto postDto=this.postService.getPostByID(postID);
		
		//Upload file in designated location and get random file name generated
		String fileName=this.fileService.uploadImage(path, image);
		
		//add uploaded image name in post details in DB
		postDto.setImageName(fileName);
		PostDto updatedPost=this.postService.updatePost(postDto, postID);
		
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	//Serve (display/access) image files
	@GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException
	{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
	
}
