package jit.wxs.breed.domain.entity;

import java.io.Serializable;

/**
 * <p>
 * 订单详情
 * </p>
 *
 * @author jitwxs
 * @since 2018-05-21
 */
public class DeviceOrderDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 订单号
     */
    private String deviceOrder;
    /**
     * 设备号
     */
    private String deviceId;
    /**
     * 设备串号
     */
    private String imei;
    /**
     * 价格
     */
    private Float price;
    /**
     * 备注
     */
    private String desc;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceOrder() {
        return deviceOrder;
    }

    public void setDeviceOrder(String deviceOrder) {
        this.deviceOrder = deviceOrder;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "DeviceOrderDesc{" +
                "id='" + id + '\'' +
                ", deviceOrder='" + deviceOrder + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", imei='" + imei + '\'' +
                ", price=" + price +
                ", desc='" + desc + '\'' +
                '}';
    }
}
