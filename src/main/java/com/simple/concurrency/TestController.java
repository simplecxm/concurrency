package com.simple.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create by S I M P L E on 2018/04/16 12:59:30
 */
@Controller
@Slf4j
public class TestController {

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test() {

        return "test";
    }

}
