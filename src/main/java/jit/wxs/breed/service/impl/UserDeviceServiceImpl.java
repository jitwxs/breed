package jit.wxs.breed.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import jit.wxs.breed.common.entity.GlobalFunction;
import jit.wxs.breed.entity.UserDevice;
import jit.wxs.breed.mapper.UserDeviceMapper;
import jit.wxs.breed.service.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户设备关联表 服务实现类
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
@Service
public class UserDeviceServiceImpl extends ServiceImpl<UserDeviceMapper, UserDevice> implements UserDeviceService {
    @Autowired
    private UserDeviceMapper userDeviceMapper;

    @Override
    public List<UserDevice> listByUserId(String userId) {
        return userDeviceMapper.selectList(new EntityWrapper<UserDevice>()
                .eq("user_id", userId));
    }

    @Override
    public List<UserDevice> listByPool(String poolId, String userId) {
        return userDeviceMapper.selectList(new EntityWrapper<UserDevice>()
                .eq("user_id", userId)
                .eq("pool_id", poolId));
    }

    public Map<String,List<UserDevice>> listByPoolAndDeviceId(String poolId, String userId) {
        List<String> deviceIds = userDeviceMapper.listDeviceIdByPool(userId, poolId);
        Map<String,List<UserDevice>> map = new HashMap<>();
        for(String deviceId : deviceIds) {
            List<UserDevice> userDevices = userDeviceMapper.selectList(new EntityWrapper<UserDevice>()
                    .eq("user_id", userId)
                    .eq("pool_id", poolId)
                    .eq("device_id", deviceId));
            map.put(deviceId, userDevices);
        }
        return map;
    }

    @Override
    public boolean unbindDevice(String id, String userId) {
        UserDevice userDevice = userDeviceMapper.selectById(id);
        if(!userId.equals(userDevice.getUserId())) {
            return false;
        }
        userDevice.setPoolId(null);
        Integer i = userDeviceMapper.updateById(userDevice);
        return i != 0;
    }

    @Override
    public boolean bindDevice(String id, String poolId) {
        UserDevice userDevice = userDeviceMapper.selectById(id);
        userDevice.setPoolId(poolId);
        Integer i = userDeviceMapper.updateById(userDevice);

        return i != 0;
    }

    @Override
    public boolean deleteDevice(String id, String userId) {
        Integer i = userDeviceMapper.delete(new EntityWrapper<UserDevice>()
                .eq("id", id)
                .eq("user_id", userId));

        return i != 0;
    }

    @Override
    public int countDevice(String userId) {
        return userDeviceMapper.selectCount(new EntityWrapper<UserDevice>().eq("user_id",userId));
    }

    @Override
    public int countDeviceWithStatus(String userId, Integer status) {
        return userDeviceMapper.selectCount(new EntityWrapper<UserDevice>().eq("user_id",userId).eq("status",status));
    }

    @Override
    public int changeStatus(String id, Integer status) {
        UserDevice userDevice = userDeviceMapper.selectById(id);
        userDevice.setStatus(status);
        userDeviceMapper.updateById(userDevice);
        return 0;
    }
}
