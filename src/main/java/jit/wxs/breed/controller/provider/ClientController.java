package jit.wxs.breed.controller.provider;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import jit.wxs.breed.controller.GlobalFunction;
import jit.wxs.breed.domain.entity.Client;
import jit.wxs.breed.domain.entity.DeviceOrder;
import jit.wxs.breed.domain.entity.SysUser;
import jit.wxs.breed.service.DeviceOrderService;
import jit.wxs.breed.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户Controller
 * @author jitwxs
 * @since 2018/5/17 18:58
 */
@RestController
@RequestMapping("/provider/client")
public class ClientController {
    @Autowired
    private DeviceOrderService deviceOrderService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private GlobalFunction globalFunction;

    /**
     * 获取所有客户信息，此处前台分页，后台无需分页
     * @author jitwxs
     * @since 2018/5/17 19:14
     */
    @GetMapping("/list")
    public List<Client> listClient() {
        // 获取所有购买过商品的客户列表
        List<DeviceOrder> deviceOrders = deviceOrderService.selectList(new EntityWrapper<DeviceOrder>()
                .eq("provider_id", globalFunction.getCurrentUserId()));
        List<String> ids = new ArrayList<>();
        for(DeviceOrder deviceOrder : deviceOrders) {
            ids.add(deviceOrder.getUserId());
        }
        // 获取所有用户信息
        List<SysUser> users = userService.selectList(new EntityWrapper<SysUser>()
                .in("id", ids));

        // SysUser --> Client
        List<Client> clients = new ArrayList<>();
        for(SysUser user : users) {
            Client client = new Client();
            BeanUtils.copyProperties(user, client);
            // 设置总消费金额
            float price = deviceOrderService.sumTotalConsume(user.getId());
            client.setConsume(price);
            clients.add(client);
        }
        return clients;
    }
}
