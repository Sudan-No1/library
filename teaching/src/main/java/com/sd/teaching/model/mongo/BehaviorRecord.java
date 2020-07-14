package com.sd.teaching.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Package: com.sd.model.BehaviorLog
 * @Description: 
 * @author sudan
 * @date 2020/5/29 18:21
 */
 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "behavior_record")
public class BehaviorRecord {

    /**类名*/
    private String className;
    /**方法名*/
    private String methodName;
    /**描述*/
    private String description;
    /**入参*/
    private String param;
    /**出参*/
    private Object result;
    /**耗时*/
    private String consumedTime;

}