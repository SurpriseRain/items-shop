var uinfo = $.cookie('userInfo');
var usercookie = JSON.parse(uinfo);
var city_list = $.cookie("city_list");
var search = $.getUrlParam("search");
var returnData;
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
	showFloorsbuying({ "lcfl":"3,8", "tpl":$("#sold-tpl-0").html(),"target":".sold .row ul" ,"type":1});
    /* 菜单 --start-- */
    $("#menu-nav").on("mouseenter",".pro_nav_list .w12", function (e) {
		/*加载菜单*/
		var url = "/showGoods/selectFL.action";
		var data=visitService(url);
		returnData = data.fl;
		//alert(JSON.stringify(returnData));
        var $this = $(e.currentTarget),
            $pannel = $("#menu-nav").find(".pro_nav_case"),
            index = $this.data("index"),
            isReady = $this.data("read"),//$pannel.data("read"),
            tpl = $.trim($("#m-tpl-1").html()),
            xmlData=[],
            url="";
        
        //alert(index);
        //alert(isReady);
        
       
            // 取数据
            // 填充
        	var temp={};
        	for(var i=0;i<returnData.length;i++){
        		temp=returnData[i];
        		if(index==i){
        			data = {"mapList":temp};
        			//alert("###"+JSON.stringify(temp));
        			dToHtml({"tpl":tpl,"data":data,"target":$pannel.find(".w09")});
        			$this.data("read","1");
        			 if(!isReady || isReady === "0"){
        				 //$pannel.fadeIn(10);
        			 }else{
        		            //$pannel.fadeIn(50);
        		     }
        		}
        	}
		
            	
    }).on("mouseleave",".pro_nav_list .w12",function(e){
        var $this = $(e.currentTarget),
        $pannel = $("#menu-nav").find(".pro_nav_case");
        //$("#menu-nav").find(".pro_nav_case").addClass("display_none");
        //$pannel.fadeOut(100);
    });
    /* 菜单 --end-- */

    /* 轮播图 --start-- */
 
    /* 轮播图 --end-- */
    var html_title = $("title").text();
	if(html_title == "电器服务云平台"){
		$(".pro_nav > i").attr("class","fa fa-chevron-down display_none");
	}else{
		$(".pro_nav > i").attr("class","fa fa-chevron-down display_block");
		$(".pro_nav_main").attr("class","w12 pro_nav_main display_none");
        $("#pro_nav_main").mouseover(function(){
          $(".pro_nav > i").attr("class","fa fa-chevron-up display_block");
	      $(".pro_nav_main").attr("class","w12 pro_nav_main display_block");
       })
        $("#pro_nav_main").mouseleave(function(){
	     $(".pro_nav > i").attr("class","fa fa-chevron-down display_block");
     	 $(".pro_nav_main").attr("class","w12 pro_nav_main display_none");
      })
	}
	$("#mq").val(search);	
	 /* 搜索 --start-- */
    if($("#mq").val()!="null" && $("#mq").val()!='') {
    	 $("#mq").val(unescape(search));
		 $("#search").click();
    }
});


/* 轮播图 --end--*/
/* 活动楼层 --start-- */
function showFloorsbuying(flParas){
    var xmlData=[];
    var lcJson={};
    lcJson.ZTFB01=1;
    lcJson.HBID="";
    if (usercookie!=null) {
        lcJson.HBID=usercookie.ZCXX01;
    }
    lcJson.LCFL01=flParas["lcfl"];
    xmlData.push(lcJson);

    var url="/QFY/selectBuyingGoods.action?XmlData="+JSON.stringify(xmlData);

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
	    			SPJGB06 = goodInfo["SPJGB06"];
	    			shortGName = gname.length > 15? gname.substring(0,15)+"...":gname;
	    			shortCName = cname.length > 15? cname.substring(0,15)+"...":cname;
	    			mapList[i-startIndex] = {
	    					"src":getFlImgSrc(goodInfo),
	    					"href":"/customer/sydjt/showProduct/product.html?spxx01="+goodInfo["SPXX01"]+"&zcxx01="+goodInfo["ZCXX01"]+"&gsid="+goodInfo["ZCXX01"]+"&gsmc="+escape(goodInfo["ZCXX02"]),
	    					"gname":gname,
	    					"shortGName":shortGName,
	    					"cname":cname,
	    					"SPJGB05":SPJGB05,
	    					"SPJGB06":SPJGB06,
	    					"shortCName":shortCName};
	    		}
	    		
	    		dToHtml({"tpl":flParas["tpl"],"data":data,"target":flParas["target"]});
	    	}
	    	if(startIndex === 0){
	    		/*goodInfo = rDataMaplist[0];
	    		gname = goodInfo["SPMC"];
	    		$("#fl-tz").find("a.fl-1-goods").attr("href","/customer/sydjt/showProduct/product.html?spxx01="+goodInfo["SPXX01"]+"&zcxx01="+goodInfo["ZCXX01"]+"&gsid="+goodInfo["ZCXX01"]+"&gsmc="+escape(goodInfo["ZCXX02"]));
	    		$("#fl-link01").append('<img src="" data-src="'+getFlImgSrc(goodInfo)+'" class="la" alt=""/>');*/
	    		$(flParas["target"]).find("img").lazyload({
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
	    		$(flParas["target"]).find("img").lazyload({
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


/* 通过数据构建HTML */
function dToHtml(obj){
    var tpl = $.trim(obj["tpl"]),
        datas = obj["data"],
        fc =  _.template(tpl),
        rh = fc(datas);
    var target = typeof obj["target"] === "string"?$(obj["target"]):obj["target"];
    target.html(rh);
}
