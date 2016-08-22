package com.leoman.messageapi.service;

import com.leoman.exception.SendMessageErrorException;
import com.leoman.privateletter.entity.PrivateLetter;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface PrivateLetterApiService {

    // 我的私信列表（分页）
    public Page<PrivateLetter> letterPage(Long userId, Integer pageNum, Integer pageSize);

    // 发送私信
    public void sendLetter(Long userId, Long toUserId, String content, MultipartRequest multipartRequest) throws SendMessageErrorException;
}
