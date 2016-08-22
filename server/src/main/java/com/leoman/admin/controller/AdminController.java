package com.leoman.admin.controller;

import com.leoman.admin.entity.Admin;
import com.leoman.admin.service.impl.AdminServiceImpl;
import com.leoman.admin.service.AdminService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/7/5 0005.
 */
@Controller
@RequestMapping(value = "admin/admin")
public class AdminController extends GenericEntityController<Admin, Admin, AdminServiceImpl>{

    @Autowired
    private AdminService adminService;

    /**
     * 列表页面
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {

        Admin admin = adminService.findByUsername("admin");
        model.addAttribute("admin",admin);

        return "admin/list";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(String password, String newPassword) {

        Admin admin = adminService.findByUsername("admin");

        Md5Util md5Util = new Md5Util();
        String p = md5Util.md5(password);
        if (!admin.getPassword().equals(md5Util.md5(password))) {
            return 2;
        }else {
            admin.setPassword(md5Util.md5(newPassword));
            adminService.update(admin);
        }
        return 1;
    }

}
