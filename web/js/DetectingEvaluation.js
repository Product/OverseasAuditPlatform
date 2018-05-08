//检测评价模块 JS

// 页面校验
$().ready(function() {	
	//start validate
    $("#detectingEvaluationForm").validate({          
        rules: {
            mustName: {
                required: true, 
                byteRangeLength:[1,50]
            },
            mustDigits: {
                required: true,
                digits:true,
				min:0
            },
            mustDate: {
                required: true				
			}
        },
        // set error messages label
        errorElement: "lable",
        // set error messages place
        errorPlacement: function (error, element) {
            if (element.is(':radio') || element.is(':checkbox')) {
                var eid = element.attr('name');
                error.appendTo(element.parent("td"));
            } else {
                error.appendTo(element.closest("td"));
            }
        }
    })
});



//检查是否选取了网站
function DEvaluationWebsiteIsNull(){
    if ($.trim( $("#txt_detectingEvaluationWebsite").val()) == "") {
        $('#txt_detectingEvaluationWebsiteError').text("网站名不能为空");
        return false;
    }
    else{
        $('#txt_detectingEvaluationWebsiteError').text("");
    }
}

//跳转到首页
function ToDetectingEvaluationFirstPage() {
    GetDetectingEvaluationList(1);
}

//跳转到上一页
function ToDetectingEvaluationPreviousPage() {
    var pageIndex = (parseInt($("#detectingEvaluationPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#detectingEvaluationPaging .pageIndex").text()) - 1);
    GetDetectingEvaluationList(pageIndex);
}

//跳转到下一页
function ToDetectingEvaluationNextPage() {
    var pageIndex = ($("#detectingEvaluationPaging .pageIndex").text() == $(
			"#detectingEvaluationPaging .pageCount").text() ? parseInt($(
			"#detectingEvaluationPaging .pageIndex").text()) : parseInt($(
			"#detectingEvaluationPaging .pageIndex").text()) + 1);
    GetDetectingEvaluationList(pageIndex);
}

//跳转到尾页
function ToDetectingEvaluationLastPage() {
    GetDetectingEvaluationList(parseInt($("#detectingEvaluationPaging .pageCount").text()));
}

