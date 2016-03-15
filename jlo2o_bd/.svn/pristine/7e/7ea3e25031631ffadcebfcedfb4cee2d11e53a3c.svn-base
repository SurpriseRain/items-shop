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
$(function(){
    // 图片懒加载
    $(".floor-ad-banner").find("img.la").lazyload({
        data_attribute:"src",
        effect:"fadeIn"
    });
    $("#fl-link01").find("img.la").lazyload({
        data_attribute:"src",
        effect:"fadeIn"
    });
    //加载楼层数据
	var url="/oper_floor/selectAllFloor.action";
	var result = visitService(url);
	$(result.mapList).each(function(index,obj){
	    showFloors({ "lcfl":obj.LCFL01+",8","lcxh":index,"lcmc":obj.LCFL02,"lcmcTarget":"#floor_"+obj.LCFL01+" h3", "tpl":$("#m-tpl-4").html(),"target":"#floor_"+obj.LCFL01+" .floor_main ul" ,"type":1});
	});

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
    

    /* 搜索 --start-- */
    $("#search").on("click",function(e){
        var $this = $(e.currentTarget),
	        $searcht = $("#search-t"),
	        $mq = $("#mq");
        if($mq.val()==null || $mq.val()=='') {
			alert("请输入查询内容");
			return;
		}
      //产品搜索
	 	if($searcht.val()=="cp") {
			var cpmc = ($mq.val()).trim();
			location.href="/customer/sydjt/showProduct/member_shop_n.html?SPXX04="+escape(cpmc);
		}else if($searcht.val()=="oe"){
			var oe = ($mq.val()).trim();
			var url="/QFY/selectSPXXbyOE.action?oe="+oe;
			var data=visitService(url);
			if(data.STATE == 1){
				data=data.data;
				location.href="/customer/sydjt/showProduct/product.html?spxx01="+data.SPXX01+"&zcxx01="+data.ZCXX01+"&gsid="+data.ZCXX01+"&gsmc="+escape(data.ZCXX02);
			}else if(data.STATE == 2){
				var oe = ($mq.val()).trim();
				location.href="/customer/sydjt/showProduct/member_shop_n.html?spglljh01="+oe;
			}else{
				alert("该OEM商品暂无！");
			}
		}else if($searcht.val()=="tm") {
				//搜索条码
			var sptm = ($mq.val()).trim();
			var url="/QFY/selectSPXXbySPTM.action?sptm="+sptm;
			var data=visitService(url);
			if(data.STATE == 1){
				data=data.data;
				location.href="/customer/sydjt/showProduct/product.html?spxx01="+data.SPXX01+"&zcxx01="+data.ZCXX01+"&gsid="+data.ZCXX01+"&gsmc="+escape(data.ZCXX02);
			}else if(data.STATE == 2){
				var oe = ($mq.val()).trim();
				location.href="/customer/sydjt/showProduct/member_shop_n.html?barcode="+sptm;
			}else{
				alert("该条码商品暂无1！");
			}
		}
	 	
	 	return false;
    });
    // 查询条件
    $("#seach_tj").on("click","li",function(e){
        var $this = $(e.currentTarget),
            $searcht = $("#search-t"),
            $mq = $("#mq");
        $this.addClass("bg-ff3c3c").siblings("li").removeClass("bg-ff3c3c");

        var tjval = $this.text();
        switch(tjval){
            case "产品":
            	$mq.attr("placeholder","输入产品名称、商品条码、店铺名称等都可以直接搜索");
                $searcht.val("cp");
                break;
            case "OEM":
            	$mq.attr("placeholder","输入产品OEM码");
                $searcht.val("oe");
                break;
            case "商品条码":
            	$mq.attr("placeholder","请输入13位商品条码");
                $searcht.val("tm");
                break;
        }
    });
/*
    var oe=$.getUrlParam("spglljh01");
    if(oe!=""&&oe!=null){
        $("#seach_tj2").click();
        $("#sousuo").val(oe);
    }*/
    /* 搜索 --end-- */
    /*热门搜索*/
    $("#rmss").find("a").on("click",function(e){
		var cpmc = (this.innerHTML).trim();
		location.href="/customer/sydjt/showProduct/member_shop_n.html?SPXX04="+escape(cpmc);
    });
    
    /* 菜单 --start-- */
    $("#menu-nav").on("mouseenter","li.category-item", function (e) {
        var $this = $(e.currentTarget),
            $pannel = $this.find("div.ci-pannel"),
            index = $this.data("index"),
            isReady = $pannel.data("read"),
            tpl = $.trim($("#m-tpl-1").html()),
            xmlData=[],
            url="";
            
        
        if(!isReady || isReady === "0"){
        	if(index === 1){
        		// 车型车分类
        		xmlData = [{"sqlid":"com.jlsoft.o2o.pub.sql.SPFL.selectCXCX","dataType":"Json"}];
        		url = "/jlquery/selecto2o.action?XmlData="+JSON.stringify(xmlData);
        	}else{
        		xmlData = [{"sqlid":"com.jlsoft.o2o.pub.sql.SPFL.selectSPFL","dataType":"Json","SPFL05":"0"+(index-1)}];//,"SPF_SPFL01":""
        		url = "/jlquery/selecto2o.action?XmlData="+JSON.stringify(xmlData);
        		tpl = $.trim($("#m-tpl-8").html());
        	}
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
            				if(index !== 1){
            					mapArr[i] = {"name":rData[i]["spfl02"],"src":"/customer/sydjt/images/menu-icon/m-0"+(index-1),"href":"/customer/sydjt/showProduct/member_shop_n.html?spfl01="+rData[i]["spfl01"]};
            				}else{
            					mapArr[i] = {"name":rData[i]["origin_country"],"src":"/customer/sydjt/images/menu-icon/m-0"+(index-1),"href":"/customer/sydjt/showProduct/member_shop_n.html?cxpp="+rData[i]["origin_country"]};
            				}            				
            			}
            			data = {"mapList":mapArr};
	            		if(index === 1){
	            			dToHtml({"tpl":tpl,"data":data,"target":$pannel.find("ul")});
	            		}else{
	            			dToHtml({"tpl":tpl,"data":data,"target":$pannel.find("ul")});
	            		}
	                    
	                    $pannel.data("read","1");
	                    $pannel.fadeIn(10);
            		}            		
                },
                "error":function(){}
            });
        }else{

            $pannel.fadeIn(50);
        }
    }).on("mouseleave","li",function(e){
        var $this = $(e.currentTarget),
            $pannel = $this.find("div.ci-pannel");
        $pannel.fadeOut(100);
    });
    /* 菜单 --end-- */

    /* 轮播图 --start-- */
    //showSlider();
    /* 轮播图 --end-- */
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

