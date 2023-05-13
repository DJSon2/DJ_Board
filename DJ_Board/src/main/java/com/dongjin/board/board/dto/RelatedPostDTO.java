package com.dongjin.board.board.dto;

import java.sql.Clob;

public class RelatedPostDTO {

	private int relatedPostId;
	private String word;

	public RelatedPostDTO() {
	}

	public RelatedPostDTO(int relatedPostId, String word) {
		super();
		this.relatedPostId = relatedPostId;
		this.word = word;
	}

	@Override
	public String toString() {
		return "RelatedPostDTO [relatedPostId=" + relatedPostId + ", word=" + word + "]";
	}

	public int getRelatedPostId() {
		return relatedPostId;
	}

	public void setRelatedPostId(int relatedPostId) {
		this.relatedPostId = relatedPostId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	
	
}
