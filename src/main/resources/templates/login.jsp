<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/bbs/loginProcess.jsp" method="post">
	<h1>로그인</h1><br>
	아이디 : <input type="text" id="webUserId" name="webUserId" placeholder="아이디"><br/>
	비밀번호: <input type="password" id="webUserPassword" name="webUserPassword" placeholder="비밀번호"><br/>
	
	<input type="submit" value="로그인">
	<input type="button" value=회원가입" onclick="location.href='<%=request.getContextPath()%>/bbs/registUser.jsp'"><br/>
</form>
</body>
</html>