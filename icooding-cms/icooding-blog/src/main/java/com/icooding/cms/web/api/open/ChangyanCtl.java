package com.icooding.cms.web.api.open;

import com.alibaba.fastjson.JSONArray;
import com.icooding.cms.dto.UserDto;
import com.icooding.cms.dto.UserInfoDto;
import com.icooding.cms.model.User;
import com.icooding.cms.model.UserSession;
import com.icooding.cms.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by jagua on 2017/11/17.
 */
@Controller
@RequestMapping("/op/changyan/login")
public class ChangyanCtl {
    @Autowired
    private UserSessionService userSessionService;

    @RequestMapping("loginout")
    public void loginBySite(@RequestParam(value = "callback") String callback, HttpServletResponse resp,HttpSession session) throws Exception {
        String guid = ((UserSession) session.getAttribute("userSession")).getGuid();
        userSessionService.logout(guid);
        // 清空当前session
        session.invalidate();
        //清除自己网站cookies信息,同时前端logout.js代码用来清理畅言cookie
        resp.getWriter().write(callback + "({\"code\":\"1\",reload_page:0, js-src:logout.js})");
    }


    //该接口在页面每一次加载时都会被调用，用来判断用户在自己网站是否登录
    @RequestMapping("getUserInfo")
    public void getUserInfo(@RequestParam(value = "callback") String callback, HttpServletRequest res,
                            HttpServletResponse resp) throws Exception {
        UserInfoDto userinfo = new UserInfoDto();
        UserSession userSession = (UserSession)res.getSession().getAttribute("userSession");
        if(userSession == null){
            userinfo.setIs_login(0);//用户未登录
        }else{
            User u = userSession.getUser();
            userinfo.setIs_login(1);//用户已登录
            UserDto user = new UserDto();
            user.setUser_id(u.getUid()); //该值具体根据自己用户系统设定
            user.setNickname(u.getNickName()); //该值具体根据自己用户系统设定
            user.setImg_url(u.getHeadIconSmall()); //该值具体根据自己用户系统设定，可以为空
            user.setProfile_url("");//该值具体根据自己用户系统设定，可以为空
            user.setSign(""); //签名已弃用，任意赋值即可
            userinfo.setUser(user);
        }
        resp.setContentType("application/x-javascript");//指定传输格式为js
        resp.getWriter().write(callback+"("+ JSONArray.toJSONString(userinfo)+")");//拼接成jsonp格式
    }


    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public void comment(String data) throws Exception {

    }

}
