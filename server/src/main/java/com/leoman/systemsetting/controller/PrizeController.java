package com.leoman.systemsetting.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.systemsetting.entity.LimitWords;
import com.leoman.systemsetting.entity.Task;
import com.leoman.systemsetting.service.LimitWordsService;
import com.leoman.systemsetting.service.TaskService;
import com.leoman.systemsetting.service.impl.PrizeServiceImpl;
import com.leoman.systemsetting.entity.Prize;
import com.leoman.systemsetting.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/6/30 0030.
 */
@Controller
@RequestMapping(value = "admin/prize")
public class PrizeController extends GenericEntityController<Prize, Prize, PrizeServiceImpl> {

    @Autowired
    private PrizeService prizeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private LimitWordsService limitWordsService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    private String index(Model model) {

        List<Prize> prizeList = prizeService.queryAll();

        Prize prize1 = prizeList.get(0);
        Prize prize2 = prizeList.get(1);
        Prize prize3 = prizeList.get(2);
        Prize prize4 = prizeList.get(3);
        Prize prize5 = prizeList.get(4);
        Prize prize6 = prizeList.get(5);
        Prize prize7 = prizeList.get(6);
        Prize prize8 = prizeList.get(7);

        model.addAttribute("prize1",prize1);
        model.addAttribute("prize2",prize2);
        model.addAttribute("prize3",prize3);
        model.addAttribute("prize4",prize4);
        model.addAttribute("prize5",prize5);
        model.addAttribute("prize6",prize6);
        model.addAttribute("prize7",prize7);
        model.addAttribute("prize8",prize8);

        //List<Task> taskList = taskService.queryAll();
        List<Task> taskList = taskService.findAll();
        model.addAttribute("taskList",taskList);

        //LimitWords limitWords = limitWordsService.queryByPK(1l);
        LimitWords limitWords = limitWordsService.getById(1l);
        model.addAttribute("limitWords", limitWords);

        return "systemsetting/list";
    }

    /**
     * 保存抽奖设置
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    private Integer save(Double name1,
                         Double name2,
                         Double name3,
                         Double name4,
                         Double name5,
                         Double name6,
                         Double name7,
                         Double name8) {

        /*Prize prize1 = prizeService.findByCoin(0);
        prize1.setPro(name1);

        Prize prize2 = prizeService.findByCoin(10);
        prize2.setPro(name2);

        Prize prize3 = prizeService.findByCoin(19);
        prize3.setPro(name3);

        Prize prize4 = prizeService.findByCoin(29);
        prize4.setPro(name4);

        Prize prize5 = prizeService.findByCoin(99);
        prize5.setPro(name5);

        Prize prize6 = prizeService.findByCoin(199);
        prize6.setPro(name6);

        Prize prize7 = prizeService.findByCoin(499);
        prize7.setPro(name7);

        Prize prize8 = prizeService.findByCoin(999);
        prize8.setPro(name8);

        prizeService.save(prize1);
        prizeService.save(prize2);
        prizeService.save(prize3);
        prizeService.save(prize4);
        prizeService.save(prize5);
        prizeService.save(prize6);
        prizeService.save(prize7);
        prizeService.save(prize8);*/

        return 1;
    }

    /**
     * 保存日常任务设置
     */
    @RequestMapping(value = "/saveTask")
    @ResponseBody
    public Integer saveTask(Integer[] type,
                            Integer[] num,
                            Integer[] coin,
                            Integer[] type1,
                            Integer[] num1,
                            Integer[] coin1) {

        //修改任务
        List<Task> taskList = taskService.findAll();
        if (taskList.size() > 0) {
            for (int i = 0; i < type.length; i++) {
                if (type[i] != null) {
                    taskList.get(i).setType(type[i]);
                    if (num[i] != null) {
                        taskList.get(i).setTaskCount(num[i]);
                    }
                    if (coin[i] != null) {
                        taskList.get(i).setCoin(coin[i]);
                    }
                    taskService.save(taskList.get(i));
                }
            }
        }

        //新增任务
        for (int i = 0; i < type1.length; i++) {
            if (type1[i] != null) {
                Task task = new Task();
                task.setType(type1[i]);
                if (num1[i] != null) {
                    task.setTaskCount(num1[i]);
                }
                if (coin1[i] != null) {
                    task.setCoin(coin1[i]);
                }
                taskService.update(task);
            }
        }

        return 1;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Integer delete(Long id) {

        Task task = taskService.queryByPK(id);
        taskService.delete(task);

        return 1;
    }

    /**
     * 保存前端用户发帖字数
     */
    @RequestMapping(value = "/saveLimit")
    @ResponseBody
    public Integer saveLimit(Integer wordNum) {

        //LimitWords limitWords = limitWordsService.queryByPK(1l);
        LimitWords limitWords = limitWordsService.getById(1l);
        limitWords.setNum(wordNum);
        limitWordsService.update(limitWords);
        return 1;
    }
}
