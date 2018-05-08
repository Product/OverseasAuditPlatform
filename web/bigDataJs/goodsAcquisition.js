/*大数据-商品管理-商品采集配置 */

//三级菜单绑定
$(function(){
	 $("#goodsTypeFirstSel").change(
				function(){
					$("#goodsTypeSecondSel").html('');
					var str = "";
					str +=  "<option value='0'>" + "请选择" + "</option>";
					$("#goodsTypeThirdSel").html('');
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
					$("#goodsTypeSecondSel").append(str);
					$("#goodsTypeThirdSel").append(str1);
			});
	 $("#goodsTypeSecondSel").change(
				function(){
					$("#goodsTypeThirdSel").html('');
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
				$("#goodsTypeThirdSel").append(str);
				});
	
});
function FirstPage_goodsAcquisition() {
	$("#productListDom").hide();
	$("#productDom_AcquisitionList").show();
	GetGoodsAcquisitionList(1,10);
}

function PreviousPage_goodsAcquisition() {
	var pageIndex = (parseInt($("#goodsAcquisitionPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#goodsAcquisitionPaging .pageIndex").text()) - 1);
	GetGoodsAcquisitionList(pageIndex,10);
}

function NextPage_goodsAcquisition() {
	var pageIndex = ($("#goodsAcquisitionPaging .pageIndex").text() == $("#goodsAcquisitionPaging .pageCount").text() ? parseInt($("#goodsAcquisitionPaging .pageIndex").text()) : parseInt($("#goodsAcquisitionPaging .pageIndex").text()) + 1);
	GetgoodsAcquisitionList(pageIndex,10);
}

function EndPage_goodsAcquisition() {
	GetGoodsAcquisitionList(parseInt($("#goodsAcquisitionPaging .pageCount").text()),10);
}

/**
 * 获取商品采集配置list数据并分页
 * @param strPageIndex
 * @param pageSize
 */
function GetGoodsAcquisitionList(strPageIndex,pageSize) {
	$.ajax({
			type : "post",
			url : projectLocation
				+ "servlet/CollectorCfgCommodity?methodName=queryList&pageIndex="
				+ strPageIndex + "&pageSize="+pageSize,
			dataType : "text",
			async : false,
			success : function(r) {
				$("#goodsAcquisitionList").html('');
				if (r == "false") {
					$("#goodsAcquisitionList").append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
					return false;
				}
				else if(r =="sessionOut"){
	            	doLogout();
	            	
	            }
				var data = $.parseJSON(r);

				$("#goodsAcquisitionPaging .dataCount").text(data.count);
				$("#goodsAcquisitionPaging .pageCount").text(parseInt((parseInt(data.count) + 9) / 10));
				$("#goodsAcquisitionPaging .pageIndex").text(strPageIndex);
				var str = "";
				var j = 0;
				 for (var i = 0; i < data.infoList.length; i++) {
					 str += GetGoodsAcquisitionEntry(data.infoList[i], i+1);
					
				}
				$("#goodsAcquisitionList").append(str);
			},
			error : function(e) {
				alert(e.responseText);
			}
		});
}

/**
 * 循环输出报告采集配置数据
 * @param data
 * @param idx
 * @returns {String}
 */
function GetGoodsAcquisitionEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>";
    	if(data.name == null || data.name == ""){
    		str += "无";
    	}else{
    		str += data.name;
    	}
    str += "</td>";
    str += "<td><a href='"+data.url+"' target='_blank'>";
		if(data.url == null || data.url == ""){
			str += "无";
		}else{
			str += data.url;
		}
	str += "</a></td>";
    str += "<td>";
		if(data.titleSign == null || data.titleSign == ""){
			str += "无";
		}else{
			str += data.titleSign;
		}
	str += "</td>";
    str += "<td>";
		if(data.contentSign == null || data.contentSign == ""){
			str += "无";
		}else{
			str += data.contentSign;
		}
	str += "</td>";
    str += "<td>";
		if(data.brandSign == null || data.brandSign == ""){
			str += "无";
		}else{
			str += data.brandSign;
		}
	str += "</td>";
    str += "<td>";
		if(data.countrySign == null || data.countrySign == ""){
			str += "无";
		}else{
			str += data.countrySign;
		}
	str += "</td>";
    str += "<td>";
		if(data.productStyleName == null || data.productStyleName == ""){
			str += "无";
		}else{
			str += data.productStyleName;
		}
	str += "</td>";
    str += "<td>";//采集频率
	    if(data.rate == 0){
			str += "无";
		}else if(data.rate == 1){
	    	str += "一天";
	    }else if(data.rate == 7){
	    	str += "一周";
	    }else if(data.rate == 30){
	    	str += "一个月";
	    }else {
	    	str += "测试数据";
	    }
	str += "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编辑' onclick='EditGoodsAcquisition(" + data.id + ")' />&nbsp;" +
    		"<input class='btn btn-default btn-xs' type='button' value='执行' onclick='ExecGoodsAcquisition(" + data.id + ")' />&nbsp;" +
    		"<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""+ data.id + "\",DelGoodsAcquisition)' /></td>";
    str += "</tr>";
    return str;
}

