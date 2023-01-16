package com.br.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.br.board.model.service.BoardService;
import com.br.board.model.vo.Attachment;
import com.br.board.model.vo.Board;
import com.br.common.MyFileRenamePolicy;
import com.br.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class ThumbnailInsertController
 */
@WebServlet("/insert.th")
public class ThumbnailInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		if(ServletFileUpload.isMultipartContent(request)) {
			int maxSize = 10*1024*1024;
			//request.getSession().getServletContext() = 웹 어플리케이션 객체 (전역객체)
			//.getRealPath("/~~")에서 / == webapp
			String savePath = request.getSession().getServletContext().getRealPath("/resources/thumbnail_upfiles/");
			
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			//업로드완
			
			//DB에 기록하기 위해 데이터 담기
			HttpSession session = request.getSession();
			int userNo = ((Member)session.getAttribute("loginUser")).getUserNo();
			
			Board b = new Board();
			b.setBoardTitle(multiRequest.getParameter("title"));
			b.setBoardContent(multiRequest.getParameter("content"));
			b.setBoardWriter(String.valueOf(userNo));
			
			// Attachment테이블에 여러번 insert할 데이터 담기
			ArrayList<Attachment> list = new ArrayList<>();
			
			for(int i=1; i<=4; i++) {
				String key = "file"+i;
				if(multiRequest.getOriginalFileName(key) != null) {
					//첨부파일이 있을 경우
					// Attachment 객체 생성 + 원본명, 수정명, 저장경로, 파일레벨 담기 => list 추가
					Attachment at = new Attachment();
					at.setOriginName(multiRequest.getOriginalFileName(key));
					at.setChangeName(multiRequest.getFilesystemName(key));
					at.setFilePath("resources/thumbnail_upfiles/");
					//대표이미지면 1, 아니면 2 입력
					at.setFileLevel(i==1? 1 : 2);
					list.add(at);
				}
			}
			
			int result = new BoardService().insertThumbnailBoard(b, list);
			
			if(result>0) {
				//성공 => 목록페이지 ( thumbnailListView.jsp ) 이미 포워딩하는 url 존재
				session.setAttribute("alertMsg", "성공적으로 게시글이 등록되었습니다");
				response.sendRedirect(request.getContextPath()+"/list.th");
				
			}else {
				//에러페이지
			}
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
