<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
	<span style="float:right;margin-right:20px;">
		<a href="?method=profitLoss" class="tjBtn leftBtn ${param.method eq 'profitLoss' ? 'tjActive' : ''}" title="盈亏统计图表">&nbsp;<i class="icon-bar-chart"></i>&nbsp;</a>
		<a href="?method=profitLoss_tb" class="tjBtn rightBtn ${param.method eq 'profitLoss_tb' ? 'tjActive' : ''}" title="盈亏统计表格">&nbsp;<i class="icon-reorder"></i>&nbsp;</a>
	</span>
</form>

<div class="span">
	<div class="portlet box">
		<div class="portlet-body">
			<table class="table table-striped table-hover" style="border-top:1px solid #dedddd;">
				<thead>
					<tr>
						<th>#</th>
						<th>理财分类</th>
						<th>盈亏金额</th>
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
								<td class="${item.profitOrLossAmount gt 0 ? 'red' : 'green'}">
									<fmt:formatNumber value="${item.profitOrLossAmount}" pattern="#0.0#"/>&nbsp;
									<i class="${item.profitOrLossAmount gt 0 ? 'icon-long-arrow-up' : 'icon-long-arrow-down'}"></i>
								</td>
								<td><fmt:formatDate value="${item.profitLossDate}" pattern="yyyy-MM-dd"/></td>
								<td>
									<a href="#" class="btn mini purple" onclick="doProfitLoss(${item.id});"><i class="icon-edit"></i>修改</a>
									<a href="#" class="btn mini black" onclick="deleteById(${item.id});"><i class="icon-trash"></i>删除</a>
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

<!-- 新增，修改弹出框中表单代码 -->
<div id="modal-profitloss" class="hide">
	<form action="#" class="form-horizontal" onsubmit="return false;">
		<input type="hidden" name="id" value="" id="md-id">
		<input type="hidden" name="oldAmount" id="md-old-amount" value="0"/>
		<div class="control-group">
			<label class="control-label" for="inputWarning">理财分类：</label>
			<div class="controls">
				<select name="md-code" id="md-code">
					<c:forEach items="${lcfl}" var="item">
						<option value="${item.key}">${item.value}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="inputSuccess">盈亏金额：</label>
			<div class="controls">
				<input type="text" name="amount" id="md-amount" valid-options="{'type':'double','inputName':'盈亏金额','required':true}" onfocus="LaValidForm.focusValid({'obj':this})" onblur="LaValidForm.blurValid({'obj':this});">
				<br><span class="help-inline"></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="inputSuccess">盈亏日期：</label>
			<div class="controls">
				<input type="text" name="ysrq" id="ysrq" valid-options="{'type':'date','inputName':'盈亏日期','required':true}" onfocus="LaValidForm.focusValid({'obj':this})" onblur="LaValidForm.blurValid({'obj':this});" readonly>
				<i class="datepicker icon-calendar"></i>
				<br><span class="help-inline"></span>
			</div>
		</div>
		
		<button type="submit" class="btn btn-red" onclick="saveProfitLoss();">保 存</button>
	</form>
</div>

<script type="text/javascript">
	function doProfitLoss(id){
		var html = $("#modal-profitloss").clone();
		if(id != undefined && id > 0){
			$.ajax({
				url : "?method=getProfitLossById",
				data : {"id" : id},
				dataType : "JSON",
				type : "post",
				success : function(result){
					var data = result.data;
					$(html).find("#md-code").val(data.code);
					$(html).find("#md-amount").val(data.profitOrLossAmount);
					$(html).find("#md-old-amount").val(data.profitOrLossAmount);
					$(html).find("#ysrq").val(data.profitLossDateStr);
					$(html).find("#md-id").val(id);
				},
			});
		}
		$(html).find("form").attr("id", "autoform");
		$(html).find(".datepicker").datetimepicker({language: "zh-CN",minView: "month",autoclose:true,todayButton:true,endDate : new Date(),format: "yyyy-mm-dd"})
				.on("changeDate", function(ev){
					var selectDate = ev.date;
					var month = selectDate.getMonth() + 1;
					month = month < 10 ? ("0" + month) : month;
					var day = selectDate.getDate();
					day = day < 10 ? ("0" + day) : day;
					var temp = selectDate.getFullYear()+ "-" + month + "-" + day;
					$(html).find("#ysrq").val(temp);
				});
		var title = id != undefined ? "修改盈亏记录" : "新增盈亏记录";
		LaModal.initModal({title:title,cont:html.show(),width:560});
		
	}
	
	function saveProfitLoss(){
		var valid = LaValidForm.valid("#autoform");
		if(valid){
			$.ajax({
				url : "?method=doProfitLoss",
				data : $("#autoform").serialize(),
				dataType : "json",
				type : "post",
				success : function(result){
					if(result.suc){
						window.location.href = "";
					} else {
						LaModal.alert("<i class='icon-warning-sign'></i>"+result.info);
					}
				}
			});
		}
	}
</script>