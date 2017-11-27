<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title></title>
<link rel="stylesheet" href="static/css/ui-dialog.css">
<script src="static/js/jquery/jquery-1.11.1.min.js"></script>
<script src="static/js/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript">
	$(function() {
		setTimeout(function(){location.href = "op/login/goLogin";},1000);
	});
</script>
</head>
<body>
<strong>登陆超时，请重新登陆</strong>
</body>
</html>