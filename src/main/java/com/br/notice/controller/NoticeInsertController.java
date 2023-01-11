package com.br.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.br.member.model.vo.Member;
import com.br.notice.model.service.NoticeService;
import com.br.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeInsertController
 */
@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String noticeTitle = request.getParameter("title");
		String noticeContent = request.getParameter("content");
		
		//로그인한 회원 정보를 얻어내는 방법
		// 1. input type="hidden"으로 애초에 요청시 숨겨서 전달하는 방법
		// 2. session에 담겨있는 회원객체로부터 뽑기 (서블릿에서도 세션에 접근가능하다!)
		HttpSession session = request.getSession();
		//세션으로부터 뽑으면 멤버객체가 아닌 오브젝트타입 상태임. 
		//강제 형변환 후 getter 메소드 사용
		int userNo = ((Member)session.getAttribute("loginUser")).getUserNo();
		
		Notice n = new Notice();
		n.setNoticeTitle(noticeTitle);
		n.setNoticeContent(noticeContent);
		// 숫자가 아닌 문자열로 담기로 설계해놨었다.
		n.setNoticeWriter(String.valueOf(userNo));
		
		int result = new NoticeService().insertNotice(n);
		
		if(result>0) {
			// 성공 => 공지사항 목록페이지 띄우기(noticeListView.jsp)
			//        alert로 "성공적으로 공지사항 등록되었습니다."
			session.setAttribute("alertMsg", "공지사항 등록에 성공했습니다");
			//이미 등록해둔 url이 있으므로 포워딩 할 필요가 없이 url재요청을 한다.
			response.sendRedirect(request.getContextPath() + "/list.no");
		}else {
			// 실패 =>  에러페이지(errorPage.jsp)
			//         에러문구 "공지사항 등록 실패"
			request.setAttribute("errorMsg", "공지사항 등록에 실패했습니다.");
			//포워딩을 한다.
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request,response);
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
