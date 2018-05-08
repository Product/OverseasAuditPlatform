//首页
function FirstPageEvaluationType() {
    GetEvaluationTypeList(1);
}

//上一页
function PreviousPageEvaluationType() {
    var pageIndex = (parseInt($("#EvaluationTypePaging .pageIndex").text()) == 1 ? 1 : parseInt($("#EvaluationTypePaging .pageIndex").text()) - 1);
    GetEvaluationTypeList(pageIndex);
}

//下一页
function NextPageEvaluationType() {
    var pageIndex = ($("#EvaluationTypePaging .pageIndex").text() == $("#EvaluationTypePaging .pageCount").text() ? parseInt($("#EvaluationTypePaging .pageIndex").text()) : parseInt($("#EvaluationTypePaging .pageIndex").text()) + 1);
    GetEvaluationTypeList(pageIndex);
}

//尾页
function EndPageEvaluationType() {
    GetEvaluationTypeList(parseInt($("#EvaluationTypePaging .pageCount").text()));
}

//将得到的数据变成html格式代码字符串
function GetEvaluationTypeEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.Remark + "</td>";
    str += "<td>" + data.CreatePerson + "</td>";
    str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialogEvaluationType(\"edit\",\""
		+ data.Id + "\",this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
		+ data.Id + "\",DelEvaluationType)' /></td>";
    str += "</tr>";
    return str;
}

//评价类别列表
function GetEvaluationTypeList(strPageIndex) {
	var strQuery = $("#txt_EvaluationTypeQuery").val();
	var strStraTime = $("#txt_EvaluationTypeBeginDate").val();
	var strEndTime = $("#txt_EvaluationTypeEndDate").val();
	$.ajax({
				type : "post",
				url : projectLocation
						+ "servlet/EvaluationTypeServlet?methodName=QueryList&strQuery="
						+ strQuery + "&strStraTime=" + strStraTime
						+ "&strEndTime=" + strEndTime + "&strPageIndex="
						+ strPageIndex + "&strPageCount=10",
				dataType : "text",
				async : false,
				success : function(r) {
					$("#EvaluationTypeList").html('');
					if (r == "false") {
						$("#EvaluationTypeList")
								.append(
										"<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
						return false;
					}
					else if(r == "sessionOut")	{
		            	doLogout();
		            }
	                else  {
	                	var data = $.parseJSON(r);

	                    $("#EvaluationTypePaging .dataCount").text(data.total);
	                    $("#EvaluationTypePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
	                    $("#EvaluationTypePaging .pageIndex").text(strPageIndex);
	                    var str = "";
	                    for (var i = 1; i <= data.webList.length; i++) {
	                        str += GetEvaluationTypeEntry(data.webList[i-1], i);
						}
						$("#EvaluationTypeList").append(str);
	                }
				},
				error : function(e) {
					alert(e.responseText);
				}
			});
}

//删除
function DelEvaluationType(id) {
		$.ajax({
			type : "post",
			url : projectLocation
					+ "servlet/EvaluationTypeServlet?methodName=del&id=" + id,
			dataType : "text",
			async : false,
			success : function(r) {
				if (r == "true") {
					alertText("删除成功！",  3500);
					GetEvaluationTypeList(1);
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

//编辑页面（添加+编辑）
function showDialogEvaluationType(methodName, Id, pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#EvaluationTypeDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    if (methodName != "add") {//编辑
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/EvaluationTypeServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_EvaluationTypeParam").val("methodName=edit&id=" + Id);
                    $("#txt_EvaluationTypeName").val(r.Name);
                    $("#txt_EvaluationTypeRemark").val(r.Remark);
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
    } else {//添加
        $("#hid_EvaluationTypeParam").val("methodName=add");
        $("#txt_EvaluationTypeName").val("");
        $("#txt_EvaluationTypeRemark").val("");
    } 
}

//保存
function SaveEvaluationType() {
	var strName = encode($("#txt_EvaluationTypeName").val());
    var strRemark = encode($("#txt_EvaluationTypeRemark").val());
    if ($.trim(strName) == "") {
        alertText("类型名称不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EvaluationTypeServlet?methodName="
            					+ getParam("hid_EvaluationTypeParam", "methodName")
            					+ "&id=" + getParam("hid_EvaluationTypeParam", "id")
                                + "&name=" + strName + "&remark=" + strRemark 
                                + "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
        	if (r.res == "false") {
                alertText("保存失败！", 3500);   
            }
            else if(r == "sessionOut")	{
            	doLogout();
            }
            else {
            	alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateEvaluationType(data);//更新评价类别列表
                $('#EvaluationTypeDom_Edit').animate({ width: "hide" });
                $("#EvaluationTypeDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//添加或编辑后更新评价类别列表
function UpdateEvaluationType(data){
    var str = "";
    if(getParam("hid_EvaluationTypeParam", "methodName") == "add")//添加保存新增一行显示添加的数据
    {
        str += GetEvaluationTypeEntry(data, '*');
        $("#EvaluationTypeList").find('.noDataSrc').remove();
        $("#EvaluationTypeList").prepend(str);
    }
    else if(getParam("hid_EvaluationTypeParam", "methodName") == "edit")//编辑保存修改编辑的那一行数据
    {
        var obj =  $("#EvaluationTypeList").find('.editObject').find('td');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.Remark);
        obj.eq(3).html(data.CreatePerson);
        obj.eq(4).html(data.CreateTime.substring(0, 19));
        $("#EvaluationTypeList").find('.editObject').removeClass('editObject');
    }
}
