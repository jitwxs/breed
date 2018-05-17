package jit.wxs.breed.controller;

import jit.wxs.breed.domain.Msg;
import jit.wxs.breed.utils.PasswordUtils;
import jit.wxs.breed.utils.RandomUtils;
import jit.wxs.breed.utils.SmsUtils;
import jit.wxs.breed.domain.entity.SysUser;
import jit.wxs.breed.domain.entity.SysUserRole;
import jit.wxs.breed.service.SysUserRoleService;
import jit.wxs.breed.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * SSO Controller
 * @author jitwxs
 * @date 2018/4/25 15:02
 */
@RestController
public class LoginController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysUserRoleService userRoleService;

    @Value("${session.key.verify.tel}")
    private String SESSION_KEY_VERIFY_TEL;
    @Value("${session.key.verify.code}")
    private String SESSION_KEY_VERIFY_CODE;
    @Value("${session.key.verify.date}")
    private String SESSION_KEY_VERIFY_DATE;
    @Value("${verify.wait-time}")
    private Long VERITY_WAIT_TIME;

    /* ------------------------以下是登陆相关------------------------ */
    /**
     * 登陆错误处理
     * @author jitwxs
     * @since 2018/4/25 15:04
     */
    @RequestMapping("/login/error")
    public Msg loginError(HttpServletRequest request) {
        AuthenticationException exception = (AuthenticationException) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

        return Msg.error(exception.getMessage(), exception.toString());
    }

    /**
     * 发送验证码
     * @author jitwxs
     * @since 2018/4/25 15:05
     */
    @GetMapping("/sendSms")
    public Msg sendSms(String tel, HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(tel)) {
            return Msg.error("号码错误");
        }

        HttpSession session = request.getSession();
        // 如果Session中验证信息非空，判断是否超过间隔时间
        Date lastDate = (Date) session.getAttribute(SESSION_KEY_VERIFY_DATE);
        if (lastDate != null) {
            long waitTime = (new Date().getTime() - lastDate.getTime()) / 1000;
            if (waitTime < VERITY_WAIT_TIME) {
                return Msg.error("间隔时间过短");
            }
        }

        // 如果Session中验证信息为空，或符合间隔时间，则发送验证码
        String verifyCode = RandomUtils.number(6);
        Msg msg = SmsUtils.send(tel, verifyCode, (VERITY_WAIT_TIME / 60) + "");

        // 如果发送成功，将相关信息存入Session
        if (msg.getStatus()) {
            session.setAttribute(SESSION_KEY_VERIFY_TEL, tel);
            session.setAttribute(SESSION_KEY_VERIFY_CODE, verifyCode);
            session.setAttribute(SESSION_KEY_VERIFY_DATE, new Date());
        }

        return msg;
    }

    /**
     * 登陆验证
     * @author jitwxs
     * @since 2018/4/25 15:08
     */
    @PostMapping("/loginCheck")
    public Msg login(HttpServletRequest request, String loginName, String password, String code) {
        if (StringUtils.isBlank(loginName) || (StringUtils.isBlank(password) && StringUtils.isBlank(code))) {
            return Msg.error("参数错误");
        }

        // 规定loginName全数字且11位为手机登陆，否则为用户名登陆
        Msg msg = null;
        if (StringUtils.isNumeric(loginName) && loginName.length() == 11) {
            if (StringUtils.isNotBlank(password)) {
                msg = loginByTelAndPassword(loginName, password);
            } else if (StringUtils.isNotBlank(code)) {
                msg = loginByTelAndCode(loginName, code, request);
            }
        } else {
            msg = loginByUsernameAndPassword(loginName, password);
        }

        return msg;
    }

    /**
     * 手机+验证码登陆
     * @author jitwxs
     * @since 2018/4/25 15:09
     */
    private Msg loginByTelAndCode(String tel, String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionTel = (String) session.getAttribute(SESSION_KEY_VERIFY_TEL);
        String sessionCode = (String) session.getAttribute(SESSION_KEY_VERIFY_CODE);

        // 验证码登陆无需验证用户是否存在
        return SmsUtils.check(sessionTel, sessionCode,tel,code);
    }

    /**
     * 手机+密码登陆
     * @author jitwxs
     * @since 2018/4/25 15:09
     */
    private Msg loginByTelAndPassword(String tel, String password) {
        boolean b = userService.checkByTelAndPassword(tel, password);
        return b ? Msg.ok() : Msg.error("账户不存在或密码错误");
    }

    /**
     * 用户名+密码登陆
     * @author jitwxs
     * @since 2018/4/25 15:09
     */
    private Msg loginByUsernameAndPassword(String username, String password) {
        boolean b = userService.checkByUserAndPassword(username, password);
        return b ? Msg.ok() : Msg.error("账户不存在或密码错误");
    }

    /* ------------------------以下是注册相关------------------------ */

    /**
     * 注册验证
     * @author jitwxs
     * @since 2018/4/25 15:11
     */
    @PostMapping("/registerCheck")
    public Msg registerCheck(String regName, String verifyCode, HttpServletRequest request) {
        if(StringUtils.isBlank(regName) || StringUtils.isBlank(verifyCode)) {
            return Msg.error("参数错误");
        }

        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("validateCode");
        // 忽略大小写
        code = code.toLowerCase();
        verifyCode = verifyCode.toLowerCase();
        if(StringUtils.isBlank(code) || !code.equals(verifyCode)) {
            return Msg.error("验证码错误");
        }

        if(userService.hasExistByUserName(regName)) {
            return Msg.error("用户名已被注册");
        }

        return Msg.ok();
    }

    /**
     * 注册
     * @author jitwxs
     * @since 2018/4/25 15:12
     */
    @PostMapping("/register")
    public Msg register(String username, String password) {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Msg.error("参数错误");
        }

        // 再次验证，此处可省略
        if(userService.hasExistByUserName(username)) {
            return Msg.error("用户名已被注册");
        }

        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(PasswordUtils.encryptPassword(password));
        user.setCreateDate(new Date());
        userService.insert(user);

        // 新用户默认权限为普通用户
        SysUserRole userRole = new SysUserRole();
        userRole.setRoleId("ROLE_USER");
        userRole.setUserId(user.getId());
        userRoleService.insert(userRole);

        return Msg.ok();
    }

    /**
     * 判断用户名是否被注册
     * @author jitwxs
     * @since 2018/4/25 15:14
     */
    @PostMapping("/checkUsername")
    public Msg checkUsername(String username) {
        boolean b = userService.hasExistByUserName(username);
        return b ? Msg.error("用户名已经被注册") : Msg.ok();
    }

    /**
     * 判断手机号是否被注册
     * @author jitwxs
     * @since 2018/4/25 15:14
     */
    @PostMapping("/checkTel")
    public Msg checkTel(String tel) {
        boolean b = userService.hasExistByTel(tel);
        return b ? Msg.error("手机号已经被注册") : Msg.ok();
    }
}
