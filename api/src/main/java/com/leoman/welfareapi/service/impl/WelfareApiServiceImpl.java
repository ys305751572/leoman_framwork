package com.leoman.welfareapi.service.impl;

import com.leoman.entity.Configue;
import com.leoman.entity.Constant;
import com.leoman.exception.*;
import com.leoman.giftrecord.dao.GiftExchangeRecordDao;
import com.leoman.giftrecord.entity.GiftExchangeRecord;
import com.leoman.reourceapi.service.impl.ResourceApiServiceImpl;
import com.leoman.systemsetting.dao.PrizeDao;
import com.leoman.systemsetting.entity.Prize;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.entity.UserInfo;
import com.leoman.userapi.service.UserIntegralService;
import com.leoman.welfare.dao.UserLotteryDao;
import com.leoman.welfare.dao.UserWelfareDao;
import com.leoman.welfare.dao.WelfareDao;
import com.leoman.welfare.entity.UserLottery;
import com.leoman.welfare.entity.UserWelfare;
import com.leoman.welfare.entity.Welfare;
import com.leoman.welfareapi.service.WelfareApiService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class WelfareApiServiceImpl implements WelfareApiService {

    @Autowired
    private WelfareDao welfareDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private GiftExchangeRecordDao giftExchangeRecordDao;

    @Autowired
    private PrizeDao prizeDao;

    @Autowired
    private UserLotteryDao userLotteryDao;

    @Autowired
    private UserWelfareDao userWelfareDao;

    @Autowired
    private UserIntegralService userIntegralService;

    @Override
    public Page<Welfare> iPage(Integer pageNum, Integer pageSize) {
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        Page<Welfare> page = welfareDao.findAll(new PageRequest(info[0] - 1, info[1]));

        for (Welfare welfare : page.getContent()) {
            welfare.setCover(Configue.getUploadUrl() + welfare.getCover());
            welfare.setUrl(Configue.getUploadUrl() + welfare.getUrl());
        }

        return page;
    }

    @Override
    @Transactional
    public void exchangeWelfare(Long userId, Long welfareId, String nickname, String mobile, String address) throws ParamsErrorException, WelfareNotFindException, UserNotFindException, BreadShortException {
        if (null == userId || null == welfareId || !StringUtils.isNotEmpty(nickname) || !StringUtils.isNotEmpty(mobile) || !StringUtils.isNotEmpty(address)) {
            throw new ParamsErrorException();
        }

        Welfare welfare = welfareDao.findOne(welfareId);
        if (null == welfare) {
            throw new WelfareNotFindException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        if (userInfo.getCoin() < welfare.getCoin()) {
            throw new BreadShortException();
        }

        // 修改兑换数
        welfare.setNum(welfare.getNum() + 1);
        welfareDao.save(welfare);

        // 修改用户剩余馒头数
        userInfo.setCoin(userInfo.getCoin() - welfare.getCoin());
        userInfoDao.save(userInfo);

        GiftExchangeRecord giftExchangeRecord = new GiftExchangeRecord();
        giftExchangeRecord.setUserInfo(userInfo);
        giftExchangeRecord.setNickname(userInfo.getNickname());
        giftExchangeRecord.setMobile(userInfo.getMobile());
        giftExchangeRecord.setGiftName(welfare.getTitle());
        giftExchangeRecord.setExchangeCoin(welfare.getCoin());
        giftExchangeRecord.setReceiverName(nickname);
        giftExchangeRecord.setReceiverMobile(mobile);
        giftExchangeRecord.setReceiverAddress(address);
        giftExchangeRecord.setStatus(0);

        giftExchangeRecordDao.save(giftExchangeRecord);
    }

    @Override
    public void exchangePlus(Long userId, Long welfareId) throws ParamsErrorException, UserNotFindException, WelfareNotFindException, BreadShortException {
        if (null == userId || null == welfareId) {
            throw new ParamsErrorException();
        }

        Welfare welfare = welfareDao.findOne(welfareId);
        if (null == welfare) {
            throw new WelfareNotFindException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        if (userInfo.getCoin() < welfare.getCoin()) {
            throw new BreadShortException();
        }

        // 修改兑换数
        welfare.setNum(welfare.getNum() + 1);
        welfareDao.save(welfare);

        // 修改用户剩余馒头数
        userInfo.setCoin(userInfo.getCoin() - welfare.getCoin());
        userInfoDao.save(userInfo);

        UserWelfare userWelfare = new UserWelfare();
        userWelfare.setUserInfo(userInfo);
        userWelfare.setWelfare(welfare);

        userWelfareDao.save(userWelfare);

        // 如果兑换的是经验值，则直接修改用户经验值
        if (welfare.getType() == Constant.WELFARE_TYPE_001) {
            userIntegralService.changeIntegral(userInfo, "兑换经验值", welfare.getExper());
        }
    }

    @Override
    public Page<GiftExchangeRecord> iPageExchange(Long userId, Integer pageNum, Integer pageSize) {
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        return giftExchangeRecordDao.findListByUserId(userId, new PageRequest(info[0] - 1, info[1]));
    }

    @Override
    public List<Prize> probabilityList() {
        return prizeDao.findAll();
    }

    @Override
    @Transactional
    public Long lottery(Long userId, Integer status, Long lotteryId) throws UserNotFindException, BreadShortException, PrizeNotFindException {
        if (null == userId) {
            throw new UserNotFindException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);

        if (null == userInfo) {
            throw new UserNotFindException();
        }

        Long tempCoin = userInfo.getCoin();

        if (userInfo.getCoin() < Constant.DEFAULT_COIN) {
            throw new BreadShortException();
        }

        // 增加抽奖记录
        UserLottery userLottery = new UserLottery();
        userLottery.setUserInfo(userInfo);
        userLottery.setContent("抽奖");
        userLottery.setCoin(Constant.DEFAULT_COIN * -1);
        userLotteryDao.save(userLottery);

        // 增加馒头变动记录
        userIntegralService.changeCoin(userInfo, "抽奖", Constant.DEFAULT_COIN * -1);

        tempCoin = tempCoin - Constant.DEFAULT_COIN;

        // 中奖
        if (null != status && status == 1) {
            Prize prize = prizeDao.findOne(lotteryId);
            if (null == prize) {
                throw new PrizeNotFindException();
            }

            // 增加中奖记录
            userLottery = new UserLottery();
            userLottery.setUserInfo(userInfo);
            userLottery.setContent("中奖");
            userLottery.setCoin(prize.getCoin());
            userLotteryDao.save(userLottery);

            // 增加馒头变动记录
            userIntegralService.changeCoin(userInfo, "中奖", prize.getCoin());

            tempCoin += prize.getCoin();
        }

        return tempCoin;
    }

    @Override
    public Page<UserLottery> lotteryPage(Long userId, Integer pageNum, Integer pageSize) {
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        return userLotteryDao.iPageByUserId(userId, new PageRequest(info[0] - 1, info[1]));
    }
}
