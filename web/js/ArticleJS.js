function FirstPageNewsDocuments() {
	GetNewsDocumentsList(1);
}

function PreviousPageNewsDocuments() {
    var pageIndex = (parseInt($("#NewsDocumentsPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#NewsDocumentsPaging .pageIndex").text()) - 1);
    GetNewsDocumentsList(pageIndex);
}

function NextPageNewsDocuments() {
    var pageIndex = ($("#NewsDocumentsPaging .pageIndex").text() == $("#NewsDocumentsPaging .pageCount").text() ? parseInt($("#NewsDocumentsPaging .pageIndex").text()) : parseInt($("#NewsDocumentsPaging .pageIndex").text()) + 1);
    GetNewsDocumentsList(pageIndex);
}

function EndPageNewsDocuments() {
	GetNewsDocumentsList(parseInt($("#NewsDocumentsPaging .pageCount").text()));
}

function GetNewsDocumentsEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    if(data.Validity == "true")
        str += "<td><a href='"+data.Location+"' target='_blank'>" + data.Location + "</a></td>";
    else
        str += "<td>" + data.Location + "</td>";
    str += "<td>" + data.Remark + "</td>";
    str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
    str += "<td>" + data.CreatePerson + "</td>";
    str += "<td>" + data.GatherTime.substring(0, 19) + "</td>";
    str += "<td><button type='button' class='btn btn-default btn-xs' onclick='showNewsDocumentsDetail(\""+data.Id+"\")' >查 看</button>&nbsp;" +
        "<button type='button' class='btn btn-default btn-xs' onclick='showNewsDocumentsDialog(\"edit\",\"" + data.Id + "\", this)' >编 辑</button>&nbsp;" +
        "<button type='button' class='btn btn-default btn-xs' onclick='confirmShow(\""+data.Id+"\", DelNewsDocuments)' >删 除</button></td>";
    str += "</tr>";
    return str;
}
function GetNewsDocumentsList(strPageIndex) {
    var strQuery = encode($("#txt_NewsDocumentsQuery").val());
    var strStraTime = $("#txt_NewsDocumentsBeginDate").val();
    var strEndTime = $("#txt_NewsDocumentsEndDate").val();
    var strEvent = $("#txt_NewsDocumentsEvent").val();
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ArticleServlet?methodName=QueryList&strQuery="
            					+ strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strEventId=" + strEvent + "&strPageCount=10",
        dataType: "text",
        async: true,
        beforeSend:function(XMLHttpRequest){
        	$("#loading1").css("display","block");
       },
        success: function (r) {
            $("#NewsDocumentsList").html('');
            if (r == "false") {
                $("#NewsDocumentsList").append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#NewsDocumentsPaging .dataCount").text(data.total);
            $("#NewsDocumentsPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#NewsDocumentsPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 1; i <= data.webList.length; i++) {
                str += GetNewsDocumentsEntry(data.webList[i-1], i);
            }
            $("#NewsDocumentsList").append(str);
        },
        complete:function(){
        	$("#loading1").css("display","none");
         },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelNewsDocuments(id) {
	    $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/ArticleServlet?methodName=del&id=" + id,
	        dataType: "text",
	        async: false,
	        success: function (r) {
                 if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetNewsDocumentsList(1);
                } else {
                    alertText("删除失败！", 3500);
                }
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    }); 
}

function checkNewsDocumentsRadio(val){
    document.getElementById("op_NewsDocuments1").checked=false;
    document.getElementById("op_NewsDocuments2").checked=false;
    if(val == "true")
        document.getElementById("op_NewsDocuments1").checked=true;
    else
        document.getElementById("op_NewsDocuments2").checked=true;
}

function showNewsDocumentsDialog(methodName, Id, pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#NewsDocumentsDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/ArticleServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $(pos).parent().parent().addClass('editObject');
                    $("#hid_NewsDocumentsParam").val("methodName=edit&id=" + Id);
                    checkNewsDocumentsRadio(r.Validity);
                    $("#txt_NewsDocumentsRemark").val(r.Remark);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    }
}

function SaveNewsDocuments() {
    
    var strRemark = $("#txt_NewsDocumentsRemark").val();
    var strVadility = $("input[name='op_NewsDocuments']:checked").val();
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ArticleServlet?methodName="
                                + getParam("hid_NewsDocumentsParam", "methodName") + "&id=" + getParam("hid_NewsDocumentsParam", "id")
                                + "&remark=" + strRemark + "&vadility="+strVadility,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("保存失败！", 3500);
            } else {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateNewsDocuments(data);
                $("#NewsDocumentsDom_Edit").animate({ width: "hide" });
                $('#NewsDocumentsDom').show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateNewsDocuments(data){
    var str = "";
    if(getParam("hid_NewsDocumentsParam", "methodName") == "add"){
        str += GetNewsDocumentsEntry(data, '*');
        $("#NewsDocumentsList").find('.noDataSrc').remove();
        $("#NewsDocumentsList").prepend(str);
    }else if(getParam("hid_NewsDocumentsParam", "methodName") == "edit"){
        var obj = $('#NewsDocumentsList').find('.editObject').find('td');
        if(data.Validity == "true")
            obj.eq(2).html("<a href='"+data.Location+"' target='_blank'>" + data.Location + "</a>");
        else
             obj.eq(2).html(data.Location);
        obj.eq(3).html(data.Remark);
        obj.eq(5).html(data.CreatePerson);
        $("#NewsDocumentsList").find('.editObject').removeClass('editObject');
    }
}

function showNewsDocumentsDetail(Id){
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#NewsDocumentsDom_Detail').show();
    $("#NewsDocumentsDomTitle").html('');
    $("#NewsDocumentsDomUrl").html('');
    $("#NewsDocumentsDomContent").html('');
    $("#NewsDocumentDomSmall_addEvent").removeClass('disappear');
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/ArticleServlet?methodName=detail&id=" + Id,
            dataType: "text",
            async: false,
            success: function (data) {
                if (data != "false") {
                    var r = $.parseJSON(data);
                    $("#NewsDocumentsDomTitle").html(r.Name);
                    $("#NewsDocumentsDomUrl").html("<a href='"+r.Location+"' target='_blank'>" + r.Location + "</a>");
                    $("#NewsDocumentsDomContent").html(r.Content);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}