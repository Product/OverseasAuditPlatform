/*大数据学习-网站管理-网站管理-网站核实 */

function FirstPage_website(){
    GetMachineWebsiteList(1,10);
}

function PreviousPage_website() {
    var pageIndex = (parseInt($("#websiteMachinePaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#websiteMachinePaging .pageIndex").text()) - 1);
    GetMachineWebsiteList(pageIndex,10);
}

function NextPage_website() {
	var pageIndex = ($("#websiteMachinePaging .pageIndex").text() == $("#websiteMachinePaging .pageCount").text() ? parseInt($("#websiteMachinePaging .pageIndex").text()) : parseInt($("#websiteMachinePaging .pageIndex").text()) + 1);
    GetMachineWebsiteList(pageIndex,10);
}

function EndPage_website() {
	GetMachineWebsiteList(parseInt($("#websiteMachinePaging .pageCount").text()),10);
}


/**
 * 获取网站list数据并分页
 * @param strPageIndex
 * @param pageSize
 */
function GetMachineWebsiteList(strPageIndex,pageSize) {
   var strKeyWords = $("#txt_webManageKeyWords").val();
   var strBeginDate = $("#txt_WebManageBeginDate").val();
   var strEndDate = $("#txt_WebManageEndDate").val();
   var strConductType = $("#op_conductType").val();
   
   $.ajax({
       type: "post",
       url: projectLocation
		+ "./servlet/WebsiteServlet?methodName=NewQueryList&strPageIndex="+ strPageIndex+"&strPageCount="+pageSize
		+"&strQuery="+encode(strKeyWords)+"&strStraTime="+strBeginDate+"&strEndTime"+strEndDate+"&webType="+strConductType,
       contentType: "application/json;charset=utf-8",
       async: false,
       success: function (r) {
           $("#websiteManageList").html('');
           if (r == "false") {
               $("#websiteManageList")
						.append("<tr class='noDataSrc'><td colspan='7' style='text-align:center;'>无数据！</td></tr>");
               return false;
           }
           else if(r =="sessionOut"){
           	doLogout();
           	
           }
           //var data = $.parseJSON(r);
           
			$("#websiteMachinePaging .dataCount").text(r.total);
			$("#websiteMachinePaging .pageCount").text(parseInt((parseInt(r.total) + 9) / 10));
			$("#websiteMachinePaging .pageIndex").text(strPageIndex);
			
           var str = "";
           var j = 0;
           for (var i = 0; i < r.websiteList.length; i++) {
           	 str += GetMacWebsiteEntry(r.websiteList[i], i+1);
           }
           $("#websiteManageList").append(str);
       },
       error: function (e) {
           alert(e.responseText);
       }
   });
}


/**
 * 获取网站list数据
 * @param data
 * @param idx
 * @returns {String}
 */
function GetMacWebsiteEntry(data, idx){
    var str = "<tr rel='"+ data.id +"'>";
    str += "<td>";
    if(data.webType != 2){
    	 str += "禁用";
    }else{
		 str += "<input type='checkbox' class='mismatchWebSiteChild' value='" + data.id + "'>";
	}
	str += "</td>";
    str += "<td>" + idx + "</td>";
    str += "<td><a href='" + data.webLocation + "' target='_blank'>" + data.webLocation + "</a></td>";
    str += "<td>" + data.webName + "</td>";
    str += "<td>" + data.webStyleName+ "</td>";
    str += "<td>" + data.createTime.substring(0, 19)+ "</td>";
    str += "<td>";
    if(data.webType == 0){
    	str += "已添加";
    }else if(data.webType == 1){
    	str += "已拒绝";
    }else if(data.webType == 2){
    	str += "<input class='btn btn-default btn-xs' type='button' value='添加' onclick='WebAdd(this)' />&nbsp;&nbsp;"
    	str += "<input class='btn btn-default btn-xs' type='button' value='拒绝' onclick='WebRefuse(this)' />";
    }
    str += "</td>";
    str += "</tr>";
    return str;
}

/**
 * 单个网站添加
 */
function WebAdd(obj){
	var id = $(obj).parent().parent().attr("rel");
	$.ajax({
		type : "post",
		url : "./servlet/WebsiteServlet?methodName=accept&ids=" + id,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "false" || r == "") {//查找失败
			} else if (r == "sessionOut") {//session过期
				indexDoLogout();
			} else if(r == "true"){
				GetMachineWebsiteList(1,10);
			}
		}
	});
}

/**
 * 批量网站添加
 */
function BatchWebAdd(obj){
	
	var idList = "";
	$("#websiteManageList .mismatchWebSiteChild").each(function(){
		if($(this).prop("checked")){
			idList += $(this).val() + "#";
		}
	});
	
	idList = encodeURIComponent(idList);
	
	$.ajax({
		type : "post",
		url : "./servlet/WebsiteServlet?methodName=accept&ids=" + idList,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "false" || r == "") {//查找失败
			} else if (r == "sessionOut") {//session过期
				indexDoLogout();
			} else if(r == "true"){
				alertText("添加成功！", 3500);
				GetMachineWebsiteList(1,10);
			}
		}
	});
}


/**
 * 单个网站拒绝
 */
function WebRefuse(obj){
	var id = $(obj).parent().parent().attr("rel");
	$.ajax({
		type : "post",
		url : "./servlet/WebsiteServlet?methodName=refuse&ids=" + id,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "false" || r == "") {//查找失败
			} else if (r == "sessionOut") {//session过期
				indexDoLogout();
			} else if(r == "true"){
				GetMachineWebsiteList(1,10);
			}
		}
	});
}

/**
 * 批量网站拒绝
 */
function BatchWebRefuse(obj){
	
	var idList = "";
	$("#websiteManageList .mismatchWebSiteChild").each(function(){
		if($(this).prop("checked")){
			idList += $(this).val() + "#";
		}
	});
	idList = encodeURIComponent(idList);
	
	$.ajax({
		type : "post",
		url : "./servlet/WebsiteServlet?methodName=refuse&ids=" + idList,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "false" || r == "") {//查找失败
			} else if (r == "sessionOut") {//session过期
				indexDoLogout();
			} else if(r == "true"){
				GetMachineWebsiteList(1,10);
				alertText("拒绝成功！", 3500);
			}
		}
	});
}

/**
 * 复选框全选全不选
 */
function WebsiteselectAll(){
	$("#websiteManage_mac .mismatchWebSite").change(function(){
			if($(this).prop("checked")){
				$("#websiteManage_mac .mismatchWebSiteChild").prop("checked",true);
			}else{
				$("#websiteManage_mac .mismatchWebSiteChild").prop("checked",false);
			}
		
	});
}



