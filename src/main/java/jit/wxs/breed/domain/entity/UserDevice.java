package jit.wxs.breed.domain.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户设备关联表
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
public class UserDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 塘口id，忽略非NULL判断
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String poolId;
    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 设备串号
     */
    private String imei;
    /**
     * 设备参数（JSON格式）
     */
    private String params;
    /**
     * 设备状态（true：开启；false：关闭）
     */
    private Integer status;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
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

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "UserDevice{" +
        ", id=" + id +
        ", userId=" + userId +
        ", poolId=" + poolId +
        ", deviceId=" + deviceId +
        ", imei=" + imei +
        ", params=" + params +
        ", status=" + status +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
