package com.sd.service.impl;

import com.sd.mapper.ExpiredBorrowMapper;
import com.sd.model.ExpiredBorrowInfo;
import com.sd.service.ExpiredBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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

    @Override
    public List<ExpiredBorrowInfo> selectAll() {
        return expiredBorrowMapper.selectAll();
    }

    @Override
    public void update(ExpiredBorrowInfo expiredBorrowInfo) {
        Example example = new Example(ExpiredBorrowInfo.class);
        example.createCriteria().andEqualTo("borrowNo",expiredBorrowInfo.getBorrowNo());
        expiredBorrowMapper.updateByExample(expiredBorrowInfo,example);
    }

    @Override
    public int updateVersion(ExpiredBorrowInfo expiredBorrowInfo) {
        Integer version = expiredBorrowInfo.getVersion();
        expiredBorrowInfo.setVersion(version+1);
        Example example = new Example(ExpiredBorrowInfo.class);
        example.createCriteria()
                .andEqualTo("version",version)
                .andEqualTo("borrowNo",expiredBorrowInfo.getBorrowNo());
        return expiredBorrowMapper.updateByExample(expiredBorrowInfo,example);
    }
}