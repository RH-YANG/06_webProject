<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.br.board.model.vo.*" %>
<%
	Board b = (Board)request.getAttribute("b");
	Attachment at = (Attachment)request.getAttribute("at");
%>
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
    .detail-area {
    	border : 1px solid white;
    	text-align : center;
    }
    
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	<div class="outer" align="center">
    <br><h2>일반게시판 상세보기</h2><br>

    <table border="1" id="detail-area">
        <tr>
            <th width="70">카테고리</th>
            <td width="70"><%=b.getCategory()%></td>
            <th width="70">제목</th>
            <td width="350"><%=b.getBoardTitle()%></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><%= b.getBoardWriter()%></td>
            <th>작성일</th>
            <td><%= b.getCreateDate()%></td>
        </tr>        
        <tr>
            <th>내용</th>
            <td colspan="3" height="200">
                <%=b.getBoardContent()%>
            </td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td colspan="3">
				<% if( at == null ){ %>
                <!-- case1. 첨부파일이 없을경우-->
                첨부파일이 없습니다
     			<% } else { %>
                <!-- case2. 첨부파일이 있을 경우-->
                <a download="다운받아질 이름.jpg" href="<%=contextPath%>/<%=at.getFilePath() + at.getChangeName()%>"><%= at.getOriginName() %></a>
				<% } %>
            </td>
        </tr>        
    </table>
    <br>
    <div>
        <a href="<%=contextPath%>/list.bo?cpage=1" class="btn btn-secondary btn-sm">목록가기</a>
        <% if( loginUser != null && loginUser.getUserId().equals(b.getBoardWriter())) { %>
        <!-- 로그인한 회원이 게시글을 쓴 회원일경우 -->
        <a href="<%=contextPath%>/updateForm.bo?no=<%=b.getBoardNo()%>" class="btn btn-warning btn-sm">수정하기</a>
        <a href="" class="btn btn-danger btn-sm">삭제하기</a>
        <% } %>
    </div>
    <br><br>
    </div>

</body>
</html>