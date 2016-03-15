var uinfo = $.cookie('userInfo');
var usercookie = JSON.parse(uinfo);
var city_list = $.cookie("city_list");
var template1 = "{{ label }} <span>约{{ NUM }}件商品</span>";
var type1 = "SPMC_STR";
//cookie误删时，存放默认值
//crCityList();
if(city_list==null){
    city_list = [];
    var o = {};
    o.city_value = "120000";
    o.city_name = "天津";
    o.city_value_2 = "120100";
    o.city_name_2 = "天津市";
    city_list.push(o);
    $.cookie("city_list", JSON.stringify(city_list), {
        expires : 1, path : '/'
    });
    city_list = $.cookie("city_list");
}
city_list = JSON.parse(city_list);
$("#city_value").val(city_list[0].city_value);
$("#city_name").html(city_list[0].city_name);

var solr = pubJson.Solr;
var searchUrl = "";
if(solr == "1"){
	searchUrl = pubJson.O2OUrl + "/customer/qfy/showProduct/member_shop_s.html";
} else {
	searchUrl = pubJson.O2OUrl + "/customer/qfy/showProduct/member_shop_n.html";
}

$(function(){
	$("#cnzz_stat_icon_1256019189").hide();
    // 图片懒加载
    $(".floor-ad-banner").find("img.la").lazyload({
        data_attribute:"src",
        effect:"fadeIn",
        errorSrc:"/customer/qfy/images/msimg.jpg"
    });
    // 同质配件 里面两个广告图 延迟加载
    $(".fl-1-goods").find("img.la").lazyload({
        data_attribute:"src",
        effect:"fadeIn",
        errorSrc:"/customer/qfy/images/msimg.jpg"
    });

    checkLogin();
    showSlider();
    showNews();
    showNewsPersonal();
    
    // 同质配件
    showFloors({ "lcfl":"3,6", "tpl":$("#m-tpl-4").html(),"fl":"#fl-tz","target":"#fl-tz-grid ul" ,"type":1});
    // 新品上架
    showFloors({ "lcfl":"4,10", "tpl":$("#m-tpl-5").html(),"fl":"#fl-qp","target":"#fl-qp-grid ul" ,"type":2});
    // 热卖商品
    //showFloors({ "lcfl":"5,10", "tpl":$("#m-tpl-5").html(),"fl":"#fl-rm","target":"#fl-rm-grid ul" ,"type":2});
    //放心汽修
    showFxqxFloor({ "num":"6", "tpl":$("#m-tpl-4").html(),"fl":"#fl-fxqx","target":"#fl-fxqx-grid ul"});
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
		   		        $("#city-wrapper").css({"display":"block"});
		   		        
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
				                	if(rData.length === 0){
				                		return;
				                	}
									var city_list = [];
									var o = {};
									o.city_value = b;
									o.city_name = select_text;
									o.city_value_2 = rData[0].dqbzm01;
									o.city_name_2 = rData[0].dqbzm02;
									city_list.push(o);
									$.cookie("city_list", JSON.stringify(city_list), {
										expires : 1, path : '/'
									});
	
								  	$("#city_name").html(select_text);
								  	$("#city_value").val(b);
								  	$city_wrapper.css({"display":"none"});
				                },
				                "error":function(){}
				            });
														
			   		     });
		   			},
	                "error":function(){}
		   		});
    	}else{
    		$("#city-wrapper").css({"display":"block"});
    	}
    }).on("mouseleave",function(e){
    	$("#city-wrapper").css({"display":"none"});
    });
    
    /* 大区选择 --end-- */   
    
   
    /* 搜索 --start-- */
    $("#search").on("click",function(e){
    	
        var $searcht = $("#search-t"),
	        $mq = $("#mq");
        if($mq.val()==='') {
			//alert("请输入查询内容");
        	//dlgShow({content:"请输入查询内容"});
        	location.href = searchUrl;
			return false;
		}
      //产品搜索
        searchFun($searcht, $mq);
        return false;
	 /*	if($searcht.val()=="cp") {
			var cpmc = ($mq.val()).trim();
			location.href=searchUrl + "?SPXX04="+escape(cpmc);
		}else if($searcht.val()=="oe"){
			var oe = ($mq.val()).trim();
			var url="/QFY/selectSPXXbyOE.action?oe="+oe;
			var data=visitService(url);
			if(data.STATE == 1){
				data=data.data;
				location.href="/customer/qfy/showProduct/product.html?spxx01="+data.SPXX01+"&zcxx01="+data.ZCXX01+"&gsid="+data.ZCXX01+"&gsmc="+escape(data.ZCXX02);
			}else if(data.STATE == 2){
				var oe = ($mq.val()).trim();
				location.href=searchUrl + "?spglljh01="+oe;
			}else{
				//alert("该OEM商品暂无！");
				dlgShow({content:"该OEM商品暂无"});
			}
		}else if($searcht.val()=="tm") {
				//搜索条码
			var sptm = ($mq.val()).trim();
			var url="/QFY/selectSPXXbySPTM.action?sptm="+sptm;
			var data=visitService(url);
			if(data.STATE == 1){
				data=data.data;
				location.href="/customer/qfy/showProduct/product.html?spxx01="+data.SPXX01+"&zcxx01="+data.ZCXX01+"&gsid="+data.ZCXX01+"&gsmc="+escape(data.ZCXX02);
			}else if(data.STATE == 2){
				//var oe = ($mq.val()).trim();
				location.href=searchUrl + "?barcode="+sptm;
			}else{
				//alert("该条码商品暂无！");
				dlgShow({content:"该条码商品暂无"});
			}
		}
	 	
	 	return false;*/
    });
    // 查询条件
    $("#seach_tj").on("click","li",function(e){
        var $this = $(e.currentTarget),
            $searcht = $("#search-t"),
            $mq = $("#mq");
        $this.addClass("bg-ff3c3c").siblings("li").removeClass("bg-ff3c3c");
        $('#mq').autocompleter('destory');
        $.autocompleter('clearCache');

        var tjval = $this.text();
        switch(tjval){
            case "产品":
            	$mq.attr("placeholder","输入产品名称可以直接搜索");
                $searcht.val("cp");
                type1 = "SPMC_STR";
                template1 = '{{ label }} <span>约{{ NUM }}件商品</span>';
                break;
            case "OEM":
            	$mq.attr("placeholder","输入产品OEM码");
                $searcht.val("oe");
                template1 = '{{ label }}';
                type1 = "ZJCLJH";
                break;
            case "商品条码":
            	$mq.attr("placeholder","请输入商品条码");
                $searcht.val("tm");
                template1 = '{{ label }}';
                type1 = "BARCODE";
                break;
        }
    });
    /* 搜索 --end-- */
    
    /* 菜单 --start-- */
    $("#menu-nav").on("mouseenter","li.category-item", function (e) {
        var $this = $(e.currentTarget),
        	$pannel = $this.find("div.ci-pannel"),
            index = $this.data("index"),
            isReady = $pannel.data("read");
            
        $("div.ci-pannel").hide();
        if(!isReady || isReady === "0"){
        	if(index !== 1){
        		getSpflInfos({"target":$this,"index":index});
        	}else{
        		$pannel.show();
        		$pannel.data("read","1");
        	}
        }else{
            $pannel.show();
        }
    }).on("mouseleave","li",function(e){
        var $this = $(e.currentTarget),
            $pannel = $this.find("div.ci-pannel");
        $pannel.hide();
    });
    /* 菜单 --end-- */
    
    $("#cx").on("mouseenter","li", function (e) {
        var $this = $(e.currentTarget),
    	    $pannel = $this.find("div.cxcx-pannel"),
    	    isReady = $pannel.data("read");
        
    	if(!isReady || isReady === "0"){
    		getCxcxInfos({"target":$this});
    	}
    	$this.parent("ul").parent("div.ci-pannel").css("width","820px");
        $pannel.fadeIn(50);
    }).on("mouseleave","li",function(e){
    	var $this = $(e.currentTarget),
    	    $pannel = $this.find("div.cxcx-pannel");
    		$pannel.fadeOut(100);
    	
    	$this.parent("ul").parent("div.ci-pannel").css("width","300px");
    });
    
    // 手机版
    $(".sn-mobile").on("mouseenter",function(e){
    	var $this = $(e.currentTarget),
    		snQrCode = $(".sn-qrcode"),
    		isSQC = snQrCode.length >= 1 ? true : false,
			tpl = "";
    	if(isSQC){
    		snQrCode.show();
    	}else{
    		tpl = '<div class="sn-qrcode" style="display: none;">' +
        			'<div class="sn-qrcode-content"></div>' +
        				'<p>扫一扫，关注下载APP</p>' +
        				'<b></b>' +
        		  '</div>';
    		$this.append(tpl);
    		$(".sn-qrcode").show();
    	}
    }).on("mouseleave",function(e){
    	$(".sn-qrcode").hide();
    });
    
    // 联系客服
    $("#cusService").mouseover(function(){
    	$(this).animate({"right":"-32px"});
    	$("#cusService_main").animate({"right":"5px"});
	});
	$("#cusService_main").mouseleave(function(){
    	$("#cusService").animate({"right":"5px"});
    	$(this).animate({"right":"-222px"});
	});
	//返回顶部
	
	$(window).scroll(function(){
		var $top=$('.goTop');

		if($(window).scrollTop()>550){

			$top.css({'display':'block'});
			$top.on('mouseover',function(){
				//添加样式

			}).on('mouseout',function(){
				// alert('移出');
				
				
			})
		}else{
			$top.hide();
		}
	});
	$('.goTop').on('click',function(){
		$('html,body').animate({
			'scrollTop':'0'
		},500);
		return false;
	});    
	/*$("#mq").on("keyup",function(e){
		var $this = $(e.currentTarget),
			$val = $this.val();
		if($val === ""){
			return;
		}
		
		setTimeout(function(){
			
			
			console.log($this.val());
		},1000);
	});*/
});

