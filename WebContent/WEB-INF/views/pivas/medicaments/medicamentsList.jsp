<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>

<head>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
	<style>
		.ui-dialog{
			-webkit-border-radius: 0px;
		}

	</style>
<script>
	var typeParam;
    var datatable;
	var qryParam;
	function editMdcm(id) {
        if (id != "") {
            $.ajax({
                type : 'POST',
                url : '${pageContext.request.contextPath}/mdcm/initUpdMdcm',
                dataType : 'json',
                cache : false,
                data : [ {name : 'medicamentsId',value : id} ],
                success : function(data) {
                    if(data.success == false){
                        qryList();
                        message({data: data});
                    }else{
                        setMyFormValue("editView-div", data);
                        var unitChangeBefore=data.unitChangeBefore;
                        var unitChangeAfter=data.unitChangeAfter;
                        if( typeof(unitChangeBefore) != "undefined" ) {
                            unitChangeBefore=unitChangeBefore.split("&&");
                            $("#medicament_unitchange_before_quantity").val(unitChangeBefore[0]);
                            $("#medicament_unitchange_before_type").text(unitChangeBefore[1]);
                        } else {
                            $("#medicament_unitchange_before_type").text(data.medicamentsDosageUnit);
                        }

                        if( typeof(unitChangeAfter) != "undefined" ) {
                            unitChangeAfter=unitChangeAfter.split("&&");
                            $("#medicament_unitchange_after_quantity").val(unitChangeAfter[0]);
                            $("#medicament_unitchange_after_type").val(unitChangeAfter[1]);
                            $("#medicament_unitchange_after_type").combobox("reset", unitChangeAfter[1]);
                        }

                        $("#editView-div").dialog("option","title","修改药品");
                        $("#editView-div").dialog("open");
                    }
                }
            });
        } else {
            message({html: '<spring:message code="common.plzSelectOneRecourd"/>',showConfirm: true});
        }
    }

    function setMdcmCategory(id) {
        if (id != "") {
            typeParam = [];
            var param = {name:"ids",value:id};
            typeParam.push(param);
            $("#editView-med").dialog("open");
        }
        else{
            message({
                html: '<spring:message code="common.plzSelectOneOrMoreRecourd"/>',
                showConfirm: true
            });
        }
    }

    function initDatatable() {
        datatable = $('.datatable').DataTable({
            "dom": '<"toolbar">frtip',
            "searching": false,
            "processing": true,
            "serverSide": true,
            "select": true,
            "ordering": false,
            "pageLength": 20,
            "language": {
                "url": "${pageContext.request.contextPath}/assets/datatables/cn.json"
            },
            "preDrawCallback": function () {
                //sublime.showLoadingbar($(".main-content"));
            },
            "drawCallback": function () {
                //sublime.closeLoadingbar($(".main-content"));
            },
            "ajax": {
                "url": "${pageContext.request.contextPath}/mdcm/mdcmslist",
                "type": "post",
                "data": function (d) {
                    var params = [];
                    if (qryParam) {
                        params = qryParam.concat();
                    }
                    for (var index in params) {
                        d[params[index].name] = params[index].value;
                    }

                    d.rp = d.length;
                    d.page = d.start / d.length + 1;
                },
                "dataSrc": function (data) {
                    data.data = data.rawRecords;
                    //data.draw = 1;
                    data.recordsFiltered = data.total;
                    data.recordsTotal = data.total;
                    return data.data;
                }
            },
            "columns": [
                {"data": "medicamentsCode", "bSortable": false},
                {"data": "medicamentsName", "bSortable": false},
                {"data": "categoryName", "bSortable": false},
                {"data": "phyFunctiy", "bSortable": false},
                {"data": "medicamentsSpecifications", "bSortable": false},
                {"data": "medicamentsDosage", "bSortable": false},
                {"data": "medicamentsSuchama", "bSortable": false},
                {"data": "medicamentsPackingUnit", "bSortable": false},
                {"data": "medicamentsMenstruum", "bSortable": false},
                {"data": "medicamentsPlace", "bSortable": false},
                {"data": "labelNames", "bSortable": false},
                {"data": "medicamentsDanger", "bSortable": false},
                {"data": "medicamentsId", "bSortable": false}
            ],
            columnDefs: [
                {
                    targets: 12,
                    render: function (data, type, row) {
                        <shiro:hasPermission name="PIVAS_BTN_223">
                        return "<a class='button btn-bg-green' href='javascript:setMdcmCategory(" + data + ");'>分类</a> <a class='button btn-bg-green' href='javascript:editMdcm(" + data + ");'><spring:message code='common.edit'/></a>";
                        </shiro:hasPermission>
                        <shiro:lacksPermission name="PIVAS_BTN_223">
                        return "";
                        </shiro:lacksPermission>
                    }
                }
            ]
        });
    }

	$(function() {
		$("#editView-form input[readonly='readonly'][name!='labelNames']").addClass("gray");
		$("#editView-form select").combobox();

		initDatatable();

		$('#editView-form').ajaxForm({
            dataType: "json",
            success : function(data){
            	if(data.success || data.code == '<%=ExceptionCodeConstants.RECORD_DELETE%>') {
            		$('#editView-div').dialog('close');
		    	}

                qryList();

            	<%-- 弹出提示信息 --%>
		    	message({
	    			data: data
            	});
            }
    	});

		$("#editView-div").dialog({
			autoOpen: false,
			height: 550,
			width: 800,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					var bValid = validateInput("editView-form")==null?true:false;

					if (!bValid ) {
						return ;
					}
					var url = "${pageContext.request.contextPath}/mdcm/addMdcm";
					var id = $("#editView-form").find("input[name='medicamentsId']").val();
					if(id && id!=""){
						url = "${pageContext.request.contextPath}/mdcm/updMdctse";
					}

					var beforeQuantity=$("#medicament_unitchange_before_quantity").val();
					var beforeType=$("#medicament_unitchange_before_type").text();
					var afterQuantity=$("#medicament_unitchange_after_quantity").val();
					var afterType=$("#medicament_unitchange_after_type").val();
					if(beforeQuantity.length !=0){
						if(afterQuantity.length == 0){
							message({html: "请输入转换后的数量",showConfirm: true});
							return;
						}
					}

					if(afterQuantity.length != 0){
						if(beforeQuantity.length ==0){
							message({html: "请输入转换前的数量",showConfirm: true});
							return;
						}
					}
					if(beforeQuantity.length !=0 && afterQuantity.length != 0){
						$("#unitChangeBefore").val(beforeQuantity+"&&"+beforeType);
						$("#unitChangeAfter").val(afterQuantity+"&&"+afterType);
					}
					$("#editView-form").attr("action", url);
					$("#editView-form").submit();
				},
				"<spring:message code='common.cancel'/>": function() {
					$(this).dialog("close");
				}
			},
			close: function() {
			}
		});

		$("#mediclLabel-div").dialog({
			autoOpen: false,
			height: 340,
			width: 380,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					var ids = getFlexiGridSelectedRowText($("#labelFlexGrid"), 2);
					var names = getFlexiGridSelectedRowText($("#labelFlexGrid"), 3);
					$("#editView-form input[name='labelIds']").val(ids);
					$("#editView-form input[name='labelNames']").val(names);

					$(this).dialog("close");
				},
				"<spring:message code='common.cancel'/>": function() {
					$(this).dialog("close");
				}
			},
			open:function(){
				loadLabelGrid();
			},
			close: function() {
			}
		});

		$("#editView-form input[name='labelNames']").click(function(){
			$("#mediclLabel-div").dialog("open");
		});

        <%-- 同步按钮 --%>
        $("#syncDataDicRoleBtn").bind("click", function () {
            layer.confirm('<spring:message code="common.startSynTask"/>', {
                btn: ['确定', '取消'], icon: 3, title: '提示'
            }, function () {
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/mdcm/synMdcms',
                    dataType: 'json',
                    cache: false,
                    data: [],
                    success: function (data) {
                        qryList();
                        layer.alert(data.description, {'title': '操作提示', icon: 0});
                    },
                    error: function () {
                        layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
                    }
                });
            });

        });

		$("#editView-med").dialog({
			autoOpen: false,
			height: 200,
			width: 350,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {

					var type = $("#ypSelect").val();
					if(type == ""){
						message({html:"请选择药品分类"});
					}
					typeParam.push({name:"type",value:type});

					$.ajax({
						type : 'POST',
						url : '${pageContext.request.contextPath}/mdcm/saveCategory',
						dataType : 'json',
						cache : false,
						//async : true,
						data : typeParam,
						success : function(data) {

							if(data.code == -1){
								message({html:data.mess});
							}

							$("#editView-med").dialog("close");
							qryList(qryParam);
						},
						error : function(data){
							message({html:'<spring:message code="common.op.error"/>'});
						}
					});

				},
				"<spring:message code='common.cancel'/>": function() {
					$("#editView-med").dialog("close");
				}
			},
			open:function(){
			},
			close: function() {
			}
		});
	});


	function setMyFormValue(formId, data){
		if($.type(formId) === "string"){
            <%-- 获取表单对象,以.开头的为class选择器 --%>
			if(formId.indexOf(".") == 0){
				var $form = $(formId);
			}else{
				var $form = $('#'+formId);
			}
		}else{
			var $form = formId;
		}
        <%-- 设置input的值 --%>
		$form.find("input").each(function(){
			var name = $(this).attr("name");
			var val = data[name];
			if($.isArray(val)){
				val = val.join(",");
			}
			$(this).val(sdfun.fn.htmlDecode(val));
		});
        <%-- 设置select的值 --%>
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

	function process(v){
		if(v == 0){
			return "<spring:message code="common.yes"/>";
		}else if(v == 1){
			return "<spring:message code="common.no"/>";
		}
	}

	function qryList(param){
        qryParam = param;
        datatable.ajax.reload();
	}

	function loadLabelGrid(){
		$("#labelFlexGrid").flexigrid({
			width : 400,
			height : 200,
			dataType : 'json',
			colModel : [
				{display: 'labelId', name : 'labelId', width : 50,hide:'true'},
				{display: 'labelName', name : 'labelName', width : 50,hide:'true'},
				{display: '<spring:message code="medic.labelName"/>', name : 'labelName', width : 200},
				{display: '是否空包', name : 'isNull', width : 100,process:function(v){
					if(v == "0"){
						return "<spring:message code="common.no"/>";
					}else if(v == "1"){
						return "<spring:message code="common.yes"/>";
					}
				}}
			],
			resizable : false, <%-- resizable table是否可伸缩 --%>
			usepager : true,
			useRp : true,
			usepager : false, <%-- 是否分页 --%>
			autoload : false, <%-- 自动加载，即第一次发起ajax请求 --%>
			hideOnSubmit : true, <%-- 是否在回调时显示遮盖 --%>
			showcheckbox : true, <%-- 是否显示多选框 --%>
			rowbinddata : true,
			numCheckBoxTitle : "<spring:message code='common.selectall'/>",
			gridClass: "bbit-grid"
		});

		$('#labelFlexGrid').flexOptions({
			newp: 1,
			url: "${pageContext.request.contextPath}/mdcm/queryMdcLbl"
        }).flexReload();
	}
</script>
</head>
<body>

<div class="main-div " style="width:100%">
	<%-- 搜索条件--开始 --%>
	<div class="ui-search-header ui-search-box" data-qryMethod funname="qryList" id="qryView-div" style="display: inline;">
		<div style="float: right; ">
		分类：<input name="categoryNames" size="8" data-cnd="true">&nbsp;&nbsp;
		名称：<input name="medicamentsNames" size="8" data-cnd="true">&nbsp;&nbsp;
		编码：<input name="medicamentsCodes" size="8" data-cnd="true">&nbsp;&nbsp;
		产地：<input name="medicamentsPlaces" size="8" data-cnd="true">&nbsp;&nbsp;
		速查码：<input name="medicamentsSuchamas" size="8" data-cnd="true">&nbsp;&nbsp;
		药理：<input name="phyFunctiys" size="8" data-cnd="true">&nbsp;&nbsp;
		<button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
		<button  class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
		</div>
		<shiro:hasPermission name="PIVAS_BTN_224">
			<a class="ui-search-btn ui-btn-bg-green" id="syncDataDicRoleBtn"><i class="am-icon-refresh"></i><span>同步</span></a></a>
		</shiro:hasPermission>
	</div>
	<%-- 搜索条件--结束 --%>
	<table class="table datatable ui-data-table display dataTable no-footer">
		<thead>
		<tr>
			<th>编码</th>
			<th>名称</th>
			<th>分类</th>
			<th>药理作用</th>
			<th><spring:message code="medicaments.medicamentsSpecifications"/></th>
			<th><spring:message code="medicaments.medicamentsDosage"/></th>
			<th><spring:message code="medicaments.medicamentsSuchama"/></th>
			<th><spring:message code="medicaments.medicamentsPackingUnit"/></th>
			<th><spring:message code="medicaments.medicamentsMenstruum"/></th>
			<th>产地</th>
			<th>标签</th>
			<th>是否高危</th>
			<th>操作</th>
		</tr>
		</thead>
	</table>
</div>

<div id="editView-div" title="<spring:message code='common.add'/>" align="center" style="display: none;">
	<form id="editView-form" action="" method="post">
		<input type="hidden" id="medicamentsId" name="medicamentsId"/>
	    <div class="popup">
	    	<div class="row" style="min-height:32px;">
				<div class="column columnL">
					<label class="tit"><spring:message code='medicaments.medicamentsName'/></label>
					<input type="text" name="medicamentsName" readonly="readonly" />
				</div>
				<div class="column columnR">
					<label class="tit"><spring:message code='medicaments.medicamentsCode'/></label>
					<input type="text" name="medicamentsCode" readonly="readonly" />
				</div>
			</div>
	    	<div class="row" style="min-height:32px;">
				<div class="column columnL">
					<label class="tit"><spring:message code='medicaments.categoryId'/></label>
					<select name="categoryId" empty="false" readonly>
						<option value=""><spring:message code="common.select"/></option>
						<c:forEach items="${medicCategoryList}" var="item">
							<option value="${item.categoryId}"><spring:escapeBody htmlEscape='true'>${item.categoryName}</spring:escapeBody></option>
						</c:forEach>
					</select>
					<span class="mand">*</span>
				</div>
				<div class="column columnR">
					<label class="tit"><spring:message code='medicaments.medicamentsSuchama'/></label>
					<input type="text" name="medicamentsSuchama" readonly="readonly" />
				</div>
			</div>
			<div class="row" style="min-height:32px;">
				<div class="column columnL" >
					<label class="tit">药品别名</label>
					<input type="text"  name="aliasName" empty="false" title="请输入药品别名,最多为256位字符" maxlength="256"/>
					<span class="mand">*</span>
				</div>
				<div class="column columnR">
					<label class="tit">药理用作</label>
					<input type="text" name="phyFunctiy" readonly="readonly" />
				</div>
			</div>
	    	<div class="row" style="min-height:32px;">
				<div class="column columnL">
					<label class="tit"><spring:message code='medicaments.medicamentsSpecifications'/></label>
					<input type="text" name="medicamentsSpecifications" readonly="readonly" />
				</div>
				<div class="column columnR">
					<label class="tit"><spring:message code='medicaments.medicamentsVolume'/></label>
					<input type="text" name="medicamentsVolume" readonly="readonly" />
				</div>
			</div>
	    	<div class="row" style="min-height:32px;">
				<div class="column columnL">
					<label class="tit"><spring:message code='medicaments.medicamentsDosage'/></label>
					<input type="text" name="medicamentsDosage" readonly="readonly" />
				</div>
				<div class="column columnR">
					<label class="tit"><spring:message code='medicaments.medicamentsVolumeUnit'/></label>
					<input type="text" name="medicamentsVolumeUnit" readonly="readonly" />
				</div>
			</div>
	    	<div class="row" style="min-height:32px;">
				<div class="column columnL">
					<label class="tit"><spring:message code='medicaments.medicamentsDosageUnit'/></label>
					<input type="text" name="medicamentsDosageUnit" readonly="readonly" />
				</div>
				<div class="column columnR">
					<label class="tit"><spring:message code='medicaments.medicamentsPackingUnit'/></label>
					<input type="text" name="medicamentsPackingUnit" readonly="readonly" />
				</div>
			</div>

	    	<div class="row" style="min-height:32px;">
				<div class="column columnL">
					<label class="tit"><spring:message code='medicaments.medicamentsMenstruum'/></label>
					<select name="medicamentsMenstruum" readonly>
						<option value=""><spring:message code="common.select"/></option>
	            		<option value="0"><spring:message code="common.no"/></option>
	            		<option value="1"><spring:message code="common.yes"/></option>
					</select>
				</div>
				<div class="column columnR">
					<label class="tit"><spring:message code='medicaments.medicamentsIsmaindrug'/></label>
					<select name="medicamentsIsmaindrug" readonly>
						<option value=""><spring:message code="common.select"/></option>
	            		<option value="0"><spring:message code="common.yes"/></option>
	            		<option value="1"><spring:message code="common.no"/></option>
					</select>
				</div>
			</div>

	    	<div class="row" style="min-height:32px;display: none">
				<div class="column columnL" style="display: none;">
					<label class="tit"><spring:message code='medicaments.medicamentsTestFlag'/></label>
					<select name="medicamentsTestFlag" readonly >
						<option value=""><spring:message code="common.select"/></option>
	            		<option value="0"><spring:message code="common.yes"/></option>
	            		<option value="1"><spring:message code="common.no"/></option>
					</select>
					<span class="mand">*</span>
				</div>
				<div class="column columnR" style="display: none;">
					<label class="tit"><spring:message code='medicaments.medicamentsIsactive'/></label>
					<select name="medicamentsIsactive" readonly empty="false">
						<option value=""><spring:message code="common.select"/></option>
	            		<option value="0"><spring:message code="common.no"/></option>
	            		<option value="1"><spring:message code="common.yes"/></option>
					</select>
					<span class="mand">*</span>
				</div>
			</div>
	    	<div class="row" style="min-height:32px;">
				<div class="column columnL">
					<label class="tit"><spring:message code='medicaments.medicamentsPlace'/></label>
					<input type="text" name="medicamentsPlace" maxlength="32" readonly="readonly"/>
				</div>
				<div class="column columnR">
					<label class="tit"><spring:message code='medicaments.medicamentsPrice'/>(元)</label>
					<input type="text" name="medicamentsPrice" maxlength="32"  empty="false"  readonly="readonly"
						title="<spring:message code='common.plzInputZNumber0'/>"/>
					<span class="mand">*</span>
				</div>
			</div>
			<div class="row" style="min-height:32px;">
				<div class="column columnL" >
					<label class="tit">是否高危药品</label>
					<select name="medicamentsDanger" readonly >
						<option value=""><spring:message code="common.select"/></option>
	            		<option value="0"><spring:message code="common.yes"/></option>
	            		<option value="1"><spring:message code="common.no"/></option>
					</select>
				</div>
				<div class="column columnR">
					<label class="tit">药品标签</label>
					<input type="hidden" name="labelIds" />
					<input type="text" name="labelNames" readonly="readonly" class="hand"/>
				</div>
			</div>

			<div class="row" style="min-height:32px;">
				<div class="column columnL" >
					<label class="tit">有效期</label>
					<input type="text" id="effective_date" name="effective_date"
					style="height: 25px;color: #555555;width: 184px;" class="Wdate" empty="true"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
				</div>
				<div class="column columnR">
					<label class="tit">配置难度系数</label>
					<input type="text" name="difficulty_degree" id="difficulty_degree" maxlength="8"
					title="<spring:message code='common.plzInputZNumber0'/>" regex="number"/>
				</div>
			</div>

			<div class="row" style="min-height:32px;">
				<div class="column columnL" >
					<label class="tit">存放货架号</label>
					<input type="text" name="shelfNo"/>
				</div>
			</div>

			<div class="row" style="min-height:32px;">
				<div style="background:#ff0000;width:100%;" >
					<input type="hidden" id="unitChangeBefore"  name="unitChangeBefore" />
					<input type="hidden" id="unitChangeAfter"  name="unitChangeAfter" />
					<div style="float:left;width:14.5%;text-align:right;margin-top: 3px">
						<span>单位换算</span>
					</div>
					<div class="column" style="float:left;width:50px;margin-left: 7px">
						<input id="medicament_unitchange_before_quantity" type="text" style="width:50px;border:1px solid #dadada"/>
					</div>
					<div class="column" style="float:left;width:32px;margin-top: 3px">
						<span id="medicament_unitchange_before_type" style="margin-top: 3px" >毫克</span>
						<span>=</span>
					</div>
					<div class="column" style="float:left;width:145px;margin-left: 6px">
						<input id="medicament_unitchange_after_quantity" type="text" style="width:50px;border:1px solid #dadada" />
						<select id="medicament_unitchange_after_type" style="width:80px;" readonly>
							<option val="ml">ml</option>
							<option val="mg">mg</option>
							<option val="g">g</option>
							<option val="l">l</option>
						</select>
					</div>
				</div>
			</div>
	    </div>
	</form>
</div>

<div id="editView-med" title="选择药品分类" align="center" style="display: none;">
	<form id="editView-formMed" method="post">
		<div class="popup">
			<div class="row" style="float:left">
				<div class="column" style="width:auto;">
					<label class="tit" style="width:58px;" >药品分类</label>
					<select id="ypSelect" style="width:200px;" >
						<option value="" selected >请选择</option>
						<c:forEach items="${medicList}" var="medicList">
						<option value="${medicList.categoryId}">${medicList.categoryName}</option>
						</c:forEach>
					</select>
	            	<span class="mand">*</span>
				</div>
			</div>
		</div>
	</form>
</div>

<div id="mediclLabel-div" title="<spring:message code="common.choose"/>" style="display: none;">
	<table id="labelFlexGrid" style="display: block;margin: 0px;"></table>
</div>

</body>

</html>