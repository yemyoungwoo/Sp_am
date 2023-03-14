package com.ymw.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsrChatController {
	@RequestMapping("/usr/member/ChatAdd")
	public String Chat() {

		return "/usr/member/ChatAdd";
	}
}