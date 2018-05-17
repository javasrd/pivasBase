<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page
	import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp"%>

<html>
<link
	href="${pageContext.request.contextPath}/assets/sysCss/common/style.css"
	type="text/css" rel="stylesheet" />
<head>
<script type="text/javascript">
    var _gridWidth = 0;
    var _gridHeight = 0;
    var text = ""; 
    var json = "";
    //页面自适应
    function resizePageSize(){
        _gridWidth = $(document).width()-12;/*  -189 是去掉左侧 菜单的宽度，   -12 是防止浏览器缩小页面 出现滚动条 恢复页面时  折行的问题 */
        _gridHeight = $(document).height()-32-100; /* -32 顶部主菜单高度，   -90 查询条件高度*/
        //$("#flexGridRole").flexResize();
    }
    
    $(function() {
        $(window).resize(function(){
            resizePageSize();
        });
        resizePageSize();
        
          $("#printQR").bind("click",function(){
           $("#jsoncode").val(json);
           $("#print-form").submit();
          });

        $("#barcodecreate").bind("click",function(){
        $("#barcode").empty();
       var user = $("#account").val();
       var pwd = $("#password").val();

      if((user == "") || (pwd == ""))
      {
           message({
            data:{success:false,description:"用户名或者密码不能为空"}
            });
            return;
      }

       pwd = des("123456abcde@lx100$#365#$",pwd,1,1,"01234567",1);
       pwd = stringToHex(pwd);
       var b = new Base64();  
       var str = b.encode(pwd);  

       json = "{\"User\":\"" + user + "\",\"Pwd\":\""+ str + "\"}";
       
       $("#userdata").val(json);
       $("#check-form").submit();
       

        });
          
//修改建筑Form的ajax交互
              
        $("#zoneInput").height(_gridHeight/2);
        $("#barcodeZone").height(_gridHeight/1.5);
        $("#barcode").height(_gridWidth/5);
        $("#barcode").width(_gridWidth/5);
        $("#account").width(_gridWidth/6);
        $("#password").width(_gridWidth/6);
     });
     
     //修改建筑Form的ajax交互
$("#print-form").ajaxForm({
    dataType: "json",
    success : function(data){
        if(data.success){
            window.open("${pageContext.request.contextPath}/printQRCode/download/<shiro:principal property="account"/>/"+data.msg);
        }else{
            message({
                data: data
            });
        }
    }
});


$("#check-form").ajaxForm({
    dataType: "json",
    success : function(data){
        if(data.success){
                 $("#barcode").qrcode({ 
                    render: "table", //table方式 
                    width: $("#barcode").width(), //宽度 
                    height:$("#barcode").height(), //高度 
                    text: $("#userdata").val(),
                    
                    
                     //任意内容
                    correctLevel:0 
        }); 
        
        $("#barcodeZone").css("visibility","visible");
        }else{
            message({
                data: data
            });
        }
    }
});
     
</script>
</head>
<body>
	<div id="zoneInput"
		style="width:70%">


		<div class="popup">
			<div class="row">
				<div class="column" style="margin-left:10%;margin-top:1%">
					<!-- 操作员 -->
					<label class="tit">账户</label>
					<input id="account" /> <span class="mand">*</span>
				</div>
			</div>
		</div>

		<div class="popup">
			<div class="row">
				<div class="column" style="margin-left:10%;margin-top:1%">
					<!-- 操作员 -->
					<label class="tit">密码</label>
					<input id="password" type="password" /> <span class="mand">*</span>
				</div>
			</div>
		</div>

		<div class="btns" style="    margin-top: 4%;margin-left: 413px;">
			<!-- 查询\重置按钮 -->
			<form id="check-form"
				action="${pageContext.request.contextPath}/printQRCode/checkData"
				method="post">
				<input id="userdata" name="userdata" style="display:none;" /> <a
					class="button" id="barcodecreate" style="padding: 10px 135px;">生成二维码</a>
			</form>
		</div>
	</div>

	<div
		style="border-style:solid;visibility:hidden;border-width:1px;border-color:#C2CCD0;margin-top:2%;width:70%"
		class="main-div" id="barcodeZone">

		<div id="barcodeWrap" style="width:100%;text-align:center;">
			<div id="barcode" style="margin-left:35%;"></div>
		</div>

		<div style="margin-left:43%;margin-top:2%" id="buttonZone">

			<form id="print-form"
				action="${pageContext.request.contextPath}/printQRCode/print"
				method="post">
				<input id="jsoncode" name="json" style="display:none" /> <a
					class="button" id="printQR">打印</a>
			</form>

		</div>
	</div>

</body>