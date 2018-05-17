var p;

//日期对象
p = {
    isWeekModel:true,
    startDate: null ,
    endDate: null ,
    setupDatePicker: function() {
        $("#datepicker").datepicker({
            showOtherMonths: !0,
            selectOtherMonths: !0,
            dateFormat: "yy-mm-dd",
            onSelect: p.onSelect,
            beforeShow: p.beforeShow,
            beforeShowDay: p.beforeShowDay,
            showButtonPanel :true,
            // onChangeMonthYear: p.onChangeMonthYear
            dayNames : ['星期天','星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
            dayNamesMin : ['日','一', '二', '三', '四', '五', '六'],
            monthNames  :['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            nextText : "下个月",
            prevText : "上个月",
            currentText :"今天",
            closeText:"关闭",
            minDate:minDateStr

        });
        $("#datepicker").datepicker("option", "firstDay", 1);

        p.selectNowWeek();
        changeDateStrFn();

        $("#checkDateBtn").click(function() {
            $("#datepicker").datepicker("show");

            if(p.isWeekModel){

                $(".ui-datepicker-calendar tr").on("mousemove", function() {
                    $("#datepicker")[0] && $(this).find("td a").switchClass("ui-state-default","ui-state-hoverDate");
                });
                $(".ui-datepicker-calendar tr").on("mouseleave", function() {
                    $("#datepicker")[0] && $(this).find("td a").switchClass("ui-state-hoverDate","ui-state-default");
                });

            }

        });
    },
    selectCurrentWeek: function() {
        p.isWeekModel && window.setTimeout(function() {
            $("#datepicker").find(".ui-datepicker-current-day a").addClass("ui-state-active")
        }, 1)
    },
    onSelect: function(i, u) {
        var f = $(this).datepicker("getDate"), o, e;

        var number = f.getDay();
        if(f.getDay() == 0){
            number = 7;
        }

        p.startDate = new Date(f.getFullYear(),f.getMonth(),f.getDate() - number + 1),
            p.endDate = new Date(f.getFullYear(),f.getMonth(),f.getDate() - number + 7);

        o = u.settings.dateFormat || $.datepicker._defaults.dateFormat,
            e = $.datepicker.formatDate(o, f, u.settings);
        p.startDate = $.datepicker.formatDate(o, p.startDate, u.settings);
        p.endDate = $.datepicker.formatDate(o, p.endDate, u.settings);
        p.todayDate = e;


        p.selectCurrentWeek();

        if(p.isWeekModel){
            //切换日期显示
            changeDateStrFn();

            //重载界面
            reloadWeekView(p.startDate);
            //初始化备注
            initRemark();

        }else{
            //改变日期显示值
            changeDayDateFun();
            //重载日视图
            reloadDayView(p.todayDate);
        }

        /*
       var s = e
         , c = "week"
         , h = "none";
       r.onSelectedCallback(s); */
    },
    beforeShow: function(n, t) {
        setTimeout(function() {
            var n = $("#datepicker").position();
            t.dpDiv.css({
                top: n.top + 120,
                left: n.left + 60,
                "z-index": 99999
            })
        }, 0)
    },
    beforeShowDay: function(t) {
        var i = "";
        return t >= p.startDate && t <= p.endDate && (i = "ui-datepicker-current-day"),
            [!0, i]
    },
    selectNowWeek: function (){

        var now = new Date();
        var number = now.getDay();
        if(now.getDay() == 0){
            number = 7;
        }

        p.startDate = new Date(now.getFullYear(),now.getMonth(),now.getDate() - number + 1),
            p.endDate = new Date(now.getFullYear(),now.getMonth(),now.getDate() - number + 7);
        nowStartDate = p.startDate ,nowEndDate = p.endDate;
        p.startDate = $.datepicker.formatDate("yy-mm-dd", p.startDate);
        p.endDate = $.datepicker.formatDate("yy-mm-dd", p.endDate);

        p.todayDate =$.datepicker.formatDate("yy-mm-dd", now);

    }
};
//初始化日期控件
p.setupDatePicker();

//一人单排标示
var oneDayFlag = false;
var nowStartDate,nowEndDate;


function getLength(str) {
    var realLength = 0, len = str.length, charCode = -1;

    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) realLength += 1;
        else realLength += 2;
    }
    return realLength;
};


