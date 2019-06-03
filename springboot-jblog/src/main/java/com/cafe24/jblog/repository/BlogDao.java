package com.cafe24.jblog.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.PostVo;


@Repository
public class BlogDao {

	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(String id) {
		
		int countBlog = sqlSession.insert("blog.insert",id);
		
		return 1==countBlog;
	}

	public BlogVo get(String id) {
		BlogVo result = sqlSession.selectOne("blog.getById",id);
		return result;
	}

	public boolean update(BlogVo blogVo) {
		int count = sqlSession.update("blog.update",blogVo);
		return 1==count;
	}


}
