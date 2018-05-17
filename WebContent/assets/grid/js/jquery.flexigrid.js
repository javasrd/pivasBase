/// <reference path="../intellisense/jquery-1.2.6-vsdoc-cn.js" />
/// <reference path="../lib/blackbird.js" />
(function($) {
    $.addFlex = function(t, p) {
        if (t.grid) return false; //如果Grid已经存在则返回
        // 引用默认属性
        p = $.extend({
            height: 'auto', //flexigrid插件的高度，单位为px
            width: 'auto', //宽度值，auto表示根据每列的宽度自动计算
            striped: true, //是否显示斑纹效果，默认是奇偶交互的形式
            novstripe: false,
            minwidth: 30, //列的最小宽度
            minheight: 80, //列的最小高度
            resizable: false, //resizable table是否可伸缩
            url: false, //ajax url,ajax方式对应的url地址
            method: 'POST', // data sending method,数据发送方式
            dataType: 'json', // type of data loaded,数据加载的类型，xml,json
            errormsg: i18n_flexigrid_queryFail, //错误提升信息
            usepager: false, //是否分页
            nowrap: true, //是否不换行
            sortorder:'asc',
            tableId:'flexiGrid',
            page: 1, //current page,默认当前页
            total: 1, //total pages,总页面数
            useRp: true, //use the results per page select box,是否可以动态设置每页显示的结果数
            rp: 20, // results per page,每页默认的结果数
            rpOptions: [10, 20, 50, 100], //可选择设定的每页结果数
            title: false, //是否包含标题
            pagestat: i18n_flexigrid_recordDisplayFromTo, //显示当前页和总页面的样式
            procmsg: i18n_flexigrid_loading, //正在处理的提示信息
            query: '', //搜索查询的条件
            qtype: '', //搜索查询的类别
            qop: "Eq", //搜索的操作符
            nomsg: i18n_flexigrid_noRecord, //无结果的提示信息
            minColToggle: 1, //minimum allowed column to be hidden
            showToggleBtn: true, //show or hide column toggle popup
            hideOnSubmit: true, //显示遮盖
            showTableToggleBtn: false, //显示隐藏Grid 
            autoload: true, //自动加载
            blockOpacity: 0.5, //透明度设置
            onToggleCol: false, //当在行之间转换时
            onChangeSort: false, //当改变排序时
            onSuccess: false, //成功后执行
            onSubmit: false, // using a custom populate function,调用自定义的计算函数
            showcheckbox: false, //是否显示第一列的checkbox（用于全选）
            checkboxName: 'flexigridCheckbox', //多选框标签的name名字
            showradio: false, 	//是否第一列显示单选框
            radioName: 'flexigridRadio', //单选框标签的name名字
            rowhandler: false, 	//是否启用行的扩展事情功能,在生成行时绑定事件，如双击，右键等
            rowbinddata: false,	//配合上一个操作，如在双击事件中获取该行的数据
            extParam: {},		//添加extParam参数可将外部参数动态注册到grid，实现如查询等操作
            gridClass: "cbit-grid",/*"bbit-grid"*/ //grid样式
            numCheckBoxTitle:'',
            onrowchecked: false,//在每一行的的checkbox选中状态发生变化时触发某个事件
            isColSel: true, //是否显示列选择过滤功能
            isDragCol:true, //鼠标拖动表头时是否改变列的顺序、拖动单元格边框时是否改变单元格宽度
            m_length:30		//单元格内容最大长度，超过长度添加tip
        }, p);

        $(t)
		.show() //show if hidden
		.attr({ cellPadding: 0, cellSpacing: 0, border: 0 })  //remove padding and spacing
		.removeAttr('width') //remove width properties	
		;

        //create grid class
        var g = {
            hset: {},
            rePosDrag: function() {

                var cdleft = 0 - this.hDiv.scrollLeft;
                if (this.hDiv.scrollLeft > 0) cdleft -= Math.floor(p.cgwidth / 2);

                $(g.cDrag).css({ top: g.hDiv.offsetTop + 1 });
                var cdpad = this.cdpad;

                $('div', g.cDrag).hide();
                //避免jQuery :visible 无效的bug
                var i = 0;
                var m = $.browser.mozilla ? 0 : ($.browser.chrome ? 1:1);
                $('thead tr:first th:visible', this.hDiv).each(function() {
                    if ($(this).css("display") == "none") {
                        return;
                    }
                    var n = i;
                    //var n = $('thead tr:first th:visible', g.hDiv).index(this);			 	  
                    var cdpos = parseInt($('div', this).width());
                    var ppos = cdpos;
                    if (cdleft == 0)
                        cdleft -= Math.floor(p.cgwidth / 2);

                    cdpos = cdpos + cdleft + cdpad;

                    // in iframe and ie
                    $('div:eq(' + n + ')', g.cDrag).css({ 'left': cdpos - m * n + 1 - m + 'px' }).show();
                    
                    // not in iframe : $('div:eq(' + n + ')', g.cDrag).css({ 'left': cdpos + 'px' }).show();

                    cdleft = cdpos;
                    i++;
                }
				);

            },
            fixHeight: function(newH) {
                newH = false;
                if (!newH) newH = $(g.bDiv).height();
                var hdHeight = $(this.hDiv).height();
                $('div', this.cDrag).each(
						function() {
						    $(this).height(newH + hdHeight);
						}
					);

                var nd = parseInt($(g.nDiv).height());
                
                //解决选择显示列的时候，有时候不出滚动条的问题。
                if (nd >= newH && newH != 0)
                    $(g.nDiv).height(newH);//.width(200);
                else
                    $(g.nDiv).height('auto').width('auto');

                $(g.block).css({ height: newH, marginBottom: (newH * -1) });

                var hrH = g.bDiv.offsetTop + newH;
                if (p.height != 'auto' && p.resizable) hrH = g.vDiv.offsetTop;
                $(g.rDiv).css({ height: hrH });

            },
            dragStart: function(dragtype, e, obj) { //default drag function start

                if (dragtype == 'colresize') //column resize
                {
                    $(g.nDiv).hide(); $(g.nBtn).hide();
                    var n = $('div', this.cDrag).index(obj);
                    //var ow = $('th:visible div:eq(' + n + ')', this.hDiv).width();
                    var ow = $('th:visible:eq(' + n + ') div', this.hDiv).width();
                    $(obj).addClass('dragging').siblings().hide();
                    $(obj).prev().addClass('dragging').show();

                    this.colresize = { startX: e.pageX, ol: parseInt(obj.style.left), ow: ow, n: n };
                    $('body').css('cursor', 'col-resize');
                }
                else if (dragtype == 'vresize') //table resize
                {
                    var hgo = false;
                    $('body').css('cursor', 'row-resize');
                    if (obj) {
                        hgo = true;
                        $('body').css('cursor', 'col-resize');
                    }
                    this.vresize = { h: p.height, sy: e.pageY, w: p.width, sx: e.pageX, hgo: hgo };

                }

                else if (dragtype == 'colMove') //column header drag
                {
                    $(g.nDiv).hide(); $(g.nBtn).hide();
                    this.hset = $(this.hDiv).offset();
                    this.hset.right = this.hset.left + $('table', this.hDiv).width();
                    this.hset.bottom = this.hset.top + $('table', this.hDiv).height();
                    this.dcol = obj;
                    this.dcoln = $('th', this.hDiv).index(obj);

                    this.colCopy = document.createElement("div");
                    this.colCopy.className = "colCopy";
                    this.colCopy.innerHTML = obj.innerHTML;
                    if ($.browser.msie) {
                        this.colCopy.className = "colCopy ie";
                    }


                    $(this.colCopy).css({ position: 'absolute', 'float': 'left', display: 'none', textAlign: obj.align });
                    $('body').append(this.colCopy);
                    $(this.cDrag).hide();

                }

                $('body').noSelect();

            },
            reSize: function() {
                this.gDiv.style.width = p.width;
                this.bDiv.style.height = p.height;
            },
            dragMove: function(e) {

                if (this.colresize) //column resize
                {
                    var n = this.colresize.n;
                    var diff = e.pageX - this.colresize.startX;
                    var nleft = this.colresize.ol + diff;
                    var nw = this.colresize.ow + diff;
                    if (nw > p.minwidth) {
                        $('div:eq(' + n + ')', this.cDrag).css('left', nleft);
                        this.colresize.nw = nw;
                    }
                }
                else if (this.vresize) //table resize
                {
                    var v = this.vresize;
                    var y = e.pageY;
                    var diff = y - v.sy;
                    if (!p.defwidth) p.defwidth = p.width;
                    if (p.width != 'auto' && !p.nohresize && v.hgo) {
                        var x = e.pageX;
                        var xdiff = x - v.sx;
                        var newW = v.w + xdiff;
                        if (newW > p.defwidth) {
                            this.gDiv.style.width = newW + 'px';
                            p.width = newW;
                        }
                    }
                    var newH = v.h + diff;
                    if ((newH > p.minheight || p.height < p.minheight) && !v.hgo) {
                        this.bDiv.style.height = newH + 'px';
                        p.height = newH;
                        this.fixHeight(newH);
                    }
                    v = null;
                }
                else if (this.colCopy) {
                    $(this.dcol).addClass('thMove').removeClass('thOver');
                    if (e.pageX > this.hset.right || e.pageX < this.hset.left || e.pageY > this.hset.bottom || e.pageY < this.hset.top) {
                        //this.dragEnd();
                        $('body').css('cursor', 'move');
                    }
                    else
                        $('body').css('cursor', 'pointer');

                    $(this.colCopy).css({ top: e.pageY + 10, left: e.pageX + 20, display: 'block' });
                }

            },
            dragEnd: function() {
                if (this.colresize) {
                    var n = this.colresize.n;
                    var nw = this.colresize.nw;
                    //$('th:visible div:eq(' + n + ')', this.hDiv).css('width', nw);
                    $('th:visible:eq(' + n + ') div', this.hDiv).css('width', nw);

                    $('tr', this.bDiv).each(
									function() {
									    $('td:visible div:eq(' + n + ')', this).css('width', nw);
									    //$('td:visible:eq(' + n + ') div', this).css('width', nw);
									}
								);
                    this.hDiv.scrollLeft = this.bDiv.scrollLeft;
                    $('div:eq(' + n + ')', this.cDrag).siblings().show();
                    $('.dragging', this.cDrag).removeClass('dragging');
                    this.rePosDrag();
                    this.fixHeight();
                    this.colresize = false;
                }
                else if (this.vresize) {
                    this.vresize = false;
                }
                else if (this.colCopy) {
                    $(this.colCopy).remove();
                    if (this.dcolt != null) {
                        if (this.dcoln > this.dcolt)
                        { $('th:eq(' + this.dcolt + ')', this.hDiv).before(this.dcol); }
                        else
                        { $('th:eq(' + this.dcolt + ')', this.hDiv).after(this.dcol); }
                        this.switchCol(this.dcoln, this.dcolt);
                        $(this.cdropleft).remove();
                        $(this.cdropright).remove();
                        this.rePosDrag();
                    }
                    this.dcol = null;
                    this.hset = null;
                    this.dcoln = null;
                    this.dcolt = null;
                    this.colCopy = null;
                    $('.thMove', this.hDiv).removeClass('thMove');
                    $(this.cDrag).show();
                }
                $('body').css('cursor', 'default');
                $('body').noSelect(false);
            },
            toggleCol: function(cid, visible) {
                var ncol = $("th[axis='col" + cid + "']", this.hDiv)[0];
                var n = $('thead th', g.hDiv).index(ncol);
                var cb = $('input[value=' + cid + ']', g.nDiv)[0];
                if (visible == null) {
                	//增加判断如果隐藏属性第一次没有值，默认取新增的用于列显示的隐藏属性
                	if(ncol.hide == null){
                		visible = ncol.hideForSelect;
                	}else{
                		visible = ncol.hide
                	}
                }
                if ($('input:checked', g.nDiv).length < p.minColToggle && !visible) return false;
                if (visible) {
                    ncol.hide = false;
                    $(ncol).show();
                    cb.checked = true;
                }
                else {
                    ncol.hide = true;
                    $(ncol).hide();
                    cb.checked = false;
                }
                $('tbody tr', t).each
							(
								function() {
								    if (visible)
								        $('td:eq(' + n + ')', this).show();
								    else
								        $('td:eq(' + n + ')', this).hide();
								}
							);
                this.rePosDrag();
                if (p.onToggleCol) p.onToggleCol(cid, visible);
                return visible;
            },
            switchCol: function(cdrag, cdrop) { //switch columns
                $('tbody tr', t).each
					(
						function() {
						    if (cdrag > cdrop)
						        $('td:eq(' + cdrop + ')', this).before($('td:eq(' + cdrag + ')', this));
						    else
						        $('td:eq(' + cdrop + ')', this).after($('td:eq(' + cdrag + ')', this));
						}
					);
                //switch order in nDiv
                if (cdrag > cdrop)
                    $('tr:eq(' + cdrop + ')', this.nDiv).before($('tr:eq(' + cdrag + ')', this.nDiv));
                else
                    $('tr:eq(' + cdrop + ')', this.nDiv).after($('tr:eq(' + cdrag + ')', this.nDiv));
                if ($.browser.msie && $.browser.version < 7.0) $('tr:eq(' + cdrop + ') input', this.nDiv)[0].checked = true;
                this.hDiv.scrollLeft = this.bDiv.scrollLeft;
            },
            scroll: function() {
                this.hDiv.scrollLeft = this.bDiv.scrollLeft;
                this.rePosDrag();
            },
            hideLoading: function() {
            	//增加是否使用分页的判断
            	if (p.usepager){
            		$('.pReload', this).removeClass('loading');
            	}
                
                if (p.hideOnSubmit) $(g.block).remove();
                
                //增加是否使用分页的判断
                if (p.usepager){
                	$('.pPageStat', this).html(p.errormsg);
                }
                
                this.loading = false;
            }
            ,
            addData: function(data) { //parse data   
                if (p.preProcess)
                { data = p.preProcess(data); }
                
                //增加判断不分页时不修改分页栏
            	if (p.usepager){
            		$('.pReload', this.pDiv).removeClass('loading');
            	}
            	this.loading = false;
                if (!data) {
                	//增加判断不分页时不修改分页栏
                	if (p.usepager){
                		$('.pPageStat', this.pDiv).html(p.errormsg);
                	}
                    return false;
                }
                var temp = p.total;
                if (p.dataType == 'xml') {
                    p.total = +$('rows total', data).text();
                }
                else {
                    p.total = data.total;
                }
                if (p.total < 0) {
                    p.total = temp;
                }
                if (p.total == 0) {
                    $('tr, a, td, div', t).unbind();
                    $(t).empty();
                    p.pages = 1;
                    p.page = 1;
                    
                    //增加判断不分页时不修改分页栏
                	if (p.usepager){
                		this.buildpager();
                        $('.pPageStat', this.pDiv).html(p.nomsg);
                	}
                	//重新设置表头文字 begin
                	var _ths = $('thead tr:first th', g.hDiv);
                	var _thsLen = _ths.length;
                	//表头下拉框
                    var _nDivTds = $('tbody tr td:odd', g.nDiv);
                	$(_ths).each(function(_j) {
                		var _idx = $(this).attr('axis').substr(3);
                		if(_idx == "-1"){//等于-1是前面的check box
                			_thsLen--;
                		}else{
                			var _nDivTd = null;
                			if(_nDivTds && _nDivTds.length == _thsLen){
                				_nDivTd = _nDivTds[_idx];
                			}
                			var cm = p.colModel[_idx];
                			if(cm.dynTitle == true && data.titles != null && data.titles[_idx] != null){
                				var _title = data.titles[_idx];
                				$(this).find('div').text(_title);
                				if(_nDivTd){
                					$(_nDivTd).text(_title);
                    			}
                			}
                		}
                	});
                	//重新设置表头文字 end
                    if (p.hideOnSubmit) {$(g.block).remove();}
                    return false;
                }

                p.pages = Math.ceil(p.total / p.rp);

                if (p.dataType == 'xml')
                { p.page = +$('rows page', data).text(); }
                else
                { p.page = data.page; }
                
                //增加判断不分页时不修改分页栏
            	if (p.usepager){
            		this.buildpager();
            	}

                var ths = $('thead tr:first th', g.hDiv);
                var _thsLen = ths.length;
                var thsdivs = $('thead tr:first th div', g.hDiv);
                //表头下拉框
                var nDivTds = $('tbody tr td:odd', g.nDiv);
                var tbhtml = [];
                var _createTbody = true;
                if(p.appendData) {//增加向表格追加行判断
                	var hasData = $(t).find('tbody').length;
                	if(hasData != 0){
                		_createTbody = false;
                	}
                }
                if(_createTbody){
                	tbhtml.push("<tbody>");
                }
                if (p.dataType == 'json') {
                    if (data.rows != null) {
                        $.each(data.rows, function(i, row) {
                        	if(row.cell == null){
                        		return;
                        	}
                        	
                            tbhtml.push("<tr id='", p.tableId+"_row", row.id, "'");
                            if(p.createTrPropName) {
                            	tbhtml.push(" " + p.createTrPropName + "='"+p.createTrPropNameFn()+"'");
                            }
                            if (i % 2 && p.striped) {
                                tbhtml.push(" class='erow'");
                            }
                            //alert(row.name +";"+row.age+";"+row.address);
                            //alert(row.cell.join("_FG$SP_"));
                            if (p.rowbinddata) {
                            			//.replace("'",'&apos;').replace('"','&amp;')
                                tbhtml.push("ch='", g.htmlDecode(row.cell.join("_FG$SP_")), "'");
                            }
                            tbhtml.push(">");
                            var trid = row.id;
                            //遍历表头
                            $(ths).each(function(j) {
                                var tddata = "";
                                var tdclass = "";
                                tbhtml.push("<td align='", this.align, "'");
                                //得到列索引,checkbox索引为-1,其它的递增
                                var idx = $(this).attr('axis').substr(3);

                                if (p.sortname && p.sortname == $(this).attr('abbr')) {
                                    tdclass = 'sorted';
                                }
                                //如果用于隐藏或用于刚开始隐藏后面列可选择显示，隐藏列
                                if (this.hide||this.hideForSelect) {
                                    tbhtml.push(" style='display:none;'");
                                }
                                var width = thsdivs[j].style.width;
                                var div = [];
                                div.push("<div style='text-align:", this.align, ";width:", width, ";");
                                if (p.nowrap == false) {
                                    div.push("white-space:normal; height:auto;");
                                    div.push("'>");
                                }else{
                                	div.push("'");
                                	div.push("");
                                	div.push(">");
                                }
                                //div.push("'>");
                                if (idx == "-1") { //checkbox
                                	//增加判断如果是多选列表，显示多选框
                                	if(p.showcheckbox){
                                		div.push("<input style='margin-top:3px;' type='checkbox' id='chk_", row.id, "' class='itemchk' value='", row.id, "' name='"+p.checkboxName+"'/>");
                                	}
                                	//增加判断如果是单选列表，显示单选框
                                	else if(p.showradio){
                                		div.push("<input style='margin-top:3px;' type='radio' id='chk_", row.id, "' class='itemchk' value='", row.id, "' name='"+p.radioName+"'/>");
                                	}
                                    
                                    if (tdclass != "") {
                                        tdclass += " chboxtd";
                                    } else {
                                        tdclass += "chboxtd";
                                    }
                                    _thsLen--;
                                }
                                else {
                                	var _nDivTd = null;
                                	if(nDivTds && nDivTds.length == _thsLen){
                                		_nDivTd = nDivTds[j];
                                	}
                                	var _th = $(this);
                                	var _hasVal = false;
                                	var _inner = null;
                                	if ((p.colModel && idx < p.colModel.length) && (data.rawRecords && data.rawRecords.length != 0)){
                                		var cm = p.colModel[idx],
                                			colArray = cm.name.split("."),
                            			 	_record = data.rawRecords[i];
                            			//支持对象.属性名
                                		if(colArray.length > 1){
                                			if(_record[colArray[0]] != null){
                                				_inner = _record[colArray[0]][colArray[1]];
                                				_hasVal = true;
                                			}
                                		}else{
	                                		//直接从原始数据中按照定义的列名取值,这样后台的顺序可以随意,只保证前台顺序即可
	                            			if(_record[cm.name] != null){
	                            				_inner = _record[cm.name];
	                            				_hasVal = true;
	                            			}
                                		}
                                		//重新设置表头文字
                                		if(cm.dynTitle == true && data.titles != null && data.titles[idx] != null){
                                			var _title = data.titles[idx];
                                			_th.find('div').text(_title);
                                			if(_nDivTd){
                                				$(_nDivTd).text(_title);
                                			}
                                		}
                                	}
                                	
                                	if(!_hasVal && idx < row.cell.length){
                               			//_inner = row.cell[idx];
                                	}
                                	if(this.optType == "optBtn"){
                                		$.each(eval(this.optDetail), function(i, optObj) {
                                			    //是否需要置灰
                                				var disbaledAttr = "";
                                			    if(optObj.optAuthFlag == "false"){
                                			    	disbaledAttr = "disabled='disabled'";
                                			    	
                                			    	//设置图片为对应的置灰图片
                                			    	var splIndex = optObj.optImage.lastIndexOf(".");
                                			    	optObj.optImage = optObj.optImage.substring(0,splIndex)+"_disabled"+optObj.optImage.substring(splIndex);
                                			    }
                                			    
                                			    //添加操作按钮
	                                    		div.push("<a "+ disbaledAttr +" onclick='" + optObj.optMethod + "(\"" + _inner + "\")' style='cursor:pointer;'>");
	                                    		div.push("<img src='" + optObj.optImage +"' width='16' title='" + optObj.optTip +"' height='16'></img>");
	                                    		div.push("</a>");
	                                    		div.push("&nbsp;&nbsp;");
                                		});
                                		
                                	}else if(this.optType == "optLink"){
                                		$.each(eval(this.optDetail), function(i, optObj) {
                                			//添加操作链接
                                    		div.push("<a onclick='" + optObj.optMethod + "(\"" + _inner + "\")' style='cursor:pointer;'>");
                                    		div.push(optObj.optTip);
                                    		div.push("</a>");
                                    		div.push("&nbsp;&nbsp;");
                                		});
                                	}else{
	                                    var divInner = "";
	                                    if(typeof _inner=='string'){
	                                    	divInner = _inner.replace(new RegExp("<","g"),"&lt;").replace(new RegExp(">","g"),"&gt;");
	                                    }else{
	                                    	divInner = _inner;
	                                    }
	                                    if (this.process) {
	                                    	var _row = data.rawRecords[i];
	                                    	divInner = divInner === null || divInner === undefined ? '' : divInner;
	                                        divInner = this.process(divInner, p.tableId + "_row" + trid, _row );
	                                    }
	                                    else if (p.nowrap == true && typeof(divInner)==="string" 
	                                    	&& p.m_length != 0 && divInner.length > p.m_length) {
	                                    		//超过长度的添加tip
		                                    	/*$.each(div, function(i, v){
		                                    		if(v == ""){
		                                    			div[i] = " title='"+ g.htmlDecode(divInner) + "'";
		                                    			return false;
		                                    		}
		                                    	});*/
	                                    }
	                                    div.push(divInner);
                                	}
                                }
                                div.push("</div>");
                                if (tdclass != "") {
                                    tbhtml.push(" class='", tdclass, "'");
                                }
                                tbhtml.push(">", div.join(""), "</td>");
                            });
                            tbhtml.push("</tr>");
                        }
					    );
                    }

                } else if (p.dataType == 'xml') {
                    i = 1;
                    $("rows row", data).each
				(
				 	function() {
				 	    i++;
				 	    var robj = this;
				 	    var arrdata = new Array();
				 	    $("cell", robj).each(function() {
				 	        arrdata.push($(this).text());
				 	    });
				 	    var nid = $(this).attr('id');
				 	    tbhtml.push("<tr id='", "row", nid, "'");
				 	    if (i % 2 && p.striped) {
				 	        tbhtml.push(" class='erow'");
				 	    }
				 	    if (p.rowbinddata) {
				 	        tbhtml.push("ch='", arrdata.join("_FG$SP_"), "'");
				 	    }
				 	    tbhtml.push(">");
				 	    var trid = nid;
				 	    $(ths).each(function(j) {
				 	        tbhtml.push("<td align='", this.align, "'");
				 	        if (this.hide) {
				 	            tbhtml.push(" style='display:none;vertical-align:middle;'");
				 	        }
				 	        var tdclass = "";
				 	        var tddata = "";
				 	        var idx = $(this).attr('axis').substr(3);

				 	        if (p.sortname && p.sortname == $(this).attr('abbr')) {
				 	            tdclass = 'sorted';
				 	        }
				 	        var width = thsdivs[j].style.width;

				 	        var div = [];
				 	        div.push("<div style='text-align:", this.align, ";width:", width, ";");
				 	        if (p.nowrap == false) {
				 	            div.push("white-space:normal;height:auto;");
				 	        }
				 	        div.push("'>");

				 	        if (idx == "-1") { //checkbox
				 	        	//增加判断如果是多选列表，显示多选框
                            	if(p.showcheckbox){
                            		div.push("<input type='checkbox' id='chk_", nid, "' class='itemchk' value='", nid, "' name='"+p.checkboxName+"'/>");
                            	}
				 	        	//如果是单选列表，显示单选框
                            	else if(p.showradio){
				 	        		div.push("<input type='radio' id='chk_", nid, "' class='itemchk' value='", nid, "' name='"+p.radioName+"'/>");
				 	        	}
				 	            
				 	            if (tdclass != "") {
				 	                tdclass += " chboxtd";
				 	            } else {
				 	                tdclass += "chboxtd";
				 	            }
				 	        }
				 	        else {
				 	            var divInner = arrdata[idx] || "&nbsp;";
				 	            if (p.rowbinddata) {
				 	                tddata = arrdata[idx] || "";
				 	            }
				 	            if (this.process) {
				 	            	var _row = data.rawRecords[i];
				 	            	divInner = divInner === null || divInner === undefined ? '' : divInner;
                                    divInner = this.process(divInner, p.tableId+"_row"+trid, _row );
				 	            }
				 	            div.push(divInner);
				 	        }
				 	        div.push("</div>");
				 	        if (tdclass != "") {
				 	            tbhtml.push(" class='", tdclass, "'");
				 	        }
				 	        tbhtml.push(" axis='", tddata, "'", ">", div.join(""), "</td>");
				 	    });
				 	    tbhtml.push("</tr>");
				 	}
				);

                }
                //增加向表格追加行判断
                if(_createTbody){
                	tbhtml.push("</tbody>");
                	$(t).html(tbhtml.join(""));
                }else{
                	$(t).find("tr:last").after(tbhtml.join(""));
                }

                //this.rePosDrag();
                this.addRowProp();
                if (p.onSuccess) p.onSuccess(data);
                if (p.hideOnSubmit) $(g.block).remove(); //$(t).show();
                this.hDiv.scrollLeft = this.bDiv.scrollLeft;
                if ($.browser.opera) $(t).css('visibility', 'visible');

            },
            htmlDecode:function(val){
            	if(val){
            		return val.replace(/'/g,'&apos;').replace(/"/g,'&amp;');
            	}
            },
            changeSort: function(th) { //change sortorder

                if (this.loading) return true;

                $(g.nDiv).hide(); $(g.nBtn).hide();

                if (p.sortname == $(th).attr('abbr')) {
                    if (p.sortorder == 'asc') p.sortorder = 'desc';
                    else p.sortorder = 'asc';
                }

                $(th).addClass('sorted').siblings().removeClass('sorted');
                $('.sdesc', this.hDiv).removeClass('sdesc');
                $('.sasc', this.hDiv).removeClass('sasc');
                $('div', th).addClass('s' + p.sortorder);
                p.sortname = $(th).attr('abbr');

                if (p.onChangeSort)
                    p.onChangeSort(p.sortname, p.sortorder);
                else
                    this.populate();

            },
            getPage: function() {
            	return p.page;
            },
            getPageNum: function() {
            	return p.rp;
            },
            buildpager: function() { //rebuild pager based on new properties

                $('.pcontrol input', this.pDiv).val(p.page);
                $('.pcontrol span', this.pDiv).html(p.pages);

                var r1 = (p.page - 1) * p.rp + 1;
                var r2 = r1 + p.rp - 1;

                if (p.total < r2) r2 = p.total;

                var stat = p.pagestat;

                stat = stat.replace(/{from}/, r1);
                stat = stat.replace(/{to}/, r2);
                stat = stat.replace(/{total}/, p.total);
                $('.pPageStat', this.pDiv).html(stat);
            },
            populate: function() { //get latest data 
                //log.trace("开始访问数据源");
            	if (this.loading) return true;
            	
                if (p.onSubmit) {
                    var gh = p.onSubmit();
                    if (!gh){
                    	//设置loading为false解决表格不查询问题
                    	this.loading = false;
                    	return false;
                    }
                }
                this.loading = true;
                if (!p.url){
                	//设置loading为false解决表格不查询问题
                	this.loading = false;
                	return false;
                }
                
                //只去掉当前表格的勾选框
            	$(g.hDiv).find(".noborder").attr("checked", false);
            	
            	//增加判断不分页时不修改分页栏
            	if (p.usepager){
            		$('.pPageStat', this.pDiv).html(p.procmsg);
            		$('.pReload', this.pDiv).addClass('loading');
            		$(g.block).css({ top: g.bDiv.offsetTop });
                    if (p.hideOnSubmit) $(this.gDiv).prepend(g.block); //$(t).hide();
                    if ($.browser.opera) $(t).css('visibility', 'hidden');
                    if (!p.newp) p.newp = 1;
                    if (p.page > p.pages) p.page = p.pages;
            	}
                
                //var param = {page:p.newp, rp: p.rp, sortname: p.sortname, sortorder: p.sortorder, query: p.query, qtype: p.qtype};
                var param = [
					 { name: 'page', value: p.newp }
					, { name: 'rp', value: p.rp }
					, { name: 'sortname', value: p.sortname }
					, { name: 'sortorder', value: p.sortorder }
					, { name: 'query', value: p.query }
					, { name: 'qtype', value: p.qtype }
					, { name: 'qop', value: p.qop }
				];
                //param = jQuery.extend(param, p.extParam);
                if (p.extParam) {
                    for (var pi = 0; pi < p.extParam.length; pi++) param[param.length] = p.extParam[pi];
                }

                if(p.extParam && Object.prototype.toString.call(p.extParam) === '[object Object]') {
                	for(key in p.extParam) {
                		param.push({name: key, value: p.extParam[key]});
                	}
                }

                $.ajax({
                    type: p.method,
                    url: p.url,
                    data: param,
                    dataType: p.dataType,
                    //修改success方法为平台返回异常的处理方式去处理
                    success: function(data) {if (data != null && data.success == false ) {  g.hideLoading(); message({data:data}); } else {g.addData(data); } },
                    error: function(data) { try { if (p.onError) { p.onError(data); } else { message({html: i18n_flexigrid_queryError,showConfirm: true}); } g.hideLoading(); } catch (e) { } },
                    complete: function(response, status) {
                    	//sessionTimeout(eval('('+response.responseText+')'));
                    }
                });
            },
            doSearch: function() {
                var queryType = $('select[name=qtype]', g.sDiv).val();
                var qArrType = queryType.split("$");
                var index = -1;
                if (qArrType.length != 3) {
                    p.qop = "Eq";
                    p.qtype = queryType;
                }
                else {
                    p.qop = qArrType[1];
                    p.qtype = qArrType[0];
                    index = parseInt(qArrType[2]);
                }
                p.query = $('input[name=q]', g.sDiv).val();
                //添加验证代码
                if (p.query != "" && p.searchitems && index >= 0 && p.searchitems.length > index) {
                    if (p.searchitems[index].reg) {
                        if (!p.searchitems[index].reg.test(p.query)) {
                            alert("你的输入不符合要求!");
                            return;
                        }
                    }
                }
                p.newp = 1;
                this.populate();
            },
            changePage: function(ctype) { //change page
            	if (this.loading) return true;
            	switch (ctype) {
                    case 'first': p.newp = 1; break;
                    case 'prev': if (p.page > 1) p.newp = parseInt(p.page) - 1; break;
                    case 'next': if (p.page < p.pages) p.newp = parseInt(p.page) + 1; break;
                    case 'last': p.newp = p.pages; break;
                    case 'input':
                        var nv = parseInt($('.pcontrol input', this.pDiv).val());
                        if (isNaN(nv)) nv = 1;
                        if (nv < 1) nv = 1;
                        else if (nv > p.pages) nv = p.pages;
                        $('.pcontrol input', this.pDiv).val(nv);
                        p.newp = nv;
                        break;
                }

                if (p.newp == p.page) return false;
                	//只去掉当前表格的勾选框
            		$(g.hDiv).find(".noborder").attr("checked", false);
                if (p.onChangePage)
                    p.onChangePage(p.newp);
                else
                    this.populate();

            },
            cellProp: function(n, ptr, pth) {
                var tdDiv = document.createElement('div');
                if (pth != null) {
                    if (p.sortname == $(pth).attr('abbr') && p.sortname) {
                        this.className = 'sorted';
                    }
                    $(tdDiv).css({ textAlign: pth.align, width: $('div:first', pth)[0].style.width });
                    if (pth.hide) $(this).css('display', 'none');
                }
                if (p.nowrap == false) $(tdDiv).css({'white-space':'normal','height':'auto'});

                if (this.innerHTML == '') this.innerHTML = '&nbsp;';

                //tdDiv.value = this.innerHTML; //store preprocess value
                tdDiv.innerHTML = this.innerHTML;

                var prnt = $(this).parent()[0];
                var pid = false;
                if (prnt.id) pid = prnt.id.substr(3);
                if (pth != null) {
                    if (pth.process)
                    { pth.process(tdDiv, pid); }
                }
                $("input.itemchk", tdDiv).each(function() {
                    $(this).click(function() {
                        if (this.checked) {
                        	//判断如果是多选框
                        	if(p.showcheckbox){
                        		if (this.checked) {
                        			$(ptr).addClass("trSelected");
                        		}else{
                        			$(ptr).removeClass("trSelected");	
                        		}
                        	}
                        	//如果是单选，将所有的行清掉样式后标记自己为选中
                        	else if(p.showradio){
                        		$(ptr).parent().children("tr").removeClass("trSelected");
                        		$(ptr).addClass("trSelected");
                        	}
                            
                        }
                        else {
                            $(ptr).removeClass("trSelected");
                        }
                        if (p.onrowchecked) {
                            p.onrowchecked.call(this);
                        }
                    });
                });
                $(this).empty().append(tdDiv).removeAttr('width'); //wrap content
                //add editable event here 'dblclick',如果需要可编辑在这里添加可编辑代码 
            },
            addCellProp: function() {
                var $gF = this.cellProp;

                $('tbody tr td', g.bDiv).each
					(
						function() {
						    var n = $('td', $(this).parent()).index(this);
						    var pth = $('th:eq(' + n + ')', g.hDiv).get(0);
						    var ptr = $(this).parent();
						    $gF.call(this, n, ptr, pth);
						}
					);
                $gF = null;
            },
            getCheckedRows: function() {
                var ids = [];
                $(":checkbox:checked", g.bDiv).each(function() {
                    ids.push($(this).val());
                });
                return ids;
            },
            getCellDim: function(obj) // get cell prop for editable event
            {
                var ht = parseInt($(obj).height());
                var pht = parseInt($(obj).parent().height());
                var wt = parseInt(obj.style.width);
                var pwt = parseInt($(obj).parent().width());
                var top = obj.offsetParent.offsetTop;
                var left = obj.offsetParent.offsetLeft;
                var pdl = parseInt($(obj).css('paddingLeft'));
                var pdt = parseInt($(obj).css('paddingTop'));
                return { ht: ht, wt: wt, top: top, left: left, pdl: pdl, pdt: pdt, pht: pht, pwt: pwt };
            },
            rowProp: function() {
                if (p.rowhandler) {
                    p.rowhandler(this);
                }
                if ($.browser.msie && $.browser.version < 7.0) {
                    $(this).hover(function() { $(this).addClass('trOver'); }, function() { $(this).removeClass('trOver'); });
                }
            },
            addRowProp: function() {
                var $gF = this.rowProp;
                $('tbody tr', g.bDiv).each(
                    function() {
                        $("input.itemchk", this).each(function() {
                            var ptr = $(this).parent().parent().parent();
                            $(this).click(function() {
                            	
                            	//判断如果是多选框
                            	if(p.showcheckbox){
                            		if (this.checked) {
                            			$(ptr).addClass("trSelected");
                            		}else{
                            			$(ptr).removeClass("trSelected");	
                            		}
                            	}
                            	//如果是单选，将所有的行清掉样式后标记自己为选中
                            	else if(p.showradio){
                            		$(ptr).parent().children("tr").removeClass("trSelected");
                            		$(ptr).addClass("trSelected");
                            	}
                                
                                //增加判断是否所有checkbox都点击的，如果是，全选自动勾选
                    	    	if(this.checked)
                    	    	{
                    	    		var ptable = $(this).parent().parent().parent().parent().parent();
                    	    		var checkLength = $(ptable).find('.chboxtd input:checked:not([disabled])').length;
                            		if($(ptable).find(".chboxtd input[type='checkbox']:not([disabled])").length == checkLength)
                            		{
                            		   $(g.hDiv).find(".cth input[type='checkbox']").attr("checked",true);
                            		}
                    	    	}
                    	    	else
                    	   		{
                    	    		$(g.hDiv).find(".cth input[type='checkbox']").attr("checked",false); 
                    	      	}
                    	    	
                                if (p.onrowchecked) {
                                    p.onrowchecked.call(this);
                                }
                            });
                        });
                        $gF.call(this);
                    }
                );
                $gF = null;
            },
            checkAllOrNot: function(parent) {
                var ischeck = $(this).attr("checked");
                $('tbody tr', g.bDiv).each(function() {
                    if (ischeck) {
                        $(this).addClass("trSelected");
                    }
                    else {
                        $(this).removeClass("trSelected");
                    }
                });
                $("input.itemchk", g.bDiv).each(function() {
                    this.checked = ischeck;
                    //Raise Event
                    if (p.onrowchecked) {
                        p.onrowchecked.call(this);
                    }
                });
            },
            pager: 0
        };

        //create model if any
        if (p.colModel) {
            thead = document.createElement('thead');
            tr = document.createElement('tr');
            //p.showcheckbox ==true;
            //如果是多选表格
            if (p.showcheckbox) {
                var cth = jQuery('<th/>');
                var cthch = jQuery('<input style="margin-top:2px;" type="checkbox" title="'+p.numCheckBoxTitle+'"/>');
                cthch.addClass("noborder")
                cth.addClass("cth").attr({ 'axis': "col-1", width: "40",align: "center", "isch": true }).append(cthch);
                $(tr).append(cth);
            }
            //判断如果是显示单选表格，不添加全选勾选框
            else if(p.showradio){
            	var cth = jQuery('<th/>');
                cth.addClass("cth").attr({ 'axis': "col-1", width: "40",align: "center", "isch": true });
                $(tr).append(cth);
            }
            for (i = 0; i < p.colModel.length; i++) {
                var cm = p.colModel[i];
                var th = document.createElement('th');

                th.innerHTML = cm.display;
                //添加单位
                if(cm.unit){
                	th.innerHTML += cm.unit;
                }

                if (cm.name && cm.sortable)
                    $(th).attr('abbr', cm.name);
                
                //记录列中的类型和操作信息
                if(cm.optType){
                	$(th).attr('optType', cm.optType);
                }
                if(cm.optDetail){
                	$(th).attr('optDetail', cm.optDetail);
                }
                //增加列是否用于隐藏，可选择显示
                if(cm.hideForSelect){
                	$(th).attr('hideForSelect', cm.hideForSelect);
                }
                //th.idx = i;
                $(th).attr('axis', 'col' + i);

                if (cm.align){
                    th.align = cm.align;
                }else{
                	th.align = 'center';
                }
                if (cm.width)
                    $(th).attr('width', cm.width);

                if (cm.hide) {
                    th.hide = true;
                }
                //增加判断如果是用于列选择的隐藏，先隐藏
                if(cm.hideForSelect){
                	th.hideForSelect = true;
                }
                if (cm.toggle != undefined) {
                    th.toggle = cm.toggle
                }
                if (cm.process) {
                    th.process = cm.process;
                }

                $(tr).append(th);
            }
            $(thead).append(tr);
            $(t).prepend(thead);
        } // end if p.colmodel	

        //init divs
        g.gDiv = document.createElement('div'); //create global container
        g.mDiv = document.createElement('div'); //create title container
        g.hDiv = document.createElement('div'); //create header container
        g.bDiv = document.createElement('div'); //create body container
        g.vDiv = document.createElement('div'); //create grip
        g.rDiv = document.createElement('div'); //create horizontal resizer
        g.cDrag = document.createElement('div'); //create column drag
        g.block = document.createElement('div'); //creat blocker
        g.nDiv = document.createElement('div'); //create column show/hide popup
        g.nBtn = document.createElement('div'); //create column show/hide button
        g.iDiv = document.createElement('div'); //create editable layer
        g.tDiv = document.createElement('div'); //create toolbar
        g.sDiv = document.createElement('div');

        if (p.usepager) g.pDiv = document.createElement('div'); //create pager container
        g.hTable = document.createElement('table');

        //set gDiv
        g.gDiv.className = p.gridClass;
        if (p.width != 'auto') g.gDiv.style.width = p.width + 'px';

        //add conditional classes
        if ($.browser.msie)
            $(g.gDiv).addClass('ie');

        if (p.novstripe)
            $(g.gDiv).addClass('novstripe');

        $(t).before(g.gDiv);
        $(g.gDiv)
		.append(t)
		;

        //set toolbar
        if (p.buttons) {
            g.tDiv.className = 'tDiv';
            var tDiv2 = document.createElement('div');
            tDiv2.className = 'tDiv2';

            for (i = 0; i < p.buttons.length; i++) {
                var btn = p.buttons[i];
                if (!btn.separator) {
                    var btnDiv = document.createElement('div');
                    btnDiv.className = 'fbutton';
                    btnDiv.innerHTML = "<div><span>" + btn.displayname + "</span></div>";
                    if (btn.title) {
                        btnDiv.title = btn.title;
                    }
                    if (btn.bclass)
                        $('span', btnDiv)
							.addClass(btn.bclass);
                    btnDiv.onpress = btn.onpress;
                    btnDiv.name = btn.name;
                    if (btn.onpress) {
                        $(btnDiv).click
							(
								function() {
								    this.onpress(this.name, g.gDiv);
								}
							);
                    }
                    $(tDiv2).append(btnDiv);
                    if ($.browser.msie && $.browser.version < 7.0) {
                        $(btnDiv).hover(function() { $(this).addClass('fbOver'); }, function() { $(this).removeClass('fbOver'); });
                    }

                } else {
                    $(tDiv2).append("<div class='btnseparator'></div>");
                }
            }
            $(g.tDiv).append(tDiv2);
            $(g.tDiv).append("<div style='clear:both'></div>");
            $(g.gDiv).prepend(g.tDiv);
        }

        //set hDiv
        g.hDiv.className = 'hDiv';

        $(t).before(g.hDiv);

        //set hTable
        g.hTable.cellPadding = 0;
        g.hTable.cellSpacing = 0;
        //g.hTable.style.borderBottom = '1px solid #ddd';
        $(g.hDiv).append('<div class="hDivBox"></div>');
        $('div', g.hDiv).append(g.hTable);
        var thead = $("thead:first", t).get(0);
        if (thead) $(g.hTable).append(thead);
        thead = null;

        if (!p.colmodel) var ci = 0;

        //setup thead			
        $('thead tr:first th', g.hDiv).each
			(
			 	function() {
			 	    var thdiv = document.createElement('div');
			 	    if ($(this).attr('abbr')) {
			 	        $(this).click(
								function(e) {
								    if (!$(this).hasClass('thOver')) return false;
								    var obj = (e.target || e.srcElement);
								    if (obj.href || obj.type) return true;
								    g.changeSort(this);
								}
							);

			 	        if ($(this).attr('abbr') == p.sortname) {
			 	            this.className = 'sorted';
			 	            thdiv.className = 's' + p.sortorder;
			 	        }
			 	    }
			 	    
			 	    //增加判断用于列选择的隐藏列初始化隐藏
			 	    if (this.hide || this.hideForSelect) $(this).hide();

			 	    if (!p.colmodel && !$(this).attr("isch")) {
			 	        $(this).attr('axis', 'col' + ci++);
			 	    }


			 	    $(thdiv).css({ textAlign: this.align, width: this.width + 'px' });
			 	    thdiv.innerHTML = this.innerHTML;

			 	    $(this).empty().append(thdiv).removeAttr('width');
			 	    //p.isDragCol增加判断拖拽表头排序
			 	    if (!$(this).attr("isch") && p.isDragCol) {
			 	        $(this).mousedown(function(e) {
			 	            g.dragStart('colMove', e, this);
			 	        })
						.hover(
							function() {

							    if (!g.colresize && !$(this).hasClass('thMove') && !g.colCopy) $(this).addClass('thOver');

							    if ($(this).attr('abbr') != p.sortname && !g.colCopy && !g.colresize && $(this).attr('abbr')) $('div', this).addClass('s' + p.sortorder);
							    else if ($(this).attr('abbr') == p.sortname && !g.colCopy && !g.colresize && $(this).attr('abbr')) {
							        var no = '';
							        if (p.sortorder == 'asc') no = 'desc';
							        else no = 'asc';
							        $('div', this).removeClass('s' + p.sortorder).addClass('s' + no);
							    }

							    if (g.colCopy) {

							        var n = $('th', g.hDiv).index(this);

							        if (n == g.dcoln) return false;



							        if (n < g.dcoln) $(this).append(g.cdropleft);
							        else $(this).append(g.cdropright);

							        g.dcolt = n;

							    } else if (!g.colresize) {
							        var thsa = $('th:visible', g.hDiv);
							        var nv = -1;
							        for (var i = 0, j = 0, l = thsa.length; i < l; i++) {
							            if ($(thsa[i]).css("display") != "none") {
							                if (thsa[i] == this) {
							                    nv = j;
							                    break;
							                }
							                j++;
							            }
							        }
							        // var nv = $('th:visible', g.hDiv).index(this);
							        var onl = parseInt($('div:eq(' + nv + ')', g.cDrag).css('left'));
							        var nw = parseInt($(g.nBtn).width()) + parseInt($(g.nBtn).css('borderLeftWidth'));
							        nl = onl - nw + Math.floor(p.cgwidth / 2);

							        $(g.nDiv).hide(); $(g.nBtn).hide();

							        //增加列选择控制
							        if(p.isColSel)
							        {	//解决chrome中隐藏列按钮太长问题
							        	$(g.nBtn).css({ 'height': $(g.hDiv).height()});
							        	$('div',g.nBtn).css({ 'height': $(g.hDiv).height()});
							        	//end
							        	$(g.nBtn).css({ 'left': nl, top: g.hDiv.offsetTop }).show();
							        }

							        var ndw = parseInt($(g.nDiv).width());

							        $(g.nDiv).css({ top: g.bDiv.offsetTop });

							        if ((nl + ndw) > $(g.gDiv).width())
							            $(g.nDiv).css('left', onl - ndw + 1);
							        else
							            $(g.nDiv).css('left', nl);


							        if ($(this).hasClass('sorted'))
							            $(g.nBtn).addClass('srtd');
							        else
							            $(g.nBtn).removeClass('srtd');

							    }

							},
							function() {
							    $(this).removeClass('thOver');
							    if ($(this).attr('abbr') != p.sortname) $('div', this).removeClass('s' + p.sortorder);
							    //增加判断 typeof(p.sortname) != 'undefined'，解决没有排序的列鼠标放上面会出箭头的问题
							    else if ($(this).attr('abbr') == p.sortname && typeof(p.sortname) != 'undefined') {
							        var no = '';
							        if (p.sortorder == 'asc') no = 'desc';
							        else no = 'asc';

							        $('div', this).addClass('s' + p.sortorder).removeClass('s' + no);
							    }
							    if (g.colCopy) {
							        $(g.cdropleft).remove();
							        $(g.cdropright).remove();
							        g.dcolt = null;
							    }
							})
						; //wrap content
			 	    }
			 	}
			);

        //set bDiv
        g.bDiv.className = 'bDiv';
        $(t).before(g.bDiv);
        $(g.bDiv)
		.css({ height: (p.height == 'auto') ? 'auto' : p.height + "px" })
		.scroll(function(e) { g.scroll() })
		.append(t)
		;

        if (p.height == 'auto') {
            $('table', g.bDiv).addClass('autoht');
        }

        //add td properties
        if (p.url == false || p.url == "") {
            g.addCellProp();
            //add row properties
            g.addRowProp();
        }

        //set cDrag

        var cdcol = $('thead tr:first th:first', g.hDiv).get(0);

        if (cdcol != null) {
            g.cDrag.className = 'cDrag';
            g.cdpad = 0;

            g.cdpad += (isNaN(parseInt($('div', cdcol).css('borderLeftWidth'))) ? 0 : parseInt($('div', cdcol).css('borderLeftWidth')));
            g.cdpad += (isNaN(parseInt($('div', cdcol).css('borderRightWidth'))) ? 0 : parseInt($('div', cdcol).css('borderRightWidth')));
            g.cdpad += (isNaN(parseInt($('div', cdcol).css('paddingLeft'))) ? 0 : parseInt($('div', cdcol).css('paddingLeft')));
            g.cdpad += (isNaN(parseInt($('div', cdcol).css('paddingRight'))) ? 0 : parseInt($('div', cdcol).css('paddingRight')));
            g.cdpad += (isNaN(parseInt($(cdcol).css('borderLeftWidth'))) ? 0 : parseInt($(cdcol).css('borderLeftWidth')));
            g.cdpad += (isNaN(parseInt($(cdcol).css('borderRightWidth'))) ? 0 : parseInt($(cdcol).css('borderRightWidth')));
            g.cdpad += (isNaN(parseInt($(cdcol).css('paddingLeft'))) ? 0 : parseInt($(cdcol).css('paddingLeft')));
            g.cdpad += (isNaN(parseInt($(cdcol).css('paddingRight'))) ? 0 : parseInt($(cdcol).css('paddingRight')));

            $(g.bDiv).before(g.cDrag);

            var cdheight = $(g.bDiv).height();
            var hdheight = $(g.hDiv).height();

            $(g.cDrag).css({ top: -hdheight + 'px' });
            //p.isDragCol增加判断拖拽表头排序
            if(p.isDragCol){
            	$('thead tr:first th', g.hDiv).each
            	(
            			function() {
            				var cgDiv = document.createElement('div');
            				$(g.cDrag).append(cgDiv);
            				if (!p.cgwidth) p.cgwidth = $(cgDiv).width();
            				$(cgDiv).css({ height: cdheight + hdheight })
            				.mousedown(function(e) { g.dragStart('colresize', e, this); })
            				;
            				if ($.browser.msie && $.browser.version < 7.0) {
            					g.fixHeight($(g.gDiv).height());
            					$(cgDiv).hover(
            							function() {
            								g.fixHeight();
            								$(this).addClass('dragging')
            							},
            							function() { if (!g.colresize) $(this).removeClass('dragging') }
            					);
            				}
            			}
            	);
            }

            //g.rePosDrag();

        }


        //add strip		
        if (p.striped)
            $('tbody tr:odd', g.bDiv).addClass('erow');


        if (p.resizable && p.height != 'auto') {
            g.vDiv.className = 'vGrip';
            $(g.vDiv)
		.mousedown(function(e) { g.dragStart('vresize', e) })
		.html('<span></span>');
            $(g.bDiv).after(g.vDiv);
        }

        if (p.resizable && p.width != 'auto' && !p.nohresize) {
            g.rDiv.className = 'hGrip';
            $(g.rDiv)
		.mousedown(function(e) { g.dragStart('vresize', e, true); })
		.html('<span></span>')
		.css('height', $(g.gDiv).height())
		;
            if ($.browser.msie && $.browser.version < 7.0) {
                $(g.rDiv).hover(function() { $(this).addClass('hgOver'); }, function() { $(this).removeClass('hgOver'); });
            }
            $(g.gDiv).append(g.rDiv);
        }

        // add pager
        if (p.usepager) {
            g.pDiv.className = 'pDiv';
            g.pDiv.innerHTML = '<div class="pDiv2"></div>';
            $(g.bDiv).after(g.pDiv);
            var html = '<div class="pGroup"><div class="pFirst pButton" title="'+i18n_flexigrid_toFirstPage+'"><span></span></div><div class="pPrev pButton" title="'+i18n_flexigrid_toPreviousPage+'"><span></span></div> </div><div class="btnseparator"></div> <div class="pGroup"><span class="pcontrol">'+i18n_flexigrid_currentPage+'<input class="ui-corner-all" style="border:1px solid #d4d0c8;width:30px;margin-top:4px;background: #fff; height:16px; line-height:16px;" size="1" value="1" />&nbsp;'+i18n_flexigrid_totalPage+'<span> 1 </span></span></div><div class="btnseparator"></div><div class="pGroup"> <div class="pNext pButton" title="'+i18n_flexigrid_toNextPage+'"><span></span></div><div class="pLast pButton" title="'+i18n_flexigrid_toLastPage+'"><span></span></div></div><div class="btnseparator"></div><div class="pGroup"> <div class="pReload pButton" title="'+i18n_flexigrid_refresh+'"><span></span></div> </div> <div class="btnseparator"></div><div class="pGroup"> <div class="pReloadGo pButton" ><span></span></div> </div> <div class="btnseparator"></div><div class="pGroup"><span class="pPageStat"></span></div>';
            $('div', g.pDiv).html(html);

 			 //此功能添加go图标进行点击操作
            $('.pReloadGo', g.pDiv).click(function() { g.changePage('input');});
            //此功能添加go图标进行点击操作end
            $('.pReload', g.pDiv).click(function() { g.populate();});
            $('.pFirst', g.pDiv).click(function() { g.changePage('first');});
            $('.pPrev', g.pDiv).click(function() { g.changePage('prev'); });
            $('.pNext', g.pDiv).click(function() { g.changePage('next'); });
            $('.pLast', g.pDiv).click(function() { g.changePage('last');});
            $('.pcontrol input', g.pDiv).keydown(function(e) { if (e.keyCode == 13) g.changePage('input') });
            if ($.browser.msie && $.browser.version < 7) $('.pButton', g.pDiv).hover(function() { $(this).addClass('pBtnOver'); }, function() { $(this).removeClass('pBtnOver'); });

            if (p.useRp) {
                var opt = "";
                for (var nx = 0; nx < p.rpOptions.length; nx++) {
                    if (p.rp == p.rpOptions[nx]) sel = 'selected="selected"'; else sel = '';
                    opt += "<option value='" + p.rpOptions[nx] + "' " + sel + " >" + p.rpOptions[nx] + "&nbsp;&nbsp;</option>";
                };
                $('.pDiv2', g.pDiv).prepend("<div class='pGroup'>"+i18n_flexigrid_everyPageCount+"<select name='rp' class='ui-corner-all' style='margin-top:3px;'>" + opt + "</select></div> <div class='btnseparator'></div>");
                $('select', g.pDiv).change(
					function() {
					    if (p.onRpChange)
					        p.onRpChange(+this.value);
					    else {
					        p.newp = 1;
					        p.rp = +this.value;
					        g.populate();
					    }
					}
				);
            }

            //add search button
            if (p.searchitems) {
                $('.pDiv2', g.pDiv).prepend("<div class='pGroup'> <div class='pSearch pButton'><span></span></div> </div>  <div class='btnseparator'></div>");
                $('.pSearch', g.pDiv).click(function() { $(g.sDiv).slideToggle('fast', function() { $('.sDiv:visible input:first', g.gDiv).trigger('focus'); }); });
                //add search box
                g.sDiv.className = 'sDiv';

                sitems = p.searchitems;

                var sopt = "";
                var op = "Eq";
                for (var s = 0; s < sitems.length; s++) {
                    if (p.qtype == '' && sitems[s].isdefault == true) {
                        p.qtype = sitems[s].name;
                        sel = 'selected="selected"';
                    } else sel = '';
                    if (sitems[s].operater == "Like") {
                        op = "Like";
                    }
                    else {
                        op = "Eq";
                    }
                    sopt += "<option value='" + sitems[s].name + "$" + op + "$" + s + "' " + sel + " >" + sitems[s].display + "&nbsp;&nbsp;</option>";
                }

                if (p.qtype == '') p.qtype = sitems[0].name;

                $(g.sDiv).append("<div class='sDiv2'>"+i18n_flexigrid_quickSearch+"：<input type='text' size='30' name='q' class='qsbox' /> <select name='qtype'>" + sopt + "</select> <input type='button' name='qclearbtn' value='清空' />&nbsp;&nbsp;<input type='button' name='qsbtn' value='查询' /></div>");
                //添加点击快速检索的查询按钮
                $('input[name=qsbtn]', g.sDiv).click(function() { g.doSearch() });
                //快速检索文本框
                $('input[name=q],select[name=qtype]', g.sDiv).keydown(function(e) { if (e.keyCode == 13) g.doSearch() });
                $('input[name=qclearbtn]', g.sDiv).click(function() { $('input[name=q]', g.sDiv).val(''); p.query = ''; g.doSearch(); });
                $(g.bDiv).after(g.sDiv);

            }

        }
        $(g.pDiv, g.sDiv).append("<div style='clear:both'></div>");

        // add title
        if (p.title) {
            g.mDiv.className = 'mDiv';
            g.mDiv.innerHTML = '<div class="ftitle">' + p.title + '</div>';
            $(g.gDiv).prepend(g.mDiv);
            if (p.showTableToggleBtn) {
                $(g.mDiv).append('<div class="ptogtitle" title="Minimize/Maximize Table"><span></span></div>');
                $('div.ptogtitle', g.mDiv).click
					(
					 	function() {
					 	    $(g.gDiv).toggleClass('hideBody');
					 	    $(this).toggleClass('vsble');
					 	}
					);
            }
            //g.rePosDrag();
        }

        //setup cdrops
        g.cdropleft = document.createElement('span');
        g.cdropleft.className = 'cdropleft';
        g.cdropright = document.createElement('span');
        g.cdropright.className = 'cdropright';

        //add block
        g.block.className = 'gBlock';
        var blockloading = $("<div/>");
        blockloading.addClass("loading");
        $(g.block).append(blockloading);
        var gh = $(g.bDiv).height();
        var gtop = g.bDiv.offsetTop;
        $(g.block).css(
		{
		    width: g.bDiv.style.width,
		    height: gh,
		    position: 'relative',
		    marginBottom: (gh * -1),
		    zIndex: 1,
		    top: gtop,
		    left: '0px'
		}
		);
        $(g.block).fadeTo(0, p.blockOpacity);

        // add column control
        if ($('th', g.hDiv).length) {
            g.nDiv.className = 'nDiv';
            g.nDiv.innerHTML = "<table cellpadding='0' cellspacing='0'><tbody></tbody></table>";
            $(g.nDiv).css(
			{
			    marginBottom: (gh * -1),
			    display: 'none',
			    top: gtop
			}
			).noSelect()
			;

            var cn = 0;


            $('th div', g.hDiv).each
			(
			 	function() {
			 	    var kcol = $("th[axis='col" + cn + "']", g.hDiv)[0];
			 	    if (kcol == null) return;
			 	    var chkall = $("input[type='checkbox']", this);
			 	    if (chkall.length > 0) {
			 	        chkall[0].onclick = g.checkAllOrNot;
			 	        return;
			 	    }
			 	    else{//begin 解决单选时隐藏列出问题
			 	    	var showradio = $(this).parent().attr("isch");
			 	    	if(showradio == "true") {
			 	    		return;
			 	    	}
			 	    }//end
			 	    if (kcol.toggle == false || this.innerHTML == "") {
			 	        cn++;
			 	        return;
			 	    }
			 	    var chk = 'checked="checked"';
			 	    if (kcol.style.display == 'none') chk = '';
			 	    
			 	    //增加判断隐藏列不显示列选择
			 	    if(typeof(kcol.hide) != 'undefined' || kcol.hide == true){
			 	    	//隐藏
			 	    	$('tbody', g.nDiv).append('<tr style="display:none"><td class="ndcol1"><input type="checkbox" ' + chk + ' class="togCol noborder" value="' + cn + '" /></td><td class="ndcol2">' + this.innerHTML + '</td></tr>');
			 	    }
			 	    else{
			 	    	//去掉样式 noborder，解决checked不起作用的问题
			 	    	$('tbody', g.nDiv).append('<tr><td class="ndcol1"><input type="checkbox" class="togCol" ' + chk + '  value="' + cn + '" /></td><td class="ndcol2">' + this.innerHTML + '</td></tr>');
			 	    }
			 	    
			 	    cn++;
			 	}
			);

            if ($.browser.msie && $.browser.version < 7.0)
                $('tr', g.nDiv).hover
				(
				 	function() { $(this).addClass('ndcolover'); },
					function() { $(this).removeClass('ndcolover'); }
				);

            $('td.ndcol2', g.nDiv).click
			(
			 	function() {
			 	    if ($('input:checked', g.nDiv).length <= p.minColToggle && $(this).prev().find('input')[0].checked) return false;
			 	    return g.toggleCol($(this).prev().find('input').val());
			 	}
			);

            $('input.togCol', g.nDiv).click
			(
			 	function() {
			 	    if ($('input:checked', g.nDiv).length < p.minColToggle && this.checked == false) return false;
			 	    $(this).parent().next().trigger('click');
			 	    //return false;
			 	}
			);


            $(g.gDiv).prepend(g.nDiv);

            $(g.nBtn).addClass('nBtn')
			.html('<div></div>')
            //.attr('title', 'Hide/Show Columns')
			.click
			(
			 	function() {
			 	    $(g.nDiv).toggle(); return true;
			 	}
			);

            if (p.showToggleBtn)
                $(g.gDiv).prepend(g.nBtn);

        }

        // add date edit layer
        $(g.iDiv)
		.addClass('iDiv')
		.css({ display: 'none' })
		;
        $(g.bDiv).append(g.iDiv);

        // add flexigrid events
        $(g.bDiv)
		.hover(function() { $(g.nDiv).hide(); $(g.nBtn).hide(); }, function() { if (g.multisel) g.multisel = false; })
		;
        $(g.gDiv)
		.hover(function() { }, function() { $(g.nDiv).hide(); $(g.nBtn).hide(); })
		;

        //add document events
        $(document)
		.mousemove(function(e) { g.dragMove(e) })
		.mouseup(function(e) { g.dragEnd() })
		.hover(function() { }, function() { g.dragEnd() })
		;
        window.movefunction = function() {
        	$(document)
    		.mousemove(function(e) { g.dragMove(e) })
    		.mouseup(function(e) { g.dragEnd() })
    		.hover(function() { }, function() { g.dragEnd() })
    		;
        }

        //browser adjustments
        if ($.browser.msie && $.browser.version < 7.0) {
            $('.hDiv,.bDiv,.mDiv,.pDiv,.vGrip,.tDiv, .sDiv', g.gDiv)
			.css({ width: '100%' });
            $(g.gDiv).addClass('ie6');
            if (p.width != 'auto') $(g.gDiv).addClass('ie6fullwidthbug');
        }

        g.rePosDrag();
        g.fixHeight();

        //make grid functions accessible
        t.p = p;
        t.grid = g;

        // load data
        if (p.url && p.autoload) {
            g.populate();
        }

        return t;

    };

    var docloaded = false;

    $(document).ready(function() { docloaded = true });

    $.fn.flexigrid = function(p) {

        return this.each(function() {
            if (!docloaded) {
                $(this).hide();
                var t = this;
                $(document).ready
					(
						function() {
						    $.addFlex(t, p);
						}
					);
            } else {
                $.addFlex(this, p);
            }
        });

    }; //end flexigrid

    $.fn.flexReload = function(p) { // function to reload grid

        return this.each(function() {
            if (this.grid && this.p.url) this.grid.populate();
        });

    }; //end flexReload
    /*$.fn.fun = function(funObj) {
    	eval(funObj);
    };*/
    

    $.fn.getPage = function(p) {
    	var page = 1 ;
        this.each(function() {
            if (this.p && this.p.page) page = this.p.page;
        });
        return page ;
    };
    $.fn.getRp = function(p) {
    	var rp = 10 ;
        this.each(function() {
            if (this.p && this.p.rp) rp = this.p.rp;
        });
        return rp ;
    };
    
    //重新指定宽度和高度
    $.fn.flexResize = function(w, h) {
    	if(typeof(w) === "number"){
    		w = (w + 'px');
    	}
    	if(typeof(h) === "number"){
    		h = (h + 'px');
    	}
        var p = { width: w, height: h };
        return this.each(function() {
            if (this.grid) {
                $.extend(this.p, p);
                this.grid.reSize();
            }
        });
    }
    $.fn.ChangePage = function(type) {
        return this.each(function() {
            if (this.grid) {
                this.grid.changePage(type);
            }
        })
    }
    $.fn.flexOptions = function(p) { //function to update general options

        return this.each(function() {
            if (this.grid) $.extend(this.p, p);
        });

    }; //end flexOptions
    $.fn.GetOptions = function() {
        if (this[0].grid) {
            return this[0].p;
        }
        return null;
    }
    $.fn.getCheckedRows = function() {
        if (this[0].grid) {
            return this[0].grid.getCheckedRows();
        }
        return [];
    }
    $.fn.flexToggleCol = function(cid, visible) { // function to reload grid

        return this.each(function() {
            if (this.grid) this.grid.toggleCol(cid, visible);
        });

    }; //end flexToggleCol
    $.fn.flexAddData = function(data) { // function to add data to grid

        return this.each(function() {
            if (this.grid) this.grid.addData(data);
        });
    };
    $.fn.noSelect = function(p) { //no select plugin by me :-)
        if (p == null)
            prevent = true;
        else
            prevent = p;
        if (prevent) {
            return this.each(function() {
                if ($.browser.msie || $.browser.safari) $(this).bind('selectstart', function() { return false; });
                else if ($.browser.mozilla) {
                    $(this).css('MozUserSelect', 'none');
                    $('body').trigger('focus');
                }
                else if ($.browser.opera) $(this).bind('mousedown', function() { return false; });
                else $(this).attr('unselectable', 'on');
            });
        } else {
            return this.each(function() {
                if ($.browser.msie || $.browser.safari) $(this).unbind('selectstart');
                else if ($.browser.mozilla) $(this).css('MozUserSelect', 'inherit');
                else if ($.browser.opera) $(this).unbind('mousedown');
                else $(this).removeAttr('unselectable', 'on');
            });
        }
    }; //end noSelect
    //向表格追加数据
    $.fn.appendData = function(data){
    	return this.each(function() {
	            if (this.grid){
	            	var gridData = {};
	            	//设置total
	            	if(this.p.total != null && this.p.total !=0){
	            		gridData.total = this.p.total;
	            	}else{
	            		gridData.total = data.length;
	            	};
	            	//设置page
	            	if(this.p.page != null && this.p.page != 0){
	            		gridData.page = this.p.page;
	            	}else{
	            		gridData.page = 1;
	            	}
	            	//将data封装成表格能识别的数据
	            	gridData.rawRecords = [];
	            	gridData.rows = [];
	            	var _colModel = this.p.colModel;
	            	$.each(data, function(i, cell){
	            		if(cell){
	            			var row = {};
	            			row.id = gridData.total + i
	            			row.cell = cell;
	            			gridData.rows.push(row);
	            			var record = {};
	            			$.each(_colModel, function(_i, _model){
	            				record[_model.name.split('.')[0]] = cell[_model.name.split('.')[0]];
	            			});
	            			gridData.rawRecords.push(record);
	            		}
	            	});
	            	this.p.appendData = true;
	            	
	            	this.grid.addData(gridData);
	            }
        });
    };
    //添加json数据
    $.fn.appendJsonData = function(data){
    	return this.each(function() {
	            if (this.grid && data.length>=0){
	            	var gridData = {};
	            	//设置total
	            	gridData.total = data.length;
	            	//设置page
	            	if(this.p.page != null && this.p.page != 0){
	            		gridData.page = this.p.page;
	            	}else{
	            		gridData.page = 1;
	            	}
	            	//将data封装成表格能识别的数据
	            	gridData.rawRecords = [];
	            	gridData.rows = [];
	            	var _colModel = this.p.colModel;
	            	$.each(data, function(i, cell){
	            		if(cell){
	            			var row = {};
	            			var _cell = [];
	            			row.id = gridData.total + i;
	            			$.each(_colModel, function(_i, _model){
	            				_cell.push(cell[_model.name]);
	            			});
	            			row.cell = _cell ;
	            			gridData.rows.push(row);
	            			
	            			var record = {};
	            			$.each(_colModel, function(_i, _model){
	            				record[_model.name.split('.')[0]] = cell[_model.name.split('.')[0]];
	            			});
	            			gridData.rawRecords.push(record);
	            		}
	            	});
	            	this.p.appendData = false;
	            	this.grid.addData(gridData);
	            }
        });
    };
    /**
     * 改变表头标题，可以传一个数组(数组的长度要与之前定义的一样)
     * 或是下标和值 e.g(1:datas, 2:index data)
     * 
     */
    $.fn.changeHeader = function(index, data){
    	return this.each(function() {
    		if (this.grid){
    			var g = this.grid;
    			//重新设置表头文字 begin
            	var _ths = $('thead tr:first th', g.hDiv);
            	var _thsLen = _ths.length;
            	//表头下拉框
                var _nDivTds = $('tbody tr td:odd', g.nDiv);
            	$(_ths).each(function(_j) {
            		var _idx = $(this).attr('axis').substr(3);
            		if(_idx == "-1"){//等于-1是前面的check box
            			_thsLen--;
            		}else{
            			//记录下拉框
            			var _nDivTd = null;
            			if(_nDivTds && _nDivTds.length == _thsLen){
            				_nDivTd = _nDivTds[_idx];
            			}
            			//只有数组参数
            			if($.isArray(index)){
            				var titles = index;
            				if(titles != null && titles[_idx] != null){
                				var _title = titles[_idx].display;
                				var _width = titles[_idx].width;
                				$(this).find('div').text(_title);
                				$(this).find('div').css("width",_width);
                				if(_nDivTd){
                					$(_nDivTd).text(_title);
                					$(this).find('div').css("width",_width);
                    			}
                			}
            			}
            			//指定下标和单位
            			else if(index == _idx){
            				var _title = data;
            				$(this).find('div').text(_title);
            				if(_nDivTd){
            					$(_nDivTd).text(_title);
                			}
            				return false;
            			}
            			
            		}
            	});
            	//重新设置表头文字 end
    		}
    	});
    };
    
})(jQuery);