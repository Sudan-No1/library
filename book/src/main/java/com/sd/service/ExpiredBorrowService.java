package com.sd.service;

import com.sd.model.ExpiredBorrowInfo;

import java.util.List;

/**
 * @Package: com.sd.service.ExpiredBorrowService
 * @Description: 
 * @author sudan
 * @date 2020/5/29 14:21
 */
 
 
public interface ExpiredBorrowService {
    void add(ExpiredBorrowInfo expiredBorrowInfo);

    List<ExpiredBorrowInfo> selectAll();

    void update(ExpiredBorrowInfo expiredBorrowInfo);

    int updateVersion(ExpiredBorrowInfo expiredBorrowInfo);
}