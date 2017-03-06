<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><sitemesh:write property="title"/></title>
	<jsp:include page="resource.jsp" flush="true"/>
</head>
<body class="page-header-fixed">
	
	<!-- 页面头 -->
	<jsp:include page="header.jsp" flush="true"/>

	<!-- 内容区 、菜单区-->
	<div class="page-container">
		<!-- 左边菜单 -->
		<jsp:include page="leftMenu.jsp" flush="true"/>
		
		<!-- 内容区 -->
		<sitemesh:write property="body"/>
	</div>
	
	<!-- 底部版权信息 -->
	<jsp:include page="footer.jsp" flush="true"/>

	<script type="text/javascript">
		$(function() {    
			App.init(); // initlayout and core plugins
			$(".datepicker").datetimepicker({
	            language: "zh-CN",
				minView: "month",
				autoclose:true,
				todayButton:true,
				endDate : new Date(),
	            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	        });
		});
		
		function deleteById(id, method, text){
			text = text == undefined ? "警告，你确定要删除吗？" : text;
			text = "<i class='icon-warning-sign'></i>&nbsp;" + text;
			LaModal.alert(text,{width:420,fn:function(){
				method = method == undefined ? "deleteById" : method;
				$.ajax({
					url : "?method="+method,
					data : {"id":id},
					dataType : "json",
					type : "post",
					success : function(result){
						if(result.suc){
							$("#row"+id).fadeOut(400);
							LaModal.closeModal();
						} else {
							LaModal.alert(result.info);
						}
					}
				})
				
			}})
		}
	</script>

</body>
</html>
