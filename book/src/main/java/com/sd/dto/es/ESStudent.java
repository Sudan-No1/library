package com.sd.dto.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @Package: com.sd.dto.es.ESStudent
 * @Description: 
 * @author sudan
 * @date 2020/11/19 20:43
 */

@Data
@Document(indexName = "book",type = "student")
public class ESStudent {

    @Id
    private String id;

    /**姓名*/
    @Field(analyzer = "ik_smart")
    private String name;
    /**班级*/
    private String classNo;
    /**邮箱*/
    private String email;

}