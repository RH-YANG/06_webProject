<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.br.board.model.vo.*, java.util.ArrayList" %>
<%
	ArrayList<Category> list = (ArrayList<Category>)request.getAttribute("list");
	Board b = (Board)request.getAttribute("b");
	Attachment at = (Attachment)request.getAttribute("at");
	
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
    #update-form table{border: 1px solid white;}
    #update-form input, #update-form textarea{
        width:100%;
        box-sizing:border-box;
    }

</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>

    <div class="outer" align="center">
        <br>
        <h2>일반게시판 수정하기</h2>
        <br>

        <form id="update-form" action="<%=contextPath%>/update.bo" method="post" enctype="multipart/form-data">
        	<input type="hidden" name="no" value="<%=b.getBoardNo()%>">
            <table>
                <tr>
                    <th width="70">카테고리</th>
                    <td width="500">
                        <select name="category">
                            <!-- db로부터 category테이블에 존재하는 값들 조회해와서 뿌려야함 -->
                            <% for(Category c : list) { %>
                            <option value="<%=c.getCategoryNo()%>"><%= c.getCategoryName() %></option>
                            <% } %>
                        </select>
                        <script>
                        	$(function(){
                        		$("#update-form option").each(function(){
                        			if($(this).text()=='<%=b.getCategory()%>') {
                        				$(this).attr("selected", true)
                        			}
                        		})
                        	})
                        </script>
                    </td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td><input type="text" name="title" required value="<%=b.getBoardTitle()%>"></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><textarea name="content" rows="10" style="resize:none" required "><%=b.getBoardContent() %></textarea></td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td>
                    <% if(at != null ) { %>
                    <!-- 기존의 첨부파일이 있었을 경우 -->
                    	<input type="hidden" name="originFileNo" value="<%=at.getFileNo()%>">
                    	<%=at.getOriginName()%>
                    <% } %>
                    <input type="file" name="upfile">
                    </td>
                </tr>
            </table>
            <br>
            <div>
                <button type="submit">수정하기</button>
                <button type="reset">취소하기</button>
            </div>
        </form>
    </div>

</body>
</html>