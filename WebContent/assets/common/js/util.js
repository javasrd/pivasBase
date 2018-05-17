var sdfun = {};
sdfun.fn = {
	htmlEncode: function(value) {
		if(value === undefined || value === null) {
    		return '';
    	}
		var temp = document.createElement('div');
		(temp.textContent != null) ? (temp.textContent = value) : (temp.innerText = value);
		var output = temp.innerHTML;
		temp = null;
		return output;
    },
    htmlDecode: function(value) {
    	if(value === undefined || value === null) {
    		return '';
    	}
    	//把空格的转义符替换，不然时间控件有错误
    	if(typeof(value) == "string"){
    		value = value.replace('&nbsp;',' ');
    	}
    	var temp = document.createElement('div');
    	temp.innerHTML = value;
    	
    	var output = null;
    		
    	//增加判断，如果可以获取到innerText，如IE，替换换行为<br/>
    	if(temp.innerText != null){
    		if(typeof(value) == "string"){
    			value = value.replace(/[\r\n]/g,"<br/>");
    		}
        	temp.innerHTML = value;
        	output = temp.innerText;
    	}
    	//火狐，使用textContent
    	else{
    		output = temp.textContent;
    	}
    	
    	temp = null;
    	return output;
    },
    trimAll: function(srcId) {
    	var $src = $(('#' + srcId.replace(/,/g,',#')));
    	$src.find(':input').blur(function() {
    		$(this).val($.trim($(this).val()));
    	});
    },
    dialog: {
    	show: function() {
    		$('#dialogDiv', window.parent.document).show().click().focus();
    	},
    	hide: function() {
    		$('#dialogDiv', window.parent.document).hide();
    	}
    },
    //值为null使用replace代替
    ifnull:function(value, replace){
    	if(replace === undefined){
    		replace = "";
    	}
    	return value == null ? replace : value;
    },
    //处理占位符,把占位符替换成指定的值,占位符为{0},{1}
    format: function(msg, args){
		if(msg != null && args != null){
			if($.isArray(args)){
				$.each(args, function(i, o){
					msg = msg.replace('{'+ i +'}', o);
				});
			}else if($.type(args) === "string"){
				msg = msg.replace(/\{\d?\}/, args);
			}
		}
		return msg;
	}
};
/**
 * 常量
 */
sdfun.constant = {
	reg: {
		email: /^(([A-Za-z0-9]+([_\.-]*[A-Za-z0-9]+)?)@([A-Za-z0-9\.-]+)\.([a-z]{2,3})){0,128}$/,
		planInterval: /^\d{1,4}$/,
		ip: /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
		path: /^[a-zA-Z]:[\\/]((?! )(?![^\\/]*\s+[\\/])[\w -]+[\\/])*(?! )(?![^.]+\s+\.)$/,
		ftpPath: /^(\/(\w+\/?)+)$/,
		account: /^([0-9a-zA-Z_])+$/,	//账号
		phone:/^[\+\d-]+$/,	//电话
		tel:/^([0-9-])+$/,
		fox:/^([0-9-]){0,16}$/,
		integer:/^([\d]*)$/,	//正整数
		alphanumeric:/^\w+$/i	//数字、字母、下划线
	}
};

/**
 * 页面改变大小时调用方法,在Chrome 40版本中onresize只会执行一次
 * @param {fun} fun 函数
 * @param {time} time 时间
 */
function pageResize(fun, time){
	if(fun == null){
		fun = 'resizePageSize();';
	}
	if(time == null){
		time = 200;
	}
	setTimeout(fun, time);
	
	var version = $.browser.version;
	version = parseInt(version.substring(0, version.indexOf(".")),10);
	if($.browser.chrome && version > 26){
		setTimeout(fun, 100);
	}
}

function startsWith( value, pattern ) {
	if(value){
		return value.indexOf( pattern ) === 0 ;
	}
	return false;
}

/**
 * 获取表格高度，用于自适应
 * @param delHeight 需要加减的高度
 * @param isGrid 是不是为表格计算高度
 * @returns 计算后的表格高度
 */
function getGridHeight(delHeight,isGrid){
	if(isGrid == null){
		isGrid = true;
	}
	if($.type(delHeight) === "boolean"){
		isGrid = delHeight;
		delHeight = 0;
	}
	delHeight = (delHeight==null?0:delHeight);
	var gridHeight= 0;
	/*if ($.browser.msie){
		gridHeight= ($(top.window).height() - 176 - 38 - 78 - (delHeight-12));
	}else{
	 }*/
	var topFrameHeight = $(top.window.document.getElementById("mainContentFram")).height();
	gridHeight= (topFrameHeight);
	
	//面包屑
	if(document.getElementById("navigationTr")){
		gridHeight = gridHeight - $("#navigationTr").height();
	}
	//查询条件层
	var search = document.getElementById("searchTr");
	if(search && search.style.display != "none"){
		gridHeight = gridHeight - $(search).height();
	}else{
		search = document.getElementById("search");
		if(search && search.style.display != "none"){
			gridHeight = gridHeight - $(search).height() - 8;//searchTr的高度与search相差8px
		}else{
			gridHeight = gridHeight + 10;
		}
	}
	//tab切换层
	var menuTabs = document.getElementById("menuTabs");
	if(menuTabs && menuTabs.style.display != "none"){
		gridHeight = gridHeight - $("#menuTabs").height();
	}
	//按钮层 
	if(document.getElementById("btnTr")){
		gridHeight = gridHeight - $("#btnTr").height();
	}
	//80=(表格头height40+表格底height24) 22=(页面中margin参数的值 + 表格需要与窗口底部空白的距离)
	if(isGrid){
		gridHeight = gridHeight- 71 - 21;
	}
	gridHeight = gridHeight -(delHeight);
	
	return gridHeight;
}

