package jit.wxs.breed.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
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
    private String modelNum;
    /**
     * 设备描述
     */
    private String description;
    private Date createDate;
    @TableField(update = "now()")
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

    public String getModelNum() {
        return modelNum;
    }

    public void setModelNum(String modelNum) {
        this.modelNum = modelNum;
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

    @Override
    public String toString() {
        return "Device{" +
        ", id=" + id +
        ", name=" + name +
        ", modelNum=" + modelNum +
        ", description=" + description +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
