package com.sd.service.impl;

import com.github.pagehelper.PageHelper;
import com.sd.common.util.BeanMapper;
import com.sd.dto.Page;
import com.sd.dto.customer.CustomerDto;
import com.sd.dto.customer.CustomerQueryDto;
import com.sd.mapper.CustomerInfoMapper;
import com.sd.model.CustomerInfo;
import com.sd.service.BaseService;
import com.sd.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Package: com.sd.service.impl.CustomerOperationServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/8/20 17:45
 */
 
@Service
public class CustomerServiceImpl extends BaseService implements CustomerService {

    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Override
    public void add(CustomerDto customerDto) {
        CustomerInfo customerInfo = BeanMapper.map(customerDto, CustomerInfo.class);
        customerInfoMapper.insert(customerInfo);
    }

    @Override
    public void update(CustomerDto customerDto) {
        CustomerInfo customerInfo = BeanMapper.map(customerDto, CustomerInfo.class);
        Example example = super.createExample(CustomerInfo.class, criteria -> {
            criteria.andEqualTo("certificateNo", customerDto.getCertificateNo());
        });
        customerInfoMapper.updateByExample(customerInfo,example);
    }

    @Override
    public CustomerDto queryByCertificateNo(String certificateNo) {
        Example example = super.createExample(CustomerInfo.class, criteria -> {
            criteria.andEqualTo("certificateNo", certificateNo);
        });
        CustomerInfo customerInfo = customerInfoMapper.selectOneByExample(example);
        return BeanMapper.map(customerInfo, CustomerDto.class);
    }

    @Override
    public Page<CustomerDto> queryPage(CustomerQueryDto customerQueryDto) {
        PageHelper.startPage(customerQueryDto.getPageNum(),customerQueryDto.getPageSize());
        Example example = super.createExampleByCondition(CustomerInfo.class, customerQueryDto);
        List<CustomerInfo> customerInfos = customerInfoMapper.selectByExample(example);
        List<CustomerDto> customerDtos = BeanMapper.mapList(customerInfos, CustomerInfo.class, CustomerDto.class);
        return listToPage(customerDtos);
    }
}