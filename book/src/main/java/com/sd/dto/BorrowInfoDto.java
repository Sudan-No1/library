package com.sd.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Package: com.sd.dto.BorrowInfoDto
 * @Description: 
 * @author sudan
 * @date 2020/5/28 16:09
 */
 
@Data
public class BorrowInfoDto {

    /**图书编号*/
    private List<String> bookList;

}