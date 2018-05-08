//检测记录模块JS

// 页面校验
$().ready(function() {
    //start validate
    $("#detectingRecordForm").validate({          
        rules: {
            mustName: {
                required: true, 
                byteRangeLength:[1,50]
            },
            mustDigits: {
                required: true,
                digits:true,
				min:0
            },
            mustDate: {
                required: true
			}
        },
        // set error messages label
        errorElement: "lable",
        // set error messages place
        errorPlacement: function (error, element) {
            if (element.is(':radio') || element.is(':checkbox')) {
                var eid = element.attr('name');
                error.appendTo(element.parent("td"));
            } else {
                error.appendTo(element.closest("td"));
            }
        }
    })
});

//检查是否选取了网站
function DRecordWebsiteIsNull(){
    if ($.trim( $("#txt_detectingRecordWebsite").val()) == "") {
        $('#txt_detectingRecordWebsiteError').text("网站名不能为空");
        return false;
    }
    else{
        $('#txt_detectingRecordWebsiteError').text("");
    }
}

//跳转到第一页
function ToDetectingRecordFirstPage() {
    GetDetectingRecordList(1);
}

//跳转到上一页
function ToDetectingRecordPreviousPage() {
    var pageIndex = (parseInt($("#DetectingRecordPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#DetectingRecordPaging .pageIndex").text()) - 1);
    GetDetectingRecordList(pageIndex);
}

//跳转到下一页
function ToDetectingRecordNextPage() {
    var pageIndex = ($("#DetectingRecordPaging .pageIndex").text() == $(
			"#DetectingRecordPaging .pageCount").text() ? parseInt($(
			"#DetectingRecordPaging .pageIndex").text()) : parseInt($(
			"#DetectingRecordPaging .pageIndex").text()) + 1);
    GetDetectingRecordList(pageIndex);
}

//跳转到尾页
function ToDetectingRecordLastPage() {
    GetDetectingRecordList(parseInt($("#DetectingRecordPaging .pageCount").text()));
}

