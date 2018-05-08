function FirstPageEvaluationIndex() {
	GetEvaluationIndexList(1);
}

function PreviousPageEvaluationIndex() {
    var pageIndex = (parseInt($("#evaluationIndexPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#evaluationIndexPaging .pageIndex").text()) - 1);
    GetEvaluationIndexList(pageIndex);
}

function NextPageEvaluationIndex() {
    var pageIndex = ($("#evaluationIndexPaging .pageIndex").text() == $("#evaluationIndexPaging .pageCount").text() ? parseInt($("#evaluationIndexPaging .pageIndex").text()) : parseInt($("#evaluationIndexPaging .pageIndex").text()) + 1);
    GetEvaluationIndexList(pageIndex);
}

function EndPageEvaluationIndex() {
	GetEvaluationIndexList(parseInt($("#evaluationIndexPaging .pageCount").text()));
}

function GetEvaluationIndexEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.StyleName + "</td>";
    str += "<td>" + data.Weight + "</td>";
    str += "<td>" + data.Remark + "</td>";
    str += "<td>" + data.CreatePerson + "</td>";
    str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showEvaluationIndexDialog(\"edit\",\"" + data.Id + "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""+data.Id+"\", DelEvaluationIndex)' /></td>";
    str += "</tr>";
    return str;
}

