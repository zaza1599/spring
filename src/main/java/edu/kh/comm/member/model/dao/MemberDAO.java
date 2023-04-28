package edu.kh.comm.member.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.comm.member.model.vo.Member;

@Repository // 영속성을 가지는 DB/파일과 연결되는 클래스임을 명시 + bean 등록
public class MemberDAO {
	
	
	/* DAO는 DB랑 연결하기 위한 Connection이 공통적으로 필요하다!
	 * -> 필드선언
	 * 
	 * +M Mybatis 영속성 프레임워크를 통해서 이용하려면 Connection을 이용해 만든 객체
	 * SqlSessionTemplate를 사용
	 * 
	 */
	
	@Autowired // root- context.xml에서 생성된 SqlSessionTemplate bean을 의존성 주입(DI)
	private SqlSessionTemplate sqlSession;
	
	private Logger logger =LoggerFactory.getLogger(MemberDAO.class);
	
	public Member login(Member inputMember) {
		
		// 1행 조회 (파라미터 없는 경우) 방법
		
		//int count = sqlSession.selectOne("namespace값.id값");
		//int count = sqlSession.selectOne("memberMapper.test1");
		//logger.debug(count + "");
		
		// 1행 조회(파라미터 있는 경우) 방법
		
		//String memberNickname = sqlSession.selectOne("memberMapper.test2", inputMember.getMemberEmail());
		//logger.debug(memberNickname);
		
		
		// 1행 조회(파라미터가 VO인 경우)
		//String memberTel = sqlSession.selectOne("memberMapper.test3", inputMember);
		
		//logger.debug(memberTel);
		
		
		
		// 1행 조회(파라미터 VO, 반환되는 결과 VO)
		Member loginMember = sqlSession.selectOne("memberMapper.login", inputMember);
		
		return loginMember;
		
		
		
		
		
		 
	}

	public int emailDupcheck(String memberEmail) {
		
		return sqlSession.selectOne("memberMapper.emailDupCheck", memberEmail);
	}

	public int nicknameDupcheck(String memberNickname) {
		
		return sqlSession.selectOne("memberMapper.nicknameDupCheck", memberNickname);
	}
	
	public int signUp(Member inputMember) {
		
		// INSERT, UPDATE, DELETE 반환값 int 형으로 고정
		// -> mapper에서도 resultType 항상 _int 고정
		// -> resultType 생략 가능(묵시적으로 _int)
		return sqlSession.insert("memberMapper.signUp", inputMember);
		
		
		
	}

	public Member selectOne(String memberEmail) {
			
		return  sqlSession.selectOne("memberMapper.selectOne", memberEmail);
	}

	public List<Member> selectAll() {
		
		return sqlSession.selectList("memberMapper.selectAll");
	}

	

}

