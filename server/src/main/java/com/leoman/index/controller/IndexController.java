package com.leoman.index.controller;

import com.leoman.admin.entity.Admin;
import com.leoman.admin.entity.AdminRole;
import com.leoman.admin.service.AdminRoleService;
import com.leoman.barrage.entity.Barrage;
import com.leoman.barrage.service.BarrageService;
import com.leoman.comment.entity.Comment;
import com.leoman.comment.service.CommentService;
import com.leoman.common.controller.common.CommonController;
import com.leoman.common.log.entity.Log;
import com.leoman.entity.Constant;
import com.leoman.feedback.entity.Feedback;
import com.leoman.feedback.service.FeedBackService;
import com.leoman.index.service.LoginService;
import com.leoman.post.entity.PostBase;
import com.leoman.post.entity.PostComment;
import com.leoman.post.service.PostBaseService;
import com.leoman.post.service.PostCommentService;
import com.leoman.recordcount.entity.RecordCount;
import com.leoman.recordcount.service.RecordCountService;
import com.leoman.report.entity.Report;
import com.leoman.report.service.ReportService;
import com.leoman.role.entity.Modules;
import com.leoman.role.entity.RoleModule;
import com.leoman.role.service.ModulesService;
import com.leoman.role.service.RoleModulesService;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserService;
import com.leoman.utils.CookiesUtils;
import com.leoman.utils.Md5Util;
import com.leoman.work.entity.Work;
import com.leoman.work.service.WorkComicService;
import com.leoman.work.service.WorkNovelService;
import com.leoman.work.service.WorkService;
import com.leoman.work.service.WorkVideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wangbin on 2015/7/29.
 */
