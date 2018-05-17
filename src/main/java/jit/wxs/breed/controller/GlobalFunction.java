package jit.wxs.breed.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import jit.wxs.breed.domain.bo.DeviceOrderSelectWrapper;
import jit.wxs.breed.domain.bo.PoolSelectWrapper;
import jit.wxs.breed.domain.bo.ProviderDeviceSelectWrapper;
import jit.wxs.breed.domain.bo.UserDeviceSelectWrapper;
import jit.wxs.breed.domain.dto.DeviceOrderDto;
import jit.wxs.breed.domain.dto.ProviderDeviceDto;
import jit.wxs.breed.domain.dto.UserDeviceDto;
import jit.wxs.breed.domain.entity.*;
import jit.wxs.breed.service.DeviceService;
import jit.wxs.breed.service.PoolService;
import jit.wxs.breed.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jitwxs
 * @date 2018/4/25 20:58
 */
@Component
public class GlobalFunction {
    private static String SESSION_KEY_USER_ID;
    @Value("${session.key.userId}")
    public void setKeyUserId(String keyUserId) {
        SESSION_KEY_USER_ID = keyUserId;
    }
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private PoolService poolService;
    @Autowired
    private SysUserService userService;

    /**
     * 获取当前登陆用户id
     * @author jitwxs
     * @since 2018/4/25 15:19
     */
    public String getCurrentUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return (String)session.getAttribute(SESSION_KEY_USER_ID);
    }

    /**
     * 构造筛选条件
     * @author jitwxs
     * @since 2018/5/13 15:50
     */
    public EntityWrapper<Pool> getPoolWrapper(PoolSelectWrapper psw) {
        EntityWrapper<Pool> entityWrapper = new EntityWrapper<>();
        if(StringUtils.isNotBlank(psw.getName())){
            entityWrapper.like("name", psw.getName());
        }
        if(StringUtils.isNotBlank(psw.getAquatic())){
            entityWrapper.like("aquatic", psw.getAquatic());
        }

        return entityWrapper;
    }

    /**
     * 构造筛选条件
     * @author jitwxs
     * @since 2018/5/13 15:50
     */
    public EntityWrapper<UserDevice> getUserDeviceWrapper(UserDeviceSelectWrapper udsw) {
        String userId = getCurrentUserId();
        EntityWrapper<UserDevice> entityWrapper = new EntityWrapper<>();

        if(StringUtils.isNotBlank(udsw.getDeviceName())){
            List<Device> devices = deviceService.listByName(udsw.getDeviceName());
            List<String> ids = new ArrayList<>();
            for(Device device : devices) {
                ids.add(device.getId());
            }
            entityWrapper.in("device_id",ids);
        }

        if(StringUtils.isNotBlank(udsw.getDeviceIMEI())){
            entityWrapper.eq("imei", udsw.getDeviceIMEI());
        }

        if(StringUtils.isNotBlank(udsw.getPoolName())){
            List<Pool> pools = poolService.listByName(udsw.getPoolName(), userId);
            List<String> ids = new ArrayList<>();
            for(Pool pool : pools) {
                ids.add(pool.getId());
            }
            entityWrapper.in("pool_id", ids);
        }

        return entityWrapper;
    }

    public UserDeviceDto userDevice2Dto(UserDevice userDevice) {
        UserDeviceDto dto = new UserDeviceDto();
        BeanUtils.copyProperties(userDevice,dto);
        dto.setDeviceName(deviceService.getName(userDevice.getDeviceId()));
        if(StringUtils.isNotBlank(userDevice.getPoolId())) {
            dto.setPoolName(poolService.getName(userDevice.getPoolId()));
        }
        return dto;
    }

    public List<UserDeviceDto> userDevices2Dto(List<UserDevice> userDevices) {
        List<UserDeviceDto> result = new ArrayList<>();
        for(UserDevice userDevice : userDevices) {
            UserDeviceDto dto = userDevice2Dto(userDevice);
            result.add(dto);
        }
        return result;
    }

    public ProviderDeviceDto providerDevice2Dto(ProviderDevice providerDevice) {
        ProviderDeviceDto dto = new ProviderDeviceDto();
        BeanUtils.copyProperties(providerDevice, dto);
        Device device = deviceService.selectById(providerDevice.getDeviceId());
        dto.setDevice(device);
        return dto;
    }

    public List<ProviderDeviceDto> providerDevice2Dto(List<ProviderDevice> list){
        List<ProviderDeviceDto> result = new ArrayList<>();
        for(ProviderDevice providerDevice : list) {
            ProviderDeviceDto  dto = providerDevice2Dto(providerDevice);
            result.add(dto);
        }
        return result;
    }

    public EntityWrapper<ProviderDevice> getProviderDeviceWrapper(ProviderDeviceSelectWrapper pdsw) {
        EntityWrapper<ProviderDevice> entityWrapper = new EntityWrapper<>();
        if(pdsw.getStatus() != null) {
            entityWrapper.eq("status", pdsw.getStatus());
        }
        if(StringUtils.isNotBlank(pdsw.getDeviceName())) {
            List<Device> devices = deviceService.listByName(pdsw.getDeviceName());
            List<String> ids = new ArrayList<>();
            for(Device device : devices) {
                ids.add(device.getId());
            }
            entityWrapper.in("device_id",ids);
        }
        return entityWrapper;
    }

    public DeviceOrderDto deviceOrder2Dto(DeviceOrder deviceOrder) {
        DeviceOrderDto dto = new DeviceOrderDto();
        BeanUtils.copyProperties(deviceOrder, dto);
        Device device = deviceService.selectById(deviceOrder.getDeviceId());
        SysUser user = userService.selectById(deviceOrder.getUserId());
        dto.setDeviceName(device.getName());
        dto.setUserName(user.getUsername());

        return dto;
    }

    public List<DeviceOrderDto> deviceOrder2Dto(List<DeviceOrder> deviceOrders) {
        List<DeviceOrderDto> result = new ArrayList<>();
        for(DeviceOrder deviceOrder : deviceOrders) {
            DeviceOrderDto dto  = deviceOrder2Dto(deviceOrder);
            result.add(dto);
        }
        return result;
    }

    public EntityWrapper<DeviceOrder> getDeviceOrderWrapper(DeviceOrderSelectWrapper dosw){
        EntityWrapper<DeviceOrder> entityWrapper = new EntityWrapper<>();
        if(StringUtils.isNotBlank(dosw.getDeviceName())) {
            List<Device> list = deviceService.listByName(dosw.getDeviceName());
            List<String> ids = new ArrayList<>();
            for(Device device : list) {
                ids.add(device.getId());
            }
            entityWrapper.in("device_id",ids);
        }

        if(StringUtils.isNotBlank(dosw.getUserName())) {
            SysUser user = userService.getByUserName(dosw.getUserName());
            if(user != null) {
                entityWrapper.eq("user_id",user.getId());
            }
        }

        if(StringUtils.isNotBlank(dosw.getImei())) {
            entityWrapper.eq("imei",dosw.getImei());
        }

        Date nowDate = new Date();
        if(dosw.getInnerDate() != null) {
            entityWrapper.between("create_date", DateUtils.addDays(nowDate,-1 * dosw.getInnerDate()), nowDate);
        }

        if(dosw.getStartDate() != null && dosw.getEndDate() != null) {
            entityWrapper.between("create_date",dosw.getStartDate(),dosw.getEndDate());
        }

        return entityWrapper;
    }
}
