$(document).ready(function(){
	$(".search > .tj > a").click(function(){
		$(".search > .tj > a").removeClass("xuan");
 		$("#nxsou").hide();
 		$("#nxsou").empty();
		$(this).addClass("xuan");
		var tjval = $("#head > .search > .tj > .xuan").text();
		var inputval = $(this).parents(".tj").next(".nr").children("input").attr("placeholder");
		if(tjval == "产品"){
		  $(this).parents(".tj").next(".nr").children("input").attr("placeholder","输入产品名称 / 商品条码都可以直接搜索");
		  $("#tjbh").val("cp");
		  //$(this).parents(".tj").next(".nr").children(".search_btn").attr("id","dp");
		}else if(tjval == "服务"){
		  $(this).parents(".tj").next(".nr").children("input").attr("placeholder","需要的服务类型、名称");		
	    }else if(tjval == "店铺"){
		  $(this).parents(".tj").next(".nr").children("input").attr("placeholder","店铺名称、编码");		
		  $("#tjbh").val("dp");
	    }else if(tjval == "商品条码"){
		  $(this).parents(".tj").next(".nr").children("input").attr("placeholder","请输入13位商品条码");	
		  $("#tjbh").val("tm");
	    }else if(tjval == "企业标识码"){
		  $(this).parents(".tj").next(".nr").children("input").attr("placeholder","请输入企业标识码");	
		  $("#tjbh").val("bsm");
		}else if(tjval == "企业位置码"){
		  $(this).parents(".tj").next(".nr").children("input").attr("placeholder","请输入企业位置码");	
		  $("#tjbh").val("wzm");
		}else if(tjval == "OE码"){
			  $(this).parents(".tj").next(".nr").children("input").attr("placeholder","请输入产品OE码");
			  $("#tjbh").val("oe");
		    }
	});

	
/*************************新浪地区**************
var myprovince = remote_ip_info['province']; 
var mycity = remote_ip_info['city'] 
var mydistrict = remote_ip_info['district']; 
$(".topleft > .cs").text(mycity);********/


/*************************搜索栏**********************/
$(window).scroll(function(){
if($(window).scrollTop()>30){
   $("#toper").addClass("scrolltop");
   $(".xxnav").css({"top":"164px"});
  }
if($(window).scrollTop()>60){
	$(".scrollsearch").css({"display":"block"});	
	$(".scrollsearch").animate({"top":"30px"});	  
  }
if($(window).scrollTop()<60){
	$(".scrollsearch").css({"display":"none"});		  
  }
if($(window).scrollTop()<30){
   $("#toper").removeClass("scrolltop");
   $(".xxnav").css({"top":"194px"});
  }
});

   
   $(".scrollsearch-main > .search > .tj").mouseover(function(){
      $(".search > .tj > b:last").css({"border-bottom":"1px solid #e4e4e4"});  
	  $(this).children("b").css({"display":"block"});  
	})
   $(".scrollsearch-main > .search > .tj > b").click(function(){
	   var tj = $(this).text();
	   var tjxuan = $(".scrollsearch-main > .search > .tj > .xuan").text();
	   $(".scrollsearch-main > .search > .tj > .xuan").empty();
	   $(".scrollsearch-main > .search > .tj > .xuan").text(tj);
	   $(this).empty();
	   $(this).text(tjxuan);
		if(tj == "产品"){
		  $(this).parents(".tj").next("input").attr("placeholder","输入产品名称、商品条码、店铺名称等都可以直接搜索");	
		  $("#head > .search > .tj > a").removeClass("xuan");
		  $("#header > .search > .tj > a#tj001").addClass("xuan");
		}else if(tj == "服务"){
		  $(this).parents(".tj").next("input").attr("placeholder","需要的服务类型、名称");	
		  $("#head > .search > .tj > a").removeClass("xuan");
		  $("#head > .search > .tj > a#tj02").addClass("xuan");
          
		}else if(tj == "服务"){
		  $(this).parents(".tj").next("input").attr("placeholder","店铺名称、编码");	
		  $("#head > .search > .tj > a").removeClass("xuan");
		  $("#head > .search > .tj > a#tj03").addClass("xuan");
		}else if(tj == "条码"){
		  $(this).parents(".tj").next("input").attr("placeholder","条码");
		  $("#head > .search > .tj > a").removeClass("xuan");
		  $("#head > .search > .tj > a#tj04").addClass("xuan");	
		}else if(tj == "位置码"){
		  $(this).parents(".tj").next("input").attr("placeholder","位置码");	
		  $("#head > .search > .tj > a").removeClass("xuan");
		  $("#head > .search > .tj > a#tj05").addClass("xuan");
		}
	 })
   $(".scrollsearch-main > .search > .tj").mouseleave(function(){
      $(".search > .tj > b:last").css({"border-bottom":"1px solid #e4e4e4"});  
	  $(this).children("b").css({"display":"none"});  
	  $(this).children(".xuan").css({"display":"block"});  
	})
   
	$(".nav-top > a.more").mouseover(function(){
	  $(this).next(".morenav").css({"display":"block"});  
	})
    $(".nav-top").mouseleave(function(){
	  $(this).children(".morenav").css({"display":"none"});  
	})
})