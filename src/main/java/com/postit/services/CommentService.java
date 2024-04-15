package com.postit.services;

import com.postit.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,int postId);
	
	CommentDto createCommentByUser(CommentDto commentDto,int postId,String username);
	
	CommentDto updateComment(CommentDto commentDto, int commentId);

	void deleteComment(int commentId);
}
