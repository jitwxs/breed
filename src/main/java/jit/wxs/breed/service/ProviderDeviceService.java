package jit.wxs.breed.service;

import jit.wxs.breed.domain.entity.ProviderDevice;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 供应商设备表 服务类
 * </p>
 *
 * @author jitwxs
 * @since 2018-05-16
 */
public interface ProviderDeviceService extends IService<ProviderDevice> {
    /**
     * 修改设备状态（上架/下架）
     * @author jitwxs
     * @since 2018/5/17 16:15
     */
    void modifyItemStatus(String id, Boolean status);
}
