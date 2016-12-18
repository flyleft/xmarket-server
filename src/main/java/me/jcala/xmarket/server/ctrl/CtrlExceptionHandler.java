package me.jcala.xmarket.server.ctrl;

import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理类
 */
@ControllerAdvice
@Slf4j
class CtrlExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandlerProd(Exception e) {
        log.warn(e.getLocalizedMessage());
        return  RespFactory.INSTANCE().serverError();
    }
}
