package com.br.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.br.member.model.service.MemberService;
import com.br.member.model.vo.Member;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) 요청시 전달값(한글있으면 깨짐 > 인코딩처리) 뽑기 & 데이터가공처리(파싱..) => 변수 또는 객체에 기록하기
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		// 2) 요청처리 (db에 sql문 실행)
		//    > Service > Dao > sql문 실행
		Member loginUser = new MemberService().loginMember(userId, userPwd);
		
		// 3) 처리된 결과를 가지고 사용자가 보게 될 응답뷰(jsp) 지정 후 포워딩 또는 url 재요청
		/*
		 * 응답할 페이지에 전달할 값이 있을 경우 어딘가에 담아야됨!
		 * 담을수 있는 영역 == JSP 내장객체
		 * 
		 * 1.application : 
		 * 여기에 담긴 데이터는 해당 웹 어플리케이션 전역에서 쓸 수 있음(자바, 서블릿, jsp 등)
		 * 2.session : 
		 * 직접 지우기 전까지 또는 세션이 만료(서버가 멈추거나 브라우저 종료)되기 전까지
		 * 모든 jsp, 서블릿에서 꺼내 쓸 수 있음.
		 * 3.request : 
		 * 현재 이 request 객체를 포워딩한 응답 jsp에서만 꺼내 쓸 수 있음(일회성)
		 * 다른페이지로 넘어가는 순간 소멸
		 * 매개변수로 선언되어있어서 바로 setAttribute 메소드로 사용가능
		 * 4.page : 
		 * 해당 jsp에서 담고 해당 jsp에서만 꺼내 쓸 수 있음.
		 * 
		 * 공통적으로 데이터를 담고자 한다면 객체.setAttribute("키", 밸류);
		 * 데이터를 꺼내고자 한다면 객체.getAttribute("키") >> Object타입으로 밸류반환
		 * 데이터를 지우고자 한다면 객체.removeAttribute("키")
		 */
		
		if(loginUser ==null ) {
			//조회결과 없음 == 로그인 실패  => 에러페이지(views/common/errorPage.jsp) 응답
			// 에러문구는 에러페이지에서만 필요하다! > request에 담아도 됨
			request.setAttribute("errorMsg", "로그인에 실패했습니다");
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
		}else {
			//조회결과 있음 == 로그인 성공  => 메인페이지(index.jsp) 응답
			
			//서블릿에서 session에 접근하고자 한다면 세션객체 얻어와야됨
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser); 
			//로그인한 회원객체 session에 담음. 여기저기에서 계속 필요하기 때문.
			
			//1.포워딩 방식 (forward)
			// 해당 선택된 jsp가 보여질 뿐, url은 절대 변경되지 않음
			//  (현재 요청했을때의 url이 남아있음)
			//RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			//view.forward(request, response);
						
			//2.url재요청 방식 (redirect)
			//  기존 페이지를 응답하는 url이 존재할 경우 사용 가능
			//  localhost:8887/web
			response.sendRedirect(request.getContextPath()); //  /web이 돌아올것임.
			// 절대경로방식 : 포트번호 바로 뒤에 붙음
			// menubar 최상단에 session을 활용하여 데이터 꺼내기
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
