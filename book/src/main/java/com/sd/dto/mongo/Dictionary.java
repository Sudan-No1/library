package com.sd.dto.mongo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Package: com.sd.dto.mongo.Dictionary
 * @Description: 数据字典
 * @author sudan
 * @date 2020/8/26 16:09
 */
 
@Data
@Document(collection = "dictionary")
public class Dictionary {

    @Indexed
    private String id;
    /**字典项*/
    private String dictTypeCode;
    /**字典项名称*/
    private String dictTypeName;
    /**字典code*/
    private String dictCode;
    /**字典值*/
    private String dictName;
    /**父级编号*/
    private String parentCode;
    /**字典排序*/
    private Integer dictOrder;
    /**是否生效*/
    private Integer active;

}