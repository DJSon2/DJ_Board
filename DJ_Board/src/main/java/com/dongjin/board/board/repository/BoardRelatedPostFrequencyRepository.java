package com.dongjin.board.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongjin.board.board.entity.RelatedPostFrequency;

public interface BoardRelatedPostFrequencyRepository extends JpaRepository<RelatedPostFrequency, Integer> {

	List<RelatedPostFrequency> findByPostId(int postId);

	List<RelatedPostFrequency> findByRelatedPostId(int relatedPostId);

	List<RelatedPostFrequency> findPostIdByRelatedPostId(int relatedPostId);
}
