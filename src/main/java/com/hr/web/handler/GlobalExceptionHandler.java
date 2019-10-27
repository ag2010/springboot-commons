package com.hr.web.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hr.bean.system.WebResult;



@ControllerAdvice
public class GlobalExceptionHandler{


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public WebResult jsonerrorHandler(HttpServletRequest req,Exception e) throws Exception {
    		e.printStackTrace();
    		WebResult webResult=new WebResult();
 		webResult.setNo(e.getMessage());
 		return webResult;
    }
    
    @ExceptionHandler(value = WebException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req,WebException e) throws Exception {
    		e.printStackTrace();
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", e.getMessage());
        mav.setViewName("/error/error");
        return mav;
    }
    
    
   
}
