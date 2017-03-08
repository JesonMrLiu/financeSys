<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

   	<div class="header navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="brand" href="index.html">
					&nbsp;&nbsp;财务管理系统
				</a>
				<a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">
				<img src="resources/images/menu-toggler.png" alt="" />
				</a>          
				<ul class="nav pull-right">
				
					<li class="dropdown user">

						<a href="#" class="dropdown-toggle" data-toggle="dropdown">

						<span class="username">${CURRENT_USER.username}</span>

						<i class="icon-angle-down"></i>

						</a>

						<ul class="dropdown-menu">
							<li class="divider"></li>
							<li><a href="#"><i class="icon-edit"></i> 修改密码</a></li>
							<li class="divider"></li>
							<li><a href="homeController.do?method=logout"><i class="icon-key"></i> 退出 </a></li>

						</ul>

					</li>


				</ul>


			</div>

		</div>

	</div>
    