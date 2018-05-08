function FirstPage_PersonalInformation() {
    GetPersonalInformationList(1);
}

function PreviousPage_PersonalInformation() {
    var pageIndex = (parseInt($("#PersonalInformationPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#PersonalInformationPaging .pageIndex").text()) - 1);
    GetPersonalInformationList(pageIndex);
}

function NextPage_PersonalInformation() {
    var pageIndex = ($("#PersonalInformationPaging .pageIndex").text() == $(
			"#PersonalInformationPaging .pageCount").text() ? parseInt($(
			"#PersonalInformationPaging .pageIndex").text()) : parseInt($(
			"#PersonalInformationPaging .pageIndex").text()) + 1);
    GetWebsiteStyleList(pageIndex);
}

function EndPage_PersonalInformation() {
    GetWebsiteStyleList(parseInt($("#PersonalInformationPaging .pageCount").text()));
}

function GetPersonalInformationEntry(data, idx){
	var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.userCode + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.RoleName + "</td>";
    str += "<td>" + data.Mobile + "</td>";
    str += "<td>" + data.CreatePerson + "</td>";
    str += "<td>" + data.CreateTime + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialog_PersonalInformation(\"Edit\",\""
			+ data.Id
			+ "\",this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
			+ data.Id + "\", DelUserInfo)' /></td>";
    str += "</tr>";
    return str;
}
function GetPersonalInformationList(strPageIndex) {
    var strQuery = $("#txt_PersonalInformationQuery").val();
    var strStraTime = $("#txt_PersonalInformationBeginDate").val();
    var strEndTime = $("#txt_PersonalInformationEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/userServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#PersonalInformationList").html('');
            if (r == "false") {
                $("#PersonalInformationList")
						.append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }else if(r=="sessionOut"){
				doLogout();
			}
            var data = $.parseJSON(r);

            $("#PersonalInformationPaging .dataCount").text(data.total);
            $("#PersonalInformationPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#PersonalInformationPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
            	str += GetPersonalInformationEntry(data.webList[i], i+1);
            }
            $("#PersonalInformationList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function DelUserInfo(id)
{
	$.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/userServlet?methodName=del&id=" + id,
        dataType: "text",
        async: false,
		   // 删除过程中还未得到删除结果，显示loading界面
		   beforeSend:function(XMLHttpRequest){
          $("#loading1").css("display","block");
			},
        success: function (r) {
			   // 删除操作完成(不管删除成功与否)，隐藏loading界面
			   $("#loading1").css("display","none");
            if (r == "true") {
                alertText("删除成功！", 3500);
                GetPersonalInformationList(1);
            }else if(r=="sessionOut"){
				doLogout();
			}
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function showDialog_PersonalInformation(methodName,Id,pos)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#PersonalInformationDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/userServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_PersonalInformationParam").val("methodName=edit&id=" + Id);
                    $("#txt_PersonalUserCode").val(r.code);
                    $("#txt_PersonalRealName").val(r.Name);
                    $("#txt_PersonalMobile").val(r.Mobile);
                    $("#txt_PersonalPassWord").val(r.Mobile);
                    getRoleMenu();
                    var cal=document.getElementsByName('txt_PersonalMenu');
                    for(i=0;i<cal.length;i++){
                    	for(j=0;j<r.Roles.length;j++){
                        	if(cal[i].value==r.Roles[j].Role)
                        		cal[i].checked=true;
                    	}
                    }
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    }
    else{
    	 
    	 $("#hid_PersonalInformationParam").val("methodName=add");
         $("#txt_PersonalUserCode").val("");
         $("#txt_PersonalRealName").val("");
         $("#txt_PersonalMobile").val("");
         $("#txt_PersonalPassWord").val("");
         getRoleMenu();
    }
}
function getRoleMenu()
{
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/RoleMenuServlet?methodName=getRoleList",
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
            	//var data = $.parseJSON(r);
            	var str="";
            	str = "<tr><td width='100' align='right' class='tab_th'>角色名：</td><td>";
                for (var i = 0; i < r.webList.length; i++)
                {
                	str +="<input type='checkbox' id='txt_PersonalMenu' name='txt_PersonalMenu' value='" + r.webList[i].Id + "'>" + r.webList[i].Name + "&nbsp;&nbsp;&nbsp;</input>"
                }
            	str+="</td></tr>";
            	$("#MenuList").html(str);
            }
            else{
            	var str="";
            	str = "<tr><td width='100' align='right' class='tab_th'>角色名</td>";
            	str += "<td>无可选角色</td></tr>";
            	$("#MenuList").html(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function SavePersonalInformation()
{
	var struserCode=$("#txt_PersonalUserCode").val();
	var strpassWord=$("#txt_PersonalPassWord").val();
	var strName = $("#txt_PersonalRealName").val();
    var strMobile = $("#txt_PersonalMobile").val();
    var strMenu="";
    var cal=document.getElementsByName('txt_PersonalMenu');
    for(i=0;i<cal.length;i++)
    	{
    	if(cal[i].checked)
    		
    		strMenu+=cal[i].value+"_";
    	}
    if(strMenu!="")
    {
    	strMenu = strMenu.substring(0, strMenu.length - 1);
    }
    if ($.trim(struserCode) == "" || $.trim(strpassWord) == "") {
        alertText("用户名和密码不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/userServlet?methodName="
            					+ getParam("hid_PersonalInformationParam", "methodName") + "&id=" + getParam("hid_PersonalInformationParam", "id")
                                + "&userCode=" + encode(struserCode) + "&passWord=" + strpassWord + "&name=" + encode(strName) +  "&strMenu=" +strMenu+ "&mobile=" + strMobile + "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	alertText("保存失败！", 3500);
            } else if(r=="sessionOut"){
				doLogout();
			}else {
            	alertText("保存成功！", 3500);
            	var data = $.parseJSON(r);
            	UpdatePersonalInformation(data);
                $('#PersonalInformationDom_Edit').animate({ width: "hide" });
                $("#PersonalInformationDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdatePersonalInformation(data){
    var str = "";
    if(getParam("hid_PersonalInformationParam", "methodName") == "add"){
        str += GetPersonalInformationEntry(data, '*');
        $("#PersonalInformationList").find('.noDataSrc').remove();
        $("#PersonalInformationList").prepend(str);
    }else if(getParam("hid_PersonalInformationParam", "methodName") == "edit"){
        var obj =  $("#PersonalInformationList").find('.editObject').find('td');
        obj.eq(1).html(data.userCode);
        obj.eq(2).html(data.Name);
        obj.eq(3).html(data.RoleName);
        obj.eq(4).html(data.Mobile);
        obj.eq(5).html(data.CreatePerson);
        obj.eq(6).html(data.CreateTime);
        $("#PersonalInformationList").find('.editObject').removeClass('editObject');
    }
}