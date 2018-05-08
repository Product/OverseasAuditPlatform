//add by lxt
//监管情况纵览模块所需JS函数
function setTotals(country){
	var country = country == undefined ? "":country;
	var strChoices = getChoices();
	var choicesLength = $("#chioceDisplay").children("span").length;

	url = "./servlet/RegulatoryServlet?methodName=getTotals&country="+country+"&strChoices="+strChoices+"&choicesLength="+choicesLength;
	$.ajax({
		  type: "post",
          url: url,
          contentType: "application/json;charset=utf-8",
          async: false,
	        success: function (r) {
	           
	           if(r != "false")
	            {
	            	data = $.parseJSON(r);
	            	$("#websiteNum").html(data.websiteTotal==null?"--":data.websiteTotal);
	            	$("#productNum").html(data.productTotal==null?"--":data.productTotal);
	            	$("#productTypeNum").html(data.productTypeTotal==null?"--":data.productTypeTotal);
	            	$("#brandNum").html(data.brandTotal==null?"--":data.brandTotal);
	            	$("#productDefineNum").html(data.productDefineTotal==null?"--":data.productDefineTotal);
	            }			
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
	
	
}
//add by lxt,获取网站数据
function getWebsiteData(strChoices,choicesLength){
	var data = "";
	$.ajax({
		  type: "post",
            url: "./servlet/RegulatoryServlet?methodName=getWebsiteInfoByCountry&Choices=" + strChoices + "&choiceslength=" + choicesLength,
            contentType: "application/json;charset=utf-8",
            async: false,
	        success: function (r) {
	           
	            if (r == "false") {
	            	
	              
	            }else if(r =="sessionOut"){
	            	indexDoLogout();
	            }
	            else
	            	{
	            	data = $.parseJSON(r);
	            	mapLoading(data.webCNTList, data.webCNVList);
	            	$(data.webCNVList).each(function (i) {
	                    codeAddress(data.webCNVList[i].name, "有" + data.webCNVList[i].value + "个网站。<input type='button' value='返回' onclick='map.setZoom(3);' />");
	            	});
	                $(data.webCNTList).each(function (i) {
	                    codeAddress(data.webCNTList[i].name, " " + data.webCNTList[i].title + "。<input type='button' value='返回' onclick='map.setZoom(3);' />");
	                });
	            	
	            	}	
	           
	
				
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
	
}

//add by lxt
//加载品牌数据
function getBrandData(strChoices,choicesLength){
	var data ="";
	$.ajax({
		  type: "post",
            url: "./servlet/RegulatoryServlet?methodName=getBrandInfoByCountry&Choices=" + strChoices + "&choiceslength=" + choicesLength,
            contentType: "application/json;charset=utf-8",
            async: false,
	        success: function (r) {
	           
	            if (r == "false") {
	            	
	              
	            }else if(r =="sessionOut"){
	            	indexDoLogout();
	            	
	            }
	          
	            else
	            	{
	            	  data = $.parseJSON(r);
	            	  mapLoading(data.brandCNTList, data.brandCNVList);
	            		$(data.brandCNVList).each(function (i) {
	            	        codeAddress(data.brandCNVList[i].name, "有" + data.brandCNVList[i].value + "个品牌。<input type='button' value='返回' onclick='map.setZoom(3);' />");
	            		});
	            	    $(data.brandCNTList).each(function (i) {
	            	        codeAddress(data.brandCNTList[i].name, " " + data.brandCNTList[i].title + "。<input type='button' value='返回' onclick='map.setZoom(3);' />");
	            	    });
	            	 
	            	}	
	        
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
	
}

//add by lxt 加载商品数据
function getProductData(strChoices,choicesLength){
	
		 var data ="";
		 $.ajax({
				  type: "post",
		            url: "./servlet/RegulatoryServlet?methodName=getProductInfoByCountry&Choices=" + strChoices + "&choiceslength=" + choicesLength,
		            contentType: "application/json;charset=utf-8",
		            async: false,
			        success: function (r) {
			           
			            if (r == "false") {
			            	
			              
			            }else if(r =="sessionOut"){
			            	indexDoLogout();
			            	
			            }
			          
			            else
			            	{
			            	  data = $.parseJSON(r);
			            	  mapLoading(data.proCNTList, data.proCNVList);
			            	  $(data.proCNVList).each(function (i) {
			            	        codeAddress(data.proCNVList[i].name, "有" + data.proCNVList[i].value + "个商品。<input type='button' value='返回' onclick='map.setZoom(3);' />");
			            		});
			            	    $(data.proCNTList).each(function (i) {
			            	        codeAddress(data.proCNTList[i].name, " " + data.proCNTList[i].title + "。<input type='button' value='返回' onclick='map.setZoom(3);' />");
			            	    });
			            	 
			            	}	
			         
			        },
			        error: function (e) {
			            alert(e.responseText);
			        }
			    });
		
}

//add by lxt,加载商品分类数据
function getProductTypeData(strChoices,choicesLength){
	var data ="";
	$.ajax({
		  type: "post",
            url: "./servlet/RegulatoryServlet?methodName=getProductTypeInfoByCountry&Choices=" + strChoices + "&choiceslength=" + choicesLength,
            contentType: "application/json;charset=utf-8",
            async: false,
	        success: function (r) {
	           
	            if (r == "false") {
	            	
	              
	            }
	            else if(r =="sessionOut"){
	            	indexDoLogout();
	            	
	            }
	          
	            else
	            	{
	            	  data = $.parseJSON(r);
	            	 					
	            	  mapLoading(data.proTypeCNTList, data.proTypeCNVList);	            	   
	            	 $(data.proTypeCNVList).each(function (i) {
			            	        codeAddress(data.proTypeCNVList[i].name, "有" + data.proTypeCNVList[i].value + "种产品类别。<input type='button' value='返回' onclick='map.setZoom(3);' />");
			            		});				  
	            	  $(data.proTypeCNTList).each(function (i) {
	            	        codeAddress(data.proTypeCNTList[i].name, " " + data.proTypeCNTList[i].title + "。<input type='button' value='返回' onclick='map.setZoom(3);' />");
	            	    });
	            	 
	            	}	
	      
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
	
}

function getProductDefineData(strChoices,choicesLength){
	var data ="";
	$.ajax({
		  type: "post",
            url: "./servlet/RegulatoryServlet?methodName=getProductDefineInfoByCountry&Choices=" + strChoices + "&choiceslength=" + choicesLength,
            contentType: "application/json;charset=utf-8",
            async: false,
	        success: function (r) {
	           
	            if (r == "false") {
	            	
	              
	            }
	            else if(r =="sessionOut"){
	            	indexDoLogout();
	            	
	            }
	          
	            else
	            	{
	            	  data = $.parseJSON(r);
	            	 					
	            	  mapLoading(data.proDefCNTList, data.proDefCNVList);	            	   
	            	 $(data.proDefCNVList).each(function (i) {
			            	        codeAddress(data.proDefCNVList[i].name, "有" + data.proDefCNVList[i].value + "个产品。<input type='button' value='返回' onclick='map.setZoom(3);' />");
			            		});				  
	            	  $(data.proDefCNTList).each(function (i) {
	            	        codeAddress(data.proDefCNTList[i].name, " " + data.proDefCNTList[i].title + "。<input type='button' value='返回' onclick='map.setZoom(3);' />");
	            	    });
	            	 
	            	}	
	      
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
}

function RegulatoryMapInit(){
	var strChoices = getChoices();
	var choicesLength = $("#chioceDisplay").children("span").length;
	
	if($('#hid_subModuleInfo').val() == "website")//获取网站数据
	{
		getWebsiteData(strChoices,choicesLength);		
	}
	else if($('#hid_subModuleInfo').val() == "brand")//获取品牌数据
		{
			getBrandData(strChoices,choicesLength);
		}
	else if($('#hid_subModuleInfo').val() == "product")//获取商品数据
		{
			getProductData(strChoices,choicesLength);
		}
	else if($('#hid_subModuleInfo').val() == "productType")//获取商品类型数据
		{
			getProductTypeData(strChoices,choicesLength);
		}
	else if($('#hid_subModuleInfo').val() == "productDefine"){	//获取产品数据
		getProductDefineData(strChoices,choicesLength);
	}
}

function initWebsiteNum(strChoices,choicesLength){
	var num = 0;
	$.ajax({
        type: "post",
        url: "./servlet/RegulatoryServlet?methodName=WebsiteNumInit&Choices=" + strChoices + "&choiceslength=" + choicesLength,
        dataType: "text",
        async: false,
        success: function (r) {
            num = r;
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
    return num;
}

function initProductDefinitionNum(strChoices,choicesLength){
	var num = 0;
	$.ajax({
        type: "post",
        url: "./servlet/RegulatoryServlet?methodName=ProductDefNumInit&Choices=" + strChoices + "&choiceslength=" + choicesLength,
        dataType: "text",
        async: false,
        success: function (r) {
            num = r;
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
    return num;
}

function initProductNum(strChoices,choicesLength){
	var num = 0;
	$.ajax({
        type: "post",
        url: "./servlet/RegulatoryServlet?methodName=ProductNumInit&Choices=" + strChoices + "&choiceslength=" + choicesLength,
        dataType: "text",
        async: false,
        success: function (r) {
            num = r;
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
    return num;
}

function initBrandNum(strChoices,choicesLength){
	var num = 0;
	$.ajax({
        type: "post",
        url: "./servlet/RegulatoryServlet?methodName=BrandNumInit&Choices=" + strChoices + "&choiceslength=" + choicesLength,
        dataType: "text",
        async: false,
        success: function (r) {
            num = r;
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
    return num;
}

function initProductTypeNum(strChoices,choicesLength){
	var num = 0;
	$.ajax({
        type: "post",
        url: "./servlet/RegulatoryServlet?methodName=ProductTypeNumInit&Choices=" + strChoices + "&choiceslength=" + choicesLength,
        dataType: "text",
        async: false,
        success: function (r) {
            num = r;
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
    return num;
}

