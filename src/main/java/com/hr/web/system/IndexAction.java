package com.hr.web.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexAction extends BaseAction{

	
	@RequestMapping("/")
	public String redirectIndex() {
		return "redirect:/system/index";
	}
	
	
    @RequestMapping("/403")
    public String error403(){
        return "/error/403";
    }
    
    
}
