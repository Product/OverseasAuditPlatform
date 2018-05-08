/*
 * 事件影响网站JS
 */

//跳转到事件影响网站页面
function toEventAffectedWebsiteDom(id,name){
    $("#iframeMain .wrap").animate({ width: "hide" });
    $("#EventAffectedWebsiteDom").show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    $("#EventAffectedWebsiteList").html("");
    $("#hid_eventWebsiteParam").val("id="+id+"&name="+name);
}

//跳转到事件影响网站首页
function EventAffectedWebsiteFirstPage() {
    GetEventAffectedWebsiteList(1);
}

//跳转到事件影响网站上一页
function EventAffectedWebsitePreviousPage() {
	var pageIndex = (parseInt($("#EventAffectedWebsitePaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#EventAffectedWebsitePaging .pageIndex").text()) - 1);
    GetEventAffectedWebsiteList(pageIndex);
}

//跳转到事件影响网站下一页
function EventAffectedWebsiteNextPage() {
    var pageIndex = ($("#EventAffectedWebsitePaging .pageIndex").text() == $(
			"#EventAffectedWebsitePaging .pageCount").text() ? parseInt($(
			"#EventAffectedWebsitePaging .pageIndex").text()) : parseInt($(
			"#EventAffectedWebsitePaging .pageIndex").text()) + 1);
    GetEventAffectedWebsiteList(pageIndex);
}

//跳转到事件影响网站尾页
function EventAffectedWebsiteLastPage() {
    GetEventAffectedWebsiteList(parseInt($("#EventAffectedWebsitePaging .pageCount").text()));
}

