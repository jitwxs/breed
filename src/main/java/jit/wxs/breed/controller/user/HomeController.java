package jit.wxs.breed.controller.user;

import jit.wxs.breed.controller.GlobalFunction;
import jit.wxs.breed.domain.Msg;
import jit.wxs.breed.service.PoolService;
import jit.wxs.breed.service.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Home页Controller
 * @author jitwxs
 * @date 2018/4/27 11:24
 */
@RestController
@RequestMapping("/user/home")
public class HomeController {
    @Autowired
    private PoolService poolService;
    @Autowired
    private UserDeviceService userDeviceService;
    @Autowired
    private GlobalFunction globalFunction;

    /**
     * 初始化home页信息
     * @author jitwxs
     * @since 2018/4/27 11:25
     */
    @GetMapping("")
    public Msg getHomeInfo() {
        String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
        String userId = globalFunction.getCurrentUserId();

        int poolNum = poolService.countPool(userId);
        int deviceNum = userDeviceService.countDevice(userId);
        int startDeviceNum = userDeviceService.countDeviceWithStatus(userId, 1);
        int stopDeviceNum = deviceNum - startDeviceNum;


        Map<String, Object> map = new HashMap<>();
        map.put("loginName", loginName);
        map.put("poolNum", poolNum);
        map.put("deviceNum", deviceNum);
        map.put("startDeviceNum", startDeviceNum);
        map.put("stopDeviceNum", stopDeviceNum);
        return Msg.ok(null,map);
    }
}
