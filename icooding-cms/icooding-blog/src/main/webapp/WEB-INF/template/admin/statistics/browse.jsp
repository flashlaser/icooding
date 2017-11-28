<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="/">


    <title>${setting.siteName}--消息统计</title>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <c:import url="/op/base/admin/head"></c:import>
    <style type="text/css">
        .input-group {
            width: 100%;
        }

        .table thead tr td {
            font-weight: bold;
        }

        .del {
            height: 22px !important;
        }
    </style>
</head>

<body>
<c:import url="/op/base/admin/header">
    <c:param name="header_index" value="3"/>
</c:import>
<div class="container-fluid">
    <div class="reinforce-main region">
        <c:import url="navi.jsp">
            <c:param name="index" value="3"/>
        </c:import>
        <div class="reinforce-content">
            <div class="site">
                <div class="site_left">
                    <h5>
                        <a href="javascript:;" rel="nofollow">管理中心</a> ><a href="timeline/element/index">统计</a> > 流量统计
                    </h5>
                </div>
                <div class="site_right">
                    <button class="btn btn-primary" onclick="location.href='admin/theme/theme'">
                        <span class="glyphicon glyphicon-plus"> </span> 添加新消息
                    </button>
                </div>
            </div>
            <div class="page">
                <c:import url="/op/loading?type=4"></c:import>
                <div id="chart" style="height:500px">
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="/op/base/foot"></c:import>
<script src="static/js/echars/echarts-all.js"></script>
<script type="text/javascript">
    $(function () {
        bar()
    });

    function bar() {
        $(".l-wrapper").show();
        var url  = "${type==1?'admin/statistics/getBrowseData/year':'admin/statistics/getBrowseData/month'}";
        $.ajax({
            type: "post",
            url: url,
            dataType: 'json',
            success: function (data) {
                console.log(data)
                $(".l-wrapper").hide();
                var myChart = echarts.init(document.getElementById('chart'));
                option = {
                    title: {
                        text: '流量统计',
                        subtext: ''
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: data.legend,
                        padding: 30
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {
                                show: true
                            },
                            dataView: {
                                show: true,
                                readOnly: true
                            },
                            magicType: {
                                show: true,
                                type: ['bar']
                            },
                            restore: {
                                show: true
                            },
                            saveAsImage: {
                                show: true
                            }
                        },
                        padding: 1
                    },
                    calculable: true,
                    xAxis: [{
                        type: 'category',
                        data: data.xAxis
                    }],
                    yAxis: [{
                        type: 'value'
                    }],
                    series: data.series
                };
                myChart.setOption(option);
            }
        });
    }
</script>
</body>
</html>
