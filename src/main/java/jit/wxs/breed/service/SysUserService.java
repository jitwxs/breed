package jit.wxs.breed.service;

import com.baomidou.mybatisplus.service.IService;
import jit.wxs.breed.domain.entity.SysUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据手机号查询用户是否存在，存在返回true
     * @author jitwxs
     * @since 2018/4/25 14:35
     */
    boolean hasExistByTel(String tel);

    /**
     * 根据用户名查询用户是否存在，存在返回true
     * @author jitwxs
     * @since 2018/4/25 14:36
     */
    boolean hasExistByUserName(String username);

    /**
     * 根据用户名查询用户
     * @author jitwxs
     * @since 2018/4/25 14:37
     */
    SysUser getByUserName(String username);

    /**
     * 根据手机号查询用户
     * @author jitwxs
     * @since 2018/4/25 14:38
     */
    SysUser getByTel(String tel);

    /**
     * 根据Id查询用户，隐藏密码
     * @author jitwxs
     * @since 2018/4/27 11:31
     */
    SysUser getById(String userId);

    /**
     * 根据手机号和密码校验
     * @author jitwxs
     * @since 2018/4/25 14:48
     */
    boolean checkByTelAndPassword(String tel, String password);

    /**
     * 根据用户名和密码校验
     * @author jitwxs
     * @since 2018/4/25 14:48
     */
    boolean checkByUserAndPassword(String username, String password);
}
