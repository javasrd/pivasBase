(function($){
	
	$.fn.hoverClass = function(options) {
		var $this = this,tdTarget,btnTarget;
		//默认属性
		var defaults = {
			    showHide: 'hide',
			    ButtonDiv: '',
			    targetDiv:''
		};
				 
		var settings = $.extend({}, defaults, options);
		
		//重置界面事件
		$(settings.ButtonDiv,$this).hide();
		$(settings.targetDiv,$this).off();
		$(settings.ButtonDiv+" button").off();
		//隐藏悬浮框
		if(settings.showHide == "hide")
		{
			return this;
		}
		
		//table添加鼠标覆盖事件
		$(settings.targetDiv,$this).hover(function(e){//移进事件
			
			var td = e.currentTarget;
			
			tdTarget = e.currentTarget;
			var tr = $(td,$this).parent();
			
			//绝对位置 
			/* var oTop = $(td).offset().top;
			var oLeft = $(td).offset().left; */
			
			var oHeight = $(td,$this).outerHeight();
			var oWidth = $(td,$this).outerWidth();
			
			var pTop = $(td,$this).position().top; 
			var pLeft = $(td,$this).position().left;
			//悬浮框所在位置
			$(settings.ButtonDiv,$this) 
			.css("top",(pTop + oHeight) + "px")
			.css("left",pLeft + "px")
			.show();
			//删除所选td
			$("table .tdselected",$this).removeClass("tdselected");
			//添加随选td
			$(td,$this).addClass("tdselected");
			//隐藏每天框的关闭按钮
			$(td,$this).find(".paibai_xiao_lists button.close").removeClass("hide");
			
			}, 
			function(e){ //移出事件
				
				var td = e.currentTarget;
				tdTarget = e.currentTarget;
				//$("table .tdselected").removeClass("tdselected");
				//展示每天框的关闭按钮
				$(td,$this).find(".paibai_xiao_lists button.close").addClass("hide");
			
			}); 
		
		//悬浮框添加按钮事件
		$(settings.ButtonDiv+" button").on("click",function(e){
			
			var btnTarget = e.currentTarget;
			
			var type = $(btnTarget).attr("data-type");
			//删除按钮
			if(type == "del"){
				//清空班次
				$(tdTarget,$this).find(".paibai_xiao_lists").empty();
				//重置工时
				$(tdTarget,$this).find(".paibai_xiao_lists").attr("data-daytime","0");
				//重新计算欠休和累计欠休
				recount(tdTarget);
				return;
				
			}
			//班次数量
			var num = $(tdTarget,$this).find(".paibai_xiao_lists").find("button");
			if(num.length == 3 && !oneDayFlag){
				$.alert("排班次数不能超过3次");
				return;
			}
			//获取班次属性
			var workid = $(btnTarget).attr("data-workid");
			var workcolour = $(btnTarget).attr("data-colour");
			var worktime = $(btnTarget).attr("data-worktime");
			var workname = $(btnTarget).text();
			workname = workname.replace(/</g,"&lt;").replace(/>/g,"&gt;");
			var worktype = $(btnTarget).attr("data-type");
			var personid = $(tdTarget,$this).attr("data-personid");
			
			var sameFlag = false;
			//判断是否存在重复班次
			$(tdTarget,$this).find(".paibai_xiao_lists div[data-isactive]").each(function(){
				
				var id = $(this).attr("data-workid");
				if(workid == id){
					
					sameFlag = true;
					return false;
				}
				
			});
			
			if( !oneDayFlag && sameFlag){
				$.alert("已存在相同班次");
				return ;
			}
			//按钮div
			var buttonDom =
			'<div data-workid="'+workid+'" data-isactive="True"> '+
			'<div class="schedualitem  hasborder" data-worktype="'+worktype+'" data-worktime="'+parseFloat(worktime)+'" '+
			'data-workid="'+workid+'" data-personid="'+personid+'" data-colour="'+workcolour+'" style="background-color:'+workcolour+';"> '+
			'<div class="hisschedualitem">'+workname+'</div> '+
			'<button type="button" class="close hisschedualitemclose hide"><span>x</span></button> '+
			'</div></div>';
			//一人单排
			if(oneDayFlag){
				
				$(tdTarget,$this).find(".paibai_xiao_lists").empty();
				$(tdTarget,$this).find(".paibai_xiao_lists").attr("data-daytime","0");
				$(tdTarget,$this).find(".paibai_xiao_lists").append(buttonDom);
				
			}else{
				
				$(tdTarget,$this).find(".paibai_xiao_lists").append(buttonDom);
				
			}
			//工作和其他类型的班次修改工时
			if(type == "0" || type == "1"){

				var time = $(tdTarget,$this).find(".paibai_xiao_lists").attr("data-daytime");
				time = parseFloat(time) +  parseFloat(worktime);
				time = parseFloat(parseFloat(time).toFixed(2));
				
				$(tdTarget,$this).find(".paibai_xiao_lists").attr("data-daytime",time);
				
			}
			//重新计算欠休和累计欠休
			recount(tdTarget);
			
		});
		
		
		return this;
	};
	
	$.fn.valueType = function() {
		$(this).keypress(function(e) {
			var valueType = ($(this).attr('valueType') + '').toLowerCase();
			var char = String.fromCharCode(e.keyCode);
			var result = true;
			switch (valueType) {
				case 'int':
					result = !isNaN(char);
					break;
				case 'number':
					result = !isNaN(char) || char === '.' && this.value.indexOf('.') === -1;
					result = result || char === '-' && this.value.indexOf('-') === -1;
					break;
				case 'time' :
                    result = !isNaN(char) || char === '.' && this.value.indexOf('.') === -1;
                    break;
				default:
					break;
			}
			return result;
		});
		$(this).blur(function() {
			
			var valueType = ($(this).attr('valueType') + '').toLowerCase();
			if(valueType === 'number') {
				if(this.value.charAt(0) === '.') {
					this.value = '0' + this.value;
				}
				if(this.value.charAt(this.value.length - 1) === '.') {
					this.value = this.value.substr(0, this.value.length - 1);
				}
				if(this.value != ""){
					this.value = parseFloat(parseFloat(this.value).toFixed(2));
				}
				
			}
			if (valueType === 'time') {
                if(this.value.charAt(0) === '.') {
                    this.value = '0' + this.value;
                }
                if(this.value.charAt(this.value.length - 1) === '.') {
                    this.value = this.value.substr(0, this.value.length - 1);
                }
                if(this.value != ""){
                    this.value = parseFloat(parseFloat(this.value).toFixed(2));
                }
			}
		});
		return this;
	};
	
})(jQuery);

function htmlDecode(value){
	
	if(value.length == 0 ) return "";
	
	value = value.replace(/&lt;/g,"<");
	value = value.replace(/&gt;/g,">");
	value = value.replace(/&amp;/g,"&");
	value = value.replace(/&quot;/g,"\"");
	value = value.replace(/&apos;/g,"\'");
	  
	return value;
}

function htmlEncode(value){
	
	if(value.length == 0 ) return "";
	
	value = value.replace(/</g,"&lt;");
	value = value.replace(/>/g,"&gt;");
	value = value.replace(/&/g,"&amp;");
	value = value.replace(/\"/g,"&quot;");
	value = value.replace(/\'/g,"&apos;");
	
	return value;
}