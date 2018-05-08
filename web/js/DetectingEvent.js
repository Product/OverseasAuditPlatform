//样品检测模块 JS

// 页面校验
$().ready(function() {
    //start validate
    $("#detectingEventForm").validate({     
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
function DEventWebsiteIsNull(){
    if ($.trim( $("#txt_detectingEventWebsite").val()) == "") {
        $('#txt_detectingEventWebsiteError').text("网站名不能为空");
        return false;
    }
    else{
        $('#txt_detectingEventWebsiteError').text("");
    }
}

//跳转到首页
function ToDetectingEventFirstPage() {
    GetDetectingEventList(1);
}

//跳转到上一页
function ToDetectingEventPreviousPage() {
    var pageIndex = (parseInt($("#DetectingEventPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#DetectingEventPaging .pageIndex").text()) - 1);
    GetDetectingEventList(pageIndex);
}

//跳转到下一页
function ToDetectingEventNextPage() {
    var pageIndex = ($("#DetectingEventPaging .pageIndex").text() == $(
			"#DetectingEventPaging .pageCount").text() ? parseInt($(
			"#DetectingEventPaging .pageIndex").text()) : parseInt($(
			"#DetectingEventPaging .pageIndex").text()) + 1);
    GetDetectingEventList(pageIndex);
}

//跳转到尾页
function ToDetectingEventLastPage() {
    GetDetectingEventList(parseInt($("#DetectingEventPaging .pageCount").text()));
}

//获取某页的所有检测事件
function GetDetectingEventList(strPageIndex) {
    var strQuery = $("#txt_detectingEventQuery").val();
    var strStraTime = $("#txt_detectingEventBeginDate").val();
    var strEndTime = $("#txt_detectingEventEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/DetectingServlet?methodName=QueryList&strQuery=" + strQuery
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
  				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#DetectingEventList").html('');
            if (r == "false") {
                $("#DetectingEventList")
						.append("<tr><td colspan='7' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#DetectingEventPaging .dataCount").text(data.total);
            $("#DetectingEventPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#DetectingEventPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.webList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + data.webList[i].Website + "</td>";
                str += "<td>" + data.webList[i].EventType + "</td>";
                str += "<td>" + data.webList[i].ProductNum + "</td>";
                str += "<td>" + data.webList[i].DetectingDate + "</td>";
                str += "<td>" + data.webList[i].Remark + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='watchDetectingEventDialog(\"init\",\""
						+ data.webList[i].Id
				        + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDetectingEventDialog(\"edit\",\""
						+ data.webList[i].Id
						+ "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
						+ data.webList[i].Id + "\", DelDetectingEvent)' /></td>";
                str += "</tr>";
                j++;
            }
            $("#DetectingEventList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//通过Id删除
function DelDetectingEvent(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/DetectingServlet?methodName=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetDetectingEventList(1);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}

//查看某检测事件的详细信息
function watchDetectingEventDialog(methodName, Id) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#DetectingEventDom_Watch').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

        $.ajax({
            type: "post",
            url: projectLocation + "servlet/DetectingServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
            	 if (r != "false") {
                     $("#hid_DetectingEventWatch").val("methodName=init&id=" + Id);
                     $("#DetectingEventWatch_Website").val(r.Website);
                     $("#DetectingEventWatch_EventType").val(r.EventType);
                     $("#DetectingEventWatch_ProductNum").val(r.ProductNum);
                     $("#DetectingEventWatch_DetectingDate").val(r.DetectingDate);
                     $("#DetectingEventWatch_CreateTime").val(r.CreateTime);
                     $("#DetectingEventWatch_Remark").val(r.Remark);
                 }
                 else if(r =="sessionOut"){
                 	doLogout();
                 	
                 }
             },
             error: function (e) {
                 alert(e.responseText);
             }
         });
}

//添加或编辑检测事件
function showDetectingEventDialog(methodName, Id) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#detectingEventDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    $('#txt_detectingEventWebsiteError').text("");
    
    if (methodName == "edit") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/DetectingServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {        		
                	$("#hid_DetectingEventParam").val("methodName=edit&id=" + Id);
                   	$('#txt_detectingEventWebsite').val(r.WebsiteId);
                    $('#txt_detectingEventWebsite').text(r.Website);
	                $("#txt_detectingEventType").val(r.EventType);
	                $("#txt_detectingProductNum").val(r.ProductNum);
	                $("#txt_detectingEventDate").val(r.DetectingDate);
	                $("#txt_detectingEventRemark").val(r.Remark);
                }
            },
            error: function (e) {
                alert("初始化失败，"+e.responseText);
            }
        });
    } else {
        $("#hid_DetectingEventParam").val("methodName=add");
        $("#txt_detectingEventWebsite").val("");
        $('#txt_detectingEventWebsite').text("");
        $("#txt_detectingEventType").val("");
        $("#txt_detectingProductNum").val("");
        $("#txt_detectingEventDate").val("");
        $("#txt_detectingEventRemark").val("");
    }
    DEventWebsiteIsNull();
	$("#detectingEventForm").valid()
}

//保存检测事件
function SaveDetectingEvent()
{
	var strName = encode($("#txt_detectingEventWebsite").val());
	var strEventType = encode($("#txt_detectingEventType").val());
	var strEventDate = $("#txt_detectingEventDate").val();
	var strProductNum = $("#txt_detectingProductNum").val();
    var strRemark = encode($("#txt_detectingEventRemark").val());
    DEventWebsiteIsNull();
	var r = $("#detectingEventForm").valid();
	if(!r){
        alertText("保存失败！", 3500);
		return false;
	}
	
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/DetectingServlet?methodName="
            					+ getParam("hid_DetectingEventParam", "methodName") + "&id=" + getParam("hid_DetectingEventParam", "id")
                                + "&website=" + strName
                                + "&eventType=" + strEventType
                                + "&productNum=" + strProductNum
                                + "&detectingTime=" + strEventDate
								+ "&remark=" + strRemark
								+ "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateDataGatherInfo(data);
                $("#detectingEventDom_Edit").animate({ width: "hide" });
                $('#DetectingEventDom').show();
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
函数名：GetDetectingEventTargetList
作用：获得网站列表
参数：strPageIndex:页码
*/
function GetDetectingEventTargetList(strPageIndex) {
    var strQuery = $("#txt_detectingEventTargetQuery").val();
    var strStraTime = $("#txt_detectingEventTargetBeginDate").val();
    var strEndTime = $("#txt_detectingEventTargetEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/WebsiteServlet?methodName=QueryList&strQuery="
            					+ encode(strQuery) + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#detectingEventWebisiteList").html('');
            if (r == "false") {
                $("#detectingEventWebisiteList").append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#detectingEventTargetPaging .dataCount").text(data.total);
            $("#detectingEventTargetPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#detectingEventTargetPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.websiteList.length; i++) {
                str += "<tr>";
                str += "<td><input type='radio' class='detectingEventWeb' value='"+data.websiteList[i].Id+"' name='detectingEventWebs' id='dg"+data.websiteList[i].Id+"'></td>";
                str += "<td><label for='dg"+data.websiteList[i].Id+"'>" + data.websiteList[i].Name + "</label></td>";
                str += "<td>" + data.websiteList[i].Location + "</td>";
                str += "<td>" + data.websiteList[i].WebStyle + "</td>";
                str += "<td>" + data.websiteList[i].Address + "</td>";
                str += "</tr>";
                j++;
            }
            $("#detectingEventWebisiteList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//检测事件编辑-添加或更改网站
function GetDetectingEventTargetDom(){
    $("#detectingEventDom_Edit").animate({ width: "hide" });
    $('#DetectingEventDom_AddTarget').show();
    $("#txt_detectingEventTargetQuery").val("");
    $("#txt_detectingEventTargetBeginDate").val("");
    $("#txt_detectingEventTargetEndDate").val("");
    $("#detectingEventWebisiteList").html('');
}

//返回检测事件编辑页面
function GoBackDetectingEventTarget(){
    $("#DetectingEventDom_AddTarget").animate({ width: "hide" });
    $('#detectingEventDom_Edit').show();
    DEventWebsiteIsNull();
}

//添加或更改网站-确定
function checkDetectingEventTargetRadio(){
    var tmp = $("input[name='detectingEventWebs']:checked");
    if(tmp.length == 0){
       alertText("没有选中网站！", 3500);
    }
    else{
        $('#txt_detectingEventWebsite').val(tmp.val());
        $('#txt_detectingEventWebsite').text(tmp.parent().parent().find('label').text());
        GoBackDetectingEventTarget();
    }
}

/**
 * @desc 跳转到网站列表首页
 */
function ToDetectingEventWebsiteFirstPage() {
	GetDetectingEventTargetList(1);
}

/**
 * @desc 跳转到网站列表上一页
 */
function ToDetectingEventWebsitePreviousPage() {
    var pageIndex = (parseInt($("#detectingEventTargetPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#detectingEventTargetPaging .pageIndex").text()) - 1);
    GetDetectingEventTargetList(pageIndex);
}

/**
 * @desc 跳转到网站列表下一页
 */
function ToDetectingEventWebsiteNextPage() {
    var pageIndex = ($("#detectingEventTargetPaging .pageIndex").text() == $(
			"#detectingEventTargetPaging .pageCount").text() ? parseInt($(
			"#detectingEventTargetPaging .pageIndex").text()) : parseInt($(
			"#detectingEventTargetPaging .pageIndex").text()) + 1);
    GetDetectingEventTargetList(pageIndex);
}

/**
 * @desc 跳转到网站列表尾页
 */
function ToDetectingEventWebsitendPage() {
	GetDetectingEventTargetList(parseInt($("#detectingEventTargetPaging .pageCount").text()));
}