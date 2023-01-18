<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.br.board.model.vo.*, java.util.ArrayList" %>
<%
	Board b = (Board)request.getAttribute("b");
	ArrayList<Attachment> list = (ArrayList<Attachment>)request.getAttribute("list");
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
        margin-top: 50;
    }
    .detail-area {
        text-align: center;
    }

</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>

    <div class="outer" align="center">
        <br><h2>사진게시판 상세보기</h2><br>
        <table class="detail-area" border="1">
            <tr>
                <td width="70">제목</td>
                <td colspan="3" width="600"><%=b.getBoardTitle()%></td>
            </tr>
            <tr>
                <td>작성자</td>
                <td><%=b.getBoardWriter()%></td>
                <td>작성일</td>
                <td><%=b.getCreateDate()%></td>
            </tr>
            <tr>
                <td>내용</td>
                <td colspan="3" height="50"><%=b.getBoardContent()%></td>
            </tr>
            <tr>
                <td>대표사진</td>
                <td colspan="3">
                	<!-- list의 첫번째 객체가 대표이미지일것임 -->
                    <img src="<%=contextPath%>/<%= list.get(0).getFilePath() + list.get(0).getChangeName() %>" width="500" height="300">
                	<!-- contextPath가 webapp임. 여기서부터 파일위치까지 나타내기-->
                	<!-- getFilePath() == resourceslthumnail_upfiles/ -->
                </td>
            </tr>
            <tr>
                <td>상세사진</td>
                <td colspan="3">
                    <!-- 상세사진은 몇개일지 모름 -->
                    <!-- 향상된 for문은 0번부터 접근하므로 일반 for문 사용 -->
					<% for(int i=1; i<list.size(); i++) { %>
                    <img src="<%=contextPath%>/<%= list.get(i).getFilePath() + list.get(i).getChangeName() %>" width="200" height="150">
					<% } %>
                </td>
            </tr>
        </table>

        <a href="<%=contextPath%>/list.th">목록가기</a>

    </div>

</body>
</html>