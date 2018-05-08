function FirstPage_Consultive() {
    GetConsultiveList(1);
}

function PreviousPage_Consultive() {
    var pageIndex = (parseInt($("#ConsultivePaging .pageIndex").text()) == 1 ? 1 : parseInt($("#ConsultivePaging .pageIndex").text()) - 1);
    GetConsultiveList(pageIndex);
}

function NextPage_Consultive() {
    var pageIndex = ($("#ConsultivePaging .pageIndex").text() == $("#ConsultivePaging .pageCount").text() ? parseInt($("#ConsultivePaging .pageIndex").text()) : parseInt($("#ConsultivePaging .pageIndex").text()) + 1);
    GetConsultiveList(pageIndex);
}

function EndPage_Consultive() {
    GetConsultiveList(parseInt($("#ConsultivePaging .pageCount").text()));
}

function GetConsultiveEntry(data,idx){
	var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Title + "</td>";
    if(data.Type=="1")
	{
		str += "<td>" + "咨询" + "</td>";
	}
	else if(data.Type=="2")
		{
			str += "<td>" + "建议" + "</td>";
		}
	else
		{
		str += "<td>" + "投诉" + "</td>";
		}
	str += "<td>" + data.Content + "</td>";
	if(data.ReturnContent!="null")
		str += "<td>" + "已回复" + "</td>";
	else
		str += "<td>" +"未回复" + "</td>";
	if(data.ReturnTime!="null")
		str += "<td>" + data.ReturnTime + "</td>";
	else
		str += "<td>" +"未回复" + "</td>";
	    str += "<td>" + data.Consultant + "</td>";
	    str += "<td>" + data.ConsultiveTime + "</td>";
	    str += "<td><input  class='btn btn-default btn-xs' type='button' value='回 复' onclick='showDialog_Answer(\""
				+ data.Id
				+ "\",this)' />&nbsp;<input  class='btn btn-default btn-xs' type='button' value='详 情' onclick='Answer_Detail(\"Ordinary\",\""+ data.Id + "\")' /></td>";
	    str += "</tr>";
	    return str;
}

