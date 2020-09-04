package com.sd.dto.weather;

import lombok.Data;

import java.util.List;

/**
 * @Package: com.sd.dto.weather.WeatherResult
 * @Description: 
 * @author sudan
 * @date 2020/9/4 16:28
 */
 
@Data
public class WeatherResult {

    private WeatherDto data;
    private Integer status;
    private String desc;

}