package com.sd.dto.weather;

import lombok.Data;

import java.util.List;

/**
 * @Package: com.sd.dto.weather.WeatherDto
 * @Description: 
 * @author sudan
 * @date 2020/9/4 17:15
 */

@Data
public class WeatherDto {
    private Forecast yesterday;
    private List<Forecast> forecast;
    private String ganmao;
    private String wendu;
    private String city;
}