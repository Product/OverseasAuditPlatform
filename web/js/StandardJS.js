/**
 * 规则库管理 演示
 */
$(function () {
	//显示和隐藏配料范围的输入框
	$("#StandardDom_Edit .materialRange").change(function(){
		changeMaterialRange($(this).val());
	});
	
	//类别切换
	$("#StandardDom_Edit .ruleType").change(function(){
		if($(this).val() == 0){//配方
			$("#StandardDom_Edit .materialRange").parent().show();
			$("#StandardDom_Edit .hasKeyWord").parent().hide();
			$("#AssistTd").show();
			$("#ruleTable .materialRangeBegin").parent().parent().show();
			$("#ruleTable .materialRangeUnit").parent().parent().show();
			changeMaterialRange($("#ruleTable .materialRange").val());
			
			$("#impact-category").show();//影响类别
			$("#parentName").hide();//父类名称
			$("#childName").hide();//子类名称
			$("#KeyWordTr").show();
			$("#AssistTd").hide();
			$("#ownership-category").hide();//归属类别
			$("#containsKeyWord").hide();//包含关键字
			$("#discontainsKeyWord").hide();
		}else if($(this).val() == 1){//分类  
			$("#StandardDom_Edit .materialRange").parent().hide();
			$("#StandardDom_Edit .hasKeyWord").parent().show();
			$("#ruleTable .materialRangeBegin").parent().parent().hide();
			$("#ruleTable .materialRangeUnit").parent().parent().hide();
			
			$("#AssistTd").show();
			$("#KeyWordTr").hide();
			$("#AssistTr").hide();
			$("#impact-category").hide();//影响类别
			$("#parentName").hide();//父类名称
			$("#childName").hide();//子类名称
			$("#ownership-category").show();//归属类别
			$("#containsKeyWord").show();//包含关键字
			$("#discontainsKeyWord").show();
		}else if($(this).val() == 2){//父类
			$("#StandardDom_Edit .materialRange").parent().hide();
			$("#StandardDom_Edit .hasKeyWord").parent().hide();
			$("#AssistTd").show();
			$("#ruleTable .materialRangeBegin").parent().parent().hide();
			$("#ruleTable .materialRangeUnit").parent().parent().hide();
			
			$("#impact-category").hide();//影响类别
			$("#parentName").show();//父类名称显示
			$("#childName").show();//子类名称
			$("#KeyWordTr").hide();
			$("#AssistTr").show();
			$("#AssistTr").find("td").eq(0).hide();
			$("#AssistTr").find("td").eq(1).hide();
			$("#ownership-category").hide();//归属类别
			$("#containsKeyWord").hide();//包含关键字
			$("#discontainsKeyWord").hide();
		}else if($(this).val() == 3){//禁止商品
			$("#StandardDom_Edit .materialRange").parent().hide();
			$("#StandardDom_Edit .hasKeyWord").parent().show();
			$("#AssistTd").show();
			$("#ruleTable .materialRangeBegin").parent().parent().hide();
			$("#ruleTable .materialRangeUnit").parent().parent().hide();
			
			$("#KeyWordTr").hide();
			$("#AssistTr").hide();
			$("#impact-category").hide();//影响类别
			$("#parentName").hide();//父类名称
			$("#childName").hide();//子类名称
			$("#ownership-category").hide();//归属类别
			$("#containsKeyWord").show();//包含关键字 
			$("#discontainsKeyWord").show();
		}
	});

	//新增规则
	$("#StandardDom_Edit .addNew_btn").click(function() {
		$("#StandardDom_Edit .standardDetailPreview").hide();
		$("#StandardDom_Edit .standardNormalTable").show();
		$("#StandardDom_Edit .materialRange").parent().show();

		$('#ruleTable .ruleName').removeAttr("readonly");//去除规则名称的readonly属性
		$("#hid_standardRuleId").val("addRule");
		$("#ruleTable .ruleName").val("");//规则名称
		getRuleEffectType();//加载规则影响商品类别
		
		$("#ruleTable .keyWord").val("");//关键字
		$("#ruleTable .materialRange").val("0");//配料范围
		changeMaterialRange(0);
		$("#ruleTable .ruleType").val(0);
		$("#ruleTable .materialRangeBegin").val("");//配料起始范围
		$("#ruleTable .materialRangeEnd").val("");//配料最终范围
		$("#ruleTable .materialRangeUnit").val("");//配料起始范围单位
		
		$("#containsKeyWord .contain").val("");//包含关键字
		$("#discontainsKeyWord .disContain").val("");//不包含关键字
		
		$("#parentName").hide();//父类名称
		$("#childName").hide();//子类名称
		$("#ownership-category").hide();//归属类别
		$("#containsKeyWord").hide();//包含关键字
		$("#AssistTd").hide();
		$("#discontainsKeyWord").hide();

		$("#AssistTd").show();
		$("#KeyWordTr").show();
		$("#impact-category").show();//影响类别
		
		$("#StandardDom_Edit .standardMenuList span").removeClass('badge-orange');
		
	});
	
	//保存规则
	$("#StandardDom_Edit .saveStandardRule").click(function() {
		var methodName = $("#hid_standardRuleId").val();
		var ruleName = encode($("#ruleTable .ruleName").val());//规则名称
		var composition = encode($("#ruleTable .keyWord").val());//配料名称
		var ruleType = $("#ruleTable .ruleType").val();//规则类别
		var condition = "";//配料范围
		/*if(ruleType == "0"){
			condition = $("#ruleTable .materialRange").val();
		}else{
			condition = $("#ruleTable .hasKeyWord").val();
		}*/
		if(ruleType == "0"){
			condition = $("#ruleTable .materialRange").val();
			var num1 = $("#ruleTable .materialRangeBegin").val();//配料起始范围
			var num2 = $("#ruleTable .materialRangeEnd").val();
			var entity = $("#StandardDom_Edit .effectType").val();
			var fatherClass = $("#fatherClass").val("");
			var childClass =  $("#childClass").val("");
		}else if(ruleType == "1"){
			var num1 = $("#containsKeyWord .contain").val();//
			var num2 = $("#discontainsKeyWord .disContain").val();
			var entity = $("#StandardDom_Edit .ownershipType").val();
			var fatherClass = $("#fatherClass").val("");
			var childClass =  $("#childClass").val("");
		}else if(ruleType == "2"){
			var entity = $("#StandardDom_Edit .ownershipType").val();
			var fatherClass = $("#fatherClass").val();
			var childClass =  $("#childClass").val();
		}else if( ruleType == "3"){
			var num1 = $("#containsKeyWord .contain").val();//
			var num2 = $("#discontainsKeyWord .disContain").val();
			var entity = $("#StandardDom_Edit .ownershipType").val("");
			var fatherClass = $("#fatherClass").val("");
			var childClass =  $("#childClass").val("");
		}
		
		var unit = encode($("#ruleTable .materialRangeUnit").val());//配料单位
		var id = $("#hid_standardId").val();//规则id
		
		
		if ($.trim(ruleName) == "" || $.trim(ruleName) == null) {
	        alertText("规则名称不能为空！请填写规则名", 3500);
	        return false;
	    }
		$.ajax({
			type: "post",
			url: projectLocation + "servlet/RulesFileServlet?methodName=" + methodName + "&ruleName=" + ruleName + "&entity=" + entity
					+ "&composition=" + composition + "&ruleType=" + ruleType  + "&unit=" + unit + "&standard=" + id + "&condition=" + condition
					+ "&num1=" + num1 + "&num2=" + num2+"&fatherClass="+fatherClass+"&childClass="+childClass,
			contentType: "application/json;charset=utf-8",
			async: false,
			success: function (r) {
				if (r == "true") {
					alertText("保存成功！", 3500);
					$("#StandardDom_Edit .standardNormalTable").hide();
					refreshStandardRule(id);//刷新规则列表
				}else{
					alertText("保存失败！", 3500);
				}
			},
			error: function (e) {
				alert(e.responseText);
			}
		});
	});
	
	//规则返回
	$("#StandardDom_Edit .standardRuleReturn").click(function() {
		$("#StandardDom_Edit .standardNormalTable").hide();
	});

	//删除规则
	$("#StandardDom_Edit .delStandardRule").click(function() {
		var ruleName = $("#standardName").text();//规则名称
		var id = $("#hid_standardId").val();//规则id
		$.ajax({
			type: "post",
			url: projectLocation + "servlet/RulesFileServlet?methodName=delRule&ruleName=" + ruleName+"&standard="+id,
			contentType: "application/json;charset=utf-8",
			async: false,
			success: function (r) {
				if (r == "true") {
					$("#StandardDom_Edit .standardNormalTable").hide(); 
					$("#StandardDom_Edit .standardDetailPreview").hide();
					refreshStandardRule(id);//刷新规则列表
					alertText("删除成功！", 3500);
				}else{
					alertText("删除失败！", 3500);
				}
			},
			error: function (e) {
				alert(e.responseText);
			}
		});
	});
	
	//规则验证-验证
	$("#standardVerification .checkStandard").click(function() {
		var productName = encode($("#standardVerification .productName").val());
		var material = "";
		$("#standardVerification .material").each(function(i){
			material += $("#standardVerification .material").eq(i).find(".name").val() + "|" + $("#standardVerification .material").eq(i).find(".value").val()
					 + "|" + $("#standardVerification .material").eq(i).find(".unit").val() + "<>";
		});
		if(material.length > 0){
			material = encode(material.substring(0,material.length-2));
		}
		$.ajax({
			type: "post",
			url: projectLocation + "servlet/MismatchProductServlet?methodName=Test&productName=" + productName + "&material=" + material,
			contentType: "application/json;charset=utf-8",
			async: false,
			success: function(r){
				if(r == "false" || r == ""){
					alertText("验证失败");
				}else{
					r = r.substring(0,r.length-2);
					var mismatchContent = r.split("||");
					var str = "";
            		for(var i = 0; i< mismatchContent.length;i++){                			
            			var content = mismatchContent[i].split("|");
            			str += content[0] + " 标准：" + content[1] + " 本商品：" + content[2] + "&#13;&#10;";//换行
            		}
					$("#standardVerification .addTextarea").html(str);
				}
			},
			error: function (e) {
				alert(e.responseText);
			}
		});
	});
});

