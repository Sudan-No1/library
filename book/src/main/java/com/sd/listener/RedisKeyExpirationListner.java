package com.sd.listener;

import com.sd.model.BorrowInfo;
import com.sd.model.StudentInfo;
import com.sd.service.BorrowService;
import com.sd.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import static com.sd.constant.RedisConstant.BORROW_KEY_PREFIXES;

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
    private StudentService studentService;

    @Override
    public void onMessage(Message message,byte[] partten){
        String expiredKey  = message.toString();
        if(expiredKey.startsWith(BORROW_KEY_PREFIXES)){
            String borrowNo = expiredKey.substring(BORROW_KEY_PREFIXES.length());
            BorrowInfo borrowInfo = borrowService.selectByNo(borrowNo);
            String studentNo = borrowInfo.getStudentNo();
            StudentInfo studentInfo = studentService.selectByNo(studentNo);
            String name = studentInfo.getName();
            String email = studentInfo.getEmail();
            log.info("发送邮件给：{}，邮箱：{}",name,email);
        }
    }
}