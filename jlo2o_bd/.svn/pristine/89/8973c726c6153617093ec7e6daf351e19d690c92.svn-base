$(function(){
	
	  
});


var userRegister = function(){
	var userName = $("#userName").val();
	var userPwd =$("#userPwd").val();
	var qruserPwd = $("#qruserPwd").val();
	if(userName.length<6){
		alert("输入的用户名小于6位,请重新输入!");
		return;
	   }
	
	if(userPwd.length<6||userPwd.length>20){
		alert("输入的密码位数不正确,请重新输入!");
		return;
	}
	
	if(qruserPwd!=userPwd){
		alert("2次输入的密码不一致，请重新输入");
		return;
	}

		  
			var XmlData = {};
			XmlData["RYDM"]=userName;
			var url2 = pubJson.PcrmUrl+"/operatorAction/checkOperator.do?XmlData="+JSON.stringify(XmlData);		
			$.ajax({  
	             type:"post",  
	             url:encodeURI(url2), 
	             data:"",  
	             dataType:"json",  
	             cache: false,
	             success:function(data){
					 var returnVal = data.data;
		     	      if(returnVal){
					    if(returnVal.num == "1"){
					    	alert("用户已存在!");
					    	$("#userName").val("");
					    	return false;
					    }else{
						    var userInfo = {};
					        userInfo["XTCZY01"] = userName;
					        userInfo["XTCZY02"] = userPwd;
					        userInfo["ZCXX02"] = userName;
					        userInfo["GSLX"] ="43";
					        userInfo["SJLY"] = "1";
					        var url = pubJson.O2OUrl+"/Register/insertQuickRegister.action?XmlData="+JSON.stringify(userInfo)+"&t="+new Date().getTime()+"";
							var url1= pubJson.PcrmUrl+"/operatorAction/addOperator.do?XmlData="+JSON.stringify(userInfo)+"";
							$.ajax({  
					             type:"post",  
					             url:encodeURI(url), 
					             data:"",  
					             dataType:"json",  
					             cache: false,
					             success:function(data){
								
									 if(!data || data.state =="success"){
										 return;
									 }
		
									 var temp = data.ZCXX01;

								if (window.localStorage) {
	                              try {
	                            window.localStorage.setItem("ZCXX01", temp);
	                             } catch (e) {
						
	                             }
	                             }
									 
								$.ajax({  
					             type:"post",  
					             url:encodeURI(url1), 
					             data:"",  
					             dataType:"json",  
					             cache: false,
					             success:function(data){
					            		if (window.localStorage) {
				                              try {
				                            window.localStorage.setItem("register", userInfo);
				                           } catch (e) {
									
				                           }
				                          }
					            	 
					            	 alert("注册成功！");
					          		 location.href="/customer/qfy/app/index2.html";
					             },error:function(){
					                 alert("请检查网络_workflow!");
					             }
					         });
									 
					     
					             },error:function(){
					                 alert("请检查网络_o2o!");
					            }
					         });
			 
						
									return true;
								}
							}
	          		 
			             },error:function(){
			                 alert("请检查网络!");
			                 
			             }
			         });
			 
		    

};

var userLogin = function(){
	var loginUser =$("#loginUser").val();
	var loginPass =$("#loginPass").val();
	
	if(loginUser.length<6){
		alert("输入的用户名小于6位,请重新输入!");
		return;
	   }
	
	if(loginPass.length<6||loginPass.length>20){
		alert("输入的密码位数不正确,请重新输入!");
		return;
	}
	
    var o={};
    o["XTCZY01"] = loginUser;
    o["XTCZY02"] = loginPass;

	            if (window.localStorage) {
                if (window.localStorage.getItem("userInfo")) {
                
                }
			
            }

    var url = pubJson.O2OUrl+"/AppInterface/appLogin.action?json="+JSON.stringify(o)+"";
           $.ajax({  
             type:"post",  
             url:encodeURI(url), 
             data:"",  
             dataType:"json",  
             cache: false,
             success:function(data){
				
                 if(!data){
            	   return;
                 }
			    if (window.localStorage) {
                try {
                    window.localStorage.setItem("userInfo", data)
                     } catch (e) {
					
                   }
                 }

				if(data.STATE =="success"){
					
			    location.href="/customer/qfy/app/index2.html";
					
				}else{
					alert("用户名或者密码输入错误,请重新输入!")
					
				}

          
             },error:function(){
                 alert("请检查网络!");
                 
             }
         });
    
}

var a,b,c,aName,bName,cName;
    function upLoading1(obj,type){
	var file = obj.files[0]; //选择上传的文件
	if(type==1){
		aName = file.name;
	}else if(type==2){
		bName = file.name;
	}else if(type==3){
		cName = file.name;
	}
    var imgUrl = new FileReader();
    imgUrl.readAsBinaryString(file);
    $(imgUrl).load(function(){
		if(type==1){
		  a = this.result;	
		}else if(type==2){
		  b = this.result;	
		}else if(type==3){
		  c = this.result;
		}
	})
	}


var re2 = function(){
	var XmlData = {};
	var companyName = $("#companyName").val();
	var linkman = $("#linkman").val();
	var emailAddress = $("#emailAddress").val();
	var phone = $("#phone").val();
	if(companyName){
	//企业名称
 	XmlData["ZCXX02"]= companyName;
	}else{
		alert("公司名称不能为空!");
		return;
	}
	
	//联系人
	if(linkman){
 	  XmlData["ZCXX03"]=linkman;
	}else{
		alert("联系人不能为空!");
		return;
	}
	
	//邮箱
    if(emailAddress){
    XmlData["ZCXX09"]=emailAddress;
	}else{
		alert("邮箱不能为空!");
		return;
	}
	
	//手机
    if(phone){
      XmlData["ZCXX06"]=phone;
	  }else{
	alert("邮箱不能为空!");
		return;
	}
	
	//法人身份证正面：fssfzzm 
	XmlData["fssfzzm"] = a;
	
	 // 对应：fssfzzmFileName
	XmlData["fssfzzmFileName"] = aName;
	
	 //法人身份证反面：fssfzfm
	XmlData["fssfzfm"] = b;
	
    //对应：fssfzfmFileName
    XmlData["fssfzfmFileName"] = bName;
	
	//营业执照副本：yyzzfb
    XmlData["yyzzfb"] = c;
	
    //对应：yyzzfbFileName
    XmlData["yyzzfbFileName"] = cName;
	
	//用户名
	XmlData["XTCZY01"] = "huangjin";
	XmlData["SJLY"] = "1"
	
	 if (window.localStorage) {
        if (window.localStorage.getItem("ZCXX01")) {
            XmlData["ZCXX01"] = window.localStorage.getItem("ZCXX01");
     }
	 }
	
    XmlData["ZCXX07"] = "";
   	XmlData["ZCXX08"] = "";


 	var url = "/Register/updateRegister.action";
	
	$.ajax({  
	 type:"post",  
	 url:url, 
	 data:{XmlData:JSON.stringify(XmlData)}, 
	//dataType:"json",  
	 cache: false,
	 success:function(data){
		 alert(data)
		 var data=JSON.parse(data).data;
		 if(!data){
			 return;
		 }
		 
		 if(data.state=="success"){
			 
		  alert("注册成功!");
		 }else{
			alert("注册失败");
			 
		 }
		 
		
	},error:function(){

		alert("进error");

	 
	}
})
}





