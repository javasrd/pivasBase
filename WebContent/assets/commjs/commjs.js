$(document).ready(function(){$('.oe_searchview input[type=text]:first').focus();});
window.onload=function(){
	$('.oe_searchview input[type=text]:first').focus();

};
$(function() {
var inputtextwidth=$('input[type=text]:first').width();
var bigdiv= $('.oe_searchview .oe_searchview_facets').width();
$('.oe_searchview input[type=text]:first').focus();
$(".oe_searchview").bind("mouseout",function(){
	var oe_searchviewwidth=$(".oe_searchview").width();
	$(".divselect").width(oe_searchviewwidth);
});
$(".divselect").mouseout(function(){
	var oe_searchviewwidth=$(".oe_searchview").width();
	if($(".oe_search_txt").val()){
		$(".oe_search_txt").parent().parent().parent().find(".divselect").show();
		$(".divselect").width(oe_searchviewwidth);
		
	}else{
		$(".oe_search_txt").parent().parent().parent().find(".divselect").hide();
	}
 });
$(".oe_searchview .oe_search_txt").bind("keyup",function(){
	var oe_searchviewwidth=$(".oe_searchview").width();
	if($(this).val()){
		$(this).parent().parent().parent().find(".divselect").show();
		$(".divselect").width(oe_searchviewwidth);
	}else{
		$(this).parent().parent().parent().find(".divselect").hide();
	}
	var transferredval=$(this).val();
	$(".searchVal").text(transferredval).html();
	$('.oe_searchview input[type=text]:first').focus();
});
$(".oe_searchview .divselect li").bind("click",function(){
	var oe_searchviewwidth=$(".oe_searchview").width();
	var darkDiv = $(".oe_tag_dark[name='"+$(this).attr("name")+"']");
	var darkSpan = null ;
	if(darkDiv && darkDiv.length>0){
	}else{
		var vHtml = '<div name="'+$(this).attr("name")+'" value="" class="oe_tag oe_tag_dark oe_searchview_facet" ><span class="oe_facet_values" >'+$(this).attr("show")+'<span class="oe_facet_value" >'+$(this).find(".searchVal").html()+'</span></span><span class="oe_facet_remove" onclick="removeFacet(this)" >x</span></div>';
		if($(".oe_tag_dark").length>0){//如果有，找到最后一个添加
			$(".oe_tag_dark:last").after(vHtml);
			$('.oe_searchview input[type=text]:first').focus();
		}else{//如果没有，从第一个添加
			$(".oe_searchview_head").append(vHtml);	
			$('.oe_searchview input[type=text]:first').focus();
		}
		//$(this).parent().parent().parent().find(".oe_searchview_facets").append(vHtml).find(".oe_facet_value");
	}
	darkSpan = $(".oe_tag_dark[name='"+$(this).attr("name")+"']").find(".oe_facet_value");
	var darkVal = $(darkSpan).html();
	if(darkVal && darkVal.length>0){
		var darkArry = darkVal.split(",");
		var f= true ;
		for(var index in darkArry){
			if(darkArry[index]==$(this).find(".searchVal").html()){
				f = false ;
			}
		}
		if(f){
			darkVal = darkVal + "," + $(this).find(".searchVal").html();
			$('.oe_searchview input[type=text]:first').focus();
		}
	}
	$(darkSpan).html(darkVal);
	$(this).parent().parent().hide();
	$(this).parent().parent().parent().find(".oe_search_txt").val("");
	$(".divselect").width(oe_searchviewwidth);
	factQry();
	$('.oe_searchview input[type=text]:first').focus();
});
$(".oe_searchview .oe_facet_remove").bind("click",function(){
	$(this).parent().remove();
	$('.oe_searchview input[type=text]:first').focus();
});
$(".oe_searchview .oe_searchview_search").bind("click",function(){
	factQry();
	$('.oe_searchview input[type=text]:first').focus();
});
//初始化输入框操作 多个搜索框用
$('[data-cnd="true"]:first').focus();
var $inp = $('[data-cnd="true"]');
$inp.bind('keydown', function (e) {
	var key = e.which;
	if (key == 9) {
		e.preventDefault();
		var nxtIdx = $inp.index(this) + 1;
		$('[data-cnd="true"]:eq(' + nxtIdx + ')').focus();
	} else if (key == 13) {
		qryByCnd();
	}
});
});
function removeFacet(obj){
$(obj).parent().remove();
var darkS  = $(".oe_tag_dark");
if(darkS && darkS.length>0){
	
}else{
	$(".oe_searchview .oe_searchview_head").html("");
}
factQry();
$('.oe_searchview input[type=text]:first').focus();
}
function clearclosedinputall(){
$(".oe_searchview .oe_tag_dark").remove();
$(".oe_searchview .oe_search_txt").val("");
$(".oe_searchview .divselect").hide();
factQry();
$('.oe_searchview input[type=text]:first').focus();
}
function factQry(){
var param = [] ;
$(".oe_searchview .oe_tag_dark ").each(function(){
	var val = $(this).find(".oe_facet_value").html().trim();
	var vals = val.split(",");
	for(var i in vals){
		vals[i] = vals[i].trim();
	}
	
	param.push({"name":$(this).attr("name"),"value":vals});
});
var funname = $(".oe_searchview .ulQry").attr("funname");
var fn = window[funname]; // runMe
if(fn){
	fn.call(fn,param);	
}
$('.oe_searchview input[type=text]:first').focus();
}

//条件查询搜索事件，多个搜索框用
function qryByCnd(){
    var params = [];
    $('[data-cnd="true"]').each(function(){
        if ($(this).val()) {
            params.push({'name': $(this).attr("name"),'value':$(this).val().trim().replace("，", ",")});
        }
    });
    var funname = $("[data-qryMethod]").attr("funname");
    var fn = window[funname]; // runMe
    if(fn){
        fn.call(fn, params);
    }
}

//条件查询清空，多个搜索框用
function cndRest(){
    $('[data-cnd="true"]').each(function(){
        $(this).val('');
    });
}

	