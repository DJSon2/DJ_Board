package com.dongjin.board.board.entity;

import java.sql.Clob;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@EntityListeners(AuditingEntityListener.class)
@Entity(name="Board")
@Table(name = "TBL_QNA_BOARD")
public class RelatedPost {

	@Id
	@Column(name = "POST_ID")
	private int postId;
	
	@Column(name = "RELATED_POST_ID")
	private int relatedPostId;
	
	@Column(name = "FREQUENCY")
	private int frequency;
	
	@Column(name = "WORD")
	private String word;

	public RelatedPost() {
		super();
	}

	public RelatedPost(int postId, int relatedPostId, int frequency, String word) {
		super();
		this.postId = postId;
		this.relatedPostId = relatedPostId;
		this.frequency = frequency;
		this.word = word;
	}

	@Override
	public String toString() {
		return "RelatedPost [postId=" + postId + ", relatedPostId=" + relatedPostId + ", frequency=" + frequency
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
