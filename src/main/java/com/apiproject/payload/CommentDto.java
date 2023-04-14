package com.apiproject.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentDto {

	private long id;
	@NotNull
	@Size(min=2,message="Comment body should have atleast 2 characters or more")
	private String body;
	@NotNull
	@Email(message="Email Format not valid")
	private String email;
	private String name;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
  
	
	
	
}
