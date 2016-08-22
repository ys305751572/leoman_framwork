package com.leoman.role.controller;

import com.leoman.admin.service.AdminRoleService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.bean.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.resources.entity.StillResource;
import com.leoman.role.entity.Modules;
import com.leoman.role.entity.Role;
import com.leoman.role.entity.RoleModule;
import com.leoman.role.service.ModulesService;
import com.leoman.role.service.RoleModulesService;
import com.leoman.role.service.RoleService;
import com.leoman.role.service.impl.RoleServiceImpl;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
@Controller
@RequestMapping(value = "admin/role")
public class RoleController extends GenericEntityController<Role, Role, RoleServiceImpl> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModulesService modulesService;

    @Autowired
    private RoleModulesService roleModulesService;

    @Autowired
    private AdminRoleService adminRoleService;

    @RequestMapping("/index")
    public String index() {
        return "role/list";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Role> page = roleService.page(pageNum, length);

        for (Role roles : page.getContent()) {
            // 循环查询每个角色的人员数量
            roles.setCount(adminRoleService.findByRoleId(roles.getId()).size());
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("/add")
    public String add(ModelMap model, Long roleId) {
        List<Modules> moduleList = modulesService.findListByParentId(0l);

        for (Modules module : moduleList) {
            module.setModulesList(modulesService.findListByParentId(module.getId()));
        }

        model.addAttribute("moduleList", moduleList);

        StringBuffer sb = new StringBuffer("");

        if (roleId != null) {
            Role roles = roleService.queryByPK(roleId);
            model.addAttribute("roles", roles);

            if (null != roles) {
                List<RoleModule> rolemodulesList = roleModulesService.findListByRoleId(roles.getId());

                for (RoleModule rolemodules : rolemodulesList) {
                    sb.append(rolemodules.getModule().getId() + ",");
                }

                if (sb.length() > 0) {
                    model.addAttribute("roleModuleIds", sb.toString().substring(0, sb.length() - 1));
                } else {
                    model.addAttribute("roleModuleIds", "");
                }
            } else {
                model.addAttribute("roleModuleIds", "");
            }
        }
        return "role/add";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Integer save(HttpServletRequest request,
                        Long id,
                        String name,
                        String types) {
        try {
            // 解析权限id
            Long[] moduleIds = JsonUtil.json2Obj(types, Long[].class);

            Role roles = null;
            RoleModule rolemodules = null;

            if (null == id) {
                roles = new Role();
                roles.setCreateDate(System.currentTimeMillis());
                roles.setUpdateDate(System.currentTimeMillis());
                roles.setName(name);

                roleService.save(roles);
            } else {
                roles = roleService.queryByPK(id);
                roles.setName(name);
                roles.setUpdateDate(System.currentTimeMillis());
                roleService.update(roles);
            }

            // 先删除该角色对应的所有权限
            roleModulesService.deleteByRoleId(roles.getId());

            // 然后逐个保存该角色的权限
            for (Long moduleId : moduleIds) {
                rolemodules = new RoleModule();
                rolemodules.setRole(roles);
                rolemodules.setModule(modulesService.queryByPK(moduleId));
                rolemodules.setCreateDate(System.currentTimeMillis());

                roleModulesService.insert(rolemodules);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(HttpServletRequest request, HttpServletResponse response, Long roleId) {
        if (roleId != null) {
            roleService.delete(roleService.queryByPK(roleId));
            return 1;
        } else {
            WebUtil.printJson(response, new Result(false).msg("权限不存在"));
        }
        return 0;
    }

    @RequestMapping("/deleteBatch")
    @ResponseBody
    public Integer deleteBatch(HttpServletRequest request, String ids) {
        try {

            Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
            for (Long id : arrayId) {
                roleService.delete(roleService.queryByPK(id));
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
