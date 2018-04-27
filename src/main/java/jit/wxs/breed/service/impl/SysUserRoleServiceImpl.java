package jit.wxs.breed.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import jit.wxs.breed.entity.SysUserRole;
import jit.wxs.breed.mapper.SysUserRoleMapper;
import jit.wxs.breed.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户权限表 服务实现类
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    public List<SysUserRole> listByUserId(String userId) {
        return userRoleMapper.selectList(new EntityWrapper<SysUserRole>().eq("user_id",userId));
    }
}
