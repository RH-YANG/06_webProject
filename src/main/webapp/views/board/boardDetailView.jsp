<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	<div class="outer" align="center">
    <br><h2>일반게시판 상세보기</h2><br>

    <table border="1" id="detail-area">
        <tr>
            <th width="70">카테고리</th>
            <td width="70">~운동~</td>
            <th width="70">제목</th>
            <td wudth="350">~제목자리~</td>
        </tr>
        <tr>
            <th>작성자</th>
            <td>~아이디~</td>
            <th>작성일</th>
            <td>~yyyy/mm/dd~</td>
        </tr>        
        <tr>
            <th>내용</th>
            <td colspan="3" height="200">
                ~내용자리~
            </td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td colspan="3">

                <!-- case1. 첨부파일이 없을경우-->
                첨부파일이 없습니다

                <!-- case2. 첨부파일이 있을 경우-->
                <a href="첨부파일의 저장경로, 첨부파일의 실제 저장된 파일명">~원본명~</a>

            </td>
        </tr>        
    </table>
    <br>
    <div>
        <a href="" class="btn btn-secondary btn-sm">목록가기</a>
        <!-- 로그인한 회원이 게시글을 쓴 회원일경우 -->
        <a href="" class="btn btn-warning btn-sm">수정하기</a>
        <a href="" class="btn btn-danger btn-sm">삭제하기</a>
    </div>
    <br><br>
    </div>

</body>
</html>