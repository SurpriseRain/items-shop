var pictureScan = {};
pictureScan.spxx = {}; 

pictureScan.load = function(urlData, data){
	pictureScan.spxx = eval('('+urlData+')');
	
//$.ajax({ url: pictureScan.spxx.url, data: data, success: function(json){pictureScan.spxx = json;}});
//NUM = pictureScan.spxx.NUM;
}

pictureScan.write = function(o){
	var s = '<div id="preview" class="spec-preview">';
	$.each(pictureScan.spxx,function (i,ImgUrl){
		//s += '<li><img width="68" height="68"  src="./4-1.png"/></li>';
		//$("#imageMenu ul").find("img").eq(i).attr("src","/gui_o2o/eco/images/spdetail/"+spxxJson.ZCXX01+"/"+spxxJson.SPXX02+"/images/"+spxxJson.SPXX02+"_"+(i+1)+"_small.jpg").attr("onerror","this.onerror=null;this.src='"+defaultImgUrl+"'" );
		if(i==0){
			s +='<span class="jqzoom"><img jqimg="" src="" /></span>';
			s +='</div>';
			s +='<!--��ͼ��ʼ-->';
			s +='<div class="spec-scroll">';
			s +='<a class="prev">&lt;</a> <a class="next">&gt;</a>';
			s +='<div id="imageMenu" class="items">';
			s +='<ul>';
			//s +='<li><img alt="" bimg="" src="" onmousemove="preview(this);"></li>';
		}else{
			//s += '<li><img alt="" bimg="" src="" onmousemove="preview(this);"></li>';
		}
	});

	s += '</ul></div></div>';
    $(o).html(s);
}

pictureScan.init = function(o) {
    pictureScan.load($(o).attr('urlData'));
    pictureScan.write(o);
}


function tabs(tabId, tabNum){

	$(tabId + " .tab li").removeClass("curr");
	$(tabId + " .tab li").eq(tabNum).addClass("curr");
	
	$(tabId + " .tabcon").hide();
	$(tabId + " .tabcon").eq(tabNum).show();
}

function preview(img){
	var curImg = $("#preview img"),
		curImgSrc = curImg.attr("src"),
		targetImgSrc = $(img).attr("src");
	if(curImgSrc === targetImgSrc){
		return;
	}
	
	curImg.attr("src",targetImgSrc);
	curImg.attr("jqimg",$(img).attr("bimg"));
}


$(function(){
	$(".jqzoom").jqueryzoom({xzoom:380,yzoom:410});
});


$(function(){
	var tempLength = 0; 
	var viewNum = 5; 
	var moveNum = 2; 
	var moveTime = 300; 
	var scrollDiv = $(".spec-scroll .items ul"); 
	var scrollItems = $(".spec-scroll .items ul li"); 
	var moveLength = scrollItems.eq(0).width() * moveNum; 
	var countLength = (scrollItems.length - viewNum) * scrollItems.eq(0).width(); 
	  
	
	$(".spec-scroll .next").bind("click",function(){
		if(tempLength < countLength){
			if((countLength - tempLength) > moveLength){
				scrollDiv.animate({left:"-=" + moveLength + "px"}, moveTime);
				tempLength += moveLength;
			}else{
				scrollDiv.animate({left:"-=" + (countLength - tempLength) + "px"}, moveTime);
				tempLength += (countLength - tempLength);
			}
		}
	});
	
	$(".spec-scroll .prev").bind("click",function(){
		if(tempLength > 0){
			if(tempLength > moveLength){
				scrollDiv.animate({left: "+=" + moveLength + "px"}, moveTime);
				tempLength -= moveLength;
			}else{
				scrollDiv.animate({left: "+=" + tempLength + "px"}, moveTime);
				tempLength = 0;
			}
		}
	});
});


