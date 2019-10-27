package com.hr.common.shiro;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.hr.common.redis.RedisCacheManager;
import com.hr.common.redis.RedisSessionDAO;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class SysShiroConfig {
	
	@Autowired
    private RedisTemplate redisTemplate;
	
	
	/**
	 * 描述:自己现实的sessionDAO
	 * @return
	 */
	@Bean
	public SessionDAO redisSessionDAO() {
		return new RedisSessionDAO();
	}
	
	@Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
	
	@Bean(name="shrioRedisCacheManager")
    public CacheManager cacheManager() {
       return new RedisCacheManager();
    }
	 
	 /**
	  * simpleCookie,不定义在集群环境下会出现There is no session with id ....
	  * @return
	  */
	 @Bean
	 public SimpleCookie simpleCookie() {
		 SimpleCookie cookie = new SimpleCookie("redis.session");
		 cookie.setPath("/");
		 return cookie;
	 }

	 
	 /**
	  * 
	  * 描述:sessionManager对象
	  * @return
	  */
	 @Bean(name="sessionManager")
	 public DefaultWebSessionManager defaultWebSessionManager() {
		 DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		 sessionManager.setSessionDAO(redisSessionDAO());
		 sessionManager.setSessionIdUrlRewritingEnabled(false);
		 //sessionManager.setGlobalSessionTimeout(1800000);//默认值30分钟 : 1000 * 60 * 30 = 1800000
		 sessionManager.setCacheManager(cacheManager());
		 sessionManager.setSessionIdCookie(simpleCookie());
		 return sessionManager;
	 }
	 
	
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		//System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//拦截器.
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/static/**", "anon");
		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		//filterChainDefinitionMap.put("/logout", "logout");
		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		filterChainDefinitionMap.put("/static/*/*", "anon");
		filterChainDefinitionMap.put("/static/*/*/*", "anon");
		filterChainDefinitionMap.put("/static/*/*/*/*", "anon");
		filterChainDefinitionMap.put("/common/css/*", "anon");
		filterChainDefinitionMap.put("/common/js/*", "anon");
		filterChainDefinitionMap.put("/common/images/*", "anon");
		filterChainDefinitionMap.put("/common/css/*/*", "anon");
		filterChainDefinitionMap.put("/common/js/*/*", "anon");
		filterChainDefinitionMap.put("/common/js/*/*/*", "anon");
		filterChainDefinitionMap.put("/common/images/*/**", "anon");
		
		filterChainDefinitionMap.put("/system/css/*", "anon");
		filterChainDefinitionMap.put("/system/js/*", "anon");
		filterChainDefinitionMap.put("/system/js/*/*", "anon");
		filterChainDefinitionMap.put("/system/js/*/*/*", "anon");
		filterChainDefinitionMap.put("/system/images/*/**", "anon");
		filterChainDefinitionMap.put("/system/layui/*", "anon");
		filterChainDefinitionMap.put("/system/layui/*/**", "anon");	
		
		
		
		filterChainDefinitionMap.put("/system/loginAction", "anon");
		//验证码
		filterChainDefinitionMap.put("/system/captchaImage", "anon");
		/*filterChainDefinitionMap.put("/app/{os}/shoes/**", "anon");
		filterChainDefinitionMap.put("/app/{os}/shoesorder/**", "anon");
		filterChainDefinitionMap.put("/app/{os}/user/**", "anon");
*/		
		
		
		filterChainDefinitionMap.put("/app/**", "anon");
		filterChainDefinitionMap.put("/shoe/**", "anon");
//		特殊不需要权限验证
		filterChainDefinitionMap.put("/device/id", "anon");
		
		//<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/**", "authc");
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/system/login");
		// 登录成功后要跳转的链接
		//shiroFilterFactoryBean.setSuccessUrl("/system/index");

		//未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}


	@Bean(name = "myShiroRealm")
	public SysShiroRealm myShiroRealm(){
		SysShiroRealm mgrShiroRealm = new SysShiroRealm();
		mgrShiroRealm.setCredentialsMatcher(new SysCredentialsMatcher());
		return mgrShiroRealm;
	}
	

	@Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }
    


	@Bean(name="securityManager")
	public DefaultWebSecurityManager securityManager(){
//		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
//		securityManager.setRealm(myShiroRealm);
//		securityManager.setCacheManager(cacheManager);
//		securityManager.setSessionManager(sessionManager);
		
		
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现 使用redis,注入缓存管理器;
        securityManager.setCacheManager(cacheManager());//这个如果执行多次，权限也是同样的一个对象;
        // 自定义session管理 使用redis
        securityManager.setSessionManager(defaultWebSessionManager());
        //注入记住我管理器;
        //securityManager.setRememberMeManager(rememberMeManager());
		
		return securityManager;
	}
	
	/**
	 * 
	 * 描述:生命周期控制器
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

	/**
	 *  开启shiro aop注解支持.
	 *  使用代理方式;所以需要开启代码支持;
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean(name="simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver
	createSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
		mappings.setProperty("UnauthorizedException","403");
		r.setExceptionMappings(mappings);  // None by default
		r.setDefaultErrorView("error");    // No default
		r.setExceptionAttribute("ex");     // Default is "exception"
		//r.setWarnLogCategory("example.MvcLogger");     // No default
		return r;
	}
}