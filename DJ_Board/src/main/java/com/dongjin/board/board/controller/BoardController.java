package com.dongjin.board.board.controller;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dongjin.board.board.dto.PostDTO;
import com.dongjin.board.board.dto.RelatedPostDTO;
import com.dongjin.board.board.entity.Post;
import com.dongjin.board.board.paging.Pagenation;
import com.dongjin.board.board.paging.SelectCriteria;
import com.dongjin.board.board.repository.BoardRepository;
import com.dongjin.board.board.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/board")	
public class BoardController {

	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	private final BoardService boardService;
	private final BoardRepository boardRepository;
    private final JdbcTemplate jdbcTemplate;

	
	@Autowired
	public BoardController(BoardService boardService, BoardRepository boardRepository, JdbcTemplate jdbcTemplate) {
		this.boardService = boardService;
		this.boardRepository = boardRepository;
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/* 게시판 전체보기 핸들러 */
	@GetMapping("/list")
	public ModelAndView findBoardList(ModelAndView mv, HttpServletRequest request) {
		
		List<PostDTO> postList = boardService.findPostList();
		
		String currentPage = request.getParameter("currentPage");
		int pageNo = 1;
		
		if(currentPage != null && !"".equals(currentPage)) {
			pageNo = Integer.parseInt(currentPage);
		}

		String searchCondition = request.getParameter("searchCondition");
		String searchValue = request.getParameter("searchValue");
	
		int totalCount = boardService.selectTotalCount(searchCondition, searchValue);
		
		/* 한 페이지에 보여 줄 게시물 수 */
		int limit = 10;		//얘도 파라미터로 전달받아도 된다.

		/* 한 번에 보여질 페이징 버튼의 갯수 */
		int buttonAmount = 5;
		
		/* 페이징 처리를 위한 로직 호출 후 페이징 처리에 관한 정보를 담고 있는 인스턴스를 반환받는다. */
		SelectCriteria selectCriteria = null;
		if(searchValue != null && !"".equals(searchValue)) {
			selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition, searchValue);
		} else {
			selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);
		}
		
		
		List<PostDTO> postList1 = boardService.searchBoardList(selectCriteria);
		
		for(PostDTO post : postList) {
			System.out.println(post);
		}

		mv.addObject("postList", postList1);
		
		mv.addObject("selectCriteria", selectCriteria);
		mv.setViewName("board/list");

		
		return mv;
	}
	
	/* 디테일 페이지 */
	@GetMapping("/{postId}")
	public ModelAndView boardDetail(ModelAndView mv, @PathVariable int postId) {

		log.info("[BoardController] boardDetail Start ==================");
		
		PostDTO post = boardService.findPostById(postId);
//		System.out.println("========================="+post);
		mv.addObject("post", post);
		mv.setViewName("/board/detail");
//		log.info("[BoardController] boardDetail test : " + post);
		
		log.info("[BoardController] boardDetail End ==================");
		return mv;
	}
	
	/* 새 게시물 작성 */
	@GetMapping("/regist")
	public void registPage() {
	}
	
	@PostMapping("/regist")
	public ModelAndView registPost(ModelAndView mv, PostDTO newPost, RelatedPostDTO newRelatedPost, RedirectAttributes rttr) {
		
		log.info("[BoardController] registPost Start ==================");
		
		java.util.Date now = new java.util.Date(); // 현재 시간 구하기
		java.sql.Date sqlDate = new java.sql.Date(now.getTime()); // java.util.Date를 java.sql.Date로 변환
		newPost.setCreatedAT(sqlDate); // 현재 시간 저장
		
		// 게시물 데이터 저장
		boardService.registNewPost(newPost);
		
		log.info("[BoardController] registNewPost 저장되는 데이터 확인(newPost) : " + newPost);

		// 시퀀스에서 다음 PK 값을 가져온다.
//		String query = "SELECT MAX(POST_ID) FROM BOARD_POST";
//		int postId = jdbcTemplate.queryForObject(query, Integer.class);
//		// 가져온 PK 값을 PostDTO에 설정한다.
//		newPost.setPostId(postId);
		

		
		// 게시물 연관성 저장
//		boardService.registNewRelatedPost(newRelatedPost);
		
		/* 게시물 테이블 내용을 통해 연관성 구하는 로직 */
		// 게시물 내용 가져오기
		String content = newPost.getContent();
		Integer postId = newPost.getPostId();
//		Integer postId1 = Post.getPostId();
		
		log.info("[BoardController] registPost postId 확인 : " + postId);

//		log.info("[BoardController] registPost postId1 확인 : " + postId1);

		// 게시물 내용을 단어로 나누기
		String[] words = content.split("\\s+");
		// 단어별로 빈도수 체크
		Map<String, Integer> wordCounts = new HashMap<>();
		for (String word : words) {
		    if (wordCounts.containsKey(word)) {
		        wordCounts.put(word, wordCounts.get(word) + 1);
		    } else {
		        wordCounts.put(word, 1);
		    }
		    log.info("[BoardController] registPost word 확인 : " + word);
		    log.info("[BoardController] registPost wordCounts 확인 : " + wordCounts);
		}

		// 전체 게시물에서 60% 이상 나타나는 단어 배제하기
		List<String> frequentWords = new ArrayList<>();
		long totalCount = boardRepository.count();
		for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
		    double frequency = (double) entry.getValue() / (double) totalCount;
		    if (frequency < 0.6) {
		        frequentWords.add(entry.getKey());
		    }
		    log.info("[BoardController] registPost frequentWords 확인 : " + frequentWords);

		}
		
		// 게시물 연관성 저장
//		boardService.registNewRelatedPost(newRelatedPost);
		
		rttr.addFlashAttribute("registSuccessMessage", "글 작성 완료");
		mv.setViewName("redirect:/main");
		
		log.info("[BoardController] registPost End ==================");
		return mv;
	}
}
