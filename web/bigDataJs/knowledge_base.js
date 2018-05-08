function RepFirstPage() {
    initRepositoryList(1);
}

function RepPreviousPage() {
    var pageIndex = (parseInt($("#repositoryPaging .pageIndex").text()) == 1 ? 1
        : parseInt($("#repositoryPaging .pageIndex").text()) - 1);
    initRepositoryList(pageIndex);
}

function RepNextPage() {
    var pageIndex = ($("#repositoryPaging .pageIndex").text() == $(
        "#repositoryPaging .pageCount").text() ? parseInt($(
        "#repositoryPaging .pageIndex").text()) : parseInt($(
        "#repositoryPaging .pageIndex").text()) + 1);
    initRepositoryList(pageIndex);
}

function RepEndPage() {
    initRepositoryList(parseInt($("#repositoryPaging .pageCount").text()));
}

function initRepositoryTree() {
    var res = getFirstTreeNum(0);
    $("#repository-tree").html("");
    $("#repository-tree").append(res);

    initRepositoryList(1);
}


function getFirstTreeNum (typeId,obj,hasChild) {
    var str;
    if (typeId !=0 && (typeof (obj) ) != "undefined") {
        // init
        beforeRepInit(typeId,obj,hasChild);

        str = "<ul>";
        // 若之前请求加载过，目前只是显示和隐藏，不需要再次请求
        if ($(obj).hasClass('badge-orange')) {
            $(obj).removeClass('badge-orange');
            $(obj).next('ul').hide();
            return ;
        }
        else {
            if ($(obj).hasClass('has')) {
                $(obj).parent().removeClass('badge-orange');
                $(obj).siblings().removeClass('badge-orange');
                $(obj).addClass('badge-orange');
                $(obj).next('ul').show();
                return;
            }
        }

    }
    else {
        str = "";
    }

    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/ProductTypeServlet?methodName=typeDIRWithNum"
        + "&productTypeId=" + typeId ,
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#websiteList").html('');
            if (r == "false") {
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
                return false;
            }
            else {
                var data = $.parseJSON(r);
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].isVisible) {
                            str += "<li><span onclick='getFirstTreeNum(" + data[i].id +",this,true)'><i class='fa fa-plus-circle'></i>" + data[i].name +
                                "<span>("+data[i].num+")</span></span></li>";
                        }
                        else {
                            str += "<li><span onclick='beforeRepInit("+ data[i].id +",this,false)'>" + data[i].name +
                                "<span>("+data[i].num+")</span></span></li>";
                        }
                    }
                }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });


    if (typeId !=0 && (typeof (obj) ) != "undefined") {
        str += "</ul>";

        //让下级隐藏显示的上面代码已经排除，此时直接追加获取好的代码
        $(obj).parent().parent().siblings().removeClass('badge-orange');
        $(obj).parent().siblings().children().removeClass('badge-orange');
        $(obj).addClass('badge-orange');
        $(obj).addClass('has');
        $(obj).parent('li').append(str);
    }

    return str;
}

function beforeRepInit(id,obj,hasChild) {
    var parentName;
    $("#repository_id").val(id);
    parentName = $(obj).parent().parent().siblings("span").text();
    if (parentName == "") {
        parentName = $(obj).text();
    }
    $("#repository_parentName").val(parentName);
    if (!hasChild) {
        // init方法
        if ($(obj).hasClass('badge-orange')) {
            $(obj).removeClass('badge-orange');
        }
        else {
            $(obj).parent().parent().siblings().removeClass('badge-orange');
            $(obj).parent().siblings().children().removeClass('badge-orange');
            $(obj).addClass('badge-orange');
        }
    }

    initRepositoryList(1,id);
}

