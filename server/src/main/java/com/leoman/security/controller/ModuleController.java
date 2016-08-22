package com.leoman.security.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.role.entity.Modules;
import com.leoman.security.service.ModuleService;
import com.leoman.security.service.impl.ModuleServiceImpl;
import com.leoman.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/23.
 */
@Controller
@RequestMapping(value = "/admin/module")
public class ModuleController extends GenericEntityController<Modules, Modules, ModuleServiceImpl> {

    @Autowired
    private ModuleService service;

    @RequestMapping(value = "/index")
    public String index() {
        return "module/list";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(Integer draw, Integer start, Integer length) {

        Integer pagenum = getPageNum(start, length);
        Page<Modules> page = service.findPage(pagenum, length);
        return DataTableFactory.fitting(draw, page);
    }

    @RequestMapping(value = "/add")
    public String add(Long id, Model model) {
        if (id != null) {
            Modules module = service.queryByPK(id);
            model.addAttribute("module", module);
        }
        return "module/add";
    }

    /**
     * 新增模块
     *
     * @param module   模块内容
     * @param parentId 父模块ID，没有则为一级页面
     * @return
     */
    /*@RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Modules module, Long parentId) {
        service.saveModule(module, parentId);
        return Result.success();
    }*/
}
