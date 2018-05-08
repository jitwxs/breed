package jit.wxs.breed.controller;

import jit.wxs.breed.activemq.MQProducer;
import jit.wxs.breed.common.entity.GlobalFunction;
import jit.wxs.breed.common.entity.Msg;
import jit.wxs.breed.common.entity.Task;
import jit.wxs.breed.common.utils.JsonUtils;
import jit.wxs.breed.service.UserDeviceService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private MQProducer MQProducer;

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
