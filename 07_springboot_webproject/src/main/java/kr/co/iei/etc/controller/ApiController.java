package kr.co.iei.etc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.co.iei.etc.model.dto.BusanFood;
import kr.co.iei.util.EmailSender;

@Controller
@RequestMapping(value="/api")
public class ApiController {
	@Autowired
	private EmailSender emailSender;
	
	@GetMapping(value="/email")
	public String email() {
		return "etc/email";
	}
	
	
	@PostMapping(value="/sendMail")
	public String sendMail(String emailTitle, String receiver, String emailContent,Model model) {
		System.out.println("제목 : " +emailTitle);
		System.out.println("받는사람 : " +receiver);
		System.out.println("내용 :"+emailContent);
		
		emailSender.sendMail(emailTitle,receiver,emailContent);
		
		
		model.addAttribute("title","처리완료");
		model.addAttribute("msg","이메일을 확인하셔");
		model.addAttribute("icon","success");
		model.addAttribute("loc","/api/email");
		return "common/msg";
	}
	
	@ResponseBody
	@PostMapping(value="/sendCode")
	public String sendCode(String receiver) {
		//인증메일 제목 생성0
		String emailTitle = "zz Mook`s world zz 인증메일입니다.";
		//인증메일 인증코드 생성
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<6;i++) {
			//0 ~ 9 : r.nextInt(10); 
			//A ~ Z : r.nextInt(26) +65 ;
			//a ~ z : r.nextInt(26) +97 ;
			
			int flag = r.nextInt(3); //0,1,2 -> 숫자쓸지, 대문자쓸지, 소문자쓸지 결정
			if(flag == 0) {
				int randomCode = r.nextInt(10);
				sb.append(randomCode);
			}else if(flag == 1) {
				char randomCode = (char)(r.nextInt(26)+65);
				sb.append(randomCode);
			}else if(flag == 2 ) {
				char randomCode = (char)(r.nextInt(26)+97);
				sb.append(randomCode);
			}
		}
		String emailContent = "<h1>안녕하세요 .zz Mook`s world zz 입니다. </h1>"
							  +"<h3>인증번호는 [<span style='color:red;'>"
							  + sb.toString()
							  +"</span>]입니다.</h3>";
		emailSender.sendMail(emailTitle, receiver, emailContent);
		return sb.toString();
		
	}
	@GetMapping(value="/pay")
	public String pay(){
		return "etc/pay";
	}
	
	@GetMapping(value="/map")
	public String map() {
		return "etc/map";
	}


 @GetMapping(value="busan")
 public String busan() {
	 return "etc/busan";
 	}
 
 @ResponseBody
 @GetMapping(value="/busanPlace")
 public List busanPlace(String pageNo) {
	 String url="https://apis.data.go.kr/6260000/FoodService/getFoodKr";
	 //serviceKey 사용시 자바에서 요청할땐 decode된 키 사용
	String serviceKey="8N2YyKDuZXRkluMR8DCqPsnD92Xvt+s8FFMCyyrdD3KL8KpspdZWZwYrd5WEYve+CmnsYmDmYWn8QNT0LH/jTQ==";
	String numOfRows="10";
	String resultType="json";
	List list = new ArrayList<BusanFood>();
	//JSON방식
	try {
		String result = Jsoup.connect(url)
				.data("serviceKey",serviceKey)
				.data("pageNo",pageNo)
				.data("numOfRows",numOfRows)
				.data("resultType",resultType)
				.ignoreContentType(true)
				.get()
				.text();
			System.out.println(result);
			//결과로 받은 문자열을 json타입으로 변환 (JsonParser)
			JsonObject object = (JsonObject)JsonParser.parseString(result);
			JsonObject getFoodKr = object.get("getFoodKr").getAsJsonObject();
			JsonArray items = getFoodKr.get("item").getAsJsonArray();
			for(int i=0;i<items.size();i++) {
				JsonObject item = items.get(i).getAsJsonObject();
				String mainTitle = item.get("MAIN_TITLE").getAsString();
				String mainImg = item.get("MAIN_IMG_THUMB").getAsString();
				String address = item.get("ADDR1").getAsString();
				String tel = item.get("CNTCT_TEL").getAsString();
				String intro = item.get("ITEMCNTNTS").getAsString();
				double lat = item.get("LAT").getAsDouble();
				BusanFood bf = new BusanFood(mainTitle, mainImg, address, tel, intro);
				list.add(bf);
			}
			
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	/*
	try {
		Document document = Jsoup.connect(url)
					.data("serviceKey",serviceKey)
					.data("pageNo",pageNo)
					.data("numOfRows",numOfRows)
					.ignoreContentType(true)
					.get();
	Elements elements = document.select("item"); //결과로 받은 xml문서중 item태그만 선택(10개)
	for(int i=0;i<elements.size();i++) {
		Element item = elements.get(i);
		String mainTitle = item.select("MAIN_TITLE").text();
		String mainImg = item.select("MAIN_IMG_THUMB").text();
		String address = item.select("ADDR1").text();
		String tel = item.select("CNTCT_TEL").text();
		String intro = item.select("ITEMCNTNTS").text();
		BusanFood bf = new BusanFood(mainTitle, mainImg, address, tel, intro);
		list.add(bf);
		
	}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	*/				
	return list;
 }
}