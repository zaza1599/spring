package edu.kh.comm.member.model.service;

import java.util.List;

import edu.kh.comm.member.model.vo.Member;

/*
 * Service Interface를 사용하는 이유
 * 
 * 1. 프로젝트에 규칙성을 부여하기 위해
 * 
 * 2. Spring AOP를 위해 필요
 * 
 * 3. 클래스간의 결합도를 약화 시키기 위해.
 */
/**
 * @author user2
 *
 */
public interface MemberService {

	// 모든 메서드가 추상 메서드 (묵시적으로 public abstract)
	// 모든 필드는 상수			(묵시적으로 public staic final)
	
	
	
	/** 로그인 서비스
	 * @param inputMember
	 * @return
	 */
	public abstract Member login(Member inputMember);

	
	
	
	
	/** 이메일 중복 체크
	 * @param memberEmail
	 * @return result
	 */
	public abstract int emailDupCheck(String memberEmail);

	
	public abstract int nicknameDupCheck(String memberNickname);





	/** 회원가입 서비스
	 * @param inputMember
	 * @return result
	 */
	public abstract int signUp(Member inputMember);





	/** 회원조회 1명
	 * @param memberEmail
	 * @return
	 */
	public abstract Member selectOne(String memberEmail);





	/** 회원조회 모두
	 * @return
	 */
	public abstract List<Member> selectAll();




	

}






