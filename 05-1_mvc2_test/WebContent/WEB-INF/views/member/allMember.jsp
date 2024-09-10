<%@page import="kr.co.iei.member.model.dto.Member"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	ArrayList<Member> list = (ArrayList<Member>)request.getAttribute("list");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>전체회원</h1>
	<hr>
	<table border="1">
		<tr>
			<th>회원번호</th>
			<th>회원닉네임</th>
			<th>나이</th>
			<th>성별</th>
		</tr>
			<%for(Member m : list) {%>
			<tr>
			<th><%=m.getUserNo() %>
			<th><%=m.getUserNickname() %>
			<th><%=m.getUserAge() %>
			<th><%=m.getUserGender() %>			
			<%} %>		
	</table>
</body>
</html>