package com.sd.controller;

import com.alibaba.fastjson.JSON;
import com.sd.dto.InvokeResult;
import com.sd.dto.weather.WeatherResult;
import com.sd.service.forest.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.sd.controller.WeatherService
 * @Description: 
 * @author sudan
 * @date 2020/9/4 16:38
 */
 

@RestController
@RequestMapping("weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("query/{city}")
    public InvokeResult<WeatherResult> getWeather(@PathVariable String  city){
        String weatherStr = weatherService.getWeather(city);
        WeatherResult weather = JSON.parseObject(weatherStr,WeatherResult.class);
        return InvokeResult.success(weather);
    }




}