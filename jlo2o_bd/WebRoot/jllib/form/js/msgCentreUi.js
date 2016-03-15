$(document).ready(function(){

	$(".contontList > li").mouseover(function(){
		var txt_title_text = $(this).attr("id");
	   $(".contontList > li").removeClass("xuan");
	   $(this).addClass("xuan");
	   if(txt_title_text == "txt_title_01"){
		   $(".txt_main").css({"display":"none"});
		   $("#txt_main_01").css({"display":"block"});
	    }else if(txt_title_text == "txt_title_02"){
		   $(".txt_main").css({"display":"none"});
		   $("#txt_main_02").css({"display":"block"});
	    }else if(txt_title_text == "txt_title_03"){
		   $(".txt_main").css({"display":"none"});
		   $("#txt_main_03").css({"display":"block"});
	    }
	})
	
	$(".more").mouseover(function(){
		$(".more_doList").css({"display":"block"})
    })
	$(".more_doList").mouseleave(function(){
		$(".more_doList").css({"display":"none"})
    })




/************************无操作列表全屏模式开始***************************/
   $(".tableList_fy > .tubiao").click(function(){
	   var rubiao_text = $(this).text();
	   if(rubiao_text == "全屏"){
		   $(this).parent(".tableList_fy").parent("div").addClass("tableList_qp");
		   $(".tubiao").text("退出全屏")
		   $(".tubiao").css({"background":"url(images/tableList_fy_ico02.png) no-repeat #ff8800"})
		 }else if(rubiao_text == "退出全屏"){
		   $(this).parent(".tableList_fy").parent("div").removeClass("tableList_qp");
		   $(".tubiao").text("全屏")
		   $(".tubiao").css({"background":"url(images/tableList_fy_ico.png) no-repeat #ff8800"})
		 }
	  })
    $(window).scroll(function(){
	  var txt_main_class = $("#txt_main_03").attr("class");
	  var scrollTop = $(document).scrollTop();
	  if(txt_main_class == "txt_main" && scrollTop > 228){
		  $("thead").addClass("xuanfu")
		}else if(txt_main_class == "txt_main" && scrollTop < 228){
		  $("thead").removeClass("xuanfu")
		}
      })
/************************无操作列表全屏模式结束***************************/

/************************无操作列表排序开始***************************/
    var asc = function(a, b){return $(a).find(".td_01").text() > $(b).find(".td_01").text() ? 1 : -1;}
    var desc = function(a, b){return $(a).find(".td_01").text() > $(b).find(".td_01").text() ? -1 : 1;}
    var sortByInput = function(sortBy) {
        var sortEle = $("tbody > tr").sort(sortBy);
        $("tbody").empty().append(sortEle);
    }
     $("thead > tr > .no01 > a").click(function(){
		var thead_a_text = $(this).text();
		$("thead > tr > .no01 > a").removeClass("xuan");
		$(this).addClass("xuan");
		if(thead_a_text == "升"){
            sortByInput(asc); 
		}else if(thead_a_text == "降"){
		    sortByInput(desc); 
	    }
	 })
/************************无操作列表排序结束***************************/


/************************点击切换***************************/
 $(".grid_main > label").click(function(){
    $(this).parent().children("label").removeClass("xuan");
    $(this).addClass("xuan");  
  })
})

//弹出层的收起展开
$(".BM > li").click(function(){
	alert(123)
       var label_zt = $(this).children("span").text();
	   if(label_zt == "-"){
		   $(this).children("span").text("+")
		   $(this).next().slideUp(300);
		}else{
		   $(this).children("span").text("-")
		   $(this).next().slideDown(300);
	    } 
	})