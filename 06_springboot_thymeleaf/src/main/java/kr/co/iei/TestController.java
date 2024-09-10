package kr.co.iei;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TestController {
	
	@GetMapping(value="/request")
	public String request() {
		//3. 비지니스 로직
		System.out.println("요청 테스트");
		//4. 결과처리
		return "test/test1";
	}
	
	@GetMapping(value="/request2")
	public String request2(String str, int no) {
		//1. 인코딩-> SpringBoot는 기본적으로 uft-8인코딩을 수행하기때문에 작업하지 않아도 무관
		//2. 값추출 -> 데이터가 전송되는 키값을 변수명으로 매개변수를 선언하면 데이터가 들어있음
//		String str = request.getParameter("str");
//		int no = Integer.parseInt(request.getParameter("no"));
		//3. 비지니스로직
		System.out.println("str : "+str);
		System.out.println("no : "+no);
		//4. 결과처리
		return "test/test1";
	}
	
	@PostMapping(value="/plus")
	public String plus(int no1, int no2, Model model) {
		//Model : 화면에 사용할 데이터를 등록하기위한 객체 
		//->request.setAttribute();
		//3. 비지니스 로직
		int result = no1 + no2;
		//4. 결과처리
		model.addAttribute("no1",no1);
		model.addAttribute("no2",no2);
		model.addAttribute("result",result);
		
		return "test/plus";
	}
	
	@GetMapping(value="/thymeTest1")
	public String test1(Model model) {
		model.addAttribute("str","안녕");
		model.addAttribute("num1" , 100);
		model.addAttribute("num2" , 200);
		
		return "test/thyme1";	
	}
	@GetMapping(value="/thymeTest2")
    public String test2(Model model) {
        
        Student s1 = new Student("학생1", 20, "서울시 강남구");
        Student s2 = new Student("학생2", 25, "부산광역시");
        model.addAttribute("s1",s1);
        model.addAttribute("s2",s2);
        
        return "test/thyme2";
    }
	
	@GetMapping(value="/thymeTest3")
	public String test3(Model model) {
		//기본형 리스트
		List list1 = new ArrayList<Integer>();
		list1.add(100);
		list1.add(200);
		
		//객체 리스트
		List list2 = new ArrayList<Student>();
		Student s1 = new Student("학생1", 20, "서울시 영등포구");
		Student s2 = new Student("학생2", 30, "서울시 강남구");
		list2.add(s1);
		list2.add(s2);
		model.addAttribute("list1", list1);
		model.addAttribute("list2", list2);
		return "test/thyme3";
	}
	
	@GetMapping(value="/thymeTest4")
	public String test4(Model model) {
		model.addAttribute("data1",12);
		model.addAttribute("data2", "하하하");
		Student s = new Student("학생일름",100,"학생주소");
		model.addAttribute("s",s);
		return "test/thyme4";
	}
	
	@GetMapping(value="/thymeTest5")
	public String test5(Model model) {
		model.addAttribute("num",30);
		return "test/thyme5";
	}
	
	@GetMapping(value="/thymeTest6")
	public String test6(Model model) {
		List list1 = new ArrayList<Integer>();
		list1.add(100);
		list1.add(200);
		list1.add(300);
		
		List list2 = new ArrayList<Student>();
		Student s1 = new Student("학생1", 30, "서울시 종로구");
		Student s2 = new Student("학생2", 25, "서울시 영등포구");
		Student s3 = new Student("학생3", 20, "서울시 강남구");
		
		list2.add(s1);
		list2.add(s2);
		list2.add(s3);
		
		model.addAttribute("list1", list1);
		model.addAttribute("list2", list2);
		return "test/thyme6";
	}
}
