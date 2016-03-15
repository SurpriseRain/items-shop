<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <% 
  	String spflShow = request.getParameter("spflShow");
  	String logoName = "logo.png";
  	if(request.getParameter("logoName") != null){
  		logoName = request.getParameter("logoName");
  	}
  %>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  	<link rel="stylesheet" type="text/css" href="/customer/qfy/css/head.css" />
	<script type="text/javascript" src="/customer/qfy/js/public.js"></script>
	<script type="text/javascript" src="/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="/js/commons.js"></script>
  	</head>
<script type="text/javascript">
  var MyCookie = $.cookie('userInfo');
  var usercookie = JSON.parse(MyCookie);
  var city_list = $.cookie("city_list");
 //cookie误删时，存放默认值
  if(city_list==null){
	    var city_list = [];
		var o = {};
		o.city_value = "120000";
		o.city_name = "天津";
		o.city_value_2 = "120100";
		o.city_name_2 = "天津市";
		city_list.push(o);
		$.cookie("city_list", JSON.stringify(city_list), {
			expires : 365, path : '/'
		});
		city_list = $.cookie("city_list");
  }
  city_list = JSON.parse(city_list);
  
  $("#city_value").val(city_list[0].city_value);
  $("#city_name").html(city_list[0].city_name); 
  //大区选择
  $(".selectSea > span").focus(function(){
  	  $(this).next().css({"display":"block"});
  });
  
  $(".selectSea").mouseleave(function(){
  	  $(this).children("ul").css({"display":"none"})
  });

  $(".selectSea").mouseover(function(){
  	  $(".city_list").css({"display":"block"});
  });
  function select_dqmc(){
	 var XmlData=[{'sqlid':'com.jlsoft.o2o.user.sql.select_dqmc','dataType':'Json'}];
	 var url="/jlquery/selecto2o.action?XmlData="+JSON.stringify(XmlData);
	 ajaxQ({
			"url" : url,//原始url 
			"callback" : function(rData){
				var str="";
				for(var j=0;j<rData.length;j++) {
					var obj="";
					if(rData[j].dqbzm02=="黑龙江省"){
						obj =rData[j].dqbzm02.substring(0,3);
					}else{
						obj =rData[j].dqbzm02.substring(0,2);
					}
					
	    			str+="<li id='"+rData[j].dqbzm01+"'>"+obj+"</li>";
	    		}
				$(".city_list").html(str);
				$(".selectSea > ul > li").click(function(){
				  	var selectSea_text = $(this).text();
				  	var b = $(this).attr("id");
				  	$(this).parent("ul").prev().prev().prev().html(selectSea_text);
				  	$(this).parent("ul").prev().val(b);
				  	$(this).parent("ul").css({"display":"none"});
				  	//将已选的省份值存放于cookie
				  	var XmlData=[{'sqlid':'com.jlsoft.o2o.user.sql.select_dqmc_2','dataType':'Json','dqbzm01':b}];
					var url="/jlquery/selecto2o.action?XmlData="+JSON.stringify(XmlData);
					var rData=visitService(url);
					var city_list = [];
					var o = {};
					o.city_value = b;
					o.city_name = selectSea_text;
					o.city_value_2 = rData[0].dqbzm01;
					o.city_name_2 = rData[0].dqbzm02;
					city_list.push(o);
					$.cookie("city_list", JSON.stringify(city_list), {
						expires : 365, path : '/'
					});
				  });
			}
		});
	 
	 }
  var baseUrl="";
	$(document).ready(function(){
		$("#grzx").on("click",function(){
			if(usercookie==null){
				alert("请登录查看");
				location.href="/customer/qfy/register/login.html";
			}
	 	});
		
		selectSrch(usercookie);
		showUser();
		//首页展示商品分类
		if("<%=spflShow%>"=="true"){
			$(".nav_fl_list").show();
			select_dqmc();
			$(".selectSea").show();
		}else{
			$(".nav_fl_list").hide();
			$(".selectSea").hide();
			$(".nav_info").mouseover(function(){
				$(".nav_fl_list").show();
			})
			$(".nav_info").siblings().mouseleave(function(){
				$(".nav_fl_list").hide();
			})
		}
		//showFLPP();
		//showKSCG();
		$(".nav_fl_list").on("mouseenter","li",function(e){
			 var $this = $(e.currentTarget),
            	 index = +($this.data("index"));
            	 
			// 加载次分类数据
	        if(index === 1){
	        	showFLPP();
	        	showFLPP = function(){};
	        }else if(index !== 9){
	        	beiju();
	        	beiju = function(){};
	        }else if(index === 9){
	        	showKSCG();
	        	showKSCG = function(){};
	        }
		});
		

		$("#seach_tj > li").click(function(){
			$("#seach_tj > li").removeClass("xuan");
			$(this).addClass("xuan");
			var tjval = $("#seach_tj > .xuan").text();
			var inputval = $(this).parents(".seach_tj").next(".seach_main").children("input").attr("placeholder");
			if(tjval == "产品"){
			  $(this).parents(".seach_tj").next(".seach_main").children("input").attr("placeholder","输入产品名称、商品条码、店铺名称等都可以直接搜索");
			  $("#tjbh").val("cp");
			}else if(tjval == "服务"){
			  $(this).parents(".seach_tj").next(".seach_main").children("input").attr("placeholder","需要的服务类型、名称");		
		    }else if(tjval == "店铺"){
			  $(this).parents(".seach_tj").next(".seach_main").children("input").attr("placeholder","店铺名称、编码");		
			  $("#tjbh").val("dp");
		    }else if(tjval == "商品条码"){
			  $(this).parents(".seach_tj").next(".seach_main").children("input").attr("placeholder","请输入商品条码");	
			  $("#tjbh").val("tm");
		    }else if(tjval == "企业标识码"){
			  $(this).parents(".seach_tj").next(".seach_main").children("input").attr("placeholder","请输入企业标识码");	
			  $("#tjbh").val("bsm");
			}else if(tjval == "企业位置码"){
			  $(this).parents(".seach_tj").next(".seach_main").children("input").attr("placeholder","请输入企业位置码");	
			  $("#tjbh").val("wzm");
			}else if(tjval == "OEM"){
				  $(this).parents(".seach_tj").next(".seach_main").children("input").attr("placeholder","请输入OEM码");	
				  $("#tjbh").val("oe");
				}
		});
		var oe=$.getUrlParam("spglljh01");
		if(oe!=""&&oe!=null){
		    	$("#seach_tj2").click();
		    	$("#sousuo").val(oe);
		}
	})
	
