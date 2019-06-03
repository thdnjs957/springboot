package com.cafe24.jblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.jblog.dto.JSONResult;
import com.cafe24.jblog.service.UserService;

@Controller("userAPIController")
@RequestMapping("/user/api")
public class UserController {

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping("/checkid")
	public JSONResult checkId(@RequestParam(value = "id", required = true, defaultValue = "") String id) {
		
		boolean exist = userService.existId(id);

//		JSONResult result = new JSONResult.success(exist);
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		map.put("result", "success");
//		map.put("data", exist);

		return JSONResult.success(exist);
	}

}
