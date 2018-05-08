/*机器学习-商品管理-商品管理 */

function FirstPage_Goods(){
    GetGoodsList(1,10);
}

function PreviousPage_Goods() {
    var pageIndex = (parseInt($("#goodsPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#goodsPaging .pageIndex").text()) - 1);
    GetGoodsList(pageIndex,10);
}

function NextPage_Goods() {
	var pageIndex = ($("#goodsPaging .pageIndex").text() == $("#goodsPaging .pageCount").text() ? parseInt($("#goodsPaging .pageIndex").text()) : parseInt($("#goodsPaging .pageIndex").text()) + 1);
    GetGoodsList(pageIndex,10);
}

function EndPage_Goods() {
	GetGoodsList(parseInt($("#goodsPaging .pageCount").text()),10);
}


/**
 * 获取商品list数据并分页
 * @param strPageIndex
 * @param pageSize
 */
function GetGoodsList(strPageIndex,pageSize) {
   var strKeyWords = $("#txt_productKeyWords").val();
   var strTypeName = $("#propertyFirstSel").val();
   var strIsMatched = $("#op_isMatched").val();
   
   
   $.ajax({
       type: "post",
       url: projectLocation
		+ "servlet/ProductServlet?methodName=matchProducts&pageIndex="+ strPageIndex+"&pageSize="+pageSize
		+"&strQuery="+encode(strKeyWords)+"&productType="+strTypeName+"&isMatched="+strIsMatched,
       contentType: "application/json;charset=utf-8",
       async: false,
       success: function (r) {
           $("#goodsManageList").html('');
           if (r == "false") {
               $("#goodsManageList")
						.append("<tr class='noDataSrc'><td colspan='7' style='text-align:center;'>无数据！</td></tr>");
               return false;
           }
           else if(r =="sessionOut"){
           	doLogout();
           	
           }
			$("#goodsPaging .dataCount").text(r.total);
			$("#goodsPaging .pageCount").text(parseInt((parseInt(r.total) + 9) / 10));
			$("#goodsPaging .pageIndex").text(strPageIndex);
			
           var str = "";
           var j = 0;
           for (var i = 0; i < r.list.length; i++) {
           	 str += GetGoodsEntry(r.list[i], i+1);
           }
           $("#goodsManageList").append(str);
       },
       error: function (e) {
           alert(e.responseText);
       }
   });
}


/**
 * 加载一级商品类型
 */
function initFirstlevelType(){
	$.ajax({
        type: "post",
        url: "./servlet/DetectingSuggestServlet?methodName=productTypeInit&parentId=",
        dataType: "text",
        async: false,
        success: function (r) {
            if (r != "false") {
                var data = $.parseJSON(r);
                var str = "<option value=''>请选择</option>";
                for (var i = 0; i < data.StyleList.length; i++) {
                    str += "<option value='" + data.StyleList[i].Id + "'>" + data.StyleList[i].Name + "</option>";
                }
                $("#propertyFirstSel").html("");
                $("#propertyFirstSel").append(str);
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
 * 当第一级菜单变化时，加载第二级菜单
 */
function typeNameFirstSel(){
    $("#propertySecondSel").html('');
    var str = "";
    str += "<option value='0'>" + "请选择" + "</option>";
    var firVal = $("#propertyFirstSel").val();//获取一级数据，显示对应二级数据

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

                }else if (r == "sessionOut") {
                    doLogout();
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    }
    $("#propertySecondSel").append(str);
    $("#propertyThirdSel").html("<option value='0'>请选择</option>");
}

/**
 * 当第二级菜单发生变化时，加载第三级菜单
 */
function typeNameSecondSel() {
	    $("#propertyThirdSel").html('');
	    var str = "";
	    str += "<option value='0'>请选择</option>";
	    var secVal = $("#propertySecondSel").val();//获取二级数据，显示对应三级数据
	    
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
	    $("#propertyThirdSel").append(str);
}


/**
 * 获取商品list数据
 * @param data
 * @param idx
 * @returns {String}
 */
function GetGoodsEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.name + "</td>";
    str += "<td>" + data.productTypeName + "</td>";
    str += "<td>" + data.brand+ "</td>";
    str += "<td>" + data.country+ "</td>";
    str += "<td>" + data.createTime.substring(0, 19)+ "</td>";
    str += "<td>" + data.isMatched+ "</td>";
    str += "<td>";
    str	+= "<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='GoodsManageEdit(\""+data.id+"\",\""+data.name+"\",\""+data.brand+"\",\""+data.countryId+"\")'/></td>";
    str += "</tr>";
    return str;
}

function GoodsManageEdit(id,name,brand,countryId){
	$("#productManage_mac").hide();
	$("#productManageEdit").show();
	getCountrylist();
	
	$("#txt_productMName").text(name);
	$("#txt_productBMName").val(brand);
	$("#op_productCMName").val(countryId);
	$("#hid_goodsid").val(id);
}

function SaveGoodsME(){
	var id= $("#hid_goodsid").val();
	var strBrand = $("#txt_productBMName").val();
	var strCountry = $("#op_productCMName").val();
	$.ajax({
        type: "post",
        url: "servlet/ProductServlet?methodName=editMachineLearning&id="+id+"&brand="+strBrand+"&country="+strCountry ,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r != "false") {
            	alertText("保存成功！", 3500);
            	GetGoodsList(1,10);
            }else if (r == "sessionOut") {
                doLogout();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//获取国家列表
function getCountrylist(){
	$.ajax({
        type: "post",
        url: "servlet/CountryServlet?methodName=getCountryList",
        dataType: "text",
        async: false,
        success: function (r) {
            if (r != "false") {
            	var str= "";
                var data = $.parseJSON(r);
                for (var i = 0; i < data.webList.length; i++) {
                    str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
                }
                $("#op_productCMName").html("");
                $("#op_productCMName").append(str);
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
