$(function(){
    var leftH = $(".left-menu").css("height");
    var leftW = $(".left-menu").css("width");
    var rightH = $(".content-container").css("height");
    var rightW = document.body.scrollWidth - parseInt(leftW)-2;
    if(parseInt(leftH)<parseInt(rightH)){
        $(".left-menu").css("height",rightH);
    }
    $(".content-container").css("width",rightW+"px");

    $(".tbl tbody").children().each(function(i,val){
        if(i%2==0){
            $(this).css("background-color","#f5f5f5");
        }else{
            $(this).css("background-color","#EDEDED");
        }
    });

    $(".tbl .checkbox").click(function(){
        if($(this).attr("id") != "CheckAll"){
            var clazz = $(this).attr("class");
            if(clazz.indexOf("checked")>-1){
                $(this).removeClass("checked");
                $("#CheckAll").removeClass("checked");
            }else{
                $(this).addClass("checked");
                var allChecked = true;
                $(".tbl .checkbox").each(function(){
                    var iClazz = $(this).attr("class");
                    if(iClazz.indexOf("checked") < 0){
                        alert(iClazz);
                        allChecked &= false;
                    }
                });
                if(allChecked){
                    $("#CheckAll").addClass("checked");
                }
            }
        }
    });

    $("#CheckAll").click(function(){
        var clazz = $(this).attr("class");
        if(clazz.indexOf("checked")>-1){
            $(".tbl .checkbox").each(function(){
                $(this).removeClass("checked");
            });
        }else{
            $(".tbl .checkbox").each(function(){
                $(this).addClass("checked");
            });
        }
    });
    
    $(".search-div .inSearch").focus(function(){
        $(".pre-search").css("display","");
    }).blur(function(){
        $(".pre-search").css("display","none");
    });
    
    $(".search-div .pre-more").focus(function(){
        $(".search-div .liBtN").css("display","");
    }).blur(function(){
        $(".search-div .liBtN").css("display","none");
    });
    
    
    $(".user-info .topLink").mouseenter(function(){
        $(".user-info .topLinkGo").css("display","block");
        $(this).css("position","absolute");
        $(this).css("margin-left","-90px");
    }).mouseleave(function(){
        $(".user-info .topLinkGo").css("display","none");
        $(this).css("position","absolute");
        $(this).css("margin-left","-90px"); 
    });
    
    $(".user-info .topLinkGo").bind("click",function(){
    	$(".user-info .topLinkGo").css("display","none");
    	//$(".user-info .topLink").css("position","");
    });
    
    $(".topLinkGo").mouseover(function(){
    	$(this).css("background-color","#b7caf2");
    }).mouseout(function(){
    	$(this).css("background-color","white");
    });
    
    
    $(".left-condition .checkbox").click(function(){
        var clazz = $(this).attr("class");
        if(clazz.indexOf("checked")>-1){
            $(this).removeClass("checked");
        }else{
            $(this).addClass("checked");
        }
    });
    
    $(".left-condition .checkbox").click(function(){
        var clazz = $(this).attr("class");
        if(clazz.indexOf("checked")>-1){
            $(this).removeClass("checked");
        }else{
            $(this).addClass("checked");
        }
    });
/*
    $(".top-menu ul li").click(function(){
        $(".top-menu ul li").each(function(){
            $(this).removeClass("selected");
        });
        $(this).addClass("selected");
    });
*/
    $(".left-menu li").click(function(){

        $(".left-menu li").each(function(){
            $(this).removeClass("selected");
        });
        $(this).addClass("selected");
    });

    $("#maskBtn").click(function(){
        $(".pop-page").css("left",(screen.width - parseInt( $(".pop-page").css("width")))/2);
        $(".pop-page").css("top",(screen.height - parseInt( $(".pop-page").css("height")))/2);
        $(".mask-div").css("display","block");
        $(".pop-page").css("display","block");

    });

    $(".pop-page .close").click(function(){
        $(".mask-div").css("display","none");
        $(".pop-page").css("display","none");
    });

    $(".user-right li").click(function(){
        var clazz = $(this).children("a").attr("class");

        if(clazz.indexOf("checked")>-1){
            $(this).children("a").removeClass("checked");
        }else{
            $(this).children("a").addClass("checked");
        }
    });
    /*
    $(".select select").live("change.xhz", function() {
        $($(this).parent().children(".cur-select")[0]).html($(this).find("option:selected").text() + "<i>");
        var $select = $(this);
        if ($select.val()) {
            appendValidateInfo($select.parent().parent().children("span").attr("id").replace("v_", ""), "success", "");
            $select.parent().parent().children("span").each(function() {
                appendValidateInfo($(this).attr("id"), "success", "");
            });
        }
    });*/
});