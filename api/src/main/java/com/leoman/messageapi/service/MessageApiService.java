package com.leoman.messageapi.service;

import com.leoman.exception.UserNotFindException;
import com.leoman.message.entity.Message;
import com.leoman.message.entity.vo.MessageVo;
import com.leoman.messageapi.vo.PostPraiseUserVo;
import com.leoman.work.entity.WorkComment;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface MessageApiService {

    // 点赞我的帖子的用户列表
    public Page<PostPraiseUserVo> praisePage(Long userId, Integer pageNum, Integer pageSize);

    // 通知消息列表
    public Page<Message> messagePage(Long userId, Integer pageNum, Integer pageSize) throws UserNotFindException;
}
