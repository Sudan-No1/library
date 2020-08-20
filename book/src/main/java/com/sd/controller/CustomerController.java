package com.sd.controller;

import com.sd.common.annotation.BehaviorLog;
import com.sd.dto.InvokeResult;
import com.sd.dto.Page;
import com.sd.dto.customer.CustomerDto;
import com.sd.dto.customer.CustomerQueryDto;
import com.sd.dto.teacher.TeacherDto;
import com.sd.dto.teacher.TeacherQueryDto;
import com.sd.service.CustomerService;
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
@RequestMapping("customer")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @PostMapping("add")
    @BehaviorLog("新增客户")
    public InvokeResult<Void> add(@RequestBody CustomerDto customerDto){
        customerService.add(customerDto);
        return InvokeResult.success();
    }

    @PostMapping("update")
    @BehaviorLog("更新客户")
    public InvokeResult<Void> update(@RequestBody CustomerDto customerDto){
        customerService.update(customerDto);
        return InvokeResult.success();
    }

    @GetMapping("query/{certificateNo}")
    @BehaviorLog("根据学号查询客户信息")
    public InvokeResult<CustomerDto> queryByStudentNo(@PathVariable String certificateNo){
        CustomerDto customerDto = customerService.queryByCertificateNo(certificateNo);
        return InvokeResult.success(customerDto);
    }

    @PostMapping("page")
    @BehaviorLog("分页查询客户信息")
    public InvokeResult<Page<CustomerDto>> queryPage(@RequestBody CustomerQueryDto customerQueryDto){
        Page<CustomerDto> page = customerService.queryPage(customerQueryDto);
        return InvokeResult.success(page);
    }

}