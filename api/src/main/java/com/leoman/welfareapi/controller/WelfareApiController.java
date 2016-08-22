package com.leoman.welfareapi.controller;

import com.leoman.common.core.ErrorType;
import com.leoman.entity.Constant;
import com.leoman.exception.*;
import com.leoman.giftrecord.entity.GiftExchangeRecord;
import com.leoman.systemsetting.entity.Prize;
import com.leoman.user.entity.UserIntegral;
import com.leoman.userapi.service.UserIntegralService;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.Result;
import com.leoman.utils.WebUtil;
import com.leoman.welfare.entity.UserLottery;
import com.leoman.welfare.entity.Welfare;
import com.leoman.welfareapi.service.WelfareApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/12.
 */
@Controller
@RequestMapping(value = "/app/welfare")
public class WelfareApiController {

    @Autowired
    private WelfareApiService welfareApiService;

    @Autowired
    private UserIntegralService userIntegralService;

    /**
     * @api {post} /app/welfare/welfareList 1、福利列表
     * @apiVersion 0.0.1
     * @apiName welfare.welfareList
     * @apiGroup welfare
     *
     * @apiDescription 福利列表
     *
     * @apiParam {Integer}     pageNum               页码
     * @apiParam {Integer}     pageSize              每页显示条数
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                 id
     * @apiSuccess {String}  list.title              标题
     * @apiSuccess {String}  list.subTitle           副标题
     * @apiSuccess {Long}    list.type               类型 0:铃声 1:经验值 2:实物 3:表情包 4:商城购买 5:游戏兑换码
     * @apiSuccess {String}  list.cover              封面路径
     * @apiSuccess {String}  list.coin               消耗馒头数
     * @apiSuccess {String}  list.url                链接地址
     * @apiSuccess {String}  list.exper              经验数
     * @apiSuccess {String}  list.description        描述
     * @apiSuccess {String}  list.num                兑换次数
     * @apiSuccess {String}  list.createDate         创建时间
     * @apiSuccess {String}  list.updateDate         修改时间
     *
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
     *                  "title" : "小熊抱枕",
     *                  "subTitle" : "抱枕",
     *                  "type" : 2,
     *                  "cover" : "http://4.16.3.22:8080/images/head.jpg",
     *                  "coin" : 299,
     *                  "url" : "http://4.16.3.22:8080/images/head.jpg"
     *                  "exper" : "10",
     *                  "description" : "抱枕",
     *                  "num" : 1,
     *                  "createDate" : 1467187179076,
     *                  "updateDate" : 1467187173567
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/welfareList", method = RequestMethod.POST)
    public void welfareList(HttpServletResponse response, Integer pageNum, Integer pageSize) {
        Page<Welfare> page = welfareApiService.iPage(pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap));
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/welfare/exchange 2、兑换实物
     * @apiVersion 0.0.1
     * @apiName welfare.exchange
     * @apiGroup welfare
     *
     * @apiDescription 兑换实物
     *
     * @apiParam {Long}      userId             用户ID                    (必填)
     * @apiParam {Long}      welfareId          福利ID                    (必填)
     * @apiParam {String}    nickname           联系人                    (必填)
     * @apiParam {String}    mobile             联系方式                  (必填)
     * @apiParam {String}    address            收货地址                  (必填)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {}
     * }
     */
    @RequestMapping(value = "/exchange", method = RequestMethod.POST)
    public void exchange(HttpServletResponse response, Long userId, Long welfareId, String nickname, String mobile, String address) {
        String result = "";
        try {
            welfareApiService.exchangeWelfare(userId, welfareId, nickname, mobile, address);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (WelfareNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00021));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (BreadShortException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00016));
        }
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/welfare/exchangeList 3、兑换记录列表
     * @apiVersion 0.0.1
     * @apiName welfare.exchangeList
     * @apiGroup welfare
     *
     * @apiDescription 兑换记录列表
     *
     * @apiParam {Long}      userId                  用户ID                    (必填)
     * @apiParam {Integer}   pageNum                 页码
     * @apiParam {Integer}   pageSize                每页显示条数
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id                 id
     * @apiSuccess {String}  list.nickname           用户昵称
     * @apiSuccess {String}  list.mobile             联系电话
     * @apiSuccess {Long}    list.giftName           实物名称
     * @apiSuccess {String}  list.exchangeCoin       消耗馒头数
     * @apiSuccess {String}  list.receiverName       收货人姓名
     * @apiSuccess {String}  list.receiverMobile     收货人电话
     * @apiSuccess {String}  list.receiverAddress    收货人地址
     * @apiSuccess {String}  list.status             状态 0:待邮寄 1:待签收 2:已签收
     * @apiSuccess {String}  list.courierName        快递公司
     * @apiSuccess {String}  list.courierSn          快递单号
     * @apiSuccess {String}  list.createDate         创建时间
     * @apiSuccess {String}  list.updateDate         修改时间
     *
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{
     *          "list": [
     *              {
     *                  "nickname": "洛天依",
     *                  "mobile": "123456",
     *                  "giftName": "东风股份和",
     *                  "exchangeCoin": 12,
     *                  "receiverName": "涂三胜",
     *                  "receiverMobile": "15878554125",
     *                  "receiverAddress": "湖北省武汉市南湖大道政院小区",
     *                  "status": 0,
     *                  "courierName": "",
     *                  "courierSn": "",
     *                  "id": 1,
     *                  "createDate": 1467291444585,
     *                  "updateDate": ""
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/exchangeList", method = RequestMethod.POST)
    public void exchangeList(HttpServletResponse response, Long userId, Integer pageNum, Integer pageSize) {
        Page<GiftExchangeRecord> page = welfareApiService.iPageExchange(userId, pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "userInfo");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/welfare/probabilityList 4、抽奖几率列表
     * @apiVersion 0.0.1
     * @apiName welfare.probabilityList
     * @apiGroup welfare
     *
     * @apiDescription 兑换记录列表
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id               id
     * @apiSuccess {String}  list.name             名称
     * @apiSuccess {String}  list.coin             获得馒头数
     * @apiSuccess {Long}    list.pro              几率
     *
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
     *                  "name": "加油",
     *                  "coin": 0,
     *                  "pro": 20
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/probabilityList", method = RequestMethod.POST)
    public void probabilityList(HttpServletResponse response) {
        List<Prize> list = welfareApiService.probabilityList();
        String result = JsonUtil.obj2ApiJson(Result.successListApi(list), "createDate", "updateDate");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/welfare/lottery 5、抽奖
     * @apiVersion 0.0.1
     * @apiName welfare.lottery
     * @apiGroup welfare
     *
     * @apiDescription 抽奖后扣除馒头数
     *
     * @apiParam {Long}      userId             用户ID                            (必填)
     * @apiParam {Integer}   status             是否抽中，0=否，1=是，默认为0
     * @apiParam {Long}      lotteryId          中奖id
     *
     * @apiSuccess {Long}    coin               剩余馒头数
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {
     *          "coin": 100
     *      }
     * }
     */
    @RequestMapping(value = "/lottery", method = RequestMethod.POST)
    public void lottery(HttpServletResponse response, Long userId, Integer status, Long lotteryId) {
        String result = "";
        try {
            result = JsonUtil.obj2ApiJson(Result.successInfoApi(welfareApiService.lottery(userId, status, lotteryId), "coin"));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        } catch (BreadShortException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00016));
        } catch (PrizeNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00024));
        }
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/welfare/lotteryList 6、抽奖记录列表
     * @apiVersion 0.0.1
     * @apiName welfare.lotteryList
     * @apiGroup welfare
     *
     * @apiDescription 抽奖记录列表
     *
     * @apiParam {Long}      userId                用户ID                    (必填)
     * @apiParam {Integer}   pageNum               页码
     * @apiParam {Integer}   pageSize              每页显示条数
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id               id
     * @apiSuccess {String}  list.content          详情
     * @apiSuccess {String}  list.coin             馒头变动数
     * @apiSuccess {Long}    list.createDate       时间
     *
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
     *                  "content": "抽奖",
     *                  "coin": -10,
     *                  "createDate": 1466502060745
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/lotteryList", method = RequestMethod.POST)
    public void lotteryList(HttpServletResponse response, Long userId, Integer pageNum, Integer pageSize) {
        Page<UserLottery> page = welfareApiService.lotteryPage(userId, pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "createDate", "userInfo");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/welfare/exchangePlus 7、兑换福利（不包含实物）
     * @apiVersion 0.0.1
     * @apiName welfare.exchangePlus
     * @apiGroup welfare
     *
     * @apiDescription 兑换福利（不包含实物）
     *
     * @apiParam {Long}      userId             用户ID                    (必填)
     * @apiParam {Long}      welfareId          福利ID                    (必填)
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status": 0,
     *      "msg": "",
     *      "data": {}
     * }
     */
    @RequestMapping(value = "/exchangePlus", method = RequestMethod.POST)
    public void exchangePlus(HttpServletResponse response, Long userId, Long welfareId) {
        String result = "";
        try {
            welfareApiService.exchangePlus(userId, welfareId);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00021));
        } catch (WelfareNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00021));
        } catch (BreadShortException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_00016));
        }
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/welfare/detailList 8、收支明细记录列表
     * @apiVersion 0.0.1
     * @apiName welfare.detailList
     * @apiGroup welfare
     *
     * @apiDescription 收支明细记录列表
     *
     * @apiParam {Long}      userId                用户ID                    (必填)
     * @apiParam {Integer}   pageNum               页码
     * @apiParam {Integer}   pageSize              每页显示条数
     *
     * @apiSuccess {Object}  list
     * @apiSuccess {Long}    list.id               id
     * @apiSuccess {String}  list.content          详情
     * @apiSuccess {String}  list.count            馒头变动数
     * @apiSuccess {Long}    list.createDate       时间
     *
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
     *                  "content": "抽奖",
     *                  "count": -10,
     *                  "createDate": 1466502060745
     *              }
     *          ]
     *      }
     * }
     */
    @RequestMapping(value = "/detailList", method = RequestMethod.POST)
    public void detailList(HttpServletResponse response, Long userId, Integer pageNum, Integer pageSize) {
        Page<UserIntegral> page = userIntegralService.integralPage(userId, Constant.USER_INTEGRAL_CHANGE_TYPE_002, pageNum, pageSize);
        Map<String, Object> dataMap = JsonUtil.fitting(page);
        String result = JsonUtil.obj2ApiJson(Result.successApi(dataMap), "type", "userInfo", "updateDate");
        WebUtil.printApi(response, result);
    }
}
