//首页
function FirstPage_VentureAnalysisStyle() {
    GetVentureAnalysisStyleList(1);
}

//前一页
function PreviousPage_VentureAnalysisStyle() {
    var pageIndex = (parseInt($("#VentureAnalysisStylePaging .pageIndex").text()) == 1 ? 1 : parseInt($("#VentureAnalysisStylePaging .pageIndex").text()) - 1);
    GetVentureAnalysisStyleList(pageIndex);
}

//后一页
function NextPage_VentureAnalysisStyle() {
    var pageIndex = ($("#VentureAnalysisStylePaging .pageIndex").text() == $("#VentureAnalysisStylePaging .pageCount").text() ? parseInt($("#VentureAnalysisStylePaging .pageIndex").text()) : parseInt($("#VentureAnalysisStylePaging .pageIndex").text()) + 1);
    GetVentureAnalysisStyleList(pageIndex);
}

//尾页
function EndPage_VentureAnalysisStyle() {
    GetVentureAnalysisStyleList(parseInt($("#VentureAnalysisStylePaging .pageCount").text()));
}

//将得到的数据转变成html格式代码字符串
function GetVentureAnalysisStyleEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
	str += "<td>" + data.Name + "</td>";
	str += "<td>" + data.Remark + "</td>";
	str += "<td>" + data.CreatePerson + "</td>";
	str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
	str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialog_1(\"edit\",\"" + data.Id + "\",this)' />&nbsp;"
	         + "<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelVentureAnalysisStyle_1(\"" + data.Id + "\")' />"
	         + "</td>";
    str += "</tr>";
    return str;
}

//显示分析类别列表
function GetVentureAnalysisStyleList(strPageIndex) {
	//关键字查询
	var strQuery = $("#txt_VentureAnalysisStyleQuery").val();
	//创建开始时间查询
	var strStraTime = $("#txt_VentureAnalysisStyleBeginDate").val();
	//创建结束时间查询
	var strEndTime = $("#txt_VentureAnalysisStyleEndDate").val();
	$.ajax({
				type : "post",
				url : projectLocation
						+ "servlet/VentureAnalysisStyleServlet?methodName=QueryList&strQuery="
						+ strQuery + "&strStraTime=" + strStraTime
						+ "&strEndTime=" + strEndTime + "&strPageIndex="
						+ strPageIndex + "&strPageCount=10",
				dataType : "text",
				async : false,
				success : function(r) {
					$("#VentureAnalysisStyleList").html('');
					if (r == "false") {
						$("#VentureAnalysisStyleList").append("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
						return false;
					}
					else if (r == "sessionOut") {
	                    doLogout();
	                }
					else{
					var data = $.parseJSON(r);
					$("#VentureAnalysisStylePaging .dataCount").text(data.total);
					$("#VentureAnalysisStylePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
					$("#VentureAnalysisStylePaging .pageIndex").text(strPageIndex);
					var str = "";
					for (var i = 0; i < data.webList.length; i++) {
						str += GetVentureAnalysisStyleEntry(data.webList[i], i+1);
					}
					$("#VentureAnalysisStyleList").append(str);
					}
				},
				error : function(e) {
					alert(e.responseText);
				}
			});
}

//删除分析类别
function DelVentureAnalysisStyle_1(id) {
	$.ajax({
		type : "post",
		url : projectLocation
				+ "servlet/VentureAnalysisStyleServlet?methodName=del&id=" + id,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "true") {
				alertText("删除成功！",  3500);
				GetVentureAnalysisStyleList(1);
			}
			else if (r == "sessionOut") {
				doLogout();
			}
			else 
			{
				alert("删除失败！",3500);
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}
//分析类别页面新增及编辑功能
function showDialog_1(methodName, Id, pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#VentureAnalysisStyleDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    //编辑
    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/VentureAnalysisStyleServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $(pos).parent().parent().addClass('editObject');
                    $("#hid_VentureAnalysisStyleParam").val("methodName=edit&id=" + Id);
                    $("#txt_VentureAnalysisStyleName").val(r.Name);
                    $("#txt_VentureAnalysisStyleRemark").val(r.Remark);
                  }
                else if (r == "sessionOut") {
                    doLogout();
                }
                else{
                	alert("编辑失败！");
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } 
    //新增
    else {
        $("#hid_VentureAnalysisStyleParam").val("methodName=add");
        $("#txt_VentureAnalysisStyleName").val("");
        $("#txt_VentureAnalysisStyleRemark").val("");
    } 
}


//保存
function SaveVentureAnalysisStyle() {
	var strName = $("#txt_VentureAnalysisStyleName").val();
    var strRemark = $("#txt_VentureAnalysisStyleRemark").val();
    if ($.trim(strName) == "") {
        alertText("类型名称不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/VentureAnalysisStyleServlet?methodName="
            					+ getParam("hid_VentureAnalysisStyleParam", "methodName")
            					+ "&id=" + getParam("hid_VentureAnalysisStyleParam", "id")
                                + "&name=" + encode(strName) + "&remark=" + encode(strRemark),
        dataType: "text",
        async: false,
        success: function (r) {
        	if (r == "false") {
                alertText("保存失败！", 3500);
            } else {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateVentureAnalysisStyle(data);
                $('#VentureAnalysisStyleDom_Edit').animate({ width: "hide" });
                $("#VentureAnalysisStyleDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}


//更新分析类别列表页面
function UpdateVentureAnalysisStyle(data){
    var str = "";
    //新增保存时新增一行全新的数据
    if(getParam("hid_VentureAnalysisStyleParam", "methodName") == "add"){
        str += GetVentureAnalysisStyleEntry(data, '*');
        $("#VentureAnalysisStyleList").find('.noDataSrc').remove();
        $("#VentureAnalysisStyleList").prepend(str);
    }
    //编辑保存时更新所在行的数据
    else if(getParam("hid_VentureAnalysisStyleParam", "methodName") == "edit"){
        var obj =  $("#VentureAnalysisStyleList").find('.editObject').find('td');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.Remark);
        obj.eq(3).html(data.CreatePerson);
        obj.eq(4).html(data.CreateTime.substring(0, 19));
        $("#VentureAnalysisStyleList").find('.editObject').removeClass('editObject');
    }
}
