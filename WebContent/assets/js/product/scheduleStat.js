var p;

//日期对象
p = {
    isViewModel:"week",
    startDate: null ,
    endDate: null ,
    todayDate:null,
    setupDatePicker: function() {

        $('#dateRange').dateRangePicker({maxDays:92,startDate:minDateStr}).bind('datepicker-closed',function(event,obj)
        {
            p.isViewModel = "custom";
            var time = $(this).val();
            var timeArr = new Array();
            timeArr = time.split("to");

            p.startDate = timeArr[0].trim();
            p.endDate = timeArr[1].trim();

            $("#view label").removeClass("active");
            $("#view label").switchClass("btn-primary","btn-custom");

            if(reportType == "reset"){
                resetCharts();
            }else if (reportType == "work"){
                workCharts();
            }

            gridLoad();

        });
        p.selectNowWeek();
        $('#dateRange').data('dateRangePicker').setDateRange(p.startDate,p.endDate);

    },
    selectNowWeek:function(){

        var f = new Date();
        var number = f.getDay();
        if(f.getDay() == 0){
            number = 7;
        }

        p.startDate = new Date(f.getFullYear(),f.getMonth(),f.getDate() - number + 1),
            p.endDate = new Date(f.getFullYear(),f.getMonth(),f.getDate() - number + 7);

        p.startDate = $.datepicker.formatDate("yy-mm-dd", p.startDate);
        p.endDate = $.datepicker.formatDate("yy-mm-dd", p.endDate);

    }

};
p.setupDatePicker();

// 基于准备好的dom，初始化echarts实例
//休假图表
resetChart = echarts.init(document.getElementById('resetMain'));
//工作图表
workChart = echarts.init(document.getElementById('workMain'));

