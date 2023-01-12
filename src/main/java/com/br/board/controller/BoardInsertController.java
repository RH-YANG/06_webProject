package com.br.board.controller;

import java.io.File;
import java.io.IOException;

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
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//mutipart/form-data로 전송하는 경우 request로부터 바로 값 뽑을 수 없다.
		//String boardTitle = request.getParameter("title");  
		//이러면 null이 담김
		
		// enctype이 mutipart/form-data로 잘 전송되었을 경우 잘 수행되어야함(오류나는지 확인)
		if(ServletFileUpload.isMultipartContent(request)) {
			// 파일업로드를 해주는 라이브러리를 가져와야 쓸수 있음
			// >> Cos.jar (com.oreilly.sevlet의 약자)
			// http://www.servlets.com
			
			//1) 전달되는 파일들을 처리할 작업내용 
			// >> 파일용량제한, 전달된 파일을 저장할 폴더 경로
			//1-1) 파일의 용량제한 지정 >> int maxSize에 byte단위로 담기
			/*
			 * byte => kbyte => mbyte => gbyte=> tbyte ...
			 * 
			 * 1kbyte == 1024byte
			 * 1mbyte == 1024kbyte == 1024*1024byte
			 */
			int maxSize = 10*1024*1024;
			
			//1-2) 전달된 파일을 저장시킬 폴더의 물리적인 경로 알아내기
			//   => String savePath
			// resources 폴더에 board_upfiles 폴더만들기
			// 세션객체.getServletContext() >> application 내장객체 반환
			// app내장객체.getRealPath() >> "/~~" 배포되는 폴더의 최상위폴더 == webapp
			//                           끝에 /를 꼭 붙인다(그래야 그 안으로 저장)
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/");
			//System.out.println(savePath); 어떻게 출력되나 확인
			//C:\workspaces\06_web-workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\webProject\resources\board_upfiles\
			
			/*
			 * 2) 전달된 파일의 파일명 수정 및 서버에 업로드(폴더에 저장) 작업
			 *   >> HttpServletRequest 타입의 객체 request를 
			 *      cos.jar의 클래스 MultipartRequest multiRequest로 변환
			 *   >> 파일명도 수정되고 폴더에 저장도 된다!
			 *   
			 *     파일명을 수정시켜주는 객체 제시해야함
			 *     =>이유1. 중복되는 경우를 없애고 
			 *     =>이유2. 한글/특수문자/띄어쓰기가 포함되어있을 경우 서버에 따라 문제발생
			 *     
			 *   파일명이 안겹치도록 수정하는 객체 : 
			 *   **DefaultFileRenamePolicy (cos.jar에서 제공)
			 *     -해당 클래스 내부에 rename()메소드가 실행되면서 파일명 수정후 업로드
			 *     rename(원본파일){
			 *     		기존에 동일한 파일명이 존재할경우
			 *     		파일명 뒤에 카운팅된 숫자를 붙여줌
			 *     		동일한것이 없을경우 그대로 저장
			 *     		return 수정파일;
			 *     }  => 한글/특수문자/띄어쓰기는 검사가 안됨..>> 나만의 객체를 만들어야한다
			 *   **나만의 FileRenamePolicy만들기(rename()정의)
			 *     - com.br.common.MyFileRenamePolicy 
			 */
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			//3) DB에 기록할 데이터를 뽑아서 vo에 주섬주섬 담기
			//    > 카테고리번호, 제목, 내용, 로그인한회원번호 => Board테이블 insert
			//    > ^넘어온 첨부파일이 있다면^ 원본명, 실제파일명, 저장경로 => Attachment 테이블 insert
			String category = multiRequest.getParameter("category");
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			//회원번호 알아오기
			HttpSession session = request.getSession();
			int userNo = ((Member)session.getAttribute("loginUser")).getUserNo();
			
			//Board담기
			Board b = new Board();
			b.setCategory(category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			b.setBoardWriter(String.valueOf(userNo));
			
			//첨부파일 있을때만 Attachment담기
			Attachment at = null;
			//multiRequest.getOriginalFileName("키") : 해당 키값(input의 name)으로 넘어온 첨부파일의 원본명 문자열 리턴(없을시 null)
			if(multiRequest.getOriginalFileName("upfile") != null) {
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("upfile"));
				//실제 저장된 파일명을 알아내는 메소드
				at.setChangeName(multiRequest.getFilesystemName("upfile"));
				//물리경로 말고 상대경로로, 마지막은 /
				at.setFilePath("resources/board_upfiles/");
			}
			
			//4) 서비스요청 (insert하러가기)
			int result = new BoardService().insertBoard(b,at);
			
			//5) 응답뷰 지정
			if(result > 0) {
				//성공 => 목록페이지의 1번
				session.setAttribute("alertMsg", "일반게시글 작성 성공");
				response.sendRedirect(request.getContextPath()+"/list.bo?cpage=1");
			}else {
				//실패 => 에러페이지
				//첨부파일 업로드는 진행이 됐음. 단 db에 기록이 실패함
				//첨부파일이 있었다면 찾아서 삭제해야한다
				if(at != null) {
					//savePath는 폴더까지의 경로가 담겨으므로 이름까지 합쳐준다
					new File(savePath + at.getChangeName()).delete();
				}
				request.setAttribute("errorMsg", "일반게시글 작성 실패");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
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
