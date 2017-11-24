package com.icooding.cms.web.admin.api.function;

import com.icooding.cms.dto.Page;
import com.icooding.cms.model.ExceptionLog;
import com.icooding.cms.service.ExceptionLogService;
import com.icooding.cms.service.RegesterCodeService;
import com.icooding.cms.web.base.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/admin/registerCode")
public class ARegisterCodeCtl {

    @Autowired
    private RegesterCodeService regesterCodeService;
    
    @RequestMapping("/list")
    public ModelAndView exception(@RequestParam(defaultValue = "0")int status, @RequestParam(defaultValue = "1")int curPage){
        ModelAndView mv = new ModelAndView("admin/function/register_code/list");
        Page list = regesterCodeService.list(curPage, Constants.EXCEPTION_LOG_PAGE_SIZE);
        mv.addObject("count", list.getCount());
        mv.addObject("lists", list.getRows());
        mv.addObject("curPage", curPage);
        mv.addObject("pageSize", Constants.EXCEPTION_LOG_PAGE_SIZE);
        return mv;
    }

    @RequestMapping("/create")
    public String create(){
        regesterCodeService.createRegesterCode();
        return "redirect:/admin/registerCode/list";
    }

}
