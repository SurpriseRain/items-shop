	$(document).ready(function(){
	var sheng_text = $("#sheng").find("option:selected").text();
	var shi_text = $("#shi").find("option:selected").text();
	var qu_text = $("#qu").find("option:selected").text();
	$("#sheng").attr("title",$("#sheng").find("option:selected").text())
	$("#shi").attr("title",$("#shi").find("option:selected").text())
	$("#qu").attr("title",$("#qu").find("option:selected").text())
	
  $(".caozuo").click(function(){
	var a_text = $(this).text();
	if(a_text == "修改"){
		var temp=$(this).parent().attr("class");
		if(temp=="w10 add2"){
			$(this).parent().attr("style","display: none");
			$(".add1").attr("style","display: table-cell block");
		}
		else if(temp=="xstp"){
			$(this).parent().attr("style","display: none");
			$(this).parent("td").next().attr("style","display: block");
		}
		else{
		$(this).siblings("input").removeAttr("readonly");
		$(this).siblings("select").removeAttr("disabled");
		$(this).siblings("input").focus();
		$(this).text("取消");
		}}
	else if(a_text == "取消"){
		var temp=$(this).parent().attr("class");
		if(temp=="w10 add1"){
			$(this).parent().attr("style","display: none");
			$(".add2").attr("style","display table-cell block");
		}
		else if(temp=="sctp"){
			$(this).parent().attr("style","display: none");
			$(this).siblings("input").val("");
			$(this).parent("td").prev().attr("style","display: block");
		}
		else{
		var input_val = $(this).siblings("input").attr("title");
		$(this).siblings("input").attr("readonly","readonly");
		$(this).siblings("select").attr("disabled","disabled");
		$(this).siblings("input").val(input_val);	
		$(this).siblings("#sheng").find("option:selected").text(sheng_text);
		$(this).siblings("#shi").find("option:selected").text(shi_text);
		$(this).siblings("#qu").find("option:selected").text(qu_text);
		$(this).text("修改");
		}}
  })	
	
})
