package com.br.member.model.service;

import static com.br.common.JDBCTemplate.close;
import static com.br.common.JDBCTemplate.commit;
import static com.br.common.JDBCTemplate.getConnection;
import static com.br.common.JDBCTemplate.rollback;

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
	
	
	public Member updateMember(Member m) {
		//JDBCTemplate에 저장해둔 메소드 사용
		Connection conn = getConnection();
		
		int result = new MemberDao().updateMember(conn, m);
		
		Member updateMem = null;
		if(result>0) {
			commit(conn);
			// 갱신된 회원 객체 다시 조회해오기 
			// ==> 세션에 담긴 회원객체를 갱신시켜야되기 때문
			// 회원을 조회하는 sql문있긴 하나(로그인에 사용했던 셀렉문)
			// 비밀번호를 모르기때문에 재사용 불가.
			updateMem = new MemberDao().selectMember(conn, m.getUserId());			
		}else {
			rollback(conn);
		}
		close(conn);
		return updateMem;
	}
	
	public Member updatePwdMember(String userId, String userPwd, String updatePwd) {
		Connection conn = getConnection();
		int result = new MemberDao().updatePwdMember(conn, userId, userPwd, updatePwd);
		
		//회원정보 갱신해서 담아야함!
		Member updateMem = null;
		if(result>0) {
			commit(conn);
			//이전에 만들어둔 Dao 메소드 활용
			updateMem = new MemberDao().selectMember(conn, userId);
		}else {
			rollback(conn);
		} 
		close(conn);
		return updateMem;		
		
	}
	
	public int deleteMember(String userId, String userPwd) {
		Connection conn = getConnection();
		int result = new MemberDao().deleteMember(conn, userId, userPwd);
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		
		return result;		
	}
	
	public int idCheck(String checkId) {
		Connection conn = getConnection();
		int result = new MemberDao().idCheck(conn, checkId);
		close(conn);
		return result;
	}
	
	
	
	
	
	
	
	
	
}
