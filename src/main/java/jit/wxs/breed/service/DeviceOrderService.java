package jit.wxs.breed.service;

import jit.wxs.breed.domain.entity.DeviceOrder;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 设备订单表 服务类
 * </p>
 *
 * @author jitwxs
 * @since 2018-05-16
 */
public interface DeviceOrderService extends IService<DeviceOrder> {
    /**
     * 计算某一用户总消费金额
     * @author jitwxs
     * @since 2018/5/17 19:18
     */
    float sumTotalConsume(String userId);
}
