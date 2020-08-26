package com.sd.common.listener;

import com.sd.dto.BaseContextHandler;
import com.sd.dto.LoginDto;
import com.sd.dto.UserDto;
import com.sd.dto.borrow.BookBorrowDto;
import com.sd.model.BorrowInfo;
import com.sd.model.ExpiredBorrowInfo;
import com.sd.model.StudentInfo;
import com.sd.service.BorrowService;
import com.sd.service.ExpiredBorrowService;
import com.sd.service.StudentService;
import com.sd.common.util.BeanMapper;
import com.sd.service.people.PeopleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.math.BigDecimal;

import static com.sd.common.constant.BusinessConstant.LOGIN_USER;
import static com.sd.common.constant.RedisConstant.BORROW_KEY_PREFIXES;

/**
 * @Package: com.yixin.yxcode.alkaid.config.RedisKeyExpirationListner
 * @Description: 
 * @author sudan
 * @date 2020/5/21 14:16
 */
 

@Configuration
@Slf4j
public class RedisKeyExpirationListner extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListner(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private ExpiredBorrowService expiredBorrowService;
    @Autowired
    private PeopleService peopleService;

    @Override
    public void onMessage(Message message,byte[] partten){
        String expiredKey  = message.toString();
        //学生借书已过期
        if(expiredKey.startsWith(BORROW_KEY_PREFIXES)){
            //1.获取学生信息，发送邮箱 TODO
            String borrowNo = expiredKey.substring(BORROW_KEY_PREFIXES.length());
            BorrowInfo borrowInfo = borrowService.selectByNo(borrowNo);
            LoginDto user = BaseContextHandler.getUser();

            BookBorrowDto bookBorrowDto = peopleService.getBorrowInfo(user.getRole());
            //2.删除借阅表数据 新增到借阅过期表中
            borrowService.deleteByNo(borrowNo);
            ExpiredBorrowInfo expiredBorrowInfo = BeanMapper.map(borrowInfo, ExpiredBorrowInfo.class);
            expiredBorrowInfo.setExpiredBorrowDays(1);
            expiredBorrowInfo.setFine(bookBorrowDto.getFine());
            expiredBorrowInfo.setVersion(0);
            expiredBorrowService.add(expiredBorrowInfo);
        }
    }
}