//检查是否已登录
function showUser(){
	if(usercookie!=null){
		selectMyCart(usercookie);
		//清除cookie
		$("#ext").attr("style","display:black").attr("href","javascript:void(0)").on("click",function(){
			$.cookie('userInfo',null,{path:"/"});  
			window.location="/customer/qfy/index.html";
		});
		/*后台管理停用
		if(usercookie.LX!=21 && usercookie.XTCZY01=="jladmin"){
			$("#houtaigl").append('<a class="left no4" href="/gui_o2o/back/Main.jsp">后台管理</a>');			
		} 
		*/
		$("#regist").remove();
		$("#loginP").html(usercookie.ZCXX02).css("width","100px");
		$("#loginP").attr('href','/customer/qfy/showDpxx/shopAllGoods.html?gsid='+usercookie.ZCXX01+'&gsmc='+escape(usercookie.ZCXX02));
		$("ul.login").hide();
		$("div.loginh").show();
		if(usercookie.ZCGS01==4){
		$("#grzx").attr('href','/customer/qfy/back/shop-main.html');
		$("#fbsp").attr('href','/customer/qfy/back/shop-main.html?menuId=A001');
			}else if(usercookie.ZCGS01==2){
				$("#grzx").attr('href','/customer/qfy/register/registerPerfectDB.html');
			}
		//登陆后显示信息
		//selectDPXXLogin();
		
	}else{			
		$("#head-cat").find("div").remove();
		$("ul.login").show();
		$("div.loginh").hide();
	}
}
	
