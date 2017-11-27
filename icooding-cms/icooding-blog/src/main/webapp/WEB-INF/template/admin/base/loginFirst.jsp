<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<base href="/">

<title></title>

<link rel="stylesheet" href="static/css/ui-dialog.css">
<script src="static/js/jquery/jquery-1.11.1.min.js"></script>
<script src="static/js/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript">
	$(function() {
		var d = dialog({
			content : "登录超时，请重新登录",
			width : "100px",
			okValue : '确定',
			ok : function() {
				window.location.href = "op/login/toLogin";
			}
		});
		d.show();

	});
</script>
</head>
</html>