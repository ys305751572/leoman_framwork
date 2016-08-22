package com.leoman.messageapi.controller;

import com.leoman.common.core.ErrorType;
import com.leoman.entity.Constant;
import com.leoman.exception.SendMessageErrorException;
import com.leoman.exception.UserNotFindException;
import com.leoman.indexapi.service.WorkService;
import com.leoman.message.entity.Message;
import com.leoman.message.entity.vo.MessageVo;
import com.leoman.messageapi.service.MessageApiService;
import com.leoman.messageapi.service.PrivateLetterApiService;
import com.leoman.messageapi.vo.PostPraiseUserVo;
import com.leoman.privateletter.entity.PrivateLetter;
import com.leoman.reourceapi.service.impl.ResourceApiServiceImpl;
import com.leoman.userapi.service.UserInfoService;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.Result;
import com.leoman.utils.WebUtil;
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
 * Created by Administrator on 2016/6/12.
 */
@Controller
@RequestMapping(value = "/app/message")
public class MessageApiController {

    @Autowired
    private MessageApiService messageApiService;

    @Autowired
    private PrivateLetterApiService privateLetterApiService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private WorkService workService;

    /**
     * @api {post} /app/message/replyMeList 1、回复我的列表
     * @apiVersion 0.0.1
     * @apiName message.replyMeList
     * @apiGroup message
     * @apiDescription 回复我的列表
     *
     * @apiParam   {Long}    userId                   用户ID（必传）
     * @apiParam   {Integer} type                     类型 4:视频 5:漫画 6:小说 7:帖子 （必传）
     * @apiParam   {Integer} pageNum                  页码
     * @apiParam   {Integer} pageSize                 每页显示条数
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                  id
     * @apiSuccess {Long}    list.userId              回复用户ID
     * @apiSuccess {String}  list.name                回复用户名称
     * @apiSuccess {String}  list.avater              回复用户封面
     * @apiSuccess {Integer} list.content             回复内容
     * @apiSuccess {String}  list.sourceId            关联目标ID
     * @apiSuccess {Integer} list.sourceType          关联目标类型 (4:视频 5:漫画 6:小说 7:帖子)
     * @apiSuccess {Integer} list.sourceName          关联目标名称
     * @apiSuccess {Integer} list.sourceAvater        关联目标封面
     * @apiSuccess {Integer} list.sourceAuthor        关联目标作者
     * @apiSuccess {Integer} list.createDate          创建时间
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list": [
     *              {
     *                  "id": 9,
     *                  "userId": 2,
     *                  "name": "复合机",
     *                  "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *                  "content": "呵呵",
     *                  "sourceId": 8,
     *                  "sourceType": 4,
     *                  "sourceName": "海绵宝宝粉",
     *                  "sourceAvater": "",
     *                  "sourceAuthor": "",
     *                  "createDate": 1466664116692
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/replyMeList", method = RequestMethod.POST)
    public void replyMeList(HttpServletResponse response, Long userId, Integer type, Integer pageNum, Integer pageSize) {
        if (null == userId || null == type) {
            WebUtil.printApi(response, Result.failureApi(ErrorType.ERROR_CODE_0001));
            return;
        }

        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);

        Map<String, Object> dataMap = null;
        switch (type) {
            case 4:
                // 视频
                Page<MessageVo> page1 = workService.pageMessage(userId, Constant.CATEGORY_TYPE_004, info[0], info[1]);
                dataMap = JsonUtil.fitting(page1);
                break;
            case 5:
                // 小说
                Page<MessageVo> page2 = workService.pageMessage(userId, Constant.CATEGORY_TYPE_005, info[0], info[1]);
                dataMap = JsonUtil.fitting(page2);
                break;
            case 6:
                // 漫画
                Page<MessageVo> page3 = workService.pageMessage(userId, Constant.CATEGORY_TYPE_006, info[0], info[1]);
                dataMap = JsonUtil.fitting(page3);
                break;
            case 7:
                // 帖子
                Page<MessageVo> page4 = userInfoService.pageMessage(userId, info[0], info[1]);
                dataMap = JsonUtil.fitting(page4);
                break;
        }

        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap));
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/message/praiseList 2、点赞我的帖子的用户列表
     * @apiVersion 0.0.1
     * @apiName message.praiseList
     * @apiGroup message
     * @apiDescription 点赞我的帖子的用户列表
     *
     * @apiParam   {Long}    userId                   用户ID
     * @apiParam   {Integer} pageNum                  页码
     * @apiParam   {Integer} pageSize                 每页显示条数
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                  id
     * @apiSuccess {Long}    list.nickname            点赞用户ID
     * @apiSuccess {String}  list.avater              点赞用户头像
     * @apiSuccess {String}  list.postName            帖子名称
     * @apiSuccess {Integer} list.createDate          创建时间
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list": [
     *              {
     *                  "id": 9,
     *                  "nickname": "复合机",
     *                  "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *                  "postName": "海绵宝宝粉",
     *                  "createDate": 1466664116692
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/praiseList", method = RequestMethod.POST)
    public void praiseList(HttpServletResponse response, Long userId, Integer pageNum, Integer pageSize) {
        Page<PostPraiseUserVo> page = messageApiService.praisePage(userId, pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap));
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/message/messageList 3、通知消息列表
     * @apiVersion 0.0.1
     * @apiName message.messageList
     * @apiGroup message
     * @apiDescription 通知消息列表
     *
     * @apiParam   {Long}    userId                   用户ID
     * @apiParam   {Integer} pageNum                  页码
     * @apiParam   {Integer} pageSize                 每页显示条数
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                  id
     * @apiSuccess {Long}    list.title               消息标题
     * @apiSuccess {String}  list.content             消息内容
     * @apiSuccess {Integer} list.createDate          创建时间
     * @apiSuccess {String}  list.updateDate          修改时间
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
     *                  "title": "器灵帖子",
     *                  "content": "器灵帖子",
     *                  "id": 1,
     *                  "createDate": 1466501548562,
     *                  "updateDate": ""
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/messageList", method = RequestMethod.POST)
    public void messageList(HttpServletResponse response, Long userId, Integer pageNum, Integer pageSize) {
        String result = "";
        try {
            Page<Message> page = messageApiService.messagePage(userId, pageNum, pageSize);
            Map<String, Object> dataMap = JsonUtil.fitting(page);
            result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "toObject", "sendDate");
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        }
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/message/letterList 4、我的私信列表
     * @apiVersion 0.0.1
     * @apiName message.letterList
     * @apiGroup message
     * @apiDescription 我的私信列表
     *
     * @apiParam   {Long}      userId                         用户ID（必传）
     * @apiParam   {Integer}   pageNum                        页码
     * @apiParam   {Integer}   pageSize                       每页显示条数
     *
     * @apiSuccess {Object}    list
     * @apiSuccess {Long}      list.id                        id
     * @apiSuccess {Object}    list.fromUserInfo              发送用户
     * @apiSuccess {Long}      list.fromUserInfo.userId       发送用户ID
     * @apiSuccess {String}    list.fromUserInfo.nickname     发送用户名称
     * @apiSuccess {String}    list.fromUserInfo.avater       发送用户头像
     * @apiSuccess {Object}    list.toUserInfo                接收用户
     * @apiSuccess {Long}      list.toUserInfo.userId         接收用户ID
     * @apiSuccess {String}    list.toUserInfo.nickname       接收用户名称
     * @apiSuccess {String}    list.toUserInfo.avater         接收用户头像
     * @apiSuccess {String}    list.content                   文本内容
     * @apiSuccess {String}    list.voice                     声音文件URL
     * @apiSuccess {String}    list.image                     图片文件URL
     * @apiSuccess {Long}      list.createDate                发送时间
     * @apiSuccess {Long}      list.updateDate                修改时间
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list": [
     *              {
     *                  "id": 1,
     *                  "fromUserInfo": {
     *                      "userId":1,
     *                      "nickname":"洛天依",
     *                      "avater":"http://4.16.3.22:8888/voice/20160701voice.mp3"
     *                  },
     *                  "toUserInfo": {
     *                      "userId":1,
     *                      "nickname":"洛天依",
     *                      "avater":"http://4.16.3.22:8888/voice/20160701voice.mp3"
     *                  },
     *                  "content": "你好",
     *                  "voice": "http://4.16.3.22:8888/voice/20160701voice.mp3",
     *                  "image": "http://4.16.3.22:8888/image/20160701image.jpg",
     *                  "createDate": 1466501548562,
     *                  "updateDate": ""
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/letterList", method = RequestMethod.POST)
    public void letterList(HttpServletResponse response, Long userId, Integer pageNum, Integer pageSize) {
        Page<PrivateLetter> page = privateLetterApiService.letterPage(userId, pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "user", "toUser", "isAttention");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/message/sendLetter 5、发送私信
     * @apiVersion 0.0.1
     * @apiName message.sendLetter
     * @apiGroup message
     * @apiDescription 发送私信
     *
     * @apiParam   {Long}      userId                  发送用户ID        (必传)
     * @apiParam   {Long}      toUserId                接收用户ID        (必传)
     * @apiParam   {String}    content                 文本内容
     * @apiParam   {Stream}    voice                   声音url
     * @apiParam   {Stream}    image                   图片url
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/sendLetter", method = RequestMethod.POST)
    public void sendLetter(HttpServletResponse response, Long userId, Long toUserId, String content, MultipartRequest multipartRequest) {
        String result = "";
        try {
            privateLetterApiService.sendLetter(userId, toUserId, content, multipartRequest);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (SendMessageErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00022));
        }
        WebUtil.printApi(response, result);
    }
}