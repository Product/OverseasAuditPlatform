function FirstPage_ProductDefinition() {
    GetProductDefinitionList(1);
}

function PreviousPage_ProductDefinition() {
    var pageIndex = (parseInt($("#ProductDefinitionPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#ProductDefinitionPaging .pageIndex").text()) - 1);
    GetProductDefinitionList(pageIndex,"");
}

function NextPage_ProductDefinition() {
    var pageIndex = ($("#ProductDefinitionPaging .pageIndex").text() == $("#ProductDefinitionPaging .pageCount").text() ? parseInt($("#ProductDefinitionPaging .pageIndex").text()) : parseInt($("#ProductDefinitionPaging .pageIndex").text()) + 1);
    GetProductDefinitionList(pageIndex);
}

function EndPage_ProductDefinition() {
    GetProductDefinitionList(parseInt($("#ProductDefinitionPaging .pageCount").text()),"");
}

function GetProductDefinitionEntry(data,idx)
{
	var str = "<tr>";
	str += "<td>" + idx + "</td>";
	str += "<td>" + data.Name_CN + "</td>";
	str += "<td>" + data.Name_EN + "</td>"
	str += "<td>" + data.Name_other + "</td>";
	str += "<td>" + data.ProductDefinitionFeature_one + "</td>";
	str += "<td>" + data.ProductDefinitionFeature_two + "</td>";
	str += "<td>" + data.ProductDefinitionFeature_three + "</td>";
	str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialog_ProductDefinition(\"Edit\",\""+ data.Id + "\",this)' />" +
			"&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""+ data.Id + "\", DelProductDefinition)' /></td>";
	str += "</tr>";
    return str;
}

function GetProductDefinitionList(strPageIndex) {
    var strQuery = $("#txt_ProductDefinitionQuery").val();
    var strStraTime = $("#txt_ProductDefinitionBeginDate").val();
    var strEndTime = $("#txt_ProductDefinitionEndDate").val();
    
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ProductDefinitionServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#ProductDefinitionList").html('');
            if (r == "false") {
                $("#ProductDefinitionList")
						.append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#ProductDefinitionPaging .dataCount").text(data.total);
            $("#ProductDefinitionPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#ProductDefinitionPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
            	str += GetProductDefinitionEntry(data.webList[i], i+1);
            }
            $("#ProductDefinitionList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function showDialog_ProductDefinition(methodName,Id,pos)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#ProductDefinitionDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    ProductDefMaterialType();
    $("#ProductDef_Materials_Display").html('');


    $("#txt_ProductDefinitionRegion").html("<option value='0'>请选择</option>");
	$("#txt_ProductDefinitionCountry").html("<option value='0'>请选择</option>");
	$("#txt_ProductDefinitionArea").html("<option value='0'>请选择</option>");
	
    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/ProductDefinitionServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_ProductDefinitionParam").val("methodName=edit&id=" + Id);
                    $("#txt_ProductDefinitionName_CN").val(r.Name_CN);
                    $("#txt_ProductDefinitionName_EN").val(r.Name_EN);
                    $("#txt_ProductDefinitionName_OTHER").val(r.Name_other);
                    $("#txt_ProductDefinitionFeature_one").val(r.ProductDefinitionFeature_one);
                    $("#txt_ProductDefinitionFeature_two").val(r.ProductDefinitionFeature_two);
                    $("#txt_ProductDefinitionFeature_three").val(r.ProductDefinitionFeature_three);
                    $("#txt_ProductDefinitionFeature_four").val(r.ProductDefinitionFeature_four);
                    $("#txt_ProductDefinitionFeature_five").val(r.ProductDefinitionFeature_five);
                    $("#txt_ProductDefinitionFeature_six").val(r.ProductDefinitionFeature_six);
                    $('#addProductDefinitionBrand').show();
                    $('#txt_ProductDefinitionBrand').show();
                    $("#txt_ProductDefinitionBrand").val(r.BrandId);
                    $('#txt_ProductDefinitionBrand').text(r.BrandName);
                    $('#addProductDefinitionProductType').show();
                    $('#txt_ProductDefinitionProductType').show();
                    $("#txt_ProductDefinitionProductType").val(r.ProductTypeId);
                    $('#txt_ProductDefinitionProductType').text(r.ProductTypeName);
                    $('#txt_ProductDefinitionManufactor').val(r.Manufactor);
                    $("#txt_ProductDefinitionCountry").html('<option value="' + r.CountryId + '" >' + r.CountryName + '</option>');
                    if(r.areaName!="")
                    	$("#txt_ProductDefinitionArea").html('<option value="' + r.AreaId + '" >' + r.AreaName + '</option>');
                    //add By Yekai
                    //加载已关联的原料
                    $("#ProductDef_Materials_Show").show();
                    for (var j = 0; j < r.materials.length; j++){
                    	var str = "<span class='choices' value='" + r.materials[j].materialId + "<>' onclick='delete_ProductDef_Material(this)'>" + r.materials[j].materialName + "<a class='timeA'>&times</a></span><br/>";
                    	$("#ProductDef_Materials_Display").append(str);
                    }
                    //end
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_ProductDefinitionParam").val("methodName=add");
        $('#addProductDefinitionBrand').show();
        $('#txt_ProductDefinitionBrand').val("");
        $('#txt_ProductDefinitionBrand').text("");
        $('#addProductDefinitionProductType').show();
        $('#txt_ProductDefinitionProductType').val("");
        $('#txt_ProductDefinitionProductType').text("");
        $("#txt_ProductDefinitionName_CN").val("");
        $("#txt_ProductDefinitionName_EN").val("");
        $("#txt_ProductDefinitionName_OTHER").val("");
        $("#txt_ProductDefinitionFeature_one").val("");
        $("#txt_ProductDefinitionFeature_two").val("");
        $("#txt_ProductDefinitionFeature_three").val("");
        $("#txt_ProductDefinitionFeature_four").val("");
        $("#txt_ProductDefinitionFeature_five").val("");
        $("#txt_ProductDefinitionFeature_six").val("");
        $('#txt_ProductDefinitionManufactor').val("");
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/RegionServlet?methodName=getRegionList",
        dataType: "json",
        async: false,
        success: function (r) {

        	$.each(r.webList, function(key, val) {
                $("#txt_ProductDefinitionRegion").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
            });
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function GetProductDefinitionBrand()
{
	//$('#addProductDefinitionBrand').hide();
	$("#ProductDefinitionDom_Edit").animate({ width: "hide" });
    $('#ProductDefinitionBrandDom_Edit').show();
    $("#txt_ProductDefinitionBrand").val("");
    $("#ProductDefinitionBrandList").html('');
}

function GetProductDefinitionProductType()
{
	$("#ProductDefinitionDom_Edit").animate({ width: "hide" });
    $('#ProductDefinitionProductTypeDom_Edit').show();
    $("#txt_ProductDefinitionProductType").val("");
    $("#ProductDefinitionProductTypeList").html('');
}


function SaveProductDefinition(){
	var strProductDefinitionName_CN = $("#txt_ProductDefinitionName_CN").val();
	var strProductDefinitionName_EN = $("#txt_ProductDefinitionName_EN").val();
	var strProductDefinitionName_OTHER= $("#txt_ProductDefinitionName_OTHER").val();
	var strProductDefinitionFeature_one = $("#txt_ProductDefinitionFeature_one").val();
	var strProductDefinitionFeature_two = $("#txt_ProductDefinitionFeature_two").val();
	var strProductDefinitionFeature_three = $("#txt_ProductDefinitionFeature_three").val();
	var strProductDefinitionFeature_four = $("#txt_ProductDefinitionFeature_four").val();
	var strProductDefinitionFeature_five = $("#txt_ProductDefinitionFeature_five").val();
	var strProductDefinitionFeature_six = $("#txt_ProductDefinitionFeature_six").val();
	var strProductDefinitionBrandId = $("#txt_ProductDefinitionBrand").val();
	var strProductDefinitionProductTypeId = $("#txt_ProductDefinitionProductType").val();
	var strProductDefinitionManufactor = $("#txt_ProductDefinitionManufactor").val();
	var strProductDefinitionCountryId = $("#txt_ProductDefinitionCountry").val();
	var strProductDefinitionAreaId = $("#txt_ProductDefinitionArea").val();
	if ($.trim(strProductDefinitionName_CN) == "" &&　$.trim(strProductDefinitionName_EN) == "" &&　$.trim(strProductDefinitionName_OTHER) == "") {
        alertText("至少有一个名称不能为空！", 3500);
        return false;
    }
	if($.trim(strProductDefinitionBrandId) == ""){
		alertText("所属品牌不能为空！", 3500);
        return false;
	}
	if($.trim(strProductDefinitionProductTypeId) == ""){
		alertText("所属类型不能为空！", 3500);
        return false;
	}
//	if($.trim(strProductDefinitionCountryId) == "0"){
//		alertText("必须选择国家！", 3500);
//        return false;
//	}
	//add By YeKai
	var strProductDefinitionMaterials = ""
	$("#ProductDef_Materials_Display span").each(function () {
		strProductDefinitionMaterials += $(this).attr("value");
		  });
		if (strProductDefinitionMaterials != "") {
			strProductDefinitionMaterials = strProductDefinitionMaterials.substring(0, strProductDefinitionMaterials.length - 2);
		 }
	 var strProductDefinitionMaterialsLength = $("#ProductDef_Materials_Display").children("span").length;
    //end
	
	
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/ProductDefinitionServlet?methodName="
            					+ getParam("hid_ProductDefinitionParam", "methodName") + "&id=" + getParam("hid_ProductDefinitionParam", "id")
                                + "&name_cn=" + encode(strProductDefinitionName_CN) + "&name_en="+ encode(strProductDefinitionName_EN) +  "&name_other=" 
                                + encode(strProductDefinitionName_OTHER) +  "&brandId=" + strProductDefinitionBrandId + "&producttypeId=" 
                                + strProductDefinitionProductTypeId + "&materialChoices=" + strProductDefinitionMaterials + "&choicesLength="
                                + strProductDefinitionMaterialsLength + "&ProductDefinitionFeature_one=" + encode(strProductDefinitionFeature_one) + "&ProductDefinitionFeature_two="
                                + encode(strProductDefinitionFeature_two) + "&ProductDefinitionFeature_three=" + encode(strProductDefinitionFeature_three) + "&userId=" + getCookie("")
                                + "&ProductDefinitionFeature_four=" + encode(strProductDefinitionFeature_four) + "&ProductDefinitionFeature_five=" + encode(strProductDefinitionFeature_five)
                                + "&ProductDefinitionFeature_six=" + encode(strProductDefinitionFeature_six)
                                + "&ProductDefinitionManufactor=" + encode(strProductDefinitionManufactor) + "&ProductDefinitionCountryId=" + strProductDefinitionCountryId
                                + "&ProductDefinitionAreaId=" + strProductDefinitionAreaId,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	alertText("保存失败！", 3500);
            }else{
            	alertText("保存成功！", 3500);
            	var data = $.parseJSON(r);
            	UpdateProductDefinition(data);
				//GetProductDefinitionList(1);
                $('#ProductDefinitionDom_Edit').animate({ width: "hide" });
                $("#ProductDefinitionDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelProductDefinition(Id)
{
	$.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ProductDefinitionServlet?methodName=del&id=" + Id,
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
                GetProductDefinitionList(1);
            } else if(r=="error"){
            	alertText("无法删除！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateProductDefinition(data){
	var str = "";
    if(getParam("hid_ProductDefinitionParam", "methodName") == "add"){
        str += GetProductDefinitionEntry(data, '*');
        $("#ProductDefinitionList").find('.noDataSrc').remove();
        $("#ProductDefinitionList").prepend(str);
    }else if(getParam("hid_ProductDefinitionParam", "methodName") == "edit"){
        var obj =  $("#ProductDefinitionList").find('.editObject').find('td');
        obj.eq(1).html(data.Name_CN);
        obj.eq(2).html(data.Name_EN);
        obj.eq(3).html(data.Name_other);
        obj.eq(4).html(data.ProductDefinitionFeature_one);
        obj.eq(5).html(data.ProductDefinitionFeature_two);
		obj.eq(6).html(data.ProductDefinitionFeature_three);
        $("#ProductDefinitionList").find('.editObject').removeClass('editObject');
    }
}

//add By YeKai
$(function () {   
	 $("#ProductDefMaterialSure").click(function () {
	        // to get the chioces
	        //获得三个选择框的文本内容和对应Id
	        var materialObj = document.getElementById('ProductDef_Materials_Sel');
	        var materialChoice = materialObj.options[materialObj.selectedIndex].text;
	        var materialChoiceId = $("#ProductDef_Materials_Sel").val();
	        if (materialChoice == "请选择") {
	            return;
	        }
	        // to show chioces
	        $("#ProductDef_Materials_Show").show();

	        var str = "<span class='choices'  value='" + materialChoiceId + "<>' onclick='delete_ProductDef_Material(this)'>" + materialChoice + "&nbsp;<a class='timeA'>&times</a></span>";
	        $("#ProductDef_Materials_Display").append(str);
	});
	//当第一级菜单变化时，加载第二级菜单
    $("#ProductDef_MaterialType_Sel").change(
		function () {
			$("#ProductDef_Materials_Sel").html("<option value='0'>请选择</option>");
			var materialTypeId=$("#ProductDef_MaterialType_Sel option:selected").val();
		    var str = "";
		    $.ajax({
				type : "post",
				url : projectLocation + "servlet/RulesServlet?methodName=getMaterial&materialTypeId="+materialTypeId,
				dataType : "text",
				async : false,
				success : function(r) {
					if (r != "false") {
						var data = $.parseJSON(r);
			            for (var i = 0; i < data.webList.length; i++) {
							str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
						}
					}
					else if (r == "sessionOut") {
		                doLogout();
		            }
					else{
						alert("加载失败！");
					}
				},
				error : function(e) {
					alert(e.responseText);
				}
			});
		    $("#ProductDef_Materials_Sel").append(str);
		});
	});

function ProductDefMaterialType(){
	$("#ProductDef_MaterialType_Sel").html("<option value='0'>所有原料类型</option>");
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/MaterialTypeServlet?methodName=getMaterialTypeList",
		dataType : "text",
		async : false,
		success : function(r) {
			if (r != "false") {
				var data = $.parseJSON(r);
				var str = "";
	            for (var i = 0; i < data.webList.length; i++) {
					str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
				}
				$("#ProductDef_MaterialType_Sel").append(str);
			}
			else if (r == "sessionOut") {
                doLogout();
            }
			else{
				alert("加载失败！");
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
	$("#ProductDef_Materials_Sel").html("<option value='0'>请选择</option>");
	var str = "";
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/RulesServlet?methodName=getMaterial&materialTypeId="+ 0,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r != "false") {
				var data = $.parseJSON(r);
	            for (var i = 0; i < data.webList.length; i++) {
					str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
				}
			}
			else if (r == "sessionOut") {
                doLogout();
            }
			else{
				alert("加载失败！");
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
	$("#ProductDef_Materials_Sel").append(str);
}

//delete the selected 原料描述  
function delete_ProductDef_Material(obj){
	// delete current span
	$(obj).remove();
	// if current content is null,to hide the 原料描述
    if ($("#ProductDef_Materials_Display").children("span").length <= 0)
    {
    	$("#ProductDef_Materials_Show").hide();
    }
}

//三级联动，显示国家
function selectProductDefCountry(){
	var regionId=$("#txt_ProductDefinitionRegion option:selected").val();
	
	$("#txt_ProductDefinitionCountry").html("<option value='0'>请选择</option>");
	$("#txt_ProductDefinitionArea").html("<option value='0'>请选择</option>");
	
	  $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/CountryServlet?methodName=QueryList&strQuery=&strPageIndex=1&strPageCount=300&regionId="+regionId,
	        dataType: "json",
	        async: false,
	        success: function (r) {
	        	$.each(r.webList, function(key, val) {
	                $("#txt_ProductDefinitionCountry").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
	            });
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
}

//三级联动，显示省、州
function selectProductDefArea(){
	var countryId=$("#txt_ProductDefinitionCountry option:selected").val();
	
	$("#txt_ProductDefinitionArea").html("<option value='0'>请选择</option>");
	
	  $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/AreasServlet?methodName=QueryList&strQuery=&strPageIndex=1&strPageCount=100&countryId="+countryId,
	        dataType: "json",
	        async: false,
	        success: function (r) {
	        	$.each(r.webList, function(key, val) {
	                $("#txt_ProductDefinitionArea").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
	            });
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
}
//end