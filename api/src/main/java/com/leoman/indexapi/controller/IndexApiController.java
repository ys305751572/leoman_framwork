package com.leoman.indexapi.controller;

import com.leoman.banner.entity.Banner;
import com.leoman.category.entity.Category;
import com.leoman.common.core.ErrorType;
import com.leoman.creatordynamic.entity.vo.CreatorDynamicVo;
import com.leoman.exception.*;
import com.leoman.floatwin.entity.FloatWin;
import com.leoman.indexapi.service.*;
import com.leoman.province.entity.vo.ProvinceVo;
import com.leoman.province.service.ProvinceService;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.vo.UserRegisterVo;
import com.leoman.userapi.service.UserInfoService;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.RandomUtil;
import com.leoman.utils.Result;
import com.leoman.utils.WebUtil;
import com.leoman.vcode.VcodeService;
import com.leoman.work.entity.Work;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/8.
 */
@Controller
@RequestMapping(value = "/app/index")
public class IndexApiController {

    @Autowired
    private VcodeService vcodeService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LoginServiceApi loginService;

    @Autowired
    private BannerApiService bannerApiService;

    @Autowired
    private WorkService workService;

    @Autowired
    private CreatorDynamicApiService creatorDynamicApiService;

    @Autowired
    private CategoryApiService categoryApiService;

    @Autowired
    private FloatWinApiService floatWinApiService;

    @Autowired
    private ProvinceService provinceService;

