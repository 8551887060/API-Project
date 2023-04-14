package com.apiproject.service;

import com.apiproject.payload.PostDto;
import com.apiproject.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto);

	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

	PostDto getPostById(long id);

	PostDto updatePostById(PostDto postDto, long id);

	void deletePostById(long id);

}
