package jit.wxs.breed.controller.provider;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import jit.wxs.breed.controller.GlobalFunction;
import jit.wxs.breed.domain.Msg;
import jit.wxs.breed.domain.bo.ProviderDeviceSelectWrapper;
import jit.wxs.breed.domain.entity.ProviderDevice;
import jit.wxs.breed.service.ProviderDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品（设备）Controller
 * @author jitwxs
 * @since 2018/5/17 15:45
 */
@RestController
@RequestMapping("/provider/item")
public class ItemController {
    @Autowired
    private ProviderDeviceService providerDeviceService;
    @Autowired
    private GlobalFunction globalFunction;

    /**
     * 获取供应商所有设备
     * @author jitwxs
     * @since 2018/5/17 15:46
     */
    @GetMapping("/list")
    public Map listItem(Integer rows, Integer page, ProviderDeviceSelectWrapper pdsw) {
        EntityWrapper<ProviderDevice> wrapper = globalFunction.getProviderDeviceWrapper(pdsw);
        wrapper.eq("provider_id", globalFunction.getCurrentUserId());

        Page<ProviderDevice> selectPage = providerDeviceService.selectPage(new Page<>(page, rows),wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("total", selectPage.getTotal());
        map.put("rows", globalFunction.providerDevice2Dto(selectPage.getRecords()));
        return map;
    }

    /**
     * 获得单条商品信息
     */
    @GetMapping("/{id}")
    public Msg getById(@PathVariable String id) {
        ProviderDevice providerDevice = providerDeviceService.selectById(id);
        if(providerDevice != null) {
            return Msg.ok(null, globalFunction.providerDevice2Dto(providerDevice));
        } else {
            return Msg.error("获取商品信息失败");
        }
    }

    /**
     * 更新供应商商品信息
     * @author jitwxs
     * @since 2018/5/17 16:42
     */
    @PutMapping("")
    public Msg updateItem(ProviderDevice providerDevice) {
        if(providerDevice != null && StringUtils.isNotBlank(providerDevice.getId())) {
            providerDeviceService.updateById(providerDevice);
            return Msg.ok();
        } else {
            return Msg.error("参数错误");
        }
    }

    /**
     * 设备的批量上架
     * @author jitwxs
     * @since 2018/5/17 16:14
     */
    @PostMapping("/up")
    public Msg upItem(String[] ids) {
        for(String id : ids) {
            providerDeviceService.modifyItemStatus(id, true);
        }
        return Msg.ok();
    }

    /**
     * 设备的批量下架
     * @author jitwxs
     * @since 2018/5/17 16:14
     */
    @PostMapping("/down")
    public Msg downItem(String[] ids) {
        for(String id : ids) {
            providerDeviceService.modifyItemStatus(id, false);
        }
        return Msg.ok();
    }

    /**
     * 新增商品
     * @author jitwxs
     * @since 2018/5/17 17:30
     */
    @PostMapping("")
    public Msg addItem(ProviderDevice providerDevice) {
        providerDevice.setProviderId(globalFunction.getCurrentUserId());
        providerDevice.setCreateDate(new Date());

        providerDeviceService.insert(providerDevice);

        return Msg.ok();
    }
}
