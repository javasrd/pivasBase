<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.sd.sm.common.constant.SmConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.sd.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>



<head>
	<!--
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
 -->
	<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
	<style type="text/css">
		.cbit-grid div.bDiv td div {
			cursor: pointer;
		}
		.oe_searchview{
			margin-top: 10px;
		}
		.search-div {
			padding: 1em 1em 0.7em 0em;
		}
	</style>
	<link href="${pageContext.request.contextPath}/assets/pivas/css/edit.css" type="text/css" rel="stylesheet" />
	<script src="${pageContext.request.contextPath}/assets/common/js/my97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">

        var _gridWidth = 0;
        var _gridHeight = 0;

        var rowParam ;
        var refush = false ;
        var paramTemp;
        var paramDate;
        var paramCharge;
        var paramAll;

        var areaStr = "" ;
        //页面自适应
        function resizePageSize(){
            _gridWidth = $(document).width()-12;/*  -189 是去掉左侧 菜单的宽度，   -12 是防止浏览器缩小页面 出现滚动条 恢复页面时  折行的问题 */
            _gridHeight = $(document).height()-32-100; /* -32 顶部主菜单高度，   -90 查询条件高度*/
            $("#flexGrid").flexResize();
        }

        $(function() {
            $(window).resize(function(){
                resizePageSize();
            });
            resizePageSize();

            $("#scrq").val(getCurrentDate("yyyy-MM-dd"));
            paramDate = [];
            paramDate.push({"name":"chargeDate","value":$("#scrq").val()});
            AddData();

            $("#deptNameSelect").multiSelect({ "selectAll": false,"noneSelected": "选择病区","oneOrMoreSelected":"*" },function(){
                areaStr = $("#deptNameSelect").selectedValuesString();
                qry();
            });

            $("#control_1").multiSelect({ "selectAll": false,"noneSelected": "收费状态","oneOrMoreSelected":"*" },function(){
                var valuestr = $("#control_1").selectedValuesString();
                var param1 = [] ;
                //alert(param);
                if(valuestr && valuestr.length>0){
                    param1.push({"name":"chargeStateS","value":valuestr});
                    setParamCharge(param1);
                }else{
                    param1 = [] ;
                    setParamCharge(param1);
                }
            });
            var _columnWidth = (parseInt(_gridWidth)-55) / 12;
            $("#flexGrid").flexigrid({
                width : _gridWidth,
                height : _gridHeight,
                url: "${pageContext.request.contextPath}/chargedetails/chargeList",
                dataType : 'json',
                colModel : [
                    {display: 'gid', name : 'gid', width : _columnWidth, sortable : false, align: 'left',hide:'true'},
                    {display: 'parentNo', name : 'parentNo', width : _columnWidth, sortable : false, align: 'left',hide:'true'},
                    {display: 'actOrderNo', name : 'actOrderNo', width : _columnWidth, sortable : false, align: 'left',hide:'true'},
                    {display: 'ydzxzt', name : 'ydzxzt', width : _columnWidth, sortable : false, align: 'left',hide:'true'},
                    {display: 'yzshzt', name : 'yzshzt', width : _columnWidth, sortable : false, align: 'left',hide:'true'},
                    {display: 'freqCode', name : 'freqCode', width : _columnWidth, sortable : false, align: 'left',hide:'true'},
                    {display: 'zxrq', name : 'zxrq', width : _columnWidth, sortable : false, align: 'left',hide:'true'},
                    {display: 'zxsj', name : 'zxsj', width : _columnWidth, sortable : false, align: 'left',hide:'true'},
                    {display: 'zxbc', name : 'zxbcS', width : _columnWidth, sortable : false, align: 'left',hide:'true'},
                    {display: 'pidsj', name : 'pidsj', width : _columnWidth, sortable : false, align: 'left',hide:'true'},

                    {display: '<spring:message code="pivas.yd.pb_name"/>', name : 'pcName', width : _columnWidth, sortable : true, align: 'left'},
                    {display: '<spring:message code="pivas.yzyd.yyrq"/>', name : 'yyrqS', width : _columnWidth, sortable : true, align: 'left'},
                    {display: '<spring:message code="pivas.yz1.wardname"/>', name : 'wardName', width : _columnWidth, sortable : true, align: 'left'},
                    {display: '<spring:message code="pivas.yz2.bedno"/>', name : 'bedno', width : _columnWidth, sortable : true, align: 'left'},
                    {display: '<spring:message code="pivas.yz2.patname"/>', name : 'patname', width : _columnWidth, sortable : true, align: 'left'},
                    {display: '<spring:message code="pivas.yz2.age"/>', name : 'age', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
                            return _row.age==undefined?"":(_row.age+ getDicValue('ageUnit',_row.ageunit) );
                        }},
                    {display: '<spring:message code="pivas.yz1.freqCode"/>', name : 'freqCode', width : _columnWidth, sortable : true, align: 'left'},
                    {display: '<spring:message code="pivas.yz1.drugname"/>', name : 'drugname', width : 250, sortable : true, align: 'left',process: function(v,_trid,_row) {
                            return v.replace(new RegExp("@@","g"),"<br>");
                        }},
                    {display: '<spring:message code="pivas.yz1.dose"/>', name : 'dose', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
                            return v.replace(new RegExp("@@","g"),"<br>");
                        }},
                    {display: '<spring:message code="pivas.yz1.doseUnit"/>', name : 'doseUnit', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
                            return v.replace(new RegExp("@@","g"),"<br>");
                        }},
                    {display: '<spring:message code="pivas.yz1.quantity"/>', name : 'quantity', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
                            return v.replace(new RegExp("@@","g"),"<br>");
                        }},


                    {display: '<spring:message code="pivas.yz4.chargeTime"/>', name : 'chargeLastTime', width : _columnWidth, sortable : true, align: 'left', hide:'true'},
                    {display: '<spring:message code="pivas.yz4.chargeStatus"/>', name : 'chargeStatus', width : _columnWidth, sortable : true, align: 'left', hide:'true', process: function(v,_trid,_row) {
                            return v==0?"收费失败":"收费成功";
                        }}
                ],
                rp: 200, // results per page,每页默认的结果数
                rpOptions: [20, 50, 100, 200],
                resizable : false, //resizable table是否可伸缩
                usepager : true,
                useRp : true,
                usepager : true, //是否分页
                autoload : false, //自动加载，即第一次发起ajax请求
                hideOnSubmit : true, //是否在回调时显示遮盖
                //showcheckbox : true, //是否显示多选框
                rowbinddata : true,
                rowhandler : rowDbclick,
                //onrowchecked : rowChecked,
                numCheckBoxTitle : "<spring:message code='common.selectall'/>"
            });

            function rowDbclick(r) {
                $(r).dblclick(
                    function() {
                        //获取该行的所有列数据
                        var columnsArray = $(r).attr('ch').split("_FG$SP_");
                        rowParam = {"gid":columnsArray[0],"parentNo":columnsArray[2],"zxrq":columnsArray[7],"zxbc":columnsArray[9]};
                        gotoInfo();
                    });
            }

            qry();

            $("#aSearch").bind("click",function(){
                qry();
            });

        });
        function gotoInfo(){
            $.ajax({
                type : 'POST',
                url : '${pageContext.request.contextPath}/chargedetails/chargeInfo',
                dataType : 'html',
                cache : false,
                data : rowParam,
                success : function(data) {
                    refush = false;
                    $("#chargeDetailDiv").remove();
                    $("#yzInfo").html(data).show();
                    $("#yzMain").hide();

                    $("#chargeDetailDiv").dialog({
                        autoOpen: false,
                        height: 370,
                        width: 650,
                        modal: true,
                        resizable: false,
                        buttons: {
                            "返回": function() {
                                $("#chargeDetailDiv").dialog("close");
                            }
                        },
                        close: function() {
                        }
                    });

                }
            });
        }
        function qry(){
            var params = [];
            if(paramAll){
                params = paramAll.concat();
            }
            params.push({"name":"areaS","value":areaStr});
            $('#flexGrid').flexOptions({
                newp: 1,
                extParam: params,
                url: "${pageContext.request.contextPath}/chargedetails/chargeList"
            }).flexReload();
        }

        function setParamCharge(param)
        {
            paramCharge = [];
            if(param!=undefined && param!=null)
            {
                paramCharge = param;
            }
            AddData();
            qry();
        }

        function setParamTemp(param)
        {
            paramTemp = [];
            if(param!=undefined && param!=null)
            {
                paramTemp = param;
            }
            AddData();
            qry();
        }


        function backMain(){
            $("#yzInfo").hide().html("");
            $("#yzMain").show();
            if(refush){
                qry();
            }
        }
        function chargeDetail(costcode){
            $("#chargeDetailDiv tbody tr").hide();
            $(".yd_"+costcode).show();
            $("#chargeDetailDiv").dialog("open");
        }
        function chargeNow(ydPzfId,costcode,charge){
            $.ajax({
                type : 'POST',
                url : '${pageContext.request.contextPath}/chargedetails/changOne',
                dataType : 'json',
                cache : false,
                data : {"ydPzfId":ydPzfId,"costCode":costcode,"charge":charge},
                success : function(response) {
                    if(response==0){
                        gotoInfo();
                        refush= true ;
                        message({html: "收费失败"});
                    }else if(response==1){
                        gotoInfo();
                        refush= true ;
                        message({html: "收费成功"});
                    }else if(response==2){
                        gotoInfo();
                        refush= true ;
                        message({html: "退费失败"});
                    }else if(response==3){
                        gotoInfo();
                        refush= true ;
                        message({html: "退费成功"});
                    }
                }
            });
        }

        function AddData()
        {
            paramAll  = [];
            if(paramTemp != undefined && paramTemp.length > 0)
            {
                paramAll = paramAll.concat(paramTemp);
            }

            if(paramCharge != undefined && paramCharge.length > 0)
            {
                paramAll = paramAll.concat(paramCharge);
            }

            if(paramDate != undefined && paramDate.length > 0)
            {
                paramAll = paramAll.concat(paramDate);
            }

        }

        function dataPick(){

            paramDate = [];
            paramDate.push({"name":"chargeDate","value":$("#scrq").val()});

            AddData();
            qry();
        }

        function clearText(){
            clearclosedinputall();
            paramTemp = [];
            AddData();
            qry();
        }
	</script>



