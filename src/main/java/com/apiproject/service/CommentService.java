package com.apiproject.service;


import java.util.List;

import com.apiproject.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, long postId);

	List<CommentDto> getCommentsByPostId(long postId);

	CommentDto updateComment(long postId, long id, CommentDto commentDto);

	void deleteComment(long postId, long id);



}
