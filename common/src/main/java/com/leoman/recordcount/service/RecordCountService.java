package com.leoman.recordcount.service;

import com.leoman.common.service.GenericManager;
import com.leoman.recordcount.entity.RecordCount;

/**
 * Created by Administrator on 2016/7/22 0022.
 */
public interface RecordCountService extends GenericManager<RecordCount>{

    // 增加今日访问人数和在线人数
    public void addCount(Integer visitCount, Integer onlineCount);

    // 清空今日访问人数和在线人数
    public void clearCount();
}
