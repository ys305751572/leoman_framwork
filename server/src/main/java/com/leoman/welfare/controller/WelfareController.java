package com.leoman.welfare.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import com.leoman.welfare.service.impl.WelfareServiceImpl;
import com.leoman.welfare.entity.Welfare;
import com.leoman.welfare.service.WelfareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
@Controller
@RequestMapping(value = "admin/welfare")
public class WelfareController extends GenericEntityController<Welfare, Welfare, WelfareServiceImpl>{

    @Autowired
    private WelfareService welfareService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "welfare/list";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<Welfare> page = welfareService.page(pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    public String add() {

        return "welfare/add";
    }

    /**
     * 编辑
     *
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Long welfareId, Model model) {

        Welfare welfare = welfareService.queryByPK(welfareId);
        model.addAttribute(welfare);

        if (welfare.getType() == 0) {

            return "welfare/editMusic";
        }else if (welfare.getType() == 1) {

            return "welfare/editExperience";
        }else if (welfare.getType() == 2) {

            return "welfare/editEntity";
        }else if (welfare.getType() == 3) {

            return "welfare/editEmotion";
        }else if (welfare.getType() == 4) {

            return "welfare/editStore";
        }else if (welfare.getType() == 5) {

            return "welfare/editGame";
        }
        return null;
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Long welfareId) {

        Welfare welfare = welfareService.queryByPK(welfareId);
        welfareService.delete(welfare);

        return 1;
    }

    /**
     * 批量删除
     *
     * @return
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteBatch(String ids) {
        Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
        for (Long id : arrayId) {
            Welfare welfare = welfareService.queryByPK(id);
            welfareService.delete(welfare);
        }
        return 1;
    }

    /**
     * 详情
     *
     * @return
     */
    @RequestMapping("/detail")
    public String detail(Long welfareId, Model model) {

        Welfare welfare = welfareService.queryByPK(welfareId);
        model.addAttribute(welfare);

        if (welfare.getType() == 0) {

            return "welfare/detailMusic";
        }else if (welfare.getType() == 1) {

            return "welfare/detailExperience";
        }else if (welfare.getType() == 2) {

            return "welfare/detailEntity";
        }else if (welfare.getType() == 3) {

            return "welfare/detailEmotion";
        }else if (welfare.getType() == 4) {

            return "welfare/detailStore";
        }else if (welfare.getType() == 5) {

            return "welfare/detailGame";
        }
        return null;
    }
    /**
     * 保存
     *
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(Long welfareId,
                        String title,
                        String subtitle,
                        Integer type,
                        Integer coin,
                        String length,
                        Integer coin1,
                        Integer experience,
                        String description1,
                        Integer coin2,
                        String description2,
                        Integer limit2,
                        Integer coin3,
                        String description3,
                        Integer limit3,
                        String url4,
                        Integer coin5,
                        String url5,
                        Integer limit5,
                        MultipartRequest multipartRequest) throws IOException {

        Welfare welfare = null;

        if (welfareId == null) {
            welfare = new Welfare();
            welfare.setCreateDate(System.currentTimeMillis());
        }else {
            welfare = welfareService.queryByPK(welfareId);
        }

        MultipartFile multipartFile = multipartRequest.getFile("cover");
        if (null != multipartFile) {
            FileBo fileBo = FileUtil.save(multipartFile);
            welfare.setCover(fileBo.getPath());
        }

        welfare.setTitle(title);
        welfare.setSubTitle(subtitle);
        welfare.setType(type);
        welfare.setUpdateDate(System.currentTimeMillis());

        if (type == 0) {

            MultipartFile multipartFile1 = multipartRequest.getFile("url");
            if (null != multipartFile1) {
                FileBo fileBo = FileUtil.save(multipartFile1);
                welfare.setUrl(fileBo.getPath());
            }
            welfare.setCoin(coin);
            welfare.setLength(length);

        }else if (type == 1) {

            welfare.setCoin(coin1);
            welfare.setExper(experience);
            welfare.setDescription(description1);

        }else if (type == 2) {

            welfare.setCoin(coin2);
            welfare.setDescription(description2);
            welfare.setConditions(limit2);

        }else if (type == 3) {

            MultipartFile multipartFile2 = multipartRequest.getFile("url3");
            if (null != multipartFile2) {
                FileBo fileBo = FileUtil.save(multipartFile2);
                welfare.setUrl(fileBo.getPath());
            }
            welfare.setCoin(coin3);
            welfare.setDescription(description3);
            welfare.setConditions(limit3);

        }else if (type == 4) {

            welfare.setUrl(url4);

        }else if (type == 5) {

            welfare.setCoin(coin5);
            welfare.setUrl(url5);
            welfare.setConditions(limit5);

        }

        welfareService.save(welfare);
        welfareService.update(welfare);
        return 1;
    }
}
