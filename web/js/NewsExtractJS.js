/*
 舆情抽取函数，可根据methodName执行近期抽取和全库抽取 
 */
function Matching(methodName,Id){
	var source = document.getElementsByName('KeyWordSource');
	var strValue = "";
	for(i=0;i<source.length;i++){
		if(source[i].checked)
			strValue += source[i].value+"_";
	}
	if(strValue!="")
    {
		strValue = strValue.substring(0, strValue.length - 1);
    }
	if ($.trim(strValue) == "") {
        alertText("请选择关键字来源！", 3500);
        return false;
    }
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/NewsExtractServlet?methodName=" + methodName + "&strValue="+strValue,
		dataType : "text",
		async : true,
		beforeSend:function(XMLHttpRequest){
            //alert('远程调用开始...');
        	$("#loading1").css("display","block");
       },
		success : function(r) {
			if (r == "true") {
				alertText("抽取成功！", 3500);
			} else if(r=="sessionOut"){
				doLogout();
			}else {
				alertText("抽取失败！", 3500);
			}
		},
		complete:function(){
            // alert('远程调用成功，状态文本值：'+textStatus);
        	$("#loading1").css("display","none");
         },
		error : function(e) {
			alert(e.responseText);
		}
	});
}