function FirstPage_Brand() {
    GetBrandList(1,"");
}

function PreviousPage_Brand() {
    var pageIndex = (parseInt($("#BrandPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#BrandPaging .pageIndex").text()) - 1);
    GetBrandList(pageIndex,"");
}

function NextPage_Brand() {
    var pageIndex = ($("#BrandPaging .pageIndex").text() == $("#BrandPaging .pageCount").text() ? parseInt($("#BrandPaging .pageIndex").text()) : parseInt($("#BrandPaging .pageIndex").text()) + 1);
    GetBrandList(pageIndex,"");
}

function EndPage_Brand() {
    GetBrandList(parseInt($("#BrandPaging .pageCount").text()),"");
}

function GetBrandEntry(data,idx)
{
	var str = "<tr>";
	str += "<td>" + idx + "</td>";
	str += "<td>" + data.Name_CN + "</td>";
	str += "<td>" + data.Name_EN + "</td>";
	str += "<td>" + data.Name_other + "</td>";
	str += "<td>" + data.Popularity + "</td>";
	str += "<td>" + data.MarketShare + "</td>";
	str += "<td>" + data.CountryName + " " + data.AreaName + "</td>";
	str += "<td>" + data.CreateTime + "</td>";
	str += "<td><button class='btn btn-default btn-xs' type='button' onclick='showDialog_Brand(\"Edit\",\""+ data.Id + "\",this)' >编 辑</button>" +
			"&nbsp;<button  type='button' class='btn btn-default btn-xs' onclick='confirmShow(\""+ data.Id + "\", DelBrand)'  >删 除</button></td>";
	str += "</tr>";
    return str;
}
function GetBrandList(strPageIndex) {
    var strQuery = $("#txt_BrandQuery").val();
    var strStraTime = $("#txt_BrandBeginDate").val();
    var strEndTime = $("#txt_BrandEndDate").val();
    
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/BrandServlet?methodName=QueryList&regionId="+getParam("hid_BrandRegionParam", "regionId")+ "&strQuery=" + encode(strQuery)
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#BrandList").html('');
            if (r == "false") {
                $("#BrandList")
						.append("<tr class='noDataSrc'><td colspan='9' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#BrandPaging .dataCount").text(data.total);
            $("#BrandPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#BrandPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
            	str += GetBrandEntry(data.webList[i], i+1);
            }
            $("#BrandList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function showDialog_Brand(methodName,Id,pos)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#BrandDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/BrandServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_BrandParam").val("methodName=edit&id=" + Id);
                    $("#txt_BrandName_CN").val(r.Name_CN);
                    $("#txt_BrandName_EN").val(r.Name_EN);
                    $("#txt_BrandName_OTHER").val(r.Name_other);
                    $("#txt_BrandPopularity").val(r.Popularity);
                    $("#txt_BrandPopularity").val(r.Popularity);
                    $("#txt_MarketShare").val(r.MarketShare);
                    $('#addBrandArea').show();
                    $('#txt_brandcountry').show();
                    $('#txt_brandcountry').text(r.CountryName+" ");
                    if(r.AreaId!="")
                    {
                    	$('#txt_brandarea').show();
                        $("#txt_brandarea").val(r.AreaId);
                        $('#txt_brandarea').text(r.AreaName);
                    }
                    
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_BrandParam").val("methodName=add");
        $('#addBrandArea').show();
        $('#txt_brandarea').val("");
        $('#txt_brandarea').text("");
        $('#txt_brandcountry').val("");
        $('#txt_brandcountry').text("");
        $("#txt_BrandName_CN").val("");
        $("#txt_BrandName_EN").val("");
        $("#txt_BrandName_OTHER").val("");
        $("#txt_BrandPopularity").val("");
        $("#txt_MarketShare").val("");
    }
}

function SaveBrand()
{
	var strBrandName_CN = $("#txt_BrandName_CN").val();
	var strBrandName_EN = $("#txt_BrandName_EN").val();
	var strBrandName_OTHER= $("#txt_BrandName_OTHER").val();
	var strBrandPopularity = $("#txt_BrandPopularity").val();
    var strMarketShare = $("#txt_MarketShare").val();
	var strAreaId = $("#txt_brandarea").val();
	var strCountryId = $("#txt_brandcountry").val();
	if ($.trim(strBrandName_CN) == "" &&　$.trim(strBrandName_EN) == "" &&　$.trim(strBrandName_OTHER) == "") {
        alertText("至少有一个名称不能为空！", 3500);
        return false;
    }
	if($.trim(strCountryId) == ""){
		alertText("所属地区不能为空！", 3500);
        return false;
	}
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/BrandServlet?methodName="
            					+ getParam("hid_BrandParam", "methodName") + "&id=" + getParam("hid_BrandParam", "id")
                                + "&name_cn=" + encode(strBrandName_CN) + "&name_en="
                                + encode(strBrandName_EN) +  "&name_other=" +encode(strBrandName_OTHER) 
                                + "&popularity=" +strBrandPopularity + "&marketShare=" + strMarketShare +  "&areaId=" + strAreaId + "&countryId=" +strCountryId +"&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	alertText("保存失败！", 3500);
            }else{
            	alertText("保存成功！", 3500);
            	var data = $.parseJSON(r);
            	UpdateBrand(data);
                $('#BrandDom_Edit').animate({ width: "hide" });
                $("#BrandsDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelBrand(Id)
{
	$.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/BrandServlet?methodName=del&id=" + Id,
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
                GetBrandList(1);
            } else if(r=="error"){
            	alertText("无法删除！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function UpdateBrand(data){
	var str = "";
    if(getParam("hid_BrandParam", "methodName") == "add"){
        str += GetBrandEntry(data, '*');
        $("#BrandList").find('.noDataSrc').remove();
        $("#BrandList").prepend(str);
    }else if(getParam("hid_BrandParam", "methodName") == "edit"){
        var obj =  $("#BrandList").find('.editObject').find('td');
        obj.eq(1).html(data.Name_CN);
        obj.eq(2).html(data.Name_EN);
        obj.eq(3).html(data.Name_other);
        obj.eq(4).html(data.CountryName+" "+data.AreaName);
        $("#BrandList").find('.editObject').removeClass('editObject');
    }
}

function GetBrandArea()
{
	$("#hid_PreviousParam").val("BrandDom_Edit");
	$("#hid_TypeParam").val("selectedType=brand");//表明是在为brand选择所属地区，add by lxt
	$("#BrandDom_Edit").animate({ width: "hide" });
    $('#BrandRegionDom_Edit').show();
    $("#txt_BrandCountryQuery").val("");
    $("#BrandRegionList").html('');
	
}