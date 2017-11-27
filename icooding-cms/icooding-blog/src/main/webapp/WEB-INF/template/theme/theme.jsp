<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<base href="/">


<title>${theme.title}- ${theme.forum.forumName} -
	${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="keywords" content="${theme.title},${theme.tags}">
<meta name="description"
	content="${fn:substring(desc,0,100)}...,${theme.title},${theme.forum.forumName}">
<style type="text/css">
.adv {
	margin-top: 10px;
}
</style>

<c:import url="/op/head"></c:import>
<link rel="stylesheet" href="static/css/bootstrap-datetimepicker.min.css">
    <link type="text/css" rel="stylesheet" href="static/js/syntaxhighlighter/styles/shCore.css"/>
    <link type="text/css" rel="stylesheet" href="static/js/syntaxhighlighter/styles/shThemeDefault.css"/>
</head>

<body>
		<!-- 头部导航条 -->
		<c:import url="/op/header">
			<c:param name="header_index" value="1" />
		</c:import>
		<div class="container">
		<ol class="breadcrumb">
			<li><a href="/" rel="nofollow"><span
					class="glyphicon glyphicon-home"></span>首页</a></li>
			<li><a href="op/forum-${theme.forum.fid}-1.html">${theme.forum.forumName}</a></li>
			<li class="active">${theme.title}</li>
		</ol>
		<div class="row">
			<div class="col-md-8">
				<div class=" theme animated fadeIn" id="theme" >
					<div class="header page-header">
						<h3 class="h3 visible-md-block visible-lg-block">
							${theme.title}
							<c:if test="${theme.authorId==userSession.user.uid}">
								<label><a href="theme/addTheme?tid=${theme.guid}">编辑</a></label>
							</c:if>
							<small> <span class="glyphicon glyphicon-user"></span>${theme.author}
								<span class="glyphicon glyphicon-calendar"></span>
							<fmt:formatDate value="${theme.publishDate}" type="date"
									pattern="yyyy年MM月dd日" /> <span
								class="glyphicon glyphicon-comment"></span>${theme.replies} <span
								class="glyphicon glyphicon-eye-open"></span>${theme.views}次浏览 <span
								class="glyphicon glyphicon-tags"></span>${theme.tags}

							</small>
						</h3>
						<h4 class="h4 visible-xs-block visible-sm-block">
							${theme.title}
								：${theme.authorId==userSession.user.uid}
							<c:if test="${theme.authorId==userSession.user.uid}">
								<label><a href="theme/addTheme?tid=${theme.guid}">编辑</a></label>
							</c:if>
							<small> <span class="glyphicon glyphicon-user"></span>${theme.author}
								<span class="glyphicon glyphicon-calendar"></span>
							<fmt:formatDate value="${theme.publishDate}" type="date"
									pattern="yyyy年MM月dd日" /> <span
								class="glyphicon glyphicon-comment"></span>${theme.replies} <span
								class="glyphicon glyphicon-eye-open"></span>${theme.views}次浏览 <span
								class="glyphicon glyphicon-tags"></span>${theme.tags}

							</small>
						</h4>
						<div class="toolbar">
							<div class="vote" data-toggle="tooltip" data-placement="top"
								data-original-title="★ ${theme.up} ☆ ${theme.down}"
								guid="${theme.guid}">
								<div class="up" data-type="1">
									<span class="glyphicon glyphicon-thumbs-up"></span><span
										class="votes">${theme.up}</span>
								</div>
								<div class="down" data-type="1">
									<span class="glyphicon glyphicon-thumbs-down"></span>
								</div>
							</div>
							<div class="bdsharebuttonbox">
								<a href="#" class="bds_more" data-cmd="more"></a><a href="#"
									class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a
									href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a
									href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a
									href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a
									href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="content col-md-12">
						${theme.content}</div>
						<div class="col-md-12">
							<div class="alert alert-success" role="alert" style="margin: 20px 0;border-left: 0;border-right: 0;border-radius:0;">
								<span class="glyphicon glyphicon-play"></span>&nbsp;本文链接：<a href="${theme.url}" rel="author">${theme.title}</a>
								，${userSession.user.nickName},转载请注明链接~~
							</div>
						</div>
						<div class="col-md-12" style="text-align: center;">
							<c:if test="${setting.grant}">
								<div id="cyReward" role="cylabs" data-use="reward"></div>
							</c:if>
						</div>

					</div>
				</div>

				<div class="comment">
 					<!-- 评论 -->
					<div id="SOHUCS" sid="${theme.guid}" ></div>
					<!-- 评论 -->
				</div>
				
				<
			</div>
			<div class="col-md-4">
				<c:import url="/op/rightNavi?fid=${theme.forum.parentForum.fid}"></c:import>
			</div>
		</div>
	</div>
	<c:import url="/op/footer"></c:import>
	<!-- 图+广告位 
	<script type="text/javascript">
		/*为了保证用户体验及收益，图片大小不得小于：200*180*/
		/*图+推广*/
		var cpro_id = "u2079355";
	</script>
	<script src="http://cpro.baidustatic.com/cpro/ui/i.js"
		type="text/javascript"></script>
	-->
	<script src="http://open.reinforce.cn/Bootstrap/datetimepicker/bootstrap-datetimepicker.min.js"></script>
	<script src="http://open.reinforce.cn/Bootstrap/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"  charset="UTF-8"></script>

	<script type="text/javascript">

		function respond(num) {
			var options = {
				url : "theme/addComment",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					var d = dialog({
						content : data.message
					});
					d.showModal();
					if (data.success) {
						setTimeout(function() {
							location.reload();
						}, 1500);
					} else {
						setTimeout(function() {
							d.close().remove();
						}, 1500);
					}
				},
				error : function(data) {
					if(data.status==404)
						dialog.alert("请求地址不存在");
					else if(data.status==500)
						dialog.alert("系统内部错误");
					else if(data.status==200){
						location.reload();
					}
					else dialog.alert("通信异常");
				}
			};
			if ($("#respondForm" + num).children("[name=commentContent]").val() != "")
				$("#respondForm" + num).ajaxSubmit(options);
			else
				dialog.alert("请填写评论内容");
		}
		$(function() {
			$('[data-toggle="tooltip"]').tooltip({
				html : true
			});
			$('.time-picker').datetimepicker({
    			format: 'yyyy-mm-dd hh:ii:ss',
    			autoclose : true,
    			todayBtn : true,
    			todayHighlight : true,
    			language : 'zh-CN'
			});
		});
		function toLogin() {
			location.href = "op/login/goLogin?redirect_to=${theme.url}%23respond";
		}
	</script>
	
	<!-- 百度一件分享 -->
	<script>
		window._bd_share_config = {
			
		"common" : {
				"bdSnsKey" : {
					"tsina" : "${weibo}",
					"tqq" : "${setting.txAppKey}"
				},
				"bdText" : "",
				"bdMini" : "2",
				"bdMiniList" : false,
				"bdPic" : "",
				"bdStyle" : "1",
				"bdSize" : "16"
			},
			"share" : {},
			"image" : {
				"viewList" : [ "qzone", "tsina", "tqq", "renren", "weixin" ],
				"viewText" : "分享到：",
				"viewSize" : "16"
			}
		};
		with (document)
			0[(getElementsByTagName('head')[0] || body)
					.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
					+ ~(-new Date() / 36e5)];
	</script>
</body>
</html>
