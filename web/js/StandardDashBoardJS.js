/**
 * 匹配标准JS
 */
$(function () {
	var standardPageSize = 10;//默认每页10条记录
	
	//====== 不匹配商品展示 ======
	$("#standard_page .load").click(function(){//加载点击
		mismatchProductLoading();
	});
	
	
	$("#standard_page .select").click(function(){//搜索点击
		getStandardsList(standardPageSize,1);
	});

	$("#standard_page .firstPage").click(function(){//首页
		getStandardsList(standardPageSize,1);
	});
	
	$("#standard_page .previousPage").click(function(){//上一页
		var pageIndex = parseInt($("#standard_page .pageIndex").html()) - 1;
		if(pageIndex < 1){
			pageIndex = 1;
		}
		getStandardsList(standardPageSize,pageIndex);
	});
	
	$("#standard_page .nextPage").click(function(){//下一页
		var pageIndex = parseInt($("#standard_page .pageIndex").html()) + 1;
		var pageCount = parseInt($("#standard_page .pageCount").html());
		if(pageIndex > pageCount){
			pageIndex = pageCount;
		}
		getStandardsList(standardPageSize,pageIndex);
	});
	
	$("#standard_page .endPage").click(function(){//尾页
		var pageIndex = parseInt($("#standard_page .pageCount").html());
		getStandardsList(standardPageSize,pageIndex);
	});
	
	$("#standard_page .mismatchStandards").change(function(){//全选全不选
		if($(this).prop("checked")){
			$("#standard_page .mismatchStandard").prop("checked",true);
		}else{
			$("#standard_page .mismatchStandard").prop("checked",false);
		}
	});
	
	//======= 类似商品 ======
	$("#similarStandard_page .firstPage").click(function(){//首页
		getSimilarStandardList(standardPageSize,1);
	});
	
	$("#similarStandard_page .previousPage").click(function(){//上一页
		var pageIndex = parseInt($("#similarStandard_page .pageIndex").html()) - 1;
		if(pageIndex < 1){
			pageIndex = 1;
		}
		getSimilarStandardList(standardPageSize,pageIndex);
	});
	
	$("#similarStandard_page .nextPage").click(function(){//下一页
		var pageIndex = parseInt($("#similarStandard_page .pageIndex").html()) + 1;
		var pageCount = parseInt($("#similarStandard_page .pageCount").html());
		if(pageIndex > pageCount){
			pageIndex = pageCount;
		}
		getSimilarStandardList(standardPageSize,pageIndex);
	});
	
	$("#similarStandard_page .endPage").click(function(){//尾页
		var pageIndex = parseInt($("#similarStandard_page .pageCount").html());
		getSimilarStandardList(standardPageSize,pageIndex);
	});
	
	$("#similarStandard_page .mismatchStandards").change(function(){//全选全不选
		if($(this).prop("checked")){
			$("#similarStandard_page .mismatchStandard").prop("checked",true);
		}else{
			$("#similarStandard_page .mismatchStandard").prop("checked",false);
		}
	});
	
	$("#similarStandard_page .btn_Close").click(function(){//退出类似商品展示
		$("#similarStandard_page tbody").html("");//清空数据
		$("#similarStandard_page").hide();
		$("#standard_page").show();
	});
	
	//====== 配料详细展示 ======
	
	$("#standardContents .btn_Close").click(function(){//关闭配料展示菜单
		$("#standardContents tbody").html("");//清空数据
		$("#standardContents").hide();
		var page = $("#standardContents .btn_Close").attr('rel');
		$("#" + page).show();
	});
});

