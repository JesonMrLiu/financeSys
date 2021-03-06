<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<title>用户登录</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/style-metro.css" rel="stylesheet" type="text/css"/>
	<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/default.css" rel="stylesheet" type="text/css" id="style_color" />
<!-- 	<link href="resources/css/login.css" rel="stylesheet" type="text/css" />	 -->
	<link href="resources/css/login-soft.css" rel="stylesheet" type="text/css"/>
</head>

<body class="login">
	<div class="logo">
		<h3>财务管理系统</h3>
<%--		<img src="resources/image/logo-big.png" alt="" />--%>
	</div>
	<div class="content">
		<form class="form-vertical login-form" action="#" onsubmit="return false;">
			<h3 class="form-title">用户登陆</h3>
			<div class="alert alert-error hide">
				<button class="close" data-dismiss="alert"></button>
				<span>请输入您的用户名.</span>
			</div>

			<div class="control-group">
				<label class="control-label visible-ie8 visible-ie9">用户名</label>
				<div class="controls">
					<div class="input-icon left">
						<i class="icon-user"></i>
						<input class="m-wrap placeholder-no-fix" type="text" placeholder="用户名" name="username" valid-options="{'inputName':'用户名','required':true}" onfocus="LaValidForm.focusValid({'obj':this})" onblur="LaValidForm.blurValid({'obj':this});"/>
						<span class="help-inline"></span>
					</div>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label visible-ie8 visible-ie9">密码</label>
				<div class="controls">
					<div class="input-icon left">
						<i class="icon-lock"></i>
						<input class="m-wrap placeholder-no-fix" type="password" placeholder="密码" name="password" valid-options="{'inputName':'密码','required':true}" onfocus="LaValidForm.focusValid({'obj':this})" onblur="LaValidForm.blurValid({'obj':this});"/>
						<span class="help-inline"></span>
					</div>
				</div>
			</div>

			<div class="form-actions">
				<button type="submit" class="btn btn-blue">
				登录 <i class="m-icon-swapright m-icon-white"></i>
				</button>            
			</div>

			<div class="forget-password">
				<h4>忘记密码？</h4>
				<p>
					不用担心, 点击 <a href="javascript:;" class="" id="forget-password">这里</a>
					来重置初始密码。
				</p>
			</div>

			<div class="create-account">
				<p>
					还没有一个账号？&nbsp; 
					<a href="registerController.do?method=register" id="register-btn" class="">注册账号</a>
				</p>
			</div>
		</form>
	</div>
	
	<!-- BEGIN COPYRIGHT -->
	<div class="copyright">2017 &copy; 财务管理系统</div>
	<!-- END COPYRIGHT -->
	
	<!-- 加载层 -->
  	<div class="loading"><img alt="" src="resources/images/loading.gif"><div class="loading-text">正在查询中...</div></div>
	
	<script src="resources/js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<script src="resources/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="resources/js/prototype.js" type="text/javascript"></script>  
	<script src="resources/js/validate-form.js" type="text/javascript"></script>  
<!-- 	<script src="resources/js/app.js" type="text/javascript"></script>   -->
<!-- 	<script src="resources/js/login-soft.js" type="text/javascript"></script>   -->
	<script type="text/javascript">
		$(function(){
			$("form.login-form input").keyup(function(e){
				if(e.keyCode == 13){
					doSubmit();
				}
			});
		});
	
		function doSubmit(){
			$(".loading").show();
			var result = LaValidForm.valid(".login-form");
			if(result){
				$.ajax({
					url : "loginController.do?method=doLogin",
					data : $(".login-form").serialize(),
					dataType : "json",
					type : "post",
					success : function(res){
						if(res.suc){
							$(".loading").hide("fast", function(){
								window.location.href = "homeController.do?method=home";
							});
						} else {
							$(".loading").hide("fast", function(){
								alert(res.info);
							});
						}
					}
				});
			} else {
				$(".loading").hide();
			}
		}
	</script>
</body>
</html>