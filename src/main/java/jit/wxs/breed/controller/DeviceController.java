package jit.wxs.breed.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import jit.wxs.breed.common.entity.GlobalFunction;
import jit.wxs.breed.common.entity.Msg;
import jit.wxs.breed.entity.Device;
import jit.wxs.breed.entity.UserDevice;
import jit.wxs.breed.entity.UserDeviceDto;
import jit.wxs.breed.service.DeviceService;
import jit.wxs.breed.service.PoolService;
import jit.wxs.breed.service.UserDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 设备Controller
 * @author jitwxs
 * @date 2018/4/25 15:53
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private UserDeviceService userDeviceService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private PoolService poolService;

    private UserDeviceDto userDevice2Dto(UserDevice userDevice) {
        UserDeviceDto dto = new UserDeviceDto();
        BeanUtils.copyProperties(userDevice,dto);
        dto.setDeviceName(deviceService.getName(userDevice.getDeviceId()));
        if(StringUtils.isNotBlank(userDevice.getPoolId())) {
            dto.setPoolName(poolService.getName(userDevice.getPoolId()));
        }
        return dto;
    }

    private List<UserDeviceDto> userDevices2Dto(List<UserDevice> userDevices) {
        List<UserDeviceDto> result = new ArrayList<>();
        for(UserDevice userDevice : userDevices) {
            UserDeviceDto dto = userDevice2Dto(userDevice);
            result.add(dto);
        }
        return result;
    }

    /**
     * 获取所有设备列表
     * @author jitwxs
     * @since 2018/4/27 10:47
     */
    @GetMapping("/list")
    public Msg listDevice() {
        List<Device> devices = deviceService.selectList(null);
        return Msg.ok(null, devices);
    }

    /**
     * 获取用户设备列表
     * @author jitwxs
     * @since 2018/4/27 10:37
     */
    @GetMapping(value = {"/","/imei/"})
    public Msg listUserDevice() {
        String userId = GlobalFunction.getCurrentUserId();
        List<UserDevice> userDevices = userDeviceService.listByUserId(userId);
        return Msg.ok(null, userDevices2Dto(userDevices));
    }

    /**
     * 根据IMEI查询设备
     * @author jitwxs
     * @since 2018/4/27 11:08
     */
    @GetMapping("/imei/{imei}")
    public Msg listUserDeviceByIMEI(@PathVariable String imei) {
        String userId = GlobalFunction.getCurrentUserId();
        List<UserDevice> userDevices = userDeviceService.selectList(new EntityWrapper<UserDevice>()
            .eq("user_id",userId).like("imei",imei));
        return Msg.ok(null, userDevices2Dto(userDevices));
    }

    /**
     * 添加设备
     * @author jitwxs
     * @since 2018/4/27 10:53
     */
    @PostMapping("/")
    public Msg addDevice(UserDevice userDevice) {
        userDevice.setUserId(GlobalFunction.getCurrentUserId());
        userDevice.setCreateDate(new Date());
        userDeviceService.insert(userDevice);
        return Msg.ok();
    }

    /**
     * 删除设备
     * @author jitwxs
     * @since 2018/4/27 10:37
     */
    @DeleteMapping("/{id}")
    public Msg deleteDevice(@PathVariable String id) {
        String userId = GlobalFunction.getCurrentUserId();
        boolean b = userDeviceService.deleteDevice(id, userId);

        return b ? Msg.ok() : Msg.error("删除失败");
    }
}
