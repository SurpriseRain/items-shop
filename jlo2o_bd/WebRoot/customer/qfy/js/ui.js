$(document).ready(function(){
  $("#cusService").mouseover(function(){
	$(this).animate({"right":"-32px"}); 
	$("#cusService_main").animate({"right":"5px"});  
  });
  $("#cusService_main").mouseleave(function(){
	$("#cusService").animate({"right":"5px"}); 
	$(this).animate({"right":"-222px"}); 
  });
	$(window).scroll(function(){
		var $top=$('.goTop');
		if($(window).scrollTop()>500){
			$top.css({'display':'block'});
			$top.on('mouseover',function(){
				//添加样式
			}).on('mouseout',function(){
				// alert('移出');	
			});
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
});


