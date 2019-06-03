package com.cafe24.jblog.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;
import com.cafe24.jblog.vo.UserVo;
import com.cafe24.security.Auth;
import com.cafe24.security.AuthUser;


@Controller
@RequestMapping("/{id:(?!assets.*).*}" )
public class BlogController {
	
	@Autowired
	BlogService blogService;
	
	@RequestMapping({"","/{pathNo1}","/{pathNo1}/{pathNo2}"})
	public String index(@PathVariable String id, 
						@PathVariable Optional<Long> pathNo1, 
						@PathVariable Optional<Long> pathNo2,
		    			Model model)
	{
		Long categoryNo = 0L;
		Long postNo = 0L;
		
		if(pathNo2.isPresent()) {
			categoryNo = pathNo1.get();
			postNo = pathNo2.get();
		}else if(pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
		}

		Map<String,Object> map  = blogService.getAll(id,categoryNo,postNo);
		
		BlogVo blogVo = (BlogVo) map.get("blogVo");
		
		List<CategoryVo> categoryList = (List<CategoryVo>)map.get("categoryList");
		List<PostVo> postList = (List<PostVo>)map.get("postList");
		
		PostVo postVo = (PostVo) map.get("postVo");
		
		model.addAttribute("blogVo",blogVo);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("postList",postList);
		model.addAttribute("postVo",postVo);
		
		return "blog/blog-main";
	}
	
	@Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable String id,@AuthUser UserVo authUser, Model model) {
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/";
		}
		
		BlogVo blogVo = blogService.getById(id);
		
		model.addAttribute("blogVo",blogVo);
		
		return "blog/blog-admin-basic";
	}
	
	@Auth
	@RequestMapping(value = "/admin/basic",method=RequestMethod.POST)
	public String adminBasic(
				@PathVariable String id, @AuthUser UserVo authUser,
				@ModelAttribute BlogVo blogVo,
				@RequestParam(value="logo-file") MultipartFile logoFile,
				Model model ) {
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/";
		}
		
		String logo = blogService.restore(logoFile);

		blogVo.setId(id);
		blogVo.setLogo(logo);
		
		blogService.update(blogVo);
		
		model.addAttribute("blogVo",blogVo);
		
		return "redirect:/"+id;
	}
	
	@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable String id,Model model,@AuthUser UserVo authUser) {
		
		BlogVo blogVo = blogService.getById(id);
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/";
		}
		
		//카테고리 리스트 보여주기
		List<CategoryVo> categoryList = blogService.getList(id);
				
		model.addAttribute("blogVo",blogVo);
		model.addAttribute("categoryList",categoryList);
		
		return "blog/blog-admin-category";
	}
	
	@Auth
	@RequestMapping("/admin/category/delete/{no}")
	public String adminDeleteCategory(@PathVariable String id,@PathVariable Long no,Model model,@AuthUser UserVo authUser) {
		
		BlogVo blogVo = blogService.getById(id);
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/";
		}
		
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setBlogId(id);
		categoryVo.setNo(no);
		
		blogService.deleteCategory(categoryVo);
				
		model.addAttribute("blogVo",blogVo);
		
		return "redirect:/"+id+"/admin/category";
	}
	
	
	@Auth
	@RequestMapping(value = "/admin/category", method=RequestMethod.POST)
	public String adminCategory(@ModelAttribute CategoryVo categoryVo,@PathVariable String id,@AuthUser UserVo authUser) {
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/";
		}
		
		boolean result = blogService.insertCategory(categoryVo);
		
		return "redirect:/"+id+"/admin/category";
	}
	
	@Auth
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable String id,Model model,@AuthUser UserVo authUser) {
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/";
		}
		
		BlogVo blogVo = blogService.getById(id);
		List<CategoryVo> categoryList = blogService.getList(id);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("blogVo",blogVo);
		return "blog/blog-admin-write";
	}
	
	@Auth
	@RequestMapping(value="/admin/write",method=RequestMethod.POST)
	public String adminWrite(@PathVariable String id,Model model,@ModelAttribute PostVo postVo,@RequestParam(value="category") Long no,@AuthUser UserVo authUser) {
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/";
		}
		
		//no = category_no
		postVo.setCategoryNo(no);
		
		boolean result = blogService.insertPost(postVo);
		
		BlogVo blogVo = blogService.getById(id);
		model.addAttribute("blogVo",blogVo);
		
		return "redirect:/"+id;
	}
	
}
