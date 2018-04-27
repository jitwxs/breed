package jit.wxs.breed.security;

import jit.wxs.breed.common.utils.PasswordUtils;
import jit.wxs.breed.entity.SysUser;
import jit.wxs.breed.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author jitwxs
 * @date 2018/3/29 19:49
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private SysUserService userService;

    @Value("${session.key.userId}")
    private String SESSION_KEY_USER_ID;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        String inputName = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();

        // userDetails一定非空，否则会抛异常
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(inputName);

        // 如果是手机号码登陆，且密码为空，无需对密码进行验证（因为对于验证码登陆，已经做过了验证）
        if (!(StringUtils.isNumeric(inputName) && inputName.length() == 11 && StringUtils.isBlank(inputPassword))) {
            if (!PasswordUtils.validatePassword(inputPassword, userDetails.getPassword())) {
                throw new BadCredentialsException("密码错误，登录失败！");
            }
        }

        //将用户id写入Session
        SysUser user;
        if (StringUtils.isNumeric(inputName) && inputName.length() == 11) {
            user = userService.getByTel(inputName);
        } else {
            user = userService.getByUserName(inputName);
        }
        //获取当前线程绑定的request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_KEY_USER_ID, user.getId());

        return new UsernamePasswordAuthenticationToken(inputName, inputPassword, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
