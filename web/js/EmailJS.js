//首页
function FirstPageEmail() {
    GetEmailList(1);
}

//上一页
function PreviousPageEmail() {
    var pageIndex = (parseInt($("#emailPaging .pageIndex").text()) == 1 ? 1 
    		: parseInt($("#emailPaging .pageIndex").text()) - 1);
    GetEmailList(pageIndex);
}

//下一页
function NextPageEmail() {
    var pageIndex = ($("#emailPaging .pageIndex").text() == $(
    		"#emailPaging .pageCount").text() ? parseInt($(
    		"#emailPaging .pageIndex").text()) : parseInt($(
    		"#emailPaging .pageIndex").text()) + 1);
    GetEmailList(pageIndex);
}

//尾页
function EndPageEmail() {
    GetEmailList(parseInt($("#emailPaging .pageCount").text()));
}

//全选
function checkAll() {
	for (var i = 0; i < document.getElementsByName("selectFlag").length; i++) {
		document.getElementsByName("selectFlag")[i].checked = document.getElementById("ifAll").checked;
	}
}

//将得到的数据变成html格式代码字符串
function GetEmailEntry(data, idx){
    var str = "<tr>";
    str += "<td><input type='checkbox' name=selectFlag value=idx /></td>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.EmailAddress + "</td>";
    str += "<td>" + data.Company + "</td>"; 
	str += "<td>" + data.Subscription + "</td>";
    str += "<td>" + data.Remark + "</td>";
    str += "<td>" + data.CreatePerson + "</td>";
    str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showDialogEmailDetail(\"detail\",\""
		+ data.Id + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialogEmail(\"edit\",\"" + data.Id
            + "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelEmail(\""
		+ data.Id + "\")' /></td>";
    str += "</tr>";
    return str;
}

//显示邮件列表
function GetEmailList(strPageIndex) {
    var strQuery = $("#txt_emailQuery").val();//查询-关键字
    var strStraTime = $("#txt_emailBeginDate").val();//查询-创建起始时间
    var strEndTime = $("#txt_emailEndDate").val();//查询-创建结束时间

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EmailServlet?methodName=QueryList&strQuery="
            					+ strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&str" +
                                		"PageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#emailList").html('');
            if (r == "false") {
                $("#emailList").append("<tr><td colspan='10' style='text-align:center;'>无数据</td></tr>");
                return false;
            }
            else if(r == "sessionOut")	{
            	doLogout();
            }
            else  {
	            var data = $.parseJSON(r);
	            $("#emailPaging .dataCount").text(data.total);
	            $("#emailPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
	            $("#emailPaging .pageIndex").text(strPageIndex);
	            var str = "";
	            for (var i = 1; i <= data.webList.length; i++) {
	                str += GetEmailEntry(data.webList[i-1],i);//将得到的数据处理成html格式代码字符串
	            }
	            $("#emailList").append(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//删除
function DelEmail(Id) {
		$.ajax({
			type : "post",
			url : projectLocation
					+ "servlet/EmailServlet?methodName=del&id=" + Id,
			dataType : "text",
			async : false,
			success : function(r) {
				if (r == "true") {
					alertText("删除成功！",3500);
					GetEmailList(1);
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

//查看
function showDialogEmailDetail(methodName, Id) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#emailListDom_Detail').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    InitEmailGroupDetail();
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EmailServlet?methodName=init&id=" + Id,
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
                $("#hid_emailListParamD").val("methodName=detail&id=" + Id);
                $("#txt_emailNameD").val(r.Name);
                $("#txt_emailAddressD").val(r.EmailAddress);
                $("#txt_emailCompanyD").val(r.Company);
                $("#txt_emailRemarkD").val(r.Remark);
                for (var i = 0; i < document.getElementsByName("txt_emailGroupD").length; i++) 
                {
                	for(var j = 0;j<r.webList.length;j++)
                	{
                		if((document.getElementsByName("txt_emailGroupD")[i].value)==r.webList[j].Id)
                			document.getElementsByName("txt_emailGroupD")[i].checked=true;
                	}
            	}
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
}

//查看页面群组列表初始化
function InitEmailGroupDetail()
{
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/EmailGroupServlet?methodName=getEmailGroup",
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
            	var str="";
            	str = "<tr><td width='100' align='right' class='tab_th'>群组名：</td><td>";
                for (var i = 0; i < r.webList.length; i++)
                {
                	str +="&nbsp;<input type='checkbox' id='txt_emailGroupD' name='txt_emailGroupD' value='" + r.webList[i].Id + "'>" + r.webList[i].Name + "&nbsp;&nbsp;&nbsp;</input>"
                }
            	str+="</td></tr>";
            	$("#GroupList_Detail").html(str);
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

//编辑页面群组列表初始化
function InitEmailGroup()
{
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/EmailGroupServlet?methodName=getEmailGroup",
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
            	var str="";
            	str = "<tr><td width='100' align='right' class='tab_th'>群组名：</td><td>";
                for (var i = 0; i < r.webList.length; i++)
                {
                	str +="&nbsp;<input type='checkbox' id='txt_emailGroup' name='txt_emailGroup' value='" + r.webList[i].Id + "'>" + r.webList[i].Name + "&nbsp;&nbsp;&nbsp;</input>"
                }
            	str+="</td></tr>";
            	$("#GroupList").html(str);
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

//编辑页面（添加+编辑）
function showDialogEmail(methodName, Id, pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#emailListDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    //编辑
    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/EmailServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	InitEmailGroup();
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_emailListParam").val("methodName=edit&id=" + Id);
                    $("#txt_emailName").val(r.Name);
                    $("#txt_emailAddress").val(r.EmailAddress);
                    $("#txt_emailCompany").val(r.Company);
                    $("#txt_emailRemark").val(r.Remark);
                    for (var i = 0; i < document.getElementsByName("txt_emailGroup").length; i++) 
                    {
                    	for(var j = 0;j<r.webList.length;j++)
                    	{
                    		if((document.getElementsByName("txt_emailGroup")[i].value)==r.webList[j].Id)
                    			document.getElementsByName("txt_emailGroup")[i].checked=true;
                    	}
                	}
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
    	//初始化群组列表
    	InitEmailGroup();
        $("#hid_emailListParam").val("methodName=add");
        $("#txt_emailName").val("");
        $("#txt_emailAddress").val("");
        $("#txt_emailCompany").val("");
        $("#txt_emailRemark").val("");
    } 
}

//保存
function SaveEmail() {
	var strEmailName = encode($("#txt_emailName").val());
	var strEmailAddress = $("#txt_emailAddress").val();
    var strCompany = encode($("#txt_emailCompany").val());
    var strRemark = encode($("#txt_emailRemark").val());
    var strEmailGroup="";//记录勾选的群组
    var cal=document.getElementsByName('txt_emailGroup');
    for(i=0;i<cal.length;i++)
    	{
    	if(cal[i].checked)
    		strEmailGroup+=cal[i].value+"_";//多个群组ID间以"_"隔开
    	}
    if(strEmailGroup!="")
    {
    	strEmailGroup = strEmailGroup.substring(0,strEmailGroup.length -1);//消除字符串最后一位"_"
    }
    if ($.trim(strEmailAddress) == "") {
        alertText("邮件地址不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EmailServlet?methodName="
            					+ getParam("hid_emailListParam", "methodName") + "&id=" + getParam("hid_emailListParam", "id")
                                + "&name=" + strEmailName + "&emailAddress=" + strEmailAddress + "&remark=" + strRemark + "&company=" + strCompany + "&emailGroup=" + strEmailGroup,
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
                UpdateEmail(data);//更新邮件列表
                $('#emailListDom_Edit').animate({ width: "hide" });
                $("#emailListDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//添加或编辑后更新邮件列表
function UpdateEmail(data){
    var str = "";
    //添加保存新增一行显示添加的数据
    if(getParam("hid_emailListParam", "methodName") == "add")
    {
        str += GetEmailEntry(data, '*');
        $("#emailList").find('.noDataSrc').remove();
        $("#emailList").prepend(str);
    }
    //编辑保存修改编辑的那一行数据
    else if(getParam("hid_emailListParam", "methodName") == "edit")
    {
        var obj =  $("#emailList").find('.editObject').find('td');
        obj.eq(2).html(data.Name);
        obj.eq(3).html(data.EmailAddress);
        obj.eq(4).html(data.Company);
        obj.eq(5).html(data.Subscription);
        obj.eq(6).html(data.Remark);
        obj.eq(7).html(data.CreatePerson);
        obj.eq(8).html(data.CreateTime.substring(0, 19));
        $("#emailList").find('.editObject').removeClass('editObject');
    }
}
