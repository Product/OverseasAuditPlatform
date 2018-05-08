function FirstPageWebisteTestTarget() {
	GetWebsiteTestTargetList(1);
}

function PreviousPageWebsiteTestTarget() {
    var pageIndex = (parseInt($("#websiteTestTargetPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#websiteTestTargetPaging .pageIndex").text()) - 1);
    GetWebsiteTestTargetList(pageIndex);
}

function NextPageWebsiteTestTarget() {
    var pageIndex = ($("##websiteTestTargetPaging .pageIndex").text() == $("#websiteTestTargetPaging .pageCount").text() ? parseInt($("#websiteTestTargetPaging .pageIndex").text()) : parseInt($("##websiteTestTargetPaging .pageIndex").text()) + 1);
    GetWebsiteTestTargetList(pageIndex);
}

function EndPageWebsiteTestTarget() {
	GetWebsiteTestTargetList(parseInt($("#websiteTestTargetPaging .pageCount").text()));
}

function FirstPageProductTestTarget() {
    GetProductTestTargetList(1);
}

function PreviousPageProductTestTarget() {
    var pageIndex = (parseInt($("#productTestTargetPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#productTestTargetPaging .pageIndex").text()) - 1);
    GetProductTestTargetList(pageIndex);
}

function NextPageProductTestTarget() {
    var pageIndex = ($("#productTestTargetPaging .pageIndex").text() == $("#productTestTargetPaging .pageCount").text() ? parseInt($("#productTestTargetPaging .pageIndex").text()) : parseInt($("#productTestTargetPaging .pageIndex").text()) + 1);
    GetProductTestTargetList(pageIndex);
}

function EndPageProductTestTarget() {
    GetProductTestTargetList(parseInt($("#productTestTargetPaging .pageCount").text()));
}

function GetWebsiteTestTargetDom(){
    $('#testDom_Edit').animate({ width: "hide" });
    $('#testDom_AddWebsiteTarget').show();
    $('#websiteTestTargetList').html('');
    $('txt_websiteTestTargetQuery').val();
    $('txt_websiteTestTargetBeginDate').val();
    $('txt_websiteTestTargetEndDate').val();
}

function GetProductTestTargetDom(){
    $('#testDom_Edit').animate({ width: "hide" });
    $('#testDom_AddProductTarget').show();
    $('#productTestTargetList').html('');
    $('txt_productTestTargetQuery').val();
    $('txt_productTestTargetBeginDate').val();
    $('txt_productTestTargetEndDate').val();
}

