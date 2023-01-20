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
    <br>

    <div id="reply-area">
        <table border="1">
            <thead>
                <tr>
                    <th>댓글작성</th>
                    <% if(loginUser != null) { %>
                    <!-- 로그인이 되어있을 경우 -->
                    <td><textarea cols="50" rows="3" style="resize:none;"></textarea></td>
                    <td><button onclick="insertReply();">댓글등록</button></td> 
					<% }else{ %>
                    <!-- 로그인이 되어있지 않은 경우 -->
                    <td><textarea cols="50" rows="3" style="resize:none;">로그인후 이용이 가능한 서비스입니다.</textarea></td>
                    <td><button disabled>댓글등록</button></td>
                    <% } %>
                </tr>
            </thead>
            <tbody>
				<!-- 댓글이 뿌려질 자리 -->
            </tbody>
        </table>
    </div>
    <br><br>
    <script>
    	// 페이지가 로드되면 selectReplyList 메소드 실행
    	$(function(){
    		selectReplyList();
    		
    		setInterval(selectReplyList, 1000);
    	})
    	
    	// ajax로 해당 게시글에 달린 댓글 목록 조회해오는 메소드
    	function selectReplyList(){
    		$.ajax({
    			url:"<%=contextPath%>/rlist.bo",
    			//숫자는 따옴표로 묶지 않아도 된다.
    			data:{no:<%=b.getBoardNo()%>},
    			success:function(list){
    				//console.log(list); 댓글 형태 확인
    				let value = "";
					if(list.length == 0) {
						//댓글이 없을 경우
						value += "<tr>"
						       +      "<td colspan='3'>조회된 댓글이 없습니다.</td>"
						       + "</tr>"
					}else{
						//댓글이 있을 경우
						for(let i=0; i<list.length; i++){
							value += "<tr>"       //필드명이 키값이다. 
							      +       "<td>" + list[i].replyWriter + "</td>"
							      +       "<td>" + list[i].replyContent + "</td>"
							      +       "<td>" + list[i].createDate + "</td>"
							      +  "</tr>";
						} 
					}
					$('#reply-area tbody').html(value);
    			},error:function(){
    				console.log('댓글목록 조회용 ajax통신 실패');
    			}
    		})
    	}
    	
    	//ajax로 댓글 작성용
    	function insertReply(){
    		$.ajax({
    			url:"<%=contextPath%>/rinsert.bo",
    			data:{
    				content:$('#reply-area textarea').val(),
    				no:<%=b.getBoardNo()%>
    			},
    			type:"post",
    			success:function(result){
    				if(result>0) { //댓글 등록 성공
    					//댓글란에 적힌것은 초기화 시킨다.
    					$("#reply-area textarea").val("");
    					//db로부터 댓글들을 재조회 한다.
						selectReplyList();    					
    				}else {
    					alert("댓글등록 실패");
    				}
    			}, error:function(){
    				console.log("댓글작성용 ajax 통신 실패")
    			}
    		})
    	}
    	
    	
    
    </script>

</body>
</html>