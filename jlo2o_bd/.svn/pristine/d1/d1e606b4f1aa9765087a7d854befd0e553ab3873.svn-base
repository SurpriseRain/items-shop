$(function(){   
	$('.choosebox li a').click(function(){
		var thisToggle = $(this).is('.size_radioToggle') ? $(this) : $(this).prev();
		var checkBox = thisToggle.prev();
		checkBox.trigger('click');
		$('.size_radioToggle').removeClass('current');
		thisToggle.addClass('current');
		return false;
	});		
});

$(".choosebox li a").click(function(){
	var text = $(this).html();
	$(".choosetext span").html(text);
	$("#result").html("" + getSelectedValue("address"));
});
$(function(){   
	$('.invo_choosebox li a').click(function(){
		var thisToggle = $(this).is('.invo_radioToggle') ? $(this) : $(this).prev();
		var checkBox = thisToggle.prev();
		checkBox.trigger('click');
		$('.invo_radioToggle').removeClass('current');
		thisToggle.addClass('current');
		return false;
	});		
});

$(".invo_choosebox li a").click(function(){
	var text = $(this).html();
	$(".choosetext span").html(text);
	$("#result").html("" + getSelectedValue("address"));
});
function getSelectedValue(id){
	return 
	$("#" + id).find(".choosetext span.value").html();
}

 $(document).ready(function(){
        $(".modify").click(function(){
            $("#modifyadd").addClass("displayblock");
        });
        $(".invo_add").click(function(){
            $("#invoice_open").addClass("displayblock");
        });
         $("#changebtnok,#changebtnno").click(function(){
            $("#modifyadd").removeClass("displayblock");
        });
          $("#fpchangebtnok,#fpchangebtnno").click(function(){
            $("#invoice_open").removeClass("displayblock");
       });
});