/**
 * 获取表格宽度，用于自适应
 * @returns 计算后的表格宽度
 */
function getGridWidth(ieWidthPer){
	ieWidthPer = ieWidthPer==null?0.975:ieWidthPer;
	var gridWidth = $(window).width();
	var left = document.getElementById("leftContent");
	if(left && left.style.display != "none"){
		gridWidth = gridWidth - $(left).width();
	}
	//左侧隐藏栏
	if($(".space").length != 0){
		gridWidth = gridWidth - $(".space").width();
	}
	var centerContent = document.getElementById("centerContent");
	if(centerContent && centerContent.style.display != "none"){
		gridWidth = gridWidth - $(centerContent).width();
	}
	
	gridWidth = parseInt(gridWidth * ieWidthPer);
	//+2因为表格bit-grid的Div没有边框
	return gridWidth+2;
}

/**
 * 获取左侧树高度，用于自适应
 * @returns 计算后的表格高度
 */
function getLeftTreeHeight(isNotArchTree){
	var gridHeight = ($(window).height() - 2);// 2是为了根据右边对齐
	if(!isNotArchTree){
		gridHeight = gridHeight - 37;//37是建筑查询框的高度
	}
	
	//设置最小高度
	if(gridHeight < 514){
		gridHeight = 514;
	}
	//alert(gridHeight);
	return gridHeight;
}

function getCenterHeight(){
	return getLeftTreeHeight(true)-8//8为.centerDevice中的padding-top;
}

/**
 * 公共字符串转json方法，转换失败返回null
 * @param data 字符串
 * @returns json格式字符串，转换失败返回null
 */
function toJsonData(data)
{
	var jsonData = null;
	if(typeof(data) == "string"){
		try
		{
			jsonData = eval('('+data+')');
		}catch(e)
		{
			
		}
	}
	
	return jsonData;
}

/**
 * 校验长度
 * @param o 要校验的对象
 * @param min 最小长度
 * @param max 最大长度
 * @returns {Boolean} true：校验通过，false：校验失败。
 */
function checkLength( o, min, max ) {
	var temp=o.val();
	temp = temp.replace(/[^\x00-\xff]/g,"aa")
	var _temp = o;
	if(o[0].tagName === 'SELECT' && $(o).data("combobox")) {
		_temp = o.next().find('input'); 
	}
	if ( $.trim(temp).length > ( max || 9999 ) || $.trim(temp).length < min ) {
		_temp.addClass( "ui-state-error" ).focus();
		return false;
	} else {
		_temp.removeClass( "ui-state-error" );
		return true;
	}
}

/**
 * 是否是最近三个月的数据1：是 0：否
 * @param searchTime
 * @returns
 */
function compareTime(searchTime)
{
	var arr = searchTime.split("-");
    var compareTime = new Date(arr[0], arr[1]);
    var now = new Date(sysNowYear, sysNowMonth - 3);
    
    if (compareTime <= now)
    {
    	return 0;
    }
    
    return 1;
    
}

//校验是否是数字和小数
function checkDecimals(objField)
{
	  var patrn=/^(-?[\d]+)([.]?)([\d]*)$/;
	  
	  if (patrn.test(objField.val())) 
	  {
		  objField.removeClass( "ui-state-error" );
		  return true;
	  }
	  else
	  {
		  objField.addClass( "ui-state-error" ).focus();
		  return false;
	  }
}

// 校验是否是正整数
function isPInt(o) {
    var patrn = /^[1-9]*[1-9][0-9]*$/;
    
    var v = $.trim(o.val()), flag = true;
	if(!v || isNaN(v)) {
		flag = false;
	} else {
		if (patrn.test(o.val())) 
		{
			flag = true;
		}
		else
		{
			flag = false;
		}
	}
	
	if(flag){
		flag = !(v > 2147483647)
	}
    
	if(!flag) {
		o.addClass( "ui-state-error" ).focus();
	}else{
		o.removeClass( "ui-state-error" );
	}
	
	return flag;
    
}

//校验是否是非负整数
function isInt(o) {
    var patrn = /^(0|[1-9]\d*)$/;
    
    var v = $.trim(o.val()), flag = true;
	if(!v || isNaN(v)) {
		flag = false;
	} else {
		if (patrn.test(o.val())) 
		{
			flag = true;
		}
		else
		{
			flag = false;
		}
	}
	
	if(flag){
		flag = !(v > 2147483647)
	}
    
	if(!flag) {
		o.addClass( "ui-state-error" ).focus();
	}else{
		o.removeClass( "ui-state-error" );
	}
	
	return flag;
    
}

function checkEmpty(o,emptyValue) {
	var _temp = o;
	if(o[0].tagName === 'SELECT' && $(o).data("combobox")) {
		_temp = o.next().find('input'); 
	}
	if((emptyValue != null && $.trim(o.val()) == emptyValue) || $.trim(o.val()).length < 1){
		_temp.addClass( "ui-state-error" ).focus();
		return false;
	} else {
		_temp.removeClass( "ui-state-error" );
		return true;
	}
}

function checkLimit(o, min, max) {
	var v = $.trim(o.val()), flag = true;
	if(!v || isNaN(v)) {
		flag = false;
	} else {
		v = parseInt(v);
		//校验最小值
		if(flag && min != undefined){
			flag = !(v < min)
		}
		//校验最大值
		if(flag && max != undefined){
			flag = !(v > max)
		}
	}
	
	if(!flag) {
		o.addClass( "ui-state-error" ).focus();
	}else{
		o.removeClass( "ui-state-error" );
	}
	
	return flag;
}

