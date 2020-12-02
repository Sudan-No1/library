package com.sd.console.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.sd.console.controller.TeacherController
 * @Description: 
 * @author sudan
 * @date 2020/12/2 17:16
 */
 
@RestController
@RequestMapping("teacher")
public class TeacherController {

    @GetMapping("list")
    public String queryList(){
        return "teacherList";
    }

}