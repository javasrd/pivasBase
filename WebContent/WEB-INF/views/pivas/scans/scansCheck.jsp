<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/assets/sysJs/common/main_min.js" type="text/javascript"></script>


<head>
	
	<style type="text/css">
	       ul{list-style: none outside none;}  
	       .deptDiv{float:right;height:30px;background:#6F96E5;-webkit-border-radius:20px;margin-right: 10px;}
	       .deptNum{margin-left: 20px;margin-right: 20px;color:#fff;}
           .abc{
              height:20px;line-height:30px;margin-top: 20px;/** 0115bianxw修改 width:220px;*/
            }
           .abc li{cursor:pointer;margin-left: 20px;margin-bottom: 10px;}
           .abc li:hover{background-color:#B7CAF2;color:#fff;}  
           
           .scansContents{float:left;background-color: #ededed;margin-top: 15px;width:100%;margin-bottom: 15px;}
           
           .scansContents div.row{float:left;margin-top: 20px;width:100%;margin-top: 5px;margin-bottom: 5px;}
           .scansContents div.row div.column{float:left; width: 50%;}
           .scansContents div.row div.column div.titl {
float:left; font-size:12px; color:#333; 
width: 26%; height: 25px; line-height: 25px; text-align: right;
 margin:0px 8px;
}
           .scansContents div.row div.column div.contents {float:left; width: 50%;}

.oe_searchview_search1{
    font-size: 1px;
    letter-spacing: 4px;
    color: transparent;
    text-shadow: none;
    font-weight: normal;
    -moz-box-shadow: none;
    -webkit-box-shadow: none;
    box-shadow: none;
    -moz-border-radius: 0;
    -webkit-border-radius: 0;
    border-radius: 0;
    border: none;
    background: transparent;
}
.oe_searchview_search1:before{
    font: 21px "mnmliconsRegular";
    content: ".";
    color: white;
    background: url('${pageContext.request.contextPath}/assets/search/images/searchblue.png') no-repeat center center ;
}
.oe_searchview .oe_searchview_clear1{
	cursor: pointer;
    top: 10px;
    width: 15px;
    height:20px;
    margin-left: 250px;
    background: url('${pageContext.request.contextPath}/assets/search/images/search_reset.gif') center center no-repeat;
}
.ui-dialog .ui-dialog-content {
  font-size: 20px;
}
	</style> 
	<script>
	
	var _gridWidth = 0;
	var _gridHeight = 0; 
	var rowHeight = 35;
	//var mainDoc = window.parent.parent.document;
	//页面自适应
	function resizePageSize(){
		//alert('1');
		_gridWidth = getGridWidth(0.78);
		$('#YDflexiGrid').flexResize(_gridWidth,0);
		$('#PQflexiGrid').flexResize(_gridWidth,0);

		//重置主页面内容的高度
		if (top.resetMainContent) {
			top.resetMainContent();
		}
	}

	function commitUsbDate()
	{

	
		if($('#barcode').val() === '')
		{
			$('#barcode').focus();
			return;
		}
		//再次扫码时关闭上一次的提示
		if($( "#msgDialog" ).data("dialog")){
			var isOpen = $( "#msgDialog" ).dialog( "isOpen" );
			if(isOpen){
				$( "#msgDialog" ).dialog( "close" );
			}
		}
		
		if(${checkType} == 1)
        {
               message({
                        data:{msg:'仓内扫描只允许在PDA APP上进行',success:false,description:'仓内扫描只允许在PDA APP上进行'},
                        fontSize:30,
                        modal:false,
                        width:450
                    });
                return;
        }
		
		var _barcode = $('#barcode').val();
		$('#barcode').attr("lastcode", _barcode);
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/scans/usbScansIn',
			dataType : 'json',
			cache : false,
			data : [ {name : 'barcode',value : _barcode},
			         {name : 'scansType',value :'${checkType}'},
			         {name : 'batchID',value :'${batchID}'},
			         {name : 'batchName',value :'${batchName}'},
			         {name : 'qryRQ',value :'${qryRQ}'}],
			success : function(data) {
			//console.log(data);
				if(data.success == false){
					message({
						data:data,
						fontSize:30,
						modal:false,
						width:450
					});
				}else{
					
				}
				scansDeptStatistics();
				qryList();
				/*
				if(data.code==0){
					scansDeptStatistics();
					qryList();
					if(data.mess){
						var messMap = JSON.parse(data.mess) ;
						if(messMap && messMap.msg){
							message({
								data: data,
								fontSize:30,
								modal:false,
								width:450
							});
						}else{
							
						}
					}
				}else{
					if(data.mess){
						var messMap = JSON.parse(data.mess) ;
						if(messMap && messMap.msg){
							message({
								data: data,
								fontSize:30,
								modal:false,
								width:450
							});
						}else{
							message({
								data: data,
								fontSize:30,
								modal:false,
								width:450
							});
						}
					}else{
						message({
							data: data,
							fontSize:30,
							modal:false,
							width:450
						});
					}
				}
				*/
				/*
				if(data.success == false){
					var msgData = JSON.parse(data.msg) ;
					message({
						html:msgData.msg,
						fontSize:30,
						modal:false,
						width:450
					});
				}else{
					if(data.msg && data.msg!=""){
						var msgData = JSON.parse(data.msg) ;
						message({
							html:msgData.msg,
							fontSize:30,
							modal:false,
							width:450
						});
					}
					scansDeptStatistics();
					qryList();
				}
				*/
			}
		});
		$('#barcode').val('');
	}
	
	function scansDeptStatistics(dtd)
	{
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/scans/scansDeptStatistics2',
			dataType : 'json',
			cache : false,
			//data : [ {name : 'zXBC',value :'${batchID}'}],
			data : [ {name : 'batchName',value :'${batchName}'},{name : 'qryRQ',value :'${qryRQ}'}],
			success : function(data) {
				if(data.success == true){
					//alert(data.msg);
					var filterarray = JSON.parse(data.msg);
					if(filterarray != null){
						var html = '';
						var totalNeed = 0 ;
						var totalAll = 0 ;
						//area.DEPTCODE as "deptCode",area.DEPTNAME as "deptName",count(*) as "num" 
						if(${checkType} == 0){
							for(var i=0;i<filterarray.length;i++){
								totalNeed = totalNeed + filterarray[i].inNeed;
								totalAll = totalAll + filterarray[i].inAll;
								 html +='<li onclick="areaClick(this)" deptname="' 
								  + filterarray[i].deptName + '">' + filterarray[i].deptName 
								  + '<div class="deptDiv"><div class="deptNum">' 
								  + filterarray[i].inNeed + '/' 
								  + filterarray[i].inAll +'</div></div></li>';	
						    }	
							html = '<li onclick="areaClick(this)" deptname="' 
								  + '全部' + '">' + '全部' 
								  + '<div class="deptDiv"><div class="deptNum">' 
								  + totalNeed + '/' 
								  + totalAll +'</div></div></li>' 
								  + html ;
						}else if(${checkType} == 1)
						{
						                          for(var i=0;i<filterarray.length;i++){
                                totalNeed = totalNeed + filterarray[i].midNeed;
                                totalAll = totalAll + filterarray[i].midAll;
                                 html +='<li onclick="areaClick(this)" deptname="' 
                                      + filterarray[i].deptName + '">' + filterarray[i].deptName 
                                  + '<div class="deptDiv"><div class="deptNum">' 
                                  + filterarray[i].midNeed + '/' 
                                  + filterarray[i].midAll +'</div></div></li>'; 
                            }
                            html = '<li onclick="areaClick(this)" deptname="' 
                                  + '全部' + '">' + '全部' 
                                  + '<div class="deptDiv"><div class="deptNum">' 
                                  + totalNeed + '/' 
                                  + totalAll +'</div></div></li>' 
                                  + html ;
						}
						else if(${checkType} == 2){
							for(var i=0;i<filterarray.length;i++){
								totalNeed = totalNeed + filterarray[i].outNeed;
								totalAll = totalAll + filterarray[i].outAll;
								 html +='<li onclick="areaClick(this)" deptname="' 
									  + filterarray[i].deptName + '">' + filterarray[i].deptName 
								  + '<div class="deptDiv"><div class="deptNum">' 
								  + filterarray[i].outNeed + '/' 
								  + filterarray[i].outAll +'</div></div></li>';	
						    }
							html = '<li onclick="areaClick(this)" deptname="' 
								  + '全部' + '">' + '全部' 
								  + '<div class="deptDiv"><div class="deptNum">' 
								  + totalNeed + '/' 
								  + totalAll +'</div></div></li>' 
								  + html ;
						}
						$('#deptUl').html(html);
						
					}
				}
			}
		});
	}

	function scansDeptStatistics2(dtd)
	{
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/scans/scansDeptStatistics2',
			dataType : 'json',
			cache : false,
			data : [ {name : 'zXBC',value :'${batchID}'},{name : 'qryRQ',value :'${qryRQ}'}],
			success : function(data) {
				if(data.success == true){
					//alert(data.msg);
					var filterarray = JSON.parse(data.msg);
					if(filterarray != null){
						var html = '';
						var totalNeed = 0 ;
						var totalAll = 0 ;
						//area.DEPTCODE as "deptCode",area.DEPTNAME as "deptName",count(*) as "num" 
						if(${checkType} == 0){
							for(var i=0;i<filterarray.length;i++){
								totalNeed = totalNeed + filterarray[i].inNeed;
								totalAll = totalAll + filterarray[i].inAll;
								 html +='<li onclick="areaClick(this)" deptname="' 
								  + filterarray[i].deptName + '">' + filterarray[i].deptName 
								  + '<div class="deptDiv"><div class="deptNum">' 
								  + filterarray[i].inNeed + '/' 
								  + filterarray[i].inAll +'</div></div></li>';	
						    }	
							html = '<li onclick="areaClick(this)" deptname="' 
								  + '全部' + '">' + '全部' 
								  + '<div class="deptDiv"><div class="deptNum">' 
								  + totalNeed + '/' 
								  + totalAll +'</div></div></li>' 
								  + html ;
						}else{
							for(var i=0;i<filterarray.length;i++){ 
								totalNeed = totalNeed + filterarray[i].outNeed;
								totalAll = totalAll + filterarray[i].outAll;
								 html +='<li onclick="areaClick(this)" deptname="' 
									  + filterarray[i].deptName + '">' + filterarray[i].deptName 
								  + '<div class="deptDiv"><div class="deptNum">' 
								  + filterarray[i].outNeed + '/' 
								  + filterarray[i].outAll +'</div></div></li>';	
						    }
							html = '<li onclick="areaClick(this)" deptname="' 
								  + '全部' + '">' + '全部' 
								  + '<div class="deptDiv"><div class="deptNum">' 
								  + totalNeed + '/' 
								  + totalAll +'</div></div></li>' 
								  + html ;
						}
						$('#deptUl').html(html);
						
					}
				}
			}
		});
	}
	
	$(function() {
		window.onresize = function(){
			
			resizePageSize();
		}
		$(window).resize(function(){
			resizePageSize();
		});
		resizePageSize();
		//mainDoc = window.parent.parent.document;
		$("#scansBtn").bind("click",function(){
			commitUsbDate();
		});
		
		
		//_gridWidth = getGridWidth(0.78);
		var _columnWidth = _gridWidth / 4 * 0.99;
		
		$("#YDflexiGrid").flexigrid({
			width : _gridWidth,
			dataType : 'json',
			colModel : [ {display : "<spring:message code='scans.drugName'/>",name : 'drugName',width : _columnWidth, sortable : true,align : 'left',process: function(v,_trid,_row) {
				return v.replace(new RegExp("@@","g"),"<br>");
			}},
						 {display : "<spring:message code='scans.specifications'/>",name : 'specifications',width : _columnWidth, sortable : true,align : 'left'}, 
						 {display : "<spring:message code='scans.dose'/>",name : 'dose',width : _columnWidth, sortable : true,align : 'left'}, 
						 {display : "<spring:message code='scans.quantity'/>",name : 'quantity',width : _columnWidth, sortable : true,align : 'left'}
			      ],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : false, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			showcheckbox : false, //是否显示多选框
			rowhandler : null, //是否启用行的扩展事情功能,在生成行时绑定事件，如双击，右键等
			rowbinddata : true,
			numCheckBoxTitle : "<spring:message code='common.selectall'/>"
		});
		//340
		var _columnWidth = (_gridWidth-20) / (9+3);
		$("#PQflexiGrid").flexigrid({
			width : _gridWidth-20,
			height: $(window).height()-160,
			dataType : 'json',
			colModel : [ 
						{display : "smjg",name : 'smJG',width : _columnWidth, hide:true},
						{display : "yDZT",name : 'yDZT',width : _columnWidth, hide:true}, 
						{display : "yDPQ",name : 'yDPQ',width : _columnWidth, hide:true}, 
						{display : "<spring:message code='scans.mdBottleSign'/>",name : 'yDPQ',width : _columnWidth, sortable : true,align : 'center'}, 
						{display : "<spring:message code='scans.mkInpatientArea'/>",name : 'deptName',width : _columnWidth, sortable : true,align : 'left'}, 
						{display : "<spring:message code='scans.mdBedNo'/>",name : 'bedNO',width : _columnWidth, sortable : true,align : 'left'}, 
						{display : "<spring:message code='scans.mdName'/>",name : 'patName',width : _columnWidth, sortable : true,align : 'left'},
						{display : "批次",name : 'pcName',width : _columnWidth, sortable : true,align : 'center'}, 
						{display : "<spring:message code='scans.status'/>",name : 'yDZT',width : _columnWidth, sortable : true,align : 'left', process: shoaMaioZhuanTai}, 
						{display : "<spring:message code='scans.printed'/>",name : 'yDZT',width : _columnWidth, sortable : true,align : 'left', process: function(v) {
								 var reValue;
								 if( v > 3){
									 reValue="<spring:message code='common.yes'/>";
								 }else{
									 reValue="<spring:message code='common.no'/>";
								 }
								 return reValue;
							  }}, 
						{display : "<spring:message code='scans.scansTime'/>",name : 'smRQ',width : _columnWidth, sortable : true,align : 'left'}, 
						{display : "<spring:message code='scans.remarks'/>",name : 'smSBYY',width : _columnWidth, sortable : true,align : 'left'},
						{display: '<spring:message code="pivas.yz1.drugname"/>', name : 'drugname', width : 250, sortable : true, align: 'left',process: function(v,_trid,_row) {
								return v.replace(new RegExp("@@","g"),"<br>");
						}}
			      ],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : false, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			showcheckbox : false, //是否显示多选框
			rowhandler : null, //是否启用行的扩展事情功能,在生成行时绑定事件，如双击，右键等
			rowbinddata : true,
			onSuccess:function(){
				$("#PQflexiGrid tr").each(function(i){
					setTdBackgroundColor($(this));
				});
			},
			numCheckBoxTitle : "<spring:message code='common.selectall'/>"
		});

		scansDeptStatistics();
		qryList('');
});
	//设置背景色
	function setTdBackgroundColor($trObj){
		var yDZT = $.trim($trObj.find("td:eq(1) div").text());
		var ydPQ = $.trim($trObj.find("td:eq(2) div").text());
		var _barcode = $('#barcode').attr("lastcode");
		//未打印的不进行变色
		if(yDZT > 3 && ydPQ == _barcode){
			$trObj.find("td").each(function(i){
				$(this).css({"background-color":"#90EE90","color":"black"});//当前扫描绿色
			});
			$("#PQflexiGrid").parent().animate({
				   scrollTop: $trObj.offset().top+'px'
			}, 500);
		}else {
			var bgColor = "";
			if(${checkType} == 0){
				 if( yDZT == 5 || yDZT == 6){
					 bgColor = "#6E97E4";//通过蓝色
				 }else{
					 bgColor = "#FFC7C8";//待核对红色
				 }
			 }else{
				 if( yDZT == 6){
					 bgColor = "#6E97E4";
				 }else{
					 bgColor = "#FFC7C8";
				 }
			 }
			$trObj.find("td").each(function(i){
				$(this).css({"background-color":bgColor,"color":"black"});
			});
		}
	}
	//处理扫描状态
	function shoaMaioZhuanTai(v){
		var reValue;
		 //入仓
		 if(${checkType} == 0){
			 if( v == 5 || v == 6 || v==7){
				 reValue="<spring:message code='scans.status.finished'/>";
			 }else{
				 reValue="<spring:message code='scans.status.notFinished'/>";
			 }
		 }else if(${checkType} == 1){
			 if( v == 6 || v == 7){
				 reValue="<spring:message code='scans.status.finished'/>";
			 }else{
				 reValue="<spring:message code='scans.status.notFinished'/>";
			 }
		 }
		 else if(${checkType} == 2){
				 if(v == 7)
				 {
				  reValue="<spring:message code='scans.status.finished'/>";
				 }
				 else
				 {
				 reValue="<spring:message code='scans.status.notFinished'/>";
				 }
		 }
			 
		 return reValue;
	}
	
	function qryList(param){
		/* $('#PQflexiGrid').flexOptions({
			newp: 1,
			extParam: $.unique(list.concat(param)),
        	url: "${pageContext.request.contextPath}/scans/queryBottleLabelList"
        }).flexReload(); */
		var list = [ 
            {name : 'deptName',value :$('#deptNameID').val()},
            //{name : 'zXBC',value : '${batchID}'},
            {name : 'batchName',value : '${batchName}'},
            {name : 'smLX',value : '${checkType}'},
            {name : 'qryRQ',value : '${qryRQ}'}
        ];
        if(param == null){
        	param = [];
        }
        
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/scans/queryBottleLabelList',
			dataType : 'json',
			cache : false,
			data : $.unique(list.concat(param)),
			success : function(data) {
				$('#PQflexiGrid').appendJsonData(data);
			}
		});
	}
	
	function areaClick(o)
	{
		var deptName = $(o).attr('deptName');
		$("#deptNameID").val(deptName);
		qryList([]);
	}
	
	function usbChange()
	{
		//alert('1');
		setTimeout(function () {
			commitUsbDate();
		}, 500);
		
	}
	
    var websocket;
    if ('WebSocket' in window) {
    	websocket = new WebSocket("ws://${serverIP}:${httpPort}/pivas/WebSocketServer");
        
    } else if ('MozWebSocket' in window) {
    	websocket = new WebSocket("ws://${serverIP}:${httpPort}/pivas/WebSocketServer");
    }
    websocket.onopen = function (evnt) {
    };
    websocket.onmessage = function (evnt) {
        //$("#msgcount").html("(<font color='red'>"+evnt.data+"</font>)")
        
        if( evnt.data != null){
        	var json = JSON.parse(evnt.data);
        	if(json != null){
              	if(json.smLX === '${checkType}'){
              		//姓名
                	if(json.patName != null){
                		$('#scansName').val(json.patName);
                	}
                	//批次
                	if(json.batchName != null){
                		$('#scansBatch').val(json.batchName);
                	}
                	//床号
                	if(json.bedNO != null){
                		$('#scansBedNO').val(json.bedNO);
                	}
                	//瓶签号
                	if(json.yDPQ != null){
                		$('#scansYDPQ').val(json.yDPQ);
                	}
                	//病区
                	if(json.deptName != null){
                		$('#scansDeptName').val(json.deptName);
                	}
                	
                	//扫描结果
                	if(json.smJG != null){
                		var tx = $('#scansResultID option[value='+json.smJG+']').text();
                		//$('#scansResult').combobox('reset', json.smJG);
                		$('#scansResult').val(tx);
                		
                	}
                	
                	//扫描日期
                	if(json.smRQ != null){
                		$('#scansTime').val(json.smRQ);
                	}
                	
                	if(json.medicineBeanList != null){
                		$("#YDflexiGrid").appendJsonData(json.medicineBeanList);
                	   	//$('#YDflexiGrid').rowHeight();
                		
                	}
              	}
        	}
    		scansDeptStatistics();
    		qryList([]);
        	$('#barcode').focus();
        	$('#barcode').val('');
        }
        
    };
    websocket.onerror = function (evnt) {
    };
    websocket.onclose = function (evnt) {
    };
    function scansOnload()
    {
    	$('#barcode').focus();
    }
	</script>
