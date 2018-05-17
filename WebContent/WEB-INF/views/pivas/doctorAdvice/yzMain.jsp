<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>

<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>

<style type="text/css">
.x-toolbar-default{
background-image:-webkit-linear-gradient(top,#B5B5B5,#B5B5B5);
}
.x-toolbar .x-toolbar-separator-horizontal{
    border-left: 1px solid #ACA899;
    border-right: 1px solid #FFF;
}
.x-column-header-over, .x-column-header-sort-ASC, .x-column-header-sort-DESC{
background-image:-webkit-linear-gradient(top,#B5B5B5,#B5B5B5 39%,#B5B5B5 40%,#B5B5B5);
}
.x-column-header{
background-image:-webkit-linear-gradient(top,#B5B5B5,#B5B5B5);
}
.x-panel .x-grid-body{
	border-color:#F5F5F5;
}
.x-grid-header-ct{
    border: 0px solid white;
}
.x-grid-table{
width:100%;
}
.x-column-header{
width:100%;
}
.x-grid-row .x-grid-cell{
}
.x-grid-row{
height:26px;
background-color: transparent;
}
.x-grid-td{
}
.x-grid-row-over {
    background: transparent;
    background-color: transparent;
}
tr:nth-child(2n)
{
    background: #EDEDED;
}
.button {
  padding: 0.54em 2em;
  }
#checkedall {
    margin-top: 48px;
    z-index: 10010;
    position: absolute;
    /*margin-left: 11px;*/
    left: 15px;
}
.x-grid-header-ct{
	background-image:url("");
}
#yzlx{
height: 26px;
margin-top: -2px;
}
select{
	height: 26px;
	margin-top: -2px;
}
.oe_searchview{
   	line-height: 18px; 
   	border: 1px solid #ababab;
   	background: white; 
   	width:500px;
    -moz-border-radius: 13px;
    cursor: text;
    padding: 1px 0;
    float:left;
    border: 1px solid #ababab;
    margin-top: 10px;
}
.pre-more{
	left:-100px;
}
#ulYZCheckMany{
left:530px;
}

</style>

