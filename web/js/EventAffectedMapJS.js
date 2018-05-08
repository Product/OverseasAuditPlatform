/**
 * 事件影响纵览，显示搜索结果
 */
function EventAffectedMapInit(){
	var url = "";
	var selectType=$('#hid_subModuleInfo').val();
	if($("#hid_eventAffect").val() =="eventAffect"){
        var strDays=$('input[name="eventTime"]:checked').val();
		var strStartTime="";
		var strEndTime="";
		var strForm="";

		if(strDays=="自定义"){
			strStartTime=$("#timeStartEvent").val();
			strEndTime=$("#timeEndEvent").val();
			strDays="0";
		}
		
		var strQuery =encode($("#txt_eventQuery").val());
		var strEventType = $("#eventType").find("option:selected").val();
		
		var strChoices = getChoices();
		var choicesLength = $("#chioceDisplay").children("span").length;
		
		url = "./servlet/EventAffectedMapServlet?methodName=select&selectType="+selectType+"&strQuery=" + strQuery
					+ "&strEventType=" + strEventType
					+ "&strDays="+strDays
					+ "&strStartTime="+strStartTime
					+ "&strEndTime="+strEndTime
					+ "&choices="+strChoices
					+"&choiceslength=" + choicesLength+"&form=1";
    }else if($("#hid_eventAffect").val() =="eventShow"){
    	var ptl="";
    	var i = 0;
		$("input[name='eventInfo']:checked").each(function(){
	       ptl += $(this).val()+"|"; 
	       i++;
	    });
    	url = "./servlet/EventAffectedMapServlet?methodName=select&selectType="+selectType
					+ "&clen="+i+ "&choices="+ptl+"&choiceslength=0&form=2";
    }
	
	var data = "";
	    
		$.ajax({
           type: "post",
           url: url, 
		   contentType: "application/json;charset=utf-8",
           async: false,
           success: function (r) {
    	           
   	            if (r == "false") {
   	            	alert("信息提取失败！", 3500);  
   	            }
   	            else{
   	            	data = $.parseJSON(r);
   	            	 $("#websiteNum").html(data.websiteTotal);
   	            	 $("#productNum").html(data.productTotal);
   	            	 $("#productTypeNum").html(data.productTypeTotal);
   	            	 $("#brandNum").html(data.brandTotal);
   	            	 $("#productDefineNum").html(data.productDefinitonTotal);
   	            	}
           },
           error: function (e) {
               alert(e.responseText);
           }
	});
	mapLoading(data.eventCNTList, data.eventCNVList);
}

/**
 * @desc 导出事件影响商品列表
 */
function EventAffectedMapImport() {
	var strDays=$('input[name="eventTime"]:checked').val();
	var strStartTime="";
	var strEndTime="";

	if(strDays=="自定义"){
		strStartTime=$("#timeStartEvent").val();
		strEndTime=$("#timeEndEvent").val();
		strDays="0";
	}
	
	var strQuery =encode($("#txt_eventQuery").val());
	var strEventType = $("#eventType").find("option:selected").val();
	
	var strChoices = getChoices();
	var choicesLength = $("#chioceDisplay").children("span").length;

    window.open("./servlet/EventAffectedMapServlet?methodName=exportExcel" 
            + "&strQuery=" + strQuery
			+ "&strEventType=" + strEventType
			+ "&strDays="+strDays
			+ "&strStartTime="+strStartTime
			+ "&strEndTime="+strEndTime
			+ "&choices="+strChoices
			+"&choiceslength=" + choicesLength + "&viewForm=1");
}