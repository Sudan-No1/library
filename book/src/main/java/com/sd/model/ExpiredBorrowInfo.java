package com.sd.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Package: com.sd.model.BorrowInfo
 * @Description:
 * @author sudan
 * @date 2020/5/28 15:55
 */
 

@Data
@Table(name = "expired_borrow_info")
public class ExpiredBorrowInfo extends BaseModel {

    /**借阅单号*/
    @Column(name = "borrow_no")
    private String borrowNo;
    /**学号*/
    @Column(name = "student_no")
    private String studentNo;
    /**图书编号*/
    @Column(name = "book_no")
    private String bookNo;
    /**借阅开始时间*/
    @Column(name = "borrow_date")
    private Date borrowDate;
    /**借阅天数*/
    @Column(name = "borrow_days")
    private Integer borrowDays;
    /**已过天数*/
    @Column(name = "expired_borrow_days")
    private Integer expiredBorrowDays;


}