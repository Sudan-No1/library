package com.sd.common.util;

import java.util.Collection;

/**
 * @Package: com.sd.common.util.CollectionUtil
 * @Description: 
 * @author sudan
 * @date 2020/8/20 14:18
 */
 
 
public class CollectionUtil {

    public static boolean isEmpty(Collection collection){
        if(collection == null || collection.size() == 0){
            return true;
        }
        return false;
    }
    public static boolean isNotEmpty(Collection collection){
        if(collection != null && collection.size() > 0){
            return true;
        }
        return false;
    }

}