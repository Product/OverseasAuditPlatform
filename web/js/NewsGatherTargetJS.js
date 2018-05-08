function FirstPageNewsGatherTarget() {
    GetNewsGatherTargetList(1);
}

function PreviousPageNewsGatherTarget() {
    var pageIndex = (parseInt($("#NewsGatherTargetPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#NewsGatherTargetPaging .pageIndex").text()) - 1);
    GetNewsGatherTargetList(pageIndex);
}

function NextPageNewsGatherTarget() {
    var pageIndex = ($("#NewsGatherTargetPaging .pageIndex").text() == $("#NewsGatherTargetPaging .pageCount").text() ? parseInt($("#NewsGatherTargetPaging .pageIndex").text()) : parseInt($("#NewsGatherTargetPaging .pageIndex").text()) + 1);
    GetNewsGatherTargetList(pageIndex);
}

function EndPageNewsGatherTarget() {
    GetNewsGatherTargetList(parseInt($("#NewsGatherTargetPaging .pageCount").text()));
}

function GetNewsGatherTargetList(strPageIndex) {
    var strQuery = $("#txt_NewsGatherTargetQuery").val();
    var strStraTime = $("#txt_NewsGatherTargetBeginDate").val();
    var strEndTime = $("#txt_NewsGatherTargetEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/WebsiteServlet?methodName=gatherWebsiteQueryList&strQuery="
                                + encode(strQuery) + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#NewsGatherWebisiteList").html('');
            if (r == "false") {
                $("#NewsGatherWebisiteList").append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#NewsGatherTargetPaging .dataCount").text(data.total);
            $("#NewsGatherTargetPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#NewsGatherTargetPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.websiteList.length; i++) {
                str += "<tr>";
                str += "<td><input type='radio' class='dataGartherWeb' value='"+data.websiteList[i].Id+"' name='NewsGatherWebs' id='dg"+data.websiteList[i].Id+"'></td>";
                str += "<td><label for='dg"+data.websiteList[i].Id+"'>" + data.websiteList[i].Name + "</label></td>";
                str += "<td>" + data.websiteList[i].Location + "</td>";
                str += "<td>" + data.websiteList[i].WebStyle + "</td>";
                str += "<td>" + data.websiteList[i].Address + "</td>";
                str += "</tr>";
                j++;
            }
            $("#NewsGatherWebisiteList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function GetNewsGatherTargetDom(){
    $('#addNewsGatherTarget').hide();
    $("#NewsGatherDom_Edit").animate({ width: "hide" });
    $('#NewsGatherDom_AddTarget').show();
    $("#txt_NewsGatherTargetQuery").val("舆情");
    $("#txt_NewsGatherTargetBeginDate").val("");
    $("#txt_NewsGatherTargetEndDate").val("");
    $("#NewsGatherWebisiteList").html('');
    GetNewsGatherTargetList(1);
}

function GoBackNewsGatherTarget(){
    $("#NewsGatherDom_AddTarget").animate({ width: "hide" });
    $('#NewsGatherDom_Edit').show();
}

function checkNewsGatherTargetRadio(){ 
        var tmp = $("input[name='NewsGatherWebs']:checked");
        if(tmp.length == 0)
            alertText("没有选中网站！", 3500);
        else{
            $('#txt_NewsGatherWebsite').val(tmp.val());
            $('#txt_NewsGatherWebsite').text(tmp.parent().parent().find('label').text());
            GoBackNewsGatherTarget();
        }
}