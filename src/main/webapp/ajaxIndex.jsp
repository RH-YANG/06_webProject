<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>

	<h1>AJAX개요</h1>
	Asychronous JavaScript and XML의 약자 / 
	비동기식 요청기술에 해당됨 / 
	서버로부터 데이터를 가져와 전체페이지를 새로 고치지 않고 일부요소만 로드할 수 있게 하는 기법
	
	<pre>
	*jQuery방식으로 AJAX 통신
	- 객체 안에 요청처리 할 내용들 담아서 보냄
	
	$.ajax({
		속성명:속성값,
		속성명:속성값,
		...
	});
	
	* 주요속성과 입력할 속성값
	- url : 요청할 url주소(필수)
	- type | method : 요청전송방식 (get/post)
	- data : 요청시 전달할 값
	- success : ajax 통신에 성공했을 경우 실행할 함수 정의
	- error : ajax 통신에 실패했을 경우 실행할 함수 정의
	- complete : 성공/실패 여부와 상관없이 무조건 실행할 함수
	
	
	</pre>
	
	
	<h2>기존에 했던 동기식 방식으로 요청시 전달해보기</h2>
	
	<h3>1) form submit으로 url 요청</h3>
	<form action="/web/test1.do">
		<input type="text" name="input">
		<button type="submit">전송</button>
	</form>
	
	<h3>2) location.href으로 url 요청</h3>
	<input type="text" id="input">
	<button onclick="test();">전송</button>
	<script>
		function test(){
			location.href = "/web/test1.do?input=" + document.getElementById("input").value;
		}
	</script>
	
		
	<h2>AJAX 이용해서 비동기식 방식으로 요청값 전달해보기</h2>
	<h3>1. 버튼 클릭시 서버에 요청 및 응답</h3>
	입력 : <input type="text" id="input1">
	<button onclick="test1();">전송</button><br>
	
	응답 - 
	<span id="output1">현재응답없음</span>
	
	<script>
		function test1(){
			$.ajax({
				url : "/web/test1.do",
				//넘길 데이터가 여러개일때는 객체로 키밸류 세트 넘긴다.(,로 구분)
				data : {input:$("#input1").val()}, 
				//서블릿에서 보낸 데이터 받으려면 success의 매개변수로 받는다.
				success : function(result){
					console.log("ajax 통신 성공");
					$("#output1").text(result);
				}, 
				error : function(){
					console.log("ajax 통신 실패");
				}, 
				complete : function(){
					console.log("성공여부 상관없이 무조건 출력");
				}
			});
		}
	</script>
	
	
	
	
	
	
	<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

</body>
</html>