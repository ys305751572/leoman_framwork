package com.leoman.userapi.service.impl;

import com.leoman.city.dao.CityDao;
import com.leoman.city.entity.City;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.creator.dao.CreatorDao;
import com.leoman.creator.dao.CreatorImageDao;
import com.leoman.creator.entity.Creator;
import com.leoman.creator.entity.CreatorImage;
import com.leoman.entity.Configue;
import com.leoman.entity.Constant;
import com.leoman.exception.*;
import com.leoman.message.entity.vo.MessageVo;
import com.leoman.post.dao.PostBaseDao;
import com.leoman.post.dao.PostCommentDao;
import com.leoman.post.entity.PostBase;
import com.leoman.post.entity.PostComment;
import com.leoman.post.entity.vo.PostImageVo;
import com.leoman.postapi.service.PostService;
import com.leoman.user.dao.*;
import com.leoman.user.entity.*;
import com.leoman.user.entity.vo.UserCollectVo;
import com.leoman.user.entity.vo.UserInfoPlusVo;
import com.leoman.user.entity.vo.UserRegisterVo;
import com.leoman.userapi.service.UserInfoService;
import com.leoman.userapi.service.UserIntegralService;
import com.leoman.userchange.dao.UserChangeDao;
import com.leoman.userchange.entity.UserChange;
import com.leoman.vcode.VcodeService;
import com.leoman.work.entity.vo.WorkComicVo;
import com.leoman.work.entity.vo.WorkNovelVo;
import com.leoman.work.entity.vo.WorkVideoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class UserInfoServiceImpl extends GenericManagerImpl<UserInfo, UserInfoDao> implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserLoginDao userLoginDao;

    @Autowired
    private PostCommentDao postCommentDao;

    @Autowired
    private PostService postService;

    @Autowired
    private UserAttentionDao userAttentionDao;

    @Autowired
    private UserCollectDao userCollectDao;

    @Autowired
    private VcodeService vcodeService;

    @Autowired
    private CreatorDao creatorDao;

    @Autowired
    private CreatorImageDao creatorImageDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private UserIntegralDao userIntegralDao;

    @Autowired
    private UserChangeDao userChangeDao;

    @Autowired
    private PostBaseDao postBaseDao;

    @Autowired
    private UserIntegralService userIntegralService;

    @Override
    public UserLogin registerCheck(UserRegisterVo urv) throws CodeErrorException {
        if (!vcodeService.validate(urv.getMobile(), urv.getCode())) {
            throw new CodeErrorException();
        }

        UserLogin userlogin = userLoginDao.findByUsername(urv.getMobile());
        if (userlogin != null) {
            return null;
        }
        userlogin = new UserLogin();
        userlogin.setUsername(urv.getMobile());
        userlogin.setPassword(urv.getPassword());
        userLoginDao.save(userlogin);
        return userlogin;
    }

    @Transactional
    @Override
    public UserInfo register(UserRegisterVo urv) throws UserExistException, CodeErrorException {
        UserLogin userlogin = registerCheck(urv);
        if (userlogin == null) {
            throw new UserExistException();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserLogin(userlogin);
        userInfo.setMobile(userlogin.getUsername());
        userInfo.setNickname(urv.getNickname());
        userInfo.setPassword(urv.getPassword());

        // 邀请好友成功注册，可以获得3个馒头
        if (null != urv.getInviteId()) {
            UserInfo inviteUser = userInfoDao.findOne(urv.getInviteId());
            inviteUser.setCoin(inviteUser.getCoin() + Constant.COIN_GET_TYPE_001);
            this.save(inviteUser);
        }

        this.save(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo findOne(Long userId) throws UserNotFindException {
        if (null == userId) {
            throw new UserNotFindException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        userInfo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());

        return userInfo;
    }

    @Override
    public UserInfo findUserLoginById(Long userId) throws UserNotFindException {
        UserInfo userInfo = userInfoDao.findOneByUserId(userId);
        userInfo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());
        return userInfo;
    }

    @Override
    public UserInfo findOne(Long sourceId, Long userId) throws UserNotFindException {
        if (null == sourceId) {
            throw new UserNotFindException();
        }

        UserInfo userInfo = userInfoDao.findOne(sourceId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        userInfo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());

        if (null != userId) {
            UserAttention tempUserAttention = userAttentionDao.findOneInfo(userId, sourceId);
            if (null == tempUserAttention || tempUserAttention.getStatus() == 1) {
                userInfo.setIsAttention(1);
            } else {
                userInfo.setIsAttention(0);
            }
        }

        // 查询收藏数
        userInfo.setNum(userCollectDao.findListByUserId(sourceId).size());

        return userInfo;
    }

    @Override
    public UserInfoPlusVo findCreatorInfo(Long creatorId, Long userId) throws ParamsErrorException, CreatorNotFindException {
        if (null == creatorId) {
            throw new ParamsErrorException();
        }

        UserInfo userInfo = userInfoDao.findOneByCreatorId(creatorId);
        if (null == userInfo || null == userInfo.getCreator()) {
            throw new CreatorNotFindException();
        }

        Creator creator = userInfo.getCreator();

        UserInfoPlusVo userInfoPlusVo = new UserInfoPlusVo();
        userInfoPlusVo.setId(creator.getId());
        userInfoPlusVo.setName(userInfo.getNickname());
        userInfoPlusVo.setCover(Configue.getUploadUrl() + creator.getCoverUrl());
        userInfoPlusVo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());
        userInfoPlusVo.setProvince(creator.getProvince().getName());
        userInfoPlusVo.setCity(creator.getCity().getName());
        userInfoPlusVo.setDescription(creator.getDescription());
        userInfoPlusVo.setLevel(userInfo.getLevel());
        userInfoPlusVo.setFans(Integer.parseInt(userInfo.getFuns().toString()));
        userInfoPlusVo.setFocus(Integer.parseInt(userInfo.getFocus().toString()));

        if (null != userId) {
            UserAttention tempUserAttention = userAttentionDao.findOneInfo(userId, userInfo.getId());
            if (null == tempUserAttention || tempUserAttention.getStatus() == 1) {
                userInfoPlusVo.setIsAttention(1);
            } else {
                userInfoPlusVo.setIsAttention(0);
            }
        }

        userInfoPlusVo.setStillList(getImageList(creatorImageDao.findList(creatorId, 0)));
        userInfoPlusVo.setLifeList(getImageList(creatorImageDao.findList(creatorId, 1)));
        userInfoPlusVo.setWeibo(creator.getWeibo());
        userInfoPlusVo.setFanClub(creator.getExperience());

        return userInfoPlusVo;
    }

    /**
     * 获取生活照和剧照
     *
     * @param imageList
     * @return
     */
    private List<PostImageVo> getImageList(List<CreatorImage> imageList) {
        List<PostImageVo> list = new ArrayList<PostImageVo>();
        PostImageVo postImageVo = null;

        for (CreatorImage creatorImage : imageList) {
            postImageVo = new PostImageVo();
            postImageVo.setId(creatorImage.getId());
            postImageVo.setUrl(Configue.getUploadUrl() + creatorImage.getImage());

            list.add(postImageVo);
        }

        return list;
    }

    @Override
    @Transactional
    public void attention(Long userId, Long sourceId, Integer isCreator, Integer type) throws ParamsErrorException, UserNotFindException, CreatorNotFindException, AttentionExistException, AttentionNotFindException {
        UserAttention userAttention = null;

        if (null == userId || null == sourceId || null == type) {
            throw new ParamsErrorException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        // 获取目标用户信息
        UserInfo sourceUserInfo = null;

        // isCreator  是否主创  0:是，1:否 默认为否
        if (null == isCreator || isCreator == 1) {
            sourceUserInfo = userInfoDao.findOne(sourceId);
            // 默认为否，即不是主创
            userAttention = userAttentionDao.findOneInfo(userId, sourceId);
        } else {
            // 是主创
            sourceUserInfo = userInfoDao.findOneByCreatorId(sourceId);

            if (null == sourceUserInfo) {
                throw new CreatorNotFindException();
            }

            userAttention = userAttentionDao.findOneInfo(userId, sourceUserInfo.getId());
        }

        // type  类型， 1：关注 2：取消关注
        if (type == 1) {
            if (null != userAttention && userAttention.getStatus() == 0) {
                throw new AttentionExistException();
            }

            if (null == userAttention) {
                // 关注
                userAttention = new UserAttention();
                userAttention.setUser(userInfo);
                userAttention.setSourceUser(sourceUserInfo);
                userAttention.setCreateDate(System.currentTimeMillis());
                userAttention.setStatus(0);

                // 用户增加关注数，被关注用户增加粉丝数
                userInfo.setFocus(userInfo.getFocus() + 1);
                sourceUserInfo.setFuns(sourceUserInfo.getFuns() + 1);

                // 第一次关注用户和被关注分别可以获得3个经验值
                userIntegralService.changeIntegral(userInfo, "关注用户" + sourceUserInfo.getNickname() + "，获得3个经验值", 3);
                userIntegralService.changeIntegral(sourceUserInfo, "被用户" + userInfo.getNickname() + "关注，获得3个经验值", 3);
            } else {
                userAttention.setStatus(0);
            }
        } else {
            if (null == userAttention || userAttention.getStatus() == 1) {
                throw new AttentionNotFindException();
            }

            // 取消关注
            userAttention.setStatus(1);

            // 用户减少关注数，被关注用户减少粉丝数
            userInfo.setFocus(userInfo.getFocus() - 1);
            sourceUserInfo.setFuns(sourceUserInfo.getFuns() - 1);
        }

        userAttentionDao.save(userAttention);
        userInfoDao.save(userInfo);
        userInfoDao.save(sourceUserInfo);
    }

    @Override
    public List<UserCollectVo> findCollectListByUserId(Long userId) {
        List<UserCollect> collectList = userCollectDao.findListByUserId(userId);
        List<UserCollectVo> list = new ArrayList<UserCollectVo>();
        UserCollectVo userCollectVo = null;

        WorkVideoVo workVideoVo = null;
        WorkNovelVo workNovelVo = null;
        WorkComicVo workComicVo = null;
        PostBase postBase = null;

        try {
            for (UserCollect userCollect : collectList) {
                userCollectVo = new UserCollectVo();

                /*switch (userCollect.getType()) {
                    case 1:
                        // 视频
                        workVideoVo = workService.findVideo(userCollect.getSourceId(), userId);
                        if (null == workVideoVo) {
                            continue;
                        }
                        userCollectVo.setId(workVideoVo.getId());
                        userCollectVo.setName(workVideoVo.getName());
                        userCollectVo.setAvater(Configue.getUploadUrl() + workVideoVo.getCover());
                        userCollectVo.setPlayNum(workVideoVo.getPlayNum());
                        userCollectVo.setBarrageNum(workVideoVo.getBarrageNum());
                        break;
                    case 2:
                        // 小说
                        workNovelVo = workService.findNovel(userCollect.getSourceId(), userId);
                        if (null == workNovelVo) {
                            continue;
                        }
                        userCollectVo.setId(workNovelVo.getId());
                        userCollectVo.setName(workNovelVo.getName());
                        userCollectVo.setAuthor(workNovelVo.getAuthor());
                        userCollectVo.setAvater(Configue.getUploadUrl() + workNovelVo.getCover());
                        userCollectVo.setDescription(workNovelVo.getDescription());
                        break;
                    case 3:
                        // 漫画
                        workComicVo = workService.findComic(userCollect.getSourceId(), userId);
                        if (null == workComicVo) {
                            continue;
                        }
                        userCollectVo.setId(workComicVo.getId());
                        userCollectVo.setName(workComicVo.getName());
                        userCollectVo.setAuthor(workComicVo.getAuthor());
                        userCollectVo.setAvater(Configue.getUploadUrl() + workComicVo.getCover());
                        userCollectVo.setPlayNum(workComicVo.getPlayNum());
                        userCollectVo.setUpdateTime(workComicVo.getUpdateTime());
                        break;
                }*/

                list.add(userCollectVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Page<PostBase> findCollectListByUserIdPost(Long userId, Integer pageNum, Integer pageSize) {
        Page<PostBase> page = postService.iPageByUserCollect(userId, pageNum, pageSize);

        for (PostBase postBase : page.getContent()) {
            postBase.setAvater(StringUtils.isNotEmpty(postBase.getAvater()) ? Configue.getUploadUrl() + postBase.getAvater() : "");
        }

        return page;
    }

    @Override
    public void collect(Long userId, Long sourceId, Integer type, Integer status) throws ParamsErrorException, UserNotFindException, CollectExistException, CollectNotFindException {
        if (null == userId || null == sourceId || null == type || null == status) {
            throw new ParamsErrorException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        UserCollect userCollect = userCollectDao.findOneByParams(userId, sourceId, type);

        // status  类型，1：收藏 2：取消收藏
        if (status == 1) {
            if (null != userCollect) {
                throw new CollectExistException();
            }

            // 收藏
            userCollect = new UserCollect();
            userCollect.setUserInfo(userInfo);
            userCollect.setSourceId(sourceId);
            userCollect.setType(type);

            userCollectDao.save(userCollect);

            if (type == 4) {
                // 发布的帖子被收藏可获得2个经验值
                PostBase postBase = postBaseDao.findOne(sourceId);
                if (null != postBase) {
                    userIntegralService.changeIntegral(postBase.getUserInfo(), "发布的帖子被收藏获得2个经验值", 2);
                }
            }
        } else {
            if (null == userCollect) {
                throw new CollectNotFindException();
            }

            userCollectDao.delete(userCollect);
        }
    }

    @Override
    public void praise(Long userId, Long sourceId, Integer type, Integer status) throws ParamsErrorException, UserNotFindException, PraiseExistException, PraiseNotFindException {
        if (null == userId || null == sourceId || null == type) {
            throw new ParamsErrorException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        UserChange userChange = userChangeDao.findOneByParams(userId, sourceId, Constant.USER_CHANGE_TYPE_001);
        PostBase postBase = postBaseDao.getPostInfo(sourceId, type);

        // 默认为点赞
        if (null == status || status == 1) {
            if (null != userChange) {
                throw new PraiseExistException();
            }

            // 首个回复、首个点赞帖子分别获得4、2个经验值
            if (userChangeDao.findList(sourceId, type, userId).size() == 0) {
                userIntegralService.changeIntegral(userInfo, "首个点赞帖子获得2个经验值", 2);
            }

            userChange = new UserChange();
            userChange.setSourceId(sourceId);
            userChange.setType(Constant.USER_CHANGE_TYPE_001);

            PostBase postBase1 = postBaseDao.findOne(sourceId);
            if (null != postBase1) {
                userChange.setContent(postBase1.getContent());
            }

            userChange.setUserInfo(userInfo);

            userChangeDao.save(userChange);

            // 增加点赞数
            postBase.setPraise(postBase.getPraise() + 1);

            // 发布的帖子被主创点赞和评论分别获得10、20经验值
            if (type == Constant.POST_TYPE_003 && userInfo.getIsCreator() == 1) {
                userInfo = postBaseDao.findOne(sourceId).getUserInfo();
                userIntegralService.changeIntegral(userInfo, "发布的帖子被主创点赞获得10经验值", 10);
            } else {
                userInfo = postBaseDao.findOne(sourceId).getUserInfo();
                userIntegralService.changeIntegral(userInfo, "发布的帖子被普通用户赞获得2经验值", 2);
            }
        } else {
            if (null == userChange) {
                throw new PraiseNotFindException();
            }
            userChangeDao.delete(userChange);

            // 减少点赞数
            postBase.setPraise(postBase.getPraise() - 1);
        }

        postBaseDao.save(postBase);
    }

    @Override
    public void modifyCreatorInfo(Long creatorId, Long cityId, String description, String weibo, String experience) throws ParamsErrorException, CreatorNotFindException {
        if (null == creatorId) {
            throw new ParamsErrorException();
        }

        Creator creator = creatorDao.findOne(creatorId);
        if (null == creator) {
            throw new CreatorNotFindException();
        }

        try {
            if (null != cityId) {
                City city = cityDao.findOne(cityId);
                creator.setCity(city);
                creator.setProvince(city.getProvince());
            }

            if (StringUtils.isNotEmpty(description)) {
                creator.setDescription(description);
            }

            if (StringUtils.isNotEmpty(weibo)) {
                creator.setWeibo(weibo);
            }

            if (StringUtils.isNotEmpty(experience)) {
                creator.setExperience(experience);
            }

            creatorDao.save(creator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void binding(Long userId, String weixin, String weibo) throws UserNotFindException {
        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        userInfo.setWeixin(weixin);
        userInfo.setWeibo(weibo);

        userInfoDao.save(userInfo);
    }

    @Override
    public Page<UserIntegral> integralPage(Long userId, Integer pageNum, Integer pageSize) throws UserNotFindException {
        if (null == pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageSize = Constant.PAGE_DEF_SIZE;
        }
        return userIntegralDao.pageByUserId(userId, new PageRequest(pageNum - 1, pageSize));
    }

    @Override
    public UserInfo findOneByWeixin(String weixin) {
        UserInfo userInfo = userInfoDao.findOneByWeixin(weixin);
        userInfo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());
        return userInfo;
    }

    @Override
    public UserInfo findOneByWeibo(String weibo) {
        UserInfo userInfo = userInfoDao.findOneByWeibo(weibo);
        userInfo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());
        return userInfo;
    }

    @Override
    public Page<MessageVo> pageMessage(Long userId, Integer pageNum, Integer pageSize) {
        final Page<PostComment> postCommentPage = postCommentDao.pageByToUserId(userId, new PageRequest(pageNum - 1, pageSize));
        Page<MessageVo> page = new Page<MessageVo>() {
            @Override
            public int getNumber() {
                return postCommentPage.getNumber();
            }

            @Override
            public int getSize() {
                return postCommentPage.getSize();
            }

            @Override
            public int getTotalPages() {
                return postCommentPage.getTotalPages();
            }

            @Override
            public int getNumberOfElements() {
                return postCommentPage.getNumberOfElements();
            }

            @Override
            public long getTotalElements() {
                return postCommentPage.getTotalElements();
            }

            @Override
            public boolean hasPreviousPage() {
                return postCommentPage.hasPreviousPage();
            }

            @Override
            public boolean isFirstPage() {
                return postCommentPage.isFirstPage();
            }

            @Override
            public boolean hasNextPage() {
                return postCommentPage.hasNextPage();
            }

            @Override
            public boolean isLastPage() {
                return postCommentPage.isLastPage();
            }

            @Override
            public Pageable nextPageable() {
                return postCommentPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return postCommentPage.previousPageable();
            }

            @Override
            public Iterator<MessageVo> iterator() {
                final Iterator<PostComment> postCommentIterator = postCommentPage.iterator();
                Iterator<MessageVo> iterator = new Iterator<MessageVo>() {
                    @Override
                    public boolean hasNext() {
                        return postCommentIterator.hasNext();
                    }

                    @Override
                    public MessageVo next() {
                        return getMessageVo(postCommentIterator.next());
                    }
                };

                return iterator;
            }

            @Override
            public List<MessageVo> getContent() {
                List<PostComment> postCommentList = postCommentPage.getContent();
                List<MessageVo> list = new ArrayList<MessageVo>();

                for (PostComment postComment : postCommentList) {
                    list.add(getMessageVo(postComment));
                }

                return list;
            }

            @Override
            public boolean hasContent() {
                return postCommentPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return postCommentPage.getSort();
            }
        };

        return page;
    }

    private MessageVo getMessageVo(PostComment postComment) {
        MessageVo messageVo = new MessageVo();
        UserInfo userInfo = userInfoDao.findOne(postComment.getFromUser().getId());
        PostBase postBase = null;

        messageVo.setId(postComment.getId());
        messageVo.setUserId(userInfo.getId());
        messageVo.setName(userInfo.getNickname());
        messageVo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());
        messageVo.setContent(postComment.getContent());
        messageVo.setSourceId(postComment.getToUser().getId());
        messageVo.setSourceType(7);
        if (postComment.getType() == Constant.POST_TYPE_003) {
            postBase = postBaseDao.findOne(postComment.getPostBase().getId());
            messageVo.setSourceAvater(Configue.getUploadUrl() + postBase.getUserInfo().getAvater());
            messageVo.setSourceAuthor(postBase.getUserInfo().getNickname());
        }
        messageVo.setCreateDate(postComment.getCreateDate());

        return messageVo;
    }
}
