package com.sd.teaching.controller;

import com.sd.teaching.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.sd.teaching.controller.UserController
 * @Description: 
 * @author sudan
 * @date 2020/7/14 10:09
 */
 
@RestController
public class UserController {

    @Autowired
    private UserService userService;

}