package com.sd.service;

import com.sd.dto.Page;
import com.sd.dto.customer.CustomerDto;
import com.sd.dto.customer.CustomerQueryDto;
import com.sd.model.CustomerInfo;

/**
 * @Package: com.sd.service.CustomerService
 * @Description: 
 * @author sudan
 * @date 2020/8/20 17:44
 */
 
 
public interface CustomerService {
    void add(CustomerDto customerDto);

    void update(CustomerDto customerDto);

    CustomerDto queryByCertificateNo(String certificateNo);

    Page<CustomerDto> queryPage(CustomerQueryDto customerQueryDto);

    CustomerInfo queryByLoginName(String loginName);
}