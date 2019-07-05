package com.cafe24.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
	   @RequestMapping("index")
	   public String list(Model model)
	   {
//	      Map<String,Object> map = boardService.getList(keyword,curPage);
//	      
//	      List<BoardVo> list = (List<BoardVo>)map.get("list");
//	      
//	      Map<String, Integer> pager = (Map<String, Integer>)map.get("pagerMap");
//	      
//	      model.addAttribute("list", list);
//	      model.addAttribute("pager", pager);
	   
	       return "gallery/index";
	   }
}
