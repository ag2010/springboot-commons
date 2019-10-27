package com.hr.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class NotFoundHandler implements ErrorController{

    @Override
    public String getErrorPath() {
        return "/error/404";
    }

    @RequestMapping(value = "/error")
    public ModelAndView error404(HttpServletResponse resp, HttpServletRequest req) {
    	  ModelAndView mav = new ModelAndView();
      mav.setViewName("/error/404");
      return mav;
    }
}
