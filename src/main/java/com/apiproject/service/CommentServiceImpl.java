package com.apiproject.service;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.apiproject.Exception.ResourceNotFoundException;
import com.apiproject.entities.Comment;
import com.apiproject.entities.Post;
import com.apiproject.payload.CommentDto;
import com.apiproject.repositories.CommentRepository;
import com.apiproject.repositories.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepo;
	
	private PostRepository postRepo;
	
	private ModelMapper mapper;
	
	public CommentServiceImpl(CommentRepository commentRepo,PostRepository postRepo,ModelMapper mapper) {
		this.commentRepo=commentRepo;
		this.postRepo=postRepo;
		this.mapper=mapper;
	}
	@Override
	public CommentDto createComment(CommentDto commentDto, long postId) {
		 Post post = postRepo.findById(postId).orElseThrow(
			()-> new ResourceNotFoundException("post", "postId", postId)	
				);
		Comment comment = mapToEntity(commentDto);
		   comment.setPost(post);
		Comment newComment = commentRepo.save(comment);
		
		 CommentDto dto = mapToDto(newComment);
		return dto;
	}

	public Comment mapToEntity(CommentDto commentDto) {
		  Comment comment = mapper.map(commentDto, Comment.class);
		return comment;
	}
	
	public CommentDto mapToDto(Comment comment) {
		 CommentDto commentDto = mapper.map(comment, CommentDto.class);
		return commentDto;
	}
	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("post", "id", postId)
				);
		List<Comment> comments = commentRepo.findByPostId(postId);
		List<CommentDto> dto = comments.stream().map(comment-> mapToDto(comment)).collect(Collectors.toList());
		return dto;
	}
	@Override
	public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
		Post post = postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("post", "id", postId)
				);
		
		Comment comment = commentRepo.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("comment", "id", id)
				);
		
		
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		
		Comment newComment = commentRepo.save(comment);
		CommentDto dto = mapToDto(newComment);
		return dto;
	}
	@Override
	public void deleteComment(long postId, long id) {
		postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("post", "id", postId)
				);
		
		commentRepo.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("comment", "id", id)
				);
		
		commentRepo.deleteById(id);
	}
	
	
	
}
