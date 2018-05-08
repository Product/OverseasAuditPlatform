function FirstPage_ExpertAnswer() {
    GetPersonalInformationList(1);
}

function PreviousPage_ExpertAnswer() {
    var pageIndex = (parseInt($("#ExpertAnswerPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#ExpertAnswerPaging .pageIndex").text()) - 1);
    GetExpertAnswerList(pageIndex);
}

function NextPage_ExpertAnswer() {
    var pageIndex = ($("#ExpertAnswerPaging .pageIndex").text() == $(
			"#ExpertAnswerPaging .pageCount").text() ? parseInt($(
			"#ExpertAnswerPaging .pageIndex").text()) : parseInt($(
			"#ExpertAnswerPaging .pageIndex").text()) + 1);
    GetExpertAnswerList(pageIndex);
}

function EndPage_ExpertAnswer() {
    GetExpertAnswerList(parseInt($("#ExpertAnswerPaging .pageCount").text()));
}

function GetExpertAnswerEntry(data, idx){
	var str = "<tr>";
    str += "<td>" + (i + 1) + "</td>";
    str += "<td>" + data.Title + "</td>";
    str += "<td>" + data.Content + "</td>";
    if(data.ReturnContent!="null")
    	str += "<td>" + data.ReturnContent + "</td>";
    else
    	str += "<td>" +"未回复" + "</td>";
    if(data.ReturnTime!="null")
    	str += "<td>" + data.ReturnTime + "</td>";
    else
    	str += "<td>" +"未回复" + "</td>";
    str += "<td>" + data.Consultant + "</td>";
    str += "<td>" + data.ConsultiveTime + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='详 情' onclick='Answer_Detail(\"Expert\",\""+ data.Id+ "\")' /></td>";
    str += "</tr>";
    return str;
}
function GetExpertAnswerList(strPageIndex)
{
	var strQuery = $("#txt_ExpertAnswerQuery").val();
    var strStraTime = $("#txt_ExpertAnswerBeginDate").val();
    var strEndTime = $("#txt_ExpertAnswerEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ConsultiveServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#ExpertAnswerList").html('');
            if (r == "false") {
                $("#ExpertAnswerList")
						.append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#ExpertAnswerPaging .dataCount").text(data.total);
            $("#ExpertAnswerPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#ExpertAnswerPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
                str +=GetExpertAnswerEntry(data.webList[i], i+1);
            }
            $("#ExpertAnswerList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}