// 指定图表的配置项和数据
//休假option
resetOption = {
    title:{
        text:'无数据',
        left:'center',
        top:'20px'
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data:['']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis:  {
        type: 'value'
    },
    yAxis: {
        type: 'category',
        data: ['']
    },
    series: []
};

//工作option
workOption = {
    title:{
        text:'无数据',
        left:'center',
        top:'20px'
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data:['']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    yAxis:  {
        type: 'value'
    },
    xAxis: {
        type: 'category',
        data: ['']
    },
    series: []
};

function gridLoad(){

    var params = {
        type:reportType
    };
    var gridParams = {
        startDate:p.startDate,
        endDate:p.endDate,
        type:p.isViewModel,
        reportType:reportType
    };

    $.ajaxFPostJson(contextPath + "/stat/getClasses",params,{
        "success": function(response){

            var object = eval(response.data);

            var length = object.length + 2;
            var cWidth = parseInt(100/length);

            var column = [{ title:'姓名', name:'user_name' ,width:100, align:'center', sortable: true, renderer : function(val, row){
                return val.replace(/\</g,"&lt;");
            }}];

            var size = object.length;
            for(var i = 0 ;i < size ; i++){

                var workname = object[i].workName;

                column.push({ title:workname, name:'workNum'+i ,width:100, align:'center', sortable: true,type:'number'});
            }

            column.push({ title:'总时间（小时）', name:'totaltime' ,width:100, align:'center', sortable: true,type:'number'});


            if(reportType =="work"){

                $('#workModal').empty();
                $('#workModal').html("<table id='workGrid'></table>");

                workGrid = $('#workGrid').mmGrid({
                    url: contextPath + '/stat/workGrid',
                    params: gridParams,
                    method: 'post',
                    autoLoad: false,
                    height:'500',
                    width:'auto',
                    remoteSort:false,
                    multiSelect: false,
                    checkCol: false,
                    fullWidthRows: true,
                    cols: column
                });

                workGrid.load();
                workGrid.on("loadSuccess",function(){
                    var num = workGrid.rowsLength();
                    $("#workGrid tbody tr td").removeClass("redrow");
                    if(num > 3){
                        $("#workGrid tbody tr:lt(3) td").addClass("redrow");
                    }

                });



            }else if(reportType =="reset"){

                $('#resetModal').empty();
                $('#resetModal').html("<table id='resetGrid'></table>");

                resetGrid = $('#resetGrid').mmGrid({
                    url: contextPath + '/stat/workGrid',
                    params: gridParams,
                    method: 'post',
                    autoLoad: false,
                    height:'500',
                    width:'auto',
                    remoteSort:false,
                    multiSelect: false,
                    checkCol: false,
                    fullWidthRows: true,
                    cols: column
                });

                resetGrid.load();
                resetGrid.on("loadSuccess",function(){
                    var num = resetGrid.rowsLength();
                    $("#resetGrid tbody tr td").removeClass("redrow");
                    if(num > 3){
                        $("#resetGrid tbody tr:lt(3) td").addClass("redrow");
                    }
                });

            }

        }
    });

}


//工作图表
function workCharts(){

    var params = {
        startDate:p.startDate,
        endDate:p.endDate,
        type:p.isViewModel
    };

    $.ajaxFPostJson(contextPath + "/stat/workCharts",params,{
        "success": function(response){

            if(response.data != null){

                var object = eval(response.data);
                var userObject = object[0].userList;
                var workObject = object[0].workList;

                if(workObject != null){

                    var workdata = new Array();

                    for(var ob in workObject){

                        var workname = workObject[ob].workName;

                        workname = htmlDecode(workname);
                        workdata.push(workname);

                        var workser = {
                            name: workname,
                            type: 'bar',
                            stack: '工时',
                            /* label: {
                                normal: {
                                    show: true,
                                    position: 'insideRight'
                                }
                            }, */
                            data: []
                        };
                        workOption.series[ob] = workser;
                    }
                    workOption.legend.data = workdata;
                }

                if(userObject != null && workObject != null){

                    var userdata = new Array();
                    userObject.reverse();

                    for(var ob in userObject){

                        var username = userObject[ob].user_name;
                        username = htmlDecode(username);

                        userdata.push(username);

                        var workList = userObject[ob].workTimeList

                        for(var wo in workList){

                            var time = workList[wo].totalTime

                            workOption.series[wo].data.push(time);
                        }

                    }

                    workOption.xAxis.data = userdata;
                }

            }

            if(workObject != null && userObject != null){

                var size = userObject.length;

                workOption.title.text = "当前选中时间区间排班最多的员工（前"+size+"名）";

            }

            workChart.setOption(workOption);
            workChart.resize();

        }
    });

}


//休假图表
function resetCharts(){

    var params = {
        startDate:p.startDate,
        endDate:p.endDate,
        type:p.isViewModel
    };

    $.ajaxFPostJson(contextPath + "/stat/resetCharts",params,{
        "success": function(response){

            if(response.data != null){

                var object = eval(response.data);
                var userObject = object[0].userList;
                var workObject = object[0].workList;

                if(workObject != null){

                    var workdata = new Array();

                    for(var ob in workObject){

                        var workname = workObject[ob].workName;
                        workname = htmlDecode(workname);
                        workdata.push(workname);

                        var workser = {
                            name: workname,
                            type: 'bar',
                            stack: '工时',
                            /* label: {
                                normal: {
                                    show: true,
                                    position: 'insideRight'
                                }
                            }, */
                            data: []
                        };
                        resetOption.series[ob] = workser;
                    }
                    resetOption.legend.data = workdata;
                }


                if(userObject != null && workObject != null){

                    var userdata = new Array();
                    userObject.reverse();

                    for(var ob in userObject){

                        var username = userObject[ob].user_name;
                        username = htmlDecode(username);
                        userdata.push(username);

                        var workList = userObject[ob].workTimeList

                        for(var wo in workList){

                            var time = workList[wo].totalTime

                            resetOption.series[wo].data.push(time);

                        }

                    }

                    resetOption.yAxis.data = userdata;
                }

            }

            if(workObject != null && userObject != null){

                var size = userObject.length;
                resetOption.title.text = "当前选中时间区间休假最多的员工（前"+size+"名）";
            }

            resetChart.setOption(resetOption);
            resetChart.resize();
        }
    });

}

//视图变更
function changeView(e){

    var value = $(e).attr("data-value");

    var dayActive = $("#dayViewBtn").hasClass("active");
    var weekActive = $("#weekViewBtn").hasClass("active");
    var monthActive = $("#monthViewBtn").hasClass("active");
    var quarterActive = $("#quarterViewBtn").hasClass("active");

    if(value == "day" && !dayActive){

        $("#view.btn-group label").switchClass("btn-primary","btn-custom");
        $("#dayViewBtn").switchClass("btn-custom","btn-primary");
        p.isViewModel = "day";

        if(reportType == "reset"){
            resetCharts();
        }else if (reportType == "work"){
            workCharts();
        }
        gridLoad();
    }else if(value == "week" && !weekActive){

        $("#view.btn-group label").switchClass("btn-primary","btn-custom");
        $("#weekViewBtn").switchClass("btn-custom","btn-primary");
        p.isViewModel = "week";

        if(reportType == "reset"){
            resetCharts();
        }else if (reportType == "work"){
            workCharts();
        }
        gridLoad();

    }else if(value == "month" && !monthActive){

        $("#view.btn-group label").switchClass("btn-primary","btn-custom");
        $("#monthViewBtn").switchClass("btn-custom","btn-primary");
        p.isViewModel = "month";

        if(reportType == "reset"){
            resetCharts();
        }else if (reportType == "work"){
            workCharts();
        }
        gridLoad();

    }else if(value == "quarter" && !quarterActive){

        $("#view.btn-group label").switchClass("btn-primary","btn-custom");
        $("#quarterViewBtn").switchClass("btn-custom","btn-primary");
        p.isViewModel = "quarter";

        if(reportType == "reset"){
            resetCharts();
        }else if (reportType == "work"){
            workCharts();
        }
        gridLoad();
    }
}


//导出excel
function excelExport(type){

    var startDate="",endDate="";

    if(type == "custom"){

        var time = $('#dateRange').val();
        var timeArr = new Array();
        timeArr = time.split("to");

        startDate = timeArr[0].trim();
        endDate = timeArr[1].trim();
    }

    $("#excelStartDate").val(startDate);
    $("#excelEndDate").val(endDate);
    $("#excelType").val(type);
    $("#excelReportType").val(reportType);

    $('#excelForm').submit();

    $('#excelModal').modal('hide');

}