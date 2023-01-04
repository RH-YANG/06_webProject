package com.br.member.model.service;

import java.sql.Connection;

import static com.br.common.JDBCTemplate.*;
import com.br.member.model.dao.MemberDao;
import com.br.member.model.vo.Member;

public class MemberService {
	
	public Member loginMember(String userId, String userPwd) {
		
		Connection conn = getConnection();
		Member m = new MemberDao().loginMember(conn, userId, userPwd);
		close(conn);
		
		return m;
		
	}

}
