var splist_1 = {};
splist_1.data = {};
splist_1.write = function(){
	//alert(JSON.stringify(splist_1.data));
	var spObj = $("#z1").find(".pro-case");
	spObj.empty();
	$(splist_1.data).each(function(j,lcsp){
		if(j%4 == 1) {
			spObj.append('<li class="first"><a class="img"><img src=""/></a><div class="pro-name"><a href=""></a></div><div class="pro-pic"><span>会员价：</span><b></b></div><div class="pro-dp"><div class="wzmrz" title="位置码已认证"></div><a class="dp" href=""></a><a class="dq"></a></div></li>');
		}else {
			spObj.append('<li><a class="img"><img src=""/></a><div class="pro-name"><a href=""></a></div><div class="pro-pic"><span>会员价：</span><b></b></div><div class="pro-dp"><div class="wzmrz" title="位置码已认证"></div><a class="dp" href=""></a><a class="dq"></a></div></li>');
		}
		//楼层商品图片路径
		var lcspUrl=imgUrl+lcsp.ZCXX01+"/"+lcsp.SPXX02+"/images/"+lcsp.SPXX02+"_1_big.jpg";
		//商品图片和链接
		spObj.find("li").eq(j).find("a").first().attr("href","/gui_o2o/qfy/fore/product.html?spxx01="+lcsp.SPXX01+"&zcxx01="+lcsp.ZCXX01+"&gsid="+lcsp.ZCXX01+"&gsmc="+escape(lcsp.ZCXX02)).find("img").attr("src",lcspUrl).attr("onerror","this.onerror=null;this.src='"+defaultImgUrl+"'");
		//商品名称和链接
		spObj.find("li").eq(j).find("div.pro-name").find("a").attr("href","/gui_o2o/qfy/fore/product.html?spxx01="+lcsp.SPXX01+"&zcxx01="+lcsp.ZCXX01+"&gsid="+lcsp.ZCXX01+"&gsmc="+escape(lcsp.ZCXX02)).html(lcsp.SPXX04);
	
		if(usercookie!=null) {
			//商品零售价
			spObj.find("li").eq(j).find("div.pro-pic").eq(0).find("b").html(accounting.formatMoney(lcsp.SPJGB05));
		} else {
			spObj.find("li").eq(j).find("div.pro-pic").eq(0).find("b").html("登陆可见");						
		}
		
		//购买链接
		spObj.find("li").eq(j).find("div.pro-rz").eq(0).find("a.cat").attr("href","/gui_o2o/qfy/fore/product.html?spxx01="+lcsp.SPXX01+"&zcxx01="+lcsp.ZCXX01+"&gsid="+lcsp.ZCXX01+"&gsmc="+escape(lcsp.ZCXX02));
		//店铺名称和链接
		spObj.find("li").eq(j).find("div.pro-dp").find("a.dp").attr("href","/gui_o2o/qfy/fore/qfy_shop.html?gsid="+lcsp.ZCXX01+"&gsmc="+escape(lcsp.ZCXX02)).html(lcsp.ZCXX02);
	});
	
	$("img").error(function(){
		    $(this).attr("src","../images/spdetail/default/images/image.jpg");
	});	
}

