package jit.wxs.breed.activemq;

import jit.wxs.breed.common.entity.Task;
import jit.wxs.breed.common.utils.JsonUtils;
import jit.wxs.breed.service.UserDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 设备消费者
 * @author jitwxs
 * @since 2018/5/8 16:58
 */
@Component
public class DeviceConsumer {
    @Autowired
    private UserDeviceService userDeviceService;

    @JmsListener(destination = "MQ_QUEUE_DEVICE")
    public void receiveQueue(String text) {
        if(StringUtils.isNotBlank(text)){
            System.out.println("接收时间：" + new Date());
            Task task = JsonUtils.jsonToPojo(text, Task.class);

            /*
             * 包含：status：0：关闭；1：启动；userDeviceId
             */
            Map<String, Object> map = task.getData();
            Integer status = (Integer) map.get("status");
            String userDeviceId = (String)map.get("userDeviceId");

            // 更新数据库
            userDeviceService.changeStatus(userDeviceId, status);
        }
    }
}
