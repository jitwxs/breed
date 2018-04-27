package jit.wxs.breed.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import jit.wxs.breed.entity.UserDevice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户设备关联表 Mapper 接口
 * </p>
 *
 * @author jitwxs
 * @since 2018-04-25
 */
public interface UserDeviceMapper extends BaseMapper<UserDevice> {
    /**
     * 获取用户某个塘口中的设备种类名
     * @author jitwxs
     * @since 2018/4/25 23:10
     */
    @Select("SELECT device_id from user_device WHERE user_id=#{uid} AND pool_id=#{poolId} GROUP BY device_id")
    List<String> listDeviceIdByPool(@Param("uid") String uid, @Param("poolId") String poolId);
}
