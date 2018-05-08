function FirstPage_Areas() {
    GetAreasList(1);
}

function PreviousPage_Areas() {
    var pageIndex = (parseInt($("#AreasPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#AreasPaging .pageIndex").text()) - 1);
    GetAreasList(pageIndex);
}

function NextPage_Areas() {
    var pageIndex = ($("#AreasPaging .pageIndex").text() == $("#AreasPaging .pageCount").text() ? parseInt($("#AreasPaging .pageIndex").text()) : parseInt($("#AreasPaging .pageIndex").text()) + 1);
    GetAreasList(pageIndex);
}

function EndPage_Areas() {
    GetAreasList(parseInt($("#AreasPaging .pageCount").text()));
}

function GetAreasEntry(data,idx)
{
	var str = "<tr>";
	str += "<td>" + idx + "</td>";
	str += "<td>" + data.AreaCode + "</td>";
	str += "<td>" + data.Name + "</td>";
	str += "<td>" + data.Country + "</td>";
	str += "<td>&nbsp;<button class='btn btn-default btn-xs' onclick='showDialog_Area(\"Edit\",\""+ data.Id + "\",this)' >编 辑</button>" +
			"&nbsp;<button class='btn btn-default btn-xs' onclick='confirmShow(\""+ data.Id + "\", DelArea)' >删 除</button></td>";
	str += "</tr>";
    return str;
}
function GetAreasList(strPageIndex) {
    var strQuery = $("#txt_AreasQuery").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/AreasServlet?methodName=QueryList&countryId="+getParam("hid_CountryParam", "countryId")+ "&strQuery=" + encode(strQuery)
				+ "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#AreasList").html('');
            if (r == "false") {
                $("#AreasList")
						.append("<tr class='noDataSrc'><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#AreasPaging .dataCount").text(data.total);
            $("#AreasPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#AreasPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
            	str += GetAreasEntry(data.webList[i], i+1);
            }
            $("#AreasList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function showDialog_Area(methodName,Id,pos)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#AreasDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/AreasServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_AreasParam").val("methodName=edit&id=" + Id);
                    $("#txt_AreasCode").val(r.AreaCode);
                    $("#txt_AreasName").val(r.Name);
                    $("#txt_AreasCountry").val(getParam("hid_CountryParam", "countryName"));
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_AreasParam").val("methodName=add");
        $("#txt_AreasCode").val("");
        $("#txt_AreasName").val("");
        $("#txt_AreasCountry").val(getParam("hid_CountryParam", "countryName"));
    }
}

function SaveAreas()
{
	var strAreasCode = $("#txt_AreasCode").val();
	var strAreasName = $("#txt_AreasName").val();
	
    if ($.trim(strAreasCode) == "" || $.trim(strAreasName) == "") {
        alertText("地区代码和名称不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/AreasServlet?methodName="
            					+ getParam("hid_AreasParam", "methodName") + "&id=" + getParam("hid_AreasParam", "id")
                                + "&countryId="+getParam("hid_CountryParam", "countryId") + "&AreasCode=" + encode(strAreasCode) + "&AreasName=" + encode(strAreasName) + "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	alertText("保存失败！", 3500);
            } else {
            	alertText("保存成功！", 3500);
            	var data = $.parseJSON(r);
            	UpdateAreas(data);
                $('#AreasDom_Edit').animate({ width: "hide" });
                $("#AreasListDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelArea(id)
{
	$.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/AreasServlet?methodName=del&id=" + id,
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
                GetAreasList(1);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateAreas(data)
{
	var str = "";
    if(getParam("hid_AreasParam", "methodName") == "add"){
        str += GetAreasEntry(data, '*');
        $("#AreasList").find('.noDataSrc').remove();
        $("#AreasList").prepend(str);
    }else if(getParam("hid_AreasParam", "methodName") == "edit"){
        var obj =  $("#AreasList").find('.editObject').find('td');
        obj.eq(1).html(data.AreasCode);
        obj.eq(2).html(data.Name);
        obj.eq(3).html(data.Country);
        $("#AreasList").find('.editObject').removeClass('editObject');
    }
}
