package com.br.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.br.board.model.service.BoardService;
import com.br.board.model.vo.Board;
import com.br.common.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/list.bo")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// -------- 페이징처리 --------
		int listCount;		// 현재 게시글 총 갯수
		int currentPage;    // 사용자가 요청한 페이지(== 현재페이지)
		int pageLimit;      // 페이지 하단에 보여질 페이징바의 페이지 최대갯수(몇개 단위씩 보여지게 할건지)
		int boardLimit;     // 한 페이지에 보여질 게시글의 최대갯수(몇개 단위씩 보여지게 할건지)
		// 위의 4개를 가지고 아래 3개의 값을 구할 것.
		int maxPage;	    // 가장 마지막 페이지 (총 페이지 수)
		int startPage;		// 사용자가 요청한 페이지 하단의 페이징바의 시작수
		int endPage;		// 사용자가 요청한 페이지 하단의 페이징바의 끝수
		
		// * listCount : 총 게시글 갯수
		listCount = new BoardService().selectListCount();
		// * currentPage : 사용자가 요청한 페이지 (현재 페이지)
		currentPage = Integer.parseInt(request.getParameter("cpage"));
		// * pageLimit : 페이징바의 페이지 최대 갯수
		pageLimit = 10;
		// * boardLimit : 한 페이지당 보여질 게시글의 최대 갯수
		boardLimit = 10;
		
		/*
		 * maxPage : 제일 마지막 페이지 수
		 * listCount, boardLimit에 영향을 받음
		 * ex) 게시글이 10개 단위 씩 보여진다는 가정하에
		 *  listCount      boardLimit      maxPage
		 *     100.0   /      10    =>10.0   10
		 *     101.0   /      10    =>10.1   11
		 *     110.0   /      10    =>11.0   11
		 *     111.0   /      10    =>11.1   12
		 * ex) 게시글이 10개 단위 씩 보여진다는 가정하에
		 *  listCount      boardLimit      maxPage
		 *     10.0    /      5    =>2.0    2
		 *     11.0    /      5    =>2.2    3
		 *     15.0   /       5    =>3.0    3
		 *     111.0   /      5    =>11.1   12     
		 */
		maxPage = (int)Math.ceil((double)listCount/boardLimit);
		
		/*
		 * startPage : 페이징바의 시작수
		 * pageLimit, currentPage에 영향을 받음
		 * 
		 * ex) 페이징바의 목록이 10개단위(pageLimit)라는 가정하에
		 *   startPage : 1, 11, 21, 31, ...
		 *   
		 *   currentPage  startPage
		 *       1             1     => 0*pageLimit + 1  => n=0
		 *       5             1     => 0*pageLimit + 1  => n=0
		 *      10             1     => 0*pageLimit + 1  => n=0
		 *      11            11     => 1*pageLimit + 1  => n=1
		 *      15            11     => 1*pageLimit + 1  => n=1
		 *      
		 *  
		 */
		startPage = (currentPage-1)/pageLimit * pageLimit+1;
		
		/*
		 * endPage : 페이징바의 끝수
		 * startPage, pageLimit, maxPage에 영향을 받음
		 * 
		 * ex) pageLimit 10가정하에
		 * startPage : 1 => endPage : 10
		 * startPage : 11 => endPage : 10
		 */
		endPage = startPage + pageLimit -1;
		if(endPage>maxPage) {
			endPage = maxPage;
		}
		
		// 7개의 값을 담을 수 있는 임의의 vo클래스 만들기
		// com.br.common.model.vo.PageInfo
		
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
		// 현재 요청한 페이지(currentPage)에 보여질 게시글 리스트 boardLimit수 만큼 조회
		ArrayList<Board> list = new BoardService().selectList(pi);
		
		request.setAttribute("pi", pi);
		request.setAttribute("list", list);
		
		//응답페이지 (views/board/boardListView.jsp)
		request.getRequestDispatcher("views/board/boardListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
