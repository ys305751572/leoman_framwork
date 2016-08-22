package com.leoman.reourceapi.controller;

import com.leoman.common.core.ErrorType;
import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.ResourceNotFindException;
import com.leoman.reourceapi.service.ResourceApiService;
import com.leoman.resources.entity.GameResource;
import com.leoman.resources.entity.MusicResource;
import com.leoman.resources.entity.StillResource;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.Result;
import com.leoman.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/12.
 */
@Controller
@RequestMapping(value = "/app/resource")
public class ResourceController {

    @Autowired
    private ResourceApiService resourceApiService;

    /**
     * @api {post} /app/resource/listJz 1、剧照列表
     * @apiVersion 0.0.1
     * @apiName resource.listJz
     * @apiGroup resource
     * @apiDescription 剧照列表
     *
     * @apiParam {Integer}     pageNum               页码
     * @apiParam {Integer}     pageSize              每页显示条数
     *
     * @apiSuccess {Object}    list
     * @apiSuccess {Long}      list.id                        id
     * @apiSuccess {String}    list.name                      名称
     * @apiSuccess {Object}    list.imageList                 图片列表
     * @apiSuccess {Long}      list.imageList.id              图片id
     * @apiSuccess {String}    list.imageList.url             图片url
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list":[
     *              {
     *                  "id": 14,
     *                  "name": "器灵剧照",
     *                  "imageList":[
     *                      {
     *                          "id": 1,
     *                          "url": "http://www.qq.com/images/2016/6/1466991484108.jpg"
     *                      },
     *                      {
     *                          "id": 2,
     *                          "url": "http://www.qq.com/images/2016/6/1466991484333.jpg"
     *                      }
     *                  ]
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/listJz", method = RequestMethod.POST)
    public void listJz(HttpServletResponse response, Integer pageNum, Integer pageSize) {
        Page<StillResource> page = resourceApiService.iPageStillList(pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "category", "num", "imageUrl", "createDate", "updateDate");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/resource/listMusic 2、音乐列表
     * @apiVersion 0.0.1
     * @apiName resource.listMusic
     * @apiGroup resource
     * @apiDescription 音乐列表
     *
     * @apiParam {Integer}     pageNum                 页码
     * @apiParam {Integer}     pageSize                每页显示条数
     *
     * @apiSuccess {Object}   list
     * @apiSuccess {Long}     list.id                   id
     * @apiSuccess {String}   list.name                 名称
     * @apiSuccess {String}   list.size                 歌曲数量
     * @apiSuccess {String}   list.url                  封面地址
     * @apiSuccess {Object}   list.musicList            音乐
     * @apiSuccess {Object}   list.musicList.id         音乐id
     * @apiSuccess {Object}   list.musicList.name       音乐名称
     * @apiSuccess {String}   list.musicList.singerName 歌手名称
     * @apiSuccess {String}   list.musicList.playNum    播放数
     * @apiSuccess {Object}   list.musicList.url        音乐链接
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list" : [
     *              {
     *                  "id" : 1,
     *                  "name" : "经典老歌",
     *                  "size" : "1",
     *                  "url" : "http://4.16.3.22:8080/file/images/2016/8/156146513sdf.jpg",
     *                  "musicList" : [
     *                      {
     *                          "id" : "1",
     *                          "name" : "一次就好",
     *                          "singerName" : "范玮琪",
     *                          "playNum" : 15,
     *                          "url" : "http;//localhost:8080"
     *                      }
     *                  ]
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/listMusic", method = RequestMethod.POST)
    public void listMusic(HttpServletResponse response, Integer pageNum, Integer pageSize) {
        Page<MusicResource> page = resourceApiService.iPageMusicList(pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "category", "imageUrl", "singer", "num", "createDate", "updateDate");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/resource/listGame 3、游戏列表
     * @apiVersion 0.0.1
     * @apiName resource.listGame
     * @apiGroup resource
     * @apiDescription 游戏列表
     *
     * @apiParam {Integer}     pageNum               页码
     * @apiParam {Integer}     pageSize              每页显示条数
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                 id
     * @apiSuccess {String}  list.name               名称
     * @apiSuccess {String}  list.url                地址
     * @apiSuccess {String}  list.linkUrl            下载链接
     * @apiSuccess {String}  list.version            版本
     * @apiSuccess {String}  list.size               大小
     * @apiSuccess {Integer} list.downCount          下载量
     * @apiSuccess {String}  list.description        简介
     * @apiSuccess {String}  list.createDate         创建时间
     * @apiSuccess {String}  list.updateDate         更新时间
     *
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list":[
     *              {
     *                  "id": 1,
     *                  "name": "保卫萝卜",
     *                  "url": "images/2016/6/1467167086912.jpg",
     *                  "linkUrl": "发给你个",
     *                  "version": "2.3.5.3",
     *                  "size": 45,
     *                  "downCount": 0,
     *                  "description": "房管局和高科技，",
     *                  "createDate": 1467167089060,
     *                  "updateDate": 1467167089060
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/listGame", method = RequestMethod.POST)
    public void listGame(HttpServletResponse response, Integer pageNum, Integer pageSize) {
        Page<GameResource> page = resourceApiService.iPageGameList(pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap));
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/resource/downResource 4、下载资源（播放音乐）
     * @apiVersion 0.0.1
     * @apiName resource.downResource
     * @apiGroup resource
     * @apiDescription 下载资源（播放音乐）
     *
     * @apiParam {Long}        id               资源id
     * @apiParam {Integer}     type             类型：1：剧照，2：音乐，3=游戏
     *
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/downResource", method = RequestMethod.POST)
    public void downResource(HttpServletResponse response, Long id, Integer type) {
        String result = "";
        try {
            if (resourceApiService.downResource(id, type)) {
                result = JsonUtil.obj2ApiJson(Result.successApi());
            } else {
                result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0002));
            }
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (ResourceNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00027));
        }

        WebUtil.printApi(response, result);
    }
}