//跳转至规则库首页
function FirstPage_StandardView() {
	GetStandardViewList(1);
}

//跳转至规则库上一页
function PreviousPage_StandardView() {
    var pageIndex = (parseInt($("#standardsPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#standardsPaging .pageIndex").text()) - 1);
    GetStandardViewList(pageIndex);
}

//跳转至规则库下一页
function NextPage_StandardView() {
	var standardPageIndex = parseInt($("#standardsPaging .pageIndex").text());
	var standardPageCount = parseInt($("#standardsPaging .pageCount").text());
    var pageIndex = standardPageIndex == standardPageCount ? standardPageIndex : (standardPageIndex + 1);
    GetStandardViewList(pageIndex);
}

//跳转至规则库尾页
function EndPage_StandardView() {
	GetStandardViewList(parseInt($("#standardsPaging .pageCount").text()));
}

//刷新规则库
function refreshStandardPage() {
	GetStandardViewList(parseInt($("#standardsPaging .pageIndex").text()));
}

//跳转至规则库特定页
function GetStandardViewList(strPageIndex){
	var strQuery = encode($("#txt_standardQuery").val());
	$.ajax({
		type: "post",
		url: projectLocation + "servlet/StandardServlet?methodName=QueryList&system=1&strQuery=" + strQuery
				+ "&pageIndex=" + strPageIndex + "&pageSize=10",
		contentType: "application/json;charset=utf-8",
		async: false,
		success: function (r) {
			if (r == "false" || r == "") {
				$("#StandardDom .standardMainTable tbody").append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
				return false;
			}
			var data = $.parseJSON(r);
			
			$("#standardsPaging .dataCount").text(data.Total);
			$("#standardsPaging .pageCount").text(parseInt((parseInt(data.Total) + 9) / 10));
			$("#standardsPaging .pageIndex").text(strPageIndex);
			var str = "";
			for (var i = 0; i < data.DataList.length; i++) {
				str += "<tr>";
				str += "<td>" + (i+1) +"</td>";
				str += "<td>" + data.DataList[i].Name +"</td>";
				str += "<td>" + data.DataList[i].UpdateTime +"</td>";
				str += "<td>已验证"  + "</td>";//+ data.DataList[i].IsVerification
				str += "<td>";
				str += "<button class='btn btn-default btn-xs' onclick='previewStandard(\"" + data.DataList[i].Id + "\")'>预览</button>&nbsp;";
				str += "<button class='btn btn-default btn-xs' onclick='editStandard(\"" + data.DataList[i].Id + "\")'>编辑</button>&nbsp;";
				str += "<button class='btn btn-default btn-xs' onclick='confirmShow(\"" + data.DataList[i].Id + "\",delStandard,\"确定要删除吗？\")'>删除</button>&nbsp;";
				str += "<button class='btn btn-default btn-xs' onclick='editStandardRulePage(\"" + data.DataList[i].Id +"\")'>规则</button>&nbsp;";
				str += "<button class='btn btn-default btn-xs' onclick='verificationStandard(\"" + data.DataList[i].Id + "\")'>验证</button>";
				str += "</td></tr>";
			}
			$("#StandardDom .standardMainTable tbody").html(str);
		},
		error: function (e) {
			alert(e.responseText);
		}
	});
}

