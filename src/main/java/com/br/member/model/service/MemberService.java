package com.br.member.model.service;

import static com.br.common.JDBCTemplate. * ;

import java.sql.Connection;
import com.br.member.model.dao.MemberDao;
import com.br.member.model.vo.Member;

public class MemberService {
	
	public Member loginMember(String userId, String userPwd) {
		
		Connection conn = getConnection();
		Member m = new MemberDao().loginMember(conn, userId, userPwd);
		close(conn);
		
		return m;
		
	}
	
	/**
	 * 회원가입요청 서비스
	 * @param m insert할 데이터들이 담겨있는 Member객체
	 * @return insert 후에 처리 된 행수
	 */
	public int insertMember(Member m) {
		//import static으로 선언했기때문에 메소드 바로 사용
		Connection conn = getConnection();
		int result = new MemberDao().insertMember(conn, m);
		
		if(result>0) { //성공
			commit(conn);
		} else { //실패
			rollback(conn);
		}
		close(conn);
		
		return result;	
	}

}
