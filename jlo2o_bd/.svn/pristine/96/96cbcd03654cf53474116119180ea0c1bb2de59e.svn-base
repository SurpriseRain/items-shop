$(document).ready(function(){
$(document).on("mouseover",".more_bt",function(){
	$(".more_cd").attr("class","more_cd display_none");
	$(this).attr("class","more_bt xuan");
	$(this).children(".more_cd").attr("class","more_cd display_block");
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


$(document).on("scroll",window,function(){
  var window_height = $(window).height();
  var window_top = $(window).scrollTop();
  var offset_top = $(".floor_case").offset().top-100;
  var offter_height = $(".floor_case").height();
  if(window_top < window_height){
	$(".fastop").fadeOut();
   }else if(window_top > window_height){
	$(".fastop").fadeIn();
   }if(window_top < offset_top){
	$(".floor_nav").fadeOut(10); 
   }else if(window_top == offset_top || window_top > offset_top){
	$(".floor_nav").fadeIn();  
  }
})
$(document).on("click",".floor_nav_main > dl",function(){
  var offset_top = $(".floor_case").offset().top;
  var window_top = $(window).scrollTop();
  var dt_text = $(this).children("dt").text();
  if(dt_text == "1F"){
	$('html,body').animate({scrollTop:offset_top},300);
  }
})

