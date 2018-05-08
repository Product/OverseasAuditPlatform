//add by yekai
$(function () {
	$("#RCAChoiceSure").click(function () {//Region、Country、Area选择确定区
	        // to get the chioces
	        //获得两个个选择框的文本内容和对应Id
		var countryObj = document.getElementById('txt_rulesCountry');
        var countryChoice = countryObj.options[countryObj.selectedIndex].text;
        var areaObj = document.getElementById('txt_rulesArea');
        var areaChoice = areaObj.options[areaObj.selectedIndex].text;
        var countryChoiceId = $("#txt_rulesCountry").val();
        var areaChoiceId = $("#txt_rulesArea").val();


        if (countryChoice == "请选择") {
            return;
        }
        // to show chioces
        $("#RCAChioceShow").show();
        if (areaChoice != "请选择") {
            var str = "<span class='choices' value='" + countryChoiceId + "|" + areaChoiceId + "<>' onclick='deleRCAChoice(this)'>" + countryChoice + "&nbsp;|&nbsp;" + areaChoice + "<a>&times</a></span>";
        }
        else if (areaChoice == "请选择") {
            var str = "<span class='choices' value='" + countryChoiceId + "|" + areaChoiceId + "<>' onclick='deleRCAChoice(this)'>" + countryChoice + "<a>&times</a></span>";
        }
        $("#RCAChioceDisplay").append(str);
    });
	//当Region菜单变化时，加载Country菜单
	$("#txt_rulesRegion").change(function () {
		$("#txt_rulesCountry").html("<option value='0'>请选择</option>");
	    var regionId = $(this).val();
	    if (regionId != "0") {
	    	$.ajax({
	    		type: "post",
	    		url: projectLocation + "servlet/CountryServlet?methodName=QueryList&strQuery=&strPageIndex=1&strPageCount=300&regionId="+regionId,
	    		dataType: "json",
	    		async: false,
	    		success: function (r) {
	    			$.each(r.webList, function(key, val) {
	    		        $("#txt_rulesCountry").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
	    		        });
	    		    },
	    		    error: function (e) {
	    		        alert(e.responseText);
	    		    }
	    		});
	    }
	});
	//当Country菜单变化时，加载Area菜单
	$("#txt_rulesCountry").change(
			function () {
				$("#txt_rulesArea").html("<option value='0'>请选择</option>");
			    var countryId = $(this).val();
			    if (countryId != "0") {

			    	$.ajax({
			    		type: "post",
			    		url: projectLocation + "servlet/AreasServlet?methodName=QueryList&strQuery=&strPageIndex=1&strPageCount=100&countryId="+countryId,
			    		dataType: "json",
			    		async: false,
			    		success: function (r) {
			    			$.each(r.webList, function(key, val) {
			    		        $("#txt_rulesArea").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
			    		    });
			    		},
			    		error: function (e) {
			    		    alert(e.responseText);
			    		}
			    	});
			    }
			});
});
//region下拉菜单
function selectRegion(){
	$("#txt_rulesRegion").html("<option>请选择</option>");//初始化region菜单
	$("#txt_rulesCountry").html("<option>请选择</option>");//初始化country菜单
	$("#txt_rulesArea").html("<option>请选择</option>");//初始化area菜单
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/RegionServlet?methodName=getRegionList",
        dataType: "json",
        async: false,
        success: function (r) {
        	$.each(r.webList, function(key, val) {
                $("#txt_rulesRegion").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
            });
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//add by Ly
function deleRCAChoice(obj){
	// delete current span
	$(obj).remove();
	// if current content is null,to hide the 国家地区描述
	if ($("#RCAChioceDisplay").children("span").length <= 0)
	{
		$("#RCAChioceShow").hide();
	}
}

$(function () {
$("#productPropertySure").click(function () {
        // to get the chioces
        //获得三个选择框的文本内容和对应Id
        var firObj = document.getElementById('firstPropertyTypes');
        var firChoice = firObj.options[firObj.selectedIndex].text;
        var secObj = document.getElementById('secondPropertyTypes');
        var secChoice = secObj.options[secObj.selectedIndex].text;
        var thiObj = document.getElementById('thirdPropertyTypes');
        var thiChoice = thiObj.options[thiObj.selectedIndex].text;
        var firChoiceId = $("#firstPropertyTypes").val();
        var secChoiceId = $("#secondPropertyTypes").val();
        var thiChoiceId = $("#thirdPropertyTypes").val();


        if (firChoice == "请选择") {
            return;
        }
        // to show chioces
        $("#productPropertyShow").show();
        if (secChoice != "请选择" && thiChoice != "请选择") {
            var str = "<span class='choices' value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='deletePropertyTypes(this)'>" + firChoice + "&nbsp;|&nbsp;" + secChoice + "&nbsp;|&nbsp;" + thiChoice + "<a>&times</a></span>";
        }
        else if (secChoice == "请选择") {
            var str = "<span class='choices'  value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='deletePropertyTypes(this)'>" + firChoice + "<a>&times</a></span>";
        }
        else if (thiChoice == "请选择") {
            var str = "<span class='choices'  value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='deletePropertyTypes(this)'>" + firChoice + "&nbsp;|&nbsp;" + secChoice + "<a>&times</a></span>";
        }
        $("#productPropertyDisplay").append(str);
    });

    //当第一级菜单变化时，加载第二级菜单
    $("#firstPropertyTypes").change(
		function () {
		    $("#secondPropertyTypes").html('');
		    var str = "";
		    str += "<option value='0'>" + "请选择" + "</option>";
		    var firVal = $(this).val();

		    if (firVal != "0") {
		        $.ajax({
		            type: "post",
		            url: "./servlet/ProductPropertyTypeServlet?methodName=sel&id=" + firVal,
		            dataType: "text",
		            async: false,
		            success: function (r) {

		                if (r != "false") {
		                    var data = $.parseJSON(r);
		                    for (var i = 0; i < data.webList.length; i++) {
		                        str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Remark + "</option>";
		                    }

		                }
		                else if (r == "sessionOut") {
		                    doLogout();

		                }
		            },
		            error: function (e) {
		                alert(e.responseText);
		            }
		        });
		    }
		    $("#secondPropertyTypes").append(str);
		    $("#thirdPropertyTypes").html("<option value='0'>请选择</option>");
		});


    //当第二级菜单发生变化时，加载第三级菜单
    $("#secondPropertyTypes").change(
		function () {
		    $("#thirdPropertyTypes").html('');
		    var str = "";
		    str += "<option value='0'>请选择</option>";
		    var secVal = $(this).val();
		    if (secVal != "0") {

		        $.ajax({
		            type: "post",
		            url: "./servlet/ProductPropertyTypeServlet?methodName=sel&id=" + secVal,
		            dataType: "text",
		            async: false,
		            success: function (r) {

		                if (r != "false") {
		                    var data = $.parseJSON(r);

		                    for (var i = 0; i < data.webList.length; i++) {
		                        str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Remark + "</option>";
		                    }

		                }
		                else if (r == "sessionOut") {
		                    doLogout();

		                }
		            },
		            error: function (e) {
		                alert(e.responseText);
		            }
		        });

		    }
		    $("#thirdPropertyTypes").append(str);
		});
});
//获得一级海关类别
function getFirstPropertyTypes(){
	$("#firstPropertyTypes").html('');
	 $("#secondPropertyTypes").html("<option value='0'>请选择</option>");
	    $("#thirdPropertyTypes").html("<option value='0'>请选择</option>");
	var str = "";
	str +=  "<option value='0'>" + "请选择" + "</option>";
	$.ajax({
		type : "post",
		url : "./servlet/ProductPropertyTypeServlet?methodName=getRootType",
		contentType: "application/json;charset=utf-8",
        async: false,
		success : function(r) {
		
			if (r != "false") {
				var data = $.parseJSON(r);
	            for (var i = 0; i < data.webList.length; i++) {
					str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Remark + "</option>";
				}
				
			}
			
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
	$("#firstPropertyTypes").append(str);
	
}

function deletePropertyTypes(obj){
	// delete current span
	$(obj).remove();
	// if current content is null,to hide the 商品描述
	if ($("#productPropertyDisplay").children("span").length <= 0)
	{
		$("#productPropertyShow").hide();
	}
}

//首页
function FirstPageRules() {
	GetRulesList(1);
}

//上一页
function PreviousPageRules() {
	var pageIndex = (parseInt($("#rulesPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#rulesPaging .pageIndex").text()) - 1);
	GetRulesList(pageIndex);
}

//下一页
function NextPageRules() {
	var pageIndex = ($("#rulesPaging .pageIndex").text() == $(
			"#rulesPaging .pageCount").text() ? parseInt($(
			"#rulesPaging .pageIndex").text()) : parseInt($(
			"#rulesPaging .pageIndex").text()) + 1);
	GetRulesList(pageIndex);
}

//尾页
function EndPageRules() {
	GetRulesList(parseInt($("#rulesPaging .pageCount").text()));
}

//将得到的数据转变成html格式代码字符串
function GetRulesEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
	str += "<td>" + data.Name + "</td>";
	str += "<td>" + data.Content + "</td>";
	str += "<td>" + data.Remark + "</td>";
	str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
	str += "<td><input  class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showRulesDialog(\"edit\",\"" + data.Id + "\",this)' />&nbsp;"
			 + "<input  class='btn btn-default btn-xs' type='button' value='抽 取' onclick='extractionDialog(\"extract\",\"" + data.Id + "\")' />&nbsp;"
			 + "<input  class='btn btn-default btn-xs' type='button' value='抽取查看' onclick='ExtractViewDialog(\"extract\",\"" + data.Id + "\")' />&nbsp;"
			 + "</td>";
	str += "</tr>";
    return str;
}

//显示规则列表
function GetRulesList(strPageIndex) {
	//关键字查询
	var strQuery = $("#txt_rulesQuery").val();
	//创建开始时间查询
	var strStraTime = $("#txt_rulesBeginDate").val();
	//创建结束时间查询
	var strEndTime = $("#txt_rulesEndDate").val();
	
	$.ajax({
				type : "post",
				url : projectLocation
						+ "servlet/RulesServlet?methodName=QueryList&strQuery="
						+ strQuery + "&strStraTime=" + strStraTime
						+ "&strEndTime=" + strEndTime + "&strPageIndex="
						+ strPageIndex + "&strPageCount=10",
				dataType : "text",
				async : false,
				success : function(r) {
					$("#rulesList").html('');
					if (r == "false") {
						$("#rulesList").append("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
						return false;
					}
					else if (r == "sessionOut") {
		                doLogout();
		            }
					var data = $.parseJSON(r);
					$("#rulesPaging .dataCount").text(data.total);
					$("#rulesPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
					$("#rulesPaging .pageIndex").text(strPageIndex);
					var str = "";
					for (var i = 0; i < data.rulesList.length; i++){
						 str += GetRulesEntry(data.rulesList[i], i+1);
					}
					$("#rulesList").append(str);
				},
				error : function(e) {
					alert(e.responseText);
				}
			});
}

//规则设定页面新增及编辑功能
function showRulesDialog(methodName, Id, pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });	
    $('#rulesDom_Edit').show();
    $("#Erules").html("");
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    $("#productTypeDisplay").html("");
    $("#materialDisplay").html("");
    $("#productPropertyDisplay").html(""); 
    $("#RCAChioceDisplay").html(""); 
    
    //编辑
    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/RulesServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	InitWStyle();
                	getFirstProductType();
                	getFirstPropertyTypes();
                	InitMaterialType();
                	selectMaterial();
                	selectRegion();
                	$(pos).parent().parent().addClass('editObject');
                	$("#hid_rulesParamB").val("methodName=edit&id=" + Id);
					$("#txt_rulesNameB").val(r.Name);
					$("#txt_rulesContentB").val(r.Content);
					$("#txt_rulesRemarkB").val(r.Remark);
					 //加载已关联的原料
                    $("#materialShow").show();
                    for (var j = 0; j < r.Materials.length; j++){
                    	var str = "<span class='choices' value='" + r.Materials[j].materialId + "<>' onclick='deleteMaterial(this)'>" + r.Materials[j].materialName + "<a class='timeA'>&times</a></span><br/>";
                    	$("#materialDisplay").append(str);
                    }   
					//加载已关联的网站类型
					for (var i = 0; i < document.getElementsByName("txt_rulesWebStyleB").length; i++) 
	                {
	                	for(var k = 0;k<r.WebsiteStyles.length;k++)
	                	{
	                		if((document.getElementsByName("txt_rulesWebStyleB")[i].value)==r.WebsiteStyles[k].WebsiteStyleId)
	                			document.getElementsByName("txt_rulesWebStyleB")[i].checked=true;
	                	}
	            	}
					if (r.ProductTypes.length != 0 )
					{
					//加载已关联的产品类型
						$('#productType').show();
				        $('#productTypeShow').show();
				        $('#productPropertyType').hide();
				        $('#productPropertyShow').hide();
                    for (var j = 0; j < r.ProductTypes.length; j++){
                    	
                         if (r.ProductTypes[j].secPTName != "" && r.ProductTypes[j].thiPTName != "") {
                             var str = "<span class='choices' value='" + r.ProductTypes[j].firPTId + "|" + r.ProductTypes[j].secPTId + "|" + r.ProductTypes[j].thiPTId + "<>' onclick='deleteProductType(this)'>" + r.ProductTypes[j].firPTName + "&nbsp;|&nbsp;" + r.ProductTypes[j].secPTName + "&nbsp;|&nbsp;" + r.ProductTypes[j].thiPTName + "<a class='timeA'>&times</a></span><br/>";
                         }
                         else if (r.ProductTypes[j].secPTName == "") {
                             var str = "<span class='choices'  value='" + r.ProductTypes[j].firPTId + "|" + r.ProductTypes[j].secPTId + "|" + r.ProductTypes[j].thiPTId + "<>' onclick='deleteProductType(this)'>" +  r.ProductTypes[j].firPTName + "<a class='timeA'>&times</a></span><br/>";
                         }
                         else if (r.ProductTypes[j].thiPTName == "") {
                             var str = "<span class='choices'  value='" + r.ProductTypes[j].firPTId + "|" + r.ProductTypes[j].secPTId + "|" + r.ProductTypes[j].thiPTId + "<>' onclick='deleteProductType(this)'>" + r.ProductTypes[j].firPTName + "&nbsp;|&nbsp;" + r.ProductTypes[j].secPTName + "<a class='timeA'>&times</a></span><br/>";
                         }
                         $("#productTypeDisplay").append(str);
                    	}
					}
					else{
                  //加载已关联的产品属性类型
						$('#productType').hide();
				        $('#productTypeShow').hide();
				        $('#productPropertyType').show();
				        $('#productPropertyShow').show();
                    for (var j = 0; j < r.ProductPropertyTypes.length; j++){
                    	
                         if (r.ProductPropertyTypes[j].secName != "" && r.ProductPropertyTypes[j].thiName != "") {
                             var str = "<span class='choices' value='" + r.ProductPropertyTypes[j].firId + "|" + r.ProductPropertyTypes[j].secId + "|" + r.ProductPropertyTypes[j].thiId + "<>' onclick='deletePropertyTypes(this)'>" + r.ProductPropertyTypes[j].firRemark + "&nbsp;|&nbsp;" + r.ProductPropertyTypes[j].secRemark + "&nbsp;|&nbsp;" + r.ProductPropertyTypes[j].thiRemark + "<a class='timeA'>&times</a></span><br/>";
                         }
                         else if (r.ProductPropertyTypes[j].secName == "") {
                             var str = "<span class='choices'  value='" + r.ProductPropertyTypes[j].firId + "|" + r.ProductPropertyTypes[j].secId + "|" + r.ProductPropertyTypes[j].thiId + "<>' onclick='deletePropertyTypes(this)'>" +  r.ProductPropertyTypes[j].firRemark + "<a class='timeA'>&times</a></span><br/>";
                         }
                         else if (r.ProductPropertyTypes[j].thiName == "") {
                             var str = "<span class='choices'  value='" + r.ProductPropertyTypes[j].firId + "|" + r.ProductPropertyTypes[j].secId + "|" + r.ProductPropertyTypes[j].thiId + "<>' onclick='deletePropertyTypes(this)'>" + r.ProductPropertyTypes[j].firRemark + "&nbsp;|&nbsp;" + r.ProductPropertyTypes[j].secRemark + "<a class='timeA'>&times</a></span><br/>";
                         }
                         $("#productPropertyDisplay").append(str);
                    	}
					}
                  //加载已关联的国家/地区
                    $("#RCAChioceShow").show();
                    for (var j = 0; j < r.Areas.length; j++){
                    	
                         if (r.Areas[j].areasName != "" ) {
                             var str = "<span class='choices' value='" + r.Areas[j].countriesId + "|" + r.Areas[j].areasId + "<>' onclick='deleRCAChoice(this)'>" + r.Areas[j].countriesName + "&nbsp;|&nbsp;" + r.Areas[j].areasName + "<a class='timeA'>&times</a></span><br/>";
                         }
                         else {
                             var str = "<span class='choices' value='" + r.Areas[j].countriesId + "|" + r.Areas[j].areasId + "<>' onclick='deleRCAChoice(this)'>" + r.Areas[j].countriesName + "<a class='timeA'>&times</a></span><br/>";                      	 
                         }
                         $("#RCAChioceDisplay").append(str);
                    }
                                    
					rd(methodName, Id);
                }
                else if (r == "sessionOut") {
                    doLogout();
                }
    			else{
    				alert("编辑失败！");
    			}
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } 
    //新增
    else {
        $('#productType').show();
        $('#productTypeShow').show();
        $('#productPropertyType').hide();
        $('#productPropertyShow').hide();
        InitWStyle();
    	getFirstProductType();
    	getFirstPropertyTypes();
    	InitMaterialType();
    	selectMaterial();
    	selectRegion();
    	$("#hid_rulesParamB").val("methodName=add");
		$("#txt_rulesNameB").val("");
		$("#txt_rulesContentB").val("");
		$("#txt_rulesRemarkB").val("");
    } 
}

//子表rulesdetail
function rd(methodName, Id) {
	$.ajax({
		type : "post",
		url : projectLocation
				+ "servlet/RulesDetailServlet?methodName=getRulesDetail&id="
				+ Id,
		dataType : "text",
		async : false,
		success : function(r) {
			//判断方法名是否为查看
			if (methodName != "detail") {
				if (r == "false") {
					$("#Erules").append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
					return false;
				}
				else if (r == "sessionOut") {
                    doLogout();
                }
				else{
				var data = $.parseJSON(r);
				var str = "";
				for (var i = 0; i < data.rulesDetailList.length; i++) {
					str += (("<tr><td rel='" + data.rulesDetailList[i].Id + "'>" + (i + 1) + "</td>"
					+ "<td><select style='width:130px;'><option value='all'>请选择</option>" 
					+ "<option value='商品名'>商品名</option><option value='商品价格'>商品价格</option><option value='商品品牌'>商品品牌</option>" 
					+ "<option value='商品描述'>商品描述</option> <option value='上市日期'>上市日期</option><option value='电压/频率'>电压/频率</option>" 
					+ "<option value='产品类型'>产品类型 </option> <option value='产品颜色'>产品颜色</option> <option value='耗电量'>耗电量</option> "
					+ "<option value='产品尺寸'>产品尺寸</option> <option value='产品重量'>产品重量</option></select>" 
					+ "</td>").replace("value='" + data.rulesDetailList[i].Condition + "'", "selected value='" + data.rulesDetailList[i].Condition + "'"))
					+ (("<td><select type='text'><option value='all'>请选择</option><option value='大于'>大于</option>"
					+ "<option value='大于等于'>大于等于</option><option value='小于'>小于</option><option value='小于等于'>小于等于</option>" 
					+ "<option value='等于'>等于</option><option value='不等于'>不等于</option><option value='包含'>包含</option><option value='不包含'>不包含</option></select>" 
					+ "</td>").replace("value='" + data.rulesDetailList[i].Relationship + "'", "selected value='" + data.rulesDetailList[i].Relationship + "'"))
					+ "<td><input type='text' value='" + data.rulesDetailList[i].Value + "' /></td>"
					+ "<td><input class='btn btn-default btn-xs' type='button' name='operation' value='删 除' onclick='delRow(this)' /></td></tr>";
				}
				$("#Erules").append(str);
				}
			}
			else {
				$("#RulesDetailList").html('');
				if (r == "false") {
					$("#RulesDetailList").append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
					return false;
				}
				var data = $.parseJSON(r);
				var str = "";
				for (var i = 0; i < data.rulesDetailList.length; i++) {
					str += "<tr><td> 序号 </td>"
							+ "<td>" + (i + 1) + "</td>"
							+ "<td> 查询条件 </td><td>" 
							+ data.rulesDetailList[i].Condition + "</td>"
							+ "<td> 查询关系 </td><td>"
							+ data.rulesDetailList[i].Relationship + "</td>"
							+ "<td> 查询值 </td><td>" 
							+ data.rulesDetailList[i].Value + "</td></tr>";
				}
				$("#RulesDetailList").append(str);
				}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}

//获取网站类型，编辑
function InitWStyle() {
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/RulesServlet?methodName=getWebsiteStyle",
		dataType : "json",
		async : false,
		success : function(r) {
			if (r != "false") {
//				var data = $.parseJSON(r);
				var str = "";
				for (var i = 0; i < r.WebsiteStyle.length; i++) 
				{
//					str += "<option value='" + data.WebsiteStyle[i].Id + "'>" + data.WebsiteStyle[i].Name + "</option>";
					str += "<input type='checkbox' id='txt_rulesWebStyleB' name='txt_rulesWebStyleB' " +
							"value='" + r.WebsiteStyle[i].Id + "'>" + r.WebsiteStyle[i].Name + "</input>&nbsp;&nbsp;&nbsp;";
				}
				$("#WStyleList").html(str);
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
}

// 新增行
function CreateNewLine() {
	// 判断是否在新增状态
	var i = 0;
	// 计算行
	$("#Erules tr").each(function() {
		i++;
		$(this).find("td").eq(0).html(i);
	});
	i++;
	// 添加行
	$("#Erules").append("<tr onclick='dlt(this)'>"
		+ "<td rel='0'>"
		+ i
		+ "</td>"
		+ "<td><select style='width:130px;'><option value='all'>请选择</option>" 
		+ "<option value='商品名'>商品名</option><option value='商品价格'>商品价格</option><option value='商品品牌'>商品品牌</option>" 
		+ "<option value='商品描述'>商品描述</option> <option value='上市日期'>上市日期</option><option value='电压/频率'>电压/频率</option>" 
		+ "<option value='产品类型'>产品类型 </option> <option value='产品颜色'>产品颜色</option> <option value='耗电量'>耗电量</option> "
		+ "<option value='产品尺寸'>产品尺寸</option> <option value='产品重量'>产品重量</option></select>" 
		+ "</td>"
		+ "<td><select type='text'><option value='all'>请选择</option><option value='大于'>大于</option>"
		+ "<option value='大于等于'>大于等于</option><option value='小于'>小于</option><option value='小于等于'>小于等于</option>" 
		+ "<option value='等于'>等于</option><option value='不等于'>不等于</option><option value='包含'>包含</option><option value='不包含'>不包含</option></select>" 
		+ "</td>"
		+ "<td><input type='text' /></td>"
		+ "<td><input class='btn btn-default btn-xs' type='button' name='operation' value='删 除' onclick='delRow(this)' /></td></tr>");
}

// 删除行
function delRow(obj)  {
	if($(obj).parent().parent().children().eq(0).attr("rel")!="0")
	 {
	  $.ajax({
	        type: "post",
	        url: projectLocation
						+ "servlet/RulesDetailServlet?methodName=delRow&id=" +$(obj).parent().parent().children().eq(0).attr("rel"),
	        dataType: "text",
	        async: false,
	        success: function (r) {
	            if (r == "true") {
	            	  $(obj).parent().parent().remove();
	            }
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
	 }
   else
   	$(obj).parent().parent().remove();
}

//选择产品类型或者产品属性类型
function PPPTypeChange(){
    var obj = $('#op_PPPType');
    if(obj.val() == 1){
        $('#productType').show();
        $('#productTypeShow').show();
        $('#productPropertyType').hide();
        $('#productPropertyShow').hide();
    }else{
        $('#productType').hide();
        $('#productTypeShow').hide();
        $('#productPropertyType').show();
        $('#productPropertyShow').show();
    }
}

 $(function () {   
 $("#productTypeSure").click(function () {
        // to get the chioces
        //获得三个选择框的文本内容和对应Id
        var firObj = document.getElementById('firstTypes');
        var firChoice = firObj.options[firObj.selectedIndex].text;
        var secObj = document.getElementById('secondTypes');
        var secChoice = secObj.options[secObj.selectedIndex].text;
        var thiObj = document.getElementById('thirdTypes');
        var thiChoice = thiObj.options[thiObj.selectedIndex].text;
        var firChoiceId = $("#firstTypes").val();
        var secChoiceId = $("#secondTypes").val();
        var thiChoiceId = $("#thirdTypes").val();


        if (firChoice == "请选择") {
            return;
        }
        // to show chioces
        $("#productTypeShow").show();
        if (secChoice != "请选择" && thiChoice != "请选择") {
            var str = "<span class='choices' value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='deleteProductType(this)'>" + firChoice + "&nbsp;|&nbsp;" + secChoice + "&nbsp;|&nbsp;" + thiChoice + "<a>&times</a></span>";
        }
        else if (secChoice == "请选择") {
            var str = "<span class='choices'  value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='deleteProductType(this)'>" + firChoice + "<a>&times</a></span>";
        }
        else if (thiChoice == "请选择") {
            var str = "<span class='choices'  value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='deleteProductType(this)'>" + firChoice + "&nbsp;|&nbsp;" + secChoice + "<a>&times</a></span>";
        }
        $("#productTypeDisplay").append(str);
});


    //当第一级菜单变化时，加载第二级菜单
    $("#firstTypes").change(
		function () {
		    $("#secondTypes").html('');
		    var str = "";
		    str += "<option value='0'>" + "请选择" + "</option>";
		    var firVal = $(this).val();

		    if (firVal != "0") {
		        $.ajax({
		            type: "post",
		            url: "./servlet/ProductTypeServlet?methodName=sel&id=" + firVal,
		            dataType: "text",
		            async: false,
		            success: function (r) {

		                if (r != "false") {
		                    var data = $.parseJSON(r);
		                    for (var i = 0; i < data.webList.length; i++) {
		                        str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
		                    }

		                }
		                else if (r == "sessionOut") {
		                    doLogout();

		                }
		            },
		            error: function (e) {
		                alert(e.responseText);
		            }
		        });
		    }
		    $("#secondTypes").append(str);
		    $("#thirdTypes").html("<option value='0'>请选择</option>");
		});

    //当第二级菜单发生变化时，加载第三级菜单
    $("#secondTypes").change(
		function () {
		    $("#thirdTypes").html('');
		    var str = "";
		    str += "<option value='0'>请选择</option>";
		    var secVal = $(this).val();
		    if (secVal != "0") {

		        $.ajax({
		            type: "post",
		            url: "./servlet/ProductTypeServlet?methodName=sel&id=" + secVal,
		            dataType: "text",
		            async: false,
		            success: function (r) {

		                if (r != "false") {
		                    var data = $.parseJSON(r);

		                    for (var i = 0; i < data.webList.length; i++) {
		                        str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
		                    }

		                }
		                else if (r == "sessionOut") {
		                    doLogout();

		                }
		            },
		            error: function (e) {
		                alert(e.responseText);
		            }
		        });

		    }
		    $("#thirdTypes").append(str);
		});
});

function getFirstProductType(){
	$("#firstTypes").html('');
	$("#secondTypes").html('');
	$("#thirdTypes").html('');
	var str = "";
	str +=  "<option value='0'>" + "请选择" + "</option>";
	$("#secondTypes").append(str);
	$("#thirdTypes").append(str);
	
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
	$("#firstTypes").append(str);
	
}


// delete the selected 产品类型  
function deleteProductType(obj){
	// delete current span
	$(obj).remove();
	// if current content is null,to hide the 商品描述
    if ($("#productTypeDisplay").children("span").length <= 0)
    {
    	$("#productTypeShow").hide();
    }
}

function InitMaterialType(){
	$("#op_RulesMaterialType").html("<option value='0'>所有原料类型</option>");
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
				$("#op_RulesMaterialType").append(str);
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
}

function selectMaterial(){
	$("#op_RulesMaterial").html("<option value='0'>请选择</option>");
	var materialTypeId=$("#op_RulesMaterialType option:selected").val();
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
	$("#op_RulesMaterial").append(str);
}

$(function () {
	 $("#materialSure").click(function () {
	        // to get the chioces
	        var firObj = document.getElementById('op_RulesMaterial');
	        var firChoice = firObj.options[firObj.selectedIndex].text;
	        var firChoiceId = $("#op_RulesMaterial").val();

	        // to show chioces
	        $("#materialShow").show();
	        
	        var str = "<span class='choices'  value='" + firChoiceId + "<>' onclick='deleteMaterial(this)'>" + firChoice + "&nbsp;<a>&times</a></span>";
	        
	        $("#materialDisplay").append(str);
	});
});	 
	 
//delete the selected 原料描述  
function deleteMaterial(obj){
	// delete current span
	$(obj).remove();
	// if current content is null,to hide the 原料描述
    if ($("#materialDisplay").children("span").length <= 0)
    {
    	$("#materialShow").hide();
    }
}


// 保存
function DetailSave() {
	// 主表信息保存
	var Name = $("#txt_rulesNameB").val();
	var Content = $("#txt_rulesContentB").val();
	var Remark = $("#txt_rulesRemarkB").val();
	//产品类型
	 var obj = $('#op_PPPType');
	 if(obj.val() == 1)
		  $('#productPropertyDisplay').html('');
	 else
		 $('#productTypeDisplay').html('');
	 var strProductTypes = ""
		 $("#productTypeDisplay span").each(function () {
			 strProductTypes += $(this).attr("value");
		 });
	 	if (strProductTypes != "") {
	 		strProductTypes = strProductTypes.substring(0, strProductTypes.length - 2);
				 }
	var productTypesLength = $("#productTypeDisplay").children("span").length;
	//产品属性类型		 
	var strProductProperties = ""
		$("#productPropertyDisplay span").each(function () {
			strProductProperties += $(this).attr("value");
			});
		if (strProductProperties != "") {
			strProductProperties = strProductProperties.substring(0, strProductProperties.length - 2);
			}
	var productPropertiesLength = $("#productPropertyDisplay").children("span").length;
	//原料
	var strMaterials = ""
		$("#materialDisplay span").each(function () {
			strMaterials += $(this).attr("value");
			});
		if (strMaterials != "") {
			strMaterials = strMaterials.substring(0, strMaterials.length - 2);
			}
	var materialsLength = $("#materialDisplay").children("span").length;
	//国家/地区
	var strRCAChioces = ""
		$("#RCAChioceDisplay span").each(function () {
			strRCAChioces += $(this).attr("value");
			});
		if (strRCAChioces != "") {
			strRCAChioces = strRCAChioces.substring(0, strRCAChioces.length - 2);
			}
	var rcaChiocesLength = $("#RCAChioceDisplay").children("span").length;
	
	//记录勾选的网站类型
	var WebStyle="";
	var calStyle=document.getElementsByName("txt_rulesWebStyleB");
	    for(i=0;i<calStyle.length;i++){
		if(calStyle[i].checked)
			//多个网站类型之间用"_"分隔
			WebStyle+=calStyle[i].value+"_";
 		}
	//判断网站类型是否为空
	if(WebStyle!=""){
			//删除字符串最后一位"_"
			WebStyle = WebStyle.substring(0,WebStyle.length -1);
		}
	else{
		alertText('缺少网站类型！',5000);
	}
	if ($.trim(Name) == "") {
		alert("提醒：类型名不能为空！");
		return false;
	}
	//子表信息保存
	var RulesDetail = "";
	$("#Erules tr").each(function() {
		var rulesdetailid = $(this).find("td").eq(0).attr("rel");
		var Condition = $(this).find("td").eq(1).children().val();
		var Relationship = $(this).find("td").eq(2).children().val();
		var Value = $(this).find("td").eq(3).children().val();
		// 子表字段集合累加
		RulesDetail += rulesdetailid + "_" + Condition + "_" + Relationship + "_" + Value + "|";
	});
	if (RulesDetail != "") 
	{ 
		RulesDetail = RulesDetail.substring(0, RulesDetail.length - 1); 
	}
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/RulesServlet?methodName="
				+ getParam("hid_rulesParamB", "methodName") + "&id="
				+ getParam("hid_rulesParamB", "id") + "&name=" + encode(Name)
				+ "&content=" + encode(Content)	+ "&websiteStyle=" + encode(WebStyle) 
				+ "&remark=" + encode(Remark) + "&RulesDetail=" + RulesDetail+ "&ProductTypes=" + strProductTypes 
				+ "&productTypesLength=" + productTypesLength 
				+ "&ProductProperties=" + strProductProperties + "&productPropertiesLength=" + productPropertiesLength 
				+ "&Materials=" + strMaterials + "&materialsLength=" + materialsLength 
				+ "&RCAChioces=" + strRCAChioces + "&rcaChiocesLength=" + rcaChiocesLength,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "flase") {
				alertText("保存失败！", 3500);
			}
			else {
				alertText("保存成功！", 3500);
				var data = $.parseJSON(r);
				//更新规则设定列表页面
				UpdateRules(data);
                $('#rulesDom_Edit').animate({ width: "hide" });
                $("#rulesDom").show();
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}

//更新规则设定列表页面
function UpdateRules(data){
    var str = "";
    //新增保存时新增一行全新的数据
    if(getParam("hid_rulesParamB", "methodName") == "add"){
        str += GetRulesEntry(data, '*');
        $("#rulesList").find('.noDataSrc').remove();
        $("#rulesList").prepend(str);
    }
    //编辑保存时更新所在行的数据
    else if(getParam("hid_rulesParamB", "methodName") == "edit"){
        var obj =  $("#rulesList").find('.editObject').find('td');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.Content);
        obj.eq(3).html(data.Remark);
        obj.eq(4).html(data.CreateTime.substring(0, 19));
        $("#rulesList").find('.editObject').removeClass('editObject');
    }
}
