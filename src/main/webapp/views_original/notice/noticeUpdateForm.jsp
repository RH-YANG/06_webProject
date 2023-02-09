<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.br.notice.model.vo.Notice" %>
<%
	Notice n = (Notice)request.getAttribute("n");
	//글번호, 제목, 내용, 작성자아아디, 작성일 담겨있다.
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
    #update-form table {border: 1px solid white;}
    #update-form input, #update-form textarea {
        width: 100%;
        box-sizing: border-box;
    }
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>
	
    <div class="outer" align="center">
        <br>
        <h2>공지사항 수정하기</h2>
        <br>
        <form action="<%=contextPath%>/update.no" method="post" id="update-form">
        	<input type="hidden" name="no" value="<%=n.getNoticeNo()%>">
            <table>
                <tr>
                    <th width="50">제목</th>
                    <td width="450"><input type="text" name="title" required value="<%=n.getNoticeTitle()%>"></td>
                </tr>
                <tr>
                    <th>내용</th>                   
                </tr>
                <tr>
                    <td colspan="2">
                        <textarea name="content" style="resize:none" id="" rows="10" required ><%=n.getNoticeContent()%></textarea>
                    </td>
                </tr>               
            </table>
            <br><br>
            <div>
                <button type="submit">수정하기</button>
                <button type="button" onclick="history.back();">뒤로가기</button>
            </div>
        </form>
        <br><br>
    </div>

</body>
</html>