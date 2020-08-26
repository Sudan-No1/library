package com.sd.controller;

import com.sd.common.annotation.BehaviorLog;
import com.sd.dto.InvokeResult;
import com.sd.dto.mongo.Dictionary;
import com.sd.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Package: com.sd.controller.DictionaryController
 * @Description: 
 * @author sudan
 * @date 2020/8/26 16:06
 */
 
@RestController
@RequestMapping("dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @BehaviorLog("字典新增")
    @PostMapping("add")
    public InvokeResult<Void> add(@RequestBody Dictionary dictionary){
        dictionaryService.add(dictionary);
        return InvokeResult.success();
    }

    @BehaviorLog("字典更新")
    @PostMapping("update")
    public InvokeResult<Void> update(@RequestBody Dictionary dictionary){
        dictionaryService.update(dictionary);
        return InvokeResult.success();
    }

    @BehaviorLog("字典项删除")
    @PostMapping("delete/{dictTypeCode}")
    public InvokeResult<Void> delete(@PathVariable String dictTypeCode){
        dictionaryService.delete(dictTypeCode);
        return InvokeResult.success();
    }

    @BehaviorLog("字典删除")
    @PostMapping("delete/{dictTypeCode}/{dictCode}")
    public InvokeResult<Void> delete(@PathVariable String dictTypeCode,@PathVariable String dictCode){
        dictionaryService.delete(dictTypeCode,dictCode);
        return InvokeResult.success();
    }

    @BehaviorLog("字典查询")
    @PostMapping("queryList/{dictTypeCode}")
    public InvokeResult<List<Dictionary>> queryList(@PathVariable String dictTypeCode){
        List<Dictionary> list = dictionaryService.queryList(dictTypeCode);
        return InvokeResult.success(list);
    }

    @BehaviorLog("字典查询")
    @PostMapping("queryName/{dictTypeCode}/{dictCode}")
    public InvokeResult<String> queryName(@PathVariable String dictTypeCode,@PathVariable String dictCode){
        String dictName = dictionaryService.queryName(dictTypeCode,dictCode);
        return InvokeResult.success(dictName);
    }

}