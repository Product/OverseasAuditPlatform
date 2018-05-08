$(document).ready(function () {
    mouseoverout();
    tabClick();
});

function mouseoverout() {
    $("#NavigationCentent div.select").on("mouseover", function (event) {
        event.stopPropagation();
        if ($(this).find("img").hasClass("action")) {

        } else {
            $("#NavigationCentent div.select img ").attr("src", "images/OverSea/main-none-selsect.png");
            $("#NavigationCentent div.select img.action").attr("src", "images/OverSea/main-select.png");
            $(this).find("img").attr("src", "images/OverSea/main-select.png");
        }
    });

    $("#NavigationCentent div.select").on("mouseout", function (event) {
        event.stopPropagation();
        $("#NavigationCentent div.select img ").attr("src", "images/OverSea/main-none-selsect.png");
        $("#NavigationCentent div.select img.action").attr("src", "images/OverSea/main-select.png");
    });
}
function tabClick() {
    $("#NavigationCentent div.select img,#NavigationCentent div.select a").click(function (event) {
        event.stopPropagation();
        $("#NavigationCentent div.select img ").removeClass("action").attr("src", "images/OverSea/main-none-selsect.png");
        $(this).parent().find("img").addClass("action").attr("src", "images/OverSea/main-select.png");
    });
}