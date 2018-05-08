function FirstPageVentureAnalysisReport() {
    GetVentureAnalysisReportList(1);
}

function PreviousPageVentureAnalysisReport() {
    var pageIndex = (parseInt($("#VentureAnalysisReportListPaging .pageIndex").text()) == 1 ? 1 
    		: parseInt($("#VentureAnalysisReportListPaging .pageIndex").text()) - 1);
    GetVentureAnalysisReportList(pageIndex);
}

function NextPageVentureAnalysisReport() {
    var pageIndex = ($("#VentureAnalysisReportListPaging .pageIndex").text() == $(
    		"#VentureAnalysisReportListPaging .pageCount").text() ? parseInt($(
    		"#VentureAnalysisReportListPaging .pageIndex").text()) : parseInt($(
    		"#VentureAnalysisReportListPaging .pageIndex").text()) + 1);
    GetVentureAnalysisReportList(pageIndex);
}

function EndPageVentureAnalysisReport() {
    GetVentureAnalysisReportList(parseInt($("#VentureAnalysisReportListPaging .pageCount").text()));
}

function GetVentureAnalysisReportList(strPageIndex) {
    var strQuery = $("#txt_VentureAnalysisReportListQuery").val();
    var strStraTime = $("#txt_VentureAnalysisReportListBeginDate").val();
    var strEndTime = $("#txt_VentureAnalysisReportListEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/VentureAnalysisServlet?methodName=QueryList&strQuery="
            					+ strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&str" +
                                		"PageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#VentureAnalysisReportList").html('');
            if (r == "false") {
                $("#VentureAnalysisReportList").append("<tr><td colspan='8' style='text-align:center;'>无数据</td></tr>");
                return false;
            }
            else if (r == "sessionOut") {
                doLogout();
            }
            else{
            var data = $.parseJSON(r);

            $("#VentureAnalysisReportListPaging .dataCount").text(data.total);
            $("#VentureAnalysisReportListPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#VentureAnalysisReportListPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.webList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + data.webList[i].Name + "</td>";
                str += "<td>" + data.webList[i].Template + "</td>";
                str += "<td>" + data.webList[i].Illustration + "</td>";
                str += "<td>" + data.webList[i].ReleaseState + "</td>";
                str += "<td>" + data.webList[i].CreatePerson + "</td>";
                str += "<td>" + data.webList[i].CreateTime.substring(0, 19) + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showDialogVentureAnalysisReport(\"detail\",\""
					+ data.webList[i].Id
					+ "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
					+ data.webList[i].Id + "\",DelVentureAnalysisReport)' /></td>";
                str += "</tr>";
                j++;
            }
            $("#VentureAnalysisReportList").append(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}


function OutReportDialog() {
	alert("报告输出中...");
}



function DelVentureAnalysisReport(id) {
		$.ajax({
			type : "post",
			url : projectLocation
					+ "servlet/VentureAnalysisServlet?methodName=del&id=" + id,
			dataType : "text",
			async : false,
			success : function(r) {
				if (r == "true") {
					alertText("删除成功！",3500);
					GetVentureAnalysisReportList(1);
				}
				else if (r == "sessionOut") {
	                doLogout();
	            }
				else{
					alert("删除失败！");
				}
			},
			error : function(e) {
				alert(e.responseText);
			}
		});
	}


function showDialogVentureAnalysisReport(methodName, Id) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#VentureAnalysisReportDom_Detail').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/VentureAnalysisServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $("#hid_VentureAnalysisReportParam").val("methodName=edit&id=" + Id);
                    $("#txt_VentureAnalysisReportReportName").val(r.Name);
                    $("#txt_VentureAnalysisReportReportTemplate").val(r.Template);
                    $("#txt_VentureAnalysisReportIllustration").val(r.Illustration);
                    $("#txt_VentureAnalysisReportReleaseState").val(r.ReleaseState);
                    $("#txt_VentureAnalysisReportCreatePerson").val(r.CreatePerson);
                    $("#txt_VentureAnalysisReportCreateTime").val(r.CreateTime);
                }
                else if (r == "sessionOut") {
                    doLogout();
                }
    			else{
    				alert("编辑失败！");
    			}
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_VentureAnalysisReportParam").val("methodName=add");
        $("#txt_VentureAnalysisReportReportName").val("");
        $("#txt_VentureAnalysisReportReportTemplate").val("");
        $("#txt_VentureAnalysisReportIllustration").val("");
        $("#txt_VentureAnalysisReportReleaseState").val("");
        $("#txt_VentureAnalysisReportCreatePerson").val("");
        $("#txt_VentureAnalysisReportCreateTime").val("");
    } 
}

