package com.sd.controller;

import com.sd.dto.BorrowInfoDto;
import com.sd.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.sd.controller.BorrowController
 * @Description: 借书
 * @author sudan
 * @date 2020/5/28 16:07
 */
 
@RestController
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("borrow")
    public void borrow(BorrowInfoDto borrowInfoDto){
        borrowService.borrow(borrowInfoDto);
    }

}