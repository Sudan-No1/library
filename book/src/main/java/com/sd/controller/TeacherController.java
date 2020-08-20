package com.sd.controller;

import com.sd.common.annotation.BehaviorLog;
import com.sd.dto.InvokeResult;
import com.sd.dto.Page;
import com.sd.dto.student.StudentDto;
import com.sd.dto.student.StudentQueryDto;
import com.sd.dto.teacher.TeacherDto;
import com.sd.dto.teacher.TeacherQueryDto;
import com.sd.model.StudentInfo;
import com.sd.service.StudentService;
import com.sd.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Package: com.sd.controller.StudentController
 * @Description: 
 * @author sudan
 * @date 2020/8/19 9:27
 */
 
@RestController
@RequestMapping("teacher")
public class TeacherController {
    
    @Autowired
    private TeacherService teacherService;
    
    @PostMapping("add")
    @BehaviorLog("新增老师")
    public InvokeResult<Void> add(@RequestBody TeacherDto teacherDto){
        teacherService.add(teacherDto);
        return InvokeResult.success();
    }

    @PostMapping("update")
    @BehaviorLog("更新老师")
    public InvokeResult<Void> update(@RequestBody TeacherDto teacherDto){
        teacherService.update(teacherDto);
        return InvokeResult.success();
    }

    @GetMapping("query/{teacherNo}")
    @BehaviorLog("根据学号查询老师信息")
    public InvokeResult<TeacherDto> queryByStudentNo(@PathVariable String teacherNo){
        TeacherDto teacherDto = teacherService.queryByStudentNo(teacherNo);
        return InvokeResult.success(teacherDto);
    }

    @PostMapping("page")
    @BehaviorLog("分页查询老师信息")
    public InvokeResult<Page<TeacherDto>> queryPage(@RequestBody TeacherQueryDto teacherQueryDto){
        Page<TeacherDto> page = teacherService.queryPage(teacherQueryDto);
        return InvokeResult.success(page);
    }

}