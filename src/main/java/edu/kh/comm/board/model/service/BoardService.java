package edu.kh.comm.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.comm.board.model.vo.BoardType;

public interface BoardService {
	
	List<BoardType> selectBoardType();

	Map<String, Object> selectBoardList(int cp, int boardCode);

	
}
