/*
 * 事件影响商品类型JS
 */

//跳转到事件影响商品类型页面
function toEventAffectedProductTypeDom(id,name){
    $("#iframeMain .wrap").animate({ width: "hide" });
    $("#EventAffectedProductTypeDom").show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    $("#EventAffectedProductTypeList").html("");
    $("#hid_eventProductTypeParam").val("id="+id+"&name="+name);
}

//跳转到事件影响商品类型首页
function EventAffectedProductTypeFirstPage() {
    GetEventAffectedProductTypeList(1);
}

//跳转到事件影响商品类型上一页
function EventAffectedProductTypePreviousPage() {
    var pageIndex = (parseInt($("#EventAffectedProductTypePaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#EventAffectedProductTypePaging .pageIndex").text()) - 1);
    GetEventAffectedProductTypeList(pageIndex);
}

//跳转到事件影响商品类型下一页
function EventAffectedProductTypeNextPage() {
    var pageIndex = ($("#EventAffectedProductTypePaging .pageIndex").text() == $(
			"#EventAffectedProductTypePaging .pageCount").text() ? parseInt($(
			"#EventAffectedProductTypePaging .pageIndex").text()) : parseInt($(
			"#EventAffectedProductTypePaging .pageIndex").text()) + 1);
    GetEventAffectedProductTypeList(pageIndex);
}

//跳转到事件影响商品类型尾页
function EventAffectedProductTypeLastPage() {
    GetEventAffectedProductTypeList(parseInt($("#EventAffectedProductTypePaging .pageCount").text()));
}

//获取某页的所有事件影响商品类型
function GetEventAffectedProductTypeList(strPageIndex) {

	var strQuery=$("#txt_EventAffectedProductTypeQuery").val();
	
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/EventAffectedProductTypeServlet?methodName=QueryList"
				+"&eventId="+getParam("hid_eventProductTypeParam", "id")
				+"&strQuery=" + strQuery + "&strPageIndex=" + strPageIndex 
				+ "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#EventAffectedProductTypeList").html('');
            if (r == "false") {
                $("#EventAffectedProductTypeList")
						.append("<tr><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#EventAffectedProductTypePaging .dataCount").text(data.total);
            $("#EventAffectedProductTypePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#EventAffectedProductTypePaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.productTypeList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + getParam("hid_eventProductTypeParam", "name") + "</td>";
                str += "<td>" + data.productTypeList[i].ProductFirstTypeName + "</td>";
                str += "<td>" + data.productTypeList[i].ProductSecondTypeName + "</td>";
                str += "<td>" + data.productTypeList[i].ProductThirdTypeName + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showEventAffectedProductTypeDialog(\"edit\",\""
						+ data.productTypeList[i].Id + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
						+ data.productTypeList[i].Id + "\", DelEventAffectedProductType)' /></td>";
                str += "</tr>";
                j++;
            }
            $("#EventAffectedProductTypeList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//通过Id删除事件影响的商品类型 
function DelEventAffectedProductType(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/EventAffectedProductTypeServlet?methodName=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetEventAffectedProductTypeList(1);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}

//添加或编辑事件影响的商品类型
function showEventAffectedProductTypeDialog(methodName, Id) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#EventAffectedProductTypeDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

	$("#txt_eventAffectedProductFirstType").html("<option>请选择</option>");
	$("#txt_eventAffectedProductSecondType").html("<option>请选择</option>");
	$("#txt_eventAffectedProductThirdType").html("<option>请选择</option>");
    
	if (methodName == "edit") {
		$.ajax({
			type: "post",
			url: projectLocation + "servlet/EventAffectedProductTypeServlet?methodName=init&id=" + Id,
	        dataType: "json",
	        async: false,
	        success: function (r) {
	            if (r != "false") {   
	            	$("#txt_eventAffectedProductTypeName").val(getParam("hid_eventProductTypeParam", "name"));
	              	$("#hid_eventAffectedProductTypeParam").val("methodName=edit&id=" + Id);

	            }
	        },
	        error: function (e) {
	        	alert(e.responseText);
	        }
		});
	} else {
		$("#txt_eventAffectedProductTypeName").val(getParam("hid_eventProductTypeParam", "name"));
		$("#hid_eventAffectedProductTypeParam").val("methodName=add");
	}
	
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ProductTypeServlet?methodName=QueryList&strQuery=&strStraTime=&strEndTime=&strPageIndex=1&strPageCount=1000&strLevel=1",
        dataType: "json",
        async: false,
        success: function (r) {
        	$.each(r.webList, function(key, val) {
                $("#txt_eventAffectedProductFirstType").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
            });
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//保存事件影响的商品类型
function SaveEventAffectedProductType()
{
	var strId =getParam("hid_eventAffectedProductTypeParam", "id");
	var strEventId = getParam("hid_eventProductTypeParam", "id");
	var productFirstTypeId=$("#txt_eventAffectedProductFirstType option:selected").val();
	var productFirstTypeName= encode($("#txt_eventAffectedProductFirstType option:selected").text());
	var productSecondTypeId=$("#txt_eventAffectedProductSecondType option:selected").val();
	var productSecondTypeName= encode($("#txt_eventAffectedProductSecondType option:selected").text());
	var productThirdTypeId=$("#txt_eventAffectedProductThirdType option:selected").val();
	var productThirdTypeName= encode($("#txt_eventAffectedProductThirdType option:selected").text());
	
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EventAffectedProductTypeServlet?methodName="
            					+ getParam("hid_eventAffectedProductTypeParam", "methodName")
            					+"&id="+strId + "&eventId=" + strEventId
                                + "&productFirstTypeId=" + productFirstTypeId +"&productFirstTypeName=" + productFirstTypeName
                                + "&productSecondTypeId=" + productSecondTypeId +"&productSecondTypeName=" + productSecondTypeName
                                + "&productThirdTypeId=" + productThirdTypeId +"&productThirdTypeName=" + productThirdTypeName,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateDataGatherInfo(data);
                $("#EventAffectedProductTypeDom_Edit").animate({ width: "hide" });
                $('#EventAffectedProductTypeDom').show();
            } else {
                alertText("保存失败！商品类别重复或未选择商品类别", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
    EventAffectedProductTypeFirstPage();
}

//三级联动，显示商品二级类别
function selectProductSecondType(){
	var id=$("#txt_eventAffectedProductFirstType option:selected").val();
	
	$("#txt_eventAffectedProductSecondType").html("<option>请选择</option>");
	$("#txt_eventAffectedProductThirdType").html("<option>请选择</option>");
	
	  $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/ProductTypeServlet?methodName=sel&id="+id,
	        dataType: "json",
	        async: false,
	        success: function (r) {
	        	$.each(r.webList, function(key, val) {
	                $("#txt_eventAffectedProductSecondType").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
	            });
	        },
	        error: function (e) {
	        }
	    });
}

//三级联动，显示商品三级类别
function selectProductThirdType(){
	var id=$("#txt_eventAffectedProductSecondType option:selected").val();
	
	$("#txt_eventAffectedProductThirdType").html("<option>请选择</option>");
	
	  $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/ProductTypeServlet?methodName=sel&id="+id,
	        dataType: "json",
	        async: false,
	        success: function (r) {
	        	$.each(r.webList, function(key, val) {
	                $("#txt_eventAffectedProductThirdType").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
	            });
	        },
	        error: function (e) {
	        }
	    });
}