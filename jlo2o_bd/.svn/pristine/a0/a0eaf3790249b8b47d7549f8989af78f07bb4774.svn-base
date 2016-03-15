$(document).ready(function(){
	/************关闭二维码************/
	$(".ewmclose").click(function(){
	   $(this).parent().remove();	
     })
	
	/************菜单显示************/
  $(".nav_fl_list > li").mouseover(function(){
	  var this_zt = $(this).attr("class");
	  var nav_title_name = $(this).children("b").children(".nav_main").children("h3").text();
	  $(".nav_fl_list > li").removeClass("xuan");
	  $(this).addClass("xuan");
//	    if(nav_title_name == "政策法规"){
//		   $(this).children(".nav_zc_main").css({"display":"block"});
//		   $(".nav_zc_main > .zhexian").css({"top":"1px"});
//		}else if(nav_title_name == "客户专区"){
//		   $(this).children(".nav_zc_main").css({"display":"block"});
//		   $(".nav_zc_main > .zhexian").css({"top":"51px"});
//	    }
//	    else 
	    if(nav_title_name == "车型分类"){
		  $(this).children(".nav_pro_main").css({"display":"block"});
		  $(".nav_pro_main > .zhexian").css({"top":"1px"});
		}else if(nav_title_name == "大家电"){
		  $(this).children(".nav_pro_main").css({"display":"block"});
		  $(".nav_pro_main > .zhexian").css({"top":"51px"});
		}else if(nav_title_name == "小家电"){
		  $(this).children(".nav_pro_main").css({"display":"block"});
		  $(".nav_pro_main > .zhexian").css({"top":"101px"});
		}else if(nav_title_name == "个人电器"){
		  $(this).children(".nav_pro_main").css({"display":"block"});
		  $(".nav_pro_main > .zhexian").css({"top":"151px"});
		}else if(nav_title_name == "厨卫电器"){
		  $(this).children(".nav_pro_main").css({"display":"block"});
		  $(".nav_pro_main > .zhexian").css({"top":"201px"});
		}else if(nav_title_name == "离合变速"){
		  $(this).children(".nav_pro_main").css({"display":"block"});
		  $(".nav_pro_main > .zhexian").css({"top":"251px"});
		}else if(nav_title_name == "车身部件"){
		  $(this).children(".nav_pro_main").css({"display":"block"});
		  $(".nav_pro_main > .zhexian").css({"top":"301px"});
		}else if(nav_title_name == "美容装饰"){
		  $(this).children(".nav_pro_main").css({"display":"block"});
		  $(".nav_pro_main > .zhexian").css({"top":"351px"});
		}else if(nav_title_name == "快速采购"){
		   $(this).children(".nav_pro_main").css({"display":"block"});
		   $(".nav_pro_main > .zhexian").css({"top":"401px"});
		}
	  
      /************七大产品分类子类显示************/
	  
	  $(".nav_pro_main > .pro_list > .title > label").mouseover(function(){
		 var label_text = $(this).text();
		 $(this).siblings("label").removeClass("xuan");
		 $(this).addClass("xuan"); 
	  })
	  
   })
   
   $(".nav_fl_list > li").mouseleave(function(){
	  $(this).children("b").next().css({"display":"none"});
	  $(".nav_fl_list > li").removeClass("xuan");
   })
   
   $(".logo_seach > .seach > .seach_tj > li").click(function(){
		$(".logo_seach > .seach > .seach_tj > li").removeClass("xuan");
 		$("#nxsou").hide();
 		$("#nxsou").empty();
		$(this).addClass("xuan");
		var tjval = $("#header > .logo_seach > .seach > ul.seach_tj > li.xuan").text();
		if(tjval == "产品"){
		  $(this).parents(".seach_tj").next(".seach_main").children("input").attr("placeholder","输入产品名称、商品条码、店铺名称等都可以直接搜索");
		  $("#tjbh").val("cp");
		}else if(tjval == "OE码"){
		  $(this).parents(".seach_tj").next(".seach_main").children("input").attr("placeholder","请输入产品OE码");		
		  $("#tjbh").val("oe");
	    }else if(tjval == "商品条码"){
		  $(this).parents(".seach_tj").next(".seach_main").children("input").attr("placeholder","请输入13位商品条码");	
		  $("#tjbh").val("tm");
	    }
	})
   
})