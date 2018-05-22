package jit.wxs.breed.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import jit.wxs.breed.domain.dto.DeviceOrderDescDto;
import jit.wxs.breed.domain.entity.DeviceOrderDesc;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 订单详情 Mapper 接口
 * </p>
 *
 * @author jitwxs
 * @since 2018-05-21
 */
public interface DeviceOrderDescMapper extends BaseMapper<DeviceOrderDesc> {

    /**
     * 根据订单ID获取订单详情列表
     * @author jitwxs
     * @since 2018/5/21 21:20
     */
    @Select("SELECT\n" +
            "	dod.*,\n" +
            "	d.NAME AS `device_name` \n" +
            "FROM\n" +
            "	`device_order_desc` AS dod\n" +
            "	LEFT JOIN `device` AS d ON dod.device_id = d.id \n" +
            "WHERE\n" +
            "	dod.device_order = #{deviceOrder}")
    List<DeviceOrderDescDto> listByDeviceOrder(String deviceOrder);

}