//校验邮箱
function checkEmail(o) {
    var patrn = sdfun.constant.reg.email;
    
    var v = $.trim(o.val()), flag = true;
	if(!v || v == '' || v == null) {
		flag = true;
	} else {
		if (patrn.test(o.val())) 
		{
			flag = true;
		}
		else
		{
			flag = false;
		}
	}

	if(!flag) {
		o.addClass( "ui-state-error" ).focus();
	}else{
		o.removeClass( "ui-state-error" );
	}
	
	return flag;
}


/**
 * 获取数据字典当前选中的值
 * @param o  获取数据字典当前选中的值
 * @param id 数据字典展示下拉框的id
 * @param val 最大长度
 * @returns {Boolean} true：校验通过，false：校验失败。
 */
function getDocVal(id, val ) {
	return $("#"+id+" option[value="+val+"]").html();
}

/**
 * 校验正则
 * @param o 要校验的对象
 * @param regexp 要校验的正则
 * @param n 校验失败的提示语，可选
 * @returns {Boolean} true：校验通过，false：校验失败。
 */
function checkRegexp( o, regexp, n ) {
	if ( !( regexp.test( o.val() ) ) ) {
		o.addClass( "ui-state-error" );
		if(n && n != "")
		{
			o.attr("title",n);	
		}
		o.focus();
		return false;
	} else {
		o.removeClass( "ui-state-error" );
		return true;
	}
}

/**
 * 检查端口
 * @param o 要校验的对象
 * @param n 校验失败的提示语，可选
 * @returns {Boolean} true：校验通过，false：校验失败。
 */
function checkPort(o, n)
{
	//先校验正则通过，值不大于65535
	if (checkLength(o, 1, 5) && checkRegexp(o, /^\d+$/, n) && o.val() < 65536 && o.val() > 0)
	{
		o.removeClass( "ui-state-error" );
		return true;
	}
	else
	{
		o.addClass( "ui-state-error" );
		if(n && n != "")
		{
			o.attr("title",n);	
		}
		o.focus();
		return false;
	}
}
/**
 * 检查非法字符
 */
function checkIllegalChar(e,v){
	
	var regExp = new RegExp("[$<>&^%`\\[\\]~]","ig");
	var flag = regExp.test(v);
	if(flag){
		$(e).addClass( "ui-state-error" );
		$(e).focus();
	}else{
		$(e).removeClass( "ui-state-error" );
	}
	
	return !flag;
}

/**
 * 检查配置是否已存在
 * url：请求地址
 * o: 校验对象
 * checkParam: 校验参数
 * errorMessage: 自定义错误消息，必选
 */
function checkExists(url, o, checkParam, errorMessage)
{
	var exists = false;
	//同步检测是否重复
	$.ajax({
		type : 'POST', 
           url : url,   
           dataType : 'json', 
           async : false,
           cache : false,
           data : checkParam,
           success : function(data) {
           	//返回失败信息
           	if(data.msg){
           		message({
   	    			data: data
               	});
           		//异常情况
           		exists = "error";
           	}
           	//成功
           	else{
           		//判断已存在，给出提示
           		if (data)
           		{
           			message({
           				html: errorMessage,
                   		showConfirm: true,
                   		confirm: function(){
		           			//添加错误样式获取焦点
		           			o.addClass("ui-state-error");
		           			o.focus();
                   		}
           			});
           			exists = true;
           		}
           	}
           }
	});
		
	return exists;
}

//获取列表中所有列的值，返回数组
function getFlexiGridRowText(grid,cellNumber){
 	var arr = new Array(0); 
 	$("tr td:nth-child("+cellNumber+") div", $(grid)).each(function(i){   
     	arr.push($(this).text());  
   });
   return arr;
}

// 根据关键字获取所在行的指定列的数据
function getFlexiGridCellValueByKey(tblObj,key,keyCellNumber,cellNumber) {
    var arr = new Array(0);
    var ret = "";
    $(tblObj).find("tr").each(function(){
        var tdArr = $(this).children();
        var tdKey = tdArr.eq(keyCellNumber).find('div').text();
        if (key == tdKey) {
            ret = tdArr.eq(cellNumber).find('div').text();
        }
    });

    return ret;
}

// 获取当前操作的单元格所在行的其它列的数据
function getFlexiGridCurrentRowText(thisObj,grid,cellNumber) {
	var arr = new Array(0); 
	$(thisObj).parent().parent().siblings(":nth-child("+cellNumber+")").find("div",$(grid)).each(function(i){   
     	arr.push($(this).text());  
   });
   return arr;
}

//获取列表中指定选中列的值，返回数组
function getFlexiGridSelectedRowText(grid,cellNumber){
 	var arr = new Array(0); 
 	$(".trSelected td:nth-child("+cellNumber+") div", $(grid)).each(function(i){
 		//判断div下面是否有checkbox或者radio
 		var $parent = $(this).parent().parent(),
 			$chk = $parent.find(":checkbox").size(),
 			$radio = $parent.find(":radio").size();
 		if(($chk == 1) || ($radio == 1)){
	     	arr.push($(this).text());  
 		}
   });
   return arr;
}

//表格回填选中  grid：表格对象；cellNumber：要对比的标识列；list：要勾选的标识集合
function flexiGridCheckBack(grid,cellNumber,list){
	//触发选择事件
	//if(isDealCheck){
	//	for(var i = 0;i < list.length;i++){
	//		$(grid).find("td:nth-child("+cellNumber+") div:contains('" + list[i] + "')").parent().parent().find("input.itemchk")[0].click();
	//	}
	//}else{
		for(var i = 0;i < list.length;i++){
			var ptr = $(grid).find("td:nth-child("+cellNumber+") div:contains('" + list[i] + "')").map(function(){
						  if($(this).text() == list[i]){
						  	return this;
						  }
					 }).parent().parent();
			
			$(ptr).find("input.itemchk").attr("checked",true);
			$(ptr).addClass("trSelected");
		}
	//}
	
}

