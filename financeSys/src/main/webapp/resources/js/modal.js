
/**
 * 定义公共弹出框
 * 弹出模态框的body部分可以两种HTML内容模式：
 * 1、嵌套同级页面代码。如：<div><input type="text"/><br><input type="submit"/></div>
 * 2、嵌套页面。如登录页面：http://w.bw.com/user/log?a=1&iframe=1
 * 说明：body部分嵌套风格通过LaModal.html{type:..,cont:..}来设置
 * 默认使用的是同级代码（type : 'div'）来设置。
 * 
 * @author 刘刚
 * @time 2015-6-11
 */
var LaModal = {
	//定义默认属性变量
	html : {
		type : "dialog",
		cont : "这是主题内容！",
		title : "温馨提示",
		btnName : "取消",
		btnName2 : "确定",
		width : 420
	},
	
	/**
	 * 设置属性值，根据传的内容
	 */
	setPropValue : function(options){
		options = options || {};
		//设置主题内容
		options = options || this.html;
		this.html.type = this.isEmpty(options.type) ? this.html.type : options.type;
		this.html.cont = this.isEmpty(options.cont) ? this.html.cont : options.cont;
		this.html.title = this.isEmpty(options.title) ? this.html.title : options.title;
		this.html.width = options.width == undefined ? 420 : options.width;
	},
	
	/**  
	 * 初始化模态弹出框体
	 * @param options : {html:{type:'弹出框类型',cont:'弹出框主体内容'[,width:高度]}}
	 * 
	 */
	initModal : function(options){
		//设置属性值
		this.setPropValue(options);
		//定义模态弹出框HTML
		var mFade = '<div id="fModal" class="modal hide fade" tabindex="-1" data-focus-on="input:first"></div>';
		var mHeader = '<div class="modal-header"></div>';
		var closeBtn = '<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i></button>';
		var mHeaderTitle = '<h3 class="header-title-text" style="display:inline-block;"></h3>';
		var mBody = '<div class="modal-body"></div>';
		//将标签元素添加到 body中
		var fModalDiv = $("body").eq(0).find("div#fModal");
		//获得所弹出框的类型，url：嵌入页面。div：嵌入div代码（主要用于做弹出消息）
		var type = this.html.type;
		//添加模态框元素
		if(fModalDiv.length <= 0){
			$("body").eq(0).append(mFade);
			//这是弹出框的宽度
			//添加header、body、footer部分
			$("#fModal").append(mHeader).append(mBody);
			$("#fModal").find("div.modal-header").eq(0).append(mHeaderTitle).append(closeBtn);
			$("#fModal").find("h3.header-title-text").text(this.html.title);
		} else {
			$("#fModal").find("div.modal-body").eq(0).empty();
		}
		//删除footer
		$("#fModal").find("div.modal-footer").remove();
//		$("#fModal").width(this.html.width);
		$("#fModal").find("div.modal-body").eq(0).append(this.html.cont);
		LaModal.showModel();
	},
	
	/**
	 * 消息提示
	 * @param info ：消息提示信息内容
	 * @param opts : {
	 * 			title:'标题',
	 * 			btnName : '按钮的名称',
	 * 			type : '提示类型'//如：success,warning,danger,default,
	 * 			fn : 第一个按钮单击事件方法
	 * 			fn2 : 第二个按钮单击事件方法
	 * 			width : 弹出模态框的宽度
	 * 			btnNum : 按钮的个数。默认为1个，为第二个按钮，在一个按钮的回调方法是，注意是fn
	 * 		  }
	 */
	alert : function(info, opts){
		this.destoryModel();
		var options = {};
		opts = opts || {};
		options.cont = info || "恭喜您，保存成功！";
		options.title = opts.title || "温馨提示";
		options.width = opts.width || 360; //默认宽度为360
		var btnNum = opts.btnNum || 1;
		var type = opts.type || "default";
		var fn = opts.fn || "";
		var fn2 = opts.fn2 || "";
		//初始化基本的内容
		this.initModal(options);
		//定义其他需要的部分
		var mFooter = '<div class="modal-footer"></div>';
		var cancelBtn = '<button type="button" data-dismiss="modal" class="btn">关闭</button>';
		var confirmBtn = '<button type="button" class="btn btn-red">确定</button>';
		//添加footer
		$("#fModal").append(mFooter);
		if(btnNum > 1){
			$("#fModal").find("div.modal-footer").append(confirmBtn).append(cancelBtn);
		}
		if(btnNum > 0 && btnNum <= 1){
			$("#fModal").find("div.modal-footer").append(confirmBtn);
		}
		//如果fn不为空，表明第二个按钮回调方法
		if(typeof fn == "string" && fn.isNotEmpty()){
			$("#fModal").find("button.btn-red").attr("onclick", fn);
		} 
		if(typeof fn == "function"){
			$("#fModal").find("button.btn-red").bind("click", fn);
		}
		if(fn == "" && fn2 == "" && btnNum == 1){
			$("#fModal").find("button.btn-red").bind("click", function(){
				LaModal.closeModal();
			});
		}
		LaModal.showModel();
	},
	
	/**
	 * 定义一个嵌套页面的iframe对象
	 * @param url : 嵌套页面的url路径
	 */
	defIframe : function(url){
		var html = "<iframe id='modal-frame' name='modal-frame' src='" + url + "' scrolling='no' width='100%' height='100%' frameboder='0'></iframe>";
		return html;
	},
	
	/**
	 * 初始化iframe 高度
	 * 该方法由被弹出框页面调用
	 */
	initFrameHeight : function(){
		var cont = parent.$(".modal-content");
		var modalHeight = parent.$("#modal-frame").contents().find("div.modal-height").eq(0);
		var height = $(modalHeight).height();
		$(cont).height(height);
		
		//调整模态框在窗口中居中
		parent.$("#fModal").modal("adjustDialog");
	},
	
	/**
	 * 显示模态窗体
	 */
	showModel : function(){
		//禁用BootStrap Modal 点击空白或按Esc键自动关闭
		$("#fModal").modal({backdrop : "static", keyboard : false});
		//显示模态框
		$("#fModal").modal("show");
//		$("#fModal").on("show.bs.modal", function(){
//			debugger;
//			$("#fModal").width(this.html.width);
//		});
	},
	
	/**
	 * 销毁模态窗体
	 */
	destoryModel : function(){
		$("div#fModal").remove();
		$("div.modal-backdrop.fade.in").remove();
	},
	
	clearCont : function(){
		var fModal = $("#fModal")
		if(fModal != undefined && fModal != null){
			$(fModal).find("div.modal-body").empty();
		}
	},
	/**
	 * 关闭模态框
	 */
	closeModal : function(){
		$("#fModal").modal("hide");
	},
	
	/**
	 * 判断字符串为空
	 */
	isEmpty : function(str){
		if(str == undefined || str == "")
			return true;
		return false;
	},
	
	/**
	 * 判断字符串不为空
	 */
	isNotEmpty : function(str){
		return this.isEmpty(str) ? false : true;
	}
	
}

/**
 * 公用工具类
 */
var bwPower = {
	/** 计算窗口高度减去header高度和footer高度 */
	calcContainerHeight : function(){
		var winHeight = $(window).height();
		var headerHeight = $("div.header").eq(0).height();
		var footerHeight = $("div.footer").eq(0).height();
		var containerHeight = winHeight - headerHeight - footerHeight;
		return containerHeight;
	},
	/** 设置container高度 */
	setContainerHeight : function(container){
		//$("div.site-wrapper-inner").height(this.calcContainerHeight());
	}
}