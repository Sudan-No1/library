package com.sd.dto.weather;

import lombok.Data;

/**
 * @Package: com.sd.dto.weather.Forecast
 * @Description: 
 * @author sudan
 * @date 2020/9/4 16:32
 */
 
@Data
public class Forecast {

    private String date;
    private String high;
    private String fx;
    private String low;
    private String fl;
    private String type;

}