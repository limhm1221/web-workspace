<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원가입</h1>
	<hr>
	<form action="/join" method="post">
		<table border="1">
			<tr>
				<th>
					<lable for="memberId">아이디</lable>
				</th>
				<td>
				<input type="text"	name="memberId" id="memeberId">
				</td>
			</tr>
					<tr>
				<th>
					<lable for="memberPw">비밀번호</lable>
				</th>
				<td>
				<input type="password"	name="memberPw" id="memeberPw">
				</td>
			</tr>
					<tr>
				<th>
					<lable for="memberName">이름</lable>
				</th>
				<td>
				<input type="text"	name="memberName" id="memeberName">
				</td>
			</tr>
					<tr>
				<th>
					<lable for="memberPhone">전화번호</lable>
				</th>
				<td>
				<input type="text"	name="memberPhone" id="memeberPhone">
				</td>
			</tr>
					<tr>
				<th>
					<lable for="memberAddr">주소</lable>
				</th>
				<td>
				<input type="text"	name="memberAddr" id="memeberAddr">
				</td>
			</tr>
				<tr>
					<th colspan="2">
					<input type="submit" value="회원가입">
				</tr>
		</table>
	</form>
</body>
</html>