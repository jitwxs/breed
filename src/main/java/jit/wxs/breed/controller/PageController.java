package jit.wxs.breed.controller;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

/**
 * 页面跳转Controller
 * @author jitwxs
 * @since 2018/4/25 14:26
 */
@Controller
public class PageController {
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @RequestMapping("/")
    public String showHome() {
        Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        if(roles.contains("ROLE_供应商")) {
            return "/provider/home";
        } else if(roles.contains("ROLE_用户")) {
            return "/user/home";
        }
        return "sso";
    }

    @GetMapping("/user/pool")
    public String showUserPool() {
        return "/user/pool";
    }

    @GetMapping("/user/device")
    public String showUserDevice() {
        return "/user/device";
    }

    @GetMapping("/user/system")
    public String showUserSystem() {
        return "/user/system";
    }

    @GetMapping("/provider/item")
    public String showProviderItem() {
        return "/provider/item";
    }

    @GetMapping("/provider/order")
    public String showProviderOrder() {
        return "/provider/order";
    }

    @GetMapping("/provider/client")
    public String showProviderClient() {
        return "/provider/client";
    }

    @GetMapping("/provider/maintenance")
    public String showMaintenance() {
        return "/provider/maintenance";
    }

    @GetMapping("/provider/system")
    public String showProviderSystem() {
        return "/provider/system";
    }
}
