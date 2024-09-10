<%@page import="kr.co.iei.member.model.dto.Member"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.co.iei.member.model.service.MemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    //자바코드를 치는 영역(스크립틀릿)
    //1.인코딩
    	request.setCharacterEncoding("utf-8");
    //2.값추출 (보내줄값이 없어서 생략)
    //3.비지니스 로직
  		MemberService memberService = new MemberService();
    	ArrayList<Member> list = memberService.selectAllMember();
    	System.out.println("조회된 회원 수 : "+list.size());
    //4. 결과처리
    	
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전체회원조회</title>
</head>
<body>
	<h1>전체회원정보</h1>
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
		</tr>
		<%for(int i=0;i<list.size();i++) {%>
			<%Member m = list.get(i); %>
			<tr>
				<%--
					자바변수의 데이터를 HTML에 표현하기 위해서는 표현식 태그를 사용
					<%= 자바변수%> / <%= 자바메소드() %>
				 --%>
				<td><%=m.getMemberNo()%></td>
				<td><%=m.getMemberId()%></td>
				<td><%=m.getMemberPw()%></td>
				<td><%=m.getMemberName()%></td>
				<td><%=m.getMemberPhone()%></td>
				<td><%=m.getMemberAddr()%></td>
				<%if(m.getMemberLevel() ==1) {%>
					<td>관리자</td>
				<%} else if(m.getMemberLevel()==2) {%>
					<td>정회원</td>
				<%}else if(m.getMemberLevel()==3) {%>
					<td>준회원</td>
					<%} %>
				
				<td><%=m.getMemberLevel()%></td>
				
				
				
				<td><%=m.getEnrollDate()%></td>		
			</tr>
		<%} %>
	</table>
</body>
</html>