//搜索功能
function selectSrch(usercookie){
 	$(".seach_bot").click(function(){
 		alert($("#tjbh").val());
 	 	//产品搜索
	 	if($("#tjbh").val()=="cp") {
		 	if($("#sousuo").val()==null || $("#sousuo").val()=='') {
				alert("请输入查询内容");
				return;
			}
			var cpmc = ($("#sousuo").val()).trim();
			//location.href="/customer/qfy/showProduct/member_shop.html?SPXX04="+escape(cpmc);
			location.href="/customer/qfy/showProduct/member_shop_n.html?SPXX04="+escape(cpmc);
		}else if($("#tjbh").val()=="oe"){
		 	if($("#sousuo").val()==null || $("#sousuo").val()=='') {
				alert("请输入查询内容");
				return;
			}
			var oe = ($("#sousuo").val()).trim();
			var url="/QFY/selectSPXXbyOE.action?oe="+oe;
			var data=visitService(url);
			if(data.STATE == 1){
				data=data.data;
				location.href="/customer/qfy/showProduct/product.html?spxx01="+data.SPXX01+"&zcxx01="+data.ZCXX01+"&gsid="+data.ZCXX01+"&gsmc="+escape(data.ZCXX02);
			}else if(data.STATE == 2){
				var oe = ($("#sousuo").val()).trim();
				location.href="/customer/qfy/showProduct/member_shop_n.html?spglljh01="+oe;
			}else{
				alert("该OEM商品暂无！");
			}
		} 
		else if($("#tjbh").val()=="dp") {
		 	if($("#sousuo").val()==null || $("#sousuo").val()=='') {
				alert("请输入查询内容");
				return;
			}
			var dpmc = ($("#sousuo").val()).trim();
			location.href="/customer/qfy/showProduct/member_shop_n.html?SPXX04="+escape(dpmc);
		} else if($("#tjbh").val()=="tm") {
				//搜索条码
				/****
				$("#nxsou").find("li").remove();
				var tm = ($("#sousuo").val()).trim();
				var XmlData = [{"BARCODE":tm}];
		 		var srchUrl=baseUrl+"/MyGoods/SrchGoods.action?XmlData="+JSON.stringify(XmlData);
		 		var rData=visitService(srchUrl);
		 		if(rData.STATE=="1"){
		 			if(rData.goodsMap.JL_State!="接收失败" && rData.ino2o==0){
		 				var goodsMap=rData.goodsMap;
		 				var itemName = goodsMap.itemName;
		 				var itemCode = tm;
						location.href="/customer/qfy/showProduct/member_shop.html?itemCode="+itemCode+"&itemName="+escape(itemName);
		 			} else if(rData.goodsMap==null && rData.ino2o==1) {
		 				location.href="/customer/qfy/showProduct/product.html?spxx01="+rData.spxx.SPXX01+"&zcxx01="+rData.spxx.ZCXX01+"&gsmc="+escape(rData.spxx.ZCXX02);
		 			}else{
		 				alert("查询不到符合条件的商品!");
		 				return;
		 			}
		 		}else{
		 			$("#nxsou").removeClass("lx");
	 				alert("查询异常!");
		 			return;
		 		}
		 	***/	
		 	if($("#sousuo").val()==null || $("#sousuo").val()=='') {
				alert("请输入查询内容");
				return;
			}
			var sptm = ($("#sousuo").val()).trim();
			var url="/QFY/selectSPXXbySPTM.action?sptm="+sptm;
			var data=visitService(url);
			if(data.STATE == 1){
				data=data.data;
				location.href="/customer/qfy/showProduct/product.html?spxx01="+data.SPXX01+"&zcxx01="+data.ZCXX01+"&gsid="+data.ZCXX01+"&gsmc="+escape(data.ZCXX02);
			}else if(data.STATE == 2){
				var oe = ($("#sousuo").val()).trim();
				location.href="/customer/qfy/showProduct/member_shop_n.html?barcode="+sptm;
			}else{
				alert("该条码商品暂无商品！");
			}
		} else if ($("#tjbh").val()=="bsm") {
	 		//搜索企业识别码
	 		$("#nxsou").find("li").remove();
			var bsm = ($("#sousuo").val()).trim();
			var XmlData = [{"BARCODE":bsm}];
	 		var srchUrl=baseUrl+"/QFYCODE/selectQFYCODE.action?XmlData="+JSON.stringify(XmlData);
	 		var returnVal=visitService(srchUrl);
	 		
	 		if(returnVal.list.length>0 ) {
	 			var returnVallist = returnVal.list[0];
	 		}
	 		
		}
 	});
	$("#sousuo").on("click",function(){
 	});
 	/*
 	$("#head").on("mouseleave",function(){
 		$("#nxsou").hide();
 		$("#nxsou").empty();
 	});
 	$("#sousuo").on("focus",function(){
 		selectFeeling();
 	});
 	$("#sousuo").on("keyup",function(){
 		selectFeeling();
 	});
 	*/
}

//加载车型分类数据到页面
function showFLPP(){
	var functionName = "loadFLPP";
	var url = baseUrl+"/showGoods/cxcxlist.action";
	visitService1(url,functionName);
}

//加载车型分类数据到页面
function loadFLPP() {
	var FLPP = returnData.cxcxlist;
	$(".cxfl").next().find("li").find("span").find("label").empty();
	$(FLPP).each(function(index,FL01){
		var sum = FL01.ORIGIN_COUNTRY_ID;
		$(".cxfl").next().find(".pro_list").append("<div id='pro_main_cxfl_"+sum+"'><li><dl><dd></dd></dl></li></div>");
		if(sum=='0'){
			$(".cxfl").next().find("li").first().append("<label id='pro_title_cxfl_"+sum+"'>"+FL01.ORIGIN_COUNTRY+"</label>");
			$(".cxfl").next().find("li").find("label").first().addClass("xuan");
		}
		else{
			$(".cxfl").next().find("li").first().append("<label id='pro_title_cxfl_"+sum+"'>"+FL01.ORIGIN_COUNTRY+"</label>");
		}
		$(".cxfl").next().find(".pro_list").find("div").eq(index).find("li").first().find("dl").find("dd").css("width","670px");
		$(FL01.PPFL02list).each(function(j,FL02){
			$(".cxfl").next().find(".pro_list").find("div").eq(index).find("li").first().find("dl").find("dd")
				.append("<em style='text-align:center;'><a class='pro_logo' href='/customer/qfy/showProduct/carSs.html?car_id="+FL02.brand_id+"&&ORIGIN_COUNTRY_ID="+sum+"'><img src='/customer/qfy/images/mycar/"+FL02.BRANDPY+"_"+FL02.brand_id+".png' /><span>"+FL02.BRAND+"</span></a></em>");
			$(".cxfl").next().find(".pro_list").find("div").first().show();
		});

		$(".pro_list > .title > label").mouseover(function(){
			  var label_id = $(this).attr("id");
			  var aaid = label_id.substring(10,17);
			  aaid = "pro_main_" + aaid;
			  $(this).parent("li").siblings("div").css({"display":"none"});
			  $("#"+aaid).css({"display":"block"}); 
		});
	});
}

