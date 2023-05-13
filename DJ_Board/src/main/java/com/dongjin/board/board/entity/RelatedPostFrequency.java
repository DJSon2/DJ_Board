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
@Entity(name="RelatedPostFrequency")
@Table(name = "BOARD_RELATED_POST_FREQUENCY")
@SequenceGenerator( /// 식별자, 시퀀스전략
        name = "RELATED_POST_FREQUENCY_SEQ_GENERATOR",
        sequenceName = "SEQ_ID",
        initialValue = 1,
        allocationSize = 1
)
public class RelatedPostFrequency {

	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "RELATED_POST_FREQUENCY_SEQ_GENERATOR"
			)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "FREQUENCY")
    private int frequency;
    
    @Column(name = "RELATED_POST_ID")
    private int relatedPostId;
    
    @Column(name = "POST_ID")
    private int postId;

    
	public RelatedPostFrequency() {
		super();
	}


	public RelatedPostFrequency(Long id, int frequency, int relatedPostId, int postId) {
		super();
		this.id = id;
		this.frequency = frequency;
		this.relatedPostId = relatedPostId;
		this.postId = postId;
	}


	@Override
	public String toString() {
		return "RelatedPostFrequency [id=" + id + ", frequency=" + frequency + ", relatedPostId=" + relatedPostId
				+ ", postId=" + postId + "]";
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getFrequency() {
		return frequency;
	}


	public void setFrequency(int frequency) {
		this.frequency = frequency;
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
	
    
}
