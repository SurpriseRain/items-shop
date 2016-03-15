var extDate={};

extDate.init = function(o,cID,initField,hidefield,record,disabled) {
  extDate.write(o,cID,initField,hidefield,record,disabled);
}  

extDate.write = function(o,cID,initField,hidefield,record,disabled){
  var s = '<input id="time'+$(o).attr('id')+'" name="'+$(o).attr('id').split("d_")[1]+'" type="text" value=""/>';
  var value=record[cID]==undefined?"":record[cID];
  if(disabled==false){
	  s += '<script type="text/javascript">'; 
	  s += ' new Ext.form.DateField({' +
		   '    applyTo: "time' + $(o).attr('id')+'",' +
		   '    xtype: "datefield",';
	  	   if(!JL.isNull($(o).attr('extreadOnly'))){
	  		   s += '    readOnly: "' + $(o).attr('extreadOnly') + '",';
	  	   }
		   if($(o).attr('minValue')=='sysdate'){
			   s += '    minValue: "' + current(0,1) + '",';
		   }else{
			   s += '    minValue: "' + '",';
		   }
		   if($(o).attr('mrValue')==0){
			   s += '	value:"",';
		   }else if($(o).attr('mrValue')==1){
			   var time=$(o).attr('extformat') == 'Y-m-d H:i:s'?2:1;
			   var addDays=$(o).attr('addDays') == undefined||$(o).attr('addDays') == ''?0:$(o).attr('addDays');
			   s += '	value:"'+current(addDays,time)+'",';
		   }
	  s += '	timePicker: true,' + 
		   '    format: "'+$(o).attr('extformat')+'"';
	  if ($(o).attr('extformat') == 'Y-m-d H:i:s'){ 
	    s += ',menu:new DatetimeMenu()';
	  }
	  s += '});';
	  s += '</script>';
  }else{
	  s=s.replace('type="text"', 'type="text" disabled="disabled" value="'+value+'"');
  }
  
  if($(o).attr('childclass')!=undefined){
	  s=s.replace('type="text"', 'type="text" class="'+$(o).attr('childClass')+'" ');
  }
  
  if($(o).attr('childstyle')!=undefined){
	  s=s.replace('type="text"', 'type="text" style="'+$(o).attr('childStyle')+'" ');
  }
  
  $(o).html(s);
}

function current(addDay,time){ 
	var d=new Date();
	d.setDate(d.getDate()+addDay*1);//当前日期+几天
	var str=''; 
	var FullYear = d.getFullYear();
	var Month = d.getMonth()+1<10?'0'+(d.getMonth()+1):d.getMonth()+1;
	var Day = d.getDate()<10?'0'+d.getDate():d.getDate();
	var Hours = d.getHours()<10?'0'+d.getHours():d.getHours();
	var Minutes = d.getMinutes()<10?'0'+d.getMinutes():d.getMinutes();
	var Seconds = d.getSeconds()<10?'0'+d.getSeconds():d.getSeconds();
	str +=FullYear+'-'; //获取当前年份 
	str +=Month+'-'; //获取当前月份（0——11） 
	if(time==1){
		str +=Day;
	}else if(time==2){
		str +=Day+' '; 
		str +=Hours+':'; 
		str +=Minutes+':'; 
		str +=Seconds; 
	}
	return str; 
} 