/**
 * 注意:调用该方法需在初始化表格的时候指定rowbinddata : true
 * 如果是通过点击表格中的名字的超链接a标签去做展示类似详情的操作,
 * 可以通过这个方法获取点击的这一行的所有数据或者某一列的数据
 * @param {} obj 点击的a标签当前对象 传this就行
 * @param {} column	列数,可以不传,不传就返回这一行全部的数据
 */
function getFlexiGridClickRowText(obj,column){
	var columnsArray = $(obj).parent().parent().parent().attr("ch").split("_FG$SP_");
	if(typeof(column) == "undefined"){
		return columnsArray;
	}else{
		return columnsArray[column];
	}
}


function action(name,obj)
{
	var css = $(this).find("span").attr("class");
	$(this).find("span").removeAttr("class");
	if(css == 'exper')
	{
		$(this).find("span").addClass("exper2");
	}
	else
	{
		$(this).find("span").addClass("exper");
	}
	$(obj).find(".bDiv").toggle();
	$(obj).find(".hDiv").toggle();
}

//当前日期 （结束日期）
var sysNowDate = new Date();
var sysNowYear = sysNowDate.getFullYear();  //  年
var sysNowMonth = sysNowDate.getMonth()+1; // 月
var sysNowDay = sysNowDate.getDate();  // 日

//年选择框
//selectId select标签的ID
function generateYear(selectId){
	var destObj =  document.getElementById(selectId);  //select元素
	
	destObj.options.add(new Option(sysNowYear, sysNowYear));
	for(var i = 1; i < 3 ; i++ ){
		var year = sysNowYear - i;
		destObj.options.add(new Option(year, year));
	}
}

//改变年，获取月数
function changeYear(selectYearId, selectMonthId, selectDayId) {
	var year = $('#'+ selectYearId).val();
	var month = $('#'+ selectMonthId)[0];
	month.options.length = 1; // 清空月下拉选项
	var months = year === sysNowYear+"" ? sysNowMonth+"" : 12;
	for(var i = 1; i <= months; i++) {
		month.options.add(new Option(i, i < 10 ? ('0' + i) : i));
	}
	// 销毁并重新创建下拉框 
	$('#'+ selectMonthId).combobox('destroy').combobox();
	changeMonth(selectYearId, selectMonthId, selectDayId);
}

// 改变月，获取天数
function changeMonth(selectYearId, selectMonthId, selectDayId) {
	var year = $('#'+ selectYearId).val();
	var month = $('#'+ selectMonthId).val();
	var day = $('#'+ selectDayId)[0];
	day.options.length = 1; // 清空月下拉选项
	if(month) {
		var days = 0;
		var sysMonth = sysNowMonth<10?('0'+sysNowMonth) : sysNowMonth+"";
		if((year === sysNowYear+"") && (month === sysMonth)) {
			days = sysNowDay+"";
		} else if(month == 1 || month == 3 || month == 5 || month == 7 || month == 10 || month == 12) {
			days = 31
		} else if(month == 2) {
			var year = $('#'+ selectYearId).val() - 0;
			if(year % 400 === 0 || year % 4 === 0 && year % 100 !== 0) {
				days = 29;
			} else {
				days = 28;
			}
		} else {
			days = 30;
		}
		for(var i = 1; i <= days; i++) {
			day.options.add(new Option(i, i < 10 ? ('0' + i) : i));
		}
	}
	// 销毁并重新创建下拉框 
	$('#'+ selectDayId).combobox('destroy').combobox();
}

/**
 * 根据指定的模式得到系统当前时间
 * <如果不能满足条件请自行扩展此方法>
 * 
 * @param pattern 指定返回时间的模式，不指定时返回yyyy-MM-dd HH:mm:ss,
 * @param sysDate 指定时间,null时使用系统当前时间
 * @param addTime 加减日期(正数加,负数减)
 * @author jagger
 */
function getCurrentDate(pattern, sysDate, addTime){
	if(sysDate == null){
		sysDate = new Date();
	}else if($.isNumeric(sysDate)){
		addTime = sysDate;
		sysDate = new Date();
	}
	//加减日期(正数加,负数减)
	if($.isNumeric(addTime)){
		if(pattern == 'yyyy'){
			sysDate.setFullYear(sysDate.getFullYear() + addTime);
		}else if(pattern == 'yyyy-MM'){
			sysDate.setMonth(sysDate.getMonth() + addTime);
		}else if(pattern == 'yyyy-MM-dd'){
			sysDate.setDate(sysDate.getDate() + addTime);
		}
	}
	var year = sysDate.getFullYear();
	var month = sysDate.getMonth()+1;
	if(month < 10){
		month = "0"+month;
	}
	var day = sysDate.getDate();
	if(day < 10){
		day = "0"+day;
	}
	var hours = sysDate.getHours();
	if(hours < 10){
		hours = "0" + hours;
	}
	var minutes = sysDate.getMinutes();
	if(minutes < 10){
		minutes = "0" + minutes;
	}
	var seconds = sysDate.getSeconds();
	if(seconds < 10){
		seconds = "0" + seconds;
	}
	var currentDate = '';
	if(pattern == 'yyyy'){
		currentDate = year;
	}else if(pattern == 'MM'){
		currentDate = month;
	}else if(pattern == 'dd'){
		currentDate = day;
	}else if(pattern == 'yyyy-MM'){
		currentDate = year+"-"+month;
	}else if(pattern == 'yyyy-MM-dd'){
		currentDate = year+"-"+month+"-"+day;
	}else if(pattern == 'yyyy-MM-dd HH'){
		currentDate = year+"-"+month+"-"+day+" "+hours;
	}else if(pattern == 'yyyy-MM-dd HH:mm'){
		currentDate = year+"-"+month+"-"+day+" "+hours+":"+minutes;
	}else if(pattern == 'HH:mm'){
		currentDate = hours+":"+minutes;
	}else{
		currentDate = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
	}
	return currentDate;
}


