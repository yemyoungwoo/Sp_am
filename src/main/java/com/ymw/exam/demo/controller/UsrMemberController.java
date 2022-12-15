package com.ymw.exam.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymw.exam.demo.service.MemberService;
import com.ymw.exam.demo.util.Utility;
import com.ymw.exam.demo.vo.Member;
import com.ymw.exam.demo.vo.ResultData;


@Controller
public class UsrMemberController {
	
	private MemberService memberService;
	
	@Autowired
	public UsrMemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		if(Utility.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}
		if(Utility.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 입력해주세요");
		}
		if(Utility.empty(name)) {
			return ResultData.from("F-3", "이름을 입력해주세요");
		}
		if(Utility.empty(nickname)) {
			return ResultData.from("F-4", "닉네임을 입력해주세요");
		}
		if(Utility.empty(cellphoneNum)) {
			return ResultData.from("F-5", "전화번호를 입력해주세요");
		}
		if(Utility.empty(email)) {
			return ResultData.from("F-6", "이메일을 입력해주세요");
		}
		
		ResultData<Integer> doJoinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if(doJoinRd.isFail()) {
			return ResultData.from(doJoinRd.getResultCode(), doJoinRd.getMsg());
		}
		
		Member member = memberService.getMemberById((int) doJoinRd.getData1());
		
		return ResultData.from(doJoinRd.getResultCode(), doJoinRd.getMsg(), "member", member);
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin() {
		return "usr/member/login";
	}

	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public ResultData doLogin(HttpSession httpSession, String loginId, String loginPw) {
		
		if(httpSession.getAttribute("loginedMemberId") != null) {
			return ResultData.from("F-1", "이미 로그인 되어있습니다");
		}
		if(Utility.empty(loginId)) {
			return ResultData.from("F-2", "아이디를 입력해주세요");
		}
		if(Utility.empty(loginPw)) {
			return ResultData.from("F-3", "비밀번호를 입력해주세요");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member == null) {
			return ResultData.from("F-4", "존재하지 않는 아이디입니다");
		}
		
		if(member.getLoginPw().equals(loginPw) == false) {
			return ResultData.from("F-5", "비밀번호가 일치하지 않습니다");
		}
		
		httpSession.setAttribute("loginedMemberId", member.getId());
		
		return ResultData.from("S-1", Utility.f("%s님 환영합니다", member.getNickname()));
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public ResultData doLogout(HttpSession httpSession) {
		
		if(httpSession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-1", "로그아웃 상태입니다");
		}
		
		httpSession.removeAttribute("loginedMemberId");
		
		return ResultData.from("S-1", "로그아웃 되었습니다");
	}

}