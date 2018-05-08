function riskManFirstPage() {
    GetRiskManList(1);
}

function riskManPreviousPage() {
    var pageIndex = (parseInt($("#riskManage_macPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#riskManage_macPaging .pageIndex").text()) - 1);
    GetRiskManList(pageIndex);
}

function riskManNextPage() {
    var pageIndex = ($("#riskManage_macPaging .pageIndex").text() == $(
			"#riskManage_macPaging .pageCount").text() ? parseInt($(
			"#riskManage_macPaging .pageIndex").text()) : parseInt($(
			"#riskManage_macPaging .pageIndex").text()) + 1);
    GetRiskManList(pageIndex);
}

function riskManEndPage() {
    GetRiskManList(parseInt($("#riskManage_macPaging .pageCount").text()));
}

/*获取风险管理表项*/
function GetRiskManEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.ProductName + "</td>";
    str += "<td>" + data.Url + "</td>";
    str += "<td>" + data.WebName + "</td>";
    str += "<td>" + data.CreateTime.substring(0,19) + "</td>";
    str += "<td>" + data.RiskLevel + "</td>";
    str += "<td>" + data.RiskSuggest + "</td>";
    str += "</tr>";
    return str;
}

/*
 函数名：GetRiskManList
 作用：获取风险管理列表
 */
function GetRiskManList(strPageIndex) {
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/MismatchProductServlet?methodName=QueryList&module=All&choiceLength=0&choices=&orderBy=CREATE_TIME&orderType=2&pageIndex="
				+ strPageIndex + "&pageSize=10",
        contentType: "application/json;charset=utf-8",
        dataType : "text",
        async: false,
        success: function (r) {
            $("#riskManage_macList").html('');
            if (r == "false") {
                $("#riskManage_macList")
						.html("<tr class='noDataSrc'><td colspan='7' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r =="sessionOut"){
            	doLogout();

            }
            var data = $.parseJSON(r);
			$("#riskManage_macPaging .dataCount").text(data.Total);
			$("#riskManage_macPaging .pageCount").text(parseInt((parseInt(data.Total) + 9) / 10));
			$("#riskManage_macPaging .pageIndex").text(strPageIndex);

            if (data.DataList.length > 0 ) {
                var str = "";
                for (var i = 0; i < data.DataList.length; i++) {
                    str += GetRiskManEntry(data.DataList[i], i+1);
                }
                $("#riskManage_macList").html(str);
            }
            else {
                $("#riskManage_macList")
                    .html("<tr class='noDataSrc'><td colspan='7' style='text-align:center;'>无数据！</td></tr>");
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//风险管理-网站风险
function getWebRisk(strPageIndex,pageSize){
	$("#riskManage_mac").hide();
	$("#WebRiskManage_mac").show();
	$.ajax({
	       type: "post",
	       url: projectLocation
			+ "servlet/MismatchProductServlet?methodName=websiteEvaluation&pageIndex="+ strPageIndex+"&pageSize="+pageSize,
	       contentType: "application/json;charset=utf-8",
	       async: false,
	       success: function (r) {
	           $("#WebRiskManage_macList").html('');
	           if (r == "false") {
	               $("#WebRiskManage_macList")
							.append("<tr class='noDataSrc'><td colspan='4' style='text-align:center;'>无数据！</td></tr>");
	               return false;
	           }else if(r =="sessionOut"){
	           		doLogout();
	           	
	           }
	           var data = $.parseJSON(r);
				$("#WebRiskManage_macPaging .dataCount").text(data.total);
				$("#WebRiskManage_macPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
				$("#WebRiskManage_macPaging .pageIndex").text(strPageIndex);
				
	           var str = "";
	           var j = 0;
	           for (var i = 0; i < data.list.length; i++) {
	           	 str += GetWebRiskEntry(data.list[i], i+1);
	           }
	           $("#WebRiskManage_macList").append(str);
	       },
	       error: function (e) {
	           alert(e.responseText);
	       }
	   });
}

function GetWebRiskEntry(data,idx){
	 var str = "<tr>";
	    str += "<td>" + idx + "</td>";
	    str += "<td>" + data.websiteName + "</td>";
	    str += "<td>" + data.result + "</td>";
	    str += "<td>" + data.suggestion + "</td>";
	    str += "</tr>";
	    return str;
}

function WebRiskManFirstPage() {
	getWebRisk(1,10);
}

function WebRiskManPreviousPage() {
    var pageIndex = (parseInt($("#WebRiskManage_macPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#WebRiskManage_macPaging .pageIndex").text()) - 1);
    getWebRisk(pageIndex,10);
}

function WebRiskManNextPage() {
    var pageIndex = ($("#WebRiskManage_macPaging .pageIndex").text() == $(
			"#WebRiskManage_macPaging .pageCount").text() ? parseInt($(
			"#WebRiskManage_macPaging .pageIndex").text()) : parseInt($(
			"#WebRiskManage_macPaging .pageIndex").text()) + 1);
    getWebRisk(pageIndex,10);
}

function WebRiskManEndPage() {
	getWebRisk(parseInt($("#WebRiskManage_macPaging .pageCount").text()),10);
}