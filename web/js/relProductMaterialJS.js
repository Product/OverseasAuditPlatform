/**
 *  商品对应配料
 */

//显示商品对应配料
function showProductMaterial(productId){
	$("#RelProductMaterial").show();
	$("#productListDom").hide();
	$("#hid_relProductId").val(productId);
	refreshRelProductmaterial(productId);
}

//新增商品对应配料
function addProductMaterial(){
	$("#hid_relProductMaterialMethodName").val('add');
	$("#RelProductMaterialEdit").show();
	$("#RelProductMaterial").hide();
	$("#RelProductMaterialEdit .materialContent").html('');
	$("#RelProductMaterialEdit .unit").html('');
	
	$.ajax({
		url : "./servlet/MaterialsServlet?methodName=AllQueryList",
		dataType : "text",
		async : false,
		success : function(r) { 
			if(r == "false"|| r == "")
			{
			}
			else if(r =="sessionOut"){
            	doLogout();
            }
			else {
				var data = $.parseJSON(r);
				var str = ""
				for(var i = 0; i<data.DataList.length; i++){
					str += "<option value='" + data.DataList[i].Id + "'>" + data.DataList[i].Name + "</option>";
				}
				$("#RelProductMaterialEdit .material").html(str);
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}

//编辑商品对应配料
function editProductMaterial(obj){
	$("#hid_relProductMaterialId").val($(obj).parent().parent().attr('id'));
	$("#RelProductMaterialEdit").show();
	$("#RelProductMaterial").hide();
	$("#hid_relProductMaterialMethodName").val('edit');
	$("#RelProductMaterialEdit .materialContent").val('');
	$("#RelProductMaterialEdit .unit").val('');
	
	$.ajax({
		url : "./servlet/MaterialsServlet?methodName=AllQueryList",
		dataType : "text",
		async : false,
		success : function(r) { 
			if(r == "false"|| r == "")
			{
			}
			else if(r =="sessionOut"){
            	doLogout();
            }
			else {
				var data = $.parseJSON(r);
				var str = ""
				for(var i = 0; i<data.DataList.length; i++){
					str += "<option value='" + data.DataList[i].Id + "'>" + data.DataList[i].Name + "</option>";
				}
				$("#RelProductMaterialEdit .material").html(str);
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
	$("#RelProductMaterialEdit .material").val($(obj).parent().parent().attr('matId'));
	$("#RelProductMaterialEdit .materialContent").val($(obj).parent().parent().find(".materialContent").html());
	$("#RelProductMaterialEdit .unit").val($(obj).parent().parent().find(".unit").html());
}

//保存
function saveProductmaterial(){
	var id = $("#hid_relProductMaterialId").val();
	var productId = $("#hid_relProductId").val();
	var materialId = $("#RelProductMaterialEdit .material").val();
	var materialContent = $("#RelProductMaterialEdit .materialContent").val();
	var unit = encode($("#RelProductMaterialEdit .unit").val());
	var methodName = $("#hid_relProductMaterialMethodName").val();
	
	$.ajax({
		url: "./servlet/RelProductMaterialServlet?methodName=" + methodName + "&id=" + id + "&productId=" + productId + "&materialId=" + materialId
			 + "&materialContent=" + materialContent + "&unit=" + unit,
		dataType : "text",
		async : false,
		success : function(r) {
			if(r == "true"){
				$("#RelProductMaterialEdit").hide();
				$("#RelProductMaterial").show();
				refreshRelProductmaterial($("#hid_relProductId").val());
			}else if(r == "sessionout"){
				doLogout();
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}

//删除商品对应配料
function delProductMaterial(obj){
	var id = $(obj).parent().parent().attr("id");
	$.ajax({
		url: "./servlet/RelProductMaterialServlet?methodName=del&id=" + id,
		dataType : "text",
		async : false,
		success : function(r) {
			if(r == "true"){
				refreshRelProductmaterial($("#hid_relProductId").val());
			}else if(r == "sessionout"){
				doLogout();
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}

//刷新商品对应配料页面
function refreshRelProductmaterial(productId){
	$.ajax({
		url : "./servlet/RelProductMaterialServlet?methodName=QueryList&productId=" + productId,
		dataType : "text",
		async : false,
		success : function(r) { 
			if(r == "false"|| r == "")
			{
			}
			else if(r =="sessionOut"){
            	doLogout();
            }
			else {
				$("#RelProductMaterial .productMaterialList tbody").html("");
				var data = $.parseJSON(r);
				var str = ""
				if (data.DataList.length == 0) {
					str += "<tr><td colspan=4>无数据!</td></tr>"
				}
				else {
					for(var i = 0; i<data.DataList.length; i++){
						productId = $("#hid_relProductId").val();
						str += "<tr id='" + data.DataList[i].Id + "' matId='" + data.DataList[i].MaterialId + "'>"
							+  "<td>" + data.DataList[i].MaterialName + "</td>"
							+  "<td class='materialContent'>" + data.DataList[i].MaterialContent + "</td>"
							+  "<td class='unit'>" + data.DataList[i].Unit + "</td>"
							+  "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='editProductMaterial(this)'/>&nbsp;"
							+  "<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='delProductMaterial(this)' /></td>";
						+  "</tr>";
					}
				}
				$("#RelProductMaterial .productMaterialList tbody").html(str);

			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}