package jit.wxs.breed.controller;

import jit.wxs.breed.common.entity.GlobalFunction;
import jit.wxs.breed.common.entity.Msg;
import jit.wxs.breed.service.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 塘口设备Controller
 * @author jitwxs
 * @date 2018/4/27 9:27
 */
@RestController
@RequestMapping("/pool/device")
public class PoolDeviceController {
    @Autowired
    private UserDeviceService userDeviceService;

    /**
     * 将设备从塘口中解绑
     * @author jitwxs
     * @since 2018/4/27 9:29
     */
    @DeleteMapping("/{id}")
    public Msg unbindDevice(@PathVariable String id) {
        String userId = GlobalFunction.getCurrentUserId();
        boolean b = userDeviceService.unbindDevice(id, userId);
        return b ? Msg.ok() : Msg.error("解绑失败");
    }

    /**
     * 绑定设备到塘口
     * @author jitwxs
     * @since 2018/4/27 9:58
     */
    @PostMapping("/")
    public Msg bindDevice(String id, String poolId) {
        boolean b = userDeviceService.bindDevice(id, poolId);
        return b ? Msg.ok() : Msg.error("绑定失败");
    }
}
