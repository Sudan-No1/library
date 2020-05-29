package com.sd.service;

import com.sd.dto.BorrowInfoDto;
import com.sd.model.BorrowInfo;

/**
 * @Package: com.sd.service.BorrowService
 * @Description: 
 * @author sudan
 * @date 2020/5/28 16:09
 */
 
 
public interface BorrowService {
    void borrow(BorrowInfoDto borrowInfoDto);

    BorrowInfo selectByNo(String borrowNo);

    void deleteByNo(String borrowNo);
}