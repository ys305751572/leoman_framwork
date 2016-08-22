package com.leoman.userapi.service;

import com.leoman.common.service.GenericManager;
import com.leoman.exception.*;
import com.leoman.user.entity.UserLogin;
import com.leoman.user.entity.vo.FansVo;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserLoginServiceApi extends GenericManager<UserLogin> {

    public UserLogin findByUsername(String username);

    public UserLogin findOneById(Long userId) throws UserNotFindException;

    public void modifyUserInfo(Long userId, String nickname, Integer gender) throws UserNotFindException;

    public void modifyPwd(Long userId, String mobile, String oldPwd, String newPwd, String code, Integer type) throws UserNotFindException, CodeErrorException, OldPasswordErrorException, OldNewPasswordException;

    public void modifyHead(Long userId, MultipartRequest multipartRequest) throws UserNotFindException, ParamsErrorException;

    public void modifyHeadPlus(Long creatorId, MultipartRequest multipartRequest) throws ParamsErrorException, CreatorNotFindException;

    public Page<FansVo> attentionPage(Long userId, Integer pageNum, Integer pageSize);

    public Page<FansVo> fans(Long userId, Integer pageNum, Integer pageSize);

    public void deleteImage(Long createImageId, Integer type) throws ParamsErrorException;

    public void insertDynamic(Long creatorId, Long time, Integer status) throws ParamsErrorException, CreatorNotFindException;

    public void deleteDynamic(Long id) throws ParamsErrorException;
}