/**
* id: dialog的ID,
* url: 请求路径,
* width: dialog的宽度,
* height: dialog的高度,
* title: dialog的标题,
* buttons ： dialog的按钮对象 {'关闭': function() {$(this).dialog('close');}} ,
* closeCallBack : 窗口关闭时的回调方法
* @author jagger
*/
function loadPage(p){
		p = $.extend({
			id : '',
			url: '',
			width: 400,
			height: 400,
			title: '',
			data:[],
			buttons : {},
			openCallBack:null,
			closeCallBack : null,
			createDialog : false,
			removeOnClose: true
	    }, p);
		
		if(p.createDialog){
			var $div = $("<div>");
			
			if(p.title){
				$div.attr("title", p.title);
			}
			
			if(p.id){
				$div.attr("id", p.id);
			}
			
			$div.load(
					p.url,
					p.data,
					function(response, status, xhr){
						//将返回转成json，如果为null则证明返回的是html，如果成功转成json并且是返回失败，提示用户
						var responseJson = toJsonData(response);
						if (responseJson != null && responseJson.success == false){
							message({
								data: responseJson
							});
						}
					}
			);
			$div.dialog({
				autoOpen: true,
				width : p.width,
				height : p.height,
				modal : true,
				resizable : false,
				buttons : p.buttons,
				open:function(){
					var openFun = p.openCallBack;
					if($.isFunction(openFun)){
						openFun();
					}
				},
				close : function() {
					var closeCallBack = p.closeCallBack;
					if(typeof closeCallBack === 'function') {
						closeCallBack();
					}
					if(p.removeOnClose){
						//删除dialog
						$div.parent().empty().remove();
						//删除 Div
						$div.empty().remove();
					}
				}
			});
		}else{
			$.ajax({
					type : 'POST',
					url : p.url,
					dataType : 'html',
					cache : false,
					data : p.data,
					success : function(data) {
						var responseJson = toJsonData(data);
						if (responseJson != null && responseJson.success == false){
							message({
								data: responseJson
							});
						}else{
							$(data).appendTo("body");
							
							if(p.title){
								$(".ui-dialog-title").html(p.title);
							}
							//open函数
							var openFun = p.openCallBack;
							if($.isFunction(openFun)){
								openFun();
							}
							//将closeCallBack的回调函数重新编写
							if(p.id){
								var close = $("#" + p.id).dialog("option","close");
								var closeCallBack = p.closeCallBack;
								$("#" + p.id).dialog("option","close",function(){
									if($.isFunction(close)){
										close();
									}
									if($.isFunction(closeCallBack)){
										closeCallBack();
									}
									if(p.removeOnClose){
										//删除dialog
										$(this).parent().empty().remove();
										//删除 Div
										$(this).empty().remove();
									}
								});
							}
						}
					}
				});
		}
}

/**
* <strong>加载公共树Dialog</strong><br><br>
* treeType:树类型 1(建筑), 2(部门)<br>
* width: dialog的宽度<br>
* height: dialog的高度<br>
* title: dialog的标题<br>
* confirmCallBack:点击确定时的回调函数名(可以接收参数"dialogId，treeObj")<br>
* closeCallBack : 窗口关闭时的回调函数名(可以接收参数"dialogId，treeObj")<br>
* treeSetting : 树的设置<br>
* @author jagger
*/
function showCommonTree(p){
	p = $.extend({
		treeType:"1",	//1(建筑) 2(部门)
		url:"",
		width: 330,
		height: 450,
		title: "",
		confirmCallBack:"",
		closeCallBack : "",
		treeSetting:null,
		maxLevel:null	//查询下拉框的最大层级
    }, p);
	
	var param = $.extend(true, {}, p);
	if(param && param.treeSetting && param.treeSetting.callback){
		$.each(param.treeSetting.callback, function(k, v){
			if($.isFunction(v)){
				param.treeSetting.callback[k]=getFunName(v);
			}
		});
	}
	delete param.url;
	$.ajax({
		type : "POST",
		url : p.url,
		dataType : 'html',
		cache : false,
		data : {param:JSON.stringify(param), maxLevel:p.maxLevel},
		success : function(data) {
			var responseJson = toJsonData(data);
			if (responseJson != null && responseJson.success == false){
				message({
					data: responseJson
				});
			}else{
				$(data).appendTo("body");
				var $dialogDiv = $("#arch_tree_common_dialog");
				//等于2时为部门树
				if(p.treeType == 2){
					$dialogDiv = $("#depart_tree_common_dialog");
				}
				//将closeCallBack的回调函数重新编写
				var closeCallBack = p.closeCallBack;
				var close = $dialogDiv.dialog("option","close");
				$dialogDiv.dialog("option","close",function(){
					if($.isFunction(close)){
						close();
					}
					//删除dialog
					$(this).parent().empty().remove();
					//删除 Div
					$(this).empty().remove();
				});
			}
		}
	});
}

/**
 * 得到函数的名称
 * @param f 函数,函数定义时要规范e.g("function funName(arg1,arg2..)");
 * @returns 函数名或函数字符串
 */
