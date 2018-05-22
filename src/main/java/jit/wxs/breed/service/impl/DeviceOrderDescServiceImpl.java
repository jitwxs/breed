package jit.wxs.breed.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import jit.wxs.breed.domain.dto.DeviceOrderDescDto;
import jit.wxs.breed.domain.entity.DeviceOrderDesc;
import jit.wxs.breed.mapper.DeviceOrderDescMapper;
import jit.wxs.breed.service.DeviceOrderDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单详情 服务实现类
 * </p>
 *
 * @author jitwxs
 * @since 2018-05-21
 */
@Service
public class DeviceOrderDescServiceImpl extends ServiceImpl<DeviceOrderDescMapper, DeviceOrderDesc> implements DeviceOrderDescService {
    @Autowired
    private DeviceOrderDescMapper deviceOrderDescMapper;

    @Override
    public List<DeviceOrderDescDto> listByDeviceOrder(String deviceOrder) {
        return deviceOrderDescMapper.listByDeviceOrder(deviceOrder);
    }
}
