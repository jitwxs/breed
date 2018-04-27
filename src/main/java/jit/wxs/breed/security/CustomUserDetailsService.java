package jit.wxs.breed.security;

import jit.wxs.breed.entity.SysRole;
import jit.wxs.breed.entity.SysUser;
import jit.wxs.breed.entity.SysUserRole;
import jit.wxs.breed.service.SysRoleService;
import jit.wxs.breed.service.SysUserRoleService;
import jit.wxs.breed.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author jitwxs
 * @date 2018/3/30 9:17
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserRoleService userRoleService;


    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        SysUser user;
        if (StringUtils.isNumeric(loginName) && loginName.length() == 11) {
            user = userService.getByTel(loginName);
            // 验证码登陆，如果用户为空，创建用户
            if(user == null) {
                user = new SysUser();
                user.setTel(loginName);
                user.setCreateDate(new Date());
                userService.insert(user);
                // 新用户默认权限为普通用户
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId("ROLE_USER");
                userRole.setUserId(user.getId());
                userRoleService.insert(userRole);
            }
        } else {
            user = userService.getByUserName(loginName);
            // 只有用户名登陆才需要判断用户是否存在
            if(user == null) {
                throw new UsernameNotFoundException("用户名不存在");
            }
        }

        // 添加权限
        List<SysUserRole> userRoles = userRoleService.listByUserId(user.getId());
        for (SysUserRole userRole : userRoles) {
            SysRole role = roleService.selectById(userRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }


        // 如果用户没有密码,就随便放一个,不然下面返回会出异常
        if(user.getPassword() == null) {
            user.setPassword("这是随便输入的,并没有实际意义,也不会存到数据库");
        }
        // 返回UserDetails实现类
        return new User(loginName, user.getPassword(), authorities);
    }
}
