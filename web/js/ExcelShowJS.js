function checkStatus(){
	$('#main').show();
	$('#EventsExcelShow').hide();
	$('#DetectingsExcelShow').hide();
	$('#WebsitesExcelShow').hide();
	$('#ProductsExcelShow').hide();
	$('#ProductTypeExcelShow').hide();
	$('#ProductDefineExcelShow').hide();
	$('#BrandExcelShow').hide();
	$('#btn_MapData').hide();
	$('#hid_viewShow').val('0');
	$('#btn_ExcelData').show();
	$('#EventAffectSearch').show();
	$('#EventExactInfo').hide();
}

function initDetectingExcel(){
	var strChoices = getChoices();
    var choicesLength = $("#chioceDisplay").children("span").length;
    var url = "";
    if($.fn.dataTable.isDataTable( '#DetectingsExcelShow table' )){
    	var table = $('#DetectingsExcelShow table').DataTable();
    	table.destroy();
    	$('#DetectingsExcelShow tbody').html('');
    }
    if($("input[name='csa']:checked").val() == 2){
        url = "./servlet/DetectingSuggestServlet?methodName=exportEvaluationExcel&sourceTypeId=" +
            $("input[name='csa']:checked").val() + "&ProductTypeChoices=" + strChoices + 
            "&choiceslength=" + choicesLength + "&suggestNum=" + $("#txt_suggestNum").val() + "&viewForm=2"+ 
            "&EvaluationRangeMin=" + $("#txt_evaluationRangeMin").val() + "&EvaluationRangeMax=" + $("#txt_evaluationRangeMax").val();
    	 $('#DetectingsExcelShow thead').html('<tr><th style="width:30%">商品名称</th><th style="width:10%">网站名称</th><th style="width:30%">网站路径</th><th style="width:10%">来源类型</th><th style="width:10%">商品品牌</th><th style="width:10%">商品产地</th></tr>');
    }else{
        url = "./servlet/DetectingSuggestServlet?methodName=exportExcel&sourceTypeId=" +
                $("input[name='csa']:checked").val() + "&ProductTypeChoices=" + strChoices + 
                "&choiceslength=" + choicesLength + "&suggestNum=" + $("#txt_suggestNum").val() + "&viewForm=2" + 
                "&EventKeyWord=" + $("#txt_eventKeyWord").val() + "&EvaluationRangeMin=" + $("#txt_evaluationRangeMin").val() + "&EvaluationRangeMax=" + $("#txt_evaluationRangeMax").val();
    	 $('#DetectingsExcelShow thead').html('<tr><th style="width:30%">商品名称</th><th style="width:8%">网站名称</th><th style="width:30%">网站路径</th><th style="width:8%">来源类型</th><th style="width:10%">来源内容</th><th style="width:7%">商品品牌</th><th style="width:7%">商品产地</th></tr>');
    }

    
	$('#DetectingsExcelShow table').DataTable({
		// "processing": true,
		// "serverSide": true,
		"ajax":{
			"url":url,
			"dataSrc":"webList"
		},
		"paging":true,
		"searching":true,
		"autoWidth":false,
		"ordering":true,
		"pageLength":10,
		"lengthChange":false,
		"language": {
	        "sProcessing": "处理中...",
	        "sLengthMenu": "显示 _MENU_ 项结果",
	        "sZeroRecords": "没有匹配结果",
	        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
	        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	        "sInfoPostFix": "",
	        "sSearch": "搜索:",
	        "sUrl": "",
	        "sEmptyTable": "表中数据为空",
	        "sLoadingRecords": "载入中...",
	        "sInfoThousands": ",",
	        "oPaginate": {
	            "sFirst": "首页",
	            "sPrevious": "上页",
	            "sNext": "下页",
	            "sLast": "末页"
	        },
	        "oAria": {
	            "sSortAscending": ": 以升序排列此列",
	            "sSortDescending": ": 以降序排列此列"
	        }
	    },

	});
}

