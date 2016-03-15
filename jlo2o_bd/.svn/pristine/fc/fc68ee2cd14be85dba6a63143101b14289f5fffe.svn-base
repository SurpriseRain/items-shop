$(document).ready(function(){
   $(".shop-left-nav > li > h3 > span").click(function(){
	  var span_text = $(this).text();
	  if(span_text == "-"){
		  $(this).text("+")
		  $(this).parent("h3").siblings("a").css({"display":"none"});
		  }else{
		  $(this).text("-")
		  $(this).parent("h3").siblings("a").css({"display":"block"});
		  }
	 })
	 
	 var left_height = $(window).height()-200;
	 $(".shop-left-nav").css({"min-height":left_height});
})
