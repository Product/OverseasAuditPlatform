function FirstPageEvaluationTarget() {
	GetEvaluationTargetList(1);
}

function PreviousPageEvaluationTarget() {
    var pageIndex = (parseInt($("#evaluationPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#evaluationPaging .pageIndex").text()) - 1);
    GetEvaluationTargetList(pageIndex);
}

function NextPageEvaluationTarget() {
    var pageIndex = ($("#evaluationPaging .pageIndex").text() == $("#evaluationPaging .pageCount").text() ? parseInt($("#evaluationPaging .pageIndex").text()) : parseInt($("#evaluationPaging .pageIndex").text()) + 1);
    GetEvaluationTargetList(pageIndex);
}

function EndPageEvaluationTarget() {
	GetEvaluationTargetList(parseInt($("#evaluationPaging .pageCount").text()));
}

function GetEvaluationTargetList(strPageIndex) {
    var strQuery = $("#txt_evaluationTargetQuery").val();
    var strStraTime = $("#txt_evaluationTargetBeginDate").val();
    var strEndTime = $("#txt_evaluationTargetEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/WebsiteServlet?methodName=QueryList&strQuery="
                                + strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $('#evaluationWebsiteTable').show();
            $("#evaluationWebisiteList").html('');
            if (r == "false") {
                $("#evaluationWebisiteList").append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            var str = "";
            var j = 0;
            for (var i = 0; i < data.websiteList.length; i++) {
                str += "<tr>";
                str += "<td><input type='radio' class='evaluationWeb' value='"+data.websiteList[i].Id+"' name='obj' id='ew"+data.websiteList[i].Id+"'></td>";
                str += "<td><label for='ew"+data.websiteList[i].Id+"'>" + data.websiteList[i].Name + "</label></td>";
                str += "<td>" + data.websiteList[i].Location + "</td>";
                str += "<td>" + data.websiteList[i].WebStyle + "</td>";
                str += "<td>" + data.websiteList[i].Address + "</td>";
                str += "</tr>";
                j++;
            }
            $("#evaluationWebisiteList").append(str);
            $('#checkEW').show();
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function checkEvalationWebRadio(){
        var tmp = $("input[name='obj']:checked");
        if(tmp.length == 0)
            alertText("没有选中网站！", 3500);
        else{
            $('#txt_evaluationWebsite').val(tmp.val());
            $('#txt_evaluationWebsite').text(tmp.parent().parent().find('label').text());
        }
        GoBackEvaluationTarget();
}

function GetEvaluationWebsiteDom(){
    $('#evaluationDom_Edit').animate({ width: "hide" });
    $('#evaluationDom_AddTarget').show();
    $('#evaluationWebisiteList').html('');
    $('txt_evaluationTargetQuery').val();
    $('txt_evaluationTargetBeginDate').val();
    $('txt_evaluationTargetEndDate').val();
}

function GoBackEvaluationTarget(){
    $('#evaluationDom_AddTarget').animate({ width: "hide" });
    $('#evaluationDom_Edit').show();
}