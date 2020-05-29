package com.sd.service.impl;

import com.sd.dto.BorrowInfoDto;
import com.sd.mapper.BorrowMapper;
import com.sd.model.BorrowInfo;
import com.sd.service.BorrowService;
import com.sd.service.RedisService;
import com.sd.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.sd.constant.RedisConstant.BORROW_KEY_PREFIXES;

/**
 * @Package: com.sd.service.impl.BorrowServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/5/28 16:12
 */
 
@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public void borrow(BorrowInfoDto borrowInfoDto) {
        String uuid = UUID.randomUUID().toString().replace("-","");
        borrowInfoDto.setBorrowNo(uuid);
        borrowInfoDto.setBorrowDate(new Date());
        BorrowInfo map = BeanMapper.map(borrowInfoDto, BorrowInfo.class);
        borrowMapper.insert(map);
        Integer borrowDays = borrowInfoDto.getBorrowDays();
        String key = BORROW_KEY_PREFIXES + uuid;
        redisService.setEx(key, uuid,borrowDays, TimeUnit.SECONDS);
    }

    @Override
    public BorrowInfo selectByNo(String borrowNo) {
        Example example = new Example(BorrowInfo.class);
        example.createCriteria().andEqualTo("borrowNo",borrowNo);
        return borrowMapper.selectOneByExample(example);
    }

    @Override
    public void deleteByNo(String borrowNo) {
        Example example = new Example(BorrowInfo.class);
        example.createCriteria().andEqualTo("borrowNo",borrowNo);
        borrowMapper.deleteByExample(example);
    }
}