//搜索不符合标准的所有商品的信息
function getStandardsList(pageSize,pageIndex){
	var standardId = "";
	var choiceLength = encode($("#standard_page .chioceDisplay span").length);
	var choices = ""; 
	
	$("#standard_page .standardChioceDisplay span").each(function () {
		standardId += $(this).attr("value") + "|";
	});
	
	if (standardId != "") {
		standardId = encode(standardId.substring(0, standardId.length - 1));
	}

	$("#standard_page .chioceDisplay span").each(function () {
		choices += $(this).attr("value");
	});
	
	if (choices != "") {
		choices = encode(choices.substring(0, choices.length - 2));
	}
	
	$.ajax({
        type: "post",
        url: "./servlet/MismatchProductServlet?methodName=QueryList&module=ByStandardId&pageSize=" + pageSize + "&pageIndex=" + pageIndex + "&standardId=" + standardId + "&choiceLength=" + choiceLength + "&choices=" + choices +"&orderBy=&orderType=",
        dataType: "text",
        async: false,
        success: function (r) {
        	var pageCount = 1;//页码值
        	var dataCount = 0;//记录总数
        	var str = "<tr><td colspan='6' style='text-align:center;'>暂无数据</td><tr>";
        	$("#standard_page .mismatchStandards").prop("checked",false);//清空全选框

        	if(r == "false" || r == ""){//查找失败
        		$("#standard_page .pageTotal").html('0');
        	}
        	else if (r == "sessionOut") {//session过期
        		indexDoLogout();
        	}
        	else {
                var data = $.parseJSON(r);
                var standardMessage = $("#standard_page .standardChioceDisplay").text();
                if(data.DataList.length>0){
                	str = "";
                	$(data.DataList).each(function(){
                		str += "<tr rel='" + this.Id + "'>" 
                			+  "<td><input type='checkbox' class='mismatchStandard' value='" + this.Id + "'></td>"
                			+  "<td>" + this.ProductName + "</td>"//商品名称
                			+  "<td>" + this.WebName + "</td>"//网站名称
                			+  "<td><a href='" + this.Url + "' target='_blank'>" + this.Url + "</td>";//网站地址
                		var mismatchContent = this.MismatchContent.split("||");
                		str += "<td>";
                		for(var i = 0; i< mismatchContent.length;i++){                			
                			if(i < 2){
                				var content = mismatchContent[i].split("|");
                				str += content[0] + " 标准：" + content[1] + " 本商品：" + content[2] + "<br/>";
                			}else{
                				str += "<button class='btn btn-default btn-xs' type='button' onclick='showAllContents(this)' class='btn btn-success btn-sm'>查看详情</button>";
                				break;
                			}
                		}
                	
                		str += "</td>";//不匹配的内容
                		str += "<td><button class='btn btn-default btn-xs' type='button' onclick='delMismatchStandard(this)' class='btn btn-success btn-sm'>删 除</button>";
                		str += "<button class='btn btn-default btn-xs' type='button' onclick='similarStandard(this)' class='btn btn-success btn-sm'>搜索类似</button></br>"
                		str	+= "<button class='btn btn-default btn-xs' type='button' onclick='newsPublish(\""+this.ProductName+"\",\""+this.MismatchContent+"\",\""+this.CreateTime+"\",\""+ standardMessage +"\",\""+this.WebName+"\")'>发 布</button></td>";
                		str += "</tr>";
                	});
                }
                if(data.Total != ""){
                	pageCount = parseInt((data.Total-1)/10) + 1;
                	dataCount = data.Total;
                }
            }
        	$("#standard_page .pageCount").html(pageCount);
        	$("#standard_page .dataCount").html(dataCount);
        	$("#standard_page .pageIndex").html(pageIndex);
        	$("#standard_page .productList tbody").html(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//从不匹配商品表中删除一条记录
function delMismatchStandard(obj){
	var id = $(obj).parent().parent().attr("rel");
	var page = $(obj).parent().parent().parent().parent().attr('page');
	$.ajax({
		type : "post",
		url : "./servlet/MismatchProductServlet?methodName=Del&id=" + id,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "false" || r == "") {//查找失败
			} else if (r == "sessionOut") {//session过期
				indexDoLogout();
			} else if(r == "true"){
				if(page == "standard_page"){
					refreshStandardList();
				}else if(page == "similarStandard_page"){
					refreshSimilarStandardList();
				}
			}
		}
	});
}

//从不匹配商品表中删除多条记录
function delSelectedMismatchStandard(obj){
	var idList = "";
	var strPage = $(obj).attr("rel");
	$("#" + strPage + " .mismatchStandard").each(function(){
		if($(this).prop("checked")){
			idList += $(this).val() + "|";
		}
	});
	
	if(idList != ""){
		idList = idList.substring(0, idList.length -1);
	}else{
		return false;
	}
	
	$.ajax({
		type : "post",
		url : "./servlet/MismatchProductServlet?methodName=DelMul&idList=" + idList,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "false" || r == "") {
			} else if (r == "sessionOut") {
				indexDoLogout();
			} else if(r == "true"){
				if(strPage == "standard_page"){
					refreshStandardList();
				}else if(strPage == "similarStandard_page"){
					refreshSimilarStandardList();
				}
			}
		}
	});
}

// 刷新不匹配商品列表
function refreshStandardList(){
	var pageIndex = parseInt($("#standard_page .pageIndex").html());
	getStandardsList(10,pageIndex);
}

//跳转到不匹配商品列表第一页
function toStandardListFirstPage(){
	getStandardsList(10,1);
}

//展示商品配料的详细信息
function showAllContents(obj){
	var id = $(obj).parent().parent().attr("rel");
	var page = $(obj).parent().parent().parent().parent().attr("page")
	$.ajax({
		type : "post",
		url : "./servlet/MismatchProductServlet?methodName=InitContent&id=" + id,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "false" || r == "") {
			} else if (r == "sessionOut") {
				indexDoLogout();
			} else{
				$("#standardContents").show();
				$("#standardContents .btn_Close").attr("rel",page);
				$("#" + page).hide();
				var data = $.parseJSON(r);
				var str = "";
				var contentsList = data.MismatchContent.split("||");
				for(var i = 0; i< contentsList.length;i++){
       				var content = contentsList[i].split("|");
       				str += "<tr><td>" + content[0] + "</td><td>" + content[1] + "</td><td>" + content[2] + "</td></tr>";
				}
				$("#standardContents tbody").html(str);
			}
		}
	});
}

