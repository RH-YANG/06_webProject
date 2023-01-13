package com.br.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.br.board.model.service.BoardService;
import com.br.board.model.vo.Attachment;
import com.br.board.model.vo.Board;

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
		int boardNo = Integer.parseInt(request.getParameter("no"));
		//서비스 객체 3군데서 필요하니 미리 정의
		BoardService bService = new BoardService();
		
		// 1) 조회수 증가 요청
		int result = bService.increseCount(boardNo);
		
		if(result>0) {
		    // >> 조회수 증가 성공시 (유효한 글번호)
			// 2-1) Board 테이블로부터 게시글 정보 조회 요청
			Board b= bService.selectBoard(boardNo);
			// 2-2) Attachment 테이블로부터 첨부파일 정보조회 요청(없으면 null로 돌아오게)
			Attachment at = bService.selectAttachment(boardNo);
			//      두 객체 담아서 boardDetailView.jsp로 응담
			request.setAttribute("b", b);
			request.setAttribute("at", at); //null이 담길 수 있다.(검사해서 첨부파일유부판단)
			request.getRequestDispatcher("views/board/boardDetailView.jsp").forward(request, response);
		}else {
			// >> 조회수 증가 실패시 (유효하지 않은 글번호)
			//   에러페이지
			request.setAttribute("errorMsg", "상세조회 실패");
			request.getRequestDispatcher("view/common/errorPage.jsp").forward(request, response);
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
