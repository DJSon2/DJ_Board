package com.dongjin.board.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongjin.board.board.entity.RelatedPost;

public interface BoardRelatedPostRepository extends JpaRepository<RelatedPost, Integer>{

}
