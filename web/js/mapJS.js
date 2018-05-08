//GMapOptions
var geocoder = null;
var map = null;
//加载
function initialize() {
    geocoder = new google.maps.Geocoder();
    var latlng = new google.maps.LatLng(31.169419, 121.000000);
    var myOptions = {
        zoom: 3,
        center: latlng,
        scrollwheel: false,
        draggable: true,
        disableDoubleClickZoom: true,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

    //国家颜色高亮加载
//    var strCountryColor = "[{\"LatLng\":\"37.0902400000,-95.7128910000\",\"Color\":\"#E5EFD6\"}," +
//                            "{\"LatLng\":\"-25.2743980000,133.7751360000\",\"Color\":\"#FAFC53\"}," +
//                            "{\"LatLng\":\"61.5240100000,105.3187560000\",\"Color\":\"#EBDAC6\"}]";
//    strCountryColor = $.parseJSON(strCountryColor);
//    $(strCountryColor).each(function (i) {
//        showBoundaryEx(strCountryColor[i].LatLng, strCountryColor[i].Color);
//    });

    //模拟数据加载
//    var strJSON = "{\"LocationData\":[{\"Address\":\"开罗\"},{\"Address\":\"美国\"},{\"Address\":\"东京\"}]}";
//    strJSON = $.parseJSON(strJSON);
//    $(strJSON.LocationData).each(function (i) {
//        codeAddress(strJSON.LocationData[i].Address, "内容 <input type='button' value='返回' onclick='map.setZoom(3);' />");
//    });
}
//google.maps.event.addDomListener(window, 'load', initialize);

//根据地址添加标注和信息
function codeAddress(address, content) {
//    geocoder.geocode({ 'address': address }, function (results, status) {
//        if (status == google.maps.GeocoderStatus.OK) {
//            //添加标注
//            var marker = new google.maps.Marker({
//                map: map,
//                position: results[0].geometry.location,
//                title: address
//            });
//            //鼠标进入事件
//            google.maps.event.addListener(marker, 'mouseover', function () {
//                if (marker.getZIndex() != 1000) {
//                    marker.setZIndex(1000);
//                }
//            });
//            //鼠标移出事件
//            google.maps.event.addListener(marker, 'mouseout', function () {
//                if (marker.getZIndex() != -1) {
//                    marker.setZIndex(-1);
//                }
//            });
//            //单击标注的事件
//            google.maps.event.addListener(marker, 'click', function () {
//                map.setZoom(7);
//                map.setCenter(marker.getPosition());
//                if (!infoawindow) {
//                    //单例infowindow  
//                    var infoawindow = new google.maps.InfoWindow({
//                        content: "<div>" + content + "</div>",
//                        maxWidth: 200
//                    });
//                    infoawindow.open(map, marker);
//                }
//            });
//        } else {
//            alert("Geocode was not successful for the following reason: " + status);
//        }
//    });
}

var polyOptions = {
    strokeColor: "#9B868B",
    fillColor: "#FF8C69",
    fillOpacity: 0.6,
    strokeWeight: 1,
    zIndex: 1
}
//显示地图
//$("#map_canvas").show();
//google.maps.event.trigger(map, 'resize');

function showBoundaryEx(latLngs, color) {
    var paths = [];
    paths.push(new google.maps.LatLng(latLngs.split(',')[1], latLngs.split(',')[0]));

    var polygon = new google.maps.Polygon();
    polygon.setOptions(polyOptions);
    polygon.setOptions({
        fillColor: color
    });
    polygon.setPaths(paths);
    polygon.setMap(map);

    google.maps.event.addListener(polygon, "mousemove", function () {
        polygon.setOptions({
            fillColor: "#FFFF00"
        });
    });

    google.maps.event.addListener(polygon, "mouseout", function () {
        polygon.setOptions({
            fillColor: color
        });
    });
}

function modeSwitchMap() {
    if ($("#main").is(":hidden")) {
        $("#map_canvas").hide();
        $("#main").show();
    } else {
        $("#main").hide();
        $("#map_canvas").show();
        google.maps.event.trigger(map, 'resize');
    }
}