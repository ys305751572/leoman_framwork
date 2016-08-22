package com.leoman.sensitivewords.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.utils.InitialUtils;
import com.leoman.sensitivewords.dao.SensitiveWordsDao;
import com.leoman.sensitivewords.entity.SensitiveWords;
import com.leoman.sensitivewords.service.SensitiveWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
@Controller
@RequestMapping(value = "admin/sensitiveWords")
public class SensitiveWordsController extends GenericEntityController<SensitiveWords, SensitiveWords, SensitiveWordsDao>{

    @Autowired
    private SensitiveWordsService sensitiveWordsService;

    /**
     * 查询所有的敏感词
     */
    @RequestMapping(value = "/detail")
    public String detail(Model model) {

        /*for (char i=65; i <= 91; i ++) {
            List<SensitiveWords> list = sensitiveWordsService.findByCode(i);
            model.addAttribute("a", list);
        }*/
        List<SensitiveWords> a = sensitiveWordsService.findByCode("A");
        model.addAttribute("a", a);

        List<SensitiveWords> b = sensitiveWordsService.findByCode("B");
        model.addAttribute("b", b);

        List<SensitiveWords> c = sensitiveWordsService.findByCode("C");
        model.addAttribute("c", c);

        List<SensitiveWords> d = sensitiveWordsService.findByCode("D");
        model.addAttribute("d", d);

        List<SensitiveWords> e = sensitiveWordsService.findByCode("E");
        model.addAttribute("e", e);

        List<SensitiveWords> f = sensitiveWordsService.findByCode("F");
        model.addAttribute("f", f);

        List<SensitiveWords> g = sensitiveWordsService.findByCode("G");
        model.addAttribute("g", g);

        List<SensitiveWords> h = sensitiveWordsService.findByCode("H");
        model.addAttribute("h", h);

        List<SensitiveWords> i = sensitiveWordsService.findByCode("I");
        model.addAttribute("i", i);

        List<SensitiveWords> j = sensitiveWordsService.findByCode("J");
        model.addAttribute("j", j);

        List<SensitiveWords> k = sensitiveWordsService.findByCode("K");
        model.addAttribute("k", k);

        List<SensitiveWords> l = sensitiveWordsService.findByCode("L");
        model.addAttribute("l", l);

        List<SensitiveWords> m = sensitiveWordsService.findByCode("M");
        model.addAttribute("m", m);

        List<SensitiveWords> n = sensitiveWordsService.findByCode("N");
        model.addAttribute("n", n);

        List<SensitiveWords> o = sensitiveWordsService.findByCode("O");
        model.addAttribute("o", o);

        List<SensitiveWords> p = sensitiveWordsService.findByCode("P");
        model.addAttribute("p", p);

        List<SensitiveWords> q = sensitiveWordsService.findByCode("Q");
        model.addAttribute("q", q);

        List<SensitiveWords> r = sensitiveWordsService.findByCode("R");
        model.addAttribute("r", r);

        List<SensitiveWords> s = sensitiveWordsService.findByCode("S");
        model.addAttribute("s", s);

        List<SensitiveWords> t = sensitiveWordsService.findByCode("T");
        model.addAttribute("t", t);

        List<SensitiveWords> u = sensitiveWordsService.findByCode("U");
        model.addAttribute("u", u);

        List<SensitiveWords> v = sensitiveWordsService.findByCode("V");
        model.addAttribute("v", v);

        List<SensitiveWords> w = sensitiveWordsService.findByCode("W");
        model.addAttribute("w", w);

        List<SensitiveWords> x = sensitiveWordsService.findByCode("X");
        model.addAttribute("x", x);

        List<SensitiveWords> y = sensitiveWordsService.findByCode("Y");
        model.addAttribute("y", y);

        List<SensitiveWords> z = sensitiveWordsService.findByCode("Z");
        model.addAttribute("z", z);

        /*List<ABCVo> list = new ArrayList<ABCVo>();
        for (int i1 = 1; i1 < 27; i1 ++) {
            ABCVo abcVo = new ABCVo();
            abcVo.setId(i1);
            abcVo.setName((char) (i1 + 48));
            list.add(abcVo);
        }
        model.addAttribute("list", list);*/

        return "sensitivewords/detail";
    }

    /**
     * 保存敏感词
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    private Integer save(HttpServletRequest request, String words) throws UnsupportedEncodingException {

        /*String words = request.getParameter("words");
        String word = new String(words.getBytes("ISO-8859-1"), "UTF-8");*/

        List<SensitiveWords> list = sensitiveWordsService.queryAll();
        for (SensitiveWords sensitiveWords : list) {
            if (words.equals(sensitiveWords.getWords())) {
                return 2;
            }
        }
        SensitiveWords sensitiveWords = new SensitiveWords();
        sensitiveWords.setWords(words);
        sensitiveWords.setCode(InitialUtils.getInitial(words));
        sensitiveWords.setCreateDate(System.currentTimeMillis());
        sensitiveWordsService.save(sensitiveWords);

        return 1;
    }

    /**
     * 删除敏感词
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    private Integer delete(Long id) {

        SensitiveWords sensitiveWords = sensitiveWordsService.queryByPK(id);
        sensitiveWordsService.delete(sensitiveWords);
        return 1;
    }

}
