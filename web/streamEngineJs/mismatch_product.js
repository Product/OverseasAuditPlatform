/*机器学习-商品管理-商品管理*/

function FirstPage_product(){
    GetProductList(1,10);
    $("#productPaging").hide();
}

function PreviousPage_product() {
    var pageIndex = (parseInt($("#productPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#productPaging .pageIndex").text()) - 1);
    GetProductList(pageIndex,10);
    $("#productPaging").hide();
}

function NextPage_product() {
	var pageIndex = ($("#productPaging .pageIndex").text() == $("#productPaging .pageCount").text() ? parseInt($("#productPaging .pageIndex").text()) : parseInt($("#productPaging .pageIndex").text()) + 1);
    GetProductList(pageIndex,10);
    $("#productPaging").hide();
}

function EndPage_product() {
	GetProductList(parseInt($("#productPaging .pageCount").text()),10);
	$("#productPaging").hide();
}


/**
 * 商品管理的list
 * @param strPageIndex
 * @param strPageCount
 */
function GetProductList(strPageIndex,strPageCount) {
   $.ajax({
       type: "post",
       url: projectLocation
		+ "servlet/ProductServlet?methodName=QueryRealtimeList&strPageIndex="+ strPageIndex+"&strPageCount="+strPageCount,
       contentType: "application/json;charset=utf-8",
       async: false,
       success: function (r) {
           $("#productList").html('');
           if (r == "false") {
               $("#productList")
						.append("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
               return false;
           }
           else if(r =="sessionOut"){
           	doLogout();
           	
           }
			$("#productPaging .dataCount").text(r.Total);
			$("#productPaging .pageCount").text(parseInt((parseInt(r.Total) + 9) / 10));
			$("#productPaging .pageIndex").text(strPageIndex);
			
           var str = "";
           var j = 0;
           for (var i = 0; i < r.ProductList.length; i++) {
           	 str += GetProductEntry(r.ProductList[i], i+1);
           }
           $("#productList").append(str);
           //$("#productPaging").hide();
       },
       error: function (e) {
           alert(e.responseText);
       }
   });
}

/**
 * 获取商品管理数据
 * @param data
 * @param idx
 * @returns {String}
 */
function GetProductEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.WebSite + "</td>";
    str += "<td>" + data.IsRisk+ "</td>";
    str += "<td>" + data.Time+ "</td>";
    str += "<td>" + data.IsUpdate+ "</td>";
    str += "</tr>";
    return str;
}

/**
 * 商品管理实时刷新
 */
function FirstPage_mismatch_product(){
    GetProductList(1,15);//获取第一页数据
	//计时，自动 
	var i =1;
	setInterval(function fresh(){
		GetProductList(i,15);
		i++;
	},5000);
	//$("#productPaging").hide();//将分页隐藏
}


/**
 * 添加计时器，自动刷新页面
 */
/*function fresh(){
	GetProductList(i,15);//加载不匹配商品列表

}*/

//关闭计时器
function stopfresh(){
	if(typeof timeCount != "undefined"){
		clearTimeout(timeCount);
	}
}
