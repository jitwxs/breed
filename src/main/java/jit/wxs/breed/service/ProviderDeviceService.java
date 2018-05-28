package jit.wxs.breed.service;

import com.baomidou.mybatisplus.service.IService;
import jit.wxs.breed.domain.entity.ProviderDevice;

import java.util.List;

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

    /**
     * 根据类别获取设备列表
     * @param providerId 供应商ID
     * @param type 谁被类型
     * @author jitwxs
     * @since 2018/5/28 14:13
     */
    List<ProviderDevice> selectByType(String providerId, Integer type);

    /**
     * 判断设备是否可用
     * 上架 && 库存 > 0
     * @author jitwxs
     * @since 2018/5/28 17:01
     */
    Boolean hasAvailability(String providerId, String id);
}