function initEventExcel(){
	var url;
	if($("#hid_eventAffect").val() =="eventAffect"){
        var strDays=$('input[name="eventTime"]:checked').val();
		var strStartTime="";
		var strEndTime="";

		if(strDays=="自定义"){
			strStartTime=$("#timeStartEvent").val();
			strEndTime=$("#timeEndEvent").val();
			strDays="0";
		}
		
		var strQuery =$("#txt_eventQuery").val();
		var strEventType = $("#eventType").find("option:selected").val();
		
		var strChoices = getChoices();
		var choicesLength = $("#chioceDisplay").children("span").length;

	   	url = "./servlet/EventAffectedMapServlet?methodName=exportExcel" 
	            + "&strQuery=" + strQuery + "&strEventType=" + strEventType
				+ "&strDays="+strDays + "&strStartTime="+strStartTime
				+ "&strEndTime="+strEndTime + "&choices="+strChoices
				+"&choiceslength=" + choicesLength + "&viewForm=2";    
    }else if($("#hid_eventAffect").val() =="eventShow"){
    	var ptl="";
    	var i = 0;
		$("input[name='eventInfo']:checked").each(function(){
	       ptl += $(this).val()+"|"; 
	       i++;
	    });
        url = "./servlet/EventAffectedMapServlet?methodName=eventDriveExcel"
				+"&choiceslength=" + i + "&choices="+ptl;
    }
	if($.fn.dataTable.isDataTable( '#EventsExcelShow table' )){
    	var table = $('#EventsExcelShow table').DataTable();
    	table.destroy();
    }
	$('#EventsExcelShow table').DataTable({
		"ajax":{
			"url":url,
			"dataSrc":"webList"
		},
		"paging":true,
		"autoWidth":false,
		"searching":true,
		"ordering":true,
		"pageLength":10,
		"lengthChange":false,
		"language": {
	        "sProcessing": "处理中...",
	        "sLengthMenu": "显示 _MENU_ 项结果",
	        "sZeroRecords": "没有匹配结果",
	        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
	        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	        "sInfoPostFix": "",
	        "sSearch": "搜索:",
	        "sUrl": "",
	        "sEmptyTable": "表中数据为空",
	        "sLoadingRecords": "载入中...",
	        "sInfoThousands": ",",
	        "oPaginate": {
	            "sFirst": "首页",
	            "sPrevious": "上页",
	            "sNext": "下页",
	            "sLast": "末页"
	        },
	        "oAria": {
	            "sSortAscending": ": 以升序排列此列",
	            "sSortDescending": ": 以降序排列此列"
	        }
	    },

	});
}

function initEventsInfo(){
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

   var url = "./servlet/EventAffectedMapServlet?methodName=eventsInfo" 
            + "&strQuery=" + strQuery + "&strEventType=" + strEventType
			+ "&strDays="+strDays + "&strStartTime="+strStartTime
			+ "&strEndTime="+strEndTime + "&choices="+strChoices
			+"&choiceslength=" + choicesLength;


	if($.fn.dataTable.isDataTable( '#EventExactInfo table' )){
    	var table = $('#EventExactInfo table').DataTable();
    	table.destroy();
    }
	$('#EventExactInfo table').DataTable({
		"ajax":{
			"url":url,
		},
		"createdRow": function ( row, data, index ) {
			$('td', row).eq(0).html('<input type="checkbox" value="'+data[0]+'" name="eventInfo" checked ="checked"/>');
        },
		"paging":true,
		"autoWidth":false,
		"searching":true,
		"ordering":true,
		"pageLength":5,
		"lengthChange":false,
		"language": {
	        "sProcessing": "处理中...",
	        "sLengthMenu": "显示 _MENU_ 项结果",
	        "sZeroRecords": "没有匹配结果",
	        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
	        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	        "sInfoPostFix": "",
	        "sSearch": "搜索:",
	        "sUrl": "",
	        "sEmptyTable": "无相关事件",
	        "sLoadingRecords": "载入中...",
	        "sInfoThousands": ",",
	        "oPaginate": {
	            "sFirst": "首页",
	            "sPrevious": "上页",
	            "sNext": "下页",
	            "sLast": "末页"
	        },
	        "oAria": {
	            "sSortAscending": ": 以升序排列此列",
	            "sSortDescending": ": 以降序排列此列"
	        }
	    },

	});
}

function initWebsiteExcel(num){
	
    if($.fn.dataTable.isDataTable( '#WebsitesExcelShow table' )){
    	var table = $('#WebsitesExcelShow table').DataTable();
    	table.destroy();
    	$('#WebsitesExcelShow tbody').html('');
    }
    
	$('#WebsitesExcelShow table').DataTable({
		"processing": true,
		"serverSide": true,
		"ajax":{
			"url":"./servlet/RegulatoryServlet",
			"dataSrc":"webList"
		},
		"paging":true,
		"searching":false,
		"autoWidth":false,
		"ordering":true,
		"pageLength":10,
		"lengthChange":false,
		"language": {
	        "sProcessing": "处理中...",
	        "sLengthMenu": "显示 _MENU_ 项结果",
	        "sZeroRecords": "没有匹配结果",
	        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
	        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	        "sInfoPostFix": "",
	        "sSearch": "搜索:",
	        "sUrl": "",
	        "sEmptyTable": "表中数据为空",
	        "sLoadingRecords": "载入中...",
	        "sInfoThousands": ",",
	        "oPaginate": {
	            "sFirst": "首页",
	            "sPrevious": "上页",
	            "sNext": "下页",
	            "sLast": "末页"
	        },
	        "oAria": {
	            "sSortAscending": ": 以升序排列此列",
	            "sSortDescending": ": 以降序排列此列"
	        }
	    },

	});
}

