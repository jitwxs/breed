package jit.wxs.breed.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import jit.wxs.breed.entity.SysRole;
import jit.wxs.breed.mapper.SysRoleMapper;
import jit.wxs.breed.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;

    public SysRole selectById(Integer id){
        return roleMapper.selectById(id);
    }
}