//预览
function previewStandard(id){
	$("#standardPreview .name").html("");//规则集名称
	$("#standardPreview .agency").html("");//依据标准
	$("#standardPreview .updateTime").html("");//更新时间
	$("#standardPreview .status").html("");//状态
	$("#standardPreview .remark").html("");//备注
	
	$("#standardPreview").show();
	$("#StandardDom").hide();
	
	$.ajax({
		type: "post",
		url: projectLocation + "servlet/StandardServlet?methodName=View&id=" + id,
		contentType: "application/json;charset=utf-8",
		async: false,
		success: function (r) {
			if (r == "false" || r== "") {
				return false;
			}
			var data = $.parseJSON(r);
			
			$("#standardPreview .name").html(data.DataList[i].Name);
			$("#standardPreview .agency").html(data.DataList[i].Agency);
			$("#standardPreview .updateTime").html(data.DataList[i].UpdateTime);
			$("#standardPreview .status").html("已验证");//状态
			$("#standardPreview .remark").html(data.DataList[i].Remark);
		},
		error: function (e) {
			alert(e.responseText);
		}
	});
}

//新增
function addStandard(){
	$("#standardEdit .addPoint").html("规则信息新增");
	$("#hid_standardEditId").val("");
	$("#standardEdit .name").val("");//规则集名称
	$("#standardEdit .agency").val("");//依据标准
	$("#standardEdit .updateTime").val("");//更新时间
	$("#standardEdit .status").val("");//状态
	$("#standardEdit .remark").val("");//备注
	
	$("#standardEdit").show();
	$("#StandardDom").hide();
}