//初始化备注
function initRemark(){

    var remark = tinyMCE.activeEditor.getContent();

    var params = {
        "startDate":p.startDate,
        "endDate":p.endDate
    };

    $.ajaxFPostJson(contextPath + "/scheduleMgr/initRemark",params,{
        "success": function(response){
            if(response.data != null){

                tinyMCE.activeEditor.setContent(response.data);
            }else{

                tinyMCE.activeEditor.setContent("");
            }

        }
    });



}

//改变周时间戳
function changeDateStrFn(){

    var startDate = p.startDate.split("-");
    endDate = p.endDate.split("-");

    $("#timeText").html("<div id='checkDateBtn'>"+startDate[0]+" 年 "+startDate[1]+" 月 "+startDate[2]+" 日 - "+endDate[0]+" 年 "+endDate[1]+" 月 "+endDate[2]+" 日</div>");

}

//改变日时间戳
function changeDayDateFun(){

    var dayDate = p.todayDate.split("-");
    $("#timeText").html("<strong>今日 "+dayDate[0]+" 年 "+dayDate[1]+" 月 "+dayDate[2]+" 日排班表</strong>");

}



//编辑按钮变更
function changeSwitch(e){
    if(e.value == "true"){
        $(e).switchClass("btn-danger","btn-primary");
        e.value = "false",
            e.innerText = "开始排班";
        $("#autoBtn").hide();
        $("#hengXian").addClass("hide");

        $("#tablePanel").hoverClass({
            showHide: 'hide',
            ButtonDiv: '#operationBar',
            targetDiv: 'td.paiban_item'
        });
        //删除td选中
        $("#scheduleWeek table .tdselected").removeClass("tdselected");
        //累计欠休不可用
        $("#scheduleWeek table>tbody .ownRestTime_total input").prop("disabled", true);

        $("#scheduleWeek table select.form-control").prop("disabled", true);

        //保存排班管理
        saveSchedule();

    }else if(e.value == "false"){
        $(e).switchClass("btn-primary","btn-danger");
        e.value = "true",
            e.innerText = "保存排班";
        $("#autoBtn").show();
        $(".divider").removeClass("hide");

        $("#tablePanel").hoverClass({
            showHide: 'show',
            ButtonDiv: '#operationBar',
            targetDiv:'td.paiban_item'
        });
        //累计欠休可用
        $("#scheduleWeek table>tbody .ownRestTime_total input").prop("disabled", false);

        $("#scheduleWeek table select.form-control").prop("disabled", false);


    }
}


