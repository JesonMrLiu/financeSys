<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>财务管理系统 -- 账单管理</title>
  </head>
  
  <body>
    <div class="page-content">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<h3 class="page-title">账单管理<small>&nbsp;显示账单记录情况</small></h3>
					<ul class="breadcrumb">
						<li>
							<i class="icon-home"></i> 
							<a href="homeController.do?method=home">首页</a> 
							<i class="icon-angle-right"></i>
							<a href="">记账</a>
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
						<span>&nbsp;&nbsp;年份：</span>
						<select class="select-select" name="y">
							<option value="">----请选择----</option>
							<c:forEach items="${years}" var="item">
								<option value="${item}" <c:if test="${(param.y eq item) or (curYear eq item)}">selected</c:if>>${item}</option>
							</c:forEach>
						</select>
						<span>&nbsp;&nbsp;月份：</span>
						<select class="select-select" name="m">
							<option value="">----请选择----</option>
							<c:forEach items="${months}" var="item">
								<option value="${item.key}" <c:if test="${(param.m eq item.key) or (curMonth eq item.key)}">selected</c:if>>${item.value}</option>
							</c:forEach>
						</select>
						<a class="btn btn-info" onclick="javascript:$(this).parents('form').submit();">查 询</a>
						<a class="btn blue" onclick="">添加</a>
						<a class="btn red" onclick="">导出Excal</a>
						<a class="btn red" onclick="importXls();">导入Excal</a>
					</form>
				
					<div class="span">
						<div class="portlet box">
							<div class="portlet-body">
								<table class="table table-striped table-hover" style="border-top:1px solid #dedddd;">
									<thead>
										<tr>
											<th>#</th>
											<th>标题</th>
											<th>记账日期</th>
											<th>收支类型</th>
											<th>金额</th>
											<th>总金额</th>
											<th>账目类型</th>
											<th>付款方</th>
											<th>收款方</th>
											<th>备注</th>
											<th>操作</th>
										</tr>
									</thead>
					
									<tbody>
										<c:if test="${fn:length(dataList) gt 0}">
											<c:forEach items="${dataList}" var="item" varStatus="status">
												<tr id="row${item.id}">
													<td>${status.index+1}</td>
													<td>${item.title}</td>
													<td><fmt:formatDate value="${item.tallyDate}" pattern="yyyy-MM-dd"/></td>
													<td>${item.payType eq 1 ? "支出" : "收入"}</td>
													<td><fmt:formatNumber value="${item.amount}" pattern="#0.0#"/></td>
													<td><fmt:formatNumber value="${item.totalAmount}" pattern="#0.0#"/></td>
													<td>${item.typeName}</td>
													<td>${item.payer}</td>
													<td>${item.payee}</td>
													<td title="${item.remark}">${fn:substring(item.remark, 0, 30)}...</td>
													<td>
														<a href="#" class="btn mini blue" onclick="deleteById(${item.id});"><i class="icon-edit"></i>修改</a>
														<a href="#" class="btn mini red" onclick="deleteById(${item.id});"><i class="icon-trash"></i>删除</a>
													</td>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${fn:length(dataList) le 0}">
											<tr>
											<td colspan="11" style="border:1px solid #d0cfcf;text-align:center;padding:5px auto;color:red;"><i class="icon-warning-sign"></i>抱歉，没有数据</td>
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
	
	<!-- 文件上传弹出框内容 -->
	<div id="importXls" class="hide">
		<form action="" enctype="multipart/form-data" method="post">
			<input type="hidden" name="method" value="importDatas"/>
			<div class="control-group">
				<span>请选择文件：</span>
				<input type="file" name="file" multiple="true" except="xls|xlsx"/>
			</div>
		</form>
		<a class="btn btn-red" onclick="doImport();">导入数据</a>
	</div>
	<script type="text/javascript">
		function importXls(){
			var html = $("#importXls").clone();
			$(html).find("form").attr("id", "autoform");
			LaModal.initModal({cont:html.show(),title:"导入记账数据"});
		}
		
		function doImport(){
			var file = $("#autoform input[name=files]").val();
			if(file == ""){
				alert("抱歉，请先选择需要导入的文件！！");
				return;
			}
			$("#autoform").submit();
		}
	</script>
  </body>
</html>
