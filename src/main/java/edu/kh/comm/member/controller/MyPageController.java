package edu.kh.comm.member.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.comm.member.model.service.MyPageService;
import edu.kh.comm.member.model.vo.Member;

//	/member/myPAge/profile
//	/member/myPAge/info
//	/member/myPAge/changePw
//	/member/myPAge/secession


@Controller
@RequestMapping("/member/myPage")
@SessionAttributes({"loginMember"}) // session scope에서 loginMember를 얻어옴

public class MyPageController {
	
	private Logger logger = LoggerFactory.getLogger(MyPageController.class);
	
	@Autowired
	private MyPageService service;
	
		// 회원 정보 조회 이동
		@GetMapping("/info")
		public String info() {
			
			return "member/myPage-info";
			
		}
	
		// 비밀번호 변경 이동
		@GetMapping("/changePw")
		public String changePw() {
			
			return "member/myPage-changePw";
			
		}
		
		// 회원 탈퇴 이동
		@GetMapping("/secession")
		public String secession() {
			
			return "member/myPage-secession";
			
		}
		
		// 프로필 변경 이동
		@GetMapping("/profile")
		public String profile() {
			
			return "member/myPage-profile";
			
		}
		
		// 회원 정보 수정
		@PostMapping("/info")
		public String updateInfo( @ModelAttribute("loginMember") Member loginMember,
								@RequestParam Map<String, Object> paramMap,
								String[] updateAddress,
								RedirectAttributes ra
								//요청 시 전달 된 파라미터를 구분하지 않고 모두 Map에 담아 얻어옴
								
				) {
			
			// 필요한 값
			
			// - 닉네임
			// - 전화번호
			// - 주소
			// - 회원번호(Session -> 로그인한 회원 정보를 통해서 얻어옴)
			// 	--> @SessionAttributes, @ModelAttribute
			
			// @SessionAttributes
			// 1) Model에 세팅된 데이터의 key와
			// @SessionAttribute에 작성된 key가 같으면
			// Model 세팅된 데이터를 request -> session scope로 이동
			
			// 2) 기존에 session scope에 이동시킨 값을 얻어오는 역할
			// @ModelAttribute("loginMember") Member loginMember)
			// 					[session key]
			//		--> @SessionAttribute를 통해 session scope에 등록된 값을 얻어와
			//			오른쪽 작성된 Member loginMember 변수에 대입
			//		단, 클래스 위에 @SessionAttributes({"lognMember"})가 작성되어 있어야 가능
			//		
			// *** 매개변수를 이용해 session, 파라미터 데이터를 동시에 얻어올 때 문제점***
			
			// session에서 객체를 얻어오기 위해 매개변수에 작성한 상태에서
			//-> @ModelAttribute("loginMember") Member loginMember
			
			// 파라미터의 name 속성값이 매개변수에 작성된 객체(loginMember)의 필드와 일치하면
			// -> name="memberNickname"
			
			//session에서 얻어온 객체의 필드에 덮어쓰기가 된다!
			
			
			//[해결 방법] 파라미터의 name속성을 변경해 얻어오면 문제 해결!
			// (필드명이 겹쳐서 문제니까 겹치지 않게 하자)
			
			// 파라미터를 저장한 paramMap에 회원번호, 주소를 추가
			String memberAddress = String.join(",,", updateAddress);
			
			//주소가 입력 되지 않았을 때
			if(memberAddress.equals(",,,,"))	memberAddress = null;
			
			paramMap.put("memberNo", loginMember.getMemberNo());
			paramMap.put("memberAddress", memberAddress);
			
			// 회원 정보 수정 서비스 호출
			int result = service.updateInfo(paramMap);
			
			String message = null;
			
			if(result > 0) {
				message = "회원 정보가 수정되었습니다 ^^.";
				
				// DB - Session의 회원 정보 동기화
				loginMember.setMemberNickname(	(String)paramMap.get("updateNickname") );
				loginMember.setMemberTel(	(String)paramMap.get("updateTel") );
				loginMember.setMemberAddress(	(String)paramMap.get("memberAddress") );
				
				
			}else {
				message = "회원 정보 수정 실패..ㅠㅠ";
			}
			
			ra.addFlashAttribute("message", message);
			
			return "redirect:info";
			
			
			
		}
		
		@PostMapping("/changePw")
		public String changePw(@ModelAttribute("loginMember")  Member loginMember,
								@RequestParam Map<String, Object> paramMap,
								// paramMap엔 myPage-changePw의 값들이 들어있음
								RedirectAttributes ra
				) {
			
			// 로그인된 회원의 번호를 paramMap에 추가
			paramMap.put("memberNo", loginMember.getMemberNo());
			
			logger.debug("paramMap : {}", paramMap);
			
			// 비밀번호 변경 서비스 호출
			int result = service.changePw(paramMap);
			
			logger.debug("result : {}", result);
			
			String message = null;
			String path = null;
			
			
			if(result > 0) {
				message = "비밀번호가 수정되었습니다 ^^.";
				path = "info";
				
			} else {
				message = "비밀번호 수정 실패...ㅠㅠ";
				path = "changePw";
			}
			
			ra.addFlashAttribute("message", message);
			
			return "redirect:" + path;
		}
		
		// 회원 탈퇴(세션, 쿠키 무효화)
		@PostMapping("/secession")
		public String secession(@ModelAttribute("loginMember")  Member loginMember,
								SessionStatus status,
								HttpServletRequest req,
								HttpServletResponse resp,
								RedirectAttributes ra) {
			// 회원 탈퇴 서비스 호출
			int result = service.secession(loginMember);
			
			
			String message = null;
			String path = null;
			
			if(result > 0) {
				message = "탈퇴 되었습니다.";
				path = "/";
				
				// 세션 없애기
				status.setComplete();
				
				// 쿠키 없애기
				Cookie cookie = new Cookie("saveId", "");
				cookie.setMaxAge(0);
				cookie.setPath(req.getContextPath());
				resp.addCookie(cookie);
				
				
				
			} else {
				message = "현재 비밀번호가 일치하지 않아 탈퇴되지 않았습니다.";
						path = "secession";
			}
			
			ra.addFlashAttribute("message", message);
			
			return "redirect:" + path ;
			
		}
		
		
		// 프로필 수정
		@PostMapping("/profile")
		public String updateProfile(@ModelAttribute("loginMember")  Member loginMember,
									@RequestParam("uploadImage") MultipartFile uploadImage,/* 업로드 파일 */
									@RequestParam Map<String, Object> map, /* delete만 담겨져있음 */
									HttpServletRequest req,
									RedirectAttributes ra) {
			
			// 경로 작성하기
			
			// 1) 웹 접근 경로 ( /comm/resources/images/memberProfile/)
			String webPath = "/resources/images/memberProfile/";
			
			// 2) 서버 저장 폴더 경로 ( C:\workspace\7_Framework\comm\src\main\webapp\resources\images\memberProfile )
			String folderPath = req.getSession().getServletContext().getRealPath(webPath);
			
			
			// map에 경로 2개, 업로드이미지, delete, 회원번호 담기
			map.put("webPath", webPath);
			map.put("folderPath", folderPath);
			map.put("uploadImage", uploadImage);
			map.put("memberNo", loginMember.getMemberNo() );
			
			int result = service.updateProfile(map);
			
			
			
			
			
			
			
			return "";
		}
		
		
}

