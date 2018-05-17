package jit.wxs.breed.domain.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备订单表
 * </p>
 *
 * @author jitwxs
 * @since 2018-05-16
 */
public class DeviceOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 设备ID
     */
    private String deviceId;
    /**
     * 供应商ID
     */
    private String providerId;
    /**
     * 购买者（用户）ID
     */
    private String userId;
    /**
     * 设备串号
     */
    private String imei;
    /**
     * 售价
     */
    private Float price;
    /**
     * 备注
     */
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @TableField(update = "now()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "DeviceOrder{" +
        ", id=" + id +
        ", deviceId=" + deviceId +
        ", providerId=" + providerId +
        ", userId=" + userId +
        ", imei=" + imei +
        ", price=" + price +
        ", remark=" + remark +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
