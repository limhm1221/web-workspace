<%@page import="kr.co.iei.member.model.dto.Member"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% 
    	ArrayList<Member> list =(ArrayList<Member>)request.getAttribute("list");
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
			<th>아이디</th>
			<th>비밀번호</th>
			<th>이름</th>
			<th>전화번호</th>
			<th>주소</th>
			<th>회원등급</th>
			<th>가입일</th>
			<th>꺼져라</th>
		</tr>
		<%for(Member m : list) {%>
		<tr>
			<th><%=m.getMemberNo()%>
			<th><a href="/searchId?memberId=<%=m.getMemberId()%>"><%=m.getMemberId()%></a>
			<th><%=m.getMemberPw()%>
			<th><%=m.getMemberName()%>
			<th><%=m.getMemberPhone()%>
			<th><%=m.getMemberAddr()%>
			<%if(m.getMemberLevel()==1) {%>
			<th>관리자</th>
			<%}else if(m.getMemberLevel()==2) {%>
			<th>정회원</th>
			<%}else if(m.getMemberLevel()==3) {%>
			<th>준회원</th>
			<%} %>
			<th><%=m.getEnrollDate() %></th>
			<th><a href="/deleteMember?memberId=<%=m.getMemberId()%>">ㅋㅋ나가임마</a></th>
		</tr>
		<%} %>
	</table>
</body>
</html>