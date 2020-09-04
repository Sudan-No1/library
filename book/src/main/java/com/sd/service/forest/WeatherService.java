package com.sd.service.forest;

import com.dtflys.forest.annotation.DataParam;
import com.dtflys.forest.annotation.Request;
import org.springframework.stereotype.Service;

/**
 * @Package: com.sd.service.forest.WeatherService
 * @Description: 
 * @author sudan
 * @date 2020/9/4 16:27
 */
@Service
public interface WeatherService {

    @Request(url="http://wthrcdn.etouch.cn/weather_mini", type = "get")
    String getWeather(@DataParam("city")String city);


}