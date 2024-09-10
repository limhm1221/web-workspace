package kr.co.iei;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.model.dto.UserDTO;
import kr.co.iei.user.model.service.UserService;

@Controller
@RequestMapping(value="/mybatis")
public class MybatisController {
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/main")
	public String main() {
		return "mybatis/main";
	}
	@GetMapping(value="/searchId")
	public String searchId(UserDTO u, Model model) {
		UserDTO user = userService.checkId2(u);
		model.addAttribute("user",user);
		return "mybatis/searchUser";
	}
	@GetMapping(value="/searchUser1")
	public String searchUser1(String type, String keyword, Model model) {
		List list = userService.searchUser1(type,keyword); 
		model.addAttribute("list",list);
		return "user/userList";
	}
	
	@GetMapping(value="/searchUser2")
	public String searchUser2(UserDTO u , Model model) {		
		List list = userService.searchUser2(u);
		model.addAttribute("list",list);
		return "user/userList";
		
	}
	@GetMapping(value="/userIdList")
	public String userIdList(Model model) {
		List list = userService.selectAllId();
		model.addAttribute("list", list);
		return "mybatis/idList";
	}
	@GetMapping(value="/searchUser3")
	public String searchUser3(String[] id,Model model) {
		List list = userService.searchUser3(id);
		model.addAttribute("list",list);
		return "user/userList";
	}
}