// 维修养护
function getSpflInfos(para){
	var xmlData = null,
		$pannel = para["target"].find("div.ci-pannel"),
	    tpl = $.trim($("#m-tpl-8").html()),
		url = "";
	xmlData = [{"sqlid":"com.jlsoft.o2o.pub.sql.SPFL.selectSPFL","dataType":"Json","SPFL05":"0"+(para["index"]-1)}];//,"SPFL05":"0"+(index-1)
	url = "/jlquery/selecto2o.action?XmlData="+JSON.stringify(xmlData);
	
	// 取数据
    // 填充
    ajaxQ({
        "url" : url,
        "callback" : function(rData){
    		if(rData && rData.length > 0){
    			var mapArr = [],
    				len = rData.length,
    				data = {},
    				i;
    			for(i = 0; i < len; i++){
    				mapArr[i] = {"name":rData[i]["spfl02"],"src":"/customer/qfy/images/menu-icon/m-0"+(para["index"]-1),"href":searchUrl+"?spfl01="+rData[i]["spfl01"]};
    			}
    			data = {"mapList":mapArr};

        		dToHtml({"tpl":tpl,"data":data,"target":$pannel.find("ul")});
        		
                $pannel.data("read","1");
                $pannel.fadeIn(10);
    		}            		
        },
        "error":function(){}
    });
}
// 车型分类
function getCxcxInfos(para){
	var $this = para["target"],
		qcty = $this.data("qcty"),
		$pannel = $this.find("div.cxcx-pannel"),
		//xmlData = xmlData = [{"sqlid":"com.jlsoft.o2o.pub.sql.SPFL.selectCXCX","dataType":"Json"}],
		xmlData = [{"sqlid":"com.jlsoft.o2o.pub.sql.SPFL.selectCXCX","dataType":"Json","origin_country":qcty}],
		url = "/jlquery/selecto2o.action?XmlData="+JSON.stringify(xmlData),
		tpl = $.trim($("#m-tpl-9").html());
		
	ajaxQ({
        "url" : url,
        "callback" : function(rData){
			//console.log(rData);
			if(rData && rData.length > 0){
				var cxData = {"mapList":[]},
					mapList = cxData["mapList"],
					rDataItem = null,
					len = rData.length,i;
				
				for(i = 0; i < len; i++){
					rDataItem = rData[i];
					mapList[i] = {"name":rDataItem["brand"],"src":getCxImgSrc(rDataItem),"href":getCxHref(rDataItem)};
				}
				
				dToHtml({"tpl":tpl,"data":cxData,"target":$pannel});
				
				$this.parent("ul").parent("div.ci-pannel").css("width","820px");
				$pannel.data("read","1");
			}	                    				
			
        },
        "error":function(){
        	$pannel.find("span").html("加载失败,请刷新后重试");
        }
    });
}

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
                    expires : 1, path : '/'
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
			$("#shopping-amount").html(0);
			location.href="/customer/qfy/register/login.html?type=1";
			return false;
		});
		var qm = $(".sn-quick-menu");
		$("#login-name").text("您好，"+usercookie.ZCXX02);
    	//$("#login-name").attr('href','/customer/qfy/showDpxx/shopAllGoods.html?gsid='+usercookie.ZCXX01+'&gsmc='+escape(usercookie.ZCXX02));
		$("#login-name").attr('href',"/customer/qfy/back/shop-main.html");
		qm.find("li.login-info").css("display","none");
		qm.find("li.login-ext").css("display","block");
		if(usercookie.ZCGS01==4){
			$("#u-home").attr('href','/customer/qfy/back/shop-main.html');
			//$("#fbsp").attr('href','/customer/qfy/back/shop-main.html?menuId=A001');
		}else if(usercookie.ZCGS01==2){
			$("#u-home").attr('href','/customer/qfy/register/registerPerfectDB.html');
		}else{
			$("#u-home").hide();
		}
		// 车主
		if(usercookie["LX"] === "24"){
			/*$("#reqPub").attr("href","/customer/qfy/c2b/CarService.html");*/
			$("#login-home").attr('href','#');
			$("#reqPub").hide();
		}else if(usercookie["LX"] === "44"){
			// 维修厂
			$("#reqPub").attr("href","/customer/qfy/c2b/repair/Repari-main.html");
		}else{
			$("#reqPub").parent("li").remove();
		}
		$("#news-user-more").attr("href","/customer/qfy/c2b/DemandMap.html?type=1");
		$("#news-part-more").attr("href","/customer/qfy/c2b/DemandMap.html?type=2");
	}else{
		var qm = $(".sn-quick-menu");
		qm.find("li.login-info").css("display","block");
		qm.find("li.login-ext").css("display","none");
		
		$("#reqPub").attr("href","/customer/qfy/register/login.html?type=1");
	}
}
/* 检查是否登录 --end--*/
/*$("#login-name").click(function(){
	if(usercookie.LX==43||usercookie.LX==44){
		$("#login-name").attr('href','/customer/qfy/back/shop-main.html');
	}else if(usercookie.LX==44){
		$("#login-name").attr('href','/customer/qfy/index.html');
	}
	
});*/

