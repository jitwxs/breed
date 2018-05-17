package jit.wxs.breed.service.impl;

import jit.wxs.breed.domain.entity.ProviderDevice;
import jit.wxs.breed.mapper.ProviderDeviceMapper;
import jit.wxs.breed.service.ProviderDeviceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 供应商设备表 服务实现类
 * </p>
 *
 * @author jitwxs
 * @since 2018-05-16
 */
@Service
public class ProviderDeviceServiceImpl extends ServiceImpl<ProviderDeviceMapper, ProviderDevice> implements ProviderDeviceService {
    @Autowired
    private ProviderDeviceMapper providerDeviceMapper;

    @Override
    public void modifyItemStatus(String id, Boolean status) {
        ProviderDevice providerDevice = providerDeviceMapper.selectById(id);
        providerDevice.setStatus(status);
        providerDeviceMapper.updateById(providerDevice);
    }
}
