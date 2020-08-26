package com.sd.common.util;

import org.springframework.util.DigestUtils;

/**
 * @Package: com.sd.common.util.MD5Util
 * @Description: 
 * @author sudan
 * @date 2020/8/21 11:08
 */
 
 
public class MD5Util {

    public static String encrypt(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());

    }

}