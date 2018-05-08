//首页
function FirstPageGroup() {
    GetGroupList(1);
}

//上一页
function PreviousPageGroup() {
    var pageIndex = (parseInt($("#groupPaging .pageIndex").text()) == 1 ? 1 
    		: parseInt($("#groupPaging .pageIndex").text()) - 1);
    GetGroupList(pageIndex);
}

//下一页
function NextPageGroup() {
    var pageIndex = ($("#groupPaging .pageIndex").text() == $(
    		"#groupPaging .pageCount").text() ? parseInt($(
    		"#groupPaging .pageIndex").text()) : parseInt($(
    		"#groupPaging .pageIndex").text()) + 1);
    GetGroupList(pageIndex);
}

//尾页
function EndPageGroup() {
    GetGroupList(parseInt($("#groupPaging .pageCount").text()));
}

//全选
function checkAll() {
	for (var i = 0; i < document.getElementsByName("selectFlag").length; i++) {
		document.getElementsByName("selectFlag")[i].checked = document.getElementById("ifAll").checked;
	}
}

//将得到的数据变成html格式代码字符串
function GetEmailGroupEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.CreatePerson + "</td>";
    str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialogEmailGroup(\"edit\",\"" + data.Id
            + "\",this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelEmailGroup(\""
		+ data.Id + "\")' /></td>";
    str += "</tr>";
    return str;
}

//邮件群组列表
function GetGroupList(strPageIndex) {
    var strQuery = $("#txt_groupQuery").val();
    var strStraTime = $("#txt_groupBeginDate").val();
    var strEndTime = $("#txt_groupEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EmailGroupServlet?methodName=QueryList&strQuery="
            					+ strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&str" +
                                		"PageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#EmailGroupList").html('');
            if (r == "false") {
                $("#EmailGroupList").append("<tr><td colspan='5' style='text-align:center;'>无数据</td></tr>");
                return false;
            }
            else if(r == "sessionOut")	{
            	doLogout();
            }
            else  {
	            var data = $.parseJSON(r);
	            $("#groupPaging .dataCount").text(data.total);
	            $("#groupPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
	            $("#groupPaging .pageIndex").text(strPageIndex);
	            var str = "";
	            for (var i = 1; i <= data.webList.length ; i++) {
	            	str += GetEmailGroupEntry(data.webList[i-1],i);//将得到的数据处理成html格式代码字符串
	            }
	            $("#EmailGroupList").append(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//删除
function DelEmailGroup(id) {
		$.ajax({
			type : "post",
			url : projectLocation
					+ "servlet/EmailGroupServlet?methodName=del&id=" + id,
			dataType : "text",
			async : false,
			success : function(r) {
				if (r == "true") {
					alertText("删除成功！",3500);
					GetGroupList(1);
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
function showDialogEmailGroup(methodName, Id,pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#GroupListDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    //编辑
    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/EmailGroupServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_groupListParam").val("methodName=edit&id=" + Id);
                    $("#txt_emailGroupName").val(r.Name);
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
        $("#hid_groupListParam").val("methodName=add");
        $("#txt_emailGroupName").val("");
    } 
}

//群组管理
function showGroupManage(Id) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#GroupListDom_Manage').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EmailGroupServlet?methodName=GroupManage&id=" + Id,
        dataType: "text",
        async: false,
        success: function (r) {
            $("#groupManageList").html('');
            if (r == "false") {
                $("#groupManageList").append("<tr><td colspan='6' style='text-align:center;'>无数据</td></tr>");
                return false;
            }
            else if(r == "sessionOut")	{
            	doLogout();
            }
            else  {
	            var data = $.parseJSON(r);
	            var str = "";
	            var j = 0;
	            for (var i = 0; i < data.webList.length; i++) {
	                str += "<tr>";
	                str += "<td><input type='checkbox' name=selectFlag value=(i+1) /></td>";
	                str += "<td>" + (i + 1) + "</td>";
	                str += "<td>" + data.webList[i].Name + "</td>";
	                str += "<td>" + data.webList[i].EmailAddress + "</td>";
					str += "<td>" + data.webList[i].Company + "</td>"; 
					str += "<td>" + data.webList[i].Subscription + "</td>";
					str += "<td>" + data.webList[i].EmailGroup + "</td>";
	                str += "<td>" + data.webList[i].Remark + "</td>"; 
	                str += "<td>" + data.webList[i].CreateTime.substring(0, 19) + "</td>";
	                str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showDialogEmailDetail(\"detail\",\""
						+ data.webList[i].Id + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialogEmail(\"edit\",\""
						+ data.webList[i].Id + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelEmail(\""
						+ data.webList[i].Id + "\")' /></td>";
	                str += "</tr>";
	                j++;
	            }
	            $("#groupManageList").append(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//保存
function SaveEmailGroup() {
	var strName = encode($("#txt_emailGroupName").val());
    if ($.trim(strName) == "") {
        alertText("群组名不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EmailGroupServlet?methodName="
            					+ getParam("hid_groupListParam", "methodName") + "&id=" + getParam("hid_groupListParam", "id")
                                + "&name=" + strName,
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
                UpdateEmailGroup(data);//更新邮件群组列表
                $('#GroupListDom_Edit').animate({ width: "hide" });
                $("#GroupListDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//添加或编辑后更新邮件群组列表
function UpdateEmailGroup(data){
    var str = "";
    //添加保存新增一行显示添加的数据
    if(getParam("hid_groupListParam", "methodName") == "add")
    {
        str += GetEmailGroupEntry(data, '*');
        $("#EmailGroupList").find('.noDataSrc').remove();
        $("#EmailGroupList").prepend(str);
    }
    //编辑保存修改编辑的那一行数据
    else if(getParam("hid_groupListParam", "methodName") == "edit")
    {
        var obj =  $("#EmailGroupList").find('.editObject').find('td');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.CreatePerson);
        obj.eq(3).html(data.CreateTime.substring(0, 19));
        $("#EmailGroupList").find('.editObject').removeClass('editObject');
    }
}