package com.sd.constant;

/**
 * @Package: com.sd.constant.RedisConstant
 * @Description: 
 * @author sudan
 * @date 2020/5/28 16:15
 */
 

public interface RedisConstant {

    /**借款前缀*/
    String BORROW_KEY_PREFIXES = "com:sd:borrowKey:";

    /**图书防重复提交前缀*/
    String REPEATSUBMIT_BOOK= "book:repeatSubmit:";

}