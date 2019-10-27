package com.hr.common.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.DomainPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hr.bean.system.SysRole;
import com.hr.bean.system.SysUsers;
import com.hr.mapper.system.SysPermissionsMapper;
import com.hr.mapper.system.SysRoleMapper;
import com.hr.mapper.system.SysUsersMapper;

@Component
@Configuration
public class SysShiroRealm extends AuthorizingRealm {
	
	
	@Autowired
	private SysUsersMapper sysUsersMapper;
	@Autowired
	private SysPermissionsMapper sysPermissionsMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUsers sysUsers  = (SysUsers)principals.getPrimaryPrincipal();
        
        SysRole sysRole=sysRoleMapper.findSysRoleById(sysUsers.getRole_id());
        if(sysRole!=null && sysRole.getId()>0){
        		authorizationInfo.addRole(sysRole.getRole_name());
        		String rolePermission=sysRole.getRole_permissions();
        		String menuPermission=sysRole.getRole_menu();
        		
        		Permission permission=new DomainPermission(rolePermission,menuPermission);
        		authorizationInfo.addObjectPermission(permission);

        		
        		if(!StringUtils.isEmpty(rolePermission)){
        			String[] permArray=rolePermission.split(",");
        			for(int i=0;i<permArray.length;i++){
        				authorizationInfo.addStringPermission(permArray[i]);
        				
        			}
        			
        		}
        		
        		if(!StringUtils.isEmpty(menuPermission)){
        			String[] permArray=menuPermission.split(",");
        			for(int i=0;i<permArray.length;i++){
        				authorizationInfo.addStringPermission(permArray[i]);
        				
        			}
        			
        		}
        		
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号.
        String userName = (String)token.getPrincipal();
        
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        List<SysUsers> userLists=sysUsersMapper.findSysUsersByUserName(userName);
        if(userLists==null){
        		return null;
        }
        if(userLists.size()<=0){
        		return null;
        }
        SysUsers userInfo =  userLists.get(0);
        if(userInfo == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
    		    userInfo, //用户名
            userInfo.getPasswd(), //密码
            ByteSource.Util.bytes(userInfo.getId()+""),
            getName()  //realm name
        	);
        return authenticationInfo;
    }
    


}