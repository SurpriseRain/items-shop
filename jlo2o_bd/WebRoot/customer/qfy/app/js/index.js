$(document).ready(function(){
	/************已入库商品title固定************/
	$(window).scroll(function(){
	   var scrollTop = $(document).scrollTop();
	   if(scrollTop > 51){
		   $(".title").attr("style","position:fixed; z-index:9998; margin-top:-51px;");
		  }else if(scrollTop < 50){
		   $(".title").attr("style","position:none;margin-top:0");
		  }		
	}) 

})