package com.icooding.cms.web.admin.api.statistics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icooding.cms.model.Forum;
import com.icooding.cms.service.BrowseLogService;
import com.icooding.cms.service.ForumService;
import com.icooding.cms.service.ThemeService;
import com.icooding.cms.utils.DateUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin/statistics/")
public class StatisticsCtl {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ForumService forumService;

    @Autowired
    private BrowseLogService browseLogService;

    @RequestMapping("/theme/{type}")
    public String index(@PathVariable(value = "type") String type, Model model) {


        model.addAttribute("type",type);
        return "admin/statistics/theme";
    }


    @RequestMapping("/browse/{type}")
    public String browse(@PathVariable(value = "type") String type, Model model) {
        model.addAttribute("type",type);
        return "admin/statistics/browse";
    }



    @RequestMapping("/getThemeData")
    @ResponseBody
    public Object getThemeData(int dateType) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> legendList = new ArrayList<String>();
        List<String> xAxisList = new ArrayList<String>();

        String[] week = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        String[] month = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        List<Forum> forums = forumService.searchChildPoint();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (dateType == 0)
            for (int i = 0; i < 7; i++) {
                if (day > 6){
                    day = 0;
                }
                xAxisList.add(week[day++]);
            }
        else {
            for (int i = 0; i < 12; i++) {
                xAxisList.add(month[i]);
            }
        }
        map.put("xAxis", xAxisList);
        List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
        for (Forum forum : forums) {
            Map<String, Object> m = new HashMap<String, Object>();

            legendList.add(forum.getForumName());
            List<Long> seriesList = new ArrayList<Long>();
            if (dateType == 0) {
                for (int i = 6; i >= 0; i--)
                    seriesList.add(themeService.statistics(forum.getFid(), dateType, "" + i));
            } else {
                DecimalFormat df1 = new DecimalFormat("00");
                for (int i1 = 1; i1 <= 12; i1++)
                    seriesList.add(themeService.statistics(forum.getFid(), dateType, DateUtil.format(new Date(), "yyyy-") + df1.format(i1)));
            }
            m.put("name", forum.getForumName());
            m.put("type", "bar");
            m.put("data", seriesList);
            series.add(m);
        }
        map.put("legend", legendList);
        map.put("series", series);

        return map;
    }



    @RequestMapping("/getBrowseData/year")
    @ResponseBody
    public Object getBrowseDataYear() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> legendList = new ArrayList<String>();
        List<String> xAxisList = new ArrayList<String>();
        String[] month = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        for (int i = 0; i < 12; i++) {
            xAxisList.add(month[i]);
        }
        legendList.add("流量统计-"+year);
        map.put("xAxis", xAxisList);
        List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = new HashMap<String, Object>();
        List<Long> seriesList = new ArrayList<Long>();
        for (int i = 1;i <= 12; i++) {
            String date = year+"-"+(i<10?"0"+i:i);
            seriesList.add(browseLogService.queryCountByDate("%Y-%m",date));
        }
        m.put("name", "流量统计-"+year);
        m.put("type", "bar");
        m.put("data", seriesList);
        series.add(m);
        map.put("legend", legendList);
        map.put("series", series);
        return map;
    }

    @RequestMapping("/getBrowseData/month")
    @ResponseBody
    public Object getBrowseDataMonth() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> legendList = new ArrayList<String>();
        List<String> xAxisList = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 30; i++) {
            xAxisList.add(0,DateFormatUtils.format(cal.getTime(),"yyyy-MM-dd"));
            cal.set(Calendar.DATE,cal.get(Calendar.DATE)-1);
        }

        map.put("xAxis", xAxisList);
        List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = new HashMap<String, Object>();

        legendList.add("流量统计");
        List<Long> seriesList = new ArrayList<Long>();

        for (String date : xAxisList) {
            seriesList.add(browseLogService.queryCountByDate("%Y-%m-%d",date));
        }

        m.put("name","流量统计");
        m.put("type", "bar");
        m.put("data", seriesList);
        series.add(m);
        map.put("legend", legendList);
        map.put("series", series);
        return map;
    }


    public static void main(String[] args) {
        List<String> xAxisList = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.DATE,cal.get(Calendar.DATE)+5);

        for (int i = 0; i < 30; i++) {
            xAxisList.add(0,DateFormatUtils.format(cal.getTime(),"yyyy-MM-dd"));
            cal.set(Calendar.DATE,cal.get(Calendar.DATE)-1);
        }
        System.out.println(xAxisList.toString());
    }

}
