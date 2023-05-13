package com.dongjin.board.board.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@EntityListeners(AuditingEntityListener.class)
@Entity(name="RelatedPost")
@Table(name = "BOARD_RELATED_POST")
@SequenceGenerator( /// 식별자, 시퀀스전략
        name = "RELATED_POST_SEQ_GENERATOR",
        sequenceName = "SEQ_RELATED_POST_ID",
        initialValue = 1,
        allocationSize = 1
)
public class RelatedPost {

	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "RELATED_POST_SEQ_GENERATOR"
			)
    @Column(name = "RELATED_POST_ID")
    private int relatedPostId;
	
	@Column(name = "POST_ID")
    private int postId;
	
	@Column(name = "FREQUENCY")
	private int frequency;
	
	@Column(name = "WORD")
	private String word;

	public RelatedPost() {
		super();
	}

	public RelatedPost(int relatedPostId, int postId, int frequency, String word) {
		super();
		this.relatedPostId = relatedPostId;
		this.postId = postId;
		this.frequency = frequency;
		this.word = word;
	}

	@Override
	public String toString() {
		return "RelatedPost [relatedPostId=" + relatedPostId + ", postId=" + postId + ", frequency=" + frequency
				+ ", word=" + word + "]";
	}

	public int getRelatedPostId() {
		return relatedPostId;
	}

	public void setRelatedPostId(int relatedPostId) {
		this.relatedPostId = relatedPostId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
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