/**
 * 商品采集-新增
 */
function AddGoodsAcquisition(){
	$("#iframeMain .wrap").animate({ width: "hide" });
	$('#productDom_AcquisitionAdd').show();
	
    $('#hid_goodsAcquisitionId').val('');
    
	$("#txt_goodsAcquisitionName").val("");
	$("#txt_goodsAcquisitionUrl").val("");
	$("#txt_goodsAcquisitionContent").val("");
	$("#txt_goodsAcquisitionTitle").val("");
	$("#txt_goodsAcquisitionNext").val(""); 
	$("#txt_goodsGetPriceSign").val(""); 
	$("#txt_goodsAcquisitionPageTotal").val(""); 
	$("#txt_goodsAcquisitionWeb").val(""); 
	$("#txt_goodsAcCreateTime").val(""); 
	$("#op_goodsAcquisitionFrequency").html('<option value="0">请选择</option><option value="1">一天</option><option value="7">一周</option><option value="30">一个月</option>'); 
	
	$("#txt_goodSaleMountSign").val("");
	$("#txt_goodsProducingAreaSign").val("");
	$("#txt_goodSuitableAgeSign").val(""); 
	$("#txt_goodStandardSign").val("");
	$("#txt_goodExpirationDateSign").val(""); 
	$("#txt_goodsAcquisitionBrand").val("");
	$("#txt_goodsAcquisitionCountry").val("");
	$("#txt_goodsTypeSign").val("");
	$("#goodsTypeSecondSel").val('<option value="0">请选择</option>'); 
	$("#goodsTypeThirdSel").val('<option value="0">请选择</option>'); 
	goodsFirstlevelType();
	goodsAcquisitionType();
}

/**
 * 商品采集-编辑
 * @param id
 */
