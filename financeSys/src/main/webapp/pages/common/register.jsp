<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <title>财务管理系统 -- 用户注册</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/style-metro.css" rel="stylesheet" type="text/css"/>
	<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/default.css" rel="stylesheet" type="text/css" id="style_color" />
	<link href="resources/css/login-soft.css" rel="stylesheet" type="text/css"/>
  </head>
  
  <body class="login">
    <div class="logo">
		<h3>财务管理系统</h3>
	</div>
	<div class="content">
		<form class="form-vertical login-form" action="#" onsubmit="return false;">
			<h3 class="">用户注册</h3>
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
						<input class="m-wrap placeholder-no-fix" type="password" id="register_password" placeholder="密码" name="password" valid-options="{'type':'password','inputName':'密码','required':true}" onfocus="LaValidForm.focusValid({'obj':this})" onblur="LaValidForm.blurValid({'obj':this});"/>
						<span class="help-inline"></span>
					</div>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label visible-ie8 visible-ie9">确认密码</label>

				<div class="controls">
					<div class="input-icon left">
						<i class="icon-ok"></i>
						<input class="m-wrap placeholder-no-fix" type="password" placeholder="确认密码" name="rpassword" valid-options="{'type':'repassword','inputName':'确认密码','required':true}" onblur="LaValidForm.blurValid({'obj':this,'compareObj':'#register_password'});" onfocus="LaValidForm.focusValid({'obj':this});"/>
						<span class="help-inline"></span>
					</div>
				</div>
			</div>

			<div class="form-actions">
				<button id="register-back-btn" type="button" class="btn blue" style="color:#fff;" onclick="javascript:history.back();">
				<i class="m-icon-swapleft m-icon-white"></i>  返回登录
				</button>
				<button type="submit" id="register-submit-btn" class="btn red pull-right" onclick="registerUser();">
				注册账号 <i class="m-icon-swapright m-icon-white"></i>
				</button>            
			</div>
		</form>
	</div>
	<!-- BEGIN COPYRIGHT -->
	<div class="copyright">2017 &copy; 财务管理系统</div>
	<!-- END COPYRIGHT -->
	<script src="resources/js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<script src="resources/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="resources/js/prototype.js" type="text/javascript"></script>  
	<script src="resources/js/validate-form.js" type="text/javascript"></script>  
	<script src="resources/js/modal.js" type="text/javascript"></script> 
	<script type="text/javascript">
		function registerUser(){
			var valid = LaValidForm.valid(".login-form");
			if(valid){
				$.ajax({
					url : "registerController.do?method=doRegister",
					data : $(".login-form").serialize(),
					dataType : "json",
					type : "post",
					success : function(result){
						if(result.suc){
							LaModal.initModal({cont:"恭喜您，注册成功，现在就<a href='loginController.do?method=login'>登录</a>"});
						} else {
							LaModal.alert("<i class='icon-warning-sign'></i>&nbsp;" + result.info);
						}
					}
				});
			}
			
		}
	</script>
  </body>
</html>
