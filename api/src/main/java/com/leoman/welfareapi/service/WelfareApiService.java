package com.leoman.welfareapi.service;

import com.leoman.exception.*;
import com.leoman.giftrecord.entity.GiftExchangeRecord;
import com.leoman.systemsetting.entity.Prize;
import com.leoman.welfare.entity.UserLottery;
import com.leoman.welfare.entity.Welfare;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface WelfareApiService {

    // 福利列表
    public Page<Welfare> iPage(Integer pageNum, Integer pageSize);

    // 兑换实物
    public void exchangeWelfare(Long userId, Long welfareId, String nickname, String mobile, String address) throws ParamsErrorException, WelfareNotFindException, UserNotFindException, BreadShortException;

    // 兑换福利（非实物）
    public void exchangePlus(Long userId, Long welfareId) throws ParamsErrorException, UserNotFindException, WelfareNotFindException, BreadShortException;

    // 福利兑换记录列表
    public Page<GiftExchangeRecord> iPageExchange(Long userId, Integer pageNum, Integer pageSize);

    // 获取抽奖几率列表
    public List<Prize> probabilityList();

    // 抽奖
    public Long lottery(Long userId, Integer status, Long lotteryId) throws UserNotFindException, BreadShortException, PrizeNotFindException;

    // 抽奖记录列表
    public Page<UserLottery> lotteryPage(Long userId, Integer pageNum, Integer pageSize);
}
