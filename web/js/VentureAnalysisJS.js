function FirstPageVentureAnalysis() {
	GetVentureAnalysisList(1);
}

function PreviousPageVentureAnalysis() {
	var pageIndex = (parseInt($("#VentureAnalysisPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#VentureAnalysisPaging .pageIndex").text()) - 1);
	GetVentureAnalysisList(pageIndex);
}

function NextPageVentureAnalysis() {
	var pageIndex = ($("#VentureAnalysisPaging .pageIndex").text() == $(
			"#VentureAnalysisPaging .pageCount").text() ? parseInt($(
			"#VentureAnalysisPaging .pageIndex").text()) : parseInt($(
			"#VentureAnalysisPaging .pageIndex").text()) + 1);
	GetVentureAnalysisList(pageIndex);
}

function EndPageVentureAnalysis() {
	GetVentureAnalysisList(parseInt($("#VentureAnalysisPaging .pageCount").text()));
}

function GetVentureAnalysisList(strPageIndex) {
	var strQuery = $("#txt_VentureAnalysisQuery").val();
	var strStraTime = $("#txt_VentureAnalysisBeginDate").val();
	var strEndTime = $("#txt_VentureAnalysisEndDate").val();

	$.ajax({
				type : "post",
				url : projectLocation
						+ "servlet/VentureAnalysisServlet?methodName=QueryList&strQuery="
						+ strQuery + "&strStraTime=" + strStraTime
						+ "&strEndTime=" + strEndTime + "&strPageIndex="
						+ strPageIndex + "&strPageCount=10",
				dataType : "text",
				async : false,
				success : function(r) {
					$("#VentureAnalysisList").html('');
					if (r == "false")
					{
						$("#VentureAnalysisList").append("<tr><td colspan='11' style='text-align:center;'>无数据！</td></tr>");
						return false;
					}
					else if (r == "sessionOut") {
		                doLogout();
		            }
					else{
					var data = $.parseJSON(r);
					$("#VentureAnalysisPaging .dataCount").text(data.total);
					$("#VentureAnalysisPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
					$("#VentureAnalysisPaging .pageIndex").text(strPageIndex);
					var str = "";
					var j = 0;
					for (var i = 0; i < data.webList.length; i++) 
					{
						str += "<tr>";
						str += "<td>" + (i + 1) + "</td>";
						str += "<td>" + data.webList[i].Name + "</td>";
						str += "<td>" + data.webList[i].Style + "</td>";
						str += "<td>" + data.webList[i].WebsiteStyle + "</td>";
						str += "<td>" + data.webList[i].Website + "</td>";
						str += "<td>" + data.webList[i].AnalysisFile + "</td>";
						str += "<td>" + data.webList[i].ReleaseState + "</td>";
						str += "<td>" + data.webList[i].Illustration + "</td>";
						str += "<td>" + data.webList[i].CreatePerson + "</td>";
						str += "<td>" + data.webList[i].CreateTime.substring(0, 19) + "</td>";
						str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showVentureAnalysisDialog(\"edit\",\""
								+ data.webList[i].Id
								+ "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
								+ data.webList[i].Id + "\",DelVentureAnalysis)' /></td>";
						str += "</tr>";
						j++;
					}
					$("#VentureAnalysisList").append(str);
					}
				},
				error : function(e) {
					alert(e.responseText);
				}
			});
}

function DelVentureAnalysis(id) {
		$.ajax({
			type : "post",
			url : projectLocation
					+ "servlet/VentureAnalysisServlet?methodName=del&id=" + id,
			dataType : "text",
			async : false,
			success : function(r) {
				if (r == "true") {
					alertText("删除成功！", "green", 3500);
					GetWebsiteStyleList(1);
				}
				else if (r == "sessionOut") {
	                doLogout();
	            }
				else{
					alert("删除失败！");
				}
			},
			error : function(e) {
				alert(e.responseText);
			}
		});
}


function showVentureAnalysisDialog(methodName, Id) {
	$("#iframeMain .wrap").animate({width : "hide"});
	$('#VentureAnalysisDom_Edit').show();
	$(obj).parent().parent().children("li").children("a").removeClass("current");
	$(obj).addClass("current");
	InitWWStyle();
	InitVSStyle();
	selectWebsite(obj);
	if (methodName != "add") {
		$.ajax({
					type : "post",
					url : projectLocation
							+ "servlet/VentureAnalysisServlet?methodName=init&id="
							+ Id,
					dataType : "json",
					async : false,
					success : function(r) {
						if (r != "false") {
							$("#hid_VentureAnalysisParam").val("methodName=edit&id=" + Id);
							$("#txt_VentureAnalysisName").val(r.Name);
							$("#op_VentureAnalysisStyle").val(r.Style);
							$("#op_VentureAnalysisWebsiteStyle").val(r.WebsiteStyle);							
							$("#op_VentureAnalysisWebsite").val(r.Website);
							$("#txt_VentureAnalysisFile").val(r.AnalysisFile);
							$("#txt_VentureAnalysisReleaseState").val(r.ReleaseState);
							$("#txt_VentureAnalysisIllustration").val(r.Illustration);
						}
						else if (r == "sessionOut") {
		                    doLogout();
		                }
						else{
							alert("编辑失败！");
						}
					},
					error : function(e) {
						alert(e.responseText);
					}
				});
	} else {
		$("#hid_VentureAnalysisParam").val("methodName=add");
		$("#txt_VentureAnalysisName").val("");
		$("#op_VentureAnalysisStyle").val("");
		$("#op_VentureAnalysisWebsiteStyle").val("");	
		$("#op_VentureAnalysisWebsite").val("");
		$("#txt_VentureAnalysisFile").val("");
		$("#txt_VentureAnalysisReleaseState").val("");
		$("#txt_VentureAnalysisIllustration").val("");

	}
}

function InitVSStyle(){
	$("#op_VentureAnalysisStyle").html("<option value='all'>所有分析类别</option>");
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/VentureAnalysisServlet?methodName=getVAStyle",
		dataType : "text",
		async : false,
		success : function(r) {
			if (r != "false") {
				var data = $.parseJSON(r);
				var str = "";
	            for (var i = 0; i < data.VentureAnalysisStyle.length; i++) {
					str += "<option value='" + data.VentureAnalysisStyle[i].Id + "'>" + data.VentureAnalysisStyle[i].Name + "</option>";
				}
				$("#op_VentureAnalysisStyle").append(str);
			}
			else if (r == "sessionOut") {
                doLogout();
            }
			else{
				alert("加载失败！");
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}


function InitWWStyle(){
	$("#op_VentureAnalysisWebsiteStyle").html("<option value='all'>所有网站类别</option>");
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/VentureAnalysisServlet?methodName=getWWStyle",
		dataType : "text",
		async : false,
		success : function(r) {
			if (r != "false") {
				var data = $.parseJSON(r);
				var str = "";
	            for (var i = 0; i < data.WebsiteStyle.length; i++) {
					str += "<option value='" + data.WebsiteStyle[i].Id + "'>" + data.WebsiteStyle[i].Name + "</option>";
				}
				$("#op_VentureAnalysisWebsiteStyle").append(str);
			}
			else if (r == "sessionOut") {
                doLogout();
            }
			else{
				alert("加载失败！");
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}

function selectWebsite(obj){
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/VentureAnalysisServlet?methodName=getAWeb",
		dataType : "text",
		async : false,
		success : function(r) {
			if (r != "false") {
				var data = $.parseJSON(r);
				var str = "";
	            for (var i = 0; i < data.Website.length; i++) {
					str += "<option value='" + data.Website[i].Id + "'>" + data.Website[i].Name + "</option>";
				}
	        	$("#op_VentureAnalysisWebsite").val();
			}
			else if (r == "sessionOut") {
                doLogout();
            }
			else{
				alert("加载失败！");
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}

function SaveVentureAnalysis() {
	var strName = $("#txt_VentureAnalysisName").val();
	var strStyle = $("#op_VentureAnalysisStyle").val();
	var strWebStyle = $("#op_VentureAnalysisWebStyle").val();
	var strWebsite = $("#op_VentureAnalysisWebsite").val();
	var strIllustration = $("#txt_VentureAnalysisIllustration").val();
	if ($.trim(strName) == "") {
		alertText("类型名称不能为空！", 3500);
		return false;
	}
$(document).ready(function () {
    $("#btnAjaxSubmit").click(function () {
    $.ajax({
            url: projectLocation + "servlet/VentureAnalysisServlet?methodName="
			+ getParam("hid_VentureAnalysisParam", "methodName") + "&id="
			+ getParam("hid_VentureAnalysisParam", "id") + "&name="
			+ encode(strName) + "&style=" + encode(strStyle) + "&webStyle=" + encode(strWebStyle) + "&website=" + strWebsite
			+ +"&illustration=" + encode(strIllustration),
            type: "post",
            dataType: "text",
            async : false,
            data: $("#Vform").serialize(),
            success : function(r) {
    			if (r == "true") {
    				alertText("保存成功！", 3500);
    			}
    			else if (r == "sessionOut") {
                    doLogout();
                }
    			else {
    				alertText("保存失败！", 3500);
    			}
    		},
    		error : function(e) {
    			alert(e.responseText);
    		}
        });
        return false;
    });
});
}