@Controller
@RequestMapping(value = "admin")
public class IndexController extends CommonController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private ModulesService modulesService;

    @Autowired
    private RoleModulesService roleModulesService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostBaseService postBaseService;

    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private BarrageService barrageService;

    @Autowired
    private WorkService workService;

    @Autowired
    private WorkVideoService workVideoService;

    @Autowired
    private WorkNovelService workNovelService;

    @Autowired
    private WorkComicService workComicService;

    @Autowired
    private RecordCountService recordCountService;

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        String error,
                        ModelMap model) {
        try {
            if (StringUtils.isNotBlank(error)) {
                model.addAttribute("error", error);
            }
            // 先读取cookies，如果存在，则将用户名回写到输入框
            Map<String, Object> params = CookiesUtils.ReadCookieMap(request);
            if (params != null && params.size() != 0) {
                String username = (String) params.get("username");
                model.put("username", username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index/login";
    }

    @Log(message = "{0}登录了系统")
    @RequestMapping(value = "/login/check")
    public String checkLogin(String username,
                             String password,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             String remark,
                             ModelMap model) {
        response.setCharacterEncoding("UTF-8");
        // 管理员
//        AdminRole adminRole = adminRoleService.sysUserLogin(request, username, Md5Util.md5(password));
        Boolean success = loginService.login(request, username, Md5Util.md5(password), Constant.MEMBER_TYPE_GLOBLE,remark);
        if (success) {
            // 登录成功后，将用户名放入cookies
            int loginMaxAge = 30 * 24 * 60 * 60; // 定义cookies的生命周期，这里是一个月。单位为秒
            CookiesUtils.addCookie(response, "username", username, loginMaxAge);

            // 增加今日访问人数和在线人数
            recordCountService.addCount(1, 1);

            return "redirect:/admin/dashboard";
        }
        model.addAttribute("error", "用户名或密码错误!");
        return "redirect:/admin/login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         ModelMap model) {
        loginService.logOut(request, Constant.MEMBER_TYPE_GLOBLE);

        // 减少今日在线人数
        recordCountService.addCount(0, -1);
        return "index/login";
    }

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:/admin/dashboard";
    }


    @RequestMapping(value = "/dashboard")
    public String dashboard(HttpServletRequest request,
                            HttpServletResponse response,
                            ModelMap model) {

        // 获取当前登录人信息
        Admin admin = (Admin) request.getSession().getAttribute(Constant.MEMBER_TYPE_GLOBLE);
        AdminRole adminRole = adminRoleService.queryByPK(admin.getId());

        // 根据当前登录人的角色id获取对应的权限列表
        List<Modules> modulesList = modulesService.findFirstList(adminRole.getRole().getId());
        List<RoleModule> roleModuleList = roleModulesService.findListByRoleId(adminRole.getRole().getId());

        for (Modules module : modulesList) {
            for (RoleModule roleModule : roleModuleList) {
                if (module.getId() == roleModule.getModule().getParentId()) {
                    module.getModulesList().add(roleModule.getModule());
                }
            }
        }

        // 将该用户信息和权限列表放入界面中
        request.getSession().setAttribute("menu_moduleList", modulesList);
        request.getSession().setAttribute("menu_adminRole", adminRole);

        // 只有当前角色是超级管理员时，才查询以下数据
        if (adminRole.getRole().getId() == 6) {
            // 查询新增会员数量
            List<UserInfo> addUserList = userService.findListNew();
            model.addAttribute("addUserNum", addUserList.size());

            //查询新增用户帖子
            List<PostBase> addUserPostList = postBaseService.findListNew(3);
            model.addAttribute("addUserPostNum", addUserPostList.size());

            //查询新增帖子回复
            List<PostComment> postCommentList = postCommentService.findListNew(1);
            model.addAttribute("addPostCommentNum", postCommentList.size());

            //查询新增小说评论
            List<Comment> novelCommentList = commentService.findListNew(1);
            model.addAttribute("addNovelCommentNum", novelCommentList.size());

            //查询新增视频评论
            List<Comment> videoCommentList = commentService.findListNew(0);
            model.addAttribute("addVideoCommentNum", videoCommentList.size());

            //查询新增漫画评论
            List<Comment> comicCommentList = commentService.findListNew(2);
            model.addAttribute("addComicCommentNum", comicCommentList.size());

            //查询新增意见反馈
            List<Feedback> feedbackList = feedBackService.findListNew();
            model.addAttribute("addFeedBackNum", feedbackList.size());

            //查询新增举报
            List<Report> reportList = reportService.findListNew();
            model.addAttribute("addReportNum", reportList.size());

            //查询新增视频弹幕
            List<Barrage> videoBarrageList = barrageService.findListNew(0);
            model.addAttribute("addVideoBarrageNum", videoBarrageList.size());

            //查询新增漫画弹幕
            List<Barrage> comicBarrageList = barrageService.findListNew(1);
            model.addAttribute("addComicBarrageNum", comicBarrageList.size());

            //查询用户帖子总数
            List<PostBase> userPostList = postBaseService.findByType(3);
            model.addAttribute("userPostNum", userPostList.size());

            //查询官方帖子总数
            List<PostBase> postList = postBaseService.queryAll();
            model.addAttribute("postNum", postList.size() - userPostList.size());

            //查询加精帖子
            List<PostBase> essencePostList = postBaseService.findByEssence(1);
            model.addAttribute("essencePostNum", essencePostList.size());

            //查询投票贴
            List<PostBase> tpPostList = postBaseService.findByType(2);
            model.addAttribute("tpPostNum", tpPostList.size());

            //查询直播贴
            List<PostBase> zbPostList = postBaseService.findByType(1);
            model.addAttribute("zbPostNum", zbPostList.size());

            //查询已锁帖
            List<PostBase> lockPostList = postBaseService.findByStatus(1, 3);
            model.addAttribute("lockPostNum", lockPostList.size());


            //查询视频总集数
            List<Work> videoList = workService.findByType(4);
            Integer videoSum = 0;
            for (Work work : videoList) {
                videoSum += workVideoService.findByWorkId(work.getId()).size();
            }
            model.addAttribute("videoSum", videoSum);

            //查询漫画总部数
            List<Work> comicList = workService.findByType(6);
            Integer comicSum = 0;
            for (Work work : comicList) {
                comicSum += workComicService.findByWorkId(work.getId()).size();
            }
            model.addAttribute("comicSum", comicSum);

            //查询小说总部数
            List<Work> novelList = workService.findByType(5);
            Integer novelSum = 0;
            for (Work work : novelList) {
                novelSum += workNovelService.findByWorkId(work.getId()).size();
            }
            model.addAttribute("novelSum", novelSum);

            //查询弹幕总数
            List<Barrage> barrageList = barrageService.queryAll();
            model.addAttribute("barrageSum", barrageList.size());

            //查询全部会员
            List<UserInfo> userInfoList = userService.findByStatus(0);
            model.addAttribute("userInfoSum", userInfoList.size());

            //查询全部主创会员
            List<UserInfo> creatorList = userService.findByStatus(1);
            model.addAttribute("creatorSum", creatorList.size());

            // 查询今日访问人数和在线人数

            // 查询今日访问人数和在线人数
            RecordCount recordCount = recordCountService.queryByPK(1l);
            model.addAttribute("visitCount", recordCount.getVisitCount());
            model.addAttribute("onlineCount", recordCount.getOnlineCount());
        }

        return "index/index";
    }


}
