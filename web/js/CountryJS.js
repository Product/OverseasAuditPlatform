function FirstPage_Country() {
    GetCountryList(1);
}

function PreviousPage_Country() {
    var pageIndex = (parseInt($("#CountryPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#CountryPaging .pageIndex").text()) - 1);
    GetCountryList(pageIndex);
}

function NextPage_Country() {
    var pageIndex = ($("#CountryPaging .pageIndex").text() == $("#CountryPaging .pageCount").text() ? parseInt($("#CountryPaging .pageIndex").text()) : parseInt($("#CountryPaging .pageIndex").text()) + 1);
    GetCountryList(pageIndex);
}

function EndPage_Country() {
    GetCountryList(parseInt($("#CountryPaging .pageCount").text()));
}

function GetCountryEntry(data,idx)
{
	var str = "<tr>";
	str += "<td>" + idx + "</td>";
	str += "<td>" + data.CountryCode + "</td>";
	str += "<td>" + data.Name + "</td>";
	str += "<td>"
	for(j=0;j<data.Regions.length;j++){
		str += data.Regions[j].Name + "&nbsp;&nbsp;";
	}
	str += "</td>";
	str += "<td><input class='btn btn-default btn-xs' type='button' value='地区列表' onclick='showAreaDialog(\""+ data.Id + "\",\""+ data.Name + "\")'/>" +
			"&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialog_Country(\"Edit\",\""+ data.Id + "\",this)' />" +
			"&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""+ data.Id + "\", DelCountry)' /></td>";
	str += "</tr>";
    return str;
}
function GetCountryList(strPageIndex) {
    var strQuery = $("#txt_CountryQuery").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/CountryServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#CountryList").html('');
            if (r == "false") {
                $("#CountryList")
						.append("<tr class='noDataSrc'><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#CountryPaging .dataCount").text(data.total);
            $("#CountryPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#CountryPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
            	str += GetCountryEntry(data.webList[i], i+1);
            }
            $("#CountryList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function showAreaDialog(Id,Name)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
	$('#AreasListDom').show();
	$(obj).parent().parent().children("li").children("a").removeClass("current");
	
    $(obj).addClass("current");
    $("#hid_CountryParam").val("countryId=" + Id +"&countryName="+Name);
    GetAreasList(1);
}

function showDialog_Country(methodName,Id,pos)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#CountryAreasDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/CountryServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_CountryParam").val("methodName=edit&id=" + Id);
                    $("#txt_CountryCode").val(r.CountryCode);
                    $("#txt_CountryName").val(r.Name);
                    getRegionList();
                    var cal=document.getElementsByName('txt_RegionMenu');
                    for(i=0;i<cal.length;i++){
                    	for(j=0;j<r.Regions.length;j++){
                        	if(cal[i].value==r.Regions[j].Id)
                        		cal[i].checked=true;
                    	}
                    }
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_CountryParam").val("methodName=add");
        $("#txt_CountryCode").val("");
        $("#txt_CountryName").val("");
        getRegionList();
    }
}

