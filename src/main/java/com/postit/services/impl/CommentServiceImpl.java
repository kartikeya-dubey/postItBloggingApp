package com.postit.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postit.entities.Comment;
import com.postit.entities.Post;
import com.postit.entities.User;
import com.postit.exceptions.ResourceNotFoundException;
import com.postit.payloads.CommentDto;
import com.postit.repositories.CommentRepo;
import com.postit.repositories.PostRepo;
import com.postit.repositories.UserRepo;
import com.postit.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, int postId) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post", "Post ID", postId));
		
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUser(post.getUser());   //This is setting the post creator as comment creator
		Comment savedComment=this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}
	
	//Modified Create Comment method where Comment content and comment creator username is supplied as input
	@Override
	public CommentDto createCommentByUser(CommentDto commentDto, int postId, String username) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post", "Post ID", postId));
		
		User user=this.userRepo.searchByUsername(username);
		System.out.println(username);
		System.out.println(user.getName().toString());
		
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment=this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}
	

	@Override
	public CommentDto updateComment(CommentDto commentDto, int commentId) {
		
		//Post post=this.postRepo.findById(postId).orElseThrow(
				//()-> new ResourceNotFoundException("Post", "Post ID", postId));
		
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(
				()-> new ResourceNotFoundException("Comment", "Comment ID", commentId));
		
		comment.setContent(commentDto.getContent());
		this.commentRepo.save(comment);
		
		return this.modelMapper.map(comment, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(
				()-> new ResourceNotFoundException("Comment", "Comment ID", commentId));
		
		this.commentRepo.delete(comment);

	}

}
