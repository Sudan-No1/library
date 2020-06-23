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
@Table(name = "borrow_info")
public class BorrowInfo extends BaseModel {

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

}