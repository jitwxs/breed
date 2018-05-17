package jit.wxs.breed.service;

import com.baomidou.mybatisplus.service.IService;
import jit.wxs.breed.domain.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户权限表 服务类
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
public interface SysUserRoleService extends IService<SysUserRole> {
    /**
     * 根据用户id查询权限
     * @author jitwxs
     * @since 2018/4/25 14:40
     */
    List<SysUserRole> listByUserId(String userId);
}
