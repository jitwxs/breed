package jit.wxs.breed.controller.user;

import jit.wxs.breed.domain.Msg;
import jit.wxs.breed.service.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 塘口设备Controller
 * @author jitwxs
 * @date 2018/4/27 9:27
 */
@RestController
@RequestMapping("/user/pool/device")
public class PoolDeviceController {
    @Autowired
    private UserDeviceService userDeviceService;

    /**
     * 将设备从塘口中解绑
     * @author jitwxs
     * @since 2018/4/27 9:29
     */
    @PostMapping("/unbind")
    public Msg unbindDevice(String[] ids) {
        for(String id : ids) {
            userDeviceService.unbindDevice(id);
        }
        return Msg.ok();
    }

    /**
     * 绑定设备到塘口
     * @author jitwxs
     * @since 2018/4/27 9:58
     */
    @PostMapping("/bind")
    public Msg bindDevice(String[] ids, String poolId) {
        for(String id : ids) {
            userDeviceService.bindDevice(id, poolId);
        }
        return Msg.ok();
    }
}
