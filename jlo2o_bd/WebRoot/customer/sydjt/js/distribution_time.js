var tiem={};
var psdzlist={};
var psqys={};
var weekday_un=new Array(7);
weekday_un[0]="Sunday";
weekday_un[1]="Monday";
weekday_un[2]="Tuesday";
weekday_un[3]="Wednesday";
weekday_un[4]="Thursday";
weekday_un[5]="Friday";
weekday_un[6]="Saturday";
var weekday_zh=new Array(7);
weekday_zh[0]="周天";
weekday_zh[1]="周一";
weekday_zh[2]="周二";
weekday_zh[3]="周三";
weekday_zh[4]="周四";
weekday_zh[5]="周五";
weekday_zh[6]="周六";
var shsjday=10;//当前延后配送时间天数
var sjdarr = [{"start":"9:00","end":"15:00"},{"start":"15:00","end":"19:00"},{"start":"19:00","end":"23:00"}];

$(document).ready(function(){
	$(document).on("click","#distribution_time td",function(){
	  if($(this).children("a").length>0){
	  $("td > a").removeClass("active");
	  $(this).children("a").addClass("active");
	  $(this).children("a").html("已选");
	  }
   })
   $(document).on("click","#distribution_time a.btn_link",function(){ 
	  $(this).addClass("btn_active");  
   })
    //$("#distribution_time #timeSave").bind("click", function() {
    //  alert(1);
    //	var times=$("#distribution_time td a.active");
    //});
	
    
   /* var date=new Date();
	var y = date.getFullYear(), m = date.getMonth()+1, d = date.getDate(), h=date.getHours(), mi=date.getMinutes();
	var psday = y + '-' + ((m<10)?('0'+m):(m)) + '-' + ((d<10)?('0'+d):(d)); //+ ' ' + h + ':' + mi;
	var weekday=weekday_zh[date.getDay()];//当前星期几
	//alert(psday);
	//var psMoth = ((m<10)?('0'+m):(m)) + '月' + ((d<10)?('0'+d):(d))+"日";
	var psMoth = ((m<10)?('0'+m):(m)) + "月" +((d<10)?('0'+d):(d))+"日";
	var sjd = h+":"+mi;
	//timeData(date,weekday_zh);
	var tempAm_s="09:00";
	var tempAm_e="15:00";
	var tempAfter_s="15:00";
	var tempAfter_e="19:00";
	var temsjd="";
	if(sjd>tempAm_s&&sjd<tempAm_e){
		temsjd="09:00-15:00";
	}
	if(sjd>tempAfter_s&&sjd<tempAfter_e){
		temsjd="15:00-19:00";
	}
	
	$(".affirm_pass_top_p > span").empty();
	$(".affirm_pass_top_p > span").attr("val",psday+tempAm_s);
	$(".affirm_pass_top_p > span").append("预计&nbsp;"+psMoth+" ["+weekday+"] "+temsjd+"&nbsp;送达&nbsp;");

	$("#distribution_time td").each(function(){
		var temptd = $(this);
		if($(temptd).attr("start")==psday+" "+tempAm_s){
			$(temptd).children("a").addClass("active");
			$("#distribution_time a.btn_link").addClass("btn_active"); 
		}
	});
  
  $("#timeSave").click(function(){
	   var times=$("#jbox-states td a.active");
   });*/
});

var timeSave=function(){
	var date=new Date();
	var y = date.getFullYear(), m = date.getMonth()+1, d = date.getDate(), h=date.getHours(), mi=date.getMinutes();
	var psday = y + '-' + ((m<10)?('0'+m):(m)) + '-' + ((d<10)?('0'+d):(d)); //+ ' ' + h + ':' + mi;
	var times=$("#distribution_time td a.active").parent();
	if(times.attr("val")==undefined){
		alert("请选择配送时间");
		return false;
	}
	$(".affirm_pass_top_p > span").empty();
	$(".affirm_pass_top_p > span").attr("val",times.attr("valtime"));
	$(".affirm_pass_top_p > span").append("配送时间：&nbsp;&nbsp;预计&nbsp;"+times.attr("val")+"&nbsp;送达&nbsp;");
	tiem["start"] = times.attr("start");
	tiem["end"] = times.attr("end");
	tiem["definename"] =  times.attr("valname");
	tiem["shqydm"] = psqys["ZONE"]==undefined?"":psqys["ZONE"];
	tiem["shqymc"] = psqys["ZONE_NAME"]==undefined?"":psqys["ZONE_NAME"];
	//alert(tiem["start"]+"######"+tiem["end"]);
	//隐藏jobx
	jboxClose();
}

