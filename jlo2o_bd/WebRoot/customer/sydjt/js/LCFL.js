var uinfo = $.cookie('userInfo');
var usercookie = JSON.parse(uinfo);
var city_list = $.cookie("city_list");
var LCFL = JL.JLForm(); 
LCFL.setPlugin({});
LCFL.setEvents(function() {
	LCFL.getTab().delegate("[name='date_s']", "click", function(event) {
		WdatePicker();
	});
	LCFL.getTab().delegate("[name='date_e']", "click", function(event) {
		WdatePicker();
	});
	LCFL.getTab().delegate("[name='seach']", "click", function(event) {
		/*DHD.resetDHD();
			var KHDZ = DHD.getPlugin()["KHDZ"];
			KHDZ["param"] = {"WLDW01":DHD.getTab().find("[name='WLDW01']").val()};
			DHD.setPlugin({"KHDZ":KHDZ});
			DHD.reloadPlugin("KHDZ", KHDZ);
			DHD01.removeAttr("disabled");
			$("input[name='CZY01']").val(userInfo.RYMC);
			*/
	});
});
LCFL.setEvents(function() {
	LCFL.getTab().delegate("[name='date_s']", "click", function(event) {
		WdatePicker();
	});
	LCFL.getTab().delegate("[name='date_e']", "click", function(event) {
		WdatePicker();
	});
});
LCFL.define({
	"initData":function(){
		//alert("地区######"+$("#city_value").val());
		var XmlData=[];
		var json={};
		
		json["DQXX01"]=$("#city_value").val()==undefined?"12":$("#city_value").val();//$("#city_value").val();
		json["ZCXX01"]='0000';
		json["GGLX01"]=$("#floor").data("gglx");
		XmlData.push(json);
		//alert(JSON.stringify(XmlData));
	    //加载楼层数据
		var url="/oper_floor/selectGGLXFloor.action?XmlData="+JSON.stringify(XmlData);
		var result = visitService(url);
		$(".floor_nav_main").empty();
		$("#floor").empty();		
		$(result.mapList).each(function(index,obj){
			$(".floor_nav").append('<div class="floor_nav_main"><dl>'+
				    '<dt class="w12">'+obj.GGLXZ03+'F</dt>'+
				    '<input style="display:none;" value="'+obj.LCFL01+'"></input>'+
				    '<dd class="w12">'+obj.LCFL02.substring(0,2)+'</dd>'+
				  '</dl></div>');
			$("#floor").append('<div class="w12 floor" id="floor_'+obj.LCFL01+'" data-index="'+index+'" data-data='+JSON.stringify(json)+'><!---floor截点---></div> ')
			$("#floor_"+obj.LCFL01).load("/customer/sydjt/floor.html",function(){
				//加载楼层大图
				LCFL.showFloorsDT({ "lcfl":obj.LCFL01,"lcxh":index,"lcmc":obj.LCFL02,"lcmcTarget":"#floor_"+obj.LCFL01+" h3", "tpl":$("#m-tpl-4-1").html(),"target":"#floor_"+obj.LCFL01+" .floor_main .floor_adve" ,"type":1});
				//加载楼层
			    LCFL.showFloors({ "lcfl":obj.LCFL01,"lcxh":index,"lcmc":obj.LCFL02,"lcmcTarget":"#floor_"+obj.LCFL01+" h3", "tpl":$("#m-tpl-4").html(),"target":"#floor_"+obj.LCFL01+" .floor_main ul" ,"type":1});
			});
			
		});
	},"showFloorsDT":function(flParas){
		var xmlData=[];
	    var lcJson=$("#floor_"+flParas["lcfl"]).data("data");
	    //lcJson["DQXX01"]=$("#city_value").val();
	    lcJson.ZTFB01=1;
	    lcJson.HBID="";
	    lcJson.LCFL01=flParas["lcfl"];
	    if (usercookie!=null) {
	        lcJson.HBID=usercookie.ZCXX01;
	    }
	    var lcmc = flParas["lcmc"];
	    var lcxh = flParas["lcxh"];
	    lcxh = parseInt(lcxh)+1;
		$(flParas["lcmcTarget"]).html('<i class="fa fa-sun-o"></i><span>'+lcxh+'F</span>'+lcmc);
	    xmlData.push(lcJson);
	    //alert(JSON.stringify(xmlData));
	    var url="/QFY/selectFloorGoodsDT.action?XmlData="+JSON.stringify(xmlData);

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
		    			//cname = goodInfo["ZCXX02"];
		    			SPJGB05 = goodInfo["SPJG"];
		    			
		    			shortGName = gname.length > 18? gname.substring(0,18)+"...":gname;
		    			shortCName = cname.length > 18? cname.substring(0,18)+"...":cname;
		    			mapList[i-startIndex] = {
		    					"src":LCFL.getFlImgSrcNew(goodInfo),
		    					"href":goodInfo["URL"],
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
	},
	"showFloors":function(flParas){
		var xmlData=[];
	    var lcJson=$("#floor_"+flParas["lcfl"]).data("data");
	    //lcJson["DQXX01"]=//$("#city_value").val();
	    lcJson.ZTFB01=1;
	    lcJson.HBID="";
	    lcJson.LCFL01=flParas["lcfl"];
	    if (usercookie!=null) {
	        lcJson.HBID=usercookie.ZCXX01;
	    }
	    var lcmc = flParas["lcmc"];
	    var lcxh = flParas["lcxh"];
	    lcxh = parseInt(lcxh)+1;
		$(flParas["lcmcTarget"]).html('<i class="fa fa-sun-o"></i><span>'+lcxh+'F</span>'+lcmc);
	    xmlData.push(lcJson);
	    //alert(JSON.stringify(xmlData));
	    var url="/QFY/selectFloorGoodsNew.action?XmlData="+JSON.stringify(xmlData);

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
		    			//cname = goodInfo["ZCXX02"];
		    			SPJGB05 = goodInfo["SPJG"];
		    			
		    			shortGName = gname.length > 18? gname.substring(0,18)+"...":gname;
		    			shortCName = cname.length > 18? cname.substring(0,18)+"...":cname;
		    			mapList[i-startIndex] = {
		    					"src":LCFL.getFlImgSrcNew(goodInfo),
		    					"href":goodInfo["URL"],
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
	},
	"getFlImgSrcNew":function(item){
		// 图片没有的时候 展示默认图片
		var imgUrl=pubJson.path_sptp+"/ggtp/";
		return imgUrl+item.ZCXX01+"/"+item.SPXX02+"/images/"+item.SPXX02+"_big."+(item.SPTP02 || ".jpg").split(".")[1];
	}
});

LCFL.initData();
//cookie误删时，存放默认值

/*$(function(){
	
	var XmlData=[];
	var json={};
	json["DQXX01"]="1201"
	json["ZCXX01"]='0000';
	json["GGLX01"]='02';
	XmlData.push(json);
    //加载楼层数据
	var url="/oper_floor/selectGGLXFloor.action?XmlData="+JSON.stringify(XmlData);
	var result = visitService(url);
	$(result.mapList).each(function(index,obj){
	    showFloors1({ "lcfl":obj.LCFL01,"lcxh":index,"lcmc":obj.LCFL02,"lcmcTarget":"#floor_"+obj.LCFL01+" h3", "tpl":$("#m-tpl-4").html(),"target":"#floor_"+obj.LCFL01+" .floor_main ul" ,"type":1},json);
	});
});
*/
   
   

/* 楼层 --start-- */

function showFloors1(flParas){
    var xmlData=[];
    var lcJson=$("#floor_"+flParas["lcfl"]).data("data");
    lcJson.ZTFB01=1;
    lcJson.HBID="";
    lcJson.LCFL01=flParas["lcfl"];
    if (usercookie!=null) {
        lcJson.HBID=usercookie.ZCXX01;
    }
    var lcmc = flParas["lcmc"];
    var lcxh = flParas["lcxh"];
    lcxh = parseInt(lcxh)+1;
	$(flParas["lcmcTarget"]).html('<i class="fa fa-sun-o"></i><span>'+lcxh+'F</span>'+lcmc);
    xmlData.push(lcJson);
   
    var url="/QFY/selectFloorGoodsNew.action?XmlData="+JSON.stringify(xmlData);

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
	    			//cname = goodInfo["ZCXX02"];
	    			SPJGB05 = goodInfo["SPJG"];
	    			
	    			shortGName = gname.length > 18? gname.substring(0,18)+"...":gname;
	    			shortCName = cname.length > 18? cname.substring(0,18)+"...":cname;
	    			mapList[i-startIndex] = {
	    					"src":getFlImgSrcNew(goodInfo),
	    					"href":goodInfo["URL"],
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


/* 楼层 */
/*function getFlImgSrcNew(item){
	// 图片没有的时候 展示默认图片
	var imgUrl=pubJson.path_sptp+"/ggtp/";
	return imgUrl+item.ZCXX01+"/"+item.SPXX02+"/images/"+item.SPXX02+"_1_big."+(item.SPTP02 || ".jpg").split(".")[1];
}*/

