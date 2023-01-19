package com.br.ajax.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.br.common.JDBCTemplate;
import com.br.member.model.dao.MemberDao;
import com.br.member.model.vo.Member;
import com.google.gson.Gson;

/**
 * Servlet implementation class AJAXController3
 */
@WebServlet("/test3.do")
public class AJAXController3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AJAXController3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("id");
		Member m = new MemberDao().selectMember(JDBCTemplate.getConnection(),userId);
		//Member객체 각 필드에 조회된 데이터들이 담겨있을것.
		
		//response.getWriter().print(m/*.toString*/);
		//자바에서의 객체를 곧바로 응답시 .toString()의 문자열이 응답됨
		
		/*JSONObject에 직접 담기
		JSONObject jObj = new JSONObject();
		jObj.put("userNo", m.getUserNo());
		jObj.put("userName", m.getUserName());
		jObj.put("userId", m.getUserId());
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jObj);*/
		
		//위 과정들을 알아서 해주는 GSON 라이브러리 사용
		//필드명을 키값으로, 담긴값을 밸류값으로 만들어줌
		//출력시킬 통로도 제시해야함
		response.setContentType("application/json; charset=UTF-8");
		new Gson().toJson(m, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
