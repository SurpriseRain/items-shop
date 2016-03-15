/**
 * name:公共控件
 * author:jhj
 * date:2015-03-09
 */
var DefStaPer = {};

DefStaPer.multiPage;//分页参数
DefStaPer.newclos;//换行参数
DefStaPer.data;//数据展示
DefStaPer.columnsArr;//列头
DefStaPer.afterUrl;//明细url
DefStaPer.load = function(returnData){
    DefStaPer.data=returnData;
}

DefStaPer.write = function(o,columnName){
	
/****************************列头展示开始*******************************************/
$(".dyryqx").remove();
var s = '';
s += '<table class="dyryqx">';
s += '<tr class="defStaPer_form">';
$.each(DefStaPer.columnsArr,function(i,val){
	if(val.checkbox==1){
		s += '<td class="no1"><input type="checkbox"/></td>';
	}else{
	s += '<td class="no2" style="width:'+val.width+';border: solid 1px #ffffff;text-align:center;">'+val.header+'</td>';
	}
});
    s += '</tr>';
/****************************列头展示结束*******************************************/
    
/****************************数据展示开始*******************************************/
$.each(DefStaPer.data,
    function(i, val) {
			s += '<tr class="jl">';

				 $.each(DefStaPer.columnsArr,function(index,temp){
					
						if(temp.checkbox==1){
							s += '<td><input type="checkbox" name='+temp.property+'/>'+eval("val."+temp.property)+'</td>';
						}
						if(temp.type=="text"){
							s += '<td><input name="'+temp.property+'" style="width:100px;"/>'+eval("val."+temp.property)+'</td>';
						}
						if(temp.type=="lable"){
							s += '<td class="case2" ><input class="gdz" id="'+temp.property+'" name="'+temp.property+'" value="'+eval("val."+temp.property)+'" style="width:'+temp.width+';border: solid 1px #ffffff;text-align:center;" readonly="readonly;"></td>';
						}
						/*单独的查询展示，没有点击事件*/
						if(temp.type=="srch"){
							s += '<td class="srch" ><input class="srch" id="'+temp.property+'" name="'+temp.property+'" value="'+eval("val."+temp.property)+'" style="width:'+temp.width+';border: solid 1px #ffffff;text-align:center;" readonly="readonly;"></td>';
						}
						if(temp.type=="number"){
							djzt = temp.property+"_"+eval("val."+temp.property);
							var valueTxt = eval("columnName."+djzt);
							s += '<td class="case2"><input class="gdz" id="'+temp.property+'" name="'+temp.property+'" value="'+valueTxt+'" style="width:'+temp.width+';border: solid 1px #ffffff;text-align:center;" readonly="readonly;"></td>';
						}
						if(temp.type=="select"){
							s += '<td>';
						    s += '<select>';
							$.each(eval("val."+temp.property),function(key,value){
								s += '<option value="'+key+'">'+value+'</option>';
							});
							s += '</select>';
							s += '</td>';
						}
						if(temp.type=="css1"){
							s += '<td class="no4">';
							s += '<div class="get"><span >请选择</span></div>';
							s += '<div class="cdqx_zcd">';
							$.each(eval("val."+temp.property),function(key,value) {
							    s += '<input id="id_'+key+'" type="checkbox" value="'+value+' " class="checkbox"  name="checkbox"/><span id="id_'+key+'">'+value+'</span>';
							});
							s += '</div>';
							s += '</td>';
						}
						if(temp.type=="css2"){
							s += '<td>';
							s += '<span>请选择</span>';
							s += '<ul class="cd_qx">';
							$.each(eval("val."+temp.property),function(key, value){
							    s += '<li><input type="radio" name="jkqx" value="'+value+'" /><font>'+value+'</font></li>';
							});
							s += '</ul>';
							s += '</td>';
						}
						if(temp.type=="radio"){
							s += '<td>';
							$.each(eval("val."+temp.property),function(key, value){
							    s += '<input type="radio"  name="yes" value='+key+'/><label>'+value+'</label>';
							});
							s += '</td>';
						}
						//订单审核
						if(temp.type=="oper"){
                            var djztValue = eval("val."+temp.property);
							if(djztValue==3){
								s += '<td><a href="javascript:void(0);" class="rpl" id="check" name="sh">审核</a></td>';
							}else if(djztValue==1){
							    s += '<td><a href="javascript:void(0);" class="rpl" id="check" name="sh">审核</a>　　<a href="javascript:void(0);" class="rpl" id="back" name="bc">驳回</a></td>';
							}else if(djztValue==9){
								s += '<td><a href="javascript:void(0);" class="rpl" id="error" name="yc">异常</a></td>';
							}
							
						}
						//点击删除
						if(temp.type=="delete"){
							s += '<td><a href="javascript:void(0);" class="del"  name="del">删除</a></td>';
						}
						//店铺审核
						if(temp.type=="dpsh"){
                            var djztValue = eval("val."+temp.property);
							if(djztValue==1){
								s += '<td><a  id="check" name="sh">已审核</a></td>';
							}else if(djztValue==0){
							    s += '<td><a class="rpl" id="check" name="sh" >未审核</a></td>';
							}
						}
						//银行卡启用
						if(temp.type=="yhkbj"){
                            var djztValue = eval("val."+temp.property);
							if(djztValue==1){
								s += '<td><a href="javascript:void(0);" id="check" name="sh">已启用</a></td>';
							}else if(djztValue==0){
							    s += '<td><a class="start" id="check" name="sh" style="color:#f40;">启用 &nbsp;</a><a href="javascript:void(0);" class="del"  name="del">删除</a></td>';
							}
						}
				 });
				 s += '</tr>';

});
s += '</table>';
	
    $(o).html(s);
    /****************************数据展示结束*******************************************/
    //点击审核按钮
    $(".rpl").click(function(){
        var xsdd01=$(this).parent("td").siblings(".case2:first").children("input").attr("value");
    	parent.ajaxGrid(DefStaPer.afterUrl+xsdd01);
    })
    //点击单条数据审核
    $(".case2").click(function(){
        var xsdd01=$(this).parent().children("td").eq(0).children("input").attr("value");
        backFun(xsdd01);
    })
    //为label时点击样式
    $(".gdz").click(function(){
    	 if(DefStaPer.afterUrl != undefined && DefStaPer.afterUrl != ""){
    		 var xsdd01=$(this).parent().parent().children("td").eq(0).children("input").attr("value");
        	 parent.ajaxGrid(DefStaPer.afterUrl+xsdd01);
    	 }
     })
     //点击删除
    $(".del").click(function(){
        var xsdd01=$(this).parent("td").siblings(".srch:first").children("input").attr("value");
        if(xsdd01==undefined){
        	xsdd01=$(this).parent("td").siblings(".case2:first").children("input").attr("value");
        }
        del(xsdd01);
    })
            //银行卡点击启用
    $(".start").click(function(){
        var xsdd01=$(this).parent("td").siblings(".case2:first").children("input").attr("value");
        update(xsdd01);
    })
    //判断是否分页
    if(DefStaPer.multiPage=="true"){
	    /****************************分页展示开始*******************************************/  
	    $(".Pagination").remove();
		$('.center').kkPages({ 
	 	   PagesClass:'tr.jl', //需要分页的元素
	 	   PagesMth:pubJson.PagesMth, //每页显示个数 
	 	   PagesNavMth:pubJson.PagesNavMth //显示导航个数
	 	});
		/****************************分页展示结束*******************************************/
    }
}

DefStaPer.init = function(o) {
	var columnName = changeColumns;
	if ($(o).attr('newclos') != 'undefine')DefStaPer.newclos=$(o).attr('newclos');
	if ($(o).attr('data') != 'undefine')DefStaPer.data=eval($(o).attr('data'));
	if ($(o).attr('multiPage') != 'undefine')DefStaPer.multiPage=$(o).attr('multiPage');
	if ($(o).attr('afterUrl') != 'undefine')DefStaPer.afterUrl=$(o).attr('afterUrl');
	if ($(o).attr('columnsArr') != 'undefine')DefStaPer.columnsArr=eval($(o).attr('columnsArr'));
	DefStaPer.load(initData());
	if(columnName!=undefined){
	    DefStaPer.write(o,columnName);
	}else{
		DefStaPer.write(o);
	}
}