function GetConsultiveList(strPageIndex) {
    var strQuery = $("#txt_ConsultiveQuery").val();
    var strStraTime = $("#txt_ConsultiveBeginDate").val();
    var strEndTime = $("#txt_ConsultiveEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ConsultiveServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#ConsultiveList").html('');
            if (r == "false") {
                $("#ConsultiveList")
						.append("<tr class='noDataSrc'><td colspan='9' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#ConsultivePaging .dataCount").text(data.total);
            $("#ConsultivePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#ConsultivePaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
                str += GetConsultiveEntry(data.webList[i],i+1);
            }
            $("#ConsultiveList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function showDialog_Answer(Id,pos)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#ConsultiveDom_Answer').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ConsultiveServlet?methodName=Detail&id="+Id,
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#AnswerList").html('');
            if (r == "false") {
                return false;
            }
            var data = $.parseJSON(r);
            $(pos).parent().parent().addClass('editObject');
            $("#hid_ConsultiveParam").val("methodName=Answer&id=" + Id);
            var str = "";
            for (var i = 0; i < (data.webList.length-1); i++) {
            	if(data.webList[i].ParentNod=="0")
            	{
            		$("#txt_ConsultiveTitle1").val(data.webList[i].Title);
            		str += "<tr>";
    	            str += "<td  align=\"right\" class=\"tab_th\">" +"标题"+ "</td>";
    	            str += "<td>" + data.webList[i].Title + "</td>";
    	            str += "</tr>";
            		str += "<tr>";
	                str += "<td  align=\"right\" class=\"tab_th\">" +"内容" + "</td>";
	                str += "<td>" + data.webList[i].Content + "</td>";
	                str += "</tr>";
	                str += "<tr>";
		            str += "<td  align=\"right\" class=\"tab_th\">" +"回复内容" + "</td>";
		            str += "<td>" + data.webList[i].ReturnContent + "</td>";
		            str += "</tr>";
	            }
            	else
            		{
	                str += "<tr>";
	                str += "<td  align=\"right\" class=\"tab_th\">" + "追问" + "</td>";
	                str += "<td>" + data.webList[i].Content + "</td>";
	                str += "</tr>";
	                str += "<tr>";
		            str += "<td  align=\"right\" class=\"tab_th\">" +"回复内容" + "</td>";
		            str += "<td>" + data.webList[i].ReturnContent + "</td>";
		            str += "</tr>";
                }
            }
            if(data.webList[i].ParentNod=="0")
    		{
	    		$("#txt_ConsultiveTitle1").val(data.webList[i].Title);
	    		str += "<tr>";
	            str += "<td  align=\"right\" class=\"tab_th\">" +"标题"+ "</td>";
	            str += "<td>" + data.webList[i].Title + "</td>";
	            str += "</tr>";
	    		str += "<tr>";
	            str += "<td  align=\"right\" class=\"tab_th\">" +"内容" + "</td>";
	            str += "<td>" + data.webList[i].Content + "</td>";
	            str += "</tr>";
	            if(data.webList[i].ReturnContent!="null")
	            {
	            	str += "<tr>";
		            str += "<td  align='right' class='tab_th'>" +"回复内容" + "</td>";
		            str += "<td>" + data.webList[i].ReturnContent + "</td>";
		            str += "</tr>";
		            str += "<tr>";
		            str += "<td  align='right' class='tab_th'>" + "追问" +"</td>";
		            str += "<td><textarea id=\"txt_AskContent\" style='width: 300px;' rows='3' runat='server' cols='20' name='S1'></textarea></td>";
		            str += "</tr>";
		            str += "<tr><td>";
                    str += "<td colspan='2' style='padding-left: 102px; padding-top: 10px;'>" +
                    		"<input   class='btn btn-default btn-xs' type='button' value='提 交' onclick='AskConsultive(\"Ask\",\"" + data.webList[i].Id + "\")' />" +
                    		"&nbsp;&nbsp;<input class='btn btn-default btn-xs' type='button' value='返 回' rel='ConsultiveEditDom' onclick='toUrl(this)' /></td>";
                    str +="</td></tr>";
	            }
	            else
	            {
		            str += "<tr>";
		            str += "<td  align=\"right\" class=\"tab_th\">" + "回复" +"</td>";
		            str += "<td><textarea id=\"txt_AnswerContent\" style=\"width: 300px;\" rows=\"3\" runat=\"server\" cols=\"20\" name=\"S1\"></textarea></td>";
		            str += "</tr>";
		            str += "<tr><td>";
                    str += "<td colspan='2' style='padding-left: 102px; padding-top: 10px;'>" +
                    		"<input class='btn btn-default btn-xs' type='button' value='提 交' onclick='AnswerConsultive(\"Answer\",\"" + data.webList[i].Id + "\")' />" +
                    		"&nbsp;&nbsp;<input type=\"button\" value=\"返 回\" rel=\"ConsultiveEditDom\" onclick=\"toUrl(this)\" /></td>";
                    str +="</td></tr>";
	            }
    		}
            else
    		{
            	str += "<tr>";
	            str += "<td  align=\"right\" class=\"tab_th\">" + "追问" + "</td>";
	            str += "<td>" + data.webList[i].Content + "</td>";
	            str += "</tr>";
	            if(data.webList[i].ReturnContent!="null")
	            {
	            	str += "<tr>";
		            str += "<td  align=\"right\" class=\"tab_th\">" + "回复内容" + "</td>";
		            str += "<td>" + data.webList[i].ReturnContent + "</td>";
		            str += "</tr>";
		            str += "<tr>";
		            str += "<td  align=\"right\" class=\"tab_th\">"+ "追问" +"</td>";
		            str += "<td><textarea id=\"txt_AskContent\" style=\"width: 300px;\" rows=\"3\" runat=\"server\" cols=\"20\" name=\"S1\"></textarea></td>";
		            str += "</tr>";
		            str += "<tr><td></td>";
                    str += "<td colspan=\"2\" style=\"padding-left: 102px; padding-top: 10px;\">" +
                    		"<input type=\"button\" value=\"提 交\" onclick='AskConsultive(\"Ask\",\"" + data.webList[i].Id + "\")' />" +
                    		"&nbsp;&nbsp;<input type=\"button\" value=\"返 回\" rel=\"ConsultiveEditDom\" onclick=\"toUrl(this)\" /></td>";
                    str +="</tr>";
	            }
	            else
	            {
	            	$("#hid_ConsultiveParam").val("methodName=Answer&id=" + data.webList[i].Id);
		            str += "<tr>";
		            str += "<td  align=\"right\" class=\"tab_th\">" + "回复" + "</td>";
		            str += "<td><textarea id=\"txt_AnswerContent\" style=\"width: 300px;\" rows=\"3\" runat=\"server\" cols=\"20\" name=\"S1\"></textarea></td>";
		            str += "</tr>";
		            str += "<tr><td></td>";
                    str += "<td colspan=\"2\" style=\"padding-left: 102px; padding-top: 10px;\">" +
                    		"<input type=\"button\" value=\"提 交\" onclick='AnswerConsultive(\"Answer\",\"" + data.webList[i].Id + "\")' />" +
                    		"&nbsp;&nbsp;<input type=\"button\" value=\"返 回\" rel=\"ConsultiveEditDom\" onclick=\"toUrl(this)\" /></td>";
                    str +="</tr>";
	            }
    		}
            $("#AnswerList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function AnswerConsultive(methodName,Id)
{
	var answer= encode($("#txt_AnswerContent").val());
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/ConsultiveServlet?methodName="
            					+ methodName + "&id=" + Id + "&answer="+ answer,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	alertText("回复失败！", 3500);
            } else {
            	alertText("回复成功！", 3500);
            	var data = $.parseJSON(r);
                UpdateConsultive(data);
                $('#ConsultiveDom_Answer').animate({ width: "hide" });
                $("#ConsultiveEditDom").show();
                
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function AskConsultive(methodName,Id)
{
	var question= encode($("#txt_AskContent").val());
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/ConsultiveServlet?methodName="
            					+ methodName + "&id=" + Id + "&question="+ question,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	alertText("追问失败！", 3500);
            } else {
            	alertText("追问成功！", 3500);
            	var data = $.parseJSON(r);
            	UpdateConsultive(data);
                $('#ConsultiveDom_Answer').animate({ width: "hide" });
                $("#ConsultiveEditDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function showDialog_Consultive(methodName,Id)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#ConsultiveDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/ConsultiveServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                    $("#hid_ConsultiveParam").val("methodName=edit&id=" + Id);
                    $("#txt_ConsultiveTitle").val(r.title);
                    $("#txt_ConsultiveContent").val(r.content);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_ConsultiveParam").val("methodName=add");
        $("#txt_ConsultiveTitle").val("");
		$("#txt_ConsultiveContent").val("");
        $('#ConsultiveContent').show();
		$('#AdivceContent').hide();
		$('#ComplainContent').hide();
    }
}
function ConsultiveChang()
{
	var strType=$("#txt_consultivetype").val();
	if(strType=="1")
	{
		$('#ConsultiveContent').show();
		$('#AdivceContent').hide();
		$('#ComplainContent').hide();
		
	}
	else if(strType=="2")
	{
		$('#ConsultiveContent').hide();
		$('#AdivceContent').show();
		$('#ComplainContent').hide();
	}
	else if(strType=="3")
	{
		$('#ConsultiveContent').hide();
		$('#AdivceContent').hide();
		$('#ComplainContent').show();
	}
}

function Answer_Detail(Role,Id)
{
	if(Role=="Ordinary" || Role=="Expert")
	{
		$("#iframeMain .wrap").animate({ width: "hide" });
		$('#ConsultiveDom_Detail').show();
	}
	else if(Role=="My")
	{
		$('#MyConsultiveAdvice').hide();
		$('#MyConsultiveDetail').show();
	}
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ConsultiveServlet?methodName=Detail&id="+Id,
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#ConsultiveDetailList").html('');
            $("#MyConsultiveDetailList").html('');
            if (r == "false") {
                return false;
            }
            var data = $.parseJSON(r);
            
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
            	if(data.webList[i].ParentNod=="0")
            	{
            		$("#txt_ConsultiveTitle1").val(data.webList[i].Title);
            		str += "<tr>";
    	            str += "<td  align=\"right\" class=\"tab_th\">" +"标题"+ "</td>";
    	            str += "<td>" + data.webList[i].Title + "</td>";
    	            str += "</tr>";
            		str += "<tr>";
	                str += "<td  align=\"right\" class=\"tab_th\">" +"内容" + "</td>";
	                str += "<td>" + data.webList[i].Content + "</td>";
	                str += "<td>" +data.webList[i].ConsultiveTime + "</td>"
	                str += "</tr>";
	                if(data.webList[i].ReturnContent!="null")
		            {
		            	str += "<tr>";
			            str += "<td  align='right' class='tab_th'>" +"回复内容" + "</td>";
			            str += "<td>" + data.webList[i].ReturnContent + "</td>";
			            str += "<td>" +data.webList[i].ReturnTime + "</td>"
			            str += "</tr>";
		            }
	            }
            	else
            		{
	                str += "<tr>";
	                str += "<td  align=\"right\" class=\"tab_th\">" + "追问" + "</td>";
	                str += "<td>" + data.webList[i].Content + "</td>";
	                str += "<td>" +data.webList[i].ConsultiveTime + "</td>"
	                str += "</tr>";
	                if(data.webList[i].ReturnContent!="null")
		            {
		            	str += "<tr>";
			            str += "<td  align='right' class='tab_th'>" +"回复内容" + "</td>";
			            str += "<td>" + data.webList[i].ReturnContent + "</td>";
			            str += "<td>" +data.webList[i].ReturnTime + "</td>"
			            str += "</tr>";
		            }
                }
            }
            str += "<tr><td></td>";
            str += "<td colspan='2' style='padding-left: 102px; padding-top: 10px;'>";
            if(Role=="Ordinary")
            	str+="<input class='btn btn-default btn-xs' type='button' value='返 回' rel='ConsultiveEditDom' onclick='toUrl(this)' /></td>";
            else if(Role=="Expert")
            	str+="<input class='btn btn-default btn-xs' type='button' value='返 回' rel='ExpertAnswerDom' onclick='toUrl(this)' /></td>";
            else if(Role=="My")
            	str += "<input class='btn btn-default btn-xs' type='button' value='返 回' onclick='toMyUrl(\"MyConsultiveAdvice\")'></input>"
            str +="</tr>";
            if(Role=="Ordinary" || Role=="Expert")
            	$("#ConsultiveDetailList").append(str);
            else if(Role=="My")
            	$("#MyConsultiveDetailList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function toMyUrl(url)
{
	$('#MyConsultiveAdvice').show();
	$('#MyConsultiveDetail').hide();
}
function SaveConsultive(){
	var strType=$("#txt_consultivetype").val();
	if(strType=="1")
	{
		var strTitle = encode($("#txt_ConsultiveTitle").val());
		var strContent = encode($("#txt_ConsultiveContent").val());
	}
	else if(strType=="2")
	{
		var strTitle = encode($("#txt_AdivceTitle").val());
		var strContent = encode($("#txt_AdivceContent").val());
	}
	else if(strType=="3")
	{
		var strTitle = encode($("#txt_ComplainTitle").val());
		var strContent = encode($("#txt_ComplainContent").val());
	}
    if ($.trim(strTitle) == "") {
        alertText("标题不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ConsultiveServlet?methodName="
            					+ getParam("hid_ConsultiveParam", "methodName") + "&id=" + getParam("hid_ConsultiveParam", "id")
                                + "&title=" + strTitle + "&type=" + strType +"&content=" + strContent + "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("保存失败！", 3500);
            } else {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateConsultive(data);
                $('#ConsultiveDom_Edit').animate({ width: "hide" });
                $("#ConsultiveEditDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateConsultive(data)
{
	var str="";
	if(getParam("hid_ConsultiveParam", "methodName") == "add"){
		str += GetConsultiveEntry(data,'*');
		$("#ConsultiveList").find('.noDataSrc').remove();
		$("#ConsultiveList").prepend(str);
	}else if(getParam("hid_ConsultiveParam", "methodName") == "Answer"){
		var obj =  $("#ConsultiveList").find('.editObject').find('td');
		var de=$("#ConsultiveList").find('.editObject').find('td').eq(5).html();
        obj.eq(4).html("已回复");
        if(de=="未回复")
        	obj.eq(5).html(data.CreateTime);
        
        $("#ConsultiveList").find('.editObject').removeClass('editObject');
	}
}