package com.sd.common.aop;

import com.sd.dto.InvokeResult;
import com.sd.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Package: com.sd.common.aop.GlobalException
 * @Description: 
 * @author sudan
 * @date 2020/6/1 13:53
 */
 
@ControllerAdvice({"com.sd"})
@Slf4j
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public InvokeResult exceptionHandler(HttpServletResponse response,Exception exception){
        log.error("系统小差");
        exception.printStackTrace();
        return InvokeResult.fail("系统开小差");
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public InvokeResult businessExceptionHandler(HttpServletResponse response,BusinessException exception){
        response.setStatus(HttpStatus.OK.value());
        int status = exception.getStatus();
        Object object = exception.getObject();
        return InvokeResult.fail(status,exception.getMessage(),object);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public InvokeResult methodArgumentNotValidException(HttpServletResponse response,MethodArgumentNotValidException exception){
        response.setStatus(HttpStatus.OK.value());
        BindingResult bindingResult = exception.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String message = "";
        for (ObjectError allError : allErrors) {
            message +=allError.getDefaultMessage()+";";
        }
        return InvokeResult.fail(message);
    }

}