<head>
<!-- 
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
 -->
	<style type="text/css">
	.cbit-grid div.bDiv td div {
	  cursor: pointer;
	}
	</style>
	<link href="${pageContext.request.contextPath}/assets/pivas/css/edit.css" type="text/css" rel="stylesheet" />
	
	<script type="text/javascript">
	var checkIdS = "";
	var repeatCheck = "N";
	var partHasCheck = 0 ;
	var paramTemp ;
	
	var _gridWidth = 0;
	var _gridHeight = 0; 
	
	var treeStore = null ;
	var treeGrid = null ;
	var checkedIDMap = {} ;
	
	Ext.require(['Ext.grid.*','Ext.selection.CheckboxModel']);
	Ext.onReady(function(){
		treeStore = Ext.create('Ext.data.TreeStore', {  
			fields: ['groupName','yzMainId','parentNo','yzlx','yzzt','yzshzt','rucangOKNum','pidsj','zxrq','zxsj',
		                 'p_bedno','wardName','p_patname','p_age','freqCode', 'drugname','dose','doseUnit',
		                 'quantity','medicamentsPackingUnit','sfysmc','sfrqS','yzshbtgyy',
		                 'yzlxS','yzztS','yzshztS','yzshbtglxS'
		                 ],
	        root:{"children":[]}  
		});
	});


	$(function() {

		$("#control_1").multiSelect({ "selectAll": false,"noneSelected": "分组条件","oneOrMoreSelected":"*" },function(){
			var valuestr = $("#control_1").selectedValuesString();
			if(valuestr && valuestr.length>0){
				initTree(valuestr);
			}else{
				
				$("#flexDiv").show();
				$("#treeDiv").hide();
				$("#checkedall").hide();
				qry();
			}
		});
		
		$("#checkedall").bind("change",function(){
			 if($(this).attr("checked")=="checked"){
				 $(".x-tree-checkbox").removeClass("x-tree-checkbox-checked").addClass("x-tree-checkbox-checked");
				 
				 if(treeGrid.selModel.store.data.items){
					 //treeGrid.selModel.store.data.items[0].data.groupName 
					 var listData = treeGrid.selModel.store.data.items;
					 for(var i in listData){
						 var row = listData[i];
						 if(row.data.pidsj!=undefined){
							 checkedIDMap[row.data.pidsj]=row.data;
						}
					 }
				 }
				 $("#ulYZCheckMany").show();
			  }else{
			    	$(".x-tree-checkbox").removeClass("x-tree-checkbox-checked");
			    	checkedIDMap = {};
			    	$("#ulYZCheckMany").hide();
			  }
		});
		
		
		//_gridWidth = 800 ;
		//var _columnWidth = (parseInt(_gridWidth)-55) / 15;
		$("#flexGrid").flexigrid({
			width : _gridWidth,
			height : _gridHeight,
			url: "${pageContext.request.contextPath}/doctorAdvice/yzPageData",
			dataType : 'json',
			colModel : [ 
						{display: 'yzMainId', name : 'yzMainId', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'parentNo', name : 'parentNo', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzlx', name : 'yzlx', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzzt', name : 'yzzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzshzt', name : 'yzshzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'rucangOKNum', name : 'rucangOKNum', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'pidsj', name : 'pidsj', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'zxrq', name : 'zxrq', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'zxsj', name : 'zxsj', width : 0, sortable : false, align: 'center',hide:'true'},
						
						{display: '<spring:message code="pivas.yz2.bedno"/>', name : 'p_bedno', width : '30', sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz1.wardname"/>', name : 'wardName', width : '40', sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz2.patname"/>', name : 'p_patname', width : '40', sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz2.age"/>', name : 'p_age', width : 30, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return _row.p_age+ getDicValue('ageUnit',_row.p_ageunit);
						}},
						{display: '<spring:message code="pivas.yz1.freqCode"/>', name : 'freqCode', width : 60, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz1.drugname"/>', name : 'drugname', width : 250, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.dose"/>', name : 'dose', width : 50, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.doseUnit"/>', name : 'doseUnit', width : 65, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.quantity"/>', name : 'quantity', width : 65, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.medicamentsPackingUnit"/>', name : 'medicamentsPackingUnit', width : 50, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.yzlx"/>', name : 'yzlx', width : 65, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return getDicValue('yzlx',v);
						}},
						{display: '<spring:message code="pivas.yz1.yzzt"/>', name : 'yzzt', width : 65, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return getDicValue('yzzt',v);
						}},
						{display: '<spring:message code="pivas.yz1.sfysmc"/>', name : 'sfysmc', width : 65, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz1.zxrq"/>', name : 'zxrq', width : 65, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz1.zxsj"/>', name : 'zxsj', width : 65, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz1.sfrq2"/>', name : 'sfrqS', width : 80, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz1.yzshzt2"/>', name : 'yzshzt', width : 70, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return getDicValue('yzshzt',v);
						}},
						{display: '<spring:message code="pivas.yz1.yzshbtgyy2"/>', name : 'yzshbtglxS', width : 120, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return _row.yzshzt==1?"":v;
						}}
						],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			showcheckbox : true, //是否显示多选框
			rowbinddata : true,
			rowhandler : rowDbclick,
			onrowchecked : rowChecked,
			onSuccess:function(){
				var patientName = "" ;
				$("#flexGrid tr").each(function(i){
					var yzshzt = $.trim($(this).find("td:eq(5) div").text());
					if(yzshzt =="1"){
						$(this).find("td").each(function(i){
							$(this).css("background-color", "#c6e0b0");
							$(this).css("border-bottom", "1px dotted #ddd");
						});
					}else if(yzshzt =="2"){
						$(this).find("td").each(function(i){
							$(this).css("background-color", "#faddbb");
							$(this).css("border-bottom", "1px dotted #ddd");
						});
					}else{
						
					}
					var patientName2 =  $.trim($(this).find("td:eq(12) div").text());
					if(patientName!="" && patientName!=patientName2){
						$(this).find("td").each(function(i){
							$(this).css("border-top", "1px solid black");
						});
					}
					patientName = patientName2;
				});
			},
			numCheckBoxTitle : "<spring:message code='common.selectall'/>"
		});
		
		function rowDbclick(r) {
			$(r).dblclick(
			function() {
				//获取该行的所有列数据
				var columnsArray = $(r).attr('ch').split("_FG$SP_");
				goToInfo(columnsArray[6]);
			});
		}
		
		function rowChecked(r){
			var pidsjN = getFlexiGridSelectedRowText($("#flexGrid"), 8);
			if(pidsjN && pidsjN.length >0) {
				$("#ulYZCheckMany").show();
			}else {
				$("#ulYZCheckMany").hide();
			}
		}
		
		qry();
		
		$("#aSearch").bind("click",function(){
			qry();
		});
		
		$("#aYZCheckMany").bind("click",function(){
			var row_partHasStop = 0 ;//部分数据 源端数据已停止，提示 部分医嘱已停止//0：执行 1：停止 2：撤销
			var row_partHasRuCang = 0 ;//部分数据 已进入入仓扫描阶段，不可审核
			var row_canCheck = 0 ;
			checkIdS = "";
			partHasCheck = 0 ;//部分数据 已审核，提示是否重新审核//0：未审核 1：审核通过 2：审核不通过
			
			if($("#flexDiv").css("display")=="block"){
				var pidsjN = getFlexiGridSelectedRowText($("#flexGrid"), 8);//parentNo 医嘱父ID
				if(pidsjN && pidsjN.length <1) {
					message({html: "<spring:message code='comm.err.param3'/>"});
					return;
				}
				var yzlxN = getFlexiGridSelectedRowText($("#flexGrid"), 4);//yzlxN	医嘱类型
				var yzztN = getFlexiGridSelectedRowText($("#flexGrid"), 5);//yzztN	医嘱状态
				var yzshztN = getFlexiGridSelectedRowText($("#flexGrid"), 6);//yzshztN	医嘱审核状态
				var rucangOKNumN = getFlexiGridSelectedRowText($("#flexGrid"), 7);//rucangOKNum	医嘱审核状态
				
				for(var i=0;i<pidsjN.length;i++){
					if(yzztN[i]==0){
						if(rucangOKNumN[i]==0){
							row_canCheck ++ ;
							if(checkIdS==""){
								checkIdS = pidsjN[i];
							}else{
								checkIdS = checkIdS + ","+pidsjN[i];
							}
							if(yzshztN[i]==1){
								partHasCheck ++ ;
							}
						}else{
							row_partHasRuCang ++ ;
						}
					}else{
						row_partHasStop ++ ;
					}
				}
			}else{
				checkIdS = "";
				
				var row = null ;
				partHasCheck = 0 ;//部分数据 已审核，提示是否重新审核//0：未审核 1：审核通过 2：审核不通过
				for(var key in checkedIDMap){
					row = checkedIDMap[key];
					if(row && row!=undefined){
						if(row.yzzt==0){
							if(row.rucangOKNum==0){
								row_canCheck ++ ;
								if(checkIdS==""){
									checkIdS = row.pidsj;
								}else{
									checkIdS = checkIdS + ","+row.pidsj;
								}
								if(row.yzshzt==1){
									partHasCheck ++ ;
								}
							}else{
								row_partHasRuCang ++ ;
							}
						}else{
							row_partHasStop ++ ;
						}
					}
				}
			}
			
			if(row_canCheck>0){
				if(row_partHasStop>0){
					var mess = (row_partHasStop>0?"[<spring:message code='comm.mess3'/>]":"") + (row_partHasRuCang>0?"[<spring:message code='comm.mess4'/>]":"") ;
					message({
		    			html: mess+'，将不会被审核！',
		    			showCancel:false,
		    			confirm:function(){
		    				gotoCheckView();
		    			}
		        	});
				}else{
					gotoCheckView();
				}
			}else{
				//message({html: "没有可审核的医嘱"});
				message({html: "<spring:message code='comm.mess11'/>"});
			}
		});
		
		$("#divYZCheckMany").dialog({
			autoOpen: false,
			height: 370,
			width: 650,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='comm.mess7'/>": function() {
					checkSubmit(1);
				},
				"<spring:message code='comm.mess8'/>": function() {
					checkSubmit(2);
				}
			},
			close: function() {
			}
		});
		
		//新增按钮
		$( "#addDataDicRoleBtn").bind("click",function(){
			$("#divYZCheckMany").dialog("open");
		});
		
		// 同步按钮
		$("#synDataDicBtn").bind("click",function(){
			message({
				 html: '<spring:message code="common.startSynTask"/>',
				 showCancel:true,
				 confirm:function(){
					$.ajax({
						type : 'POST', 
						url : '${pageContext.request.contextPath}/doctorAdvice/synYz',
						dataType : 'json',  
						cache : false,
						data : [],
						success : function(data) {
							qry();
							message({data: data});
							},
						error : function () {
							 message({
							     html: '<spring:message code="common.op.error"/>',
							     showConfirm: true
							    });
							}
						});
				    }
			    });
			});
		
	});
	function qry(){
		var params = [];
		if(paramTemp){
			params = paramTemp.concat();
		}
		params.push({"name":"rucangOKNum","value":"Y"});
		params.push({"name":"ydztLowN","value":5});
		params.push({"name":"areaEnabled","value":1});
		params.push({"name":"yzlx","value":$("#yzlx").val()});
		params.push({"name":"yzResource","value":$("#yzlx").val()==1?"1":"0"});
		$('#flexGrid').flexOptions({
			newp: 1,
			extParam: params,
        	url: "${pageContext.request.contextPath}/doctorAdvice/yzPageData"
        }).flexReload();
	}
	function qryList(param){
		paramTemp = param ;
		if($("#flexDiv").css("display")=="block"){
			qry();
		}else{
			var valuestr = $("#control_1").selectedValuesString();
			initTree(valuestr);
		}
	}
	function backMain(){
		$("#yzInfo").hide().html("");
		$("#yzMain").show();
		if($("#flexDiv").css("display")=="block"){
			$("#checkedall").hide();
		}else{
			$("#checkedall").show();
		}
		
	}
	
	function gotoCheckView(){
		$("#checkPass").val("");
		$("#yzshbtglx").val("");
		$("#yzshbtgyy").val("");
		$("#divYZCheckMany").dialog("open");
	}
	function checkSubmit(newYzshzt){
		if(!$("#checkAccount").val()){
			message({html: "<spring:message code='comm.mess9'/>"});
			return ;
		}
		if(!$("#checkPass").val()){
			message({html: "<spring:message code='comm.mess10'/>"});
			return ;
		}
		if(newYzshzt==2){
			if($("#yzshbtglx").val() && $("#yzshbtglx").val()!=""){
				
			}else{
				message({html: "<spring:message code='comm.mess12'/>"});
				return ;
			}
		}
		if(newYzshzt==1){
			if(partHasCheck>0){
				message({
					html: "<spring:message code='comm.mess14'/>",
					showCancel:true,
					confirmText: "<spring:message code='comm.mess1'/>",
					cancelText: "<spring:message code='comm.mess2'/>",
					confirm:function(){
						repeatCheck = "Y" ;
						checkSubmitData(newYzshzt);
					},
					cancel:function(){
						repeatCheck = "N" ;
						checkSubmitData(newYzshzt);
					}
		    	});
			}else{
				repeatCheck = "N" ;
				checkSubmitData(newYzshzt);
			}
		}else{
			if(partHasCheck>0){
				message({
					html: "<spring:message code='comm.mess13'/>",
					showCancel:true,
					confirm:function(){
						repeatCheck = "N" ;
						checkSubmitData(newYzshzt);
					}
		    	});
			}else{
				repeatCheck = "N" ;
				checkSubmitData(newYzshzt);
			}
		}
	}
	function checkSubmitData(newYzshzt){
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
			dataType : 'json',
			cache : false,
			data : {"pidsjN":checkIdS,
				"repeatCheck":repeatCheck,
				"newYzshzt":newYzshzt,
				yzshbtglx:$("#yzshbtglx").val(),
				"yzshbtgyy":$("#yzshbtgyy").val(),
				"checkAccount":$("#checkAccount").val(),
				"checkPass":$("#checkPass").val()
			},
			success : function(response) {
				message({html: response.msg});
				if(response.code==0){
					$("#divYZCheckMany").dialog("close");
					//message({html: "审方已置为不通过"});
					$("#ulYZCheckMany").hide();
					if($("#flexDiv").css("display")=="block"){
						qry();
						backMain();
					}else{
						var valuestr = $("#control_1").selectedValuesString();
						initTree(valuestr);
						
						checkedIDMap = {};
						$("#checkedall").removeAttr("checked");//取消全选
					}
				}else{
					message({html: response.msg});
					//message(respone.mess);
				}
				
			}
		});
	}
	function goToInfo(pidsj){
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/toYzInfo',
			dataType : 'html',
			cache : false,
				data : {"pidsj":pidsj},
			success : function(data) {
				$("#yzInfo").html(data).show();
				$("#yzMain").hide();
				$("#checkedall").hide();
			}
		});
	}
	
	function initTree(valuestr){
		var params =  [];
		if(paramTemp){
			params = paramTemp.concat();
		}
		params.push({"name":"rucangOKNum","value":"Y"});
		params.push({"name":"ydztLowN","value":5});
		params.push({"name":"groupBy","value":valuestr});
		params.push({"name":"areaEnabled","value":1});
		params.push({"name":"yzlx","value":$("#yzlx").val()});
		params.push({"name":"yzResource","value":$("#yzlx").val()==1?"1":"0"});
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/yzGroupByArea',
			dataType : 'json',
			cache : false,
			data : params ,
			success : function(response) {
				$("#flexDiv").hide();
				$("#treeDiv").html("");
				$("#checkedall").show();
				$("#treeDiv").show();
				
				treeStore = Ext.create('Ext.data.TreeStore', {  
					fields: ['groupName','yzMainId','parentNo','yzlx','yzzt','yzshzt','rucangOKNum','pidsj','zxrq','zxsj',
				                 'p_bedno','wardName','p_patname','p_age','p_ageunit','freqCode', 'drugname','dose','doseUnit',
				                 'quantity','medicamentsPackingUnit','sfysmc','sfrqS','yzshbtgyyS',
				                 'yzlxS','yzztS','yzshztS','yzshbtglxS'
				                 ],
			        root:{"children":response}  
			  });   
				//定义行数据和行标头，行框度
				gridCols = [	{xtype: 'treecolumn',text:'',dataIndex:'groupName',width: '130px'},
				                {text:'<spring:message code="pivas.yz2.bedno"/>',dataIndex:'p_bedno',width: '60px',css :'background: #FF0000;'},
				                {text:'<spring:message code="pivas.yz1.wardname"/>',dataIndex:'wardName',width: '80px'},
				                {text:'<spring:message code="pivas.yz2.patname"/>',dataIndex:'p_patname',width: '70px'},
				                {text:'<spring:message code="pivas.yz2.age"/>',dataIndex:'p_age',width: '40px', renderer: function(v,p,record){
	                              return record.get("p_age")+ getDicValue('ageUnit',record.get("p_ageunit"));
	                            }},
				                {text:'<spring:message code="pivas.yz1.freqCode"/>',dataIndex:'freqCode',width: '60px'},
				                {text:'<spring:message code="pivas.yz1.drugname"/>',dataIndex:'drugname',width: '200px', renderer: function(v,p,record){
	                              return v.replace(new RegExp("@@","g"),"<br>");
	                            }},
				                {text:'<spring:message code="pivas.yz1.dose"/>',dataIndex:'dose',width: '60px', renderer: function(v,p,record){
	                              return v.replace(new RegExp("@@","g"),"<br>");
	                            }},
				                {text:'<spring:message code="pivas.yz1.doseUnit"/>',dataIndex:'doseUnit',width: '60px', renderer: function(v,p,record){
	                              return v.replace(new RegExp("@@","g"),"<br>");
	                            }},
				                {text:'<spring:message code="pivas.yz1.quantity"/>',dataIndex:'quantity',width: '60px', renderer: function(v,p,record){
	                              return v.replace(new RegExp("@@","g"),"<br>");
	                            }},
				                {text:'<spring:message code="pivas.yz1.medicamentsPackingUnit"/>',dataIndex:'medicamentsPackingUnit',width: '50px', renderer: function(v,p,record){
	                              return v.replace(new RegExp("@@","g"),"<br>");
	                            }},
				                {text:'<spring:message code="pivas.yz1.yzlx"/>',dataIndex:'yzlx',width: '65px', renderer: function(v,p,record){
	                              return getDicValue('yzlx',v);
	                            }},
				                {text:'<spring:message code="pivas.yz1.yzzt"/>',dataIndex:'yzzt',width: '65px', renderer: function(v,p,record){
	                              return getDicValue('yzzt',v);
	                            }},
				                {text:'<spring:message code="pivas.yz1.sfysmc"/>',dataIndex:'sfysmc',width: '65px'},
				                {text:'<spring:message code="pivas.yz1.zxrq"/>',dataIndex:'zxrq',width: '80px'},
				                {text:'<spring:message code="pivas.yz1.zxsj"/>',dataIndex:'zxsj',width: '65px'},
				                {text:'<spring:message code="pivas.yz1.sfrq2"/>',dataIndex:'sfrqS',width: '140px'},
				                {text:'<spring:message code="pivas.yz1.yzshzt2"/>',dataIndex:'yzshzt',width: '80px', renderer: function(v,p,record){
	                              return getDicValue('yzshzt',v);
	                            }},
				                {text:'<spring:message code="pivas.yz1.yzshbtgyy2"/>',dataIndex:'yzshbtglxS',width: '120px', renderer: function(v,p,record){
	                              return record.get("yzshzt")==1?"":v;
	                            }}
				];
				//创建树的承载容器
				treeGrid = Ext.create('Ext.tree.Panel',{
					rootVisible: false,//是否显示根节点
					title: '表单层级遍历',//标头，但已隐藏
					id:'treeGrid',
					header:false,//头部的  bar
					bodyStyle: 'background:#F5F5F5;',
					autoHight:true,
					stripeRows : true,
					frame: true,
					store: treeStore,
					columns: gridCols, 
					animate: true,
					enableDD: true,
					containerScroll: true,
					border: true,
					width: '1560px',
					renderTo: "treeDiv",//将当前对象所生成的HTML对象存放在指定的对象中
					selModel: {
				        injectCheckbox: 0,
				        mode: "SIMPLE",     //"SINGLE"/"SIMPLE"/"MULTI"三种模式
				        checkOnly: true     //限制只能通过checkbox选择
				    },
				    //此处提供双击行获取行数据
				     listeners:{  
			        "checkchange": function(node, state) {//选择父节点勾选所有子节点；勾除所有子节点取出父节点勾选  
			            if (node.parentNode != null) {  
			            	if(!state){
			            		$("#checkedall").removeAttr("checked");//取消全选
			            	}
			            	
			                node.cascade(function(node){ 
			                	node.set('checked', state);
			                	if(node.raw.pidsj!=undefined){
			                		if(state){
			                			checkedIDMap[node.raw.pidsj]=node.raw;
									}else{
										checkedIDMap[node.raw.pidsj]=null;
									}
								}
			                    return true;  
			                });
			                var f = false ;
			                for(var key in checkedIDMap){
			                	if(checkedIDMap[key] && checkedIDMap[key]!=undefined){
			                		f = true ;
			                	}
			                }
			               	if(f){
			               		$("#ulYZCheckMany").show();
			               	}else{
			               		$("#ulYZCheckMany").hide();
			               	}  	
			            } 
			        }  
			    },
			    afterrender: function(node) {  
			    	var ss = node ;
	                   //展开树  
	                   //treeGrid.expandAll();  
	                }  
				});
				treeGrid.on('itemclick', function(treeview, record, item, index, e, opts) {
				});
				treeGrid.on('itemdblclick', function(treeview, record, item, index, e, opts) {
					if(record.raw.pidsj!=undefined){
						goToInfo(record.raw.pidsj);
					}
				});
				$("#treeGrid table tr").each(function(){
					if($(this).find("td:eq(17) div").html()=="<spring:message code='comm.mess15'/>"){
						$(this).css("background-color", "#c6e0b0");
						$(this).css("border-bottom", "1px solid #ddd");
						$(this).find("td").css("border-bottom", "1px solid #ddd");
					}else if($(this).find("td:eq(17) div").html()=="<spring:message code='comm.mess16'/>"){
						$(this).css("background-color", "rgb(250, 221, 187)");
						$(this).css("border-bottom", "1px solid #ddd");
						$(this).find("td").css("border-bottom", "1px solid #ddd");
					}
				});
			}
		});
	}
	
	</script>
