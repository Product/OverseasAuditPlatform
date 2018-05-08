$(function () {
$("#propertyChoiceSure").click(function () {
        // to get the chioces
        //获得三个选择框的文本内容和对应Id
        var firObj = document.getElementById('propertyFirstSel');
        var firChoice = firObj.options[firObj.selectedIndex].text;
        var secObj = document.getElementById('propertySecondSel');
        var secChoice = secObj.options[secObj.selectedIndex].text;
        var thiObj = document.getElementById('propertyThirdSel');
        var thiChoice = thiObj.options[thiObj.selectedIndex].text;
        var firChoiceId = $("#propertyFirstSel").val();
        var secChoiceId = $("#propertySecondSel").val();
        var thiChoiceId = $("#propertyThirdSel").val();


        if (firChoice == "请选择") {
            return;
        }
        // to show chioces
        $("#propertyChioceShow").show();
        if (secChoice != "请选择" && thiChoice != "请选择") {
            var str = "<span class='choices' value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='delePropertyChoice(this)'>" + firChoice + "&nbsp;|&nbsp;" + secChoice + "&nbsp;|&nbsp;" + thiChoice + "<a class='timeA'>&times</a></span><br/>";
        }
        else if (secChoice == "请选择") {
            var str = "<span class='choices'  value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='delePropertyChoice(this)'>" + firChoice + "<a class='timeA'>&times</a></span><br/>";
        }
        else if (thiChoice == "请选择") {
            var str = "<span class='choices'  value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='delePropertyChoice(this)'>" + firChoice + "&nbsp;|&nbsp;" + secChoice + "<a class='timeA'>&times</a></span><br/>";
        }
        $("#propertyChioceDisplay").append(str);
    });



    //add by lxt
    //当第一级菜单变化时，加载第二级菜单
    $("#propertyFirstSel").change(
		function () {
		    $("#propertySecondSel").html('');
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
		    $("#propertySecondSel").append(str);
		    $("#propertyThirdSel").html("<option value='0'>请选择</option>");
		});


    //add by lxt
    //当第二级菜单发生变化时，加载第三级菜单
    $("#propertySecondSel").change(
		function () {
		    $("#propertyThirdSel").html('');
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
		    $("#propertyThirdSel").append(str);
		});
});
//获得一级海关类别
function getPropertyFirstlevelType(){
	$("#propertyFirstSel").html('');
	 $("#propertySecondSel").html("<option value='0'>请选择</option>");
	    $("#propertyThirdSel").html("<option value='0'>请选择</option>");
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
	$("#propertyFirstSel").append(str);
	
}

function delePropertyChoice(obj){
	// delete current span
	$(obj).remove();
	// if current content is null,to hide the 商品描述
	if ($("#propertyChioceDisplay").children("span").length <= 0)
	{
		$("#propertyChioceShow").hide();
	}
}
//首页
function FirstPageProductType() {
    GetProductTypeList(1);
}

//上一页
function PreviousPageProductType() {
    var pageIndex = (parseInt($("#ProductTypePaging .pageIndex").text()) == 1 ? 1 : parseInt($("#ProductTypePaging .pageIndex").text()) - 1);
    GetProductTypeList(pageIndex);
}

//下一页
function NextPageProductType() {
    var pageIndex = ($("#ProductTypePaging .pageIndex").text() == $("#ProductTypePaging .pageCount").text() ? parseInt($("#ProductTypePaging .pageIndex").text()) : parseInt($("#ProductTypePaging .pageIndex").text()) + 1);
    GetProductTypeList(pageIndex);
}

//尾页
function EndPageProductType() {
    GetProductTypeList(parseInt($("#ProductTypePaging .pageCount").text()));
}

