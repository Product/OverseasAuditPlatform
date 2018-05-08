$(function(){
	 $("#op_first_type").change(
				function(){
					$("#op_second_type").html('');
					var str = "";
					str +=  "<option value='0'>" + "请选择" + "</option>";
					$("#op_third_type").html('');
					var str1 = "";
					str1 +=  "<option value='0'>" + "请选择" + "</option>";
					var firVal = $(this).val();
					
					if(firVal!="0")
					{
						$.ajax({
							type : "post",
							url : "./servlet/ProductTypeServlet?methodName=sel&id=" + firVal,
							dataType : "text",
							async : false,
							success : function(r) {
								 
								if (r != "false") {
									var data = $.parseJSON(r);
						            for (var i = 0; i < data.webList.length; i++) {
										str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
									}
									
								}
								else if(r =="sessionOut"){
					            	doLogout();
					            	
					            }
							},
							error : function(e) {
								alert(e.responseText);
							}
						});
					}
					$("#op_second_type").append(str);
					$("#op_third_type").append(str1);
			});
	 $("#op_second_type").change(
				function(){
					$("#op_third_type").html('');
					var str = "";
					str +=  "<option value='0'>" + "请选择" + "</option>";
					var secVal = $(this).val();
					if(secVal!="0")
					{
					
							$.ajax({
						type : "post",
						url : "./servlet/ProductTypeServlet?methodName=sel&id=" + secVal,
						dataType : "text",
						async : false,
						success : function(r) {
							 
							if (r != "false") {
								var data = $.parseJSON(r);
								
					            for (var i = 0; i < data.webList.length; i++) {
									str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
								}
							
							}
							else if(r =="sessionOut"){
				            	doLogout();
				            	
				            }
						},
						error : function(e) {
							alert(e.responseText);
						}
					});
				
			}
				$("#op_third_type").append(str);
				});
	
});
function PFirstPage() {
	GetProductList(1);
}

function PPreviousPage() {
	var pageIndex = (parseInt($("#ProductPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#ProductPaging .pageIndex").text()) - 1);
	GetProductList(pageIndex);
}

function PNextPage() {
	var pageIndex = ($("#ProductPaging .pageIndex").text() == $("#ProductPaging .pageCount").text() ? parseInt($("#ProductPaging .pageIndex").text()) : parseInt($("#ProductPaging .pageIndex").text()) + 1);
	GetProductList(pageIndex);
}

