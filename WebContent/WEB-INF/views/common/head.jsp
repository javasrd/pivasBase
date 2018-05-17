<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 


		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />


        <meta name="description" content="描述">
    	<meta name="author" content="作者">
    
    	<script src="${contextPath}/assets/common/bower_components/jquery/jquery.min.js"></script>
		<link id="bs-css" href="${contextPath}/assets/common/bower_components/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
		
    	<link href="${contextPath}/assets/common/css/charisma-app.css" rel="stylesheet">
    
    	<link href="${contextPath}/assets/common/css/animate.min.css" rel='stylesheet'>

        <!-- The fav icon -->
   		<link rel="shortcut icon" href="${contextPath}/assets/common/img/favicon.ico">
   		

		<script src="${contextPath}/assets/common/bower_components/bootstrap/dist/js/bootstrap.js"></script>
	    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
	    <!--[if lt IE 9]>
	    <script src="${contextPath}/assets/common/js/html5.js"></script>
	    <![endif]-->
  		
  		<link rel="stylesheet" href="${contextPath}/assets/jquery-ui-1.11.4/jquery-ui.css">
  		<link rel="stylesheet" href="${contextPath}/assets/jquery-ui-1.11.4/jquery-ui.theme.min.css">
  		<script src="${contextPath}/assets/jquery-ui-1.11.4/jquery-ui.js"></script>
        <script type="text/javascript">
            var bLanguage = '${sessionScope.language}' === 'zh' ? 'zh-cn' : 'en';
        </script>
  		<script src="${contextPath}/assets/common/js/my97DatePicker/WdatePicker.js" type="text/javascript"></script>
  		
	    <!-- 公共 接送处理 -->
		<script src="${contextPath}/assets/common/js/json2.js"></script>

        <script src="${contextPath}/assets/layer/layer.js" type="text/javascript"></script>
	    <!-- 下拉框     没效果
	    <link href="${contextPath}/assets/common/bower_components/chosen/chosen.min.css" rel='stylesheet'>
	    <script src="${contextPath}/assets/common/bower_components/chosen/chosen.jquery.min.js"></script>
	    -->
	    
	    
	    <!-- mmGrid列表控件 -->
	    <style type="text/css" >
			.mmGrid {
                font-family: 'Helvetica Neue',helvetica, "Hiragino Sans GB",'Microsoft YaHei', "WenQuanYi Micro Hei", sans-serif;
                font-size: 14px;
                color: #444;
            }
            .mmGrid table tr{
            	background: -webkit-linear-gradient(top, rgba(255,255,255,1) 1%,rgba(243,243,243,1) 100%);
            }
            .pageRight{
            	text-align: right;
            }
            
            /*弹窗 新增修改 输入框等样式*/
            .inputF .formTit{
            	width: 20%;
    			text-align: right;
    			padding-right: 15px;
            }
            .inputF .formInp{
            	width:50%;
            }
            .inputF .formChk{
            	 
            }
            .inputF .formDesc{
            	padding-left: 10px;
            	color: darkgrey;
            }
            .inputF .corRed{
            	color: red;
            }
            
		</style>
		<link href="${contextPath}/assets/common/mmGrid/mmGrid.css" rel='stylesheet'>
		<link href="${contextPath}/assets/common/mmGrid/mmPaginator.css" rel='stylesheet'>
		<link href="${contextPath}/assets/common/mmGrid/theme/bootstrap/mmGrid-bootstrap.css" rel='stylesheet'>
		<link href="${contextPath}/assets/common/mmGrid/theme/bootstrap/mmPaginator-bootstrap.css" rel='stylesheet'>
		<link href="${contextPath}/assets/common/mmGrid/theme/bootstrap/mmPaginator-bootstrap.css" rel='stylesheet'>
		<link href="${contextPath}/assets/jqwidgets/css/jqx.base.css" rel='stylesheet'>
		      
		<script src="${contextPath}/assets/common/mmGrid/mmGrid.js"></script>
		<script src="${contextPath}/assets/common/mmGrid/mmPaginator.js"></script>
		<script src="${contextPath}/assets/jqwidgets/js/jqxcore.js"></script>
		<script src="${contextPath}/assets/jqwidgets/js/jqxmenu.js"></script>
		
	    
	    
        <script type="text/javascript">
        
    	var contextPath = "${contextPath}" ;
        
        $(document).ready(function () {
			
        	//左侧菜单事件--开始---------------------------
            $('.navbar-toggle').click(function (e) {
                e.preventDefault();
                $('.nav-sys').html($('.navbar-collapse').html());
                $('.sidebar-nav').toggleClass('active');
                $(this).toggleClass('active');
            });

            var $sidebarNav = $('.sidebar-nav');

            // Hide responsive navbar on clicking outside
            $(document).mouseup(function (e) {
                if (!$sidebarNav.is(e.target) // if the target of the click isn't the container...
                    && $sidebarNav.has(e.target).length === 0
                    && !$('.navbar-toggle').is(e.target)
                    && $('.navbar-toggle').has(e.target).length === 0
                    && $sidebarNav.hasClass('active')
                    )// ... nor a descendant of the container
                {
                    e.stopPropagation();
                    $('.navbar-toggle').click();
                }
            });
          //左侧菜单事件--结束---------------------------
            
            
            //菜单栏上的关闭按钮
            $('.btn-close').click(function (e) {
                e.preventDefault();
                $(this).parent().parent().parent().fadeOut();
            });
          	//菜单栏上的最小化按钮
            $('.btn-minimize').click(function (e) {
                e.preventDefault();
                var $target = $(this).parent().parent().next('.box-content');
                if ($target.is(':visible')) $('i', $(this)).removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
                else                       $('i', $(this)).removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
                $target.slideToggle();
            });
          	
          	//临时 设置按钮
            $('.btn-setting').click(function (e) {
                e.preventDefault();
                $('#myModal').modal();//$('#myModal').modal('show');
            });
          	
          	
          	$("#myAlertDiv .btOK").bind("click",function(){
          		$("#myAlertDiv").modal('hide');
          		$(".modal-backdrop").remove();
          		if(alertOKFunObj){
          			alertOKFunObj.apply();
          		}
          	});
			$("#myAlertDiv .btNO").bind("click",function(){
				$("#myAlertDiv").modal('hide');
				$(".modal-backdrop").remove();
				if(alertNOFunObj){
					alertNOFunObj.apply();
          		}
          	});

          	
          	$("#myAlertDiv2 .btOK").bind("click",function(){
          		$("#myAlertDiv2").modal('hide');
          		$(".modal-backdrop").remove();
          		if(alertOKFunObj2){
          			alertOKFunObj2.apply();
          		}
          	});
			$("#myAlertDiv2 .btNO").bind("click",function(){
				$("#myAlertDiv2").modal('hide');
				$(".modal-backdrop").remove();
				if(alertNOFunObj2){
					alertNOFunObj2.apply();
          		}
          	});
          	
        });
        
       	var alertOKFunObj = null ;
       	var alertNOFunObj = null ;

       	var alertOKFunObj2 = null ;
       	var alertNOFunObj2 = null ;
       	
        /*
        function myAlert(mess){
            $('#myAlertMess').html(mess);
            var options = {} ;
            options.remote = true ;
            $('#myAlertDiv').modal('show',options);
        }
        */
        (function($){
        	var _myAlert = function(mess,option){
        		if(!(!!mess)) {
        			return ;
        		}
        		alertOKFunObj = null ;
        		alertNOFunObj = null ;
        		if(option && option.cancleShow==true){
        			$("#myAlertDiv .btNO").css("display","");
        			if(option && option.cancle && $.isFunction(option.cancle)) {
        				alertNOFunObj = option.cancle;
    				}
        		}else{
        			$("#myAlertDiv .btNO").css("display","none");
        		}
        		if(option && option.config && $.isFunction(option.config)) {
        			alertOKFunObj = option.config;
        		}
        		$('#myAlertMess').html(mess);
                $('#myAlertDiv').modal({"backdrop":"static"});
        	};
        	var _myAlert2 = function(mess,option){
        		if(!(!!mess)) {
        			return ;
        		}
        		alertOKFunObj2 = null ;
        		alertNOFunObj2 = null ;
        		if(option && option.cancleShow==true){
        			$("#myAlertDiv2 .btNO").css("display","");
        			if(option && option.cancle && $.isFunction(option.cancle)) {
        				alertNOFunObj2 = option.cancle;
    				}
        		}else{
        			$("#myAlertDiv2 .btNO").css("display","none");
        		}
        		if(option && option.config && $.isFunction(option.config)) {
        			alertOKFunObj2 = option.config;
        		}
        		$('#myAlertMess2').html(mess);
                $('#myAlertDiv2').modal({"backdrop":"static"});
        	};
			var _myAjax = function(url,params,serOption,typeMap){
        		var resultData = null ;
        		if(!(!!url)) {
        			alert("请求url为空，无法请求！");
        			return ;
        		}
        		if($.isFunction(serOption.before)) {
        			serOption.before.apply(this);
        		}
        		var v_contentType = "application/x-www-form-urlencoded;charset=UTF-8" ; //发送到服务器的编码
        		var v_data = params ;
        		if(typeMap.contentType=="json"){
        			v_contentType = "application/json;charset=UTF-8" ;
        			v_data = JSON.stringify(params) ;
        			//if(typeMap.type=="GET"){
        			//	v_data = JSON.parse(v_data);
        			//}
        		}
        		var v_type = nullDeal(typeMap.type,"POST") ;
        		var v_async = nullDeal(serOption.async,true) ;
        		var v_dataType = nullDeal(typeMap.dataType,"json") ;
        		var v_cache = nullDeal(serOption.cache,true);
        		var v_timeout = nullDeal(serOption.timeout,60000);
        		
        		$.ajax({
        			url: url,
        			data: v_data,
        			
        			type: v_type,
        			contentType: v_contentType,
        			
        			async: v_async,
        			dataType: v_dataType,
        			cache: v_cache,
        			timeout: v_timeout,
        			success: function(result, textStatus) {
        				resultData = result ;
        				if($.isFunction(serOption.success)) {
        					serOption.success.apply(this,[result]);
        				}
        		    },
        			complete: function(XMLHttpRequest,textStatus) {
        		    },
        		    error:function(XMLHttpRequest, textStatus, errorThrown) {
        		    	var errMess = "" ;
    		    		if(XMLHttpRequest.responseText){
    		    			errMess = XMLHttpRequest.responseText;
    		    		}
        		    	if(serOption.errorShow && serOption.errorShow==false){
        		    		
        		    	}else{
        		    		//$.alert("服务异常，请联系管理员 "+errMess);
        		    	}
        		    	if($.isFunction(serOption.error)) {
        					serOption.error.apply(this,[errMess]);
        				}
        		    }
        		}).always(function(){
        			if($.isFunction(serOption.always)) {
        				serOption.always.apply(this);
        			}
        		});
        		
        		if(serOption.async==false){
        			if($.isFunction(serOption.asyncDo)) {
        				serOption.asyncDo.apply(this);
        			}
        		}else{
        			return resultData;
        		}
        	};
        	
        	$.extend($, {
        		alert: function(mess,option){
        			return _myAlert(mess,option);
        		},
        		alert2: function(mess,option){
        			return _myAlert2(mess,option);
        		},
        		ajaxJPostJson: function(url,params,serOption){
        			return _myAjax(url,params,serOption,{"dataType":"json","type":"POST","contentType":"json"});
        		},
        		ajaxJPostHtml: function(url,params,serOption){
        			return _myAjax(url,params,serOption,{"dataType":"html","type":"POST","contentType":"json"});
        		},
        		ajaxJGetjson: function(url,params,serOption){
        			return _myAjax(url,params,serOption,{"dataType":"json","type":"GET","contentType":"json"});
        		},
        		ajaxJGetHtml:function(url,params,serOption){
        			return _myAjax(url,params,serOption,{"dataType":"html","type":"GET","contentType":"json"});
        		},
        		ajaxFPostJson: function(url,params,serOption){
        			return _myAjax(url,params,serOption,{"dataType":"json","type":"POST","contentType":"form"});
        		},
        		ajaxFPostHtml: function(url,params,serOption){
        			return _myAjax(url,params,serOption,{"dataType":"html","type":"POST","contentType":"form"});
        		},
        		ajaxFGetjson: function(url,params,serOption){
        			return _myAjax(url,params,serOption,{"dataType":"json","type":"GET","contentType":"form"});
        		},
        		ajaxFGetHtml:function(url,params,serOption){
        			return _myAjax(url,params,serOption,{"dataType":"html","type":"GET","contentType":"form"});
        		},
        		ajaxHPostHtml:function(url,params,serOption){
        			return _myAjax(url,params,serOption,{"dataType":"html","type":"Post","contentType":"html"});
        		}
        		
        	});
        })(jQuery,this);
        
        
        /*mmGrid 列表用来校验是否数字，并精确到后两位小数*/
    	function fixed2(val){
            if(typeof val != 'number'){
                return '';
            }
            return val.toFixed(2);
        }
    	function nullDeal(obj,strDefault){
    		if(obj==null||obj==""||typeof(obj)=="undefined"){
    			return strDefault ;
    		}
    		return obj;
    	};
    	function isNull(obj){
    		if(obj!=undefined && (""+obj)!=""  && obj!="null"){
    			return false ;
    		}
    		return true ;
    	}
    	function isNotNull(obj){
    		if(obj!=undefined && (""+obj)!=""  && obj!="null"){
    			return true ;
    		}
    		return false ;
    	}
    	function initParam(divId){
    		var paramTemp  = {};
    		var $search = $('#'+divId) ;
    		$search.find("input").each(function(){
    			if($(this).attr("name") && isNotNull($(this).val())){
    				paramTemp[$(this).attr("name")]=$(this).val();
    			}
    		});
    		$search.find("select").each(function(){
    			if($(this).attr("name") && isNotNull($(this).val())){
    				paramTemp[$(this).attr("name")]=$(this).val();
    			}
    		});
    		$search.find("textarea").each(function(){
    			if($(this).attr("name") && isNotNull($(this).val())){
    				paramTemp[$(this).attr("name")]=$(this).val();
    			}
    		});
    		return paramTemp;
    	}
    	function clearParam(divId){
    		var $search = $('#'+divId) ;
    		$search.find("input").val("");
    		$search.find("select").each(function(){
    			$(this).find("option:first").attr('selected','selected');
    		});
    		$search.find("textarea").val("");
    	}
    	
    	function isEmail(str) {
    		return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(str);
    	}
    	
    	function isTellphone(str) {
    		return /^\d+$/.test(str);
    	}
    	
    	/**
    	 * 校验长度
    	 * @param o 要校验的对象
    	 * @param min 最小长度
    	 * @param max 最大长度
    	 * @returns {Boolean} true：校验通过，false：校验失败。
    	 */
    	function checkLength( o, min, max ) {
    		var temp=o.val();
    		temp = temp.replace(/[^\x00-\xff]/g,"aa")
    		var _temp = o;
    		if(o[0].tagName === 'SELECT' && $(o).data("combobox")) {
    			_temp = o.next().find('input'); 
    		}
    		if ( $.trim(temp).length > ( max || 9999 ) || $.trim(temp).length < min ) {
    			_temp.addClass( "ui-state-error" ).focus();
    			return false;
    		} else {
    			_temp.removeClass( "ui-state-error" );
    			return true;
    		}
    	}
    	
    	
    	/**
    	 * 校验正则
    	 * @param o 要校验的对象
    	 * @param regexp 要校验的正则
    	 * @param n 校验失败的提示语，可选
    	 * @returns {Boolean} true：校验通过，false：校验失败。
    	 */
    	function checkRegexp( o, regexp, n ) {
    		if ( !( regexp.test( o.val() ) ) ) {
    			o.addClass( "ui-state-error" );
    			if(n && n != "")
    			{
    				o.attr("title",n);
    			}
    			o.focus();
    			o.val("")
    			return false;
    		} else {
    		    o.removeClass( "ui-state-error" );
    			return true;
    		}
    	}
        </script>
        
  
    <div class="modal fade" id="myAlertDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index: 1060"><!-- z-index控制提示框和弹窗的遮盖 -->
        <div class="modal-dialog" style="width: 450px;" >
            <div class="modal-content">
                <div class="modal-header" >
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3 class="modal-header-h3" >提示信息</h3>
                </div>
                <div class="modal-body">
                    <p id="myAlertMess">提示内容</p>
                </div>
                <div class="modal-footer">
                    <a href="#" class="btn btn-primary btOK" >确定</a><!-- data-dismiss="modal"  -->
                    <a href="#" class="btn btn-primary btNO" style="display: none" >取消</a><!-- data-dismiss="modal"  -->
                </div>
            </div>
        </div>
    </div>
     <div class="modal fade" id="myAlertDiv2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index: 1060"><!-- z-index控制提示框和弹窗的遮盖 -->
        <div class="modal-dialog" style="width: 450px;" >
            <div class="modal-content">
                <div class="modal-header" >
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3 class="modal-header-h3" >提示信息</h3>
                </div>
                <div class="modal-body">
                    <p id="myAlertMess2">提示内容</p>
                </div>
                <div class="modal-footer">
                    <a href="#" class="btn btn-primary btOK" >确定</a><!-- data-dismiss="modal"  -->
                    <a href="#" class="btn btn-primary btNO" style="display: none" >取消</a><!-- data-dismiss="modal"  -->
                </div>
            </div>
        </div>
    </div>
    