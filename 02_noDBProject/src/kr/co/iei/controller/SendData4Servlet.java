package kr.co.iei.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SendData4Servlet
 */
@WebServlet("/sendData4")
public class SendData4Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendData4Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String str = request.getParameter("str");
		//정수
		//->클라이언트에서 보내온 정보는 무조건 문자열 -> 숫자로 사용하고 싶으면 wrapper 클래스를 이용해서 변환해서 사용
		int num =Integer.parseInt(request.getParameter("num")); 
		//라디오: 라디오 중 선택된 버튼의 value값이 넘어옴
		String gender = request.getParameter("gender");
		//checkbox : 동일한 name을 가지고 여러개 데이터가 전송될 수 있음
		//->전송되는 데이터가 여러개일 수 있음(한개당 문자열 하나가 필요-> 문자열 여러개 필요 ->String[])
		//->다른 전송데이터는 무조건 문자열1개지만 여러개이므로 리턴타입이 다름 -> 메소드를 다른걸 사용해야함
		String[] hobby = request.getParameterValues("hobby");
		//select : select태그의 name으로 선택한 option태그의 value가 전송
		String age = request.getParameter("age");
		//textarea:input과 거의 동일 (차이점은 문자열에 개행문자가 포함될 수 있음)
		String comment = request.getParameter("comment");
		//hidden : hidden타입은 화면에  노출만안되고 value에있는 값이 전송
		String hiddenData = request.getParameter("hiddenData");
		//readonly :수정이 불가능 할 뿐 value에 있는 값이 전송
		String input1 = request.getParameter("input1");
		//disabled : 데이터 자체가전송이 안됨 -> 전송되지않은 데이터를 추출하면 null 리턴
		String input2 = request.getParameter("input2");
		//div : input, select , textarea가 아닌 일반태그들은 데이터 전송을 못함 -> null 리턴
		String div = request.getParameter("div");
		
		System.out.println("str :"+str);
		System.out.println("num :"+num);
		System.out.println("gender :"+gender);
		for(int i=0;i<hobby.length;i++) {
			System.out.println("hobby : "+hobby[i]);
		}
		System.out.println("age :"+age);
		System.out.println("comment :"+comment);
		System.out.println("hiddenData :"+hiddenData);
		System.out.println("readonly :"+input1);
		System.out.println("disabled :"+input2);
		System.out.println("div :"+div);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
