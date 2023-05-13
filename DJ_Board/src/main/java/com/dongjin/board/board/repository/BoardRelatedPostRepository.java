package com.dongjin.board.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongjin.board.board.entity.RelatedPost;

public interface BoardRelatedPostRepository extends JpaRepository<RelatedPost, Integer>{

	RelatedPost findByWord(String wordsString);


}
