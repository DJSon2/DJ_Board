package com.dongjin.board.board.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dongjin.board.board.entity.Post;

public interface BoardRepository extends JpaRepository<Post, Integer>{

	int countByTitleContaining(String searchValue);

	List<Post> findByTitleContaining(String searchValue, Pageable paging);
}
