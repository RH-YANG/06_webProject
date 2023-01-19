package com.br.ajax.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class AJAXController2
 */
@WebServlet("/test2.do")
public class AJAXController2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AJAXController2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		//빈문자열이나 null을 파싱하면 에러 남(NumberFormatException).
		int age = Integer.parseInt(request.getParameter("age"));
		
		//요청처리 db에 다 했다는 가정하에 
		
		/*case1)응답할 데이터가 하나일 경우
		String responseData = "이름 : "+name+", 나이 : "+age;
		//응답데이터에 한글이 있으면 바로 돌려주면 안됨 한글 깨짐
		response.setContentType("text/html; charSet=UTF-8");
		//통로만들어 응답데이터 보내기
		response.getWriter().print(responseData);*/	
		
		//case2)응답할 데이터가 여러개일 경우
		/*response.setContentType("text/html; charSet=UTF-8");
		response.getWriter().print(name);
		response.getWriter().print(age);
		사용불가 */
		
		/* 방법1. 자바스크립트의 배열형태
		JSONArray jArr = new JSONArray(); // [] 텅빈배열
		jArr.add(name); // 0번인덱스에 담긴다
		jArr.add(age); // 1번인덱스에 담긴다
		//응답데이터가 JSON형태일때 해당 마임타입을 입력해야한다
		response.setContentType("application/json; charSet=UTF-8");
		response.getWriter().print(jArr);*/
		
		//방법2. 자바스크립트의 일반객체형태
		JSONObject jObj = new JSONObject();
		jObj.put("name", name);
		jObj.put("age", age);
		response.setContentType("application/json; charSet=UTF-8");
		response.getWriter().print(jObj);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
