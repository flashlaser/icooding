package com.icooding.cms.web.admin.api.function;

import java.util.List;

import com.icooding.cms.model.Spider;
import com.icooding.cms.service.SpiderService;
import com.icooding.cms.web.base.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class ASpiderCtl {

    @Autowired
    private SpiderService spiderService;
    
    @RequestMapping("/spider")
    public ModelAndView spider(@RequestParam(defaultValue = "1")int curPage){
        ModelAndView mv = new ModelAndView("admin/function/spider/list");
        List<Spider> lists = spiderService.page(curPage, Constants.SPIDER_PAGE_SIZE);
        long count = spiderService.count();
        mv.addObject("count", count);
        mv.addObject("lists", lists);
        mv.addObject("curPage", curPage);
        mv.addObject("pageSize", Constants.SPIDER_PAGE_SIZE);
        return mv;
    }
}
