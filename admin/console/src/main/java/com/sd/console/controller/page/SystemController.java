package com.sd.console.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author sudan
 * @Package: com.sd.console.controller.page.SystemController
 * @Description:
 * @date 2020/12/2 11:54
 */

@Controller
public class SystemController {

    @GetMapping("/index")
    public String index() {
        return "index2";
    }

}