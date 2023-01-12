package com.br.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BoardDetailController
 */
@WebServlet("/detail.bo")
public class BoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청시 전달값 뽑기 (no키값으로 조회할 글번호)
		
		/*
		 * 1) 조회수 증가 요청
		 * >> 조회수 증가 성공시 (유효한 글번호)
		 * 2-1) Board 테이블로부터 게시글 정보 조회 요청
		 * 2-2) Attachment 테이블로부터 첨부파일 정보조회 요청(없으면 null로 돌아오게)
		 *      두 객체 담아서 boardDetailView.jsp로 응담
		 */
		
		// >> 조회수 증가 실패시 (유효하지 않은 글번호)
		//   에러페이지

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
