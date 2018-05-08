function FirstPage_ComplaintHandling() {
    GetComplaintHandlingList(1);
}

function PreviousPage_ComplaintHandling() {
    var pageIndex = (parseInt($("#ComplaintHandlingPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#ComplaintHandlingPaging .pageIndex").text()) - 1);
    GetComplaintHandlingList(pageIndex);
}

function NextPage_ComplaintHandling() {
    var pageIndex = ($("#ComplaintHandlingPaging .pageIndex").text() == $("#ComplaintHandlingPaging .pageCount").text() ? parseInt($("#ComplaintHandlingPaging .pageIndex").text()) : parseInt($("#ComplaintHandlingPaging .pageIndex").text()) + 1);
    GetComplaintHandlingList(pageIndex);
}

function EndPage_ComplaintHandling() {
    GetComplaintHandlingList(parseInt($("#ComplaintHandlingPaging .pageCount").text()));
}

function GetComplaintHandlingEntry(data, idx){
	var str = "<tr>";
    str += "<td>" + (i + 1) + "</td>";
    str += "<td>" + data.Title + "</td>";
    str += "<td>" + data.Content + "</td>";
    if(data.ReturnContent!="null")
    {
    	str += "<td>" + data.ReturnContent + "</td>";
    	str += "<td>" + data.ReturnTime + "</td>";
    	str += "<td>" + data.Consultant + "</td>";
        str += "<td>" + data.ConsultiveTime + "</td>";
        str += "<td>已处理</td>";
    }
    else
    {
    	str += "<td>" +"未处理" + "</td>";
    	str += "<td>" +"未处理" + "</td>";
        str += "<td>" + data.Consultant + "</td>";
        str += "<td>" + data.ConsultiveTime + "</td>";
        str += "<td><button class='btn btn-default btn-xs' type='button' onclick='showDialog_ComplaintHandling(\"ComplainHanding\",\""
				+ data.Id
				+ "\",this)' >处 理</button></td>";
    }
    str += "</tr>";
    return str;
}
function GetComplaintHandlingList(strPageIndex)
{
	var strQuery = $("#txt_ComplaintHandlingQuery").val();
    var strStraTime = $("#txt_ComplaintHandlingBeginDate").val();
    var strEndTime = $("#txt_ComplaintHandlingEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ConsultiveServlet?methodName=Complain_QueryList&strQuery=" + encode(strQuery)
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#ComplaintHandlingList").html('');
            if (r == "false") {
                $("#ComplaintHandlingList")
						.append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#ComplaintHandlingPaging .dataCount").text(data.total);
            $("#ComplaintHandlingPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#ComplaintHandlingPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
                str +=GetComplaintHandlingEntry(data.webList[i],i+1);
            }
            $("#ComplaintHandlingList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function showDialog_ComplaintHandling(methodName,Id,pos)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#ComplaintHandling_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

        $.ajax({
            type: "post",
            url: projectLocation + "servlet/ConsultiveServlet?methodName=ComplainHanding&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
            	$(pos).parent().parent().addClass('editObject');
            	$("#ComplainHandingDetail").html('');
                if (r == "false") {
                    return false;
                }
                var str = "";
                str += "<tr>";
        	    str += "<td  align=\"right\" class=\"tab_th\">" +"投诉对象"+ "</td>";
        	    str += "<td>" + r.Title + "</td>";
        	    str += "</tr>";
                str += "<tr>";
    	        str += "<td  align=\"right\" class=\"tab_th\">" +"投诉内容" + "</td>";
    	        str += "<td>" + r.Content + "</td>";
    	        str += "</tr>";
    	        str += "<tr><td align=\"right\" class=\"tab_th\">处理</td><td>";
    	        str += "<textarea id=\"txt_HandingContent\" style=\"width: 300px;\" rows=\"3\" runat=\"server\" cols=\"20\" name=\"S1\"></textarea></td></tr>"
    	        str += "<button class='btn btn-default btn-xs' type='button' onclick='Save_ComplaintHandling(\"Handing\",\""
						+ r.Id
						+ "\")' >提 交</button>";
    	        str += "<tr><td></td>";
                str += "<td colspan=\"2\" style=\"padding-left: 102px; padding-top: 10px;\">" +
                		"<input type=\"button\" value=\"提 交\" onclick='SaveComplaintHandling(\"" + r.Id + "\")' class=\"submit_btn\"/>" +
                		"&nbsp;&nbsp;<input type=\"button\" value=\"返 回\" rel=\"ComplaintHandlingDom\" onclick=\"toUrl(this)\" class=\"goback_btn\"/>" +
                		"</td></tr>";
    	        $("#ComplainHandingDetail").append(str);
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}

function SaveComplaintHandling(Id)
{
	var strHanding= $('#txt_HandingContent').val();
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/ConsultiveServlet?methodName=Handing&id=" + Id + "&Handing="+ encode(strHanding),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	alertText("处理失败！", 3500);
            } else {
            	alertText("处理成功！", 3500);
            	var data = $.parseJSON(r);
            	UpdateComplaintHandling(data);
            	$('#ComplaintHandling_Edit').animate({ width: "hide" });
                $("#ComplaintHandlingDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateComplaintHandling(data)
{
	var obj =  $("#ComplaintHandlingList").find('.editObject').find('td');
	obj.eq(1).html(data.Title);
    obj.eq(2).html(data.Content);
    obj.eq(3).html(data.ReturnContent);
    obj.eq(4).html(data.ReturnTime);
    obj.eq(5).html(data.Consultant);
    obj.eq(6).html(data.ConsultiveTime);
    obj.eq(7).html("已处理");
    $("#ComplaintHandlingList").find('.editObject').removeClass('editObject');
}