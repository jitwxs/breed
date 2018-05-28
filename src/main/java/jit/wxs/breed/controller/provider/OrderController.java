package jit.wxs.breed.controller.provider;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import jit.wxs.breed.controller.GlobalFunction;
import jit.wxs.breed.domain.Msg;
import jit.wxs.breed.domain.bo.DeviceOrderSelectWrapper;
import jit.wxs.breed.domain.dto.DeviceOrderDescDto;
import jit.wxs.breed.domain.entity.DeviceOrder;
import jit.wxs.breed.domain.entity.DeviceOrderDesc;
import jit.wxs.breed.service.DeviceOrderDescService;
import jit.wxs.breed.service.DeviceOrderService;
import jit.wxs.breed.service.SysUserService;
import jit.wxs.breed.utils.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 供应商订单Controller
 * @author jitwxs
 * @since 2018/5/17 18:24
 */
@RestController
@RequestMapping("/provider/order")
public class OrderController {
    @Autowired
    private DeviceOrderService deviceOrderService;
    @Autowired
    private DeviceOrderDescService deviceOrderDescService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private GlobalFunction globalFunction;

    /**
     * 获取供应商所有订单记录
     * @author jitwxs
     * @since 2018/5/17 15:46
     */
    @GetMapping("/list")
    public Map listOrder(Integer rows, Integer page, String order, String sort, DeviceOrderSelectWrapper dosw) {
        EntityWrapper<DeviceOrder> wrapper = globalFunction.getDeviceOrderWrapper(dosw);
        wrapper.eq("provider_id", globalFunction.getCurrentUserId());

        Page<DeviceOrder> selectPage = deviceOrderService.selectPage(
                new Page<>(page, rows,sort,"asc".equals(order)),wrapper);
        Map<String,Object> map = new HashMap<>(16);
        map.put("total", selectPage.getTotal());
        map.put("rows", globalFunction.deviceOrder2Dto(selectPage.getRecords()));
        return map;
    }

    /**
     * 获取订单详细信息
     * @author jitwxs
     * @since 2018/5/21 21:49
     */
    @GetMapping("/desc")
    public Map getOrderDesc(String orderId) {
        List<DeviceOrderDescDto> list = deviceOrderDescService.listByDeviceOrder(orderId);

        Map<String,Object> map = new HashMap<>(16);
        map.put("total", list.size());
        map.put("rows", list);
        return map;
    }

    /**
     * 删除订单
     * @author jitwxs
     * @since 2018/5/28 17:31
     */
    @PostMapping("/delete")
    public Msg deleteById(String[] ids) {
        for (String id: ids) {
            deviceOrderService.deleteById(id);
        }
        return Msg.ok();
    }

    /**
     * 新增订单
     * @author jitwxs
     * @since 2018/5/28 15:34
     */
    @PostMapping("")
    @Transactional(rollbackFor = Exception.class)
    public Msg createOrder(String clientName, String remark, String[] deviceId, String[] imei, Float[] price, String[] desc) {
        if(StringUtils.isBlank(clientName)) {
            return Msg.error("客户不能为空");
        }
        if(deviceId.length == 0 || deviceId.length != imei.length || imei.length != price.length) {
            return Msg.error("商品列表为空或字段不匹配");
        }

        // 创建订单
        String orderId = RandomUtils.timeId();
        DeviceOrder order = new DeviceOrder();
        order.setCreateDate(new Date());
        order.setId(orderId);
        order.setProviderId(globalFunction.getCurrentUserId());
        order.setUserId(userService.getByUserName(clientName).getId());
        if(StringUtils.isNotBlank(remark)) {
            order.setRemark(remark);
        }
        // 计算总价
        float totalPrice = 0;
        for(float p : price) {
            totalPrice+=p;
        }
        order.setPrice(totalPrice);
        deviceOrderService.insert(order);

        // 创建订单详情
        for(int i=0; i<deviceId.length;i++) {
            DeviceOrderDesc orderDesc = new DeviceOrderDesc();
            orderDesc.setDeviceId(deviceId[i]);
            orderDesc.setDeviceOrder(orderId);
            orderDesc.setImei(imei[i]);
            orderDesc.setPrice(price[i]);
            if(StringUtils.isNotBlank(desc[i])) {
                orderDesc.setDesc(desc[i]);
            }
            deviceOrderDescService.insert(orderDesc);
        }

        return Msg.ok();
    }
}