//关闭jBox
function jboxClose(){
	//$(".jbox-body").hide();
	//jBox.close();
	$("#distribution_time").hide();
}

function timeData(date,week){
	var y = date.getFullYear(), m = date.getMonth()+1, d = date.getDate(), h=date.getHours(), mi=date.getMinutes();
	var psday = y + '-' + m + '-' + d; //+ ' ' + h + ':' + mi;
	var psmoth=y + '-' + m;
	$("#distribution_time").empty();
	var s="";
	/*s +="<div class=\"distribution_title\">";
	s +="配送时间";
	s +="</div>";
	s +="<div class=\"distribution_btn\">";
	s +="	<a class=\"btn_link\">";
	s +="   	指定时间";
	s +="        <em></em>";
	s +="    </a>";
	s +="</div>";*/
	s +="<table>";
	s +="	<tr class=\"title\">";
	s +="    	<td class=\"no1\">时间段</td>";
	for(var k=0;k<shsjday;k++){
		psmoth = (date.getMonth()+1) + '-' +date.getDate();
		if(k==0){
		s +="        <td><span>"+psmoth+"</span><label>今天</label></td>";
		}else{
		s +="        <td><span>"+psmoth+"</span><label>"+week[date.getDay()]+"</label></td>";
		}
		date.setDate(date.getDate()+1);
	} 	
	s +="    </tr>";
	for(var i=0;i<sjdarr.length;i++){
		temp=sjdarr[i];
		s +="<tr>";
		s +="    	<td class=\"no1\" strart="+temp.start+" end="+temp.end+">"+temp.start+"-"+temp.end+"</td>";
		var date =new Date();
		var dqdate =new Date();
		for(var j=0;j<shsjday;j++){
			psmoth = (date.getMonth()+1) + "月" +date.getDate()+"日";
			var temppsday = date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate();
			var datestring = date.getFullYear()+"/"+(date.getMonth()+1)+"/"+date.getDate()+" "+temp.start+":00";
			var datetring = date.getFullYear()+"/"+(date.getMonth()+1)+"/"+date.getDate()+" "+temp.end+":00";
			if(dqdate.getTime()<new Date(datetring).getTime()){
				s +="<td val="+psmoth+"&nbsp;["+week[date.getDay()]+"]&nbsp;"+temp.start+"-"+temp.end+" day="+temppsday+" range="+temp.start+"-"+temp.end+" start="+temppsday+"&nbsp;"+temp.start+" end="+temppsday+"&nbsp;"+temp.end+"><a>可选</a></td>";
			}else{
				s +="        <td></td>";
			}
			date.setDate(date.getDate()+1);
		}
		s +="</tr>";
	}
	s +="</table>";
	s +="<p>温馨提示：我们会努力按照您指定的时间配送，但因天气、交通等各类因素影响，您的订单有可能会有延误现象！</p>";
	s +="<div class=\"distribution_bottom\"><a id=\"timeSave\" onclick=\"timeSave();\">保存</a><a>取消</a></div>";
	$("#distribution_time").append(s);
}

