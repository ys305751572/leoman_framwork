package com.leoman.report.service;

import com.leoman.common.service.GenericManager;
import com.leoman.feedback.entity.Feedback;
import com.leoman.report.entity.Report;
import com.leoman.resources.entity.StillResource;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public interface ReportService extends GenericManager<Report>{

    public Page<Report> page(String nickname1, String mobile, String nickname2, Integer pageNum, Integer pageSize);

    public List<Report> findListNew();

}