//获取某页的所有受影响的网站
function GetEventAffectedWebsiteList(strPageIndex) {

	var strQuery=$("#txt_EventAffectedWebsiteQuery").val();
	
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/EventAffectedWebsiteServlet?methodName=WebsiteQueryList"
				+"&eventId="+getParam("hid_eventWebsiteParam", "id")
				+"&strQuery=" + strQuery + "&strPageIndex=" + strPageIndex 
				+ "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#EventAffectedWebsiteList").html('');
            if (r == "false") {
                $("#EventAffectedWebsiteList")
						.append("<tr><td colspan='4' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#EventAffectedWebsitePaging .dataCount").text(data.total);
            $("#EventAffectedWebsitePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#EventAffectedWebsitePaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.eventAffectedWebsiteList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + getParam("hid_eventWebsiteParam", "name") + "</td>";
                str += "<td>" + data.eventAffectedWebsiteList[i].WebsiteName + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showEventAffectedWebsiteDialog(\"editWebsite\",\""
						+ data.eventAffectedWebsiteList[i].Id + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
						+ data.eventAffectedWebsiteList[i].Id + "\", DelEventAffectedWebsite)' /></td>";
                str += "</tr>";
                j++;
            }
            $("#EventAffectedWebsiteList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//通过Id删除事件影响的网站 
function DelEventAffectedWebsite(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/EventAffectedWebsiteServlet?methodName=delWebsite&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetEventAffectedWebsiteList(1);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}

//添加或编辑事件影响的网站
function showEventAffectedWebsiteDialog(methodName, Id) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#EventAffectedWebsiteDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    
    if (methodName == "editWebsite") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/EventAffectedWebsiteServlet?methodName=initWebsite&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {   
                	$("#txt_eventAffectedWebsiteEventName").val(getParam("hid_eventWebsiteParam", "name"));
                	$("#hid_eventAffectedWebsiteParam").val("methodName=editWebsite&id=" + Id);
	                $('#txt_eventWebsite').val(r.websiteId);
	                $('#txt_eventWebsite').html(r.websiteName);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
    	$("#txt_eventAffectedWebsiteEventName").val(getParam("hid_eventWebsiteParam", "name"));
        $("#hid_eventAffectedWebsiteParam").val("methodName=addWebsite");
        $("#txt_eventWebsite").val("");
        $("#txt_eventWebsite").text("");
    }
}

//保存事件影响的网站
function SaveEventAffectedWebsite()
{
	var strEventId = getParam("hid_eventWebsiteParam", "id");
	var strWebsiteId = $("#txt_eventWebsite").val();
	var strWebsiteName = encode($("#txt_eventWebsite").html());
	
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EventAffectedWebsiteServlet?methodName="
            					+ getParam("hid_eventAffectedWebsiteParam", "methodName")
            					+"&id="+getParam("hid_eventWebsiteParam", "id") + "&eventId=" + strEventId
                                + "&websiteId=" + strWebsiteId +"&websiteName=" + strWebsiteName,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateDataGatherInfo(data);
                $("#EventAffectedWebsiteDom_Edit").animate({ width: "hide" });
                $('#EventAffectedWebsiteDom').show();
            } else {
                alertText("保存失败！网站重复或未选择网站", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

/*
函数名：GetEventWebsiteTargetList
作用：获得网站列表
*/
function GetEventWebsiteTargetList(strPageIndex) {
    var strQuery = $("#txt_eventWebsiteTargetQuery").val();
    var strStraTime = $("#txt_eventWebsiteTargetBeginDate").val();
    var strEndTime = $("#txt_eventWebsiteTargetEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/WebsiteServlet?methodName=QueryList&strQuery="
            					+ encode(strQuery) + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#eventWebsiteList").html('');
            if (r == "false") {
                $("#eventWebsiteList").append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#eventWebsiteTargetPaging .dataCount").text(data.total);
            $("#eventWebsiteTargetPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#eventWebsiteTargetPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.websiteList.length; i++) {
                str += "<tr>";
                str += "<td><input type='radio' class='eventWebsite' value='"+data.websiteList[i].Id+"' name='eventWebsites' id='dg"+data.websiteList[i].Id+"'></td>";
                str += "<td><label for='dg"+data.websiteList[i].Id+"'>" + data.websiteList[i].Name + "</label></td>";
                str += "<td>" + data.websiteList[i].Location + "</td>";
                str += "<td>" + data.websiteList[i].Address + "</td>";
                str += "<td>" + data.websiteList[i].CreateTime + "</td>";
                str += "<td>" + data.websiteList[i].Remark + "</td>";
                str += "</tr>";
                j++;
            }
            $("#eventWebsiteList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//网站列表首页
function EventWebsiteTargetFirstPage() {
	GetEventWebsiteTargetList(1);
}

//网站列表上一页
function EventWebsiteTargetPreviousPage() {
    var pageIndex = (parseInt($("#eventWebsiteTargetPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#eventWebsiteTargetPaging .pageIndex").text()) - 1);
    GetEventWebsiteTargetList(pageIndex);
}

//网站列表下一页
function EventWebsiteTargetNextPage() {
    var pageIndex = ($("#eventWebsiteTargetPaging .pageIndex").text() == $(
			"#eventWebsiteTargetPaging .pageCount").text() ? parseInt($(
			"#eventWebsiteTargetPaging .pageIndex").text()) : parseInt($(
			"#eventWebsiteTargetPaging .pageIndex").text()) + 1);
    GetEventWebsiteTargetList(pageIndex);
}
//网站列表尾页
function EventWebsiteTargetLastPage() {
	GetEventWebsiteTargetList(parseInt($("#eventWebsiteTargetPaging .pageCount").text()));
}

//事件影响网站编辑-添加或更改网站
function GetEventWebsiteTargetDom(){
    $("#EventAffectedWebsiteDom_Edit").animate({ width: "hide" });
    $('#eventWebsiteDom_AddTarget').show();
    $("#txt_eventWebsiteTargetQuery").val("");
    $("#txt_eventWebsiteTargetBeginDate").val("");
    $("#txt_eventWebsiteTargetEndDate").val("");
    $("#eventWebsiteList").html('');
}

//返回事件影响网站编辑页面
function GoBackEventWebsiteTarget(){
    $("#EventAffectedWebsiteList").html("");
    $("#eventWebsiteDom_AddTarget").animate({ width: "hide" });
    $('#EventAffectedWebsiteDom_Edit').show();
}

//添加或更改网站-确定
function checkEventWebsiteTargetRadio(){
    var tmp = $("input[name='eventWebsites']:checked");
    if(tmp.length == 0){
       alertText("没有选中网站！", 3500);
    }
    else{
    	$('#txt_eventWebsite').html(tmp.parent().parent().find('label').text());
    	$('#txt_eventWebsite').val(tmp.val());
        GoBackEventWebsiteTarget();
    }
}