function initProductDefinitionExcel(){
	
    if($.fn.dataTable.isDataTable( '#ProductDefineExcelShow table' )){
    	var table = $('#ProductDefineExcelShow table').DataTable();
    	table.destroy();
    	$('#ProductDefineExcelShow tbody').html('');
    }
    
	$('#ProductDefineExcelShow table').DataTable({
		"processing": true,
		"serverSide": true,
		"ajax":{
			"url":"./servlet/RegulatoryServlet",
			"dataSrc":"webList"
		},
		"createdRow": function ( row, data, index ) {
			$('td', row).eq(1).html('<img src='+data[1]+' style="height:100px"/>');
        },
		"paging":true,
		"searching":false,
		"autoWidth":false,
		"ordering":true,
		"pageLength":10,
		"lengthChange":false,
		"language": {
	        "sProcessing": "处理中...",
	        "sLengthMenu": "显示 _MENU_ 项结果",
	        "sZeroRecords": "没有匹配结果",
	        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
	        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	        "sInfoPostFix": "",
	        "sSearch": "搜索:",
	        "sUrl": "",
	        "sEmptyTable": "表中数据为空",
	        "sLoadingRecords": "载入中...",
	        "sInfoThousands": ",",
	        "oPaginate": {
	            "sFirst": "首页",
	            "sPrevious": "上页",
	            "sNext": "下页",
	            "sLast": "末页"
	        },
	        "oAria": {
	            "sSortAscending": ": 以升序排列此列",
	            "sSortDescending": ": 以降序排列此列"
	        }
	    },

	});
}

function initProductExcel(){
	
    if($.fn.dataTable.isDataTable( '#ProductsExcelShow table' )){
    	var table = $('#ProductsExcelShow table').DataTable();
    	table.destroy();
    	$('#ProductsExcelShow tbody').html('');
    }
    
	$('#ProductsExcelShow table').DataTable({
		"processing": true,
		"serverSide": true,
		"ajax":{
			"url":"./servlet/RegulatoryServlet",
			"dataSrc":"webList"
		},
		"paging":true,
		"searching":false,
		"autoWidth":false,
		"ordering":true,
		"pageLength":10,
		"lengthChange":false,
		"language": {
	        "sProcessing": "处理中...",
	        "sLengthMenu": "显示 _MENU_ 项结果",
	        "sZeroRecords": "没有匹配结果",
	        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
	        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	        "sInfoPostFix": "",
	        "sSearch": "搜索:",
	        "sUrl": "",
	        "sEmptyTable": "表中数据为空",
	        "sLoadingRecords": "载入中...",
	        "sInfoThousands": ",",
	        "oPaginate": {
	            "sFirst": "首页",
	            "sPrevious": "上页",
	            "sNext": "下页",
	            "sLast": "末页"
	        },
	        "oAria": {
	            "sSortAscending": ": 以升序排列此列",
	            "sSortDescending": ": 以降序排列此列"
	        }
	    },

	});
}

function initBrandExcel(){
	
    if($.fn.dataTable.isDataTable( '#BrandExcelShow table' )){
    	var table = $('#BrandExcelShow table').DataTable();
    	table.destroy();
    	$('#BrandExcelShow tbody').html('');
    }
    
	$('#BrandExcelShow table').DataTable({
		"processing": true,
		"serverSide": true,
		"ajax":{
			"url":"./servlet/RegulatoryServlet",
			"dataSrc":"webList"
		},
		"paging":true,
		"searching":false,
		"autoWidth":false,
		"ordering":true,
		"pageLength":10,
		"lengthChange":false,
		"language": {
	        "sProcessing": "处理中...",
	        "sLengthMenu": "显示 _MENU_ 项结果",
	        "sZeroRecords": "没有匹配结果",
	        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
	        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	        "sInfoPostFix": "",
	        "sSearch": "搜索:",
	        "sUrl": "",
	        "sEmptyTable": "表中数据为空",
	        "sLoadingRecords": "载入中...",
	        "sInfoThousands": ",",
	        "oPaginate": {
	            "sFirst": "首页",
	            "sPrevious": "上页",
	            "sNext": "下页",
	            "sLast": "末页"
	        },
	        "oAria": {
	            "sSortAscending": ": 以升序排列此列",
	            "sSortDescending": ": 以降序排列此列"
	        }
	    },

	});
}

