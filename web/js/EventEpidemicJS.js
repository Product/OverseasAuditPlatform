function FirstPage_EventEpidemic() {
    GetEventEpidemicList(1);
}

function PreviousPage_EventEpidemic() {
    var pageIndex = (parseInt($("#EventEpidemicPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#EventEpidemicPaging .pageIndex").text()) - 1);
    GetEventEpidemicList(pageIndex);
}

function NextPage_EventEpidemic() {
    var pageIndex = ($("#EventEpidemicPaging .pageIndex").text() == $("#EventEpidemicPaging .pageCount").text() ? parseInt($("#EventEpidemicPaging .pageIndex").text()) : parseInt($("#EventEpidemicPaging .pageIndex").text()) + 1);
    GetEventEpidemicList(pageIndex);
}

function EndPage_EventEpidemic() {
    GetEventEpidemicList(parseInt($("#EventEpidemicPaging .pageCount").text()));
}

//将得到的数据转变成html格式代码字符串
function GetEventEpidemicEntry(data, idx){
    var str = "<tr>";
    str += "<td><input type='radio' class='EventEpidemic' value='"+data.Id+"' name='EventEpidemic' id='ee"+data.Id+"'></td>";
	str += "<td><label for='ee"+data.Id+"'>" + data.Title + "</label></td>";
	str += "<td>" + data.Content + "</td>";
	str += "<td>" + data.Remark + "</td>";
	str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
	str += "</tr>";
    return str;
}

function GetEventEpidemicList(strPageIndex){
	//关键字查询
	var strQuery = $("#txt_EventEpidemicQuery").val();
	//创建开始时间查询
	var strStraTime = $("#txt_EventEpidemicBeginDate").val();
	//创建结束时间查询
	var strEndTime = $("#txt_EventEpidemicEndDate").val();
	
	$.ajax({
				type : "post",
				url : projectLocation
						+ "servlet/EpidemicServlet?methodName=QueryList&strQuery="
						+ strQuery + "&strStraTime=" + strStraTime
						+ "&strEndTime=" + strEndTime + "&strPageIndex="
						+ strPageIndex + "&strPageCount=10",
				dataType : "text",
				async : false,
				success : function(r) {
					$("#EventEpidemicList").html('');
					if (r == "false") {
						$("#EventEpidemicList").append("<tr class='noDataSrc'><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
						return false;
					}
					else if (r == "sessionOut") {
		                doLogout();
		            }
					var data = $.parseJSON(r);
					$("#EventEpidemicPaging .dataCount").text(data.total);
					$("#EventEpidemicPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
					$("#EventEpidemicPaging .pageIndex").text(strPageIndex);
					var str = "";
					for (var i = 0; i < data.EpidemicList.length; i++){
						 str += GetEventEpidemicEntry(data.EpidemicList[i], i+1);
					}
					$("#EventEpidemicList").append(str);
				},
				error : function(e) {
					alert(e.responseText);
				}
			});
}