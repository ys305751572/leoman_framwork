package com.leoman.userapi.service;

import com.leoman.common.service.GenericManager;
import com.leoman.exception.*;
import com.leoman.message.entity.vo.MessageVo;
import com.leoman.post.entity.PostBase;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserIntegral;
import com.leoman.user.entity.UserLogin;
import com.leoman.user.entity.vo.UserCollectVo;
import com.leoman.user.entity.vo.UserInfoPlusVo;
import com.leoman.user.entity.vo.UserRegisterVo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserInfoService extends GenericManager<UserInfo> {

    public UserLogin registerCheck(UserRegisterVo urv) throws UserExistException, CodeErrorException;

    public UserInfo register(UserRegisterVo urv) throws UserExistException, CodeErrorException;

    public UserInfo findOne(Long userId) throws UserNotFindException;

    public UserInfo findUserLoginById(Long userId) throws UserNotFindException;

    public UserInfo findOne(Long sourceId, Long userId) throws UserNotFindException;

    public UserInfoPlusVo findCreatorInfo(Long creatorId, Long userId) throws ParamsErrorException, CreatorNotFindException;

    public void attention(Long userId, Long sourceId, Integer isCreator, Integer type) throws ParamsErrorException, UserNotFindException, CreatorNotFindException, AttentionExistException, AttentionNotFindException;

    public List<UserCollectVo> findCollectListByUserId(Long userId) throws UserNotFindException;

    public Page<PostBase> findCollectListByUserIdPost(Long userId, Integer pageNum, Integer pageSize);

    public void collect(Long userId, Long sourceId, Integer type, Integer status) throws ParamsErrorException, UserNotFindException, CollectExistException, CollectNotFindException;

    public void praise(Long userId, Long sourceId, Integer type, Integer status) throws ParamsErrorException, UserNotFindException, PraiseExistException, PraiseNotFindException;

    public void modifyCreatorInfo(Long creatorId, Long cityId, String description, String weibo, String experience) throws ParamsErrorException, CreatorNotFindException;

    public void binding(Long userId, String weixin, String weibo) throws UserNotFindException;

    public Page<UserIntegral> integralPage(Long userId, Integer pageNum, Integer pageSize) throws UserNotFindException;

    public UserInfo findOneByWeixin(String weixin);

    public UserInfo findOneByWeibo(String weibo);

    // 回复我的帖子的用户列表
    public Page<MessageVo> pageMessage(Long userId, Integer pageNum, Integer pageSize);
}
