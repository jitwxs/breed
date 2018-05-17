package jit.wxs.breed.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import jit.wxs.breed.domain.Msg;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 腾讯云短信服务
 * @author jitwxs
 * @date 2018/4/20 16:09
 */
@Component
public class SmsUtils {

    private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);

    private static Integer APP_ID;
    private static String APP_KEY;
    private static Integer TEMPLATE_ID;
    private static String SMS_SIGN;

    @Value("${tencent.sms.app_id}")
    public void setAppId(Integer app_id) {
        APP_ID = app_id;
    }

    @Value("${tencent.sms.app_key}")
    public void setAppKey(String appKey) {
        APP_KEY = appKey;
    }

    @Value("${tencent.sms.tempate_id}")
    public void setTemplateId(Integer templateId) {
        TEMPLATE_ID = templateId;
    }

    @Value("${tencent.sms.sign}")
    public void setSmsSign(String smsSign) {
        SMS_SIGN = smsSign;
    }

    /**
     * 发送短信
     * @param tel 手机号码
     * @param code 验证码
     * @param min 有效时间
     */
    public static Msg send(String tel, String code, String min) throws Exception{
        SmsSingleSender sender = new SmsSingleSender(APP_ID,APP_KEY);
        ArrayList<String> params = new ArrayList<>();
        // 添加模板参数
        params.add(code);
        params.add(min);
        SmsSingleSenderResult result =  sender.sendWithParam("86", tel, TEMPLATE_ID, params, SMS_SIGN, "", "");

        if(result.result == 0) {
            return Msg.ok("验证码发送成功");
        } else {
            logger.error("验证码发送失败",result.toString());
            return Msg.error("验证码发送失败");
        }
    }

    /**
     * 校验短信
     * @author jitwxs
     * @since 2018/4/25 15:33
     */
    public static Msg check(String sessionTel, String sessionCode, String tel, String code) {
        if (StringUtils.isBlank(sessionTel) || StringUtils.isBlank(sessionCode)) {
            return Msg.error("验证码失效，请重新验证");
        }

        //验证手机号码是否一致
        if (!sessionTel.equals(tel)) {
            return Msg.error("注册号码与发送验证码号码不一致");
        }

        //校验验证码是否正确
        if (!sessionCode.equals(code)) {
            return Msg.error("验证码错误");
        }

        // 验证码登陆无需验证用户是否存在
       return Msg.ok("验证成功");
    }
}
