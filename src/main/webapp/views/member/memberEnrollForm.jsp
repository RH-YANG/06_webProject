<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.outer {
		background-color: black;
		color:white;
		width:1000px;
		margin:auto;
		margin-top: 50px;
	}
	#enroll-form table {margin:auto;}
	#enroll-form input {margin: 5px;}

</style>
</head>
<body>
	
	<!-- 
	현재 파일이 속해있는 폴더는 member인데 menubar는 common에 있다.
	속해있는 폴더 빠져나가기 : ../
	 -->
	<%@ include file="../common/menubar.jsp" %>

	<div class="outer">
		<br>
		<h2 align="center">회원가입</h2>
		<!-- contextPath는 menubar.jsp에만 선언했지만 include로 포함시켰기 때문에 쓸 수 있다. -->
		<form action="<%=contextPath%>/insert.me" method="post" id="enroll-form">
			<table>
				<tr>
					<td>* 아이디 </td>
					<td><input type="text" name="userId" required></td>
					<td><button typr="button">중복확인</button></td>
				</tr>
				<tr>
					<td>* 비밀번호</td>
					<td><input type="password" name="userPwd" maxlength="15" required></td>
					<td></td>
				</tr>
				<tr>
					<td>* 비밀번호 확인</td>
					<td><input type="password" maxlength="15" required></td>
					<td></td>
				</tr>
				<tr>
					<td>* 이름</td>
					<!-- DB에서 이름이 15바이트이므로 최대길이 5-->
					<td><input type="text" name="userName" required maxlength="5"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;전화번호</td>
					<td><input type="text" name="phone" placeholder="-포함해서 입력"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;이메일</td>
					<td><input type="email" name="email"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;주소</td>
					<td><input type="text" name="address"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;관심분야</td>
					<td colspan="2">
						<input type="checkbox" name="interest" value="sports" id="sports">
						<label for="sports">운동</label> 
						<input type="checkbox" name="interest" value="climbing" id="climbing">
						<label for="climbing">등산</label> 
						<input type="checkbox" name="interest" value="fishing" id="fishing">
						<label for="fishing">낚시</label> <br>
						<input type="checkbox" name="interest" value="cooking" id="cooking">
						<label for="cooking">요리</label>
						<input type="checkbox" name="interest" value="game" id="game">
						<label for="game">게임</label>
						<input type="checkbox" name="interest" value="movie" id="movie">
						<label for="movie">영화</label>
					</td>
				</tr>
			</table> <br><br>
			<div align="center">
				<button type="submit">회원가입</button>
				<button type="reset">초기화</button>
			</div> <br><br>
			

		</form>


	</div>

	
</body>
</html>