//保存排班数据
function saveSchedule(){

    var jsonArray = [];
    var dayChangeArray = [];
    var startDate= p.startDate;
    var endDate = p.endDate;
    //遍历分组人员
    $("#scheduleWeek tbody tr.current_week_plantype_row").each(function(i){

        var personid = $(this).attr("data-personid");
        var groupname = $(this).attr("data-groupname");
        var groupid = $(this).attr("data-groupid");
        var username = $(this).find("td.nametd[data-name]").attr("data-name");

        var postid = $(this).find("select[name='postSelect']").val();
        var postname = $(this).find("select[name='postSelect']").find("option:selected").text()

        var week_owetime = $(this).find("td.ownRestTime_currentweek span").text();
        var total_owetime = $(this).find("td.ownRestTime_total input").val();
        var total_time = parseFloat(week_owetime)+40;
        total_time = parseFloat(parseFloat(total_time).toFixed(2));

        var changeTotalOwnTime = $(this).find("td.ownRestTime_total input").attr("data-change");

        var weekDataBeanList = [];

        var daychange = [];

        //遍历人员每天安排的班次
        $(this).find("td.paiban_item").each(function(i){

            var workdate = $(this).attr("data-daystring");

            var dayTime = $(this).find("div.paibai_xiao_lists").attr("data-daytime");
            var olddayTime = $(this).find("div.paibai_xiao_lists").attr("data-olddaytime");
            var changeFlag = "";
            if(dayTime != olddayTime){

                var dayTimeN = parseFloat(dayTime);
                var olddayTimeN = parseFloat(olddayTime);

                if(olddayTimeN != 0 && dayTimeN == 0 && olddayTimeN > dayTimeN){

                    changeFlag = "sub";
                }else if(olddayTimeN == 0 && dayTimeN != 0 && olddayTimeN < dayTimeN){

                    changeFlag = "add";
                }else{
                    changeFlag = "same";
                }

            }else{

                changeFlag = "same";
            }

            var dayChangeEm = {

                change:changeFlag,
                date:workdate,
            };
            daychange.push(dayChangeEm);



            var weekType = i + 1;
            //班次数量
            var num = $(this).find("div.paibai_xiao_lists").find("div[data-isactive]").length;

            /* if(num > 0){ */
            //遍历班次
            $(this).find("div.paibai_xiao_lists").find("div[data-isactive]").each(function(i){

                var worktype = $(this).find("div.schedualitem").attr("data-worktype");
                var worktime = $(this).find("div.schedualitem").attr("data-worktime");
                var workid = $(this).find("div.schedualitem").attr("data-workid");
                var sort = i + 1 ;
                var workname = $(this).find("div.hisschedualitem").text();
                var workcolour = $(this).find("div.schedualitem").attr("data-colour");

                var emelent =
                    {
                        weekType:weekType,
                        workType:worktype,
                        workId:workid,
                        sort:sort,
                        workDate:workdate,
                        workName:workname,
                        workColour:workcolour,
                        totalTime:worktime,
                        user_id:personid
                    };

                weekDataBeanList.push(emelent);

            });
        });

        var dayChangeArrayEm = {
            id:personid,
            name:username,
            changeList:daychange
        };
        dayChangeArray.push(dayChangeArrayEm);

        var jsonEmelent =
            {
                groupId:groupid,
                groupName:groupname,
                user_id:personid,
                user_name:username,
                postId:postid,
                postName:postname,
                week_owetime:week_owetime,
                total_owetime:total_owetime,
                total_time:total_time,
                startDate:startDate,
                endDate:endDate,
                changeTotalOwnTime:changeTotalOwnTime,
                weekDataBeanList:weekDataBeanList

            };

        jsonArray.push(jsonEmelent);
    });


    var params = {
        "jsonStr":JSON.stringify(jsonArray),
        "startDate": p.startDate,
        "endDate" : p.endDate,
        "changeStr":JSON.stringify(dayChangeArray)
    };


    $.ajaxFPostJson(contextPath + "/scheduleMgr/saveSchedule",params,{
        "success": function(response){

            layer.msg('保存成功');

            $("#scheduleWeek table td div.paibai_xiao_lists").each(function(i){

                var daytime = $(this).attr("data-daytime");
                $(this).attr("data-olddaytime",daytime);

            });

            $("#scheduleWeek table td.ownRestTime_total").each(function(i){

                var input = $(this).find("input");
                var inputV = $(input).val();
                $(input).attr("data-oldvalue",inputV);
                $(input).attr("value",inputV);
            });
        }
    });

}

