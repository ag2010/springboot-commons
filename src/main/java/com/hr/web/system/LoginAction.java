package com.hr.web.system;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.hr.bean.system.SysMenu;
import com.hr.bean.system.SysUsers;
import com.hr.bean.system.WebResult;
import com.hr.common.log.system.SysLogLogin;
import com.hr.mapper.system.SysMenuMapper;
import com.hr.mapper.system.SysRoleMapper;
import com.hr.mapper.system.SysUsersMapper;
import com.hr.util.StringUtil;




@Controller
@RequestMapping("/system")
public class LoginAction extends BaseAction{
	
	
	@Autowired
	private SysUsersMapper sysUsersMapper;
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	
	@Autowired
	private Producer   captchaProducer;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	

	
	@RequestMapping("/index")
	public String index(Model model){
		model.addAttribute("user",this.getCurrentUser());
		model.addAttribute("menuList",this.getSession().getAttribute("menuOneList"));
		return "/system/index";
	}
	
	

	
	
	@RequestMapping("/login")
	public String login(){
		return "/system/login/login";
	}
	
	
	@RequestMapping("/logOut")
	public String logOut(HttpServletRequest request,   HttpServletResponse response){
        try {
        	Subject subject = this.getSubject();
            subject.logout();
        } catch (SessionException ise) {
           ise.printStackTrace();
        }
		return "/system/login/login";
	}
	
	
	
	@ResponseBody
    @RequestMapping(value="/getMenuByPid",method = RequestMethod.POST)
	public List<SysMenu> getMenuByPid(int pid){
		List<SysMenu> menu_2_array=new ArrayList<SysMenu>();
		menu_2_array=sysMenuMapper.findAllTwoLevelByPid(pid);
		for(SysMenu ssmenu:menu_2_array){
			List<SysMenu> menu_3_array=sysMenuMapper.findAllThirdLevelByPid(ssmenu.getId());
			ssmenu.setChildMenu(menu_3_array);
		}
		return menu_2_array;
	}
	
	@SysLogLogin
	@ResponseBody
    @RequestMapping(value="/loginAction",method = RequestMethod.POST)
    public WebResult loginAction(String username,String password,String codeImg,HttpServletRequest request){
    		rs=new WebResult();
    		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
    			rs.setNo("请输入用户名和密码");
    			return rs;
    		}
    		
    		//账户校验
    		SysUsers sysUsers = sysUsersMapper.login(username, password);
    		rs.setCode(0);
            rs.setMsg("欢迎进入后台管理系统");
    		if(!StringUtils.isEmpty(username) || !StringUtils.isEmpty(password) || sysUsers == null){
        		HttpSession session = request.getSession(true);
        		if(null!=session){
        			Object count = session.getAttribute("count");
        			int num = count == null ? 0 : (int) count;
                    num++;
                    if(num>=3){
                    	//验证码校验
                    	rs.setCount(num);
            			String kaptchaExpected = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
                		if(StringUtils.isEmpty(codeImg)){
                			rs.setNo("验证码不能为空");
                			return rs;
                		}else {
                			if(!kaptchaExpected.equals(codeImg)){
                				rs.setNo("验证码不正确");
                    			return rs;
                			}
                		}
                		return rs;
                    }else{
                    	session.setAttribute("count", num);
                    	rs.setCount(num);
                    	return rs;
                    }
        		}else{
        			rs.setCount(0);
        		}
                
    		}
    		
    		 
    		
    		//是否锁定
    		boolean flag=false;
    		//用户登录
    		try {
    			// 1、 封装用户名和密码到token令牌对象  [支持记住我]
    			AuthenticationToken token = new UsernamePasswordToken(
    					username, password,false);
    			// 2、 Subject调用login
    			Subject subject = SecurityUtils.getSubject();
    			//在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
    			//每个Realm都能在必要时对提交的AuthenticationTokens作出反应
    			//所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
    			subject.login(token);
    			
    			//登录成功后查询所有菜单
    			//List<SysMenu> menu_1_array1=sysMenuMapper.findAllOneLevel();
    			List<SysMenu> menu_1_array = new ArrayList<SysMenu>();

    			//为sql语句准备参数
    			Map<String,Object> params=new HashMap<String,Object>();
    			params.put("username", username);  			
    			

   				if(null!=params&&params.size()>0)
   				{

   	   				if(null!=params&&params.size()>0) {

   	   				}
   				}
   			
    			this.setSessionObj("menuOneList", menu_1_array);
    			rs.setOK("登录成功");
    		}catch(UnknownAccountException uae){
    			rs.setNo("不存在该用户");
    		}catch(IncorrectCredentialsException ice){
    			//获取输错次数
    			rs.setNo("密码不正确");
    		}catch(LockedAccountException lae){
    			rs.setNo("账户已锁定");
    			flag=true;
    		}catch(ExcessiveAttemptsException eae){
    			rs.setNo("用户名或密码错误次数大于5次,账户已锁定");
    			flag=true;
    		}catch(AuthenticationException ae){
    			//通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
    			rs.setNo("密码不正确");
    		} catch (Exception e) {
    			e.printStackTrace();
    			rs.setNo("用户登录失败，请您稍后再试");
    		}
        return rs;
    }
	
	
	/**
	 * 验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/captchaImage")  
    public ModelAndView getKaptchaImage(HttpServletRequest request,   HttpServletResponse response) throws Exception {  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control",   "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
  
        String capText = captchaProducer.createText();  
        try {  
            String uuid=StringUtil.randInt(4);       
            request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
            Cookie cookie = new Cookie("captchaCode",uuid);  
            response.addCookie(cookie);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
          
  
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    } 
	
	
	
	
	
	
	
}
