package com.leoman.indexapi.service.Impl;

import com.leoman.adsindex.dao.AdsIndexDao;
import com.leoman.adsindex.entity.AdsIndex;
import com.leoman.adsindex.entity.vo.AdsIndexVo;
import com.leoman.barrage.dao.BarrageDao;
import com.leoman.barrage.entity.Barrage;
import com.leoman.entity.Configue;
import com.leoman.entity.Constant;
import com.leoman.exception.CommentNotFindException;
import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.UserNotFindException;
import com.leoman.exception.WorkNotFindException;
import com.leoman.indexapi.service.WorkService;
import com.leoman.message.entity.vo.MessageVo;
import com.leoman.post.dao.PostCommentDao;
import com.leoman.post.dao.PostImageDao;
import com.leoman.post.entity.PostImage;
import com.leoman.postapi.service.impl.PostServiceImpl;
import com.leoman.reourceapi.service.impl.ResourceApiServiceImpl;
import com.leoman.user.dao.UserCollectDao;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.entity.UserCollect;
import com.leoman.user.entity.UserInfo;
import com.leoman.userapi.service.UserIntegralService;
import com.leoman.utils.DateUtils;
import com.leoman.utils.FileUtil;
import com.leoman.work.dao.*;
import com.leoman.work.entity.*;
import com.leoman.work.entity.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class WorkServiceImplApi implements WorkService {

    @Autowired
    private WorkDao workDao;

    @Autowired
    private WorkVideoDao workVideoDao;

    @Autowired
    private WorkNovelDao workNovelDao;

    @Autowired
    private WorkComicDao workComicDao;

    @Autowired
    private WorkComicImageDao workComicImageDao;

    @Autowired
    private WorkCommentDao workCommentDao;

    @Autowired
    private WorkRecommendDao workRecommendDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private BarrageDao barrageDao;

    @Autowired
    private UserCollectDao userCollectDao;

    @Autowired
    private WorkCreatorDao workCreatorDao;

    @Autowired
    private UserIntegralService userIntegralService;

    @Autowired
    private AdsIndexDao adsIndexDao;

    @Autowired
    private PostCommentDao postCommentDao;

    @Autowired
    private PostImageDao postImageDao;

    @Autowired
    private WorkSurroundDao workSurroundDao;

    @Override
    public List<Work> iFindList(String time) throws ParamsErrorException {
        List<Work> list = new ArrayList<Work>();
        List<WorkRecommend> recommendList = null;

        try {
            if (StringUtils.isNotEmpty(time)) {
                time = DateUtils.longToString(System.currentTimeMillis(), "yyyy-MM-dd");
            }
            String startTime = time + " 00:00:00";
            String endTime = time + " 23:59:59";
            Long startDate = DateUtils.stringToLong(startTime, "yyyy-MM-dd hh:mm:ss");
            Long endDate = DateUtils.stringToLong(endTime, "yyyy-MM-dd hh:mm:ss");
            list = workDao.iFindList(startDate, endDate);

            // list = workDao.iFindList();

            for (Work work : list) {
                recommendList = workRecommendDao.findListByParams(work.getId(), work.getCategory().getType());
                work.setPosition(work.getCategory().getType());
                work.setCover(Configue.getUploadUrl() + work.getCover());
                if (null != recommendList && recommendList.size() > 0) {
                    work.setWorkId(recommendList.get(0).getSourceId());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<AdsIndexVo> iFindList() {
        List<AdsIndex> adsIndexList = adsIndexDao.findAll();
        List<AdsIndexVo> list = new ArrayList<AdsIndexVo>();
        AdsIndexVo adsIndexVo = null;
        List<WorkCreator> workCreatorList = null;
        UserInfo userInfo = null;

        for (AdsIndex adsIndex : adsIndexList) {
            adsIndexVo = new AdsIndexVo();
            adsIndexVo.setId(adsIndex.getId());
            adsIndexVo.setTitle(adsIndex.getTitle());
            adsIndexVo.setSubTitle(adsIndex.getSubTitle());
            adsIndexVo.setCover(Configue.getUploadUrl() + adsIndex.getCover());
            adsIndexVo.setPosition(adsIndex.getPosition());
            adsIndexVo.setWorkId(adsIndex.getWorkId());

            switch (adsIndex.getPosition()) {
                case 0:
                    // 0:帖子
                    // 查询主创回复数和粉丝回复数
                    adsIndexVo.setCreatorBarrageNum(postCommentDao.findListByParams(adsIndex.getWorkId(), Constant.COMMENT_TYPE_DEFAULT, Constant.IF_CREATOR_YES).size());
                    adsIndexVo.setBarrageNum(postCommentDao.findListByParams(adsIndex.getWorkId(), Constant.COMMENT_TYPE_DEFAULT, Constant.IF_CREATOR_NO).size());
                    break;
                case 1:
                    // 1:视频
                    // 查询主创弹幕数和粉丝弹幕数
                    adsIndexVo.setCreatorBarrageNum(workCommentDao.findListByParams(adsIndex.getWorkId(), Constant.COMMENT_TYPE_DEFAULT, Constant.IF_CREATOR_YES, Constant.CATEGORY_TYPE_004).size());
                    adsIndexVo.setBarrageNum(workCommentDao.findListByParams(adsIndex.getWorkId(), Constant.COMMENT_TYPE_DEFAULT, Constant.IF_CREATOR_NO, Constant.CATEGORY_TYPE_004).size());
                    break;
                case 2:
                    // 2:小说
                    // 查询作者头像、名称
                    workCreatorList = workCreatorDao.findWorkId(adsIndex.getWorkId());
                    if (null == workCreatorList || workCreatorList.size() == 0) {
                        adsIndexVo.setAuthorName("");
                        adsIndexVo.setAuthorHead("");
                    } else {
                        userInfo = workCreatorList.get(0).getUserInfo();
                        adsIndexVo.setAuthorName(userInfo.getNickname());
                        adsIndexVo.setAuthorHead(StringUtils.isNotEmpty(userInfo.getAvater()) ? Configue.getUploadUrl() + userInfo.getAvater() : "");
                    }
                    break;
                case 3:
                    // 3:漫画
                    // 查询主创弹幕数和粉丝弹幕数
                    adsIndexVo.setCreatorBarrageNum(workCommentDao.findListByParams(adsIndex.getWorkId(), Constant.COMMENT_TYPE_DEFAULT, Constant.IF_CREATOR_YES, Constant.CATEGORY_TYPE_005).size());
                    adsIndexVo.setBarrageNum(workCommentDao.findListByParams(adsIndex.getWorkId(), Constant.COMMENT_TYPE_DEFAULT, Constant.IF_CREATOR_NO, Constant.CATEGORY_TYPE_005).size());
                    break;
                case 4:
                    // 4:资源
                    // 跳过
                    break;
                case 5:
                    // 5:福利社
                    // 跳过
                    break;
            }

            list.add(adsIndexVo);
        }

        return list;
    }

    @Override
    public List<WorkVideoVo> findVideoList(Long category, Integer type) throws ParamsErrorException, WorkNotFindException {
        List<WorkVideoVo> list = new ArrayList<WorkVideoVo>();
        List<Work> workList = null;
        if (null == type || type == 0) {
            if (null == category) {
                workList = workDao.iFindListAll();
            } else {
                workList = workDao.iFindList(category);
            }
        } else {
            if (null == category) {
                workList = workDao.iFindListHotAll();
            } else {
                workList = workDao.iFindListHot(category);
            }
        }
        WorkVideoVo workVideoVo = null;

        try {
            for (Work work : workList) {
                workVideoVo = getWorkVideoVo(work);

                if (null == workVideoVo) {
                    continue;
                }

                list.add(workVideoVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Page<WorkVideoVo> videoPage(Long userId, Integer pageNum, Integer pageSize) {
        // 视频
        final Page<Work> workPage = workDao.findCollectListByUserIdAndType(userId, Constant.CATEGORY_TYPE_004, Constant.COLLECT_TYPE_001, new PageRequest(pageNum - 1, pageSize));
        return getVideoPage(workPage);
    }

    @Override
    public Page<WorkVideoVo> videoPage(Integer pageNum, Integer pageSize) {
        // 视频
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        final Page<Work> workPage = workDao.findCollectListByType(Constant.CATEGORY_TYPE_004, new PageRequest(info[0] - 1, info[1]));
        return getVideoPage(workPage);
    }

    private Page<WorkVideoVo> getVideoPage(final Page<Work> workPage) {
        Page<WorkVideoVo> page = new Page<WorkVideoVo>() {
            @Override
            public int getNumber() {
                return workPage.getNumber();
            }

            @Override
            public int getSize() {
                return workPage.getSize();
            }

            @Override
            public int getTotalPages() {
                return workPage.getTotalPages();
            }

            @Override
            public int getNumberOfElements() {
                return workPage.getNumberOfElements();
            }

            @Override
            public long getTotalElements() {
                return workPage.getTotalElements();
            }

            @Override
            public boolean hasPreviousPage() {
                return workPage.hasPreviousPage();
            }

            @Override
            public boolean isFirstPage() {
                return workPage.isFirstPage();
            }

            @Override
            public boolean hasNextPage() {
                return workPage.hasNextPage();
            }

            @Override
            public boolean isLastPage() {
                return workPage.isLastPage();
            }

            @Override
            public Pageable nextPageable() {
                return workPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return workPage.previousPageable();
            }

            @Override
            public Iterator<WorkVideoVo> iterator() {
                Iterator<WorkVideoVo> iterator = new Iterator<WorkVideoVo>() {
                    @Override
                    public boolean hasNext() {
                        return workPage.iterator().hasNext();
                    }

                    @Override
                    public WorkVideoVo next() {
                        try {
                            return getWorkVideoVo(workPage.iterator().next());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (WorkNotFindException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };

                return iterator;
            }

            @Override
            public List<WorkVideoVo> getContent() {
                List<WorkVideoVo> list = new ArrayList<WorkVideoVo>();

                try {
                    for (Work work : workPage.getContent()) {
                        list.add(getWorkVideoVo(work));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (WorkNotFindException e) {
                    e.printStackTrace();
                }

                return list;
            }

            @Override
            public boolean hasContent() {
                return workPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return workPage.getSort();
            }
        };

        return page;
    }

    @Override
    public List<WorkComicVo> findComicList(Long category) throws ParamsErrorException, WorkNotFindException {
        List<WorkComicVo> list = new ArrayList<WorkComicVo>();
        List<Work> workList = workDao.iFindList(category);
        WorkComicVo workComicVo = null;

        try {
            for (Work work : workList) {
                workComicVo = getWorkComicVo(work);

                if (null == workComicVo) {
                    continue;
                }

                list.add(workComicVo);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Page<WorkComicVo> comicPage(Long userId, Integer pageNum, Integer pageSize) {
        // 漫画
        final Page<Work> workPage = workDao.findCollectListByUserIdAndType(userId, Constant.CATEGORY_TYPE_006, Constant.COLLECT_TYPE_003, new PageRequest(pageNum - 1, pageSize));
        return getComicPage(workPage);
    }

    @Override
    public Page<WorkComicVo> comicPage(Integer pageNum, Integer pageSize) {
        // 漫画
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        final Page<Work> workPage = workDao.findCollectListByType(Constant.CATEGORY_TYPE_006, new PageRequest(info[0] - 1, info[1]));
        return getComicPage(workPage);
    }

    private Page<WorkComicVo> getComicPage(final Page<Work> workPage) {
        Page<WorkComicVo> page = new Page<WorkComicVo>() {
            @Override
            public int getNumber() {
                return workPage.getNumber();
            }

            @Override
            public int getSize() {
                return workPage.getSize();
            }

            @Override
            public int getTotalPages() {
                return workPage.getTotalPages();
            }

            @Override
            public int getNumberOfElements() {
                return workPage.getNumberOfElements();
            }

            @Override
            public long getTotalElements() {
                return workPage.getTotalElements();
            }

            @Override
            public boolean hasPreviousPage() {
                return workPage.hasPreviousPage();
            }

            @Override
            public boolean isFirstPage() {
                return workPage.isFirstPage();
            }

            @Override
            public boolean hasNextPage() {
                return workPage.hasNextPage();
            }

            @Override
            public boolean isLastPage() {
                return workPage.isLastPage();
            }

            @Override
            public Pageable nextPageable() {
                return workPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return workPage.previousPageable();
            }

            @Override
            public Iterator<WorkComicVo> iterator() {
                Iterator<WorkComicVo> iterator = new Iterator<WorkComicVo>() {
                    @Override
                    public boolean hasNext() {
                        return workPage.iterator().hasNext();
                    }

                    @Override
                    public WorkComicVo next() {
                        try {
                            return getWorkComicVo(workPage.iterator().next());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (WorkNotFindException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };

                return iterator;
            }

            @Override
            public List<WorkComicVo> getContent() {
                List<WorkComicVo> list = new ArrayList<WorkComicVo>();

                try {
                    for (Work work : workPage.getContent()) {
                        list.add(getWorkComicVo(work));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (WorkNotFindException e) {
                    e.printStackTrace();
                }

                return list;
            }

            @Override
            public boolean hasContent() {
                return workPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return workPage.getSort();
            }
        };

        return page;
    }

    @Override
    public List<WorkNovelVo> findNovelList(Long category) throws ParamsErrorException, WorkNotFindException {
        List<WorkNovelVo> list = new ArrayList<WorkNovelVo>();
        List<Work> workList = workDao.iFindList(category);
        WorkNovelVo workNovelVo = null;

        try {
            for (Work work : workList) {
                workNovelVo = getWorkNovelVo(work);

                if (null == workNovelVo) {
                    continue;
                }

                list.add(workNovelVo);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Page<WorkNovelVo> novelPage(Long userId, Integer pageNum, Integer pageSize) {
        // 小说
        final Page<Work> workPage = workDao.findCollectListByUserIdAndType(userId, Constant.CATEGORY_TYPE_005, Constant.COLLECT_TYPE_002, new PageRequest(pageNum - 1, pageSize));
        return getNovelPage(workPage);
    }

    @Override
    public Page<WorkNovelVo> novelPage2(Integer pageNum, Integer pageSize) {
        // 小说
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        final Page<Work> workPage = workDao.findCollectListByType(Constant.CATEGORY_TYPE_005, new PageRequest(info[0] - 1, info[1]));
        return getNovelPage(workPage);
    }

    private Page<WorkNovelVo> getNovelPage(final Page<Work> workPage) {
        Page<WorkNovelVo> page = new Page<WorkNovelVo>() {
            @Override
            public int getNumber() {
                return workPage.getNumber();
            }

            @Override
            public int getSize() {
                return workPage.getSize();
            }

            @Override
            public int getTotalPages() {
                return workPage.getTotalPages();
            }

            @Override
            public int getNumberOfElements() {
                return workPage.getNumberOfElements();
            }

            @Override
            public long getTotalElements() {
                return workPage.getTotalElements();
            }

            @Override
            public boolean hasPreviousPage() {
                return workPage.hasPreviousPage();
            }

            @Override
            public boolean isFirstPage() {
                return workPage.isFirstPage();
            }

            @Override
            public boolean hasNextPage() {
                return workPage.hasNextPage();
            }

            @Override
            public boolean isLastPage() {
                return workPage.isLastPage();
            }

            @Override
            public Pageable nextPageable() {
                return workPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return workPage.previousPageable();
            }

            @Override
            public Iterator<WorkNovelVo> iterator() {
                Iterator<WorkNovelVo> iterator = new Iterator<WorkNovelVo>() {
                    @Override
                    public boolean hasNext() {
                        return workPage.iterator().hasNext();
                    }

                    @Override
                    public WorkNovelVo next() {
                        try {
                            return getWorkNovelVo(workPage.iterator().next());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (WorkNotFindException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };

                return iterator;
            }

            @Override
            public List<WorkNovelVo> getContent() {
                List<WorkNovelVo> list = new ArrayList<WorkNovelVo>();

                try {
                    for (Work work : workPage.getContent()) {
                        list.add(getWorkNovelVo(work));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (WorkNotFindException e) {
                    e.printStackTrace();
                }

                return list;
            }

            @Override
            public boolean hasContent() {
                return workPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return workPage.getSort();
            }
        };

        return page;
    }

    @Override
    public WorkVideoVo findVideo(Long workId, Long userId) throws ParamsErrorException, WorkNotFindException {
        try {
            if (null == workId) {
                throw new ParamsErrorException();
            }

            WorkVideoVo workVideoVo = getWorkVideoVo(getWork(workId));

            if (null == workVideoVo) {
                throw new WorkNotFindException();
            }

            if (null != userId) {
                UserCollect userCollect = userCollectDao.findOneByParams(userId, workId, Constant.COLLECT_TYPE_001);
                workVideoVo.setIsCollect(null == userCollect ? 0 : 1);
            } else {
                workVideoVo.setIsCollect(0);
            }

            // 查询主创列表
            workVideoVo.setCreatorList(getWorkCreatorList(workId));

            // 查询周边推荐列表
            workVideoVo.setSurroundList(getWorkSurroundList(workId));

            return workVideoVo;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (WorkNotFindException e) {
            throw new WorkNotFindException();
        }
        return null;
    }

    @Override
    public WorkComicVo findComic(Long workId, Long userId) throws ParamsErrorException, WorkNotFindException {
        try {
            if (null == workId) {
                throw new ParamsErrorException();
            }

            WorkComicVo workComicVo = getWorkComicVo(getWork(workId));

            if (null == workComicVo) {
                throw new WorkNotFindException();
            }

            // 获取漫画章节详情
            workComicVo.setDetailList(getComicDetail(workId));

            if (null != userId) {
                UserCollect userCollect = userCollectDao.findOneByParams(userId, workId, Constant.COLLECT_TYPE_003);
                workComicVo.setIsCollect(null == userCollect ? 0 : 1);
            } else {
                workComicVo.setIsCollect(0);
            }

            // 查询主创列表
            workComicVo.setCreatorList(getWorkCreatorList(workId));

            // 查询周边推荐列表
            workComicVo.setSurroundList(getWorkSurroundList(workId));

            return workComicVo;
        } catch (WorkNotFindException e) {
            throw new WorkNotFindException();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据漫画id获取对应的章节列表
     *
     * @param workId
     * @return
     */
    private List<WorkComicDetailVo> getComicDetail(Long workId) {
        List<WorkComicDetailVo> list = new ArrayList<WorkComicDetailVo>();
        WorkComicDetailVo workComicDetailVo = null;
        List<WorkComicImage> imageList = null;

        List<WorkComic> workComicList = workComicDao.findByWorkId(workId);

        for (WorkComic workComic : workComicList) {
            workComicDetailVo = new WorkComicDetailVo();
            workComicDetailVo.setId(workComic.getId());
            workComicDetailVo.setName(workComic.getName());
            workComicDetailVo.setSeries(workComic.getSeries());
            workComicDetailVo.setPlayNum(workComic.getPlayNum());
            workComicDetailVo.setBarrageNum(workComic.getBarrageNum());

            imageList = workComicImageDao.findByComicId(workComic.getId());

            for (WorkComicImage workComicImage : imageList) {
                workComicImage.setUrl(Configue.getUploadUrl() + workComicImage.getUrl());
            }

            workComicDetailVo.setImageList(imageList);

            list.add(workComicDetailVo);
        }

        return list;
    }

    @Override
    public WorkNovelVo findNovel(Long workId, Long userId) throws ParamsErrorException, WorkNotFindException {
        try {
            if (null == workId) {
                throw new ParamsErrorException();
            }

            WorkNovelVo workNovelVo = getWorkNovelVo(getWork(workId));

            if (null == workNovelVo) {
                throw new WorkNotFindException();
            }

            // 获取小说内容
            List<WorkNovel> list = workNovelDao.findByWorkId(workId);
            WorkNovelDetailVo workNovelDetailVo = null;
            List<WorkNovelDetailVo> workNovelDetailVoList = new ArrayList<WorkNovelDetailVo>();
            for (WorkNovel workNovel : list) {
                workNovelDetailVo = new WorkNovelDetailVo();
                workNovelDetailVo.setId(workNovel.getId());
                workNovelDetailVo.setName(workNovel.getName());
                workNovelDetailVo.setSeries(workNovel.getSeries());
                workNovelDetailVo.setContent(workNovel.getDetail().replaceAll("&lt", "<").replaceAll("&gt", ">"));
                workNovelDetailVo.setUpdateTime(workNovel.getUpdateDate());

                workNovelDetailVoList.add(workNovelDetailVo);
            }

            workNovelVo.setDetailList(workNovelDetailVoList);

            if (null != userId) {
                UserCollect userCollect = userCollectDao.findOneByParams(userId, workId, Constant.COLLECT_TYPE_002);
                workNovelVo.setIsCollect(null == userCollect ? 0 : 1);
            } else {
                workNovelVo.setIsCollect(0);
            }

            // 查询主创列表
            workNovelVo.setCreatorList(getWorkCreatorList(workId));

            // 查询周边推荐列表
            workNovelVo.setSurroundList(getWorkSurroundList(workId));

            return workNovelVo;
        } catch (WorkNotFindException e) {
            throw new WorkNotFindException();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<WorkSurround> getWorkSurroundList(Long workId) {
        List<WorkSurround> list = workSurroundDao.findByWorkId(workId);

        for (WorkSurround workSurround : list) {
            workSurround.setCover(Configue.getUploadUrl() + workSurround.getCover());
        }

        return list;
    }

    @Override
    public Page<WorkComment> iPageByTypeAndId(final Long workId, final Integer type, Integer pageNum, Integer pageSize) {
        Page<WorkComment> page = workCommentDao.findAll(new Specification<WorkComment>() {
            @Override
            public Predicate toPredicate(Root<WorkComment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (null != workId) {
                    Predicate pre = cb.equal(root.get("work").get("id").as(Long.class), workId);
                    predicateList.add(pre);
                }

                if (null != type) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
                    predicateList.add(pre);
                }

                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }

                if (result != null) {
                    query.where(result);
                }
                return query.getGroupRestriction();
            }

        }, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

        return page;
    }

    @Override
    public Page<WorkCommentVo> iPageByParams(Long workId, Integer type, Integer pageNum, Integer pageSize) {
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        final Page<WorkComment> workCommentPage = workCommentDao.iPage(workId, type, new PageRequest(info[0] - 1, info[1]));
        Page<WorkCommentVo> page = new Page<WorkCommentVo>() {
            @Override
            public int getNumber() {
                return workCommentPage.getNumber();
            }

            @Override
            public int getSize() {
                return workCommentPage.getSize();
            }

            @Override
            public int getTotalPages() {
                return workCommentPage.getTotalPages();
            }

            @Override
            public int getNumberOfElements() {
                return workCommentPage.getNumberOfElements();
            }

            @Override
            public long getTotalElements() {
                return workCommentPage.getTotalElements();
            }

            @Override
            public boolean hasPreviousPage() {
                return workCommentPage.hasPreviousPage();
            }

            @Override
            public boolean isFirstPage() {
                return workCommentPage.isFirstPage();
            }

            @Override
            public boolean hasNextPage() {
                return workCommentPage.hasNextPage();
            }

            @Override
            public boolean isLastPage() {
                return workCommentPage.isLastPage();
            }

            @Override
            public Pageable nextPageable() {
                return workCommentPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return workCommentPage.previousPageable();
            }

            @Override
            public Iterator<WorkCommentVo> iterator() {
                final Iterator<WorkComment> workCommentIterator = workCommentPage.iterator();
                Iterator<WorkCommentVo> iterator = new Iterator<WorkCommentVo>() {
                    @Override
                    public boolean hasNext() {
                        return workCommentIterator.hasNext();
                    }

                    @Override
                    public WorkCommentVo next() {
                        return getWorkCommentVo(workCommentIterator.next());
                    }
                };

                return iterator;
            }

            @Override
            public List<WorkCommentVo> getContent() {
                List<WorkComment> workCommentList = workCommentPage.getContent();
                List<WorkCommentVo> list = new ArrayList<WorkCommentVo>();

                for (WorkComment workComment : workCommentList) {
                    list.add(getWorkCommentVo(workComment));
                }

                return list;
            }

            @Override
            public boolean hasContent() {
                return workCommentPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return workCommentPage.getSort();
            }
        };

        return page;
    }

    @Override
    public void saveWorkComment(MultipartRequest multipartRequest, Long workId, Integer type, Long userId, String content) throws ParamsErrorException, UserNotFindException, WorkNotFindException {
        if (null == workId || null == type || null == userId || !StringUtils.isNotEmpty(content)) {
            throw new ParamsErrorException();
        }

        Work work = workDao.findOne(workId);
        if (null == work) {
            throw new WorkNotFindException();
        }

        if (null == work.getCategory() || work.getCategory().getType() != type) {
            throw new WorkNotFindException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        try {
            WorkComment workComment = new WorkComment();
            workComment.setUserInfo(userInfo);
            workComment.setWork(work);
            workComment.setType(type);
            workComment.setPraise(0);
            workComment.setContent(content);
            workComment.setTypePlus(Constant.COMMENT_TYPE_DEFAULT);

            workCommentDao.save(workComment);

            // 增加评论图片
            addWorkImages(multipartRequest, workComment.getId(), Constant.POST_TYPE_005);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addWorkImages(MultipartRequest multipartRequest, Long workCommentId, Integer type) {
        // 增加评论图片
        PostImage postImage = null;
        List<MultipartFile> multipartFiles = multipartRequest.getFiles("images");
        try {
            for (MultipartFile multipartFile : multipartFiles) {
                postImage = new PostImage();
                postImage.setPostId(workCommentId);
                postImage.setType(type);
                postImage.setUrl(FileUtil.save(multipartFile).getPath());

                postImageDao.save(postImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveWorkReply(MultipartRequest multipartRequest, Long workCommentId, Long userId, String content) throws ParamsErrorException, UserNotFindException, CommentNotFindException {
        if (null == workCommentId || null == userId || !StringUtils.isNotEmpty(content)) {
            throw new ParamsErrorException();
        }

        WorkComment workComment = workCommentDao.findOne(workCommentId);
        if (null == workComment) {
            throw new CommentNotFindException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        try {
            WorkComment tempWorkComment = new WorkComment();
            tempWorkComment.setUserInfo(userInfo);
            tempWorkComment.setToUserId(workComment.getId());
            tempWorkComment.setWork(workComment.getWork());
            tempWorkComment.setType(workComment.getType());
            tempWorkComment.setPraise(0);
            tempWorkComment.setContent(content);
            tempWorkComment.setTypePlus(Constant.COMMENT_TYPE_REPLY);

            workCommentDao.save(tempWorkComment);

            // 增加评论回复图片
            addWorkImages(multipartRequest, tempWorkComment.getId(), Constant.POST_TYPE_006);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Barrage> iFindBarrageList(Long workId, Integer type) throws ParamsErrorException, WorkNotFindException {
        if (null == workId || null == type) {
            throw new ParamsErrorException();
        }

        Work work = workDao.findOne(workId);
        if (null == work) {
            throw new WorkNotFindException();
        }

        return barrageDao.iFindList(workId, type);
    }

    @Override
    @Transactional
    public void insertBarrage(Integer type, Long workId, Long userId, Integer time, String content) throws ParamsErrorException, UserNotFindException {
        if (null == type || null == workId || null == userId || null == time || !StringUtils.isNotEmpty(content)) {
            throw new ParamsErrorException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        Work work = null;

        // 0：视频  1：漫画
        if (type == 0) {
            work = workVideoDao.findOne(workId).getWork();
        } else {
            WorkComic workComic = workComicDao.findOne(workId);
            work = workComic.getWork();

            workComic.setBarrageNum(workComic.getBarrageNum() + 1);
            workComicDao.save(workComic);
        }

        Barrage barrage = new Barrage();
        barrage.setType(type);
        barrage.setWorkId(workId);
        barrage.setUserInfo(userInfo);
        barrage.setPraise(0L);
        barrage.setContent(content);
        barrage.setTime(time);

        barrageDao.save(barrage);

        // 发送弹幕，获得5点经验
        userIntegralService.changeIntegral(userInfo, "发送弹幕获得5个经验值", 5);

        // 根据作品类型增加对应的弹幕数
        work.setBarrageNum(work.getBarrageNum() + 1);
        workDao.save(work);
    }

    @Override
    public Page<MessageVo> pageMessage(Long userId, Integer type, Integer pageNum, Integer pageSize) {
        final Page<WorkComment> workCommentPage = workCommentDao.iPageByUserId(userId, type, new PageRequest(pageNum - 1, pageSize));
        Page<MessageVo> page = new Page<MessageVo>() {
            @Override
            public int getNumber() {
                return workCommentPage.getNumber();
            }

            @Override
            public int getSize() {
                return workCommentPage.getSize();
            }

            @Override
            public int getTotalPages() {
                return workCommentPage.getTotalPages();
            }

            @Override
            public int getNumberOfElements() {
                return workCommentPage.getNumberOfElements();
            }

            @Override
            public long getTotalElements() {
                return workCommentPage.getTotalElements();
            }

            @Override
            public boolean hasPreviousPage() {
                return workCommentPage.hasPreviousPage();
            }

            @Override
            public boolean isFirstPage() {
                return workCommentPage.isFirstPage();
            }

            @Override
            public boolean hasNextPage() {
                return workCommentPage.hasNextPage();
            }

            @Override
            public boolean isLastPage() {
                return workCommentPage.isLastPage();
            }

            @Override
            public Pageable nextPageable() {
                return workCommentPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return workCommentPage.previousPageable();
            }

            @Override
            public Iterator<MessageVo> iterator() {
                final Iterator<WorkComment> workCommentIterator = workCommentPage.iterator();

                Iterator<MessageVo> iterator = new Iterator<MessageVo>() {
                    @Override
                    public boolean hasNext() {
                        return workCommentIterator.hasNext();
                    }

                    @Override
                    public MessageVo next() {
                        return getMessageVo(workCommentIterator.next());
                    }
                };

                return iterator;
            }

            @Override
            public List<MessageVo> getContent() {
                List<WorkComment> workCommentList = workCommentPage.getContent();
                List<MessageVo> list = new ArrayList<MessageVo>();

                for (WorkComment workComment : workCommentList) {
                    list.add(getMessageVo(workComment));
                }

                return list;
            }

            @Override
            public boolean hasContent() {
                return workCommentPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return workCommentPage.getSort();
            }
        };

        return page;
    }

    private MessageVo getMessageVo(WorkComment workComment) {
        MessageVo messageVo = new MessageVo();
        UserInfo userInfo = new UserInfo();

        if (null != workComment.getToUserId()) {
            userInfo = userInfoDao.findOne(workComment.getToUserId());
        }

        messageVo.setId(workComment.getId());
        messageVo.setUserId(workComment.getUserInfo().getId());
        messageVo.setName(workComment.getUserInfo().getNickname());
        messageVo.setAvater(Configue.getUploadUrl() + workComment.getUserInfo().getAvater());
        messageVo.setContent(workComment.getContent());
        messageVo.setSourceId(null == workComment.getToUserId() ? workComment.getWork().getId() : workComment.getToUserId());
        messageVo.setSourceType(workComment.getType());
        messageVo.setSourceName(null == workComment.getToUserId() ? workComment.getWork().getName() : userInfo.getNickname());
        messageVo.setSourceAvater(null == workComment.getToUserId() ? Configue.getUploadUrl() + workComment.getWork().getCover() : userInfo.getAvater());
        if (null == workComment.getToUserId()) {
            switch (workComment.getType()) {
                case 4:
                    messageVo.setSourceAuthor("");
                    break;
                case 5:
                    messageVo.setSourceAuthor(workComicDao.findOne(workComment.getWork().getId()).getAuthor());
                    break;
                case 6:
                    messageVo.setSourceAuthor(workNovelDao.findOne(workComment.getWork().getId()).getAuthor());
                    break;
            }
        } else {
            messageVo.setSourceAuthor("");
        }
        messageVo.setCreateDate(workComment.getCreateDate());

        return messageVo;
    }

    private Work getWork(Long workId) throws WorkNotFindException {
        if (null == workId) {
            throw new WorkNotFindException();
        }

        Work work = workDao.findOne(workId);
        if (null == work) {
            throw new WorkNotFindException();
        }

        return work;
    }

    private WorkVideoVo getWorkVideoVo(Work work) throws ParseException, WorkNotFindException {
        if (null == work || work.getCategory().getType() != Constant.CATEGORY_TYPE_004) {
            throw new WorkNotFindException();
        }

        List<WorkVideo> tempList = workVideoDao.findByWorkId(work.getId());

        WorkVideo workVideo = null;

        if (null != tempList && tempList.size() > 0) {
            workVideo = tempList.get(tempList.size() - 1);
        }

        WorkVideoVo workVideoVo = new WorkVideoVo();
        workVideoVo.setId(work.getId());

        if (null != workVideo) {
            workVideoVo.setName(workVideo.getName());
            workVideoVo.setSeries(workVideo.getSeries());
            workVideoVo.setLinkUrl(workVideo.getLinkUrl());
        } else {
            workVideoVo.setName("");
            workVideoVo.setSeries(0);
            workVideoVo.setLinkUrl("");
        }

        workVideoVo.setCover(work.getCover());
        workVideoVo.setPlayNum(work.getPlayNum());
        workVideoVo.setBarrageNum(work.getBarrageNum());
        workVideoVo.setIsEnd(work.getIsEnd());

        return workVideoVo;
    }

    private WorkComicVo getWorkComicVo(Work work) throws ParseException, WorkNotFindException {
        if (null == work || work.getCategory().getType() != Constant.CATEGORY_TYPE_006) {
            throw new WorkNotFindException();
        }

        List<WorkComic> tempList = workComicDao.findByWorkId(work.getId());

        WorkComic workComic = null;

        if (null != tempList && tempList.size() > 0) {
            workComic = tempList.get(tempList.size() - 1);
        }

        WorkComicVo workComicVo = new WorkComicVo();
        workComicVo.setId(work.getId());
        workComicVo.setDescription(work.getDescription());
        if (null == workComic) {
            workComicVo.setName("");
            workComicVo.setAuthor("");
            workComicVo.setSeries(0);
            workComicVo.setUpdateTime(null);
        } else {
            workComicVo.setName(workComic.getName());
            workComicVo.setAuthor(workComic.getAuthor());
            workComicVo.setSeries(workComic.getSeries());
            workComicVo.setUpdateTime(workComic.getUpdateDate());
        }

        workComicVo.setCover(Configue.getUploadUrl() + work.getCover());
        workComicVo.setPlayNum(work.getPlayNum());
        workComicVo.setBarrageNum(work.getBarrageNum());
        workComicVo.setIsEnd(work.getIsEnd());

        return workComicVo;
    }

    private WorkNovelVo getWorkNovelVo(Work work) throws ParseException, WorkNotFindException {
        if (null == work || work.getCategory().getType() != Constant.CATEGORY_TYPE_005) {
            throw new WorkNotFindException();
        }

        List<WorkNovel> tempList = workNovelDao.findByWorkId(work.getId());

        WorkNovel workNovel = null;

        if (null != tempList && tempList.size() > 0) {
            workNovel = tempList.get(tempList.size() - 1);
        }

        WorkNovelVo workNovelVo = new WorkNovelVo();
        workNovelVo.setId(work.getId());
        workNovelVo.setName(work.getName());
        workNovelVo.setCover(Configue.getUploadUrl() + work.getCover());
        workNovelVo.setAuthor(null == workNovel ? "" : workNovel.getAuthor());
        workNovelVo.setSeries(work.getSeriesCount());
        workNovelVo.setPlayNum(work.getPlayNum());
        workNovelVo.setDescription(work.getDescription());
        workNovelVo.setUpdateTime(work.getUpdateDate());
        workNovelVo.setIsEnd(work.getIsEnd());

        return workNovelVo;
    }

    /**
     * 拼接作品评论VO类
     *
     * @param workComment
     * @return
     */
    private WorkCommentVo getWorkCommentVo(WorkComment workComment) {
        UserInfo userInfo = workComment.getUserInfo();
        WorkCommentVo workCommentVo = new WorkCommentVo();
        workCommentVo.setId(workComment.getId());
        workCommentVo.setUserId(userInfo.getId());
        workCommentVo.setNickname(userInfo.getNickname());
        workCommentVo.setLevel(userInfo.getLevel());
        workCommentVo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());
        workCommentVo.setContent(workComment.getContent());
        workCommentVo.setCommentImageList(PostServiceImpl.getPostImageVo(postImageDao.findList(workComment.getId(), Constant.POST_TYPE_005)));
        workCommentVo.setPraise(workComment.getPraise());
        workCommentVo.setCreateDate(workComment.getCreateDate());
        if (null != workComment.getToUserId()) {
            workCommentVo.setReplyName(userInfoDao.findOne(workComment.getToUserId()).getNickname());
            workCommentVo.setReplyContent(workComment.getContent());
            workCommentVo.setReplyImageList(PostServiceImpl.getPostImageVo(postImageDao.findList(workComment.getToUserId(), Constant.POST_TYPE_006)));
        }

        return workCommentVo;
    }

    /**
     * 获取主创列表
     *
     * @param workId
     * @return
     */
    private List<WorkCreatorVo> getWorkCreatorList(Long workId) {
        List<WorkCreatorVo> list = new ArrayList<WorkCreatorVo>();
        List<WorkCreator> creatorList = workCreatorDao.findWorkIdDesc(workId);
        WorkCreatorVo workCreatorVo = null;
        UserInfo userInfo = null;

        for (WorkCreator workCreator : creatorList) {
            userInfo = workCreator.getUserInfo();
            workCreatorVo = new WorkCreatorVo();
            workCreatorVo.setId(userInfo.getCreator().getId());
            workCreatorVo.setName(userInfo.getNickname());
            workCreatorVo.setAvater(StringUtils.isNotEmpty(userInfo.getAvater()) ? Configue.getUploadUrl() + userInfo.getAvater() : "");
            workCreatorVo.setPraise(workCreator.getPraise());
            workCreatorVo.setCoin(workCreator.getUserInfo().getCoin());

            list.add(workCreatorVo);
        }

        return list;
    }
}