</head>

<body>



<div id="yzMain" class="main-div" style="width:100%">

	<div style="height: 32px;float: left;margin-top: 12px;z-index: 1000000;">
		<label class="tit" id='chargeDateText'>收费日期：</label>
		<input type="text" id="scrq" style="color: #555555;height:26px;width: 100px;" class="Wdate" empty="false" readonly="readonly" onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:dataPick});"/>
	</div>

	<!-- 搜索条件--开始 -->
	<div class="oe_searchview" style="margin-left:30px">

		<div class="oe_searchview_facets" >

			<div class="oe_searchview_input oe_searchview_head"></div>

			<div class="oe_searchview_input"  id="inputsearch" >
				<input id="txt" type="text"  class="oe_search_txt" onkeydown="this.onkeyup();" onkeyup="this.size=(this.value.length>1?this.value.length:1);" size="1" style="max-height: 18px;width:250px;" />
			</div>
		</div>
		<div class="oe_searchview_clear" onclick="clearText();" style="margin-left:180px"></div>
		<button class="oe_searchview_search" type="button" id="searchbtn" style="margin-left:200px">搜索</button>
		<div class="oe-autocomplete" ></div>
		<div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
			<cite>请选择...</cite>
			<ul class="ulQry" style="-webkit-border-radius: 20;" funname="setParamTemp" >
				<li show="床号" name="bednoS" >搜索 床号：<span class="searchVal"></span></li>
				<li show="病人" name="patnameS" >搜索 病人：<span class="searchVal"></span></li>
				<li show="组号" name="parentNoS" >搜索 组号：<span class="searchVal"></span></li>
				<li show="病区" name="wardNameS" >搜索 病区：<span class="searchVal"></span></li>
				<li show="频次" name="freqCodeS" >搜索 频次：<span class="searchVal"></span></li>
				<li show="批次" name="freqZXBCS" >搜索 批次：<span class="searchVal"></span></li>
			</ul>
		</div>

	</div>
	<!-- 搜索条件--结束 -->

	<div id="qryView-div">
		<div class="search-div">
			<div class="oe_view_manager_view_search"></div>
			<span style="margin-left: 10px;">
	    		<select id="control_1" name="control_1[]" multiple="multiple" size="1" style="width:100px;height: 26px;margin-top: -4px;">
						<option value="0">收费失败</option>
						<option value="1">收费成功</option>
						<option value="2">退费失败</option>
						<option value="3">退费成功</option>
				</select>
			</span>
			<select id="deptNameSelect" name="deptNameSelect[]" multiple="multiple" size="5" style="width:100px;">
				<c:forEach items="${inpAreaList}" var="inpAreaList" varStatus="status">
					<option value="${inpAreaList.deptCode}">${inpAreaList.deptName}</option>
				</c:forEach>
			</select>

		</div>
	</div>
	<div class="tbl">
		<table id="flexGrid" style="display: block;margin: 0px;"></table>
	</div>

</div>

<div id="yzInfo" class="main-div" style="width:100%;dispaly:none"></div>



</body>

</html>