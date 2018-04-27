package jit.wxs.breed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转Controller
 * @className PageController
 * @author jitwxs
 * @since 2018/4/25 14:26
 */
@Controller
public class PageController {
    @GetMapping("/sso")
    public String showSSO() {
        return "sso";
    }

    @RequestMapping("/home")
    public String showHome() {
        return "home";
    }

    @GetMapping("/pool")
    public String showPool() {
        return "pool";
    }

    @GetMapping("/device")
    public String showDevice() {
        return "device";
    }

    @GetMapping("/system")
    public String showSystem() {
        return "system";
    }
}
