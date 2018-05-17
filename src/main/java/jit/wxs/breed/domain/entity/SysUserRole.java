package jit.wxs.breed.domain.entity;

import java.io.Serializable;

/**
 * <p>
 * 用户权限表
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String userId;
    private String roleId;


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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysUserRole{" +
        ", id=" + id +
        ", userId=" + userId +
        ", roleId=" + roleId +
        "}";
    }
}
