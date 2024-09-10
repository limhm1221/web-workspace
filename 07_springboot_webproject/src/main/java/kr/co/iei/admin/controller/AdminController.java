package kr.co.iei.admin.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.service.MemberService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value="/allMember")
	public String allMember(Model model) {
		List list = memberService.selectAllMember();
		model.addAttribute("list", list);
		return "/admin/allMember";
	}
	@GetMapping(value="/changeLevel")
	public String changeLevel(Member m, Model model) {
		int result = memberService.changeLevel(m);
		if(result > 0) {
			return "redirect:/admin/allMember";
		}else {
			model.addAttribute("title", "등급변경실패");
			model.addAttribute("msg", "개발자에게 문의하세요");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/admin/allMember");	
			return "common/msg";
		}
	}
	@GetMapping(value="/checkedChangeLevel")
	public String checkedChangeLevel(String no, String level, Model model) {
		boolean result = memberService.checkedChangeLevel(no, level);
		if(result) {
			return "redirect:/admin/allMember";
		}else {
			model.addAttribute("title", "등급변경실패");
			model.addAttribute("msg", "개발자에게 문의하세요");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/admin/allMember");
		}
		return "common/msg";
	}
}
