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
	#mypage-form table {margin:auto;}
	#mypage-form input {margin: 5px;}

</style>
</head>
<body>
	<%@include file="../common/menubar.jsp" %>
	<%
		String userId = loginUser.getUserId();
		String userName = loginUser.getUserName();
		String phone = loginUser.getPhone()==null? "":loginUser.getPhone();
		String email = loginUser.getEmail()==null? "":loginUser.getEmail();
		String address = loginUser.getAddress()==null? "":loginUser.getAddress();
		String interest = loginUser.getInterest()==null? "":loginUser.getInterest();		
	%>

	<div class="outer">
		<br>
		<h2 align="center">마이페이지</h2>
		<form action="<%=contextPath%>/update.me" method="post" id="mypage-form">
			<table>
				<tr>
					<td>* 아이디 </td>
					<td><input type="text" name="userId" value="<%=userId%>" readonly></td>
					<td></td>
				</tr>
				<tr>
					<td>* 이름</td>
					<!-- DB에서 이름이 15바이트이므로 최대길이 5-->
					<td><input type="text" name="userName" value="<%=userName%>"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;전화번호</td>
					<td><input type="text" name="phone" value="<%=phone%>"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;이메일</td>
					<td><input type="email" name="email" value="<%=email%>"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;주소</td>
					<td><input type="text" name="address" value="<%=address%>"></td>
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
			</table>
			
			<script>
				$(function(){
					const interest = "<%=interest%>";
					//현재 로그인한 회원의 관심분야들 자바스크립트 변수에 옮겨담기
					// "sports,climbing,game" 혹은 "" 로 담겨있을 것
					
					//순차적으로 접근하면서 함수 적용하기
					$('input[type=checkbox]').each(function(){
						// $(this) : 순차적으로 접근한 체크박스요소
						// $(this).val() : 해당요소의 value값(sports, climbing..)
						if(interest.search($(this).val()) != -1) {
							$(this).attr("checked",true);
						}
					})
				})
			</script> 
			
			<br><br>
			<div align="center">
				<button type="submit" class="btn btn-secondary btn-sm">정보변경</button>
				<button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#updatePwdModal">비밀번호변경</button>
                <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#deleteModal">회원탈퇴</button>
			</div> <br><br>
			
		</form>


	</div>    
	
	<!-- 비밀번호 변경용 모달 div  -->
	<div class="modal" id="deleteModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">회원탈퇴</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body" align="center">
	      <b>탈퇴 후 복구가 불가능합니다. <br> 정말로 탈퇴하시겠습니까? <br><br></b>
	        <form action="<%=contextPath%>/delete.me" method="post">
	        	<input type="hidden" name="userId" value="<%=userId%>"> 
	        	비밀번호 : <input type="password" name="userPwd" required><br><br>
	        	<button type="submit" class="btn btn-danger btn-sm">탈퇴하기</button>
	        </form>
	      </div>
	    </div>
	  </div>
	</div>
	

	<!-- 회원 비밀번호 변경용 모달 div  -->
	<div class="modal" id="updatePwdModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">비밀번호 변경</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body" align="center">
	        <form action="<%=contextPath%>/updatePwd.me" method="post">
	        	<input type="hidden" name="userId" value="<%=userId%>"> 
	        	<table>
	        		<tr>
	        			<td>현재 비밀번호</td>
	        			<td><input type="password" name="userPwd" required></td>
	        		</tr>
	        		<tr>
	        			<td>변경할 비밀번호</td>
	        			<td><input type="password" name="updatePwd" required></td>
	        		</tr>
	        		<tr>
	        			<td>변경할 비밀번호 확인</td>
	        			<td><input type="password" required></td>
	        		</tr>	        	
	        	</table><br>
	        	<button type="submit" class="btn btn-secondary btn-sm">비밀번호 변경</button>	        
	        </form>
	      </div>
	    </div>
	  </div>
	</div>
	
</body>
</html>