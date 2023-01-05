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
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1) 요청시 전달값 뽑기(+가공처리) => 변수/객체에 담기
		
		//post방식일 경우 한글이 있다면 인코딩 필요
		request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone"); //빈문자열일경우 db에 null로 들어감
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String[] interestArr = request.getParameterValues("interest");
		//자바 배열은 db에 바로 넣을 수 없음
		//문자열로 가공처리 필요
		String interest = "";
		if(interestArr != null) {
			interest = String.join(", ", interestArr);
		}
		//데이터 담아두기
		Member m = new Member(userId, userPwd, userName, phone, email, address, interest);
		
		//2) 요청처리 (db에 sql문 실행)
		int result = new MemberService().insertMember(m);
		
		
		//3) 처리 결과를 가지고 사용자가 보게될 응답뷰 지정 후 포워딩 또는 url재요청
		if(result>0) {
			//회원가입 성공 => index페이지
			//성공알람 띄우기
			//request에 담으면 index에서 쓸 수 없음.(포워딩이 아니기 때문)
			HttpSession session = request.getSession();
			session.setAttribute("alertMsg", "성공적으로 회원가입이 되었습니다.");
					
			response.sendRedirect(request.getContextPath());			
		}else {
			// 회원가입 실패 => views/common/errorPage.jsp
			// 포워딩할때 request에 errorMsg를 저장해서 전달(에러페이지에서 출력함)
			request.setAttribute("errorMsg", "회원가입 실패했습니다");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);			
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
