
$(document).ready(function(){
	$("#allgoods").mouseover(function(){
		$("#inav-flshow").css({"display":"block"});
	})
	$("#inav-flshow").mouseover(function(){
		$("#inav-flshow").css({"display":"block"});
	})
	$("#allgoods").mouseleave(function(){
		$("#inav-flshow").css({"display":"none"});
	})
	$("#inav-flshow").mouseleave(function(){
	$("#inav-flshow").css({"display":"none"});
	})
	
	$("#showwx").mouseover(function(){								
		$("#wxtp").css({"display":"block"});
	})
	$("#showwx").mouseleave(function(){
		$("#wxtp").css({"display":"none"});
	})
	
	$(".news-pro > li").mouseover(function(){
		var li_class = $(this).attr("class");
		if(li_class == "zq" || li_class == "img"){
			$(this).children(".zz").css({"display":"block"})
		 }else{
			$(this).append('')
		 }
    })
	$(".news-pro > li").mouseleave(function(){
			$(this).children(".zz").css({"display":"none"})
    })
})