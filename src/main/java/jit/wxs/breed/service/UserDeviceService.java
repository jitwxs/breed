package jit.wxs.breed.service;

import com.baomidou.mybatisplus.service.IService;
import jit.wxs.breed.entity.UserDevice;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户设备关联表 服务类
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
public interface UserDeviceService extends IService<UserDevice> {
    /**
     * 获取用户所有设备
     * @author jitwxs
     * @since 2018/4/26 15:45
     */
    List<UserDevice> listByUserId(String userId);

    /**
     * 获取指定塘口所有设备
     * @author jitwxs
     * @since 2018/4/25 21:39
     */
    List<UserDevice> listByPool(String poolId, String userId);

    /**
     * 获取指定塘口所有设备，按设备类别分类
     * K:设备ID
     * @author jitwxs
     * @since 2018/4/25 21:39
     */
    Map<String,List<UserDevice>> listByPoolAndDeviceId(String poolId, String userId);

    /**
     * 解绑设备
     * @author jitwxs
     * @since 2018/4/27 9:40
     */
    boolean unbindDevice(String id,String userId);

    /**
     * 绑定设备
     * @author jitwxs
     * @since 2018/4/27 10:00
     */
    boolean bindDevice(String id, String poolId);

    /**
     * 删除设备
     * @author jitwxs
     * @since 2018/4/27 10:38
     */
    boolean deleteDevice(String id, String userID);

    /**
     * 统计用户设备数
     * @author jitwxs
     * @since 2018/4/27 12:20
     */
    int countDevice(String userId);

    /**
     * 根据状态统计用户设备数
     * @author jitwxs
     * @since 2018/4/27 12:20
     */
    int countDeviceWithStatus(String userId, Integer status);

    /**
     * 改变状态
     * @param status 状态：0：关闭；1：启动
     * @author jitwxs
     * @since 2018/5/8 21:48
     */
    int changeStatus(String id, Integer status);
}
