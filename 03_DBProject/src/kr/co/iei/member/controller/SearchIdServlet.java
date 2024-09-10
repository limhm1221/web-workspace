package kr.co.iei.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.service.MemberService;

/**
 * Servlet implementation class SearchIdServlet
 */
@WebServlet("/searchId")
public class SearchIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 인코딩
		request.setCharacterEncoding("utf-8");
		//2. 값추출
		String memberId = request.getParameter("memberId");
		//3. 비즈니스로직
		MemberService memberService = new MemberService();
		Member m = memberService.selectOneMember(memberId);
		//4. 결과처리
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html><head><title>아이디로 회원 조회</title></head>");
		out.println("<body>");
		if(m == null) {
			out.println("<h1>회원 정보를 조회할 수 없습니다.</h1>");
		}else {
			out.println("<h1>회원 정보</h1>");
			out.println("<hr>");
			out.println("<ul>");
			out.println("	<li>회원번호 : "+m.getMemberNo()+"</li>");
			out.println("	<li>회원아이디  : "+m.getMemberId()+"</li>");
			out.println("	<li>비밀번호 : "+m.getMemberPw()+"</li>");
			out.println("	<li>회원이름 : "+m.getMemberName()+"</li>");
			out.println("	<li>전화번호 : "+m.getMemberPhone()+"</li>");
			out.println("	<li>주소 : "+m.getMemberAddr()+"</li>");
			if(m.getMemberLevel() == 1) {
				out.println("<li>관리자</li>");
			}else if(m.getMemberLevel() == 2) {
				out.println("<li>정회원</li>");
			}else if(m.getMemberLevel() == 3) {
				out.println("<li>준회원</li>");
			}
			out.println("	<li>가입일 : "+m.getEnrollDate()+"</li>");
			out.println("</ul>");
		}
		out.println("<a href='/'>메인으로</a>");
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