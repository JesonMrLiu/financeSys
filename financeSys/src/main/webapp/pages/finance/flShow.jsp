<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>财务管理系统 -- 理财分类</title>
<style type="text/css">
	.panel {margin-bottom: 20px;background-color: #fff;border: 1px solid transparent;border-radius: 4px;-webkit-box-shadow: 0 1px 1px rgba(0,0,0,.05);box-shadow: 0 1px 1px rgba(0,0,0,.05);}
	.panel-primary {border-color: #337ab7;}
</style>
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
							<a href="index.html">首页</a> 
							<i class="icon-angle-right"></i>
							<a href="">理财分类</a>
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
			
			<!-- 展示理财分类图标 -->
			<div class="row-fluid">
				<div class="row-fluid margin-bottom-40">
					<c:forEach items="${dataList}" var="item">
						<div class="span3 pricing pricing-active hover-effect item" id="row${item.id}">
							<div class="pricing-head pricing-head-active">
								<h3>${item.name}<span></span></h3>
								<h4><i>￥</i>${item.totalAmountZhengShu}<i>${empty item.totalAmountXiaoShu ? '.0' : item.totalAmountXiaoShu}</i><%--<span>&gt;&gt;总金额&lt;&lt;</span>--%></h4>
							</div>
							
							<ul class="pricing-content unstyled">
								<li><b>初始投入：</b> <fmt:formatNumber value="${item.firstAmount}" pattern="##.0#"/> </li>
								<li><b>昨日盈亏：</b><span class="${item.lastProfitOrLoss ge 0 ? 'red' : 'green'}"> ${item.lastProfitOrLoss ge 0 ? "+" : ""}<fmt:formatNumber value="${empty item.lastProfitOrLoss ? 0.0 : item.lastProfitOrLoss}" pattern="##0.0##"/>&nbsp;&nbsp;<i class="${item.lastProfitOrLoss ge 0 ? 'icon-long-arrow-up' : 'icon-long-arrow-down'}"></i></span></li>
								<li><b>盈亏总额：</b><span class="${item.totalProfitOrLossAmount ge 0 ? 'red' : 'green'}"> ${item.totalProfitOrLossAmount ge 0 ? '+' : ''}<fmt:formatNumber value="${empty item.totalProfitOrLossAmount ? 0.0 : item.totalProfitOrLossAmount}" pattern="##0.0##"/>&nbsp;&nbsp;<i class="${item.totalProfitOrLossAmount ge 0 ? 'icon-long-arrow-up' : 'icon-long-arrow-down'}"></i></span></li>
								<li><b>累计追投：</b><span class="red"> <fmt:formatNumber value="${empty item.investInTotal ? 0.0 : item.investInTotal}" pattern="##0.0##"/></span></li>
								<li><b>累计提现：</b><span class="green"> <fmt:formatNumber value="${empty item.withdrawalsInTotalAbs ? 0.0 : item.withdrawalsInTotalAbs}" pattern="##0.0##"/></span></li>
							</ul>
							
							<div class="pricing-footer">
								<p></p>
								<a href="#" data-toggle="modal" class="btn red" onclick="addInvest(${item.code}, '${item.name}', 1);">追投 </a>&nbsp;&nbsp; 
								<a href="#" data-toggle="modal" class="btn red" onclick="addInvest(${item.code}, '${item.name}', 2);">提现 </a>&nbsp;&nbsp;  
								<a href="?method=profitLoss" data-toggle="modal" class="btn red">盈亏</a>  
							</div>
							
							<div class="details">
								<a class="icon" onclick="doFenLei(${item.id});" title="修改"><i class="icon-pencil"></i></a>
								<a class="icon" onclick="deleteFlById(${item.id}, 'deleteFlById')" title="删除"><i class="icon-remove"></i></a>    
							</div>
						</div>
					</c:forEach>
					<div class="span3 pricing pricing-addfl" style="border-color:#b5b4b4;">
						<div class="addflicondiv" onclick="doFenLei();" onmouseover="getFocus()" onmouseout="leave();"><i class="icon-plus"></i></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 增加投入表单代码 -->	
	<div id="addInvest" class="hide">
		<form action="#" class="form-horizontal" onsubmit="return false;">
			<input type="hidden" name="code" id="fl-code" value=""/>
			<input type="hidden" name="type" id="fl-type" value=""/>
			<div class="control-group">
				<label class="control-label" for="inputWarning">分类名称：</label>
				<div class="controls"><span class="fl-name"></span></div>
			</div>
	
			<div class="control-group">
				<label class="control-label" for="inputSuccess">金额：</label>
				<div class="controls">
					<input type="text" name="amount" id="fl-addAmount" valid-options="{'type':'double','inputName':'金额','required':true}" onfocus="LaValidForm.focusValid({'obj':this})" onblur="LaValidForm.blurValid({'obj':this});">
					<br><span class="help-inline"></span>
				</div>
			</div>
	
			<button type="submit" class="btn btn-red" onclick="saveFinance();">保 存</button>
		</form>
	</div>
	
	<!-- 添加理财分类 -->
	<jsp:include page="addFl.jsp"/>
	
	<script type="text/javascript">
		$(function(){
			UIModals.init();
			setInterval(function(){
				var fatherWidth = $(".row-fluid.margin-bottom-40").width();
				var itemWidth = $(".row-fluid.margin-bottom-40 .span3.pricing").width();
				var count = parseInt(fatherWidth / itemWidth);
				$(".row-fluid.margin-bottom-40 .span3.pricing").eq(count).removeClass("card-left-zero");
				$(".row-fluid.margin-bottom-40 .span3.pricing").eq(count).addClass("card-left-zero");
			}, 100);
		});
		
		function deleteFlById(id){
			text = "<i class='icon-warning-sign'></i>&nbsp;警告，删除后不可逆转，你确定要删除吗？";
			LaModal.alert(text,{width:420,fn:function(){
				$.ajax({
					url : "?method=deleteFlById",
					data : {"id":id},
					dataType : "json",
					type : "post",
					success : function(result){
						if(result.suc){
							window.location.href = "";
						} else {
							LaModal.alert(result.info);
						}
					}
				})
				
			}})
		}
		
		function addInvest(code, name, type){
			var html = $("#addInvest").clone();
			html.removeClass("hide");
			$(html).find("form").attr("id", "autoform");
			$(html).find("input#fl-code").val(code);
			$(html).find("span.fl-name").text(name);
			$(html).find("#fl-type").val(type);
			var title = type == 1 ? "追投" : "提现";
			LaModal.initModal({cont:html.show(),title:title});
		}
		
		function saveFinance(){
			var result = LaValidForm.valid("#autoform");
			if(result){
				$.ajax({
					url : "?method=addFinanceRecord",
					data : $("#autoform").serialize(),
					dataType : "JSON",
					type : "POST",
					success : function(res){
						if(res.suc){
							LaModal.alert(res.info, {fn:function(){
								window.location.href = "";
							}});
						} else {
							LaModal.alert(res.info);
						}
					},
					error : function(res){
						console.log(res.responseText);
					}
				});
			}
		}
		
		function getFocus(){
			$(".addflicondiv").css('border','3px dashed #000');
			$(".addflicondiv i").css('color', '#000');
		}
		function leave(){
			$(".addflicondiv").css('border','2px dashed #d2cece');
			$(".addflicondiv i").css('color', '#cac5c5');
		}
		function sendAdd(){
			window.location.href = "?method=addFenLei";
		}
	</script>
</body>
</html>
