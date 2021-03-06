package com.icooding.cms.web.admin.api.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icooding.cms.model.FriendLink;
import com.icooding.cms.dto.Index;
import com.icooding.cms.service.FriendLinkService;
import com.icooding.cms.utils.Strings;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin/friendLink")
public class AFriendLinkCtl {

    private static final Logger LOG = Logger.getLogger(AFriendLinkCtl.class);
    
	@Autowired
	private FriendLinkService friendLinkService;
	
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(defaultValue = "1")int curPage, @RequestParam(defaultValue = "0")int state){
		ModelAndView mv = new ModelAndView("admin/function/friendLink/list");
		mv.addObject("links", friendLinkService.page(state, curPage, 10));
		mv.addObject("count", friendLinkService.count(state));
		mv.addObject("curPage", curPage);
		mv.addObject("pageSize", 10);
		mv.addObject("state", state);
		return mv;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Object update(int fid, boolean agree, String reason){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			FriendLink friendLink = friendLinkService.find(fid);
			friendLink.setState(agree?FriendLink.STATE_PASS:FriendLink.STATE_DENIED);
			friendLink.setReason(reason);
			friendLinkService.update(friendLink);
			if(!Strings.isBlank(reason)&&!Strings.isBlank(friendLink.getEmail())){
				//发送邮件通知站长
			}
			Index index = Index.getInstance();
			index.setFriendLinks(friendLinkService.searchByState(FriendLink.STATE_PASS));
			map.put("success", true);
		}catch(Exception e){
			LOG.error("友链审批失败", e);
			map.put("success", false);
			map.put("msg", "未知错误");
		}
		return map;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Object add(String siteName, String url){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			FriendLink friendLink = new FriendLink();
			friendLink.setState(FriendLink.STATE_PASS);
			friendLink.setSiteName(siteName);
			friendLink.setUrl(url);
			friendLinkService.update(friendLink);
			Index index = Index.getInstance();
			index.setFriendLinks(friendLinkService.searchByState(FriendLink.STATE_PASS));
			map.put("success", true);
		}catch(Exception e){
		    LOG.error("友链添加失败", e);
			map.put("success", false);
			map.put("msg", "未知错误");
		}
		return map;
	}
	
	@RequestMapping("/check")
	public ModelAndView check(){
		ModelAndView mv = new ModelAndView("admin/function/friendLink/check");
		List<Map<String, Object>> list = Index.getInstance().getFriendLinkCheck();
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping("/del")
	@ResponseBody
	public Object del(int id){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			FriendLink friendLink = friendLinkService.find(id);
			friendLink.setState(FriendLink.STATE_DENIED);
			friendLinkService.update(friendLink);
			Index index = Index.getInstance();
			index.setFriendLinks(friendLinkService.searchByState(FriendLink.STATE_PASS));
			map.put("success", true);
		}catch(Exception e){
		    LOG.error("友链删除失败", e);
			map.put("success", false);
			map.put("msg", "未知错误");
		}
		return map;
	}
}
