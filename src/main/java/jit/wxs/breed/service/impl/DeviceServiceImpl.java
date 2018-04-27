package jit.wxs.breed.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import jit.wxs.breed.entity.Device;
import jit.wxs.breed.mapper.DeviceMapper;
import jit.wxs.breed.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public String getName(String id) {
        Device device = deviceMapper.selectById(id);

        return device.getName();
    }
}
