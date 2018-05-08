function FirstPage_MyConsultive() {
	SearchConsultiveInformation(1);
}

function PreviousPage_MyConsultive() {
    var pageIndex = (parseInt($("#MyConsultivePaging .pageIndex").text()) == 1 ? 1 : parseInt($("#MyConsultivePaging .pageIndex").text()) - 1);
    SearchConsultiveInformation(pageIndex);
}

function NextPage_MyConsultive() {
    var pageIndex = ($("#MyConsultivePaging .pageIndex").text() == $("#MyConsultivePaging .pageCount").text() ? parseInt($("#MyConsultivePaging .pageIndex").text()) : parseInt($("#MyConsultivePaging .pageIndex").text()) + 1);
    SearchConsultiveInformation(pageIndex);
}

function EndPage_MyConsultive() {
	SearchConsultiveInformation(parseInt($("#MyConsultivePaging .pageCount").text()));
}

function HomePage()
{
	$("#iframeMain .wrap").animate({ width: "hide" });
	$('#HomePage').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    $('#MyConsultiveAdvice').hide();
    $('#MyConsultiveDetail').hide();
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/userServlet?methodName=Modify",
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
            	$("#UserCode").val(r.userCode);
            	if(r.realName!="null")
            		$("#Real_Name").val(r.realName);
            	else
            		$("#Real_Name").val("");
            	if(r.Mobile!="null")
            		$("#MobilePhone").val(r.Mobile);
            	else
            		$("#MobilePhone").val("");
            	var str="";
            	str +="<tr><td></td><td><input class='btn btn-default btn-xs' type='button' value='提 交' onclick='savaModify(\""+ r.Id + "\")'></td></tr>";
            	$("#Confirm").html(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function SearchConsultiveInformation(strPageIndex)
{
	$('#MyConsultiveAdvice').show();
	$.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ConsultiveServlet?methodName=My_QueryList&strPageIndex=&"+strPageIndex +"strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#MyConsultive").html('');
            if (r == "false") {
                $("#MyConsultive").html("您还没有任何咨询建议投诉");
                return false;
            }
            var data = $.parseJSON(r);

            $("#MyConsultivePaging .dataCount").text(data.total);
            $("#MyConsultivePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#MyConsultivePaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + data.webList[i].Title + "</td>";
                if(data.webList[i].Type=="1")
            	{
            		str += "<td>咨询</td>";
            	}
            else if(data.webList[i].Type=="2")
            	{
            		str += "<td>建议</td>";
            	}
            else
            	{
            	str += "<td>投诉</td>";
            	}
            str += "<td>" + data.webList[i].Content + "</td>";
            if(data.webList[i].ReturnContent!="null")
            	str += "<td>已回复</td>";
            else
            	str += "<td>未回复</td>";
                str += "<td>" + data.webList[i].ConsultiveTime + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='详 情' onclick='Answer_Detail(\"My\",\""+ data.webList[i].Id + "\")' /></td>";
                str += "</tr>";
            }
            $("#MyConsultive").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function ChangePassword()
{
	$("#iframeMain .wrap").animate({ width: "hide" });
	$('#ChangePassword').show();
	$("#Old_Password").val("");
	$("#New_Password").val("");
	$("#Confirm_Password").val("");
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
}
function SaveChangePassword()
{
	var old_password=$("#Old_Password").val();
	var new_password=$("#New_Password").val();
	var confirm_password=$("#Confirm_Password").val();
	if(new_password=="" || confirm_password==""){
		alertText("新密码不能为空！", 3500);
        return false;
	}
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/userServlet?methodName=ChangePassword&old_password="+ old_password
        						+ "&new_password=" + new_password + "&confirm_password=" + confirm_password + "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("修改成功！", 3500);
            } else if(r=="false1"){
                alertText("密码错误！", 3500);
            }else if(r=="false2"){
            	alertText("两次密码不一致！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function savaModify(Id)
{
	var realName=$("#Real_Name").val();
	var mobile=$("#MobilePhone").val();
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/userServlet?methodName=edit_self&id=" + Id
                                + "&realName=" + encode(realName) + "&mobile=" + mobile + "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("修改成功！", 3500);
            } else {
                alertText("修改失败！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}