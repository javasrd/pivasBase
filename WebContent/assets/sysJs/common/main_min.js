var isIE6 = false;
if (document.all) {
	var v = navigator.appVersion;
	if (v.indexOf("6.0") > 0){
		isIE6 = true;
	}
}

//非表格伸缩
function pucker(id, self) {
	var obj = document.getElementById(id);
	var cssName = $(self).attr("class");
	$(self).removeClass();
	if (obj.style.display == "none") {
		if (cssName == "cPointCat2") {
			$(self).addClass("cPoint2");
		} else {
			$(self).addClass("cPoint");
		}

		obj.style.display = "";
		if (!isIE6) {
			obj.height = "50%";
		}
	} else {
		if (cssName == "cPoint2") {
			$(self).addClass("cPointCat2");
		} else {
			$(self).addClass("cPointCat");
		}
		obj.style.display = "none";
	}
}

//表格上方伸缩
function upDownPucker(id, self, gridId, delHeight, heightOther) {
	var width = 0;
	delHeight = delHeight==null?0:delHeight;
	heightOther = heightOther==null?0:heightOther;
	if (gridId != null && $("#" + gridId).GetOptions()) {
		//记录原始宽度
		width = $("#" + gridId).GetOptions().width;
		
		// 设置表格长度为10，解决伸缩问题
		$("#" + gridId).flexResize('1px', '1px');
	}
	if(typeof(width) === "number"){
		width = (width + 'px');
	}
	//需要隐藏或显示的元素
	var obj = document.getElementById(id);
	var cssName = $(self).attr("class");
	$(self).removeClass();
	//显示
	if (obj.style.display == "none") {
		if (cssName == "cPointCat2") {
			$(self).addClass("cPoint2");
		} else {
			$(self).addClass("cPoint");
		}

		obj.style.display = "";
		if (!isIE6) {
			obj.height = "50%";
		}
		
		if (gridId != null && $("#" + gridId).GetOptions()) {
			// 重新设置表格自适应
			$("#" + gridId).flexResize(width, getGridHeight(delHeight) + 'px');
		}
		
	} 
	//隐藏
	else {
		if (cssName == "cPoint2") {
			$(self).addClass("cPointCat2");
		} else {
			$(self).addClass("cPointCat");
		}
		obj.style.display = "none";
		
		if (gridId != null && $("#" + gridId).GetOptions()) {
			// 重新设置表格自适应
			$("#" + gridId).flexResize(width, getGridHeight(delHeight - heightOther)+ 'px');
		}
	}

}

var _leftRightPuckerObj = new Object();
//表格左侧伸缩
function leftRightPucker(id, self, gridId, ieWidthPer, delHeight) {
	var height = 0;
	ieWidthPer = ieWidthPer==null?0:ieWidthPer;
	var obj = document.getElementById(id);
	var cssName = $(self).attr("class");
	$(self).removeClass();
	//显示
	if (obj.style.display == "none") {
		if (cssName == "cPointCat2") {
			$(self).addClass("cPoint2");
		} else {
			$(self).addClass("cPoint");
		}
		
		obj.style.display = "";
		if (!isIE6) {
			obj.height = "50%";
		}
		
		if (gridId != null && $("#" + gridId).GetOptions()) {
			ieWidthPer = (ieWidthPer == 0?null:ieWidthPer);
			if(_leftRightPuckerObj.height){
				height = _leftRightPuckerObj.height;
			}else{
				height = getGridHeight(delHeight);
			}
			$("#" + gridId).flexResize(getGridWidth(ieWidthPer), height);
		}
	} 
	//隐藏
	else {
		if (gridId != null && $("#" + gridId).GetOptions()) {
			//记录原始高度
			_leftRightPuckerObj.height = $("#" + gridId).GetOptions().height;
			
			// 设置表格长度为10，解决伸缩问题
			//$("#" + gridId).flexResize('1', '1');
		}
		
		if (cssName == "cPoint2") {
			$(self).addClass("cPointCat2");
		} else {
			$(self).addClass("cPointCat");
		}
		obj.style.display = "none";
		
		if (gridId != null && $("#" + gridId).GetOptions()) {
			ieWidthPer = (ieWidthPer == 0?null:ieWidthPer);
			height = getGridHeight(delHeight);
			$("#" + gridId).flexResize(getGridWidth(ieWidthPer)-5, height);
		}
	}
}

// 切换图片
function MM_preloadImages() { // v3.0
	var d = document;
	if (d.images) {
		if (!d.MM_p) {
			d.MM_p = new Array();
		}
		var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
		for (i = 0; i < a.length; i++) {
			if (a[i].indexOf("#") != 0) {
				d.MM_p[j] = new Image;
				d.MM_p[j++].src = a[i];
			}
		}
	}
}

function MM_swapImgRestore() { // v3.0
	var i, x, a = document.MM_sr;
	for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++) {
		x.src = x.oSrc;
	}
}

function MM_findObj(n, d) { // v4.01
	var p, i, x;
	if (!d) {
		d = document;
	}
		
	if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
		d = parent.frames[n.substring(p + 1)].document;
		n = n.substring(0, p);
	}
	if (!(x = d[n]) && d.all) {
		x = d.all[n];
	}
	for (i = 0; !x && i < d.forms.length; i++) {
		x = d.forms[i][n];
	}
	for (i = 0; !x && d.layers && i < d.layers.length; i++) {
		x = MM_findObj(n, d.layers[i].document);
	}
	if (!x && d.getElementById) {
		x = d.getElementById(n);
	}
	return x;
}

function MM_swapImage() { // v3.0
	var i, j = 0, x, a = MM_swapImage.arguments;
	document.MM_sr = new Array;
	for (i = 0; i < (a.length - 2); i += 3) {
		if ((x = MM_findObj(a[i])) != null) {
			document.MM_sr[j++] = x;
			if (!x.oSrc) {
				x.oSrc = x.src;
			}
			x.src = a[i + 2];
		}
	}
}