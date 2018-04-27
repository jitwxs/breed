package jit.wxs.breed.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import jit.wxs.breed.entity.Pool;
import jit.wxs.breed.mapper.PoolMapper;
import jit.wxs.breed.service.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 塘口表 服务实现类
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
@Service
public class PoolServiceImpl extends ServiceImpl<PoolMapper, Pool> implements PoolService {
    @Autowired
    private PoolMapper poolMapper;

    @Override
    public List<Pool> listByUserId(String uid) {
        return poolMapper.selectList(new EntityWrapper<Pool>().eq("user_id", uid));
    }

    @Override
    public List<Pool> listByName(String name, String uid) {
        return poolMapper.selectList(new EntityWrapper<Pool>().eq("user_id", uid).like("name",name));
    }

    @Override
    public Pool getById(String id, String uid) {
        Pool pool = poolMapper.selectById(id);
        if(pool != null && pool.getUserId().equals(uid)) {
            return pool;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteById(String id, String uid) {
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        map.put("user_id", uid);
        int i = poolMapper.deleteByMap(map);

        return !(i == 0);
    }

    @Override
    public String getName(String id) {
        Pool pool = poolMapper.selectById(id);
        if(pool == null ) {
            return null;
        } else {
            return pool.getName();
        }
    }

    @Override
    public int countPool(String userId) {
        return poolMapper.selectCount(new EntityWrapper<Pool>().eq("user_id",userId));
    }
}
