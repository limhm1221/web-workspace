package kr.co.iei.etc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.service.MemberService;

@Controller
@RequestMapping(value="/ajax")
public class AjaxController {
	@Autowired
	private MemberService  memberService;
	 
	@GetMapping(value="/main")
	public String main() {
		return "etc/ajax";
	}
	
	//비동기요청은 HTML 페이지를 되돌려주는게 목적이 아님
	//비동기요청은 HTML이 아니라 리턴하는 데이터 자체를 되돌려주는게 목적
	//Spring controller에서 문자열을 그냥 리턴하면 ViewResolver가 관여해서 html페이지를 찾아가게 설정
	//-> ViewResolver에게 관여하지말고 문자열 그대로 되돌려주라는 설정
	@ResponseBody
	@GetMapping(value="/ajax1")
	public String ajax1() {
		System.out.println("서버 호출 완료");
		return "hi";
	}
	
	@ResponseBody
	@GetMapping(value="/ajax2")
	public String ajax2(String input1) {
		System.out.println("비동기 요청으로 보내온 데이터 " +input1);
		return "hi2";
	}
	
	@ResponseBody
	@PostMapping(value="/ajax3")
	//비동기 통신은 되돌려주고 싶은 데이터의 형태로 리턴
	public int ajax3(int num1, int num2) {
		int result = num1 + num2;
		System.out.println("result : " + result);
		
		return result;		
	}
	
	@ResponseBody
	@PostMapping(value="/ajax4")
	public String ajax4(String memberId){
		Member m = memberService.selectOneMember(memberId);
		if(m != null) {
			//Member객체를 그냥 script쪽으로 전달하면 해석이 불가능 (-> Java의 객체 형태이므로)
			//두 언어사이에 데이터 교환을 표준을 지정 -> JSON
			//Member타입을 JSON형태로 변환해서 되돌려주는 작업
			//Gson을 이용해서 Member 내부의 데이터를 JSON형태를 변환
			JsonObject obj = new JsonObject(); //HashMap<String, Object>
			//회원번호, 아이디, 전화번호, 주소
			obj.addProperty("memberId", m.getMemberId());
			obj.addProperty("memberNo", m.getMemberNo());
			obj.addProperty("memberName", m.getMemberName());
			obj.addProperty("memberPhone", m.getMemberPhone());
			obj.addProperty("memberAddr", m.getMemberAddr());
			System.out.println(obj.toString());
			//return obj.toString();
			
			
			Gson gson = new Gson();
			return gson.toJson(m); //toJson(object) :  매개변수로 전달한 객체를 json형태로 변환해서 리턴하는 함수 
			
		}else {
			return null;			
		}
	}
	@ResponseBody
	@GetMapping(value="/ajax5")
	public String ajax5() {
		List<Member> list = memberService.selectAllMember();
		//자바의 리스트 / 배열데이터를 자바스크립트 배열형태로 변환
		JsonArray resultArray = new JsonArray();//JSON을 여러개 저장하는 배열
		for(Member m : list) {
			JsonObject obj = new JsonObject();
			obj.addProperty("memberId", m.getMemberId());
			obj.addProperty("memberNo", m.getMemberNo());
			obj.addProperty("memberName", m.getMemberName());
			obj.addProperty("memberPhone", m.getMemberPhone());
			obj.addProperty("memberAddr", m.getMemberAddr());
			resultArray.add(obj);
		}
		System.out.println(resultArray.toString());
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	@ResponseBody
	@GetMapping(value="/ajax6")
	public Member ajax6(String memberId){
		Member m = memberService.selectOneMember(memberId);		
		//되돌려주고싶은 자바 자료를 그대로 리턴하면
		//비동기요청의 경우에만 jackson라이브러리가 json형태로 자동으로 변환해서 리턴
		return m;
	}
	
	@ResponseBody
	@GetMapping(value="/ajax7")
	public List<Member> ajax7() {
		List<Member> list = memberService.selectAllMember();
		
		return list;
	}
}
