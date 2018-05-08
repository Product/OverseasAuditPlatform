function FirstPageNewsGatherTask() {
	GetNewsGatherTaskList(1);
}

function PreviousPageNewsGatherTask() {
    var pageIndex = (parseInt($("#NewsGatherTaskPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#NewsGatherTaskPaging .pageIndex").text()) - 1);
    GetNewsGatherTaskList(pageIndex);
}

function NextPageNewsGatherTask() {
    var pageIndex = ($("#NewsGatherTaskPaging .pageIndex").text() == $("#NewsGatherTaskPaging .pageCount").text() ? parseInt($("#NewsGatherTaskPaging .pageIndex").text()) : parseInt($("#NewsGatherTaskPaging .pageIndex").text()) + 1);
    GetNewsGatherTaskList(pageIndex);
}

function EndPageNewsGatherTask() {
	GetNewsGatherTaskList(parseInt($("#NewsGatherTaskPaging .pageCount").text()));
}

function GetNewsGatherTaskEntry(data, idx){
    var str = "<tr>";
    str += "<td><input type='checkbox' rel='" + data.Id + "'></td>"; // checkbox的Id？
     str += "<td class='NewsGatherTaskOrd'>" + idx + "</td>";
                str += "<td>" + data.TaskName + "</td>";
                str += "<td class='NewsGatherTaskAnum'>" + data.ArticleNum + "</td>";
                if (data.StateNum == 0)//空闲
                    str += "<td class='NewsGatherTaskState'><span style='color:green;'>" + data.State + "</span></td>";
                else if (data.StateNum == 1)//执行中
                    str += "<td class='NewsGatherTaskState'><span style='color:orange;'>" + data.State + "</span></td>";
                else if (data.StateNum == 2)//异常
                    str += "<td class='NewsGatherTaskState'><span style='color:red;'>" + data.State + "</span></td>";
                str += "<td>" + data.Remark + "</td>";
                str += "<td>" + data.CreatePerson + "</td>";

                str += "<td>"
                        + data.CreateTime.substring(0, 19)
                        + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showNewsGatherTaskDetail(\"view\",\"" + data.Id
                        + "\",this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showNewsGatherTaskDialog(\"edit\",\"" + data.Id
                        + "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='执 行' class='excuteBtnF' onclick='NewsGatherTaskExcute(this, \"" + data.Id
                        + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\"" + data.Id
                        + "\",DelNewsGatherTask)' /></td>";
                str += "</tr>";
    return str;
}


function GetNewsGatherTaskByGId(strPageIndex){
	var strQuery = "";
    var strStraTime = "";
    var strEndTime = "";
    $.ajax({
        type: "post",
        url: projectLocation
                + "servlet/NewsGatherTaskServlet?methodName=QueryList&strQuery=" + encode(strQuery)
                + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                + strPageIndex + "&strPageCount=10&Id=" + getParam("hid_NewsGatherParam", "id"),
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#NewsGatherTaskList").html('');
            if (r == "false") {
                $("#NewsGatherTaskList")
                        .append("<tr class='noDataSrc'><td colspan='9' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }else if(r =="sessionOut"){
                doLogout();
                
            }
            var data = $.parseJSON(r);

            $("#NewsGatherTaskPaging .dataCount").text(data.total);
            $("#NewsGatherTaskPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#NewsGatherTaskPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.webList.length; i++) {
                str += GetNewsGatherTaskEntry(data.webList[i], i+1);
            }
            $("#NewsGatherTaskList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
函数名：GetNewsGatherTaskList
作用：获取数据采集列表
*/

function GetNewsGatherTaskList(strPageIndex) {
    var strQuery = $("#txt_NewsGatherTaskQuery").val();
    var strStraTime = $("#txt_NewsGatherTaskBeginDate").val();
    var strEndTime = $("#txt_NewsGatherTaskEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
                + "servlet/NewsGatherTaskServlet?methodName=QueryList&strQuery=" + encode(strQuery)
                + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                + strPageIndex + "&strPageCount=10&Id=" + getParam("hid_NewsGatherParam", "id"),
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#NewsGatherTaskList").html('');
            if (r == "false") {
                $("#NewsGatherTaskList")
                        .append("<tr class='noDataSrc'><td colspan='9' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }else if(r =="sessionOut"){
                doLogout();
                
            }
            var data = $.parseJSON(r);

            $("#NewsGatherTaskPaging .dataCount").text(data.total);
            $("#NewsGatherTaskPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#NewsGatherTaskPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.webList.length; i++) {
                str += GetNewsGatherTaskEntry(data.webList[i], i+1);
            }
            $("#NewsGatherTaskList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

$().ready(function() {
    //start validate
    $("#NewsGatherTaskfirstTaskForm").validate({          
        rules: {
            mustName: {
                required: true, 
                byteRangeLength:[1,50]
            },
            pagestep: {
                required: true,
                digits:true,
                min:0
            },
            urlname: {
                required: true,
                url:true
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

function progress(obj){
    if($("#NewsGatherTaskfirstTaskForm").valid()){
        var pos = $('#NewsGatherProgressBar div').index(obj);
        if($(obj).children('div').attr('class') == 'second_div'){
            $('#NewsGatherProgressBar div:lt('+(pos+1)+')').each(function(){
                if($(this).attr('class') == 'progressImg'){
                    $(this).children('div').animate({
                        width:'100%'
                    }, 500);
                }else{
                    $(this).children('div').attr('class', 'first_div');
                    $(this).children('span').attr('class', 'first_span');
                }
                $('#'+$(this).attr('title')).hide();
            });
        }else{
            $('#NewsGatherProgressBar div:gt('+pos+')').each(function(){
                if($(this).attr('class') == 'progressImg'){
                    $(this).children('div').animate({
                        width:'0px'
                    }, 500);
                }else{
                    $(this).children('div').attr('class', 'second_div');
                    $(this).children('span').attr('class', 'second_span');
                }
                $('#'+$(this).attr('title')).hide();
            });
        }
        $('#'+$(obj).attr('title')).show();
    }
}

/*未测
function DelNewsGatherTask(id) {
	    $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/NewsGatherTaskServlet?methodName=del&id=" + id,
	        dataType: "text",
	        async: false,
	        success: function (r) {
                 if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetNewsGatherTaskList(1);
                } else {
                    alertText("删除失败！", 3500);
                }
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    }); 
}*/

function NewsGatherTaskpagingTypeChange(){
    if ($("#txt_NewsGatherTaskpagingType").val() != "DYNAMIC") {
        $(".pagingType").hide();
        $("#newsdynamicPart").hide();
    } else {
        $(".pagingType").show();
        $("#newsdynamicPart").show();
    }
}

function NewsGatherTasknextPageTypeChange(){
    if ($("#txt_NewsGatherTasknextPageType").val() == "TEXT") {
        $(".nextPageTypeIsText").hide();
    } else {
        $(".nextPageTypeIsText").show();
    }
}

function NewsGatherTaskDisplayControl(){
	if ($("#txt_newsGatherTaskPagingType").val() != "DYNAMIC") {
        $(".npagingType").hide();
		$("#ndynamicPart").hide();
    } else {
        $(".npagingType").show();
		$("#ndynamicPart").show();
    }
	 if ($("#txt_newsGatherTaskNextpageType").val() == "TEXT") {
	        $(".nnextPageTypeIsText").hide();
	    } else {
	        $(".nnextPageTypeIsText").show();
	    }
}

function CreateNewsGatherTaskParamsLine() {
    $("#NewsGatherTaskParamsList").append("<tr><td rel='0'></td><td><input type='text' /></td><td><input type='text' /></td><td><input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelNewsGatherTaskParamsRow(this)' /></td></tr>");
    NewsGatherTaskParamsCheckRowNO();
}

function NewsGatherTaskParamsCheckRowNO() {
    $("#NewsGatherTaskParamsList tr").each(function (i) {
        $(this).children().eq(0).html(i + 1);
    });
}

function DelNewsGatherTaskParamsRow(obj) {
     if($(obj).parent().parent().children().eq(0).attr("rel")!="0")//当该条属性已经存在于数据库，将其对应数据项删除
         {
          $.ajax({
                type: "post",
                url: projectLocation
                            + "servlet/NewsGatherTaskServlet?methodName=ParamsDel&id=" + $(obj).parent().parent().children().eq(0).attr("rel"),
                dataType: "text",
                async: false,
                success: function (r) {
                    if (r == "true") {
                         $(obj).parent().parent().remove();
                            NewsGatherTaskParamsCheckRowNO();
                    }
                },
                error: function (e) {
                    alert(e.responseText);
                }
            });
         }
    else{
         $(obj).parent().parent().remove();
         NewsGatherTaskParamsCheckRowNO();
    }
}

function showNewsGatherTaskDetail(methodName, Id,pos){
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#NewsGatherTaskDom_View').show();

    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/NewsGatherTaskServlet?methodName=view&id=" + Id,
        dataType: "text",
        async: false,
        success: function (r) {

            if (r != "false") {
                var data = $.parseJSON(r);
                $("#hid_newsGatherTaskListParam").val("methodName=view&id=" + Id);
                $("#txt_newsGatherTaskName").val(data.TaskName);
                $("#txt_newsGatherTaskRemark").val(data.Remark);
                
                $("#txt_newsGatherTaskUrl").val(data.URL);
                $("#txt_newsGatherTaskRequestType").val(data.RequestType);
                $("#txt_newsGatherTaskPagingType").val(data.PagingType);
                $("#txt_newsGatherTaskPagingParams").val(data.PagingParams);
                $("#txt_newsGatherTaskPagingStep").val(data.PagingStep);
                $("#txt_newsGatherTaskNextpageType").val(data.NextPageType);
                $("#txt_newsGatherTaskNextpagePath").val(data.NextPagePath);
                $("#txt_newsGatherTaskNextpageStr").val(data.NextPageStr);
                $("#txt_newsGatherTaskLastpageType").val(data.LastPageType);
                $("#txt_newsGatherTaskLastpageStr").val(data.LastPageStr);
                $("#txt_newsGatherTaskArticlePath").val(data.ArticlePath);
                $("#txt_newsGatherTaskPathType").val(data.PathType);
                $("#txt_newsGatherTaskLastPageUrl").val(data.LastPageUrl);
                
                $("#vNewsGatherTaskParamsList").html('');
                var plstr = "";

                for (var j = 0; j < data.ParamsList.length; j++) {//Params参数显示
                    plstr += "<tr>";
                    plstr += "<td>" + (j + 1) + "</td>";
                    plstr += "<td>" + data.ParamsList[j].Key + "</td>";
                    plstr += "<td>" + data.ParamsList[j].Val + "</td>";
                    plstr += "</tr>";
                }
                $("#vNewsGatherTaskParamsList").append(plstr);
                $("#vNewsGatherTaskItemPathList").html('');

                var ipstr = "";

                for (var j = 0; j < data.strItemPath.length; j++) {//ItemPath列表显示
                    ipstr += "<tr>";
                    ipstr += "<td>" + (j + 1) + "</td>";
                    ipstr += "<td>" + data.strItemPath[j].ItemType + "</td>";
                    ipstr += "<td>" + data.strItemPath[j].ItemStr + "</td>";
                    ipstr += "<td>" + data.strItemPath[j].ItemAttr + "</td>";
                    ipstr += "<td>" + data.strItemPath[j].ItemValPath + "</td>";
                    ipstr += "</tr>";
                }
                $("#vNewsGatherTaskItemPathList").append(ipstr);
                NewsGatherTaskDisplayControl();
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

function showNewsGatherTaskDialog(methodName, Id,pos){ 
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#NewsGatherTaskDom_Edit').show();
    $('#NewsGatherTasksecondProgress').hide();
    $('#NewsGatherTaskthirdProgress').hide();
    initNewsGatherTaskDialog(0);
    if(methodName == "add"){
        $("#hid_NewsGatherTaskListParam").val("methodName=add&gid=" + getParam("hid_NewsGatherParam", "id"));
    }else if(methodName == "edit"){
        $("#hid_NewsGatherTaskListParam").val("methodName=edit&id=" + Id + "gid=" + getParam("hid_NewsGatherParam", "id"));
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/NewsGatherTaskServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $(pos).parent().parent().addClass('editObject');
                    $("#hid_NewsGatherTaskListParam").val("methodName=edit&id=" + Id + "&gid=" + getParam("hid_NewsGatherParam","id"));
                    $("#txt_NewsGatherTaskName").val(r.TaskName);
                    $("#txt_NewsGatherTaskURL").val(r.URL);
                    $("#sel_requestType").val(r.RequestType);
                    
                    $("#txt_NewsGatherTaskRemark").val(r.Remark);
                    $("#txt_NewsGatherTaskpagingType").val(r.PagingType);
                    $("#txt_NewsGatherTaskpagingParams").val(r.PagingParams);
                    $("#txt_NewsGatherTaskpagingStep").val(r.PagingStep);
                    $("#txt_NewsGatherTasknextPageType").val(r.NextPageType);
                    $("#txt_NewsGatherTasknextPagePath").val(r.NextPagePath);
                    $("#txt_NewsGatherTasknextPageStr").val(r.NextPageStr);
                    $("#txt_NewsGatherTasklastPageType").val(r.LastPageType);
                    $("#txt_NewsGatherTasklastPageStr").val(r.LastPageStr);
                    $("#ddl_NewsGatherTaskrequestType").val(r.RequestType);
                    $("#txt_NewsGatherTasklastPageUrl").val(r.LastPageUrl);
                    $("#txt_NewsGatherTaskarticlePath").val(r.ArticlePath);
                    $("#txt_NewsGatherTaskpathType").val(r.PathType);
                    NewsGatherTaskpagingTypeChange();
                    NewsGatherTasknextPageTypeChange();
                    var strParams = "";
                    for (var i = 0; i < r.ParamsList.length; i++) {
                        strParams += "<tr><td rel='" + r.ParamsList[i].Id + "'></td><td><input type='text' value='"
                                        + r.ParamsList[i].Key
                                        + "' /></td><td><input type='text' value='"
                                        + r.ParamsList[i].Val
                                        + "' /></td><td><input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelNewsGatherTaskParamsRow(this)' /></td></tr>";
                    }
                    var strItemPath = "";
                    if (r.strItemPath.length >= 0) {

                            for (var i = 0; i < r.strItemPath.length; i++) {
                                 //显示ItemPath列表

                                    strItemPath += (("<tr><td rel='" + r.strItemPath[i].Id + "'><input type='text' value='"
                                        + r.strItemPath[i].ItemType
                                        + "' /></td><td><input type='text' value='"
                                        + r.strItemPath[i].ItemStr
                                        + "' /></td><td><select>"
                                        + "<option value='CLASS'>Class</option>"
                                        + "<option value='ID'>Id</option>"
                                        + "<option value='SELECTION'>Selection</option>"
                                        + "<option value='TEXT'>Text</option>"
                                        + "</select></td>").replace("value='" + r.strItemPath[i].ItemAttr + "'", "selected value='" + r.strItemPath[i].ItemAttr + "'")
                                        + ("<td><select><option value='CLASS'>Class</option>"
                                        + "<option value='TEXT'>Text</option><option value='HREF'>Href</option>"
                                        + "<option value='HREF_REL'>Href_rel</option><option value='TITLE'>Title</option><option value='HTML'>Html</option>"
                                        + "</select></td>").replace("value='" + r.strItemPath[i].ItemValPath + "'", "selected value='" + r.strItemPath[i].ItemValPath + "'")
                                        + "<td><input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelNewsItemPathRow(this)' /></td></tr>");
                                
                            }
                        }

                        $("#NewsGatherTaskParamsList").html(strParams);
                        $("#NewsGatherTaskItemPathList").html(strItemPath);
                        $("#firstTaskForm").valid()
                        NewsGatherTaskParamsCheckRowNO();
                    }
                    else if (r == "sessionOut") {
                        doLogout();

                    }
                },
                error: function (e) {
                    alert(e.responseText);
                }
            });
    }
    
}

function initNewsGatherTaskDialog(pos) {
    $('#NewsGatherProgressBar div:gt('+pos+')').each(function(){
        if($(this).attr('class') == 'progressImg'){
            $(this).children('div').css('width', '0');
        }else{
            $(this).children('div').attr('class', 'second_div');
            $(this).children('span').attr('class', 'second_span');
        }
        $('#'+$(this).attr('title')).hide();
    });
    $('#NewsGatherTaskfirstProgress').show();
    $("#txt_NewsGatherTaskName").val("");
    $("#sel_requestType").val("POST");
           
    $("#txt_NewsGatherTaskURL").val("");
    $("#txt_NewsGatherTaskRemark").val("");
    $("#txt_NewsGatherTaskpagingType").val("");
    $("#txt_NewsGatherTaskpagingParams").val("");
    $("#txt_NewsGatherTaskpagingStep").val("");
    $("#txt_NewsGatherTasknextPageType").val("");
    $("#txt_NewsGatherTasknextPagePath").val("");
    $("#txt_NewsGatherTasknextPageStr").val("");
    $("#txt_NewsGatherTasklastPageType").val("");
    $("#txt_NewsGatherTasklastPageStr").val("");
    $("#NewsGatherTaskParamsList").html("");
    $("#NewsGatherTaskItemPathList").html("");
    $("#txt_NewsGatherTasklastPageUrl").val("");
    $("#txt_NewsGatherTaskarticlePath").val("");
    $("#txt_NewsGatherTaskpathType").val("");
}

//配置测试
function NewsGatherCheckTask(checkType) {
    var strName = $("#txt_NewsGatherTaskName").val();
   
    var strUrl = $("#txt_NewsGatherTaskURL").val();
    var strRequestType = $("#sel_requestType").val();
    
    var strPagingType = $("#txt_NewsGatherTaskpagingType").val();
    var strPagingParams = $("#txt_NewsGatherTaskpagingParams").val();
    var strPagingStep = $("#txt_NewsGatherTaskpagingStep").val();
    var strNextPageType = $("#txt_NewsGatherTasknextPageType").val();
    var strNextPagePath = $("#txt_NewsGatherTasknextPagePath").val();
    var strNextPageStr = $("#txt_NewsGatherTasknextPageStr").val();
    var strLastPageType = $("#txt_NewsGatherTasklastPageType").val();
    var strLastPageStr = $("#txt_NewsGatherTasklastPageStr").val();
    var strLastPageUrl = $("#txt_NewsGatherTasklastPageUrl").val();
    var strArticlePath = $("#txt_NewsGatherTaskarticlePath").val();
    var strPathType = $("#txt_NewsGatherTaskpathType").val();
    var strParams = "";
    var strItemPath = "";
    if ($.trim(strName) == "") {
        alertText("任务名不能为空！", 3500);
        return false;
    }

    $("#NewsGatherTaskParamsList tr").each(function () {
        strParams += $(this).children().eq(0).attr("rel") + "|" + $(this).children().eq(1).children().val() + "|" + $(this).children().eq(2).children().val() + "<>";
    });
    if (strParams != "") {
        strParams = strParams.substring(0, strParams.length - 2);
    }
    
    $("#NewsGatherTaskItemPathList tr").each(function () {
        strItemPath += $(this).children().eq(0).attr("rel") + "|" + $(this).children().eq(0).children().val() + "|" + $(this).children().eq(1).children().val() + "|"
                    + $(this).children().eq(2).children().val() + "|" + $(this).children().eq(3).children().val() + "<>";
    });
    if (strItemPath != "") {
        strItemPath = strItemPath.substring(0, strItemPath.length - 2);
    }

    var requestUrl = ""
    if (checkType == "checkBasic") {
        requestUrl = projectLocation
                    + "servlet/NewsGatherTaskServlet?methodName=checkBasic&name=" + strName
                    + "&strParams=" + encode(strParams) + "&strItemPath=" + encode(strItemPath)
                    + "&URL=" + encode(strUrl) + "&strRequestType=" + encode(strRequestType)
                    + "&strPagingType=" + encode(strPagingType) + "&strPagingParams=" + encode(strPagingParams)
                    + "&strPagingStep=" + encode(strPagingStep) + "&strNextPageType=" + encode(strNextPageType) 
                    + "&strArticlePath=" + encode(strArticlePath) + "&strPathType=" + encode(strPathType) 
                    + "&strNextPagePath=" + encode(strNextPagePath) + "&strNextPageStr=" + encode(strNextPageStr)
                    + "&strLastPageType=" + encode(strLastPageType) + "&strLastPageStr=" + encode(strLastPageStr)
                    + "&strLastPageUrl=" + encode(strLastPageUrl);
    } 
	//未测
	else if (checkType == "checkPaging") {
        requestUrl = projectLocation
                    + "servlet/NewsGatherTaskServlet?methodName=checkPaging&name=" + strName
                    + "&strParams=" + encode(strParams) + "&strItemPath=" + encode(strItemPath)
                    + "&URL=" + encode(strUrl) + "&strRequestType=" + encode(strRequestType)
                    + "&strPagingType=" + encode(strPagingType) + "&strPagingParams=" + encode(strPagingParams)
                    + "&strPagingStep=" + encode(strPagingStep) + "&strNextPageType=" + encode(strNextPageType)
                    + "&strArticlePath=" + encode(strArticlePath) + "&strPathType=" + encode(strPathType)
                    + "&strNextPagePath=" + encode(strNextPagePath) + "&strNextPageStr=" + encode(strNextPageStr)
                    + "&strLastPageType=" + encode(strLastPageType) + "&strLastPageStr=" + encode(strLastPageStr)
                    + "&strLastPageUrl=" + encode(strLastPageUrl);
    } else if (checkType == "checklastPage") {
        requestUrl = projectLocation
                    + "servlet/NewsGatherTaskServlet?methodName=checklastPage&name=" + strName
                    + "&strParams=" + encode(strParams) + "&strItemPath=" + encode(strItemPath)
                    + "&URL=" + encode(strUrl) + "&strRequestType=" + encode(strRequestType)
                    + "&strPagingType=" + encode(strPagingType) + "&strPagingParams=" + encode(strPagingParams)
                    + "&strPagingStep=" + encode(strPagingStep) + "&strNextPageType=" + encode(strNextPageType)
                    + "&strArticlePath=" + encode(strArticlePath) + "&strPathType=" + encode(strPathType)
                    + "&strNextPagePath=" + encode(strNextPagePath) + "&strNextPageStr=" + encode(strNextPageStr)
                    + "&strLastPageType=" + encode(strLastPageType) + "&strLastPageStr=" + encode(strLastPageStr)
                    + "&strLastPageUrl=" + encode(strLastPageUrl);
    } else if (checkType == "checkFieldMapping") {
        requestUrl = projectLocation
                    + "servlet/NewsGatherTaskServlet?methodName=checkFieldMapping&name=" + strName
                    + "&strParams=" + encode(strParams) + "&strItemPath=" + encode(strItemPath)
                    + "&URL=" + encode(strUrl) + "&strRequestType=" + encode(strRequestType)
                    + "&strPagingType=" + encode(strPagingType) + "&strPagingParams=" + encode(strPagingParams)
                    + "&strPagingStep=" + encode(strPagingStep) + "&strNextPageType=" + encode(strNextPageType) 
                    + "&strArticlePath=" + encode(strArticlePath) + "&strPathType=" + encode(strPathType)
                    + "&strNextPagePath=" + encode(strNextPagePath) + "&strNextPageStr=" + encode(strNextPageStr)
                    + "&strLastPageType=" + encode(strLastPageType) + "&strLastPageStr=" + encode(strLastPageStr)
                    + "&strLastPageUrl=" + encode(strLastPageUrl);
    } else {
        alertText("错误参数！", 3500);
        return false;
    }

    $.ajax({
        type: "post",
        url: requestUrl,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("测试失败！", 4500);
            } else {
                showCheckTaskdiv(r.toLowerCase() == "true" ? "这是最后一页" : r);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function FromNewsGatherTaskBackNewsGatherDom(){
    $("#NewsGatherTaskListDom").animate({ width: "hide" });
    $('#NewsGatherDom').show();
}

function SaveNewsGatherTask(){
	var strName = $("#txt_NewsGatherTaskName").val();
    var strUrl = $("#txt_NewsGatherTaskURL").val();
    var strRemark = $("#txt_NewsGatherTaskRemark").val();
    var strRequestType = $("#sel_requestType").val();
    var strPagingType = $("#txt_NewsGatherTaskpagingType").val();
    var strPagingParams = $("#txt_NewsGatherTaskpagingParams").val();
    var strPagingStep = $("#txt_NewsGatherTaskpagingStep").val();
    var strNextPageType = $("#txt_NewsGatherTasknextPageType").val();
    var strNextPagePath = $("#txt_NewsGatherTasknextPagePath").val();
    var strNextPageStr = $("#txt_NewsGatherTasknextPageStr").val();
    var strLastPageType = $("#txt_NewsGatherTasklastPageType").val();
    var strLastPageStr = $("#txt_NewsGatherTasklastPageStr").val();
    var strLastPageUrl = $("#txt_NewsGatherTasklastPageUrl").val();
    var strArticlePath = $("#txt_NewsGatherTaskarticlePath").val();
    var strPathType = $("#txt_NewsGatherTaskpathType").val();
    
    var strParams = "";
    var strItemPath = "";
    if ($.trim(strName) == "") {
        alertText("任务名不能为空！", 3500);
        return false;
    }
    
  //保存taskParamsList表中每行的数据
    $("#NewsGatherTaskParamsList tr").each(function () {
        strParams += $(this).children().eq(0).attr("rel") + "|" + $(this).children().eq(1).children().val() + "|" + $(this).children().eq(2).children().val() + "<>";
    });
    if (strParams != "") {
        strParams = strParams.substring(0, strParams.length - 2);
    }
    //保存ItemPath中对应的数据
    
    $("#NewsGatherTaskItemPathList tr").each(function () {
        strItemPath += $(this).children().eq(0).attr("rel") + "|" + $(this).children().eq(0).children().val() + "|" + $(this).children().eq(1).children().val() + "|"
                    + $(this).children().eq(2).children().val() + "|" + $(this).children().eq(3).children().val() + "<>";
    });
    if (strItemPath != "") {
        strItemPath = strItemPath.substring(0, strItemPath.length - 2);
    }
    
  //将得到的数据传入后台
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/NewsGatherTaskServlet?methodName=" + getParam("hid_NewsGatherTaskListParam", "methodName")
            + "&id=" + getParam("hid_NewsGatherTaskListParam", "id") + "&gid=" + getParam("hid_NewsGatherTaskListParam", "gid") + "&name=" + encode(strName) 
            + "&remark=" + encode(strRemark) + "&strParams=" + encode(strParams) + "&strItemPath=" + encode(strItemPath)
            + "&URL=" + encode(strUrl) + "&strRequestType=" + encode(strRequestType)
            + "&strPagingType=" + encode(strPagingType) + "&strPagingParams=" + encode(strPagingParams)
            + "&strPagingStep=" + encode(strPagingStep) + "&strNextPageType=" + encode(strNextPageType) 
            + "&strArticlePath=" + encode(strArticlePath) + "&strPathType=" + encode(strPathType)
            + "&strNextPagePath=" + encode(strNextPagePath) + "&strNextPageStr=" + encode(strNextPageStr)
            + "&strLastPageType=" + encode(strLastPageType) + "&strLastPageStr=" + encode(strLastPageStr)
            + "&strLastPageUrl=" + encode(strLastPageUrl),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {//保存失败
                alertText("保存失败！", 3500);
            } 
            else if(r =="sessionOut"){
            	doLogout();
            	
            }else {//保存成功
                alertText("保存成功！", 3500);
                //更新更改信息，页面跳转
                var data = $.parseJSON(r);
                UpdateNewsGatherTaskInfo(data);
                $('#NewsGatherTaskDom_Edit').animate({ width: "hide" });
                $("#NewsGatherTaskListDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateNewsGatherTaskInfo(data){
	var str = "";
    if(getParam("hid_NewsGatherTaskListParam", "methodName") == "add"){//当为新增时，在TaskList 列表中追加数据
        str += GetNewsGatherTaskEntry(data, '*');
        $("#NewsGatherTaskList").find('.noDataSrc').remove();
        $("#NewsGatherTaskList").prepend(str);
        var dataTotal = parseInt($("#NewsGatherTaskPaging .dataCount").text())+1;
        $("#NewsGatherTaskPaging .dataCount").text(dataTotal)
        $("#NewsGatherTaskPaging .pageCount").text(parseInt((dataTotal + 9) / 10));
        $("#NewsGatherTaskPaging .pageIndex").text(parseInt((dataTotal + 9) / 10));
    }else if(getParam("hid_NewsGatherTaskListParam", "methodName") == "edit"){//当为编辑时，在对应的表项中修改显示信息
        var obj =  $("#NewsGatherTaskList").find('.editObject').find('td');
        obj.eq(1).html('*');
        obj.eq(2).html(data.TaskName);
        obj.eq(5).html(data.Remark);
        obj.eq(6).html(data.CreatePerson);
        obj.eq(7).html(data.CreateTime);
        $("#NewsGatherTaskList").find('.editObject').removeClass('editObject');
    }
}

function CreateNewsGatherTaskItemPathLine(){
	$("#NewsGatherTaskItemPathList").append("<tr><td rel='0'><input type='text' /></td><td><input type='text' /></td><td><select>"
            + "<option value='CLASS'>Class</option><option value='ID'>Id</option>"
            + "<option value='SELECTION'>Selection</option><option value='TEXT'>Text</option>"
            + "</select></td><td><select><option value='CLASS'>Class</option>"
            + "<option value='TEXT'>Text</option><option value='HREF'>Href</option>"
            + "<option value='HREF_REL'>Href_rel</option><option value='TITLE'>Title</option>"
            + "<option value='HTML'>Html</option>"
            + "</select></td><td><input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelNewsItemPathRow(this)' /></td></tr>");
}

function DelNewsItemPathRow(obj){
	if($(obj).parent().parent().children().eq(0).attr("rel")!="0")//如果当前数据已经在数据库中存储过，则删除数据库中对应数据
	 {
	  $.ajax({
	        type: "post",
	        url: projectLocation
						+ "servlet/NewsGatherTaskServlet?methodName=ItemPathDel&id=" +$(obj).parent().parent().children().eq(0).attr("rel"),
	        dataType: "text",
	        async: false,
	        success: function (r) {
	            if (r == "true") {
	            	  $(obj).parent().parent().remove();
	            }
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
	 }
   else//否则，直接删除该行
   	$(obj).parent().parent().remove();
}

function NewsGatherTaskExcute(obj,Id){
	$(obj).attr("disabled", true);//使执行项的执行按钮无效
	//更改执行项的状态为"执行中"
    var tmp = $(obj).parent().parent();
    tmp.children('.NewsGatherTaskState').children().remove();

    tmp.children('.NewsGatherTaskAnum').text('--');
    tmp.children('.NewsGatherTaskState').append("<span style='color:orange'>执行中<span>");
    var ord = tmp.children('.NewsGatherTaskOrd').text();
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/NewsGatherTaskServlet?methodName=Excute&id=" + Id,
        dataType: "text",
        async: true,
        success: function (r) {
			$(obj).attr("disabled", false);
            var data = $.parseJSON(r);

            tmp.children('.NewsGatherTaskAnum').text(data.ArticleNum);//执行后更新商品数
            if (data.StateNum == "0") {//如果执行后Task的状态为0，则执行成功
                alertText(ord + "号数据采集完成！", 3500);
                tmp.children('.NewsGatherTaskState').children().remove();
                tmp.children('.NewsGatherTaskState').append("<span style='color:green;'>" + data.State + "<span>");
            } else {//如果不为0，则执行失败
                alertText(ord + "号数据采集失败！", 3500);
                tmp.children('.NewsGatherTaskState').children().remove();
                tmp.children('.NewsGatherTaskState').append("<span style='color:red;'>" + data.State + "<span>");
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function NewsGatherTaskBatchexcution(){
	$(".batExcuteBtn").attr("disabled", true);//执行时，批量执行按钮失效
    var strIDS = "";
    $("#NewsGatherTaskList tr").each(function () {//统计TaskList表中被勾选的任务，获得他们的Id并更改显示的状态
        if ($(this).children("td")[0].children[0].checked) {
        	$(this).children("td").children('.excuteBtnF').attr('disabled', true);
        	$(this).children('.NewsGatherTaskAnum').text('--');
        	$(this).children('.NewsGatherTaskState').children().remove();
        	$(this).children('.NewsGatherTaskState').append("<span style='color:orange'>执行中<span>");
            strIDS += $(this).children().eq(0).children().attr("rel") + ",";
        }
    });
    if (strIDS != "") {
        strIDS = strIDS.substring(0, strIDS.length - 1);
        $.ajax({
            type: "post",
            url: projectLocation
    					+ "servlet/NewsGatherTaskServlet?methodName=bexcute&ids=" + strIDS,
            dataType: "text",
            async: true,
            success: function (r) {
				$(".excuteBtnF").attr("disabled", false);
				$(".batExcuteBtn").attr("disabled", false);
                if (r == "false") {
                    alert("批量任务执行失败！");
                } else if (r == "sessionOut") {
                    doLogout();
                }
                else {
                	  alert("批量任务执行成功！");
                	var data = $.parseJSON(r);
                	for (var i = 0; i < data.TaskStateList.length; i++)
                		  {//对于TaskList中的每一行，如果它是被执行的任务，更改它的显示状态
	                	 $("#NewsGatherTaskList tr").each(function () {
	                	        if ($(this).children().eq(0).children().attr("rel") == data.TaskStateList[i].Id) {
	                	        	
	                	        		$(this).children('.NewsGatherTaskAnum').text(data.TaskStateList[i].ArticleNum);
	                	        		if (data.TaskStateList[i].StateNum == "0")
	                	        		{
	                	        			$(this).children('.NewsGatherTaskState').children().remove();
		                	            	$(this).children('.NewsGatherTaskState').append("<span style='color:green;'>"+data.TaskStateList[i].State+"<span>");
	                	        		
	                	        		}
	                	        		else
	                	        			{
	                	        			$(this).children('.NewsGatherTaskState').children().remove();
		                	            	$(this).children('.NewsGatherTaskState').append("<span style='color:red;'>"+data.TaskStateList[i].State+"<span>");
	                	        			}
	                	            	
	                	            
	                	        }
	                	    });
                		  }
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    }
}

function DelNewsGatherTask(id){
    $.ajax({
            type: "post",
            url: projectLocation + "servlet/NewsGatherTaskServlet?methodName=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                 if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetNewsGatherTaskList(1);
                } else {
                    alertText("删除失败！", 3500);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        }); 
}
/*****************************************************
function showNewsGatherDialog(methodName, Id, pos){
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#NewsGatherDom_Edit').show();
    if(methodName == "add"){//新增
        $('#txt_NewsGatherRemark').val('');
        $("#hid_NewsGatherParam").val("methodName=add");
        $('#txt_NewsGatherWebsite').text('');
        $('#txt_NewsGatherWebsite').val('');
        $('#editNewsGatherTargetTable').hide();
        $('#addNewsGatherTarget').show();
    }else if(methodName == 'edit'){//编辑
         $.ajax({
            type: "post",
            url: projectLocation + "servlet/NewsGatherServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $(pos).parent().parent().addClass('editObject');
                    $('#editNewsGatherTargetTable').show();
                    $("#hid_NewsGatherParam").val("methodName=edit&id=" + Id);
                    $("#txt_NewsGatherRemark").val(r.Remark);
                    $('#editNewsGatherTargetList').html('');
                    $('#addNewsGatherTarget').hide();
                    str = "";
                    str += "<tr>";
                    str += "<td><label for='dg"+r.WebsiteId+"'>" + r.WebsiteName + "</label></td>";
                    str += "<td>" + r.Location + "</td>";
                    str += "<td>" + r.WebStyle + "</td>";
                    str += "<td>" + r.Address + "</td>";
                    str += "<td><input type='button' value='更改' onclick='GetNewsGatherTargetDom()'/></td>"
                    str += "</tr>";
                    $('#editNewsGatherTargetList').append(str);
                    $('#txt_NewsGatherWebsite').val(r.WebsiteId);
                    $('#txt_NewsGatherWebsite').text(r.WebsiteName);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    }
}




function SaveNewsGather(){
    var strName = $("#txt_NewsGatherWebsite").val();
    var strRemark = $("#txt_NewsGatherRemark").val();
    if ($.trim(strName) == "") {
        alertText("类型名不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/NewsGatherServlet?methodName="
                                + getParam("hid_NewsGatherParam", "methodName") + "&id=" + getParam("hid_NewsGatherParam", "id")
                                + "&website=" + encode(strName) + "&remark=" + encode(strRemark),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("保存失败！", 3500);
            } else {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateNewsGatherInfo(data);
                $("#NewsGatherDom_Edit").animate({ width: "hide" });
                $('#NewsGatherDom').show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateNewsGatherInfo(data){
    if(getParam("hid_NewsGatherParam", "methodName") == "add"){
        var str = GetNewsGatherEntry(data, '*');
        $("#NewsGatherList").find('.noDataSrc').remove();
        $("#NewsGatherList").prepend(str);
    }else if(getParam("hid_NewsGatherParam", "methodName") == "edit"){
        var obj = $('#NewsGatherList').find('.editObject').find('td');
        obj.eq(1).html(data.WebsiteName);
        obj.eq(5).html(data.Remark);
        $("#NewsGatherList").find('.editObject').removeClass('editObject');
    }
}
********************************************/