/* 轮播图 --start-- */
function showSlider(){
    var xmlData=[{"size":4,"gsid":"JL"}];
    var url="/HomePage/selectPictureMainPage.action?XmlData="+JSON.stringify(xmlData);
    ajaxQ({
        "url" : url,
        "callback" : function(rData){
            var len = !!rData["mapList"] ? rData["mapList"].length : 0,
                cmsSlArr = {"mapList":[]},
                i;
            if(len <= 0){
                return;
            }
            dToHtml({"tpl":$("#m-tpl-3").html(),"data":rData,"target":"#cms-box"});

            for(i = 0; i < len; i++){
                cmsSlArr["mapList"].push({});
            }

            dToHtml({"tpl":$("#m-tpl-6").html(),"data":cmsSlArr,"target":"#cmsNav"});

            $("#cmsNav a").powerSwitch({
                animation: "fade",
                classAdd: "on",
                classPrefix: "cms_",
                container: $("#cmsControl"),
                autoTime : 5000,
                onSwitch: function(target) {
                    var eleLazyLoad = target.find("img, iframe").get(0);
                    if (eleLazyLoad && !eleLazyLoad.src) {
                        eleLazyLoad.src = eleLazyLoad.getAttribute("data-src");
                    }
                }
            });

        },
        "error":function(){}
    });
}
/* 轮播图 --end--*/
/* 楼层 --start-- */
function showFloors(flParas){
    var xmlData=[];
    var lcJson={};
    lcJson.ZTFB01=1;
    lcJson.HBID="";
    if (usercookie!=null) {
        lcJson.HBID=usercookie.ZCXX01;
    }
    lcJson.LCFL01=flParas["lcfl"];
    xmlData.push(lcJson);

    var url="/QFY/selectFloorGoods.action?XmlData="+JSON.stringify(xmlData);

    ajaxQ({
        "url" : url,
        "callback" : function(rData){
	    	//console.log(rData);
	    	if(rData["mapList"].length<=0){
	    		return;
	    	}
	    	var data = {"mapList":[]},
	    		rDataMaplist = rData["mapList"];
	    	var mapList = data["mapList"],
	    		startIndex = flParas["type"] === 1?0:flParas["type"],
	    		goodInfo ={},
	    		gname = "",
	    		cname = "",
	    		shortGName = "",
	    		shortCName = "",
	    		price = "",
	    		icon = "",
	    		i;
	    	if(rDataMaplist && rDataMaplist.length > 0){
	    		
	    		len = rDataMaplist.length;
	    		for(i=startIndex;i<len;i++){
	    			price = getGPrc();
	    			goodInfo = rDataMaplist[i];
	    			gname = goodInfo["SPMC"];
	    			cname = goodInfo["ZCXX02"];
	    			shortGName = gname.length > 16? gname.substring(0,16)+"...":gname;
	    			shortCName = cname.length > 12? cname.substring(0,12)+"...":cname;
	    			if(price === ""){
	    				price = accounting.formatMoney(goodInfo["SPJGB05"]);
	    			}
	    			icon = getProductQualityIcon(goodInfo);
	    			if(icon !== ""){
	    				icon = '<div class="p-icon">'+icon+'</div>';
	    			}
	    			mapList[i-startIndex] = {
	    					"src":getFlImgSrc(goodInfo),
	    					"href":"/customer/qfy/showProduct/product.html?spxx01="+goodInfo["SPXX01"]+"&zcxx01="+goodInfo["ZCXX01"]+"&gsid="+goodInfo["ZCXX01"]+"&gsmc="+escape(goodInfo["ZCXX02"]),
	    					"gname":gname,
	    					"shortGName":shortGName,
	    					"cname":cname,
	    					"shortCName":shortCName,
	    					"price": price,
	    					"icon":icon
	    					};
	    			icon = "";
	    		}
	    		
	    		dToHtml({"tpl":flParas["tpl"],"data":data,"target":flParas["target"]});
	    	}
	    	if(startIndex === 0){
	    		/*goodInfo = rDataMaplist[0];
	    		gname = goodInfo["SPMC"];
	    		$("#fl-tz").find("a.fl-1-goods").attr("href","/customer/qfy/showProduct/product.html?spxx01="+goodInfo["SPXX01"]+"&zcxx01="+goodInfo["ZCXX01"]+"&gsid="+goodInfo["ZCXX01"]+"&gsmc="+escape(goodInfo["ZCXX02"]));
	    		$("#fl-link01").append('<img src="" data-src="'+getFlImgSrc(goodInfo)+'" class="la" alt=""/>');*/
	    		$("#fl-tz").find("img.la").lazyload({
	    	        data_attribute:"src",
	    	        effect:"fadeIn",
	    	        placeholder:"../../images/s.gif",
	    	        errorSrc:"/customer/qfy/images/msimg.jpg"
	    	    });
	    		
	    	}else if(startIndex === 2){
	    		goodInfo = rDataMaplist[0];
	    		gname = goodInfo["SPMC"];
    			shortGName = gname.length > 16? gname.substring(0,16)+"...":gname;
    			shortCName = cname.length > 12? cname.substring(0,12)+"...":cname;
    			
    			price = getGPrc();
    			if(price === ""){
    				price = accounting.formatMoney(goodInfo["SPJGB05"]);
    			}
    			icon = "";
    			icon = getProductQualityIcon(goodInfo);
    			if(icon !== ""){
    				icon = '<div class="p-icon">'+icon+'</div>';
    			}
    			
	    		$($(flParas["fl"]).find("a.grid-row-2")[0]).attr("href","/customer/qfy/showProduct/product.html?spxx01="+goodInfo["SPXX01"]+"&zcxx01="+goodInfo["ZCXX01"]+"&gsid="+goodInfo["ZCXX01"]+"&gsmc="+escape(goodInfo["ZCXX02"]));
	    		$(flParas["fl"]+" #fl-link02").append('<img src="../../images/s.gif" data-src="'+getFlImgSrc(goodInfo)+'" class="la" alt=""/><div class="good-info">'+
						'<p class="good-name" title="'+gname+'">'+shortGName+'</p>'+
	    				'<p class="good-company" title="'+cname+'">'+shortCName+'</p>'+
	    				'<p class="good-price">'+price+'</p>'+
	  				'</div>' + icon);
	    		if(rDataMaplist.length <= 1){
	    			return;
	    		}
	    		goodInfo = rDataMaplist[1];
	    		gname = goodInfo["SPMC"];
	    		shortGName = gname.length > 16? gname.substring(0,16)+"...":gname;
    			shortCName = cname.length > 12? cname.substring(0,12)+"...":cname;
    			price = getGPrc();
    			if(price === ""){
    				price = accounting.formatMoney(goodInfo["SPJGB05"]);
    			}
    			icon = "";
    			icon = getProductQualityIcon(goodInfo);
    			if(icon !== ""){
    				icon = '<div class="p-icon" style="top: 230px;">'+icon+'</div>';
    			}
	    		$($(flParas["fl"]).find("a.grid-row-2")[1]).attr("href","/customer/qfy/showProduct/product.html?spxx01="+goodInfo["SPXX01"]+"&zcxx01="+goodInfo["ZCXX01"]+"&gsid="+goodInfo["ZCXX01"]+"&gsmc="+escape(goodInfo["ZCXX02"]));
	    		$(flParas["fl"]+" #fl-link03").append('<img src="../../images/s.gif" data-src="'+getFlImgSrc(goodInfo)+'" class="la" alt=""/><div class="good-info">'+
	    				'<p class="good-name" title="'+gname+'">'+shortGName+'</p>'+
	    				'<p class="good-company" title="'+cname+'">'+shortCName+'</p>'+
	    				'<p class="good-price">'+price+'</p>'+
	  				'</div>' + icon);
	    		$(flParas["fl"]).find("img.la").lazyload({
	    	        data_attribute:"src",
	    	        effect:"fadeIn",
	    	        placeholder:"../../images/s.gif",
	    	        errorSrc:"/customer/qfy/images/msimg.jpg"
	    	    });
	    	}
        },
        "error":function(){}
    });
}
function showFxqxFloor(flParas){
    var lcJson={"num":flParas["num"]};
    var url="/FWSP/selectFWSPList.action?json="+JSON.stringify(lcJson);

    ajaxQ({
        "url" : url,
        "callback" : function(rData){
	    	//console.log(rData);
	    	if(rData["fwspList"].length<=0){
	    		return;
	    	}
	    	var data = {"mapList":[]},
	    		rDataMaplist = rData["fwspList"];
	    	var mapList = data["mapList"],
	    		startIndex = 0,
	    		goodInfo ={},
	    		gname = "",
	    		cname = "",
	    		shortGName = "",
	    		shortCName = "",
	    		price = "",
	    		i;
	    	if(rDataMaplist && rDataMaplist.length > 0){
	    		
	    		len = rDataMaplist.length;
	    		for(i=startIndex;i<len;i++){
	    			//price = getGPrc();
	    			goodInfo = rDataMaplist[i];
	    			gname = goodInfo["ServiceName"];
	    			cname = goodInfo["ZCXX02"];
	    			shortGName = gname.length > 16? gname.substring(0,16)+"...":gname;
	    			shortCName = cname.length > 12? cname.substring(0,12)+"...":cname;
	    			//if(price === ""){
	    				price = accounting.formatMoney(goodInfo["CurrentPrice"]);
	    			//}
	    			
	    			mapList[i-startIndex] = {
	    					"src":"/jlsoft/fftp/fwsp/" + goodInfo["ServiceTitlePicturePath"],
	    					"href":"/customer/qfy/c2b/product_FWSP.html?id="+goodInfo["ID"],
	    					"gname":gname,
	    					"shortGName":shortGName,
	    					"cname":cname,
	    					"shortCName":shortCName,
	    					"price": price
	    					};
	    		}
	    		
	    		dToHtml({"tpl":flParas["tpl"],"data":data,"target":flParas["target"]});
	    		
	    		$("#fl-fxqx").find("img.la").lazyload({
	    	        data_attribute:"src",
	    	        effect:"fadeIn",
	    	        placeholder:"../../images/s.gif",
	    	        errorSrc:"/customer/qfy/images/msimg.jpg"
	    	    });
	    	}
	    	
        },
        "error":function(){}
    });
}
/* 楼层 --end-- */