/* 轮播图 --start-- */
function showSlider(){
    var xmlData=[{"size":4,"gsid":"0000"}];
    var url="/HomePage/selectPictureMainPage.action?XmlData="+JSON.stringify(xmlData);
    /***
    ajaxQ({
        "url" : url,
        "callback" : function(rData){
            var len = !!rData["mapList"] ? rData["mapList"].length : 0,
                cmsSlArr = {"mapList":[]},
                i;alert("len "+len)
            if(len <= 0){
                return;
            }alert(2)
            dToHtml({"tpl":$("#m-tpl-3").html(),"data":rData,"target":"#cms-box"});
alert(3)
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
                    var eleLazyLoad = target.find("img, iframe").get(0), index = target.data("index");
                    if (eleLazyLoad && !eleLazyLoad.src) {
                        eleLazyLoad.src = eleLazyLoad.getAttribute("data-src");
                    }
                }
            });

        },
        "error":function(){}
    });
    ****/
    var rData = visitService(url);
    var str="";
    $(rData.mapList).each(function(index,item){
    	str += "<li style=\"background:url(/jlsoft/banner/"+item.FILENAME+">) no-repeat center top\"><a href='"+item.URL+"'></a></li>";

    });    alert(str)
    $("#cms-box").append(str);
    
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
    var lcmc = flParas["lcmc"];
    var lcxh = flParas["lcxh"];
    lcxh = parseInt(lcxh)+1;
	$(flParas["lcmcTarget"]).html('<i class="fa fa-sun-o"></i><span>'+lcxh+'F</span>'+lcmc);
    
    lcJson.LCFL01=flParas["lcfl"];
    xmlData.push(lcJson);

    var url="/QFY/selectFloorGoodsNew.action?XmlData="+JSON.stringify(xmlData);

    ajaxQ({
        "url" : url,
        "callback" : function(rData){
	    	console.log(rData);
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
	    		i;
	    	if(rDataMaplist && rDataMaplist.length > 0){
	    		len = rDataMaplist.length;
	    		for(i=startIndex;i<len;i++){
	    			goodInfo = rDataMaplist[i-startIndex];
	    			gname = goodInfo["SPMC"];
	    			cname = goodInfo["ZCXX02"];
	    			if (usercookie!=null) {
	    				if(usercookie.LX=='24'){
	    					SPJGB05 = goodInfo["SPJGB02"];
	    				}else{
	    					SPJGB05 = goodInfo["SPJGB05"];
	    				}
	    			}else{
	    				SPJGB05 = goodInfo["SPJGB02"];
	    			}
	    			shortGName = gname.length > 18? gname.substring(0,18)+"...":gname;
	    			shortCName = cname.length > 18? cname.substring(0,18)+"...":cname;
	    			mapList[i-startIndex] = {
	    					"src":getFlImgSrc(goodInfo),
	    					"href":"/customer/sydjt/showProduct/proItem.html?sxsp01="+goodInfo["SXSP01"]+"&spxx01="+goodInfo["SPXX01"]+"&zcxx01="+goodInfo["ZCXX01"]+"&gsid="+goodInfo["ZCXX01"]+"&gsmc="+escape(goodInfo["ZCXX02"]),
	    					"gname":gname,
	    					"shortGName":shortGName,
	    					"cname":cname,
	    					"SPJGB05":SPJGB05,
	    					"shortCName":shortCName};
	    		}
	    		
	    		dToHtml({"tpl":flParas["tpl"],"data":data,"target":flParas["target"]});
	    	}
	    	if(startIndex === 0){
	    		/*goodInfo = rDataMaplist[0];
	    		gname = goodInfo["SPMC"];
	    		$("#fl-tz").find("a.fl-1-goods").attr("href","/customer/sydjt/showProduct/product.html?spxx01="+goodInfo["SPXX01"]+"&zcxx01="+goodInfo["ZCXX01"]+"&gsid="+goodInfo["ZCXX01"]+"&gsmc="+escape(goodInfo["ZCXX02"]));
	    		$("#fl-link01").append('<img src="" data-src="'+getFlImgSrc(goodInfo)+'" class="la" alt=""/>');*/
	    		$(".grid-col").find("img").lazyload({
	    	        data_attribute:"src",
	    	        effect:"fadeIn",
	    	        placeholder:"../../images/s.gif"
	    	    });
	    		var temp ="#"+flParas["target"];
	    		$(temp).find("img").lazyload({
	    	        data_attribute:"src",
	    	        effect:"fadeIn",
	    	        placeholder:"../../images/s.gif"
	    	    });
	    		$("#fl-st-grid").find("img").lazyload({
	    	        data_attribute:"src",
	    	        effect:"fadeIn",
	    	        placeholder:"../../images/s.gif"
	    	    });
	    	}else if(startIndex === 2){/****
	    		goodInfo = rDataMaplist[8];
	    		gname = goodInfo["SPMC"];
	    		if (usercookie!=null) {
    				if(usercookie.LX=='24'){
    					SPJGB05 = goodInfo["SPJGB02"];
    				}else{
    					SPJGB05 = goodInfo["SPJGB05"];
    				}
    			}else{
    				SPJGB05 = goodInfo["SPJGB02"];
    			}
    			shortGName = gname.length > 10? gname.substring(0,10)+"...":gname;
    			shortCName = cname.length > 10? cname.substring(0,10)+"...":cname;
    			$($("#fl-qp").find("a.grid-row-2")[0]).attr("href","/customer/sydjt/showProduct/product.html?spxx01="+goodInfo["SPXX01"]+"&zcxx01="+goodInfo["ZCXX01"]+"&gsid="+goodInfo["ZCXX01"]+"&gsmc="+escape(goodInfo["ZCXX02"]));
	    		$("#fl-link02").append('<img src="" data-src="'+getFlImgSrc(goodInfo)+'" class="la" alt=""/><div class="good-info">'+
						'<p class="good-name" title="'+gname+'">'+shortGName+'</p>'+
	    				'<p class="good-company" title="'+cname+'">'+shortCName+'</p>'+
	    				'<p class="good-price">￥:'+SPJGB05+'</p>'+
	  				'</div>');
	    		goodInfo = rDataMaplist[9];
	    		gname = goodInfo["SPMC"];
	    		if (usercookie!=null) {
    				if(usercookie.LX=='24'){
    					SPJGB05 = goodInfo["SPJGB02"];
    				}else{
    					SPJGB05 = goodInfo["SPJGB05"];
    				}
    			}else{
    				SPJGB05 = goodInfo["SPJGB02"];
    			}
	    		shortGName = gname.length > 10? gname.substring(0,10)+"...":gname;
    			shortCName = cname.length > 10? cname.substring(0,10)+"...":cname;
	    		$($("#fl-qp").find("a.grid-row-2")[1]).attr("href","/customer/sydjt/showProduct/product.html?spxx01="+goodInfo["SPXX01"]+"&zcxx01="+goodInfo["ZCXX01"]+"&gsid="+goodInfo["ZCXX01"]+"&gsmc="+escape(goodInfo["ZCXX02"]));
	    		$("#fl-link03").append('<img src="" data-src="'+getFlImgSrc(goodInfo)+'" class="la" alt=""/><div class="good-info">'+
	    				'<p class="good-name" title="'+gname+'">'+shortGName+'</p>'+
	    				'<p class="good-company" title="'+cname+'">'+shortCName+'</p>'+
	    				'<p class="good-price" >￥:'+SPJGB05+'</p>'+
	  				'</div>');****/
	    		$("#fl-qp-grid").find("img").lazyload({
	    	        data_attribute:"src",
	    	        effect:"fadeIn",
	    	        placeholder:"../../images/s.gif"
	    	    });
	    	}
        },
        "error":function(){}
    });
}
/* 楼层 --end-- */

/* 直通车 --start-- */
showZtc();
function showZtc(){
    var functionName = "loadMoreNews";
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
    	        placeholder:"../../images/s.gif"
    	    });
        },
        "error":function(){}
    });
}
/* 直通车 --end-- */

/* 通过数据构建HTML */
function dToHtml(obj){
    var tpl = $.trim(obj["tpl"]),
        datas = obj["data"],
        fc =  _.template(tpl),
        rh = fc(datas);
    var target = typeof obj["target"] === "string"?$(obj["target"]):obj["target"];
    target.html(rh);
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
