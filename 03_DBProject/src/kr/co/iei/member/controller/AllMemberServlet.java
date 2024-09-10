package kr.co.iei.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.service.MemberService;

/**
 * Servlet implementation class AllMemberServlet
 */
@WebServlet("/allMember")
public class AllMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllMemberServlet() {
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
		//화면에서 전달한 데이터가 없으므로 생략
		//3. 비즈니스로직(이 서블릿이 처리할 백엔드작업 -> 전체회원조회)
		MemberService memberService = new MemberService();
		ArrayList<Member> list = memberService.selectAllMember();
		System.out.println("전체 회원 수 : "+list.size());
		//4. 결과처리
		//응답형식 및 문자셋 지정
		response.setContentType("text/html;charset=utf-8");
		//HTML생성 객체 생성
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html><head><title>전체 회원 조회</title><head>");
		out.println("<body>");
		out.println("<h1>전체 회원 정보</h1>");
		out.println("<hr>");
		out.println("<table border='1'>");
		out.println("<tr>");
		out.println("	<th>회원번호</th>");
		out.println("	<th>아이디</th>");
		out.println("	<th>비밀번호</th>");
		out.println("	<th>이름</th>");
		out.println("	<th>전화번호</th>");
		out.println("	<th>주소</th>");
		out.println("	<th>회원등급</th>");
		out.println("	<th>가입일</th>");
		out.println("</tr>");
		for (Member m : list) {
			out.println("<tr>");
			out.println("	<td>"+m.getMemberNo()+"</td>");
			out.println("	<td>"+m.getMemberId()+"</td>");
			out.println("	<td>"+m.getMemberPw()+"</td>");
			out.println("	<td>"+m.getMemberName()+"</td>");
			out.println("	<td>"+m.getMemberPhone()+"</td>");
			out.println("	<td>"+m.getMemberAddr()+"</td>");
			if(m.getMemberLevel() == 1) {
				out.println("<td>관리자</td>");
			}else if(m.getMemberLevel() == 2) {
				out.println("<td>정회원</td>");
			}else if(m.getMemberLevel() == 3) {
				out.println("<td>준회원</td>");
			}
			out.println("	<td>"+m.getEnrollDate()+"</td>");
			out.println("</tr>");
		}
		out.println("</table>");
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