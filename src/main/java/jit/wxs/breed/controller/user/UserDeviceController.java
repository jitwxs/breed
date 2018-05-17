package jit.wxs.breed.controller.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import jit.wxs.breed.activemq.MQProducer;
import jit.wxs.breed.controller.GlobalFunction;
import jit.wxs.breed.domain.Msg;
import jit.wxs.breed.domain.Task;
import jit.wxs.breed.domain.bo.UserDeviceSelectWrapper;
import jit.wxs.breed.domain.entity.UserDevice;
import jit.wxs.breed.service.UserDeviceService;
import jit.wxs.breed.utils.JsonUtils;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 设备Controller
 * @author jitwxs
 * @date 2018/4/25 15:53
 */
@RestController
@RequestMapping("/user/device")
public class UserDeviceController {
    @Autowired
    private UserDeviceService userDeviceService;
    @Autowired
    private MQProducer MQProducer;
    @Autowired
    private GlobalFunction globalFunction;

    /**
     * 获取用户设备列表
     * @author jitwxs
     * @since 2018/4/27 10:37
     */
    @GetMapping("/list")
    public Map listUserDevice(Integer rows, Integer page, UserDeviceSelectWrapper udsw) {
        EntityWrapper<UserDevice> wrapper = globalFunction.getUserDeviceWrapper(udsw);
        wrapper.eq("user_id", globalFunction.getCurrentUserId());

        Page<UserDevice> selectPage = userDeviceService.selectPage(new Page<>(page, rows), wrapper);

        Map<String,Object> map = new HashMap<>();
        map.put("total", selectPage.getTotal());
        map.put("rows", globalFunction.userDevices2Dto(selectPage.getRecords()));
        return map;
    }

    /**
     * 添加设备
     * @author jitwxs
     * @since 2018/4/27 10:53
     */
    @PostMapping("")
    public Msg addDevice(UserDevice userDevice) {
        userDevice.setUserId(globalFunction.getCurrentUserId());
        userDevice.setCreateDate(new Date());
        userDeviceService.insert(userDevice);
        return Msg.ok();
    }

    /**
     * 获取设备状态
     * @author jitwxs
     * @since 2018/5/8 13:58
     */
    @GetMapping("/status/{id}")
    public Msg getDeviceStatus(@PathVariable String id) {
        Integer status = userDeviceService.selectById(id).getStatus();

        return Msg.ok(null,status);
    }

    /**
     * 设备任务
     * @param type 类型；；0：关闭；1：启动
     * @param second 多少秒后执行
     * @param id 用户设备id
     * @author jitwxs
     * @since 2018/5/8 15:22
     */
    @PostMapping("/task")
    public Msg devTask(Integer type,Long second, String id) {
        // 准备数据
        Map<String,Object> map = new HashMap<>();
        map.put("status", type);
        map.put("userDeviceId",id);

        Task task =  new Task("设备定时任务", map);
        Destination destination = new ActiveMQQueue("MQ_QUEUE_DEVICE");
        // 发送延时消息
        MQProducer.delaySend(destination, JsonUtils.objectToJson(task), second * 1000);
        System.out.println("发送时间：" + new Date());

        return Msg.ok();
    }
}
