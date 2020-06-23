package com.sd.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Package: com.sd.dto.BorrowInfoDto
 * @Description: 
 * @author sudan
 * @date 2020/5/28 16:09
 */
 
@Data
public class BorrowInfoDto {
    /**学号*/
    private String studentNo;
    /**图书编号*/
    private String bookNo;
    /**借阅开始时间*/
    private Date borrowDate;
    /**借阅天数*/
    @NotNull(message = "借阅天数不能为空")
    private Integer borrowDays;
}