(function ($) {  
    $.fn.extend({  
        "rowspan": function (rowspanParm) { 
            return this.each(function () {  
                var that;
				var text;
                $('tr', this).each(function (row) { 
                 $('td:eq('+rowspanParm.sameCol+')', this).filter(':visible').each(function(col) { 
                        if (that != null && $(this).text() == $(that).text()) { 				
                            rowspan = $(that).siblings("td:eq("+rowspanParm.mergeCol+")").attr("rowSpan");  
                            if (rowspan == undefined) {  
                                $(that).siblings("td:eq("+rowspanParm.mergeCol+")").attr("rowSpan", 1);  
                                rowspan = $(that).siblings("td:eq("+rowspanParm.mergeCol+")").attr("rowSpan");  
                            }  
							text+=parseFloat($(this).siblings("td:eq("+rowspanParm.calCol+")").text());
                            rowspan = Number(rowspan) + 1;  
                            $(that).siblings("td:eq("+rowspanParm.mergeCol+")").attr("rowSpan", rowspan);  
                            $(this).siblings("td:eq("+rowspanParm.mergeCol+")").hide();  
                        } else {  
                            that = this;
							text=parseFloat($(this).siblings("td:eq("+rowspanParm.calCol+")").text());
                        } 	
						
						$(that).siblings("td:eq("+rowspanParm.mergeCol+")").css("border","2px solid "+rowspanParm.color);
						$(that).siblings("td:eq("+rowspanParm.mergeCol+")").html("<div style='text-align:center;width:"+rowspanParm.width+"'>"+text+"</div>");
						$(that).siblings("td:eq("+rowspanParm.mergeCol+")").on("click",function(event){
							alert("nihai");
						});
                    });  
					
                });  
            });  
        }  
    });  
  
})(jQuery); 