//视图变更
function changeView(e){

    var value = $(e).attr("data-value");

    var dayActive = $("#dayViewBtn").hasClass("active");
    var weekActive =$("#weekViewBtn").hasClass("active");

    if(value == "day" && !dayActive){

        $("#weekViewBtn").switchClass("btn-primary","btn-custom");
        $("#dayViewBtn").switchClass("btn-custom","btn-primary");

        $("#weekViewBtn").removeClass("active");
        $("#dayViewBtn").addClass("active");

        //日视图状态
        p.isWeekModel = false;
        //更多、编辑按钮隐藏
        $("#moreBtn").hide();
        $("#startBtn").hide();

        var todayDate = new Date();
        var todayStr = $.datepicker.formatDate("yy-mm-dd", todayDate);
        p.todayDate = todayStr;

        $("#dayView").show();
        $("#scheduleWeek").hide();

        $("#commentContainer").hide();
        $("#helpContainer").hide();

        $("#tablePanel").hoverClass({
            showHide: 'hide',
            ButtonDiv: '#operationBar',
            targetDiv: 'td.paiban_item'
        });
        //删除td选中
        $("#scheduleWeek table .tdselected").removeClass("tdselected");

        $("#lastWeekBtn").attr("title","前一日");
        $("#nextWeekBtn").attr("title","后一日");

        //改变日时间
        changeDayDateFun();
        //加载日视图
        reloadDayView(todayStr);


    }else if(value == "week" && !weekActive){

        $("#dayViewBtn").switchClass("btn-primary","btn-custom");
        $("#weekViewBtn").switchClass("btn-custom","btn-primary");

        $("#weekViewBtn").addClass("active");
        $("#dayViewBtn").removeClass("active");

        //周视图状态
        p.isWeekModel = true;
        //更多、编辑按钮展示
        $("#moreBtn").show();
        $("#startBtn").show();

        $("#dayView").hide();
        $("#scheduleWeek").show();

        $("#commentContainer").show();
        $("#helpContainer").show();

        $("#tablePanel").hoverClass({
            showHide: 'show',
            ButtonDiv: '#operationBar',
            targetDiv:'td.paiban_item'
        });

        //删除td选中
        $("#scheduleWeek table .tdselected").removeClass("tdselected");
        //累计欠休可用
        $("#scheduleWeek table>tbody .ownRestTime_total input").prop("disabled", true);

        $("#scheduleWeek table select.form-control").prop("disabled", true);


        $("#lastWeekBtn").attr("title","上一周");
        $("#nextWeekBtn").attr("title","下一周");
        //选取本周
        p.selectNowWeek();
        //改变日期显示
        changeDateStrFn();
        //重载周视图
        reloadWeekView(p.startDate);
        //初始化备注
        initRemark();


    }
}

//显示岗位
function showPostFn(e){

    var check = $(e).attr("data-value");
    if(check=="false"){

        $(e).find("a").switchClass("paibanxianshi_false","paibanxianshi_true");
        //改变显示岗位状态为是
        $(e).attr("data-value","true");
        //岗位列展示
        $("#scheduleWeek .postCla").removeClass("hide");


    }else if(check=="true"){

        $(e).find("a").switchClass("paibanxianshi_true","paibanxianshi_false");
        //改变显示岗位状态为否
        $(e).attr("data-value","false");
        //岗位列隐藏
        $("#scheduleWeek .postCla").addClass("hide");

    }

}

//显示上周
function showLastWeekFn(e){

    var check = $(e).attr("data-value");
    if(check=="false"){

        $(e).find("a").switchClass("paibanxianshi_false","paibanxianshi_true");
        //改变显示上周状态为是
        $(e).attr("data-value","true");
        //显示上周
        $("#scheduleWeek tbody .lastweek_plantype_row").removeClass("hide");

        //隐藏本周每行的姓名列
        $("#scheduleWeek tbody .current_week_plantype_row").each(function(e){

            $(this).find("td.nametd:first").addClass("hide");

        });

    }else if(check=="true"){

        $(e).find("a").switchClass("paibanxianshi_true","paibanxianshi_false");
        //改变显示上周状态为否
        $(e).attr("data-value","false");
        //隐藏上周
        $("#scheduleWeek tbody .lastweek_plantype_row").addClass("hide");
        //展示本周每行的姓名列
        $("#scheduleWeek tbody .current_week_plantype_row").each(function(e){

            $(this).find("td.nametd:first").removeClass("hide");

        });
    }

}

