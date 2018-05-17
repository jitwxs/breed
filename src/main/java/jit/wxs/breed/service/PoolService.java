package jit.wxs.breed.service;

import com.baomidou.mybatisplus.service.IService;
import jit.wxs.breed.domain.entity.Pool;

import java.util.List;

/**
 * <p>
 * 塘口表 服务类
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
public interface PoolService extends IService<Pool> {
    /**
     * 获取用户所有塘口
     * @author jitwxs
     * @since 2018/4/25 18:20
     */
    List<Pool> listByUserId(String uid);

    List<Pool> listByName(String name, String uid);

    Pool getById(String id, String uid);

    boolean deleteById(String id, String uid);

    /**
     * 获取塘口名
     * @author jitwxs
     * @since 2018/4/26 15:55
     */
    String getName(String id);

    /**
     * 统计塘口数量
     * @author jitwxs
     * @since 2018/4/27 12:18
     */
    int countPool(String userId);
}
