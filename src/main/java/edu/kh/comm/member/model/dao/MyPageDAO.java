package edu.kh.comm.member.model.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.comm.member.controller.MyPageController;

/**
 * @author user2
 *
 */
@Repository
public class MyPageDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private Logger logger = LoggerFactory.getLogger(MyPageController.class);
	
	public int updateInfo(Map<String, Object> paramMap ) {
		
		return sqlSession.update("myPageMapper.updateInfo", paramMap);
	}



	public int changePw(Map<String, Object> paramMap) {
		
		
		
		return sqlSession.update("myPageMapper.changePw", paramMap);
	}



	/** 현재 로그인한 회원의 암호화된 비밀번호를 조회하는 DAO
	 * @param memberNo
	 * @return encPw
	 */
	public String selectEncPw(int memberNo) {
		
		return sqlSession.selectOne("myPageMapper.selectEncPw", memberNo);
	}



	/** 회원 탈퇴 DAO
	 * @param memberNo
	 * @return result
	 */
	public int secession(int memberNo) {
		
		return sqlSession.update("myPageMapper.secession", memberNo );
	}
}
