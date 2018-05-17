package jit.wxs.breed.mapper;

import jit.wxs.breed.domain.entity.DeviceOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 设备订单表 Mapper 接口
 * </p>
 *
 * @author jitwxs
 * @since 2018-05-16
 */
public interface DeviceOrderMapper extends BaseMapper<DeviceOrder> {
    @Select("SELECT sum(price) FROM device_order WHERE user_id = #{userId}")
    Float countTotalConsume(String userId);
}
