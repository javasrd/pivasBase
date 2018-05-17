window.iSS = {
	// 常量
	constants : {
		// 数据类型
		dataType : {
			undef		: "undefined",
			string 		: "string",
			"boolean" 	: "boolean",
			number 		: "number",
			obj			: "object",
			func		: "function",
			array		: "[object Array]"
		},
		// 元素名称
		tagName : {
			input 		: "INPUT",
			div			: "DIV",
			textarea	: "TEXTAREA"
		},
		// 操作事件
		event : {
			mousemove 		: "mousemove",
			mousedown 		: "mousedown",
			mouseup			: "mouseup",
			click			: "click",
			dblclick		: "dblclick",
			keydown			: "keydown",
			keyup			: "keyup",
			keypress		: "keypress",
			propertychange	: "propertychange",
			input			: "input",
			change			: "change",
			paste			: "paste"
		},
		// input类型
		inputType : {
			button 		: "button",
			checkbox	: "checkbox",
			file		: "file",
			hidden		: "hidden",
			image		: "image",
			password	: "password",
			radio		: "radio",
			reset		: "reset",
			submit		: "submit",
			text		: "text"
		},
		// TextRange对象常用字符
		textRange : {
			unit : {
				character 	: "character",
				word		: "word",
				sentence	: "sentence",
				textedit	: "textedit"
			}
		},
		// 常用正则表达式
		regExp : {
			// 双字节字符
			dblChar 		: /[^\x00-\xff]/g,
			// 邮箱
			email 			: /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/,
			// 正整数
			pInteger 		: /^[1-9]\d*$/,
			// 负整数
			nInteger 		: /^-[1-9]\d*$/,
			// 整数
			integer			: /^-?[1-9]\d*$/,
			// 非负整数
			nNInteger		: /^[1-9]\d*|0$/,
			// 非正整数
			nPInteger		: /^-[1-9]\d*|0$/,
			// 正浮点数
			pFloat			: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/,
			// 负浮点数	
			nFloat			: /^-([1-9]\d*\.\d*|0\.\d*[1-9]\d*)$/,
			// 非负浮点数
			nNFloat			: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$/,
			// 非正浮点数
			nPFloat			: /^(-([1-9]\d*\.\d*|0\.\d*[1-9]\d*))|0?\.0+|0$/
		}
	},
	// 判断obj是不是数组
	isArray : function (obj) {
		return obj ? Object.prototype.toString.call(obj) === this.constants.dataType.array : false;
	},
	// 获取str字符长度,dbl默认为false,为true则双字节字符算2位
	length : function(str, dbl) {
		dbl = dbl === true ? true : false;
		if(typeof str === this.constants.dataType.string) {
			var match = str.match(this.constants.regExp.dblChar);
			return str.length + (dbl === true && match ? match.length : 0); 
		}
		return 0;
	},
	getInput : function(formId) {
		var arr = [], that = this;
		(formId ? $("#" + formId).find("input,textarea") : $("input,textarea")).each(function() {
			if(this.type === that.constants.inputType.text 
				|| this.type === that.constants.inputType.password 
				|| this.tagName === that.constants.tagName.textarea) {
				arr.push(this);
			}
		});
		return $(arr);
	},
	// 获取光标在输入框中的位置和选中文本
	getSelection : function() {
		var result = {};
		if($.browser.msie) {
			var rng = document.selection.createRange(),
			// 若未选中文本, 光标的开始位置即结束位置, 反之光标的开始位置为选中文本的开始位置, 结束位置为选中文本的结束位置
				selectionLength = rng.text.length;
			result.text = rng.text;
			// 更改范围的开始位置为0, 范围的结束位置不变
			rng.moveStart(this.constants.textRange.unit.character, -document.activeElement.value.length);
			// 获取范围的长度, 即光标结束位置, 减去选中文本的长度, 即为光标的开始位置
			result.end = rng.text.length;
			result.start = rng.text.length - selectionLength;
			// 我们只是为了获取光标的起始的位置, 并不想更改任何东西, 把范围的开始位置改回来
			rng.moveStart(this.constants.textRange.unit.character, result.end - selectionLength);
		} else {
			this.getInput().each(function() {
				if(this.selectionStart < this.selectionEnd && this.selectionEnd > 0) {
					result.start = this.selectionStart;
					result.end = this.selectionEnd;
					result.text = this.value.substring(this.selectionStart, this.selectionEnd);
					this.selectionStart = this.selectionEnd = 0;
					return false;
				}
			});
		}
		return result;
	},
	
	// 取消键盘的onkeydown事件
	cancelKey : function(e) {
		e = e || window.event;
		e.keyCode = 0;
		if($.browser.msie) {
			e.returnValue = false;
		} else if(e.preventDefault) {
			e.preventDefault();
		}
		return false;
	},
	
	//限制输入长度
	maxlength : function(parentId, dbl) {
		var that = this, temp = {};
		this.getInput(parentId).each(function(e) {
			$(this).bind(that.constants.event.keydown, function() {
				temp.preValue = this.value;
				$.extend(temp, that.getSelection());
				temp.beforeValue = this.value.substr(0, temp.selectionStart);
				temp.afterValue = this.value.substr(temp.end);
			}).bind(that.constants.event.keypress, function(e) {
				if(!that.getSelection().text && that.length(this.value, dbl) >= this.maxlen) {
					return that.cancelKey(e);
				}
			}).bind(that.constants.event.paste, function(e) {
				if(!that.getSelection().text && that.length(this.value, dbl) >= this.maxlen) {
					return that.cancelKey(e);
				}
			});
			this.oninput = this.onpropertychange = function() {
				if(that.length(this.value, dbl) > this.maxlen) {
					var text = this.value.substr(0, this.maxlen);
					while(that.length(text, dbl) > this.maxlen) {
						text = text.substr(0, text.length - 1);
					}
					this.value = text;
				}
			}
		});
	},
	
	formatInput : function(formId) {
		$("#" + formId + " :input").each(function() {
			this.onblur = function() {
				this.value = $.trim(this.value);
			}
		});
	},
	
	cbc : function(obj, objs) {
		if(typeof obj === this.constants.dataType.string) {
			obj = $("#" + obj);
		} else {
			obj = $(obj);
		}
		if(typeof objs === this.constants.dataType.string) {
			objs = $("input[type='checkbox'][name='" + objs + "']");
		} else {
			objs = $(objs);
		}
		if(obj[0] && obj[0].type === this.constants.inputType.checkbox) {
			obj.click(function() {
				objs.attr('checked', this.checked);
			});
			objs.click(function() {
				obj.attr('checked', true);
				objs.each(function() {
					if(!this.checked) {
						obj.attr('checked', false);
						return false;
					}
				});
			});
		}
	}
};