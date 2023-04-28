package edu.kh.comm.member.model.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.comm.member.controller.MyPageController;
import edu.kh.comm.member.model.dao.MyPageDAO;
import edu.kh.comm.member.model.vo.Member;

@Service
public class MyPageServiceImpl implements MyPageService{
	
	@Autowired
	private MyPageDAO dao;
	
	private Logger logger = LoggerFactory.getLogger(MyPageController.class);
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	// 회원 정보 수정 서비스 구현
	@Override
	public int updateInfo(Map<String, Object> paramMap) {
		return dao.updateInfo(paramMap);
	}


	// 비밀번호 변경 서비스 구현
	@Override
	public int changePw(Map<String, Object> paramMap) {
		// 여기서 paramMap 디버그 하면 newPw는 평문 상태.
		// 1) DB에서 현재 회원의 비밀번호를 조회한다.
		String encPw = dao.selectEncPw( (int) paramMap.get("memberNo"));
		// 2) 입력된 현재 비밀번호(평문상태)와 
		// 조회된 비밀번호(암호화)를 비교(bcypt.matches()이용)
		if(bcrypt.matches( (String)paramMap.get("currentPw"), encPw)) {
			
			paramMap.put("newPw", bcrypt.encode((String)paramMap.get("newPw")));
			
			
			return dao.changePw(paramMap);
		} 
		// 3) 비교 결과가 일치하면
		// 새 비밀번호 암호화 해서 update 구문 수행
		
		
		return 0;
		
		
	}


	@Override
	public int secession(Member loginMember) {
		
		
		// 1) DB에서 현재 회원의 비밀번호를 조회
		String encPw = dao.selectEncPw(loginMember.getMemberNo() );
		
		if( bcrypt.matches(loginMember.getMemberPw(), encPw) ) {
			// 2) 비밀번호가 일치하면 회원 번호를 이용해 탈퇴 진행
			
			
		return dao.secession(loginMember.getMemberNo() );	
			
		}
		
		// 3) 비밀번호가 일치하지 않으면 0 반환
		
		
		return 0;
	}
	
	
}
