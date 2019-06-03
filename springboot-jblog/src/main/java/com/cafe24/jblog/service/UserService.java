package com.cafe24.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.repository.UserDao;
import com.cafe24.jblog.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	public boolean join(UserVo userVo) {
		
		String id = userVo.getId();
		boolean userInsertResult = userDao.insert(userVo);
		if(userInsertResult) {
			boolean blogInsertResult = blogDao.insert(id);
			boolean categoryInsertResult = categoryDao.insertDefault(id);
			if(blogInsertResult && categoryInsertResult){
				return true;
			}
		}
		return false;
	}
	
	public boolean existId(String id) {
		UserVo userVo = userDao.get(id);
		return userVo != null;
	}
	
	
	public UserVo getUser(UserVo userVo) {
		
		return userDao.get(userVo.getId(), userVo.getPassword());
		
	}
}