function GetDetectingEvaluationList(strPageIndex) {
    var strQuery = $("#txt_detectingEvaluationQuery").val();
    var strStraTime = $("#txt_detectingEvaluationBeginDate").val();
    var strEndTime = $("#txt_detectingEvaluationEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/DetectingEvaluationServlet?methodName=QueryList&strQuery=" + strQuery
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#DetectingEvaluationList").html('');
            if (r == "false") {
                $("#DetectingEvaluationList")
						.append("<tr><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#detectingEvaluationPaging .dataCount").text(data.total);
            $("#detectingEvaluationPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#detectingEvaluationPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.webList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + data.webList[i].Website + "</td>";
                str += "<td>" + data.webList[i].ProductNum + "</td>";
                str += "<td>" + data.webList[i].Complaints + "</td>";
                str += "<td>" + data.webList[i].OverallRating + "</td>";
                str += "<td>" + data.webList[i].Remark + "</td>";
                str += "<td>" + data.webList[i].EndTime + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='watchDetectingEvaluationDialog(\"edit\",\""
						+ data.webList[i].Id
				        + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDetectingEvaluationDialog(\"edit\",\""
						+ data.webList[i].Id
						+ "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
						+ data.webList[i].Id + "\", DelDetectingEvaluation)' /></td>";
                str += "</tr>";
                j++;
            }
            $("#DetectingEvaluationList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//通过ID删除检测评价
function DelDetectingEvaluation(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/DetectingEvaluationServlet?methodName=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetDetectingEvaluationList(1);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}

//查看检测评价
function watchDetectingEvaluationDialog(methodName, Id) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#DetectingEvaluationDom_Watch').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/DetectingEvaluationServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $("#hid_watchDetectingEvaluationParam").val("methodName=edit&id=" + Id);
                    $("#txt_watchdetectingEvaluationName").val(r.Website);
                    $("#txt_watchdetectingEvaluationProductNum").val(r.ProductNum);
                    $("#txt_watchdetectingEvaluationComplaints").val(r.Complaints);
                    $("#txt_watchdetectingEvaluationOverallRating").val(r.OverallRating);
                    $("#txt_watchdetectingEvaluationEndTime").val(r.EndTime);
                    $("#txt_watchdetectingEvaluationCreateTime").val(r.CreateTime);
                    $("#txt_watchdetectingEvaluationRemark").val(r.Remark); 
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_watchDetectingEvaluationParam").val("methodName=add");
        $("#txt_watchdetectingEvaluationName").val("");
		$("#txt_watchdetectingEvaluationProductNum").val("");
		$("#txt_watchdetectingEvaluationComplaint").val("");
		$("#txt_watchdetectingEvaluationOverallRating").val("");
        $("#txt_watchdetectingEvaluationRemark").val("");
		$("#txt_watchdetectingEvaluationEndTime").val("");
    }
}

//添加或编辑检测评价
function showDetectingEvaluationDialog(methodName, Id) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $("#detectingEvaluationDom_Edit").show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    
    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/DetectingEvaluationServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $("#hid_DetectingEvaluationParam").val("methodName=edit&id=" + Id);
                    $("#txt_detectingEvaluationWebsite").val(r.WebsiteId);
                    $("#txt_detectingEvaluationWebsite").text(r.Website);
                    $("#txt_detectingEvaluationProductNum").val(r.ProductNum);
                    $("#txt_detectingEvaluationComplaint").val(r.Complaints);
                    $("#txt_detectingEvaluationOverallRating").val(r.OverallRating);
                    $("#txt_detectingEvaluationRemark").val(r.Remark);
                    $("#txt_detectingEvaluationDate").val(r.EndTime);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_DetectingEvaluationParam").val("methodName=add");
        $("#txt_detectingEvaluationWebsite").val("");
        $("#txt_detectingEvaluationWebsite").text("");
		$("#txt_detectingEvaluationProductNum").val("");
		$("#txt_detectingEvaluationComplaint").val("");
		$("#txt_detectingEvaluationOverallRating").val("");
        $("#txt_detectingEvaluationRemark").val("");
		$("#txt_detectingEvaluationDate").val("");
    }
    DEvaluationWebsiteIsNull();
	$("#detectingEvaluationForm").valid()
}

//保存检测评价
function SaveDetectingEvaluation() {
    var strName = encode($("#txt_detectingEvaluationWebsite").val());
	var strProductNum = $("#txt_detectingEvaluationProductNum").val();
	var strComplaint = $("#txt_detectingEvaluationComplaint").val();
	var strOverallRating = $("#txt_detectingEvaluationOverallRating").val();
    var strRemark = encode($("#txt_detectingEvaluationRemark").val());
	var strEndTime = $("#txt_detectingEvaluationDate").val();
    DEvaluationWebsiteIsNull();
	var r = $("#detectingEvaluationForm").valid();
	if(!r){
        alertText("保存失败！", 3500);
		return false;
	}

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/DetectingEvaluationServlet?methodName="
            					+ getParam("hid_DetectingEvaluationParam", "methodName") + "&id=" + getParam("hid_DetectingEvaluationParam", "id")
                                + "&website=" + strName
                                + "&productNum=" + strProductNum
                                + "&complaints=" + strComplaint							
                                + "&overallRating=" + strOverallRating				
								+ "&remark=" + strRemark
								+ "&endTime=" + strEndTime
								+ "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateDataGatherInfo(data);
                $("#detectingEvaluationDom_Edit").animate({ width: "hide" });
                $('#DetectingEvaluationDom').show();
            } else {
                alertText("保存失败！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

/*
函数名：GetDetectingEvaluationTargetList
作用：获得网站列表
参数：data：网站分类的一条信息
idx:序列号
*/
function GetDetectingEvaluationTargetList(strPageIndex) {
    var strQuery = $("#txt_detectingEvaluationTargetQuery").val();
    var strStraTime = $("#txt_detectingEvaluationTargetBeginDate").val();
    var strEndTime = $("#txt_detectingEvaluationTargetEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/WebsiteServlet?methodName=QueryList&strQuery="
            					+ encode(strQuery) + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#detectingEvaluationWebsiteList").html('');
            if (r == "false") {
                $("#detectingEvaluationWebsiteList").append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#detectingEvaluationTargetPaging .dataCount").text(data.total);
            $("#detectingEvaluationTargetPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#detectingEvaluationTargetPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.websiteList.length; i++) {
                str += "<tr>";
                str += "<td><input type='radio' class='detectingEvaluationWeb' value='"+data.websiteList[i].Id+"' name='detectingEvaluationWebs' id='dg"+data.websiteList[i].Id+"'></td>";
                str += "<td><label for='dg"+data.websiteList[i].Id+"'>" + data.websiteList[i].Name + "</label></td>";
                str += "<td>" + data.websiteList[i].Location + "</td>";
                str += "<td>" + data.websiteList[i].WebStyle + "</td>";
                str += "<td>" + data.websiteList[i].Address + "</td>";
                str += "</tr>";
                j++;
            }
            $("#detectingEvaluationWebsiteList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//检测评价编辑-添加或更改网站
function GetDetectingEvaluationTargetDom(){
    $("#detectingEvaluationDom_Edit").animate({ width: "hide" });
    $("#detectingEvaluationDom_AddTarget").show();
    $("#txt_detectingEvaluationTargetQuery").val("");
    $("#txt_detectingEvaluationTargetBeginDate").val("");
    $("#txt_detectingEvaluationTargetEndDate").val("");
    $("#detectingEvaluationWebisiteList").html('');
}

//返回检测评价编辑
function GoBackDetectingEvaluationTarget(){
    $("#detectingEvaluationDom_AddTarget").animate({ width: "hide" });
    $("#detectingEvaluationDom_Edit").show();
    DEvaluationWebsiteIsNull();
}

//添加或更改网站-确定
function checkDetectingEvaluationTargetRadio(){
        var tmp = $("input[name='detectingEvaluationWebs']:checked");
        if(tmp.length == 0)
            alertText("没有选中网站！", 3500);
        else{
            $("#txt_detectingEvaluationWebsite").val(tmp.val());
            $('#txt_detectingEvaluationWebsite').text(tmp.parent().parent().find('label').text());
            GoBackDetectingEvaluationTarget();
        }
}

/**
 * @desc 跳转到网站列表首页
 */
function ToDetectingEvaluationWebsiteFirstPage() {
	GetDetectingEvaluationTargetList(1);
}

/**
 * @desc 跳转到网站列表上一页
 */
function ToDetectingEvaluationWebsitePreviousPage() {
    var pageIndex = (parseInt($("#detectingEvaluationTargetPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#detectingEvaluationTargetPaging .pageIndex").text()) - 1);
    GetDetectingEvaluationTargetList(pageIndex);
}

/**
 * @desc 跳转到网站列表下一页
 */
function ToDetectingEvaluationWebsiteNextPage() {
    var pageIndex = ($("#detectingEvaluationTargetPaging .pageIndex").text() == $(
			"#detectingEvaluationTargetPaging .pageCount").text() ? parseInt($(
			"#detectingEvaluationTargetPaging .pageIndex").text()) : parseInt($(
			"#detectingEvaluationTargetPaging .pageIndex").text()) + 1);
    GetDetectingEvaluationTargetList(pageIndex);
}

/**
 * @desc 跳转到网站列表尾页
 */
function ToDetectingEvaluationWebsitendPage() {
	GetDetectingEvaluationTargetList(parseInt($("#detectingEvaluationTargetPaging .pageCount").text()));
}