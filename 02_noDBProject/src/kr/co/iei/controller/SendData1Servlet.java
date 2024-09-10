package kr.co.iei.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SendData1Servlet
 */
@WebServlet("/sendData1")
public class SendData1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendData1Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청시 보낸 데이터에 한글이 포함되어있을 수  있으므로 인코딩(한글이 없어도 무조건 인코딩)
		//한그이 없어도 인코딩을 무조건 수행 -> 인코딩한다고 영어/숫자는 문제가 발생하지 않으니까 
		request.setCharacterEncoding("utf-8");
		//요청 된 정보 중 전달된 데이터를 추출 
		String name = request.getParameter("name");
		String test = request.getParameter("test");
		System.out.println("name : "+name);
		System.out.println("test : "+test);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
