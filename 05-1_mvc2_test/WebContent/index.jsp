<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>서버테스트</title>
</head>
<body>
	<h1>mvc2_test 임형묵 </h1>
	<hr>
	
	<%--
		전체회원 조회
		1.DB작업 수행 여부 : O
		2. query : select * from member_tbl order by 1
		3. 사용자에게 받을 데이터 없음 -> Controller 호출
		4. 요청 성공 했을때 / 실패 했을때 어떻게 처리할지 결정
	 --%>
	<h3><a href="/allMember">1. 전체회원 조회</a></h3>
	
	<%--
	 	회원가입 
	 	1. DB 작업 : O
	 	2. query ="insert into member_tbl values(member_seq.nextval,?,?,?)";
	 	3. 입력받을 데이터 : 닉네임/나이/성별 - > 입력양식으로 이동
	 	4. 성공했을때 실패했을 때 
	 		->회원 가입 성공한 경우 : allMember.jsp로 이동해서 전체회원 정보 중 추가된 회원정보 확인
	 		->회원 가입 실패한 경우 :> insertFail.jsp로 이동해서 회원가입에 실패했습니다. 출력
	  --%>
	   <h3><a href="/joinFrm">2. 회원가입</h3>
</body>
</html>