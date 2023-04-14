package com.apiproject.service;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.apiproject.Exception.ResourceNotFoundException;
import com.apiproject.entities.Post;
import com.apiproject.payload.PostDto;
import com.apiproject.payload.PostResponse;
import com.apiproject.repositories.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepo;
	private ModelMapper mapper;
	
	public PostServiceImpl(PostRepository postRepo,ModelMapper mapper) {
		this.postRepo=postRepo;
		this.mapper= mapper;
	}
	@Override
	public PostDto createPost(PostDto postDto) {
		Post post = mapToEntity(postDto);
			Post postEntity = postRepo.save(post);
		   PostDto dto = mapToDto(postEntity);
		return dto;
	}
	
	public Post mapToEntity(PostDto postDto) {
		Post post = mapper.map(postDto, Post.class);
		return post;
	}
	
	public PostDto mapToDto(Post post) {
		PostDto dto = mapper.map(post, PostDto.class);
		return dto;
	}
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort =sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		 Pageable pageable=PageRequest.of(pageNo, pageSize, sort);
		 Page<Post> posts = postRepo.findAll(pageable);
		 List<Post> content = posts.getContent();
		 List<PostDto> contents = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		 
		 PostResponse postResponse= new PostResponse();
		 postResponse.setContent(contents);
		 postResponse.setPageNo(posts.getNumber());
		 postResponse.setPageSize(posts.getSize());
		 postResponse.setTotalElements(posts.getTotalElements());
		 postResponse.setTotalPages(posts.getTotalPages());
		return postResponse;
	}
	@Override
	public PostDto getPostById(long id) {
		Post post = postRepo.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("post", "id", id)
				);
		PostDto postDto = mapToDto(post);
		return postDto;
	}
	@Override
	public PostDto updatePostById(PostDto postDto, long id) {
		Post post = postRepo.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("post", "id", id)
				);
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		Post post1 = postRepo.save(post);
		PostDto dto = mapToDto(post1);
		return dto;
	}
	@Override
	public void deletePostById(long id) {
		 postRepo.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("post", "id", id)
				);
		postRepo.deleteById(id);
	}

}