//编辑
function editStandard(id){
	$("#standardEdit .addPoint").html("规则信息编辑");
	$("#hid_standardEditId").val(id);
	$("#standardEdit .name").val("");//规则集名称
	$("#standardEdit .agency").val("");//依据标准
	$("#standardEdit .updateTime").val("");//更新时间
	$("#standardEdit .status").val("");//状态
	$("#standardEdit .remark").val("");//备注
	
	$("#standardEdit").show();
	$("#StandardDom").hide();
	
	$.ajax({
		type: "post",
		url: projectLocation + "servlet/StandardServlet?methodName=View&id=" + id,
		contentType: "application/json;charset=utf-8",
		async: false,
		success: function (r) {
			if (r == "false" || r== "") {
				return false;
			}
			var data = $.parseJSON(r);
		
			$("#standardEdit .name").val(data.DataList[i].Name);
			$("#standardEdit .agency").val(data.DataList[i].Agency);
			$("#standardEdit .updateTime").val(data.DataList[i].UpdateTime);
			$("#standardEdit .status").val(1);
			$("#standardEdit .remark").val(data.DataList[i].Remark);
		},
		error: function (e) {
			alert(e.responseText);
		}
	});
}

//新增或编辑-保存
function saveEditStandard(){
	var methodName = "";
	if($("#hid_standardEditId").val() == ""){
		methodName = "Add";
	}else{
		methodName = "Edit";
	}

	var name = encode($("#standardEdit .name").val());//规则集名称
	var agency = encode($("#standardEdit .agency").val());//依据标准
	var updateTime = $("#standardEdit .updateTime").val();//更新时间
	var status = $("#standardEdit .status").val();//状态
	var remark = encode($("#standardEdit .remark").val());//备注
	
	$.ajax({
		type: "post",
		url: projectLocation + "servlet/StandardServlet?methodName=" + methodName + "&id=" + $("#hid_standardEditId").val()
				+ "&name=" + name + "&agency=" + agency + "&updateTime=" + updateTime + "&status=" + status + "&remark=" + remark+"&system=1",
		contentType: "application/json;charset=utf-8",
		async: false,
		success: function (r) {
			if(r == "true"){
				alertText("保存成功！", 3500);
				$("#standardEdit").hide();
				$("#StandardDom").show();
				GetStandardViewList(1);
			}
			else {
				alertText("保存失败！", 3500);
				return false;
			}
		},
		error: function (e) {
			alert(e.responseText);
		}
	});
}

