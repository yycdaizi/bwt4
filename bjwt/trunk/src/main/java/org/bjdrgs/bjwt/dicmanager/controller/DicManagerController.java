package org.bjdrgs.bjwt.dicmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("dicManagerController")
@RequestMapping("/")
public class DicManagerController {

	public DicManagerController(){
		System.out.println("create DicManagerController");
	}
	
	@RequestMapping("/")
	public String init(){
		return "dicmanager/dicManager";
	}
	
	@RequestMapping("/login*")
	public String login(@RequestParam("user")String user){
		return "dicmanager/dicmanager";
	}
}
