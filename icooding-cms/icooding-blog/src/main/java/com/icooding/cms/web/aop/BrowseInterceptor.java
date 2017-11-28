package com.icooding.cms.web.aop;

import com.icooding.cms.model.BrowseLog;
import com.icooding.cms.service.BrowseLogService;
import com.icooding.cms.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.context.support.UiApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.logging.Logger;

/**
 * project_name icooding-cms
 * class BrowseInterceptor
 * date  2017/11/28
 * author ibm
 * version 1.0
 */
@Component
public class BrowseInterceptor {
    Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private BrowseLogService browseLogService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public boolean beforeRun( ) throws Exception {
        return true;
    }
    public void afterRun() throws Exception {
        String link = httpServletRequest.getRequestURL().toString();
        if(!link.contains("WEB-INF")){
            String ip = RequestUtils.getIp2(httpServletRequest);
            String referer = httpServletRequest.getHeader("referer");
            BrowseLog browseLog = new BrowseLog();
            browseLog.setCreateTime(new Date());
            browseLog.setIp(ip);
            browseLog.setLink(link);
            browseLog.setReferer(referer);
            browseLogService.save(browseLog);
        }

    }
}
