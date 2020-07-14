package com.sd.teaching.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Package: com.sd.teaching.service.user.UserServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/7/14 10:50
 */
 
@Service
public class UserServiceImpl {

    @Autowired
    private UserServiceFactory userServiceFactory;

    public UserService create(String type){
        return userServiceFactory.create(type);
    }

}