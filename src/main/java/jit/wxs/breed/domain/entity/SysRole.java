package jit.wxs.breed.domain.entity;

import java.io.Serializable;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;


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

    @Override
    public String toString() {
        return "SysRole{" +
        ", id=" + id +
        ", name=" + name +
        "}";
    }
}