function initProductTypeExcel(){
	
    if($.fn.dataTable.isDataTable( '#ProductTypeExcelShow table' )){
    	var table = $('#ProductTypeExcelShow table').DataTable();
    	table.destroy();
    	$('#ProductTypeExcelShow tbody').html('');
    }
    
	$('#ProductTypeExcelShow table').DataTable({
		"processing": true,
		"serverSide": true,
		"ajax":{
			"url":"./servlet/RegulatoryServlet",
			"dataSrc":"webList"
		},
		"paging":true,
		"searching":false,
		"autoWidth":false,
		"ordering":true,
		"pageLength":10,
		"lengthChange":false,
		"language": {
	        "sProcessing": "处理中...",
	        "sLengthMenu": "显示 _MENU_ 项结果",
	        "sZeroRecords": "没有匹配结果",
	        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
	        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	        "sInfoPostFix": "",
	        "sSearch": "搜索:",
	        "sUrl": "",
	        "sEmptyTable": "表中数据为空",
	        "sLoadingRecords": "载入中...",
	        "sInfoThousands": ",",
	        "oPaginate": {
	            "sFirst": "首页",
	            "sPrevious": "上页",
	            "sNext": "下页",
	            "sLast": "末页"
	        },
	        "oAria": {
	            "sSortAscending": ": 以升序排列此列",
	            "sSortDescending": ": 以降序排列此列"
	        }
	    },

	});
}

function viewExcelFormDatum(){
	$('#main').hide();
	$('#hid_viewShow').val('1');
	$('#backWorld').hide();
	$('#btn_ExcelData').hide();
	$('#btn_MapData').show();
	$('#DetectingsExcelShow').hide();
	$('#EventsExcelShow').hide();
	$('#WebsitesExcelShow').hide();
	$('#BrandExcelShow').hide();
	$('#ProductsExcelShow').hide();
	$('#ProductTypeExcelShow').hide();
	$('#ProductDefineExcelShow').hide();
	var pageStatus = $("#hid_pageStatus").val();
	if(pageStatus == "1"){
		initDetectingExcel();
		$('#DetectingsExcelShow').show();
	}else if(pageStatus == "3"){
		EventAffectedMapInit();
		initEventExcel();
		$('#EventsExcelShow').show();
		if($("#hid_eventAffect").val() =="eventAffect"){
                initEventsInfo();
                $('#EventAffectSearch').hide();
                $('#EventExactInfo').show();
                $("#hid_eventAffect").val("eventShow");
        }
	}else if(pageStatus == "0"){
		var strChoices = getChoices();
		var choicesLength = $("#chioceDisplay").children("span").length;
		
		if($('#hid_subModuleInfo').val() == "website")//获取网站数据 
		{
			var num = initWebsiteNum(strChoices,choicesLength);
			initWebsiteExcel(num);
			$('#WebsitesExcelShow').show();
		}
		else if($('#hid_subModuleInfo').val() == "brand")//获取品牌数据
		{
			initBrandNum(strChoices,choicesLength);
			initBrandExcel();
			$('#BrandExcelShow').show();
		}
		else if($('#hid_subModuleInfo').val() == "product")//获取商品数据
		{
			initProductNum(strChoices,choicesLength);
			initProductExcel();
			$('#ProductsExcelShow').show();
		}
		else if($('#hid_subModuleInfo').val() == "productType")//获取商品类型数据
		{
			initProductTypeNum(strChoices,choicesLength);
			initProductTypeExcel();
			$('#ProductTypeExcelShow').show();
		}
		else if($('#hid_subModuleInfo').val() == "productDefine"){	//获取产品数据
			initProductDefinitionNum(strChoices,choicesLength);
			initProductDefinitionExcel();
			$('#ProductDefineExcelShow').show();
		}
	}
}

function viewWorldFormDatum(){
	$('#hid_viewShow').val('0');
	$('#backWorld').show();
	$('#btn_ExcelData').show();
	$('#btn_MapData').hide();
	$('#main').show();
	
	var pageStatus = $("#hid_pageStatus").val();
	if(pageStatus == "1"){
		DetectingSuggestMapInit();
	}else if(pageStatus == "3"){
		EventAffectedMapInit();
	}else if(pageStatus == "0"){
		RegulatoryMapInit();
	}
	$('#DetectingsExcelShow').hide();
	$('#EventsExcelShow').hide();
	$('#WebsitesExcelShow').hide();
	$('#ProductDefineExcelShow').hide();
	$('#ProductsExcelShow').hide();
	$('#ProductTypeExcelShow').hide();
	$('#BrandExcelShow').hide();
}
