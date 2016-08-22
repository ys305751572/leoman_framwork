package com.leoman.utils.timer;

import com.leoman.recordcount.service.RecordCountService;
import com.leoman.utils.BeanUtils;

/**
 * Created by 涂奕恒 on 2015/11/17.
 */
public class SyncJob {

    public SyncJob() {
        System.out.println("--------------同步任务初始化-----------------");
    }

    /**
     * 调用方法
     */
    public void execute() {
        System.out.println("--------------定时任务开始-----------------");

        try {
            RecordCountService recordCountService = (RecordCountService) BeanUtils.getBean("recordCountServiceImpl");

            // 清空在线人数和今日访问人数
            recordCountService.clearCount();

            System.out.println("--------------清空在线人数和今日访问人数-----------------");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("--------------定时任务结束-----------------");
    }

}
