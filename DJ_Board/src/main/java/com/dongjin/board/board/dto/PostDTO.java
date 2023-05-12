package com.dongjin.board.board.dto;

import java.sql.Clob;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PostDTO {

	private int postId;
	private String title;
	private String content;
	private String authorName;
	private java.sql.Date createdAT;

	
	public PostDTO() {
	}


	public PostDTO(int postId, String title, String content, String authorName, Date createdAT) {
		super();
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.authorName = authorName;
		this.createdAT = createdAT;
	}


	@Override
	public String toString() {
		return "PostDTO [postId=" + postId + ", title=" + title + ", content=" + content + ", authorName=" + authorName
				+ ", createdAT=" + createdAT + "]";
	}


	public int getPostId() {
		return postId;
	}


	public void setPostId(int postId) {
		this.postId = postId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


	public java.sql.Date getCreatedAT() {
		return createdAT;
	}


	public void setCreatedAT(java.sql.Date createdAT) {
		this.createdAT = createdAT;
	}




	
}