//一人单排
function oneDayFn(e){

    var check = $(e).attr("data-value");
    if(check=="false"){
        $(e).find("a").switchClass("paibanxianshi_false","paibanxianshi_true");
        //改变一人单排状态为是
        $(e).attr("data-value","true");
        oneDayFlag = true;

    }else if(check=="true"){
        $(e).find("a").switchClass("paibanxianshi_true","paibanxianshi_false");
        //改变一人单排状态为否
        $(e).attr("data-value","false");
        oneDayFlag = false;

    }

}

//上周按钮
function lastWeekDataFn(){


    //周视图
    if(p.isWeekModel){

        var sD = new Date(p.startDate),eD = new Date(p.endDate);

        var startDate = new Date(sD.getFullYear(),sD.getMonth(),sD.getDate() - 7),
            endDate = new Date(eD.getFullYear(),eD.getMonth(),eD.getDate() - 7);

        var startString = $.datepicker.formatDate("yy-mm-dd", startDate),
            endString = $.datepicker.formatDate("yy-mm-dd", endDate);

        p.startDate = startString,p.endDate = endString;

        //改变日期显示值
        changeDateStrFn();
        //重载周视图
        reloadWeekView(startString);

        //初始化备注
        initRemark();

    }else{

        var dayD = new Date(p.todayDate);
        var lastDate = new Date(dayD.getFullYear(),dayD.getMonth(),dayD.getDate() - 1);
        var lastStr = $.datepicker.formatDate("yy-mm-dd", lastDate);
        p.todayDate = lastStr;

        //改变日期显示值
        changeDayDateFun();
        //重载日视图
        reloadDayView(p.todayDate);

    }
}

//下周按钮
function nextWeekDataFn(){

    if(p.isWeekModel){

        var sD = new Date(p.startDate),eD = new Date(p.endDate);

        var startDate = new Date(sD.getFullYear(),sD.getMonth(),sD.getDate() + 7),
            endDate = new Date(eD.getFullYear(),eD.getMonth(),eD.getDate() + 7);

        var startString = $.datepicker.formatDate("yy-mm-dd", startDate),
            endString = $.datepicker.formatDate("yy-mm-dd", endDate);

        p.startDate = startString,p.endDate = endString;

        //改变日期值
        changeDateStrFn();
        //重载周视图
        reloadWeekView(startString);
        //初始化备注
        initRemark();


    }else{

        var dayD = new Date(p.todayDate);
        var nextDate = new Date(dayD.getFullYear(),dayD.getMonth(),dayD.getDate() + 1);
        var nextStr = $.datepicker.formatDate("yy-mm-dd", nextDate);
        p.todayDate = nextStr;

        //改变日期显示值
        changeDayDateFun();
        //重载日视图
        reloadDayView(p.todayDate);

    }

}

//加载日视图
function reloadDayView(date){

    $.ajaxHPostHtml(contextPath + "/scheduleMgr/reviewDay",{"dayDate":date},{
        "success": function(html){
            $("#dayView").html(html);

        }
    });


}

//重载周视图
function reloadWeekView(date){

    $.ajaxHPostHtml(contextPath + "/scheduleMgr/reviewWeek",{"date":date},{
        "success": function(html){

            $("#scheduleWeek").html(html);

            compareDateWithBtn(p.startDate);

            //重载后，编辑按钮初始化，悬浮框隐藏
            var startFlag = $("#startBtn").val();
            if(startFlag == "true"){

                $("#startBtn").switchClass("btn-danger","btn-primary");
                $("#startBtn").text("开始排班");
                $("#startBtn").val("false");
                $("#autoBtn").hide();
                $("#hengXian").addClass("hide");

                $("#tablePanel").hoverClass({
                    showHide: 'hide',
                    ButtonDiv: '#operationBar',
                    targetDiv: 'td.paiban_item'
                });
                //删除选中td
                $("#scheduleWeek table .tdselected").removeClass("tdselected");

                //累计欠休可用
                $("#scheduleWeek table>tbody .ownRestTime_total input").prop("disabled", true);

                $("#scheduleWeek table select.form-control").prop("disabled", true);

            }

            //是否显示岗位和上周
            showSetting();

            $("#scheduleWeek table input.hisinput").valueType();

            //初始化备注
            initRemark();

        }
    });

}


