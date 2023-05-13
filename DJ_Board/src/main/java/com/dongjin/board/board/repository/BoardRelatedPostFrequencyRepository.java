package com.dongjin.board.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongjin.board.board.entity.RelatedPostFrequency;

public interface BoardRelatedPostFrequencyRepository extends JpaRepository<RelatedPostFrequency, Integer> {

}
