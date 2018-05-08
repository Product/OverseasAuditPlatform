//样品检测搜索
function DetectingSuggestMapInit() {
    var strChoices = getChoices();
    var choicesLength = $("#chioceDisplay").children("span").length;

    if($("input[name='csa']:checked").val() == 2 ){
        $.ajax({
            type: "post",
            url: "./servlet/DetectingSuggestServlet?methodName=EvaluationList&sourceTypeId=" +
                $("input[name='csa']:checked").val() + "&ProductTypeChoices=" + strChoices + "&choiceslength=" + choicesLength + "&suggestNum=" + $("#txt_suggestNum").val() + 
                  "&EvaluationRangeMin=" + $("#txt_evaluationRangeMin").val() + "&EvaluationRangeMax=" + $("#txt_evaluationRangeMax").val(),
            dataType: "text",
            async: false,
            success: function (r) {
                if (r != "false") {
                    var data = $.parseJSON(r);
                    mapLoading(data.strTitle, data.strCountryNum);
                    $(data.strCountryNum).each(function (i) {
                        codeAddress(data.strCountryNum[i].name, "有" + data.strCountryNum[i].value + "个推荐数据。<input class='btn btn-default btn-xs' type='button' value='返 回' onclick='map.setZoom(3);' />");
                    });
                }
                else if (r == "sessionOut") {
                    indexDoLogout();
                }
                else {
                    alert("加载失败！");
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    }else{
        $.ajax({
            type: "post",
            url: "./servlet/DetectingSuggestServlet?methodName=MapList&sourceTypeId=" +
                $("input[name='csa']:checked").val() + "&ProductTypeChoices=" + strChoices + "&choiceslength=" + choicesLength + "&suggestNum=" + $("#txt_suggestNum").val() + 
                "&EventKeyWord=" + encode($("#txt_eventKeyWord").val()) + "&EvaluationRangeMin=" + $("#txt_evaluationRangeMin").val() + "&EvaluationRangeMax=" + $("#txt_evaluationRangeMax").val(),
            dataType: "text",
            async: false,
            success: function (r) {
                if (r != "false") {
                    var data = $.parseJSON(r);
                    mapLoading(data.strTitle, data.strCountryNum);
                    $(data.strCountryNum).each(function (i) {
                        codeAddress(data.strCountryNum[i].name, "有" + data.strCountryNum[i].value + "个推荐数据。<input class='btn btn-default btn-xs' type='button' value='返 回' onclick='map.setZoom(3);' />");
                    });
                }
                else if (r == "sessionOut") {
                    indexDoLogout();
                }
                else {
                    alert("加载失败！");
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    }   
}

function DetectingSuggestImport() {
    var strChoices = getChoices();
    var choicesLength = $("#chioceDisplay").children("span").length;
    if($("input[name='csa']:checked").val() == 2){
        window.open("./servlet/DetectingSuggestServlet?methodName=exportEvaluationExcel&sourceTypeId=" +
            $("input[name='csa']:checked").val() + "&ProductTypeChoices=" + strChoices + 
            "&choiceslength=" + choicesLength + "&suggestNum=" + $("#txt_suggestNum").val() + "&viewForm=1" +
            "&EvaluationRangeMin=" + $("#txt_evaluationRangeMin").val() + "&EvaluationRangeMax=" + $("#txt_evaluationRangeMax").val());
    }else{
        window.open("./servlet/DetectingSuggestServlet?methodName=exportExcel&sourceTypeId=" +
                $("input[name='csa']:checked").val() + "&ProductTypeChoices=" + strChoices + 
                "&choiceslength=" + choicesLength + "&suggestNum=" + $("#txt_suggestNum").val() + "&viewForm=1" +
                "&EventKeyWord=" + encode($("#txt_eventKeyWord").val()) + "&EvaluationRangeMin=" + $("#txt_evaluationRangeMin").val() + "&EvaluationRangeMax=" + $("#txt_evaluationRangeMax").val());
    }
}

function ExtractEvent(){
    if(document.getElementById('extractEventRadio').checked){
        $('#eventKeyWord').show()//.removeClass('content-hide');
        $('#evaluationRange').hide()//.addClass('content-hide');
        $('#txt_evaluationRange').text('');
        $('#txt_evaluationRange').val('');
    }
}

function ExtractGrade(){
    if(document.getElementById('extractGradeRadio').checked){
        $('#evaluationRange').show();//.removeClass('content-hide');
        $('#eventKeyWord').hide();//.addClass('content-hide');
        $('#txt_eventKeyWord').val('');
    }
}

function ExtractAll(){
    if(document.getElementById('extractAllRadio').checked){
        $('#evaluationRange').show()//.removeClass('content-hide');
        $('#eventKeyWord').show()//.removeClass('content-hide');
    }
}
