// this function for form validate,added by liqiuling
$().ready(function() {
    //start validate
    $("#firstTaskForm").validate({          
        rules: {
            mustName: {
                required: true
            },
            pagestep: {
                required: true,
                digits:true,
				min:0
            },
			urlname: {
                required: true,
				url:true
			}
        },
        // set error messages label
        errorElement: "lable",
        // set error messages place
        errorPlacement: function (error, element) {
            if (element.is(':radio') || element.is(':checkbox')) {
                var eid = element.attr('name');
                error.appendTo(element.parent("td"));
            } else {
                error.appendTo(element.closest("td"));
            }
        }
    })
});
$(function(){
	 $("#txt_firstTaskType").change(
				function(){
					$("#txt_secondTaskType").html('');
					var str = "";
					str +=  "<option value='0'>" + "请选择" + "</option>";
					$("#txt_thirdTaskType").html('');
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
					$("#txt_secondTaskType").append(str);
					$("#txt_thirdTaskType").append(str1);
			});
	 $("#txt_secondTaskType").change(
				function(){
					$("#txt_thirdTaskType").html('');
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
				$("#txt_thirdTaskType").append(str);
				});
	
});
//获取一级类别菜单
function getTaskFirstType(){
	$("#txt_firstTaskType").html('');
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
	$("#txt_firstTaskType").append(str);
	
}
//获取二级类别菜单
function getTaskSecondType(id){
	$("#txt_secondTaskType").html('');
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
	$("#txt_secondTaskType").append(str);
	
}
//获取三级类别菜单
function getTaskThirdType(id){
	$("#txt_thirdTaskType").html('');
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
$("#txt_thirdTaskType").append(str);
	
}
function TFirstPage() {
    GetTaskList(1);
}

function TPreviousPage() {
    var pageIndex = (parseInt($("#taskPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#taskPaging .pageIndex").text()) - 1);
    GetTaskList(pageIndex);
}

function TNextPage() {
    var pageIndex = ($("#taskPaging .pageIndex").text() == $("#taskPaging .pageCount").text() ? parseInt($("#taskPaging .pageIndex").text()) : parseInt($("#taskPaging .pageIndex").text()) + 1);
    GetTaskList(pageIndex);
}

