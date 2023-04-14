package com.apiproject.payload;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostDto {

	private long id;
    @NotNull
    @Size(min=2, message="Post title should have atleast 2 characters")
	private String title;
    @NotNull
    @Size(min=10,message="Post description should have atleast 10 characters or more")
	private String description;
    
    @NotNull
	private String content;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
