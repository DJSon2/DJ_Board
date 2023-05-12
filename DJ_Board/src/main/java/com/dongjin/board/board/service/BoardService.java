package com.dongjin.board.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dongjin.board.board.dto.PostDTO;
import com.dongjin.board.board.entity.Post;
import com.dongjin.board.board.paging.SelectCriteria;
import com.dongjin.board.board.repository.BoardRelatedPostRepository;
import com.dongjin.board.board.repository.BoardRepository;

import jakarta.transaction.Transactional;

@Service
public class BoardService {

	private static final Logger log = LoggerFactory.getLogger(BoardService.class);
	private final ModelMapper modelMapper;
	private final BoardRepository boardRepository;
	private final BoardRelatedPostRepository boardRelatedPostRepository;
	
	@Autowired
	public BoardService(BoardRepository boardRepository, ModelMapper modelMapper, BoardRelatedPostRepository boardRelatedPostRepository) {
		this.modelMapper = modelMapper;
		this.boardRepository = boardRepository;
		this.boardRelatedPostRepository = boardRelatedPostRepository;
	}
	
	/* 게시글 전체 조회 */
	@Transactional
	public List<PostDTO> findPostList() {
		
		List<Post> postList = boardRepository.findAll(Sort.by("postId"));
		
		return postList.stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
	}
	
	/* 페이징, 검색 */
	public int selectTotalCount(String searchCondition, String searchValue) {

		int count = 0;
		if(searchValue != null) {
			if("title".equals(searchCondition)) {
				count = boardRepository.countByTitleContaining(searchValue);
			}

		} else {
			count = (int)boardRepository.count();
		}

		return count;
	}
	
	/* 게시글 검색 리스트 페이징 처리 포함 */
	public List<PostDTO> searchBoardList(SelectCriteria selectCriteria) {

		int index = selectCriteria.getPageNo() - 1;			// Pageble객체를 사용시 페이지는 0부터 시작(1페이지가 0)
		int count = selectCriteria.getLimit();
		String searchValue = selectCriteria.getSearchValue();

		/* 페이징 처리와 정렬을 위한 객체 생성 */
		Pageable paging = PageRequest.of(index, count, Sort.by(Sort.Direction.DESC, "postId"));	// Pageable은 org.springframework.data.domain패키지로 import

		List<Post> postList = new ArrayList<Post>();
		if(searchValue != null) {

			if("title".equals(selectCriteria.getSearchCondition())) {
				postList = boardRepository.findByTitleContaining(selectCriteria.getSearchValue(), paging);
			}

		} else {
			postList = boardRepository.findAll(paging).toList();
		}

		/* 자바의 Stream API와 ModelMapper를 이용하여 entity를 DTO로 변환 후 List<MenuDTO>로 반환 */
		return postList.stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
	}
	
	/* 상세페이지 */
	@Transactional
	public PostDTO findPostById(int postId) {
		log.info("[BoardService] findPostById Start ==================");
		Post post = boardRepository.findById(postId).get();
		
		log.info("[BoardService] findPostById test : " + post);

		log.info("[BoardService] findPostById End ==================");
		return modelMapper.map(post, PostDTO.class);
	}
	
	/* 새로운 게시물 등록 */
	@Transactional
	public void registNewPost(PostDTO newPost) {

		boardRepository.save(modelMapper.map(newPost, Post.class)); 
	}
}
