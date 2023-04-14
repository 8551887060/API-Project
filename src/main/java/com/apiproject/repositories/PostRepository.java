package com.apiproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiproject.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
