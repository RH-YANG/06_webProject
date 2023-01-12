package com.br.board.model.service;

import static com.br.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import com.br.board.model.dao.BoardDao;
import com.br.board.model.vo.Attachment;
import com.br.board.model.vo.Board;
import com.br.board.model.vo.Category;
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
	
	public ArrayList<Category> selectCategoryList() {
		Connection conn = getConnection();
		ArrayList<Category> list = new BoardDao().selectCategoryList(conn);
		close(conn);
		return list;
	}
	
	public int insertBoard(Board b,Attachment at) {
		Connection conn = getConnection();
		//부모테이블 먼저 insert해야 .currval이 잘 먹힌다.
		int result1 = new BoardDao().insertBoard(conn, b);
		//**첨부파일이 없는 게시글도 있기때문에 0이 아닌 1로 초기화해야한다! 
		int result2 = 1;
		if(at != null) {
			result2 = new BoardDao().insertAttachment(conn, at);
		}
		//둘중 하나라도 실패했다면 롤백해야한다
		if(result1>0 && result2>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result1*result2;
	}

}
