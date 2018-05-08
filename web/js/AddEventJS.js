//显示添加事件面板
function showNewsDocumentDomLarge_addEvent(){	
	$('#NewsDocumentDomLarge_addEvent').removeClass('disappear');
	$('#NewsDocumentDomSmall_addEvent').addClass('disappear');
    NewFocusGetEvent(1);
    showNewsFocusEvents();
    $('#NewsFocusRecord strong').html(0);
}
//把json格式数据展开成html
function GetNewsFocusEventInfo(data, i, sign){
    var str = "";
    if(i == 0){
        str += "<div class='NewsFocusEventIdx' style='border: none;'>";
    }else{
        str += "<div class='NewsFocusEventIdx'>";
    }
    str += "<span>" + data.Name + "</span>";
    str += "<div class='NewFocusEventOperators'>";
    if(sign == 0)
        str += "<input type='button' class='NewFocusEventOperator' value='√' onclick='addNewsFocusEventMatch("+data.Id+", this)'>";
    else
        str += "<input type='button' class='NewFocusEventOperator' value='x' onclick='cancelNewsFocusEventMatch("+data.Id+", this, 1)'>";
    str += "<input type='button' class='NewFocusEventOperator' value='+' id='' onclick='unfoldNewsFocusEventDetail("+data.Id+", this, "+i+")'/>";
    str += "</div></div>";
    return str;
}
//显示添加事件小面板
function showNewsDocumentDomSmall_addEvent(){
	$('#NewsDocumentDomSmall_addEvent').removeClass('disappear');
	$('#NewsDocumentDomLarge_addEvent').addClass('disappear');
	$('#NewsDocumentDom_Events').addClass('disappear');
}
//显示该文章关联的所有事件
function showNewsDocumentDom_Events(){
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ArticleEventServlet?methodName=QueryList"
                             + "&articleId=" + getParam("hid_NewsFocusDetailParam", "id"),
        dataType: "text",
        async: false,
        success: function (r) {
            $("#NewsFocusEventItems").html('');
            if (r == "false") {
                $("#NewsFocusEventItems").append("<div id='NewsFocusEvent_noData' style='border: none;' class='NewsFocusEventIdx'><span class='NewsFocusEventName'>无关联事件！</span></div>");
                $('#NewsDocumentDom_Events span strong').html(0);
                return false;
            }
            var data = $.parseJSON(r);

            var str = "";
            var j = 0;
            $('#NewsDocumentDom_Events span strong').html(data.total);
            for (var i = 0; i < data.webList.length; i++) {
                str += GetNewsFocusEventInfo(data.webList[i], i, 1);
            }
            $("#NewsFocusEventItems").append(str);
            $('#NewsDocumentDom_Events span strong').html(data.total);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
	$('#NewsDocumentDom_Events').removeClass('disappear');
	$('#NewsDocumentDomSmall_addEvent').addClass('disappear');
}

//添加舆情关注和事件的关联
function addNewsFocusEventMatch(idx, obj){
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/ArticleEventServlet?methodName=add"
                             + "&eventId=" + idx
                             + "&articleId=" + getParam("hid_NewsFocusDetailParam", "id"),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r != "false") {
                var data = $.parseJSON(r);
                var str = GetNewsFocusEventInfo(data, 1, 1);
                var num = parseInt($('#NewsFocusRecord strong').html()) + 1;
                $('#NewsFocusRecord strong').html(num);
                $(obj).val('×');
                $(obj).attr('onclick', 'cancelNewsFocusEventMatch('+idx+', this, 0)');
            } else {
                alertText("保存失败！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
//取消舆情关注和事件的关联
function cancelNewsFocusEventMatch(idx, obj, sign){
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/ArticleEventServlet?methodName=del"
                             + "&eventId=" + idx
                             + "&articleId=" + getParam("hid_NewsFocusDetailParam", "id"),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r != "false") {
                var data = $.parseJSON(r);
                var str = GetNewsFocusEventInfo(data, 1, 1);
                if(sign == 0){
                    $(obj).val('√');
                    $(obj).attr('onclick', 'addNewsFocusEventMatch('+idx+', this)');
                    var num = parseInt($('#NewsFocusRecord strong').html()) - 1;
                    $('#NewsFocusRecord strong').html(num);
                }else{
                    $(obj).parent().parent().remove();
                    var num = parseInt($('#NewsDocumentDom_Events span strong').html()) - 1;
                    $('#NewsDocumentDom_Events span strong').html(num);
                }
            } else {
                alertText("删除失败！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
//展开事件详细信息
function unfoldNewsFocusEventDetail(idx, aim, pos){
	$.ajax({
        type : "post",
        url : projectLocation + "servlet/EventServlet?methodName=initEvent&id="+ idx,
        dataType : "text",
        async : false,
        success : function(data) {
            var r = $.parseJSON(data);
            if (r != "false") {
                var str = "<div id='NewsFocusEventsDetail"+idx+"'>";
                str += "<span class='NewsFocusEventName'>开始日期 : </span>";
                str += "<span class='NewsFocusEventCnt'>"+r.event.BeginDate+"</span><br>"
                str += "<span class='NewsFocusEventName'>结束日期 : </span>";
                str += "<span class='NewsFocusEventCnt'>"+r.event.EndDate+"</span><br>"
                str += "<span class='NewsFocusEventName'>备注 : </span>";
                str += "<span class='NewsFocusEventCnt'>"+r.event.Remark+"</span><br>";
                str += "<span class='NewsFocusEventName'>事件类型 : </span>";
                str += "<span class='NewsFocusEventCnt'>"+r.event.TypeName+"</span><br>";
                for(var i = 0; i < r.keys.length; i++){
                    str += "<span class='NewsFocusEventName'>"+r.keys[i]+" : </span>";
                    str += "<span class='NewsFocusEventCnt'>"+r.keysContent[i]+"</span><br>";
                }
                str += "</div>";
                $(aim).parent().parent().after(str);
            }
        },
        error : function(e) {
            $(aim).parent().parent().after("<div id='NewsFocusEventsDetail"+idx+"'><span class='NewsFocusEventName'>无法加载详细信息！</span></div>");
        }
    });
	var obj = document.getElementsByClassName('NewsFocusEventIdx');
	var top = obj[pos].offsetTop + obj[pos].offsetHeight;
	var left = obj[pos].offsetLeft;
	//$('#NewsFocusEventsDetail').attr('style', 'top:'+top+'px;left:'+left+'px');
	//$('#NewsFocusEventsDetail').show();
	$(aim).val('-');
	$(aim).attr('onclick', 'foldNewsFocusEventDetail('+idx+', this, '+pos+')');
}
//收起事件详细信息
function foldNewsFocusEventDetail(idx, obj, pos){
	 $("#NewsFocusEventsDetail"+idx).remove();
	// $('#NewsFocusEventsDetail').hide();
	$(obj).val('+');
	$(obj).attr('onclick', 'unfoldNewsFocusEventDetail('+idx+', this, '+pos+')');
}
//搜索该篇文章未关联事件
function NewFocusGetEvent(){
	var strQuery = $("#txt_NewsFocusEvent").val();
    var strStartTime = $("#txt_NewsFocusEventBeginDate").val();
    var strEndTime = $("#txt_NewsFocusEventEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ArticleEventServlet?methodName=queryEvents&strQuery=" + strQuery
				+ "&strStartTime=" + strStartTime + "&strEndTime=" + strEndTime + "&strPageIndex=1"
  				+ "&strPageCount=10" + "&articleId=" + getParam("hid_NewsFocusDetailParam", "id"),
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#NewsFocusEvents").html('');
            if (r == "false") {
                $("#NewsFocusEvents").append("<div id='NewsFocusEvent_noData' style='border: none;' class='NewsFocusEventIdx'><span class='NewsFocusEventName'>无数据！</span></div>");
                $("#NewsFocusEventDataCount strong").text(0);
                $('#NewsFocusEventPagination').pagination(0, {
                    callback:PageCallback,
                    prev_text:'<<',
                    next_text:'>>',
                    items_per_page:10,
                    num_edge_entries:1,
                    num_display_entries:4,
                 });
                return false;
            }
            var data = $.parseJSON(r);

            $("#NewsFocusEventDataCount strong").text(data.total);
           
           var num = parseInt($("#NewsFocusEventDataCount strong").text());
            $('#NewsFocusEventPagination').pagination(num, {
                callback:PageCallback,
                prev_text:'<<',
                next_text:'>>',
                items_per_page:10,
                num_edge_entries:1,
                num_display_entries:4,
             });
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
//分页获取查询事件信息
function GetNewFocusEventByPage(idx){
    var strQuery = $("#txt_NewsFocusEvent").val();
    var strStartTime = $("#txt_NewsFocusEventBeginDate").val();
    var strEndTime = $("#txt_NewsFocusEventEndDate").val();
    $.ajax({
        type: "post",
        url: projectLocation
                + "servlet/ArticleEventServlet?methodName=queryEvents&strQuery=" + strQuery
                + "&strStartTime=" + strStartTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                + idx + "&strPageCount=10" + "&articleId=" + getParam("hid_NewsFocusDetailParam", "id"),
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#NewsFocusEvents").html('');
            if (r == "false") {
                $("#NewsFocusEvents").append("<div id='NewsFocusEvent_noData' style='border: none;' class='NewsFocusEventIdx'><span class='NewsFocusEventName'>无数据！</span></div>");
                return false;
            }
            var data = $.parseJSON(r);

            var str = "";
            var j = 0;
            for (var i = 0; i < data.eventList.length; i++) {
                str += GetNewsFocusEventInfo(data.eventList[i], i, 0);
            }
            $("#NewsFocusEvents").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function PageCallback(idx, jq){
    GetNewFocusEventByPage(parseInt(idx)+1);
}
//保存舆情关注和事件的关联
function saveNewsFocusEvent(){
    var strName = encode($('#txt_NewsFocusEventName').val());
    var strEventBeginDate = $('#txt_AddNewsFocusEventBeginDate').val();
    var strEventEndDate = $('#txt_AddNewsFocusEventEndDate').val();
    var strRemark = $('#txt_newsFocusEventRemark').val();
    var strTypeId = $('#sel_NewsFocusEventType').val();
    var strTypeName = encode($('#sel_NewsFocusEventType').text());
    var strEpidemicId="";

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EventServlet?methodName=addEvent"
                             + "&eventName=" + strName
                             + "&eventTypeId=" + strTypeId
                             + "&eventTypeName=" + strTypeName
                             + "&eventBeginDate=" + strEventBeginDate
                             + "&eventEndDate=" + strEventEndDate
                             + "&remark=" + strRemark
                             + "&epidemicId=" + strEpidemicId
                             + "&keywordId=" + $('#NewsFocusEventKeywordId').val(),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r != "false") {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateNewsFocusEventInfo(data);
                showNewsFocusEvents();
            } else {
                alertText("保存失败！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
//保存后更新添加事件面板信息
function UpdateNewsFocusEventInfo(data){
    if($("#NewsFocusEvents").children('.NewsFocusEventIdx').eq(0).attr('id')=='NewsFocusEvent_noData'){
        $('#NewsFocusEvent_noData').remove();
    }else{
        $("#NewsFocusEvents").children('.NewsFocusEventIdx').eq(0).attr("style", "");
    }
    var str = "<div class='NewsFocusEventIdx'  style='border: none;'>";
    str += "<span>" + data.Name + "</span>";
    str += "<div class='NewFocusEventOperators'>";
    str += "<input type='button' class='NewFocusEventOperator' value='√' onclick='addNewsFocusEventMatch("+data.Id+", this)'>";
    str += "<input type='button' class='NewFocusEventOperator' value='+' id='' onclick='unfoldNewsFocusEventDetail("+data.Id+", this, "+i+")'/>";
    str += "</div></div>";
    $("#NewsFocusEvents").prepend(str);
    var num = parseInt($('#NewsFocusEventDataCount strong').html())+1;
    $('#NewsFocusEventDataCount strong').html(num);
}
//显示添加事件查询面板
function showNewsFocusEvents(){
    $("#NewsFocusEvents").show();
    $('#NewsFocusEventDataCount').show();
    $('#NewsFocusEventPagination').show();
    $('#NewsFocusEventAddDetail').hide();
    $('#NewsFocusFoot').show();
    $('#NewsFocusEventAdd').hide();
}
//隐藏添加事件查询面板
function hideNewsFocusEvents(){
    $("#NewsFocusEvents").hide();
    $('#NewsFocusEventDataCount').hide();
    $('#NewsFocusEventPagination').hide();
    $('#NewsFocusEventAddDetail').show();
    $('#NewsFocusFoot').hide();
}
//添加事件时事件类型初始化
function NewsFocusEventAddInit(){
    hideNewsFocusEvents();
    $('#txt_NewsFocusEventName').val('');
    $('#txt_AddNewsFocusEventBeginDate').val('');
    $('#txt_AddNewsFocusEventEndDate').val('');
    $('#txt_newsFocusEventRemark').val('');
    $("#NewsFocusEventType").text('');
    $("#sel_NewsFocusEventType").html('');
    $.ajax({
            type: "post",
            url: projectLocation + "servlet/KeyWordServlet?methodName=init&id=" + $('#NewsFocusEventKeywordId').val(),
            dataType: "text",
            async: false,
            success: function (data) {
                if (data != "false") {
                    $("#sel_NewsFocusEventType").html('');
                    var r = $.parseJSON(data);
                    $('#NewsFocusKeyWord').html(r.keyWord.Name);
                    $('#NewsFocusEventKeywordId').val(r.keyWord.Id);
                    var str = "";
                    for(var i = 0; i<r.eventTypes.names.length; i++){
                        str += "<option value='"+r.eventTypes.ids[i]+"'>"+r.eventTypes.names[i]+"</option>"
                    }
                    
                    if(str.length > 0){
                        $("#sel_NewsFocusEventType").append(str);
                        $("#sel_NewsFocusEventType").val(r.eventTypes.ids[0]);
                        $("#NewsFocusEventAdd").show();
                    }
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
    });
}
//返回添加面板
function backNewsFocusEvents(){
    showNewsFocusEvents();
    $('#NewsFocusEventAdd').show();
}

