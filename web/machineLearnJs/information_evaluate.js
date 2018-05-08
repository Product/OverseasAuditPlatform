function indexObjChange () {
    if($("#evaluateType").val() == "3") {
        $("#proType").show();
    }
    else {
        $("#proType").hide();
    }
}

// 加载品类第二级
function getProTypeListInit () {
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
            }else if(r =="sessionOut"){
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

function evaManFirstPage() {
    GetEvaluateList(1);
}

function evaManPreviousPage() {
    var pageIndex = (parseInt($("#evaluatePaging .pageIndex").text()) == 1 ? 1
        : parseInt($("#evaluatePaging .pageIndex").text()) - 1);
    GetEvaluateList(pageIndex);
}

function evaManNextPage() {
    var pageIndex = ($("#evaluatePaging .pageIndex").text() == $(
        "#evaluatePaging .pageCount").text() ? parseInt($(
        "#evaluatePaging .pageIndex").text()) : parseInt($(
        "#evaluatePaging .pageIndex").text()) + 1);
    GetEvaluateList(pageIndex);
}

function evaManEndPage() {
    GetEvaluateList(parseInt($("#evaluatePaging .pageCount").text()));
}

/*获取标准产品表项*/
function GetEvaluateEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.productInfo + "</td>";
    str += "<td>" + data.productLocation + "</td>";
    str += "<td>" + getNameById(data.evaluationType,data.productTypeName) + "</td>";
    str += "<td>" + data.score + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='viewEvaluate(\""
        + data.productTypeId + "\",\""+ data.productId + "\",\""+ data.evaluationType + "\",this)' />&nbsp;" +
        //"<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
        //+ data+ "\",DelEvaluate)' />" +
        "</td>";
    str += "</tr>";
    return str;
}

function getNameById (num,typeName) {
    var str = "";
    switch (num) {
        case "1":
            str = "电商平台";
            break;
        case "2":
            str = "品牌";
            break;
        case "3":
            str = "品类--"+typeName;
            break;
        default :
            str = "";
    }
    return str;
}
/*
 函数名：GetEvaluateList
 作用：获取评价列表
 */