tiem.write = function(data){
	//alert(JSON.stringify(data));
	shsjday = data.psdays;
	//去重复
	shsjday = undulpicate(shsjday);
	sjdarr = data.pstimes;
	var dqdate = new Date();
	var y = dqdate.getFullYear(), m = dqdate.getMonth()+1, d = dqdate.getDate(), h=dqdate.getHours(), mi=dqdate.getMinutes();
	var dqmoth = ((m<10)?('0'+m):(m)) + '-' +((d<10)?('0'+d):(d));
	$("#distribution_time").empty();
	var s="";
	/*s +="<div class=\"distribution_title\">";
	s +="配送时间";
	s +="</div>";
	s +="<div class=\"distribution_btn\">";
	s +="	<a class=\"btn_link btn_active\">";
	s +="   	指定时间";
	s +="        <em></em>";
	s +="    </a>";
	s +="</div>";*/
	s +="<table>";
	s +="	<tr class=\"title\">";
	s +="    	<td class=\"no1\">时间段</td>";
	$.each(shsjday,function(k,val){
		var date =new Date(val.DATE);
		y = date.getFullYear(), m = date.getMonth()+1, d = date.getDate(), h=date.getHours(), mi=date.getMinutes();
		psmoth = ((m<10)?('0'+m):(m)) + '-' +((d<10)?('0'+d):(d));
		if(dqmoth==psmoth){
		s +="        <td><span>"+psmoth+"</span><label>今天</label></td>";
		}else{
		s +="        <td><span>"+psmoth+"</span><label>"+weekday_zh[date.getDay()]+"</label></td>";
		}
	});
	//for(var k=0;k<shsjday.length;k++){
		
//	} 	
	s +="    </tr>";
	$.each(sjdarr,function(i,temp){
		s +="<tr>";
		s +="    	<td class=\"no1\" strart="+temp.START+" end="+temp.END+">"+temp.START+"-"+temp.END+"</td>";
		$.each(shsjday,function(j,val){
			var date =new Date(val.DATE);
			var dqdate =new Date();
			psmoth = (((date.getMonth()+1)<10)?('0'+(date.getMonth()+1)):((date.getMonth()+1))) + "月" +((date.getDate()<10)?('0'+date.getDate()):(date.getDate()))+"日";
			var selectdate=(((date.getMonth()+1)<10)?('0'+(date.getMonth()+1)):((date.getMonth()+1))) + "月" +((date.getDate()<10)?('0'+date.getDate()):(date.getDate()))+"日"+date.getHours()+":"+date.getMinutes();
			//alert(selectdate);
			var temppsday = date.getFullYear() + '-' + (((date.getMonth()+1)<10)?('0'+(date.getMonth()+1)):((date.getMonth()+1))) + '-' + ((date.getDate()<10)?('0'+date.getDate()):(date.getDate()));
			var datestring = date.getFullYear()+"/"+(((date.getMonth()+1)<10)?('0'+(date.getMonth()+1)):((date.getMonth()+1)))+"/"+((date.getDate()<10)?('0'+date.getDate()):(date.getDate()))+" "+temp.START+":00";
			var datetring  = date.getFullYear()+"/"+(((date.getMonth()+1)<10)?('0'+(date.getMonth()+1)):((date.getMonth()+1)))+"/"+((date.getDate()<10)?('0'+date.getDate()):(date.getDate()))+" "+temp.END+":00";
			if(dqdate.getTime()<new Date(datetring).getTime()){
				if(dqdate.getTime()<new Date(datetring).getTime()&&dqdate.getTime()>new Date(datestring).getTime()){
					s +="<td val="+psmoth+"&nbsp;["+weekday_zh[date.getDay()]+"]&nbsp;"+temp.START+"-"+temp.END+" day="+temppsday+" range="+temp.START+"-"+temp.END+" start="+temppsday+"/"+temp.START+" end="+temppsday+"/"+temp.END+" valtime=\""+temppsday+temp.START+"\" valname=\""+temp.DEFINENAME+"\"><a class=\"active\">可选</a></td>";
					$("#distribution_time a.btn_link").addClass("btn_active");
				}else{
				s +="<td val="+psmoth+"&nbsp;["+weekday_zh[date.getDay()]+"]&nbsp;"+temp.START+"-"+temp.END+" day="+temppsday+" range="+temp.START+"-"+temp.END+" start="+temppsday+"/"+temp.START+" end="+temppsday+"/"+temp.END+" valtime=\""+temppsday+temp.START+"\" valname=\""+temp.DEFINENAME+"\"><a>可选</a></td>";
				}
			}else{
				s +="        <td style=\"cursor: not-allowed;\"></td>";
			}
			date.setDate(date.getDate()+1);
		});
		//for(var j=0;j<shsjday;j++){
			
		//}
		s +="</tr>";
	});
	//for(var i=0;i<sjdarr.length;i++){
		
	//}
	s +="</table>";
	s +="<p>温馨提示：我们会努力按照您指定的时间配送，但因天气、交通等各类因素影响，您的订单有可能会有延误现象！</p>";
	s +="<div class=\"distribution_bottom\"><a id=\"timeSave\" onclick=\"timeSave();\">保存</a><a onclick=\"jboxClose();\">取消</a></div>";
	$("#distribution_time").append(s);
}

//数组去重复
function undulpicate(array){
	for(var i=0;i<array.length;i++) {
		for(var j=i+1;j<array.length;j++) {
			//注意 ===
			if(JSON.stringify(array[i])===JSON.stringify(array[j])) {
				array.splice(j,1);
				j--;
			}
		}
	}
	return array;
}
