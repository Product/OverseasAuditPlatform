function FirstPageNewsGather() {
	GetNewsGatherList(1);
}

function PreviousPageNewsGather() {
    var pageIndex = (parseInt($("#NewsGatherPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#NewsGatherPaging .pageIndex").text()) - 1);
    GetNewsGatherList(pageIndex);
}

function NextPageNewsGather() {
    var pageIndex = ($("#NewsGatherPaging .pageIndex").text() == $("#NewsGatherPaging .pageCount").text() ? parseInt($("#NewsGatherPaging .pageIndex").text()) : parseInt($("#NewsGatherPaging .pageIndex").text()) + 1);
    GetNewsGatherList(pageIndex);
}

function EndPageNewsGather() {
	GetNewsGatherList(parseInt($("#NewsGatherPaging .pageCount").text()));
}

function GetNewsGatherEntry(data, idx){
    var str = "<tr>";
     str += "<td class='NewsGatherOrd'>" + idx + "</td>";
                str += "<td>" + data.WebsiteName + "</td>";
                str += "<td class='NewsGatherTnum'>" + data.TaskNum + "</td>";
                str += "<td class='NewsGatherPnum'>" + data.ArticleNum + "</td>";
                if (data.StateNum == 0)//空闲
                    str += "<td class='NewsGatherState'><span style='color:green;'>" + data.State + "</span></td>";
                else if (data.StateNum == 1)//执行中
                    str += "<td class='NewsGatherState'><span style='color:orange;'>" + data.State + "</span></td>";
                else if (data.StateNum == 2)//异常
                    str += "<td class='NewsGatherState'><span style='color:red;'>" + data.State + "</span></td>";
                str += "<td>" + data.Remark + "</td>";
                str += "<td>" + data.CreatePerson + "</td>";

                str += "<td>"
                        + data.CreateTime.substring(0, 19)
                        + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showNewsGatherTasks(\"view\",\"" + data.Id
                        + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showNewsGatherDialog(\"edit\",\"" + data.Id
                        + "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='执 行' onclick='NewsGatherExcute(this, \"" + data.Id
                        + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\"" + data.Id
                        + "\",DelNewsGather)' /></td>";
                str += "</tr>";
    return str;
}

/*
函数名：GetNewsGatherList
作用：获取数据采集列表
*/

function GetNewsGatherList(strPageIndex) {
    var strQuery = $("#txt_NewsGatherQuery").val();
    var strStraTime = $("#txt_NewsGatherBeginDate").val();
    var strEndTime = $("#txt_NewsGatherEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
                + "servlet/NewsGatherServlet?methodName=QueryList&strQuery=" + encode(strQuery)
                + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                + strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#NewsGatherList").html('');
            if (r == "false") {
                $("#NewsGatherList")
                        .append("<tr class='noDataSrc'><td colspan='9' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }else if(r =="sessionOut"){
                doLogout();
                
            }
            var data = $.parseJSON(r);

            $("#NewsGatherPaging .dataCount").text(data.total);
            $("#NewsGatherPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#NewsGatherPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.webList.length; i++) {
                str += GetNewsGatherEntry(data.webList[i], i+1);
            }
            $("#NewsGatherList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelNewsGather(id) {
	    $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/NewsGatherServlet?methodName=del&id=" + id,
	        dataType: "text",
	        async: false,
	        success: function (r) {
                 if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetNewsGatherList(1);
                } else {
                    alertText("删除失败！", 3500);
                }
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    }); 
}

function checkNewsGatherRadio(val){
    document.getElementById("op_NewsGather1").checked=false;
    document.getElementById("op_NewsGather2").checked=false;
    if(val == "true")
        document.getElementById("op_NewsGather1").checked=true;
    else
        document.getElementById("op_NewsGather2").checked=true;
}


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
                    str += "<td><input class='btn btn-default btn-xs' type='button' value='更 改' onclick='GetNewsGatherTargetDom()'/></td>"
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

function showNewsGatherTasks(name, Id){
    $("#hid_NewsGatherParam").val("methodName=tasks&id=" + Id);
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#NewsGatherTaskListDom').show();
    GetNewsGatherTaskByGId(1);
    
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

function NewsGatherExcute(obj,Id){
	$(obj).attr("disabled", true);
    var tmp = $(obj).parent().parent();
    tmp.children('.NewsGatherState').children().remove();
    tmp.children('.NewsGatherTnum').text('--');
    tmp.children('.NewsGatherPnum').text('--');
    tmp.children('.NewsGatherState').append("<span style='color:orange'>执行中<span>");
    var ord = tmp.children('.NewsGatherOrd').text();
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/NewsGatherServlet?methodName=Execute&id=" + Id,
        dataType: "text",
        async: true,
        success: function (r) {
			$(obj).attr("disabled", false);
            var data = $.parseJSON(r);
            tmp.children('.NewsGatherTnum').text(data.TaskNum);
            tmp.children('.NewsGatherPnum').text(data.ArticleNum);
            if (data.StateNum == "0") {//执行成功，状态空闲
                alertText(ord+"号数据采集完成！", 3500);
                tmp.children('.NewsGatherState').children().remove();
                tmp.children('.NewsGatherState').append("<span style='color:green;'>"+data.State+"<span>");
            } else {
                alertText(ord+"号数据采集失败！", 3500);
                tmp.children('.NewsGatherState').children().remove();
                tmp.children('.NewsGatherState').append("<span style='color:red;'>"+data.State+"<span>");
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