function initRepositoryList(strPageIndex,typeId) {
    var queryStr =  $("#repository_keyword").val();
    if (typeof typeId == "undefined") {
        if ($("#repository_id").val() != "") {
            typeId = $("#repository_id").val();
        }
        else {
            return ;
        }
    }
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/repositoryServlet?methodName=getList&strPageCount=10&typeId=" + typeId +"&strPageIndex="+strPageIndex+"&queryStr="+encode(queryStr) ,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r != "false") {
                    var str = "";
                    var data = $.parseJSON(r);

                    $("#repositoryPaging .dataCount").text(data.count);
                    $("#repositoryPaging .pageCount").text(parseInt((parseInt(data.count) + 9) / 10));
                    $("#repositoryPaging .pageIndex").text(strPageIndex);

                    if (data.repositoryList.length > 0) {
                        for (var i = 0; i < data.repositoryList.length; i++) {
                            str += GetRepositoryEntry(data.repositoryList[i], i + 1);
                        }
                        $("#repository-list").html(str);
                    }
                    else {
                        $("#repository-list").html("暂无数据");
                    }
                }
                else if(r == "sessionOut"){
                    doLogout();
                }
                else if(r == "false"){
                    $("#repository-list").html("<div style='text-align: center'>暂无数据！</div>")
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
         });
}

/*获取知识库表项*/
function GetRepositoryEntry(data){
	 var str = "<div class='repository-div'><div>";
	    str += "<span class='repository-title'>" + data.title +"</span>";
	    str += "<span style='color: #b6cad2'>[" + data.createtime + "]</span>";
		str += "<div style='float:right'>";
		str += "<input class='btn btn-default btn-xs' type='button' value='查 看' onclick='repositoryView(" + data.id + ")' />";
		str += "<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='repositoryEdit(" + data.id + ")' />";
	    str += "<input class='btn btn-default btn-xs' type='button' value='执行' onclick='execteKnowledge(" + data.id + ")' />";
	    str += "<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(" + data.id + ",delRepository)' /></div>";
	    str += "</div>";
	    str += "<span style='color: #b6cad2'> 摘要：&nbsp;" + data.content +"</span>";
	    str += "</div>";
	    return str;
}

//知识库新增
function repositoryAdd() {
    $("#rep_list").hide();
    $("#rep_add").show();
    $("#rep_add .addPoint").html("商品类别新增");

    $("#txt_repName").val('');
    $("#txt_repTypeRemark").val('');
    $("#rep_displayType").text('');
    $("#rep_propertyFirstSel").html('<option value="0">请选择</option>');
    $("#rep_propertySecondSel").html('<option value="0">请选择</option>');
    $("#rep_propertyThirdSel").html('<option value="0">请选择</option>');
    $("#hid_repTypeParam").val("methodName=add");
    // 获取商品第一级下拉框
    getRepMenu(0,0);
}

//知识库编辑
function repositoryEdit(id) {
    $("#rep_list").hide();
    $("#rep_add").show();
    $("#rep_add .addPoint").html("商品类别编辑");
    var parentName = $("#repository_parentName").val();
    $("#rep_displayType").text(parentName);
    $("#rep_propertyFirstSel").html('<option value="0">请选择</option>');
    $("#rep_propertySecondSel").html('<option value="0">请选择</option>');
    $("#rep_propertyThirdSel").html('<option value="0">请选择</option>');

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/repositoryServlet?methodName=queryById&id=" + id,
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
                $("#hid_repTypeParam").val("methodName=edit&id=" + id);
                $("#txt_repName").val(r.title);
                $("#txt_repTypeRemark").val(r.content);
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

    getRepMenu(0,0);
}

