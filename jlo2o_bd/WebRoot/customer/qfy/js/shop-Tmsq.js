$(document).ready(function(){
   $(".dqsp-title > tr > td > a").click(function(){
	   var a_text = $(this).text();
	   var bmzc_text = $(this).parents("td").siblings().children("input.bmzc").val();
	   var bmsl_text = $(this).parents("td").siblings().children("input.bmsl").val();
	   var bmhd_text = $(this).parents("td").siblings().children("input.bmhd").val();
	   var spmc_text = $(this).parents("td").siblings().children("b").text();
	   if(a_text == "确定" || a_text == "编辑"){
		   $("#tmsqzz").css({"display":"block"});
		   $(".tmsqzz-title > .bmzc").val(bmzc_text);
		   $(".tmsqzz-title > .bmsl").val(bmsl_text);
		   $(".tmsqzz-title > .bmhd").val(bmhd_text);
		   $(".tmsqzz-title > b").val(spmc_text);
		}
	   
	})

	$(".tmsqzz-title > font").click(function(){
		$("#tmsqzz").css({"display":"none"});
    })
	$(".tmsqzz-a > .bot > a").click(function(){
		var a_text = $(this).text();
		if(a_text == "取消"){
	       $("#tmsqzz").css({"display":"none"});
		}
    })
	
	
   $(".dqsp-title > tr").mouseover(function(){
		  $(this).addClass("bian");     
	})
   $(".dqsp-title > tr").mouseleave(function(){
		  $(this).removeClass("bian");     
	})	
	
	$(".dqtb > .qx > .checkbox").click(function(){
		var dqtb_checkbox = $(this).attr("checked");
		if(dqtb_checkbox == "checked"){
		  $(".dqsp-title > tr > td.no5 > input").attr("checked","checked");
		  $(".dqsp-title > tr").addClass("xuan");
	    }else{
		  $(".dqsp-title > tr > td.no5 > input").removeAttr("checked");
		  $(".dqsp-title > tr").removeClass("xuan");
		}
    })
	
   $(".dqsp-title > tr > td.no5 > input").click(function(){
	   var no5_checked = $(".dqsp-title > tr > td.no5 > input").attr("checked");
	   if(no5_checked == "checked"){
		  $(this).parent("td").parent("tr").addClass("xuan");   
		}else{
		  $(this).parent("td").parent("tr").removeClass("xuan");
		}   
	})
	
   $(".tmsqzz-list > li").mouseover(function(){
		  $(this).addClass("bian");     
	})
   $(".tmsqzz-list > li").mouseleave(function(){
		  $(this).removeClass("bian");     
	})
	
	$(".tmsqzz-a > .qx > .checkbox").click(function(){
		var tmsqzz_checkbox = $(this).attr("checked");
		if(tmsqzz_checkbox == "checked"){
		  $(".tmsqzz-list > li > .checkbox").attr("checked","checked");
		  $(".tmsqzz-list > li").addClass("xuan");
	    }else{
		  $(".tmsqzz-list > li > .checkbox").removeAttr("checked");
		  $(".tmsqzz-list > li").removeClass("xuan");
		}
    })
	
   $(".tmsqzz-list > li > .checkbox").click(function(){
	   var li_checkbox = $(this).attr("checked");
	   if(li_checkbox == "checked"){
		  $(this).parent("li").addClass("xuan");   
		}else{
		  $(this).parent("li").removeClass("xuan");
		}   
	})	
	
})
