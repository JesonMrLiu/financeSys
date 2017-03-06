<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>理财分类提现、入账记录</title>
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
							<a href="">提现入账记录</a>
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
								<option value="${item.key}" <c:if test="${param.code eq item.key}">selected</c:if>>${item.value}</option>
							</c:forEach>
						</select>
						<span>开始日期：</span>
						<input type="text" class="control-line datepicker select-input" id="startdate" name="startdate" placeholder="开始日期" style="cursor:pointer;" value="${param.startdate}" readonly/>
						<span>结束日期：</span>
						<input type="text" class="control-line datepicker select-input" id="enddate" name="enddate" placeholder="结束日期" style="cursor:pointer;" value="${param.enddate}" readonly/>
						&nbsp;&nbsp;
						<a class="btn btn-info" onclick="javascript:$(this).parents('form').submit();">查 询</a>
						<a class="btn red" onclick="doProfitLoss();">新增</a>
					</form>
					
					<div class="span">
						<div class="portlet box">
							<div class="portlet-body">
								<table class="table table-striped table-hover" style="border-top:1px solid #dedddd;">
									<thead>
										<tr>
											<th>#</th>
											<th>理财分类</th>
											<th>提现/入账</th>
											<th>时间</th>
											<th>操作</th>
										</tr>
									</thead>
					
									<tbody>
										<c:if test="${fn:length(dataList) gt 0}">
											<c:forEach items="${dataList}" var="item" varStatus="status">
												<tr id="row${item.id}">
													<td>${status.index+1}</td>
													<td>${item.name}</td>
													<td class="${item.amount ge 0 ? 'red' : 'green'}">${item.amount ge 0 ? "追投：" : "提现："}<fmt:formatNumber value="${item.amountAbs}" pattern="#0.0#"/></td>
													<td><fmt:formatDate value="${item.invertTime}" pattern="yyyy-MM-dd"/></td>
													<td>
														<a href="#" class="btn mini purple" onclick="doProfitLoss(${item.id});"><i class="icon-edit"></i>修改</a>
														<a href="#" class="btn mini black" onclick="deleteById(${item.id}, 'deleteRecordById');"><i class="icon-trash"></i>删除</a>
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
	
	<!-- 提现投入表单代码 -->	
	<div id="withdrawInvest" class="hide">
		<form action="#" class="form-horizontal" onsubmit="return false;">
			<input type="hidden" name="id" id="md-id" value="">
			<input type="hidden" name="oldAmount" id="md-old-amount" value="0"/>
			<div class="control-group">
				<label class="control-label" for="inputWarning">分类名称：</label>
				<div class="controls">
					<select name="code" id="md-code">
						<c:forEach items="${lcfl}" var="item">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="inputWarning">操作类型：</label>
				<div class="controls">
					<select name="md-type" id="md-type">
						<option value="1">追投</option>
						<option value="2">提现</option>
					</select>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="inputSuccess">时间：</label>
				<div class="controls">
					<input type="text" name="investTime" id="md-investTime" valid-options="{'type':'date','inputName':'时间','required':true}" onfocus="LaValidForm.focusValid({'obj':this})" onblur="LaValidForm.blurValid({'obj':this});" readonly>
					<i class="datepicker icon-calendar"></i>
					<br><span class="help-inline"></span>
				</div>
			</div>
	
			<div class="control-group">
				<label class="control-label" for="inputSuccess">金额：</label>
				<div class="controls">
					<input type="text" name="amount" id="md-addAmount" valid-options="{'type':'double','inputName':'金额','required':true}" onfocus="LaValidForm.focusValid({'obj':this})" onblur="LaValidForm.blurValid({'obj':this});">
					<br><span class="help-inline"></span>
				</div>
			</div>
	
			<button type="submit" class="btn btn-red" onclick="saveWithdrawInvert();">保 存</button>
		</form>
	</div>
	<script type="text/javascript">
		function doProfitLoss(id){
			var html = $("#withdrawInvest").clone();
			$(html).find("form").attr("id", "autoform");
			$(html).find(".datepicker").datetimepicker({language: "zh-CN",minView: "month",autoclose:true,todayButton:true,endDate : new Date(),format: "yyyy-mm-dd"})
				.on("changeDate", function(ev){
					var selectDate = ev.date;
					var month = selectDate.getMonth() + 1;
					month = month < 10 ? ("0" + month) : month;
					var day = selectDate.getDate();
					day = day < 10 ? ("0" + day) : day;
					var temp = selectDate.getFullYear()+ "-" + month + "-" + day;
					$(html).find("#md-investTime").val(temp);
				});
			if(id != undefined && id > 0){
				$(html).find("#md-id").val(id);
				$.ajax({
					url : "?method=getWithdrawInvestById",
					data : {"id" : id},
					dataType : "json",
					type : "post",
					success : function(result){
						if(result.suc){
							var data = result.data;
							$(html).find(".md-name").text(data.name);
							$(html).find("#md-type").val(data.amount > 0 ? 1 : 2);
							$(html).find("#md-addAmount").val(Math.abs(data.amount));
							$(html).find("#md-old-amount").val(data.amount);//修改前的金额
							$(html).find("#md-investTime").val(data.investTimeStr);
							$(html).find("#md-code").val(data.code);
							LaModal.initModal({title:"修改提现追投",cont:html.show(),width:560});
						} else {
							LaModal.alert(result.info);
						}
					}
				});
			} else {
				LaModal.initModal({title:"新增提现追投",cont:html.show(),width:560});
			}
		}
		
		function saveWithdrawInvert(){
			var valid = LaValidForm.valid("#autoform");
			if(valid){
				console.log($("#autoform").serialize());
				$.ajax({
					url : "?method=doWithdrawInvest",
					data : $("#autoform").serialize(),
					dataType : "json",
					type : "post",
					success : function(result){
						if(result.suc){
							window.location.href = "";
						} else {
							LaModal.alert(result.info);
						}
					}
				});
			}
		}
	</script>
</body>
</html>