package com.leoman.userapi.controller;

import com.leoman.address.entity.Address;
import com.leoman.common.core.ErrorType;
import com.leoman.creatordynamic.entity.vo.DynamicVo;
import com.leoman.exception.*;
import com.leoman.indexapi.service.CreatorDynamicApiService;
import com.leoman.indexapi.service.WorkService;
import com.leoman.post.entity.PostBase;
import com.leoman.reourceapi.service.impl.ResourceApiServiceImpl;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserIntegral;
import com.leoman.user.entity.vo.FansVo;
import com.leoman.user.entity.vo.UserInfoPlusVo;
import com.leoman.userapi.service.AddressServiceApi;
import com.leoman.userapi.service.UserInfoService;
import com.leoman.userapi.service.UserLoginServiceApi;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.Result;
import com.leoman.utils.WebUtil;
import com.leoman.work.entity.vo.WorkComicVo;
import com.leoman.work.entity.vo.WorkNovelVo;
import com.leoman.work.entity.vo.WorkVideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/8.
 */
@Controller
@RequestMapping(value = "/app/user")
public class UseController {

    @Autowired
    private UserLoginServiceApi userLoginService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AddressServiceApi addressService;

    @Autowired
    private CreatorDynamicApiService creatorDynamicApiService;

    @Autowired
    private WorkService workService;

