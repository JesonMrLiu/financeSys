<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>理财分类盈亏统计</title>
</head>
<body>
	<div class="page-content">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<h3 class="page-title">理财分类 <small>展示理财分类情况</small></h3>
					<ul class="breadcrumb">
						<li>
							<i class="icon-home"></i> 
							<a href="homeController.do?method=home">首页</a> 
							<i class="icon-angle-right"></i>
							<a href="">盈亏统计</a>
						</li>

						<li class="pull-right no-text-shadow">
							<div id="dashboard-report-range" class="dashboard-date-range tooltips no-tooltip-on-touch-device responsive" data-tablet="" data-desktop="tooltips" data-placement="top" data-original-title="Change dashboard date range">
								<i class="icon-calendar"></i> 
								<span></span> 
								<i class="icon-angle-down"></i>
							</div>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="row-fluid">
				<div class="row-fluid margin-bottom-40">
				
					<!-- 盈亏chart图表 -->
					<c:if test="${param.method eq 'profitLoss'}">
						<jsp:include page="profitLoss_chart.jsp"/>
					</c:if>
					
					<!-- 盈亏表格展示 -->
					<c:if test="${param.method eq 'profitLoss_tb'}">
						<jsp:include page="profitLoss_table.jsp"/>
					</c:if>
				</div>
			</div>
				
		</div>
	</div>
</body>
</html>