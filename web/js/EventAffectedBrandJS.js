/*
 * 事件影响品牌JS
 */

//跳转到影响品牌页面
function toEventAffectedBrandDom(id,name){
    $("#iframeMain .wrap").animate({ width: "hide" });
    $("#EventAffectedBrandDom").show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    $("#EventAffectedBrandList").html("");
    $("#hid_eventBrandParam").val("id="+id+"&name="+name);
}

//跳转到影响品牌首页
function EventAffectedBrandFirstPage() {
    GetEventAffectedBrandList(1);
}

//跳转到影响品牌上一页
function EventAffectedBrandPreviousPage() {
    var pageIndex = (parseInt($("#EventAffectedBrandPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#EventAffectedBrandPaging .pageIndex").text()) - 1);
    GetEventAffectedBrandList(pageIndex);
}

//跳转到影响品牌下一页
function EventAffectedBrandNextPage() {
    var pageIndex = ($("#EventAffectedBrandPaging .pageIndex").text() == $(
			"#EventAffectedBrandPaging .pageCount").text() ? parseInt($(
			"#EventAffectedBrandPaging .pageIndex").text()) : parseInt($(
			"#EventAffectedBrandPaging .pageIndex").text()) + 1);
    GetEventAffectedBrandList(pageIndex);
}

//跳转到影响品牌尾页
function EventAffectedBrandLastPage() {
    GetEventAffectedBrandList(parseInt($("#EventAffectedBrandPaging .pageCount").text()));
}

