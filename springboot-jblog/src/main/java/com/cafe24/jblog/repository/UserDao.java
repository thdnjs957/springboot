package com.cafe24.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert",vo);
		return 1 == count; 
	}

	public UserVo get(String id, String password) { // 로그인
		
		Map<String,String> map = new HashMap<String, String>();
		
		map.put("id", id);
		map.put("password",password);
		
		UserVo uservo = sqlSession.selectOne("getByIdAndPassword",map);
		
		return uservo;
		
	}
	
	public UserVo get(String id) {
		
		UserVo result = sqlSession.selectOne("user.getById",id);
		
		return result;
	}
	
	
	
}
