package com.leoman.userapi.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.creator.dao.CreatorDao;
import com.leoman.creator.dao.CreatorImageDao;
import com.leoman.creator.entity.Creator;
import com.leoman.creator.entity.CreatorImage;
import com.leoman.creatordynamic.dao.DynamicDao;
import com.leoman.creatordynamic.entity.Dynamic;
import com.leoman.entity.Configue;
import com.leoman.entity.FileBo;
import com.leoman.exception.*;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.dao.UserLoginDao;
import com.leoman.user.entity.UserAttention;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserLogin;
import com.leoman.user.entity.vo.FansVo;
import com.leoman.userapi.service.UserAttentionService;
import com.leoman.userapi.service.UserInfoService;
import com.leoman.userapi.service.UserLoginServiceApi;
import com.leoman.utils.DateUtils;
import com.leoman.utils.FileUtil;
import com.leoman.vcode.VcodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class UserLoginServiceApiImpl extends GenericManagerImpl<UserLogin, UserLoginDao> implements UserLoginServiceApi {

    @Autowired
    private UserLoginDao dao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private VcodeService vcodeService;

    @Autowired
    private UserAttentionService userAttentionService;

    @Autowired
    private CreatorDao creatorDao;

    @Autowired
    private CreatorImageDao creatorImageDao;

    @Autowired
    private DynamicDao dynamicDao;

    @Override
    public UserLogin findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public UserLogin findOneById(Long userId) throws UserNotFindException {
        if (null == userId) {
            throw new UserNotFindException();
        }

        UserLogin userLogin = dao.findOne(userId);
        if (null == userLogin) {
            throw new UserNotFindException();
        }

        return userLogin;
    }

    @Override
    public void modifyUserInfo(Long userId, String nickname, Integer gender) throws UserNotFindException {
        UserInfo userInfo = getUserInfo(userId, userInfoDao);

        if (StringUtils.isNotEmpty(nickname)) {
            userInfo.setNickname(nickname);
        }

        if (null != gender) {
            userInfo.setGender(gender);
        }

        userInfo.setAvater(userInfo.getAvater().replaceAll(Configue.getUploadUrl(), ""));

        userInfoService.update(userInfo);
    }

    @Override
    public void modifyPwd(Long userId, String mobile, String oldPwd, String newPwd, String code, Integer type) throws UserNotFindException, CodeErrorException, OldPasswordErrorException, OldNewPasswordException {
        UserLogin userLogin = null;
        UserInfo userInfo = null;

        if (null == type || type == 1) {
            userInfo = getUserInfo(userId, userInfoDao);
            userLogin = userInfo.getUserLogin();

            /*if (!StringUtils.isNotEmpty(code)) {
                throw new CodeErrorException();
            }

            if (!vcodeService.validate(userInfo.getMobile(), code)) {
                throw new CodeErrorException();
            }*/

            if (!userLogin.getPassword().equals(oldPwd)) {
                throw new OldPasswordErrorException();
            }

            if (oldPwd.equals(newPwd)) {
                throw new OldNewPasswordException();
            }
        } else {
            userInfo = userInfoDao.findOneByMobile(mobile);
            if (null == userInfo) {
                throw new UserNotFindException();
            }
            if (!StringUtils.isNotEmpty(code)) {
                throw new CodeErrorException();
            }
            if (!vcodeService.validate(userInfo.getMobile(), code)) {
                throw new CodeErrorException();
            }
            userLogin = userInfo.getUserLogin();
        }

        userLogin.setPassword(newPwd);

        dao.save(userLogin);
    }

    @Override
    public void modifyHead(Long userId, MultipartRequest multipartRequest) throws UserNotFindException, ParamsErrorException {
        UserInfo userInfo = getUserInfo(userId, userInfoDao);

        MultipartFile multipartFile = multipartRequest.getFile("file");
        if (null == multipartFile) {
            throw new ParamsErrorException();
        }

        try {
            FileBo fileBo = FileUtil.save(multipartFile);

            userInfo.setAvater(fileBo.getPath());

            userInfoService.update(userInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void modifyHeadPlus(Long creatorId, MultipartRequest multipartRequest) throws CreatorNotFindException, ParamsErrorException {
        Creator creator = creatorDao.findOne(creatorId);
        if (null == creator) {
            throw new CreatorNotFindException();
        }
        UserInfo userInfo = userInfoDao.findOneByCreatorId(creatorId);

        MultipartFile multipartFile = multipartRequest.getFile("avater");
        List<MultipartFile> stillList = multipartRequest.getFiles("stillList");
        List<MultipartFile> lifeList = multipartRequest.getFiles("lifeList");
        FileBo fileBo = null;
        CreatorImage creatorImage = null;

        try {
            if (null != multipartFile) {
                fileBo = FileUtil.save(multipartFile);
                userInfo.setAvater(fileBo.getPath());
            }

            userInfoService.update(userInfo);

            if (null != stillList && stillList.size() > 0) {
                for (MultipartFile still : stillList) {
                    fileBo = FileUtil.save(still);
                    creatorImage = new CreatorImage();
                    creatorImage.setCreator(creator);
                    creatorImage.setType(0);
                    creatorImage.setImage(fileBo.getPath());

                    creatorImageDao.save(creatorImage);
                }
            }

            if (null != lifeList && lifeList.size() > 0) {
                for (MultipartFile life : lifeList) {
                    fileBo = FileUtil.save(life);
                    creatorImage = new CreatorImage();
                    creatorImage.setCreator(creator);
                    creatorImage.setType(1);
                    creatorImage.setImage(fileBo.getPath());

                    creatorImageDao.save(creatorImage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Page<FansVo> attentionPage(Long userId, Integer pageNum, Integer pageSize) {
        final Page<UserAttention> attentionPage = userAttentionService.iPageByUserId(userId, pageNum, pageSize);
        return getAttentionPage(attentionPage, false);
    }

    private Page<FansVo> getAttentionPage(final Page<UserAttention> attentionPage, final boolean flag) {
        Page<FansVo> page = new Page<FansVo>() {
            @Override
            public int getNumber() {
                return attentionPage.getNumber();
            }

            @Override
            public int getSize() {
                return attentionPage.getSize();
            }

            @Override
            public int getTotalPages() {
                return attentionPage.getTotalPages();
            }

            @Override
            public int getNumberOfElements() {
                return attentionPage.getNumberOfElements();
            }

            @Override
            public long getTotalElements() {
                return attentionPage.getTotalElements();
            }

            @Override
            public boolean hasPreviousPage() {
                return attentionPage.hasPreviousPage();
            }

            @Override
            public boolean isFirstPage() {
                return attentionPage.isFirstPage();
            }

            @Override
            public boolean hasNextPage() {
                return attentionPage.hasNextPage();
            }

            @Override
            public boolean isLastPage() {
                return attentionPage.isLastPage();
            }

            @Override
            public Pageable nextPageable() {
                return attentionPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return attentionPage.previousPageable();
            }

            @Override
            public Iterator<FansVo> iterator() {
                Iterator<FansVo> iterator = new Iterator<FansVo>() {
                    @Override
                    public boolean hasNext() {
                        return attentionPage.iterator().hasNext();
                    }

                    @Override
                    public FansVo next() {
                        return getFansVo(attentionPage.iterator().next(), flag);
                    }
                };

                return iterator;
            }

            @Override
            public List<FansVo> getContent() {
                List<FansVo> list = new ArrayList<FansVo>();
                for (UserAttention userAttention : attentionPage.getContent()) {
                    list.add(getFansVo(userAttention, flag));
                }
                return list;
            }

            @Override
            public boolean hasContent() {
                return attentionPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return attentionPage.getSort();
            }
        };

        return page;
    }

    private FansVo getFansVo(UserAttention userAttention, boolean flag) {
        UserInfo userInfo = userAttention.getSourceUser();
        FansVo fans = new FansVo();
        fans.setUserId(userInfo.getId());
        fans.setNickname(userInfo.getNickname());
        fans.setAvater(Configue.getUploadUrl() + userInfo.getAvater());
        if (flag) {
            fans.setIsAttention(null == userAttentionService.findAttentionInfo(userAttention.getSourceUser().getId(), userAttention.getUser().getId()) ? 1 : 0);
        }
        return fans;
    }

    @Override
    public Page<FansVo> fans(Long userId, Integer pageNum, Integer pageSize) {
        final Page<UserAttention> attentionPage = userAttentionService.iPageFans(userId, pageNum, pageSize);
        return getAttentionPage(attentionPage, true);
    }

    @Override
    public void deleteImage(Long createImageId, Integer type) throws ParamsErrorException {
        if (null == createImageId || null == type) {
            throw new ParamsErrorException();
        }
        CreatorImage creatorImage = creatorImageDao.findOne(createImageId, type);
        if (null != creatorImage) {
            creatorImageDao.delete(creatorImage);
        }
    }

    @Override
    public void insertDynamic(Long creatorId, Long time, Integer status) throws ParamsErrorException, CreatorNotFindException {
        if (null == creatorId || null == time || status == null) {
            throw new ParamsErrorException();
        }

        Creator creator = creatorDao.findOne(creatorId);
        if (null == creator) {
            throw new CreatorNotFindException();
        }

        try {
            Dynamic dynamic = new Dynamic();
            dynamic.setCreator(creator);
            dynamic.setSource(1);
            dynamic.setTime(time);
            dynamic.setStatus(status);
            dynamic.setDate(DateUtils.longToString(time, "yyyy-MM-dd hh:mm:ss").substring(0, 11));

            dynamicDao.save(dynamic);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDynamic(Long id) throws ParamsErrorException {
        Dynamic dynamic = dynamicDao.findOne(id);
        if (null != dynamic) {
            dynamicDao.delete(dynamic);
        }
    }

    public static UserInfo getUserInfo(Long userId, UserInfoDao userInfoDao) throws UserNotFindException {
        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        userInfo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());

        return userInfo;
    }
}