//展示首页左侧养护快修--美容装饰数据
function beiju() {
	var functionName = "loadbeiju";
	var url=baseUrl+"/showGoods/selectFL.action";
	visitService1(url,functionName);
}

function loadbeiju(){
	var fkqgSps = returnData.fl;
	$("#sppllength").attr("value",fkqgSps.length);
	//$("#pjfl1").find(".sxtj-main").find("ul").empty();
	$(fkqgSps).each(function(i,obj){
			var a1 = 0;
			var a2 = 0;
			var a3 = 0;
			var a4 = 0;
			var a5 = 0;
			var a6 = 0;
			var a7 = 0;
			$(obj.PPFL02list).each(function(j,obj2){
				var sum = 0;
				
				if(obj.FLCODE == '01') {
					a1++;
					if(a1<10) {
						sum = "0" + a1;
					} else {
						sum = a1;
					}
					var dhid = "yhkx";
				} else if(obj.FLCODE == '02') {
					a2++;
					if(a2<10) {
						sum = "0" + a2;
					} else {
						sum = a2;
					}
					var dhid = "dlxt";
				} else if(obj.FLCODE == '03') {
					a3++;
					if(a3<10) {
						sum = "0" + a3;
					} else {
						sum = a3;
					}
					var dhid = "dpxg";
				} else if(obj.FLCODE == '04') {
					a4++;
					if(a4<10) {
						sum = "0" + a4;
					} else {
						sum = a4;
					}
					var dhid = "qcdq";
				} else if(obj.FLCODE == '05') {
					a5++;
					if(a5<10) {
						sum = "0" + a5;
					} else {
						sum = a5;
					}
					var dhid = "lhbs";
				} else if(obj.FLCODE == '06') {
					a6++;
					if(a6<10) {
						sum = "0" + a6;
					} else {
						sum = a6;
					}
					var dhid = "csbj";
				} else if(obj.FLCODE == '07') {
					a7++;
					if(a7<10) {
						sum = "0" + a7;
					} else {
						sum = a7;
					}
					var dhid = "mrzs";
				}

				if(sum=='01'){
					$("."+dhid).next().find("li").first().append("<label id='pro_title_"+dhid+"_"+sum+"'>"+obj2.FLNAME+"</label>");
					$("."+dhid).next().find("li").find("label").first().addClass("xuan");
				}
				else{
					$("."+dhid).next().find("li").first().append("<label id='pro_title_"+dhid+"_"+sum+"'>"+obj2.FLNAME+"</label>");
				}
				$("."+dhid).next().find(".pro_list").append("<div id='pro_main_"+dhid+"_"+sum+"'><li id='pro_main_"+dhid+"_"+sum+"_li'></li></div>");
				$(obj2.PPFL03list).each(function(k,obj3){
					//显示6个三级分类
						$("#pro_main_"+dhid+"_"+sum+"_li").append("<dl><dt>"+obj3.FLNAME+"</dt><dd id='pro_main_"+dhid+"_"+sum+"_li_"+k+"'></dd></dl>");
					$(obj3.SPFL04List).each(function(m,obj4){
						//显示6个四级分类
						if(m<10){
							$("#pro_main_"+dhid+"_"+sum+"_li_"+k+"").append("<em><a href='/customer/qfy/showProduct/member_shop.html?f3_id="+obj4.FLCODE+"&f3_name="+escape(obj4.FLNAME)+"' target='_blank'>"+obj4.FLNAME+"</a></em>");
						} 
						if(m==10){
							$("#pro_main_"+dhid+"_"+sum+"_li_"+k+"").append("<em  class='pro_main_"+dhid+"_"+sum+"_li_"+k+"'><a style='color:red;'>查看更多>></a></em>");
							$(".pro_main_"+dhid+"_"+sum+"_li_"+k+"").on("click",function(){
						    	 $(this).hide();
						    	 $(".pro_main_"+dhid+"_"+sum+"_li_"+k+"_a").show();
						    	 $(".pro_main_"+dhid+"_"+sum+"_li_"+k+"_b").show();
						    });
						}
						if(m>=10){
							$("#pro_main_"+dhid+"_"+sum+"_li_"+k+"").append("<em class='pro_main_"+dhid+"_"+sum+"_li_"+k+"_a'  style='display: none;'><a href='/customer/qfy/showProduct/member_shop.html?f3_id="+obj4.FLCODE+"&f3_name="+escape(obj4.FLNAME)+"' target='_blank'>"+obj4.FLNAME+"</a></em>");
						}
						if(m>=10 && parseInt(m)==parseInt(obj3.SPFL04List.length-1)){
							$("#pro_main_"+dhid+"_"+sum+"_li_"+k+"").append("<em  class='pro_main_"+dhid+"_"+sum+"_li_"+k+"_b' style='display: none;'><a style='color:red;'> <<返回</a></em>");
							$(".pro_main_"+dhid+"_"+sum+"_li_"+k+"_b").on("click",function(){
								 $(this).hide();
						    	 $(".pro_main_"+dhid+"_"+sum+"_li_"+k+"_a").hide();
						    	 $(".pro_main_"+dhid+"_"+sum+"_li_"+k+"").show();
						    });
						}
						
					});
				});
				$("."+dhid).next().find(".pro_list").find("div").first().show();
		});
		
		$(".pro_list > .title > label").mouseover(function(){
			  var label_id = $(this).attr("id");
			  var aaid = label_id.substring(10,17);
			  aaid = "pro_main_" + aaid;
			  $(this).parent("li").siblings("div").css({"display":"none"});
			  $("#"+aaid).css({"display":"block"}); 
			  /*
			  if(label_id == "pro_title_yhkx_02"){
				 $(this).parent("li").siblings("div").css({"display":"none"});
				 $("#pro_main_yhkx_02").css({
				 	"display":"block"}); 
			  }
			  */
		})
	});
}

