<%@page import="kr.co.iei.member.model.dto.Member"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	Member member = (Member)request.getAttribute("member");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원정보</h1>
	<hr>
	<ul>
		<li>회원번호 : <%=member.getMemberNo() %></li>
		<li>아이디 : <%=member.getMemberId() %></li>
		<li>비밀번호 : <%=member.getMemberPw() %></li>
		<li>이름 : <%=member.getMemberName() %></li>
		<li>전화번호 : <%=member.getMemberPhone() %></li>
		<li>주소 : <%=member.getMemberAddr() %></li>
		<li>등급 : 
			<%if(member.getMemberLevel()==1) {%>
			<th>관리자</th>
			<%}else if(member.getMemberLevel()==2) {%>
			<th>정회원</th>
			<%}else if(member.getMemberLevel()==3) {%>
			<th>준회원</th>
			<%} %>
			</li>
		<li>가입일 : <%=member.getEnrollDate() %>
	</ul>
	<h3><a href="/updateSearchId?memberId=<%=member.getMemberId() %>">정보수정하기</a></h3>
</body>
</html>