//自动排班
function autoFn(e){

    $("#dialog-confirm").dialog({
        resizable: false,
        modal: true,
        buttons: {
            "确定": function() {
                $(this).dialog( "close" );

                var params = {
                    "startDate": p.startDate
                };
                $.ajaxHPostHtml(contextPath + "/scheduleMgr/autoSchedule",params,{
                    "success": function(html){

                        $("#scheduleWeek").html(html);

                        $("#tablePanel").hoverClass({
                            showHide: 'show',
                            ButtonDiv: '#operationBar',
                            targetDiv: 'td.paiban_item'
                        });
                        //删除选中td
                        $("#scheduleWeek table .tdselected").removeClass("tdselected");
                        //累计欠休可用
                        $("#scheduleWeek table>tbody .ownRestTime_total input").prop("disabled", false);

                        $("#scheduleWeek table select.form-control").prop("disabled", false);

                        $("#scheduleWeek table input.hisinput").valueType();

                        //初始化界面状态
                        showSetting();

                        //重新获取累计欠休和周欠休
                        $("#scheduleWeek table td.ownRestTime_currentweek").each(function(i){
                            //获取累计欠休所在的行
                            var tr = $(this).parent("tr");
                            $(tr).find("div.paibai_xiao_lists").each(function(i){
                                var time = 0 ;
                                $(this).find("div.schedualitem").each(function(i){

                                    var worktype = $(this).attr("data-worktype");
                                    var worktime = $(this).attr("data-worktime");
                                    if(worktype == 0 || worktype == 1 ){
                                        time = parseFloat(time) + parseFloat(worktime);
                                        time = parseFloat(parseFloat(time).toFixed(2));
                                    }
                                });

                                $(this).attr("data-daytime",time);

                            });
                            recount(this);
                        });

                    }
                });

            },
            "取消": function() {
                $(this).dialog( "close" );
                return ;
            }
        }
    });

}


//比较日期，编辑按钮是否可操作
function compareDateWithBtn(startDate){

    var sD = new Date(startDate);
    if(sD < nowStartDate){

        $("#startBtn").removeClass("disabled");
        $("#startBtn").addClass("disabled");

        $("#commentContainer button").hide();

    }else{

        $("#startBtn").removeClass("disabled");
        $("#commentContainer button").show();
    }

}

//是否显示岗位和上周
function showSetting(){

    var showPost = $("#showPostBtn a").attr("data-value");
    if(showPost == "true"){
        //展示岗位列
        $("#scheduleWeek .postCla").removeClass("hide");

    }

    var showLast = $("#showLastWeekBtn a").attr("data-value");
    if(showLast == "true"){
        //展示上周行
        $("#scheduleWeek tbody .lastweek_plantype_row").removeClass("hide");
        //隐藏本周行的姓名列
        $("#scheduleWeek tbody .current_week_plantype_row").each(function(e){

            $(this).find("td.nametd:first").addClass("hide");

        });

    }


}

//累计欠休是否可用
function checkOwetime(e){

    var td = $(e).closest("td");
    var tr = $(td).parent("tr");
    var num = $(tr).find("td div.paibai_xiao_lists div.schedualitem").length;
    if(num == 0){

        $(e).blur();
        return ;
    }

}

