package me.jcala.xmarket.server.ctrl;

import me.jcala.xmarket.server.conf.RestIni;
import me.jcala.xmarket.server.entity.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Result<String> exceptionHandler(RuntimeException e) {
        LOGGER.warn(e.getLocalizedMessage());
        Result<String> result=new Result<>();
        result.setCode(RestIni.serverErr);
        result.setMsg(RestIni.serverErrMsg);
        return result;
    }

}
