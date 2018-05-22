package jit.wxs.breed.domain.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备型号
     */
    private String model;

    private int type;

    /**
     * 设备图片，多个用空格分隔
     */
    private String picUrl;

    @TableField(exist = false)
    private String[] picUrls;
    /**
     * 设备描述
     */
    private String description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String[] getPicUrls() {
        if(StringUtils.isNotBlank(this.picUrl)) {
            return this.picUrl.trim().split(",");
        } else {
            return null;
        }
    }

    public void setPicUrls(String[] picUrls) {
        this.picUrls = picUrls;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", type=" + type +
                ", picUrl='" + picUrl + '\'' +
                ", picUrls=" + Arrays.toString(picUrls) +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
