package com.dongjin.board.board.dto;

import java.sql.Clob;
import java.sql.Date;
import java.sql.Timestamp;

public class CommentDTO {

	private int commentId;
	private int postId;
	private String content;
	private int authorId;
	private java.sql.Date createAT;

	public CommentDTO() {
	}

	public CommentDTO(int commentId, int postId, String content, int authorId, Date createAT) {
		super();
		this.commentId = commentId;
		this.postId = postId;
		this.content = content;
		this.authorId = authorId;
		this.createAT = createAT;
	}

	@Override
	public String toString() {
		return "CommentDTO [commentId=" + commentId + ", postId=" + postId + ", content=" + content + ", authorId="
				+ authorId + ", createAT=" + createAT + "]";
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public java.sql.Date getCreateAT() {
		return createAT;
	}

	public void setCreateAT(java.sql.Date createAT) {
		this.createAT = createAT;
	}

	
}
