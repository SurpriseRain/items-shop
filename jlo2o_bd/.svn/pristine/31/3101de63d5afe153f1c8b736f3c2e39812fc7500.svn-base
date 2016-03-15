$(document).ready(function(){
	/************首页头部搜索框的宽度************/
	var index_width = $(window).width()-134;
	var pro_main = $(window).width()-15;
	var pro_main_li = (pro_main-15)/3;
	var list_seach = $(window).width()-52;
	var pro_rk_list = $(window).width()-72;
	var pro_name = $(window).width()-122;
	$("#header > input").css({"width":index_width});
	$("#pro_main").css({"width":pro_main});
	$("#pro_main > li").css({"width":pro_main_li});
	$(".list_seach").css({"width":list_seach});
	$("#pro_rk_list > li > a > .text").css({"width":pro_rk_list});
	$("#pro_rk_list > li > a > .text > .pro_name").css({"width":pro_name});
	 
	/************密码显示/影藏字符************/

	$(".login_name_pwd > label").click(function(){
		var label_class = $(this).attr("class");
		if(label_class == "eyes_close"){
			$(this).attr("class","eyes_open");
			$(this).prev().attr("type","text");
		}else{
			$(this).attr("class","eyes_close")
			$(this).prev().attr("type","password");
		}
	})
	
	$("#pro_main > li > a").mouseover(function(){
	   $(this).animate({"width":"94%","height":"124px","margin":"3px 3%"},100)	
	})
	$("#pro_main > li > a").mouseleave(function(){
	   $(this).animate({"width":"100%","height":"130px","margin":"0"},100)	
	}) 
 
})