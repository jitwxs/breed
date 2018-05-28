package jit.wxs.breed.controller;

import com.qcloud.cos.model.ObjectMetadata;
import jit.wxs.breed.domain.Msg;
import jit.wxs.breed.utils.CosClientUtils;
import jit.wxs.breed.utils.PasswordUtils;
import jit.wxs.breed.utils.SmsUtils;
import jit.wxs.breed.domain.entity.SysRole;
import jit.wxs.breed.domain.entity.SysUser;
import jit.wxs.breed.domain.entity.SysUserRole;
import jit.wxs.breed.service.SysRoleService;
import jit.wxs.breed.service.SysUserRoleService;
import jit.wxs.breed.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 用户Controller
 * @author jitwxs
 * @date 2018/4/25 15:15
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private GlobalFunction globalFunction;

    @Value("${session.key.verify.tel}")
    private String SESSION_KEY_VERIFY_TEL;
    @Value("${session.key.verify.code}")
    private String SESSION_KEY_VERIFY_CODE;

    @Value("${cos.secretId}")
    private String secretId;

    @Value("${cos.secretKey}")
    private String secretKey;

    @Value("${cos.regionName}")
    private String regionName;

    @Value("${cos.bucketName}")
    private String bucketName;

    @Value("${cos.pic.upload}")
    private String picUploadPath;

    /**
     * 获取个人信息
     * @author jitwxs
     * @since 2018/4/25 15:16
     */
    @GetMapping("")
    public Msg getUserInfo() {
        String userId = globalFunction.getCurrentUserId();
        SysUser user = userService.selectById(userId);

        List<SysUserRole> userRoles = userRoleService.listByUserId(userId);
        List<SysRole> roles = new ArrayList<>();
        for (SysUserRole userRole : userRoles) {
            SysRole role = roleService.selectById(userRole.getRoleId());
            roles.add(role);
        }

        Map<String, Object> map = new HashMap<>(16);
        map.put("user", user);
        map.put("roles", roles);

        return Msg.ok(null,map);
    }

    /**
     * 更新个人信息
     * @author jitwxs
     * @since 2018/4/27 13:25
     */
    @PostMapping("")
    public Msg saveUserInfo(HttpServletRequest request) throws Exception {
        SysUser user = userService.selectById(globalFunction.getCurrentUserId());
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;

        // 遍历普通参数（即formData的fileName和fileSize）
        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            String val = req.getParameter(key);
            switch (key) {
                case "email":
                    user.setEmail(val);
                    break;
                case "sex":
                    user.setSex(val);
                    break;
                default:break;
            }
        }

        // 遍历文件参数（即formData的file），只取一个，即头像
        Iterator<String> iterator = req.getFileNames();
        if(iterator.hasNext()) {
            MultipartFile file = req.getFile(iterator.next());
            // 获取流
            InputStream in = file.getInputStream();
            // 准备ObjectMetadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            // 上传到COS
            CosClientUtils myCosClient = new CosClientUtils(secretId,secretKey,regionName);
            String url = myCosClient.upload(bucketName, in,metadata, picUploadPath + "/" + file.getOriginalFilename());
            user.setIcon(url);
        }

        userService.updateById(user);

        return Msg.ok();
    }

    /**
     * 设置用户名(用户名不支持修改)
     * @author jitwxs
     * @since 2018/4/25 15:20
     */
    @PostMapping("/username")
    public Msg setUsername(String username) {
        String userId = globalFunction.getCurrentUserId();
        SysUser user = userService.selectById(userId);
        if(user == null || StringUtils.isNotBlank(user.getUsername())) {
            return Msg.error("身份验证失败或用户名非空");
        }

        if(userService.hasExistByUserName(username)) {
            return Msg.error("用户名已被注册，请更换用户名");
        }

        user.setUsername(username);
        userService.updateById(user);

        return Msg.ok();
    }

    /**
     * 设置密码(密码不存在情况下)
     */
    @PostMapping("/password")
    public Msg setPassword(String password) {
        String userId = globalFunction.getCurrentUserId();
        SysUser user = userService.selectById(userId);
        if(user == null || StringUtils.isNotBlank(user.getPassword())) {
            return Msg.error("身份验证失败或密码非空");
        }

        user.setPassword(PasswordUtils.encryptPassword(password));
        userService.updateById(user);

        return Msg.ok();
    }

    /**
     * 修改密码
     * @author jitwxs
     * @since 2018/4/25 15:37
     */
    @PatchMapping("/password")
    public Msg modifyPassword(String oldPassword, String newPassword) {
        if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            return Msg.error("参数错误");
        }

        String userId = globalFunction.getCurrentUserId();
        SysUser user = userService.selectById(userId);
        if(user == null || StringUtils.isBlank(user.getPassword())) {
            return Msg.error("身份验证失败或原密码不存在");
        }

        if(!PasswordUtils.validatePassword(oldPassword, user.getPassword())) {
            return Msg.error("原密码错误");
        }

        user.setPassword(PasswordUtils.encryptPassword(newPassword));
        userService.updateById(user);

        return Msg.ok();
    }

    /**
     * 设置手机号
     * @author jitwxs
     * @since 2018/4/25 15:32
     * @param tel 手机号
     * @param code 短信验证码
     */
    @PostMapping("/tel")
    public Msg bindTel(String tel, String code, HttpServletRequest request) {
        // 校验验证码
        HttpSession session = request.getSession();
        String sessionTel = (String) session.getAttribute(SESSION_KEY_VERIFY_TEL);
        String sessionCode = (String) session.getAttribute(SESSION_KEY_VERIFY_CODE);
        Msg msg =  SmsUtils.check(sessionTel, sessionCode,tel,code);
        if(!msg.getStatus()) {
            return msg;
        }

        String userId = globalFunction.getCurrentUserId();
        SysUser user = userService.selectById(userId);
        if(user == null ) {
            return Msg.error("身份验证失败或密码非空");
        }

        user.setTel(tel);
        userService.updateById(user);

        return Msg.ok();
    }

    /**
     * 重置密码(使用手机验证码重置)
     * @author jitwxs
     * @since 2018/4/25 15:28
     * @param tel 账户绑定的手机号
     * @param password 新密码
     * @param code 短信验证码
     */
    @PostMapping("/restPassword")
    public Msg restPassword(String tel, String password, String code, HttpServletRequest request) {
        if (StringUtils.isBlank(tel) || StringUtils.isBlank(password) || StringUtils.isBlank(code)) {
            return new Msg(false, "参数错误", null);
        }

        // 校验验证码
        HttpSession session = request.getSession();
        String sessionTel = (String) session.getAttribute(SESSION_KEY_VERIFY_TEL);
        String sessionCode = (String) session.getAttribute(SESSION_KEY_VERIFY_CODE);
        Msg msg =  SmsUtils.check(sessionTel, sessionCode,tel,code);
        if(!msg.getStatus()) {
            return msg;
        }

        SysUser user = userService.getByTel(tel);
        if(user == null) {
            return Msg.error("该手机号未被注册");
        }

        user.setPassword(PasswordUtils.encryptPassword(password));
        userService.updateById(user);

        return Msg.ok();
    }

    /**
     * wangEditor上传
     * @author jitwxs
     * @since 2018/5/22 18:30
     */
    @PostMapping("/editor/upload")
    public Map upload(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(16);
        List<String> data = new ArrayList<>();

        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        // 遍历文件参数（即formData的file）
        Iterator<String> iterator = req.getFileNames();

        try {
            while(iterator.hasNext()) {
                MultipartFile file = req.getFile(iterator.next());
                // 获取流
                InputStream in = file.getInputStream();
                // 准备ObjectMetadata
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(file.getContentType());
                metadata.setContentLength(file.getSize());

                // 上传到COS
                CosClientUtils myCosClient = new CosClientUtils(secretId,secretKey,regionName);
                String url = myCosClient.upload(bucketName, in,metadata, picUploadPath + "/" + file.getOriginalFilename());

                data.add(url);
            }
        } catch (IOException e) {
            e.printStackTrace();

            map.put("errno",1);
            return map;
        }

        map.put("errno",0);
        map.put("data", data);
        return map;
    }
}