//获取并展示相似商品列表
function getSimilarStandardList(pageSize,pageIndex){
	var id = $("#hid_similarStandard").val();
	$.ajax({
		type : "post",
		url : "./servlet/MismatchProductServlet?methodName=SimilarStandard&id=" + id + "&pageSize=" + pageSize + "&pageIndex=" + pageIndex,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "false" || r == "") {
			} else if (r == "sessionOut") {
				indexDoLogout();
			} else{
				var data = $.parseJSON(r);
				var pageCount = 1;//页码值
	        	var dataCount = 0;//记录数
				if(data.DataList.length>0){
					str = "";
					$(data.DataList).each(function(){
						str += "<tr rel='" + this.Id + "'>" 
							+  "<td><input type='checkbox' class='mismatchStandard' value='" + this.Id + "'></td>"
							+  "<td>" + this.ProductName + "</td>"//商品名称
							+  "<td>" + this.WebName + "</td>"//网站名称
							+  "<td><a href='" + this.Url + "' target='_blank'>" + this.Url + "</td>";//网站地址
	                	var mismatchContent = this.MismatchContent.split("||");
	                	str += "<td>";
	                	for(var i = 0; i< mismatchContent.length;i++){                			
	                		if(i < 2){
	                			var content = mismatchContent[i].split("|");
	                			str += content[0] + " 标准：" + content[1] + " 本商品：" + content[2] + "<br/>";
	                		}else{
	                			str += "<button class='btn btn-default btn-xs' type='button' onclick='showAllContents(this)' class='btn btn-success btn-sm'>查看详情</button>";
	                			break;
	                		}
	                	}
	                	str += "</td>";//不匹配的内容
                		str += "<td><button class='btn btn-default btn-xs' type='button' onclick='delMismatchStandard(this)' class='btn btn-success btn-sm'>删 除</button></td>";
	                	str += "</tr>";
	               	});
					if(data.Total != ""){
	                	pageCount = parseInt((data.Total -1)/10) + 1;
	                	dataCount = data.Total;
	                }
	            }
	            $("#similarStandard_page .pageCount").html(pageCount);
	            $("#similarStandard_page .dataCount").html(dataCount);
	            $("#similarStandard_page .pageIndex").html(pageIndex);
	            $("#similarStandard_page .productList tbody").html(str);
	            $("#similarStandard_page").show();
	            $("#standard_page").hide();
	            
			}
		}
	});
}

//搜索类似商品
function similarStandard(obj){
	var id = $(obj).parent().parent().attr("rel");
	$("#hid_similarStandard").val(id);
	getSimilarStandardList(10,1);
}

//刷新类似列表
function refreshSimilarStandardList(){
	getSimilarStandardList(10,1);
}

//重新加载
function mismatchProductLoading(){
	$.ajax({
		type : "post",
		url : "./servlet/MismatchProductServlet?methodName=Loading&standardId=",
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "false" || r == "") {
			}
		}	
	});
}

//信息发布
function newsPublish(ProductName,MismatchContent,createTime,standardMessage,webName){
	$.ajax({
		type : "post",
		url : projectLocation+ "servlet/getInfoDiffusion?method=addByProduct&title="+encode(ProductName)+"&content="+encode(MismatchContent)+"&createTime="+createTime+"&standardMessage="+encode(standardMessage)+"&webName="+encode(webName),
		contentType: "application/json;charset=utf-8",
		async : false,
		success : function(r) {
			if(r = "true"){
				alertText("发布成功！可在信息管理模块查看",3500);
				getStandardsList(10,1);
			}else if(r == "sessionOut"){
				doLogout();
			}
		},
		error: function (e) {
            alert(e.responseText);
        }
	});
}

function alertText(str, time) {
    if (str != "") {
        $("#messgeBox .mbContent").html(str);
        $("#messgeBox").slideDown(500);
        timer = setTimeout(function () { $("#messgeBox").slideUp(500); }, time);
        $("#messgeBox").hover(
           function () {
               if (timer) clearTimeout(timer);
           },
           function () {
               timer = setTimeout(function () { $("#messgeBox").slideUp(500); }, time);
           });
    }
}