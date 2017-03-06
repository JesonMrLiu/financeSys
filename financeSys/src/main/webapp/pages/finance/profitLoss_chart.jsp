<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--  -->
<form class="form-horizontal" action="">
	<input type="hidden" name="method" value="${param.method}"/>
	<span>&nbsp;&nbsp;截止日期：</span>
	<input type="text" class="control-line datepicker" id="enddate" name="enddate" placeholder="结束日期" style="cursor:pointer;" value="${param.enddate}" readonly/>
	&nbsp;&nbsp;
	<a class="btn btn-info" onclick="javascript:$(this).parents('form').submit();">查 询</a>
	<span style="float:right;margin-right:20px;">
		<a href="?method=profitLoss" class="tjBtn leftBtn ${param.method eq 'profitLoss' ? 'tjActive' : ''}" title="盈亏统计图表">&nbsp;<i class="icon-bar-chart"></i>&nbsp;</a>
		<a href="?method=profitLoss_tb" class="tjBtn rightBtn ${param.method eq 'profitLoss_tb' ? 'tjActive' : ''}" title="盈亏统计表格">&nbsp;<i class="icon-reorder"></i>&nbsp;</a>
	</span>
</form>

<div id="tj-charts" style="min-width:300px;height:460px;margin:0 auto;"></div>

<script src="resources/js/highcharts.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function () {
		refreshChart();
	});
	
	function refreshChart(){
		$.ajax({
			url : "?method=getChartDatas",
			dataType : "JSON",
			type : "POST",
			data : {"endDate" : $("#enddate").val()},
			success : function(res){
				initChart(res);
			}
		});
		
	}                    
	
	function initChart(datas){
		var xTime = datas.xTime;
		var codes = datas.codes;
		var codeNames = datas.codeWithName;
		var profitData = [];
		var colors = ["#ff0000", "#000", "#0642a0", "#40cc02", "#5c04d6", "#effb09"];
		$.each(codes, function(index, code){
			var temp = {};
			temp.name = codeNames[code];
			temp.data = datas["yData-"+code];
			temp.marker={};
			temp.marker.enabled = false;
			temp.color = colors[index];
			profitData[index] = temp;
		});
		
		//设置不使用UTC时间轴缩放
		Highcharts.setOptions({global : {useUTC : false}});
		
		$('#tj-charts').highcharts({
			chart: {
				type : "line"
			},
	        title: {
	            text: '理财分类盈亏统计',
	            x: -20 //center
	        },
	        credits : {enabled : false},
	        xAxis: {
	        	type : "datetime",
	            categories: xTime,
	            tickPixelInterval : 15,
	            labels : {
		            formatter : function(){
		            	var date = new Date(this.value);
		            	var year = date.getFullYear();
		            	var month = date.getMonth() + 1;
		            	var day = date.getDate();
		            	return year + "-" + month + "-" + day;
		            }
	            }
	        },
	        yAxis: {
	            title: {
	                text: '访问次数'
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            formatter: function() {
	            	var date = new Date(this.x);
	            	var hour = date.getHours();
	            	var minute = date.getMinutes();
	            	var dateStr = date.getFullYear() + "年" + (date.getMonth()+1) + "月" + date.getDate() + "日 " ;
	                return '时间： <b>' + dateStr + '</b><br/>' + this.series.name + '【盈亏】：<b>' + this.y + ' </b>';
	            }
	        },
	        legend: {
	            layout: 'horizontal',
	            align: 'center',
	            verticalAlign: 'bottom',
	            borderWidth: 0
	        },
	        series: profitData
	    });
	}
	
	</script>