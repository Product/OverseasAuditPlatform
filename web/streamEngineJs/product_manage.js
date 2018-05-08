function sProManFirstPage() {
    GetStreamProManList(1);
}

function sProManPreviousPage() {
    var pageIndex = (parseInt($("#streamProManListPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#streamProManListPaging .pageIndex").text()) - 1);
    GetStreamProManList(pageIndex);
}

function sProManNextPage() {
    var pageIndex = ($("#streamProManListPaging .pageIndex").text() == $(
			"#streamProManListPaging .pageCount").text() ? parseInt($(
			"#streamProManListPaging .pageIndex").text()) : parseInt($(
			"#streamProManListPaging .pageIndex").text()) + 1);
    GetStreamProManList(pageIndex);
}

function sProManEndPage() {
    GetStreamProManList(parseInt($("#streamProManListPaging .pageCount").text()));
}

/*获取产品管理表项*/
function GetStreamProManEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.proType + "</td>";
    //str += "<td>" + data.features + "</td>";
    str += "<td>" + data.option + "</td>";
    str += "<td>" + data.updateTime.substring(0,19) + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='InitStreamPro(\""
			+ data.id
			+ "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
			+ data.id + "\",DelStreamProMan)' /></td>";
    str += "</tr>";
    return str;
}

/*
 函数名：GetStreamProManList
 作用：获取产品管理列表
 */
