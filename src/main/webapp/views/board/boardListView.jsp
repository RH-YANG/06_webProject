<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.br.common.model.vo.PageInfo, java.util.ArrayList, com.br.board.model.vo.Board " %>
<%
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer{
        background-color: black;
        color:white;
        width:1000px;
        height:550px;
        margin: auto;
        margin-top: 50px;
    }
    .list-area{
        border: 1px solid white;
        text-align: center;
    }
    .list-area>tbody>tr:hover {
    	background-color:gray;
    	cursor:pointer;
    }
</style>
</head>
<body>
    <%@ include file="../common/menubar.jsp"%>
    <div class="outer" align="center">
        <br><h2>일반게시판</h2><br>
        <br>
        <% if(loginUser != null) { %>
        <div align="right" style="width:700px">
        	<a href="<%=contextPath%>/enrollForm.bo" class="btn btn-secondary btn-sm">글작성</a>
        	<br><br>
        </div>
        <% } %>
        <table class="list-area">
            <thead>
                <tr>
                    <th width="70">글번호</th>
                    <th width="80">카테고리</th>
                    <th width="300">제목</th>
                    <th width="100">작성자</th>
                    <th width="50">조회수</th>
                    <th width="100">작성일</th>
                </tr>
            </thead>
            <tbody>
            <% if(list.isEmpty()) { %>
                <!-- case1. 게시글이 없을 경우 -->
                <tr>
                    <td colspan="6">조회된 게시글이 없습니다.</td>
                </tr>
            <%} else { %>
                <!-- case2. 게시글이 있을 경우 -->
                <% for(Board b : list) { %>
                <tr>
                    <td><%=b.getBoardNo() %></td>
                    <td><%=b.getCategory() %></td>
                    <td><%=b.getBoardTitle() %></td>
                    <td><%=b.getBoardWriter() %></td>
                    <td><%=b.getCount() %></td>
                    <td><%=b.getCreateDate() %></td>
                </tr>
                <%} %>
	        <%} %>
            </tbody>
        </table>
        <script>
        	$(function(){
        		$(".list-area>tbody>tr").click(function(){
        			location.href = '<%=contextPath%>/detail.bo?no=' + $(this).children().eq(0).text();
        		})
        	})
        
        </script>
        <br><br>
        <div class="paging-area">
			<% if(pi.getCurrentPage()>1){ %>
            <button onclick="location.href='<%=contextPath%>/list.bo?cpage=<%=pi.getCurrentPage()-1%>';">&lt;</button>
            <%} %>
			<% for(int p=pi.getStartPage(); p<=pi.getEndPage(); p++ ) {%>
            <button onclick="location.href='<%=contextPath%>/list.bo?cpage=<%=p%>';"><%=p %></button>
			<%} %>
			<% if(pi.getCurrentPage() != pi.getMaxPage()) {%>
            <button onclick="location.hred='<%=contextPath%>/list.bo?cpage=<%=pi.getCurrentPage()+1%>';">&gt;</button>
			<%} %>
        </div>
    </div>
</body>
</html>