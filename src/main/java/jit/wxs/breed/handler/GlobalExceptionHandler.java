package jit.wxs.breed.handler;

import jit.wxs.breed.common.entity.Msg;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jitwxs
 * @date 2018/4/25 16:43
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Msg globalException(HttpServletRequest request, Exception e) {
        return Msg.error(e.getMessage(),request.getRequestURL().toString());
    }
}
