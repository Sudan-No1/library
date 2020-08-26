package com.sd.service.people.impl;

import com.sd.common.util.CollectionUtil;
import com.sd.service.people.OperationService;
import com.sd.service.people.PeopleFactoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package: PeopleFactoryServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/8/20 18:12
 */
 
@Service
public class PeopleFactoryServiceImpl implements PeopleFactoryService {

    @Resource
    private List<OperationService> peopleServiceList;
    @Autowired
    private NullPeopleServiceImpl nullPeopleService;

    @Override
    public OperationService createPeopleService(String code) {
        if(CollectionUtil.isEmpty(peopleServiceList)){
            return nullPeopleService;
        }
        for(OperationService peopleService : peopleServiceList){
            if(StringUtils.equals(peopleService.getCode().getCode(),code)){
               return peopleService;
            }
        }
        return nullPeopleService;
    }
}