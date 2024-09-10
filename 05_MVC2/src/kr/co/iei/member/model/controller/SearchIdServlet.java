package kr.co.iei.member.model.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
@WebServlet(asyncSupported = true, urlPatterns = { "/searchId" })
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
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		//3. 비지니스 로직
		MemberService memberService = new MemberService();	
		Member m = memberService.selectOneMember(memberId);
		//4. 결과 처리
		if(m== null) {
			//4-1. 결과 처리할 페이지 지정
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/member/searchFail.jsp");
			//4-2. 화면제작에 필요한 데이터 등록
			//4-3. 페이지 이동
			view.forward(request, response);
		}else {
			//4-1. 결과 처리할 페이지 지정
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/member/searchSuccess.jsp");
			//4-2. 화면제작에 필요한 데이터 등록
			request.setAttribute("member", m);
			//4-3. 페이지 이동
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
