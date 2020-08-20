package com.sd.mapper;

import com.sd.model.CustomerInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CustomerInfoMapper extends Mapper<CustomerInfo> {
}