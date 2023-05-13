package com.dongjin.board.board.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.dongjin.board.board.dto.RelatedPostDTO;
import com.dongjin.board.board.entity.Post;
import com.dongjin.board.board.entity.RelatedPost;
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
	public BoardService(BoardRepository boardRepository, ModelMapper modelMapper,
			BoardRelatedPostRepository boardRelatedPostRepository) {
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
		if (searchValue != null) {
			if ("title".equals(searchCondition)) {
				count = boardRepository.countByTitleContaining(searchValue);
			}

		} else {
			count = (int) boardRepository.count();
		}

		return count;
	}

	/* 게시글 검색 리스트 페이징 처리 포함 */
	public List<PostDTO> searchBoardList(SelectCriteria selectCriteria) {

		int index = selectCriteria.getPageNo() - 1; // Pageble객체를 사용시 페이지는 0부터 시작(1페이지가 0)
		int count = selectCriteria.getLimit();
		String searchValue = selectCriteria.getSearchValue();

		/* 페이징 처리와 정렬을 위한 객체 생성 */
		Pageable paging = PageRequest.of(index, count, Sort.by(Sort.Direction.DESC, "postId")); // Pageable은
																								// org.springframework.data.domain패키지로
																								// import

		List<Post> postList = new ArrayList<Post>();
		if (searchValue != null) {

			if ("title".equals(selectCriteria.getSearchCondition())) {
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

	@Transactional
	public void registNewPost(PostDTO newPost, RelatedPostDTO newRelatedPost) {
		log.info("[BoardService] registNewPost Start ==================");
		
		/* 게시물 DB를 위한 저장 */
	    java.util.Date now = new java.util.Date(); // 현재 시간 구하기
	    java.sql.Date sqlDate = new java.sql.Date(now.getTime()); // java.util.Date를 java.sql.Date로 변환
	    newPost.setCreatedAT(sqlDate); // 현재 시간 저장

	    Post post = modelMapper.map(newPost, Post.class);
	    
	    boardRepository.save(post);
	    
	    
	    /* 연관 게시물 테이블 저장 */
	    Integer postId = post.getPostId(); // 새로 생성된 게시물의 postId 값 가져오기
		
		log.info("[BoardService] registNewPost postId 확인ㅇ : " + postId);

	    
	    String[] words = post.getContent().split("\\s+");

	    Map<String, Integer> wordCounts = new HashMap<>();
	    for (String word : words) {
	        if (wordCounts.containsKey(word)) {
	            wordCounts.put(word, wordCounts.get(word) + 1);
	        } else {
	            wordCounts.put(word, 1);
	        }
	    }
	    
		log.info("[BoardService] registNewPost 게시물 내용 단어 별 횟수 카운팅 : " + wordCounts);

	    long totalCount = words.length;

		log.info("[BoardService] registNewPost totalCount : " + totalCount);

	    List<String> frequentWords = new ArrayList<>();
	    for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
	        
	    	double frequency = (double) entry.getValue() / (double) totalCount;
	    	
	    	
	        frequentWords.add(entry.getKey());
	        log.info("[BoardService] registNewPost frequency : " + frequency);
	       
	        log.info("[BoardService] registNewPost frequentWords : " + frequentWords);

	        if (frequency < 0.6) {
	        	
	        	// 등록하려는 단어가 이전에 등록되었는지 검색
	        	// List<String>을 String 형으로 변경
	        	String WordsString = String.join(",", frequentWords);
	           
    	        log.info("[BoardService] registNewPost 이전에 존재하던 RelatedId 확인 : " + WordsString);
  	
	        	RelatedPost existingRelatedPost = boardRelatedPostRepository.findByWord(WordsString);
	           
	            if (existingRelatedPost != null) {
	            	
	    	        log.info("[BoardService] registNewPost existingRelatedPost if 문 Start ========= ");

	                // 등록된 단어가 있을 경우, 해당 단어의 PK값을 가져옴
	                newRelatedPost.setRelatedPostId(existingRelatedPost.getRelatedPostId());
	               
	                int existingRelatedPost1 = existingRelatedPost.getRelatedPostId();
	    	        log.info("[BoardService] registNewPost existingRelatedPost1 확인 : " + existingRelatedPost1);

	                newRelatedPost.setWord(WordsString);
	                newRelatedPost.setPostId(postId);
	    	        newRelatedPost.setFrequency((int) frequency);
	    	        
	    	        log.info("[BoardService] registNewPost 이전에 존재하던 단어(WordsString 확인 : " + WordsString);
	    	        RelatedPost relatedPost = modelMapper.map(newRelatedPost, RelatedPost.class);
	    	        boardRelatedPostRepository.save(relatedPost);
	    	        log.info("[BoardService] registNewPost existingRelatedPost if 문 End ========= ");

	    	        
	            } else {
	            	
	    	        log.info("[BoardService] registNewPost existingRelatedPost if 문 else Start ========= ");
	            	
	                // 등록된 단어가 없을 경우, 시퀀스에서 새로운 PK값을 가져옴       
	    	        newRelatedPost.setRelatedPostId(0); // 시퀀스에서 가져온 다음 값 설정
	    	        newRelatedPost.setWord(WordsString);
	    	        newRelatedPost.setPostId(postId);
	    	        newRelatedPost.setFrequency((int) frequency);
	    	        
	    	        log.info("[BoardService] registNewPost 새로 등록되는 단어 확인 : " + WordsString);
	    	        RelatedPost relatedPost = modelMapper.map(newRelatedPost, RelatedPost.class);
	    	        boardRelatedPostRepository.save(relatedPost);
	    	        
	    	        log.info("[BoardService] registNewPost existingRelatedPost if 문 else End ========= ");

	            }
	        

	        }
	        // frequentWords 리스트 초기화
	        frequentWords.clear();
	        log.info("[BoardService] registNewPost frequentWords.clear() 확인 : ");

	    }
	   

		log.info("[BoardService] registNewPost End ==================");
	}

	
	
}
