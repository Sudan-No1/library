package com.sd.controller;

import com.sd.common.exception.BusinessException;
import com.sd.dto.BorrowInfoDto;
import com.sd.dto.LoginDto;
import com.sd.service.BorrowService;
import com.sd.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package: com.sd.controller.BorrowController
 * @Description: 借书
 * @author sudan
 * @date 2020/5/28 16:07
 */
 
@RestController
@Api("借阅管理")
public class BorrowController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BorrowService borrowService;

    @PostMapping("borrow")
    public void borrow(@RequestBody BorrowInfoDto borrowInfoDto, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isEmpty(authorization)) {
            throw new BusinessException("未登录授权");
        }
        String token = authorization.replace("Bearer ","");
        Claims claims = jwtUtil.parseToken(token);
        LoginDto loginDto = claims.get("loginInfo", LoginDto.class);
        borrowService.borrow(borrowInfoDto,loginDto);
    }

}