function showDialog_Region(methodName,Id)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#RegionDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    $("#hid_RegionParam").val("methodName=add");
    $("#txt_RegionCode").val("");
    $("#txt_RegionName").val("");
    getCountryList();
}
function getCountryList()
{
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/CountryServlet?methodName=getCountryList",
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
            	//var data = $.parseJSON(r);
            	var str="";
            	str = "<tr><td width='100' align='right' class='tab_th'>国家：</td><td>";
                for (var i = 0; i < r.webList.length; i++)
                {
                	str +="<input type='checkbox' id='txt_CountryMenu' name='txt_CountryMenu' value='" + r.webList[i].Id + "'>" + r.webList[i].Name + "&nbsp;&nbsp;&nbsp;</input>"
                }
            	str+="</td></tr>";
            	$("#includeCountryList").html(str);
            }
            else{
            	var str="";
            	str = "<tr><td width='100' align='right' class='tab_th'>国家：</td>";
            	str += "<td>无可选国家</td></tr>";
            	$("#includeCountryList").html(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function getRegionList()
{
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/RegionServlet?methodName=getRegionList",
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
            	//var data = $.parseJSON(r);
            	var str="";
            	str = "<tr><td width='100' align='right' class='tab_th'>区域：</td><td>";
                for (var i = 0; i < r.webList.length; i++)
                {
                	str +="<input type='checkbox' id='txt_RegionMenu' name='txt_RegionMenu' value='" + r.webList[i].Id + "'>" + r.webList[i].Name + "&nbsp;&nbsp;&nbsp;</input>"
                }
            	str+="</td></tr>";
            	$("#RegionList").html(str);
            }
            else{
            	var str="";
            	str = "<tr><td width='100' align='right' class='tab_th'>区域：</td>";
            	str += "<td>无可选区域</td></tr>";
            	$("#RegionList").html(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function SaveCountry()
{
	var strCountryCode = $("#txt_CountryCode").val();
	var strCountryName = $("#txt_CountryName").val();
	var strMenu="";
    var cal=document.getElementsByName('txt_RegionMenu');
    for(i=0;i<cal.length;i++)
    	{
    	if(cal[i].checked)
    		
    		strMenu+=cal[i].value+"_";
    	}
    if(strMenu!="")
    {
    	strMenu = strMenu.substring(0, strMenu.length - 1);
    }
    if ($.trim(strCountryCode) == "" || $.trim(strCountryName) == "") {
        alertText("国家代码和名称不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/CountryServlet?methodName="
            					+ getParam("hid_CountryParam", "methodName") + "&id=" + getParam("hid_CountryParam", "id")
                                + "&CountryCode=" + encode(strCountryCode) + "&CountryName=" + encode(strCountryName) +  "&strMenu=" +strMenu+  "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	alertText("保存失败！", 3500);
            }else{
            	alertText("保存成功！", 3500);
            	var data = $.parseJSON(r);
            	UpdateCountry(data);
                $('#CountryAreasDom_Edit').animate({ width: "hide" });
                $("#CountryAreasDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function SaveRegion()
{
	var strRegionCode = $("#txt_RegionCode").val();
	var strRegionName = $("#txt_RegionName").val();
	var strMenu="";
    var cal=document.getElementsByName('txt_CountryMenu');
    for(i=0;i<cal.length;i++)
    	{
    	if(cal[i].checked)
    		
    		strMenu+=cal[i].value+"_";
    	}
    if(strMenu!="")
    {
    	strMenu = strMenu.substring(0, strMenu.length - 1);
    }
    if ($.trim(strRegionCode) == "" || $.trim(strRegionName) == "") {
        alertText("区域代码和名称不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/RegionServlet?methodName="
            					+ getParam("hid_RegionParam", "methodName") + "&id=" + getParam("hid_RegionParam", "id")
                                + "&RegionCode=" + encode(strRegionCode) + "&RegionName=" + encode(strRegionName) +  "&strMenu=" +strMenu+  "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	alertText("保存失败！", 3500);
            }else{
            	alertText("保存成功！", 3500);
            	GetCountryList(1);
                $('#CountryAreasDom_Edit').animate({ width: "hide" });
                $("#CountryAreasDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function DelCountry(id)
{
	$.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/CountryServlet?methodName=del&id=" + id,
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
                GetCountryList(1);
            } else if(r=="error"){
            	alertText("无法删除已有地区的国家！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateCountry(data){
	var str = "";
    if(getParam("hid_CountryParam", "methodName") == "add"){
        str += GetCountryEntry(data, '*');
        $("#CountryList").find('.noDataSrc').remove();
        $("#CountryList").prepend(str);
    }else if(getParam("hid_CountryParam", "methodName") == "edit"){
        var obj =  $("#CountryList").find('.editObject').find('td');
        obj.eq(1).html(data.CountryCode);
        obj.eq(2).html(data.Name);
        for(j=0;j<data.Regions.length;j++){
    		str += data.Regions[j].Name + "&nbsp;&nbsp;";
    	}
        obj.eq(3).html(str);
        $("#CountryList").find('.editObject').removeClass('editObject');
    }
}
