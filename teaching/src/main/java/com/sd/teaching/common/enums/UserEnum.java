package com.sd.teaching.common.enums;

/**
 * @Package: UserEnum
 * @Description: 
 * @author sudan
 * @date 2020/7/14 10:56
 */
 
 
public enum UserEnum {

    USER_SERVICE_STUDENT("student","学生"),
    USER_SERVICE_TEACHER("teacher","老师"),
    USER_SERVICE_SOCIALMAN("socialMan","校外人"),
    USER_SERVICE_NULL("","");
    private String type;
    private String name;

    public String getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }

    UserEnum(String type,String name){
        this.type = type;
        this.name = name;
    }
}