//显示某页的所有检测记录
function GetDetectingRecordList(strPageIndex) {
    var strQuery = $("#txt_detectingRecordQuery").val();
    var strStraTime = $("#txt_detectingRecordBeginDate").val();
    var strEndTime = $("#txt_detectingRecordEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/DetectingRecordServlet?methodName=QueryList&strQuery=" + strQuery
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#DetectingRecordList").html('');
            if (r == "false") {
                $("#DetectingRecordList")
						.append("<tr><td colspan='9' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            
            var data = $.parseJSON(r);

            $("#DetectingRecordPaging .dataCount").text(data.total);
            $("#DetectingRecordPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#DetectingRecordPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.webList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + data.webList[i].Website + "</td>";
                str += "<td>" + data.webList[i].Product + "</td>";
                str += "<td>" + data.webList[i].Organization + "</td>";
                str += "<td>" + data.webList[i].Sample + "</td>";
                str += "<td>" + data.webList[i].Qualified + "</td>";
                str += "<td>" + data.webList[i].DetectingTime + "</td>";
                str += "<td>" + data.webList[i].Remark + "</td>";

                str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='watchDetectingRecordDialog(\"edit\",\""
						+ data.webList[i].Id
				        + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDetectingRecordDialog(\"edit\",\""
						+ data.webList[i].Id
						+ "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
						+ data.webList[i].Id + "\", DelDetectingRecord)' /></td>";
                str += "</tr>";
                j++;
            }
            $("#DetectingRecordList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//查看检测记录
function watchDetectingRecordDialog(methodName, Id) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#DetectingRecordDom_Watch').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/DetectingRecordServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $("#hid_watchDetectingRecordParam").val("methodName=edit&id=" + Id);
                    $('#txt_watchdetectingRecordName').val(r.Website);
                    $("#txt_watchdetectingRecordProduct").val(r.Product);
                    $("#txt_watchdetectingRecordOrganization").val(r.Organization);
                    $("#txt_watchdetectingRecordSample").val(r.Sample);
                    $("#txt_watchdetectingRecordQualified").val(r.Qualified);
					$("#txt_watchdetectingRecordDate").val(r.DetectingTime);
					$("#txt_watchdetectingRecordCreateTime").val(r.CreateTime);					
					$("#txt_watchdetectingRecordRemark").val(r.Remark);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_watchDetectingRecordParam").val("methodName=add");
        $("#txt_watchdetectingRecordName").val("");
        $("#txt_watchdetectingRecordProduct").val("");
        $("#txt_watchdetectingRecordOrganization").val("");
        $("#txt_watchdetectingRecordSample").val("");
        $("#txt_watchdetectingRecordQualified").val("");
        $("#txt_watchdetectingRecordDate").val("");
		$("#txt_watchdetectingRecordCreateTime").val("");		
        $("#txt_watchdetectingRecordRemark").val("");
    }
}

//通过ID删除检测记录
function DelDetectingRecord(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/DetectingRecordServlet?methodName=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetDetectingRecordList(1);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}

//添加或编辑检测记录
function showDetectingRecordDialog(methodName, Id) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#detectingRecordDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/DetectingRecordServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $("#hid_DetectingRecordParam").val("methodName=edit&id=" + Id);
                    $("#txt_detectingRecordWebsite").val(r.WebsiteId);
                    $("#txt_detectingRecordWebsite").text(r.Website);
                    $("#txt_detectingRecordProduct").val(r.Product);
                    $("#txt_detectingRecordOrganization").val(r.Organization);
                    $("#txt_detectingRecordSample").val(r.Sample);
                    $("#txt_detectingRecordQualified").val(r.Qualified);
					$("#txt_detectingRecordDate").val(r.DetectingTime);
					$("#txt_detectingRecordRemark").val(r.Remark);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_DetectingRecordParam").val("methodName=add");
        $("#txt_detectingRecordWebsite").val("");
        $("#txt_detectingRecordWebsite").text("");
        $("#txt_detectingRecordProduct").val("");
        $("#txt_detectingRecordOrganization").val("");
        $("#txt_detectingRecordSample").val("");
        $("#txt_detectingRecordQualified").val("");
        $("#txt_detectingRecordDate").val("");
        $("#txt_detectingRecordRemark").val("");
    }
    DRecordWebsiteIsNull();
	$("#detectingRecordForm").valid()
}

//保存检测记录
function SaveDetectingRecord()
{
	var strName = encode($("#txt_detectingRecordWebsite").val());
	var strProduct = encode($("#txt_detectingRecordProduct").val());
	var strOrganization = encode($("#txt_detectingRecordOrganization").val());
	var strSample = $("#txt_detectingRecordSample").val();
	var strQualified = $("#txt_detectingRecordQualified").val();
	var strRecordDate = $("#txt_detectingRecordDate").val();
    var strRemark = encode($("#txt_detectingRecordRemark").val());
    DRecordWebsiteIsNull();
	var r = $("#detectingRecordForm").valid();
	if(!r){
        alertText("保存失败！", 3500);
		return false;
	}
	
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/DetectingRecordServlet?methodName="
            					+ getParam("hid_DetectingRecordParam", "methodName") + "&id=" + getParam("hid_DetectingRecordParam", "id")
                                + "&website=" + strName
                                + "&product=" + strProduct
                                + "&organization=" + strOrganization
                                + "&sample=" + strSample
                                + "&qualified=" + strQualified
                                + "&detectingTime=" + strRecordDate
								+ "&remark=" + strRemark
								+ "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateDataGatherInfo(data);
                $("#detectingRecordDom_Edit").animate({ width: "hide" });
                $('#DetectingRecordDom').show();
            } else {
                alertText("保存失败！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

/*
函数名：GetDetectingRecordTargetList
作用：获得网站列表
参数：data：网站分类的一条信息
idx:序列号
*/
function GetDetectingRecordTargetList(strPageIndex) {//获取符合条件的可添加的网站列表
    var strQuery = $("#txt_detectingRecordTargetQuery").val();
    var strStraTime = $("#txt_detectingRecordTargetBeginDate").val();
    var strEndTime = $("#txt_detectingRecordTargetEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/WebsiteServlet?methodName=QueryList&strQuery="
            					+ encode(strQuery) + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#detectingRecordWebisiteList").html('');
            if (r == "false") {
                $("#detectingRecordWebisiteList").append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#detectingRecordTargetPaging .dataCount").text(data.total);
            $("#detectingRecordTargetPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#detectingRecordTargetPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.websiteList.length; i++) {
                str += "<tr>";
                str += "<td><input type='radio' class='detectingRecordWeb' value='"+data.websiteList[i].Id+"' name='detectingRecordWebs' id='dg"+data.websiteList[i].Id+"'></td>";
                str += "<td><label for='dg"+data.websiteList[i].Id+"'>" + data.websiteList[i].Name + "</label></td>";
                str += "<td>" + data.websiteList[i].Location + "</td>";
                str += "<td>" + data.websiteList[i].WebStyle + "</td>";
                str += "<td>" + data.websiteList[i].Address + "</td>";
                str += "</tr>";
                j++;
            }
            $("#detectingRecordWebisiteList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//样品检测-检测记录编辑-添加网站对象
function GetDetectingRecordTargetDom(){
    $("#detectingRecordDom_Edit").animate({ width: "hide" });
    $('#DetectingRecordDom_AddTarget').show();
    $("#txt_detectingRecordTargetQuery").val("");
    $("#txt_detectingRecordTargetBeginDate").val("");
    $("#txt_detectingRecordTargetEndDate").val("");
    $("#detectingRecordWebisiteList").html('');
}

//返回数据采集页面
function GoBackDetectingRecordTarget(){
    $("#DetectingRecordDom_AddTarget").animate({ width: "hide" });
    $('#detectingRecordDom_Edit').show();
    DRecordWebsiteIsNull();   
}

//添加网站对象-确定
function checkDetectingRecordTargetRadio(){
        var tmp = $("input[name='detectingRecordWebs']:checked");
        if(tmp.length == 0)
            alertText("没有选中网站！", 3500);
        else{
            $('#txt_detectingRecordWebsite').val(tmp.val());
            $('#txt_detectingRecordWebsite').text(tmp.parent().parent().find('label').text());
            GoBackDetectingRecordTarget();
        }
}

/**
 * @desc 跳转到网站列表首页
 */
function ToDetectingRecordWebsiteFirstPage() {
	GetDetectingRecordTargetList(1);
}

/**
 * @desc 跳转到网站列表上一页
 */
function ToDetectingRecordWebsitePreviousPage() {
    var pageIndex = (parseInt($("#detectingRecordTargetPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#detectingRecordTargetPaging .pageIndex").text()) - 1);
    GetDetectingRecordTargetList(pageIndex);
}

/**
 * @desc 跳转到网站列表下一页
 */
function ToDetectingRecordWebsiteNextPage() {
    var pageIndex = ($("#detectingRecordTargetPaging .pageIndex").text() == $(
			"#detectingRecordTargetPaging .pageCount").text() ? parseInt($(
			"#detectingRecordTargetPaging .pageIndex").text()) : parseInt($(
			"#detectingRecordTargetPaging .pageIndex").text()) + 1);
    GetDetectingRecordTargetList(pageIndex);
}

/**
 * @desc 跳转到网站列表尾页
 */
function ToDetectingRecordWebsitendPage() {
	GetDetectingRecordTargetList(parseInt($("#detectingRecordTargetPaging .pageCount").text()));
}