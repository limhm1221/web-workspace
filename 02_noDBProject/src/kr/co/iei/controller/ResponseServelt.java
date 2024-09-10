package kr.co.iei.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ResponseServelt
 */
@WebServlet("/response")
public class ResponseServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResponseServelt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("서버호출하기22");
		//클라이언트에게 응답할 문서 타입 지정 및 문자셋 설정
		response.setContentType("text/html;charset=utf-8");
		//사용자가 볼 HTML문서를 작성하는 객체 생성
		PrintWriter out = response.getWriter();
		//HTML 문서 작성
		out.println("<!DOCTYPE html>");
		out.println("<html><head><title>응답페이지</title>/head>");
		out.println("<body>");
		out.println("<h1>서블릿 응답 페이지 입니다</h1>");
		out.println("<a href='/'>메인페이지로 돌아가기</a>");
		out.println("</body></html>");
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
