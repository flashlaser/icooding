package com.icooding.cms.web.api;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.icooding.cms.dto.GlobalSetting;
import com.icooding.cms.model.Param;
import com.icooding.cms.model.User;
import com.icooding.cms.service.ParamService;
import com.icooding.cms.service.UserService;
import com.icooding.cms.utils.EncryptUtil;
import com.icooding.cms.utils.Strings;
import com.icooding.cms.web.api.open.OpenCtl;
import com.icooding.cms.web.base.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class InstallCtl {

	@Autowired
	private ParamService paramService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/install")
	public String install(){
		return "install";
	}
	
	@RequestMapping(value = "/install/update", method=RequestMethod.POST)
	@ResponseBody
	public Object update(String appName, String url, String email, String nickName, String password){
		Map<String, Object> map = new HashMap<String, Object>();
		
		GlobalSetting globalSetting = GlobalSetting.getInstance();
		
		//防止有人偷调接口
		if(!Strings.isEmpty(globalSetting.getAppName())){
		    map.put("success", false);
		    return map;
		}
		
		try{
			updateParam(Constants.SITE_NAME, appName, Param.TYPE_TEXT);
			updateParam(Constants.APP_NAME, appName, Param.TYPE_TEXT);
			updateParam(Constants.APP_URL, url, Param.TYPE_TEXT);
			updateParam(Constants.ADMIN_EMAIL, email, Param.TYPE_TEXT);
			updateParam(Constants.REG_ALLOW, 1, Param.TYPE_INT);
			updateParam(Constants.NEED_EMAIL_VERIFY, 1, Param.TYPE_INT);
			updateParam(Constants.QQ, 0, Param.TYPE_INT);
			updateParam(Constants.WEIBO, 0, Param.TYPE_INT);
			updateParam(Constants.REDIS_OPEN, 0, Param.TYPE_INT);
			updateParam(Constants.SEARCH_TYPE, 1, Param.TYPE_INT);
			globalSetting.setSiteName(appName);
			globalSetting.setAppName(appName);
			globalSetting.setAppUrl(url);
			globalSetting.setAdminEmail(email);
			globalSetting.setRegAllow(false);
			globalSetting.setNeedEmailVerify(true);
			globalSetting.setRedisOpen(false);
			
			User user = new User();
			user.setEmail(email);
			user.setNickName(nickName);
			user.setPassword(EncryptUtil.pwd(user.getActivateDate(), password));
			user.setUserType(User.USER_TYPE_ADMIN);
			userService.save(user);
			user.setPassword(EncryptUtil.pwd(user.getActivateDate(), password));
			userService.update(user);
			 
			//删除安装页面
			URL file = OpenCtl.class.getClassLoader().getResource("log4j.properties");
            String path = file.getPath();
            path = path.substring(0, path.indexOf("WEB-INF"));
            path = path.replaceAll("%20", " ");
            String jspPath = path + "WEB-INF/jsp/install.jsp";
            String classPath = path + "WEB-INF/classes/fate/webapp/blog/api/installCtl.class";
            File jspFile = new File(jspPath);
            if(jspFile.exists()){
                jspFile.delete();
            }
                
            File classFile = new File(classPath);
            if(classFile.exists()){
                classFile.delete();
            }
            
            
			map.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return map;
	}
	
	public void updateParam(String key, Object value, int type){
		Param pobj = paramService.findByKey(key);
		if(pobj==null){
			pobj = new Param();
			pobj.setKey(key);
			pobj.setType(type);
		}
		if(type==Param.TYPE_TEXT)
			pobj.setTextValue((String) value);
		else
			pobj.setIntValue((int) value);
		paramService.update(pobj);
	}
}
