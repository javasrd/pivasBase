var echarts;
// 初始化echarts
function initEcharts(contextPath)
{
	require.config({
        paths: {
            echarts: contextPath+'/assets/echarts'
        }
    });
    
    require(
            [
                'echarts',
                'echarts/chart/line',   // 按需加载所需图表，如需动态类型切换功能，别忘了同时加载相应图表
                'echarts/chart/pie',
                'echarts/chart/bar',
            ],
            function (ec) {
            	echarts = ec;
            }
    );
}

function createPie(option,unit)
{
	var config = null;
	if(unit == null){
	    config = {
	    		domId:"main",
	    		formatter:"{a} <br/>{b} : {c} ({d}%)",
	    		labelFormatter:'{b}\n{d}%',
	    		seriesName:"访问来源",
	    		radius : '60%',
	    		data:[],
	    		legendNames:[],
	    		colorList:[],
	    		clickCall:null
	    	   };		
	}else{
	    config = {
	    		domId:"main",
	    		formatter:"{a} <br/>{b} : {c} " + unit + " ({d}%)",
	    		labelFormatter:'{b}\n{d}%',
	    		seriesName:"访问来源",
	    		radius : '60%',
	    		data:[],
	    		legendNames:[],
	    		colorList:[],
	    		clickCall:null
	    	   };
	}

		
	var op = $.extend(config, option); 

	var option = {
		    tooltip : {
		        trigger: 'item',
		        formatter: op.formatter
		    },
		    legend: {
		        y : 'top',
		        x : 'center',
		        data:op.legendNames
		    },
		    calculable : false,
		    series : [
		        {
		            name:op.seriesName,
		            type:'pie',
		            radius : op.radius,
		            center: ['50%', 225],
		            itemStyle: {
		                normal: {
		                    color: function(params) {
		                    	if (op.colorList.length > params.dataIndex)
		                    	{
		                    		return op.colorList[params.dataIndex];
		                    	}
		                    },
		                    label: {
		                        show: true,
		                        position: 'outer',
		                        formatter: op.labelFormatter
		                    }
		                }
		            },
		            data:op.data
		        }
		    ]
	}
	
	var myChart = echarts.init(document.getElementById(op.domId));
	myChart.setOption(option);
	
	if (op.clickCall)
	{
		myChart.on("click", op.clickCall);
	}
	
	return myChart;
}

function createBar(option,unit)
{
	var config = {
		domId:"main",
		legendNames:[],
		seriesData:[],
		xAxisData:[]
	};
			
	var op = $.extend(config, option); 
	if(unit == null){
		option = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {         
			            type : 'shadow'
			        }
			        
			    },
			    legend: {
			        data:op.legendNames
			    },
			    calculable : true,
			    xAxis : [
			        { 
			            type : 'category',
			            data : op.xAxisData
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series:op.seriesData
			};	
	}else{
		var tUnit = '{value} ' + unit;

		option = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {         
			            type : 'shadow'
			        }
			    },
			    legend: {
			        data:op.legendNames
			    },
			    calculable : true,
			    xAxis : [
			        { 
			            type : 'category',
			            data : op.xAxisData
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            axisLabel:{formatter:tUnit}
			        }
			    ],
			    series:op.seriesData
			};	
	}

		                    
	
	var myChart = echarts.init(document.getElementById(op.domId));
	myChart.setOption(option);
	
	return myChart;
}