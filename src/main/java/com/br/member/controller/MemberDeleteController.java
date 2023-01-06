package com.br.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.br.member.model.service.MemberService;

/**
 * Servlet implementation class MemberDeleteController
 */
@WebServlet("/delete.me")
public class MemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 요청시 전달값
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		// 2) 요청처리
		int result = new MemberService().deleteMember(userId, userPwd);
		
		// 3) 응답뷰
		HttpSession session = request.getSession();
		if(result>0) {
			session.setAttribute("alertMsg", "탈퇴가 완료되었습니다");
			//로그인 풀림처리
			session.removeAttribute("loginUser");
			response.sendRedirect(request.getContextPath());
			
		}else {
			session.setAttribute("alertMsg", "비밀번호가 일치하지 않습니다");
			response.sendRedirect(request.getContextPath()+"/myPage.me");
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
