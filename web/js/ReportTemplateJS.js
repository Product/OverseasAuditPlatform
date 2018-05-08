function FirstPageReportTemplate() {
    GetReportTemplate(1);
}

function PreviousPageReportTemplate() {
    var pageIndex = (parseInt($("#reportTemplatePaging .pageIndex").text()) == 1 ? 1 
    		: parseInt($("#reportTemplatePaging .pageIndex").text()) - 1);
    GetReportTemplate(pageIndex);
}

function NextPageReportTemplate() {
    var pageIndex = ($("#reportTemplatePaging .pageIndex").text() == $(
    		"#reportTemplatePaging .pageCount").text() ? parseInt($(
    		"#reportTemplatePaging .pageIndex").text()) : parseInt($(
    		"#reportTemplatePaging .pageIndex").text()) + 1);
    GetReportTemplate(pageIndex);
}

function EndPageReportTemplate() {
    GetReportTemplate(parseInt($("#reportTemplatePaging .pageCount").text()));
}

//将得到的数据变成html格式代码字符串
function GetReportTemplateEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.TemplateStyle + "</td>";
    str += "<td>" + data.TemplateContent + "</td>"; 
	str += "<td>" + "是" + "</td>";
    str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showDialogEmailDetail(\"detail\",\""
		+ data.Id + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialogEmail(\"edit\",\"" + data.Id
            + "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelEmail(\""
		+ data.Id + "\")' /></td>";
    str += "</tr>";
    return str;
}

//报告模板列表
function GetReportTemplate(strPageIndex) {
    var strQuery = $("#txt_reportTemplateQuery").val();
    var strStraTime = $("#txt_reportTemplateBeginDate").val();
    var strEndTime = $("#txt_reportTemplateEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ReportTemplateServlet?methodName=QueryList&strQuery="
            					+ strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&str" +
                                		"PageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#reportTemplate").html('');
            if (r == "false") {
                $("#reportTemplate").append("<tr><td colspan='7' style='text-align:center;'>无数据</td></tr>");
                return false;
            }
            else if(r == "sessionOut")	{
            	doLogout();
            }
            else  {
	            var data = $.parseJSON(r);
	
	            $("#reportTemplatePaging .dataCount").text(data.total);
	            $("#reportTemplatePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
	            $("#reportTemplatePaging .pageIndex").text(strPageIndex);
	            var str = "";
	            for (var i = 1; i <= data.webList.length; i++) {
	                str += GetReportTemplateEntry(data.webList[i-1],i);//将得到的数据处理成html格式代码字符串

	            }
	            $("#reportTemplate").append(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//删除
function DelReportTemplate(id) {
	$.ajax({
		type : "post",
		url : projectLocation
				+ "servlet/ReportTemplateServlet?methodName=del&id=" + id,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "true") {
				alertText("删除成功！",3500);
				GetReportTemplate(1);
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

//获取评价类别下拉菜单
function getEvaluationType(){
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/EvaluationTypeServlet?methodName=getEvaluationType",
        dataType: "text",
        async: false,
        success: function (data) {
            if (data != "false") {
            	var str = "";
                var r = $.parseJSON(data);
            	for (var i = 0; i < r.total; i++) {
            		str += "<option value ='" + r.webList[i].Name + "'>" + r.webList[i].Name + "</option>";
            	}
            	$("#op_templateCatg").find("option").remove(); 
            	$("#op_templateCatg").append(str);
                $("#op_templateCatg").val(r.webList[0].Id);
            }else{
                alertText('缺少评价类别！', 5000);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
	});
}

//获取分析类别下拉菜单
function getVentureAnalysisStyle(){
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/VentureAnalysisStyleServlet?methodName=getVentureAnalysisStyle",
        dataType: "text",
        async: false,
        success: function (data) {
            if (data != "false") {
            	var str = "";
                var r = $.parseJSON(data);
            	for (var i = 0; i < r.total; i++) {
            		str += "<option value ='" + r.webList[i].Name + "'>" + r.webList[i].Name + "</option>";
            	}
            	$("#op_templateCatg").find("option").remove();
            	$("#op_templateCatg").append(str);
                $("#op_templateCatg").val(r.webList[0].Id);
            }else{
                alertText('缺少分析类别！', 5000);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
	});
}

//查看
function showDialogReportTemplateDetail(methodName, Id) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#ReportTemplateDom_Detail').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/ReportTemplateServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$("#hid_reportTemplateParamD").val("methodName=edit&id=" + Id);
                    $("#txt_templateNameD").val(r.Name);
                    $("#txt_templateStyleD").val(r.TemplateStyle);
                    $("#txt_templateContentD").val(r.TemplateContent);
                    CKEDITOR.instances.cke_ReportTemplateD.setData(decodeURIComponent(r.TemplateFile));
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
    	$("#hid_reportTemplateParamD").val("methodName=add");
        $("#txt_templateNameD").val("");
        $("#txt_templateStyleD").val("");
        $("#txt_templateContentD").val("");
        CKEDITOR.instances.cke_ReportTemplateD.setData();
    } 
}

//编辑页面（添加+编辑）
function showDialogReportTemplate(methodName, Id, pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#ReportTemplateDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    getVentureAnalysisStyle();
    //编辑
    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/ReportTemplateServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_reportTemplateParam").val("methodName=edit&id=" + Id);
                    $("#txt_templateName").val(r.Name);
                    $("#txt_templateStyle").val(r.TemplateStyle);
                    $("#txt_templateContent").val(r.TemplateContent);
                    CKEDITOR.instances.cke_ReportTemplate.setData(decodeURIComponent(r.TemplateFile));
//                    $("#cke_ReportTemplate").val(r.TemplateFile);
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
    } 
    //添加
    else {
        $("#hid_reportTemplateParam").val("methodName=add");
        $("#txt_templateName").val("");
        $("#txt_templateStyle").val("");
        $("#txt_templateContent").val("");
        CKEDITOR.instances.cke_ReportTemplate.setData();
    } 
}

//保存
function SaveReportTemplate() {
	var strTemplateName = encode($("#txt_templateName").val());
    var strTemplateStyle = encode($("#txt_templateStyle").val());
    var strTemplateContent = encode($("#txt_templateContent").val());
    var strTemplateFile = encode(CKEDITOR.instances.cke_ReportTemplate.getData());
    if ($.trim(strTemplateName) == "") {
        alertText("模版名称不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ReportTemplateServlet?methodName="
            					+ getParam("hid_reportTemplateParam", "methodName") + "&id=" + getParam("hid_reportTemplateParam", "id")
                                + "&templateName=" + strTemplateName + "&templateStyle=" + strTemplateStyle + "&templateContent=" + strTemplateContent + "&templateFile=" + strTemplateFile,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("保存失败！", 3500);
            } 
            else if(r == "sessionOut")	{
            	doLogout();
            }
            else {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateReportTemplate(data);//更新报告模板列表
                $('#ReportTemplateDom_Edit').animate({ width: "hide" });
                $("#ReportTemplateDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function ChangeTemplateType(Id) {
	var selectTemplateType = document.getElementById(Id);
	var strTemplateType = selectTemplateType.value;
	if(strTemplateType == "评价报告")
	{
		getEvaluationType();
	}
	else if(strTemplateType == "风险分析")
	{
		getVentureAnalysisStyle();
	}
}

//添加或编辑后更新报告模板列表
function UpdateReportTemplate(data){
    var str = "";
    //添加保存新增一行显示添加的数据
    if(getParam("hid_reportTemplateParam", "methodName") == "add")
    {
        str += GetReportTemplateEntry(data, '*');
        $("#reportTemplate").find('.noDataSrc').remove();
        $("#reportTemplate").prepend(str);
    }
    //编辑保存修改编辑的那一行数据
    else if(getParam("hid_reportTemplateParam", "methodName") == "edit")
    {
        var obj =  $("#reportTemplate").find('.editObject').find('td');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.TemplateStyle);
        obj.eq(3).html(data.TemplateContent);
        obj.eq(4).html("是");
        obj.eq(5).html(data.CreateTime.substring(0, 19));
        $("#reportTemplate").find('.editObject').removeClass('editObject');
    }
}

//function replaceTextarea(){//替换编辑框内容
//	$.ajax({
//		type : "post",
//		url : "",//后台读取模板内容	
//		dataType: 'json' ,
//	    success : function(data) {
//	    	$("#cke_Report").empty();
//	    	$("#cke_Report").html(data.value);
//	    }
//	});
//}
