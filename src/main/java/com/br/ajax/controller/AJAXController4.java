package com.br.ajax.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.br.notice.model.service.NoticeService;
import com.br.notice.model.vo.Notice;
import com.google.gson.Gson;

/**
 * Servlet implementation class AJAXController4
 */
@WebServlet("/test4.do")
public class AJAXController4 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AJAXController4() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Notice> list = new NoticeService().selectNoticeList();
		//JSONArray로 넘겨주어야함. 이때 vo객체는 객체로 만든다
		// [{Notice객체},{Notice객체},...] JSONObject로 담은 후 JSONArray에 담기
		// Gson이 알아서 해준다.
		response.setContentType("application/json; charset=UTF-8");
		new Gson().toJson(list, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
