package jit.wxs.breed.service;

import com.baomidou.mybatisplus.service.IService;
import jit.wxs.breed.domain.entity.Device;

import java.util.List;

/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
public interface DeviceService extends IService<Device> {
    /**
     * 获取设备名
     * @author jitwxs
     * @since 2018/4/26 15:48
     */
    String getName(String id);

    List<Device> listByName(String name);

    /**
     * 查询设备是否存在
     * @author jitwxs
     * @since 2018/5/28 16:57
     */
    Boolean hasExist(String id);
}
