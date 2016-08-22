package com.leoman.postapi.service.impl;

import com.leoman.category.dao.CategoryDao;
import com.leoman.category.entity.Category;
import com.leoman.creator.dao.CreatorDao;
import com.leoman.creator.entity.Creator;
import com.leoman.entity.Configue;
import com.leoman.entity.Constant;
import com.leoman.entity.FileBo;
import com.leoman.exception.*;
import com.leoman.post.dao.PostBaseDao;
import com.leoman.post.dao.PostCommentDao;
import com.leoman.post.dao.PostImageDao;
import com.leoman.post.dao.TpPostUserDao;
import com.leoman.post.entity.*;
import com.leoman.post.entity.vo.*;
import com.leoman.postapi.service.PostService;
import com.leoman.user.dao.UserAttentionDao;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.entity.UserAttention;
import com.leoman.user.entity.UserInfo;
import com.leoman.userapi.service.UserIntegralService;
import com.leoman.userapi.service.impl.UserLoginServiceApiImpl;
import com.leoman.userchange.dao.UserChangeDao;
import com.leoman.userchange.entity.UserChange;
import com.leoman.utils.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostImageDao postImageDao;

    @Autowired
    private PostCommentDao postCommentDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private PostBaseDao postBaseDao;

    @Autowired
    private UserChangeDao userChangeDao;

    @Autowired
    private UserAttentionDao userAttentionDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CreatorDao creatorDao;

    @Autowired
    private TpPostUserDao tpPostUserDao;

    @Autowired
    private UserIntegralService userIntegralService;

    @Override
    @Transactional
    public void saveUserPost(MultipartRequest multipartRequest, Long userId, Long category, String content, Integer voiceLength) {
        try {
            // 保存用户帖基本信息
            PostBase postBase = new PostBase();
            UserInfo userInfo = userInfoDao.findOne(userId);

            // 发布帖子可获得20经验值
            userIntegralService.changeIntegral(userInfo, "发布帖子获得20经验值", 20);

            // 保存图片和语音
            MultipartFile voice = multipartRequest.getFile("voice");
            if (null != voice) {
                FileBo voiceFile = FileUtil.save(voice);
                postBase.setUrl(voiceFile.getPath());
                postBase.setLength(voiceLength);
            }

            postBase.setUserInfo(userInfo);
            postBase.setContent(content);
            postBase.setType(Constant.POST_TYPE_003);
            postBase.setCategory(categoryDao.findOne(category));
            postBase.setPraise(0);
            postBase.setComment(0);
            postBase.setCreateDate(System.currentTimeMillis());
            postBase.setStatus(0);

            postBaseDao.save(postBase);

            List<MultipartFile> list = multipartRequest.getFiles("file");
            FileBo imageFile = null;
            PostImage postImage = null;
            for (MultipartFile file : list) {
                if (null != file) {
                    imageFile = FileUtil.save(file);
                    postImage = new PostImage();
                    postImage.setPostId(postBase.getId());
                    postImage.setType(Constant.POST_TYPE_003);
                    postImage.setUrl(imageFile.getPath());

                    postImageDao.save(postImage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void savePostComment(MultipartRequest multipartRequest, Integer type, Long postId, Long userId, String content) throws UserNotFindException {
        UserInfo userInfo = UserLoginServiceApiImpl.getUserInfo(userId, userInfoDao);

        // 首个回复、首个点赞帖子分别获得4、2个经验值
        if (postCommentDao.findList(type, postId, Constant.COMMENT_TYPE_DEFAULT).size() == 0) {
            userIntegralService.changeIntegral(userInfo, "首个评论帖子获得4个经验值", 4);
        }

        PostComment postComment = new PostComment();
        postComment.setPostBase(postBaseDao.findOne(postId));
        postComment.setFromUser(userInfoDao.findOne(userId));
        postComment.setType(type);
        postComment.setContent(content);
        postComment.setStatus(Constant.COMMENT_TYPE_DEFAULT);

        postCommentDao.save(postComment);

        // 增加评论图片
        addPostImages(multipartRequest, postComment.getId());

        // 增加评论数
        PostBase postBase = postBaseDao.getPostInfo(postId, type);
        postBase.setComment(postBase.getComment() + 1);
        postBaseDao.save(postBase);

        // 发布的帖子被主创点赞和评论分别获得10、20经验值
        if (type == Constant.POST_TYPE_003 && userInfo.getIsCreator() == 1) {
            userInfo = postBaseDao.findOne(postId).getUserInfo();
            userIntegralService.changeIntegral(userInfo, "发布的帖子被主创评论获得20经验值", 20);
        }
    }

    private void addPostImages(MultipartRequest multipartRequest, Long postCommentId) {
        // 增加评论图片
        PostImage postImage = null;
        List<MultipartFile> multipartFiles = multipartRequest.getFiles("images");
        try {
            for (MultipartFile multipartFile : multipartFiles) {
                postImage = new PostImage();
                postImage.setPostId(postCommentId);
                postImage.setType(Constant.POST_TYPE_004);
                postImage.setUrl(FileUtil.save(multipartFile).getPath());

                postImageDao.save(postImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Page<PostBase> iPage(String key, Long userId, Long category, Integer isMe, Integer pageNum, Integer pageSize) throws ParamsErrorException {
        if (null == pageNum || null == pageSize) {
            throw new ParamsErrorException();
        }

        if (StringUtils.isNotEmpty(key)) {
            key = "%" + key + "%";
        } else {
            key = "%%";
        }

        Page<PostBase> page = null;
        if (null == isMe || isMe == 0) {
            // 热门帖子
            if (null == category || category == 0) {
                page = postBaseDao.iPageDesc(key, new PageRequest(pageNum - 1, pageSize));
            } else {
                page = postBaseDao.iPageByCategory(key, category, new PageRequest(pageNum - 1, pageSize));
            }
        } else {
            // 获取我的帖子列表
            page = postBaseDao.iPageMyPost(key, userId, new PageRequest(pageNum - 1, pageSize));
        }

        for (PostBase postBase : page.getContent()) {
            getPostBaseInfo(postBase, userId);
        }

        return page;
    }

    @Override
    public PostBase findOneById(Long postId, Integer type, Long userId) throws ParamsErrorException, PostNotFindException {
        try {
            if (null == postId || null == type) {
                throw new ParamsErrorException();
            }

            PostBase postBase = postBaseDao.getPostInfo(postId, type);
            getPostBaseInfo(postBase, userId);
            return postBase;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private PostBase getPostBaseInfo(PostBase postBase, Long userId) {
        if (postBase.getType() == Constant.POST_TYPE_003) {
            postBase.setUser(getPostUserPlusVo(postBase.getUserInfo(), userId));
        }
        postBase.setImageList(getPostImageVo(postImageDao.findList(postBase.getId(), postBase.getType())));
        postBase.setUserList(getPostUserVo(userChangeDao.findList(postBase.getId(), Constant.USER_CHANGE_TYPE_001)));
        postBase.setCommentList(getPostCommentVo(postCommentDao.findList(postBase.getType(), postBase.getId(), 0)));

        postBase.setAvater(StringUtils.isNotEmpty(postBase.getAvater()) ? Configue.getUploadUrl() + postBase.getAvater() : "");
        postBase.setUrl(StringUtils.isNotEmpty(postBase.getUrl()) ? Configue.getUploadUrl() + postBase.getUrl() : "");

        return postBase;
    }

    @Override
    public List<PostCommentVo> findCommentList(Integer type, Long postId) {
        List<PostCommentVo> postCommentVoList = new ArrayList<PostCommentVo>();

        try {
            List<PostComment> list = postCommentDao.findList(type, postId);
            for (PostComment postComment : list) {
                postCommentVoList.add(getPostCommentVo(postComment));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postCommentVoList;
    }

    @Override
    public void savePostReply(MultipartRequest multipartRequest, Long postCommentId, Long userId, String content) throws ParamsErrorException, UserNotFindException, CommentNotFindException {
        if (null == postCommentId || null == userId || !StringUtils.isNotEmpty(content)) {
            throw new ParamsErrorException();
        }

        PostComment postComment = postCommentDao.findOne(postCommentId);
        if (null == postComment) {
            throw new CommentNotFindException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        try {
            PostComment tempPostComment = new PostComment();
            tempPostComment.setPostBase(postComment.getPostBase());
            tempPostComment.setFromUser(userInfoDao.findOne(userId));
            tempPostComment.setToPostComment(postCommentDao.findOne(postComment.getId()));
            tempPostComment.setToUser(postComment.getFromUser());
            tempPostComment.setType(postComment.getType());
            tempPostComment.setContent(content);
            tempPostComment.setStatus(Constant.COMMENT_TYPE_REPLY);

            postCommentDao.save(tempPostComment);

            // 增加评论图片
            addPostImages(multipartRequest, tempPostComment.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void reward(Long creatorId, Long userId, Integer count) throws ParamsErrorException, CreatorNotFindException, UserNotFindException, BreadShortException {
        if (null == creatorId || null == userId || null == count) {
            throw new ParamsErrorException();
        }

        Creator creator = creatorDao.findOne(creatorId);
        if (null == creator) {
            throw new CreatorNotFindException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        if (null != userInfo.getCreator() && userInfo.getCreator().getId() == creatorId) {
            throw new ParamsErrorException();
        }

        if (userInfo.getCoin() < count) {
            throw new BreadShortException();
        }

        // 主创增加馒头
        UserInfo creatorInfo = userInfoDao.findOneByCreatorId(creatorId);
        creatorInfo.setCoin(creatorInfo.getCoin() + count);
        userInfoDao.save(creatorInfo);

        // 用户减少馒头
        userInfo.setCoin(userInfo.getCoin() - count);
        userInfoDao.save(userInfo);

        // 送馒头贴主可获得15经验值
        userIntegralService.changeIntegral(userInfo, "送馒头贴主获得15经验值", 15);
    }

    @Override
    public void vote(Long userId, Long tpPostSubId) throws ParamsErrorException, UserNotFindException, VoteExistException {
        if (null == userId || null == tpPostSubId) {
            throw new ParamsErrorException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        TpPostSub tpPostSub = new TpPostSub();
        tpPostSub.setId(tpPostSubId);

        TpPostUser tpPostUser = tpPostUserDao.findOneByParams(tpPostSubId, userId);
        if (null != tpPostUser) {
            throw new VoteExistException();
        }

        tpPostUser = new TpPostUser();
        tpPostUser.setUserInfo(userInfo);
        tpPostUser.setTpPostSub(tpPostSub);

        tpPostUserDao.save(tpPostUser);
    }

    @Override
    public List<PostMiniVo> findListByUserId(Long userId, Integer type) throws ParamsErrorException, UserNotFindException {
        if (null == userId || null == type) {
            throw new ParamsErrorException();
        }

        List<PostMiniVo> list = new ArrayList<PostMiniVo>();
        PostMiniVo postMiniVo = null;

        if (type == 1) {
            List<PostComment> postCommentList = postCommentDao.findListByToUserId(userId);
            for (PostComment postComment : postCommentList) {
                postMiniVo = new PostMiniVo();
                postMiniVo.setId(postComment.getPostBase().getId());
                postMiniVo.setContent(postComment.getContent());
                postMiniVo.setType(postComment.getType());
                postMiniVo.setCreateDate(postComment.getCreateDate());

                list.add(postMiniVo);
            }
        } else {
            List<UserChange> userChangeList = userChangeDao.findListByParams(userId, Constant.USER_CHANGE_TYPE_001);
            for (UserChange userChange : userChangeList) {
                postMiniVo = new PostMiniVo();
                postMiniVo.setId(userChange.getSourceId());
                postMiniVo.setContent(userChange.getContent());
                postMiniVo.setType(userChange.getType());
                postMiniVo.setCreateDate(userChange.getCreateDate());

                list.add(postMiniVo);
            }
        }

        return list;
    }

    @Override
    public List<Category> postTypeList() {
        return categoryDao.findType(Constant.CATEGORY_TYPE_001);
    }

    @Override
    public Page<PostBase> iPageByUserCollect(Long userId, Integer pageNum, Integer pageSize) {
        return postBaseDao.iPageByUserCollect(userId, new PageRequest(pageNum - 1, pageSize));
    }

    /**
     * 拼接帖子评论VO类
     *
     * @param postComment
     * @return
     */
    private PostCommentVo getPostCommentVo(PostComment postComment) {
        PostCommentVo postCommentVo = null;
        UserInfo userInfo = userInfoDao.findOne(postComment.getFromUser().getId());
        postCommentVo = new PostCommentVo();
        postCommentVo.setId(postComment.getId());
        postCommentVo.setUserId(postComment.getFromUser().getId());
        postCommentVo.setNickname(userInfo.getNickname());
        postCommentVo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());
        postCommentVo.setContent(postComment.getContent());
        postCommentVo.setCommentImageList(getPostImageVo(postImageDao.findList(postComment.getId(), Constant.POST_TYPE_004)));
        postCommentVo.setCreateDate(postComment.getCreateDate());
        if (null != postComment.getToPostComment()) {
            postCommentVo.setReplyName(postComment.getToPostComment().getFromUser().getNickname());
            postCommentVo.setReplyContent(postComment.getToPostComment().getContent());
            postCommentVo.setReplyImageList(getPostImageVo(postImageDao.findList(postComment.getToPostComment().getId(), Constant.POST_TYPE_004)));
        }

        return postCommentVo;
    }

    /**
     * 拼接帖子图片VO列表
     *
     * @param list
     * @return
     */
    public static List<PostImageVo> getPostImageVo(List<PostImage> list) {
        List<PostImageVo> postImageVoList = new ArrayList<PostImageVo>();
        PostImageVo postImageVo = null;

        try {
            for (PostImage postImage : list) {
                postImageVo = new PostImageVo();
                postImageVo.setId(postImage.getId());
                postImageVo.setUrl(Configue.getUploadUrl() + postImage.getUrl());

                postImageVoList.add(postImageVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postImageVoList;
    }

    /**
     * 拼接帖子评论VO列表
     *
     * @param list
     * @return
     */
    private List<PostCommentVo> getPostCommentVo(List<PostComment> list) {
        List<PostComment> postCommentList = new ArrayList<PostComment>();

        if (null == list && list.size() > 3) {
            postCommentList.add(list.get(0));
            postCommentList.add(list.get(1));
            postCommentList.add(list.get(2));

            list = postCommentList;
        }

        List<PostCommentVo> postCommentVoList = new ArrayList<PostCommentVo>();
        PostCommentVo postCommentVo = null;
        UserInfo userInfo = null;
        for (PostComment postComment : list) {
            postCommentVo = new PostCommentVo();
            userInfo = userInfoDao.findOne(postComment.getFromUser().getId());
            postCommentVo.setUserId(postComment.getFromUser().getId());
            postCommentVo.setNickname(userInfo.getNickname());
            postCommentVo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());
            postCommentVo.setContent(postComment.getContent());
            postCommentVo.setCreateDate(postComment.getCreateDate());

            postCommentVoList.add(postCommentVo);
        }

        return postCommentVoList;
    }

    /**
     * 拼接帖子点赞用户VO类
     *
     * @param list
     * @return
     */
    private List<PostUserVo> getPostUserVo(List<UserChange> list) {
        List<PostUserVo> postUserVoList = new ArrayList<PostUserVo>();
        PostUserVo postUserVo = null;
        UserInfo userInfo = null;

        try {
            for (UserChange userChange : list) {
                userInfo = userChange.getUserInfo();
                postUserVo = new PostUserVo();
                postUserVo.setId(userInfo.getId());
                postUserVo.setNickname(userInfo.getNickname());
                postUserVo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());

                postUserVoList.add(postUserVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postUserVoList;
    }

    /**
     * 拼接帖子发布人VO类
     *
     * @param userInfo
     * @param userId
     * @return
     */
    private PostUserPlusVo getPostUserPlusVo(UserInfo userInfo, Long userId) {
        PostUserPlusVo postUserPlusVo = new PostUserPlusVo();

        postUserPlusVo.setId(userInfo.getId());
        postUserPlusVo.setNickname(userInfo.getNickname());
        postUserPlusVo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());
        postUserPlusVo.setLevel(userInfo.getLevel());
        if (null != userId) {
            UserAttention tempUserAttention = userAttentionDao.findOneInfo(userId, userInfo.getId());
            if (null == tempUserAttention || tempUserAttention.getStatus() == 1) {
                postUserPlusVo.setIsAttention(1);
            } else {
                postUserPlusVo.setIsAttention(0);
            }
        }
        postUserPlusVo.setIsCreator(userInfo.getIsCreator());

        return postUserPlusVo;
    }

    /**
     * 获取投票帖详情列表
     *
     * @param postId
     * @param userId
     * @return
     */
    private List<TpPostSub> getTpPostSubList(Long postId, Long userId) {
        List<TpPostSub> list = postBaseDao.iFindListTP(postId);

        if (null == userId) {
            for (TpPostSub tpPostSub : list) {
                tpPostSub.setCover(Configue.getUploadUrl() + tpPostSub.getCover());
                tpPostSub.setStatus(null != tpPostUserDao.findOneByParams(tpPostSub.getId(), userId) ? 0 : 1);
            }
        }

        return list;
    }

    private List<ZbPostInfo> getZbPostInfo(Long postId) {
        List<ZbPostInfo> list = postBaseDao.iFindListZB(postId);
        List<PostImage> imageList = null;

        for (ZbPostInfo zbPostInfo : list) {
            imageList = postImageDao.findList(postId, Constant.POST_TYPE_001);

            for (PostImage postImage : imageList) {
                postImage.setUrl(Configue.getUploadUrl() + postImage.getUrl());
            }

            zbPostInfo.setImageList(imageList);
        }

        return list;
    }
}
