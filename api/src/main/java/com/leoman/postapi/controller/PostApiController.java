package com.leoman.postapi.controller;

import com.leoman.common.core.ErrorType;
import com.leoman.exception.*;
import com.leoman.post.entity.PostBase;
import com.leoman.post.entity.vo.PostCommentVo;
import com.leoman.postapi.service.PostService;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.Result;
import com.leoman.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/12.
 */
@RestController
@RequestMapping(value = "/app/post")
public class PostApiController {

    @Autowired
    private PostService postService;

    /**
     * @api {post} /app/post/insert 1、发表帖子
     * @apiVersion 0.0.1
     * @apiName post.insert
     * @apiGroup post
     * @apiDescription 帖子列表
     *
     * @apiParam {Long}      userId         用户ID
     * @apiParam {Long}      category       分类Id
     * @apiParam {String}    content        帖子内容
     * @apiParam {Stream}    file           图片
     * @apiParam {Stream}    voice          语音
     * @apiParam {Integer}   voiceLength    语音长度（秒数）
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insertPost(HttpServletResponse response, MultipartRequest multipartRequest, Long userId, Long category, String content, Integer voiceLength) {
        String result = "";
        try {
            postService.saveUserPost(multipartRequest, userId, category, content, voiceLength);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0002));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/post/list 2、帖子列表
     * @apiVersion 0.0.1
     * @apiName post.list
     * @apiGroup post
     * @apiDescription 帖子列表
     *
     * @apiParam {String}    key          搜索关键词
     * @apiParam {Long}      userId       用户ID
     * @apiParam {Long}      category     类别Id 为空 则为默认分类 "0：热门帖子"
     * @apiParam {Integer}   isMe         isMe=1时，查询我发布的帖子
     * @apiParam {Integer}   pageNum      页码                                  (必传)
     * @apiParam {Integer}   pageSize     每页显示条数                           (必传)
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                        帖子ID
     * @apiSuccess {Integer} list.type                      帖子类型 0:普通帖 1:直播帖 2:投票帖 3:用户帖
     * @apiSuccess {String}  list.content                   帖子内容
     * @apiSuccess {Long}    list.createDate                发布时间
     * @apiSuccess {Integer} list.status                    状态（0：正常，1=锁帖）
     * @apiSuccess {Integer} list.comment                   评论数
     * @apiSuccess {Integer} list.praise                    点赞数
     *
     * @apiSuccess {Object}  list.user                      发帖人
     * @apiSuccess {Long}    list.user.id                   发帖人ID
     * @apiSuccess {String}  list.user.nickname             发帖人昵称
     * @apiSuccess {Integer} list.user.level                发帖人等级
     * @apiSuccess {String}  list.user.avater               发帖人头像
     * @apiSuccess {Integer} list.user.isAttention          是否关注该发帖人（0：已关注，1：未关注）
     * @apiSuccess {Integer} list.user.isCreator            是否主创（0：否，1：是）
     *
     * @apiSuccess {Object}  list.imageList                 图片列表
     * @apiSuccess {Long}    list.imageList.id              图片ID
     * @apiSuccess {String}  list.imageList.url             图片链接
     *
     * @apiSuccess {Object}  list.userList                  点赞用户列表
     * @apiSuccess {Long}    list.userList.id               点赞用户id
     * @apiSuccess {String}  list.userList.nickname         点赞用户昵称
     * @apiSuccess {String}  list.userList.avater           点赞用户头像
     *
     * @apiSuccess {Object}  list.commentList               最新三条评论列表
     * @apiSuccess {Long}    list.commentList.userId        评论人ID
     * @apiSuccess {String}  list.commentList.nickname      评论人昵称
     * @apiSuccess {String}  list.commentList.avater        评论人头像
     * @apiSuccess {String}  list.commentList.content       评论内容
     * @apiSuccess {Long}    list.commentList.createDate    评论时间
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
     *                  "id": 2,
     *                  "type": 2,
     *                  "content": "帖子分类是1",
     *                  "createDate": "1466501548562",
     *                  "user": {
     *                      "id": 1,
     *                      "nickname": "洛天依",
     *                      "level": 0,
     *                      "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *                      "isAttention": 1
     *                  },
     *                  "imageList": [
     *                      {
     *                          "id": 2,
     *                          "url": "http://4.16.3.22:8080/images/2016/6/1466563262995.jpg"
     *                      }
     *                  ],
     *                  "userList": [
     *                      {
     *                          "id": 3,
     *                          "nickname": "王小二",
     *                          "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q"
     *                      }
     *                  ],
     *                  "commentList": [
     *                      {
     *                          "userId": 1,
     *                          "nickname": "白小飞",
     *                          "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *                          "content": "不错，赞一个~~~",
     *                          "createDate": "2016-06-22 09:21:33"
     *                      }
     *                  ]
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void list(HttpServletResponse response, String key, Long userId, Long category, Integer isMe, Integer pageNum, Integer pageSize) {
        String result = "";
        try {
            Page<PostBase> page = postService.iPage(key, userId, category, isMe, pageNum, pageSize);
            Map<String, Object> dataMap = JsonUtil.fitting(page);
            result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "replyList", "category", "postId", "userInfo");
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0002));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/post/detail 3、帖子详情
     * @apiVersion 0.0.1
     * @apiName post.detail
     * @apiGroup post
     * @apiDescription 帖子详情
     *
     * @apiParam {Long}      postId     帖子ID                                     (必传)
     * @apiParam {Integer}   type       帖子类型 0:普通帖 1:直播帖 2:投票帖 3:用户帖  (必传)
     * @apiParam {Long}      userId     用户ID
     *
     * @apiSuccess {Object}  post
     * @apiSuccess {Long}    post.id                        帖子ID
     * @apiSuccess {Integer} post.type                      帖子类型 0:普通帖 1:直播帖 2:投票帖 3:用户帖
     * @apiSuccess {String}  post.content                   帖子内容
     * @apiSuccess {Long}    post.createDate                发布时间
     * @apiSuccess {Integer} post.status                    状态（0：正常，1=锁帖）
     * @apiSuccess {Integer} post.comment                   评论数
     * @apiSuccess {Integer} post.praise                    点赞数
     *
     * @apiSuccess {Object}  post.user                      发帖人
     * @apiSuccess {Long}    post.user.id                   发帖人ID
     * @apiSuccess {String}  post.user.nickname             发帖人昵称
     * @apiSuccess {Integer} post.user.level                发帖人等级
     * @apiSuccess {String}  post.user.avater               发帖人头像
     * @apiSuccess {Integer} post.user.isAttention          是否关注该发帖人（0：已关注，1：未关注）
     * @apiSuccess {Integer} list.user.isCreator            是否主创（0：否，1：是）
     *
     * @apiSuccess {Object}  post.imageList                 图片列表
     * @apiSuccess {Long}    post.imageList.id              图片ID
     * @apiSuccess {String}  post.imageList.url             图片链接
     *
     * @apiSuccess {Object}  post.userList                  点赞用户列表
     * @apiSuccess {Long}    post.userList.id               点赞用户id
     * @apiSuccess {String}  post.userList.nickname         点赞用户昵称
     * @apiSuccess {String}  post.userList.avater           点赞用户头像
     *
     * @apiSuccess {Object}  post.commentList               最新三条评论列表
     * @apiSuccess {Long}    post.commentList.userId        评论人ID
     * @apiSuccess {String}  post.commentList.nickname      评论人昵称
     * @apiSuccess {String}  post.commentList.avater        评论人头像
     * @apiSuccess {String}  post.commentList.content       评论内容
     * @apiSuccess {Long}    post.commentList.createDate    评论时间
     *
     * @apiSuccess {Object}  post.tpPostSubList             投票列表（仅投票帖适用）
     * @apiSuccess {String}  post.tpPostSubList.name        投票候选人名称
     * @apiSuccess {String}  post.tpPostSubList.cover       投票候选人头像
     * @apiSuccess {Integer} post.tpPostSubList.count       投票候选人票数
     * @apiSuccess {Integer} post.tpPostSubList.status      是否投票，0：已投票  1：未投票
     *
     * @apiSuccess {Object}  post.zbSubList                 直播列表（仅直播帖适用）
     * @apiSuccess {String}  post.zbSubList.content         直播内容
     * @apiSuccess {Object}  post.zbSubList.imageList       直播图片列表
     * @apiSuccess {Long}    post.zbSubList.imageList.id    直播图片ID
     * @apiSuccess {String}  post.zbSubList.imageList.url   直播图片路径
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "postInfo": [
     *              {
     *                  "id": 2,
     *                  "type": 2,
     *                  "content": "帖子分类是1",
     *                  "createDate": "1466501562661",
     *                  "user": {
     *                      "id": 1,
     *                      "nickname": "洛天依",
     *                      "level": 0,
     *                      "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *                      "isAttention": 1
     *                  },
     *                  "imageList": [
     *                      {
     *                          "id": 2,
     *                          "url": "http://4.16.3.22:8080/images/2016/6/1466563262995.jpg"
     *                      }
     *                  ],
     *                  "userList": [
     *                      {
     *                          "id": 3,
     *                          "nickname": "王小二",
     *                          "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q"
     *                      }
     *                  ],
     *                  "commentList": [
     *                      {
     *                          "userId": 1,
     *                          "nickname": "白小飞",
     *                          "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *                          "content": "不错，赞一个~~~",
     *                          "createDate": "1466501562661",
     *                          "replyList": [
     *                              {
     *                                  "userId": 1,
     *                                  "nickname": "白小飞",
     *                                  "avater": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *                                  "content": "不错，赞一个~~~",
     *                                  "createDate": "1466501562661"
     *                              }
     *                          ]
     *                      }
     *                  ],
     *                  "tpPostSubList": [
     *                      {
     *                          "id": 1,
     *                          "name": "白小飞",
     *                          "cover": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *                          "count": 1263,
     *                          "status": 0
     *                      },
     *                      {
     *                          "id": 2,
     *                          "name": "马化腾",
     *                          "cover": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q",
     *                          "count": 1254,
     *                          "status": 1
     *                      }
     *                  ],
     *                  "zbSubList": [
     *                      {
     *                          "content": "巴拉巴拉巴拉巴拉巴拉巴拉",
     *                          "imageList": [
     *                              {
     *                                  "id": 1,
     *                                  "url": "http://7xsyp2.com2.z0.glb.qiniucdn.com/FpBaMotUC1r5Eaptl1q_QVjHz22q"
     *                              }
     *                          ]
     *                      }
     *                  ]
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public void detail(HttpServletResponse response, Long postId, Integer type, Long userId) {
        String result = "";
        try {
            PostBase postBase = postService.findOneById(postId, type, userId);
            result = JsonUtil.obj2ApiJson(Result.successInfoApi(postBase, "postInfo"), "postId", "category", "userInfo");
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (PostNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00011));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/post/comment/list 5、评论列表
     * @apiVersion 0.0.1
     * @apiName post.comment.list
     * @apiGroup post
     * @apiDescription 评论列表
     *
     * @apiParam {Integer}   type        类别 0:普通贴 1:直播贴 2:投票贴 3:用户帖  (必填)
     * @apiParam {String}    postId      帖子id                                 (必填)
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                       评论ID
     * @apiSuccess {Long}    list.userId                   评论人ID
     * @apiSuccess {String}  list.nickname                 评论人昵称
     * @apiSuccess {String}  list.avater                   评论人头像
     * @apiSuccess {String}  list.content                  评论内容
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
     *                  "content": "巴拉巴拉小魔杖",
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
    @RequestMapping(value = "/comment/list", method = RequestMethod.POST)
    public void listComment(HttpServletResponse response, Integer type, Long postId) {
        String result = "";
        try {
            List<PostCommentVo> list = postService.findCommentList(type, postId);
            result = JsonUtil.obj2ApiJson(Result.successListApi(list));
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0002));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/post/insertComment 6、发表评论
     * @apiVersion 0.0.1
     * @apiName post.insertComment
     * @apiGroup post
     * @apiDescription 发表评论
     *
     * @apiParam {Integer}    type         类别 0:普通贴 1:直播贴 2:投票贴 3:用户帖  (必填)
     * @apiParam {Integer}    postId       帖子id                                 (必填)
     * @apiParam {Integer}    userId       评论人id                               (必填)
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
    public void insertComment(HttpServletResponse response, Integer type, Long postId, Long userId, String content, MultipartRequest multipartRequest) {
        String result = "";
        try {
            postService.savePostComment(multipartRequest, type, postId, userId, content);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/post/insertReply 9、回复评论
     * @apiVersion 0.0.1
     * @apiName post.insertReply
     * @apiGroup post
     * @apiDescription 回复评论
     *
     * @apiParam {Long}       postCommentId   评论id                                 (必填)
     * @apiParam {Long}       userId          评论人id                               (必填)
     * @apiParam {String}     content         评论内容                               (必填)
     * @apiParam {Stream}     images          图片数组                             (必填)
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
    public void insertReply(HttpServletResponse response, Long postCommentId, Long userId, String content, MultipartRequest multipartRequest) {
        String result = "";
        try {
            postService.savePostReply(multipartRequest, postCommentId, userId, content);
            result = JsonUtil.obj2ApiJson(Result.successApi());
            System.out.println("回复评论成功！！！");
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
     * @api {post} /app/post/reward 10、打赏主创
     * @apiVersion 0.0.1
     * @apiName post.reward
     * @apiGroup post
     * @apiDescription 打赏主创
     *
     * @apiParam {Long}       creatorId       主创ID                               (必填)
     * @apiParam {Long}       userId          用户ID                               (必填)
     * @apiParam {Integer}    count           馒头数                               (必填)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/reward", method = RequestMethod.POST)
    public void reward(HttpServletResponse response, Long creatorId, Long userId, Integer count) {
        String result = "";
        try {
            postService.reward(creatorId, userId, count);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (CreatorNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00015));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (BreadShortException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00016));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/post/vote 11、投票帖投票
     * @apiVersion 0.0.1
     * @apiName post.vote
     * @apiGroup post
     * @apiDescription 投票帖投票
     *
     * @apiParam {Long}       userId          用户ID                               (必填)
     * @apiParam {Long}       tpPostSubId     投票项ID                             (必填)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public void vote(HttpServletResponse response, Long userId, Long tpPostSubId) {
        String result = "";
        try {
            postService.vote(userId, tpPostSubId);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (VoteExistException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00023));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/user/postListPlus 12、我回复的（赞过的）帖子列表
     * @apiVersion 0.0.1
     * @apiName user.postListPlus
     * @apiGroup user
     * @apiDescription 我回复的（赞过的）帖子列表
     *
     * @apiParam {Long}       userId                用户ID                          (必传)
     * @apiParam {Integer}    type                  类型 1:回复过的 2:赞过的         (必传)
     *
     * @apiSuccess {Object}   list
     * @apiSuccess {Long}     list.id               帖子ID
     * @apiSuccess {Long}     list.type             帖子类型  0:普通帖 1:直播帖 2:投票帖 3:用户帖
     * @apiSuccess {String}   list.content          帖子内容
     * @apiSuccess {Long}     list.createDate       时间
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list":[
     *              "id": 1,
     *              "type": 2,
     *              "content": 巴拉巴拉,
     *              "createDate": 1466501562661
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/postListPlus", method = RequestMethod.POST)
    public void postListPlus(HttpServletResponse response, Long userId, Integer type) {
        String result = "";
        try {
            result = JsonUtil.obj2ApiJson(Result.successListApi(postService.findListByUserId(userId, type)));
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/post/postTypeList 13、帖子分类列表
     * @apiVersion 0.0.1
     * @apiName post.postTypeList
     * @apiGroup post
     * @apiDescription 帖子分类列表
     *
     * @apiSuccess {Object}   list
     * @apiSuccess {Long}     list.id               分类ID
     * @apiSuccess {String}   list.content          分类名称
     * @apiSuccess {Long}     list.createDate       时间
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list":[
     *              "id": 1,
     *              "content": 巴拉巴拉,
     *              "createDate": 1466501562661
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/postTypeList", method = RequestMethod.POST)
    public void postTypeList(HttpServletResponse response) {
        String result = JsonUtil.obj2ApiJson(Result.successListApi(postService.postTypeList()), "singer", "count", "url", "updateDate", "type");
        WebUtil.printApi(response, result);
    }
}