//规则(库展示)
function editStandardRulePage(id){
	$("#StandardDom_Edit").show();
	$("#StandardDom").hide();
	$("#StandardDom_Edit .standardNormalTable").hide();
	$("#hid_standardId").val(id);//规则库id
	refreshStandardRule(id);// 加载左边的父类的
	$("#StandardDom_Edit .standardDetailPreview").hide();
}

//规则展示
function showStandardRule(ruleName){
	var standard =$("#hid_standardId").val();//规则id
	$.ajax({
		type: "post",
		url: projectLocation + "servlet/MismatchProductServlet?methodName=ViewRule&ruleName=" + ruleName + "&standard=" + standard,
		contentType: "application/json;charset=utf-8",
		async: false,
		success: function (r) {
			if (r == "false" || r== "") {
				return false;
			}
			else{
				var data = $.parseJSON(r);
				getRuleEffectType();//加载规则影响商品类别
				$("#StandardDom_Edit .effectType").html(''); 
				$('#ruleTable .ruleName').val(data.DataList[0].Name);
				$("#ruleTable .keyWord").val(data.DataList[0].Composition);
				$("#ruleTable .materialRangeUnit").val(data.DataList[0].Unit);
				$("#ruleTable .effectType").val(data.DataList[0].Entity);
				var Condition = data.DataList[0].Condition;
				changeMaterialRange(Condition);
				$("#ruleTable .materialRange").val(data.DataList[0].Condition);
				$('#ruleTable .materialRangeBegin').val(data.DataList[0].Num1);
				$("#ruleTable .materialRangeEnd").val(data.DataList[0].Num2);				
			}
		},
		error: function (e) {
			alert(e.responseText);
		}
	});
	$("#StandardDom_Edit .standardNormalTable").show();
	$('#ruleTable .ruleName').attr("readonly","readonly")//将规则名称设置为只读
	$("#hid_standardRuleId").val("EditRule");//记录为编辑
	$("#iframeMain").scrollTop(0);//跳至顶端
}

//刷新规则列表
function refreshStandardRule(id){
	if(id>0){
		$.ajax({
			type: "post",
			url: projectLocation + "servlet/RulesFileServlet?methodName=ruleList&standard=" + id,
			contentType: "application/json;charset=utf-8",
			async: false,
			success: function (r) {
				$("#StandardDom_Edit .standardMenuList").html("");
				if (r == "false" || r== "") {
					$("#StandardDom_Edit .standardMenuList").html('<ul><li>暂无数据，请添加规则！</li></ul>');
					return false;
				}else{
					var data = r;
					if (data.length> 0) {
						var str = "<ul>";
						for(var i=0;i<data.length;i++){
							str += "<li><span onclick='showCode(\"" +data[i].name+ "\",this)'>" + data[i].name + "</span></li><input type='hidden' id='" +data[i].name+ "' value='\"" +data[i].content+ "\"'>";
						}
						str += "</ul>";
						$("#StandardDom_Edit .standardMenuList").html(str);
	                }									
				}
			},
			error: function (e) {
				alert(e.responseText);
			}
		});
	}else{
		alert("系统内部错误！");
		return false;
	}
}