(function($){
	$.fn.jqueryzoom = function(options){
		var settings = {
			xzoom: 200,//zoomed width default width
			yzoom: 200,//zoomed div default width
			offset: 10,	//zoomed div default offset
			position: "right",//zoomed div default position,offset position is to the right of the image
			lens:1, //zooming lens over the image,by default is 1;
			preload: 1
		};

		if(options) {
			$.extend(settings, options);
		}

	    var noalt='';
	    $(this).hover(function(){
		    var imageLeft = this.offsetLeft;
		    //var imageRight = this.offsetRight;
		    var imageTop =  $(this).get(0).offsetTop;
		    var imageWidth = $(this).children('img').get(0).offsetWidth;
		    var imageHeight = $(this).children('img').get(0).offsetHeight;


		    noalt= $(this).children("img").attr("alt");

		    var bigimage = $(this).children("img").attr("jqimg");
	    
		    bigimage = bigimage === "" ? "/customer/qfy/images/msimg.jpg" : bigimage;

		    $(this).children("img").attr("alt",'');

		    if($("div.zoomdiv").get().length == 0){
	    		$(this).after("<div class='zoomdiv'><img class='bigimg' src='"+bigimage+"' /></div>");
	    		$(this).append("<div class='jqZoomPup'>&nbsp;</div>");
		    }


		    if(settings.position == "right"){	
		        if(imageLeft + imageWidth + settings.offset + settings.xzoom > screen.width){	
		        leftpos = imageLeft  - settings.offset - settings.xzoom;	
		        }else{	
			    leftpos = imageLeft + imageWidth + settings.offset;
		        }
		    }else{
			    leftpos = imageLeft - settings.xzoom - settings.offset;
			    if(leftpos < 0){		
			    	leftpos = imageLeft + imageWidth  + settings.offset;	
			    }
		    }

		    $("div.zoomdiv").css({ top: imageTop,left: leftpos });
		    $("div.zoomdiv").width(settings.xzoom);
		    $("div.zoomdiv").height(settings.yzoom);
	        $("div.zoomdiv").show();
	        if(!settings.lens){
	          $(this).css('cursor','crosshair');
			}

		   $(document.body).mousemove(function(e){
		       mouse = new MouseEvent(e);
		       /*$("div.jqZoomPup").hide();*/
			    var bigwidth = $(".bigimg").get(0).offsetWidth;
			    var bigheight = $(".bigimg").get(0).offsetHeight;
			    var scaley ='x';
			    var scalex= 'y';
		
			    if(isNaN(scalex)|isNaN(scaley)){
				    var scalex = (bigwidth/imageWidth);	
				    var scaley = (bigheight/imageHeight);	
			
				    /*$("div.jqZoomPup").width((settings.xzoom)/scalex );	
					$("div.jqZoomPup").height((settings.yzoom)/scaley);	*/
				    $("div.jqZoomPup").css({"widht":"168px","height":"214px"});	
			        if(settings.lens){
			        	$("div.jqZoomPup").css('visibility','visible');
					}
			    }
		
		        xpos = mouse.x - $("div.jqZoomPup").width()/2 - imageLeft;
		        ypos = mouse.y - $("div.jqZoomPup").height()/2 - imageTop ;
		        if(settings.lens){
		        	xpos = (mouse.x - $("div.jqZoomPup").width()/2 < imageLeft ) ? 0 : (mouse.x + $("div.jqZoomPup").width()/2 > imageWidth + imageLeft ) ?  (imageWidth -$("div.jqZoomPup").width() -2)  : xpos;
		
		        	ypos = (mouse.y - $("div.jqZoomPup").height()/2 < imageTop ) ? 0 : (mouse.y + $("div.jqZoomPup").height()/2  > imageHeight + imageTop ) ?  (imageHeight - $("div.jqZoomPup").height() -2 ) : ypos;
		
		        }
		
		        if(settings.lens){
		        	$("div.jqZoomPup").css({ top: ypos,left: xpos });
		        }
				scrolly = ypos;
				$("div.zoomdiv").get(0).scrollTop = scrolly * scaley;
				scrollx = xpos;
				$("div.zoomdiv").get(0).scrollLeft = (scrollx) * scalex ;

		   });
	    },function(){
           $(this).children("img").attr("alt",noalt);
	       $(document.body).unbind("mousemove");
	       if(settings.lens){
	    	   $("div.jqZoomPup").remove();
	       }
	       $("div.zoomdiv").remove();
	    });
    count = 0;

	if(settings.preload){
		$('body').append("<div style='display:none;' class='jqPreload"+count+"'>sdsdssdsd</div>");

		$(this).each(function(){
	        var imagetopreload= $(this).children("img").attr("jqimg");	
	        var content = jQuery('div.jqPreload'+count+'').html();	
	        jQuery('div.jqPreload'+count+'').html(content+'<img src=\"'+imagetopreload+'\">');
		});
	}

};

})(jQuery);

function MouseEvent(e) {
	this.x = e.pageX;
	this.y = e.pageY;
}


