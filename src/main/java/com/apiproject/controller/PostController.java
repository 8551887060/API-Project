package com.apiproject.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiproject.payload.PostDto;
import com.apiproject.payload.PostResponse;
import com.apiproject.service.PostService;
import com.apiproject.util.AppConstants;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    
	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService= postService;
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
		return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
		PostDto dto = postService.createPost(postDto);
		return new ResponseEntity<>(dto,HttpStatus.CREATED);
	}
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value="pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO,required = false) int pageNo,
			@RequestParam(value="pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir
			) {
		return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);	
	  
	}
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {
		PostDto dto = postService.getPostById(id);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable("id") long id){
		PostDto dto = postService.updatePostById(postDto, id);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable("id") long id){
		postService.deletePostById(id);
		return new ResponseEntity<>("Post deleted successfully...", HttpStatus.OK);
	}
}