function getFunName(f){
	var str = f.toString();
	var names = str.match(/^function\s([\w]+)\(([\w,\s]*)\)/g);
	if(names != null){
		var name = names[0];
		name = name.replace(/function\s/, "");
		var i = name.indexOf("(");
		return name.substring(0, i);
	}else{
		return str;
	}
}
//把2014-10-21 12:00:11格式的时间转换成12:00:11 10-21-2014
function parseStringToDate(str){
	if($.type(str) === 'date'){
		return str;
	}
	var _parsedStr = [];
	if(str.indexOf("-") != -1){
		var times = str.split(" ");
		
		//年月日
		var _ymd = times[0].split("-");
		$.each(_ymd, function(_j, _t){
			//把字符串转换成数据
			var _t = parseInt(_t);
			//月份范围 (0 ~ 11)需要将月份-1
			if(_j == 1 && _t > 0){
				_t = _t - 1;
			}
			_parsedStr.push(_t);
		});
		
		if(times.length > 1){
			//时分秒
			var hhmmss = times[1].split(":");
			//把时分秒拆开
			$.each(hhmmss, function(_idx, val){
				_parsedStr.push(parseInt(val));
			});
		}
	}else{
	}
	
	var _time = [0, 0, 0, 0, 0, 0];
	$.each(_parsedStr, function(_idx, _val){
		_time[_idx] = _val;
	});
	
	return new Date(_time[0],_time[1],_time[2],_time[3],_time[4],_time[5]);
}

/**
 * 重置表单:
 * 该方法将重置input为空,重置select为-1(请选择).如需其他,可自行扩展
 * @param {} formId:需要重置的表单id,或者Jquery对象
 * @return {} 返回表单的jquery对象,可以继续进行链式操作
 */
function resetForm(formId){
	if($.type(formId) === "string"){
		//获取表单对象,以.开头的为class选择器
		if(formId.indexOf(".") == 0){
			var $form = $(formId);
		}else{
			var $form = $('#'+formId);
		}
	}else{
		var $form = formId;
	}
	
	//重置input
	$form.find("input[type!='button']").filter("input[type!='checkbox']").filter("input[type!='radio']").val('').removeClass("ui-state-error");;
	// 重置textarea
	$form.find("textarea").val('');
	//重置select
	$form.find('select').val('');
	$form.find('select').each(function(){
		if($(this).data("combobox")){
			$(this).combobox('destroy').combobox();
		}
	});
	
	return $form;
}

/**
 * 设置表单的值,通过表单的name来查询数据中相应的值,或者Jquery对象
 * @param formId 表单ID
 * @param data 数据
 */
function setFormValue(formId, data){
	if($.type(formId) === "string"){
		//获取表单对象,以.开头的为class选择器
		if(formId.indexOf(".") == 0){
			var $form = $(formId);
		}else{
			var $form = $('#'+formId);
		}
	}else{
		var $form = formId;
	}
	//设置input的值
	$form.find("input").each(function(){
		var name = $(this).attr("name");
		var val = data[name];
		if($.isArray(val)){
			val = val.join(",");
		}
		$(this).val(sdfun.fn.htmlDecode(val));
	});
	//设置select的值
	$form.find("select").each(function(){
		var name = $(this).attr("name");
		var val = data[name];
		if($.isArray(val)){
			val = val.join(",");
		}
		if($(this).data("combobox")){
			$(this).combobox("reset", val);
		}else{
			$(this).val(val);
		}
	});
}

/**
* 校验文本框值的正确性,指定ID包含子节点时查找子节点的input和select，id为input或select时就是本身
*@param id 指定
*@returns null校验成功，obj不成功
*/
function validateInput(id)
{
	var ret = true;
	var _$this = null;
	//校验文本框
	var _$tags = $("#"+id +" :input:not(select):visible").add("#"+id +" select");
	//id为input或select时校验本身
	if(_$tags.length == 0){
		_$tags = $("#"+id);
	}
	_$tags.each( function() {
		_$this = $(this);
		//下拉框的美化样式也要显示
		var _selectShowed = false;
		if(_$this.is("select")) {
			_selectShowed = _$this.next().find('input').is(":visible");
		}
		//显示的标签才做判断
		if((_$this.is(":visible") || _selectShowed) && typeof(this.value) != "undefined")
		{
			_$this.val($.trim(_$this.val()));
			//校验是否能为空
			if (ret && _$this.attr("empty") == 'false')
			{
				if (!checkEmpty(_$this)){
					ret = false;
				}
			}
			//校验最大长度最小长度
			if(ret && _$this.attr("maxlength"))
			{
				var _min = _$this.attr("minlength")?_$this.attr("minlength"):0;
				var _max = _$this.attr("maxlength");
				if(!checkLength(_$this, _min, _max)){
					ret = false;
				}
			}
			//校验最大值最小值
			if(ret && (_$this.attr("minvalue") || _$this.attr("maxvalue")))
			{
				var _min = _$this.attr("minvalue");
				var _max = _$this.attr("maxvalue");
				if(!checkLimit(_$this, _min, _max)){
					ret = false;
				}
			}
			//校验正则表达式
			if(ret && _$this.attr("regex") && _$this.val() != "")
			{
				var regexp = _$this.attr("regex");
				//校验是否是数字和小数
				if("number" == regexp)
				{
					!checkDecimals(_$this) && (ret = false);
					if(ret){
						var range = _$this.attr("range");
						if(range){
							var rangeArr = range.split(","),
							integerLen = 0,
							decimalLen = 0;
							if(rangeArr.length == 1){
								integerLen = rangeArr[0];
							}else if(rangeArr.length == 2){
								integerLen = rangeArr[0];
								decimalLen = rangeArr[1];
							}
							
							//拼接正则表达式
							var pattern = [];
							pattern.push("/");
							var p = "^([\\d]{1," + integerLen + "})";//只有整数
							if(decimalLen == 0){
								p += "$";
							}else{
								p = p + "$|" + p + "([.]{1})([\\d]{1," + decimalLen + "})$";//有小数
							}
							pattern.push(p);
							pattern.push("/");
							if(!checkRegexp(_$this, eval(pattern.join("")))){
								ret = false
							};
						}
					}
				}else if("nonnegative" == regexp){
					if(!checkRegexp( _$this, /^[1-9]+([.][0-9]+){0,1}$/))
					ret = false;
				}else if("positive" == regexp){
					if(!checkRegexp( _$this, /^[1-9]*[1-9][0-9]*$/))
						ret = false;
				}
				//校验正整数
				else if("integer" == regexp || "int" == regexp)
				{
					if(!checkRegexp( _$this, sdfun.constant.reg.integer))
						ret = false;
				}
				//校验账号
				else if("account" == regexp)
				{
					if(!checkRegexp( _$this, sdfun.constant.reg.account))
						ret = false;
				}
				//校验邮件
				else if("email" == regexp)
				{
					if(!checkRegexp( _$this, sdfun.constant.reg.email))
						ret = false;
				}
				//校验电话
				else if("phone" == regexp)
				{
					if(!checkRegexp( _$this, sdfun.constant.reg.phone))
						ret = false;
				}
				//校验端口
				else if("port" == regexp)
				{
					if(!checkPort( _$this))
						ret = false;
				}
				else
				{
					if(sdfun.constant.reg[regexp] != null){
						!checkRegexp( _$this, sdfun.constant.reg[regexp]) && (ret = false);
					}else {
						!checkRegexp( _$this, eval(regexp)) && (ret = false);
					}
				}
			}
			//校验失败跳出循环
			if(!ret){
				return ret;
			}
		}
	});
	//校验都通过返回null
	if(ret){
		_$this = null;
	}
	//校验不通过返回不通过的对象
	return _$this;
}

