/**
 * @describe  疫情纵览DashBoard
 */

/**
 * @describe 事件影响纵览，显示搜索结果
 */
function EpidemicAffectedMapInit(){
	
	var strDays=$('input[name="epidemicTime"]:checked').val();
	var strStartTime="";
	var strEndTime="";
	var selectType=$('#hid_subModuleInfo').val();

	if(strDays=="自定义"){
		strStartTime=$("#timeStartEvent").val();
		strEndTime=$("#timeEndEvent").val();
		strDays="0";
	}
	
	var strQuery =$("#txt_eventQuery").val();
	var strEpidemicType = $("#epidemicType").find("option:selected").val();
	
	var data = "";

	$.ajax({
		type : "post",
		url : "./servlet/EpidemicAffectedMapServlet?methodName=select&selectType="
				+ selectType + "&strQuery=" + strQuery + "&strEpidemicType="
				+ strEpidemicType + "&strDays=" + strDays + "&strStartTime="
				+ strStartTime + "&strEndTime=" + strEndTime,
		contentType : "application/json;charset=utf-8",
		async : false,
		success : function(r) {

			if (r == "false") {
				alert("信息提取失败！", 3500);
			} else {
				data = $.parseJSON(r);
				$("#websiteNum").html(data.websiteTotal);
				$("#productNum").html(data.productTotal);
				$("#productTypeNum").html(data.productTypeTotal);
				$("#brandNum").html(data.brandTotal);
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
	mapLoading(data.eventCNTList, data.eventCNVList);
}

/**
 * @describe 导出疫情影响商品列表
 */
function EpidemicAffectedMapImport() {
	var strDays=$('input[name="epidemicTime"]:checked').val();
	var strStartTime="";
	var strEndTime="";

	if(strDays=="自定义"){
		strStartTime=$("#timeStartEvent").val();
		strEndTime=$("#timeEndEvent").val();
		strDays="0";
	}
	
	var strQuery =$("#txt_eventQuery").val();
	var strEpidemicType = $("#epidemicType").find("option:selected").val();
	
	var choicesLength = $("#chioceDisplay").children("span").length;

    window.open("./servlet/EpidemicAffectedMapServlet?methodName=exportExcel" 
            + "&strQuery=" + strQuery
			+ "&strEpidemicType=" + strEpidemicType
			+ "&strDays="+strDays
			+ "&strStartTime="+strStartTime
			+ "&strEndTime="+strEndTime);
}