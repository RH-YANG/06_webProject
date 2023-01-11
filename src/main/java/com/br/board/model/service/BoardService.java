package com.br.board.model.service;

import static com.br.common.JDBCTemplate.close;
import static com.br.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import com.br.board.model.dao.BoardDao;
import com.br.board.model.vo.Board;
import com.br.common.model.vo.PageInfo;

public class BoardService {
	
	public int selectListCount() {
		Connection conn = getConnection();
		int listCount = new BoardDao().selectListCount(conn);
		close(conn);
		return listCount;		
	}
	
	
	public ArrayList<Board> selectList(PageInfo pi) {
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDao().selectList(conn,pi);
		
		return list;
		
	}

}
