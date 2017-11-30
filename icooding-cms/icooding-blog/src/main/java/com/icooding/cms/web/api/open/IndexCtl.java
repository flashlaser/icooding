package com.icooding.cms.web.api.open;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.icooding.cms.dto.Index;
import com.icooding.cms.model.*;
import com.icooding.cms.service.ForumService;
import com.icooding.cms.service.FriendLinkService;
import com.icooding.cms.service.ParamService;
import com.icooding.cms.service.ThemeService;
import com.icooding.cms.utils.ClientInfo;
import com.icooding.cms.utils.FilterHTMLTag;
import com.icooding.cms.web.base.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/")
public class IndexCtl {

	private static final Logger LOG = Logger.getLogger(IndexCtl.class);
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private ForumService forumService;
	
	@Autowired
	private ParamService paramService;
	
	@Autowired
	private FriendLinkService friendLinkService;
	
	@RequestMapping("/")
	public ModelAndView index(@RequestParam(defaultValue = "1")int curPage, HttpServletRequest request, HttpSession session){
		ModelAndView mv = new ModelAndView("index");
		LOG.info("IP："+ ClientInfo.getIp(request)+" \""+request.getHeader("User-Agent")+"\" 进入了网站首页");
		Index index = Index.getInstance();
		Map<Integer,List<Theme>> list = index.getThemes();
		if(list.get(curPage)==null){
		    LOG.info("查询文章列表");
			List<Theme> themes = themeService.pageByFid(0, Constants.INDEX_LIST_LENGTH, curPage, false, true, false, Theme.STATE_PUBLISH);
			
			for(Theme theme:themes){
				String c = FilterHTMLTag.delHTMLTag(theme.getContent());
				theme.setContent((c.length()>200?c.substring(0, 200):c)+"...");
				theme.setReplies(0);
			}
			list.put(curPage, themes);
		}
		if(index.getCount()==0){
		    LOG.info("查询文章总数");
			index.setCount(themeService.count(0, false, Theme.STATE_PUBLISH));
		}
		if(index.getFriendLinks().isEmpty()){
			index.setFriendLinks(friendLinkService.searchByState(FriendLink.STATE_PASS));
		}
		
		mv.addObject("themes", index.getThemes().get(curPage));
		mv.addObject("curPage", curPage);
		mv.addObject("count", index.getCount());
		mv.addObject("pageSize", Constants.INDEX_LIST_LENGTH);
//		mv.addObject("forums", index.getList());
		mv.addObject("title", index.getTitle());
		mv.addObject("keywords", index.getKeywords());
		mv.addObject("description", index.getDescription());
		mv.addObject("friendLinks", index.getFriendLinks());
//		mv.addObject("sid", session.getId());
		return mv;
	}
	
	@RequestMapping("/nana")
	public ModelAndView nana(){
		ModelAndView mv = new ModelAndView("index2");
		Index index = Index.getInstance();
		mv.addObject("forums", index.getList());
		return mv;
	}
	
	public Map<String, Object> forumToJson(Forum forum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("forumName", forum.getForumName());
		map.put("type", forum.getType());
		map.put("fid", forum.getFid());
		map.put("forumOrder", forum.getForumOrder());
		map.put("parentFid", forum.getParentForum()==null?0:forum.getParentForum().getFid());
		map.put("forumIcon", forum.getForumIcon());
		map.put("iconWidth", forum.getIconWidth());
		if(forum.getType()!=Forum.TYPE_REGION){
			long count = themeService.count(forum.getFid(), false, Theme.STATE_PUBLISH);
			Theme theme = themeService.getLastestTheme(forum.getFid());
			map.put("theme", count);
			if(theme!=null){
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
	
	/**
	 * 获取文章内容中的图片地址
	 * @param content
	 * @return
	 */
	public String getImg(String content){
		content = content.substring(content.indexOf("<img")+4);
		if(content.indexOf("\"")==-1){
			return "";
		}
		content = content.substring(content.indexOf("src=")+5);
		if(content.indexOf("\"")==-1){
			return "";
		}
		content = content.substring(0,content.indexOf("\""));
		return content;
	}
	
}