</head>

<body>






<div id="yzMain" class="main-div" style="width:100%">

<input type="checkbox" id="checkedall" style="margin-top: 50px;z-index: 10010;position: absolute;left:15px;display: none;">



<!-- 搜索条件--开始 -->
<div class="oe_searchview" style="">
    <div class="oe_searchview_facets" >
	    <div class="oe_searchview_input oe_searchview_head"></div>
	    <div class="oe_searchview_input"  id="inputsearch" >
	    	  <input id="txt" type="text" class="oe_search_txt" onkeydown="this.onkeyup();" onkeyup="this.size=(this.value.length>1?this.value.length:1);" size="1"/>
	    </div>
    </div>
    <div class="oe_searchview_clear" onclick="clearclosedinputall();"></div>
    <button class="oe_searchview_search" type="button" id="searchbtn">搜索</button>
	<div class="oe-autocomplete" ></div>
	<div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
		<cite>请选择...</cite>
		<ul class="ulQry" style="-webkit-border-radius: 20;" funname="qryList" >
			<li show="<spring:message code='pivas.yz2.bedno'/>" name="bednoS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz2.bedno'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz2.patname'/>" name="patnameS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz2.patname'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz1.parentNo'/>" name="parentNoS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.parentNo'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz1.wardname'/>" name="wardNameS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.wardname'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.freqCode'/>：<span class="searchVal"></span></li>
		</ul>
	</div>