function TEndPage() {
    GetTaskList(parseInt($("#taskPaging .pageCount").text()));
}
/*读取GatherTask表项*/
function GetGatherTaskEntry(data, idx){
	  var str = "<tr>";
	  str += "<td><input type='checkbox' rel='" + data.Id + "'></td>"; // checkbox的Id？
      str += "<td class='gatherTaskOrd'>" + idx + "</td>";
      str += "<td>" + data.TaskName + "</td>";
      str += "<td>" + data.FirstTypeName + " "+ data.SecondTypeName + "" + data.ThirdTypeName + "</td>";
      str += "<td class='gatherTaskPnum'>" + data.ProductNum + "</td>";
      if (data.StateNum == 0)//状态空闲
          str += "<td class='gatherTaskState'><span style='color:green;'>" + data.State + "</span></td>";
      else if (data.StateNum == 1)//执行中
          str += "<td class='gatherTaskState'><span style='color:orange;'>" + data.State + "</span></td>";
      else if (data.StateNum == 2)//异常
          str += "<td class='gatherTaskState'><span style='color:red;'>" + data.State + "</span></td>";
      str += "<td>" + data.Remark + "</td>";
      str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
      str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showTDialog(\"view\",\""
						+ data.Id
						+ "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' class='excuteBtnF' value='执 行' onclick='TExcute(this, \"" + data.Id
						+ "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showTDialog(\"edit\",\""
						+ data.Id
						+ "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelTask(\""
						+ data.Id + "\")' /></td>";
      str += "</tr>";

  return str;
}
/*
函数名：GetTaskListByGId
作用：通过数据采集Id获取其中的子任务列表
*/
function GetTaskListByGId(strPageIndex) {
    var strQuery = "";
    var strStraTime = "";
    var strEndTime = "";
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/GatherTaskServlet?methodName=TQueryList&strQuery="
	                    + encode(strQuery) + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime
	                    + "&id=" + getParam("hid_DataGatherParam", "id") + "&strPageIndex="
	    				+ strPageIndex + "&strPageCount=10",
        dataType: "text",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#TaskList").html('');
            if (r == "false") {//未获取到数据
                $("#TaskList").append("<tr class='noDataSrc'><td colspan='9' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r =="sessionOut"){
            	doLogout();
            	
            }
            var data = $.parseJSON(r);

            $("#taskPaging .dataCount").text(data.total);
            $("#taskPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#taskPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.TaskList.length; i++) {
            	 str += GetGatherTaskEntry(data.TaskList[i], i+1);
            }
            $("#TaskList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
函数名：GetTaskList
作用：获取Task列表，Task页面常规查询函数
*/
function GetTaskList(strPageIndex) {
    var strQuery = $("#txt_taskQuery").val();
    var strStraTime = $("#txt_taskBeginDate").val();
    var strEndTime = $("#txt_taskEndDate").val();


    $.ajax({
        type: "post",
        url: projectLocation + "servlet/GatherTaskServlet?methodName=TQueryList&strQuery="
                    + encode(strQuery) + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime
                    + "&id=" + getParam("hid_DataGatherParam", "id") + "&strPageIndex="
    				+ strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#TaskList").html('');
            if (r == "false") {//未查询到数据时
                $("#TaskList").append("<tr class='noDataSrc'><td colspan='9' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }else if(r =="sessionOut"){
            	doLogout();
            	
            }
            //查询到数据时
            var data = $.parseJSON(r);
//更新条目数及页码
            $("#taskPaging .dataCount").text(data.total);
            $("#taskPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#taskPaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.TaskList.length; i++) {
            	str += GetGatherTaskEntry(data.TaskList[i], i+1);
            }
            $("#TaskList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
函数名：showTDialog
作用：控制对于特定Task的查看，编辑和添加
参数：id（被操作的王炸id）
methodName：操作名称参数
pos：操作位置
*/
function showTDialog(methodName, Id, pos) {
    // add by liqiuling, don't delete!!
    $('#TaskListDom').css("display", "none");
    $("#firstProgress").css("display", "block");
    $("#secondProgress").css("display", "none");
    $("#thirdProgress").css("display", "none");
    $("#firstImg> div").css("width", "0");
    $("#secondImg> div").css("width", "0");
    $("#progressBarfirst").children("div").removeClass("first_div");
    $("#progressBarfirst").children("span").removeClass("first_span");
    $("#progressBarfirst").children("div").addClass("second_div");
    $("#progressBarfirst").children("span").addClass("second_span");
    $("#progressBarsecond").children("div").removeClass("first_div");
    $("#progressBarsecond").children("span").removeClass("first_span");
    $("#progressBarsecond").children("div").addClass("second_div");
    $("#progressBarsecond").children("span").addClass("second_span");


    if (methodName == "view") {//详情查看
    	
        $("#iframeMain .wrap").animate({ width: "hide" });
        $('#taskDom_View').show();

        $(obj).parent().parent().children("li").children("a").removeClass("current");
        $(obj).addClass("current");

        $.ajax({
            type: "post",
            url: projectLocation + "servlet/GatherTaskServlet?methodName=tview&id=" + Id,
            dataType: "text",
            async: false,
            success: function (r) {

                if (r != "false") {
                    var data = $.parseJSON(r);
                    $("#hid_taskListParam").val("methodName=view&id=" + Id);
                    $("#txt_TaskName").val(data.TaskName);
                    $("#txt_TaskRemark").val(data.Remark);
                   
                    $("#txt_FirstTaskType").val(data.FirstTypeName);
                    $("#txt_SecondTaskType").val(data.SecondTypeName);
                    $("#txt_ThirdTaskType").val(data.ThirdTypeName);
                    $("#txt_TaskUrl").val(data.URL);
                    $("#txt_RequestType").val(data.RequestType);
                    $("#txt_PagingType").val(data.PagingType);
                    $("#txt_PagingParams").val(data.PagingParams);
                    $("#txt_PagingStep").val(data.PagingStep);
                    $("#txt_NextpageType").val(data.NextPageType);
                    $("#txt_NextpagePath").val(data.NextPagePath);
                    $("#txt_NextpageStr").val(data.NextPageStr);
                    $("#txt_LastpageType").val(data.LastPageType);
                    $("#txt_LastpageStr").val(data.LastPageStr);
                    $("#txt_ProductPath").val(data.ProductPath);
                    $("#txt_PathType").val(data.PathType);
                    $("#txt_LastPageUrl").val(data.LastPageUrl);
                    
                    $("#vtaskParamsList").html('');
                    var plstr = "";

                    for (var j = 0; j < data.ParamsList.length; j++) {//Params参数显示
                        plstr += "<tr>";
                        plstr += "<td>" + (j + 1) + "</td>";
                        plstr += "<td>" + data.ParamsList[j].Key + "</td>";
                        plstr += "<td>" + data.ParamsList[j].Val + "</td>";
                        plstr += "</tr>";
                    }
                    $("#vtaskParamsList").append(plstr);
                    $("#vtaskItemPathList").html('');

                    var ipstr = "";

                    for (var j = 0; j < data.strItemPath.length; j++) {//ItemPath列表显示
                        ipstr += "<tr>";
                        ipstr += "<td>" + (j + 1) + "</td>";
                        ipstr += "<td>" + data.strItemPath[j].ItemType + "</td>";
                        ipstr += "<td>" + data.strItemPath[j].ItemStr + "</td>";
                        ipstr += "<td>" + data.strItemPath[j].ItemAttr + "</td>";
                        ipstr += "<td>" + data.strItemPath[j].ItemValPath + "</td>";
                        ipstr += "</tr>";
                    }
                    $("#vtaskItemPathList").append(ipstr);
                    DisplayControl();
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
    else {//编辑和新增
        $("#iframeMain .wrap").animate({ width: "hide" });
        $('#taskDom_Edit').show();
        $(".pagingType").show();
		$("#dynamicPart").show();
		//数据采集 下一页特征字符
        $(".nextPageTypeIsText").show();
        $(".nextPageTypeIsNotText").show();
        $(obj).parent().parent().children("li").children("a").removeClass("current");
        $(obj).addClass("current");
       
        getTaskFirstType();
        if (methodName != "add") {//编辑
            $.ajax({
                type: "post",
                url: projectLocation + "servlet/GatherTaskServlet?methodName=tinit&id=" + Id,
                dataType: "json",
                async: false,
                success: function (r) {
                    if (r != "false") {
                    	$(pos).parent().parent().addClass('editObject');
                        $("#hid_taskListParam").val("methodName=tedit&id=" + Id + " &gid=" + getParam("hid_DataGatherParam", "id"));
                        $("#txt_taskName").val(r.TaskName);
                        $("#txt_taskURL").val(r.URL);
                       
                        $("#txt_firstTaskType").val(r.FirstTypeId);
                        getTaskSecondType(r.FirstTypeId);
                        $("#txt_secondTaskType").val(r.SecondTypeId);
                        getTaskThirdType(r.SecondTypeId);
                        $("#txt_thirdTaskType").val(r.ThirdTypeId);
                        $("#txt_taskRemark").val(r.Remark);
                        $("#txt_pagingType").val(r.PagingType);
                        $("#txt_pagingParams").val(r.PagingParams);
                        $("#txt_pagingStep").val(r.PagingStep);
                        $("#txt_nextPageType").val(r.NextPageType);
                        $("#txt_nextPagePath").val(r.NextPagePath);
                        $("#txt_nextPageStr").val(r.NextPageStr);
                        $("#txt_lastPageType").val(r.LastPageType);
                        $("#txt_lastPageStr").val(r.LastPageStr);
                        $("#ddl_requestType").val(r.RequestType);
                        $("#txt_lastPageUrl").val(r.LastPageUrl);
                        $("#txt_productPath").val(r.ProductPath);
                        $("#txt_pathType").val(r.PathType);
                        pagingTypeChange();
                        nextPageTypeChange();
                        var strParams = "";
                        for (var i = 0; i < r.ParamsList.length; i++) {
                            strParams += "<tr><td rel='" + r.ParamsList[i].Id + "'></td><td><input type='text' value='"
                                        + r.ParamsList[i].Key
                                        + "' /></td><td><input type='text' value='"
                                        + r.ParamsList[i].Val
                                        + "' /></td><td><input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelParamsRow(this)' /></td></tr>";
                        }
                        var strItemPath = "";
                        if (r.strItemPath.length >= 0) {

                            for (var i = 0; i < r.strItemPath.length; i++) {
                                 //显示ItemPath列表

                                    strItemPath += (("<tr><td rel='" + r.strItemPath[i].Id + "'><input type='text' value='"
                                        + r.strItemPath[i].ItemType
                                        + "' /></td><td><input type='text' value='"
                                        + r.strItemPath[i].ItemStr
                                        + "' /></td><td><select>"
                                        + "<option value='CLASS'>Class</option>"
                                        + "<option value='ID'>Id</option>"
                                        + "<option value='SELECTION'>Selection</option>"
                                        + "<option value='TEXT'>Text</option>"
                                        + "</select></td>").replace("value='" + r.strItemPath[i].ItemAttr + "'", "selected value='" + r.strItemPath[i].ItemAttr + "'")
                                        + ("<td><select><option value='CLASS'>Class</option>"
                                        + "<option value='TEXT'>Text</option><option value='HREF'>Href</option>"
                                        + "<option value='HREF_REL'>Href_rel</option><option value='TITLE'>Title</option>"
                                        + "</select></td>").replace("value='" + r.strItemPath[i].ItemValPath + "'", "selected value='" + r.strItemPath[i].ItemValPath + "'")
                                        + "<td><input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelItemPathRow(this)' /></td></tr>");
                                
                            }
                        }


                        $("#taskParamsList").html(strParams);
                        $("#taskItemPathList").html(strItemPath);
						$("#firstTaskForm").valid()
                        TaskParamsCheckRowNO();
                    }
                    else if (r == "sessionOut") {
                        doLogout();

                    }
                },
                error: function (e) {
                    alert(e.responseText);
                }
            });
        } else {//新增
            $("#hid_taskListParam").val("methodName=tadd&gid=" + getParam("hid_DataGatherParam", "id"));
            $("#txt_taskName").val("");
           
            $("#txt_taskURL").val("");
            $("#txt_taskRemark").val("");
            $("#txt_pagingType").val("");
            $("#txt_pagingParams").val("");
            $("#txt_pagingStep").val("");
            $("#txt_nextPageType").val("");
            $("#txt_taskURL").val("");
            $("#txt_nextPagePath").val("");
            $("#txt_nextPageStr").val("");
            $("#txt_lastPageType").val("");
            $("#txt_lastPageStr").val("");
            $("#taskParamsList").html("");
            $("#taskItemPathList").html("");
            $("#txt_lastPageUrl").val("");
            $("#txt_productPath").val("");
            $("#txt_pathType").val("");
        }
    }
}
/*
函数名：CreateTaskItemPathLine
作用：编辑过程中新增ItemPath行
新增行中rel属性赋值为0
*/
function CreateTaskItemPathLine() {
    $("#taskItemPathList").append("<tr><td rel='0'><input type='text' /></td><td><input type='text' /></td><td><select>"
                                        + "<option value='CLASS'>Class</option><option value='ID'>Id</option>"
                                        + "<option value='SELECTION'>Selection</option><option value='TEXT'>Text</option>"
                                        + "</select></td><td><select><option value='CLASS'>Class</option>"
                                        + "<option value='TEXT'>Text</option><option value='HREF'>Href</option>"
                                        + "<option value='HREF_REL'>Href_rel</option><option value='TITLE'>Title</option>"
                                        + "</select></td><td><input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelItemPathRow(this)' /></td></tr>");
}
/*
函数名：CreateTaskParamsLine
作用：编辑过程中新增Params行
新增行中rel属性赋值为0
*/
function CreateTaskParamsLine() {
    $("#taskParamsList").append("<tr><td rel='0'></td><td><input type='text' /></td><td><input type='text' /></td><td><input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelParamsRow(this)' /></td></tr>");
    TaskParamsCheckRowNO();
}
/*
函数名： TaskParamsCheckRowNO
作用：为列表中的TaskParams加上编号
新增行中rel属性赋值为0
*/
function TaskParamsCheckRowNO() {
    $("#taskParamsList tr").each(function (i) {
        $(this).children().eq(0).html(i + 1);
    });
}
/*
函数名：DelParamsRow
作用：删除Params行，如果TaskParams数据库表中存有相应的数据项，一并删除
*/
function DelParamsRow(obj) {
	 if($(obj).parent().parent().children().eq(0).attr("rel")!="0")//当该条属性已经存在于数据库，将其对应数据项删除
		 {
		  $.ajax({
		        type: "post",
		        url: projectLocation
							+ "servlet/GatherTaskServlet?methodName=ParamsDel&id=" + $(obj).parent().parent().children().eq(0).attr("rel"),
		        dataType: "text",
		        async: false,
		        success: function (r) {
		            if (r == "true") {
		            	 $(obj).parent().parent().remove();
		            	    TaskParamsCheckRowNO();
		            }
		        },
		        error: function (e) {
		            alert(e.responseText);
		        }
		    });
		 }
	 else//该项为新增项时，直接删除该行
		 {
		 $(obj).parent().parent().remove();
		 TaskParamsCheckRowNO();
		 }
		 

}
/*
函数名：DelItemPathRow
作用：删除ItemPath行，如果TaskItemPath数据表中存有相应的数据项，一并删除
*/
function DelItemPathRow(obj) {
  
    if($(obj).parent().parent().children().eq(0).attr("rel")!="0")//如果当前数据已经在数据库中存储过，则删除数据库中对应数据
	 {
	  $.ajax({
	        type: "post",
	        url: projectLocation
						+ "servlet/GatherTaskServlet?methodName=ItemPathDel&id=" +$(obj).parent().parent().children().eq(0).attr("rel"),
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
    else//否则，直接删除该行
    	$(obj).parent().parent().remove();
    	
	 
}
/*
函数名：SaveTask
作用：保存Task

*/
function SaveTask() {
	//保存编辑框中的参数
    var strName = $("#txt_taskName").val();
 
    var strFirstType = $("#txt_firstTaskType").val();
    var strSecondType = $("#txt_secondTaskType").val();
    var strThirdType = $("#txt_thirdTaskType").val();
    var strUrl = $("#txt_taskURL").val();
    var strRemark = $("#txt_taskRemark").val();
    var strRequestType = $("#ddl_requestType").val();
    var strPagingType = $("#txt_pagingType").val();
    var strPagingParams = $("#txt_pagingParams").val();
    var strPagingStep = $("#txt_pagingStep").val();
    var strNextPageType = $("#txt_nextPageType").val();
    var strNextPagePath = $("#txt_nextPagePath").val();
    var strNextPageStr = $("#txt_nextPageStr").val();
    var strLastPageType = $("#txt_lastPageType").val();
    var strLastPageStr = $("#txt_lastPageStr").val();
    var strLastPageUrl = $("#txt_lastPageUrl").val();
    var strProductPath = $("#txt_productPath").val();
    var strPathType = $("#txt_pathType").val();
    

    var strParams = "";
    var strItemPath = "";
    if ($.trim(strName) == "") {
        alertText("任务名不能为空！", 3500);
        return false;
    }
//保存taskParamsList表中每行的数据
    $("#taskParamsList tr").each(function () {
        strParams += $(this).children().eq(0).attr("rel") + "|" + $(this).children().eq(1).children().val() + "|" + $(this).children().eq(2).children().val() + "<>";
    });
    if (strParams != "") {
        strParams = strParams.substring(0, strParams.length - 2);
    }
    //保存ItemPath中对应的数据
    
    $("#taskItemPathList tr").each(function () {
        strItemPath += $(this).children().eq(0).attr("rel") + "|" + $(this).children().eq(0).children().val() + "|" + $(this).children().eq(1).children().val() + "|"
                    + $(this).children().eq(2).children().val() + "|" + $(this).children().eq(3).children().val() + "<>";
    });
    if (strItemPath != "") {
        strItemPath = strItemPath.substring(0, strItemPath.length - 2);
    }

//将得到的数据传入后台
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/GatherTaskServlet?methodName=" + getParam("hid_taskListParam", "methodName")
            + "&id=" + getParam("hid_taskListParam", "id") + "&gid=" + getParam("hid_taskListParam", "gid") + "&name=" + encode(strName) 
            + "&remark=" + encode(strRemark) + "&strParams=" + encode(strParams) + "&strItemPath=" + encode(strItemPath)
            + "&URL=" + encode(strUrl) + "&strRequestType=" + encode(strRequestType)  + "&firstType=" + encode(strFirstType) 
            + "&secondType=" + encode(strSecondType) + "&thirdType=" + encode(strThirdType)
            + "&strPagingType=" + encode(strPagingType) + "&strPagingParams=" + encode(strPagingParams)
            + "&strPagingStep=" + encode(strPagingStep) + "&strNextPageType=" + encode(strNextPageType) 
            + "&strProductPath=" + encode(strProductPath) + "&strPathType=" + encode(strPathType)
            + "&strNextPagePath=" + encode(strNextPagePath) + "&strNextPageStr=" + encode(strNextPageStr)
            + "&strLastPageType=" + encode(strLastPageType) + "&strLastPageStr=" + encode(strLastPageStr)
            + "&strLastPageUrl=" + encode(strLastPageUrl),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {//保存失败
                alertText("保存失败！", 3500);
            } 
            else if(r =="sessionOut"){
            	doLogout();
            	
            }else {//保存成功
                alertText("保存成功！", 3500);
                //更新更改信息，页面跳转
                var data = $.parseJSON(r);
                UpdateGatherTaskInfo(data);
                $('#taskDom_Edit').animate({ width: "hide" });
                $("#TaskListDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
函数名：UpdateGatherTaskInfo
作用：在编辑或新增后，更新GatherTask列表信息
参数：data：被更改或新增的Task信息
*/
function UpdateGatherTaskInfo(data){
    var str = "";
    if(getParam("hid_taskListParam", "methodName") == "tadd"){//当为新增时，在TaskList 列表中追加数据
        str += GetGatherTaskEntry(data, '*');
        $("#TaskList").find('.noDataSrc').remove();
        $("#TaskList").prepend(str);
        var dataTotal = parseInt($("#taskPaging .dataCount").text())+1;
        $("#taskPaging .dataCount").text(dataTotal)
        $("#taskPaging .pageCount").text(parseInt((dataTotal + 9) / 10));
        $("#taskPaging .pageIndex").text(parseInt((dataTotal + 9) / 10));
    }else if(getParam("hid_taskListParam", "methodName") == "tedit"){//当为编辑时，在对应的表项中修改显示信息
        var obj =  $("#TaskList").find('.editObject').find('td');
        obj.eq(1).html('*');
        obj.eq(2).html(data.TaskName);
        obj.eq(3).html(data.FirstTypeName + ' '+ data.SecondTypeName + ' ' + data.ThirdTypeName);   
        obj.eq(6).html(data.Remark);
        obj.eq(7).html(data.CreateTime);
        $("#TaskList").find('.editObject').removeClass('editObject');
    }
}
/*
函数名：DelTask
作用：删除任务
参数：id（被删除的商品id）
*/
function DelTask(id) {
    if (confirm("是否删除？")) {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/GatherTaskServlet?methodName=tdel&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetTaskList(1);
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
}
/*
函数名：TExcute
作用：Task执行函数
参数：id，要执行的TaskId，obj：在列表中的指针
*/
function TExcute(obj, Id) {
	
	$(obj).attr("disabled", true);//使执行项的执行按钮无效
	//更改执行项的状态为"执行中"
    var tmp = $(obj).parent().parent();
    tmp.children('.gatherTaskState').children().remove();

    tmp.children('.gatherTaskPnum').text('--');
    tmp.children('.gatherTaskState').append("<span style='color:orange'>执行中<span>");
    var ord = tmp.children('.gatherTaskOrd').text();
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/GatherTaskServlet?methodName=tExcute&id=" + Id,
        dataType: "text",
        async: true,
        success: function (r) {
			$(obj).attr("disabled", false);
            var data = $.parseJSON(r);

            tmp.children('.gatherTaskPnum').text(data.ProductNum);//执行后更新商品数
            if (data.StateNum == "0") {//如果执行后Task的状态为0，则执行成功
                alertText(ord + "号数据采集完成！", 3500);
                tmp.children('.gatherTaskState').children().remove();
                tmp.children('.gatherTaskState').append("<span style='color:green;'>" + data.State + "<span>");
            } else {//如果不为0，则执行失败
                alertText(ord + "号数据采集失败！", 3500);
                tmp.children('.gatherTaskState').children().remove();
                tmp.children('.gatherTaskState').append("<span style='color:red;'>" + data.State + "<span>");
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

/*
函数名：Batchexcution
作用：批量执行函数

*/
function Batchexcution() {
	$(".batExcuteBtn").attr("disabled", true);//执行时，批量执行按钮失效
    var strIDS = "";
    $("#TaskList tr").each(function () {//统计TaskList表中被勾选的任务，获得他们的Id并更改显示的状态
        if ($(this).children("td")[0].children[0].checked) {
        	$(this).children("td").children('.excuteBtnF').attr('disabled', true);
        	$(this).children('.gatherTaskPnum').text('--');
        	$(this).children('.gatherTaskState').children().remove();
        	$(this).children('.gatherTaskState').append("<span style='color:orange'>执行中<span>");
            strIDS += $(this).children().eq(0).children().attr("rel") + ",";
        }
    });
    if (strIDS != "") {
        strIDS = strIDS.substring(0, strIDS.length - 1);
        $.ajax({
            type: "post",
            url: projectLocation
    					+ "servlet/GatherTaskServlet?methodName=bexcute&ids=" + strIDS,
            dataType: "text",
            async: true,
            success: function (r) {
				$(".excuteBtnF").attr("disabled", false);
				$(".batExcuteBtn").attr("disabled", false);
                if (r == "false") {
                    alert("批量任务执行失败！");
                } else if (r == "sessionOut") {
                    doLogout();
                }
                else {
                	  alert("批量任务执行成功！");
                	var data = $.parseJSON(r);
                	for (var i = 0; i < data.TaskStateList.length; i++)
                		  {//对于TaskList中的每一行，如果它是被执行的任务，更改它的显示状态
	                	 $("#TaskList tr").each(function () {
	                	        if ($(this).children().eq(0).children().attr("rel") == data.TaskStateList[i].Id) {
	                	        	
	                	        		$(this).children('.gatherTaskPnum').text(data.TaskStateList[i].ProductNum);
	                	        		if (data.TaskStateList[i].StateNum == "0")
	                	        		{
	                	        			$(this).children('.gatherTaskState').children().remove();
		                	            	$(this).children('.gatherTaskState').append("<span style='color:green;'>"+data.TaskStateList[i].State+"<span>");
	                	        		
	                	        		}
	                	        		else
	                	        			{
	                	        			$(this).children('.gatherTaskState').children().remove();
		                	            	$(this).children('.gatherTaskState').append("<span style='color:red;'>"+data.TaskStateList[i].State+"<span>");
	                	        			}
	                	            	
	                	            
	                	        }
	                	    });
                		  }
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    }
}

//配置测试
function CheckTask(checkType) {
    var strName = $("#txt_taskName").val();
   
    var strFirstType = $("#txt_firstTaskType").val();
    var strSecondType = $("#txt_secondTaskType").val();
    var strThirdType = $("#txt_thirdTaskType").val();
    var strUrl = $("#txt_taskURL").val();
    var strRequestType = $("#ddl_requestType").val();
    var strPagingType = $("#txt_pagingType").val();
    var strPagingParams = $("#txt_pagingParams").val();
    var strPagingStep = $("#txt_pagingStep").val();
    var strNextPageType = $("#txt_nextPageType").val();
    var strNextPagePath = $("#txt_nextPagePath").val();
    var strNextPageStr = $("#txt_nextPageStr").val();
    var strLastPageType = $("#txt_lastPageType").val();
    var strLastPageStr = $("#txt_lastPageStr").val();
    var strLastPageUrl = $("#txt_lastPageUrl").val();
    var strProductPath = $("#txt_productPath").val();
    var strPathType = $("#txt_pathType").val();
    var strParams = "";
    var strItemPath = "";
    if ($.trim(strName) == "") {
        alertText("任务名不能为空！", 3500);
        return false;
    }

    $("#taskParamsList tr").each(function () {
        strParams += $(this).children().eq(0).attr("rel") + "|" + $(this).children().eq(1).children().val() + "|" + $(this).children().eq(2).children().val() + "<>";
    });
    if (strParams != "") {
        strParams = strParams.substring(0, strParams.length - 2);
    }
    
    $("#taskItemPathList tr").each(function () {
        strItemPath += $(this).children().eq(0).attr("rel") + "|" + $(this).children().eq(0).children().val() + "|" + $(this).children().eq(1).children().val() + "|"
                    + $(this).children().eq(2).children().val() + "|" + $(this).children().eq(3).children().val() + "<>";
    });
    if (strItemPath != "") {
        strItemPath = strItemPath.substring(0, strItemPath.length - 2);
    }

    var requestUrl = ""
    if (checkType == "checkBasic") {
        requestUrl = projectLocation
					+ "servlet/GatherTaskServlet?methodName=checkBasic&name=" + strName
                    + "&strParams=" + encode(strParams) + "&strItemPath=" + encode(strItemPath)
                    + "&URL=" + encode(strUrl) + "&strRequestType=" + encode(strRequestType)
                    + "&strPagingType=" + encode(strPagingType) + "&strPagingParams=" + encode(strPagingParams)
                    + "&firstType=" + encode(strFirstType) 
                    + "&secondType=" + encode(strSecondType) + "&thirdType=" + encode(strThirdType)
                    + "&strPagingStep=" + encode(strPagingStep) + "&strNextPageType=" + encode(strNextPageType) 
                    + "&strProductPath=" + encode(strProductPath) + "&strPathType=" + encode(strPathType) 
                    + "&strNextPagePath=" + encode(strNextPagePath) + "&strNextPageStr=" + encode(strNextPageStr)
                    + "&strLastPageType=" + encode(strLastPageType) + "&strLastPageStr=" + encode(strLastPageStr)
                    + "&strLastPageUrl=" + encode(strLastPageUrl);
    } else if (checkType == "checkPaging") {
        requestUrl = projectLocation
					+ "servlet/GatherTaskServlet?methodName=checkPaging&name=" + strName
                    + "&strParams=" + encode(strParams) + "&strItemPath=" + encode(strItemPath)
                    + "&URL=" + encode(strUrl) + "&strRequestType=" + encode(strRequestType)
                    + "&strPagingType=" + encode(strPagingType) + "&strPagingParams=" + encode(strPagingParams)
                    + "&firstType=" + encode(strFirstType) 
                    + "&secondType=" + encode(strSecondType) + "&thirdType=" + encode(strThirdType)
                    + "&strPagingStep=" + encode(strPagingStep) + "&strNextPageType=" + encode(strNextPageType)
                    + "&strProductPath=" + encode(strProductPath) + "&strPathType=" + encode(strPathType)
                    + "&strNextPagePath=" + encode(strNextPagePath) + "&strNextPageStr=" + encode(strNextPageStr)
                    + "&strLastPageType=" + encode(strLastPageType) + "&strLastPageStr=" + encode(strLastPageStr)
                    + "&strLastPageUrl=" + encode(strLastPageUrl);
    } else if (checkType == "checklastPage") {
        requestUrl = projectLocation
					+ "servlet/GatherTaskServlet?methodName=checklastPage&name=" + strName
                    + "&strParams=" + encode(strParams) + "&strItemPath=" + encode(strItemPath)
                    + "&URL=" + encode(strUrl) + "&strRequestType=" + encode(strRequestType)
                    + "&strPagingType=" + encode(strPagingType) + "&strPagingParams=" + encode(strPagingParams)
                    + "&firstType=" + encode(strFirstType) 
                    + "&secondType=" + encode(strSecondType) + "&thirdType=" + encode(strThirdType)
                    + "&strPagingStep=" + encode(strPagingStep) + "&strNextPageType=" + encode(strNextPageType)
                    + "&strProductPath=" + encode(strProductPath) + "&strPathType=" + encode(strPathType)
                    + "&strNextPagePath=" + encode(strNextPagePath) + "&strNextPageStr=" + encode(strNextPageStr)
                    + "&strLastPageType=" + encode(strLastPageType) + "&strLastPageStr=" + encode(strLastPageStr)
                    + "&strLastPageUrl=" + encode(strLastPageUrl);
    } else if (checkType == "checkFieldMapping") {
        requestUrl = projectLocation
					+ "servlet/GatherTaskServlet?methodName=checkFieldMapping&name=" + strName
                    + "&strParams=" + encode(strParams) + "&strItemPath=" + encode(strItemPath)
                    + "&URL=" + encode(strUrl) + "&strRequestType=" + encode(strRequestType)
                    + "&strPagingType=" + encode(strPagingType) + "&strPagingParams=" + encode(strPagingParams)
                    + "&firstType=" + encode(strFirstType) 
                    + "&secondType=" + encode(strSecondType) + "&thirdType=" + encode(strThirdType)
                    + "&strPagingStep=" + encode(strPagingStep) + "&strNextPageType=" + encode(strNextPageType) 
                    + "&strProductPath=" + encode(strProductPath) + "&strPathType=" + encode(strPathType)
                    + "&strNextPagePath=" + encode(strNextPagePath) + "&strNextPageStr=" + encode(strNextPageStr)
                    + "&strLastPageType=" + encode(strLastPageType) + "&strLastPageStr=" + encode(strLastPageStr)
                    + "&strLastPageUrl=" + encode(strLastPageUrl);
    } else {
        alertText("错误参数！", 3500);
        return false;
    }

    $.ajax({
        type: "post",
        url: requestUrl,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("测试失败！", 4500);
            } else {
                showCheckTaskdiv(r.toLowerCase() == "true" ? "这是最后一页" : r);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function showCheckTaskdiv(content) {
    $("#checkTaskShow").css("display", "block");
    $("#checkTaskShow .content").html(content);
    $("#checkTaskShow .content").css("height", parseInt($("#checkTaskShow").height() - 22));
}
function hideCheckTaskdiv() {
    $("#checkTaskShow").css("display", "none");
}
function maxSizeCheckTaskdiv() {
    $("#checkTaskShow").css("width", "98%");
    $("#checkTaskShow").css("height", "97%");
    $("#checkTaskShow .content").css("height", parseInt($("#checkTaskShow").height() - 22));
    $("#checkTaskShow").css("top", "0");
    $("#checkTaskShow").css("left", "0");
}

function minSizeCheckTaskdiv() {
    $("#checkTaskShow").css("width", "53%");
    $("#checkTaskShow").css("height", "49%");
    $("#checkTaskShow .content").css("height", parseInt($("#checkTaskShow").height() - 22));
    $("#checkTaskShow").css("top", "25%");
    $("#checkTaskShow").css("left", "22%");
}

function pagingTypeChange() {
    if ($("#txt_pagingType").val() != "DYNAMIC") {
        $(".pagingType").hide();
		$("#dynamicPart").hide();
    } else {
        $(".pagingType").show();
		$("#dynamicPart").show();
    }
}

function nextPageTypeChange() {
    if ($("#txt_nextPageType").val() == "TEXT") {
        $(".nextPageTypeIsText").hide();
    } else {
        $(".nextPageTypeIsText").show();
    }
}

function DisplayControl(){
	if ($("#txt_PagingType").val() != "DYNAMIC") {
        $(".vpagingType").hide();
		$("#vdynamicPart").hide();
    } else {
        $(".vpagingType").show();
		$("#vdynamicPart").show();
    }
	 if ($("#txt_NextpageType").val() == "TEXT") {
	        $(".vnextPageTypeIsText").hide();
	    } else {
	        $(".vnextPageTypeIsText").show();
	    }
	
	}