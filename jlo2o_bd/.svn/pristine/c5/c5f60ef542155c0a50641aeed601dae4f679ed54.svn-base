$(function(){
	var numpic = $('.carousel li').size()-1;
	var nownow = 0;
	var inout = 0;
	var TT = 0;
	var SPEED = 4000;
	$('.carousel li').eq(0).siblings('li').css({'display':'none'});
	var ulstart = '<ul class="pagination">',
		ulcontent = '',
		ulend = '</ul>';
	ADDLI();
	var pagination = $('.pagination li');	
	pagination.eq(0).addClass('current')
	function ADDLI(){
		for(var i = 0; i <= numpic; i++){
			ulcontent += '<li>' + (i+1) + '</li>';
		}		
		$('.carousel').after(ulstart + ulcontent + ulend);	
	}
	pagination.on('mouseover',DOTCHANGE)	
	function DOTCHANGE(){		
		var changenow = $(this).index();		
		$('.carousel li').eq(nownow).css('z-index','2');
		$('.carousel li').eq(changenow).css({'z-index':'2'}).fadeIn(500);
		pagination.eq(changenow).addClass('current').siblings('li').removeClass('current');
		$('.carousel li').eq(nownow).fadeOut(400,function(){$('.carousel li').eq(changenow).fadeIn(500);});
		nownow = changenow;
	}	
	pagination.mouseenter(function(){
		inout = 1;
	})	
	pagination.mouseleave(function(){
		inout = 0;
	})	
	function GOGO(){		
		var NN = nownow+1;		
		if( inout == 1 ){
			} else {
			if(nownow < numpic){
			$('.carousel li').eq(nownow).css('z-index','2');
			$('.carousel li').eq(NN).css({'z-index':'2'}).fadeIn(500);
			pagination.eq(NN).addClass('current').siblings('li').removeClass('current');
			$('.carousel li').eq(nownow).fadeOut(400,function(){$('.carousel li').eq(NN).fadeIn(500);});
			nownow += 1;
		}else{
			NN = 0;
			$('.carousel li').eq(nownow).css('z-index','2');
			$('.carousel li').eq(NN).stop(true,true).css({'z-index':'2'}).fadeIn(500);
			$('.carousel li').eq(nownow).fadeOut(400,function(){$('.carousel li').eq(0).fadeIn(500);});
			pagination.eq(NN).addClass('current').siblings('li').removeClass('current');
			nownow=0;
			}
		}
		TT = setTimeout(GOGO, SPEED);
	}	
	TT = setTimeout(GOGO, SPEED); 
});