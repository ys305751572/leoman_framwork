package com.leoman.postapi.service;

import com.leoman.category.entity.Category;
import com.leoman.exception.*;
import com.leoman.post.entity.PostBase;
import com.leoman.post.entity.vo.PostCommentVo;
import com.leoman.post.entity.vo.PostMiniVo;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface PostService {

    // 普通用户发帖
    public void saveUserPost(MultipartRequest multipartRequest, Long userId, Long category, String content, Integer voiceLength);

    // 发表关于帖子的评论
    public void savePostComment(MultipartRequest multipartRequest, Integer type, Long postId, Long userId, String content) throws UserNotFindException;

    // 获取帖子列表
    public Page<PostBase> iPage(String key, Long userId, Long category, Integer isMe, Integer pageNum, Integer pageSize) throws ParamsErrorException;

    // 根据id获取帖子详情
    public PostBase findOneById(Long postId, Integer type, Long userId) throws ParamsErrorException, PostNotFindException;

    // 获取帖子评论列表
    public List<PostCommentVo> findCommentList(Integer type, Long postId);

    // 回复帖子评论
    public void savePostReply(MultipartRequest multipartRequest, Long postCommentId, Long userId, String content) throws ParamsErrorException, UserNotFindException, CommentNotFindException;

    // 用户打赏主创
    public void reward(Long creatorId, Long userId, Integer count) throws ParamsErrorException, CreatorNotFindException, UserNotFindException, BreadShortException;

    // 投票帖投票
    public void vote(Long userId, Long tpPostSubId) throws ParamsErrorException, UserNotFindException, VoteExistException;

    // 我回复的（赞过的）帖子列表
    public List<PostMiniVo> findListByUserId(Long userId, Integer type) throws ParamsErrorException, UserNotFindException;

    // 帖子分类列表
    public List<Category> postTypeList();

    // 我收藏的帖子列表（分页）
    public Page<PostBase> iPageByUserCollect(Long userId, Integer pageNum, Integer pageSize);
}
