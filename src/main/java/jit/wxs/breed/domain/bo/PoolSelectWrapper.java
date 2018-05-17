package jit.wxs.breed.domain.bo;

import java.io.Serializable;

/**
 * 塘口搜索条件
 * @author jitwxs
 * @since 2018/5/17 8:56
 */
public class PoolSelectWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 塘口名称
     */
    private String name;

    /**
     * 水产品种
     */
    private String aquatic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAquatic() {
        return aquatic;
    }

    public void setAquatic(String aquatic) {
        this.aquatic = aquatic;
    }
}
