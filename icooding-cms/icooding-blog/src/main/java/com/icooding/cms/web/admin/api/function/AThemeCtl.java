package com.icooding.cms.web.admin.api.function;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.icooding.cms.dto.GlobalSetting;
import com.icooding.cms.dto.Index;
import com.icooding.cms.model.*;
import com.icooding.cms.service.*;
import com.icooding.cms.utils.DateUtil;
import com.icooding.cms.utils.FilterHTMLTag;
import com.icooding.cms.utils.PingUtils;
import com.icooding.cms.web.base.Constants;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/theme")
public class AThemeCtl {

    private static final Logger LOG = Logger.getLogger(AThemeCtl.class.getName());


    @Autowired
    private UserService userService;


    @Autowired
    private ThemeService themeService;

    @Autowired
    private ThemeTagService themeTagService;

    @Autowired
    private ForumService forumService;

    @Autowired
    private PingRecordService pingRecordService;

    @Autowired
    private MediaService mediaService;

    /**
     * 发布主题，暂定为管理员功能
     * 
     * @param theme
     * @param state
     * @param fid
     * @param session
     * @return
     */
    @RequestMapping("/addTheme")
    @ResponseBody
    public Object addTheme(Theme theme, int state, int fid, String[] files, String[] titles,
                           String[] singers, String[] times, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            UserSession userSession = (UserSession) session.getAttribute("userSession");
            if (userSession == null) {
                map.put("success", false);
                map.put("msg", "登录超时，请重新登录");
                return map;
            }
            GlobalSetting globalSetting = (GlobalSetting) session.getAttribute("setting");
            Forum forum = forumService.find(fid);
            if (forum == null) {
                map.put("success", false);
                map.put("msg", "版块不存在");
                return map;
            }
            theme.setForum(forum);
            theme.setAuthorDomain(userSession.getUser().getDomain());
            theme.setAuthor(userSession.getUser().getNickName());
            theme.setAuthorId(userSession.getUser().getUid());
            theme.setState(state);

            String tag2 = "";
            /* 把文章的标签存入数据库以便下次直接点击 */
            if (theme.getTags() != null && !"".equals(theme.getTags().trim())) {
                String[] tags = theme.getTags().split(",");
                Set<String> set = new HashSet<String>();
                /* 遍历标签，如果已存在则更新时间；不存在则创建 */
                for (String tag : tags) {
                    if (!themeTagService.exits(tag, userSession.getUser().getUid())) {
                        ThemeTag themeTag = new ThemeTag();
                        themeTag.setTagName(tag.trim());
                        themeTag.setLastUsed(new Date());
                        themeTag.setUser(userSession.getUser());
                        themeTagService.save(themeTag);
                    } else {
                        ThemeTag themeTag = themeTagService.findByNameAndUser(tag, userSession
                                .getUser().getUid());
                        themeTag.setLastUsed(new Date());
                        themeTagService.update(themeTag);
                    }
                    set.add(tag);
                }
                /* 通过Set删除重复的标签 */
                Iterator<String> it = set.iterator();
                while (it.hasNext()) {
                    tag2 += it.next() + ",";
                }
                tag2 = tag2.substring(0, tag2.lastIndexOf(","));
                theme.setTags(tag2);
            }
            theme.setTitle(theme.getTitle().trim());
            /* 从前台传的数据相当于新建了一个文章，会覆盖旧数据，所以得从数据库中取 */
            if (theme.getGuid() != null && !"".equals(theme.getGuid())) {
                Theme a = themeService.find(theme.getGuid(), globalSetting.getRedisOpen());
                a.setTitle(theme.getTitle());
                a.setContent(theme.getContent());
                a.setPublishDate(theme.getPublishDate());
                a.setLastModify(new Date());
                a.setType(theme.getType());
                a.setState(state);
                a.setTags(tag2);
                theme = a;
            }

            if (theme.getPublishDate() == null) {
                theme.setPublishDate(new Date());
            }

            theme = themeService.update(theme, globalSetting.getRedisOpen());
            theme.setUrlId(String.valueOf(theme.getGuid().hashCode()));
            theme = themeService.update(theme, globalSetting.getRedisOpen());
            if (theme.getType() == Theme.TYPE_VIDEO && files != null){
                for (String media : files) {
                    Media m = mediaService.findByUrl(media);
                    if (m == null){
                        m = new Media();
                    }
                    m.setTheme(theme);
                    m.setType(theme.getType());
                    m.setUrl(media);
                    mediaService.update(m);
                }
            }else if (theme.getType() == Theme.TYPE_AUDIO && files != null){
                for (int i = 0; i < files.length; i++) {
                    Media m = mediaService.findByUrl(files[i]);
                    if (m == null){
                        m = new Media();
                    }
                    m.setTheme(theme);
                    m.setType(theme.getType());
                    m.setUrl(files[i]);
                    m.setLastTime(times[i]);
                    m.setSinger(singers[i]);
                    m.setTitle(titles[i]);
                    mediaService.update(m);
                }
            }
            refresh();
            map.put("url", theme.getUrlId());
            map.put("success", true);
            map.put("msg", "保存成功");
        } catch (Exception e) {
            LOG.info("主题提交失败", e);
            map.put("success", false);
            map.put("msg", "保存失败");
        }

