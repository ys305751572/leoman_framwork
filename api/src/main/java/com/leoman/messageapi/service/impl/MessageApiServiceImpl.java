package com.leoman.messageapi.service.impl;

import com.leoman.entity.Configue;
import com.leoman.entity.Constant;
import com.leoman.exception.UserNotFindException;
import com.leoman.message.dao.MessageDao;
import com.leoman.message.entity.Message;
import com.leoman.messageapi.service.MessageApiService;
import com.leoman.messageapi.vo.PostPraiseUserVo;
import com.leoman.reourceapi.service.impl.ResourceApiServiceImpl;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.entity.UserInfo;
import com.leoman.userchange.dao.UserChangeDao;
import com.leoman.userchange.entity.UserChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class MessageApiServiceImpl implements MessageApiService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserChangeDao userChangeDao;

    @Autowired
    private MessageDao messageDao;

    @Override
    public Page<PostPraiseUserVo> praisePage(Long userId, Integer pageNum, Integer pageSize) {
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        final Page<UserChange> userChangePage = userChangeDao.findListByUserId(userId, Constant.USER_CHANGE_TYPE_001, new PageRequest(info[0] - 1, info[1]));
        Page<PostPraiseUserVo> page = new Page<PostPraiseUserVo>() {
            @Override
            public int getNumber() {
                return userChangePage.getNumber();
            }

            @Override
            public int getSize() {
                return userChangePage.getSize();
            }

            @Override
            public int getTotalPages() {
                return userChangePage.getTotalPages();
            }

            @Override
            public int getNumberOfElements() {
                return userChangePage.getNumberOfElements();
            }

            @Override
            public long getTotalElements() {
                return userChangePage.getTotalElements();
            }

            @Override
            public boolean hasPreviousPage() {
                return userChangePage.hasPreviousPage();
            }

            @Override
            public boolean isFirstPage() {
                return userChangePage.isFirstPage();
            }

            @Override
            public boolean hasNextPage() {
                return userChangePage.hasNextPage();
            }

            @Override
            public boolean isLastPage() {
                return userChangePage.isLastPage();
            }

            @Override
            public Pageable nextPageable() {
                return userChangePage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return userChangePage.previousPageable();
            }

            @Override
            public Iterator<PostPraiseUserVo> iterator() {
                final Iterator<UserChange> userChangeIterator = userChangePage.iterator();
                Iterator<PostPraiseUserVo> iterator = new Iterator<PostPraiseUserVo>() {
                    @Override
                    public boolean hasNext() {
                        return userChangeIterator.hasNext();
                    }

                    @Override
                    public PostPraiseUserVo next() {
                        return getPostPraiseUserVo(userChangeIterator.next());
                    }
                };

                return iterator;
            }

            @Override
            public List<PostPraiseUserVo> getContent() {
                List<UserChange> userChangeList = userChangePage.getContent();
                List<PostPraiseUserVo> list = new ArrayList<PostPraiseUserVo>();

                for (UserChange userChange : userChangeList) {
                    list.add(getPostPraiseUserVo(userChange));
                }

                return list;
            }

            @Override
            public boolean hasContent() {
                return userChangePage.hasContent();
            }

            @Override
            public Sort getSort() {
                return userChangePage.getSort();
            }
        };
        return page;
    }

    private PostPraiseUserVo getPostPraiseUserVo(UserChange userChange) {
        PostPraiseUserVo postPraiseUserVo = new PostPraiseUserVo();
        postPraiseUserVo.setId(userChange.getUserInfo().getId());
        postPraiseUserVo.setNickname(userChange.getUserInfo().getNickname());
        postPraiseUserVo.setAvater(Configue.getUploadUrl() + userChange.getUserInfo().getAvater());
        postPraiseUserVo.setPostName(userChange.getContent());
        postPraiseUserVo.setCreateDate(userChange.getCreateDate());

        return postPraiseUserVo;
    }

    @Override
    public Page<Message> messagePage(Long userId, Integer pageNum, Integer pageSize) throws UserNotFindException {
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);

        if (null == userId) {
            throw new UserNotFindException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);

        if (null == userInfo) {
            throw new UserNotFindException();
        }

        String type = "";

        if (userInfo.getIsCreator() == 0) {
            type = "%1%";
        } else {
            type = "%2%";
        }

        return messageDao.pageByUserType(type, new PageRequest(info[0] - 1, info[1]));
    }
}
