package com.sd.teaching.service.user.impl;

import com.sd.teaching.service.user.UserService;
import com.sd.teaching.service.user.UserServiceFactory;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Package: com.sd.teaching.service.user.impl.UserServiceFactoryImpl
 * @Description: 
 * @author sudan
 * @date 2020/7/14 10:48
 */
 
@Service
public class UserServiceFactoryImpl implements UserServiceFactory {

    @Resource
    private List<UserService> userServiceList;

    @Autowired
    private NullUserServiceImpl nullUserService;

    @Override
    public UserService create(String type) {
        if(userServiceList == null){
            return nullUserService;
        }
        for(UserService userService : userServiceList){
            if(userService.getEnum().getType().equals(type)){
                return userService;
            }
        }
        return nullUserService;
    }

}