</div>
<!-- 搜索条件--结束 -->

        <div id="qryView-div">
        <div class="search-div">
    		<div class="oe_view_manager_view_search"></div>
    		
    		<span style="margin-left: 10px;">
				<select id="control_1" name="control_1[]" multiple="multiple" size="5" style="width:100px;height: 26px;margin-top: -2px;">
					<option value="area"><spring:message code='pivas.yz2.deptname'/></option>
					<option value="patient"><spring:message code='pivas.yz2.patname'/></option>
				</select>
			</span>
			<%--<span style="margin-left: 10px;"><input type="radio" name="yzlx" />长嘱</span>--%>
			<%--<span style="margin-left: 10px;"><input type="radio" name="yzlx" />临嘱</span>--%>
			<span style="height: 26px;margin-top: -2px;">

			<select id="yzlx" style="height: 26px;margin-top: -2px;">
				<option value="0" selected="selected" >长嘱</option>
				<option value="1">临嘱</option>
			</select>
			</span>
			<!-- 
			<select id="batchRule1111" style="visibility: none" >
			<c:forEach items="batchRuleList" var="batchRule" >
			<option value="${batchRule.pinc_code}" >${batchRule.pinc_name}</option>
			</c:forEach>
			</select>
			 -->
			 
			<ul id="batchRule" style="visibility: none" >
			<c:forEach items="batchRuleList" var="batchRule" >
			<c:if test="${batchRule.ru_key ==''}">
			<li name="${rule.pinc_code}" >${rule.ru_key}</li>
			</c:if>
			</c:forEach>
			</ul>
			
			<ul id="ruleKey" style="visibility: none" >
			<c:forEach items="${ruleList}" var="rule" >
			<c:if test="${rule.ru_key !=''}">
			<li name="${rule.pinc_code}" >${rule.ru_key}</li>
			</c:if>
			</c:forEach>
			</ul>
			
	        <a class="button" id="synDataDicBtn" style="" ><spring:message code='pivas_yz1.syn'/></a>
	        <ul id="ulYZCheckMany" class="pre-more" tabindex='-1' style="display: none;  margin-top: -26px;margin-left: 190px;"  >
	        		<li class="liBtH">
						<a class="button2"><spring:message code='comm.mess17'/></a>
                     </li>
                     <li class="liBtN" style="display: none;">
						<a class="button2" id="aYZCheckMany"><spring:message code='pivas_yz1.shenfangMore'/></a>
                     </li>
             </ul> 
        </div>
        </div>
        <div id="flexDiv" class="tbl" >
            <table id="flexGrid" style="display: block;margin: 0px;"></table>
        </div>
       
       	<div id="treeDiv" style="display:none;" ></div>
        
        
        
