package kr.co.iei.user.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.model.dto.UserDTO;
import kr.co.iei.user.model.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public String login(UserDTO u, HttpSession session) {
		UserDTO user = userService.selectOneUser(u);
		
		if(user != null) {
			session.setAttribute("user", user);
		}
		return "redirect:/";
	}
	
	@GetMapping(value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";		
	}
	
	@GetMapping(value="/allUser")
	public String allUser(Model model) {
		List list = userService.selectAllUser();
		model.addAttribute("list",list);
		return "user/userList";
	}
	
	@GetMapping(value="/joinFrm")
	public String joinFrm() {
		return "user/joinFrm";
	}
	@PostMapping(value="/join")
	public String join(UserDTO user) {
		int result = userService.insertUser(user);
		
		if(result>0) {
			return "redirect:/";			
		}else {
			return "redirect:/user/joinFrm";
		}
	}
	@GetMapping(value="/mypage")
	public String mypage() {
		return "user/mypage";
	}
	@PostMapping(value="/update")
	public String update(UserDTO u, @SessionAttribute UserDTO user) {
		int result = userService.userUpdate(u);
		if(result > 0) {
			user.setUserName(u.getUserName());
			return "redirect:/user/mypage";
		}else {
			return "redirect:/";
		}
	}
	@GetMapping(value="/delete")
	public String deleteUser(@SessionAttribute UserDTO user, HttpSession session) {
		int result = userService.deleteUser(user);
		
		if(result > 0) {
			session.invalidate();
			return "redirect:/";
		}else {
			return "redirect:/user/mypage";
		}
	}
	@ResponseBody
	@GetMapping(value="/checkId")
	public int checkID(String userId){
		UserDTO user = userService.checkId(userId);
		
		if(user != null) {
			//입력한 아이디로 회원조회 가능 -> 이미사용 가능
			return 1;
		}else{
			//입력한 아이디로 회원이 조회 불가능 - > 사용 가능한 아이디
			return 0;
		}
	}
	@ResponseBody
	@GetMapping(value="checkId2")
	public int checkId2(UserDTO u){
		UserDTO user = userService.checkId2(u);
		if(user != null) {
			//입력한 아이디로 회원조회 가능 -> 이미사용 가능
			return 1;
		}else{
			//입력한 아이디로 회원이 조회 불가능 - > 사용 가능한 아이디
			return 0;
		}
	}
	@GetMapping(value="/updatePwFrm")
	public String updatePwFrm() {
		return "user/updatePwFrm";
	}
	
	@ResponseBody
	@PostMapping(value="/oldPwCheck")
	public int oldPwCheck(UserDTO u) {
		UserDTO user = userService.selectOneUser(u);
		if(user != null) {
			return 1;
		}else {
			return 0;
		}
	}
	
	@PostMapping(value="/changePw")
	public String changePw(UserDTO u) {
		int result = userService.updatePw(u);
		
		return "redirect:/user/mypage";
	}
	
}


