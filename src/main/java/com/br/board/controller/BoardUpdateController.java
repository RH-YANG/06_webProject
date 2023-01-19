package com.br.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.br.board.model.service.BoardService;
import com.br.board.model.vo.Attachment;
import com.br.board.model.vo.Board;
import com.br.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardUpdateController
 */
@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		if(ServletFileUpload.isMultipartContent(request)) {
			//1-1) 파일의 용량제한
			int maxSize = 10*1024*1024;
			//1-2) 물리적인 파일저장위치
			//     서블릿객체를 session객체로부터 getServletContext로 얻어낸다음 getRealPath
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/");
			//2) 전달된 파일 서버에 업로드(한줄로 완료)
			//    request에 담긴것도 multiReq로 옮긴다.
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			//3) db에 데이터기록
			//   첨부파일이 있든없든 수행하는것 : Board 테이블 업데이트
			int boardNo = Integer.parseInt(multiRequest.getParameter("no"));
			String category = multiRequest.getParameter("category");
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			
			Board b = new Board();
			b.setBoardNo(boardNo);
			b.setCategory(category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			
			//새로 넘어온 첨부파일 있는지 여부 확인할때 : at의 null여부
			// 새로운 첨부파일이 넘어왔을 경우 그때 생성한다.
			Attachment at = null;
			if(multiRequest.getOriginalFileName("upfile") != null) { 
				//첨부파일이 있을경우임. 해당 메소드는 오리지널 파일이름을 반환할 뿐 아니라 없으면 null이므로
				at = new Attachment();
				//기존 첨부파일이 있건없건 필요한 데이터들 = 새로운 첨부파일 원본명, 저장명, 저장경로
				at.setOriginName(multiRequest.getOriginalFileName("upfile"));
				at.setChangeName(multiRequest.getFilesystemName("upfile"));
				at.setFilePath("resources/board_upfiles/");
				
				//숨겨서 넘긴 정보를 활용해서 기존파일여부를 확인한다.
				if(multiRequest.getParameter("originFileNo") != null) {
					// 기존의 첨부파일이 있었을 경우 => UPDATE ATTACHMENT (기존파일번호필요)
					at.setFileNo(Integer.parseInt(multiRequest.getParameter("originFileNo")));
				}else {
					// 기존의 첨부파일이 없었을 경우 => INSERT ATTACHMENT (현재게시글번호필요)
					at.setRefBoardNo(boardNo);
				}
			}
			int result = new BoardService().updateBoard(b,at);
			//at는 세가지 경우가 있음. 
			// 1) new첨부 X == null 
			// 2) new첨부 O, old첨부 O  == fileNo에 기존파일정보있음
			// 3) new첨부 O, old첨부 X  == refBoardNo에 정보 있음
			if(result>0) {
				//성공
				request.getSession().setAttribute("alertMsg", "성공적으로 수정되었습니다.");
				response.sendRedirect(request.getContextPath()+"/detail.bo?no="+boardNo);
			}else {
				//실패 => 에러페이지
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
