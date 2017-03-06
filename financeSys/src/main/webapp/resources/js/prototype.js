
/**
 * 公共类增加方法
 * @auther  币网-刘刚
 * @date	2015-06-26
 */

/** 为字符串添加为空验证 */
String.prototype.isEmpty = function(){
	return this == undefined || this.trim() == "" || this.trim() == "undefined"? true : false;
};

/** 为字符串添加不为空验证 */
String.prototype.isNotEmpty = function(){
	return !this.isEmpty();
};

/** 清空字符串左右空格 */
String.prototype.trim = function(){
	return $.trim(this);
};

/** 替换所有 */
String.prototype.replaceAll = function(s1,s2){  
	var r = new RegExp(s1.replace(/([\(\)\[\]\{\}\^\$\+\-\*\?\.\"\'\|\/\\])/g,"\\$1"),"ig");
	return this.replace(r,s2);
}

/** 
 * 添加字符串相等方法
 * @param str : 需要比较的字符串
 */
String.prototype.equals = function(str){
	return this == str;
};

/** 添加字符串是否是邮箱 */
String.prototype.isEmail = function(){
	return /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-z0-9]+\.[a-zA-Z]{2,3}$/.test(this);
};

/** 添加字符串是否为手机号码 */
String.prototype.isMobile = function(){
	return /^1[34578]{1}\d{9}$/.test(this);
};

/** 添加字符串是否为座机 */
String.prototype.isPhone = function(){
	return /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(this);
};

/** 添加字符串是否为数字 */
String.prototype.isNumber = function(){
	return /^\d+$/.test(this);
};

/** 添加字符串是否为小数 */
String.prototype.isDecimal = function(){
	return /^\d+(\.\d+)?$/.test(this);
};

/** 添加字符串是否为字母，包括大小写字母 */
String.prototype.isLetter = function(){
	return /^[a-zA-Z]+$/.test(this);
};

/** 将数字字符串转换成数字 */
String.prototype.toNumber = function(){
	return parseInt(this);
};

/** 将数字字符转换成float */
String.prototype.toFloat = function(){
	return parseFloat(this);
};

/** 
 * 将字符串数字转换成保留decimal位小数位的小数
 * @param tag : 需要转化的字符串数字的对象
 * @param decimal : 保存的小数位
 * 
 */
String.prototype.decimalFormat = function(tag, decimal){
	var val = $(tag).val().trim();
	if(!val.isDecimal()){
		return val;
	}
	var m = decimal + 1;
	if(val.indexOf(".") > 0 && val.substring(val.indexOf(".")).length > m){
		val = val.substring(0 , val.indexOf(".") + m);
		$(tag).val(val);
	}
	return val;
}

/**
 * 添加字符串匹配类别
 * @returns : 该字符串所属的类别。
 * 如：mobile：手机；email：邮箱；phone：座机。等
 */
String.prototype.matchers = function(){
	var text = "string";
	if(this.trim().isEmail()){
		text = "email";
	}
	if(this.trim().isMobile()){
		text = "mobile";
	}
	if(this.trim().isPhone()){
		text = "phone";
	}
	if(this.trim().isNumber()){
		text = "number";
	}
	if(this.trim().isLetter()){
		text = "letter";
	}
	if(this.trim().isDecimal()){
		text = "decimal";
	}
	return text;
};

/**
 * 比较数字大小，所比较数字在比较数字之间（包含）
 * 
 * @param lowerNum	：比较较小的数字
 * @param PowerNum	：比较较大的数字
 */
Number.prototype.compare = function(lowerNum, powerNum){
	return this >= lowerNum && this <= powerNum ? true : false;
};


/**
 * Bw数字处理类
 * 
 * @date 2015-09-28
 * @author 刘刚
 */
var BwNumber = {};
/** 小数相加 */
BwNumber.add = function(f1, f2){
	var idx1 = f1.indexOf(".");
	var len1 = idx1 > 0 ? f1.length - idx1 : 0; 
	
	var idx2 = f2.indexOf(".");
	var len2 = idx2 > 0 ? f2.length - idx2 : 0;
	
	var f3 = (f1 * Math.pow(10, len1 + len2)).toFixed(0) + (f2 * Math.pow(10, len1 +len2)).toFixed(0);
	return f3/(Math.pow(10, len1 + len2));
}
/** 小数相减 */
BwNumber.minus = function(f1, f2){
	var idx1 = f1.indexOf(".");
	var len1 = idx1 > 0 ? f1.length - idx1 : 0; 
	
	var idx2 = f2.indexOf(".");
	var len2 = idx2 > 0 ? f2.length - idx2 : 0;
	
	var f3 = (f1 * Math.pow(10, len1 + len2)).toFixed(0) - (f2 * Math.pow(10, len1 +len2)).toFixed(0);
	return f3/(Math.pow(10, len1 + len2));
}

/** 小数相减 */
BwNumber.multiply = function(f1, f2){
	var idx1 = f1.indexOf(".");
	var len1 = idx1 > 0 ? f1.length - idx1 : 0; 
	
	var idx2 = f2.indexOf(".");
	var len2 = idx2 > 0 ? f2.length - idx2 : 0;
	
	var f3 = (f1 * Math.pow(10, len1)).toFixed(0) * (f2 * Math.pow(10, len2)).toFixed(0);
	return f3/(Math.pow(10, len1 + len2));
}

/** 小数相除 */
BwNumber.divider = function(f1, f2){
	var idx1 = f1.indexOf(".");
	var len1 = idx1 > 0 ? f1.length - idx1 : 0; 
	
	var idx2 = f2.indexOf(".");
	var len2 = idx2 > 0 ? f2.length - idx2 : 0;
	
	var f3 = (f1 * Math.pow(10, len1 + len2)).toFixed(0) / (f2 * Math.pow(10, len2)).toFixed(0);
	return f3/(Math.pow(10, len1));
}

