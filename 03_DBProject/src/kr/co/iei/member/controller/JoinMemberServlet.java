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
 * Servlet implementation class joinMemberServlet
 */
@WebServlet("/joinMember")
public class JoinMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String memberId= request.getParameter("memberId");
		String memberPw= request.getParameter("memberPw");
		String memberName= request.getParameter("memberName");
		String memberPhone= request.getParameter("memberPhone");
		String memberAddr= request.getParameter("memberAddr");
		Member m = new Member();
		m.setMemberId(memberId);
		m.setMemberPw(memberPw);
		m.setMemberName(memberName);
		m.setMemberPhone(memberPhone);
		m.setMemberAddr(memberAddr	);
		//3. 비지니스 로직
		MemberService memberSercive = new MemberService();
		int result = memberSercive.insertMember(m);
		//4. 결과처리
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html><head><title>회원가입</title></head>");
		out.println("<body>");
		if(result > 0) {
			out.println("<h1>회원가입 성공</h1>");
		}else {
			out.println("<h1>회원가입 실패</h1>");
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