//页面左侧展示-快速采购
function showKSCG(){
	var url="/showGoods/sselectWXXM.action";
	var data = visitService(url).project;
	if(data!=null){
		$("#div_kscg").html("");
		for(var i=0;i<data.length;i++){
			$("#div_kscg").append("<em><a href=\"/customer/qfy/showProduct/member_shop.html?project_id="+data[i].XMBH+"\">"+data[i].XMMC+"</a>");
		}	
	}
}

//查询购物车
function selectMyCart(user){
	//salert(usercookie);
	//alert(1);
	var xmlData=[];
	var json={};
	json.HBID=usercookie.ZCXX01;
	json.DQXX01=usercookie.ZCXX07;
	xmlData.push(json);
	var url="/CartManage/selectGwcByHbid.action?XmlData="+JSON.stringify(xmlData);
	var data= visitService(url);
	//展示
	//$(".shuliang").html(data.GWCLIST[0].TOTALNUM);
	//alert(JSON.stringify(data));
	//alert(JSON.stringify(data.GWCLIST[0].TOTALNUM));
	if(""==data.GWCLIST||null==data.GWCLIST)
	{
		$(".shuliang").html(0);
	}else
	{
		$(".shuliang").html(data.GWCLIST[0].TOTALNUM);
	}
	
	$(data.GWCLIST).each(function(m,spxxList){
			/*
			
			$(".cat-zh").append("<div class='cat-products'><ul></ul></div>");
			$(spxxList.SPITEMLIST).each(function(index,cartInfo){
				$(".cat-zh").find("ul").eq(m).append("<li><div class='img'><a href='#'><img/></div><div class='text'><div class='txt-title'><a href='#'></a></div><div class='txt-main'><span></span><span></span> <a href='#'>删除</a></div></div></li>");	
				var cartGoods=$(".cat-zh").find("ul").eq(m).find("li").eq(index);
					cartGoods.find(".img a").attr("href","javascipt:void(0)").find("img").attr("src","/gui_o2o/mz/images/spdetail/"+spxxList.ZTID+"/"+cartInfo.SPXX02+"/images/"+cartInfo.SPXX02+"_1_small.jpg").attr("onerror","this.onerror=null;this.src='/gui_o2o/mz/images/spdetail/default/images/image.jpg'");
					//购物车中添加商品名称和链接
					cartGoods.find(".text div.txt-title a").attr("href","product.html?spxx01="+cartInfo.SPXX01+"&zcxx01="+spxxList.ZTID).html(cartInfo.SPXX04);
					cartGoods.find(".txt-main span").first().html(cartInfo.GWC02).next().html(" × "+cartInfo.GWC01);
					cartGoods.find(".txt-main a").attr("href","javascript:void(0)").on("click",function(){
						var returnVal=deleteSpSingle(cartInfo.SPXX01,spxxList.ZTID,user);
							if(returnVal.STATE =='1'){
									//删除节点 刷新jiem
									selectAmount(spxxList,cartInfo,this);
									if($(this).parents("ul").find("li").length<2){
										$(this).parents("ul").find("li").last().remove();
									}
									$(this).parents("li").remove();
									if(main!=undefined) {
										//alert("ok");
										a();
									}
									//statisticalData(index);
							}else{
									alert("删除失败!");
									return false;
							}
					});
					
			});
			var jiesuan=$(".cat-zh").find("ul").eq(m);
			    jiesuan.append("<li class='jiesuan'><a href='#'></a></li>");
			    jiesuan.find("li a").last().attr("href","/gui_o2o/mz/fore/cart_new.html").html("共计"+spxxList.TOTALNUM+"个商品，总计："+spxxList.TOTALAMOUNT+"元<br /><b>去购物车结算</b>");
			    */
			    
				//$(".shuliang").html(spxxList.TOTALNUM);
	});
}
</script>
  <body topMargin="0px" leftMargin="0px">
	<div class="toper">
    <div class="top_main">
      <ul class="top_left">
      	<li class="login"><a >您好,</a></li>
        <li class="login"><a id="loginP" href="/customer/qfy/register/login.html">请登录</a></li>
        <!-- <li class="reg"><a id="regist" href="/customer/qfy/register/register.html">免费注册</a></li> -->
        <li class="login"><a href="" class="login" style="display:none;" id="ext">[退出]</a></li>
        <li  class="dq">
	        <div class="selectSea">
	         配送至:<a></a><span id="city_name">天津</span><b>[ 更改 ]</b><input id="city_value" type="hidden" value="120000"/>
			  <ul class="city_list" style="z-index:1;">
			    
			  </ul>
			</div>
        </li>
        <!-- <li class="dq">
          <a>武汉</a><span>[ 更改 ]</span>
          <div class="city">
            <div class="hotcity"><label>热门城市</label><a href="#">北京</a><a href="#">天津</a><a href="#">上海</a><a href="#">深圳</a><a href="#">广州</a><a href="#">武汉</a></div>
            <div class="hotcity xzcity"><b class="xuan">ABCD</b><b>EFGH</b><b>IJKL</b><b>MNOP</b><b>QRET</b><b>UWXYZ</b></div>
           <div class="city_main">
             <a>澳门</a><a>澳门</a><a>澳门</a><a>澳门</a><a>澳门</a><a>澳门</a><a>澳门</a><a>澳门</a><a>澳门</a>
           </div>
          </div>
        </li>
       <li class="gz" hidden><a>关注汽服云</a></li> -->
      </ul>
      <ul class="top_right">
        <li class="mycenter">
        	<a onclick="javascript:void(0);" id="grzx" href="#">我的汽服云</a><span></span>
