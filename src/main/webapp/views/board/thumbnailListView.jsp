<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.br.board.model.vo.Board, java.util.ArrayList" %>
<%
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer {
        background-color: black;
        color: white;
        width: 1000px;
        height: 800px;
        margin: auto;
        margin-top: 50px;
    }
    .list-area {
        width: 760px;
        margin:auto;
    }
    .thumbnail {
        border: 1px solid white;
        width: 220px;
        /*옆으로 배치하기 위해 인라인, 가로세로 지정하기위해 블럭*/
        display: inline-block;
        margin:14px;
       
    }

</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
    <div class="outer">
        <br><h2 align="center">사진게시판</h2><br>
        
        <% if(loginUser != null) {%>
        <!-- 로그인한 회원만 보여지게 -->
        <div align="right" style="width:850px">
            <a href="<%=contextPath%>/enrollForm.th" class="btn btn-sm btn-secondary">글작성</a> <br>
        </div>
        <% } %>

        
        <!--왼쪽자리부터 하나씩 채워지게하려면 상위요소에 부여했던
        align=center를 지워야함, 원하는 각 요소마다 기재하기-->
        <div class="list-area">
        <% for(Board b : list) { %>
            <div class="thumbnail" align="center">
                <img src="<%=contextPath%>/<%=b.getTitleImg()%>" width="200" height="150">
                <p>
                    No.<%=b.getBoardNo()%> <%=b.getBoardTitle()%><br>
                    조회수 : <%=b.getCount()%>
                </p>
            </div>
		<% } %>         
        </div>
    </div>

</body>
</html>