/**
 * 彻底干掉当前系统用的这个狗屁下拉框的元素
 * @param {} obj 下拉框jquery对象
 */
function emptySelect(obj){
	obj.next("span").remove();
	obj.empty();
}

/**
 * 调整查询层label的宽度
 */
function adjustSearchLebelWidth(id){
	var _maxWidth = -1;
	if(id === undefined){
		id = "search";
	}
	
	if($("#" + id).hasClass("noadjust")){
		return;
	}
	//得到最长的label宽度
	$("#"+ id +" .search_main .search_condition .search_column label").each(function(i, o){
		var _w = $(this).width();
		$(this).attr("ori_width", _w);
		if(_w > _maxWidth){
			_maxWidth = _w;
		}
	});
	//设置label宽度
	if(_maxWidth != -1){
		$("#"+ id +" .search_main .search_condition .search_column label").css("width",_maxWidth);
	}
}


function showLocale(objD)
{
    var str,colorhead,colorfoot;
    var yy = objD.getYear();
    if(yy<1900) yy = yy+1900;
    var MM = objD.getMonth()+1;
    if(MM<10) MM = '0' + MM;
    var dd = objD.getDate();
    if(dd<10) dd = '0' + dd;
    var hh = objD.getHours();
    if(hh<10) hh = '0' + hh;
    var mm = objD.getMinutes();
    if(mm<10) mm = '0' + mm;
    var ss = objD.getSeconds();
    if(ss<10) ss = '0' + ss;
    var ww = objD.getDay();
    if  ( ww==0 )  colorhead="<font color=\"white\">";
    if  ( ww > 0 && ww < 6 )  colorhead="<font color=\"white\">";
    if  ( ww==6 )  colorhead="<font color=\"white\">";
    if  (ww==0)  ww="星期日";
    if  (ww==1)  ww="星期一";
    if  (ww==2)  ww="星期二";
    if  (ww==3)  ww="星期三";
    if  (ww==4)  ww="星期四";
    if  (ww==5)  ww="星期五";
    if  (ww==6)  ww="星期六";
    colorfoot="</font>"
    str = colorhead + yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss + "  " + ww + colorfoot;
    return(str);
};

