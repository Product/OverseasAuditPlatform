function FirstPageReport() {
    GetReportList(1);
}

function PreviousPageReport() {
    var pageIndex = (parseInt($("#reportListPaging .pageIndex").text()) == 1 ? 1 
    		: parseInt($("#reportListPaging .pageIndex").text()) - 1);
    GetReportList(pageIndex);
}

function NextPageReport() {
    var pageIndex = ($("#reportListPaging .pageIndex").text() == $(
    		"#reportListPaging .pageCount").text() ? parseInt($(
    		"#reportListPaging .pageIndex").text()) : parseInt($(
    		"#reportListPaging .pageIndex").text()) + 1);
    GetReportList(pageIndex);
}

function EndPageReport() {
    GetReportList(parseInt($("#reportListPaging .pageCount").text()));
}

function GetReportList(strPageIndex) {
    var strQuery = $("#txt_reportListQuery").val();
    var strStraTime = $("#txt_reportListBeginDate").val();
    var strEndTime = $("#txt_reportListEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ReportServlet?methodName=QueryList&strQuery="
            					+ strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&str" +
                                		"PageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#reportList").html('');
            if (r == "false") {
                $("#reportList").append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据</td></tr>");
                return false;
            }
            else if(r == "sessionOut")	{
            	doLogout();
            }
            else  { 	
	            var data = $.parseJSON(r);
	
	            $("#reportListPaging .dataCount").text(data.total);
	            $("#reportListPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
	            $("#reportListPaging .pageIndex").text(strPageIndex);
	            var str = "";
	            var j = 0;
	            for (var i = 0; i < data.webList.length; i++) {
	                str += "<tr>";
	                str += "<td>" + (i + 1) + "</td>";
	                str += "<td>" + data.webList[i].Name + "</td>";
	                str += "<td>" + data.webList[i].ReportTemplateId + "</td>";
	                str += "<td>" + data.webList[i].ReportTemplateName + "</td>";
	                str += "<td>" + data.webList[i].ReportContent + "</td>";
	//                str += "<td>" + data.webList[i].ReportFile + "</td>";
	                str += "<td>" + data.webList[i].ReportStatus + "</td>"; 
	//                str += "<td>" + data.webList[i].ReleaseTime + "</td>";
	                str += "<td>" + data.webList[i].CreateTime.substring(0, 19) + "</td>";
	                str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showDialogReportDetail(\"detail\",\""
						+ data.webList[i].Id
						+ "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialogReport(\"edit\",\""
						+ data.webList[i].Id
						+ "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
						+ data.webList[i].Id + "\",DelReport)'/></td>";
	                str += "</tr>";
	                j++;
	            }
	            $("#reportList").append(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelReport(id) {
		$.ajax({
			type : "post",
			url : projectLocation
					+ "servlet/ReportServlet?methodName=del&id=" + id,
			dataType : "text",
			async : false,
			success : function(r) {
				if (r == "true") {
					alertText("删除成功！",3500);
					GetReportList(1);
				}
				else if(r == "sessionOut")	{
	            	doLogout();
	            }
				else  {
					alertText("删除失败！",3500);
				}
			},
			error : function(e) {
				alert(e.responseText);
			}
		});
}

function InitReportTemplate(){ //报告模板下拉菜单(编辑页面)
	$("#op_reportTemplate").html("<option value=''>请选择</option>");
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ReportServlet?methodName=getReportTemplate",
        dataType: "text",
        async: false,
        success: function (r) {
        	if (r != "false") {
				var data = $.parseJSON(r);
				var str = "";
	            for (var i = 0; i < data.ReportTemplate.length; i++) {
					str += "<option value='" + data.ReportTemplate[i].Id + "'>" + data.ReportTemplate[i].Name + "</option>";
				}
				$("#op_reportTemplate").append(str);
			}
        	else if(r == "sessionOut")	{
            	doLogout();
            }
			else  {
				alertText("加载失败！",3500);
			}
		},
		error : function(e) {
			alert(e.responseText);
    	 }
    });
}

function ChangeReportFile(Id) {
	var selectReportFile = document.getElementById(Id);
	var reportFileId = selectReportFile.value;
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/ReportServlet?methodName=changeFile&id=" + reportFileId,
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
                CKEDITOR.instances.cke_Report.setData(decodeURIComponent(r.TemplateFile));
            }
            else if(r == "sessionOut")	{
            	doLogout();
            }
			else  {
				alertText("加载失败！",3500);
			}
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function InitReportTemplateD(){ //报告模板下拉菜单(查看页面)
	$("#op_reportTemplateD").html("<option value=''>请选择</option>");
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ReportServlet?methodName=getReportTemplate",
        dataType: "text",
        async: false,
        success: function (r) {
        	if (r != "false") {
				var data = $.parseJSON(r);
				var str = "";
	            for (var i = 0; i < data.ReportTemplate.length; i++) {
					str += "<option value='" + data.ReportTemplate[i].Id + "'>" + data.ReportTemplate[i].Name + "</option>";
				}
				$("#op_reportTemplateD").append(str);
			}
        	else if(r == "sessionOut")	{
            	doLogout();
            }
			else  {
				alertText("加载失败！",3500);
			}
		},
		error : function(e) {
			alert(e.responseText);
    	 }
    });
}

function showDialogReportDetail(methodName, Id) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#ReportListDom_Detail').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    InitReportTemplateD();
    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/ReportServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$("#hid_reportListParamD").val("methodName=edit&id=" + Id);
                    $("#txt_reportNameD").val(r.Name);
                    $("#op_reportTemplateD").val(r.ReportTemplate);
                    $("#txt_reportContentD").val(r.ReportContent);
                    $("#op_reportStatusD").val(r.ReportStatus);
//                    CKEDITOR.instances.cke_ReportD.setData(decodeURIComponent(r.ReportFile));
                    
                }
                else if(r == "sessionOut")	{
	            	doLogout();
	            }
                else  {
    				alertText("查看失败！",3500);
    			}
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
    	$("#hid_reportListParamD").val("methodName=add");
        $("#txt_reportNameD").val("");
        $("#op_reportTemplateD").val("");
        $("#txt_reportContentD").val("");
        $("#op_reportStatusD").val("");
