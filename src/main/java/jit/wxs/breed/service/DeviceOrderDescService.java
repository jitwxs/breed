package jit.wxs.breed.service;

import com.baomidou.mybatisplus.service.IService;
import jit.wxs.breed.domain.dto.DeviceOrderDescDto;
import jit.wxs.breed.domain.entity.DeviceOrderDesc;

import java.util.List;

/**
 * <p>
 * 订单详情 服务类
 * </p>
 *
 * @author jitwxs
 * @since 2018-05-21
 */
public interface DeviceOrderDescService extends IService<DeviceOrderDesc> {
    /**
     * 根据订单ID获取订单详情列表
     * @author jitwxs
     * @since 2018/5/21 21:18
     */
    List<DeviceOrderDescDto> listByDeviceOrder(String deviceOrder);
}
