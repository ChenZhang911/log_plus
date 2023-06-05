package com.jory.redislog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    /***
     * 访问/test/hello  跳转到templates_1页面
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String hello(Model model){
        return "index";
    }

}
