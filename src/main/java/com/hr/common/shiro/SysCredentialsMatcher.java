package com.hr.common.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.hr.util.MD5Util;

/**
 * 自定义密码校验规则
 * @author xxu
 *
 */
public class SysCredentialsMatcher extends SimpleCredentialsMatcher{
	
	@Override 
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Object tokenCredentials = encrypt(String.valueOf(token.getPassword()));  
        Object accountCredentials = getCredentials(info);
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false  
        return equals(tokenCredentials, accountCredentials);  
	}
	
    //将传进来密码加密方法  
    private String encrypt(String data) {
    		return MD5Util.qrsoftMD5(data);
    }
}
