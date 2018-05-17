package jit.wxs.breed.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import jit.wxs.breed.domain.Msg;
import jit.wxs.breed.domain.entity.Device;
import jit.wxs.breed.domain.entity.DeviceType;
import jit.wxs.breed.service.DeviceService;
import jit.wxs.breed.service.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 设备Controller
 * @author jitwxs
 * @since 2018/5/17 16:20
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceTypeService deviceTypeService;

    /**
     * 获取单一设备详细信息
     * @author jitwxs
     * @since 2018/5/17 16:21
     */
    @GetMapping("/{id}")
    public Msg getById(@PathVariable String id) {
        Device device = deviceService.selectById(id);

        if(device != null) {
            return Msg.ok(null,device);
        } else {
            return Msg.error("获取设备信息失败");
        }
    }

    /**
     * 获取设备一级类别
     * @author jitwxs
     * @since 2018/5/17 17:00
     */
    @GetMapping("/type/tree")
    public Msg listDeviceRootType() {
        List<DeviceType> rootTree = deviceTypeService.selectList(new EntityWrapper<DeviceType>()
                .isNull("parent_id"));

        return Msg.ok(null,rootTree);
    }

    /**
     * 获取设备二级类别
     * @author jitwxs
     * @since 2018/5/17 17:00
     */
    @GetMapping("/type/tree/{id}")
    public Msg listDeviceRootType(@PathVariable Integer id) {
        List<DeviceType> rootTree = deviceTypeService.selectList(new EntityWrapper<DeviceType>()
                .eq("parent_id", id));

        return Msg.ok(null,rootTree);
    }

    /**
     * 根据类别查询设备
     * @author jitwxs
     * @since 2018/5/17 16:58
     */
    @GetMapping("/type/{type}")
    public Msg listByType(@PathVariable Integer type) {
        List<Device> devices = deviceService.selectList(new EntityWrapper<Device>()
            .eq("type", type));
        return Msg.ok(null,devices);
    }
}
