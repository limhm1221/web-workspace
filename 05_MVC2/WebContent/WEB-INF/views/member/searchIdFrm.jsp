<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>아아디로 회원 조회</h1>
	<hr>
	<form action="/searchId" method="get">
		<label for="memberId">조회할 아이디 입력</label>
		<input type="text" name="memberId" id="memberId">
		<input type="submit" value="조회하기">
	</form>
</body>
</html>