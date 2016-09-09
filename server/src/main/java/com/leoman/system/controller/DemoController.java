package com.leoman.system.controller;

import com.leoman.system.service.TestCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 测试controller
 * Created by yesong on 2016/9/1.
 */
@RequestMapping(value = "/admin/demo")
@Controller
public class DemoController {

    @Autowired
    private TestCache testCache;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        // 第一次查询，应该走数据库
        System.out.print("first query...");
        testCache.getByName("admin111");
        // 第二次查询，应该不查数据库，直接返回缓存的值
        System.out.print("second query...");
        testCache.getByName("admin111");
        return "system/demo/demo";
    }
}
