function FirstPageTest() {
	GetTestList(1);
}

function PreviousPageTest() {
    var pageIndex = (parseInt($("#testPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#testPaging .pageIndex").text()) - 1);
    GetTestList(pageIndex);
}

function NextPageTest() {
    var pageIndex = ($("#testPaging .pageIndex").text() == $("#testPaging .pageCount").text() ? parseInt($("#testPaging .pageIndex").text()) : parseInt($("#testPaging .pageIndex").text()) + 1);
    GetTestList(pageIndex);
}

function EndPageTest() {
	GetTestList(parseInt($("#testPaging .pageCount").text()));
}

function GetTestEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.Style + "</td>";
    str += "<td>" + data.Num + "</td>";
    str += "<td>" + data.Score + "</td>";
    str += "<td>" + data.Remark + "</td>";
    str += "<td>" + data.StartTime.substring(0, 19) + "</td>";
    str += "<td>" + data.EndTime.substring(0, 19) + "</td>";
    str += "<td>" + data.CreatePerson + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showTestDialog(\"edit\",\"" + data.Id + "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""+data.Id+"\",DelTest)' /></td>";
    str += "</tr>";
    return str;
}

function GetTestList(strPageIndex) {
    var strQuery = $("#txt_testQuery").val();
    var strStraTime = $("#txt_testBeginDate").val();
    var strEndTime = $("#txt_testEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/TestServlet?methodName=QueryList&strQuery="
            					+ strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#testList").html('');
            if (r == "false") {
                $("#testList").append("<tr class='noDataSrc'><td colspan='10' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#testPaging .dataCount").text(data.total);
            $("#testPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#testPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
                str += GetTestEntry(data.webList[i], i+1);
            }
            $("#testList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelTest(id) {
	    $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/TestServlet?methodName=del&id=" + id,
	        dataType: "text",
	        async: false,
	        success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetTestList(1);
                } else {
                    alertText("删除失败！", 3500);
                }
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    }); 
}

function showTestDialog(methodName, Id, pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#testDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/TestServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	getTestStyle();
                    $(pos).parent().parent().addClass('editObject');
                	$("#hid_testParam").val("methodName=edit&id=" + Id);
                    $("#txt_testName").val(r.Name);
                    $("#txt_testRemark").val(r.Remark);
                    $("#txt_testNum").val(r.Num);
                    $("#txt_testTargetQuery").val("");
                    $("#op_testTarget").html('');
                    $("#op_testStyle").val('op'+r.Type+'i'+r.Style);
                    $("#txt_testScore").val(r.Score);
                    $("#txt_testEditStartTime").val(r.StartTime.substring(0, 19));
                    $("#txt_testEditEndTime").val(r.EndTime.substring(0, 19));
                    getTestStyleType();
                    GetTestTargetTable(Id);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
    	getTestStyle();
        $("#hid_testParam").val("methodName=add");
        $("#txt_testName").val("");
        $("#txt_testRemark").val("");
        $("#txt_testNum").val("");
        $("#txt_testTargetQuery").val("");
        $("#testTargetTable").hide();
        $("#op_testTarget").html("");
        $("#txt_testScore").val("");
        $("#txt_testEditStartTime").val("");
        $("#txt_testEditEndTime").val("");
        getTestStyleType();
    }
}

function SaveTest() {
    var strName = encode($("#txt_testName").val());
    var strRemark = encode($("#txt_testRemark").val());
    var strNum = $("#txt_testNum").val();
    var strStyle = $("#op_testStyle").val().substr(4);
    var strScore = $("#txt_testScore").val();
    var strStartTime = $("#txt_testEditStartTime").val();
    var strEndTime = $("#txt_testEditEndTime").val();
    var strWebTarget = "", strProTarget="";
    $('#op_testTarget').children('div').each(function(){
        var str = $(this).attr('id');
        if(str.charAt(15) == 'w')
            strWebTarget += str.substr(16) +"|";
        else
            strProTarget += str.substr(16) +"|";
    })
    if ($.trim(strName) == "") {
        alertText("类型名不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/TestServlet?methodName="
                                + getParam("hid_testParam", "methodName") + "&id=" + getParam("hid_testParam", "id")
                                + "&name=" + strName + "&remark=" + strRemark + "&num=" + strNum + "&score=" + strScore
                                + "&startTime=" + strStartTime + "&endTime=" + strEndTime +"&style="+strStyle+"&webTarget="+strWebTarget+"&proTarget="+strProTarget,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("保存失败！", 3500);
            } else {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateTest(data);
                $('#testDom_Edit').animate({ width: "hide" });
                $("#testDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateTest(data){
    var str = "";
    if(getParam("hid_testParam", "methodName") == "add"){
        str += GetTestEntry(data, '*');
        $("#testList").find('.noDataSrc').remove();
        $("#testList").prepend(str);
    }else if(getParam("hid_testParam", "methodName") == "edit"){
        var obj =  $("#testList").find('.editObject').find('td');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.Style);
        obj.eq(3).html(data.Num);
        obj.eq(4).html(data.Score);
        obj.eq(5).html(data.Remark);
        obj.eq(6).html(data.StartTime.substring(0, 19));
        obj.eq(7).html(data.EndTime.substring(0, 19));
        obj.eq(8).html(data.CreatePerson);
        $("#testList").find('.editObject').removeClass('editObject');
    }
}

function getTestStyle(){
    $("#op_testStyle").html('');
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/TestServlet?methodName=style",
        dataType: "text",
        async: false,
        success: function (data) {
            if (data != "false") {
            	var str = "";
                var r = $.parseJSON(data);
            	for (var i = 0; i < r.total; i++) {
            		str += "<option value ='op"+r.webList[i].Type+"i"+r.webList[i].Id+"'>"+r.webList[i].Name+"</option>";
            	}
            	$("#op_testStyle").append(str);
                $("#op_testStyle").val(r.webList[0].Id);
            }else{
                alertText('缺少测试类型！', 5000);
                $('#addWebsiteTestTarget').hide();
                $('#addProductTestTarget').hide();
				
				// updated by liqiuling
				confirmShow("",transferToTestStyle,"是否转到测试类型插入？");
             //   if (confirm("是否转到测试类型插入？")) {
             //       transferToTestStyle();
             //   }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
	});
}

function transferToTestStyle(){
    $("#testDom_Edit").animate({ width: "hide" });
    $('#testStyleDom_Edit').show();
}

function getTestStyleType(){
    var type = $('#op_testStyle').val().charAt(2);
    if(type == "3"){
        $('#addProductTestTarget').show();
        $('#addWebsiteTestTarget').show();
    }else if(type == "2"){
        $('#addWebsiteTestTarget').hide();
        $('#addProductTestTarget').show();
    }else{
        $('#addWebsiteTestTarget').show();
        $('#addProductTestTarget').hide();
    }
}