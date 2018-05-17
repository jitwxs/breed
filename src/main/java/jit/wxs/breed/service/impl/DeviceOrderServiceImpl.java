package jit.wxs.breed.service.impl;

import jit.wxs.breed.domain.entity.DeviceOrder;
import jit.wxs.breed.mapper.DeviceOrderMapper;
import jit.wxs.breed.service.DeviceOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备订单表 服务实现类
 * </p>
 *
 * @author jitwxs
 * @since 2018-05-16
 */
@Service
public class DeviceOrderServiceImpl extends ServiceImpl<DeviceOrderMapper, DeviceOrder> implements DeviceOrderService {
    @Autowired
    private DeviceOrderMapper deviceOrderMapper;

    @Override
    public float sumTotalConsume(String userId) {
        Float sum = deviceOrderMapper.countTotalConsume(userId);
        if(sum == null) {
            return 0;
        }
        return sum;
    }
}
