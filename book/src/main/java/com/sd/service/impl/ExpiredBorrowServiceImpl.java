package com.sd.service.impl;

import com.sd.mapper.ExpiredBorrowMapper;
import com.sd.model.ExpiredBorrowInfo;
import com.sd.service.ExpiredBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Package: com.sd.service.impl.ExpiredBorrowServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/5/29 14:22
 */
 
@Service
public class ExpiredBorrowServiceImpl implements ExpiredBorrowService {

    @Autowired
    private ExpiredBorrowMapper expiredBorrowMapper;

    @Override
    public void add(ExpiredBorrowInfo expiredBorrowInfo) {
        expiredBorrowMapper.insertSelective(expiredBorrowInfo);
    }
}