//知识库查看
function repositoryView(id) {
    $("#rep_list").hide();
    $("#rep_view").show();
    var parentName = $("#repository_parentName").val();
    $("#rep_displayTypeView").text(parentName);
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/repositoryServlet?methodName=queryById&id=" + id,
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
                $("#txt_repNameView").val(r.title);
                $("#txt_repRemarkView").val(r.content);
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

//知识库删除
function delRepository(id) {
    $.ajax({
        type : "post",
        url : projectLocation
        + "servlet/repositoryServlet?methodName=del&id=" + id,
        dataType : "text",
        async : false,
        success : function(r) {
            if (r == "true") {
                alertText("删除成功！",  3500);
                initRepositoryList(1);
               // initRepositoryList();
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

function p(s) {
    return s < 10 ? '0' + s: s;
}

function getFormatTime(currentDate) {
    //获取当前年
    var year=currentDate.getFullYear();
    //获取当前月
    var month=currentDate.getMonth()+1;
    var date=currentDate.getDate();
    var h=currentDate.getHours();       //获取当前小时数(0-23)
    var m=currentDate.getMinutes();     //获取当前分钟数(0-59)
    var s=currentDate.getSeconds();
    var now=year+'-'+p(month)+"-"+p(date)+" "+p(h)+':'+p(m)+":"+p(s);
    return now;
}

//知识库保存
function saveRep() {
    var title = encode($("#txt_repName").val());
    var content = encode($("#txt_repTypeRemark").val());
    var time = getFormatTime(new Date());
    var strPtid;
    if ($("#rep_propertyThirdSel").val() != null && $("#rep_propertyThirdSel").val() != '0') {
        strPtid = $("#rep_propertyThirdSel").val();
    }
    else {
        if ($("#rep_propertySecondSel").val() != null && $("#rep_propertySecondSel").val() != '0') {
            strPtid = $("#rep_propertySecondSel").val();
        }
        else {
            if ($("#rep_propertyFirstSel").val() != null && $("#rep_propertyFirstSel").val() != '0') {
                strPtid = $("#rep_propertyFirstSel").val();
            }
            else {
                // 父ID没有更改
                strPtid = $("#repository_id").val();
            }
        }
    }

    if ($.trim(title) == "") {
        alertText("名称不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/repositoryServlet?methodName=save&author=admin"
       // + getParam("hid_repTypeParam", "methodName")
        + "&id=" + getParam("hid_repTypeParam", "id")
        + "&title=" + title + "&content=" + content
        + "&typeId=" + strPtid +"&createtime="+time,
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
                $("#rep_list").show();
                $("#rep_add").hide();
                alertText("保存成功！", 3500);
                initRepositoryList(1);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function showList (node) {
    $("#"+node).hide();
    $("#rep_list").show();
}

//根据id获取商品种类结构
function getRepMenu(id,level) {
    var str = '<option value="0">请选择</option>';
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/ProductTypeServlet?methodName=typeDIR"
        + "&productTypeId=" + id ,
        contentType: "application/json;charset=utf-8",
        async: true,
        success: function (r) {
            $("#websiteList").html('');
            if (r == "false") {
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
                return false;
            }
            else {
                var data = $.parseJSON(r);
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        str += "<option value='"+ data[i].id +"'>" + data[i].name + "</option>";
                    }
                }
                if (level == 0) {
                    $("#rep_propertyFirstSel").html('');
                    $("#rep_propertyFirstSel").append(str);
                }else if (level == 1){

                    $("#rep_propertySecondSel").html('');
                    $("#rep_propertySecondSel").append(str);
                }else {
                    $("#rep_propertyThirdSel").html('');
                    $("#rep_propertyThirdSel").append(str);
                }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function proRepSelect(level) {
    $("#rep_displayType").text('');
    var parentId;
    if (level == 1) {
        parentId = $("#rep_propertyFirstSel").val();
        if (parentId == '') {
            return;
        }
        else {
            getRepMenu(parentId,level);
        }
    }
    else {
        parentId = $("#rep_propertySecondSel").val();
        if (parentId == '') {
            return;
        }
        else {
            getRepMenu(parentId,level);
        }
    }
}

//执行
function execteKnowledge(id){
	 $.ajax({
	        type: "post",
	        url: projectLocation 
	        	+ "servlet/CollectorCfg?method=excuteExpertRepository&id=" + id,
	        dataType: "json",
	        async: false,
	        success: function (r) {
	            if (r == "success") {
	            	 alertText("执行成功！后台正在爬去数据，请等待……",4000);
	            }
	            else if(r == "sessionOut"){
	                doLogout();
	            }
	            else  {
	                alertText("执行失败！",3500);
	            }
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
}

