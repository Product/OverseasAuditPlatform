/*
 * 事件维护JS
 */

//跳转到事件列表首页
function EventFirstPage() {
    GetEventsList(1);
}

//跳转到事件列表上一页
function EventPreviousPage() {
    var pageIndex = (parseInt($("#EventPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#EventPaging .pageIndex").text()) - 1);
    GetEventsList(pageIndex);
}

//跳转到事件列表下一页
function EventNextPage() {
    var pageIndex = ($("#EventPaging .pageIndex").text() == $(
			"#EventPaging .pageCount").text() ? parseInt($(
			"#EventPaging .pageIndex").text()) : parseInt($(
			"#EventPaging .pageIndex").text()) + 1);
    GetEventsList(pageIndex);
}

//跳转到事件列表尾页
function EventLastPage() {
    GetEventsList(parseInt($("#EventPaging .pageCount").text()));
}


//获取某页的所有事件
function GetEventsList(strPageIndex) {
    var strQuery = $("#txt_EventsQuery").val();
    var strStartTime = $("#txt_EventsBeginDate").val();
    var strEndTime = $("#txt_EventsEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/EventServlet?methodName=QueryList&strQuery=" + strQuery
				+ "&strStartTime=" + strStartTime + "&strEndTime=" + strEndTime + "&strPageIndex="
  				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#EventList").html('');
            if (r == "false") {
                $("#EventList")
						.append("<tr><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#EventPaging .dataCount").text(data.total);
            $("#EventPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#EventPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.eventList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + data.eventList[i].Name + "</td>";
                str += "<td>" + data.eventList[i].TypeName + "</td>";
                str += "<td>" + data.eventList[i].BeginDate + "</td>";
                str += "<td>" + data.eventList[i].EndDate + "</td>";
                str += "<td>" + data.eventList[i].Remark + "</td>";
                if(data.eventList[i].TypeName=="疫情事件"){
                	str += "<td><input class='btn btn-default btn-xs' type='button' value='影响地区'  onclick='toEventAffectedAreaDom(\""+ data.eventList[i].Id +"\",\""+ data.eventList[i].Name+"\")' />&nbsp;"
                	+  "<input class='btn btn-default btn-xs' type='button' value='影响商品种类' onclick='toEventAffectedProductTypeDom(\""+ data.eventList[i].Id +"\",\""+ data.eventList[i].Name+"\")' /></td>";
                }
                else if(data.eventList[i].TypeName=="网站事件"){
                	str += "<td><input class='btn btn-default btn-xs' type='button' value='影响网站' onclick='toEventAffectedWebsiteDom(\""+ data.eventList[i].Id +"\",\""+ data.eventList[i].Name+"\")' /></td>";
                }
                else if(data.eventList[i].TypeName=="品牌事件"){
                	str += "<td><input class='btn btn-default btn-xs' type='button' value='影响品牌' onclick='toEventAffectedBrandDom(\""+ data.eventList[i].Id +"\",\""+ data.eventList[i].Name+"\")' /></td>";
                }
                else if(data.eventList[i].TypeName=="地域事件"){
                	str += "<td><input class='btn btn-default btn-xs' type='button' value='影响地区'  onclick='toEventAffectedAreaDom(\""+ data.eventList[i].Id +"\",\""+ data.eventList[i].Name+"\")' />&nbsp;"
                	+  "<input class='btn btn-default btn-xs' type='button' value='影响商品种类' onclick='toEventAffectedProductTypeDom(\""+ data.eventList[i].Id +"\",\""+ data.eventList[i].Name+"\")' /></td>";
                }
                else if(data.eventList[i].TypeName=="产品事件"){
                	str += "<td><input class='btn btn-default btn-xs' type='button' value='影响商品种类' onclick='toEventAffectedProductTypeDom(\""+ data.eventList[i].Id +"\",\""+ data.eventList[i].Name+"\")' /></td>";
                }
                else if(data.eventList[i].TypeName=="原料事件"){
                	str += "<td><input class='btn btn-default btn-xs' type='button' value='影响配方原料' onclick='toEventAffectedMaterialDom(\""+ data.eventList[i].Id +"\",\""+ data.eventList[i].Name+"\")' /></td>";
                }
                str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showEventArticles("+ data.eventList[i].Id
                        + ")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showEventDialog(\"edit\",\""
						+ data.eventList[i].Id
						+ "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
						+ data.eventList[i].Id + "\", DelEvent)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='抽取' onclick='ExtractEventProduct(\""
						+ data.eventList[i].Id + "\")' /></td>";
                str += "</tr>";
                j++;
            }
            $("#EventList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function backEventDom(){
    $('#NewsDocumentsDom').animate({
        width : "hide"
    });
    $("#btn_BackEvent").hide();
    $("#txt_NewsDocumentsEvent").val(-1);
    $('#EventDom').show();
}

function showEventArticles(id){
    $("#iframeMain .wrap").animate({
        width : "hide"
    });
    $('#NewsDocumentsDom').show();
    $("#btn_BackEvent").show();
    $("#txt_NewsDocumentsEvent").val(id);
    GetNewsDocumentsList(1);
}

//通过Id删除事件
function DelEvent(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/EventServlet?methodName=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetEventsList(1);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}

//添加或编辑事件
function showEventDialog(methodName, Id) {
	$("#iframeMain .wrap").animate({
		width : "hide"
	});
	$('#eventDom_Edit').show();
	$(obj).parent().parent().children("li").children("a")
			.removeClass("current");
	$(obj).addClass("current");
	$('#txt_eventepidemic').val("");
	$('#txt_eventepidemic').html("");
	var eventTypeId=0;
	if (methodName == "edit") {
		$.ajax({
			type : "post",
			url : projectLocation + "servlet/EventServlet?methodName=init&id="
					+ Id,
			dataType : "json",
			async : false,
			success : function(r) {
				if (r != "false") {
					$("#hid_eventParam").val("methodName=edit&id=" + Id);
					$("#txt_eventName").val(r.Name);
					$("#txt_eventBeginDate").val(r.BeginDate);
					$("#txt_eventEndDate").val(r.EndDate);
					$("#txt_eventRemark").val(r.Remark);
					eventTypeId=r.TypeId;
					if (eventTypeId == 1) {
						$('#txt_eventepidemic').val(r.EpidemicId);
						$('#txt_eventepidemic').html(r.EpidemicName);
					}
				}
			},
			error : function(e) {
				alert(e.responseText);
			}
		});
	} else {
		$("#hid_eventParam").val("methodName=add");
		$("#txt_eventName").val("");
		$("#txt_eventBeginDate").val("");
		$("#txt_eventEndDate").val("");
		$("#txt_eventRemark").val("");
		$("#addEpidemic").hide();
		$("#txt_eventepidemic").val("");
		$("#txt_eventepidemic").text("");
	}
	SelectEventType(eventTypeId);
	eventTypeChang();
}

// 保存事件
function SaveEvent()
{
	var strName = encode($("#txt_eventName").val());
	var strTypeId = $("#txt_eventType option:selected").val();
	var strTypeName = encode($("#txt_eventType option:selected").text());
	var strEventBeginDate = $("#txt_eventBeginDate").val();
	var strEventEndDate = $("#txt_eventEndDate").val();
    var strRemark = encode($("#txt_eventRemark").val());
    var strEpidemicId="";
	if(strTypeId=="1"){
		strEpidemicId = $("#txt_eventepidemic").val();
	}
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EventServlet?methodName="
            				 + getParam("hid_eventParam", "methodName") + "&id=" + getParam("hid_eventParam", "id")
                             + "&eventName=" + strName
                             + "&eventTypeId=" + strTypeId
                             + "&eventTypeName=" + strTypeName
                             + "&eventBeginDate=" + strEventBeginDate
                             + "&eventEndDate=" + strEventEndDate
                             + "&remark=" + strRemark
                             + "&epidemicId=" + strEpidemicId,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateDataGatherInfo(data);
                $("#eventDom_Edit").animate({ width: "hide" });
                $('#EventDom').show();
                GetEventsList(1);
            } else {
                alertText("保存失败！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

/**
 * @desc 显示事件类型
 */
function SelectEventType(eventTypeId){
	$("#txt_eventType").html("<option>请选择</option>");
	
	  $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/EventTypeServlet?methodName=QueryList",
	        dataType: "json",
	        async: false,
	        success: function (r) {
	        	$.each(r.eventTypeList, function(key, val) {
	        		var str='<option value="' + val.Id;
	        		if(val.Id==eventTypeId){
	        			str+='" selected = "selected">' + val.Name + '</option>';
	        		}else{
	        			str+='" >' + val.Name + '</option>';
	        		}
	                $("#txt_eventType").append(str);
	            });
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
}

/*
事件类型选择疫情时显示疫情选择框  by倪
*/
function eventTypeChang(){
	var strTypeName = $("#txt_eventType").val();
	if(strTypeName=="1"){
		$("#addEpidemic").css("display", "inline");
        $('#txt_eventepidemic').show();
	}
	else{
		$("#addEpidemic").css("display", "none");
        $('#txt_eventepidemic').hide();
	}
}

function GetEpidemic(){
	$("#eventDom_Edit").animate({ width: "hide" });
    $('#EventEpidemicDom').show();
    $("#txt_EventEpidemicQuery").val("");
    $("#EventEpidemicList").html('');
}

//判断是否选择了疫情
function checkEventEpidemicRadio(){
	var tmp = $("input[name='EventEpidemic']:checked");
    if(tmp.length == 0)
        alertText("没有选中疫情！", 3500);
    else{
        $('#txt_eventepidemic').val(tmp.val());
        $('#txt_eventepidemic').text(tmp.parent().parent().find('label').text());
        $("#EventEpidemicDom").animate({ width: "hide" });
        $('#eventDom_Edit').show();
        $('#txt_eventepidemic').show();
    }
}

//商品抽取
function ExtractEventProduct(Id) {
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/EventServlet?methodName=Extract&Id="
				+ Id,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "true") {
				$(obj).attr("disabled", false);
				alertText("抽取成功！", 3500);
			} else {
				alertText("抽取失败！", 3500);
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}