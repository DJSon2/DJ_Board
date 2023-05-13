package com.dongjin.board.board.controller;

import java.util.List;

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
import com.dongjin.board.board.dto.RelatedPostFrequencyDTO;
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

		if (currentPage != null && !"".equals(currentPage)) {
			pageNo = Integer.parseInt(currentPage);
		}

		String searchCondition = request.getParameter("searchCondition");
		String searchValue = request.getParameter("searchValue");

		int totalCount = boardService.selectTotalCount(searchCondition, searchValue);

		/* 한 페이지에 보여 줄 게시물 수 */
		int limit = 10; // 얘도 파라미터로 전달받아도 된다.

		/* 한 번에 보여질 페이징 버튼의 갯수 */
		int buttonAmount = 5;

		/* 페이징 처리를 위한 로직 호출 후 페이징 처리에 관한 정보를 담고 있는 인스턴스를 반환받는다. */
		SelectCriteria selectCriteria = null;
		if (searchValue != null && !"".equals(searchValue)) {
			selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition,
					searchValue);
		} else {
			selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);
		}

		List<PostDTO> postList1 = boardService.searchBoardList(selectCriteria);

		for (PostDTO post : postList) {
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
	public ModelAndView registPost(ModelAndView mv, PostDTO newPost, RelatedPostDTO newRelatedPost, RelatedPostFrequencyDTO newRelatedPostFrequency ,RedirectAttributes rttr) {
	    log.info("[BoardController] registPost Start ==================");

	    // 게시물 데이터 저장
	    boardService.registNewPost(newPost, newRelatedPost, newRelatedPostFrequency);

	    log.info("[BoardController] registPost END ==================");

	    return new ModelAndView("redirect:/");
	}

}