<%--        	<div class="mycenter_list">--%>
<%--        		<a onclick="javascript:void(0);" id="grzx" href="#">个人中心</a>--%>
<%--        		<a onclick="javascript:void(0);" id="fbsp" href="#">发布商品</a>--%>
<%--        		<a hidden>出售中的商品<font>20</font></a>--%>
<%--        		<a hidden>仓库中的商品<font>15</font></a>--%>
<%--        		<a hidden>申请公益品牌</a>--%>
<%--        	</div>--%>
        </li>
        <li class="mycat"><div class="cat-zh"></div><a href="/customer/qfy/shopping/cart.html">购物车<b class="shuliang">0</b></a><span></span></li>
        <!--<li><font>|</font></li>
        <li class="myphone"><a>手机汽服云</a></li>-->
        <li><font>|</font></li>
        <li class="kf"><a href="/customer/qfy/showMessage/index-lxwm.html">联系客服</a></li>
        <li class="wzdh" hidden><a>网站导航</a><span></span></li>
      </ul>
    </div>  
  </div>
  <!--------------LOGO搜索部分--------------->
  <div class="logo_seach">
    <a class="logo" href="/customer/qfy/index.html" style="background:url(/customer/qfy/images/<%=logoName%>) #fff; "></a>
    <div class="seach">
      <ul id="seach_tj" class="seach_tj"><li id="seach_tj1" class="xuan">产品</li><li id="seach_tj2">OEM</li><li id="seach_tj3">商品条码</li></ul>
      <div class="seach_main">
        <input type="hidden" value="cp" id="tjbh">
        <input id="sousuo" placeholder="输入产品名称、商品条码、店铺名称等都可以直接搜索" />
        <!--------------搜索下拉影藏--------------->
        <ul class="seach_list">
          <li><a>输入产品名称、商品条码、店铺名称等都可以直接搜索</a><span>约15555个结果</span></li>
          <li><a>输入产品名称、商品条码、店铺名称等都可以直接搜索</a><span>约15555个结果</span></li>
        </ul>
        <a class="seach_bot">搜 索</a>
        <a hidden class="gjss">高级<br />搜索</a>        
      </div>
    </div>
    <!--------------二维码--------------->
    <div class="shewm"><div class="ewmclose"></div></div>
  </div>
  <div class="clera"></div>
  
  <div class="nav">
    <!--------------政策/产品/服务分类菜单--------------->
    <div class="nav_info">产品/服务分类</div>
    <ul class="nav_fl_list">
