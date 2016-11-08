package me.jcala.xmarket.server.ctrl;

import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理类
 */
@ControllerAdvice
@Slf4j
class CtrlExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity exceptionHandlerProd(RuntimeException e) {
        log.warn(e.getLocalizedMessage());
        return  RespFactory.INSTANCE().serverError();
    }
}
