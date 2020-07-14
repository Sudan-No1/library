package com.sd.teaching.dto;

import lombok.Data;

/**
 * @Package: com.sd.dto.InvokeResult
 * @Description: 
 * @author sudan
 * @date 2020/6/1 13:55
 */
 
@Data
public class InvokeResult<T> {

    private T data;
    private int code;
    private String message;

    private InvokeResult(){
    }

    public static InvokeResult success(){
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setCode(0);
        invokeResult.setMessage("success");
        return invokeResult;
    }

    public static InvokeResult success(Object data){
        InvokeResult invokeResult = success();
        invokeResult.setData(data);
        return invokeResult;
    }

    public static InvokeResult fail(String message){
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setCode(-1);
        invokeResult.setMessage(message);
        return invokeResult;
    }

    public static InvokeResult fail(int code,String message){
        InvokeResult invokeResult = fail(message);
        invokeResult.setCode(code);
        return invokeResult;
    }

    public static InvokeResult fail(int code,String message,Object data){
        InvokeResult invokeResult = fail(code,message);
        invokeResult.setData(data);
        return invokeResult;
    }

}