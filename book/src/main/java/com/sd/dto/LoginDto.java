package com.sd.dto;

import lombok.Data;

/**
 * @Package: com.sd.dto.UserDto
 * @Description: 
 * @author sudan
 * @date 2020/8/21 10:54
 */
 
@Data
public class LoginDto {

    /**登录账号*/
    private String loginName;
    /**登录密码*/
    private String password;
    /**角色*/
    private String role;

}