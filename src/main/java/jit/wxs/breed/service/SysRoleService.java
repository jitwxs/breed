package jit.wxs.breed.service;

import com.baomidou.mybatisplus.service.IService;
import jit.wxs.breed.entity.SysRole;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据ID查询
     * @author jitwxs
     * @since 2018/4/25 14:32
     */
    SysRole selectById(Integer id);
}
