package edu.kh.comm.board.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.comm.board.model.vo.BoardDetail;
import edu.kh.comm.board.model.vo.BoardType;

public interface BoardService {
	
	/** 게시판 코드 목록 조회
	 * @return
	 */
	List<BoardType> selectBoardType();

	/** 게시글 목록 조회 서비스
	 * @param cp
	 * @param boardCode
	 * @return
	 */
	Map<String, Object> selectBoardList(int cp, int boardCode);

	/** 게시글 상세 조회 서비스
	 * @param boardNo
	 * @return
	 */
	BoardDetail selectBoardDetail(int boardNo);

	

	/** 조회수 증가 서비스
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);

	/** 게시글 삽입 + 이미지 삽입
	 * @param detail
	 * @param imageList
	 * @param webPath
	 * @param folderPath
	 * @return
	 */
	int insertBoard(BoardDetail detail, List<MultipartFile> imageList, String webPath, String folderPath) throws IOException;

	int updateBoard(BoardDetail detail, List<MultipartFile> imageList, String webPath, String folderPath, String deleteList) throws IOException;

	

	
}
