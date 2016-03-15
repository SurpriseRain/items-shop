var uinfo = $.cookie('userInfo');
var usercookie = JSON.parse(uinfo);
var city_list = $.cookie("city_list");
//cookie误删时，存放默认值
if(city_list==null){
    city_list = [];
    var o = {};
    o.city_value = "12";
    o.city_name = "辽宁";
    o.city_value_2 = "1201";
    o.city_name_2 = "沈阳市";
    city_list.push(o);
    $.cookie("city_list", JSON.stringify(city_list), {
        expires : 365, path : '/'
    });
    city_list = $.cookie("city_list");
}
city_list = JSON.parse(city_list);
$("#city_value").val(city_list[0].city_value);
$("#city_name").html(city_list[0].city_name);
//选择不同地区加载不同广告
/*$("div.ggtype").each(function(i,val){
	var jlform = eval($(val).data("ggtype"));
	//alert($("#city_value").val());
	alert("1008611");
	jlform.initData();
	});*/
$(function(){
    /* 大区选择 --start-- */
    $(".city-locate").on("mouseenter",function(e){
    	var read = $("#city-list").data("read");
    	if(read === 0){
	    	var XmlData=[{'sqlid':'com.jlsoft.o2o.user.sql.select_dqmc','dataType':'Json'}];
	   	 	var url="/jlquery/selecto2o.action?XmlData="+JSON.stringify(XmlData);
		   	 ajaxQ({
		   			"url" : url,//原始url 
		   			"callback" : function(rData){
		   		 		var tpl = $.trim($("#m-tpl-2").html()),
		   		 			data = {"mapList":rData};
		   				dToHtml({"tpl":tpl,"data":data,"target":"#city-list"});
		   				$("#city-list").data("read","1");
		   		        //$("#city-wrapper").css({"display":"block"});
		   		        
			   		     $("#city-list").on("click","li",function(e){
			   		    	 var $this = $(e.currentTarget),
			   		    	 	select_text = $.trim($this.children("a").text()),
			   		    	 	b = $this.attr("id"),
			   		    	 	$city_wrapper = $("#city-wrapper");
						  	//将已选的省份值存放于cookie
						  	var XmlData=[{'sqlid':'com.jlsoft.o2o.user.sql.select_dqmc_2','dataType':'Json','dqbzm01':b}];
							var url="/jlquery/selecto2o.action?XmlData="+JSON.stringify(XmlData);
							ajaxQ({
				                "url" : url,
				                "callback" : function(rData){
									var city_list = [];
									var o = {};
									o.city_value = b;
									o.city_name = select_text;
									o.city_value_2 = rData[0].dqbzm01;
									o.city_name_2 = rData[0].dqbzm02;
									city_list.push(o);
									$.cookie("city_list", JSON.stringify(city_list), {
										expires : 365, path : '/'
									});
	
								  	$("#city_name").html(select_text);
								  	$("#city_value").val(b);
									//选择不同地区加载不同广告
									$("div.ggtype").each(function(i,val){
										var jlform = eval($(val).data("ggtype"));
										//alert($("#city_value").val());
										jlform.initData();
			   		    			});
								  	//$city_wrapper.css({"display":"none"});
								  	$city_wrapper.attr("class","more_cd display_none");
				                },
				                "error":function(){}
				            });
														
			   		     });
		   			},
	                "error":function(){}
		   		});
    	}else{
    		//$("#city-wrapper").css({"display":"block"});
    		$(".more_cd").attr("class","more_cd display_block");
    	}
    }).on("mouseleave",function(e){
    	//$("#city-wrapper").css({"display":"none"});
    	$(".more_cd").attr("class","more_cd display_none");
    });
    /* 大区选择 --end-- */   

});

