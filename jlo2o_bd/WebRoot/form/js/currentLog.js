var userInfoLog=JSON.parse($.cookie("userInfo"));
var rydmLog = userInfoLog.XTCZY01;
function showLC(XMBH,GZLBH,BZBH,RZBH){	   
	   var url=pubJson.PcrmUrl+'/document/getListLogger.do?XmlData={"LX":"FORM","XMBH":"'+XMBH+'","GZLBH":"'+GZLBH+'","RZBH":'+RZBH+'}';
	   var returnVal = visitService(url);
	   var returnStr = returnVal.resultList;
	   
	   window.parent.$(".userLc").empty();
	   var curActionNum=0;
	   var tempCurAction="";
	   var num=0,m=0,n=0,k=-1;
	   var str ='<div class="userlcLeft" title="上一步流程"></div>';
	         str +='<div class="userlcMain">';
	         str +='<div class="userlcZhe">';
	   var str1="",str2="",str3="",str4="";
	   /*************************循环获取流程 开始*************************************************/
	   for(var i=0;i<returnStr.length;i++){
	     var LX=returnStr[i].LX;
	     if(i==0){
	       if(LX==-1){
	           str += '<div id="LC'+returnStr[i].BZBH+'" class="userlcCase"><a class="fxlc_yz" title="（岗位:'+returnStr[i].GWFZMC+'，处理人：'+returnStr[i].CLRYMC+'，处理时间：'+returnStr[i].CLSJ+'）">'+returnStr[i].BZMC+'</a><span>→</span></div>';
	       }else if(LX==1){
	    	   str += '<div id="LC'+returnStr[i].BZBH+'" class="userlcCase"><a class="fxlc_zz" title="（进行到这一步）">'+returnStr[i].BZMC+'</a><span>→</span></div>';
	    	   k=0;
	       }else{
	         str += '<div id="LC'+returnStr[i].BZBH+'" class="userlcCase"><a class="fxlc_yz">'+returnStr[i].BZMC+'</a><span>→</span></div>';
	       }
	     }else if((curActionNum==1 && LX==0) || LX !=0){
	       if(LX==1){
	         str += '<div id="LC'+returnStr[i].BZBH+'" class="userlcCase"><a class="fxlc_wz">'+returnStr[i].BZMC+'（岗位:'+returnStr[i].GWFZMC+'）</a><span>→</span></div>';
	         num=i;
	       }else{
	         if(LX==-1){
	           str += '<div id="LC'+returnStr[i].BZBH+'" class="userlcCase"><a class="fxlc_yz" title="（岗位:'+returnStr[i].GWFZMC+'，处理人：'+returnStr[i].CLRYMC+'，处理时间：'+returnStr[i].CLSJ+'）">'+returnStr[i].BZMC+'</a><span>→</span></div>';
	         }else{
	           str += '<div id="LC'+returnStr[i].BZBH+'" class="userlcCase"><a class="fxlc_zz" title="（进行到这一步）">'+returnStr[i].BZMC+'</a>';
	           
	           /*************************循环获取行为 开始************************************************/
	           var url2=pubJson.PcrmUrl+'/document/viewWorkflowFirstStep.do?XmlData={"XMBH":"'+XMBH+'","GZLBH":"'+GZLBH+'","BZBH":"'+returnStr[i].BZBH+'","RZBH":'+RZBH+',"PI_USERNAME":"'+rydmLog+'"}';
	           var returnVal = visitService(url2).listWorkflowStepAction;
	                     str += '<div class="fxlc_main">';
	 					    str +=    '<div class="lj"></div>';
	 					    str +=       '<div class="fxlc_close" title="关闭">×</div>';
	                     for(var j=0;j<returnVal.length;j++){
	                         str +=         '<a href="javascript:void(0);" id="xwmc_'+returnVal[j].xwbh+'" onclick="changePage('+returnVal[j].gzlbh+','+returnVal[j].xwbh+','+returnVal[j].ywdh+','+returnVal[j].rzbh+')">'+returnVal[j].xwmc+'</a>';//onclick="changePage('+XMBH+','+returnVal[j].gzlbh+','+returnVal[j].xwbh+','+returnVal[j].ywdh+','+returnStr[i].BZBH+','+returnVal[j].rzbh+')"
	    					}
	    					str += '</div>';
	           /***********************循环获取行为 结束*************************************************/
	           str += '<span>→</span></div>';
	         }
	         
	       }
	     }
	  
	     //现在记录，多条行为显示多个radio
	     if(LX==0 || i==0 ){
	       if( returnStr[i].GDBZBH > 0){
	 		if(curActionNum==0 ){
	 		  tempCurAction="<div>&nbsp;&nbsp;&nbsp;<input type='radio' name='curAction' id='xw"+returnStr[i].GDBZBH+"' value='"+returnStr[i].XWBH+"' checked='checked'>"+returnStr[i].XWMC;
	 		}else{
	 		  tempCurAction=tempCurAction+"&nbsp;<input type='radio' name='curAction' id='xw"+returnStr[i].GDBZBH+"' value='"+returnStr[i].XWBH+"'>"+returnStr[i].XWMC;
	 		}
	 	  }
	       curActionNum++;
	     }
	     //最近historylog序号
	 	if(i>0 && LX==0){
	 			  var lx0=returnStr[i-1].LX;
	 			 if(lx0==-1){ 
	     		 	bzbh=returnStr[i].BZBH;
	     		 	m=i;
	     		 }
	     	  }
	     //str += str3;
	     if(LX==1){
	 	  //只有一条行为，不显示radio
	 		if(i>0 && returnStr[i-1].LX==0){
	 		  n=i;
	 		  if(curActionNum>0 && tempCurAction !=""){  
	 			// str += '<li><a class="fxlc_yz">"可选行为：'+tempCurAction+'"</a>';
	 		  }
	 			 curActionNum=0;
	 		}
	 	}
	     
	     //str += '</li>';
	   
	   }
	       
	   /*************************循环获取流程 结束*************************************************/
	   str += '</div></div>';
	     str+= '<div class="userlcRight" title="下一步流程"></div>';
	   //str += '</div>';
	   window.parent.$(".userLc").append(str);
	   /*******************************只显示当前步骤前后各2步  开始**************************************/
	       for(var i=0;i<returnStr.length;i++){
	    	 if(k==0){
	     	   if(i>(n+2) || (m>2 && i<m-2)){
	     		 window.parent.$("#LC"+returnStr[i].BZBH).css({"display":"none"});
	     		 window.parent.$("#LC"+returnStr[i].BZBH).prev().children("span").css({"display":"none"});
	 		   }
	         }else if(k==-1){
	           if(i>(n+1) || (m>2 && i<m-2)){
		         window.parent.$("#LC"+returnStr[i].BZBH).css({"display":"none"});
		     	 window.parent.$("#LC"+returnStr[i].BZBH).prev().children("span").css({"display":"none"});
		 	   }
	 		 }
	       }
	   /*******************************只显示当前步骤前后各2步 结束**************************************/
	     window.parent.$(".userlcZhe").children("div:last").children("span").css({"display":"none"});//隐藏多余的'→'
	     /******************点击上一步、下一步、关闭层 事件 开始*******************************************/
	     window.parent.$(".userlcRight").click(function(){
	 	   window.parent.$(".userlcZhe").animate({"margin-left":"-400px"});		
	     })
	 	 window.parent.$(".userlcLeft").click(function(){
	 	   window.parent.$(".userlcZhe").animate({"margin-left":"0"});		
	     })
	     window.parent.$(".fxlc_close").click(function(){
	       window.parent.$(".fxlc_main").fadeOut(100);		
	     })
	 	 window.parent.$(".fxlc_zz").click(function(){
	 	   window.parent.$(".fxlc_zz").next(".fxlc_main").fadeIn(100);		
	     })
	     /******************点击上一步、下一步、关闭层 事件 结束*******************************************/	     
}

	 