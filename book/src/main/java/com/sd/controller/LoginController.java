package com.sd.controller;

import com.sd.dto.InvokeResult;
import com.sd.dto.LoginDto;
import com.sd.dto.UserDto;
import com.sd.service.people.PeopleService;
import com.sd.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.sd.common.constant.BusinessConstant.LOGIN_USER;

/**
 * @Package: com.sd.controller.LoginController
 * @Description: 
 * @author sudan
 * @date 2020/8/21 10:44
 */
 
@RestController
@RequestMapping("user")
public class LoginController {


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PeopleService peopleService;

    @PostMapping("login")
    public InvokeResult<Void> login(@RequestBody LoginDto loginDto, HttpServletRequest request){
        peopleService.login(loginDto);
        String token = jwtUtil.createJwt(loginDto.getPassword(), loginDto.getLoginName(), loginDto);
        request.getSession().setAttribute(LOGIN_USER,loginDto);
        return InvokeResult.success(token);
    }

    @PostMapping("register")
    public InvokeResult<Void> register(@RequestBody UserDto userDto){
        peopleService.addUser(userDto);
        return InvokeResult.success();
    }

}