package jit.wxs.breed.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 塘口表
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
public class Pool implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 塘口名称
     */
    private String name;
    /**
     * 塘口面积（单位：亩）
     */
    private Double areas;
    /**
     * 塘口深度（单位：米）
     */
    private Double depth;
    /**
     * 塘口中的水产品种（多个用逗号分隔）
     */
    private String aquatic;
    /**
     * 投放密度（单位：尾/亩）
     */
    private Double density;
    /**
     * 所属用户
     */
    private String userId;
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

    public Double getAreas() {
        return areas;
    }

    public void setAreas(Double areas) {
        this.areas = areas;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public String getAquatic() {
        return aquatic;
    }

    public void setAquatic(String aquatic) {
        this.aquatic = aquatic;
    }

    public Double getDensity() {
        return density;
    }

    public void setDensity(Double density) {
        this.density = density;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        return "Pool{" +
        ", id=" + id +
        ", name=" + name +
        ", areas=" + areas +
        ", depth=" + depth +
        ", aquatic=" + aquatic +
        ", density=" + density +
        ", userId=" + userId +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
