package com.sd.teaching.service.user;

/**
 * @Package: com.sd.teaching.service.user.UserServiceFactory
 * @Description: 
 * @author sudan
 * @date 2020/7/14 10:47
 */
 
 
public interface UserServiceFactory {

    UserService create(String type);
}