    /**
     * @api {post} /app/user/info 1、用户详情
     * @apiVersion 0.0.1
     * @apiName user.info
     * @apiGroup user
     * @apiDescription 用户详情
     *
     * @apiParam {Long} sourceId        目标用户id           (必传)
     * @apiParam {Long} userId          用户id               (必传)
     *
     * @apiSuccess {Object}  userInfo
     * @apiSuccess {String}  userInfo.mobile           手机
     * @apiSuccess {String}  userInfo.nickname         昵称
     * @apiSuccess {Integer} userInfo.gender           性别 （男:0 女:1）
     * @apiSuccess {String}  userInfo.avater           头像
     * @apiSuccess {Integer} userInfo.level            会员等级
     * @apiSuccess {Integer} userInfo.funs             粉丝数
     * @apiSuccess {Integer} userInfo.focus            关注数
     * @apiSuccess {Integer} userInfo.posts            发帖数
     * @apiSuccess {Integer} userInfo.praises          主创点赞数
     * @apiSuccess {Integer} userInfo.num              收藏数
     * @apiSuccess {Integer} userInfo.coin             馒头币
     * @apiSuccess {Integer} userInfo.integral         积分
     * @apiSuccess {Integer} userInfo.isCreator        会员类型 0:普通会员 1:主创
     * @apiSuccess {Long}    userInfo.id               id
     * @apiSuccess {String}  userInfo.createTime       注册时间
     * @apiSuccess {Integer} userInfo.isAttention      是否关注 （0：已关注，1=未关注）
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
     *              "num": 20,
     *              "coin": 0,
     *              "integral": 0,
     *              "isCreator": 0,
     *              "id": 14,
     *              "createTime": "2016-06-20 03:45:25"
     *          }
     *      }
     * }
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public void info(HttpServletResponse response, Long sourceId, Long userId) {
        String result = "";
        try {
            UserInfo userInfo = userInfoService.findOne(sourceId, userId);
            result = JsonUtil.obj2ApiJson(Result.successInfoApi(userInfo, "userInfo"), "password", "creator");
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/modifyInfo 2、修改用户信息
     * @apiVersion 0.0.1
     * @apiName user.modifyInfo
     * @apiGroup user
     * @apiDescription 修改用户信息
     *
     * @apiParam {Long} userId                   (必传)
     * @apiParam {String} nickname               昵称
     * @apiParam {Integer} gender                性别，0=男，1=女
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/modifyInfo", method = RequestMethod.POST)
    public void modifyInfo(HttpServletResponse response, Long userId, String nickname, Integer gender) {
        String result = "";
        try {
            userLoginService.modifyUserInfo(userId, nickname, gender);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/modifyPwd 3、修改/忘记 密码
     * @apiVersion 0.0.1
     * @apiName user.modifyPwd
     * @apiGroup user
     * @apiDescription 修改密码
     *
     * @apiParam {Long}    userId                   用户ID (修改密码时必传)
     * @apiParam {String}  mobile                   手机号 (忘记密码时必传)
     * @apiParam {String}  oldPwd                   原密码 (必传)
     * @apiParam {String}  newPwd                   新密码 (必传)
     * @apiParam {String}  code                     验证码 (必传)
     * @apiParam {Integer} type                     类型：1=修改密码，2=忘记密码（为空的话，默认为修改密码）
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
    public void modifyPwd(HttpServletResponse response, Long userId, String mobile, String oldPwd, String newPwd, String code, Integer type) {
        String result = "";
        try {
            userLoginService.modifyPwd(userId, mobile, oldPwd, newPwd, code, type);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (CodeErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0004));
        } catch (OldPasswordErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0005));
        } catch (OldNewPasswordException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0006));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/modifyAvater 4、修改用户头像
     * @apiVersion 0.0.1
     * @apiName user.modifyAvater
     * @apiGroup user
     * @apiDescription 修改用户头像
     *
     * @apiParam {Long} userId                   用户id(必传)
     * @apiParam {Stream} file                   头像文件(必传)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/modifyAvater", method = RequestMethod.POST)
    public void modifyAvater(HttpServletResponse response, Long userId, MultipartRequest multipartRequest) {
        String result = "";
        try {
            userLoginService.modifyHead(userId, multipartRequest);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        }

        WebUtil.printApi(response, result);
    }


    /**
     * @api {post} /app/user/listAddress 5、用户地址列表
     * @apiVersion 0.0.1
     * @apiName user.listAddress
     * @apiGroup user
     * @apiDescription 用户地址列表
     *
     * @apiParam {Long}      userId                用户ID        (必传)
     * @apiParam {Integer}   pageNum               页码          (必传)
     * @apiParam {Integer}   pageSize              每页显示条数   (必传)
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {String}  list.name             收件人姓名
     * @apiSuccess {String}  list.mobile           收件人手机
     * @apiSuccess {String}  list.address          收件人地址
     * @apiSuccess {Integer} list.isDefault        是否默认地址，0=否，1=是
     * @apiSuccess {Long}    list.id               地址ID
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data": {
     *          "page":{
     *              "totalNum": 1,
     *              "totalPage": 1,
     *              "currentPage": 1
     *          },
     *          "list": [
     *              {
     *                  "name": "白小飞",
     *                  "mobile": "15965887421",
     *                  "address": "民族大道青年城",
     *                  "isDefault": 0,
     *                  "id": 3
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/listAddress", method = RequestMethod.POST)
    public void listAddress(HttpServletResponse response, Long userId, Integer pageNum, Integer pageSize) {
        String result = "";
        try {
            Page<Address> page = addressService.iPageByUserId(userId, pageNum, pageSize);
            Map<String, Object> dataMap = JsonUtil.fitting(page);
            result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "userInfo");
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0002));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/insertAddress 6、添加/修改地址
     * @apiVersion 0.0.1
     * @apiName user.insertAddress
     * @apiGroup user
     * @apiDescription 添加地址
     *
     * @apiParam {Long} userId                   (必传)
     * @apiParam {Long} id                       地址ID(编辑时必传)
     * @apiParam {String} name                   收件人姓名
     * @apiParam {String} mobile                 收件人手机
     * @apiParam {String} address                收件人地址
     * @apiParam {Integer} isDefault             是否默认，1=是，0=否
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/insertAddress", method = RequestMethod.POST)
    public void insertAddress(HttpServletResponse response, Long userId, Long id, String name, String mobile, String address, Integer isDefault) {
        String result = "";
        try {
            addressService.iSaveInfo(userId, id, name, mobile, address, isDefault);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/delAddress 7、删除地址
     * @apiVersion 0.0.1
     * @apiName user.delAddress
     * @apiGroup user
     * @apiDescription 删除地址
     *
     * @apiParam {Long} userId                   (必传)
     * @apiParam {Long} id                       (必传)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/delAddress", method = RequestMethod.POST)
    public void delAddress(HttpServletResponse response, Long userId, Long id) {
        String result = "";
        try {
            addressService.iDelInfo(userId, id);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (AddressErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0007));
        } catch (UserAddressErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0008));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/attentionList 8、我的关注
     * @apiVersion 0.0.1
     * @apiName user.attentionList
     * @apiGroup user
     * @apiDescription 我的关注
     *
     * @apiParam {Long}     userId                用户ID         (必传)
     * @apiParam {Integer}  pageNum               页码           (必传)
     * @apiParam {Integer}  pageSize              每页显示条数   (必传)
     *
     * @apiSuccess {Object} list
     * @apiSuccess {Long}   list.userId           用户Id
     * @apiSuccess {String} list.nickname         用户昵称
     * @apiSuccess {String} list.avater           用户头像
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/attentionList", method = RequestMethod.POST)
    public void attentionList(HttpServletResponse response, Long userId, Integer pageNum, Integer pageSize) {
        Page<FansVo> page = userLoginService.attentionPage(userId, pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "isAttention");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/fans 9、我的粉丝
     * @apiVersion 0.0.1
     * @apiName user.fans
     * @apiGroup user
     * @apiDescription 我的粉丝
     *
     * @apiParam {Long}      userId                用户ID   (必传)
     * @apiParam {Integer}   pageNum               页码           (必传)
     * @apiParam {Integer}   pageSize              每页显示条数   (必传)
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.userId           用户Id
     * @apiSuccess {String}  list.nickname         用户昵称
     * @apiSuccess {String}  list.avater           用户头像
     * @apiSuccess {Integer} list.isAttention      是否关注，0=已关注，1=未关注
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/fans", method = RequestMethod.POST)
    public void fans(HttpServletResponse response, Long userId, Integer pageNum, Integer pageSize) {
        Page<FansVo> page = userLoginService.fans(userId, pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap));
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/attention 10、关注or取消关注
     * @apiVersion 0.0.1
     * @apiName user.attention
     * @apiGroup user
     * @apiDescription 关注用户
     *
     * @apiParam {Long}    userId                   用户ID                          （必传）
     * @apiParam {Long}    sourceId                 目标用户ID（主创用户ID）          （必传）
     * @apiParam {Integer} isCreator                是否主创 0:是，1:否 默认为否
     * @apiParam {Integer} type                     类型，1：关注 2：取消关注         （必传）
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/attention", method = RequestMethod.POST)
    public void attention(HttpServletResponse response, Long userId, Long sourceId, Integer isCreator, Integer type) {
        String result = "";
        try {
            userInfoService.attention(userId, sourceId, isCreator, type);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (CreatorNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00015));
        } catch (AttentionExistException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00017));
        } catch (AttentionNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00018));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/infoPlus 11、主创用户详情
     * @apiVersion 0.0.1
     * @apiName user.infoPlus
     * @apiGroup user
     * @apiDescription 主创用户详情
     *
     * @apiParam {Long} sourceId        主创用户id           (必传)
     * @apiParam {Long} userId          用户id               (必传)
     *
     * @apiSuccess {Object}  infoPlus
     * @apiSuccess {Long}    infoPlus.id                    主创ID
     * @apiSuccess {String}  infoPlus.name                  主创名称
     * @apiSuccess {String}  infoPlus.cover                 封面
     * @apiSuccess {Integer} infoPlus.avater                头像
     * @apiSuccess {String}  infoPlus.province              所在省份
     * @apiSuccess {Integer} infoPlus.city                  所在城市
     * @apiSuccess {Integer} infoPlus.description           简介
     * @apiSuccess {Integer} infoPlus.level                 等级
     * @apiSuccess {Integer} infoPlus.fans                  粉丝数
     * @apiSuccess {Integer} infoPlus.focus                 关注数
     * @apiSuccess {Integer} infoPlus.isAttention           是否关注 （0：已关注，1=未关注）
     * @apiSuccess {Integer} infoPlus.stillList             剧照列表
     * @apiSuccess {Integer} infoPlus.stillList.id          剧照ID
     * @apiSuccess {Integer} infoPlus.stillList.url         剧照路径
     * @apiSuccess {Integer} infoPlus.lifeList              生活照列表
     * @apiSuccess {Integer} infoPlus.lifeList.id           生活照ID
     * @apiSuccess {Integer} infoPlus.lifeList.url          生活照路径
     * @apiSuccess {Integer} infoPlus.weibo                 微博
     * @apiSuccess {Integer} infoPlus.fanClub               后援会
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {
     *          "infoPlus": {
     *              "id": 1,
     *              "name": "李冰冰",
     *              "cover": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *              "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *              "province": "河北省",
     *              "city": "邯郸市",
     *              "description": "巴拉巴拉",
     *              "level": 1,
     *              "fans": 52,
     *              "focus": 36,
     *              "isAttention": 1,
     *              "stillList": [
     *                  {
     *                      "id": 1,
     *                      "url": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q"
     *                  }
     *              ],
     *              "lifeList": [
     *                  {
     *                      "id": 1,
     *                      "url": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q"
     *                  }
     *              ],
     *              "weibo": "http://www.weiBo.com",
     *              "fanClub": "23828382,"
     *          }
     *      }
     * }
     */
    @RequestMapping(value = "/infoPlus", method = RequestMethod.POST)
    public void infoPlus(HttpServletResponse response, Long sourceId, Long userId) {
        String result = "";
        try {
            UserInfoPlusVo userInfoPlus = userInfoService.findCreatorInfo(sourceId, userId);
            result = JsonUtil.obj2ApiJson(Result.successInfoApi(userInfoPlus, "infoPlus"));
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (CreatorNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00015));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/creatorDynamic 12、主创今日动态列表
     * @apiVersion 0.0.1
     * @apiName user.creatorDynamic
     * @apiGroup user
     * @apiDescription 主创动态列表
     *
     * @apiParam {Long}   creatorId        主创用户id           (必传)
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {String}  list.date                           日期
     * @apiSuccess {Object}  list.dynamicList                    主创动态列表
     * @apiSuccess {Long}    list.dynamicList.id                 主创动态ID
     * @apiSuccess {String}  list.dynamicList.content            内容
     * @apiSuccess {Long}    list.dynamicList.creatorId          主创ID
     * @apiSuccess {String}  list.dynamicList.name               主创名称
     * @apiSuccess {String}  list.dynamicList.avater             主创头像
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list": [
     *              {
     *                  "date": "2016-06-27",
     *                  "dynamicList": [
     *                      {
     *                          "id": 12,
     *                          "content": "正在发送弹幕",
     *                          "creatorId": 1,
     *                          "name": "罗大佑",
     *                          "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q"
     *                      }
     *                  ],
     *                  "date": "2016-06-28",
     *                  "dynamicList": [
     *                      {
     *                          "id": 13,
     *                          "content": "正在查看帖子",
     *                          "creatorId": 2,
     *                          "name": "李冰冰",
     *                          "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q"
     *                      }
     *                  ]
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/creatorDynamic", method = RequestMethod.POST)
    public void creatorDynamic(HttpServletResponse response, Long creatorId) {
        String result = "";
        try {
            if (null == creatorId) {
                result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
            } else {
                List<DynamicVo> list = creatorDynamicApiService.iFindList2(creatorId);
                result = JsonUtil.obj2ApiJson(Result.successListApi(list));
            }
        } catch (CreatorNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00015));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/collectList 13、我的收藏
     * @apiVersion 0.0.1
     * @apiName user.collectList
     * @apiGroup user
     * @apiDescription 我的收藏
     *
     * @apiParam   {Long}     userId                用户ID(必传)
     * @apiParam   {Integer}  type                  类型：1=视频，2=小说，3=漫画，4=帖子         （必传）
     * @apiParam   {Integer}  pageNum               页码
     * @apiParam   {Integer}  pageSize              每页显示条数
     *
     * @apiSuccess {Object}   list                  （视频列表）
     * @apiSuccess {Long}     list.id               视频ID
     * @apiSuccess {String}   list.name             视频名称
     * @apiSuccess {String}   list.cover            视频封面
     * @apiSuccess {Integer}  list.series           集数
     * @apiSuccess {String}   list.linkUrl          源地址
     * @apiSuccess {Integer}  list.playNum          播放量
     * @apiSuccess {Integer}  list.barrageNum       弹幕数
     * @apiSuccess {Integer}  list.isEnd            是否完结 0:未完结 1:已完结
     * or
     * @apiSuccess {Object}  list                   （小说列表）
     * @apiSuccess {Long}    list.id                小说ID
     * @apiSuccess {String}  list.name              小说名称
     * @apiSuccess {String}  list.cover             小说封面
     * @apiSuccess {String}  list.author            小说作者
     * @apiSuccess {Integer} list.series            章数
     * @apiSuccess {Integer} list.playNum           播放量
     * @apiSuccess {String}  list.updateTime        更新时间
     * @apiSuccess {Integer} list.isEnd             是否完结 0:未完结 1:已完结
     * or
     * @apiSuccess {Object}  list                   （漫画列表）
     * @apiSuccess {Long}    list.id                漫画ID
     * @apiSuccess {String}  list.name              漫画名称
     * @apiSuccess {String}  list.cover             漫画封面
     * @apiSuccess {String}  list.author            漫画作者
     * @apiSuccess {Integer} list.series            章数
     * @apiSuccess {Integer} list.playNum           播放量
     * @apiSuccess {Integer} list.barrageNum        弹幕数
     * @apiSuccess {String}  list.updateTime        更新时间
     * @apiSuccess {Integer} list.isEnd             是否完结 0:未完结 1:已完结
     * or
     * @apiSuccess {Object}  list                   （帖子列表）
     * @apiSuccess {Long}    list.id                帖子ID
     * @apiSuccess {Integer} list.type              帖子类型 0:普通帖 1:直播帖 2:投票帖 3:用户帖
     * @apiSuccess {String}  list.content           帖子内容
     * @apiSuccess {Long}    list.createDate        发布时间
     * @apiSuccess {Integer} list.status            状态（0：正常，1=锁帖）
     * @apiSuccess {Integer} list.comment           评论数
     * @apiSuccess {Integer} list.praise            点赞数
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/collectList", method = RequestMethod.POST)
    public void collectList(HttpServletResponse response, Long userId, Integer type, Integer pageNum, Integer pageSize) {
        if (null == userId || null == type) {
            WebUtil.printApi(response, Result.failureApi(ErrorType.ERROR_CODE_0001));
            return;
        }

        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);

        Map<String, Object> dataMap = null;
        switch (type) {
            case 1:
                // 视频
                Page<WorkVideoVo> page1 = workService.videoPage(userId, info[0], info[1]);
                dataMap = JsonUtil.fitting(page1);
                break;
            case 2:
                // 小说
                Page<WorkNovelVo> page2 = workService.novelPage(userId, info[0], info[1]);
                dataMap = JsonUtil.fitting(page2);
                break;
            case 3:
                // 漫画
                Page<WorkComicVo> page3 = workService.comicPage(userId, info[0], info[1]);
                dataMap = JsonUtil.fitting(page3);
                break;
            case 4:
                // 帖子
                Page<PostBase> page4 = userInfoService.findCollectListByUserIdPost(userId, info[0], info[1]);
                dataMap = JsonUtil.fitting(page4);
                break;
        }
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "isCollect", "creatorList", "category", "userInfo", "user", "imageList", "userList", "commentList", "tpPostSubList", "zbSubList", "length");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/collect 14、收藏or取消收藏
     * @apiVersion 0.0.1
     * @apiName user.collect
     * @apiGroup user
     * @apiDescription 关注用户
     *
     * @apiParam {Long}    userId                   用户ID                                            （必传）
     * @apiParam {Long}    sourceId                 收藏目标ID                                        （必传）
     * @apiParam {Integer} type                     收藏目标类型：1=视频，2=小说，3=漫画，4=帖子         （必传）
     * @apiParam {Integer} status                   类型，1：收藏 2：取消收藏                           （必传）
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/collect", method = RequestMethod.POST)
    public void collect(HttpServletResponse response, Long userId, Long sourceId, Integer type, Integer status) {
        String result = "";
        try {
            userInfoService.collect(userId, sourceId, type, status);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (CollectExistException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00019));
        } catch (CollectNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00020));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/modifyInfoPlus 15、修改主创用户信息
     * @apiVersion 0.0.1
     * @apiName user.modifyInfoPlus
     * @apiGroup user
     * @apiDescription 修改主创用户信息
     *
     * @apiParam {Long}     creatorId       主创用户id           (必传)
     * @apiParam {Long}     cityId          城市id
     * @apiParam {String}   description     简介
     * @apiParam {String}   weibo           微博
     * @apiParam {String}   experience      后援会
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {}
     * }
     */
    @RequestMapping(value = "/modifyInfoPlus", method = RequestMethod.POST)
    public void modifyInfoPlus(HttpServletResponse response, Long creatorId, Long cityId, String description, String weibo, String experience) {
        String result = "";
        try {
            userInfoService.modifyCreatorInfo(creatorId, cityId, description, weibo, experience);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (CreatorNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00015));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/modifyAvaterPlus 16、修改主创头像、剧照，生活照
     * @apiVersion 0.0.1
     * @apiName user.modifyAvaterPlus
     * @apiGroup user
     * @apiDescription 修改用户头像
     *
     * @apiParam {Long}    creatorId                主创用户id             (必传)
     * @apiParam {Stream}  avater                   头像文件
     * @apiParam {Stream}  stillList                剧照（数组）
     * @apiParam {Stream}  lifeList                 生活照（数组）
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/modifyAvaterPlus", method = RequestMethod.POST)
    public void modifyAvaterPlus(HttpServletResponse response, Long creatorId, MultipartRequest multipartRequest) {
        String result = "";
        try {
            userLoginService.modifyHeadPlus(creatorId, multipartRequest);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (CreatorNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00015));
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/deleteImage 17、删除主创剧照，生活照
     * @apiVersion 0.0.1
     * @apiName user.deleteImage
     * @apiGroup user
     * @apiDescription 删除主创剧照，生活照
     *
     * @apiParam {Long}     id              图片id                     (必传)
     * @apiParam {Integer}  type            图片类别 0:剧照 1:生活照    (必传)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
    public void deleteImage(HttpServletResponse response, Long id, Integer type) {
        String result = "";
        try {
            userLoginService.deleteImage(id, type);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/insertDynamic 18、主创添加动态
     * @apiVersion 0.0.1
     * @apiName user.insertDynamic
     * @apiGroup user
     * @apiDescription 主创添加动态
     *
     * @apiParam {Long}       creatorId           主创id                   (必传)
     * @apiParam {Long}       time                时间                     (必传)
     * @apiParam {Integer}    status              状态                     (必传)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/insertDynamic", method = RequestMethod.POST)
    public void insertDynamic(HttpServletResponse response, Long creatorId, Long time, Integer status) {
        String result = "";
        try {
            userLoginService.insertDynamic(creatorId, time, status);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (CreatorNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00015));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/deleteDynamic 19、主创删除动态
     * @apiVersion 0.0.1
     * @apiName user.deleteDynamic
     * @apiGroup user
     * @apiDescription 主创删除动态
     *
     * @apiParam {Long}       id           主创id                   (必传)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/deleteDynamic", method = RequestMethod.POST)
    public void deleteDynamic(HttpServletResponse response, Long id) {
        String result = "";
        try {
            userLoginService.deleteDynamic(id);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/binding 20、第三方绑定
     * @apiVersion 0.0.1
     * @apiName user.binding
     * @apiGroup user
     * @apiDescription 第三方绑定
     *
     * @apiParam {Long}   userId        用户id                (必传)
     * @apiParam {String} weixin        微信唯一标识           (如果想取消绑定就传空)
     * @apiParam {String} weibo         微博唯一标识           (如果想取消绑定就传空)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {}
     * }
     */
    @RequestMapping(value = "/binding", method = RequestMethod.POST)
    public void binding(HttpServletResponse response, Long userId, String weixin, String weibo, Integer type) {
        String result = "";
        try {
            userInfoService.binding(userId, weixin, weibo);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/integralList 21、用户经验值记录
     * @apiVersion 0.0.1
     * @apiName user.integralList
     * @apiGroup user
     * @apiDescription 用户经验值记录
     *
     * @apiParam   {Long}    userId                  用户id                (必传)
     * @apiParam   {Integer} pageNum                 页码
     * @apiParam   {Integer} pageSize                每页显示条数
     *
     * @apiSuccess {Object}   list
     * @apiSuccess {String}   list.content          内容
     * @apiSuccess {Integer}  list.count            经验值
     * @apiSuccess {String}   list.createDate       创建时间
     * @apiSuccess {String}   list.updateDate       更新时间
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
     *                  "content" : "帖子被加精华",
     *                  "count" : 50,
     *                  "createDate" : 1468292811675,
     *                  "updateDate": ""
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/integralList", method = RequestMethod.POST)
    public void integralList(HttpServletResponse response, Long userId, Integer pageNum, Integer pageSize) {
        String result = "";
        try {
            Page<UserIntegral> page = userInfoService.integralPage(userId, pageNum, pageSize);
            Map<String, Object> dataMap = JsonUtil.fitting(page);
            result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "userInfo", "type", "id");
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/praise 22、点赞or取消点赞
     * @apiVersion 0.0.1
     * @apiName user.praise
     * @apiGroup user
     * @apiDescription 点赞or取消点赞
     *
     * @apiParam {Long}    userId                   用户ID                                            （必传）
     * @apiParam {Long}    sourceId                 点赞目标ID（帖子id）                               （必传）
     * @apiParam {Integer} type                     点赞目标类型：0:普通帖 1:直播帖 2:投票帖 3:用户帖     （必传）
     * @apiParam {Integer} status                   类型，1：点赞 2：取消点赞                           （必传）
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/praise", method = RequestMethod.POST)
    public void praise(HttpServletResponse response, Long userId, Long sourceId, Integer type, Integer status) {
        String result = "";
        try {
            userInfoService.praise(userId, sourceId, type, status);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (PraiseExistException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00025));
        } catch (PraiseNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00026));
        }

        WebUtil.printApi(response, result);
    }
}
