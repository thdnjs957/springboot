package com.cafe24.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.repository.PostDao;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;

@Service
public class BlogService {

	@Autowired
	CategoryDao categoryDao;
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	BlogDao blogDao;

	private static final String SAVE_PATH = "/jblog-uploads";
	private static final String URL = "/images";
	
	public Map<String,Object> getAll(String id, Long categoryNo, Long postNo) {
		
		BlogVo blogVo = blogDao.get(id);
		
		Map<String, Object> mapAll = new HashMap<String,Object>();
		
		List<CategoryVo> categoryList = categoryDao.getList(id);
		
		List<PostVo> postList = postDao.getListDefault(id); 
		
		PostVo postVo = postDao.getCategoryPostDefault(id);
		
		if(categoryNo != 0L && postNo != 0L ) {
			
			Map<String, Object> map = new HashMap<String,Object>();
			
			map.put("categoryNo", categoryNo);
			map.put("no", postNo);
			map.put("blogId", id);
			
			postList = postDao.getList(map); 
			postVo = postDao.getCategoryPost(map);
			
		}else if(categoryNo != 0L && postNo == 0L) {
			//카테고리만 클릭했을 경우 
			Map<String, Object> map = new HashMap<String,Object>();
			
			map.put("categoryNo", categoryNo);
			map.put("blogId", id);
			
			postList = postDao.getList(map);
			postVo = postDao.getCategoryPostFirst(map); 
			
		}
		
		mapAll.put("blogVo",blogVo);
		mapAll.put("categoryList", categoryList);
		mapAll.put("postList", postList);
		mapAll.put("postVo",postVo);
		
		return mapAll;
	}

	public String restore(MultipartFile multipartFile) {
		
		String url = "";

		try {
		
			if(multipartFile.isEmpty()) {
				return url;
			}
			
			String originalFilename = 
					multipartFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf('.')+1);
			String saveFileName = generateSaveFileName(extName);
			long fileSize = multipartFile.getSize();
			
			System.out.println("##########" + originalFilename);
			System.out.println("##########" + extName);
			System.out.println("##########" + saveFileName);
			System.out.println("##########" + fileSize);
			
			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(fileData);
			os.close();
			
			url = URL + "/" + saveFileName;
			
		} catch (IOException e) {
			throw new RuntimeException("Fileupload error:" + e);
		}
		
		return url;
	}
	
	private String generateSaveFileName(String extName) {
		
		String filename = "";
		Calendar calendar = Calendar.getInstance();
		
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}
	
	public boolean update(BlogVo blogVo) {
		
		return blogDao.update(blogVo);
	}
	
	public BlogVo getById(String id) {

		return blogDao.get(id);
	}
	
	
	public boolean insertCategory(CategoryVo categoryVo) {
		
		return categoryDao.insert(categoryVo);
	}

	public boolean deleteCategory(CategoryVo categoryVo) {
		return categoryDao.delete(categoryVo);
	}

	public List<CategoryVo> getList(String id) {
		return categoryDao.getList(id);
	}

	
	public boolean insertPost(PostVo postVo) {
		return postDao.insert(postVo);
	}

	
}