//        CKEDITOR.instances.cke_ReportD.setData();
    } 
}

function showDialogReport(methodName, Id) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#ReportListDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    InitReportTemplate();
    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/ReportServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $("#hid_reportListParam").val("methodName=edit&id=" + Id);
                    $("#txt_reportName").val(r.Name);
                    $("#op_reportTemplate").val(r.ReportTemplate);
                    $("#txt_reportContent").val(r.ReportContent);
                    $("#op_reportStatus").val(r.ReportStatus);
//                    CKEDITOR.instances.cke_Report.setData(decodeURIComponent(r.ReportFile));
                }
                else if(r == "sessionOut")	{
	            	doLogout();
	            }
                else  {
    				alertText("编辑失败！",3500);
    			}
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_reportListParam").val("methodName=add");
        $("#txt_reportName").val("");
        $("#op_reportTemplate").val("");
        $("#txt_reportContent").val("");
        $("#op_reportStatus").val("");
//        CKEDITOR.instances.cke_Report.setData();
    } 
}

function SaveReport() {
	var strReportName = encode($("#txt_reportName").val());
    var strReportTemplate = $("#op_reportTemplate").val();
    var strReportContent = encode($("#txt_reportContent").val());
    var strReportStatus = encode($("#op_reportStatus").val());
    var strReportFile = encodeURIComponent(encodeURIComponent(CKEDITOR.instances.cke_Report.getData()));
    if ($.trim(strReportName) == "") {
        alertText("模版名称不能为空！", 3500);
        return false;
    }
//    $(function () {
//        $('#fileupload').fileupload({
//            dataType: 'json',
//            done: function (e, data) {
//                $.each(data.result.files, function (index, file) {
//                    $('<p/>').text(file.name).appendTo(document.body);
//                });
//            }
//        });
//    });
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ReportServlet?methodName="
            					+ getParam("hid_reportListParam", "methodName") + "&id=" + getParam("hid_reportListParam", "id")
                                + "&reportName=" + strReportName + "&reportTemplate=" + strReportTemplate + "&reportContent=" + strReportContent + "&reportStatus=" + strReportStatus + "&reportFile=" + strReportFile + "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("保存成功！", 3500);
            } 
            else if(r == "sessionOut")	{
            	doLogout();
            }
            else {
                alertText("保存失败！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//$(function () {
//    'use strict';
//
//    // Initialize the jQuery File Upload widget:
//    $('#fileupload').fileupload();
//
//    // Enable iframe cross-domain access via redirect option:
//    $('#fileupload').fileupload(
//        'option',
//        'redirect',
//        window.location.href.replace(
//            /\/[^\/]*$/,
//            '/cors/result.html?%s'
//        )
//    );
//
//    if (window.location.hostname === 'blueimp.github.com') {
//        // Demo settings:
//        $('#fileupload').fileupload('option', {
//            url: '//jquery-file-upload.appspot.com/',
//            maxFileSize: 5000000,
//            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
//            process: [
//                {
//                    action: 'load',
//                    fileTypes: /^image\/(gif|jpeg|png)$/,
//                    maxFileSize: 20000000 // 20MB
//                },
//                {
//                    action: 'resize',
//                    maxWidth: 1440,
//                    maxHeight: 900
//                },
//                {
//                    action: 'save'
//                }
//            ]
//        });
//        // Upload server status check for browsers with CORS support:
//        if ($.support.cors) {
//            $.ajax({
//                url: '//jquery-file-upload.appspot.com/',
//                type: 'HEAD'
//            }).fail(function () {
//                $('<span class="alert alert-error"/>')
//                    .text('Upload server currently unavailable - ' +
//                            new Date())
//                    .appendTo('#fileupload');
//            });
//        }
//    } else {
//        // Load existing files:
//        $('#fileupload').each(function () {
//            var that = this;
//            $.getJSON(this.action, function (result) {
//                if (result && result.length) {
//                    $(that).fileupload('option', 'done')
//                        .call(that, null, {result: result});
//                }
//            });
//        });
//    }
//
//});