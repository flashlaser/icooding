package com.icooding.cms.web.api.open;

import com.icooding.cms.model.Spider;
import com.icooding.cms.web.base.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by jagua on 2017/11/17.
 */
@Controller
@RequestMapping("/op/tools")
public class ToolsCtl {

    @RequestMapping(value = "/dlc",method = RequestMethod.GET)
    public ModelAndView dlc() throws Exception {
        ModelAndView mv = new ModelAndView("tools/dlc");
        return mv;
    }
}
