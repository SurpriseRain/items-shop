/**

 * Description 分页插件
 * Powered By 小K
 * Data 2013-05-28
 * Dependence jQuery v1.8.3
 
 **/
(function($){
	$.fn.kkPages = function(options){
		var opts = $.extend({},$.fn.kkPages.defaults, options);
		return this.each(function(){		
  			
			var $this = $(this);
			var $PagesClass = opts.PagesClass; // 分页元素
			var $AllMth = opts.RowNum; //总个数
			var $Mth = opts.PagesMth; //每页显示个数
			var $NavMth = opts.PagesNavMth - 0; // 导航显示个数
			var $FileName =  opts.FileName;
			// 定义分页导航
			var PagesNavHtml = "<div class=\"Pagination Pagination1\"><a href=\"javascript:;\" class=\"homePage\">首页</a><a href=\"javascript:;\" class=\"PagePrev\">上一页</a><span class=\"Ellipsis\"><b>...</b></span><div class=\"pagesnum\"></div><span class=\"Ellipsis\"><b>...</b></span><a href=\"javascript:;\" class=\"PageNext\">下一页</a><a href=\"javascript:;\" class=\"lastPage\">尾页</a><cite class=\"FormNum\">直接到第<input type=\"text\" name=\"PageNum\" id=\"PageNum\">页</cite><a href=\"javascript:;\" class=\"PageNumOK\">确定</a></div>";

			/*默认初始化显示*/
			if($AllMth==0){
				//alert("友情提示：\n\n暂无数据！");
				//return;
			}
			//if($AllMth > $Mth){
					
					//判断显示
					var relMth = $Mth - 1;
					$this.find($PagesClass).filter(":gt("+relMth+")").hide();

					// 计算数量,页数
					var PagesMth = Math.ceil($AllMth / $Mth); // 计算页数
					var PagesMthTxt = "<span>共<b>"+$AllMth+"</b>条，共<b>"+PagesMth+"</b>页</span>";
		            $(".Pagination").remove();
					$this.append(PagesNavHtml).find(".Pagination").append(PagesMthTxt);
					// 计算分页导航显示数量
					var PagesNavNum = "";
					for(var i=1;i<=PagesMth;i++){
						PagesNavNum = PagesNavNum + "<a href=\"javascript:;\">"+i+"</a>";
					};
					$this.find(".pagesnum").append(PagesNavNum).find("a:first").addClass("PageCur");
					//判断是否显示省略号
					if($NavMth < PagesMth){
						$this.find("span.Ellipsis:last").show();
						var relNavMth = $NavMth - 1;
						$this.find(".pagesnum a").filter(":gt("+relNavMth+")").hide();
					}else{
						$this.find("span.Ellipsis:last").hide();
					};
				/*默认显示已完成,下面是控制区域代码*/

				//跳转页面
				var $input = $this.find(".Pagination #PageNum");
				var $submit = $this.find(".Pagination .PageNumOK");
				//跳转页面文本框
				$input.keyup(function(){
						var pattern_d = /^\d+$/; //全数字正则
						if(!pattern_d.exec($input.val())) {   
							//alert("友情提示：\n\n请填写正确的数字！");
							dlgShow({content:"请填写正确的数字"});
							$input.focus().val("");
							return false
                		};
				});
				//跳转页面确定按钮
				$submit.click(function(){
						if($input.val() == ""){
							//alert("友情提示：\n\n请填写您要跳转到第几页！");
							dlgShow({content:"请填写您要跳转到第几页"});
							$input.focus().val("");
							return false
						}if($input.val() > PagesMth){
							//alert("友情提示：\n\n您跳转的页面不存在！");
							dlgShow({content:"您跳转的页面不存在"});
							$input.focus().val("");
							return false
						}else{
							showPages($input.val());
						};
					});
					
				//导航控制分页
				var $PagesNav = $this.find(".pagesnum a"); //导航指向
				var $PagesFrist = $this.find(".homePage"); //首页
				var $PagesLast = $this.find(".lastPage"); //尾页
				var $PagesPrev = $this.find(".PagePrev"); //上一页
				var $PagesNext = $this.find(".PageNext"); //下一页
				
				//导航指向
				$PagesNav.click(function(){
						var NavTxt = $(this).text();
						showPages(NavTxt);
				});
				//首页
				$PagesFrist.click(function(){
					var OldNav = $this.find(".pagesnum a[class=PageCur]");
					if(OldNav.text() == 1 || OldNav.text()==""){
						//alert("友情提示：\n\n不要再点啦~已经是首页啦！");
						dlgShow({content:"不要再点啦~已经是首页啦"});
					}else{
						showPages(1);
					}
				});
				//尾页
				$PagesLast.click(function(){
					var OldNav = $this.find(".pagesnum a[class=PageCur]");
					if(OldNav.text() == PagesMth){
						//alert("友情提示：\n\n不要再点啦~已经是尾页啦！");
						dlgShow({content:"不要再点啦~已经是首页啦"});
					}else{
						showPages(PagesMth);
					}
				});	
				//上一页
				$PagesPrev.click(function(){
						var OldNav = $this.find(".pagesnum a[class=PageCur]");
						if(OldNav.text() == 1 || OldNav.text()==""){
							//alert("友情提示：\n\n不要再点啦~已经是首页啦！");
							dlgShow({content:"不要再点啦~已经是首页啦"});
						}else{
							var NavTxt = parseInt(OldNav.text()) - 1;
							showPages(NavTxt);
						};	
				});
				//下一页
				$PagesNext.click(function(){
					var OldNav = $this.find(".pagesnum a[class=PageCur]");
					if(OldNav.text() == PagesMth){
						//alert("友情提示：\n\n不要再点啦~已经是最后一页啦！");
						dlgShow({content:"不要再点啦~已经是最后一页啦"});
					}else{	
						var NavTxt = parseInt(OldNav.text()) + 1;
						showPages(NavTxt);
					};
				});
			
			// 主体显示隐藏分页函数
			function showPages(page){
					$PagesNav.each(function(){
						var NavText = $(this).text();
						if(NavText == page){
							$(this).addClass("PageCur").siblings().removeClass("PageCur");					
						};
					});
				//显示导航样式
				var AllMth = PagesMth / $NavMth;
				var frontNum=0;
			    var backNum=0;
			    
			    var newPage=parseInt(page);
			    var currentPageNum=1;
			    if(newPage<(parseInt($NavMth)+1)){
			    	currentPageNum=1;
			    }else{
			    	currentPageNum=Math.ceil(newPage/$NavMth);
			    }
			    if(newPage<$NavMth||newPage%$NavMth==0){
			    	if(newPage%$NavMth==0){
			    		frontNum=$NavMth-1;
			    	}else{
			    		frontNum=newPage-1;
			    	}
			    	backNum=$NavMth*currentPageNum-newPage;
			    }else{
			    	frontNum=newPage%5-1;
			    	backNum=$NavMth*currentPageNum-newPage;
			    }
			    var currentPages=new Array();
			    currentPages.push(newPage);
			    $PagesNav.hide();
			    var frontCurrentNum=newPage;
			    for(var i=0;i<frontNum;i++){
			    	frontCurrentNum=frontCurrentNum-1;;
			    	currentPages.push(frontCurrentNum);
			    }
			    
			    var backCurrentNum=newPage;
			    for(var i=0;i<backNum;i++){
			    	backCurrentNum=backCurrentNum+1;;
			    	currentPages.push(backCurrentNum);
			    }

			    var pageMax=Math.max.apply(null,currentPages);
			    var pageMin=Math.min.apply(null,currentPages);
			    if(currentPageNum==1){
			    	 $PagesNav.filter(":lt("+5+")").show();	
				     $PagesNav.filter(":gt("+5+")").hide();
				     $this.find("span.Ellipsis:first").hide();
			    }else{
				    $PagesNav.filter(":lt("+(parseInt(pageMin)-1)+")").hide();	
				    $PagesNav.filter(":gt("+(parseInt(pageMin-2))+")").show();
				    $PagesNav.filter(":gt("+(parseInt(pageMax-1))+")").hide();
				    $this.find("span.Ellipsis:first").show();
			    }
			    					// 显示内容区域
					var url = pubJson.O2OUrl + "/GridPageDataServlet?pages=" + page + "&fileName=" + fileName;
					var pageData = visitServiceGrid(url);
					showSPXX(pageData);
				};
				
			//}; //判断结束			
				
		}); //主体代码
	};
	
	// 默认参数
	$.fn.kkPages.defaults = {
		PagesClass:'tr.jl', //需要分页的元素
		PagesMth:5, //每页显示个数		
		PagesNavMth:5, //显示导航个数
		RowNum:8, //总页数
		FileName:'' //文件名
	};
	
	$.fn.kkPages.setDefaults = function(settings) {
		$.extend( $.fn.kkPages.defaults, settings );
	};
	
})(jQuery);