package com.icooding.cms.web.api.open;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.icooding.cms.dto.GlobalSetting;
import com.icooding.cms.model.*;
import com.icooding.cms.service.*;
import com.icooding.cms.utils.FilterHTMLTag;
import com.icooding.cms.web.base.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("")
public class ThemeCtl {

	private static final Logger LOG = Logger.getLogger(ThemeCtl.class);
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private CommentsService commentsService;
	
	@Autowired
	private VoteRecordService voteRecordService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ForumService forumService;
	
	@Autowired
	private AdvertisementService advertisementService; 
	
	@Autowired
    private ThirdPartyAccessService thirdPartyAccessService;
	
	private String[] spider_key = {
			"googlebot",
			"mediapartners-google",
			"feedfetcher-Google",
			"ia_archiver",
			"iaarchiver",
			"sqworm",
			"baiduspider",
			"msnbot",
			"yodaobot",
			"yahoo! slurp;",
			"yahoo! slurp china;",
			"yahoo",
			"iaskspider",
			"sogou spider",
			"sogou web spider",
			"sogou push spider",
			"sogou orion spider",
			"sogou-test-spider",
			"sogou+head+spider",
			"sohu",
			"sohu-search",
			"Sosospider",
			"Sosoimagespider",
			"JikeSpider",
			"360spider",
			"qihoobot",
			"tomato bot",
			"bingbot",
			"youdaobot",
			"askjeeves/reoma",
			"manbot",
			"robozilla",
			"MJ12bot",
			"HuaweiSymantecSpider",
			"Scooter",
			"Infoseek",
			"ArchitextSpider",
			"Grabber",
			"Fast",
			"ArchitextSpider",
			"Gulliver",
			"Lycos",
			"YisouSpider",
			"YYSpider",
			"360JK",//360监控
			"Baidu-YunGuanCe-Bot",//百度云观测
			"Alibaba",//阿里云云盾对机子的安全扫描
			"AhrefsBot",
			"Renren Share Slurp",//人人分享
			"SinaWeiboBot",//新浪微博分享
			"masscan"//Masscan 扫描器
	};
	
	/**
	 * 判断是否是蜘蛛来访
	 * @param request
	 * @return
	 */
	public boolean isSpider(HttpServletRequest request){
		String agent = request.getHeader("User-Agent");
		int i = 0;
		boolean flag = false;
		if(agent==null)
			return true;
		for(i=0;i<spider_key.length;i++){
			if(agent.trim().toLowerCase().contains(spider_key[i].toLowerCase())){
				break;
			}
		}
		if(i<spider_key.length){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 左边为新url，右边为了让搜索引擎也能访问到
	 * @param domain
	 * @param urlId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"/{domain}/{urlId}.html"},method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable("domain") String domain, @PathVariable("urlId") String urlId, HttpServletRequest request, HttpServletResponse response){
		String referer = request.getHeader("Referer");
		 GlobalSetting globalSetting = GlobalSetting.getInstance();
		Theme theme = themeService.findByDomainAndUrlId(domain, urlId, globalSetting.getRedisOpen());
		if(theme==null){
			try {
				response.sendError(404);//发送404
			} catch (IOException e) {
				LOG.error("发送404状态码失败", e);
			}
			return null;
		}
		String jsp = null;
		switch (theme.getType()) {
            case 0 :
                jsp = "theme/theme";
                break;
            case 1 :
                jsp = "theme/audioTheme";
                break;
            case 2 :
                jsp = "theme/videoTheme";
                break;
            default :
                jsp = "theme/theme";
                break;
		}
		ModelAndView mv = new ModelAndView(jsp);
		if(referer!=null&&referer.contains("op/search")){
			theme.setSearch(theme.getSearch()+1);
		}
		UserSession userSession = (UserSession) request.getSession(false).getAttribute("userSession");
		//主题不是草稿状态并且当前用户不是管理员
		if(!isSpider(request)&&theme.getState()!=Theme.STATE_EDIT&&(userSession==null||(userSession!=null&&userSession.getUser().getUserType()== User.USER_TYPE_NORMAL))){
			theme.setViews(theme.getViews()+1);
		}
		theme.setReplies(0);
		theme = themeService.update(theme, globalSetting.getRedisOpen());
		
		Advertisement advertisement = advertisementService.findLastByType(Advertisement.TYPE_INSIDE);
		
		ThirdPartyAccess weibo = thirdPartyAccessService.findByType(ThirdPartyAccess.TYPE_XINLANG);
        mv.addObject("weibo", weibo==null?"":weibo.getAccessKey());
		
		mv.addObject("changyanAppId", globalSetting.getChangyanAppId());
		mv.addObject("changyanSecret", globalSetting.getChangyanSecret());
		mv.addObject("theme", theme);
		mv.addObject("desc", FilterHTMLTag.delHTMLTag(theme.getContent()));
		mv.addObject("sid", request.getSession(false).getId());
		mv.addObject("adv", advertisement);
		return mv;
	}

	@RequestMapping("/theme/addTheme")
	public ModelAndView addTheme(@RequestParam(defaultValue = "0")int fid,String tid,HttpSession session){
		ModelAndView mv = new ModelAndView("theme/addTheme");
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		GlobalSetting globalSetting = GlobalSetting.getInstance();
		Forum forum = null;
		if(tid!=null){
			Theme theme = themeService.find(tid, globalSetting.getRedisOpen());
			if(theme!=null){
				if(theme.getAuthorId()!=userSession.getUser().getUid()){
					mv.addObject("success", false);
					mv.addObject("msg", "您没有编辑该主题的权限");
					return mv;
				}
				forum = theme.getForum();
				mv.addObject("theme", theme);
			}else{
			    forum = forumService.find(fid);
			}
		}else{
		    forum = forumService.find(fid);
		}
			
		if(forum==null||forum.getType()==Forum.TYPE_REGION){
			mv.addObject("success", false);
			mv.addObject("msg", "所选版块不存在");
		}else{
			mv.addObject("success", true);
			mv.addObject("forum", forum);
		}
		return mv;
	}

	
	@RequestMapping("/tag/{tagName}")
	public ModelAndView tags(@PathVariable("tagName")String tagName, @RequestParam(defaultValue = "1")int curPage){
		ModelAndView mv = new ModelAndView("tag/list");
		List<Theme> themes = themeService.pageByTag(tagName, Constants.THEME_LIST_LENGTH, curPage, false);
		long count = themeService.countByTag(tagName, false);
		mv.addObject("themes", themes);
		mv.addObject("count", count);
		mv.addObject("tagName", tagName);
		mv.addObject("curPage", curPage);
		mv.addObject("tag", tagName);
		return mv;
	}
}
