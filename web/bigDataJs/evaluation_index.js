//


function initIndeTree() {
    var res = getIndexTree(0);
    $("#index_tree").html("");
    $("#index_tree").append(res);
}

function indexObjChange () {
    if($("#indexObj").val() == "3") {
        $("#proType").show();
    }
    else {
        $("#proType").hide();
        getIndexList();
    }
    $("#hid_indexType").val($("#indexObj").val());
    $("#hid_indexName").val($("#indexObj").text());
}

function nextStep() {
    $("#selIndex_tab").hide();
    $("#listIndex_tab").show();
    $("#listIndex_submit").show();
    getSelectedIndex();
}


function backIndex () {
    $("#selIndex_tab").show();
    $("#listIndex_tab").hide();
    $("#listIndex_submit").hide();
}
// 加载品类第二级
function getProTypeList () {
    var str = "";
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/ProductTypeServlet?methodName=typeDIR"
        + "&productTypeId=0",
        contentType: "text",
        async: false,
        success: function (r) {
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
                        str += "<option value='"+ data[i].id +"'>"+ data[i].name +"</option>";
                    }
                }
                $("#proType").html(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

// 加载指标库
function getIndexTree(typeId,obj,flag,name) {
    var str;
    if (typeId !=0 && (typeof (obj) ) != "undefined") {
        // init
        beforeInitIndex(typeId,flag);

        str = "<ul>";

        if (obj) {
            //if ($(obj).children("i").hasClass("fa-plus-circle")) {
            //    $(obj).children("i").removeClass('fa-plus-circle');
            //    $(obj).children("i").addClass("fa-minus-circle");
            //}
            //else {
            //    $(obj).children("i").removeClass('fa-minus-circle');
            //    $(obj).children("i").addClass("fa-plus-circle");
            //}
            // 若之前请求加载过，目前只是显示和隐藏，不需要再次请求
            if ($(obj).hasClass('badge-orange')) {
                $(obj).removeClass('badge-orange');
                $(obj).next('ul').hide();
                //$(obj).children("i").removeClass('fa-minus-circle');
                //$(obj).children("i").addClass("fa-plus-circle");
                return ;
            }
            else {
                if ($(obj).hasClass('has')) {
                    $("#pro-tree li>span").filter('.badge-orange').removeClass('badge-orange');
                    $(obj).addClass('badge-orange');
                    $(obj).next('ul').show();
                    //$(obj).children("i").removeClass('fa-plus-circle');
                    //$(obj).children("i").addClass("fa-minus-circle");
                    return;
                }
            }
        }

    }
    else {
        str = "";
    }


     // treeId
    var treeId = $("#indexMenuOption").val();
    typeId == 0 ? typeId = "" : typeId = typeId;
    var url = projectLocation + "servlet/EvaluationStyleServlet?methodName=list" + "&parent=" + typeId + "&tree=" + treeId;

    $.ajax({
        type: "post",
        url: url,
        contentType: "application/json;charset=utf-8",
        dataType : "text",
        async: false,
        success: function (r) {
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
                    $.each(data,function(i){
                        if (data[i].flag === "1") {
                            str += "<li><span onclick='getIndexTree(" + data[i].id +",this,"+data[i].flag+")'>" + data[i].name + '</span>' +
                                "<button class='btn btn-xs' onclick='indexMove(\"" + data[i].id +"\",\""+ data[i].name + "\")'>添加</button></li>";
                        }
                        else {
                            str += "<li><span onclick='getIndexTree(" + data[i].id +",this,"+data[i].flag+")'>" + data[i].name + "</span></li>";
                        }
                    });
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
        //$(obj).children("i").removeClass('fa-plus-circle');
        //$(obj).children("i").addClass("fa-minus-circle");
        $("#index_tree li>span").filter('.badge-orange').removeClass('badge-orange');
        $(obj).addClass('badge-orange');
        $(obj).addClass('has');
        if(flag != "1") {
            $(obj).parent('li').append(str);
        }

    }

    return str;
}

function indexMove(id,name) {
    if ($("#indexMenuO").is(":hidden")) {
        if(displayIndex.indexOf(id) == "-1"){
            var str = "<li><input name='index'  type='checkbox' value='"+ id +"'/>"+name+"</li>";
            $("#index_option").append(str);
        }
        else{
            alertText("已添加过当前指标！",3500);
        }
    }
}

function beforeInitIndex(id,flag) {
    // 编辑不会修改父节点
    $("#hiddenIndex_edit").hide();
    $("#indexTreeAdd").hide();
    $("#indexAdd").show();
    $("#indexTreeDel").hide();

    $("#hidIndex_id").val(id);
    $("#hidIndex_flag").val(flag);

    initIndextype(id,flag);

}

function initIndextype(productId,flag) {
    var url = "";
    if (flag=="1") {
        url = projectLocation + "servlet/EvaluationIndexServlet?methodName=get&id=" + productId;
    }
    else {
        url = projectLocation + "servlet/EvaluationStyleServlet?methodName=get&id=" + productId
    }
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
                $("#hid_IndexTypeParam").val("methodName=edit&id=" + productId);
                $("#txt_IndexTypeName").val(r.name);
                if (flag === 1) {
                    $("#hidIndex_pId").val(r.evaluationStyle.id);
                }
                else if(flag === 0) {
                    $("#hidIndex_pId").val(r.tree.id);
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
}

//function getIndexTree () {
//    var str = "";
//    $.ajax({
//        type: "post",
//        url: projectLocation
//        + "servlet/EvaluationIndexServlet?methodName=allIndices",
//        contentType: "text",
//        async: false,
//        success: function (r) {
//            if (r == "false") {
//                return false;
//            }
//            else if(r =="sessionOut"){
//                doLogout();
//                return false;
//            }
//            else {
//                var data = $.parseJSON(r);
//                if (data.length > 0) {
//                    for (var i = 0; i < data.length; i++) {
//                        if (data[i].indices.length > 0) {
//                            str += "<li><span onclick='openCloseIndex(this)'><i class='fa fa-plus-circle'></i>" + data[i].styleName +
//                                "</span>";
//                            str += "<ul>"
//                            for (var j =0 ; j< data[i].indices.length; j++){
//                                str += "<li><span>" + data[i].indices[j].name +"</span></li>";
//                            }
//                            str += "</ul></li>";
//                        }
//                        else {
//                            str += "<li><span>" + data[i].styleName + "</span></li>";
//                        }
//                    }
//                    $("#index_tree").html(str);
//                }
//                else {
//                    $("#index_tree").html('暂无数据！');
//                }
//            }
//        },
//        error: function (e) {
//            alert(e.responseText);
//        }
//    });
//
//}

//指标目录添加
function addIndexType() {
    // 新增会修改父节点
    $("#hiddenIndex_edit").show();
    $("#indexTreeAdd").hide();
    $("#indexAdd").show();
    $("#indexTreeDel").hide();
    $("#hid_IndexTypeParam").val("methodName=add");
    $("#txt_IndexTypeName").val('');
    $("#propertyIndexFirstSel").val('');
    $("#propertyIndexSecondSel").val('');
    $("#hidIndex_pId").val('');
    $("#propertyIndexFirstSel").html('<option value="0">请选择</option>');
    $("#propertyIndexSecondSel").html('<option value="0">请选择</option>');

    $("#hidIndex_id").val("0");
    $("#hidIndex_flag").val("");
    getIndexsMenu(0,0);
}

// 新增编辑指标保存
function SaveIndexType() {
    var strName = encode($("#txt_IndexTypeName").val());
    var strPtid;
    if ($.trim(strName) == "") {
        alertText("类型名称不能为空！", 3500);
        return false;
    }
    // 得到menuId
    var menuId = $("#indexMenuOption").val();

    var flag = $("#hidIndex_flag").val();
    var url = "";
    // 编辑
    if (flag === "1" || flag === "0") {

        // 父ID没有更改
        strPtid = $("#hidIndex_pId").val();
        if (flag == "1") {
            url = projectLocation + "servlet/EvaluationIndexServlet?methodName=save"
                + "&id=" + getParam("hid_IndexTypeParam", "id")
                + "&name=" + strName + "&evaluationStyle=" + strPtid ;
        }
        else if(flag == "0") {
            url = projectLocation + "servlet/EvaluationStyleServlet?methodName=save"
                + "&id=" + getParam("hid_IndexTypeParam", "id")
                + "&name=" + strName + "&parent=&tree="+menuId;
        }
    }
    // 新增
    else {
        if ($("#propertyIndexFirstSel").val() != null && $("#propertyIndexFirstSel").val() != '0') {
            strPtid = $("#propertyIndexFirstSel").val();
            // 父ID没有更改
            url = projectLocation + "servlet/EvaluationIndexServlet?methodName=save"
                + "&name=" + strName + "&evaluationStyle=" + strPtid ;
        }
        else {
            url = projectLocation + "servlet/EvaluationStyleServlet?methodName=save"
                + "&name=" + strName + "&parent=&tree="+menuId;
        }
    }

    $.ajax({
        type: "post",
        url: url,
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
                initIndeTree();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//根据id获取指标种类结构
function getIndexsMenu(id,level) {
    var str = '<option value="0">请选择</option>';
    // 得到menuId
    var menuId = $("#indexMenuOption").val();
    id == 0 ? id = "" : id = id;

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EvaluationStyleServlet?methodName=list" + "&parent=" + id +"&tree=" + menuId ,
        contentType: "application/json;charset=utf-8",
        dataType : "text",
        async: true,
        success: function (r) {
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
                    $("#propertyIndexFirstSel").html('');
                    $("#propertyIndexFirstSel").append(str);
                }else if (level == 1){
                    $("#propertyIndexSecondSel").html('');
                    $("#propertyIndexSecondSel").append(str);
                }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function proIndexSelect(level) {
    var parentId;
    if (level == 1) {
        parentId = $("#propertyIndexFirstSel").val();
        if (parentId == '') {
            return;
        }
        else {
            getIndexsMenu(parentId,level);
        }
    }
    else {
        parentId = $("#propertyIndexSecondSel").val();
        if (parentId == '') {
            return;
        }
        else {
            getIndexsMenu(parentId,level);
        }
    }
}

//删除目录树
function delIndexType() {
    var id =$("#hidIndex_id").val();
    if (id == "") {
        alertText("请选中左侧指标树！", 3500);
        return false;
    }
    var flag = $("#hidIndex_flag").val();
    var url = "";
    if (flag === "1") {
        url = projectLocation + "servlet/EvaluationIndexServlet?methodName=del&id=" + id;
    }
    else if (flag === "0") {
        url = projectLocation + "servlet/EvaluationStyleServlet?methodName=del&id=" + id;
    }
    else {
        alertText("请选中左侧指标树！", 3500);
        return false;
    }
    $.ajax({
        type : "post",
        url : url,
        dataType : "text",
        async : false,
        success : function(r) {
            if (r == "true") {
                alertText("删除成功！",  3500);
                addIndexType();
                initIndeTree();
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


function openCloseIndex(obj) {
    $(obj).parent("li").children("ul").is(":hidden") ? $(obj).parent("li").children("ul").show() : $(obj).parent("li").children("ul").hide();
}

// 加载指标列表
var displayIndex=[];
function getIndexList() {
    var str = "";
    var id = $("#indexObj").val();
    var productType = $("#proType").val();
    var strQuery = encode($("#index_Query").val());
    if (id != "3") {
        productType = "";
    }
    $("#index_option").html("");
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/EvaluationManagementServlet?methodName=init&type="+id+"&productType="+productType+
        "&strQuery="+strQuery,
        contentType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
                return false;
            }
            else {
                //var data = $.parseJSON(r);
                var data = r;
                displayIndex = [];
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        displayIndex.push(data[i].id);
                        if (data[i].isChecked) {
                            str += "<li><input name='index' rel='"+data[i].weight+"' type='checkbox' value='"+ data[i].id +"' checked />" + data[i].name + "</li>";
                        }
                        else {
                            str += "<li><input name='index' rel='"+data[i].weight+"' type='checkbox' value='"+ data[i].id +"'/>" + data[i].name + "</li>";
                        }
                    }
                    $("#index_option").html(str);
                }
                else {
                    $("#index_option").html('暂无数据！');
                }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

var chk_value =[];
function getSelectedIndex () {
    chk_value =[];
    $('input[name="index"]:checked').each(function(){
        var item = {};
        item.id = $(this).val();
        item.weight = $(this).attr("rel");
        item.name = $(this).parent().text();
        chk_value.push(item);
    });

    var type = $("#indexObj").val();
    var typeName = "";
    switch (type){
        case "1":
            typeName = "电商平台";
            break;
        case "2":
            typeName = "品牌";
            break;
        case "3":
            typeName = "品类  ";
            typeName += $('#proType option:selected').text();
            break;

    }
    var length = chk_value.length;
    var str = "";
    $("#index_list").html("");
    for (var i = 0 ; i < length; i++) {
        if (i == 0) {
            str += "<tr>";
            str += "<td rowspan='"+ length +"'>" + typeName + "</td>";
            str += "<td>" + (i+1) + "</td>";
            str += "<td>" + chk_value[i].name + "</td>";
            str += "<td><input type='text' value='"+ chk_value[i].weight +"' id='index_"+chk_value[i].id+"'></td>";
            str += "</tr>";
        }
        else {
            str += "<tr>";
            str += "<td>" + (i+1) + "</td>";
            str += "<td>" + chk_value[i].name + "</td>";
            str += "<td><input type='text' value='"+ chk_value[i].weight +"' id='index_"+chk_value[i].id+"'></td>";
            str += "</tr>";
        }
    }
    $("#index_list").html(str);
}

function saveIndex() {
    var type = $("#indexObj").val();
    var productType = $("#proType").val();
    var weights = "";
    var total = 0;
    for (var i = 0 ; i < chk_value.length; i++) {
        var weight = $("#index_"+chk_value[i].id).val();
        total += Number($("#index_"+chk_value[i].id).val());
        weights += chk_value[i].id+","+weight+"#";
    }
    if (total != 1) {
        alertText("权重和必须为1 ！", 3500);
        return;
    }
    weights = encode(weights.substring(0,weights.length-1));

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EvaluationManagementServlet?methodName=add&type=" + type +
        "&productType=" + productType + "&weights=" + weights,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("保存失败！", 3500);
            }
            else if(r =="sessionOut"){
                doLogout();

            }else {
                alertText("保存成功！", 3500);
                backIndex();
                getIndexList();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function averageWeight() {
    for (var i = 0 ; i < chk_value.length; i++) {
        $("#index_"+chk_value[i].id).val((1/chk_value.length).toFixed(2));
    }
}

// 编辑按钮点击
function editIndexTree() {
    $("#delAddBtn").show();
    $("#editBtn").hide();
    //目录管理显示
    $("#indexManageO").show();
    indexList();
    getIndexsMenu(0,0);
}

function indexList() {
    if ($("#indexManageO").is(":hidden")) {
        // 指标配置显示
        $("#indexMenuO").hide();
        $("#indexManageO").show();
        $("#evaluation_index .menu_list").children("div").removeClass("Title_tab");
        $("#indexC").addClass("Title_tab");
        $("#delAddBtn").hide();
        $("#editBtn").show();

    }
    else {
        // 目录管理显示
        $("#indexMenuO").show();
        $("#indexManageO").hide();
        $("#evaluation_index .menu_list").children("div").removeClass("Title_tab");
        $("#menuM").addClass("Title_tab");
        $("#delAddBtn").show();
        $("#editBtn").hide();
    }
}

//加载目录树
function  getIndexTrees(type) {
    var str = '';
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/EvaluationTreeServlet?methodName=list" ,
        dataType : "text",
        async: true,
        success: function (r) {
            if (r == "false") {
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
                return false;
            }
            else {
                var data = $.parseJSON(r);
                // 根据type去判断在哪加载
                // 1加载目录指标树的option
                if (type == "1") {
                    $("#indexMenuOption").html('');
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            str += "<option value='"+ data[i].id +"'>" + data[i].name + "</option>";
                        }
                    }
                    $("#indexMenuOption").html(str);
                }
                else if (type == "2") {
                    $("#indexsDel").html('');
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            str += '<tr class="twoTabNormal"><td class="tab_th"><input name="indexDel" type="radio" value="'+data[i].id+'">' +
                                '</td><td class="tab_tr">'+data[i].name+'</td></tr>';
                        }
                    }
                    $("#indexsDel").html(str);
                }

            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

// 目录树的新增
function addIndexTree() {
    // 显示新增界面
    $("#indexTreeAdd").show();
    $("#indexAdd").hide();
    $("#indexTreeDel").hide();

    $("#txt_indexTreeName").val("");

}

// 目录树新增操作
function SaveIndexsTree() {
    var menuName = encode($("#txt_indexTreeName").val());
    if ($.trim(menuName) == "") {
        alertText("名称不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EvaluationTreeServlet?methodName=save"
        + "&name=" + menuName,
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
                getIndexTrees("1");
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

// 目录树删除
function delIndexTree() {
    // 显示目录树删除界面
    $("#indexTreeAdd").hide();
    $("#indexAdd").hide();
    $("#indexTreeDel").show();
     getIndexTrees('2')
}

//目录树删除操作
function DelIndexs() {
    var id = $('input:radio[name="indexDel"]:checked').val();
    if (id == "" || typeof id == "undefined") {
        alertText("请选中目录！", 3500);
        return false;
    }
    $.ajax({
        type : "post",
        url : projectLocation
        + "servlet/EvaluationTreeServlet?methodName=del&id=" + id,
        dataType : "text",
        async : false,
        success : function(r) {
            if (r == "true") {
                alertText("删除成功！",  3500);
                getIndexTrees("1");
                delIndexTree();
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

