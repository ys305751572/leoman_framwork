package com.leoman.report.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.feedback.entity.Feedback;
import com.leoman.report.entity.Report;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface ReportDao extends IBaseJpaRepository<Report> {

    @Query("select a from Report a where a.postBase.id = ?1")
    public List<Report> findListByParams(Long postId);

    @Query("select a from Report a where a.createDate > ?1")
    public List<Report> findListNew(Long oldDate);
}
