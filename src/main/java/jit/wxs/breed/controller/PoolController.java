package jit.wxs.breed.controller;

import jit.wxs.breed.common.entity.GlobalFunction;
import jit.wxs.breed.common.entity.Msg;
import jit.wxs.breed.entity.Device;
import jit.wxs.breed.entity.Pool;
import jit.wxs.breed.entity.UserDevice;
import jit.wxs.breed.service.DeviceService;
import jit.wxs.breed.service.PoolService;
import jit.wxs.breed.service.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 塘口Controller
 * @author jitwxs
 * @date 2018/4/25 15:53
 */
@RestController
@RequestMapping("/pool")
public class PoolController {
    @Autowired
    private PoolService poolService;
    @Autowired
    private UserDeviceService userDeviceService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 获取塘口列表
     * @author jitwxs
     * @since 2018/4/25 18:59
     */
    @GetMapping(value = {"/","/name/"})
    public Msg listPool() {
        String  userId = GlobalFunction.getCurrentUserId();
        List<Pool> pools = poolService.listByUserId(userId);

        return Msg.ok(null, pools);
    }

    @GetMapping("/name/{name}")
    public Msg listByName(@PathVariable String name) {
        String  userId = GlobalFunction.getCurrentUserId();
        List<Pool> pools = poolService.listByName(name, userId);;
        return Msg.ok(null, pools);
    }

    /**
     * 新增塘口
     * @author jitwxs
     * @since 2018/4/26 14:56
     */
    @PostMapping("/")
    public Msg addPool(Pool pool) {
        pool.setUserId(GlobalFunction.getCurrentUserId());
        pool.setCreateDate(new Date());
        poolService.insert(pool);
        return Msg.ok();
    }

    @PutMapping("/")
    public Msg updatePool(Pool pool) {
        poolService.updateById(pool);
        return Msg.ok();
    }

    /**
     * 根据id获取塘口
     * @author jitwxs
     * @since 2018/4/25 20:26
     */
    @GetMapping("/{id}")
    public Msg getById(@PathVariable String id) {
        String  userId = GlobalFunction.getCurrentUserId();
        Pool pool = poolService.getById(id, userId);
        return pool == null ? Msg.error("获取失败") : Msg.ok(null,pool);
    }

    /**
     * 根据id删除塘口
     * @author jitwxs
     * @since 2018/4/25 20:29
     */
    @DeleteMapping ("/{id}")
    public Msg deleteById(@PathVariable String id) {
        String  userId = GlobalFunction.getCurrentUserId();
        boolean b = poolService.deleteById(id, userId);
        return b ? Msg.ok() : Msg.error("删除失败");
    }

    /**
     * 获取塘口所有设备
     * @author jitwxs
     * @since 2018/4/25 21:31
     */
    @GetMapping("/{id}/device")
    public Msg listDevice(@PathVariable String id) {
        String  userId = GlobalFunction.getCurrentUserId();
        Map<String, List<UserDevice>> map = userDeviceService.listByPoolAndDeviceId(id, userId);

        // 获取设备ID-->设备名的Map
        Map<String, String> deviceMap = new HashMap<>();
        Set<String> keySet = map.keySet();
        for(String deviceId : keySet) {
            Device device = deviceService.selectById(deviceId);
            deviceMap.put(deviceId,device.getName());
        }

        Map<String,Object> result = new HashMap<>();
        result.put("device",map);
        result.put("deviceName",deviceMap);

        return Msg.ok(null, result);
    }
}
