$(document).ready(function(){
var nav=0;
$(".fastop").load("/customer/sydjt/返回顶部.html");
$(".toper").load("/customer/sydjt/toper.html");
$(".header").load("/customer/sydjt/header.html");
$(".nav").load("/customer/sydjt/nav.html");
$(".banner").load("/customer/sydjt/banner.html");
$(".floor").load("/customer/sydjt/floor.html");
$(".floor_nav").load("/customer/sydjt/floor_nav.html");
//$(".breadcrumb").load("/customer/sydjt/showProduct/breadcrumb.html");
//$(".searchwrap").load("/customer/sydjt/showProduct/searchwrap.html");
//$(".pro_info").load("/customer/sydjt/pbuying.html");
$(".sold").load("/customer/sydjt/sold.html");
$(".footer").load("/customer/sydjt/footer.html");
$(".login").load("/customer/sydjt/register/login_main.html",function(){
	/*var name = PageUrl.split("/");
	name = name[name.length-1];
	name = name.split(".html")[0];
	$(this).attr("page",name);*/
	var jlform = eval("login");
	jlform.setTab(this); 
	var json = {};
	json["initfield"] = {};
	json["save"] = false;
	jlform.setData({});
	jlform.initForm(json);
});
$(".cart_main").load("/customer/sydjt/shopping/cart_main.html");
//$(".service").load("/customer/sydjt/service.html");
$(".registered").load("/customer/sydjt/register/reg_main.html");
//商品页
$(".pro_card").load("/customer/sydjt/showProduct/product_cart.html");
$(".pro_info").load("/customer/sydjt/showProduct/product_info.html");

$(".settlement_header").load("/customer/sydjt/shopping/cart_02_header.html");
$(".settlement_main").load("/customer/sydjt/shopping/cart_02_main.html");


$(".pay_header").load("/customer/sydjt/shopping/pay_header.html");
$(".pay_main").load("/customer/sydjt/shopping/pay_main.html");
$(".pay_main_cg").load("/customer/sydjt/shopping/pay_success.html");

$(".wjmm").load("forgetPwd_main.html");
});


