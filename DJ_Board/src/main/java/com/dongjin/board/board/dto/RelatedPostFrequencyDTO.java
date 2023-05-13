package com.dongjin.board.board.dto;

public class RelatedPostFrequencyDTO {

	private int id;
	private int relatedPostId;
	private int postId;
	private int frequency;
	
	
	public RelatedPostFrequencyDTO() {
		super();
	}
	public RelatedPostFrequencyDTO(int id, int relatedPostId, int postId, int frequency) {
		super();
		this.id = id;
		this.relatedPostId = relatedPostId;
		this.postId = postId;
		this.frequency = frequency;
	}
	@Override
	public String toString() {
		return "RelatedPostFrequency [id=" + id + ", relatedPostId=" + relatedPostId + ", postId=" + postId
				+ ", frequency=" + frequency + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	
	
}