//累计欠休是否改变
function changeOwetime(e){

    var oldValue = $(e).attr("data-oldvalue");
    var value = $(e).val();

    var td = $(e).closest("td");
    var tr = $(td).parent("tr");
    var num = $(tr).find("td div.paibai_xiao_lists div.schedualitem").length;

    if(isNaN(value) || value == ""){
        if(num != 0){
            $(e).val(0);
            value=0;
        }else{
            $(e).val("");
        }
    }

    var oldN = parseFloat(oldValue==""?"0":oldValue);

    var newN = parseFloat(value==""?"0":value);

    if(oldN != newN){
        var change = newN - oldN;
        change = parseFloat(parseFloat(change).toFixed(2));

        $(e).attr("data-change",change);

    }else{
        $(e).attr("data-change","0");
    }

}

//重新计算欠休和累计欠休
function recount(td){

    //获取累计欠休所在的行
    var tr = $(td).parent("tr");
    var total = 0 ;
    //遍历该行的每列
    $(tr).find("td div.paibai_xiao_lists").each(function(i){
        //获取每天的工作小时
        var time = $(this).attr("data-daytime");

        total = parseFloat(total) + parseFloat(time);

    });
    //获取一周的班次数量
    var num = $(tr).find("td div.paibai_xiao_lists div.schedualitem").length;

    //周欠休
    var weekowntime = 0;

    //周欠休= 总工时-40
    if(num == 0){
        weekowntime = "";
    }else{

        weekowntime = total - 40;
        weekowntime = parseFloat(parseFloat(weekowntime).toFixed(2));
    }

    total = parseFloat(parseFloat(total).toFixed(2));

    //赋值本周欠休
    $(tr).find("td.ownRestTime_currentweek").attr("data-totaltime",total);
    $(tr).find("td.ownRestTime_currentweek span").text(weekowntime);


    //累计欠休
    var totalowntime = $(tr).find("td.ownRestTime_total input").attr("data-lastvalue");
    if(totalowntime == "" || totalowntime == null){
        totalowntime = 0;
    }
    //获取本周的累计欠休
    var nowtotalowntime = parseFloat(totalowntime) + parseFloat(weekowntime==""?0:weekowntime);

    if(num == 0){
        nowtotalowntime = "";
    }else{
        nowtotalowntime = parseFloat(parseFloat(nowtotalowntime).toFixed(2));
    }


    //赋值累计欠休
    $(tr).find("td.ownRestTime_total input").val(nowtotalowntime);
    //累计欠休是否改变
    changeOwetime($(tr).find("td.ownRestTime_total input"));

}
//导出excel
function excelExport(type){

    var date;
    if(type == "week" || type == "month"){
        date = p.startDate;
    }else{
        date = p.todayDate;
    }

    $("#excelType").val(type);
    $("#excelDate").val(date);

    $('#excelForm').submit();

    $('#excelModal').modal('hide');

}
//打印
function printHtml(type){

    var date;
    if(type == "week" || type == "month"){
        date = p.startDate;
    }else{
        date = p.todayDate;
    }

    $("#printType").val(type);
    $("#printDate").val(date);

    $('#printForm').submit();

    $('#printModal').modal('hide');
    //window.print();

}

//加载日视图
function loadDay(date){

    $("#weekViewBtn").switchClass("btn-primary","btn-custom");
    $("#dayViewBtn").switchClass("btn-custom","btn-primary");

    $("#weekViewBtn").removeClass("active");
    $("#dayViewBtn").addClass("active");

    //日视图状态
    p.isWeekModel = false;
    //更多、编辑按钮隐藏
    $("#moreBtn").hide();
    $("#startBtn").hide();

    p.todayDate = date;

    $("#dayView").show();
    $("#scheduleWeek").hide();

    $("#commentContainer").hide();
    $("#helpContainer").hide();

    $("#tablePanel").hoverClass({
        showHide: 'hide',
        ButtonDiv: '#operationBar',
        targetDiv: 'td.paiban_item'
    });
    //删除td选中
    $("#scheduleWeek table .tdselected").removeClass("tdselected");

    $("#lastWeekBtn").attr("title","前一日");
    $("#nextWeekBtn").attr("title","后一日");

    //改变日时间
    changeDayDateFun();
    //加载日视图
    reloadDayView(date);

}