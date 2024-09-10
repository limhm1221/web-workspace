<%@page import="kr.co.iei.member.model.service.MemberService"%>
<%@page import="kr.co.iei.member.model.dto.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
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
	m.setMemberAddr(memberAddr);
    
	MemberService memberSercive = new MemberService();
	int result = memberSercive.insertMember(m);
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<%if(result > 0) { %>
		<h1>회원가입성공</h1>
	<%}else { %>
		<h1>회원가입 실패</h1>
	<%}%>
	<a href='/'>메인으로</a>
</body>
</html>