function EditGoodsAcquisition(id){
    $('#hid_goodsAcquisitionId').val(id);
    $("#txt_goodsAcquisitionName").val("");
	$("#txt_goodsAcquisitionUrl").val("");
	$("#txt_goodsAcquisitionContent").val("");
	$("#txt_goodsAcquisitionTitle").val("");
	$("#txt_goodsAcquisitionNext").val(""); 
	$("#txt_goodsGetPriceSign").val(""); 
	$("#txt_goodsAcquisitionPageTotal").val(""); 
	$("#txt_goodsAcquisitionWeb").val(""); 
	$("#txt_goodsAcCreateTime").val(""); 
	$("#op_goodsAcquisitionFrequency").html('<option value="0">请选择</option><option value="1">一天</option><option value="7">一周</option><option value="30">一个月</option>'); 
	
	$("#txt_goodSaleMountSign").val("");
	$("#txt_goodsProducingAreaSign").val("");
	$("#txt_goodSuitableAgeSign").val(""); 
	$("#txt_goodStandardSign").val("");
	$("#txt_goodExpirationDateSign").val("");
	$("#txt_goodsAcquisitionBrand").val("");
	$("#txt_goodsAcquisitionCountry").val("");
	$("#txt_goodsTypeSign").val("");
	goodsFirstlevelType(); //商品类别一级菜单加载
	goodsAcquisitionType();//所有产品
	$("#productDom_AcquisitionList").hide();
    $("#productDom_AcquisitionAdd").show();
    
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/CollectorCfgCommodity?methodName=queryById&id=" + id,
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
                $("#hid_goodsAcquisitionId").val(id);
                $("#txt_goodsAcquisitionName").val(r.name); 
                $("#txt_goodsAcquisitionUrl").val(r.url);
                $("#txt_goodsAcquisitionContent").val(r.contentSign);
                $("#txt_goodsAcquisitionTitle").val(r.titleSign);
                $("#txt_goodsAcquisitionNext").val(r.nextSign);
                $("#txt_goodsGetPriceSign").val(r.getPriceSign);
                $("#txt_goodsAcquisitionPageTotal").val(r.pageTotal);
                $("#txt_goodsAcCreateTime").val(r.createTime);
                $("#txt_goodsAcquisitionWeb").val(r.websiteId); 
            	$("#op_goodsAcquisitionType").val(r.productStyleId); 
            	$("#op_goodsAcquisitionFrequency").val(r.rate); 
            	
            	$("#txt_goodSaleMountSign").val(r.saleMountSign);
            	$("#txt_goodsProducingAreaSign").val(r.producingAreaSign);
            	$("#txt_goodSuitableAgeSign").val(r.suitableAgeSign); 
            	$("#txt_goodStandardSign").val(r.standardSign);
            	$("#txt_goodExpirationDateSign").val(r.expirationDateSign);
            	$("#txt_goodsAcquisitionBrand").val(r.brandSign);
            	$("#txt_goodsAcquisitionCountry").val(r.countrySign);
            	$("#txt_goodsTypeSign").val(r.productTypeSign);
            	//商品类型级联
            	
            	$("#goodsTypeFirstSel").val(r.firstlevelType);
		        goodsTypeSecondSel(r.firstlevelType);
	            $("#goodsTypeSecondSel").val(r.secondlevelType);
		        goodsTypeThirdSel(r.secondlevelType);
	            $("#goodsTypeThirdSel").val(r.thirdlevelType);
            }
            else if(r == "sessionOut"){
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

/**
 * 一级商品类型
 */
function goodsFirstlevelType(){
	$("#goodsTypeFirstSel").html('');
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
	            $("#goodsTypeFirstSel").append(str);
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
	
}

/**
 * 二级菜单
 */
function goodsTypeSecondSel(id){
	$("#goodsTypeSecondSel").html('');
	var str = "";
	str +=  "<option value='0'>" + "请选择" + "</option>";
	
	if(id!="0"){
		$.ajax({
			type : "post",
			url : "servlet/ProductTypeServlet?methodName=sel&id=" + id,
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
	$("#goodsTypeSecondSel").append(str);
}

/**
 * 三级菜单
 */
function goodsTypeThirdSel(id) {
	$("#goodsTypeThirdSel").html('');
	var str = "";
	str +=  "<option value='0'>" + "请选择" + "</option>";
	
	if(id!="0"){
	  $.ajax({
			type : "post",
			url : "servlet/ProductTypeServlet?methodName=sel&id=" + id,
			dataType : "text",
			async : false,
			success : function(r) {
				if (r != "false") {
					var data = $.parseJSON(r);
					
		            for (var i = 0; i < data.webList.length; i++) {
						str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
					}
				
				}else if(r =="sessionOut"){
					doLogout();
				}
			},
			error : function(e) {
				alert(e.responseText);
			}
	  	});
	}
	$("#goodsTypeThirdSel").append(str);
}


/**
 * 商品采集-保存
 * @returns {Boolean}
 */
function SaveGoodsAcquisition() {
	var strName = $("#txt_goodsAcquisitionName").val();
    var strLocation = $("#txt_goodsAcquisitionUrl").val();
    var strContent =  $("#txt_goodsAcquisitionContent").val();
    var strTitleArg = $("#txt_goodsAcquisitionTitle").val();
    var strNext = $("#txt_goodsAcquisitionNext").val();
    var strPriceArg = $("#txt_goodsGetPriceSign").val();
    var strpageTotal = $("#txt_goodsAcquisitionPageTotal").val();
    var strCreateTime = $("#txt_goodsAcCreateTime").val();
    var strWebSiteId =  $("#txt_goodsAcquisitionWeb").val();
	var strGoodsType = $("#op_goodsAcquisitionType").val();
	var strFrequency = $("#op_goodsAcquisitionFrequency").val();
	
    var strSaleMountSign = $("#txt_goodSaleMountSign").val();
    var strProducingAreaSign = $("#txt_goodsProducingAreaSign").val();
	var strSuitableAgeSign = $("#txt_goodSuitableAgeSign").val(); 
	var strStandardSign = $("#txt_goodStandardSign").val();
	var strExpirationDateSign = $("#txt_goodExpirationDateSign").val();
	
	var strBrand = $("#txt_goodsAcquisitionBrand").val();
	var strCountry = $("#txt_goodsAcquisitionCountry").val();
	var strTypeSign =$("#txt_goodsTypeSign").val();
	var strGoodsTypeFirst = $("#goodsTypeFirstSel").val();
	var strGoodsTypeSecond = $("#goodsTypeSecondSel").val();
	var strGoodsTypeThird = $("#goodsTypeThirdSel").val();
	
    var strId= $("#hid_goodsAcquisitionId").val();
    
    if ($.trim(strName) == "") {
        alertText("采集器名不能为空！", 3500);
        return false;
    } 
    
    var reg=/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
    if (!reg.test($.trim(strLocation))) {
        alertText("请输入正确的网址! 例如：https://www.baidu.com", 4000);
        return false;
    }
    
    $.ajax({
		type: "post",
		url: projectLocation
			+ "servlet/CollectorCfgCommodity?methodName=addOrUpdate&id=" + strId
			+ "&name=" + encode(strName) + "&url=" + encode(strLocation) + "&contentSign="+encode(strContent)+ "&titleSign="+encode(strTitleArg)
			+ "&nextSign=" + encode(strNext)+"&priceSign=" + encode(strPriceArg) + "&countrySign=" +encode(strCountry)+ "&brandSign="+encode(strBrand)
			+ "&pageTotal="+strpageTotal+ "&createTime="+encode(strCreateTime)+"&productStyleId="+strGoodsType+ "&websiteId="+strWebSiteId
			+ "&rate="+strFrequency+"&saleMountSign="+encode(strSaleMountSign)+ "&producingAreaSign="+encode(strProducingAreaSign)+"&suitableAgeSign="+encode(strSuitableAgeSign)
			+ "&standardSign="+encode(strStandardSign)+ "&expirationDateSign="+encode(strExpirationDateSign)+"&firstlevelType="+strGoodsTypeFirst+"&secondlevelType="+strGoodsTypeSecond
			+ "&thirdlevelType="+strGoodsTypeThird+ "&productTypeSign="+strTypeSign,
		contentType: "application/json;charset=utf-8",
		async: false,
		success: function (r) {
			if(r !=null && r != "false" && r != "{}" && r !="异常" ){
				GetGoodsAcquisitionList(1,10);
				$("#productDom_AcquisitionAdd").hide();
				$("#productDom_AcquisitionList").show();
				alertText("保存成功！", 3500);
				
			}
		},
		error: function (e) {
			alertText("保存失败！", 3500);
			return false;
			//alert(e.responseText);
		}
	});
}

/**
 * 所有产品类型列表
 */
function goodsAcquisitionType(){
	$.ajax({
		 type: "post",
         url: projectLocation
					+ "servlet/ProductStyleServlet?methodName=allProductStyles",
         dataType: "text",
         async: false,
         success: function (r) {
             if (r != "false") {
                 var data = $.parseJSON(r);
                 var str = "<option value=''>请选择</option>";
                 for (var i = 0; i < data.list.length; i++) {
                     str += "<option value='" + data.list[i].id + "'>" + data.list[i].name + "</option>";
                 }
                 $("#op_goodsAcquisitionType").html("");
                 $("#op_goodsAcquisitionType").append(str);
             }
             else if (r == "sessionOut") {
                 indexDoLogout();
             }
             else {
                
             }
         },
         error: function (e) {
             alert(e.responseText);
         }
	});
}

/**
 * 采集配置-执行
 * @param id
 */
function ExecGoodsAcquisition(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/CollectorCfgCommodity?methodName=excute&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "success") {
                    alertText("执行成功！正在爬去数据，请等待……", 5000);
                }else if(r =="sessionOut"){
                	doLogout();
                }else if(r == "error" || r == "" || r == "false"){
                	alertText("执行失败！请查看是否参数配置错误！", 3500);
         	   }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}

/**
 * 删除采集配置
 * @param id
 */
function DelGoodsAcquisition(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/CollectorCfgCommodity?methodName=delete&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                	GetGoodsAcquisitionList(1,10);
                    alertText("删除成功！", 3500);
                }else if(r =="sessionOut"){
                	doLogout();
                	
                }else if(r == "false"){
                	alertText("删除失败！", 3500);
         	   }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}
