;(function (factory) {
    if (typeof define === "function" && define.amd) {
        // AMD模式
        define([ "jquery" ], factory);
    } else {
        // 全局模式
        factory(jQuery);
    }
}(function ($) {
	//当不是disabled状态并值为空时赋值
    $.fn.valueNotDisabled = function (value) {
    	if(!this.is(":disabled") && JL.isNull(this.value)){
    		this.val(value);
    	}
    };

    //当不是disabled状态并值不为空时赋值
    $.fn.valueNotNull_NotDisabled = function (value) {
    	if(!this.is(":disabled")){
    		this.val(value);
    	}
    };
    
    //当不是disabled状态并值不为空时赋值
    $.fn.tooltip = function (tips) {
    	var width = this.width();
    	
    	var div = $("<div>");
    	div.css({
    		"position": "absolute",
    		"z-index": "10",
    		"width": width+2,
    		"margin-top": "50px",
    		"background-color": "#FEFEFE",
    		"box-shadow": "0 0 10px #EEE",
    		"border": "1px solid #DDD",
    		"border-radius": "4px", 
    		"padding": "10px"
    	});
    	div.append(tips);
    	div.hide();
    	this.after(div);
    	
    	this.mouseover(function(){
    		div.fadeIn();
    	});
    	this.mouseleave(function(){
    		div.fadeOut();
    	});
    };
}));
