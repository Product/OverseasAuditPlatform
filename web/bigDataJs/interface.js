function viewInterface(num) {
    if(num == "1") {
        $("#data_interface").hide();
        $("#data_interfaceDetail1").show();
        $("#data_interfaceDetail2").hide();
    }
    else {
        $("#data_interface").hide();
        $("#data_interfaceDetail2").show();
        $("#data_interfaceDetail1").hide();
    }
}