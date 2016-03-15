function init() {
    var divs = $("div[jl]");
    for (var i = 0; i < divs.length; i++) {
        //alert($(divs[i]).attr('id'));
        eval($(divs[i]).attr('jlid') + ".init(" + $(divs[i]).attr('id') + ");");
    }
}

init();
