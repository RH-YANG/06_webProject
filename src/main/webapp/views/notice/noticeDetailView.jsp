<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.br.notice.model.vo.Notice" %>
<%
	Notice n = (Notice)request.getAttribute("notice");
	//db로부터 조회한 글번호, 제목, 내용, 작성자, 작성일이 담겨있다.
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
        width:1000px;
        height:500px;
        margin:auto;
        margin-top: 50px;
        }
    </style>
</head>
<body>
	<%@include file="../common/menubar.jsp" %>

    <div class="outer" align="center">
        <br><h2>공지사항 상세보기</h2> <br>

        <table id="detail-area" border="1">
            <tr>
                <th width="70">제목</th>
                <td colspan="3" width="430"><%=n.getNoticeTitle()%></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td><%=n.getNoticeWriter()%></td>
                <th>작성일</th>
                <td><%=n.getCreateDate()%></td>
            </tr>            
            <tr>
                <th>내용</th>
                <td colspan="3">
                    <p style="height:150px"><%=n.getNoticeContent()%>
                    </p>
                </td>
            </tr>
        </table><br><br>
        <div>
            <a href="<%=contextPath%>/list.no" class="btn btn-secondary btn-sm">목록가기</a>
            <% if(loginUser != null && loginUser.getUserId().equals(n.getNoticeWriter())) { %>
            <!-- 현재 로그인한 사용자가 해당 글을 쓴 본인일 경우 -->
            <a href="<%=contextPath%>/updateForm.no?no=<%=n.getNoticeNo() %>" class="btn btn-warning btn-sm">수정하기</a>
            <a href="<%=contextPath%>/delete.no?no=<%=n.getNoticeNo()%>" class="btn btn-danger btn-sm">삭제하기</a>
            <% } %>
        </div>
    </div>
</body>
</html>