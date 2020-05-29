package com.sd.schedule;

import com.sd.model.ExpiredBorrowInfo;
import com.sd.service.ExpiredBorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @Package: com.sd.schedule.ExpiredBorrowSchedule
 * @Description: 
 * @author sudan
 * @date 2020/5/29 15:05
 */
 
@Service
@Slf4j
public class ExpiredBorrowSchedule {

    @Autowired
    private ExpiredBorrowService expiredBorrowService;

    @Scheduled(cron = "*/10 * * * * *")
    public void expiredTime(){
        List<ExpiredBorrowInfo> list = expiredBorrowService.selectAll();
        for (ExpiredBorrowInfo expiredBorrowInfo : list){
            Integer expiredBorrowDays = expiredBorrowInfo.getExpiredBorrowDays();//过期天数
            Date borrowDate = expiredBorrowInfo.getBorrowDate();//借阅时间
            Integer borrowDays = expiredBorrowInfo.getBorrowDays();//借阅天数
            //（当前时间-借阅时间）- 借阅天数 = 过期天数
            int days = (int) ((System.currentTimeMillis()-borrowDate.getTime())/(1000*3600*24)+1-borrowDays);
            if(days != expiredBorrowDays){
                expiredBorrowInfo.setExpiredBorrowDays(days);
                //关键 使用乐观锁 多台服务同时更新记录时 先更新版本号成功 才能更新数据
                if(expiredBorrowService.updateVersion(expiredBorrowInfo) >0){
                    log.info("过期对象信息：{}，过期天数：{}",expiredBorrowInfo,days);
                    expiredBorrowService.update(expiredBorrowInfo);
                }
            }
        }
    }

}