function GetEvaluationIndexList(strPageIndex) {
    var strQuery = $("#txt_evaluationIndexQuery").val();
    var strStraTime = $("#txt_evaluationIndexBeginDate").val();
    var strEndTime = $("#txt_evaluationIndexEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EvaluationIndexServlet?methodName=QueryList&strQuery="
            					+ strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#evaluationIndexList").html('');
            if (r == "false") {
                $("#evaluationIndexList").append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#evaluationIndexPaging .dataCount").text(data.total);
            $("#evaluationIndexPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#evaluationIndexPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 1; i <= data.webList.length; i++) {
                str += GetEvaluationIndexEntry(data.webList[i-1], i);
            }
            $("#evaluationIndexList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelEvaluationIndex(id) {
	    $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/EvaluationIndexServlet?methodName=del&id=" + id,
	        dataType: "text",
	        async: false,
	        success: function (r) {
                 if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetEvaluationIndexList(1);
                } else {
                    alertText("删除失败！", 3500);
                }
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    }); 
}

function getEvaluationStyle(){
    $("#op_evaluationIndexStyle").html('');
    $.ajax({
            type: "post",
            url: projectLocation + "servlet/EvaluationStyleServlet?methodName=style",
            dataType: "text",
            async: false,
            success: function (r) {
                if(r != 'false'){
                    var data = $.parseJSON(r);var str = "";
                    for (var i = 0; i < data.length; i++) {
                        str += "<option value ='"+data[i].Id+"'>"+data[i].Name+"</option>";
                    }
                    $("#op_evaluationIndexStyle").append(str);
                    $("#op_evaluationIndexStyle").val(data[0].Id);
                }else{
                    alertText('缺少评估类型！', 3500);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
    });
}
function showEvaluationIndexDialog(methodName, Id, pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#evaluationIndexDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/EvaluationIndexServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    getEvaluationStyle();
                    $(pos).parent().parent().addClass('editObject');
                    $("#hid_evaluationIndexParam").val("methodName=edit&id=" + Id);
                    $("#txt_evaluationIndexName").val(r.Name);
                    $("#op_evaluationIndexStyle").val(r.Style);
                    $("#txt_evaluationIndexWeight").val(r.Weight);
                    $("#txt_evaluationIndexRemark").val(r.Remark);
                    $("#op_evaluationIndexType").val(r.Type);
                    $('#txt_evaluationIndexNoneMin').val(r.NoneMin); 
                    $('#txt_evaluationIndexNoneMax').val(r.NoneMax);
                    $('#txt_evaluationIndexOneMin').val(r.OneMin);
                    $('#txt_evaluationIndexOneMax').val(r.OneMax);
                    $('#txt_evaluationIndexTwoMin').val(r.TwoMin);
                    $('#txt_evaluationIndexTwoMax').val(r.TwoMax);
                    $('#txt_evaluationIndexThreeMin').val(r.ThreeMin);
                    $('#txt_evaluationIndexThreeMax').val(r.ThreeMax);
                    $('#txt_evaluationIndexScoreYes').val(r.ScoreYes);
                    $('#txt_evaluationIndexScoreNo').val(r.ScoreNo);
                    EvaluationIndexTypeChange();
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        getEvaluationStyle()
        $("#hid_evaluationIndexParam").val("methodName=add");
        $("#txt_evaluationIndexName").val("");
        $("#txt_evaluationIndexWeight").val("");
        $("#txt_evaluationIndexRemark").val("");
        $("#op_evaluationIndexType").val(1);
        $('#txt_evaluationIndexNoneMin').val(""); 
        $('#txt_evaluationIndexNoneMax').val("");
        $('#txt_evaluationIndexOneMin').val("");
        $('#txt_evaluationIndexOneMax').val("");
        $('#txt_evaluationIndexTwoMin').val("");
        $('#txt_evaluationIndexTwoMax').val("");
        $('#txt_evaluationIndexThreeMin').val("");
        $('#txt_evaluationIndexThreeMax').val("");
        $('#txt_evaluationIndexScoreYes').val("");
        $('#txt_evaluationIndexScoreNo').val("");
        EvaluationIndexTypeChange();

    }
}

function SaveEvaluationIndex() {
    var strName = encode($("#txt_evaluationIndexName").val());
    var strStyle = $("#op_evaluationIndexStyle").val();
    var strWeight = $("#txt_evaluationIndexWeight").val();
    var strRemark = encode($("#txt_evaluationIndexRemark").val());
    var strType = $('#op_evaluationIndexType').val();
    var none_min, none_max, one_min, one_max, two_min, two_max, three_min, three_max;
    var score_yes, score_no;
    if(strType == 1){
        score_yes = $('#txt_evaluationIndexScoreYes').val();
        score_no = $('#txt_evaluationIndexScoreNo').val();
        none_min = none_max = one_min = one_max = two_min = two_max = three_min = three_max = 0;
    }else{
        none_min = $('#txt_evaluationIndexNoneMin').val(); 
        none_max = $('#txt_evaluationIndexNoneMax').val();
        one_min = $('#txt_evaluationIndexOneMin').val();
        one_max = $('#txt_evaluationIndexOneMax').val();
        two_min = $('#txt_evaluationIndexTwoMin').val();
        two_max = $('#txt_evaluationIndexTwoMax').val();
        three_min = $('#txt_evaluationIndexThreeMin').val();
        three_max = $('#txt_evaluationIndexThreeMax').val();
        score_yes = score_no = 0;
    }
    if ($.trim(strName) == "") {
        alertText("类型名不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EvaluationIndexServlet?methodName="
                                + getParam("hid_evaluationIndexParam", "methodName") + "&id=" + getParam("hid_evaluationIndexParam", "id")
                                + "&name=" + strName + "&style=" + strStyle + "&weight="+strWeight+"&userId=" + getCookie("")
                                + "&type=" + strType + "&nonemax=" + none_max +"&nonemin=" + none_min +"&onemax=" + one_max + "&onemin=" +one_min
                                + "&twomax=" + two_max + "&twomin=" + two_min + "&threemax=" + three_max + "&threemin=" + three_min
                                + "&scoreno=" + score_no + "&scoreyes=" + score_yes + "&remark=" + strRemark,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("保存失败！", 3500);
            } else {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateEvaluationIndex(data);
                $("#evaluationIndexDom_Edit").animate({ width: "hide" });
                $('#evaluationIndexDom').show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateEvaluationIndex(data){
    var str = "";
    if(getParam("hid_evaluationIndexParam", "methodName") == "add"){
        str += GetEvaluationIndexEntry(data, '*');
        $("#evaluationIndexList").find('.noDataSrc').remove();
        $("#evaluationIndexList").prepend(str);
    }else if(getParam("hid_evaluationIndexParam", "methodName") == "edit"){
        var obj = $('#evaluationIndexList').find('.editObject').find('td');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.StyleName);
        obj.eq(3).html(data.Weight);
        
        obj.eq(4).html(data.Remark);
        obj.eq(5).html(data.CreatePerson);
        obj.eq(6).html(data.CreateTime.substring(0, 19));
        $("#evaluationIndexList").find('.editObject').removeClass('editObject');
    }
}


function EvaluationIndexTypeChange(){
    var obj = $('#op_evaluationIndexType');
    if(obj.val() == 1){
        $('#evaluationIndexBool').show();
        $('#evaluationIndexNum').hide();
    }else{
        $('#evaluationIndexBool').hide();
        $('#evaluationIndexNum').show();
    }
}