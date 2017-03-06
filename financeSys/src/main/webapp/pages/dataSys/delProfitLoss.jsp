<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>已删除盈亏数据</title>
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
							<a href="">已删除盈亏数据</a>
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
					<form class="form-horizontal" action="">
						<input type="hidden" name="method" value="${param.method}"/>
						<span>&nbsp;&nbsp;理财分类：</span>
						<select class="select-select" name="code">
							<option value="">----请选择----</option>
							<c:forEach items="${lcfl}" var="item">
								<option value="${item.key}" <c:if test="${param.code eq item.key ? 'selected' : ''}"></c:if>>${item.value}</option>
							</c:forEach>
						</select>
						<span>开始日期：</span>
						<input type="text" class="control-line datepicker select-input" id="startdate" name="startdate" placeholder="开始日期" style="cursor:pointer;" value="${param.startdate}" readonly/>
						<span>结束日期：</span>
						<input type="text" class="control-line datepicker select-input" id="enddate" name="enddate" placeholder="结束日期" style="cursor:pointer;" value="${param.enddate}" readonly/>
						&nbsp;&nbsp;
						<a class="btn btn-info" onclick="javascript:$(this).parents('form').submit();">查 询</a>
					</form>
				
					<div class="span">
						<div class="portlet box">
							<div class="portlet-body">
								<table class="table table-striped table-hover" style="border-top:1px solid #dedddd;">
									<thead>
										<tr>
											<th>#</th>
											<th>理财分类</th>
											<th>盈亏数额</th>
											<th>盈亏日期</th>
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
													<td class="${item.profitOrLossAmount gt 0 ? 'red' : 'green'}">
														<fmt:formatNumber value="${item.profitOrLossAmount}" pattern="#0.0#"/>&nbsp;
														<i class="${item.profitOrLossAmount gt 0 ? 'icon-long-arrow-up' : 'icon-long-arrow-down'}"></i>
													</td>
													<td><fmt:formatDate value="${item.profitLossDate}" pattern="yyyy-MM-dd"/></td>
													<td><fmt:formatDate value="${item.deleteTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
													<td>
														<a href="#" class="btn mini red" onclick="deleteById(${item.id},'deleteProfitLossById','删除不可逆转，是否确定继续删除？');"><i class="icon-trash"></i>彻底删除</a>
													</td>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${fn:length(dataList) le 0}">
											<tr>
											<td colspan="6" style="border:1px solid #d0cfcf;text-align:center;padding:5px auto;color:red;"><i class="icon-warning-sign"></i>抱歉，没有数据</td>
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
