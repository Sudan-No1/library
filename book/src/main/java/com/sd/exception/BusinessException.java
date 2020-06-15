package com.sd.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @Package: com.sd.exception.BusinessException
 * @Description: 
 * @author sudan
 * @date 2020/6/1 14:11
 */
@Data
public class BusinessException extends RuntimeException{

    private int status;
    private String errorCode;
    private Object object;

    public BusinessException(){
        this.status = -1;
    }

    public BusinessException(String message){
        super(message);
        this.status = -1;
    }

    public BusinessException(String message,Object object){
        super(message);
        this.status = -1;
        this.object = object;
    }

}