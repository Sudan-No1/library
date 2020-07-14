package com.sd.teaching.controller;

import com.sd.teaching.common.annotions.BehaviorLog;
import com.sd.teaching.dto.InvokeResult;
import com.sd.teaching.model.StudentInfo;
import com.sd.teaching.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Package: com.sd.teaching.controller.UserController
 * @Description: 
 * @author sudan
 * @date 2020/7/14 10:09
 */
 
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    @BehaviorLog("新增学生信息")
    public InvokeResult<Void> add(@RequestBody StudentInfo studentInfo){
        studentService.add(studentInfo);
        return InvokeResult.success();
    }

    @PostMapping("/update")
    @BehaviorLog("更新学生信息")
    public InvokeResult<Void> update(@RequestBody StudentInfo studentInfo){
        studentService.update(studentInfo);
        return InvokeResult.success();
    }

    @GetMapping("/queryAll")
    @BehaviorLog("查询学生信息")
    public InvokeResult<List<StudentInfo>> queryAll(){
        List<StudentInfo> list = studentService.queryAll();
        return InvokeResult.success(list);
    }

    @GetMapping("/delete/{stuNo}")
    @BehaviorLog("删除学生信息")
    public InvokeResult<Void> delete(@PathVariable String stuNo){
        studentService.delete(stuNo);
        return InvokeResult.success();
    }


}