/* 直通车 --start-- */
/*showZtc();*/
function showZtc(){
    //var functionName = "loadMoreNews";
    var newsXmlData=[];
    var newsJson={};
    newsJson.num=3;
    newsXmlData.push(newsJson);
    var url="/HomePage/selectW_DDPX.action?XmlData="+JSON.stringify(newsXmlData);

    ajaxQ({
        "url" : url,
        "callback" : function(rData){
    		var data = {"mapList":rData["mapList"].slice(0,5)},
    			rDataMaplist = data["mapList"],
    			len = rDataMaplist.length,
    			i;
    		for(i=0;i<len;i++){
    			rDataMaplist[i] = {"src":getZtcImgSrc(rDataMaplist[i]),"name":rDataMaplist[i]["DPXX01"]};
    		}
    		dToHtml({"tpl":$("#m-tpl-7").html(),"data":data,"target":"#fl-last"});
    		$("#fl-last").find("img.la").lazyload({
    	        data_attribute:"src",
    	        effect:"fadeIn",
    	        placeholder:"../../images/s.gif",
    	        errorSrc:"/customer/qfy/images/msimg.jpg"
    	    });
        },
        "error":function(){}
    });
}
/* 直通车 --end-- */

/* 新闻 --start-- */
function showNews(){
    var url="/RepairDemand/select_Produce.action";
    ajaxQ({
        "url" : url,
        "callback" : function(rData){
            var data = rData["produceList"],
            	len = !!data ? data.length : 0,
                newsArr = {"mapList":[]},
                name = "",
                classStr = "",
                i;
            if(len === 0){
            	return;
            }
            
            for(i = 0; i < len; i++){
            	if(data[i] === null || typeof data[i] !== "object"){
            		return;
            	}
            	name = data[i]["NAME"];
            	
            	name = name.length > 10? name.substring(0,10)+"...":name;
            	switch(data[i]["STATE"]){
            		case "3":
            			classStr = "news-icon-yg"; // 员工
            			break;
            		case "2":
            			classStr = "news-icon-wx"; // 配件
            			break;
            		case "1":
            			classStr = "news-icon-pj"; // 外协
            			break;
        			default:
        				classStr = "news-icon-pj"; // 外协
        				break;
            	}
            	newsArr["mapList"][i] = {
            			"name" : name,
            			"rName":data[i]["NAME"],
            			"classStr" : classStr
            	};
            }
            dToHtml({"tpl":$("#m-tpl-10").html(),"data":newsArr,"target":"#news-part-list"});
            
        },
        "error":function(){}
    });
}