function GetWebsiteTestTargetList(strPageIndex) {
    var strQuery = $("#txt_websiteTestTargetQuery").val();
    var strStraTime = $("#txt_websiteTestTargetBeginDate").val();
    var strEndTime = $("#txt_websiteTestTargetEndDate").val();
    $("#websiteTestTargetList").html('');
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/TestTargetServlet?methodName=webTargetQuery&strQuery="+ strQuery+ "&strStraTime=" + strStraTime + "&strEndTime="
                                + strEndTime + "&strPageIndex=" + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (data) {
            if (data != "false") {
                var r=$.parseJSON(data);
                var webstr = "";
                for (var i = 0; i < r.webList.length; i++) {
                    webstr += "<tr>";
                    webstr += "<td><input type='checkbox' class='websiteTestTarget' value='w"+r.webList[i].Id+"' name='webtarget' id='ew"+r.webList[i].Id+"'></td>";
                    webstr += "<td><a href='"+r.webList[i].Location+"' class='testTargetName'>" + r.webList[i].Name + "</a></td>";
                    webstr += "<td><a href='"+r.webList[i].Location+"'>" + r.webList[i].Location + "</a></td>";
                    webstr += "<td>" + r.webList[i].Style + "</td>";
                    webstr += "<td>" + r.webList[i].Address + "</td>";
                    webstr += "</tr>";
                }
                $("#websiteTestTargetList").append(webstr);
            }else{
                $("#websiteTestTargetList").append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}


function GetProductTestTargetList(strPageIndex) {
    var strQuery = $("#txt_productTestTargetQuery").val();
    var strStraTime = $("#txt_productTestTargetBeginDate").val();
    var strEndTime = $("#txt_productTestTargetEndDate").val();
    $("#productTestTargetList").html('');
    $.ajax({
        type: "post",
        url: projectLocation +"servlet/TestTargetServlet?methodName=proTargetQuery&strQuery="+ strQuery+ "&strStraTime=" + strStraTime + "&strEndTime="
                                + strEndTime + "&strPageIndex=" + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (data) {
            if (data != "false") {
                var r=$.parseJSON(data);
                var prostr="";
                 for (var i = 0; i < r.proTotal; i++) {
                    prostr += "<tr>";
                    prostr += "<td><input type='checkbox' class='productTestTarget' value='p"+r.proList[i].Id+"' name='protarget' id='ep"+r.proList[i].Id+"'></td>";
                    prostr += "<td><a href='"+r.proList[i].Location+"' class='testTargetName'>" + r.proList[i].Name + "</a></td>";
                    prostr += "<td><a href='"+r.proList[i].Location+"'>" + r.proList[i].Location + "</a></td>";
                    prostr += "<td>" + r.proList[i].Style + "</td>";
                    prostr += "<td>" + r.proList[i].Address + "</td>";
                    prostr += "</tr>";
                }
                $("#productTestTargetList").append(prostr);
            }else{
                $("#productTestTargetList").append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function GetTestTargetTable(Id){
    $("#testTargetsList").html('');
    $("#testTargetTable").show();
    $.ajax({
            type: "post",
            url: projectLocation + "servlet/TestTargetServlet?methodName=getTestTargets&Id=" + Id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "false") {
                    $("#testTargetsList").append("<tr><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                    return false;
                }
                var data = $.parseJSON(r);
                var str="";
                for (var i = 0; i < data.webList.length; i++) {
                    str += "<tr>";
                    str += "<td>" + (i + 1) + "</td>";
                    str += "<td>" + data.webList[i].Name + "</td>";
                    str += "<td>" + data.webList[i].Location + "</td>";
                    str += "<td>" + data.webList[i].Style + "</td>";
                    str += "<td>" + data.webList[i].Address + "</td>";
                    str += "<td><input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""+data.webList[i].Id+"\",DelTestTarget)' /></td>";
                    str += "</tr>";
                }
                $("#testTargetsList").append(str);
            },
            error: function (e) {
                alert(e.responseText);
            }
    });
}

function DelTestTarget(id){
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/TestTargetServlet?methodName=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetTestTargetTable(getParam("hid_testParam", "id"));
                } else {
                    alertText("删除失败！", 3500);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        }); 
}

function DelTest_TestTarget(obj){
    document.getElementById($(obj).val()).checked=false;
    $(obj).parent().remove();
    $('#'+$(obj).val()).attr('checked', false);
}

function CheckProductTestTarget(){
    $("input[name='protarget']:checked").each(function(){
        $('#op_testTarget').append("<div id='test_TestTarget"+$(this).val()+"' class='testTargetLabel testTargetProductLabel'>"+$(this).parent().parent().find('.testTargetName').text()+"<button class='testTargetDel' onclick='DelTest_TestTarget(this)' value='"+$(this).attr('id')+"'>×</button></div>");
    });
    GoBackProductTestTarget();
}

function CheckWebsiteTestTarget(){
    $("input[name='webtarget']:checked").each(function(){
        $('#op_testTarget').append("<div id='test_TestTarget"+$(this).val()+"' class='testTargetLabel testTargetWebsiteLabel'>"+$(this).parent().parent().find('.testTargetName').text()+"<button class='testTargetDel' onclick='DelTest_TestTarget(this)' value='"+$(this).attr('id')+"'>×</button></div>");
    });
    GoBackWebsiteTestTarget();
}

function ClearWebsiteTestTarget(){
    $('.websiteTestTarget').attr('checked', false); 
}

function ClearProductTestTarget(){
    $('.productTestTarget').attr('checked', false); 
}

function GoBackWebsiteTestTarget(){
    $('#testDom_AddWebsiteTarget').animate({ width: "hide" });
    $('#testDom_Edit').show();
}

function GoBackProductTestTarget(){
    $('#testDom_AddProductTarget').animate({ width: "hide" });
    $('#testDom_Edit').show();
}