</div>

<div id="yzInfo" class="main-div" style="width:100%;dispaly:none">
</div>

<%-- 新增编辑弹出框 --%>
<div id="divYZCheckMany" title="<spring:message code='pivas_yz1.shenfangMore'/>" align="center" style="display: none;">
			<form id="editView-form" action="" method="post">
				<input type="hidden" id="gid" name="gid"/>
					<div class="popup" style="padding-top: 24px;">
						<div class="row">
							<div class="column">
								<label class="tit2"><spring:message code='user.zhanghao'/></label>
								<input type="text" class="edit" name="checkAccount" id="checkAccount" value="<spring:escapeBody htmlEscape="true"><shiro:principal property="account"/></spring:escapeBody>" maxlength="32" style="height: 28px;" title="<spring:message code='common.op.remind2'/>"/>
				            	<span class="mand">*</span>
							</div>
							<div class="column">
								<label class="tit2"><spring:message code='user.mima'/></label>
								<input type="password" class="edit" name="checkPass" id="checkPass" maxlength="32" style="height: 28px;" title="<spring:message code='common.op.remind2'/>"/>
				            	<span class="mand">*</span>
							</div>
						</div>
						
						<div class="row">
							<div class="column">
								<label class="tit2"><spring:message code='comm.mess18'/></label>
								<select name="yzshbtglx" id="yzshbtglx" style="width: 183px;  height: 28px;">
									<option value=""><spring:message code='comm.mess19'/></option>
									<c:forEach items="${errTypeList}" var="errType"  >
									<option value="${errType.gid}">${errType.name}</option>
									</c:forEach>
								</select>
								<span class="mand2"></span>
							</div>
							<div class="column">
								<label class="tit2"><spring:message code='common.remark'/></label>
								<textarea rows="1"  name="yzshbtgyy" id="yzshbtgyy" style="width: 400px" maxlength="256" title="<spring:message code='common.op.remind6'/>"></textarea>
							</div>
						</div>
					</div>
			</form>
		</div>

		
</body>

</html>