function showNewsPersonal(){
    var url="/CarService/selectCarService.action";
    ajaxQ({
        "url" : url,
        "callback" : function(rData){
            var data = rData["carowner"],
            	len = !!data ? data.length : 0,
                newsArr = {"mapList":[]},
                name = "",
                phone = "",
                classStr = "",
                i;
            if(len === 0){
            	return;
            }
            
            for(i = 0; i < len; i++){
            	if(data[i] === null || typeof data[i] !== "object"){
            		return;
            	}
            	name = data[i]["ReallyName"] === null ? "" : data[i]["ReallyName"];
            	phone = data[i]["Phone"] === null ? "" : data[i]["Phone"];

                switch(data[i]["InitialName"]){
	        		case "救":
	        			classStr = "news-icon-jiu"; // 员工
	        			break;
	        		case "养":
	        			classStr = "news-icon-yang"; // 配件
	        			break;
	        		case "拖":
	        			classStr = "news-icon-tuo"; // 外协
	        			break;
	        		case "修":
	        			classStr = "news-icon-xiu"; // 外协
	        			break;
	        		case "服":
	        			classStr = "news-icon-fu"; // 外协
	        			break;
	    			default:
	    				classStr = "news-icon-yang"; // 外协
	    				break;
	        	}
            	name = name.length > 3? name.substring(0,3)+"...":name;
            	
            	newsArr["mapList"][i] = {
            			"name" : name,
            			"rName":data[i]["ReallyName"],
            			"phone":phone,
            			"rPhone":data[i]["Phone"],
            			"typeStr" : data[i]["InitialName"],
            			"classStr" : classStr
            	};
            }
            dToHtml({"tpl":$("#m-tpl-11").html(),"data":newsArr,"target":"#news-user-list"});
            
        },
        "error":function(){}
    });
}

