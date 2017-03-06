/**
 * 默认短信验证为false
 */
var validShortMsgCode = false;
//记录安全密码请求数据结果
var _safeRecord = {};
//记录短信验证码请求数据结果
var _msgRecord = {};
/**
 * 表单提交验证
 * 作用：表单验证
 * 
 * 
 * @author 币网-刘刚
 * @time 2015-06-12
 */
var LaValidForm = {
	vForm : {},//需要验证的表单对象
	vInputs : [], //需要验证的输入框
	formatError : [],  //验证格式不正确的输入框
	emptyError : [], //验证为空的输入框
	validResult : true, //验证结果，默认为true
	/**
	 * 需要获取的验证的选项
	 * 默认验证选项
	 */
	defOptions : {
		type : 'string', // 验证格式。默认为验证字符串
		required : true,  //是否是必填项。默认为非必填项
		inputName : ''	//输入框名称。如：用户名/密码/手机/邮箱等
	},
	
	addClass : function(obj, cls) {
		this.clearClass(obj);
		$(obj).addClass(cls);
	},
	
	clearClass : function(obj){
		$(obj).removeClass("error").removeClass("success").removeClass("warning");
	},
	
	/**
	 * 验证表单
	 */
	valid : function(form){
		this.vForm = $(form).eq(0);
		//获得需要验证的input输入框，首先排除不需要验证的输入框（包括被隐藏的input）
		//验证包括input输入框和textarea文本框
		this.vInputs = this.removeNotValidInputs($(this.vForm).find("input[type!=radio][type!=checkbox],textarea,select"));
		//验证表单，最初将验证失败的内容设置默认为空
		this.formatError = [];
		this.emptyError = [];
		//循环遍历，获取需要验证的输入框的验证选项
		$(this.vInputs).each(function(){
			//输入框验证
			LaValidForm.inputValidFun(this);
		});
		//验证结果，并返回
		return this.validResult;
	},
	
	/**
	 * 输入框验证为空或格式问题
	 * @param obj	： 输入框对象或Id
	 * @param compareObj : 当前输入框需要与compareObj的输入内容进行比较
	 */
	inputValidFun : function(obj, compareObj){
		var valid_opts = $(obj).attr("valid-options");
		var options = (valid_opts == undefined ? undefined : $.parseJSON(valid_opts.replaceAll("'", '"'))) || LaValidForm.defOptions;
		var type = options.type;
//		var valid = options.valid;
		var required = options.required;
		var inputName = options.inputName || '';
		var val = $.trim($(obj).val());
		//根据是否需要验证，来进项相关的类型验证
		if(required){
			switch(type){
			case "emailOrMobile" : 
				//输入框为必填项，或者输入的内容不为空时
				if(val.length > 0){
					LaValidForm.validEmailOrMobile(val) ? LaValidForm.clearInputcls(obj, "success") 
							: LaValidForm.recordValidFailInputs(LaValidForm.isEmpty(inputName) ? "邮箱/手机号码" : inputName, obj);
				} else{//输入框内容为空，并且该输入框为必填的
					LaValidForm.recordValidEmptyInputs(LaValidForm.isEmpty(inputName) ? "邮箱/手机号码" : inputName, obj);
				}
				break;
			case "email" : 
				if(val.length > 0){
					LaValidForm.validEmail(val) ? LaValidForm.clearInputcls(obj, "success") 
							: LaValidForm.recordValidFailInputs(LaValidForm.isEmpty(inputName) ? "邮箱" : inputName, obj);
				} else{
					LaValidForm.recordValidEmptyInputs(LaValidForm.isEmpty(inputName) ? "邮箱" : inputName, obj, obj);
				}
				break;
			case "mobile" : 
				if(val.length > 0){
					LaValidForm.validMobile(val) ? LaValidForm.clearInputcls(obj, "success") 
							: LaValidForm.recordValidFailInputs(LaValidForm.isEmpty(inputName) ? "手机号码" : inputName, obj, obj);
				} else{
					LaValidForm.recordValidEmptyInputs(LaValidForm.isEmpty(inputName) ? "手机号码" : inputName, obj);
				}
				break;
			case "password" : 
				if(val.length > 0){
					LaValidForm.validPassword(val) ? LaValidForm.clearInputcls(obj, "success") 
							: LaValidForm.recordValidFailInputs(LaValidForm.isEmpty(inputName) ? "密码" : inputName, obj);
				} else {
					LaValidForm.recordValidEmptyInputs(LaValidForm.isEmpty(inputName) ? "密码" : inputName, obj);
				}
				break;
			case "repassword" : 
				if($(compareObj) != undefined && $.trim($(compareObj).val()) != ""){
					if($.trim($(compareObj).val()) != $.trim($(obj).val())){
						$(obj).parents("div.control-group").removeClass("default").removeClass("warning").removeClass("danger").removeClass("success").addClass("danger");
						LaValidForm.initFormValidDivTips(obj);
						 $(obj).parents("div.control-group").find("p.validate").html("两次输入的密码不一致").fadeIn(300);
						 this.validResult = false;
					} else {
						$(obj).parents("div.control-group").addClass("success");	
					}
				}
				break;
			case "username" : 
				if(val.length > 0){
					LaValidForm.validUser(val) ? LaValidForm.clearInputcls(obj, "success") 
							: LaValidForm.recordValidFailInputs(LaValidForm.isEmpty(inputName) ? "用户名" : inputName, obj);
				} else if(LaValidForm.isEmpty(val)){
					LaValidForm.recordValidEmptyInputs(LaValidForm.isEmpty(inputName) ? "用户名" : inputName, obj);
				}
				break;
			case "double" : 
				if(val.length > 0){
					LaValidForm.validDouble(val) ? LaValidForm.clearInputcls(obj, "success") 
							: LaValidForm.recordValidFailInputs(LaValidForm.isEmpty(inputName) ? "金额" : inputName, obj);
				} else if(LaValidForm.isEmpty(val)){
					LaValidForm.recordValidEmptyInputs(LaValidForm.isEmpty(inputName) ? "金额" : inputName, obj);
				} 
				break;
			case "number" : 
				if(val.length > 0){
					LaValidForm.validNumber(val) ? LaValidForm.clearInputcls(obj, "success") 
							: LaValidForm.recordValidFailInputs(LaValidForm.isEmpty(inputName) ? "数字" : inputName, obj);
				} else if(LaValidForm.isEmpty(val)){
					LaValidForm.recordValidEmptyInputs(LaValidForm.isEmpty(inputName) ? "数字" : inputName, obj);
				} 
				break;
			case "date" :
				if(val.length > 0){
					LaValidForm.validDate(val) ? LaValidForm.clearInputcls(obj, "success") 
							: LaValidForm.recordValidFailInputs(LaValidForm.isEmpty(inputName) ? "日期" : inputName, obj);
				} else if(LaValidForm.isEmpty(val)){
					LaValidForm.recordValidEmptyInputs(LaValidForm.isEmpty(inputName) ? "日期" : inputName, obj);
				}
				break;
			case "ipAddress" : 
				if(val.length > 0){
					LaValidForm.validIp(val) ? LaValidForm.clearInputcls(obj, "success") 
							: LaValidForm.recordValidFailInputs(LaValidForm.isEmpty(inputName) ? "ip地址" : inputName, obj);
				} else if(LaValidForm.isEmpty(val)){
					LaValidForm.recordValidEmptyInputs(LaValidForm.isEmpty(inputName) ? "ip地址" : inputName, obj);
				}
				break;
			case "select" :
				if(val.length <= 0){
					LaValidForm.recordValidEmptyInputs(inputName, obj, "select");
				} else {
					LaValidForm.clearInputcls(obj, "success")
				}
				break;
			case "string" : 
			default:
				if(val.length <= 0){
					LaValidForm.recordValidEmptyInputs(inputName, obj);
				} else {
					LaValidForm.clearInputcls(obj, "success")
				}
				break; 
			}
		}
	},
	
	/**
	 * 记录验证格式不正确的输入框
	 */
	recordValidFailInputs : function(cont, obj){
		this.initFormValidDivTips(obj);
		$(obj).parents("div.control-group").addClass("error").find("span.help-inline").eq(0).html(cont + "格式有误！").fadeIn(300);
		this.validResult = false;
	},
	
	/**
	 * 记录输入框内容为空的
	 */
	recordValidEmptyInputs : function(cont, obj, type){
//		this.initFormValidDivTips(obj);
		var text = "请填写" + cont;
		if(type != undefined && type != null){
			switch(type){
			case "select" :　
				text = "请选择" + cont;
				break;
			}
		}
		this.addClass($(obj).parents("div.control-group"), "warning")
		$(obj).parents("div.control-group").find("span.help-inline").eq(0).html(text).fadeIn(300);
		this.validResult = false;
	},
	
	/**
	 * 将输入框提示清除，
	 * 再重设输入框提示，并移除下拉提示
	 * 
	 * @param obj	：输入框对象或id
	 * @param type	：设置的输入框内容提示类型
	 */
	clearInputcls : function(obj, type){
		LaValidForm.addClass($(obj).parents("div.control-group"), "");
		if(type != undefined || type != ""){
			$(obj).parents("div.control-group").addClass(type);
			/*if("success" == type){
				$(obj).parents("div.control-group").find("span.help-inline").eq(0).addClass("ok")
			} else {
				$(obj).parents("div.control-group").find("span.help-inline").eq(0).removeClass("ok")
			}*/
		}
		$(obj).siblings("span.help-inline").text("");
	},
	
	/**
	 * 显示提示
	 * @param obj	：要显示提示信息的输入框对象或id
	 * @param text	：提示内容
	 * @param type	：提示错误类型
	 */
	showTips : function(obj, text, type){
		var val = $(obj).val();
		LaValidForm.clearInputTip($(obj).parent("div"));
		LaValidForm.initFormValidDivTips(obj);
		$(obj).parents("div.control-group").addClass(type).find("span.help-inline").eq(0).html(text).fadeIn(300);
		if(val.isNotEmpty()){
			$(obj).val(val);
			$(obj).siblings("label[class^=placeholder]").eq(0).hide();
		}
	},
	
	/**
	 * 显示提示，并且不显示输入框的下拉p提示框
	 * @param obj	：要显示提示信息的输入框对象或id
	 * @param text	：提示内容
	 * @param type	：提示错误类型
	 */
	showTipsAndNotValidate : function(obj, text, type){
		this.showTips(obj, text, type);
		$(obj).siblings("span.help-inline").text("");
	},
	
	/**
	 * 初始化div提示框，并添加到父页面的body中
	 * @param obj : input输入框对象
	 */
	initFormValidDivTips : function(obj){
		var html = '<p class="validate" style="display:none;"></p>';
		$(obj).parent("div").find("p.validate").length > 0 ? null : $(obj).parent("div").append(html);
	},
	
	/**
	 * 清除输入框的提示
	 * 只需将输入框的顶层div.form-group的提示class去掉
	 * @prama obj : 输入框顶层div，该div的class是用来表示提示类别
	 */
	clearObjTipCls : function(obj){
		$(obj).each(function(){
			LaValidForm.clearInputTip(this);
		});
	},
	
	/**
	 * 将输入框的提示相关都清除
	 * @param obj : 输入框父级DIV对象
	 */
	clearInputTip : function(obj){
		this.clearInputTipsWithoutVal(obj);
		$(obj).find("label.placeholder").show()
		$(obj).find("input").val("");
	},
	
	/**
	 * 清除输入框的提示
	 * 注：不清除输入框中的值
	 * @param obj : 输入框父级div对象或id、class
	 */
	clearInputTipsWithoutVal : function(obj){
		$(obj).find("p.validate").remove();
		$(obj).removeClass("default").removeClass("danger").removeClass("success").removeClass("warning");
		$(obj).find("label.placeholderFocus").removeClass("placeholderFocus").hide();
	},
	
	/**
	 * 销毁提示框
	 */
	destoryTips : function(obj){
		$(obj).parents("div.control-group").find("span.help-inline").text("");
	},
	
	/**
	 * 移除不需要验证的input
	 */
	removeNotValidInputs : function(objs){
		var ipsObjs = [];
		objs.each(function(index, ele){
			if(ele != undefined && ele.style != undefined){
				ele.style.display == "none" || ele.type == "hidden" || $(ele).parent("div").css("display") == "none" ? null : ipsObjs[ipsObjs.length] = ele;
			}
		});
		return ipsObjs;
	},
	
	/**
	 * 内容是否为空
	 * @param tag : 输入框对象
	 * @param text : 输入框类型。如：密码，新密码
	 */
	isEmptyContent : function(tag, text){
		var val = $(tag).val();
		if(this.isEmpty($.trim(val))){
			this.recordValidEmptyInputs(text, tag);
			this.setResultTips(tag, "warning");
			return true;
		}
		return false;
	},
	
	/**
	 * 设置结果提示
	 * 
	 * @param obj
	 * @param type ： danger、default、warning、success
	 */
	setResultTips : function(obj, type){
		$(obj).parent("div").removeClass("default").removeClass("warning").
			removeClass("danger").removeClass("success").addClass(type);
	},
	
	/**
	 * 失去焦点验证
	 */
	blurValid : function(options){
		this.validResult = true;
//		$(options.obj).parent("div").removeClass("default").removeClass("warning").removeClass("danger").removeClass("success");
		this.addClass($(options.obj).parent("div"), "");
		//将输入框中的提示内容根据输入框是否输入内容来显示与否
		/*if(this.isNotEmpty($(options.obj).val())){
			$(options.obj).siblings("label[class^=placeholder]").eq(0).hide();
		} else {
			$(options.obj).siblings("label[class^=placeholder]").eq(0).removeClass("placeholderFocus");
			$(options.obj).siblings("label[class^=placeholder]").eq(0).show();
		}*/
		
		this.inputValidFun(options.obj, options.compareObj);
		if(this.validResult){
			this.destoryTips(options.obj);
		}
		return this.validResult;
	},
	
	/**
	 * 获得焦点验证
	 */
	focusValid : function(options){
		this.validResult = true;
		this.clearClass($(options.obj).parents("div.control-group"));
		this.destoryTips(options.obj);
		return this.validResult;
	},
	
	/**
	 * 键盘事件，在输入框中输入内容，将输入框中的提示内容隐藏
	 * @param obj
	 */
	onkeyInput : function(obj){
		if(this.isNotEmpty($(obj).val())){
			$(obj).siblings("label[class^=placeholder]").eq(0).hide();
		} else {
			$(obj).siblings("label[class^=placeholder]").eq(0).show();
		}
	},
	
	/**
	 * 邮箱验证
	 */
	validEmail : function(val){
		//^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-z0-9]+\.[a-zA-Z]{2,3}$
		return /^([a-zA-Z0-9_\\.-])+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/.test(val) ? true : false; 
	},
	
	/**
	 * 手机验证
	 */
	validMobile : function(val){
		return /^1[34578]{1}\d{9}$/.test(val) ? true : false;
	},
	
	/**
	 * 登录用户名：邮箱或手机验证
	 */
	validEmailOrMobile : function(val){
		return this.validEmail(val) || this.validMobile(val) ? true : false;
	},
	
	/**
	 * 验证密码
	 * 密码由6-16位数字、字母或常用符号组成，字母区分大小写，不能是纯数字或字母
	 */
	validPassword : function(val){
		return /^[a-zA-Z0-9@#\$%\^&\*_\-\+=\.\\\(\)\{\}<>\[\]]{6,16}$/.test(val) ? (/^[\d]{6,16}$/.test(val) || /^[a-zA-Z]{6,16}$/.test(val) ? false :true) : false;
	},
	
	/**
	 * 验证注册用户用户名
	 * 用户名由数字或小写字母组成，不能以数字开头，不超过30个字符
	 */
	validUser : function(val){
		return /^[a-z]([a-z0-9]){2,30}$/.test(val.toLowerCase());
	},
	
	/**
	 * 验证数字
	 */
	validNumber : function(val){
		return /^\d+$/.test(val);
	},
	
	/**
	 * 验证Double类型的数
	 */
	validDouble : function(val){
		return /^(-)?\d+(\.\d+)?$/.test(val);
	},
	
	/** 
	 * 验证日期 
	 * 
	 * 格式：1、yyyy-MM-dd （yyyy/MM/dd 或 dd/MM/yyyy 或 yyyy年MM月dd日）
	 * 	  2、yyyy-MM-dd HH:mm
	 *    3、yyyy-MM-dd HH:mm:ss  
	 * 
	 */
	validDate : function(val){
		return /^(\d{4})([\-\/年\.]){1}([01]\d){1}([\-\/年\.]){1}([0-3]\d){1}((\s[0-2]\d){1}:([0-6]\d){1}(:([0-6]\d){1})?)?$/.test(val);
	},
	
	/**
	 * 验证ip地址
	 * 格式：xxx.xxx.xxx.xxx
	 * 说明：ip只能存在数字和英文点号
	 */
	validIp : function(val){
		return /^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/.test(val);
	},
	
	/**
	 * 字符串为空
	 */
	isEmpty : function(str){
		return str == undefined || str == "" ? true : false;
	},
	
	/**
	 * 字符串不为空
	 */
	isNotEmpty : function(str){
		return this.isEmpty(str) ? false : true;
	}
	
};

/** 
 * 计算密码强度
 * @param tag : 计算密码强度的对象
 * 弱的颜色：#d74316（显示第一个）   中的颜色：#f65900（显示前两个）   强的颜色：#16ac3f（三个全部显示）
 */
var pwdSecurity = {
	//当前需要验证的密码强度的对象
	curObj : null,
	//弱
	lowerSec : function(){
		$("div.pwd-strong").find("span").css("background", "");
		$("div.pwd-strong").find("span.level-1").css("background", "#d74316");
		this.setPwdLevel("lower");
	},
	//中
	middleSec : function(){
		$("div.pwd-strong").find("span").css("background", "#f65900");
		$("div.pwd-strong").find("span.level-3").css("background", "");
		this.setPwdLevel("middle");
	},
	//强
	powerSec : function(){
		$("div.pwd-strong").find("span").css("background", "#16ac3f");
		this.setPwdLevel("power");
	},
	defaultSec : function(){
		$("div.pwd-strong").find("span").css("background", "");
		this.setPwdLevel("lower");
	},
	/**
	 * 设置密码级别，并将值保存到隐藏输入框中
	 * @param strength ：密码强度级别
	 */
	setPwdLevel : function(strength){
		//获得保存密码强度的输入框
		var itemObj = $(this.curObj).parents("div.item").eq(0);
		var levelIpt = $(itemObj).find("div.progress").eq(0).find("input[name$=Level]").eq(0);
		//根据输入的密码强度设置强度级别
		switch(strength){
		case "lower" : $(levelIpt).val("30"); break;
		case "middle" : $(levelIpt).val("60"); break;
		case "power" : $(levelIpt).val("100"); break;
		default : break;
		}
	}, 
	//计算密码类型
	calcSecurity : function(val){
		var sec = 0;
		if(val.match(/[a-z]/ig)) sec++;
		if(val.match(/[A-Z]/ig)) sec++;
		if(val.match(/[0-9]/ig)) sec++;
		if(val.match(/[@#\$%\^&\*_\-\+=\.\\]/ig)) sec++;
		return sec;
	},
	//计算密码复杂度
	calcComplex : function(val){
		
	},
	//计算密码强度
	calcPwdStrength : function(tag){
		this.curObj = tag;
		//验证密码格式
		LaValidForm.onkeyInput(tag);
		
		var val = $.trim($(tag).val());
		if(val == "") {
			this.defaultSec();	
			return;
		}
		//密码长度小于6，并不为空
		else if(val.length < 6){
			this.lowerSec();
			return;
		}
		//暂时注释，待改进
		/*var sec = this.calcSecurity(val);
		if(sec < 2) this.lowerSec();
		else if(sec >1 && sec < 3) this.middleSec();
		else if(sec > 3) this.powerSec();
		//密码长度为8到11位数的强制为中等等级
		if(val.length > 7 && val.length < 12){
			sec < 3 ? this.middleSec() : this.powerSec();
		} else if(val.length > 13){
			this.powerSec();
		} */
		//使用之前版本的验证
		var i = passwordCheck(val);
		if(120 - 20 * i  < 40) this.lowerSec();
		else if(120 - 20 * i <= 60 && 120 - 20 * i >= 40) this.middleSec();
		else this.powerSec();
	}
};

//表单自动填充，通过setTimeout计时器调用如下方法将输入框置空
function setInputEmptyVal(tag, type){
	var input = "input[name!=mCode][id!=secret][id!=type][id!=needMobile][id!=needPwd][id!=sCode][id!=email]";//需要清空的输入框，将不需要清空的输入框排除掉
	//$(tag == undefined ? "body" : tag).find(input).val("");
}
