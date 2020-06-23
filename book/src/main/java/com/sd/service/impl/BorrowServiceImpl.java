package com.sd.service.impl;

import com.sd.common.exception.BusinessException;
import com.sd.dto.BorrowInfoDto;
import com.sd.mapper.BorrowMapper;
import com.sd.model.BookInfo;
import com.sd.model.BorrowInfo;
import com.sd.service.BookService;
import com.sd.service.BorrowService;
import com.sd.service.RedisService;
import com.sd.common.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.sd.common.constant.RedisConstant.BORROW_KEY_PREFIXES;

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

    @Autowired
    private BookService bookService;

    @Override
    @Transactional
    public void borrow(BorrowInfoDto borrowInfoDto) {
        String bookNo = borrowInfoDto.getBookNo();
        //1.查询图书表，校验库存数量
        BookInfo bookInfo = bookService.selectByBookNo(bookNo);
        if(bookInfo.getActive() == 0){
            throw new BusinessException("书本库存量不足，请选择其他书籍。");
        }
        //2.更新图书信息
        if(bookInfo.getInventory() == 1){
            bookInfo.setActive(0);
        }
        bookInfo.setInventory(bookInfo.getInventory()-1);
        bookService.update(bookInfo);

        //3.新增图书借阅表
        borrowInfoDto.setBorrowDate(new Date());
        BorrowInfo map = BeanMapper.map(borrowInfoDto, BorrowInfo.class);
        borrowMapper.insert(map);
        //4.查询借阅ID，并放入缓存中
        Example example = new Example(BorrowInfo.class);
        example.createCriteria()
                .andEqualTo("studentNo",map.getStudentNo())
                .andEqualTo("bookNo",map.getBookNo());
        BorrowInfo borrowInfo = borrowMapper.selectOneByExample(example);
        String id = borrowInfo.getId();
        Integer borrowDays = borrowInfoDto.getBorrowDays();
        String key = BORROW_KEY_PREFIXES + id;
        redisService.setEx(key, id,borrowDays, TimeUnit.SECONDS);
    }

    @Override
    public BorrowInfo selectByNo(String borrowNo) {
        Example example = new Example(BorrowInfo.class);
        example.createCriteria().andEqualTo("borrowNo",borrowNo);
        return borrowMapper.selectOneByExample(example);
    }

    @Override
    public void deleteByNo(String borrowNo) {
      borrowMapper.deleteByPrimaryKey(borrowNo);
    }
}