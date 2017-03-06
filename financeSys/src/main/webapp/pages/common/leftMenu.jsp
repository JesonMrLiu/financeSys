<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="page-sidebar nav-collapse collapse">
	<ul class="page-sidebar-menu">
		<li class="start ${param.method eq 'home' ? 'active' : ''}">
			<a href="homeController.do?method=home">
				<i class="icon-home"></i> 
				<span class="title">首页</span>
				<span class="selected"></span>
			</a>
		</li>

		<li class="${fn:contains('fenlei,addFenLei,profitLoss,profitLoss_tb,withdrawRecord', param.method) ? 'active' : ''}">
			<a href="javascript:;">
				<i class="icon-briefcase"></i> 
				<span class="title">理财管理</span>
				<span class="arrow ${fn:contains('fenlei,addFenLei,profitLoss,profitLoss_tb,withdrawRecord', param.method) ? 'open' : ''}"></span>
			</a>
			
			<ul class="sub-menu">
				<li class="${fn:contains('fenlei,addFenLei', param.method) ? 'active' : ''}"><a href="financialController.do?method=fenlei">理财分类</a></li>
				<li class="${fn:contains('withdrawRecord', param.method) ? 'active' : ''}"><a href="financialController.do?method=withdrawRecord">提现入账记录</a></li>
				<li class="${fn:contains('profitLoss,profitLoss_tb', param.method) ? 'active' : ''}"><a href="financialController.do?method=profitLoss">盈亏统计</a></li>
			</ul>
		</li>
		
		<li class="${fn:contains('tally,addTally', param.method) ? 'active' : ''}">
			<a href="javascript:;">
				<i class="icon-file-text"></i> 
				<span class="title">账单管理</span>
				<span class="arrow ${fn:contains('tally,addTally', param.method) ? 'open' : ''}"></span>
			</a>
			
			<ul class="sub-menu">
				<li class="${fn:contains('tally,addTally', param.method) ? 'active' : ''}"><a href="tallyController.do?method=tally">记账</a></li>
			</ul>
		</li>
		
		<li class="${fn:contains('delFenlei,delWithdrawInvest,delProfitLoss', param.method) ? 'active' : ''}">
			<a href="javascript:;">
				<i class="icon-trash"></i> 
				<span class="title">回收站</span>
				<span class="arrow ${fn:contains('delFenlei,delWithdrawInvest,delProfitLoss', param.method) ? 'open' : ''}"></span>
			</a>
			
			<ul class="sub-menu">
				<li class="${fn:contains('delFenlei', param.method) ? 'active' : ''}"><a href="dataController.do?method=delFenlei">已删理财分类</a></li>
				<li class="${fn:contains('delWithdrawInvest', param.method) ? 'active' : ''}"><a href="dataController.do?method=delWithdrawInvest">已删提现入账</a></li>
				<li class="${fn:contains('delProfitLoss', param.method) ? 'active' : ''}"><a href="dataController.do?method=delProfitLoss">已删盈亏</a></li>
			</ul>
		</li>
	</ul>
	
</div>
