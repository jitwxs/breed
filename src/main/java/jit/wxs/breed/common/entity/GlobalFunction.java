package jit.wxs.breed.common.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    /**
     * 获取当前登陆用户id
     * @author jitwxs
     * @since 2018/4/25 15:19
     */
    public static String getCurrentUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return (String)session.getAttribute(SESSION_KEY_USER_ID);
    }
}