<%--      <li>--%>
<%--        <b class="zcfg">--%>
<%--          <div class="nav_main">--%>
<%--            <h3>政策法规</h3>--%>
<%--            <span><a href="/customer/qfy/showMessage/poRegulations.html?ALID=1">发改委</a><a href="/customer/qfy/showMessage/poRegulations.html?ALID=3">教育部</a><a href="/customer/qfy/showMessage/poRegulations.html?ALID=4">公安部</a></span>--%>
<%--          </div>--%>
<%--          <div class="nav_jt"></div>--%>
<%--        </b>--%>
<%--         <!--------------政策法规子分类详情--------------->--%>
<%--        <div class="nav_zc_main">--%>
<%--          <div class="zhexian"></div>--%>
<%--          <ul>--%>
<%--            <li><a class="text" href="/customer/qfy/showMessage/poRegulations.html?ALID=1">发展改革委</a></li>--%>
<%--            <li><a class="text" href="/customer/qfy/showMessage/poRegulations.html?ALID=3">教育部</a></li>--%>
<%--            <li><a class="text" href="/customer/qfy/showMessage/poRegulations.html?ALID=4">公安部</a></li>--%>
<%--            <li><a class="text" href="/customer/qfy/showMessage/poRegulations.html?ALID=5">环境保护部</a></li>--%>
<%--            <li><a class="text" href="/customer/qfy/showMessage/poRegulations.html?ALID=6">住房城乡建设部</a></li>--%>
<%--            <li><a class="text" href="/customer/qfy/showMessage/poRegulations.html?ALID=2">交通运输部</a></li>--%>
<%--            <li><a class="text" href="/customer/qfy/showMessage/poRegulations.html?ALID=7">商务部</a></li>--%>
<%--            <li><a class="text" href="/customer/qfy/showMessage/poRegulations.html?ALID=8">工商总局</a></li>--%>
<%--            <li><a class="text" href="/customer/qfy/showMessage/poRegulations.html?ALID=9">质检总局</a></li>--%>
<%--            <li><a class="text" href="/customer/qfy/showMessage/poRegulations.html?ALID=10">保监会</a></li>--%>
<%--          </ul>--%>
<%--        </div>--%>
<%--      </li>--%>
      <!-- 
      <li>
        <b class="khgn">
          <div class="nav_main">
            <h3>客户专区</h3>
            <span><a href="/customer/qfy/showMessage/remind.html">条码追溯</a><a href="/customer/qfy/showMessage/remind.html">快速理赔</a></span>
          </div>
          <div class="nav_jt"></div>
        </b>
