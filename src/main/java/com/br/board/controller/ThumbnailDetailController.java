package com.br.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.br.board.model.service.BoardService;
import com.br.board.model.vo.Attachment;
import com.br.board.model.vo.Board;

/**
 * Servlet implementation class ThumbnailDetailController
 */
@WebServlet("/detail.th")
public class ThumbnailDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo = Integer.parseInt(request.getParameter("no"));
		BoardService bService = new BoardService();
		//조회수 증가
		int result = bService.increseCount(boardNo);
		if(result>0) {
			//성공시 => 유효한 게시글임 => 상세페이지
			//게시글정보 (글번호, 제목, 내용, 작성자아이디, 작성일) => Board 테이블
			//일반게시글에 사용했던것 재사용
			Board b = bService.selectBoard(boardNo);
			//첨부파일들정보 (저장경로, 수정명) => Attachment 테이블
			ArrayList<Attachment> list = bService.selectAttachmentList(boardNo);
			
			request.setAttribute("b", b);
			request.setAttribute("list", list);
			request.getRequestDispatcher("views/board/thumbnailDetailView.jsp").forward(request, response);
		}else {
			//실패시 에러페이지
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