//获取某页的所有受影响品牌
function GetEventAffectedBrandList(strPageIndex) {

	var strQuery=$("#txt_EventAffectedBrandQuery").val();
	
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/EventAffectedBrandServlet?methodName=BrandQueryList"
				+"&eventId="+getParam("hid_eventBrandParam", "id")
				+"&strQuery=" + strQuery + "&strPageIndex=" + strPageIndex 
				+ "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#EventAffectedBrandList").html('');
            if (r == "false") {
                $("#EventAffectedBrandList")
						.append("<tr><td colspan='4' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#EventAffectedBrandPaging .dataCount").text(data.total);
            $("#EventAffectedBrandPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#EventAffectedBrandPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.eventAffectedBrandList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + getParam("hid_eventBrandParam", "name") + "</td>";
                str += "<td>" + data.eventAffectedBrandList[i].BrandName + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showEventAffectedBrandDialog(\"editBrand\",\""
						+ data.eventAffectedBrandList[i].Id + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
						+ data.eventAffectedBrandList[i].Id + "\", DelEventAffectedBrand)' /></td>";
                str += "</tr>";
                j++;
            }
            $("#EventAffectedBrandList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//通过Id删除影响品牌 
function DelEventAffectedBrand(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/EventAffectedBrandServlet?methodName=delBrand&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetEventAffectedBrandList(1);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}

//添加或编辑影响品牌
function showEventAffectedBrandDialog(methodName, Id) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#EventAffectedBrandDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    
    if (methodName == "editBrand") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/EventAffectedBrandServlet?methodName=initBrand&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {   
                	$("#txt_eventAffectedBrandEventName").val(getParam("hid_eventBrandParam", "name"));
                	$("#hid_eventAffectedBrandParam").val("methodName=editBrand&id=" + Id);
	                $("#txt_eventBrand").val(r.BrandId);
	                $("#txt_eventBrand").text(r.BrandName);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
    	$("#txt_eventAffectedBrandEventName").val(getParam("hid_eventBrandParam", "name"));
        $("#hid_eventAffectedBrandParam").val("methodName=addBrand");
        $("#txt_eventBrand").val("");
        $("#txt_eventBrand").text("");
    }
}

//保存影响品牌
function SaveEventAffectedBrand()
{
	var strId =getParam("hid_eventAffectedBrandParam", "id");
	var strEventId = getParam("hid_eventBrandParam", "id");
	var strBrandId = $("#txt_eventBrand").val();
	var strBrandName =encode($("#txt_eventBrand").html());
	
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EventAffectedBrandServlet?methodName="
            					+ getParam("hid_eventAffectedBrandParam", "methodName")
            					+"&id="+strId + "&eventId=" + strEventId
                                + "&brandId=" + strBrandId +"&brandName=" + strBrandName,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateDataGatherInfo(data);
                $("#EventAffectedBrandDom_Edit").animate({ width: "hide" });
                $('#EventAffectedBrandDom').show();
            } else {
                alertText("保存失败！品牌重复或未选择品牌", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

/*
函数名：GetEventBrandTargetList
作用：获得品牌列表
*/
function GetEventBrandTargetList(strPageIndex) {
    var strQuery = $("#txt_eventBrandTargetQuery").val();
    var strStraTime = $("#txt_eventBrandTargetBeginDate").val();
    var strEndTime = $("#txt_eventBrandTargetEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/BrandServlet?methodName=QueryList&strQuery="
            					+ encode(strQuery) + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#eventBrandList").html('');
            if (r == "false") {
                $("#eventBrandList").append("<tr><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#eventBrandTargetPaging .dataCount").text(data.total);
            $("#eventBrandTargetPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#eventBrandTargetPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.webList.length; i++) {
                str += "<tr>";
                str += "<td><input type='radio' class='eventBrand' value='"+data.webList[i].Id+"' name='eventBrands' id='dg"+data.webList[i].Id+"'></td>";
                str += "<td><label for='dg"+data.webList[i].Id+"'>" + data.webList[i].Name_CN + "</label></td>";
                str += "<td>" + data.webList[i].Name_EN + "</td>";
                str += "<td>" + data.webList[i].Name_other + "</td>";
                str += "<td>" + data.webList[i].AreaName + "</td>";
                str += "<td>" + data.webList[i].CountryName + "</td>";
                str += "<td>" + data.webList[i].CreateTime + "</td>";
                str += "</tr>";
                j++;
            }
            $("#eventBrandList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//品牌列表首页
function EventBrandTargetFirstPage() {
	GetEventBrandTargetList(1);
}

//品牌列表上一页
function EventBrandTargetPreviousPage() {
    var pageIndex = (parseInt($("#eventBrandTargetPaging.pageIndex").text()) == 1 ? 1
			: parseInt($("#eventBrandTargetPaging.pageIndex").text()) - 1);
					GetEventBrandTargetList(pageIndex);
}

//品牌列表下一页
function EventBrandTargetNextPage() {
    var pageIndex = ($("#eventBrandTargetPaging .pageIndex").text() == $(
			"#eventBrandTargetPaging .pageCount").text() ? parseInt($(
			"#eventBrandTargetPaging .pageIndex").text()) : parseInt($(
			"#eventBrandTargetPaging .pageIndex").text()) + 1);
    GetEventBrandTargetList(pageIndex);
}
//品牌列表尾页
function EventBrandTargetLastPage() {
	GetEventBrandTargetList(parseInt($("#eventBrandTargetPaging .pageCount").text()));
}

//事件影响品牌编辑-添加或更改网站
function GetEventBrandTargetDom(){
    $("#EventAffectedBrandDom_Edit").animate({ width: "hide" });
    $('#eventBrandDom_AddTarget').show();
    $("#txt_eventBrandTargetQuery").val("");
    $("#txt_eventBrandTargetBeginDate").val("");
    $("#txt_eventBrandTargetEndDate").val("");
    $("#eventBrandList").html('');
}

//返回事件影响品牌编辑页面
function GoBackEventBrandTarget(){
    $("#EventAffectedBrandList").html("");
    $("#eventBrandDom_AddTarget").animate({ width: "hide" });
    $('#EventAffectedBrandDom_Edit').show();
}

//添加或更改品牌-确定
function checkEventBrandTargetRadio(){
    var tmp = $("input[name='eventBrands']:checked");
    if(tmp.length == 0){
       alertText("没有选中品牌！", 3500);
    }
    else{
    	$('#txt_eventBrand').html(tmp.parent().parent().find('label').text());
    	$('#txt_eventBrand').val(tmp.val());
        GoBackEventBrandTarget();
    }
}
