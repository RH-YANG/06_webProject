package com.br.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.br.member.model.service.MemberService;
import com.br.member.model.vo.Member;

/**
 * Servlet implementation class MemberPwdUpdateController
 */
@WebServlet("/updatePwd.me")
public class MemberPwdUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberPwdUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 요청시 전달값
		// post 방식이나 한글이 없어서 인코딩 안해도됨
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String updatePwd = request.getParameter("updatePwd");
		
		
		// 2) 요청처리
		Member updateMem = new MemberService().updatePwdMember(userId, userPwd, updatePwd);
		
		// 3) 응답뷰
		HttpSession session = request.getSession();
		if(updateMem==null) { //실패(현재 비밀번호 잘못 입력했을경우)
			// 에러페이지로 가지않고 마이페이지에서 확인하라는 알람창
			session.setAttribute("alertMsg", "현재 비밀번호를 다시 확인해주세요.");
			
		}else { //성공
			// 마이페이지에서 성공했다는 알림창
			session.setAttribute("alertMsg", "성공적으로 비밀번호 변경되었습니다.");
			// session에 담긴 회원정보 갱신하기
			session.setAttribute("loginUser", updateMem);
		}
		//성공과 실패 둘다 실행할 코드
		response.sendRedirect(request.getContextPath()+"/myPage.me");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
