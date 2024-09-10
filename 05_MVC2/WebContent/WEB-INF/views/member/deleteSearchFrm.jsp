<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원탈퇴</h1>
	<hr>
	<form action="/deleteMember" method="get">
	<label for ="memberId">삭제할 아이디 입력 :</label>
		<input type="text" name="memberId" id="memberId">
		<input type="submit" value="삭제하기">
	</form>
</body>
</html>