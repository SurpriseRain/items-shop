$(document).ready(function(){

$(document).on("mouseover",".pro_nav_list > dl",function(){
	$(".pro_nav_main").attr("class","w12 pro_nav_main");
	$(".pro_nav_list > dl").removeClass("xuan");
	$(this).addClass("xuan");
	$(".pro_nav_case").attr("class","w10 lg_w10 md_w02 pro_nav_case display_block");
  })
$(document).on("mouseleave",".pro_nav_main",function(){
	//$(".pro_nav_main").attr("class","w12 pro_nav_main");
	$(".pro_nav_list > dl").removeClass("xuan");
	$(".pro_nav_case").attr("class","w10 lg_w10 md_w02 pro_nav_case display_none");
  })

$(document).on("mouseover",".more_bt",function(){
	$(".more_cd").attr("class","more_cd display_none");
	$(this).attr("class","more_bt xuan");
	$(".more_cd").attr("class","more_cd display_block");
  })
$(document).on("mouseleave",".more_bt",function(){
	$(".more_cd").attr("class","more_cd display_none");
	$(".more_bt").attr("class","more_bt");
  })
$(document).on("mouseleave",".toper",function(){
	$(".more_cd").attr("class","more_cd display_none");
	$(".more_bt").attr("class","more_bt");
  })
$(document).on("click",".btn",function(){
	var btn_text = $(this).text();
	if(btn_text == "更多"){
	  $(this).prev().removeClass("xuan");
	  $(this).html("收起<i class='fa fa-chevron-up'></i>");
	  $(this).parent().parent().attr("class","w12 bian");
	}else if(btn_text == "收起"){
	  $(this).prev().removeClass("xuan");
	  $(this).html("更多<i class='fa fa-chevron-down'></i>");
	  $(this).parent().parent().attr("class","w12");
	}else if(btn_text == "多选"){
	  var duoxuan_class = $(this).attr("class");
	  if(duoxuan_class == "btn xuan"){
		  $(this).removeClass("xuan");
		  $(this).next().html("更多<i class='fa fa-chevron-down'></i>");
	      $(this).parent().parent().attr("class","w12");
	     }else{
		  $(".s_main > dl > dt > a").attr("class","btn");
		  $(".s_main > dl").attr("class","w12");
		  $(".s_main > dl > dt > a:last-child").html("更多<i class='fa fa-chevron-down'></i>");
		  $(this).addClass("xuan");
		  $(this).next().html("收起<i class='fa fa-chevron-up'></i>");
	      $(this).parent().parent().attr("class","w12 bian xuan");
          $(document).on("click",".s_brand_img > ul > li",function(){
			  var img_class = $(this).attr("class");
			  if(img_class == "xuan"){
		        $(this).removeClass("xuan");
			  }else{
		        $(this).addClass("xuan");
			  }
	       })
		 }
	}
  })
$(document).on("focus",".login_info > dl > dd > input[type='text']",function(){
	$(this).parent("dd").addClass("xuan");
	$(this).parent("dd").prev("dt").addClass("xuan");
  })
$(document).on("blur",".login_info > dl > dd > input[type='text']",function(){
	$(this).parent("dd").removeClass("xuan");
	$(this).parent("dd").prev("dt").removeClass("xuan");
  })
$(document).on("focus",".login_info > dl > dd > input[type='password']",function(){
	$(this).parent("dd").addClass("xuan");
	$(this).parent("dd").prev("dt").addClass("xuan");
  })
$(document).on("blur",".login_info > dl > dd > input[type='password']",function(){
	$(this).parent("dd").removeClass("xuan");
	$(this).parent("dd").prev("dt").removeClass("xuan");
  })
})

var end=0;


$(document).on("scroll",window,function(){
  var window_height = $(window).height();
  var window_top = $(window).scrollTop();
  $("#floor .floor").each(function(index,val){
	  //alert(index);
	  var tempidex=$(val).data("index");
	  var offset_top = $(val).offset().top-100;
	  var offter_height = $(val).height();
	  /*if(window_top==1){
		  end=0;
		  $(".floor_nav").fadeOut(10);
	  }*/
	  
	  if(window_top < window_height){
		  //alert($(window).scrollTop());
		  end=0;
		  $(".floor_nav").fadeOut(10);
	   }else if( window_top >= offset_top){
		    end++;
		    if(end==1){
		    	$(".floor_nav").fadeIn();
		    }
			$(".floor_nav_main").find("dl").find("dd").removeClass("xuan");
			$(".floor_nav_main").find("dl").eq(tempidex).find("dd").addClass("xuan");
	  }
  });
  //alert("window_top:"+window_top+"window_height:"+window_height+"offset_top:"+offset_top+"offter_height:"+offter_height);
  if(window_top < window_height){
	$(".fastop").fadeOut();
   }else if(window_top > window_height){
	$(".fastop").fadeIn();
   }
})
$(document).on("click",".floor_nav_main > dl",function(){
  var window_top = $(window).scrollTop();
  var dt_text = $(this).children("dt").text();
  var xh = $(this).children("input").val();
  $('html,body').animate({scrollTop:$('#floor_'+xh).offset().top}, 500);

})



