
var page_1 = {};
var pagesize=3;
var targetid={};
var targetjlid={};
page_1.data = {}; 
var fileName={};
var sum=0;
var totalPage=0;
page_1.load = function(url, data){
/*$.ajax({
	    type: "GET",  //请求方式
	    async: false,   //true表示异步 false表示同步
	    url:encodeURI(url),
	    data:{},
	    success: function(data){
	      if (data != null){
	        try{
	          var json = jQuery.parseJSON(data);
	          page_1.data = json.data;
	          fileName = json.fileName;
	        }catch(e){
	          return;
	        }
	      }
	    },
    	error:function(XMLHttpRequest, textStatus, errorThrown) {
	    	//alert("获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest+"+"+errorThrown);//正式数据
        }
	  });*/
sum = page_1.data.length;
totalPage = Math.ceil(sum/pagesize);
}

page_1.write = function(o){
var s = '<div id="main" class="w980 clearfix">';
s += '<div style="clear:both;"></div>';
s += '<div class="fore clearfix">';
s +=      '<div class="shop_page w100">';
s +=        '<span class="fr">';
s +=           '<a onclick="page_1.pageUp(splist_1,lb);" class="prev">上一页</a>';
s +=           '<select name="PAGING" id="PAGING" onchange="page_1.PAGING('+targetjlid+','+targetid+',this);">';//PAGING(this);
	for(var i=1;i<=totalPage;i++){
		s +='<option value="'+i+'">'+i+'</option>';
	}
s +='</select>';
s +=           '<a onclick="page_1.pageDown(splist_1,lb);" class="next">下一页</a>';
s +=        '</span>';
s +=        '<br class="clear">';
s +=    '</div>';
s +=  '</div>';
s +='</div>';
s +='</div>';
	//alert(s);
    $(o).html(s);
}

page_1.init = function(o) {
	if ($(o).attr('data') != 'undefine')page_1.data=eval($(o).attr('data'));
	if ($(o).attr('targetid') != 'undefine')targetid=$(o).attr('targetid');
	if ($(o).attr('targetjlid') != 'undefine')targetjlid=$(o).attr('targetjlid');
    page_1.load($(o).attr('url'));
    page_1.write(o);
	page_1.loadPages(eval(targetjlid),eval(targetid));
}


page_1.reply = function(from_jlid, json, o) {
    if ($(o).attr('before_reply') != 'undefine') eval($(o).attr('before_reply'));
    //page_1.load($(o).attr('url'), json);
    page_1.write(o);
    if (!$(o).attr('after_reply') != 'undefine') eval($(o).attr('after_reply'));
}
//默认第一页
page_1.loadPages = function(from_jlid,o) {
	var index=1;
	var upPage=0;
	var arr=[];
	for(var i=0;i<sum;i++){
		if((upPage*pagesize)<=i&&i<(index*pagesize)){
		arr.push(page_1.data[i])
		}
	}
	from_jlid.data=arr;
    from_jlid.write(o);
}
//分页
page_1.PAGING = function(from_jlid,o,e) {
	var index=e.value;
	var upPage=e.value-1;
	/*var url="/GridPageDataServlet?pages="+pagesize+"&fileName="+fileName;
	$.ajax({
	    type: "GET",  //请求方式
	   // dataType: "JSON",
	    async: false,   //true表示异步 false表示同步
	    url:encodeURI(url),
	    data:{},
	    success: function(data){
	      if (data != null){
	        try{
	        	page_1.data = jQuery.parseJSON(data);
	        }catch(e){
	        	alert("获取");
	          return;
	        }
	      }
	    },
    	error:function(XMLHttpRequest, textStatus, errorThrown) {
	    	//alert("获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest+"+"+errorThrown);
        }
	  });*/
	var arr=[];
	for(var i=0;i<sum;i++){
		if((upPage*pagesize)<=i&&i<(index*pagesize)){
		arr.push(page_1.data[i])
		}
	}
	from_jlid.data=arr;
    from_jlid.write(o);
}


//上一页 
page_1.pageUp = function(from_jlid,o){
	if($("#PAGING").val()==1||$("#PAGING").val()==null){
		alert("已是首页");
		return;
	}else{
		var pageUp=$("#PAGING").val()*1-1;
		$("#PAGING").val(pageUp);
		document.getElementById("PAGING").value=pageUp;
		page_1.PAGING(from_jlid,o,document.getElementById("PAGING"));
	}	
}
//下一页 
page_1.pageDown = function(from_jlid,o){
	if($("#PAGING").val()==totalPage){
		alert("已是最后一页");
		return;
	}else{
		var pageDown=$("#PAGING").val()*1+1;
		document.getElementById("PAGING").value=pageDown;
		page_1.PAGING(from_jlid,o,document.getElementById("PAGING"));
	}
}

