package com.leoman.indexapi.service;

import com.leoman.adsindex.entity.vo.AdsIndexVo;
import com.leoman.barrage.entity.Barrage;
import com.leoman.exception.CommentNotFindException;
import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.UserNotFindException;
import com.leoman.exception.WorkNotFindException;
import com.leoman.message.entity.vo.MessageVo;
import com.leoman.work.entity.Work;
import com.leoman.work.entity.WorkComment;
import com.leoman.work.entity.vo.WorkComicVo;
import com.leoman.work.entity.vo.WorkCommentVo;
import com.leoman.work.entity.vo.WorkNovelVo;
import com.leoman.work.entity.vo.WorkVideoVo;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface WorkService {

    // 查询首页作品集列表
    public List<Work> iFindList(String time) throws ParamsErrorException;

    // 查询首页推荐列表
    public List<AdsIndexVo> iFindList();

    // 查询视频列表
    public List<WorkVideoVo> findVideoList(Long category, Integer type) throws ParamsErrorException, WorkNotFindException;

    // 查询视频列表（分页）
    public Page<WorkVideoVo> videoPage(Long userId, Integer pageNum, Integer pageSize);

    // 查询视频列表（分页）
    public Page<WorkVideoVo> videoPage(Integer pageNum, Integer pageSize);

    // 查询漫画列表
    public List<WorkComicVo> findComicList(Long category) throws ParamsErrorException, WorkNotFindException;

    // 查询漫画列表（分页）
    public Page<WorkComicVo> comicPage(Long userId, Integer pageNum, Integer pageSize);

    // 查询漫画列表（分页）
    public Page<WorkComicVo> comicPage(Integer pageNum, Integer pageSize);

    // 查询小说列表
    public List<WorkNovelVo> findNovelList(Long category) throws ParamsErrorException, WorkNotFindException;

    // 查询小说列表（分页）
    public Page<WorkNovelVo> novelPage(Long userId, Integer pageNum, Integer pageSize);

    // 查询小说列表（分页）
    public Page<WorkNovelVo> novelPage2(Integer pageNum, Integer pageSize);

    // 查询视频详情
    public WorkVideoVo findVideo(Long workId, Long userId) throws ParamsErrorException, WorkNotFindException;

    // 查询漫画详情
    public WorkComicVo findComic(Long workId, Long userId) throws ParamsErrorException, WorkNotFindException;

    // 查询小说详情
    public WorkNovelVo findNovel(Long workId, Long userId) throws ParamsErrorException, WorkNotFindException;

    // 查询评论列表
    public Page<WorkComment> iPageByTypeAndId(Long workId, Integer type, Integer pageNum, Integer pageSize);

    // 查询评论列表
    public Page<WorkCommentVo> iPageByParams(Long workId, Integer type, Integer pageNum, Integer pageSize);

    // 发表作品评论
    public void saveWorkComment(MultipartRequest multipartRequest, Long workId, Integer type, Long userId, String content) throws ParamsErrorException, UserNotFindException, WorkNotFindException;

    // 回复作品评论
    public void saveWorkReply(MultipartRequest multipartRequest, Long workCommentId, Long userId, String content) throws ParamsErrorException, UserNotFindException, CommentNotFindException;

    // 弹幕列表
    public List<Barrage> iFindBarrageList(Long workId, Integer type) throws ParamsErrorException, WorkNotFindException;

    // 发送弹幕
    public void insertBarrage(Integer type, Long workId, Long userId, Integer time, String content) throws ParamsErrorException, UserNotFindException;

    public Page<MessageVo> pageMessage(Long userId, Integer type, Integer pageNum, Integer pageSize);
}
