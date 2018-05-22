package jit.wxs.breed.domain.dto;

import jit.wxs.breed.domain.entity.DeviceOrderDesc;

public class DeviceOrderDescDto extends DeviceOrderDesc {
    /**
     * 设备名
     */
    private String deviceName;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
