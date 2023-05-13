package com.dongjin.board.board.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@Column(name = "WORD")
	private String word;

	public RelatedPost() {
		super();
	}

	public RelatedPost(int relatedPostId, String word) {
		super();
		this.relatedPostId = relatedPostId;
		this.word = word;
	}

	@Override
	public String toString() {
		return "RelatedPost [relatedPostId=" + relatedPostId + ", word=" + word + "]";
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
