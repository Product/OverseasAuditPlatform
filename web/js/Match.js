function FirstPageNewsFocus() {
	GetNewsFocusList(1);
}

function PreviousPageNewsFocus() {
    var pageIndex = (parseInt($("#NewsFocusPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#NewsFocusPaging .pageIndex").text()) - 1);
    GetNewsFocusList(pageIndex);
}

function NextPageNewsFocus() {
    var pageIndex = ($("#NewsFocusPaging .pageIndex").text() == $("#NewsFocusPaging .pageCount").text() ? parseInt($("#NewsFocusPaging .pageIndex").text()) : parseInt($("#NewsFocusPaging .pageIndex").text()) + 1);
    GetNewsFocusList(pageIndex);
}

function EndPageNewsFocus() {
	GetNewsFocusList(parseInt($("#NewsFocusPaging .pageCount").text()));
}

function GetNewsFocusEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    if(data.Validity == "true")
        str += "<td><a href='"+data.Location+"' target='_blank'>" + data.Name + "</a></td>";
    else
        str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.Remark + "</td>";
    str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
    str += "<td>" + data.CreatePerson + "</td>";
    str += "<td>" + data.GatherTime.substring(0, 19) + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showNewsFocusDetail(\""+data.Id+"\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""+data.Id+"\", DelNewsFocus)' /></td>";
    str += "</tr>";
    return str;
}

function GetNewsFocusList(strPageIndex) {
    var strQuery = $("#txt_NewsFocusQuery").val();
    var strStraTime = $("#txt_NewsFocusBeginDate").val();
    var strEndTime = $("#txt_NewsFocusEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/MatchServlet?methodName=QueryList&strQuery="
            					+ strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#NewsFocusList").html('');
            if (r == "false") {
                $("#NewsFocusList").append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#NewsFocusPaging .dataCount").text(data.total);
            $("#NewsFocusPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#NewsFocusPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 1; i <= data.webList.length; i++) {
                str += GetNewsFocusEntry(data.webList[i-1], i);
            }
            $("#NewsFocusList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelNewsFocus(id) {
	    $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/MatchServlet?methodName=del&id=" + id,
	        dataType: "text",
	        async: false,
	        success: function (r) {
                 if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetNewsFocusList(1);
                } else {
                    alertText("删除失败！", 3500);
                }
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    }); 
}


function showNewsFocusDetail(Id){
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#NewsFocusDom_Detail').show();
    $("#NewsFocusDomTitle").html('');
    $("#NewsFocusDomUrl").html('');
    $("#NewsFocusDomContent").html('');
    var str = "";
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/MatchServlet?methodName=detail&id=" + Id,
            dataType: "text",
            async: false,
            success: function (data) {
                if (data != "false") {
                    var r = $.parseJSON(data);
                    $("#NewsFocusDomTitle").html(r.Article.Name);
                    $("#NewsFocusDomUrl").html("<a href='"+r.Article.Location+"' target='_blank'>" + r.Article.Location + "</a>");
                    for (var i = 0; i < r.Keys.KeysName.length; i++) {
                        str += "<stong>"+r.Keys.KeysName[i]+"</strong>: ";
                        for(var j = 0; j < r.Keys.KeysContent.length; j++){
                            str += r.Keys.KeysContent[j]+" "
                        }
                        str += "<br>";
                    }
                    $("#NewsFocusDomKeys").html(str);
                    $("#NewsFocusDomContent").html(r.Article.Content);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}