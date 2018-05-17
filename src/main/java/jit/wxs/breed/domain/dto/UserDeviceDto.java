package jit.wxs.breed.domain.dto;

import jit.wxs.breed.domain.entity.UserDevice;

/**
 * @author jitwxs
 * @date 2018/4/26 15:45
 */
public class UserDeviceDto extends UserDevice {
    private String deviceName;

    private String poolName;

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
