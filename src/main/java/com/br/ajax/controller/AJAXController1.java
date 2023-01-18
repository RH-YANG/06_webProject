package com.br.ajax.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AJAXController1
 */
@WebServlet("/test1.do")
public class AJAXController1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AJAXController1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str =  request.getParameter("input");
		System.out.println("요청시 전달값 : "+str);
		
		//요청처리
		
		//요청처리 후 응답할 데이터 (한글이 포함된 문자열)
		String responseData = "입력된값 : "+str+", 길이 : "+str.length();
		
		//응답데이터 돌려주기
		response.setContentType("text/html; charset=UTF-8"); //한글포함 문자열 돌려줄때
		//요청자와의 통로를 만든 후 출력시킨다.(서블릿으로 응답화면 꾸릴때 썼던 구문들)
		response.getWriter().print(responseData);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
