var login = JL.JLForm();
	//var returnData = null;
	//var rememberUrl = $.getUrlParam("rememberUrl");
	login.setPlugin({
		});
	login.setEvents(function() {
		login.getTab().delegate("[name='tishi']", "click", function(event) {
			$(".prompt").attr("style",{"display":"block"});
		});
		login.getTab().delegate("[name='XTCZY02']", "keypress", function(event) {
			login.Password_onkeypress(event);
		});
		login.getTab().delegate("[name='login_userName']", "keypress", function(event) {
			login.userName_onkeypress(event);
		});
		login.getTab().delegate("[name='login']", "click", function(event) {
			var username = login.getTab().find("[name='login_userName']").val();
			var password = login.getTab().find("[name='XTCZY02']").val();
			if(username =="" || username==null){
				//$(".error").attr("style",{"display":"block"});
				login.getTab().find("[name='ts']").html('<div class="w12 message error"><i class="fa fa-times-circle"></i><span>用户名不能为空！</span></div>');
				return false;
			}
			var arr = [];
			var o = {};
			o["XTCZY01"] = username;
			o["XTCZY02"] = password;
			arr.push(o);
			var users=arr;
			var data = visitService("/UserLogin/login.action?XmlData="+JSON.stringify(arr));
			var loginState = data.state;
			if (loginState == 1) {
				//alert("用户名有误!");
				login.getTab().find("[name='ts']").html('<div class="w12 message error"><i class="fa fa-times-circle"></i><span>用户名错误！</span></div>');
				login.getTab().find("[name='login_userName']").focus();
				return false;
			} else if (loginState == 2) {
				//alert("密码错误!");
				login.getTab().find("[name='ts']").html('<div class="w12 message error"><i class="fa fa-times-circle"></i><span>密码错误！</span></div>');
				login.getTab().find("[name='XTCZY02']").focus();
				return false; 
			} else if (loginState == 3) {
				var userInfo = data.userInfo;
				//判断公司名称是否为空，为空调转到补充数据页面
				if((userInfo.ZCXX06=="" || userInfo.ZCXX06==null || userInfo.ZCGS01==2) && userInfo.ZCXX56=="0"){
					login.registerPerfect(username,userInfo.ZCXX01);
				}else if(userInfo.ZCGS01==3){
					//跳转到成功页面
					var url = pubJson.O2OUrl+"/customer/sydjt/register/registerPerfect2.html?ZCXX02="+escape(userInfo.ZCXX02);
					window.location.href=url;
				}else{
					//保存cookies值
					if (login.getTab().find("[id='safeSignCheck']").is(":checked")) {
						if (users != null) {
							var bool = false;
							for ( var i = 0; i < users.length; i++) {
									if (users[i].userName == $("#XTCZY01").val()) {
										bool = true;
										users[i].passWord = $("#XTCZY02").val();
									}
							}
							if (bool == false) {
								var o = {};
								o.userName = $("#XTCZY01").val();
								o.passWord = $("#XTCZY02").val();
								users.push(o);
							}
						} else {
							users = [];
							var o = {};
							o.userName = $("#XTCZY01").val();
							o.passWord = $("#XTCZY02").val();
							users.push(o);
						}
						$.cookie("users", null, {
							path : '/'
						});
						$.cookie("users", JSON.stringify(users), {
							expires : 90,
							path : '/'
						});
					}
					else{
						$.cookie("users", null, {
						path : '/'
						});
					};
					var updateDate = new Date();
					updateDate.setTime(updateDate.getTime() + 1 * 24 * 60 * 60* 1000); //超过1天,cookie需要重新更新
					userInfo.UPDATETIME = updateDate.getTime();
					$.cookie("userInfo", null, {path : '/'});
					$.cookie("userInfo", JSON.stringify(userInfo), {expires : 30,path : '/'});
					
					sessionStorage.setItem("userInfo",JSON.stringify(userInfo));
					//登录成功返回上页
					/*
					if (rememberUrl == "" || rememberUrl == null || rememberUrl == "null") {
					rememberUrl = "/customer/sydjt/index.html";
					$(".login").hide();
					$(".user_info").show();
					window.location.href = rememberUrl;
					}else{
					}
					*/
					history.go(-1);
				}
			}
		})
	});
	login.define({
			"returnData":null,
			"registerPerfect": function(rydm,zcxx01) {
				var url = pubJson.O2OUrl+"/customer/sydjt/register/registerPhone.html?XTCZY01="+rydm+"&ZCXX01="+zcxx01;
				window.location.href=url;
			},
			"Password_onkeypress": function(event){
				if(event.keyCode==13)  
					login.getTab().find("[name='login']").trigger("click");
			},
			"userName_onkeypress": function(event){
				if(event.keyCode==13)   
	    		$("#XTCZY02").focus();
			}
		});