/* 新闻 --end-- */

function crCityList(){
	var city_list = $.cookie("city_list");
	if(city_list!=null){
		city_list = JSON.parse(city_list);
		if(city_list[0] && city_list[0]["city_value"] !== "120000"){
			city_list = [];
		    var o = {};
		    o.city_value = "120000";
		    o.city_name = "天津";
		    o.city_value_2 = "120100";
		    o.city_name_2 = "天津市";
		    city_list.push(o);
		    $.cookie("city_list", JSON.stringify(city_list), {
		        expires : 1, path : '/'
		    });
		}
	}
}
function getGPrc(){
	if(usercookie!=null) {
		if(usercookie.LX !== "43" && usercookie.LX !== "42" && usercookie.ZCGS01 === 4){
			//商品零售价
			price = "";
		} else if(usercookie.LX == "24"){
			price = "";
		}
		else {
			price = "¥价格不可见";
		}
  	} else {
  		price = "¥登录可见";
    }
	
	return price;
}

/* 通过数据构建HTML */
function dToHtml(obj){
    var tpl = $.trim(obj["tpl"]),
        datas = obj["data"],
        fc =  _.template(tpl),
        rh = fc(datas);
    var target = typeof obj["target"] === "string"?$(obj["target"]):obj["target"];
    target.html(rh);
}
function getCxImgSrc(item){
	return "/customer/qfy/images/mycar/"+item["brand_py"]+"_"+item["brand_id"]+".png";
}
// 产品质量保证图标
function getProductQualityIcon(goodInfo){
	var icon = "";
	// 同质配件
	if(goodInfo["SPGL27"] === 1){
		icon = '<img src="/customer/qfy/images/i-tzpj.png" style="width:50px;height:50px;" class="fx-icon"></img>';
	}else{
		// 可追溯
		if(goodInfo["SPGL26"] === 1){
			icon = '<img src="/customer/qfy/images/i-fxqx.png" style="width:50px;height:50px;" class="fx-icon"></img>';
		}
		//  放心汽配
		if(goodInfo["SPGL28"] === 1){
			icon += '<img src="/customer/qfy/images/i-fxqp.png" style="width:50px;height:50px;" class="fx-icon"></img>';
		}
	}
	
	return icon;
}
/* 楼层 */
function getFlImgSrc(item){
	// 图片没有的时候 展示默认图片
	var imgUrl=pubJson.path_sptp+"/sptp/";
	return imgUrl+item.ZCXX01+"/"+item.SPXX02+"/images/"+item.SPXX02+"_1_big."+(item.SPTP02 || ".jpg").split(".")[1];
}
function getZtcImgSrc(item){
	var imgUrl=pubJson.path_sptp+"/dptp/";
	return imgUrl+item.DPXX02;
}
//获取车型链接
function getCxHref(rDataItem){
	var brandid = rDataItem["brand_id"] || "",
		ocid = rDataItem["origin_country_id"] || 0;
	return "/customer/qfy/showProduct/carSs.html?car_id="+brandid+"&&ORIGIN_COUNTRY_ID="+ocid;
}

