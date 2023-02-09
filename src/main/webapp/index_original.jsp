<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<!-- 
		* 회원서비스
					| C(Insert) | R(Select) | U(Update)	| D(Delete)
		============================================================
			로그인    |			|	 O		|			|
		   회원가입    |	  O 	|			|	  		|
		   마이페이지  |			|    O		|     O     |
			회원탈퇴   |	        |    		|     O     |
		[아이디중복체크]|        	|     O		|			|
		
		* 공지사항서비스
		  공지사항 리스트 조회 (R) / 상세조회 (R)
		  공지사항 작성 (C) / 공지사항 수정 (U) / 공지사항 삭제 (U)
		  
		* 일반게시판서비스
		  게시판 리스트 조회 (R) - 페이징 조회 / 상세조회 (R)
		  게시판 작성 (C) - 첨부파일 업로드 / 게시판 수정 (U) / 게시판 삭제 (U)
		  [댓글리스트 조회 (R) / 댓글 작성 (C)]
		  
		* 사진게시판서비스
		  게시판 리스트 조회 - 썸네일 형식 / 상제조회
		  게시판 작성 - 다중 첨부파일 업로드 / 첨부파일 미리보기
	 -->
	 
	 <%@ include file="views/common/menubar.jsp" %>
	 
	 
</body>
</html>