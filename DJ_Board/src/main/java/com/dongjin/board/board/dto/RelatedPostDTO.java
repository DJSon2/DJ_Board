package com.dongjin.board.board.dto;

import java.sql.Clob;

public class RelatedPostDTO {

	private int postId;
	private int relatedPostId;
	private int frequency;
	private String word;

	public RelatedPostDTO() {
	}

	public RelatedPostDTO(int postId, int relatedPostId, int frequency, String word) {
		super();
		this.postId = postId;
		this.relatedPostId = relatedPostId;
		this.frequency = frequency;
		this.word = word;
	}

	@Override
	public String toString() {
		return "RelatedPostDTO [postId=" + postId + ", relatedPostId=" + relatedPostId + ", frequency=" + frequency
				+ ", word=" + word + "]";
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getRelatedPostId() {
		return relatedPostId;
	}

	public void setRelatedPostId(int relatedPostId) {
		this.relatedPostId = relatedPostId;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	
	
}
