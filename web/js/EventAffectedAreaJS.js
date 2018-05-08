/*
 * 事件影响地区Js 
 */ 

// 跳转到影响地区页面
function toEventAffectedAreaDom(id,name){
    $("#iframeMain .wrap").animate({ width: "hide" });
    $("#EventAffectedAreaDom").show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    $("#EventAffectedAreaList").html("");
    $("#hid_eventAreaParam").val("id="+id+"&name="+name);
}

//跳转到影响地区首页
function EventAffectedAreaFirstPage() {
    GetEventAffectedAreaList(1);
}

//跳转到影响地区上一页
function EventAffectedAreaPreviousPage() {
    var pageIndex = (parseInt($("#EventAffectedAreaPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#EventAffectedAreaPaging .pageIndex").text()) - 1);
    GetEventAffectedAreaList(pageIndex);
}

//跳转到影响地区下一页
function EventAffectedAreaNextPage() {
    var pageIndex = ($("#EventAffectedAreaPaging .pageIndex").text() == $(
			"#EventAffectedAreaPaging .pageCount").text() ? parseInt($(
			"#EventAffectedAreaPaging .pageIndex").text()) : parseInt($(
			"#EventAffectedAreaPaging .pageIndex").text()) + 1);
    GetEventAffectedAreaList(pageIndex);
}

//跳转到影响地区尾页
function EventAffectedAreaLastPage() {
    GetEventAffectedAreaList(parseInt($("#EventAffectedAreaPaging .pageCount").text()));
}

//获取某页的所有影响区域
function GetEventAffectedAreaList(strPageIndex) {

	var strQuery=$("#txt_EventAffectedAreaQuery").val();
	
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/EventAffectedAreaServlet?methodName=AreaQueryList"
				+"&eventId="+getParam("hid_eventAreaParam", "id")
				+"&strQuery=" + strQuery + "&strPageIndex=" + strPageIndex 
				+ "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#EventAffectedAreaList").html('');
            if (r == "false") {
                $("#EventAffectedAreaList")
						.append("<tr><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#EventAffectedAreaPaging .dataCount").text(data.total);
            $("#EventAffectedAreaPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#EventAffectedAreaPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.eventAffectedAreaList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + getParam("hid_eventAreaParam", "name") + "</td>";
                str += "<td>" + data.eventAffectedAreaList[i].RegionName + "</td>";
                str += "<td>" + data.eventAffectedAreaList[i].CountryName + "</td>";
                str += "<td>" + data.eventAffectedAreaList[i].AreaName + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showEventAffectedAreaDialog(\"editArea\",\""
						+ data.eventAffectedAreaList[i].Id + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
						+ data.eventAffectedAreaList[i].Id + "\", DelEventAffectedArea)' /></td>";
                str += "</tr>";
                j++;
            }
            $("#EventAffectedAreaList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//通过Id删除影响区域
function DelEventAffectedArea(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/EventAffectedAreaServlet?methodName=delArea&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetEventAffectedAreaList(1);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}

//添加或编辑影响区域
function showEventAffectedAreaDialog(methodName, Id) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#EventAffectedAreaDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    
	$("#txt_eventAffectedAreaRegion").html("<option>请选择</option>");
	$("#txt_eventAffectedAreaCountry").html("<option>请选择</option>");
	$("#txt_eventAffectedAreaArea").html("<option>请选择</option>");
    
    if (methodName == "editArea") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/EventAffectedAreaServlet?methodName=initArea&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {   
                	$("#txt_eventAffectedAreaName").val(getParam("hid_eventAreaParam", "name"));
                	$("#hid_eventAffectedAreaParam").val("methodName=editArea&id=" + Id);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
    	$("#txt_eventAffectedAreaName").val(getParam("hid_eventAreaParam", "name"));
        $("#hid_eventAffectedAreaParam").val("methodName=addArea");
    }
    
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/RegionServlet?methodName=getRegionList",
        dataType: "json",
        async: false,
        success: function (r) {

        	$.each(r.webList, function(key, val) {
                $("#txt_eventAffectedAreaRegion").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
            });
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//保存影响区域
function SaveEventAffectedArea()
{
	var strId =getParam("hid_eventAffectedAreaParam", "id");
	var strEventId = getParam("hid_eventAreaParam", "id");
	var regionId=$("#txt_eventAffectedAreaRegion option:selected").val();
	var regionName= encode($("#txt_eventAffectedAreaRegion option:selected").text());
	var countryId=$("#txt_eventAffectedAreaCountry option:selected").val();
	var countryName= encode($("#txt_eventAffectedAreaCountry option:selected").text());
	var areaId=$("#txt_eventAffectedAreaArea option:selected").val();
	var areaName= encode($("#txt_eventAffectedAreaArea option:selected").text());
	
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EventAffectedAreaServlet?methodName="
            					+ getParam("hid_eventAffectedAreaParam", "methodName")
            					+"&id="+strId + "&eventId=" + strEventId
                                + "&regionId=" + regionId +"&regionName=" + regionName
                                + "&countryId=" + countryId +"&countryName=" + countryName
                                + "&areaId=" + areaId +"&areaName=" + areaName,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateDataGatherInfo(data);
                $("#EventAffectedAreaDom_Edit").animate({ width: "hide" });
                $('#EventAffectedAreaDom').show();
            } else {
                alertText("保存失败！地区重复或未选择地区（暂不允许国家重复）", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//三级联动，显示国家
function selectCountry(){
	var regionId=$("#txt_eventAffectedAreaRegion option:selected").val();
	
	$("#txt_eventAffectedAreaCountry").html("<option>请选择</option>");
	$("#txt_eventAffectedAreaArea").html("<option>请选择</option>");
	
	  $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/CountryServlet?methodName=QueryList&strQuery=&strPageIndex=1&strPageCount=300&regionId="+regionId,
	        dataType: "json",
	        async: false,
	        success: function (r) {
	        	$.each(r.webList, function(key, val) {
	                $("#txt_eventAffectedAreaCountry").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
	            });
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
}

//三级联动，显示省、州
function selectArea(){
	var countryId=$("#txt_eventAffectedAreaCountry option:selected").val();
	
	$("#txt_eventAffectedAreaArea").html("<option>请选择</option>");
	
	  $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/AreasServlet?methodName=QueryList&strQuery=&strPageIndex=1&strPageCount=100&countryId="+countryId,
	        dataType: "json",
	        async: false,
	        success: function (r) {
	        	$.each(r.webList, function(key, val) {
	                $("#txt_eventAffectedAreaArea").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
	            });
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
}