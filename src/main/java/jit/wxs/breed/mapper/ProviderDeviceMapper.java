package jit.wxs.breed.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import jit.wxs.breed.domain.entity.ProviderDevice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 供应商设备表 Mapper 接口
 * </p>
 *
 * @author jitwxs
 * @since 2018-05-16
 */
public interface ProviderDeviceMapper extends BaseMapper<ProviderDevice> {
    @Select("SELECT p.* FROM `provider_device` \n" +
            "AS p LEFT JOIN device AS d ON p.device_id = d.id\n" +
            "WHERE p.provider_id=#{providerId} \n" +
            "AND d.type=#{type} AND p.status=1 AND p.stock>0;")
    /**
     * 根据设备类型查询供应商设备
     * 只查询上架且库存大于0的商品
     * @param providerId 供应商ID
     * @param type 设备类型
     * @author jitwxs
     * @since 2018/5/28 14:45
     */
    List<ProviderDevice> selectByType(@Param("providerId") String providerId, @Param("type") Integer type);

    @Select("SELECT count(*) != 0 FROM `provider_device` \n" +
            "WHERE provider_id=#{providerId} AND device_id=#{id} \n" +
            "AND stock > 0 AND `status` = 1")
    /**
     * 判断设备是否可用
     * 上架 && 库存 > 0
     * @author jitwxs
     * @since 2018/5/28 17:02
     */
    Boolean hasAvailability(@Param("providerId") String providerId, @Param("id") String id);
}
