package jit.wxs.breed.domain.bo;

import java.io.Serializable;

/**
 * 供应商设备查询条件
 * @author jitwxs
 * @since 2018/5/17 16:00
 */
public class ProviderDeviceSelectWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 设备状态
     */
    private Boolean status;

    /**
     * 设备名
     */
    private String deviceName;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
