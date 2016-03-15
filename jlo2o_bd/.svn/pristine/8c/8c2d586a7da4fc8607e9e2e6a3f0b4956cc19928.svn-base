$(document).ready(function(){
   $(".yjfl").click(function(){
	 $(this).siblings(".yjfl").removeClass("xuanyifl");
	 $(this).addClass("xuanyifl"); 
	 var yjfl_text = $(this).children("span").text();
	 })
	/*****************适用车系JQ******************/
   $(".cx").change(function(){
	   var cs_text = $(this).find("option:selected").text();
	   if(cs_text == "特定车型备件"){
		   $(".sycx").css({"display":"block"});
		}else{
	       $(".sycx").css({"display":"none"});
	    }
	});
   $("font").click(function(){
	   var font_text = $(this).text();
	   if(font_text == "+"){
		  $(this).text("-");
		  $(this).siblings("ul").css({"display":"block"});  
		}else{
		  $(this).text("+");
		  $(this).siblings("ul").css({"display":"none"});  	
	    }
	});
	$(".checkbox").change(function(){
		var this_checkbox_zt = $(this).attr("checked");
		if(this_checkbox_zt == "checked"){
		      $(this).parent("li").children("ul").find(".checkbox").attr("checked","checked");
	    }else{
		  $(this).parent("li").children("ul").find(".checkbox").removeAttr("checked");	
	    }
    });
	
   /*****************商品规格JQ******************/
   $(".ge").change(function(){
	   var ge_text = $(this).find("option:selected").text();
	   if(ge_text == "请选择"){
		   $(".spge").empty();	
		}else if(ge_text == "容量"){
		   $(".spge").empty();
		   $(this).siblings(".spge").append('<input class="spgg-rl" /><label>ml</label>');
		}else if(ge_text == "尺寸"){
		   $(".spge").empty();
		   $(this).siblings(".spge").append('<input class="spgg-rl" placeholder="长" /><label>cm</label><input class="spgg-rl" placeholder="宽" /><label>cm</label><input class="spgg-rl" placeholder="高" /><label>cm</label>');
	    }else if(ge_text == "重量"){
		   $(".spge").empty();
		   $(this).siblings(".spge").append('<input class="spgg-rl" /><label>kg</label>');	
		}
	});
   /*****************商品名称字数JQ******************/
   $(".spmc").keyup(function(){
	  var spmc_length = $(this).val().length;
	  var font_length = 60-spmc_length;
	  var font_chaochu_length = spmc_length-60;
	  if(font_length < 0){
         $(".spmc").next(".tishi").empty();
		 $(this).next().append('已超出<font style="color:#e40000;">'+font_chaochu_length+'</font>个字')
	  }else{
         $(".spmc").next(".tishi").empty();
		 $(this).next().append('还能输入<font>'+font_length+'</font>个字')	
		  
	  }
	})
	
})
