package edu.kh.comm.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.comm.board.model.vo.Board;
import edu.kh.comm.board.model.vo.BoardDetail;
import edu.kh.comm.board.model.vo.BoardImage;
import edu.kh.comm.board.model.vo.BoardType;
import edu.kh.comm.board.model.vo.Pagination;

@Repository 
public class BoardDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/** 게시판 코드, 이름 조회
	 * @return boardTypeList
	 */
	public List<BoardType> selectBoardType() {
		return sqlSession.selectList("boardMapper.selectBoardType");
	}

	
	
	/** 특정 게시판의 전체 게시글 수 조회 DAO
	 * @param boardCode
	 * @return listCount
	 */
	public int getListCount(int boardCode) {
		
		return sqlSession.selectOne("boardMapper.getListCount", boardCode);
	}



	/** 게시글 목록 조회 DAO
	 * @param pagination
	 * @param boardCode
	 * @return boardList
	 */
	public List<Board> selectBoardList(Pagination pagination, int boardCode) {
		
		// RowBounds 객체(마이바티스)
		// - 전체 조회 결과에서
		// 몇개의 행을 건너 뛰고
		// 그 다음 몇 개의 행만 조회(limit)할 것인지 지정
		
		int offset = ( pagination.getCurrentPage() -1 ) * pagination.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		
		return sqlSession.selectList("boardMapper.selectBoardList", boardCode, rowBounds);
	}



	/** 게시글 상세 조회 DAO
	 * @param boardNo
	 * @return detail
	 */
	public BoardDetail selectBoardDetail(int boardNo) {
		
		
		
		return sqlSession.selectOne("boardMapper.selectBoardDetail", boardNo);
	}



//	public BoardDetail ReadCount(int boardNo) {
//		// TODO Auto-generated method stub
//		return sqlSession.selectOne("boardMapper.ReadCount", boardNo);
//	}



	/** 조회수 증가 DAO
	 * @param boardNo
	 * @return
	 */
	public int updateReadCount(int boardNo) {
		
		return sqlSession.update("boardMapper.updateReadCount", boardNo);
	}



	/** 게시글 삽입 DAO
	 * @param detail
	 * @return boardNo
	 */
	public int inserBoard(BoardDetail detail) {
		
		int result = sqlSession.insert("boardMapper.insertBoard", detail); // 0 or 1
		
		
		
		if( result > 0) result = detail.getBoardNo();
		
		
		
		return 0;
	}



	/** 게시글 이미지 삽입(리스트) DAO
	 * @param boardImageList
	 * @return result
	 */
	public int insertBoardImageList(List<BoardImage> boardImageList) {
		
		return sqlSession.insert("boardMapper.insertBoardImageList", boardImageList);
	}



	/** 게시글 수정 DAO
	 * @param detail
	 * @return result
	 */
	public int updateBoard(BoardDetail detail) {
		
		return sqlSession.update("boardMapper.updateBoard", detail);
	}



	/** 게시글 이미지 삭제
	 * @param map
	 * @return result
	 */
	public int deleteBoardImage(Map<String, Object> map) {
		
		return sqlSession.delete("boardMapper.deleteBoardImage", map);
	}



	/** 게시글 이미지 1개 수정
	 * @param img
	 * @return result
	 */
	public int updateBoardImage(BoardImage img) {
		
		return sqlSession.update("boardMapper.updateBoardImage", img);
	}



	/** 게시글 이미지 1개 삽입
	 * @param img
	 * @return result
	 */
	public int insertBoardImage(BoardImage img) {
		
		return sqlSession.insert("boardMapper.insertBoardImage", img);
	}



	
}
