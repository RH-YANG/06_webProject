<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.br.member.model.vo.Member" %>
<%
	String contextPath = request.getContextPath();
	// "/web" 기록됨
	

	//session은 jsp 내장객체여서 아래와 같이 선언 없이 바로 쓸 수 있음. 
	//반면 서블릿은 세션객체를 얻어온 다음에 써야함(request.get~ 메소드 사용)
	Member loginUser = (Member)session.getAttribute("loginUser");
	// 로그인 시도 전 menubar.jsp 로딩시 null이 담겨있음.
	// 로그인 성공 후 menubar.jsp 로딩 시 회원의 정보가 담긴 Member객체
	
	
	String alertMsg = (String)session.getAttribute("alertMsg");
	// menubar.jsp가 로딩될때마다 실행되나, alertMsg가 존재하지 않아서 null이 반환됨.
	// 회원가입과 같은 서비스 요청 후에는 session에 해당 키에 해당하는 밸류가 담겨있음.
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .login-area>* {float:right}
    .login-area a {
        text-decoration:none;
        color:black;
        font-size:12px;
    }
    .nav-area {background:black;}
    .menu {
        display:table-cell;
        width:150px;
        height:50px;
    }
    .menu a { /*테두리는 설정했다가 삭제*/
        text-decoration:none;
        color:white;
        font-size:20px;
        font-weight:bold;
        display:block;
        width:100%;
        height:100%;
        line-height:50px;
    }
    .menu a:hover {background:darkgrey;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<%if(alertMsg != null) { %>
		<script>
			alert('<%=alertMsg%>');
		</script>
		<% session.removeAttribute("alertMsg"); %>
	<% } %>

    <h1 align="center">Welcome Yes-Programming World</h1>

    <div class="login-area">
    	<% if(loginUser == null) { %>
        <!-- case1. 로그인 전 -->
        <!-- /web이라 작성하면 안됨 바뀔 수 있어서 request.getContextPath()로 호출 -->
        <form action="<%=contextPath%>/login.me" method="post">
            <table>
                <tr>
                    <th>아이디</th>
                    <td><input type="text" name="userId" required></td>
                </tr>
            
                <tr>
                    <th>비밀번호</th>
                    <td><input type="password" name="userPwd" required></td> 
                </tr>
                <tr>
                    <th colspan="2">
                        <button type="submit">로그인</button>
                        <button type="button" onclick="enrollPage();">회원가입</button>
                        <!-- 기본타입이 submit이기 때문에 type="button"을 입력 -->
                    </th>
                </tr>
            </table>
            <script>
            	function enrollPage() {
            		//절대경로방식
            		//location.href = "<%=contextPath%>/views/member/memberEnrollForm.jsp";
            		//url에 웹 어플리케이션의 디렉토리 구조가 노출되면 보안에 취약
            		location.href = "<%=contextPath%>/enrollForm.me";
            		//단순 페이지 요청에 있어서도 Servlet을 만들어 처리하자
            		//포워딩 방식을 통해서 해당 페이지가 보여지게끔(이때 url에는 서블릿 매핑값만 남아있음)
            	}
            </script>
        </form>
		<% }else{ %>
        <!-- case2. 로그인 후 -->
        <div>
            <b><%= loginUser.getUserName()%>님</b>의 방문을 환영합니다 <br><br>
            <div align="center">
                <a href="<%=contextPath%>/myPage.me">마이페이지</a>
                <a href="<%=contextPath%>/logout.me">로그아웃</a>
            </div>
        </div>
        <% } %>

    </div>

        <br clear="both">
        <br>

        <div class="nav-area" align="center">
            <div class="menu"><a href="">HOME</a></div>
            <div class="menu"><a href="">공지사항</a></div>
            <div class="menu"><a href="">일반게시판</a></div>
            <div class="menu"><a href="">사진게시판</a></div>
        </div>

</body>
</html>