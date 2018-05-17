package jit.wxs.breed.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import jit.wxs.breed.utils.PasswordUtils;
import jit.wxs.breed.domain.entity.SysUser;
import jit.wxs.breed.mapper.SysUserMapper;
import jit.wxs.breed.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper userMapper;

    public boolean hasExistByTel(String tel) {
        List<SysUser> list = userMapper.selectList(new EntityWrapper<SysUser>().eq("tel", tel));
        return !(list == null || list.size() == 0);
    }

    public boolean hasExistByUserName(String username) {
        List<SysUser> list = userMapper.selectList(new EntityWrapper<SysUser>().eq("username",username));
        return !(list == null || list.size() == 0);
    }

    public SysUser getByUserName(String username) {
        List<SysUser> users = userMapper.selectList(new EntityWrapper<SysUser>().eq("username",username));
        if(users == null || users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public SysUser getByTel(String tel) {
        List<SysUser> users = userMapper.selectList(new EntityWrapper<SysUser>().eq("tel", tel));
        if(users == null || users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public SysUser getById(String userId) {
        SysUser user = userMapper.selectById(userId);
        // 为了安全，不暴露密码
        if(user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public boolean checkByTelAndPassword(String tel, String password) {
        SysUser dbUser = getByTel(tel);

        // 需要判断用户是否设置了密码
        if(dbUser == null || StringUtils.isBlank(dbUser.getPassword())) {
            return false;
        }
        return PasswordUtils.validatePassword(password, dbUser.getPassword());
    }

    public boolean checkByUserAndPassword(String username, String password) {
        SysUser dbUser = getByUserName(username);

        if(dbUser == null || StringUtils.isBlank(dbUser.getPassword())) {
            return false;
        }

        return PasswordUtils.validatePassword(password, dbUser.getPassword());
    }
}
