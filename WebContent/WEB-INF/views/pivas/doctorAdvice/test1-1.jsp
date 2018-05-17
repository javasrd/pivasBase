<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="/WEB-INF/views/common/common.jsp" %>

<!DOCTYPE html>
<html>
<head lang="en">

    <meta charset="UTF-8">
    <title></title>	
     <link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/assets/pivas/js/srvs.js" type="application/javascript"></script>

	<script type="text/javascript">
	
	var _gridWidth = 0;
	var _gridHeight = 0; 
	var param1 = [];
	
	var thisPArea = '${deptCodeFirst}';
	//页面自适应
	function resizePageSize(){
		_gridWidth = $(document).width()-200-12;/*  -189 是去掉左侧 菜单的宽度，   -12 是防止浏览器缩小页面 出现滚动条 恢复页面时  折行的问题 */
		_gridHeight = $(document).height()-32-200; /* -32 顶部主菜单高度，   -90 查询条件高度*/
		$("#flexGrid").flexResize();
	}
	function qryListByArea(refushPAreaId){
		if(refushPAreaId && refushPAreaId!=undefined){
			thisPArea = refushPAreaId;
			$("#ulDept li").each(function(){
				$(this).removeClass("completed");
			});
			$("#area_"+thisPArea).parent().parent().addClass("completed");
			$(".progressor-bar-inner").css("width",parseFloat(($("#area_"+thisPArea).attr("present")+"E+2"))+"%");
			$(".pre-nums").html($("#area_"+thisPArea).html());
		}
		if(thisPArea!=-1){
			qry(true);
			backMain();
		}
	}
	function refushPArea(){
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/groupNumByInpArea',
			dataType : 'json',
			cache : false,
			data : {"wardCode":thisPArea,"enabled":"1"},
			success : function(data) {
				var list = data || [];
				var rownum = 0 ;
				var checkOkNum = 0 ;
				if(list && list.length>0){
					for(var i in list){
						var row = list[i];
						rownum = rownum + row.countNum;
						checkOkNum = checkOkNum + row.checkOkNum;
					}
					for(var i in list){
						var row = list[i];
						$("#area_"+row.wardCode).html(row.checkOkNum+"/"+row.countNum).attr("present",row.checkOkNum/row.countNum);
					}
					$("#area_all").html(checkOkNum+"/"+rownum);
					
					$("#ulDept li").each(function(){
						$(this).removeClass("completed");
						//if
					});
					$("#area_"+thisPArea).parent().parent().addClass("completed");
					$(".progressor-bar-inner").css("width",parseFloat(($("#area_"+thisPArea).attr("present")+"E+2"))+"%");
					$(".pre-nums").html($("#area_"+thisPArea).html());
				}else{
					
				}
			}
		});
	}
	
	$(function() {
		$(window).resize(function(){
			resizePageSize();
		});
		resizePageSize();
		
		refushPArea();
		
		//var _columnWidth = (parseInt(_gridWidth)-55) / 15;
		var _columnWidth = (parseInt(_gridWidth)-55) / 10;
		$("#flexGrid").flexigrid({
			width : _gridWidth,
			height : _gridHeight,
			url: "${pageContext.request.contextPath}/pati/patiList",
			dataType : 'json',
			colModel : [ 
						{display: 'inhospNo', name : 'inhospNo', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: '<spring:message code="pivas.patient.code"/>', name : 'inhospNo', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.name"/>', name : 'patName', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.sex"/>', name : 'sexName', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.wardName"/>', name : 'wardName', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.state"/>', name : 'state', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.caseid"/>', name : 'case_ID', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.birthday"/>', name : 'birthDay', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.age"/>', name : 'ageDetail', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.avdp"/>', name : 'avdp', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.bedNo"/>', name : 'bedNo', width : _columnWidth, sortable : true, align: 'center'}
					],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			rowbinddata : function(r){
				var columnsArray = $(r).attr('ch').split("_FG$SP_");
				alert(columnsArray);
			}
		});
		
		
		qry(false);
		
		$("#aSearch").bind("click",function(){
			qry(false);
		});
	});
	function qry(clear){
		if(clear){
			clearclosedinputall();
		}
		if(thisPArea!=undefined && thisPArea!="-1"){
			if(param1!=undefined){
				params = param1;
			}else{
				params = [];
			}
			params.push({"name":"wardCode","value":thisPArea});
			$('#flexGrid').flexOptions({
				newp: 1,
				extParam: params,
	        	url: "${pageContext.request.contextPath}/pati/patiList"
	        }).flexReload();
		}
	}
	function qryList(param){
		if(param instanceof Array){
			param1 = param ;
		}else{
			param1 = [];
		}
		
		qry(false);
	}
	function backMain(){
		$("#yzInfo").hide().html("");
		$("#yzMain").show();
	}
	var checkIdS = "";
	var repeatCheck = "N";

	var checkIdS = "";
	var newYzshzt = "" ;
	function checkOK(obj){
		newYzshzt = "1" ;
		$("#checkOKBt").css("background-color","#73ab65");
		$("#checkNOBt").css("background-color","#ebb800");
	}
	function checkNO(obj){
		newYzshzt = "2" ;
		$("#checkOKBt").css("background-color","#ebb800");
		$("#checkNOBt").css("background-color","#d45120");
	}
	function checkOne(yzMainId,pidsj,yzshzt,checkType){
		if(checkType=='checkOK'){//审核新的状态为通过
			if(yzshzt==1){//原状态为通过，重新审核通过
				message({
					html: "<spring:message code='comm.mess20'/>",
					showCancel:true,
					confirm:function(){
						checkSubmit(yzMainId,pidsj,"Y","1",yzshzt);
					}
		    	});
			}else{
				checkSubmit(yzMainId,pidsj,"N","1",yzshzt);
			}
		}else{//审核新的状态为不通过
			if($("#checkNOSele_"+yzMainId).val() && $("#checkNOSele_"+yzMainId).val()!=""){
			}else{
				$("#checkNOYY_"+yzMainId).show();
				return ;
			}
			$("#checkNOYY_"+yzMainId).hide();
			if(yzshzt==1){//原状态为通过，重新审核通过
				message({
					html: "<spring:message code='comm.mess20'/>",
					showCancel:true,
					confirm:function(){
						checkSubmit(yzMainId,pidsj,"N","2",yzshzt);
					}
		    	});
			}else{
				checkSubmit(yzMainId,pidsj,"N","2",yzshzt);
			}
		}
	}
	
	function checkSubmit(yzMainId,pidsj,repeatCheck,newYzshzt,yzshzt){
		$("#checkNOYY_"+yzMainId).hide();
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
			dataType : 'json',
			cache : false,
			data : {"pidsjN":pidsj,"repeatCheck":repeatCheck,"newYzshzt":newYzshzt,yzshbtglx:$("#checkNOSele_"+yzMainId).val(),"yzshbtgyy":$("#yzshbtgyy_"+yzMainId).val(),"yzParea":"1"},
			success : function(response) {
				message({html: response.msg});
				if(response.code==0){
					if(yzshzt==0){
						refushPArea();
					}
					if(newYzshzt==1){
						$("#checkOK_"+yzMainId).css("background-color","#73ab65");
						$("#checkNO_"+yzMainId).css("background-color","#ebb800");
						$("#checkNOSele_"+yzMainId).val("");
						$("#yzshbtgyy_"+yzMainId).val("");
					}else{
						$("#checkOK_"+yzMainId).css("background-color","#ebb800");
						$("#checkNO_"+yzMainId).css("background-color","#d45120");
					}
				}else{
				}
			}
		});
	}
	
	</script>
	
</head>
<body>
            <div class="content">
                <div class="category-list">
                    <ul id="ulDept" >
                        <li>
                            <a href="#" class="category">
                                <spring:message code='comm.mess21'/>
                            </a>
                        </li>
	                <li id="areaLi_111" class="completed" >
	                  <a href="javascript:void(0)" class="category">111111111111</a>
	                 </li>
	                 <li id="areaLi_222" >
	                  <a href="javascript:void(0)" class="category">222222222222</a>
	                 </li>
					</ul>
                </div>
                <div class="list-info"  style="margin-top: 15px;">
                    <div class="progressor-div">
                        <span class="text">测试标题</span>
                    </div>

                    <div class="tbl" style="margin-top: 10px;">
                    	<table id="flexGrid" style="display: block;"></table>
                    </div>
                    
                </div>

            </div>

</body>
</html>