package com.sd.service.impl;

import com.alibaba.fastjson.JSON;
import com.sd.common.exception.BusinessException;
import com.sd.common.util.CollectionUtil;
import com.sd.common.util.IdWorker;
import com.sd.dto.BaseContextHandler;
import com.sd.dto.BorrowInfoDto;
import com.sd.dto.LoginDto;
import com.sd.dto.borrow.BookBorrowDto;
import com.sd.mapper.BorrowMapper;
import com.sd.model.BookInfo;
import com.sd.model.BorrowInfo;
import com.sd.service.BaseService;
import com.sd.service.BookService;
import com.sd.service.BorrowService;
import com.sd.service.RedisService;
import com.sd.service.people.PeopleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.sd.common.constant.BusinessConstant.SIGN_COMMA;
import static com.sd.common.constant.BusinessConstant.SIGN_YES;
import static com.sd.common.constant.BusinessConstant.SIGN_YES_STRING;
import static com.sd.common.constant.RabbitMqConstant.MQ_EXCHANGE_NAME;
import static com.sd.common.constant.RabbitMqConstant.MQ_KEY_BOOK_BORROW;
import static com.sd.common.constant.RedisConstant.BORROW_KEY_PREFIXES;

/**
 * @Package: com.sd.service.impl.BorrowServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/5/28 16:12
 */
 
@Service
@Slf4j
public class BorrowServiceImpl extends BaseService implements BorrowService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private BookService bookService;

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public void borrow(BorrowInfoDto borrowInfoDto) {
        //获取用户信息
        LoginDto loginDto = BaseContextHandler.getUser();
        //校验借书数量
        BookBorrowDto bookBorrowDto =checkBookNum(borrowInfoDto,loginDto);
        //校验并更新库存数量
        checkAndSaveBookInfo(borrowInfoDto.getBookList());
        //新增图书借阅表
        String borrowNo = addBorrowInfo(borrowInfoDto,bookBorrowDto,loginDto);
        //查询借阅ID，并放入缓存中
        log.info("redis缓存{},{}",BORROW_KEY_PREFIXES+borrowNo,bookBorrowDto.getBorrowDays());
        //redisService.setEx(BORROW_KEY_PREFIXES+borrowNo, borrowNo,bookBorrowDto.getBorrowDays(), TimeUnit.SECONDS);
        redisService.setEx(BORROW_KEY_PREFIXES+borrowNo, borrowNo,1, TimeUnit.SECONDS);
    }

    private String addBorrowInfo(BorrowInfoDto borrowInfoDto, BookBorrowDto bookBorrowDto, LoginDto loginDto) {
        BorrowInfo borrowInfo = new BorrowInfo();
        String borrowNo = idWorker.nextId();
        borrowInfo.setBorrowNo(borrowNo);
        borrowInfo.setBookNo(StringUtils.join(borrowInfoDto.getBookList(), SIGN_COMMA));
        borrowInfo.setBorrowDate(new Date());
        borrowInfo.setBorrowDays(bookBorrowDto.getBorrowDays());//设置默认借书时间
        borrowInfo.setLoginName(loginDto.getLoginName());
        borrowInfo.setActive(SIGN_YES);
        borrowMapper.insert(borrowInfo);
        return borrowNo;
    }

    private BookBorrowDto checkBookNum(BorrowInfoDto borrowInfoDto, LoginDto loginDto) {
        List<BorrowInfo> borrowInfos = this.selectByLoginName(loginDto.getLoginName());
        int alreadyBorrowNum = 0;
        if(CollectionUtil.isNotEmpty(borrowInfos)){
            for (BorrowInfo borrowInfo : borrowInfos){
                alreadyBorrowNum += borrowInfo.getBookNo().split(SIGN_COMMA).length;
            }
        }
        BookBorrowDto bookBorrowDto = peopleService.getBorrowInfo(loginDto.getRole());
        Integer bookNum = bookBorrowDto.getBookNum();
        List<String> bookList = borrowInfoDto.getBookList();
        if(bookNum.intValue() < bookList.size()+alreadyBorrowNum){
            throw new BusinessException("抱歉，您的借书数量超出您可以借阅的最大数量");
        }
        return bookBorrowDto;
    }

    private void checkAndSaveBookInfo(List<String> bookList) {
        for(String bookNo : bookList){
            BookInfo bookInfo = bookService.selectByBookNo(bookNo);
            if(bookInfo.getActive() == 0){
                throw new BusinessException("书本《"+bookInfo.getName()+"》库存量不足，请选择其他书籍。");
            }
        }
        for(String bookNo : bookList){
            BookInfo bookInfo = bookService.selectByBookNo(bookNo);
            if(bookService.updateInventory(bookInfo) == 0){
                throw new BusinessException("更新库存数量失败");
            }
        }
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

    @Override
    public List<BorrowInfo> selectByLoginName(String loginName) {
        Example example = super.createExample(BorrowInfo.class, criteria -> {
            criteria.andEqualTo("loginName", loginName);
            criteria.andEqualTo("active", SIGN_YES_STRING);
        });
        return borrowMapper.selectByExample(example);
    }
}