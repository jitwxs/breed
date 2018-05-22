package jit.wxs.breed.controller.provider;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import jit.wxs.breed.controller.GlobalFunction;
import jit.wxs.breed.domain.bo.DeviceOrderSelectWrapper;
import jit.wxs.breed.domain.dto.DeviceOrderDescDto;
import jit.wxs.breed.domain.entity.DeviceOrder;
import jit.wxs.breed.service.DeviceOrderDescService;
import jit.wxs.breed.service.DeviceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private GlobalFunction globalFunction;

    /**
     * 获取供应商所有订单记录
     * @author jitwxs
     * @since 2018/5/17 15:46
     */
    @GetMapping("/list")
    public Map listOrder(Integer rows, Integer page, DeviceOrderSelectWrapper dosw) {
        EntityWrapper<DeviceOrder> wrapper = globalFunction.getDeviceOrderWrapper(dosw);
        wrapper.eq("provider_id", globalFunction.getCurrentUserId());

        Page<DeviceOrder> selectPage = deviceOrderService.selectPage(new Page<>(page, rows),wrapper);
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
}