/* 大区选择 --start--*/
function select_dqmc(){
    var XmlData=[{"sqlid":"com.jlsoft.o2o.user.sql.select_dqmc","dataType":"Json"}];
    var url="/jlquery/selecto2o.action?XmlData="+JSON.stringify(XmlData);
    ajaxQ({
        "url" : url,
        "callback" : function(rData){
            if($("#city-list").data("read") === "1"){
                return;
            }
            dToHtml({"tpl":$("#m-tpl-2").html(),"data":rData,"target":"#city-list"});
            $("#city-list").data("read","1");

            $("#city-list").on("click","li",function(e){
                var $this = $(e.currentTarget);
                var selectSea_text = $this.text();
                var b = $this.attr("id");
                $("#city_name").html(selectSea_text);
                $("#city_value").val(b);
                $("#city-list").css({"display":"none"});
                /*$("div.ggtype").each(function(i,val){
   		    		var jlform = eval($(val).data("ggtype"));
	   		    	jlform.setTab(val); 
	   		    	var json = {};
	   		    	jlform.initForm(json);
   		    	});*/
                //将已选的省份值存放于cookie
                var XmlData=[{"sqlid":"com.jlsoft.o2o.user.sql.select_dqmc_2","dataType":"Json","dqbzm01":b}];
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
        },
        "error":function(){}
    });

}
/* 大区选择 --end-- */

/* 购物车 --start-- */
//selectMyCart();
function selectMyCart(){
    var xmlData=[];
    var json={};
    json.HBID=usercookie.ZCXX01;
    json.DQXX01=usercookie.ZCXX07;
    xmlData.push(json);
    var url="/CartManage/selectGwcByHbid.action?XmlData="+JSON.stringify(xmlData);
    
    ajaxQ({
        "url" : url,
        "callback" : function(rData){
	    	if(!rData || rData.GWCLIST == "" || rData.GWCLIST == null) {
	            $("#shopping-amount").html(0);
	        }else {
	            $("#shopping-amount").html(rData.GWCLIST[0].TOTALNUM);
	        }
        },
        "error":function(){}
    });
}
/* 购物车 --end-- */

/* 检查是否登录 --start--  */
checkLogin();
function checkLogin(){
	if(usercookie!=null){
		selectMyCart(usercookie);
		// 
		$("#login-ext").one("click",function(e){
			var qm = $(".sn-quick-menu");
			qm.find("li.login-info").css("display","block");
			$("#login-name").text("");
			$("#login-ext").text("");
			qm.find("li.login-ext").css("display","none");
			$.cookie('userInfo',null,{path:"/"});
			window.location.href="/customer/sydjt/index.html";
			return false;
		});
		var qm = $(".sn-quick-menu");
		if(usercookie.LX=='24'){//个人
			$("#login-name").text("您好，"+usercookie.XTCZY01);
		}else{//企业
			$("#login-name").text("您好，"+usercookie.ZCXX02);
		}
		qm.find("li.login-info").css("display","none");
		qm.find("li.login-ext").css("display","block");
		if(usercookie.ZCGS01==4){
			$("#u-home").attr('href','/customer/sydjt/back/shop-main.html');
			//$("#fbsp").attr('href','/customer/sydjt/back/shop-main.html?menuId=A001');
		}else if(usercookie.ZCGS01==2){
			$("#u-home").attr('href','/customer/sydjt/register/registerPerfectDB.html');
		}
		
	}else{
		var qm = $(".sn-quick-menu");
		qm.find("li.login-info").css("display","block");
		qm.find("li.login-ext").css("display","none");
	}
}
/* 检查是否登录 --end--*/
$("#login-name").click(function(){
	if(usercookie.LX==24||usercookie.LX==43){
		$("#login-name").attr('href','/customer/sydjt/back/shop-main.html');
	}else if(usercookie.LX==44){
		$("#login-name").attr('href','/customer/sydjt/index.html');
	}
	
});

//设为首页
function SetHome(obj,url){
    try{
        obj.style.behavior='url(#default#homepage)';
        obj.setHomePage(url);
    }catch(e){
        if(window.netscape){
            try{
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            }catch(e){
                alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
            }
        }else{
            alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【"+url+"】设置为首页。");
        }
    }
}
 
//收藏本站
function AddFavorite(title, url) {
    try {
        window.external.addFavorite(url, title);
    }
    catch (e) {
        try {
            window.sidebar.addPanel(title, url, "");
        }
        catch (e) {
            alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加");
        }
    }
}
