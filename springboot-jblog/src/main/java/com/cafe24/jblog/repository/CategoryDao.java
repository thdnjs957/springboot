package com.cafe24.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.CategoryVo;

@Repository
public class CategoryDao {
	
	@Autowired
	private SqlSession sqlSession;

	public List<CategoryVo> getList(String id) {
		
		List<CategoryVo> result = sqlSession.selectList("category.getList",id);
		return result;
		
	}

	public boolean insert(CategoryVo categoryVo) {
		
		int count = sqlSession.insert("category.insert",categoryVo);
		return 1 == count;
		
	}

	public boolean insertDefault(String id) {
		int countCategory = sqlSession.insert("category.insertDefault",id);
		return 1 == countCategory;
	}

	public boolean delete(CategoryVo categoryVo) {
		int count = sqlSession.delete("category.delete",categoryVo);
		return 1 == count;
	}
	
}
