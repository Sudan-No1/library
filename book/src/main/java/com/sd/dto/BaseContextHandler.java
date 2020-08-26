package com.sd.dto;

import com.sd.common.exception.BusinessException;

import java.util.HashMap;
import java.util.Map;

import static com.sd.common.constant.BusinessConstant.LOGIN_USER;

/**
 * @Package: com.sd.dto.BaseContextHandler
 * @Description: 
 * @author sudan
 * @date 2020/8/21 10:39
 */
 
 
public class BaseContextHandler {

    private BaseContextHandler(){}

    private static ThreadLocal<LoginDto> threadLocal = new ThreadLocal<>();

    public static void setUser(LoginDto loginDto){
        threadLocal.set(loginDto);
    }

    public static LoginDto getUser(){
        LoginDto loginUser =new LoginDto();
        loginUser.setLoginName("jmz");
        loginUser.setPassword("jmz");
        loginUser.setRole("teacher");
        /*LoginDto loginUser  = threadLocal.get();

        if(loginUser == null){
            throw new BusinessException("用户未登录");
        }*/
        return loginUser;
    }

}