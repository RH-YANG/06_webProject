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
 * Servlet implementation class MemberUpdateController
 */
@WebServlet("/update.me")
public class MemberUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 요청시 전달값 뽑기
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String[] interestArr = request.getParameterValues("interest");
		String interest = "";
		if(interestArr != null) {
			interest = String.join(", ", interestArr);
		}
		Member m = new Member(userId, userName, phone, email, address, interest);
		
		// 2) 요청처리
		// int result가 아닌 Member로 받는다.
		// 업데이트가 성공한 후 다시 셀렉문 실행해서 조회해올 것이므로
		Member updateMem = new MemberService().updateMember(m);
		
		// 3) 요청처리 결과를 가지고 사용자가 보게 될 뷰 지정
		if(updateMem == null) { //실패
			//에러문구 담아서 에러페이지 포워딩
			// 에러페이지에서 errorMsg에 담긴 밸류를 출력시킴.
			request.setAttribute("errorMsg", "회원정보 등록에 실패했습니다.");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);;
		}else { //성공
			// 알람이 뜬 후 업데이트 된 정보와 함께 마이페이지가 뜨도록
			// alert띄우기(이미 구현된 기능에 session에 alertMsg문구가 있으면 출력되도록 코드짜놈)
			HttpSession session = request.getSession();
			session.setAttribute("alertMsg", "성공적으로 회원정보를 수정했습니다.");
			// 변경된 회원정보를 반영시키기. 다시 담으면 덮어쓰가가 됨.
			session.setAttribute("loginUser", updateMem);
			
			// 응답페이지(views/member/myPage.jsp)
			// 기능구현 표를 보면 해당 페이지를 응답하는 url존재(/mypage.me)
			// MypageController 서블릿이 구동되고 mypage 포워딩됨
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