function GetEvaluateList(strPageIndex) {
    var keyword = $("#txt_evaluateKeyWords").val();
    var evaluateType = $("#evaluateType").val();
    var productTypeId = $("#proType").val();
    if (evaluateType != "3") {
        productTypeId = "";
    }
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/RelEvaluationProductServlet?methodName=queryList&strQuery=" + encode(keyword)
        + "&evaluationType=" + evaluateType + "&pageIndex=" + strPageIndex + "&pageSize=10&productTypeId="+productTypeId,
        contentType: "application/json;charset=utf-8",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#evaluateManageList").html('');
            if (r == "false") {
                $("#evaluateManageList")
                    .append("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
            }
            else {
                //var data = {"total":"3","list":[
                //    {"id":"1","name":"雅培奶粉","url":"http://www:baidu.com","type":"奶粉","score":"0.33"},
                //    {"id":"2","name":"雅培奶粉2","url":"http://www:baidu.com","type":"奶粉","score":"0.33"},
                //    {"id":"3","name":"雅培奶粉3","url":"http://www:baidu.com","type":"奶粉","score":"0.33"},
                //    {"id":"4","name":"雅培奶粉4","url":"http://www:baidu.com","type":"奶粉","score":"0.33"}]};
                var data = $.parseJSON(r);
                $("#evaluatePaging .dataCount").text(data.total);
                $("#evaluatePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
                $("#evaluatePaging .pageIndex").text(strPageIndex);
                if (data.list.length > 0) {
                    var str = "";
                    for (var i = 0; i < data.list.length; i++) {
                        str += GetEvaluateEntry(data.list[i], i+1);
                    }
                    $("#evaluateManageList").append(str);
                }
                else {
                    $("#evaluateManageList")
                        .append("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                }

            }

        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
 函数名：viewEvaluate；
 作用：获取网站类型
 */
function viewEvaluate(productTypeId,productId,evaluationType,obj){
    $("#evaluate_mac").hide();
    $("#evaluate_add").hide();
    $("#evaluate_view").show();
    var trObj = $(obj).parent().parent().children();
    var proInfo = trObj.eq(1).text();
    var evaObj = trObj.eq(3).text();
    $.ajax({
        type : "post",
        url : projectLocation + "servlet/RelEvaluationProductServlet?methodName=init&productTypeId="+productTypeId
        + "&productId=" + productId + "&evaluationType=" + evaluationType,
        dataType : "text",
        async : false,
        success : function(r) {
            $("#indexViewList").html('');
            if (r == "false") {
                $("#indexViewList")
                    .append("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
            }
            else {
                var data = $.parseJSON(r);
                if (data.length > 0) {
                    var str = "";
                    str += "<tr>";
                    str += "<td rowspan='"+ data.length +"'>" + proInfo + "</td>";
                    str += "<td rowspan='"+ data.length +"'>" + evaObj + "</td>";
                    for (var i = 0; i < data.length; i++) {
                        str += "<td>" + (i+1) + "</td>";
                        str += "<td>" + data[i].evaluationName + "</td>";
                        str += "<td>" + data[i].weight + "</td>";
                        str += "<td>" + data[i].grade + "</td>";
                        str += "</tr>";
                    }
                    $("#indexViewList").append(str);
                }
                else {
                    $("#indexViewList")
                        .append("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                }

            }
        },
        error : function(e) {
            alert(e.responseText);
        }
    });
}

// 新增评价
function addEvaluate () {
    $("#evaluate_mac").hide();
    $("#evaluate_add").show();
    $("#evaluate_view").hide();

    $("#txt_evaluateType").val("0");
    $("#txt_evaluateObj").val("0");
    /*$("#txt_proType").val("0");*/
    $("#txt_proType").hide();
    $("#indexList").html("");

    getProTypeList();
    getProSel();
}

// 加载商品列表
function getProSel () {
    var str = "";
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/ProductServlet?methodName=randTopProducts",
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
                return false;
            }else if(r =="sessionOut"){
                doLogout();
                return false;
            }else {
                var data = $.parseJSON(r);
                if (data.list.length > 0) {
                    for (var i = 0; i < data.list.length; i++) {
                        str += "<option value='"+ data.list[i].id +"'>"+ data.list[i].name.substring(0,20) +"</option>";
                    }
                }
                $("#txt_evaluateObj").html(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

/*
 函数名：DelEvaluate
 作用：删除评价
 参数：data（被删除的评价data）
 */
function DelEvaluate(data) {
    var productTypeId = data.productTypeId;
    var productId = data.productId;
    var evaluationType = data.evaluationType;
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/evaluateServlet?methodName=delete&productId=" + productId + "&productTypeId=" +productTypeId
        + "&evaluationType=" + evaluationType,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("删除成功！", 3500);
                GetEvaluateList(1);
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
                $("#txt_proType").html(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function evaluateObjChange () {
    if($("#txt_evaluateType").val() == "1") {//电商平台
    	$("#txt_proType").hide();//品类绑定下拉隐藏
    	var html_=onlineRetailersOptionStr();
		$("#txt_evaluateObj").html(html_);
		evaluateIndexList();
        //getProSel();
    }else if($("#txt_evaluateType").val() == "3"){//品类
    	 $("#txt_proType").show();
         evaluateIndexList();
         getProSel();
    }else {//默认加载
    	 $("#txt_proType").hide();
    	 evaluateIndexList();
         getProSel();
    }
}

var chk_value =[];
//根据评价类型id获取对应指标
function evaluateIndexList() {
    chk_value =[];
    var typeId = $("#txt_evaluateType").val();
    var productType = $("#txt_proType").val();
    if (typeId != "3") {
        productType = "";
    }
    var str = "";
    
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/EvaluationManagementServlet?methodName=init&type="+typeId+"&productType="+productType+
        "&strQuery=",
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
                var len = 0;
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].isChecked) {
                            len = 1;
                            chk_value.push(data[i]);
                            str += "<tr>";
                            str += "<td>" + (i+1) + "</td>";
                            str += "<td>" + data[i].name + "</td>";
                            str += "<td>" + data[i].weight + "</td>";
                            str += "<td><font>*</font>&nbsp;<input type='text' id='index_"+data[i].evaluationManagemenId+"' style='ime-mode:Disabled'></td>";
                            str += "</tr>";
                        }
                    }
                    $("#indexList").html(str);
                }
                else {
                    $("#indexList").html("<tr class='noDataSrc'><td colspan='4' style='text-align:center;'>无数据！</td></tr>");
                }
                if (len === 0) {
                    $("#indexList").html("<tr class='noDataSrc'><td colspan='4' style='text-align:center;'>无数据！</td></tr>");
                }

            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}


/*
 函数名：SaveEvaluate
 作用：保存评价
 */
function SaveEvaluate() {
    var evaluationType = $("#txt_evaluateType").val();
    var productType = $("#txt_proType").val();
    var productId = $("#txt_evaluateObj").val();
    //var productId = "1111818";
    if (evaluationType != "3") {
        productType = "";
    }

    if ($.trim(evaluationType) == "") {
        alertText("评价对象类型不能为空！", 3500);
        return false;
    }

    if ($.trim(productId) == "") {
        alertText("评价对象不能为空！", 3500);
        return false;
    }
    var scores = "";
    
    var r = /^(\d|5)(\.\d)?$/;
    
    //var r = /^\+?(\d*\.\d{2})$/;
    for (var i = 0 ; i < chk_value.length; i++) {
        var score = $("#index_"+chk_value[i].evaluationManagemenId).val();
        if (!r.test($.trim(score))) {
            alertText("请输入0-5的数字，保留一位小数！", 3500);
            return false;
        }else{
            if(parseFloat(score) > 5){
                alertText("分数值不能大于5，请输入0-5的数字，保留一位小数", 3500);
                return false;
            }
            else if (parseFloat(score) < 0) {
                alertText("分数值不能小于0，请输入0-5的数字，保留一位小数", 3500);
                return false;
            }
        }
        scores += chk_value[i].evaluationManagemenId+","+score+"#";
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/RelEvaluationProductServlet?methodName=save&productId="
        + productId + "&score=" + encodeURIComponent(scores) + "&evaluationType=" + evaluationType
        + "&productTypeId=" + productType,
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
                GetEvaluateList(1);
                $("#evaluate_mac").show();
                $("#evaluate_add").hide();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//选中电商平台 加载option
function onlineRetailersOptionStr() {
    var html_ = '';
    html_ += '<option value="1117575" >邦途海外专营店</option>';
    html_ += '<option value="1117574" >EMP海外旗舰店</option>';
    html_ += '<option value="1117573" >qqg海外旗舰店</option>';
    html_ += '<option value="1117572" >伦敦客海外专营店</option>';
    html_ += '<option value="1117571" >uufirst海外专营店</option>';
    html_ += '<option value="1117570" >Woolworths海外旗舰店</option>';
    html_ += '<option value="1117569" >TARGET海外旗舰店</option>';
    html_ += '<option value="1117568" >Unichem海外旗舰店</option>';
    html_ += '<option value="1117567" >tmart海外专营店</option>';
    html_ += '<option value="1117566" >Boons海外旗舰店</option>';
    html_ += '<option value="1117565" >袋鼠街海外专营店</option>';
    html_ += '<option value="1117564" >stonehouse海外旗舰店</option>';
    html_ += '<option value="1117563" >爱他美官方海外旗舰店</option>';
    html_ += '<option value="1117562" >vvo海外专营店</option>';
    html_ += '<option value="1117561" >babycare海外专营店</option>';
    html_ += '<option value="1117560" >DIO海外旗舰店</option>';
    html_ += '<option value="1117559" >英国皇家邮政海外旗舰店</option>';
    html_ += '<option value="1117558" >Semper海外旗舰店</option>';
    html_ += '<option value="1117557" >大树连锁药局官方海外旗舰店</option>';
    html_ += '<option value="1117556" >Helsemin海外旗舰店</option>';
    html_ += '<option value="1117555" >美赞臣官方海外旗舰店</option>';
    html_ += '<option value="1117554" >格兰国际海外专营店</option>';
    html_ += '<option value="1117553" >贝拉米海外旗舰店</option>';
    html_ += '<option value="1117552" >a2海外旗舰店</option>';
    html_ += '<option value="1117551" >foodworks海外旗舰店</option>';
    html_ += '<option value="1117550" >天空之境海外专营店</option>';
    html_ += '<option value="1117549" >佰优海外专营店</option>';
    html_ += '<option value="1117548" >牛栏牌海外旗舰店</option>';
    html_ += '<option value="1117547" >优丽儿海外专营店</option>';
    html_ += '<option value="1117546" >BIOSTIME官方海外旗舰店</option>';
    html_ += '<option value="1117545" >环球e站海外专营店</option>';
    html_ += '<option value="1117544" >friso美素佳儿官方海外旗舰店</option>';
    html_ += '<option value="1117543" >贝贝金海外专营店</option>';
    html_ += '<option value="1117542" >斯诺宝海外专营店</option>';
    html_ += '<option value="1117541" >纽迪希亚官方海外旗舰店</option>';
    html_ += '<option value="1117540" >雅培官方海外旗舰店</option>';
    html_ += '<option value="1117539" >COSTCO海外旗舰店</option>';
    html_ += '<option value="1117538" >樱花商事海外专营店</option>';
    html_ += '<option value="1117537" >yuwawa母婴海外专营店</option>';
    html_ += '<option value="1117536" >西选海外旗舰店</option>';
    html_ += '<option value="1117535" >欣德海外专营店</option>';
    html_ += '<option value="1117534" >windeln官方海外旗舰店</option>';
    html_ += '<option value="1117533" >裕丰母婴海外专营店</option>';
    html_ += '<option value="1117532" >BABYKID海外旗舰店</option>';
    html_ += '<option value="1117531" >跨境淘母婴海外专营店</option>';
    html_ += '<option value="1117530" >麦乐购海外旗舰店</option>';
    html_ += '<option value="1117529" >moony海外旗舰店</option>';
    html_ += '<option value="1117528" >优贝海外专营店</option>';
    html_ += '<option value="1117527" >花王官方海外旗舰店</option>';
    return html_;
}
