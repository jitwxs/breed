package jit.wxs.breed.domain.dto;

import jit.wxs.breed.domain.entity.DeviceOrder;

/**
 * @author jitwxs
 * @since 2018/5/17 18:27
 */
public class DeviceOrderDto extends DeviceOrder {
    /**
     * 设备名
     */
    private String deviceName;

    /**
     * 客户名
     */
    private String userName;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
