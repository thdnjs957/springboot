package com.cafe24.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.PostVo;

@Repository
public class PostDao {
	
	@Autowired
	private SqlSession sqlSession;

	public PostVo getByNo() {
		PostVo result = sqlSession.selectOne("post.getByNo");
		return result;
	}

	public boolean insert(PostVo postVo) {
		int count = sqlSession.insert("post.insert",postVo);
		return 1 == count;
	}
	
	public PostVo getCategoryPost(Map<String, Object> map) { //해당 카테고리 , 해당 포스트 하나 가져옴
		PostVo postvo = sqlSession.selectOne("post.getCategoryPost",map);
		return postvo;
		
	}

	public PostVo getCategoryPostDefault(String id) { //해당 카테고리 , 디폴트 포스트 하나 가져옴
		PostVo postVo = sqlSession.selectOne("post.getCategoryPostDefault",id);
		return postVo;
	}

	public List<PostVo> getList(Map<String, Object> map) { 
		List<PostVo> postList = sqlSession.selectList("post.getList",map);
		return postList;
	}
	
	public List<PostVo> getListDefault(String id) {
		List<PostVo> postList = sqlSession.selectList("post.getListDefault",id);
		return postList;
	}

	public PostVo getCategoryPostFirst(Map<String, Object> map) {
		PostVo postVo = sqlSession.selectOne("post.getCategoryPostFirst",map);
		return postVo;
	}
	
}
