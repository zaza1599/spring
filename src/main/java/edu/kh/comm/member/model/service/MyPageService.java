package edu.kh.comm.member.model.service;

import java.util.Map;

import edu.kh.comm.member.model.vo.Member;

public interface MyPageService {

	/** 회원정보 수정 서비스
	 * @param paramMap
	 * @return
	 */
	int updateInfo(Map<String, Object> paramMap);
	

	/** 비밀번호 변경
	 * @param paramMap
	 * @return
	 */
	int changePw(Map<String, Object> paramMap);
	

	/** 회원 탈퇴
	 * @param loginMember
	 * @return result
	 */
	int secession(Member loginMember);

}
