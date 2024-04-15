package com.postit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postit.payloads.ApiResponse;
import com.postit.payloads.CommentDto;
import com.postit.services.CommentService;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable(name = "postId") int postId)
	{
		CommentDto createdCommentDto=this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createdCommentDto, HttpStatus.CREATED);
	}
	
	//Modified Create Comment api call where comment maker details are also supplied
	@PostMapping("/post/{postId}/user/{username}/comments")
	public ResponseEntity<CommentDto> createCommentByUser(
			@RequestBody CommentDto commentDto,
			@PathVariable String username,
			@PathVariable(name = "postId") int postId)
	{
		CommentDto createdCommentDto=this.commentService.createCommentByUser(commentDto, postId, username);
		return new ResponseEntity<CommentDto>(createdCommentDto, HttpStatus.CREATED);
	}
	
	//Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJ0aWtleWFAZGV2LmluIiwiaWF0IjoxNzEyNjgwNjg0LCJleHAiOjE3MTI2OTg2ODR9.6YUkrHNS3cDGUfJ-ImZoDWjCCO0oFshA7d8HoP7ddYE
	
	//ADMIN role based service
	//PUT - update comment
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/comments/update/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,
			@PathVariable(name = "commentId") int commentId)
	{
		CommentDto updatedCommentDto=this.commentService.updateComment(commentDto, commentId);
		return new ResponseEntity<CommentDto>(updatedCommentDto, HttpStatus.OK);
	} 
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId)
	{
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully!",true), HttpStatus.OK);
	}

}
