<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>已删除理财分类</title>
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
							<a href="">已删除理财分类</a>
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
					<div class="span">
						<div class="portlet box">
							<div class="portlet-body">
								<table class="table table-striped table-hover" style="border-top:1px solid #dedddd;">
									<thead>
										<tr>
											<th>#</th>
											<th>理财分类</th>
											<th>当前总额</th>
											<th>删除时间</th>
											<th>操作</th>
										</tr>
									</thead>
					
									<tbody>
										<c:if test="${fn:length(dataList) gt 0}">
											<c:forEach items="${dataList}" var="item" varStatus="status">
												<tr id="row${item.id}">
													<td>${status.index+1}</td>
													<td>${item.name}</td>
													<td><fmt:formatNumber value="${item.currentTotalAmount}" pattern="#0.0#"/>&nbsp;</td>
													<td><fmt:formatDate value="${item.deleteTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
													<td>
														<a href="#" class="btn mini red" onclick="deleteById(${item.id},'deleteFenleiById');"><i class="icon-trash"></i>彻底删除</a>
													</td>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${fn:length(dataList) le 0}">
											<tr>
											<td colspan="5" style="border:1px solid #d0cfcf;text-align:center;padding:5px auto;color:red;"><i class="icon-warning-sign"></i>抱歉，没有数据</td>
											</tr>
										</c:if>
									</tbody>
								</table>
								${pager.html}
							</div>
						</div>
					</div>
				</div>
			</div>
				
		</div>
	</div>
  </body>
</html>
