package jit.wxs.breed.domain.dto;

import jit.wxs.breed.domain.entity.Device;
import jit.wxs.breed.domain.entity.ProviderDevice;

/**
 * @author jitwxs
 * @since 2018/5/17 15:25
 */
public class ProviderDeviceDto extends ProviderDevice {
    private Device device;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
