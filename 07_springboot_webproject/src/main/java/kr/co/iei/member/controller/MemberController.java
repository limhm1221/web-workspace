package kr.co.iei.member.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.service.MemberService;

@Controller
@RequestMapping(value="/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value="/loginFrm")
	public String loginFrm() {
		return "member/login";
	}
	
	@PostMapping(value="/login")
	public String login(Member m, Model model,	HttpSession session) {
		//service를 이용해서 DB에 입력받은 아이디/패스워드가 일치하는 회원 조회
		//조회조건에 아이디가 포함되어있으므로 조회결과는 회원 1명 또는 0명 -> Member타입으로 결과 받기
		Member member = memberService.selectOneMember(m); 
		if(member == null) {
			//아이디 패스워드 일치하는 회원이 없으면 null
			// - > "아이디 또는 패스워드를 확인하세요" 메세지 띄우고
			model.addAttribute("title", "로그인 실패!");
			model.addAttribute("msg","아이디 또는 비밀번호를 확인하세요");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/member/loginFrm");
			return "common/msg";
		}else {
			//아이디 패스워드 일치하는 회원이 있으면 member 데이터 들어있음
			if(member.getMemberLevel()==3) {
				//회원등급이 준회원이면 로그인 불가		
				//-> 준회원은 로그인 할 수 없습니다. 관리자에게 문의하세요 
				model.addAttribute("title", "로그인 권한없음!");
				model.addAttribute("msg","관리자에게 문의하세요.");
				model.addAttribute("icon","warning");
				model.addAttribute("loc","/");
				return "common/msg";
			}else {
			//회원등급이 정회원/ 관리자면 로그인 처리
				// -> 메인페이지
				//modele 데이터를 등록하면 해당 요청이 끝나면 등록한 데이터를 더이상 하용할 수 없음
				//model.addAttribute("member", member); 
				//데이터를 계속 저장해주기 위해서 request가 아니라 session이라는 공간에 데이터 저장
				//->session영역을 가져오는 방법 requset.getSession();
				//HttpSession session = request.getSession();
				//세션에 member라는 이름으로 로그인한 회원 정보를 저장
				session.setAttribute("member", member);
				return "redirect:/";			
			}
		}
	}
	
	@GetMapping(value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();//현재 세션 정보를 파기
		return "redirect:/";	
	}
	@GetMapping(value="/joinFrm")
	public String joinFrm() {
		return "member/joinFrm";
	}
	
	@PostMapping(value="/checkId")
	public String checkId(String checkId, Model model) {
		Member member = memberService.selectOneMember(checkId);
		if(member == null) {
			//입력받은 아이디가 없음 -> 사용 가능한 아이디
			model.addAttribute("result",0);
		}else {
			//입력받은 아이디 회원이 있음 -> 이미 사용중인 아이디
			model.addAttribute("result",1);
		}
		model.addAttribute("memberId", checkId);
		return "member/checkId";
	}
	@PostMapping(value="/join")
	public String join(Member m, Model model) {
		int result = memberService.insertMember(m);
		if(result > 0) {
			model.addAttribute("title","회원가입성공");
			model.addAttribute("msg","어서오세요");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/");
			return "common/msg";
		}else {
			return "redierct:/";
			
		}
	}
	
	//세션에 저장되어있는 정보로 컨트롤러에서 가져와서 회원정보를 그때 다시 조회해서 화면으로 주는 방식
	@GetMapping(value="/mypage1")
	public String mypage(@SessionAttribute Member member,Model model) {
		String memberId = member.getMemberId();
		Member m = memberService.selectOneMember(memberId);
		model.addAttribute("m",m);
		return "member/mypage1";
	}
	
	@PostMapping(value="/update1")
	public String update1(Member m) {
		int result = memberService.updateMember(m);
		if(result > 0) {
			return "redirect:/member/mypage1";
		}else {
			return "redirect:/";
		}
	}
	
	@GetMapping(value="/mypage2")
	public String mypage2() {
		//화면제작 시 세션에 저장되어있는 회원정보를 바로 사용 
		return "member/mypage2";
	}
	
	@PostMapping(value="/update2")
	public String update2(Member m,@SessionAttribute Member member) {
		int result = memberService.updateMember(m);
		if(result > 0) {
			//정보 변경에 성공한 경우 - > 세션에 데이터를 갱신 -> 최신화
			member.setMemberPw(m.getMemberPw());
			member.setMemberAddr(m.getMemberAddr());
			member.setMemberPhone(m.getMemberPhone());
			return "redirect:/member/mypage2";
		}else {
			return "redirect:/";
		}
	}
	
	@GetMapping(value="/delete")
	public String deleteMember(@SessionAttribute Member member,Model model) {
		int memberNo= member.getMemberNo();
		int result = memberService.deleteMember(memberNo);
		if(result > 0) {
			model.addAttribute("title","탈퇴완료");
			model.addAttribute("msg","만나서 반가웠고 다시는 보지말자");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/member/logout");
		}else {
			model.addAttribute("title","탈퇴실패");
			model.addAttribute("msg","처리 중 오류가 발생했습니다. 잠시후 다시 시도해주세요");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/member/mypage2");
		}
		return "common/msg";
	}
	
	@ResponseBody
	@GetMapping(value="/ajaxCheckId")
	public int ajaxCheckId(String memberId) {
		Member member = memberService.selectOneMember(memberId);		
		if(member == null) {
			return 0;
		}else {
			return 1;
		}
	}
	
	@RequestMapping(value="/loginMsg")
	public String loginMsg(Model model) {
		model.addAttribute("title","로그인확인");
		model.addAttribute("msg","로그인 후 이용이 가능합니다.");
		model.addAttribute("icon","info");
		model.addAttribute("loc","/member/loginFrm");
		return "common/msg";
		
	}
	
	@RequestMapping(value="/adminMsg")
	public String adminMsg(Model model) {
		model.addAttribute("title","관리자 페이지");
		model.addAttribute("msg","관리자만 접근이 가능합니다");
		model.addAttribute("icon","warning");
		model.addAttribute("loc","/member/loginFrm");
		return "common/msg";
		
	}
}
	