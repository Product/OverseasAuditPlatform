/*
 * 事件影响配方原料JS 
 */ 

// 跳转到影响配方原料页面
function toEventAffectedMaterialDom(id,name){
    $("#iframeMain .wrap").animate({ width: "hide" });
    $("#EventAffectedMaterialDom").show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    $("#EventAffectedMaterialList").html("");
    $("#hid_eventAffectedMaterialParam").val("id="+id+"&name="+name);
}

//跳转到影响配方原料首页
function EventAffectedMaterialFirstPage() {
    GetEventAffectedMaterialList(1);
}

//跳转到影响配方原料上一页
function EventAffectedMaterialPreviousPage() {
    var pageIndex = (parseInt($("#EventAffectedMaterialPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#EventAffectedMaterialPaging .pageIndex").text()) - 1);
    GetEventAffectedMaterialList(pageIndex);
}

//跳转到影响配方原料下一页
function EventAffectedMaterialNextPage() {
    var pageIndex = ($("#EventAffectedMaterialPaging .pageIndex").text() == $(
			"#EventAffectedMaterialPaging .pageCount").text() ? parseInt($(
			"#EventAffectedMaterialPaging .pageIndex").text()) : parseInt($(
			"#EventAffectedMaterialPaging .pageIndex").text()) + 1);
    GetEventAffectedMaterialList(pageIndex);
}

//跳转到影响地区尾页
function EventAffectedMaterialLastPage() {
    GetEventAffectedMaterialList(parseInt($("#EventAffectedMaterialPaging .pageCount").text()));
}

//获取某页的所有影响配方原料
function GetEventAffectedMaterialList(strPageIndex) {

	var strQuery=$("#txt_EventAffectedMaterialQuery").val();
	
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/EventAffectedMaterialServlet?methodName=MaterialQueryList"
				+"&eventId="+getParam("hid_eventAffectedMaterialParam", "id")
				+"&strQuery=" + strQuery + "&strPageIndex=" + strPageIndex 
				+ "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#EventAffectedMaterialList").html('');
            if (r == "false") {
                $("#EventAffectedMaterialList")
						.append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#EventAffectedMaterialPaging .dataCount").text(data.total);
            $("#EventAffectedMaterialPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#EventAffectedMaterialPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.eventAffectedMaterialList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + getParam("hid_eventAffectedMaterialParam", "name") + "</td>";
                str += "<td>" + data.eventAffectedMaterialList[i].MaterialTypeName + "</td>";
                str += "<td>" + data.eventAffectedMaterialList[i].MaterialName + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showEventAffectedMaterialDialog(\"editMaterial\",\""
						+ data.eventAffectedMaterialList[i].Id + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
						+ data.eventAffectedMaterialList[i].Id + "\", DelEventAffectedMaterial)' /></td>";
                str += "</tr>";
                j++;
            }
            $("#EventAffectedMaterialList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//通过Id删除影响配方原料
function DelEventAffectedMaterial(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/EventAffectedMaterialServlet?methodName=delMaterial&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetEventAffectedMaterialList(1);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}

//添加或编辑影响配方原料
function showEventAffectedMaterialDialog(methodName, Id) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#EventAffectedMaterialDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    
	$("#txt_eventAffectedMaterialType").html("<option>请选择</option>");
	$("#txt_eventAffectedMaterial").html("<option>请选择</option>");
    
    if (methodName == "editMaterial") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/EventAffectedMaterialServlet?methodName=initMaterial&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {   
                	$("#txt_eventAffectedMaterialName").val(getParam("hid_eventAffectedMaterialParam", "name"));
                	$("#hid_eventMaterialParam").val("methodName=editMaterial&id=" + Id);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
    	$("#txt_eventAffectedMaterialName").val(getParam("hid_eventAffectedMaterialParam", "name"));
        $("#hid_eventMaterialParam").val("methodName=addMaterial");
    }
    
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/MaterialTypeServlet?methodName=getMaterialTypeList",
        dataType: "json",
        async: false,
        success: function (r) {

        	$.each(r.webList, function(key, val) {
                $("#txt_eventAffectedMaterialType").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
            });
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//保存影响配方原料
function SaveEventAffectedMaterial()
{
	var strId = getParam("hid_eventMaterialParam", "id");
	var strEventId = getParam("hid_eventAffectedMaterialParam", "id");
	var materialTypeId = $("#txt_eventAffectedMaterialType option:selected").val();
	var materialTypeName = encode($("#txt_eventAffectedMaterialType option:selected").text());
	var materialId = $("#txt_eventAffectedMaterial option:selected").val();
	var materialName = encode($("#txt_eventAffectedMaterial option:selected").text());
	
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EventAffectedMaterialServlet?methodName=" + getParam("hid_eventMaterialParam", "methodName")
             +"&id="+strId + "&eventId=" + strEventId + "&materialTypeId=" + materialTypeId + "&materialTypeName=" + materialTypeName
             + "&materialId=" + materialId + "&materialName=" + materialName,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateDataGatherInfo(data);
                $("#EventAffectedMaterialDom_Edit").animate({ width: "hide" });
                $('#EventAffectedMaterialDom').show();
            } else {
                alertText("保存失败！未选择配方原料", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//二级联动，显示配方原料
function selectMaterial(){
	$("#txt_eventAffectedMaterial").html("<option>请选择</option>");//清空下拉菜单
	var materialTypeId=$("#txt_eventAffectedMaterialType option:selected").val();
	  $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/MaterialsServlet?methodName=getMaterialList&id="+materialTypeId,
	        dataType: "json",
	        async: false,
	        success: function (r) {
	        	$.each(r.webList, function(key, val) {
	                $("#txt_eventAffectedMaterial").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
	            });
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
}