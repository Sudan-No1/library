package com.sd.controller;

import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.sd.dto.InvokeResult;
import com.sd.dto.weather.WeatherResult;
import com.sd.service.forest.WeatherService;
import de.odysseus.el.util.SimpleContext;
import org.apache.el.ExpressionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

/**
 * @Package: com.sd.controller.WeatherService
 * @Description: 
 * @author sudan
 * @date 2020/9/4 16:38
 */
 

@RestController
@RequestMapping("thirdParty")
public class WeatherController {
    public static void main(String[] args) {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        context.setVariable("var1", factory.createValueExpression("Hello", String.class));
        context.setVariable("var2", factory.createValueExpression("World", String.class));
        String s = "{\"argIn1\":\"${var1}\",\"argIn2\":\"${var2}\"}";
            ValueExpression e = factory.createValueExpression(context, s, String.class);
        System.out.println(e.getValue(context));// --> {"argIn1":"Hello","argIn2":"World"}
    }

    @Autowired
    private WeatherService weatherService;

    @GetMapping("weather/query/{city}")
    public InvokeResult<WeatherResult> getWeather(@PathVariable String  city){
        String weatherStr = weatherService.getWeather(city);
        WeatherResult weather = JSON.parseObject(weatherStr,WeatherResult.class);
        return InvokeResult.success(weather);
    }

    @GetMapping("queryMobileSegment/{phone}")
    public InvokeResult<String> getMobileSegment(@PathVariable String phone){
        String url = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=";
        return InvokeResult.success(HttpUtil.get(url + phone));
    }

    @GetMapping("sendMail")
    public InvokeResult<Void> sendMail(@PathVariable String message){
        MailUtil.send("1712252320@qq.com", "测试", message, false);
        return InvokeResult.success();
    }



}