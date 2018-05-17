package jit.wxs.breed.domain.bo;

import java.io.Serializable;

/**
 * 用户设备搜索条件
 * @author jitwxs
 * @since 2018/5/17 11:30
 */
public class UserDeviceSelectWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 设备名
     */
    private String deviceName;

    /**
     * 设备IMEI
     */
    private String deviceIMEI;

    /**
     * 塘口名
     */
    private String poolName;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIMEI() {
        return deviceIMEI;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }
}
