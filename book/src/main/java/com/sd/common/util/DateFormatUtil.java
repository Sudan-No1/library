package com.sd.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Package: com.sd.common.util.DateFormatUtil
 * @Description: 
 * @author sudan
 * @date 2020/5/28 11:11
 */
 
 
public class DateFormatUtil {

    public static Date pareDate(String pattern, String source) throws ParseException {
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(source);
    }

    public static String formatDate(Date source) {
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(source);
    }
}