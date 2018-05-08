function FirstPageEvaluation() {
	GetEvaluationList(1);
}

function PreviousPageEvaluation() {
    var pageIndex = (parseInt($("#evaluationPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#evaluationPaging .pageIndex").text()) - 1);
    GetEvaluationList(pageIndex);
}

function NextPageEvaluation() {
    var pageIndex = ($("#evaluationPaging .pageIndex").text() == $("#evaluationPaging .pageCount").text() ? parseInt($("#evaluationPaging .pageIndex").text()) : parseInt($("#evaluationPaging .pageIndex").text()) + 1);
    GetEvaluationList(pageIndex);
}

function EndPageEvaluation() {
	GetEvaluationList(parseInt($("#evaluationPaging .pageCount").text()));
}

function GetEvaluationEntry(data, idx){
	var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.Location + "</td>";
    str += "<td>" + data.Score + "</td>";
    if(data.Status > 0)
        str += "<td class='evaluationPublished'>" + "已发布" + "</td>";
    else
        str += "<td class='evaluationPublished'>" + "未发布" + "</td>";
    str += "<td>" + data.Remark + "</td>";
    str += "<td>" + data.CreatePerson + "</td>";
    str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
    if(data.Status == 0)
        str += "<td class='evaluationOperate'><input class='evaluationTemp' class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showEvaluationDialog(\"edit\",\"" + data.Id
            + "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelEvaluation(\"" + data.Id
            + "\")' />&nbsp;<input class='evaluationTemp' class='btn btn-default btn-xs' type='button' value='发 布' onclick='PublishEvaluation(\"" + data.Id + "\", this)' /></td>";
    else
        str += "<td><input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""+data.Id+"\",DelEvaluation)' /></td>";
    str += "</tr>";

    return str;
}

function GetEvaluationList(strPageIndex) {
    var strQuery = $("#txt_evaluationQuery").val();
    var strStraTime = $("#txt_evaluationBeginDate").val();
    var strEndTime = $("#txt_evaluationEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EvaluationServlet?methodName=QueryList&strQuery="
            					+ strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#evaluationList").html('');
            if (r == "false") {
                $("#evaluationList").append("<tr class='noDataSrc'><td colspan='9' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#evaluationPaging .dataCount").text(data.total);
            $("#evaluationPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#evaluationPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 1; i <= data.webList.length; i++) {
                str += GetEvaluationEntry(data.webList[i-1], i);
            }
            $("#evaluationList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelEvaluation(id) {
	    $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/EvaluationServlet?methodName=del&id=" + id,
	        dataType: "text",
	        async: false,
	        success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetEvaluationList(1);
                } else {
                    alertText("删除失败！", 3500);
                }
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    }); 
}

function showAddEvaluationTarget(){
    GetEvaluationWebsiteDom();
}

function showEvaluationDialog(methodName, Id, pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#evaluationDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/EvaluationServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $(pos).parent().parent().addClass('editObject');
                    $('#editEvaluationTarget').show();
                    $('#addEvaluationWebsite').hide();
                    $("#hid_evaluationParam").val("methodName=edit&id=" + Id);
                    $("#txt_evaluationRemark").val(r.Remark);
                    $('#evaluationWebsiteTable').hide();
                    $('#txt_evaluationWebsite').val(r.Wid);
                    $('#txt_evaluationWebsite').text(r.Name);
                    $('#editEvaluationWebisiteList').html('');
                    str = "";
                    str += "<tr>";
                    str += "<td><label for='ew"+r.Id+"'>" + r.Name + "</label></td>";
                    str += "<td>" + r.Location + "</td>";
                    str += "<td>" + r.Style + "</td>";
                    str += "<td>" + r.Address + "</td>";
                    str += "<td><input class='btn btn-default btn-xs' type='button' value='更 换' onclick='showAddEvaluationTarget()'></td>"
                    str += "</tr>";
                    $('#editEvaluationWebisiteList').append(str);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $('#editEvaluationTarget').hide();
        $('#addEvaluationWebsite').show();
        $("#hid_evaluationParam").val("methodName=add");
        $("#txt_evaluationWebsite").text("");
        $("#txt_evaluationWebsite").val("");
        $("#txt_evaluationRemark").val("");
        $("#txt_evaluationWebsiteQuery").val("");
        $('#evaluationWebsiteTable').hide();
        $("#editEvaluationWebisiteList").html('');
        $('#checkEW').hide();
    }
}

function SaveEvaluation() {
    var strName = $("#txt_evaluationWebsite").val();
    var strRemark = encode($("#txt_evaluationRemark").val());
    if ($.trim(strName) == "") {
        alertText("类型名不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EvaluationServlet?methodName="
                                + getParam("hid_evaluationParam", "methodName") + "&id=" + getParam("hid_evaluationParam", "id")
                                + "&website=" + strName + "&remark=" + strRemark,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r.res == "false") {
                alertText("保存失败！", 3500);   
            } else {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateEvaluation(data);
                $('#evaluationDom_Edit').animate({ width: "hide" });
                $("#evaluationDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateEvaluation(data){
    var str = "";
    if(getParam("hid_evaluationParam", "methodName") == "add"){
        str += GetEvaluationEntry(data, '*');
        $("#evaluationList").find('.noDataSrc').remove();
        $("#evaluationList").prepend(str);
    }else if(getParam("hid_evaluationParam", "methodName") == "edit"){
        var obj =  $("#evaluationList").find('.editObject').find('td');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.Location);
        obj.eq(3).html(data.Score);
       
        obj.eq(5).html(data.Remark);
        obj.eq(6).html(data.CreatePerson);
        obj.eq(7).html(data.CreateTime.substring(0, 19));
        $("#evaluationList").find('.editObject').removeClass('editObject');
    }
}




function PublishEvaluation(id, obj){
    $.ajax({
            type: "post",
            url: projectLocation + "servlet/EvaluationServlet?methodName=pub&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("发布成功！", 3500);
                    $(obj).parent().parent().children('.evaluationPublished').text('已发布');
                    $(obj).parent().parent().children('.evaluationOperate').children('.evaluationTemp').remove();
                } else {
                    alertText("发布失败！", 3500);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        }); 
}