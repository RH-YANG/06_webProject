package com.br.board.model.service;

import static com.br.common.JDBCTemplate.close;
import static com.br.common.JDBCTemplate.commit;
import static com.br.common.JDBCTemplate.getConnection;
import static com.br.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import com.br.board.model.dao.BoardDao;
import com.br.board.model.vo.Attachment;
import com.br.board.model.vo.Board;
import com.br.board.model.vo.Category;
import com.br.board.model.vo.Reply;
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
	
	
	public int increseCount(int boardNo) {
		Connection conn = getConnection();
		int result = new BoardDao().increseCount(conn, boardNo);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public Board selectBoard(int boardNo) {
		Connection conn = getConnection();
		Board b = new BoardDao().selectBoard(conn, boardNo);
		close(conn);
		return b;		
	}
	
	public Attachment selectAttachment(int boardNo) {
		Connection conn = getConnection();
		Attachment at = new BoardDao().selectAttachment(conn, boardNo);
		close(conn);
		return at;
	}
	
	public int updateBoard(Board b,Attachment at) {
		Connection conn = getConnection();
		int result1 = new BoardDao().updateBoard(conn, b);
		
		int result2 = 1; //1로 초기화
		if(at != null) {
			if(at.getFileNo() != 0) { 
				// fileNo 타입은 숫자이므로 기본값 0으로 비교
				//old첨부 O => UPDATE ATTACHMENT
				result2 = new BoardDao().updateAttachment(conn, at);
				
			}else { 
				//old첨부 X  ==> INSERT ATTACHMENT
				//기존의 insertAttachment 메소드 쓸 수 없음. sql문이 살짝 다름
				result2 = new BoardDao().insertNewAttachment(conn, at);
			}
		}
		if(result1>0 && result2>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result1*result2;
	}
	
	public int insertThumbnailBoard(Board b, ArrayList<Attachment> list) {
		Connection conn = getConnection();
		//Board 테이블 insert
		int result1 = new BoardDao().insertThBoard(conn,b);
		
		//Attachment테이블
		int result2 = new BoardDao().insertAttachmentList(conn, list); 
		
		if(result1*result2>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result1*result2;
	}
	
	public ArrayList<Board> selectThumbnailList(){
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDao().selectThumbnailList(conn);
		close(conn);
		return list;
	}

	
	public ArrayList<Attachment> selectAttachmentList(int boardNo){
		Connection conn = getConnection();
		ArrayList<Attachment> list = new BoardDao().selectAttachmentList(conn, boardNo);
		close(conn);
		return list;
	}
	
	public ArrayList<Reply> selectReplyList(int boardNo) {
		Connection conn = getConnection();
		ArrayList<Reply> list = new BoardDao().selectReplyList(conn, boardNo);
		close(conn);
		return list;
	}
	
	public int insertReply(Reply r) {
		Connection conn = getConnection();
		int result = new BoardDao().insertReply(conn, r);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

}