function GetStreamProManList(strPageIndex) {
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ProductFeatureServlet?methodName=queryList&pageIndex="
				+ strPageIndex + "&pageSize=10",
        contentType: "application/json;charset=utf-8",
        dataType : "text",
        async: false,
        success: function (r) {
            $("#streamProManList").html('');
            if (r == "false") {
                $("#streamProManList")
						.html("<tr class='noDataSrc'><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r =="sessionOut"){
            	doLogout();

            }
            var data = $.parseJSON(r);
            //var data = {"proList":[
            //    {"id":"1","proType":"奶粉","features":"国家","option":"1#2#3","updateTime":"2017-3-29 11:11:11"},
            //    {"id":"2","proType":"奶粉2","features":"国家2","option":"1#2#3","updateTime":"2017-3-29 11:11:11"},
            //],"total":2}
			$("#streamProManListPaging .dataCount").text(data.total);
			$("#streamProManListPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
			$("#streamProManListPaging .pageIndex").text(strPageIndex);

            if (data.proList.length > 0 ) {
                var str = "";
                for (var i = 0; i < data.proList.length; i++) {
                    str += GetStreamProManEntry(data.proList[i], i+1);
                }
                $("#streamProManList").html(str);
            }
            else {
                $("#streamProManList")
                    .html("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
 函数名：InitStreamPro；
 作用：获取产品
 */
function InitStreamPro(id,obj){
    $("#hid_featureId").val(id);
    getFeatures();
    $("#txt_addFeature").val("");
	$("#ProductDefinitionDom").hide();
    $("#ProductDefinitionDom_Edit").show();
    $("#ProductDefinitionDom_Edit .Title_tab").html("产品编辑");
    $("#hid_streamProManParam").val('edit');
    $(obj).parent().parent().addClass('editObject');
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/ProductFeatureServlet?methodName=init&id="+id,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r != "false") {
                $("#stream_id").val(id);
				var data = $.parseJSON(r);
                $("#stream_proTypeName").val(data.proType);
                //$("input[name='feature'][value="+data.features+"]").attr("checked",true);
                var items = data.option ? data.option.split(",") : [];
                if (items.length > 0) {
                    for (var i = 0; i<items.length; i++) {
                        $("#option_"+items[i]).attr("checked","checked");
                    }
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

function getFeatures() {
    $.ajax({
        type : "post",
        url : projectLocation + "servlet/FeatureServlet?methodName=initList",
        dataType : "text",
        async : false,
        success : function(r) {
            if (r != "false") {
                var data = $.parseJSON(r);
                var str = "";
                var str1 = "";
                var treeData = [];
                var treeObj = {};
                for (var i=0; i<data.list.length; i++) {
                    //str  += "<input type='radio' name='feature' value='"+ data.list[i].id +"' class='feature'/>" + data.list[i].name;
                    treeObj = {
                        id:data.list[i].id,
                        text:data.list[i].name
                    };
                    treeData.push(treeObj)
                }
                str1 += $('#ddlLine').combotree({
                    valueField: 'id',
                    textField: 'text',
                    data: treeData,
                    multiple: true

                });
                //$("#feaRadio").html(str);
                $("#ddlLine").html(str1);

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
/*
function getFeatures() {
    $.ajax({
        type : "post",
        url : projectLocation + "servlet/FeatureServlet?methodName=initList",
        dataType : "text",
        async : false,
        success : function(r) {
            if (r != "false") {
                var data = $.parseJSON(r);
                var str = "";
                var str1 = "";
                for (var i=0; i<data.list.length; i++) {
                    str += "<input type='radio' name='feature' value='"+ data.list[i].id +"' class='feature'/>" + data.list[i].name;
                    //str1 += "<input type='checkbox' name='option' id='option_"+data.list[i].id+"' value='"+ data.list[i].id +"' class='feature'/>" + data.list[i].name;
                }
                for (var i=0; i<data.list.length; i++) {
                    str  += "<input type='radio' name='feature' value='"+ data.list[i].id +"' class='feature'/>" + data.list[i].name;
                    str1 += $('#ddlLine').combotree({
                        valueField: 'id', 
                        textField: 'text',
                        data: [{'id':data.list[i].id, 'text': 'data.list[i].name'}],
                        url:'servlet/FeatureServlet?methodName=initList',
                        multiple: true
                       
                    });
                }
               
                $("#feaRadio").html(str);
                $("#ddlLine").html(str1);

            }
            else if(r =="sessionOut"){
                doLogout();
            }
        },
        error : function(e) {
            alert(e.responseText);
        }
    });
}*/


// 新增特征
function addFeature() {
    var feaName = $("#txt_addFeature").val();
    var id = $("#hid_featureId").val();
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/FeatureServlet?methodName=save"
        + "&id=&name=" + encode(feaName),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("保存失败！", 3500);
            }
            else if(r =="sessionOut"){
                doLogout();

            }else {
                if (id) {
                    InitStreamPro(id);
                }
                else {
                    getFeatures();
                }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

// 新增
function addStreamProMan () {
    getFeatures();
    $("#txt_addFeature").val("");
    $("#hid_featureId").val("");
    $("#hid_streamProManParam").val('add');
    $("#ProductDefinitionDom").hide();
    $("#ProductDefinitionDom_Edit").show();
    $("#ProductDefinitionDom_Edit .Title_tab").html("产品新增");
    $("#stream_id").val("");
    $("#stream_proTypeName").val("");
    $("[name='option']:checkbox").attr("checked",false);
    $("input:radio[name='feature']").attr("checked",false);

}


/*
函数名：DelStreamProMan
作用：删除产品
参数：id（被删除的产品id）
*/
function DelStreamProMan(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/ProductFeatureServlet?methodName=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetStreamProManList(1);
                }
                else if(r =="sessionOut"){
                	doLogout();
                	
                }
                else if(r == "false")
         	   {
         	       alertText("删除失败！", 3500);
         	   }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    
}

/*
函数名：streamProManSave
作用：保存产品
*/
function streamProManSave() {
    var id = $("#stream_id").val();
    var proType = $("#stream_proTypeName").val();
    //var features = $("input:radio[name='feature']:checked").val();
    var option = "";
    option = $("#ddlLine").combotree("getValues");
   
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ProductFeatureServlet?methodName=save"
        + "&id=" + id + "&proType=" + encode(proType) + "&features=1&option=" + encodeURIComponent(option),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	 alertText("保存失败！", 3500);
            } 
            else if(r =="sessionOut"){
            	doLogout();
            	
            }else {
                GetStreamProManList(1);
            	alertText("保存成功！", 3500);
                $("#ProductDefinitionDom").show();
                $("#ProductDefinitionDom_Edit").hide();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
函数名：UpdateWebsite
作用：保存后更新网站列表
参数：data：被更改的网站信息
*/
function UpdateStreamProMan(data){
    var str = "";
    if($("#hid_streamProManParam").val() == "add"){
        str += GetStreamProManEntry(data, '*');
        $("#streamProManList").find('.noDataSrc').remove();
        $("#streamProManList").prepend(str);
    }else if($("#hid_streamProManParam").val() == "edit") {
        var obj = $("#streamProManList").find('.editObject').find('td');
        obj.eq(0).html('*');
        obj.eq(1).html(data.proType);
        //obj.eq(2).html(data.features);
        obj.eq(3).html(getOptions(data.option));
        obj.eq(4).html(data.updateTime);
        $("#streamProManList").find('.editObject').removeClass('editObject');
    }
}

//根据可选特征id,字符串获取名字
function getOptions(str) {
    var items = str ? str.split(",") : [];
    var optionStr = "";
    for (var i = 0; i < items.length; i++) {
        switch (items[i]) {
            case "1":
                optionStr += "品牌、";
                break;
            case "2":
                optionStr += "产地、";
                break;
            case "3":
                optionStr += "分段、";
                break;
            case "4":
                optionStr += "包装类型、";
                break;
            case "5":
                optionStr += "适用年龄、";
                break;
            case "6":
                optionStr += "植物品种、";
                break;
            case "7":
                optionStr += "开花季节、";
                break;
            case "8":
                optionStr += "适用空间、";
                break;
            default :
                optionStr += "颜色分类、";
                break;
        }
    }
    optionStr = optionStr.substring(0,optionStr.length-1);
    return optionStr;
}