//ui-autocomplete ui-menu ui-widget ui-widget-content ui-corner-all
(function( $ ) {
	$.widget( "ui.combobox", {
		_create: function() {
			var input,
				that = this,
				select = this.element.hide(),
				selected = select.children( ":selected" ),
				value = select[0].selectedIndex != -1 ? $(select[0].options[select[0].selectedIndex]).text() : "",
				wrapper = this.wrapper = $( "<span>" )
					.addClass( "ui-combobox" )
					.insertAfter( select );
			function removeIfInvalid(element) {
				var value = $( element ).val(),
					matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( value ) + "$", "i" ),
					valid = false;
				select.children( "option" ).each(function() {
					if ( $( this ).text().match( matcher ) ) {
						this.selected = valid = true;
						return false;
					}
				});
				if ( !valid ) {
					// remove invalid value, as it didn't match anything
					$( element )
						.val( "" )
						.attr( "title", value + " didn't match any item" )
						.tooltip( "open" );
					select.val( "" );
					setTimeout(function() {
						input.tooltip( "close" ).attr( "title", "" );
					}, 2500 );
					input.data( "autocomplete" ).term = "";
					return false;
				}
			}
			input = $( "<input>" )
				.appendTo( wrapper )
				.val( value )
				.attr( "title", "" )
				//.attr( "readonly", "readonly" )
				.addClass( "ui-state-default ui-combobox-input" )
				.autocomplete({
					delay: 0,
					minLength: 0,
					source: function( request, response ) {
						var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
						response( select.children( "option" ).map(function() {
							var text = $( this ).text();
							//Decode by OY
							text = sdfun.fn.htmlDecode($.trim(text));
							//if ( this.value && ( !request.term || matcher.test(text) ) )
							if ( !request.term || matcher.test(text) )
								return {
									label: text.replace(
										new RegExp(
											"(?![^&;]+;)(?!<[^<>]*)(" +
											$.ui.autocomplete.escapeRegex(request.term) +
											")(?![^<>]*>)(?![^&;]+;)", "gi"
										), "<strong>$1</strong>" ),
									value: text,
									option: this
								};
						}) );
					},
					select: function( event, ui ) {
						ui.item.option.selected = true;
						
						// åªæä¸ææ¡ä¸­çå¼æ¹åçæ¶åï¼æè§¦åonchangeäºä»¶ eidt by chenqi
						if(select[0] && this.value != $(ui.item.option).text() && typeof(select[0].onchange) == "function") {
							select[0].onchange();
						}
						if(select[0].value) {
							select.next().find('input').removeClass('ui-state-error');
						}
						that._trigger( "selected", event, {
							item: ui.item.option
						});
						
					},
					change: function( event, ui ) {
						if ( !ui.item )
							return removeIfInvalid( this );
					}
				})
				.addClass( "ui-widget-content ui-corner-left" );
			
			
			var ww = /* select[0].style.width */ $(select[0]).css("width"),
				paddingLeft = 5;
			if(ww) {
				if(typeof(ww) === "string" && ww.indexOf('%') > -1) {
					ww = select.parent().width() * parseInt(ww) / 100 - 26 - paddingLeft;
				} else {
					ww = parseInt(ww) - 24 - paddingLeft;
				}
				
			} else {
				ww = select.parent().width() - 24 - paddingLeft;
			}
			
			// IE9ä»¥ä¸çæ¬
			if($.browser.msie) {
				var version = parseInt($.browser.version);
				if(version < 9) {
					ww += 1;
				} else if(version == 9) {
					ww -= 2;
				}
			}
			ww+=1;
			if(select.attr('readonly')) {
				input.attr( "readonly", "readonly" );
			}
			var $ul;
			input.css({'width': ww,'paddingLeft': paddingLeft})
			.data( "autocomplete" )._renderItem = function( ul, item ) {
				if(!ul.attr('setConfig')) {
					ul.attr('setConfig','1');
					$ul = ul;
					ul.css({'maxWidth': (ww+1),'borderWidth':1,'overflow-y':'auto','overflow-x':'auto','borderTopWidth':0});
				}
				
				return $( "<li style='white-space:nowrap;font-size:12px;'>" )
					.data( "item.autocomplete", item )
					.append( "<a>" + item.label + "</a>" )
					.appendTo( ul );
			};

			var $a = $( "<a >" )
				.attr('onselectstart','return false;')
				.attr( "tabIndex", -1 )
				//.css( "width", 27 )
				.css('bottom',0)
				.tooltip()
				.appendTo( wrapper )
				.button({
					icons: {
						primary: "ui-icon-triangle-1-s"
					},
					text: false
				})
				.removeClass( "ui-corner-all" )
				.addClass( "ui-corner-right ui-combobox-toggle" )
				.click(function() {
					// close if already visible
					if(this.disabled)
						return;
					if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
						input.autocomplete( "close" );
						removeIfInvalid( input );
						return;
					}
					// work around a bug (likely same cause as #5265)
					$( this ).blur();

					// pass empty string as value to search for, displaying all results
					input.autocomplete( "search", "" );
					input.focus();
					if ($ul && $ul[0]) {
						// è®¾ç½®ä¸æéé¡¹çæå¤§é«åº¦
						var maxHeight = $(window).height() - (getAbsoluteTop(this) + $(this).height()) - 7;
						var height = $ul.find('.ui-menu-item').size() * 22;
						if(height > 22) {
							height += 1;
						}
						if(maxHeight < 0){
							maxHeight = 100;
						}
						
						if($ul[0].scrollWidth - $ul.width() > 4) {
							maxHeight -= 16;
							height += 16;
							$ul.find('.ui-menu-item').width($ul[0].scrollWidth - 4);
						}
						$ul.css({'maxHeight': maxHeight, 'height': height});
					}
				});
			$a.width(($a.height()||24)+3);
			input.tooltip({
				position: {
					of: this.button
				},
				tooltipClass: "ui-state-highlight"
			});
			
			if(select.attr('frozen') !== undefined) {
				$a.removeClass('ui-button').attr('disabled',true);
				input.css('background-color','#F3F3F3').attr('readonly', 'readonly');
			}
		},
		destroy: function() {
			this.wrapper.remove();
			this.element.show();
			$.Widget.prototype.destroy.call( this );
		},
		show: function(){
			this.element.next().show();
		},
		hide: function(){
			this.element.next().hide();
		},
		disable: function() {
			this.element.attr('disabled',true).next().find('*').attr('disabled',true).end().find('a').removeClass('ui-button');
		},
		enable: function() {
			this.element.attr('disabled',false).next().find('*').attr('disabled',false).end().find('a').addClass('ui-button');
		},
		frozen: function(){
			this.element.next().find('*').attr('disabled',true).end().find('a').removeClass('ui-button').end().find('input').css('background-color','#F3F3F3');
		},
		unfrozen: function() {
			this.element.next().find('*').attr('disabled',false).end().find('a').addClass('ui-button');
		},
		reset: function(value){
			var options = this.element.find("option");
			var _theInput = $(this.wrapper.get(0)).find("input");
			if(value === undefined){
				if(options.length > 0){
					var opt = options[0];
					opt.selected = true;
					_theInput.val(opt.text);
				}else{
					_theInput.val("");
				}
			}else{
				$(options).each(function(i){
					if(value == this.value || value == this.text)
					{
						this.selected = true;
						_theInput.val(this.text);
						return;
					}
				});
			}
		}
		
	});
})( jQuery );