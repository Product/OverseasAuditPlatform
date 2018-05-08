var countries;
function FirstPage_BrandRegion() {
    GetBrandRegionList(1);
}

function PreviousPage_BrandRegion() {
    var pageIndex = (parseInt($("#BrandRegionPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#BrandRegionPaging .pageIndex").text()) - 1);
    GetBrandRegionList(pageIndex);
}

function NextPage_BrandRegion() {
    var pageIndex = ($("#BrandRegionPaging .pageIndex").text() == $("#BrandRegionPaging .pageCount").text() ? parseInt($("#BrandRegionPaging .pageIndex").text()) : parseInt($("#BrandRegionPaging .pageIndex").text()) + 1);
    GetBrandRegionList(pageIndex);
}

function EndPage_BrandRegion() {
    GetBrandRegionList(parseInt($("#BrandRegionPaging .pageCount").text()));
}

function GetBrandRegionEntry(data,idx)
{
	var str = "<tr>";
	str += "<td>" + idx + "</td>";
	str += "<td>" + data.RegionCode + "</td>";
	str += "<td>" + data.Name + "</td>";
	str += "<td><button type='button' class='btn btn-default btn-xs' onclick='showAreaDialog_Region(\""+ data.Id + "\")'>国家列表</button>";
	str += "</tr>";
    return str;
}
function GetBrandRegionList(strPageIndex) {
    var strQuery = $("#txt_BrandRegionQuery").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/RegionServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#BrandRegionList").html('');
            if (r == "false") {
                $("#BrandRegionList")
						.append("<tr class='noDataSrc'><td colspan='4' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            countries = r;
            var data = $.parseJSON(r);
            
            $("#BrandRegionPaging .dataCount").text(data.total);
            $("#BrandRegionPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#BrandRegionPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
            	str += GetBrandRegionEntry(data.webList[i], i+1);
            }
            $("#BrandRegionList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function FirstPage_BrandCountry() {
    GetBrandCountryList(1);
}

function PreviousPage_BrandCountry() {
    var pageIndex = (parseInt($("#BrandCountryPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#BrandCountryPaging .pageIndex").text()) - 1);
    GetBrandCountryList(pageIndex);
}

function NextPage_BrandCountry() {
    var pageIndex = ($("#BrandCountryPaging .pageIndex").text() == $("#BrandCountryPaging .pageCount").text() ? parseInt($("#BrandCountryPaging .pageIndex").text()) : parseInt($("#BrandCountryPaging .pageIndex").text()) + 1);
    GetBrandCountryList(pageIndex);
}

function EndPage_BrandCountry() {
    GetBrandCountryList(parseInt($("#BrandCountryPaging .pageCount").text()));
}

function GetBrandCountryEntry(data,idx)
{
	var str = "<tr>";
	str += "<td><input type='radio' class='BrandCountry' value='"+data.Id+"' name='BrandCountry' id='bc"+data.Id+"'></td>";
	str += "<td><label for='bc"+data.Id+"'>" + data.CountryCode + "</label></td>";
	str += "<td>" + data.Name + "</td>";
	str += "<td><button type='button' class='btn btn-default btn-xs' onclick='showAreaDialog_Area(\""+ data.Id + "\")'>地区列表</button>";
	str += "</tr>";
    return str;
}

function GetBrandCountryList(strPageIndex) {
    var strQuery = $("#txt_BrandCountryQuery").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/CountryServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#BrandCountryList").html('');
            if (r == "false") {
                $("#BrandCountryList")
						.append("<tr class='noDataSrc'><td colspan='4' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#BrandCountryPaging .dataCount").text(data.total);
            $("#BrandCountryPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#BrandCountryPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
            	str += GetBrandCountryEntry(data.webList[i], i+1);
            }
            $("#BrandCountryList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function GetBrandCountryByR() {
	var strQuery = $("#txt_BrandCountryQuery").val();
	var Id=getParam("hid_BrandRegionParam","regionId");
            $("#BrandCountryList").html('');
            var data = $.parseJSON(countries);
            for (var i = 0; i < data.webList.length; i++)
            {
            	if(data.webList[i].Id==Id)
            	{
            		$("#BrandCountryPaging .dataCount").text(data.webList[i].countriesList.length);
                    $("#BrandCountryPaging .pageCount").text(parseInt((parseInt(data.webList[i].countriesList.length) + 9) / 10));
                    $("#BrandCountryPaging .pageIndex").text("1");
                    
                    var str = "";
                    if(strQuery=="")
                    {
                    	for (var j = 0; j < data.webList[i].countriesList.length; j++)
                    	{
                    		str += "<tr>";
                    		str += "<td><input type='radio' class='BrandCountry' value='"+data.webList[i].countriesList[j].Id+"' name='BrandCountry' id='bc"+data.webList[i].countriesList[j].Id+"'></td>";
                    		str += "<td><label for='bc"+data.webList[i].countriesList[j].Id+"'>" + data.webList[i].countriesList[j].CountryCode + "</label></td>";
                    		str += "<td>" + data.webList[i].countriesList[j].Name + "</td>";
                    		str += "<td><button type='button' class='btn btn-default btn-xs' onclick='showAreaDialog_Area(\""+ data.webList[i].countriesList[j].Id + "\")'>地区列表</button>";
                    		str += "</tr>";
                    	}
                    }
                    else
                    {
                    	for (var j = 0; j < data.webList[i].countriesList.length; j++)
                    	{
                    		if(data.webList[i].countriesList[j].CountryCode.indexOf(strQuery)>=0 || data.webList[i].countriesList[j].Name.indexOf(strQuery)>=0)
                    		{
                    			str += "<tr>";
                        		str += "<td><input type='radio' class='BrandCountry' value='"+data.webList[i].countriesList[j].Id+"' name='BrandCountry' id='bc"+data.webList[i].countriesList[j].Id+"'></td>";
                        		str += "<td><label for='bc"+data.webList[i].countriesList[j].Id+"'>" + data.webList[i].countriesList[j].CountryCode + "</label></td>";
                        		str += "<td>" + data.webList[i].countriesList[j].Name + "</td>";
                        		str += "<td><button type='button' class='btn btn-default btn-xs' onclick='showAreaDialog_Area(\""+ data.webList[i].countriesList[j].Id + "\")'>地区列表</button>";
                        		str += "</tr>";
                    		}
                    		
                    	}
                   	}
                	if(str=="")
                	{$("#BrandCountryList")
						.append("<tr class='noDataSrc'><td colspan='4' style='text-align:center;'>无数据！</td></tr>");
                		return false;}
                	else{
                	$("#BrandCountryList").append(str);
                	return true;}
            	}
          	}
}

function FirstPage_BrandArea() {
    GetBrandAreaList(1);
}

function PreviousPage_BrandArea() {
    var pageIndex = (parseInt($("#BrandCountryPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#BrandAreaPaging .pageIndex").text()) - 1);
    GetBrandAreaList(pageIndex);
}

function NextPage_BrandArea() {
    var pageIndex = ($("#BrandAreaPaging .pageIndex").text() == $("#BrandAreaPaging .pageCount").text() ? parseInt($("#BrandAreaPaging .pageIndex").text()) : parseInt($("#BrandAreaPaging .pageIndex").text()) + 1);
    GetBrandAreaList(pageIndex);
}

function EndPage_BrandArea() {
    GetBrandAreaList(parseInt($("#BrandAreaPaging .pageCount").text()));
}

function GetBrandAreaEntry(data,idx)
{
	var str = "<tr>";
	str += "<td><input type='radio' class='BrandArea' value='"+data.Id+"' name='BrandArea' id='ba"+data.Id+"'></td>";
	str += "<td><label for='ba"+data.Id+"'>" + data.AreaCode + "</label></td>";
	str += "<td>" + data.Name + "</td>";
	str += "</tr>";
    return str;
}

function GetBrandAreaList(strPageIndex){
	var strQuery = $("#txt_BrandAreaQuery").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/AreasServlet?methodName=QueryList&countryId="+getParam("hid_BrandCountryParam", "countryId")+ "&strQuery=" + encode(strQuery)
				+ "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#BrandAreaList").html('');
            if (r == "false") {
                $("#BrandAreaList")
						.append("<tr class='noDataSrc'><td colspan='3' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#BrandAreaPaging .dataCount").text(data.total);
            $("#BrandAreaPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#BrandAreaPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
            	str += GetBrandAreaEntry(data.webList[i], i+1);
            }
            $("#BrandAreaList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function showAreaDialog_Region(Id)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
	$('#BrandCountryDom_Edit').show();
	$(obj).parent().parent().children("li").children("a").removeClass("current");
	$(obj).addClass("current");
	$("#hid_BrandRegionParam").val("regionId="+Id);
	GetBrandCountryByR();
}

function showAreaDialog_Area(Id)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
	$('#BrandAreaDom_Edit').show();
	$(obj).parent().parent().children("li").children("a").removeClass("current");
	$(obj).addClass("current");
	$("#hid_BrandCountryParam").val("countryId="+Id);
	GetBrandAreaList(1);
}

function checkBrandAreaRadio()
{
	var tmp = $("input[name='BrandArea']:checked");
    if(tmp.length == 0)
        alertText("没有选中地区！", 3500);
    else{
    	if(getParam("hid_TypeParam", "selectedType") == "brand")
    		{
    		$('#txt_brandcountry').val(getParam("hid_BrandCountryParam","countryId"));
            $('#txt_brandarea').val(tmp.val());
            $('#txt_brandarea').text(tmp.parent().parent().find('label').text());
            $("#BrandAreaDom_Edit").animate({ width: "hide" });
            $('#BrandDom_Edit').show();
    		}
    	else if(getParam("hid_TypeParam", "selectedType") == "website")
    		{
    		$('#txt_websitecountry').val(getParam("hid_BrandCountryParam","countryId"));
            $('#txt_websitearea').val(tmp.val());
            $('#txt_websitearea').text(tmp.parent().parent().find('label').text());
            $("#BrandAreaDom_Edit").animate({ width: "hide" });
            $('#websiteListDom_Edit').show();
    		}
    	else if(getParam("hid_TypeParam", "selectedType") == "product")
		{
		$('#txt_productcountry').val(getParam("hid_BrandCountryParam","countryId"));
        $('#txt_productarea').val(tmp.val());
        $('#txt_productarea').text(tmp.parent().parent().find('label').text());
        $("#BrandAreaDom_Edit").animate({ width: "hide" });
        $('#productDom_Edit').show();
		}
    	
        
    }
}

function checkBrandCountryRadio()
{
	var tmp = $("input[name='BrandCountry']:checked");
    if(tmp.length == 0)
        alertText("没有选中地区！", 3500);
    else{
    	if(getParam("hid_TypeParam", "selectedType") == "brand")
    		{
    		$('#txt_brandarea').val("");
        	$('#txt_brandarea').text("");
            $('#txt_brandcountry').val(tmp.val());
            $('#txt_brandcountry').text(tmp.parent().parent().find('label').text());
            $("#BrandCountryDom_Edit").animate({ width: "hide" });
            $('#BrandDom_Edit').show();
    		}
    	else if(getParam("hid_TypeParam", "selectedType") == "website")
		{
		$('#txt_websitearea').val("");
    	$('#txt_websitearea').text("");
        $('#txt_websitecountry').val(tmp.val());
        $('#txt_websitecountry').text(tmp.parent().parent().find('label').text());
        $("#BrandCountryDom_Edit").animate({ width: "hide" });
        $('#websiteListDom_Edit').show();
		}
    	else if(getParam("hid_TypeParam", "selectedType") == "product")
		{
		$('#txt_productarea').val("");
    	$('#txt_productarea').text("");
        $('#txt_productcountry').val(tmp.val());
        $('#txt_productcountry').text(tmp.parent().parent().find('label').text());
        $("#BrandCountryDom_Edit").animate({ width: "hide" });
        $('#productDom_Edit').show();
		}
    	
    	
      
    }
}

//add by lxt
function ReturnPrivateParam(obj)
{
	var strPreviousParams = $("#hid_PreviousParam").val();
	
	$(obj).attr("rel",strPreviousParams);
	toUrl(obj);
	}