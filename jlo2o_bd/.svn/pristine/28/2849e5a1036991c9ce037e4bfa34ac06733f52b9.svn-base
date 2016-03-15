function init() {
    var divs = $("div[jl]");
    for (var i = 0; i < divs.length; i++) {
        //alert($(divs[i]).attr('id'));
        eval($(divs[i]).attr('jlid') + ".init(" + $(divs[i]).attr('id') + ");");
    }
}

//当弹出窗口有初始方法时，人为手动初始GRID数据
if(typeof(openUrlInit)!="function"){
	init();
}