function PEndPage() {
	GetProductList(parseInt($("#ProductPaging .pageCount").text()));
}
/*获取商品表项*/
function GetProductEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.Location + "</td>";
    str += "<td>" + data.Price + "</td>";
    str += "<td>" + data.WebsiteName + "</td>";
    str += "<td>"
			+ data.CreateTime.substring(0, 19)
			+ "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showPLDialog(\"edit\",\""
			+ data.Id
			+ "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showPLDialog(\"view\",\""
			+ data.Id + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='配料' onclick='showProductMaterial(\""
			+ data.Id + "\")'&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
			+ data.Id + "\",DelProduct)' /></td>";
    
    str += "</tr>";
    return str;
}
/*
函数名：GetProductList
作用：获取商品列表
*/
function GetProductList(strPageIndex) {
	var strQuery = $("#txt_ProductQuery").val();
	var strStraTime = $("#txt_ProductBeginDate").val();
	var strEndTime = $("#txt_ProductEndDate").val();
	

	$.ajax({
			type : "post",
			url : projectLocation + "servlet/ProductServlet?methodName=QueryList&strQuery="
					+ encode(strQuery) + "&strStraTime=" + strStraTime
					+ "&strEndTime=" + strEndTime + "&strPageIndex="
					+ strPageIndex + "&strPageCount=10",
			dataType : "text",
			async : false,
			success : function(r) {
				$("#ProductList").html('');
				if (r == "false") {
					$("#ProductList").append("<tr class='noDataSrc'><td colspan='7' style='text-align:center;'>无数据！</td></tr>");
					return false;
					
					
				}
				else if(r =="sessionOut"){
	            	doLogout();
	            	
	            }
				r =r.replace(/\t/g,"");
				r =r.replace(/\n/g,"");
				var data = $.parseJSON(r);
				//var data = eval('(' + r + ')');
				  
				$("#ProductPaging .dataCount").text(data.total);
				$("#ProductPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
				$("#ProductPaging .pageIndex").text(strPageIndex);
				var str = "";
				var j = 0;
				 for (var i = 0; i < data.proLList.length; i++) {
					 str += GetProductEntry(data.proLList[i], i+1);
					
				}
				$("#ProductList").append(str);
			},
			error : function(e) {
				alert(e.responseText);
			}
		});

}
/*
函数名：DelProduct
作用：删除商品
参数：id（被删除的商品id）
*/
function DelProduct(id) {

		$.ajax({
			type : "post",
			url : projectLocation
					+ "servlet/ProductServlet?methodName=del&id=" + id,
			dataType : "text",
			async : false,
			success : function(r) {
				  if (r == "true") {
	                    alertText("删除成功！", 3500);
					GetProductList(1);
				}
				  else if(r =="sessionOut"){
		            	doLogout();
		            	
		            }
				  else if(r == "false")
	           	   {
	           	   alertText("删除失败！", 3500);
	           	   }
			},
			error : function(e) {
				alert(e.responseText);
			}
		});
	
}
/*
函数名：InitProductStyle；
作用：获取商品类型
*/

/*
函数名：InitWebsite；
作用：获取网站
*/
function InitWebsite(){
	
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/ProductServlet?methodName=getWebsite",
		dataType : "text",
		async : false,
		success : function(r) {
			 $("#op_website").html('');
			if (r != "false") {
				var data = $.parseJSON(r);
				var str = "";
	            for (var i = 0; i < data.Website.length; i++) {
					str += "<option value='" + data.Website[i].Id + "'>" + data.Website[i].Name + "</option>";
				}
				$("#op_website").append(str);
			}else if(r =="sessionOut"){
            	doLogout();
            	
            }
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}
//加载产品列表
function initProductDefinition(){
	$("#op_productDefinition").html('');
	var str = "";
	str +=  "<option value='0'>" + "请选择" + "</option>";
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/ProductDefinitionServlet?methodName=getProDefinitionList",
		dataType : "text",
		async : false,
		success : function(r) {
			
			if (r != "false") {
				var data = $.parseJSON(r);
	            for (var i = 0; i < data.proList.length; i++) {
					str += "<option value='" + data.proList[i].Id + "'>" + data.proList[i].Name + "</option>";
				}
				
			}else if(r =="sessionOut"){
            	doLogout();
            	
            }
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
	$("#op_productDefinition").append(str);
}
/*
函数名：showPLDialog
作用：控制对于特定商品的查看，编辑和添加
参数：id（被操作的王炸id）
methodName：操作名称参数
pos：操作位置
*/

function showPLDialog(methodName, Id, pos) {
	if(methodName == "view")//详情查看
		{
		
		$("#iframeMain .wrap").animate({ width: "hide" });
	    $('#productDom_view').show();	
	    
	    $(obj).parent().parent().children("li").children("a").removeClass("current");
	    $(obj).addClass("current");
	    $.ajax({
            type: "post",
            url: projectLocation + "servlet/ProductServlet?methodName=view&id=" + Id,
            dataType: "text",
            async: false,
            success: function (r) {
            	
                if (r != "false") {
                	var data = $.parseJSON(r);
                    $("#hid_productParam").val("methodName=view&id=" + Id);
                    $("#txt_DPName").val(data.Name);
                    $("#txt_DPRemark").val(data.Remark);
                    $("#txt_DPBrand").val(data.Brand);
                    $("#txt_DPPrice").val(data.Price);
                    $("#txt_DPFreight").val(data.Freight);
                    $("#txt_DPLocation").val(data.Location);
                    $("#txt_DPsuitableAge").val(data.SuitableAge);
                    $("#txt_DPWebsite").val(data.WebsiteName);
                    $("#txt_DPCustoms").val(data.CustomsDuty);
                    $("#txt_DPStandard").val(data.Standard);
                
                    $("#txt_DPPurchaseDate").val(data.PurchaseTime);
                    $("#txt_DPPicture").val("<img src='" +data.Picture + "'/>");
                    $("#txt_DPOnlineDate").val(data.OnlineTime);
                    $("#txt_DPEx").val(data.ExpirationDate);
                    $("#txt_DPProducingarea").val(data.ProducingArea);
                    $("#txt_DPDefinition").val(data.ProDefName);
                    $("#txt_DPSales").val(data.Sales);
                    $("#txt_DPCountry").val(data.CountryName);
                    $("#txt_DPArea").val(data.AreaName);
                    $("#txt_DPFirstType").val(data.FirstTypeName);
                    $("#txt_DPSecondType").val(data.SecondTypeName);
                    $("#txt_DPThirdType").val(data.ThirdTypeName);
                }
                else if(r =="sessionOut"){
                	doLogout();
                	
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
	  
	    
		}
	else
	{//编辑
		$("#iframeMain .wrap").animate({ width: "hide" });
		$('#productDom_Edit').show();
		$(obj).parent().parent().children("li").children("a").removeClass("current");
		$(obj).addClass("current");
		    
		//InitWebsite();
		getFirstType();
		initProductDefinition();
		
		if (methodName != "add") {
			$.ajax({
				type: "post",
				url: projectLocation + "servlet/ProductServlet?methodName=init&id=" + Id,
				dataType: "text",
				async: false,
				success: function (r) {
					if (r != "false") {
						var data = $.parseJSON(r);
						$("#hid_productParam").val("methodName=edit&id=" + Id);
						$(pos).parent().parent().addClass('editObject');
						$("#txt_productName").val(data.Name);
						$("#txt_productRemark").val(data.Remark);
						$("#txt_productBrand").val(data.Brand);
						$("#txt_productPrice").val(data.Price);
						$("#txt_productFreight").val(data.Freight);
						$("#txt_Location").val(data.Location);
						$("#txt_suitableage").val(data.SuitableAge);
						$("#txt_pwebsiteName").val(data.WebsiteId);
						$("#txt_productcustoms").val(data.CustomsDuty);
						$("#txt_productStandard").val(data.Standard);
		                  
						$("#op_first_type").val(data.FirstType);
						getSecondType(data.FirstType);
						$("#op_second_type").val(data.SecondType);
						getThirdType(data.SecondType);
						$("#op_third_type").val(data.ThirdType);
						$("#op_website").val(data.WebsiteId);
						$("#txt_PurchaseDate").val(data.PurchaseTime);
						$("#txt_OnlineDate").val(data.OnlineTime);
						$("#txt_expirationDate").val(data.ExpirationDate);
						$("#txt_producingarea").val(data.ProducingArea);
						$("#txt_picture").val(data.Picture);
						$("#op_productDefinition").val(data.PdId);
						$("#txt_productSales").val(data.Sales);
						$('#addProductArea').show();
						if(data.CountryId!="")
						{ 
							$('#txt_productcountry').show();
							$("#txt_productcountry").val(data.CountryId);
							$('#txt_productcountry').text(data.CountryName+" ");
						}
		                   
						if(data.AreaId!="")
						{
							$('#txt_productarea').show();
							$("#txt_productarea").val(data.AreaId);
							$('#txt_productarea').text(data.AreaName);
						}
					}
					else if(r =="sessionOut"){
						doLogout();				
					}
				},
				error: function (e) {
					alert(e.responseText);
				}
			});
		}else{
			$("#hid_productParam").val("methodName=add");
			
			$("#txt_productName").val("");
			$("#txt_productRemark").val("");
			$("#txt_productBrand").val("");
			$("#txt_productPrice").val("");
			$("#txt_productFreight").val("");
			$("#txt_Location").val("");
			$("#txt_suitableage").val("");
			$("#txt_pwebsiteName").val("");
			$("#txt_productcustoms").val("");
			$("#txt_productStandard").val("");
                 
			$("#txt_PurchaseDate").val("");
			$("#txt_OnlineDate").val("");
			$("#txt_expirationDate").val("");
			$("#txt_producingarea").val("");
			$("#txt_picture").val("");
                  
			$("#txt_productSales").val("");
			$('#addProductArea').show();
			
			$('#txt_productcountry').show();
			$("#txt_productcountry").val("");
			$('#txt_productcountry').text("");
                  
			$('#txt_productarea').show();
			$("#txt_productarea").val("");
			$('#txt_productarea').text("");
		}
	}
}
/*
函数名：SaveProduct
作用：保存商品

*/
function SaveProduct() {
    var strName = $("#txt_productName").val();
    var strRemark = $("#txt_productRemark").val();
    var strBrand = $("#txt_productBrand").val();
    var strFreight = $("#txt_productFreight").val();
    var strPrice = $("#txt_productPrice").val();
    var strStandard = $("#txt_productStandard").val();
   
    var strWebsite = $("#op_website").val();
    var strFirstType = $("#op_first_type").val();
    var strSecondType =$("#op_second_type").val();
    var strThirdType = $("#op_third_type").val();

    var strEx = $("#txt_expirationDate").val();
    var strLocation = $("#txt_Location").val();
    var strsuitableage = $("#txt_suitableage").val();

    var strcustoms = $("#txt_productcustoms").val();
    var strPurchaseDate = $("#txt_PurchaseDate").val();
    var strOnlineDate = $("#txt_OnlineDate").val();
    var strProducingarea = $("#txt_producingarea").val();
    var strPicture = $("#txt_picture").val();
    var strSales = $("#txt_productSales").val();
    var strProDef = $("#op_productDefinition").val();
    
    var strAreaId = $("#txt_productarea").val();
	var strCountryId = $("#txt_productcountry").val();
    

    
 
    
    if ($.trim(strName) == "") {
        alertText("商品名不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ProductServlet?methodName="
            					+ getParam("hid_productParam", "methodName") + "&id=" + getParam("hid_productParam", "id")
                                + "&name=" + encode(strName) + "&remark=" + encode(strRemark) + "&customs=" + encode(strcustoms) + "&price=" + encode(strPrice) 
                                + "&freight=" + encode(strFreight) + "&purchaseDate=" + strPurchaseDate 
                                + "&onlineDate=" + strOnlineDate + "&producingarea=" + encode(strProducingarea)+ "&brand=" + encode(strBrand) 
                                + "&standard=" + encode(strStandard) + "&website=" + strWebsite +"&expirationDate=" + encode(strEx) + "&Location=" + encode(strLocation) 
                                + "&suitableage=" + encode(strsuitableage) + "&picture=" + encode(strPicture)
                                + "&firstType=" + strFirstType+ "&secondType=" + strSecondType + "&thirdType=" + strThirdType + "&areaId=" + strAreaId + "&countryId=" + strCountryId
                                +"&sales=" + strSales + "&proDef=" +  strProDef,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("保存失败！", 3500);
            } 
            else if(r =="sessionOut"){
            	doLogout();
            	
            }else {//保存成功
                alertText("保存成功！", 3500);
                //更新数据+页面跳转
                var data = $.parseJSON(r);
                UpdateProduct(data);
                $('#productDom_Edit').animate({ width: "hide" });
                $("#productListDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
函数名：UpdateProduct
作用：保存后更新商品列表
参数：data：被更改的网站信息
*/
function UpdateProduct(data){
    var str = "";
    if(getParam("hid_productParam", "methodName") == "add"){//新增
        str += GetProductEntry(data, '*');
        $("#ProductList").find('.noDataSrc').remove();
        $("#ProductList").prepend(str);//增加数据
        var dataTotal = parseInt($("#ProductPaging .dataCount").text())+1;
        $("#ProductPaging .dataCount").text(dataTotal)
        $("#ProductPaging .pageCount").text(parseInt((dataTotal + 9) / 10));
        $("#ProductPaging .pageIndex").text(parseInt((dataTotal + 9) / 10));//更新页码及商品数
    }else if(getParam("hid_productParam", "methodName") == "edit"){//编辑时更新数据
        var obj =  $("#ProductList").find('.editObject').find('td');
        obj.eq(0).html('*');
        obj.eq(1).html(data.Remark);
        obj.eq(2).html(data.Location);
        obj.eq(3).html(data.Price);
        obj.eq(4).html(data.WebsiteName); 
        obj.eq(5).html(data.CreateTime);
        $("#ProductList").find('.editObject').removeClass('editObject');
    }
}

function GetProductArea()
{
	$("#hid_PreviousParam").val("productDom_Edit");
	$("#hid_TypeParam").val("selectedType=product");
	$("#productDom_Edit").animate({ width: "hide" });
    $('#BrandRegionDom_Edit').show();
    $("#txt_BrandCountryQuery").val("");
    $("#BrandRegionList").html('');
	
}
function getFirstType(){
	$("#op_first_type").html('');
	var str = "";
	str +=  "<option value='0'>" + "请选择" + "</option>";
	$.ajax({
		type : "post",
		url : "./servlet/ProductTypeServlet?methodName=getRootType",
		contentType: "application/json;charset=utf-8",
        async: false,
		success : function(r) {
		
			if (r != "false") {
				var data = $.parseJSON(r);
	            for (var i = 0; i < data.webList.length; i++) {
					str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
				}
				
			}
			
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
	$("#op_first_type").append(str);
	
}
function getSecondType(id){
	$("#op_second_type").html('');
	var str = "";
	str +=  "<option value='0'>" + "请选择" + "</option>";
	
	
	if(id!="0")
	{
				$.ajax({
			type : "post",
			url : "./servlet/ProductTypeServlet?methodName=sel&id=" + id,
			dataType : "text",
			async : false,
			success : function(r) {
				 
				if (r != "false") {
					var data = $.parseJSON(r);
		            for (var i = 0; i < data.webList.length; i++) {
						str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
					}
					
				}
				else if(r =="sessionOut"){
	            	doLogout();
	            	
	            }
			},
			error : function(e) {
				alert(e.responseText);
			}
		});
	}
	$("#op_second_type").append(str);
	
}
function getThirdType(id){
	$("#op_third_type").html('');
	var str = "";
	str +=  "<option value='0'>" + "请选择" + "</option>";
	
	if(id!="0")
	{
	
			$.ajax({
		type : "post",
		url : "./servlet/ProductTypeServlet?methodName=sel&id=" + id,
		dataType : "text",
		async : false,
		success : function(r) {
			 
			if (r != "false") {
				var data = $.parseJSON(r);
				
	            for (var i = 0; i < data.webList.length; i++) {
					str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
				}
			
			}
			else if(r =="sessionOut"){
            	doLogout();
            	
            }
		},
		error : function(e) {
			alert(e.responseText);
		}
	});

}
$("#op_third_type").append(str);
	
}

function MatchingProductDefinition()
{
	$.ajax({
		type : "post",
		url : "./servlet/ProductServlet?methodName=matchProductDef",
		dataType : "text",
		async : false,
		success : function(r) {
			 
			if (r == "true") {
				alert("匹配完成！");
			
			}
			else if(r == "false")
				{
				alert("匹配出错！");
				}
			else if(r =="sessionOut"){
            	doLogout();
            	
            }
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}