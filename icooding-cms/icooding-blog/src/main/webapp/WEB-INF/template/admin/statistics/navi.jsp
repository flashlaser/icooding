<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="reinforce-sidebar" data-left="${param.index-1}">
	<!-- List group -->
	<ul class="reinforce-menu">
		<li>
			<a href="javascript:;" class="list-title" rel="nofollow"><i class="fa fa-files-o"></i>&nbsp;主题统计</a>
			<ul>
				<li><a href="/admin/statistics/theme/0" class="list-title" rel="nofollow">--最近7天</a></li>
				<li><a href="/admin/statistics/theme/1" class="list-title" rel="nofollow">--今年内</a></li>
			</ul>
		</li>
		<li>
			<a href="javascript:;" class="list-title" rel="nofollow"><i class="fa fa-files-o"></i>&nbsp;流量统计</a>
			<ul>
				<li><a href="/admin/statistics/browse/0" class="list-title" rel="nofollow">--最近30天</a></li>
				<li><a href="/admin/statistics/browse/1" class="list-title" rel="nofollow">--今年内</a></li>
			</ul>
		</li>
	</ul>
</div>
