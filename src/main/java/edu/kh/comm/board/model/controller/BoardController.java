package edu.kh.comm.board.model.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.comm.board.model.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;
	
	
	
	
	// 게시글 목록 조회
	
	// @PathVariable("value") : URL 경로에 포함되어있는 값을 변수로 사용할 수 있게 하는 역할
	// -> 자동으로 request scope에 등록 됨 -> jsp${value} EL 작성 가능
	
	@GetMapping("/list/{boardCode}")
	public String boardList( @PathVariable("boardCode") int boardCode,
							@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
							Model model) {
		
		// 게시글 목록 조회 서비스 호출
		// 1) 게시판 이름 조회
		// 2) 페이지네이션 객체 생성
		// 3) 게시글 목록 조회
		
		
		Map<String, Object> map = null;
		
		map = service.selectBoardList(cp, boardCode);
		
		//map.put("boardCode", boardCode);
		
		model.addAttribute("map", map);
		
		return "board/boardList";
		
	}
}
