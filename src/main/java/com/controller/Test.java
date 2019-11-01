package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YangWenjun
 * @date 2019/10/30 11:05
 * @project MockFramework
 * @title: Test
 * @description:
 */
//@Controller
    @RestController
    @ResponseBody
    @RequestMapping("/")
public class Test {

    @RequestMapping("/testEnv")
    public String testEnv(){
        return "this is first testEnv";
    }

}
