package com.leoman.floatwin.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.entity.FileBo;
import com.leoman.floatwin.entity.FloatWin;
import com.leoman.floatwin.service.FloatWinService;
import com.leoman.floatwin.service.impl.FloatWinServiceImpl;
import com.leoman.utils.DateUtils;
import com.leoman.utils.FileUtil;
import com.leoman.work.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
@Controller
@RequestMapping(value = "admin/floatWin")
public class FloatWinController extends GenericEntityController<FloatWin, FloatWin, FloatWinServiceImpl> {

    @Autowired
    private FloatWinService floatWinService;

    @Autowired
    private WorkService workService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {

        FloatWin floatWin = floatWinService.getById(1l);
        model.addAttribute("floatWin", floatWin);

        return "floatwin/edit";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(Long id,
                        String title,
                        Integer position,
                        Long categoryId,
                        Long seriesId,
                        String time,
                        @RequestParam(value = "imageFile", required = false) MultipartFile multipartFile) throws ParseException {

        FloatWin floatWin = null;
        if (id == null) {
            floatWin = new FloatWin();
            floatWin.setCreateDate(System.currentTimeMillis());
        }else {
            floatWin = floatWinService.queryByPK(id);
            floatWin.setUpdateDate(System.currentTimeMillis());
        }

        try {
            if (null != multipartFile) {
                FileBo fileBo = FileUtil.save(multipartFile);
                floatWin.setCover(fileBo.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        floatWin.setTitle(title);
        floatWin.setPosition(position);
        floatWin.setCategoryId(categoryId);
        floatWin.setDetailId(seriesId);
        floatWin.setTime(DateUtils.stringToLong(time, "yyyy-MM-dd hh:mm:ss"));

        floatWinService.save(floatWin);
        return 1;
    }

    /**
     * 显示、隐藏
     */
    @RequestMapping(value = "/banned")
    @ResponseBody
    public Integer banned(HttpServletResponse response, Long floatWinId, Integer status) {

        FloatWin floatWin = floatWinService.queryByPK(floatWinId);
        floatWin.setStatus(status);
        floatWinService.update(floatWin);

        return 1;
    }
}