// 搜索页面跳转
function searchFun($searcht, $mq){
	//产品搜索
 	if($searcht.val()=="cp") {
		var cpmc = ($mq.val()).trim();
		location.href= searchUrl + "?SPXX04=" + escape(cpmc);
	}else if($searcht.val()=="oe"){
		var oe = ($mq.val()).trim();
		var url="/QFY/selectSPXXbyOE.action?oe="+oe;
		var data=visitService(url);
		if(data.STATE == 1){
			data=data.data;
			location.href="/customer/qfy/showProduct/product.html?spxx01="+data.SPXX01+"&zcxx01="+data.ZCXX01+"&gsid="+data.ZCXX01+"&gsmc="+escape(data.ZCXX02);
		}else if(data.STATE == 2){
			var oe = ($mq.val()).trim();
			location.href=searchUrl + "?spglljh01="+oe;
		}else{
			//alert("该OEM商品暂无！");
			dlgShow({content:"该OEM商品暂无"});
		}
	}else if($searcht.val()=="tm") {
			//搜索条码
		var sptm = ($mq.val()).trim();
		var url="/QFY/selectSPXXbySPTM.action?sptm="+sptm;
		var data=visitService(url);
		if(data.STATE == 1){
			data=data.data;
			location.href="/customer/qfy/showProduct/product.html?spxx01="+data.SPXX01+"&zcxx01="+data.ZCXX01+"&gsid="+data.ZCXX01+"&gsmc="+escape(data.ZCXX02);
		}else if(data.STATE == 2){
			location.href=searchUrl + "?barcode="+sptm;
		}else{
			//alert("该条码商品暂无！");
			dlgShow({content:"该条码商品暂无"});
		}
	}
 	return false;
}
