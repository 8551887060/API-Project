package com.apiproject.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiproject.payload.CommentDto;
import com.apiproject.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
    
	private CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService=commentService;
	}
	
	@PostMapping("/posts/{postId}/comment")
	public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDto commentDto,BindingResult bindingResult,@PathVariable("postId") long postId){
		if(bindingResult.hasErrors()) {
		return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		CommentDto dto = commentService.createComment(commentDto,postId);
		return new ResponseEntity<>(dto,HttpStatus.CREATED);
	}
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId){
		 List<CommentDto> dto = commentService.getCommentsByPostId(postId);
		return dto;
	}
	@PutMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment(
			@PathVariable("postId") long postId,
			@PathVariable("id") long id,
			@RequestBody CommentDto commentDto){
		CommentDto dto = commentService.updateComment(postId,id,commentDto);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(
			@PathVariable("postId") long postId,
			@PathVariable("id") long id
			){
		commentService.deleteComment(postId,id);
		return new ResponseEntity<>("Comment Deleted Successfully..",HttpStatus.OK);
	}
}
