<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav class="navbar  navbar-inverse navbar-fixed-top" id="header">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                    data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="    line-height: 30px;" href="http://${appUrl}">
                <%--<img src="static/images/logo-white.png" width="40" height="40" alt="${setting.appName}" >--%>
                首页
            </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse"
             id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-main">
                <c:forEach items="${navis}" var="navi">
                    <c:if test="${fn:length(navi.children)==0}">
                        <li><a href="${navi.url}" url="${navi.url}">${navi.name}</a></li>
                    </c:if>
                    <c:if test="${fn:length(navi.children)>0}">
                        <li class="dropdown">
                            <a href="javascript:;" target="${navi.target}" class="dropdown-toggle"
                               data-toggle="dropdown" role="button"
                               aria-expanded="false" url="${navi.childUrl}"> ${navi.name} <span
                                    class="caret"></span>
                            </a>
                            <ul class="dropdown-menu" role="menu">
                                <c:forEach items="${navi.children}" var="n">
                                    <li><a href="${n.url}">${n.name}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>


            <ul class="nav navbar-nav navbar-right">
                <form class="navbar-form navbar-left" role="search" action="javascript:opensearch()">
                    <div class="form-group has-feedback">
                        <input type="text" class="form-control" placeholder="Java" id="keyword"/>
                        <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
                    </div>
                </form>
                <c:if test="${userSession==null}">
                    <li><a href="op/register/goRegister"><img src="static/images/filetype/34.png"
                                                              style="margin-right: 4px; float: left; top: 0px;"
                                                              width="18" height="18" alt="注册"/>注册</a></li>
                    <li><a href="op/login/toLogin"><img src="static/images/filetype/35.png"
                                                        style="margin-right: 4px; float: left; top: 0px;" width="18"
                                                        height="18" alt="登录"/>登录</a></li>
                </c:if>
                <c:if test="${userSession!=null}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle user-info" data-toggle="dropdown" role="button"
                           aria-expanded="false" rel="nofollow">
                            <img src="${userSession.user.headIconSmall}" class="img-circle" width=30
                                 alt="${userSession.user.nickName}"/>
                                ${userSession.user.nickName}<span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <c:if test="${userSession!=null&&userSession.user.userType==1}">
                                <li><a href="admin/siteSet/siteInfo" rel="nofollow"><span  class="glyphicon glyphicon-align-justify"></span>&nbsp;&nbsp;管理中心</a></li>
                            </c:if>
                            <c:if test="${userSession!=null&&userSession.user.userType==1}">
                                <li><a href="profile/myTheme" rel="nofollow"><span  class="glyphicon glyphicon-edit"></span>&nbsp;&nbsp;我的文章</a></li>
                            </c:if>
                            <li><a href="profile/basicInfo"><span
                                    class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;个人资料</a></li>
                            <li><a href="profile/headIcon"><span
                                    class="glyphicon glyphicon-picture"></span>&nbsp;&nbsp;修改头像</a></li>
                            <li><a href="#" rel="nofollow"><span
                                    class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;修改密码</a></li>
                            <li class="divider"></li>
                            <li><a href="javascript:logout()"><span
                                    class="glyphicon glyphicon-log-out"></span>&nbsp;&nbsp;退出</a></li>
                        </ul>
                    </li>

                </c:if>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>