    /**
     * @api {post} /app/index/sendCode 1、发送验证码
     * @apiVersion 0.0.1
     * @apiName index.sendCode
     * @apiGroup index
     *
     * @apiDescription 发送验证码
     *
     * @apiParam {String} mobile 手机号 (必填)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/sendCode")
    public void sendCode(HttpServletResponse response, String mobile) {
        if (!StringUtils.isNotEmpty(mobile)) {
            WebUtil.printApi(response, Result.failureApi(ErrorType.ERROR_CODE_0001));
        }
        if (vcodeService.sendKX(RandomUtil.getCode(6), mobile)) {
            WebUtil.printApi(response, Result.successApi());
        } else {
            WebUtil.printApi(response, Result.failureApi(ErrorType.ERROR_CODE_0002));
        }
    }

    /**
     * @api {post} /app/index/register 2、注册
     * @apiVersion 0.0.1
     * @apiName index.register
     * @apiGroup index
     *
     * @apiDescription 注册
     *
     * @apiParam {String}   mobile                    手机号 (必填)
     * @apiParam {String}   nickname                  昵称 (必填)
     * @apiParam {String}   password                  密码 (必填)
     * @apiParam {String}   code                      验证码 (必填)
     * @apiParam {Long}     inviteId                  邀请人ID
     *
     * @apiSuccess {Object} userinfo
     * @apiSuccess {String} userinfo.mobile           手机
     * @apiSuccess {String} userinfo.nickname         昵称
     * @apiSuccess {String} userinfo.gender           性别
     * @apiSuccess {String} userinfo.avater           头像
     * @apiSuccess {String} userinfo.level            会员等级
     * @apiSuccess {String} userinfo.funs             粉丝数
     * @apiSuccess {String} userinfo.focus            关注数
     * @apiSuccess {String} userinfo.posts            发帖数
     * @apiSuccess {String} userinfo.praises          主创点赞数
     * @apiSuccess {String} userinfo.coin             馒头币
     * @apiSuccess {String} userinfo.integral         积分
     * @apiSuccess {String} userinfo.isCreator        会员类型 0:普通会员 1:主创
     * @apiSuccess {Long}   userinfo.id               id
     * @apiSuccess {String} userinfo.createTime       注册时间
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {
     *          "userInfo": {
     *              "mobile": "13872116519",
     *              "nickname": "老大",
     *              "gender": 0,
     *              "avater": "",
     *              "level": 0,
     *              "funs": 0,
     *              "focus": 0,
     *              "posts": 0,
     *              "praises": 0,
     *              "coin": 0,
     *              "integral": 0,
     *              "isCreator": 0,
     *              "id": 14,
     *              "createTime": "2016-06-20 03:45:25"
     *          }
     *      }
     * }
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(HttpServletResponse response, UserRegisterVo urv) {
        String result = "";
        UserInfo userInfo = null;
        try {
            userInfo = userInfoService.register(urv);
        } catch (UserExistException e) {
            WebUtil.printApi(response, Result.failureApi(ErrorType.ERROR_CODE_0009));
        } catch (CodeErrorException e) {
            WebUtil.printApi(response, Result.failureApi(ErrorType.ERROR_CODE_0004));
        }
        result = JsonUtil.obj2ApiJson(Result.successInfoApi(userInfo, "userInfo"), "userLogin", "password", "num", "creator");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/index/login 3、登录
     * @apiVersion 0.0.1
     * @apiName index.login
     * @apiGroup index
     *
     * @apiDescription 登录
     *
     * @apiParam {String} mobile 手机号 (必填)
     * @apiParam {String} password 密码 (必填)
     *
     * @apiSuccess {Object}   userinfo
     * @apiSuccess {String}   userinfo.mobile           手机
     * @apiSuccess {String}   userinfo.nickname         昵称
     * @apiSuccess {String}   userinfo.gender           性别
     * @apiSuccess {String}   userinfo.avater           头像
     * @apiSuccess {String}   userinfo.level            会员等级
     * @apiSuccess {String}   userinfo.funs             粉丝数
     * @apiSuccess {String}   userinfo.focus            关注数
     * @apiSuccess {String}   userinfo.posts            发帖数
     * @apiSuccess {String}   userinfo.praises          主创点赞数
     * @apiSuccess {String}   userinfo.coin             馒头币
     * @apiSuccess {String}   userinfo.integral         积分
     * @apiSuccess {String}   userinfo.isCreator        会员类型 0:普通会员 1:主创
     * @apiSuccess {Integer}  userinfo.num              收藏数
     * @apiSuccess {Long}     userinfo.id               id
     * @apiSuccess {String}   userinfo.createTime       注册时间
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {
     *          "userInfo": {
     *              "mobile": "13872116519",
     *              "nickname": "老大",
     *              "gender": 0,
     *              "avater": "",
     *              "level": 0,
     *              "funs": 0,
     *              "focus": 0,
     *              "posts": 0,
     *              "praises": 0,
     *              "coin": 0,
     *              "integral": 0,
     *              "isCreator": 0,
     *              "num": 10,
     *              "id": 14,
     *              "createTime": "2016-06-20 03:45:25"
     *          }
     *      }
     * }
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletResponse response, UserRegisterVo urv) {
        String result = "";
        UserInfo userInfo = null;
        try {
            userInfo = loginService.login(urv.getMobile(), urv.getPassword());
        } catch (UsernamePasswordException e) {
            WebUtil.printApi(response, Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (UserNotFindException e) {
            WebUtil.printApi(response, Result.failureApi(ErrorType.ERROR_CODE_00010));
        }
        result = JsonUtil.obj2ApiJson(Result.successInfoApi(userInfo, "userInfo"), "userLogin", "password", "creator");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/index/listBanner 4、首页banner图列表
     * @apiVersion 0.0.1
     * @apiName index.listBanner
     * @apiGroup index
     *
     * @apiDescription 首页banner图列表
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {String}  list.cover            封面地址
     * @apiSuccess {String}  list.linkUrl          外链地址(为空:则跳转内部链接)
     * @apiSuccess {Integer} list.position         链接位子 0:帖子 1:视频 2:小说 3:漫画
     * @apiSuccess {Long}    list.workId           作品ID
     * @apiSuccess {Long}    list.id               banner图ID
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list": [
     *              {
     *                  "cover" : "http://localhost:8080",
     *                  "linkUrl" : "http://localhost:8080",
     *                  "position" : "0",
     *                  "workId" : "1"
     *                  "id" : 1
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/listBanner", method = RequestMethod.POST)
    public void listBanner(HttpServletResponse response) {
        List<Banner> list = bannerApiService.iFindList();
        String result = JsonUtil.obj2ApiJson(Result.successListApi(list), "work");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/index/listWorks 5、首页作品列表（废弃）
     * @apiVersion 0.0.1
     * @apiName index.listWorks
     * @apiGroup index
     *
     * @apiDescription 首页作品列表
     *
     * @apiParam   {String} time                    时间（年月日，时分秒不要，如：2016-6-20）
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {String}  list.name               标题
     * @apiSuccess {String}  list.description        说明
     * @apiSuccess {String}  list.cover              封面地址
     * @apiSuccess {Integer} list.isBarrage          主创发送弹幕状态，0：已发送，1=未发送
     * @apiSuccess {Long}    list.creatorId          主创ID
     * @apiSuccess {Long}    list.position           链接位子 1:视频 2:小说 3:漫画
     * @apiSuccess {Long}    list.workId             链接ID
     * @apiSuccess {Long}    list.id                 作品ID
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list": [
     *              {
     *                  "name" : "阳光午后",
     *                  "description" : "阳光下的午后",
     *                  "cover" : "http://localhost:8080",
     *                  "isBarrage" : "0",
     *                  "creatorId" : "12",
     *                  "position" : "1",
     *                  "workId" : "1"
     *                  "id" : 1
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/listWorks", method = RequestMethod.POST)
    public void listWorks(HttpServletResponse response, String time) {
        String result = "";

        try {
            List<Work> list = workService.iFindList(time);
            result = JsonUtil.obj2ApiJson(Result.successListApi(list), "workVideo", "workNovel", "workComic", "playNum", "barrageNum", "isEnd", "seriesCount", "category");
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/index/creatorDynamic 6、首页主创动态列表
     * @apiVersion 0.0.1
     * @apiName index.creatorDynamic
     * @apiGroup index
     *
     * @apiDescription 首页主创动态列表
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                 主创动态ID
     * @apiSuccess {Long}    list.creatorId          主创ID
     * @apiSuccess {Long}    list.userId             主创对应的用户ID
     * @apiSuccess {String}  list.name               主创名称
     * @apiSuccess {String}  list.avater             主创头像
     * @apiSuccess {Integer} list.status             类型：1:正在看帖子 2:正在回帖子 3:正在发帖子 4:正在看小说 5:正在看漫画 6:正在看网剧 7:正在发弹幕
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list": [
     *              {
     *                  "id": 12,
     *                  "creatorId": 1,
     *                  "userId": 1,
     *                  "name": "罗大佑",
     *                  "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *                  "status": 7
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/creatorDynamic", method = RequestMethod.POST)
    public void creatorDynamic(HttpServletResponse response) {
        String result = "";
        try {
            List<CreatorDynamicVo> list = creatorDynamicApiService.iFindList(null);
            result = JsonUtil.obj2ApiJson(Result.successListApi(list));
        } catch (CreatorNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00015));
        }
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/index/workList 7、作品集列表（废弃）
     * @apiVersion 0.0.1
     * @apiName index.workList
     * @apiGroup index
     *
     * @apiDescription 作品集列表
     *
     * @apiParam   {Integer} pageNum                 页码
     * @apiParam   {Integer} pageSize                每页显示条数
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                 作品集id
     * @apiSuccess {String}  list.name               作品集名称
     * @apiSuccess {Long}    list.type               类型：4:视频 5:小说 6:漫画
     * @apiSuccess {String}  list.url                图片路径
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "page":{
     *              "totalNum": 1,
     *              "totalPage": 1,
     *              "currentPage": 1
     *          },
     *          "list": [
     *              {
     *                  "id" : 1,
     *                  "name" : "器灵视频",
     *                  "type" : 4,
     *                  "url" : "http://4.16.3.22:8080/images/head.jpg"
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/workList", method = RequestMethod.POST)
    public void workList(HttpServletResponse response, Integer pageNum, Integer pageSize) {
        Page<Category> page = categoryApiService.iFindList(pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "count", "singer");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/index/signIn 8、签到
     * @apiVersion 0.0.1
     * @apiName index.signIn
     * @apiGroup index
     *
     * @apiDescription 签到
     *
     * @apiParam {Long}   userId    用户ID (必填)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {}
     * }
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public void signIn(HttpServletResponse response, Long userId) {
        String result = "";
        try {
            loginService.signIn(userId);
        } catch (UserNotFindException e) {
            WebUtil.printApi(response, Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (UserSignErrorException e) {
            WebUtil.printApi(response, Result.failureApi(ErrorType.ERROR_CODE_00014));
        }
        result = JsonUtil.obj2ApiJson(Result.successApi());
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/index/loginPlus 9、第三方登录
     * @apiVersion 0.0.1
     * @apiName index.loginPlus
     * @apiGroup index
     *
     * @apiDescription 第三方登录
     *
     * @apiParam {String}   openId      唯一标识               (必填)
     * @apiParam {Integer}  type        类型，1:微信 2:微博     (必填)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {
     *          "userInfo": {
     *              "mobile": "13872116519",
     *              "nickname": "老大",
     *              "gender": 0,
     *              "avater": "",
     *              "level": 0,
     *              "funs": 0,
     *              "focus": 0,
     *              "posts": 0,
     *              "praises": 0,
     *              "coin": 0,
     *              "integral": 0,
     *              "isCreator": 0,
     *              "num": 10,
     *              "id": 14,
     *              "createTime": "2016-06-20 03:45:25"
     *          }
     *      }
     * }
     */
    @RequestMapping(value = "/loginPlus", method = RequestMethod.POST)
    public void loginPlus(HttpServletResponse response, String openId, Integer type) {
        String result = "";
        UserInfo userInfo = null;
        try {
            userInfo = loginService.loginPlus(openId, type);
        } catch (ParamsErrorException e) {
            WebUtil.printApi(response, Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (UserNotFindException e) {
            WebUtil.printApi(response, Result.failureApi(ErrorType.ERROR_CODE_00010));
        }
        result = JsonUtil.obj2ApiJson(Result.successInfoApi(userInfo, "userInfo"), "userLogin", "password", "creator");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/index/floatWin 10、首页浮窗广告
     * @apiVersion 0.0.1
     * @apiName index.floatWin
     * @apiGroup index
     *
     * @apiDescription 首页浮窗广告
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                  浮窗广告id
     * @apiSuccess {String}  list.title               标题
     * @apiSuccess {Long}    list.cover               封面
     * @apiSuccess {String}  list.position            链接位置 0:帖子 1:视频 2:小说 3:漫画
     * @apiSuccess {String}  list.workId              作品id（分类id）
     * @apiSuccess {String}  list.detailId            作品详情id
     * @apiSuccess {String}  list.status              状态，0=显示，1=隐藏，默认为0
     * @apiSuccess {String}  list.time                时间
     * @apiSuccess {String}  list.createDate          创建时间
     * @apiSuccess {String}  list.updateDate          修改时间
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {
     *          "floatWin": {
     *              "title": "测试广告",
     *              "cover": "http://www.qq.com/image/default.jpg",
     *              "position": 0,
     *              "detailId": 2,
     *              "status": 0,
     *              "time": "1466502068224",
     *              "workId": 10,
     *              "id": 14,
     *              "createDate": "1466502068224",
     *              "updateDate": ""
     *          }
     *      }
     * }
     */
    @RequestMapping(value = "/floatWin", method = RequestMethod.POST)
    public void floatWin(HttpServletResponse response) {
        FloatWin floatWin = floatWinApiService.getFloatWinInfo();
        String result = JsonUtil.obj2ApiJson(Result.successInfoApi(floatWin, "floatWin"), "work");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/index/cityList 11、省市列表
     * @apiVersion 0.0.1
     * @apiName index.cityList
     * @apiGroup index
     *
     * @apiDescription 省市列表
     *
     * @apiSuccess {Object}    list
     * @apiSuccess {Long}      list.id                  省份id
     * @apiSuccess {String}    list.name                省份名称
     * @apiSuccess {Object}    list.cityList            城市列表
     * @apiSuccess {Long}      list.cityList.id         城市id
     * @apiSuccess {String}    list.cityList.name       城市名称
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {
     *          "list": [
     *              {
     *                  "id": 1,
     *                  "name": "北京",
     *                  "cityList": [
     *                      {
     *                          "id": 1,
     *                          "name": "北京市"
     *                      }
     *                  ]
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/cityList", method = RequestMethod.POST)
    public void cityList(HttpServletResponse response) {
        List<ProvinceVo> provinceList = provinceService.getProvinceList();
        String result = JsonUtil.obj2ApiJson(Result.successListApi(provinceList), "province");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/index/listRecommend 12、首页推荐列表
     * @apiVersion 0.0.1
     * @apiName index.listRecommend
     * @apiGroup index
     *
     * @apiDescription 首页推荐列表
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                 id
     * @apiSuccess {String}  list.title              大标题
     * @apiSuccess {String}  list.subTitle           小标题
     * @apiSuccess {String}  list.cover              封面地址
     * @apiSuccess {Integer} list.position           类型 0:帖子 1:视频 2:小说 3:漫画 4:资源 5:福利社
     * @apiSuccess {Long}    list.workId             关联ID
     * @apiSuccess {Integer} list.creatorBarrageNum  主创弹幕数（主创回复数）
     * @apiSuccess {Integer} list.barrageNum         粉丝弹幕数（粉丝回复数）
     * @apiSuccess {String}  list.authorName         作者名称
     * @apiSuccess {String}  list.authorHead         作者头像
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list": [
     *              {
     *                  "title": "呱",
     *                  "subTitle": "呱唧呱唧",
     *                  "cover": "http://images.17173.com/2015/news/2015/02/09/mj0209dl01s.jpg",
     *                  "position": 1,
     *                  "workId": 13,
     *                  "creatorBarrageNum": 0,
     *                  "barrageNum": 0,
     *                  "authorName": "",
     *                  "authorHead": "",
     *                  "id": 2
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/listRecommend", method = RequestMethod.POST)
    public void listRecommend(HttpServletResponse response) {
        String result = JsonUtil.obj2ApiJson(Result.successListApi(workService.iFindList()), "createDate", "updateDate");
        WebUtil.printApi(response, result);
    }
}