//调用后台
function SELECTmethod(sppl,fourCODE,sixCODE,prista,priend,sppx,SPNAMELIST,SXZDM,MOBILE_MODLE_ID){
	//登入前
	//if(MyCookie==null){	
		$("#splists").empty();
		var para=[];
		var paraJson={};
		paraJson.sppl=sppl;
		paraJson.fourCODE=fourCODE;
		paraJson.sixCODE=sixCODE;
		paraJson.prista=prista;
		paraJson.priend=priend;
		paraJson.pagesize="12";
		paraJson.sppx=sppx;
		paraJson.SPGL02="-1";//明珠B2B
		var len2=SXZDM.length;
		paraJson.SPNAMELIST=SPNAMELIST;
		paraJson.MOBILE_MODLE_ID=MOBILE_MODLE_ID;
		if(SXZDM!=undefined&&SXZDM!=null&&SXZDM!=''&&len2>0){
			paraJson.SXZDM=SXZDM;			
		}			
		para.push(paraJson);
		var url=baseUrl+"/PCdisplay/jlquery/selectHYSPnotloginPX.action?XmlData="+JSON.stringify(para);
		var rData0 = "";
		$.ajax({
		    type: "GET",  //请求方式
		    async: false,   //true表示异步 false表示同步
		    url:encodeURI(url),
		    data:{},
		    success: function(data){
		      if (data != null){
		        try{
		            var json = jQuery.parseJSON(data);
		            rData0 = json.data;
		            fileName = json.fileName;
		            var spObj = $("#z1").find(".pro-case");
		            spObj.empty();
		            splist_1.data=rData0;
		            $(splist_1.data).each(function(j,lcsp){
		            	if(j%4 == 1) {
		            		spObj.append('<li class="first jl"><a class="img"><img src=""/></a><div class="pro-name"><a href=""></a></div><div class="pro-pic"><span>会员价：</span><b></b></div><div class="pro-dp"><div class="wzmrz" title="位置码已认证"></div><a class="dp" href=""></a><a class="dq"></a></div></li>');
		            	}else {
		            		spObj.append('<li class="jl"><a class="img"><img src=""/></a><div class="pro-name"><a href=""></a></div><div class="pro-pic"><span>会员价：</span><b></b></div><div class="pro-dp"><div class="wzmrz" title="位置码已认证"></div><a class="dp" href=""></a><a class="dq"></a></div></li>');
		            	}
		            	//楼层商品图片路径
		            	var lcspUrl=imgUrl+lcsp.ZCXX01+"/"+lcsp.SPXX02+"/images/"+lcsp.SPXX02+"_1_big.jpg";
		            	//商品图片和链接
		            	spObj.find("li").eq(j).find("a").first().attr("href","/customer/qfy/showProduct/product.html?spxx01="+lcsp.SPXX01+"&zcxx01="+lcsp.ZCXX01+"&gsid="+lcsp.ZCXX01+"&gsmc="+escape(lcsp.ZCXX02)).find("img").attr("src",lcspUrl).attr("onerror","this.onerror=null;this.src='"+defaultImgUrl+"'");
		            	//商品名称和链接
		            	spObj.find("li").eq(j).find("div.pro-name").find("a").attr("href","/customer/qfy/showProduct/product.html?spxx01="+lcsp.SPXX01+"&zcxx01="+lcsp.ZCXX01+"&gsid="+lcsp.ZCXX01+"&gsmc="+escape(lcsp.ZCXX02)).html(lcsp.SPXX04);

		            	if(usercookie!=null) {
							if(usercookie.LX=="43"&&usercookie.ZCXX01!=lcsp.ZCXX01){
								spObj.find("li").eq(j).find("div.pro-pic").eq(0).find("b").html("价格不可见");
								}else{
								//商品零售价
								spObj.find("li").eq(j).find("div.pro-pic").eq(0).find("b").html(accounting.formatMoney(lcsp.SPJGB05));
									}
		            	} else {
		            		spObj.find("li").eq(j).find("div.pro-pic").eq(0).find("b").html("登陆可见");						
		            	}
		            	
		            	//购买链接
		            	spObj.find("li").eq(j).find("div.pro-rz").eq(0).find("a.cat").attr("href","/customer/qfy/showProduct/product.html?spxx01="+lcsp.SPXX01+"&zcxx01="+lcsp.ZCXX01+"&gsid="+lcsp.ZCXX01+"&gsmc="+escape(lcsp.ZCXX02));
		            	//店铺名称和链接
		            	spObj.find("li").eq(j).find("div.pro-dp").find("a.dp").attr("href","/customer/qfy/showDpxx/shopAllGoods.html?gsid="+lcsp.ZCXX01+"&gsmc="+escape(lcsp.ZCXX02)).html(lcsp.ZCXX02);
		            });
		    	    $(".Pagination").remove();
		    		$('.center').kkPages({ 
		    	 	   PagesClass:'li.jl', //需要分页的元素
		    	 	   PagesMth:8, //每页显示个数 
		    	 	   PagesNavMth:pubJson.PagesNavMth //显示导航个数
		    	 	});
		            $("img").error(function(){
		            	    $(this).attr("src","../images/image.jpg");
		            });	
		        }catch(e){
		          return;
		        }
		      }
		    },
	    	error:function(XMLHttpRequest, textStatus, errorThrown) {
		    	alert("获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest+"+"+errorThrown);
	        }
		});
		
}
DefStaPer.init = function(o) {
	
}