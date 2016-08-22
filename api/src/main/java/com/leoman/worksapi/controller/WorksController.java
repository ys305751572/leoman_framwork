package com.leoman.worksapi.controller;

import com.leoman.barrage.entity.Barrage;
import com.leoman.common.core.ErrorType;
import com.leoman.exception.CommentNotFindException;
import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.UserNotFindException;
import com.leoman.exception.WorkNotFindException;
import com.leoman.indexapi.service.WorkService;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.Result;
import com.leoman.utils.WebUtil;
import com.leoman.work.entity.vo.WorkComicVo;
import com.leoman.work.entity.vo.WorkCommentVo;
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
@RequestMapping(value = "/app/works")
public class WorksController {

    @Autowired
    private WorkService workService;

    /**
     * @api {post} /app/works/videoList 1、作品列表(视频)
     * @apiVersion 0.0.1
     * @apiName works.videoList
     * @apiGroup works
     * @apiDescription 作品列表(视频)
     *
     * @apiParam   {Integer} pageNum                 页码
     * @apiParam   {Integer} pageSize                每页显示条数
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                 视频ID
     * @apiSuccess {String}  list.name               视频名称
     * @apiSuccess {String}  list.cover              视频封面
     * @apiSuccess {Integer} list.series             集数
     * @apiSuccess {String}  list.linkUrl            源地址
     * @apiSuccess {Integer} list.playNum            播放量
     * @apiSuccess {Integer} list.barrageNum         弹幕数
     * @apiSuccess {Integer} list.isEnd              是否完结 0:未完结 1:已完结
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list": [
     *              {
     *                  "id" : 1,
     *                  "name" : "器灵视频",
     *                  "cover" : "http://www.baiDu.com",
     *                  "series" : 34,
     *                  "linkUrl" : "http://www.baiDu.com",
     *                  "playNum" : 5000,
     *                  "barrageNum" : 400,
     *                  "isEnd" : 0
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/videoList", method = RequestMethod.POST)
    public void videoList(HttpServletResponse response, Integer pageNum, Integer pageSize) {
        Page<WorkVideoVo> page = workService.videoPage(pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "isCollect", "creatorList", "surroundList");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/works/videoDetail 2、作品详情(视频)
     * @apiVersion 0.0.1
     * @apiName works.videoDetail
     * @apiGroup works
     * @apiDescription 作品详情(视频)
     *
     * @apiParam   {Long}    workId                                   视频ID（必传）
     * @apiParam   {Long}    userId                                   用户ID
     *
     * @apiSuccess {Object}  workVideo
     * @apiSuccess {Long}    workVideo.id                             视频ID
     * @apiSuccess {String}  workVideo.name                           视频名称
     * @apiSuccess {String}  workVideo.cover                          视频封面
     * @apiSuccess {Integer} workVideo.series                         集数
     * @apiSuccess {String}  workVideo.linkUrl                        源地址
     * @apiSuccess {Integer} workVideo.playNum                        播放量
     * @apiSuccess {Integer} workVideo.barrageNum                     弹幕数
     * @apiSuccess {Integer} workVideo.isEnd                          是否完结 0:未完结 1:已完结
     * @apiSuccess {Integer} workVideo.isCollect                      是否收藏 0:未收藏 1:已收藏
     * @apiSuccess {Object}  workVideo.creatorList                    主创列表
     * @apiSuccess {Long}    workVideo.creatorList.id                 主创id
     * @apiSuccess {String}  workVideo.creatorList.name               主创姓名
     * @apiSuccess {String}  workVideo.creatorList.avater             主创头像
     * @apiSuccess {Integer} workVideo.creatorList.praise             主创点赞数
     * @apiSuccess {Integer} workVideo.creatorList.coin               主创馒头数
     * @apiSuccess {Object}  workVideo.surroundList                   周边推荐列表
     * @apiSuccess {Long}    workVideo.surroundList.id                周边id
     * @apiSuccess {String}  workVideo.surroundList.name              周边名称
     * @apiSuccess {String}  workVideo.surroundList.description       周边描述
     * @apiSuccess {String}  workVideo.surroundList.linkUrl           周边链接地址
     * @apiSuccess {String}  workVideo.surroundList.cover             周边封面
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "workNovel": {
     *              "id" : 1,
     *              "name" : "器灵视频",
     *              "cover" : "http://www.baiDu.com",
     *              "series" : 34,
     *              "linkUrl" : "http://www.baiDu.com",
     *              "playNum" : 5000,
     *              "barrageNum" : 400,
     *              "isEnd" : 0,
     *              "isCollect" : 0,
     *              "creatorList": [
     *                  {
     *                      "id": 57,
     *                      "name": "范冰冰",
     *                      "avater": "http://4.16.3.22:8080/file/upload/images/2016/8/1470712108910.jpg"
     *                  }
     *              ]
     *          }
     *      }
     * }
     */
    @RequestMapping(value = "/videoDetail", method = RequestMethod.POST)
    public void videoDetail(HttpServletResponse response, Long workId, Long userId) {
        String result = "";

        try {
            WorkVideoVo workVideoVo = workService.findVideo(workId, userId);
            result = JsonUtil.obj2ApiJson(Result.successInfoApi(workVideoVo, "workVideo"), "work");
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (WorkNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00012));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/works/novelList 3、作品列表(小说)
     * @apiVersion 0.0.1
     * @apiName works.novelList
     * @apiGroup works
     * @apiDescription 作品列表(小说)
     *
     * @apiParam   {Integer} pageNum                 页码
     * @apiParam   {Integer} pageSize                每页显示条数
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                 小说ID
     * @apiSuccess {String}  list.name               小说名称
     * @apiSuccess {String}  list.cover              小说封面
     * @apiSuccess {String}  list.author             小说作者
     * @apiSuccess {Integer} list.series             章数
     * @apiSuccess {Integer} list.playNum            播放量
     * @apiSuccess {String}  list.description        描述
     * @apiSuccess {String}  list.updateTime         更新时间
     * @apiSuccess {Integer} list.isEnd              是否完结 0:未完结 1:已完结
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list": [
     *              {
     *                  "id" : 1,
     *                  "name" : "器灵小说",
     *                  "cover" : "http://www.baiDu.com",
     *                  "author" : "天蚕土豆",
     *                  "series" : 340,
     *                  "playNum" : 5000,
     *                  "description" : "各回各家根据规划",
     *                  "updateTime" : "1469505913970",
     *                  "isEnd" : 0
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/novelList", method = RequestMethod.POST)
    public void novelList(HttpServletResponse response, Integer pageNum, Integer pageSize) {
        Page<WorkNovelVo> page = workService.novelPage2(pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "isCollect", "detailList", "creatorList", "surroundList");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/works/novelDetail 4、作品详情(小说)
     * @apiVersion 0.0.1
     * @apiName works.novelDetail
     * @apiGroup works
     * @apiDescription 作品详情(小说)
     *
     * @apiParam   {Long}    workId                                     小说ID（必传）
     * @apiParam   {Long}    userId                                     用户ID
     *
     * @apiSuccess {Object}  workNovel
     * @apiSuccess {Long}    workNovel.id                               小说ID
     * @apiSuccess {String}  workNovel.name                             小说名称
     * @apiSuccess {String}  workNovel.cover                            小说封面
     * @apiSuccess {String}  workNovel.author                           小说作者
     * @apiSuccess {Integer} workNovel.series                           章数
     * @apiSuccess {Integer} workNovel.playNum                          播放量
     * @apiSuccess {String}  workNovel.description                      简介
     * @apiSuccess {String}  workNovel.updateTime                       更新时间
     * @apiSuccess {Integer} workNovel.isEnd                            是否完结 0:未完结 1:已完结
     * @apiSuccess {Integer} workNovel.isCollect                        是否收藏 0:未收藏 1:已收藏
     * @apiSuccess {Object}  workNovel.detailList                       详情列表
     * @apiSuccess {Long}    workNovel.detailList.id                    详情id
     * @apiSuccess {String}  workNovel.detailList.name                  详情名称
     * @apiSuccess {Integer} workNovel.detailList.series                详情集数
     * @apiSuccess {String}  workNovel.detailList.content               详情内容
     * @apiSuccess {Long}    workNovel.detailList.updateTime            更新时间
     * @apiSuccess {Object}  workNovel.creatorList                      主创列表
     * @apiSuccess {Long}    workNovel.creatorList.id                   主创id
     * @apiSuccess {String}  workNovel.creatorList.name                 主创姓名
     * @apiSuccess {String}  workNovel.creatorList.avater               主创头像
     * @apiSuccess {Integer} workNovel.creatorList.praise               主创点赞数
     * @apiSuccess {Integer} workNovel.creatorList.coin                 主创馒头数
     * @apiSuccess {Object}  workNovel.surroundList                     周边推荐列表
     * @apiSuccess {Long}    workNovel.surroundList.id                  周边id
     * @apiSuccess {String}  workNovel.surroundList.name                周边名称
     * @apiSuccess {String}  workNovel.surroundList.description         周边描述
     * @apiSuccess {String}  workNovel.surroundList.linkUrl             周边链接地址
     * @apiSuccess {String}  workNovel.surroundList.cover               周边封面
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "workNovel": {
     *              "id" : 1,
     *              "name" : "器灵小说",
     *              "cover" : "http://www.baiDu.com",
     *              "author" : "天蚕土豆",
     *              "series" : 340,
     *              "playNum" : 5000,
     *              "description" : "器灵小说",
     *              "updateTime" : "1469505913970",
     *              "isEnd" : 0,
     *              "isCollect" : 0,
     *              "detailList": [
     *                  {
     *                      "id": 12,
     *                      "name": "第一章",
     *                      "series": 1,
     *                      "content": "器灵小说第一章内容",
     *                      "updateTime": "1467354959152"
     *                  }
     *              ],
     *              "creatorList": [
     *                  {
     *                      "id": 33,
     *                      "name": "陈绮贞",
     *                      "avater": "http://4.16.3.22:8080/file/upload/2016/8/images/2016/8/1470712108910.jpg"
     *                  }
     *              ]
     *          }
     *      }
     * }
     */
    @RequestMapping(value = "/novelDetail", method = RequestMethod.POST)
    public void novelDetail(HttpServletResponse response, Long workId, Long userId) {
        String result = "";

        try {
            WorkNovelVo workNovelVo = workService.findNovel(workId, userId);
            result = JsonUtil.obj2ApiJson(Result.successInfoApi(workNovelVo, "workNovel"), "work");
            WebUtil.printApi(response, result);
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (WorkNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00012));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/works/comicList 5、作品列表(漫画)
     * @apiVersion 0.0.1
     * @apiName works.comicList
     * @apiGroup works
     * @apiDescription 作品列表(漫画)
     *
     * @apiParam   {Integer} pageNum                 页码
     * @apiParam   {Integer} pageSize                每页显示条数
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                 漫画ID
     * @apiSuccess {String}  list.name               漫画名称
     * @apiSuccess {String}  list.cover              漫画封面
     * @apiSuccess {String}  list.author             漫画作者
     * @apiSuccess {String}  list.description        漫画简介
     * @apiSuccess {Integer} list.series             章数
     * @apiSuccess {Integer} list.playNum            播放量
     * @apiSuccess {Integer} list.barrageNum         弹幕数
     * @apiSuccess {String}  list.updateTime         更新时间
     * @apiSuccess {Integer} list.isEnd              是否完结 0:未完结 1:已完结
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list": [
     *              {
     *                  "id" : 1,
     *                  "name" : "器灵漫画",
     *                  "cover" : "http://www.baiDu.com",
     *                  "author" : "吴邪",
     *                  "description" : "巴拉巴拉",
     *                  "series" : 34,
     *                  "playNum" : 5000,
     *                  "barrageNum" : 400,
     *                  "updateTime" : "1469505913970",
     *                  "isEnd" : 0
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/comicList", method = RequestMethod.POST)
    public void comicList(HttpServletResponse response, Integer pageNum, Integer pageSize) {
        Page<WorkComicVo> page = workService.comicPage(pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "isCollect", "detailList", "creatorList", "surroundList");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/works/comicDetail 6、作品详情(漫画)
     * @apiVersion 0.0.1
     * @apiName works.comicDetail
     * @apiGroup works
     * @apiDescription 作品详情(漫画)
     *
     * @apiParam   {Long}    workId                                    漫画ID（必传）
     * @apiParam   {Long}    userId                                    用户ID
     *
     * @apiSuccess {Object}  comicInfo
     * @apiSuccess {Long}    comicInfo.id                              漫画ID
     * @apiSuccess {String}  comicInfo.name                            漫画名称
     * @apiSuccess {String}  comicInfo.cover                           漫画封面
     * @apiSuccess {String}  comicInfo.author                          漫画作者
     * @apiSuccess {String}  comicInfo.description                     漫画简介
     * @apiSuccess {Integer} comicInfo.series                          章数
     * @apiSuccess {Integer} comicInfo.playNum                         播放量
     * @apiSuccess {Integer} comicInfo.barrageNum                      弹幕数
     * @apiSuccess {String}  comicInfo.updateTime                      更新时间
     * @apiSuccess {Integer} comicInfo.isEnd                           是否完结 0:未完结 1:已完结
     * @apiSuccess {Integer} comicInfo.isCollect                       是否收藏 0:未收藏 1:已收藏
     * @apiSuccess {Integer} comicInfo.detailList                      章节列表
     * @apiSuccess {Long}    comicInfo.detailList.id                   章节id
     * @apiSuccess {String}  comicInfo.detailList.name                 章节名称
     * @apiSuccess {Integer} comicInfo.detailList.series               章节所属集数
     * @apiSuccess {Integer} comicInfo.detailList.playNum              章节播放量
     * @apiSuccess {Integer} comicInfo.detailList.barrageNum           章节弹幕数
     * @apiSuccess {Object}  comicInfo.detailList.imageList            章节图片列表
     * @apiSuccess {Long}    comicInfo.detailList.imageList.id         章节图片id
     * @apiSuccess {String}  comicInfo.detailList.imageList.url        章节图片url
     * @apiSuccess {Object}  comicInfo.creatorList                     主创列表
     * @apiSuccess {Long}    comicInfo.creatorList.id                  主创id
     * @apiSuccess {String}  comicInfo.creatorList.name                主创姓名
     * @apiSuccess {String}  comicInfo.creatorList.avater              主创头像
     * @apiSuccess {Integer} comicInfo.creatorList.praise              主创点赞数
     * @apiSuccess {Integer} comicInfo.creatorList.coin                主创馒头数
     * @apiSuccess {Object}  comicInfo.surroundList                    周边推荐列表
     * @apiSuccess {Long}    comicInfo.surroundList.id                 周边id
     * @apiSuccess {String}  comicInfo.surroundList.name               周边名称
     * @apiSuccess {String}  comicInfo.surroundList.description        周边描述
     * @apiSuccess {String}  comicInfo.surroundList.linkUrl            周边链接地址
     * @apiSuccess {String}  comicInfo.surroundList.cover              周边封面
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "comicInfo": {
     *              "id" : 1,
     *              "name" : "器灵视频",
     *              "cover" : "http://www.baiDu.com",
     *              "author" : "张起灵",
     *              "description" : "漫画简介",
     *              "series" : 34,
     *              "playNum" : 5000,
     *              "barrageNum" : 400,
     *              "updateTime" : "1469505913970",
     *              "isEnd" : 0,
     *              "isCollect" : 0,
     *              "detailList": [
     *                  {
     *                      "id": 1,
     *                      "name": "大闹天宫",
     *                      "series": 1,
     *                      "imageList": [
     *                          {
     *                              "id": 2,
     *                              "url": "http://4.16.3.22:8080/files/upload/images/2016/7/1469599145480.jpg"
     *                          }
     *                      ]
     *                  }
     *              ],
     *              "creatorList": [
     *                  {
     *                      "id": 29,
     *                      "name": "洛天依",
     *                      "avater": "http://4.16.3.22:8080/files/upload/images/2016/8/1470974359936.jpg"
     *                  }
     *              ]
     *          }
     *      }
     * }
     */
    @RequestMapping(value = "/comicDetail", method = RequestMethod.POST)
    public void comicDetail(HttpServletResponse response, Long workId, Long userId) {
        String result = "";

        try {
            WorkComicVo workComicVo = workService.findComic(workId, userId);
            result = JsonUtil.obj2ApiJson(Result.successInfoApi(workComicVo, "comicInfo"), "workComic", "createDate", "updateDate", "work");
            WebUtil.printApi(response, result);
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (WorkNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00012));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/works/commentList 7、评论列表
     * @apiVersion 0.0.1
     * @apiName works.commentList
     * @apiGroup works
     * @apiDescription 评论列表
     *
     * @apiParam {Integer}   type         类别 4:视频 5:小说 6:漫画 (必填)
     * @apiParam {Long}      workId       作品ID                   (必填)
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                       评论ID
     * @apiSuccess {Long}    list.userId                   评论人ID
     * @apiSuccess {String}  list.nickname                 评论人昵称
     * @apiSuccess {String}  list.avater                   评论人头像
     * @apiSuccess {Integer} list.level                    评论人等级
     * @apiSuccess {String}  list.content                  评论内容
     * @apiSuccess {Integer} list.praise                   点赞数
     * @apiSuccess {Object}  list.commentImageList         评论图片列表
     * @apiSuccess {Long}    list.commentImageList.id      评论图片ID
     * @apiSuccess {String}  list.commentImageList.url     评论图片URL
     * @apiSuccess {Long}    list.createDate               评论时间
     * @apiSuccess {String}  list.replyName                回复人名称
     * @apiSuccess {String}  list.replyContent             回复内容
     * @apiSuccess {Object}  list.replyImageList           回复图片列表
     * @apiSuccess {Long}    list.replyImageList.id        回复图片ID
     * @apiSuccess {String}  list.replyImageList.url       回复图片URL
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
     *                  "userId": 1,
     *                  "nickname": "洛天依",
     *                  "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *                  "content": "不错不错",
     *                  "praise": 0,
     *                  "commentImageList": [
     *                      {
     *                          "id": 23,
     *                          "url": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q"
     *                      }
     *                  ]
     *                  "createDate": "1469599621685",
     *                  "replyName": "梁静茹",
     *                  "replyContent": "是啊",
     *                  "replyImageList": [
     *                      {
     *                          "id": 3,
     *                          "url": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q"
     *                      }
     *                  ]
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/commentList", method = RequestMethod.POST)
    public void commentList(HttpServletResponse response, Long workId, Integer type, Integer pageNum, Integer pageSize) {
        Page<WorkCommentVo> page = workService.iPageByParams(workId, type, pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successListApi(dataMap));
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/works/insertComment 8、发表评论
     * @apiVersion 0.0.1
     * @apiName works.insertComment
     * @apiGroup works
     * @apiDescription 发表评论
     *
     * @apiParam {Integer}    type         类别 4:视频 5:小说 6:漫画               (必填)
     * @apiParam {Long}       workId       作品id                                 (必填)
     * @apiParam {Long}       userId       评论人id                               (必填)
     * @apiParam {String}     content      评论内容                               (必填)
     * @apiParam {Stream}     images       图片数组
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/insertComment", method = RequestMethod.POST)
    public void insertComment(HttpServletResponse response, Integer type, Long workId, Long userId, String content, MultipartRequest multipartRequest) {
        String result = "";
        try {
            workService.saveWorkComment(multipartRequest, workId, type, userId, content);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (WorkNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00012));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/works/insertReply 9、回复评论
     * @apiVersion 0.0.1
     * @apiName works.insertReply
     * @apiGroup works
     * @apiDescription 回复评论
     *
     * @apiParam {Long}       workCommentId   评论id                                 (必填)
     * @apiParam {Long}       userId          评论人id                               (必填)
     * @apiParam {String}     content         评论内容                               (必填)
     * @apiParam {Stream}     images          图片数组                               (必填)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/insertReply", method = RequestMethod.POST)
    public void insertReply(HttpServletResponse response, Long workCommentId, Long userId, String content, MultipartRequest multipartRequest) {
        String result = "";
        try {
            workService.saveWorkReply(multipartRequest, workCommentId, userId, content);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (CommentNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00013));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/works/barrageList 10、弹幕
     * @apiVersion 0.0.1
     * @apiName works.barrageList
     * @apiGroup works
     * @apiDescription 弹幕(视频)
     *
     * @apiParam {Integer}   type         类别 0:视频 1:漫画        (必填)
     * @apiParam {Long}      workId       作品ID                   (必填)
     *
     * @apiSuccess {Object}  list
     *
     * @apiSuccess {Object}  list.praise                   点赞数
     * @apiSuccess {Object}  list.content                  弹幕内容
     * @apiSuccess {Object}  list.time                     弹幕所在时间
     * @apiSuccess {Object}  list.id                       弹幕ID
     * @apiSuccess {Object}  list.createTime               弹幕发送内容
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {
     *          "list": [
     *              {
     *                  "praise": 58,
     *                  "content": "呱唧呱唧",
     *                  "time": "1分30秒",
     *                  "id": 1,
     *                  "createTime": "1469505913970"
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/barrageList", method = RequestMethod.POST)
    public void barrageList(HttpServletResponse response, Long workId, Integer type) {
        String result = "";

        try {
            List<Barrage> list = workService.iFindBarrageList(workId, type);
            result = JsonUtil.obj2ApiJson(Result.successListApi(list), "userLogin", "userInfo", "workVideo", "workComic", "workId", "type");
            WebUtil.printApi(response, result);
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (WorkNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00012));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/works/insertBarrage 11、发送弹幕
     * @apiVersion 0.0.1
     * @apiName works.insertBarrage
     * @apiGroup works
     * @apiDescription 发送弹幕
     *
     * @apiParam {Integer}    type         类别 0:视频 1:漫画        (必填)
     * @apiParam {Long}       workId       作品ID                   (必填)
     * @apiParam {Long}       userId       用户ID                   (必填)
     * @apiParam {Integer}    time         弹幕时间，单位：秒         (必填)
     * @apiParam {String}     content      弹幕内容                  (必填)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/insertBarrage", method = RequestMethod.POST)
    public void insertBarrage(HttpServletResponse response, Integer type, Long workId, Long userId, Integer time, String content) {
        String result = "";
        try {
            workService.insertBarrage(type, workId, userId, time, content);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, result);
    }
}