//将得到的数据变成html格式代码字符串
function GetProductTypeEntry(data, idx){
    var str = "";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.PName + "</td>";
    str += "<td>" + data.Remark + "</td>";
    str += "<td>" + data.CreatePerson + "</td>";
    str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showChildrenProductType(\"1\", \"ptchild"
        + data.Id + "\",this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialogProductType(\"edit\",\""
		+ data.Id + "\",this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
        + data.Id + "\", DelProductType)' /></td>";
    return str;
}

function foldChildrenProductType(name){
    $('.'+name).each(function(){
            var aim = $(this).find('td input').eq(0).attr('onclick');
            if(aim != undefined){
                var judge = aim.charAt(25);
                
                if(judge == "0"){
                    var ii = 36;
                    while(aim.charAt(ii) != '"')
                        ii++;
                    var idd = aim.substring(30, ii);
                    foldChildrenProductType(idd);
                }
            }
    });
    $('.'+name).remove();
}

function showChildrenProductType(med, name, obj){
    var tgr = $(obj).parent().parent();
    var idx = $('#ProductTypeList tbody tr').index(tgr);
    var hed = $(tgr).children('td').eq(0).html();
    if(med == "0"){
        $(obj).val("查 看");
        $(obj).attr('onclick', 'showChildrenProductType(\"1\", \"'+name+'\", this)');
        foldChildrenProductType(name);
        return;
    }
    $(obj).val("收 起");
    $(obj).attr('onclick', 'showChildrenProductType(\"0\", \"'+name+'\", this)');
    $.ajax({
        type : "post",
        url : projectLocation
            + "servlet/ProductTypeServlet?methodName=sel&id="+name.substring(7),
        dataType : "text",
        async : false,
        success : function(r) {
            if (r == "false") {
                $(tgr).after("<tr class='"+name+"'><td colspan='7' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r == "sessionOut"){
                doLogout();
            }
            else{
                var data = $.parseJSON(r);
                var str = "";
                if(data.webList.length == 0)
                    $(tgr).after("<tr class='"+name+"'><td colspan='7' style='text-align:center;'>无数据！</td></tr>");
                for (var i = 1; i <= data.webList.length; i++) {
                    str += "<tr class='"+name+"'>"+GetProductTypeEntry(data.webList[i-1], hed+"."+i)+"</tr>";
                }
                $(tgr).after(str);
            }
        },
        error : function(e) {
            alert(e.responseText);
        }
    });
}

//商品类别列表
function GetProductTypeList(strPageIndex) {
	var strQuery = $("#txt_ProductTypeQuery").val();
	var strStraTime = $("#txt_ProductTypeBeginDate").val();
	var strEndTime = $("#txt_ProductTypeEndDate").val();
    var strLevel = $('#op_ProductTypeLevel').val();
    $('#op_ProductTypeLevel').val(strLevel);
    //getProductTypeLevel(strLevel);
	$.ajax({
				type : "post",
				url : projectLocation
						+ "servlet/ProductTypeServlet?methodName=QueryList&strQuery="
						+ strQuery + "&strStraTime=" + strStraTime
						+ "&strEndTime=" + strEndTime + "&strPageIndex="
						+ strPageIndex + "&strPageCount=10" +"&strLevel="+strLevel,
				dataType : "text",
				async : false,
				success : function(r) {
					$("#ProductTypeList").html('');
					if (r == "false") {
						$("#ProductTypeList")
								.append(
										"<tr class='noDataSrc'><td colspan='7' style='text-align:center;'>无数据！</td></tr>");
						return false;
					}
					else if(r == "sessionOut")	{
		            	doLogout();
		            }
	                else  {
	                	var data = $.parseJSON(r);

	                    $("#ProductTypePaging .dataCount").text(data.total);
	                    $("#ProductTypePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
	                    $("#ProductTypePaging .pageIndex").text(strPageIndex);
	                    var str = "";
	                    for (var i = 1; i <= data.webList.length; i++) {
	                        str += "<tr>"+GetProductTypeEntry(data.webList[i-1], i)+"</tr>";
						}
						$("#ProductTypeList").append(str);
	                }
				},
				error : function(e) {
					alert(e.responseText);
				}
			});
}

//删除
function DelProductType(id) {
		$.ajax({
			type : "post",
			url : projectLocation
					+ "servlet/ProductTypeServlet?methodName=del&id=" + id,
			dataType : "text",
			async : false,
			success : function(r) {
				if (r == "true") {
					alertText("删除成功！",  3500);

                    /*$(obj).parent().parent().remove();
                    if($("#ProductTypeList").html() == ""){
                        $("#ProductTypeList").append("<tr class='noDataSrc'><td colspan='7' style='text-align:center;'>无数据！</td></tr>");
                    }*/
					GetProductTypeList(1);
				}
				else if(r == "sessionOut")	{
	            	doLogout();
	            }
                else  {
    				alertText("删除失败！",3500);
    			}
			},
			error : function(e) {
				alert(e.responseText);
			}
		});
	}


//动态响应select显示下层的商品种类
function getChildrenProductType(obj){
    var idx;
    if(obj != null){
        idx = $('#ProductTypeSelect select').index(obj);
        $('#ProductTypeSelect select:gt('+idx+')').remove();
        $('#hid_ProductTypeParentParam').val($(obj).val());
        if($(obj).val() == $(obj).children('option').eq(0).val())
            return;
        if(idx >= 1)
            return;
    }   
    var id = $('#hid_ProductTypeParentParam').val();
    //if(getParam("hid_ProductTypeParam", "methodName") == "edit" && (idx+1) >= $('#hid_ProductTypeLevel').val())
    //    return;
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ProductTypeServlet?methodName=sel&id=" + id,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r != "false") {
                var data = $.parseJSON(r);

                var str = '<select onchange="getChildrenProductType(this)">';
                str +='<option value="'+id+'">该级添加新类别</option>';
                if(data != null){
                    for (var i = 0; i < data.webList.length; i++) {
                        if(data.webList[i].Id != getParam("hid_ProductTypeParam", "id"))
                            str +='<option value="'+data.webList[i].Id+'">'+data.webList[i].Name+'</option>';
                    }
                }
                str += '</select>';
                $('#ProductTypeSelect').append(str);
            }
            else if(r == "sessionOut")  {
                doLogout();
            }
            else  {
                alertText("产品类型获取失败！",3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//动态添加修改商品种类页面的该商品所属商品种类类别
function getParentProductType(id){
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ProductTypeServlet?methodName=selfa&id=" + id,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r != "false") {
                var data = $.parseJSON(r);
                var str;
                for (var i = 0; i < data.data.length; i++) {
                    //if(i < 1)
                        str = '<select onchange="getChildrenProductType(this)">';
                    //if(i == 1)
                    //    str = '<select onchange="getCProductType(this)">';
                    str +='<option value="'+id+'">该级添加新类别</option>';
                    for(var j = 0; j < data.data[i].length; j++)
                        str +='<option value="'+data.data[i][j].Id+'">'+data.data[i][j].Name+'</option>';
                    str += '</select>';
                    $('#ProductTypeSelect').prepend(str);
                }
                var len = data.selected.length-1;
                for(var i = 0; i < data.selected.length; i++){
                    $('#ProductTypeSelect select').eq(i).val(data.selected[len-i]);
                    if(i != 0)
                        $('#ProductTypeSelect select').eq(i).children('option').eq(0).val(data.selected[len-i+1]);
                }
                $('#ProductTypeSelect select').eq(0).children('option').eq(0).val(0); 
            }
            else if(r == "sessionOut")  {
                doLogout();
            }
            else  {
                alertText("产品类型获取失败！",3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}


//编辑页面（添加+编辑）
function showDialogProductType(methodName, Id, pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#ProductTypeDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    getPropertyFirstlevelType();
    $("#propertyChioceDisplay").html('');
    if (methodName != "add") {//编辑
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/ProductTypeServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_ProductTypeParam").val("methodName=edit&id=" + Id);
                    $("#txt_ProductTypeName").val(r.Name);
                    $("#txt_ProductTypeRemark").val(r.Remark);
                    $('#ProductTypeSelect').find('select').remove();
                    $('#hid_ProductTypeParentParam').val(r.PtId);
                    $('#hid_ProductTypeLevel').val(r.Level);
                    getParentProductType(r.Id);
                   //加载已关联的海关类型
                    $("#propertyChioceShow").show();
                    for (var j = 0; j < r.propertyTypes.length; j++){
                    	
                         if (r.propertyTypes[j].secName != "" && r.propertyTypes[j].thiName != "") {
                             var str = "<span class='choices' value='" + r.propertyTypes[j].firId + "|" + r.propertyTypes[j].secId + "|" + r.propertyTypes[j].thiId + "<>' onclick='delePropertyChoice(this)'>" + r.propertyTypes[j].firRemark + "&nbsp;|&nbsp;" + r.propertyTypes[j].secRemark + "&nbsp;|&nbsp;" + r.propertyTypes[j].thiRemark + "<a class='timeA'>&times</a></span><br/>";
                         }
                         else if (r.propertyTypes[j].secName == "") {
                             var str = "<span class='choices'  value='" + r.propertyTypes[j].firId + "|" + r.propertyTypes[j].secId + "|" + r.propertyTypes[j].thiId + "<>' onclick='delePropertyChoice(this)'>" +  r.propertyTypes[j].firRemark + "<a class='timeA'>&times</a></span><br/>";
                         }
                         else if (r.propertyTypes[j].thiName == "") {
                             var str = "<span class='choices'  value='" + r.propertyTypes[j].firId + "|" + r.propertyTypes[j].secId + "|" + r.propertyTypes[j].thiId + "<>' onclick='delePropertyChoice(this)'>" + r.propertyTypes[j].firRemark + "&nbsp;|&nbsp;" + r.propertyTypes[j].secRemark + "<a class='timeA'>&times</a></span><br/>";
                         }
                         $("#propertyChioceDisplay").append(str);
                    }

                    
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
    } else {//添加
        $("#hid_ProductTypeParam").val("methodName=add");
        $("#txt_ProductTypeName").val("");
        $("#txt_ProductTypeRemark").val("");
        $('#hid_ProductTypeParentParam').val(0);
        $('#ProductTypeSelect').find('select').remove();
        getChildrenProductType(null);
    } 
}

//保存
function SaveProductType() {
	var strName = encode($("#txt_ProductTypeName").val());
    var strRemark = encode($("#txt_ProductTypeRemark").val());
    var strPtid = $("#hid_ProductTypeParentParam").val();
    
    //add by lxt
    var strChoices = ""
	$("#propertyChioceDisplay span").each(function () {
		strChoices += $(this).attr("value");
		  });
		if (strChoices != "") {
			strChoices = strChoices.substring(0, strChoices.length - 2);
		 }
	 var choicesLength = $("#propertyChioceDisplay").children("span").length;
		 
    if ($.trim(strName) == "") {
        alertText("类型名称不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ProductTypeServlet?methodName="
            					+ getParam("hid_ProductTypeParam", "methodName")
            					+ "&id=" + getParam("hid_ProductTypeParam", "id")
                                + "&name=" + strName + "&remark=" + strRemark 
                                + "&ptid=" + strPtid +"&PropertyTypeChoices=" + strChoices + "&choiceslength=" + choicesLength,
        dataType: "text",
        async: false,
        success: function (r) {
        	if (r.res == "false") {
                alertText("保存失败！", 3500);   
            }
            else if(r == "sessionOut")	{
            	doLogout();
            }
            else {
            	alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateProductType(data);//更新评价类别列表
                $('#ProductTypeDom_Edit').animate({ width: "hide" });
                $("#ProductTypeDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//添加或编辑后更新评价类别列表
function UpdateProductType(data){
    var str = "";
    if(getParam("hid_ProductTypeParam", "methodName") == "add")//添加保存新增一行显示添加的数据
    {
        str += "<tr>"+GetProductTypeEntry(data, '*')+"</tr>";
        $("#ProductTypeList").find('.noDataSrc').remove();
        $("#ProductTypeList").prepend(str);
    }
    else if(getParam("hid_ProductTypeParam", "methodName") == "edit")//编辑保存修改编辑的那一行数据
    {
        var obj =  $("#ProductTypeList").find('.editObject').find('td');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.PName);
        obj.eq(3).html(data.Remark);
        obj.eq(4).html(data.CreatePerson);
        obj.eq(5).html(data.CreateTime.substring(0, 19));
        $("#ProductTypeList").find('.editObject').removeClass('editObject');
    }
}

/*--------------------------------------------------------------------------------------------------------------------*/
//动态获取所有的数据库中的级别
function getProductTypeLevel(level){
    var strLevel = $('#op_ProductTypeLevel').html();
    var arrLevel = new Array('', '一', '二', '三', '四', '五');
    $.ajax({
        type : "post",
        url : projectLocation
            + "servlet/ProductTypeServlet?methodName=level",
        dataType : "text",
        async : false,
        success : function(r) {
            if (r == "false") {
                $('#op_ProductTypeLevel').html('<option value="1">一级类别</option>');
                $('#op_ProductTypeLevel').val(1);
                return false;
            }
            else if(r == "sessionOut")  {
                doLogout();
            }
            else{
                var data = parseInt(r);
                var str = "";
                for (var i = 1; i <= data; i++) {
                    str += "<option value='"+i+"'>"+arrLevel[i]+"级类别</option>";
                }
                $('#op_ProductTypeLevel').html(str);
                $('#op_ProductTypeLevel').val(level);
            }
        },
        error : function(e) {
            alert(e.responseText);
        }
    });
}

// 显示遮罩层
function confirmSpecialShow(id, obj, callback,text) {
    $("#bg").css("display", "block");
    $("#show").css("display", "block");
    if (text != null)
    {
        $("#wordsarea").text("");
        $("#wordsarea").append(text);
    }
    //确定按钮事件
    $("#btnCloseSure").click(function () {
        $("#bg").css("display", "none");
        $("#show").css("display", "none");
        if (typeof (callback) == 'function') {
            if (id == null)
            {
                callback();
            } else {
                callback(id, obj);
            }
        }
    });
    //取消按钮事件
    $("#btnCloseCancel").click(function () {
        $("#bg").css("display", "none");
        $("#show").css("display", "none");
    });
}

