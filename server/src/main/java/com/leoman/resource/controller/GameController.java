package com.leoman.resource.controller;

import com.leoman.category.entity.Category;
import com.leoman.category.service.CategoryService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.resource.service.GameService;
import com.leoman.resource.service.MusicService;
import com.leoman.resource.service.impl.StillServiceImpl;
import com.leoman.resources.entity.GameResource;
import com.leoman.resources.entity.MusicResource;
import com.leoman.resources.entity.StillResource;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
@Controller
@RequestMapping(value = "admin/game")
public class GameController extends GenericEntityController<StillResource, StillResource, StillServiceImpl> {

    @Autowired
    private GameService gameService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "resource/listGame";
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

            Page<GameResource> page = gameService.page(pageNum, length);

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

        return "resource/add";
    }

    /**
     * 编辑
     *
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Long gameId, Model model) {

        GameResource gameResource = gameService.queryByPK(gameId);
        model.addAttribute(gameResource);

        return "resource/add";
    }

    /**
     * 详情
     *
     * @return
     */
    @RequestMapping("/detail")
    public String detail(Long gameId, Model model) {

        GameResource gameResource = gameService.queryByPK(gameId);
        model.addAttribute(gameResource);

        return "resource/detail";
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Long gameId) {

        GameResource gameResource = gameService.queryByPK(gameId);
        gameService.delete(gameResource);

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
            GameResource gameResource = gameService.queryByPK(id);
            gameService.delete(gameResource);
        }
        return 1;
    }

    /**
     * 保存
     *
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(Long gameId,
                        String name,
                        String version,
                        Integer size,
                        String linkUrl,
                        String description,
                        @RequestParam(value = "imageFile",required = false) MultipartFile file) throws IOException {

        try {
            GameResource gameResource = null;
            if (gameId == null) {
                gameResource = new GameResource();
                gameResource.setCreateDate(System.currentTimeMillis());
            }else {
                gameResource = gameService.queryByPK(gameId);
            }

            gameResource.setName(name);
            gameResource.setVersion(version);
            gameResource.setSize(size);
            gameResource.setLinkUrl(linkUrl);
            gameResource.setDescription(description);
            gameResource.setUpdateDate(System.currentTimeMillis());

            MultipartFile multipartFile = file;
            if (null != multipartFile) {
                FileBo fileBo = FileUtil.save(multipartFile);
                gameResource.setUrl(fileBo.getPath());
            }

            gameService.save(gameResource);
            gameService.update(gameResource);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
