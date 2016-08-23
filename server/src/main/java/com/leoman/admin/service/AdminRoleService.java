package com.leoman.admin.service;

import com.leoman.admin.entity.AdminRole;
import com.leoman.common.service.GenericManager;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public interface AdminRoleService extends GenericManager<AdminRole>{

    // 管理员列表（分页）
    public Page<AdminRole> page(String account, Long roleId, String mobile, Integer pageNum, Integer pageSize);

    public List<AdminRole> findByRoleId(Long roleId);

    // 总后台管理员登录
    public AdminRole sysUserLogin(HttpServletRequest request, String username, String password);

    public Integer save(HttpServletRequest request,
                     Long id,
                     Long roleId,
                     String account,
                     String mobile,
                     String password);
}
