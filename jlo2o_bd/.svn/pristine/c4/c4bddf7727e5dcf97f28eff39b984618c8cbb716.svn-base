jQuery(xy  = function($){//alert(1);alert(GWCDATA)
	if(!GWCDATA || GWCDATA.GWCLIST == "" || GWCDATA.GWCLIST == null) {//alert(5)
        $("#shopping-amount").html(0);
    }else {//alert(4);alert($("#right-shopping-amount").html())
       $("#right-shopping-amount").html(GWCDATA.GWCLIST[0].TOTALNUM);//alert(3)

    }
	//创建DOM
	var 
	quickHTML = document.querySelector("div.quick_link_mian"),
	quickShell = $(document.createElement('div')).html(quickHTML).addClass('quick_links_wrap'),
	quickLinks = quickShell.find('.quick_links');
	quickPanel = quickLinks.next();
	quickShell.appendTo('.mui-mbar-tabs');//alert(2)
	
	//具体数据操作 
	var 
	quickPopXHR,
	loadingTmpl = '<div class="loading" style="padding:30px 80px"><i></i><span>Loading...</span></div>',
	popTmpl = '<a href="javascript:;" class="ibar_closebtn" title="关闭"></a><div class="ibar_plugin_title"><h3><%=title%></h3></div><div class="pop_panel"><%=content%></div><div class="arrow"><i></i></div><div class="fix_bg"></div>',
	//historyListTmpl = '<ul><%for(var i=0,len=items.length; i<5&&i<len; i++){%><li><a href="<%=items[i].productUrl%>" target="_blank" class="pic"><img alt="<%=items[i].productName%>" src="<%=items[i].productImage%>" width="60" height="60"/></a><a href="<%=items[i].productUrl%>" title="<%=items[i].productName%>" target="_blank" class="tit"><%=items[i].productName%></a><div class="price" title="单价"><em>&yen;<%=items[i].productPrice%></em></div></li><%}%></ul>',
	historyListTmpl = '<ul>';
	var imgUrl=pubJson.path_sptp+"/sptp/";
	$(GWCDATA.GWCLIST[0].SPITEMLIST).each(function(index,spxxjson){//alert(JSON.stringify(spxxjson))
		var picUrl="";
		if(spxxjson.SPTP02==null){//为null时候后面商品不显示了.
				picUrl=pubJson.defaultImgUrl;
		}else{ 
	        	picUrl=imgUrl+spxxjson.ZTID+"/"+spxxjson.SPXX02+"/images/"+spxxjson.SPXX02+"_1_big."+(spxxjson.SPTP02).split(".")[1];
		}
		historyListTmpl +='<li class="cart_item"><div class="cart_item_pic"><a href="/customer/sydjt/showProduct/product.html?spxx01='+spxxjson.SPXX01+'&zcxx01='+spxxjson.ZTID+'&gsid='+spxxjson.ZTID+'&gsmc='+escape(GWCDATA.GWCLIST[0].ZCXX02)+'"><img src="'+picUrl+'" onerror=this.onerror=null;this.src="/customer/sydjt/images/msimg.jpg" /></a></div><div class="cart_item_desc"><a href="/customer/sydjt/showProduct/product.html?spxx01='+spxxjson.SPXX01+'&zcxx01='+spxxjson.ZTID+'&gsid='+spxxjson.ZTID+'&gsmc='+escape(GWCDATA.GWCLIST[0].ZCXX02)+'" class="cart_item_name">'+spxxjson.SPXX04+'</a><div class="cart_item_sku"></div><div class="cart_item_price"><span class="cart_price">'+accounting.formatMoney(spxxjson.GWC02)+'</span><span class="cart_gs">x'+spxxjson.GWC01+'</span></div></div></li>';
	});
	historyListTmpl +='</ul>';
	historyListTmpl +='</div><div class="cart_handler"><div class="cart_handler_header"><span class="cart_handler_left">共<span class="cart_price">'+GWCDATA.GWCLIST[0].TOTALNUM+'</span>件商品</span><span class="cart_handler_right">'+GWCDATA.GWCLIST[0].TOTALAMOUNT+'</span></div>';
	
	var 
	quickPop = quickShell.find('#quick_links_pop'),
	quickDataFns = {
		//购物信息
		message_list: {
			title: '购物车',
			content: '<div class="ibar_plugin_content"><div class="ibar_cart_group ibar_cart_product">'+historyListTmpl+'<a href="/customer/sydjt/shopping/cart.html" class="cart_go_btn" target="_blank">去购物车结算</a></div></div>',
			init:$.noop
		},
		
		//我的资产
		history_list: {
			title: '我的资产',
			content: '<div class="ibar_plugin_content"><div class="ia-head-list"><a href="#" target="_blank" class="pl"><div class="num">0</div><div class="text">优惠券</div></a><a href="#" target="_blank" class="pl"><div class="num">0</div><div class="text">红包</div></a><a href="#" target="_blank" class="pl money"><div class="num">￥0</div><div class="text">余额</div></a></div><div class="ga-expiredsoon"><div class="es-head">即将过期优惠券</div><div class="ia-none">您还没有可用的现金券哦！</div></div><div class="ga-expiredsoon"><div class="es-head">即将过期红包</div><div class="ia-none">您还没有可用的红包哦！</div></div></div>			',
			init: $.noop
		},
		//给客服留言
		leave_message: {
			title: '我关注的产品',
			content: $("#ibar_gzcp").html(),
			init:$.noop
		},
		mpbtn_histroy:{
			title: '我的足迹',
			content:'<div class="ibar_plugin_content"><div class="ibar-history-head">共3件产品<a href="#">清空</a></div><div class="ibar-moudle-product"><div class="imp_item"><a href="#" class="pic"><img src="../images/xiez.jpg" width="100" height="100" /></a><p class="tit"><a href="#">夏季透气真皮豆豆鞋反绒男士休闲鞋韩</a></p><p class="price"><em>￥</em>649.00</p><a href="#" class="imp-addCart">加入购物车</a></div><div class="imp_item"><a href="#" class="pic"><img src="../images/xiez.jpg" width="100" height="100" /></a><p class="tit"><a href="#">夏季透气真皮豆豆鞋反绒男士休闲鞋韩</a></p><p class="price"><em>￥</em>649.00</p><a href="#" class="imp-addCart">加入购物车</a></div><div class="imp_item"><a href="#" class="pic"><img src="../images/xiez.jpg" width="100" height="100" /></a><p class="tit"><a href="#">夏季透气真皮豆豆鞋反绒男士休闲鞋韩</a></p><p class="price"><em>￥</em>649.00</p><a href="#" class="imp-addCart">加入购物车</a></div></div></div>',
			init: $.noop
		},
		mpbtn_wdsc:{
			title: '收藏的产品',
			content: '<div class="ibar_plugin_content"><div class="ibar_cart_group ibar_cart_product"><ul class="spec"><li class="cart_item"><div class="cart_item_pic"><a href="#"><img src="../images/xiez.jpg" /></a></div><div class="cart_item_desc"><a href="#" class="cart_item_name">夏季透气真皮豆豆鞋反绒男士休闲鞋韩版磨砂驾车鞋英伦船鞋男鞋子</a><div class="cart_item_sku"><span>尺码：38码</span></div><div class="cart_item_price"><span class="cart_price">￥700.00</span><span class="cart_gs">x1</span></div></div>	</li></ul></div><div class="cart_handler"><a href="../cart/cart.html" class="cart_go_btn jiaru" target="_blank">加入购物车</a></div></div>',
			init: $.noop
		},
	};
	
	//showQuickPop
	var 
	prevPopType,
	prevTrigger,
	doc = $(document),
	popDisplayed = false,
	hideQuickPop = function(){
		if(prevTrigger){
			prevTrigger.removeClass('current');
		}
		popDisplayed = false;
		prevPopType = '';
		quickPop.hide();
		quickPop.animate({queue:true});
	},
	showQuickPop = function(type){
		if(quickPopXHR && quickPopXHR.abort){
			quickPopXHR.abort();
		}
		if(type !== prevPopType){
			var fn = quickDataFns[type];
			quickPop.html(ds.tmpl(popTmpl, fn));
			fn.init.call(this, fn);
		}
		doc.unbind('click.quick_links').one('click.quick_links', hideQuickPop);

		quickPop[0].className = 'quick_links_pop quick_' + type;
		popDisplayed = true;
		prevPopType = type;
		quickPop.show();
		quickPop.animate({right:40,queue:true});
	};
	quickShell.bind('click.quick_links', function(e){
		e.stopPropagation();
	});
	quickPop.delegate('a.ibar_closebtn','click',function(){
		quickPop.hide();
		quickPop.animate({queue:true});
		if(prevTrigger){
			prevTrigger.removeClass('current');
		}
	});

	//通用事件处理
	var 
	view = $(window),
	quickLinkCollapsed = !!ds.getCookie('ql_collapse'),
	getHandlerType = function(className){
		return className.replace(/current/g, '').replace(/\s+/, '');
	},
	showPopFn = function(){
		var type = getHandlerType(this.className);
		if(popDisplayed && type === prevPopType){
			return hideQuickPop();
		}
		showQuickPop(this.className);
		if(prevTrigger){
			prevTrigger.removeClass('current');
		}
		prevTrigger = $(this).addClass('current');
	},
	quickHandlers = {
		//购物车，最近浏览，商品咨询
		my_qlinks: showPopFn,
		message_list: showPopFn,
		history_list: showPopFn,
		leave_message: showPopFn,
		mpbtn_histroy:showPopFn,
		mpbtn_recharge:showPopFn,
		mpbtn_wdsc:showPopFn,
		//返回顶部
		return_top: function(){
			ds.scrollTo(0, 0);
			hideReturnTop();
		}
	};
	quickShell.delegate('a', 'click', function(e){
		var type = getHandlerType(this.className);
		if(type && quickHandlers[type]){
			quickHandlers[type].call(this);
			e.preventDefault();
		}
	});
	
	//Return top
	var scrollTimer, resizeTimer, minWidth = 1350;

	function resizeHandler(){
		clearTimeout(scrollTimer);
		scrollTimer = setTimeout(checkScroll, 160);
	}
	
	function checkResize(){
		quickShell[view.width() > 1340 ? 'removeClass' : 'addClass']('quick_links_dockright');
	}
	function scrollHandler(){
		clearTimeout(resizeTimer);
		resizeTimer = setTimeout(checkResize, 160);
	}
	function checkScroll(){
		view.scrollTop()>100 ? showReturnTop() : hideReturnTop();
	}
	function showReturnTop(){
		quickPanel.addClass('quick_links_allow_gotop');
	}
	function hideReturnTop(){
		quickPanel.removeClass('quick_links_allow_gotop');
	}
	view.bind('scroll.go_top', resizeHandler).bind('resize.quick_links', scrollHandler);
	quickLinkCollapsed && quickShell.addClass('quick_links_min');
	resizeHandler();
	scrollHandler();
});