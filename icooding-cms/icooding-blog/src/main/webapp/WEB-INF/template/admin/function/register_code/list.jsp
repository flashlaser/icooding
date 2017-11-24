<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <title>注册码 - ${setting.appName}</title>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="keywords" content="">
    <meta http-equiv="description" content="">
    <c:import url="/op/base/admin/head"></c:import>
</head>

<body>
<c:import url="/op/base/admin/header">
    <c:param name="header_index" value="2" />
</c:import>
<div class="container-fluid">
    <div class="reinforce-main region">
        <c:import url="../navi.jsp">
            <c:param name="index" value="7" />
        </c:import>
        <div class="reinforce-content">
            <div class="site">
                <div class="site_left">
                    <h5>
                        <a href="javascript:;" rel="nofollow">管理中心</a> > 注册码
                    </h5>
                </div>
            </div>
            <div class="page">
                <a class="btn btn-primary pull-right mg-t-10" href="admin/registerCode/create">
                    <span class="glyphicon glyphicon-plus"> </span> 创建10个注册码
                </a>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <td>序号</td>
                        <td>注册码</td>
                        <td>是否可用</td>
                        <td>激活用户</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${lists}" var="list" varStatus="var">
                        <tr>
                            <td>${((curPage-1)*pageSize)+(var.index+1)}</td>
                            <td>${list.code}</td>
                            <td>${list.status==0?"已激活":"未激活"}</td>
                            <td>${list.username}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="page-num">
                    <c:import url="/op/page/1/${curPage}/?pageSize=${pageSize}&count=${count}"></c:import>
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
<c:import url="/op/base/foot"></c:import>
</body>
</html>