        return map;
    }

    /**
     * 刷新单例中的数据
     */
    public void refresh() {
        Index index = Index.getInstance();
        List<Forum> forums = forumService.searchRoot();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // for (Forum f : forums) {
        // list.add(forumToJson(f));
        // }
        if (forums.size() > 2){
            list.add(forumToJson(forums.get(2)));
        }
        index.setList(list);
        index.setHot(themeService.pageHot(5, 1, false, Theme.STATE_PUBLISH));
        index.setSearchHot(themeService.pageSearchHot(5, 1, false, Theme.STATE_PUBLISH));

        Map<Integer, List<Theme>> list1 = new HashMap<Integer, List<Theme>>();
        List<Theme> themes = themeService.pageByFid(0, Constants.INDEX_LIST_LENGTH, 1, false, true,
                false, Theme.STATE_PUBLISH);
        for (Theme theme : themes) {
            String c = FilterHTMLTag.delHTMLTag(theme.getContent());
            theme.setContent((c.length() > 200 ? c.substring(0, 200) : c) + "...");
        }
        list1.put(1, themes);
        index.setThemes(list1);
        index.setCount(themeService.count(0, false, Theme.STATE_PUBLISH));
    }

    public Map<String, Object> forumToJson(Forum forum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("forumName", forum.getForumName());
        map.put("type", forum.getType());
        map.put("fid", forum.getFid());
        map.put("forumOrder", forum.getForumOrder());
        map.put("parentFid", forum.getParentForum() == null ? 0 : forum.getParentForum().getFid());
        map.put("forumIcon", forum.getForumIcon());
        map.put("iconWidth", forum.getIconWidth());
        if (forum.getType() != Forum.TYPE_REGION) {
            long count = themeService.count(forum.getFid(), false, Theme.STATE_PUBLISH);
            Theme theme = themeService.getLastestTheme(forum.getFid());
            map.put("theme", count);
            if (theme != null) {
                map.put("title", theme.getTitle());
                map.put("url", "/"+theme.getAuthorDomain()+"/"+theme.getUrlId()+".html");
                map.put("author", theme.getAuthor());
                map.put("publishDate", theme.getPublishDate());
                map.put("img", getImg(theme.getContent()));
            }
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (forum.getChildForums() != null && !forum.getChildForums().isEmpty()) {

            for (Forum f : forum.getChildForums()) {
                list.add(forumToJson(f));
            }
        }
        map.put("children", list);
        return map;
    }

    public String getImg(String content) {
        content = content.substring(content.indexOf("<img") + 4);
        if (content.indexOf("\"") == -1){
            return "";
        }
        
        content = content.substring(content.indexOf("src=") + 5);
        if (content.indexOf("\"") == -1){
            return "";
        }
            
        content = content.substring(0, content.indexOf("\""));
        return content;
    }

    @RequestMapping("/updateAll")
    @ResponseBody
    public Object updateAll(HttpSession session) throws IllegalStateException, IOException,
            DocumentException {
        List<Theme> themes = themeService.findAll(false);
        GlobalSetting globalSetting = GlobalSetting.getInstance();
        for (Theme theme : themes) {
            theme.setUrlId(String.valueOf(theme.getGuid().hashCode()));
            User user = userService.find(theme.getAuthorId());
            theme.setAuthorDomain(user.getDomain());
            theme.setAuthor(user.getNickName());
            theme = themeService.update(theme, globalSetting.getRedisOpen());
        }
        refresh();
        return true;
    }

    @RequestMapping("/toEditTheme")
    public ModelAndView toEditTheme(String guid) {
        ModelAndView mv = new ModelAndView("admin/function/theme/editTheme");
        GlobalSetting globalSetting = GlobalSetting.getInstance();
        Theme theme = themeService.find(guid, globalSetting.getRedisOpen());
        mv.addObject("theme", theme);
        return mv;
    }

    @RequestMapping("/editTheme")
    @ResponseBody
    public Object editTheme(String guid, String content, String title, int type) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            GlobalSetting globalSetting = GlobalSetting.getInstance();
            Theme theme = themeService.find(guid, globalSetting.getRedisOpen());
            // theme.setThemeType(themeTypeService.find(type));
            User user = userService.find(theme.getAuthorId());
            theme.setAuthorDomain(user.getDomain());
            theme.setAuthor(user.getNickName());
            theme.setContent(content);
            theme.setTitle(title);
            themeService.update(theme, globalSetting.getRedisOpen());
            map.put("success", true);
        } catch (Exception e) {
            LOG.error("编辑主题失败", e);
            map.put("success", false);
            map.put("msg", "保存失败");
        }
        return map;
    }

    @RequestMapping("/themeList")
    public ModelAndView themeList(@RequestParam(defaultValue = "0") int fid,
            @RequestParam(defaultValue = "1") int curPage) {
        ModelAndView mv = new ModelAndView("admin/function/theme/themeList");
        Forum forum = forumService.find(fid);
        List<Theme> themes = themeService.pageByFid(fid, Constants.THEME_PER, curPage, false, true,
                false, 0);
        List<Forum> forums = forumService.searchChildPoint();
        mv.addObject("forums", forums);
        mv.addObject("themes", themes);
        mv.addObject("forum", forum);
        mv.addObject("curPage", curPage);
        mv.addObject("pageSize", Constants.THEME_PER);
        mv.addObject("count", themeService.count(fid, false, 0));
        return mv;
    }

    @RequestMapping("/seoPing")
    public ModelAndView seoPing(@RequestParam(defaultValue = "1") int curPage) {
        ModelAndView mv = new ModelAndView("admin/function/theme/seoPing");
        List<PingRecord> list = pingRecordService.findAll(curPage, Constants.PING_PAGE_SIZE);
        long count = pingRecordService.count();
        mv.addObject("pings", list);
        mv.addObject("count", count);
        mv.addObject("pageSize", Constants.PING_PAGE_SIZE);
        mv.addObject("curPage", curPage);
        return mv;
    }

    @RequestMapping("/rePing")
    @ResponseBody
    public Object rePing(int id, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            PingRecord pingRecord = pingRecordService.find(id);
            GlobalSetting globalSetting = (GlobalSetting) session.getAttribute("setting");
            boolean baidu = PingUtils.ping(Constants.BAIDU_PING, pingRecord.getTheme().getTitle(),
                     globalSetting.getAppUrl() + "/", pingRecord.getTheme().getUrlId()+".html",
                    null);
            pingRecord.setBaidu(baidu);
            pingRecord.setPingDate(new Date());
            pingRecordService.update(pingRecord);
            map.put("success", baidu);
        } catch (Exception e) {
            LOG.error("重ping失败", e);
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping("/themeTypeList")
    public ModelAndView themeTypeList() {
        ModelAndView mv = new ModelAndView("admin/function/theme/themeTypeList");
        List<Forum> forums = forumService.searchChildPoint();
        mv.addObject("forums", forums);
        // List<ThemeType> types = themeTypeService.orderByPriority();
        // mv.addObject("types", types);
        return mv;
    }

    @RequestMapping("/deleteTheme")
    @ResponseBody
    public Object deleteTheme(String guid) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            GlobalSetting globalSetting = GlobalSetting.getInstance();
            Theme theme = themeService.find(guid, globalSetting.getRedisOpen());
            theme.setIsDelete(true);
            themeService.update(theme, globalSetting.getRedisOpen());
            map.put("msg", "删除成功");
            map.put("success", true);
        } catch (Exception e) {
            LOG.error("删除主题失败", e);
            map.put("success", false);
            map.put("msg", "删除失败");
        }
        refresh();
        return map;
    }

    @RequestMapping("/multiDeleteTheme")
    @ResponseBody
    public Object multiDeleteTheme(String guids) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            List<String> guid = new ArrayList<String>();
            String[] ids = guids.split(",");
            for (String id : ids) {
                guid.add(id);
            }

            themeService.multiDelete(guid);

            map.put("msg", "删除成功");
            map.put("success", true);
        } catch (Exception e) {
            LOG.error("批量删除主题失败", e);
            map.put("success", false);
            map.put("msg", "删除失败");
        }
        refresh();
        return map;
    }

    @RequestMapping("/crushTheme")
    @ResponseBody
    public Object crushTheme(String guid) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            GlobalSetting globalSetting = GlobalSetting.getInstance();
            Theme theme = themeService.find(guid, globalSetting.getRedisOpen());
            themeService.crush(theme, globalSetting.getRedisOpen());
            map.put("msg", "删除成功");
            map.put("success", true);
        } catch (Exception e) {
            LOG.error("彻底删除主题失败", e);
            map.put("success", false);
            map.put("msg", "删除失败");
        }
        refresh();
        return map;
    }

    @RequestMapping("/recycleBin")
    public ModelAndView recycleBin(@RequestParam(defaultValue = "0") int fid,
            @RequestParam(defaultValue = "1") int curPage) {
        ModelAndView mv = new ModelAndView("admin/function/theme/recycleBin");
        List<Theme> themes = themeService.pageByFid(fid, Constants.THEME_PER, curPage, true, true,
                false, 0);
        mv.addObject("themes", themes);
        mv.addObject("curPage", curPage);
        mv.addObject("pageSize", Constants.THEME_PER);
        mv.addObject("count", themeService.count(fid, true, 0));
        return mv;
    }

    @RequestMapping("/restoreTheme")
    @ResponseBody
    public Object restoreTheme(String guid) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            GlobalSetting globalSetting = GlobalSetting.getInstance();
            Theme theme = themeService.find(guid, globalSetting.getRedisOpen());
            theme.setIsDelete(false);
            themeService.update(theme, globalSetting.getRedisOpen());
            map.put("msg", "恢复成功");
            map.put("success", true);
        } catch (Exception e) {
            LOG.error("恢复主题失败", e);
            map.put("success", false);
            map.put("msg", "恢复失败");
        }
        return map;
    }

    /**
     * 置顶
     * 
     * @param guid
     *            主题的id
     * @param priority
     *            优先级
     * @return
     */
    @RequestMapping("/setTop")
    @ResponseBody
    public Object setTop(String guid, int priority) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            GlobalSetting globalSetting = GlobalSetting.getInstance();
            Theme theme = themeService.find(guid, globalSetting.getRedisOpen());
            theme.setPriority(priority);
            themeService.update(theme, globalSetting.getRedisOpen());
            map.put("msg", (priority == 0 ? "取消" : "") + "置顶成功");
            map.put("success", true);
        } catch (Exception e) {
            LOG.error((priority == 0 ? "取消" : "") + "置顶失败", e);
            map.put("success", false);
            map.put("msg", (priority == 0 ? "取消" : "") + "置顶失败");
        }
        return map;
    }

    /**
     * 表单提交日期绑定
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// 写上你要的日期格式
        // dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
