package com.leoman.work.service.impl;

import com.leoman.category.dao.CategoryDao;
import com.leoman.category.entity.Category;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.entity.FileBo;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.utils.FileUtil;
import com.leoman.work.dao.WorkCreatorDao;
import com.leoman.work.dao.WorkDao;
import com.leoman.work.dao.WorkSurroundDao;
import com.leoman.work.dao.WorkVideoDao;
import com.leoman.work.entity.Work;
import com.leoman.work.entity.WorkCreator;
import com.leoman.work.entity.WorkSurround;
import com.leoman.work.entity.WorkVideo;
import com.leoman.work.service.WorkSurroundService;
import com.leoman.work.service.WorkVideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
@Service
public class WorkSurroundServiceImpl extends GenericManagerImpl<WorkSurround, WorkSurroundDao> implements WorkSurroundService {

    @Autowired
    private WorkSurroundDao workSurroundDao;

    @Autowired
    private WorkDao workDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private WorkCreatorDao workCreatorDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public List<WorkSurround> findByWorkId(Long workId) {
        return workSurroundDao.findByWorkId(workId);
    }

    @Override
    @Transactional
    public void saveAll(Long id,
                        String name,
                        Integer seriesCount,
                        Long category,
                        String description,
                        String[] creator,
                        String[] name2,
                        String[] description2,
                        String[] linkUrl,
                        String[] names,
                        String[] descriptions,
                        String[] linkUrls,
                        MultipartRequest multipartRequest) throws IOException {

        Work work = null;

        if (null == id) {
            work = new Work();
            work.setCreateDate(System.currentTimeMillis());
        } else {
            work = workDao.findOne(id);

            // 先删除原有的分类数量
            Category category1 = work.getCategory();
            category1.setCount(category1.getCount() - 1);
            categoryDao.save(category1);
        }

        work.setName(name);
        work.setSeriesCount(seriesCount);

        // 后新增分类数量
        Category category1 = categoryDao.findOne(category);
        category1.setCount(category1.getCount() + 1);
        categoryDao.save(category1);
        work.setCategory(category1);

        work.setDescription(description);
        workDao.save(work);
        MultipartFile multipartFile = multipartRequest.getFile("imageFile");
        if (null != multipartFile) {
            FileBo fileBo = FileUtil.save(multipartFile);
            work.setCover(fileBo.getPath());
        }
        workDao.save(work);

        List<WorkCreator> workCreatorList = workCreatorDao.findWorkId(id);
        for (int i = 0; i < creator.length - 1; i++) {
            WorkCreator workCreator = new WorkCreator();
            workCreator.setWork(work);
            workCreator.setUserInfo(userInfoDao.findOneByNickname(creator[i]));
            workCreator.setPraise(0);
            workCreatorDao.save(workCreator);
        }

        //修改周边
        List<WorkSurround> workSurroundList = workSurroundDao.findByWorkId(work.getId());
        if (workSurroundList.size() > 0) {
            for (int i = 0; i < names.length; i++) {
                if (StringUtils.isNotEmpty(names[i])) {
                    workSurroundList.get(i).setName(names[i]);
                    workSurroundList.get(i).setWork(work);
                    if (StringUtils.isNotEmpty(descriptions[i])) {
                        workSurroundList.get(i).setDescription(descriptions[i]);
                    }
                    if (StringUtils.isNotBlank(linkUrls[i])) {
                        workSurroundList.get(i).setLinkUrl(linkUrls[i]);
                    }
                    MultipartFile file = multipartRequest.getFile("covers" + workSurroundList.get(i).getId());
                    if (null != file) {
                        FileBo fileBo = FileUtil.save(file);
                        workSurroundList.get(i).setCover(fileBo.getPath());
                    }
                    workSurroundDao.save(workSurroundList.get(i));
                }
            }

        }

        //新增周边
        for (int i = 0; i < name2.length; i++) {
            if (StringUtils.isNotEmpty(name2[i])) {
                WorkSurround workSurround = new WorkSurround();
                workSurround.setName(name2[i]);
                workSurround.setWork(work);
                if (StringUtils.isNotEmpty(description2[i])) {
                    workSurround.setDescription(description2[i]);
                }
                if (StringUtils.isNotBlank(linkUrl[i])) {
                    workSurround.setLinkUrl(linkUrl[i]);
                }
                List<MultipartFile> multipartFiles = multipartRequest.getFiles("cover");
                if (multipartFiles.size() > 0) {
                    if (null != multipartFiles.get(i)) {
                        FileBo fileBo = FileUtil.save(multipartFiles.get(i));
                        workSurround.setCover(fileBo.getPath());
                    }
                }
                workSurroundDao.save(workSurround);
            }
        }
    }

}
