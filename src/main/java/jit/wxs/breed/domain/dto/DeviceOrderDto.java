package jit.wxs.breed.domain.dto;

import jit.wxs.breed.domain.entity.DeviceOrder;

/**
 * @author jitwxs
 * @since 2018/5/17 18:27
 */
public class DeviceOrderDto extends DeviceOrder {
    /**
     * 供应商名
     */
    private String providerName;

    /**
     * 客户名
     */
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}
