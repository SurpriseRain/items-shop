/**
 * name:公共控件
 * author:jhj
 * date:2015-03-09
 */
var DefStaPer = {};

DefStaPer.multiPage;//分页参数
DefStaPer.newclos;//换行参数
DefStaPer.rowClick;//行点击事件
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
s += '<table class="list_table">';
s += '<tr class="title">';
$.each(DefStaPer.columnsArr,function(i,val){
	if(val.checkbox==1){
		s += '<td class="no1"><input type="checkbox" class="checkbox all" /></td>';
	}else if(val.type=="hidden"){
		s += '<td style="display:none">'+val.header+'</td>';
	}else{
		s += '<td class="no2" style="width:'+val.width+';">'+val.header+'</td>';
	}
});
    s += '</tr>';
/****************************列头展示结束*******************************************/
    
/****************************数据展示开始*******************************************/
$.each(DefStaPer.data,
    function(i, val) {
				//判断是否有行点击事件
	            if(DefStaPer.rowClick == "false"){
	            	s += '<tr class="jl">';
	            }else{
	            	s += "<tr id='tr_"+i+"' class='jl' onclick='rowClick("+i+")'>";
	            }

			    
				 $.each(DefStaPer.columnsArr,function(index,temp){
					    var fieldValue = eval("val."+temp.property);
					    if(fieldValue==undefined || fieldValue=="undefined")fieldValue="";
						if(temp.checkbox==1){
							s += '<td><input type="checkbox" name='+temp.property+'/>'+fieldValue+'</td>';
						}
						if(temp.type=="hidden"){
							s += '<td style="display:none;"><input id="'+temp.property+'" name="'+temp.property+'" value="'+fieldValue+'"/></td>';
						}
						if(temp.type=="text"){
							s += '<td><input name="'+temp.property+'" style="width:100px;"/>'+fieldValue+'</td>';
						}
						if(temp.type=="lable"){
							s += '<td class="case2"><input id="'+temp.property+'" name="'+temp.property+'"  value="'+fieldValue+'" title="'+fieldValue+'" class="span" style="width:'+temp.width+';" readonly="readonly;"/></td>';
						}
						if(temp.type=="img"){
							s += '<td class="case2"><img src="'+pubJson.path_sptp+'/sptp/'+usercookie.ZCXX01+'/'+fieldValue+'/images/'+fieldValue+'_1_small.jpg" onerror=this.onerror=null;this.src="/customer/qfy/images/msimg.jpg" name="'+temp.property+'"  title="'+fieldValue+'" class="span" style="width:'+temp.width+';" readonly="readonly;"/></td>';
						}
						/*单独的查询展示，没有点击事件*/
						if(temp.type=="srch"){
							s += '<td class="srch" ><input class="srch span" id="'+temp.property+'" name="'+temp.property+'" value="'+fieldValue+'" style="width:'+temp.width+';" readonly="readonly;"></td>';
						}
						if(temp.type=="number"){
							djzt = temp.property+"_"+fieldValue;
							var valueTxt = eval("columnName."+djzt);
							s += '<td class="case2"><input type="text" id="'+temp.property+'_code" value="'+fieldValue+'" style="display:none;"/><a class="gdz" id="'+temp.property+'" name="'+temp.property+'" style="width:'+temp.width+';">'+valueTxt+'</a></td>';
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
							$.each(fieldValue,function(key,value) {
							    s += '<input id="id_'+key+'" type="checkbox" value="'+value+' " class="checkbox"  name="checkbox"/><span id="id_'+key+'">'+value+'</span>';
							});
							s += '</div>';
							s += '</td>';
						}
						if(temp.type=="css2"){
							s += '<td>';
							s += '<span>请选择</span>';
							s += '<ul class="cd_qx">';
							$.each(fieldValue,function(key, value){
							    s += '<li><input type="radio" name="jkqx" value="'+value+'" /><font>'+value+'</font></li>';
							});
							s += '</ul>';
							s += '</td>';
						}
						if(temp.type=="radio"){
							s += '<td>';
							$.each(fieldValue,function(key, value){
							    s += '<input type="radio"  name="yes" value='+key+'/><label>'+value+'</label>';
							});
							s += '</td>';
						}
						//商品操作
						if(temp.type=="spcz"){
							var djztValue = eval("val."+temp.property);
							var spxx01 = eval("val."+DefStaPer.columnsArr[0].property);
							s += '<td><a href="javascript:void(0);" class="edit_ operation"  name="edit"  onClick=edit("'+spxx01+'")>编辑</a>'
							if(djztValue=="0")
							{
								s += '<a href="javascript:void(0);" class="sj_ operation"  name="sj" onClick=sj("'+spxx01+'")>上架</a>';
							}else
							{
								s += '<a href="javascript:void(0);" class="xj_ operation"  name="xj" onClick=sj("'+spxx01+'")>下架</a>';
							}
							s += '<a href="javascript:void(0);" class="del_ operation"  name="del" onClick=del("'+spxx01+'")>删除</a></td>';
							//s += '<td><a href="javascript:void(0);" class="rpl operation" id="check" name="sh">查看</a></td>';
						}
						//订单审核
						if(temp.type=="ddsh"){
							var djztValue = fieldValue;
							s += '<td><a href="javascript:void(0);" class="rpl operation" id="check" name="sh">查看</a></td>';
						}
						
						//买家退单入库
						if(temp.type=="mjtd"){
							s += '<td><a href="javascript:void(0);" class="rpl operation" id="check" name="sh">扫码/导入</a></td>';
						}
						
						//点击删除
						if(temp.type=="delete"){
							s += '<td><a href="javascript:void(0);" class="del operation"  name="del">删除</a></td>';
						}
						
						//货架编号
						if(temp.type=="bjbh"){
							  var djztValue = eval("val."+temp.property);
							s += '<td><input class="srch span" id="'+temp.property+'" name="'+temp.property+'" style="width:'+temp.width+';" ></td>';
						}
						//店铺审核
						if(temp.type=="dpsh"){
                            var djztValue = fieldValue;
							if(djztValue==1){
								s += '<td>已审核</td>';
							}else if(djztValue==0){
							    s += '<td>未审核</td>';
							}
						}
						//银行卡启用
						if(temp.type=="yhkbj"){
                            var djztValue = eval("val."+temp.property);
							if(djztValue==1){
								s += '<td><a href="javascript:void(0);" class="operation" id="check" name="sh">已启用</a></td>';
							}else if(djztValue==0){
							    s += '<td><a class="start operation" id="check" name="sh" style="color:#f40;">启用 </a><a href="javascript:void(0);" class="del operation"  name="del">删除</a></td>';
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
	if($(o).attr('rowClick') != 'undefine'){
		DefStaPer.rowClick=$(o).attr('rowClick');
	}else{
		DefStaPer.rowClick="false";
	}
	if($(o).attr('height') != 'undefine'){
		DefStaPer.height=$(o).attr('height');
	}else{
		DefStaPer.height="2000";
	}
	DefStaPer.load(initData());
	if(DefStaPer.data!=""){
		if(columnName!=undefined){
			DefStaPer.write(o,columnName);
		}else{
			DefStaPer.write(o);
		}
	}
}

//行点击事件，弹出明细窗口
function rowClick(rowIndex){
	var rowId="tr_"+rowIndex;
	var rowObj = $("#"+rowId+"");
	if(DefStaPer.afterUrl != undefined && DefStaPer.afterUrl != ""&&DefStaPer.rowClick=="open"){
		parent.ajaxGrid(DefStaPer.afterUrl,DefStaPer.height);
		parent.$("#alertDIV").load(function() {
			if(typeof(parent.$("#alertDIV")[0].contentWindow.openUrlInit)=='function'){
				parent.$("#alertDIV")[0].contentWindow.openUrlInit(rowObj);
			}
		})
	}else if(DefStaPer.rowClick=="backFill"){
		backFun(rowObj);
	}
}