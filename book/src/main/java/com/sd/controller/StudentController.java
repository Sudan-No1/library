package com.sd.controller;

import com.sd.common.annotation.BehaviorLog;
import com.sd.dto.InvokeResult;
import com.sd.dto.Page;
import com.sd.dto.student.StudentDto;
import com.sd.dto.student.StudentQueryDto;
import com.sd.model.StudentInfo;
import com.sd.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Package: com.sd.controller.StudentController
 * @Description: 
 * @author sudan
 * @date 2020/8/19 9:27
 */
 
@RestController
@RequestMapping("student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @PostMapping("add")
    @BehaviorLog("新增学生")
    public InvokeResult<Void> add(@RequestBody StudentDto studentDto){
        studentService.add(studentDto);
        return InvokeResult.success();
    }

    @PostMapping("es/add")
    @BehaviorLog("新增学生")
    public InvokeResult<Void> esAdd(@RequestBody StudentDto studentDto){
        studentService.esAdd(studentDto);
        return InvokeResult.success();
    }

    @PostMapping("es/query")
    @BehaviorLog("新增学生")
    public InvokeResult<List<StudentDto>> esQuery(@RequestBody StudentDto studentDto){
        List<StudentDto> list = studentService.esQuery(studentDto);
        return InvokeResult.success(list);
    }

    @PostMapping("update")
    @BehaviorLog("更新学生")
    public InvokeResult<Void> update(@RequestBody StudentInfo studentInfo){
        studentService.update(studentInfo);
        return InvokeResult.success();
    }

    @GetMapping("query/{studentNo}")
    @BehaviorLog("根据学号查询学生信息")
    public InvokeResult<StudentInfo> queryByStudentNo(@PathVariable String studentNo){
        StudentInfo studentInfo = studentService.queryByStudentNo(studentNo);
        return InvokeResult.success(studentInfo);
    }

    @PostMapping("page")
    @BehaviorLog("分页查询学生信息")
    public InvokeResult<Page<StudentInfo>> queryPage(@RequestBody StudentQueryDto studentQueryDto){
        Page<StudentInfo> page = studentService.queryPage(studentQueryDto);
        return InvokeResult.success(page);
    }

}