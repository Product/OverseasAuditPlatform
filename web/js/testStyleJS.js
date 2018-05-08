function FirstPageTestStyle() {
	GetTestStyleList(1);
}

function PreviousPageTestStyle() {
    var pageIndex = (parseInt($("#testStylePaging .pageIndex").text()) == 1 ? 1 : parseInt($("#testStylePaging .pageIndex").text()) - 1);
    GetTestStyleList(pageIndex);
}

function NextPageTestStyle() {
    var pageIndex = ($("#testStylePaging .pageIndex").text() == $("#testStylePaging .pageCount").text() ? parseInt($("#testStylePaging .pageIndex").text()) : parseInt($("#testStylePaging .pageIndex").text()) + 1);
    GetTestStyleList(pageIndex);
}

function EndPageTestStyle() {
	GetTestStyleList(parseInt($("#testStylePaging .pageCount").text()));
}

function GetTestStyleEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.TypeName + "</td>";
    str += "<td>" + data.Remark + "</td>";
    str += "<td>" + data.CreatePerson + "</td>";
    str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showTestStyleDialog(\"edit\",\"" + data.Id + "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""+data.Id+"\",DelTestStyle)' /></td>";
    str += "</tr>";
    return str;
}

function GetTestStyleList(strPageIndex) {
    var strQuery = $("#txt_testStyleQuery").val();
    var strStraTime = $("#txt_testStyleBeginDate").val();
    var strEndTime = $("#txt_testStyleEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/TestStyleServlet?methodName=QueryList&strQuery="
            					+ strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#testStyleList").html('');
            if (r == "false") {
                $("#testStyleList").append("<tr class='noDataSrc'><td colspan='7' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#testStylePaging .dataCount").text(data.total);
            $("#testStylePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#testStylePaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.webList.length; i++) {
                str += GetTestStyleEntry(data.webList[i], i+1);
                j++;
            }
            $("#testStyleList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelTestStyle(id) {
	    $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/TestStyleServlet?methodName=del&id=" + id,
	        dataType: "text",
	        async: false,
	        success: function (r) {
	            if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetTestStyleList(1);
                } else {
                    alertText("删除失败！", 3500);
                }
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    }); 
}

function showTestStyleDialog(methodName, Id, pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#testStyleDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/TestStyleServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $(pos).parent().parent().addClass('editObject');
                    $("#hid_testStyleParam").val("methodName=edit&id=" + Id);
                    $("#txt_testStyleName").val(r.Name);
                    $("#txt_testStyleRemark").val(r.Remark);
                    $("#op_testStyleWebsite").attr('checked', false);
                    $("#op_testStyleProduct").attr('checked', false);
                    if(r.Type == 1)
                        document.getElementById("op_testStyleWebsite").checked=true;
                    else if(r.Type == 2)
                        document.getElementById("op_testStyleProduct").checked=true;
                    else{
                        document.getElementById("op_testStyleProduct").checked=true;
                        document.getElementById("op_testStyleWebsite").checked=true;
                    }
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_testStyleParam").val("methodName=add");
        $("#txt_testStyleName").val("");
        $("#txt_testStyleRemark").val("");
        $("#op_testStyleWebsite").attr('checked', false);
        $("#op_testStyleProduct").attr('checked', false);
    }
}

function SaveTestStyle() {
    var strName = encode($("#txt_testStyleName").val());
    var strRemark = encode($("#txt_testStyleRemark").val());
    if ($.trim(strName) == "") {
        alertText("类型名不能为空！", 3500);
        return false;
    }
    if($('input[name=op_testStyleType]:checked').size() == 0){
         alertText("请选择测评对象", 3500);
        return false;
    }
    var strType = 0;
    if($('input[name=op_testStyleType]:checked').size() == 2)
        strType = 3;
    else if($('input[name=op_testStyleType]:checked').size() == 1)
        strType = $('input[name=op_testStyleType]:checked').val();
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/TestStyleServlet?methodName="
                                + getParam("hid_testStyleParam", "methodName") + "&id=" + getParam("hid_testStyleParam", "id")
                                + "&name=" + strName + "&remark=" + strRemark +"&type="+strType,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("保存失败！", 3500);
            } else {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateTestStyle(data);
                $('#testStyleDom_Edit').animate({ width: "hide" });
                $("#testStyleDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateTestStyle(data){
    var str = "";
    if(getParam("hid_testStyleParam", "methodName") == "add"){
        str += GetTestStyleEntry(data, '*');
        $("#testStyleList").find('.noDataSrc').remove();
        $("#testStyleList").prepend(str);
    }else if(getParam("hid_testStyleParam", "methodName") == "edit"){
        var obj =  $("#testStyleList").find('.editObject').find('td');

        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.TypeName);
        obj.eq(3).html(data.Remark);
        obj.eq(5).html(data.CreateTime.substring(0, 19));
        obj.eq(4).html(data.CreatePerson);
        $("#testStyleList").find('.editObject').removeClass('editObject');
    }
}