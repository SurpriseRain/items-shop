
var splist_1 = {};

splist_1.data = {}; 

splist_1.load = function(url, data){
//$.ajax({ url: url, data: data, success: function(json){splist_1.data = json;}});
//splist_1.data =  [{"img":"cpimg.png","spid":"01","spname":"长春华众离合器CS430压盘","spjg":{"零售价：":"30.00","批发价：":"60.00","客户价：":"90.00"}}];
}

splist_1.write = function(o){
var s = '<ul class="pro-case">';
$.each(splist_1.data,
    function(i, val) {
	s += '<li>';
	s += '<a href="#" class="img"><img src="images/'+val.img+'" /></a>';
	s += '<div class="pro-name"><a href="#">'+val.spname+'</a></div>';
		$.each(val.spjg,
        function(name, value) {
		s += '<div class="pro-pic"><span>'+name+'：</span><b>￥'+value+'</b></div>';
		});
	s +='<div class="pro-car"><a href="#" class="car">加入购物车</a></div>'
	});
	s += '</li></ul>';
	//alert(s);
    $(o).html(s);
}

/*<ul class="pro-case">
      <li  class="first">
       
        <a href="#" class="img"><img src="images/cpimg.png" /></a>
        <div class="pro-name"><a href="#">机油机油机油机油机油机油机油机油机油机油机油机油机油机油机油机油</a></div>
        <div class="pro-pic"><span>零售价：</span><b>999.00</b></div>
        
 		<div class="pro-pic"><span>批发价：登录可见</span></div>
        <div class="pro-pic"><span>客户价：四及以上星客户专享价</span></div>
        <div class="pro-car"><a href="#" class="car">加入购物车</a></div>
      </li>
      <li>*/
splist_1.init = function(o) {
	if ($(o).attr('data') != 'undefine') splist_1.data=eval($(o).attr('data'));
    splist_1.load($(o).attr('url'));
    splist_1.write(o);

}


splist_1.reply = function(from_jlid, json, o) {
    if ($(o).attr('before_reply') != 'undefine') eval($(o).attr('before_reply'));
    //splist_1.load($(o).attr('url'), json);
    splist_1.write(o);
    if (!$(o).attr('after_reply') != 'undefine') eval($(o).attr('after_reply'));

}

