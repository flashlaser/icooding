package com.icooding.cms.web.api.open;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.icooding.cms.dto.GlobalSetting;
import com.icooding.cms.model.RegesterCode;
import com.icooding.cms.model.SecurityVerification;
import com.icooding.cms.model.User;
import com.icooding.cms.model.UserSession;
import com.icooding.cms.service.RegesterCodeService;
import com.icooding.cms.service.SecurityVerificationService;
import com.icooding.cms.service.UserService;
import com.icooding.cms.service.UserSessionService;
import com.icooding.cms.utils.Base64;
import com.icooding.cms.utils.ClientInfo;
import com.icooding.cms.utils.EncryptUtil;
import com.icooding.cms.utils.TokenUtil;
import com.icooding.cms.web.base.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 用户注册
 * @author Fate
 *
 */
@Controller
@RequestMapping("/op/register")
public class RegisterCtl {
	
	private static final Logger LOG = Logger.getLogger(RegisterCtl.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSessionService userSessionService;
	
	@Autowired
	private SecurityVerificationService securityVerificationService;

	@Autowired
	private RegesterCodeService regesterCodeService;
	
	@RequestMapping("/goRegister")
	public String goRegister(HttpServletRequest request,HttpServletResponse response){
		UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
		if(userSession!=null){//如果已经登录，则不让他进入注册页
		    try {
                response.sendRedirect("/");
            } catch (IOException e) {
                LOG.error("注册页重定向失败", e);
            }
		}
			
		HttpSession session = request.getSession(false);
		
		if(request.getSession().getAttribute("callback")==null){//获取进入注册前的页面，注册完后跳回
			String callback = request.getHeader("REFERER");
			session.setAttribute("callback", callback);
		}
		session.setAttribute("loginType", UserSession.TYPE_LOCAL);
		
		return "register/register";
	}
	
	@RequestMapping("/submit")
	@ResponseBody
	public Object submit(String nickName, String password, String username, int type,String registerCode, HttpSession session,HttpServletRequest request){
		LOG.info("IP:"+ ClientInfo.getIp(request)+" \""+request.getHeader("User-Agent")+"\" 进行了注册。");
		Map<String,Object> map = new HashMap<String, Object>();
		//可能各种原因导致的表单验证通过，但提交时又重复了
		if(userService.checkLoginName(username)||userService.checkNickName(nickName)){
			map.put("msg", "请勿重复提交");
			map.put("success", false);
			return map;
		}
		try{
			User user = userService.register(nickName,password,username,type,registerCode);
			String url = (String) session.getAttribute("callback");
			map.put("msg", "注册成功");
			map.put("success", true);
    		session.removeAttribute("callback");
    		userSessionService.login(user, request);
    		map.put("url", url);
		
		}catch(Exception e){
			LOG.error("注册失败", e);
			map.put("msg", "注册失败");
			map.put("success", false);
		}
		return map;
	}
		
	/**
	 * 发送邮件验证码
	 * @param toMails 接收人
	 * @param uid
	 * @throws UnsupportedEncodingException
	 */
	public void sendEmail(String toMails, int uid) throws MessagingException, UnsupportedEncodingException{
	    
	    GlobalSetting globalSetting = GlobalSetting.getInstance();
	    
	    //验证码插入数据库
		SecurityVerification securityVerification = securityVerificationService
				.findBySecurityVerificationAndType(uid, SecurityVerification.VERIFICATION_TYPE_EMAIL);
		if (securityVerification == null) {
			securityVerification = new SecurityVerification();
			securityVerification.setUser(userService.find(uid));
		}
		Date now = Calendar.getInstance().getTime();
		if(securityVerification.getCode()==null||now.getTime()
				- securityVerification.getVerificationTime().getTime() > securityVerification
				.getTimeout() * 60 * 1000){
    		String code = TokenUtil.getRandomString(8, 2);
    		securityVerification.setCode(code);
    		securityVerification.setStatus(SecurityVerification.VERIFICATION_STATUS_FAIL);
    		securityVerification.setTimeout(Constants.EMAIL_TIMEOUT);
    		securityVerification.setVerificationType(SecurityVerification.VERIFICATION_TYPE_EMAIL);
    		securityVerification.setVerificationTime(new Date());
    		securityVerificationService.update(securityVerification);
    		
    		
    		// 建立邮件消息
    		MimeMessage mailMessage = globalSetting.getJavaMailSender().createMimeMessage();
    		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
    		// 设置收件人，寄件人 用数组发送多个邮件
    		messageHelper.setTo(toMails);
    		String nick = javax.mail.internet.MimeUtility.encodeText(globalSetting.getAppName()); 
    		messageHelper.setFrom(new InternetAddress(nick + " <"+globalSetting.getSmtpFrom()+">"));
    		messageHelper.setSubject(globalSetting.getSiteName()+"邮箱验证（请勿回复此邮件）");
    		//发送HTML邮件
    		messageHelper
    				.setText(
    						"<!doctype html>"
    								+ "<html>"
    								+ "<head>"
    								+ "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>"
    								+ "<title"+globalSetting.getSiteName()+"邮箱验证</title>"
    								+ "</head>"
    								+ "<body>"
    								+ "<div style='margin:0 auto;width:650px;'>"
    								+ "<h3>尊敬的用户：</h3>"
    								+ "<p>请点击以下地址，完成邮箱验证：</p>"
    								+ "<p><a href='http://"+globalSetting.getAppUrl()+"/op/security/verification/goVerifyEmail?uid="+uid+"&code="+URLEncoder.encode(Base64.encode(code.getBytes()),"UTF-8")+"'>http://"+globalSetting.getAppUrl()+"/op/security/verification/goVerifyEmail?uid="+uid+"&code="+URLEncoder.encode(Base64.encode(code.getBytes()),"UTF-8")+"</a></p>"
    								+ "<p>此链接有效期为"+Constants.EMAIL_TIMEOUT/60+"小时<span style='color:#808080'>（如果您无法点击此链接，请将链接复制到浏览器地址栏后访问）</span>"
    								+ "</p>" + "</div>"  + "</body>"
    								+ "</html>", true);
    		globalSetting.getJavaMailSender().send(mailMessage);
		}else{
			
		}
	}
	
	/**
	 * 验证手机号/邮箱是否存在
	 * @param name
	 * @param param
	 * @param session
	 * @return
	 */
	@RequestMapping("/check")
	@ResponseBody
	public Object check(String name, String param, HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		if(name.equals("username")||name.equals("email")){
			boolean exist = userService.checkLoginName(param);
			String s = "";
			if(param.contains("@"))
				s = "邮箱";
			else
				s = "手机号";
			map.put("status", exist?"n":"y");
			map.put("info",exist?"该"+s+"已被注册！":"该"+s+"可以使用");
		}else if(name.equals("nickName")){
			boolean exist = userService.checkNickName(param);
			map.put("status", exist?"n":"y");
			map.put("info", exist?"昵称已存在":"该昵称可以使用");
		}else if(name.equals("mobile")){
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			if(param.equals(userSession.getUser().getMobile())){
				map.put("status", "y");
				map.put("info","该手机号可以使用");
				return map;
			}
			boolean exist = userService.checkLoginName(param);
			map.put("status", exist?"n":"y");
			map.put("info",exist?"该手机号已被注册！":"该手机号可以使用");
		}
		return map;
	}



	@RequestMapping("/checkCode")
	@ResponseBody
	public Object checkCode(String name, String param, HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		RegesterCode byCode = regesterCodeService.findByCode(param);
		if(byCode == null){
			map.put("status","n");
			map.put("info","注册码不存在!");
			return map;
		}
		map.put("status", byCode.getStatus()==0?"n":"y");
		map.put("info",byCode.getStatus()==0?"该注册码已被使用！":"该注册码可用");
		return map;
	}

}
