package com.leoman.messageapi.service.impl;

import com.leoman.entity.Configue;
import com.leoman.exception.SendMessageErrorException;
import com.leoman.messageapi.service.PrivateLetterApiService;
import com.leoman.privateletter.dao.PrivateLetterDao;
import com.leoman.privateletter.entity.PrivateLetter;
import com.leoman.reourceapi.service.impl.ResourceApiServiceImpl;
import com.leoman.user.dao.UserAttentionDao;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.entity.UserAttention;
import com.leoman.user.entity.vo.FansVo;
import com.leoman.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class PrivateLetterApiServiceImpl implements PrivateLetterApiService {

    @Autowired
    private PrivateLetterDao privateLetterDao;

    @Autowired
    private UserAttentionDao userAttentionDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public Page<PrivateLetter> letterPage(Long userId, Integer pageNum, Integer pageSize) {
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        Page<PrivateLetter> page = privateLetterDao.pageAboutMe(userId, new PageRequest(info[0] - 1, info[1]));
        FansVo fansVo = null;

        for (PrivateLetter privateLetter : page.getContent()) {
            fansVo = new FansVo();
            fansVo.setUserId(privateLetter.getUser().getId());
            fansVo.setNickname(privateLetter.getUser().getNickname());
            fansVo.setAvater(Configue.getUploadUrl() + privateLetter.getUser().getAvater());
            privateLetter.setFromUserInfo(fansVo);

            fansVo = new FansVo();
            fansVo.setUserId(privateLetter.getToUser().getId());
            fansVo.setNickname(privateLetter.getToUser().getNickname());
            fansVo.setAvater(Configue.getUploadUrl() + privateLetter.getToUser().getAvater());
            privateLetter.setToUserInfo(fansVo);
        }

        return page;
    }

    @Override
    public void sendLetter(Long userId, Long toUserId, String content, MultipartRequest multipartRequest) throws SendMessageErrorException {
        // 检测双方之间是否互相关注过，如果没有，则不让发送私信
        Boolean flag = false;

        UserAttention userAttention1 = userAttentionDao.findOneInfo(userId, toUserId);
        UserAttention userAttention2 = userAttentionDao.findOneInfo(toUserId, userId);

        MultipartFile voice = multipartRequest.getFile("voice");
        MultipartFile image = multipartRequest.getFile("image");
        if (null != voice && voice.getSize() == 0) {
            voice = null;
        }
        if (null != image && image.getSize() == 0) {
            image = null;
        }

        if (null != userAttention1 && null != userAttention2 && userAttention1.getStatus() == 0 && userAttention2.getStatus() == 0) {
            flag = true;
        }

        if ((null != voice || null != image) && !flag) {
            throw new SendMessageErrorException();
        }

        try {
            PrivateLetter privateLetter = new PrivateLetter();
            privateLetter.setUser(userInfoDao.findOne(userId));
            privateLetter.setToUser(userInfoDao.findOne(toUserId));
            privateLetter.setContent(content);
            if (null != voice) {
                privateLetter.setVoice(FileUtil.save(voice).getPath());
            }
            if (null != image) {
                privateLetter.setImage(FileUtil.save(image).getPath());
            }

            privateLetterDao.save(privateLetter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