function showCode(argName,obj){
	$("#StandardDom_Edit .standardMenuList span").removeClass('badge-orange');
    $(obj).addClass('badge-orange');
    
	var content = document.getElementById(argName).value;
	$("#StandardDom_Edit .standardNormalTable").hide();
	$("#StandardDom_Edit .standardDetailPreview").show();
	
	$("#standardName").text(argName);
	$("#standardContent").text(content);
	
}

//验证
function verificationStandard(){
	$("#standardVerification .productName").html("");
	$("#standardVerification .addTextarea").html("");
	$("#standardVerification").show();
	$("#StandardDom").hide();
	$("#standardVerification .verificationInfo tbody").html("<tr class=\"twoTabNormal material\">" +
			"<td class=\"tab_td\"><input type=\"text\" class='name'></td><td class=\"tab_td\"><input type=\"text\" class='value'></td>" +
			"<td class=\"tab_td\"><input type=\"text\" class='unit'></td><td class=\"tab_td\">&nbsp;&nbsp;" +
			"<input class=\"back_btn\" type=\"button\" value=\"删  除\" onclick=\"delStandardMaterial(this)\"></td></tr>");
}

//添加配料
function addStandardMaterial(){
	$("#standardVerification .verificationInfo").append(
		"<tr class='twoTabNormal material'><td class='tab_td'><input type='text' class='name'/></td><td class='tab_td'><input type='text' class='value'/></td>" +
		"<td class='tab_td'><input type='text' class='unit'/></td><td class='tab_td'>&nbsp;&nbsp;" +
		"<input class='back_btn' class='btn btn-default btn-xs' type='button' value='删 除' onclick='delStandardMaterial(this)' /></td></tr>"
	);
}

//删除配料
function delStandardMaterial(obj){
	$(obj).parent().parent().remove();
}

//删除规则
function delStandard(id){
	$.ajax({
		type: "post",
		url:projectLocation + "servlet/StandardServlet?methodName=Del&id=" + id,
		dataType: "text",
		async: false,
		success: function (r) {
			if (r == "false" || r == '') {
				alertText("删除失败！", 3500);
			}
			else {
				alertText("删除成功！", 3500);
				refreshStandardPage();
			}
		},
		error: function (e) {
			alert(e.responseText);
		}
	});
}

//获取规则影响商品类别
function getRuleEffectType(){
	$.ajax({
		type: "post",
		url: projectLocation + "servlet/RulesFileServlet?methodName=entityList",
		dataType: "text",
		async: false,
		success: function (r) {
			if (r == "false" || r == '') {
				return false;
			}else {
				var data = $.parseJSON(r);
				var str = "";
				for(var i=0;i<data.length;i++){
					str += "<option value=\"" + data[i] + "\">" + data[i] + "</option>";
				}
				
				$("#StandardDom_Edit .effectType").html(str);
				$("#ownership-category .ownershipType").html(str); 
				$("#childClass").html(str);
				$("#fatherClass").html(str);
			}
		},
		error: function (e) {
			alert(e.responseText);
		}
	});
}

//修改配料范围的显示
function changeMaterialRange(range){
	if(range == 3 || range == 4){//间于或外于
		$("#StandardDom_Edit .materialRangeBegin").parent().parent().show();
		$("#StandardDom_Edit .materialRangeUnit").parent().parent().show();
		$("#StandardDom_Edit .materialRangeEnd").show();
		$("#StandardDom_Edit .materialRangeMark").show();
	}else{//大于、小于
		$("#StandardDom_Edit .materialRangeBegin").parent().parent().show();
		$("#StandardDom_Edit .materialRangeUnit").parent().parent().show();
		$("#StandardDom_Edit .materialRangeMark").hide();
		$("#StandardDom_Edit .materialRangeEnd").hide();
	}
}