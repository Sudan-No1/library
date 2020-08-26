package com.sd.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.sd.common.constant.PeopleTypeEnum
 * @Description: 
 * @author sudan
 * @date 2020/8/21 9:14
 */
 
 
public enum PeopleTypeEnum {

    PEOPLE_TYPE_ENUM_STUDENT("student","学生"),
    PEOPLE_TYPE_ENUM_TEACHER("teacher","老师"),
    PEOPLE_TYPE_ENUM_CUSTOMER("customer","客户"),
    PEOPLE_TYPE_ENUM_NULL(null,null);

    private String code;
    private String name;
    PeopleTypeEnum(String code,String name){
        this.code = code;
        this.name = name;
    }

    public String getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    public static PeopleTypeEnum getEnumByCode(String code){
        for (PeopleTypeEnum peopleTypeEnum : values()){
            if(peopleTypeEnum.getCode().equals(code)){
                return peopleTypeEnum;
            }
        }
        return null;
    }
    public static List<String> getAllCode(){
        List<String> list = new ArrayList<>();
        for (PeopleTypeEnum peopleTypeEnum : values()){
            list.add(peopleTypeEnum.getCode());
        }
        return list;
    }

}