<%--         ------------客户功能子分类详情---------------%>
        <div class="nav_zc_main">
          <div class="zhexian"></div>
          <ul>
            <li><a class="xqfb"></a></li>
            <li><a class="tmzs" href="/customer/qfy/showMessage/remind.html"/></a></li>
            <li><a class="kstd"></a></li>
            <li><a class="gzfx"></a></li>
            <li><a class="kslp"></a></li>
            <li><a class="bxgm"></a></li>
          </ul>
        </div>
      </li> 
       -->
      <li data-index="1">
        <b class="cxfl">
          <div class="nav_main">
            <h3>车型分类</h3>
            <span><a href="/customer/qfy/showProduct/carSs.html?car_id=52&&ORIGIN_COUNTRY_ID=1">宝马</a><a href="/customer/qfy/showProduct/carSs.html?car_id=49&&ORIGIN_COUNTRY_ID=1">大众</a><a href="/customer/qfy/showProduct/carSs.html?car_id=51&&ORIGIN_COUNTRY_ID=1">奥迪</a><a href="/customer/qfy/showProduct/carSs.html?car_id=48&&ORIGIN_COUNTRY_ID=1">奔驰</a></span>
          </div>
          <div class="nav_jt"></div>
        </b>
        <!--------------车型分类子分类详情--------------->
        <div class="nav_pro_main">
          <div class="zhexian"></div>
          <ul class="pro_list">
            <li class="title cxfl-ch">
              <span>车型分类</span>
            </li>
          </ul>
        </div>
      </li>
      <li data-index="2">
        <b class="yhkx">
          <div class="nav_main">
            <h3>养护快修</h3>
            <span><a href="/customer/qfy/showProduct/member_shop.html?f3_id=010101&f3_name=%u673A%u6CB9">机油</a><a href="/customer/qfy/showProduct/member_shop.html?f3_id=011001&f3_name=%u73BB%u7483%u6C34">玻璃水</a><a href="/customer/qfy/showProduct/member_shop.html?f3_id=031001&f3_name=%u706B%u82B1%u585E">火花塞</a></span>
          </div>
          <div class="nav_jt"></div>
        </b>
        <!--------------子分类详情--------------->
        <div class="nav_pro_main">
          <div class="zhexian"></div>
          <ul class="pro_list">
            <li class="title">
              <span>产品分类</span>
            </li>
          </ul>
        </div>
      </li>
      <li data-index="3">
        <b class="dlxt">
          <div class="nav_main">
            <h3>动力系统</h3>
            <span><a href="/customer/qfy/showProduct/member_shop.html?f3_id=041212&f3_name=%u6392%u6C14%u7BA1%u603B%u6210">排气管总成</a><a href="/customer/qfy/showProduct/member_shop.html?f3_id=050301&f3_name=%u71C3%u6CB9%u6CF5">燃油泵</a></span>
          </div>
          <div class="nav_jt"></div>
        </b>
        <!--------------子分类详情--------------->
        <div class="nav_pro_main">
          <div class="zhexian"></div>
          <ul class="pro_list">
            <li class="title">
              <span>产品分类</span>
            </li>
          </ul>
        </div>
      </li>
      <li data-index="4">
        <b class="dpxg">
          <div class="nav_main">
            <h3>底盘悬挂</h3>
            <span><a href="/customer/qfy/showProduct/member_shop.html?f3_id=080201&f3_name=%u65B9%u5411%u76D8">方向盘</a><a href="/customer/qfy/showProduct/member_shop.html?f3_id=090401&f3_name=%u9A7B%u8F66%u5236%u52A8%u5668">驻车制动器</a></span>
          </div>
          <div class="nav_jt"></div>
        </b>
        <!--------------子分类详情--------------->
        <div class="nav_pro_main">
          <div class="zhexian"></div>
          <ul class="pro_list">
            <li class="title">
              <span>产品分类</span>
            </li>
          </ul>
        </div>
      </li>
      <li data-index="5">
        <b class="qcdq">
          <div class="nav_main">
            <h3>汽车电器</h3>
            <span><a href="/customer/qfy/showProduct/member_shop.html?f3_id=070101&f3_name=%u7A7A%u8C03%u6CF5">空调</a><a href="/customer/qfy/showProduct/member_shop.html?f3_id=160918&f3_name=%u5E95%u76D8%u7EBF%u675F">底盘线束</a></span>
          </div>
          <div class="nav_jt"></div>
        </b>
        <!--------------子分类详情--------------->
        <div class="nav_pro_main">
          <div class="zhexian"></div>
          <ul class="pro_list">
            <li class="title">
              <span>产品分类</span>
            </li>
          </ul>
        </div>
      </li>
      <li data-index="6">
        <b class="lhbs">
          <div class="nav_main">
            <h3>离合变速</h3>
            <span><a href="/customer/qfy/showProduct/member_shop.html?f3_id=100201&f3_name=%u79BB%u5408%u5668%u538B%u76D8">离合器压盘</a></span>
          </div>
          <div class="nav_jt"></div>
        </b>
        <!--------------子分类详情--------------->
        <div class="nav_pro_main">
          <div class="zhexian"></div>
          <ul class="pro_list">
            <li class="title">
              <span>产品分类</span>
            </li>
          </ul>
        </div>
      </li>
      <li data-index="7">
        <b class="csbj">
          <div class="nav_main">
            <h3>车身部件</h3>
            <span><a href="/customer/qfy/showProduct/member_shop.html?f3_id=130101&f3_name=%u4EEA%u8868%u53F0%u58F3">仪表台壳</a><a href="/customer/qfy/showProduct/member_shop.html?f3_id=130125&f3_name=%u70B9%u70DF%u5668">点烟器</a></span>
          </div>
          <div class="nav_jt"></div>
        </b>
        <!--------------子分类详情--------------->
        <div class="nav_pro_main">
          <div class="zhexian"></div>
          <ul class="pro_list">
            <li class="title">
              <span>产品分类</span>          
            </li>
          </ul>
        </div>
      </li>
      <li data-index="8">
        <b class="mrzs">
          <div class="nav_main">
            <h3>美容装饰</h3>
            <span><a href="/customer/qfy/showProduct/member_shop.html?f3_id=190101&f3_name=%u6C7D%u8F66%u5750%u57AB">汽车坐垫</a><a href="/customer/qfy/showProduct/member_shop.html?f3_id=200501&f3_name=%u73BB%u7483%u8D34%u819C">玻璃贴膜</a></span>
          </div>
          <div class="nav_jt"></div>
        </b>
        <!--------------子分类详情--------------->
        <div class="nav_pro_main">
          <div class="zhexian"></div>
          <ul class="pro_list">
            <li class="title">
              <span>产品分类</span>
            </li>
          </ul>
        </div>
      </li>
      <!-- 快速采购 -->
  	  <li data-index="9">
      	<b class="kscg">
        	<div class="nav_main">
            	<h3>快速采购</h3>
            	<span><a>维修项目</a></span>
         	</div>
         	<div class="nav_jt"></div>
        </b>
        <!-- 快速采购详情 -->
        <div class="nav_pro_main">
        	<div class="zhexian"></div>
          	<ul class="pro_list">
            <li>
            	<dl><dd id="div_kscg">             	
            		<em><a href="#"></a></em>
              	</dd></dl>
            </li>
          </ul>
        </div>
      </li>
    </ul>
    <!--------------首页等菜单--------------->
    <ul class="nav_list">
      <li class="first"><a class="tj sy" href="/customer/qfy/index.html">首页</a></li>      
      <li><a  class="tj zcfg" href="/customer/qfy/showMessage/poRegulations.html">政策法规</a></li>
      <li><a href="/customer/qfy/showMessage/bm-index.html" class="tj tybm">统一编码</a></li>
      <li><a href="/customer/qfy/showMessage/pbd-index.html" class="tj qtpp">群体品牌</a></li>
<%--      <li><a href="/customer/qfy/showMessage/sce-315.html" class="tj zscq">知识产权</a></li>--%>
      <li><a href="/customer/qfy/showMessage/sptmzs.html" class="tj sptms">商品追溯</a></li>
      <li class="span"><span>|</span></li>
      <li class="gyl"><a href="/customer/qfy/showProduct/member_shop.html"></a></li>
      <!-- <li class="gyl"><a href="/customer/qfy/showMessage/remind.html"></a></li> -->
      <!-- <li><a href="/customer/qfy/showMessage/remind.html">客户服务</a></li>
      <li><a href="/customer/qfy/showMessage/remind.html">维修服务</a></li>
      <li><a href="/customer/qfy/showMessage/remind.html">金融服务</a></li> -->
      <li><a href="/customer/qfy/showMessage/index-zzfw.html" class="tj zzfws">增值服务</a></li>
    </ul>
   </div>
  </body>
</html>