</head>
<body onload="scansOnload()" onresize="resizePageSize();" onclick="scansOnload()">

<div class="main-div" style="width:100%">

  	<table id="mainTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
		<tr>
			<!-- 左侧树 -->
	    	<td  valign="top" nowrap="nowrap" id="leftContentaa" width="30%" >
	     		<div class="abc">
					<ul id="deptUl"> 
						
					</ul> 
			 	</div>
	    	</td>

	    	<!-- 缩进条 -->
	    	
	    	<td  nowrap="nowrap" class="space"><div class="cPoint" onclick="leftRightPucker('leftContentaa',this,'flexiGridr');" ></div></td>
	    	<!-- 右侧主页 -->
	       	<td valign="top" width="70%" >
	     		<table style="margin-left: 20px" width="80%">
			  		<!-- 导航 -->
			  		<tr>
				    	<td valign="top" height="35px"> 
							<div style="font-size:18px;color: #444444;font-weight:bold;">
								${checkName}->${batchName}
							</div>
			   			</td>
		     	   </tr>  
		     	   
			  		<%-- <tr>
				    	<td valign="top"  style="height:50px;">
							<div class="oe_searchview">
					     		<div class="oe_searchview_facets" >
					     		  <button class="oe_searchview_search1" title="重新搜索" type="button" id="searchbtn">搜索</button>
						    <div class="oe_searchview_input oe_searchview_head"></div>
						    <div class="oe_searchview_input"  id="inputsearch" >
						    	  <input id="txt" type="text" class="oe_search_txt" style="max-height: 25px;width: 250px;z-index: 1000;"/>
						    	  
						    </div>
					    		</div>
									<div class="oe-autocomplete" ></div>
									<div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
										<cite>请选择...</cite>
										<ul class="ulQry" style="-webkit-border-radius: 20;" funname="qryList" >
											<li show="<spring:message code='scans.mdName'/>" name="patName" > <spring:message code="common.search1"/> <spring:message code="scans.mdName"/><span class="searchVal"></span></li>
											<li show="<spring:message code='scans.mdBedNo'/>" name="bedNO" > <spring:message code="common.search1"/> <spring:message code="scans.mdBedNo"/><span class="searchVal"></span></li>
											<li show="<spring:message code='scans.mdBottleSign'/>" name="yDPQ" > <spring:message code="common.search1"/> <spring:message code="scans.mdBottleSign"/><span class="searchVal"></span></li>

										</ul>
									</div>
							</div>
							<div style="position: relative;left:-20px;top:7px;height:3px;z-index: 99;" >
							<img alt="" src="http://demo.openerp.cn/web/assets/src/img/search_reset.gif" height="10px;" width="10px;" style="margin-top: 10px;" onclick="clearclosedinputall();">
							</div>
							<div style="display: none;">
							    <input id="deptNameID" type="hidden"/>
							</div>
			   			</td>
		     	   </tr>   --%>
		     	   <div style="display: none;">
							    <input id="deptNameID" type="hidden"/>
					</div>
			 		<!-- 电网对象列表 -->
			  		<tr style="margin-top: 15px;">
				    	<td valign="top">
							<div  class="scansContents" id="flexiGridr">
							    <c:if test="${usbScanCode}">
								<div class="row">
								</c:if>
							    <c:if test="${usbScanCode != true}">
								<div class="row" style="display: none">
								</c:if>

									   <!-- 条码号 -->
									   <div style="float:left; font-size:12px; color:#333; 
	                                      width: 13%; height: 25px; line-height: 25px; text-align: right;
	                                      margin:0px 8px;">
									           <strong> <spring:message code="scans.barcodeTit"/></strong>
									   </div>
									    <div style="float:left; width: 49%;">
										     <input style=" width: 100%;" id="barcode" >
										</div>
										<div style="float:left; width: 25%;margin-top:3px;">
										   <a class="button" id="scansBtn" onclick="commitUsbDate()"><spring:message code='scans.scans'/></a>
										</div>
								</div>
								<%-- <div class="row">
									<!-- 姓名 -->
									<div class="column"> 
										<div class="titl">
										       <strong><spring:message code="scans.mdName"/></strong>
										</div>
										<div class="contents">
										   <input id="scansName" style="width: 100%;" readonly="readonly">  
										</div>
									          
									</div>
									<!-- 批次  -->
									<div class="column"> 
                                           <div class="titl">
									             <strong> <spring:message code="scans.mdBatch"/></strong>
									        </div>

										<div class="contents">
										   <input id="scansBatch" style=" width: 100%;" readonly="readonly">  
										</div>  
									</div>
								</div>
								<!-- 床号 -->
								<div class="row">
									<div class="column"> 
										<div class="titl">
										     <strong><spring:message code="scans.mdBedNo"/></strong>
										</div>
										<div class="contents">
										   <input id="scansBedNO" style=" width: 100%;" readonly="readonly">  
										</div>
									          
									</div>
									<!-- 瓶签号 -->
									<div class="column"> 
                                           <div class="titl">
									              <strong> <spring:message code="scans.mdBottleSign"/></strong>
									        </div>

										<div class="contents">
										   <input id="scansYDPQ" style=" width: 100%;" readonly="readonly">  
										</div>  
									</div>
								</div>
								<!-- 病区 -->
								<div class="row">
									<div class="column"> 
										<div class="titl">
										      <strong><spring:message code="scans.mkInpatientArea"/></strong>
										</div>
										<div class="contents">
										   <input id="scansDeptName" style=" width: 100%;" readonly="readonly">  
										</div>
									          
									</div>
									<!-- 扫描时间 -->
									<!-- div class="column"> 
                                           <div class="titl">
									             <spring:message code="scans.scansTime"/>
									        </div>

										<div class="contents">
										   <input id="scansTime" style=" width: 100%;" readonly="readonly" >  
										</div>  
									</div>
								</div-->
								<!-- 扫描结果 -->
									<div class="column"> 
										<div class="titl">
										     <strong><spring:message code="scans.scansRst"/></strong>
										</div>
										<div class="contents">
										   <input id="scansResult" style=" width: 100%;" readonly="readonly" > 
										</div>									          
									</div> --%>
							</div>
			   			</td>
		     	   </tr>
		     	   <!-- tr><td>
						<div id="ptable" style="width:97.5%; height: 100%; margin: 2px 0px 10px 0px;">
							<table id="YDflexiGrid" style="display: block;margin: 0px;"></table>
						</div>
		     	   </td></tr--> 	
		     	   <tr><td valign="top" style="height: 10px;background-color: white;">
						
		     	   </td></tr>
		     	   <tr><td valign="top">
						<div id="ptablePQ" style="width:97.5%; height: 100%; margin: 2px 0px 10px 0px;">
							<table id="PQflexiGrid" style="display: block;margin: 0px;"></table>
						</div>
		     	   </td></tr> 
	      		</table>
	      	</td>
	      </tr>
	      <tr></tr>
	      
	      
  	</table>
</div>

	<div id="selectDiv1" style="display:none;">
	  <sd:select id="scansResultID"  width="156px"  required="false" categoryName="scans.scansResult" tableName="sys_dict" />
	</div>
</body>

</html>