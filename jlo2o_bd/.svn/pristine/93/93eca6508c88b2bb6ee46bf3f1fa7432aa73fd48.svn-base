var MyCookie = $.cookie('userInfo');
var usercookie = JSON.parse(MyCookie)
	var Oper_SYGGW = JL.JLForm(); 
	Oper_SYGGW.setEvents(function() {
		Oper_SYGGW.getTab().delegate("[name='serach']", "click", function(event) {
			if(Oper_SYGGW.getTab().find("select[name='DQXX01']").val()==""){
				alert("请选择地区");
				return false;
			}
			if(Oper_SYGGW.getTab().find("#date_s").val()==""||$("#date_e").val()==""){
				alert("请选择广告日期");
				return false;
			}
			if(Oper_SYGGW.getTab().find("dl.public_form>dd>ul.area>li.xuan").length==0){
				alert("请选择广告日期时间");
				return false;
			}
			Oper_SYGGW.initData();
		});
		Oper_SYGGW.getTab().delegate("[name='date_s']", "click", function(event) {
			WdatePicker();
		});
		Oper_SYGGW.getTab().delegate("[name='date_e']", "click", function(event) {
			WdatePicker();
		});
		Oper_SYGGW.getTab().delegate("[type=\"file\"]", "change", function(event) {
					var objUrl = getObjectURL(this.files[0]) ;
					$(this).attr("title",this.files[0].name)
					console.log("objUrl = "+objUrl) ;
					if (objUrl) {
						$(this).parent().find("img").attr("src", objUrl) ;
					}
					$(this).parent().find("i").remove();
					$(this).parent().find("img").show();
		});
		Oper_SYGGW.getTab().delegate("[name='tj']", "click", function(event) {
			var fileArray=[];
			var arr=$("input[class='modalInput']");
			for(var i=1;i<=arr.length;i++){
				fileArray.push("file0"+i);
			}
			var ggwlist=Oper_SYGGW.spellDetailsInput();
			if(ggwlist.length==0){
				alert("至少选择一个广告位");
				return false;
			}
			var XmlData=$("#form1").formToJson();//[0]["ggwsp"];
			//XmlData[0]["GGGL15"]=escape(XmlData[0].GGGL15);
			//var ggwsparr=XmlData[0].ggwsp;
			//alert(JSON.stringify(ggwsparr));
			delete XmlData[0].ggwsp;
			XmlData[0]["ggwsp"]=ggwlist;
			console.log(JSON.stringify(XmlData));
			//?XmlData="+JSON.stringify(XmlData)
			var url="/oper_GGGL/insertGGGL.action";
			var spjs="1";
			$.ajaxFileUpload({
				type:"POST",
				secureuri:false,
 					fileElementId:fileArray,
				url:encodeURI(url),////encodeURI避免中文乱码
				dataType:"text",
				data:{"spjs":spjs,"XmlData":escape(JSON.stringify(XmlData))},
				success: function(data) { 
					//关闭遮罩层
					top.closeWait();
					
				var json = jQuery.parseJSON(data);
				var jsondata = json.data;
				var data1=jsondata.STATE;
				//alert(jsondata.STATE);
				if(data1==1){
					alert("保存成功!");
					//location.reload();
					/*clearData();
					if(SPXX01!=null){
						parent.execBackFun("aaa");
						parent.$("#ajaxGrid").remove();
					}*/
				}else{
					alert("保存失败!");
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					//关闭遮罩层
					top.closeWait();
					alert(textStatus);
					//alert(JSON.stringify(XMLHttpRequest)+"  ++ "+textStatus+"  ++  "+errorThrown);
			    }
			});
		});
		Oper_SYGGW.getTab().delegate("[name='seach']", "click", function(event) {
			/*DHD.resetDHD();
				var KHDZ = DHD.getPlugin()["KHDZ"];
				KHDZ["param"] = {"WLDW01":DHD.getTab().find("[name='WLDW01']").val()};
				DHD.setPlugin({"KHDZ":KHDZ});
				DHD.reloadPlugin("KHDZ", KHDZ);
				DHD01.removeAttr("disabled");
				$("input[name='CZY01']").val(userInfo.RYMC);
				*/
			var DDGL = Oper_SYGGW.getPlugin()["DDGL"];
			
			DDGL["param"] = {"ZCXX01":usercookie.ZCXX01,"W_DJZX02":Oper_SYGGW.getTab().find("[name='W_DJZX02']").val(),"date_s":Oper_SYGGW.getTab().find("[name='date_s']").val(),"date_e":Oper_SYGGW.getTab().find("[name='date_e']").val()};
			Oper_SYGGW.reloadPlugin("DDGL",DDGL);
		});
	});
	Oper_SYGGW.define({
		"init":function(){
			$("dl.public_form>dd>ul.area>li").each(function(i,val){
				$(val).find("span").bind("click",function(j,e){
					$("dl.public_form>dd>ul.area>li").removeClass("xuan");
					$(this).parent().addClass("xuan");
					$("#date_s").attr("sjqj",$(this).parent().data("sjqj").split("-")[0]);
					$("#date_e").attr("sjqj",$(this).parent().data("sjqj").split("-")[1]);
				})
			});
		},
		"initData":function(){
			var MyCookie = $.cookie('userInfo');
			var usercookie = JSON.parse(MyCookie);
			//??????
			//Oper_SYGGW.getTab().find("input[name='GGGL09']").val(usercookie.XTCZY01);
			$("input[name='GGGL09']").val(usercookie.XTCZY01);
			//????????????????
		$("div.ggtype").each(function(i,val){
			var XmlData=[];
			var json={};
			json["DQXX01"]=$("select[name='DQXX01']").val();//$("#city_value").val();
			json["ZCXX01"]='0000';
			json["GGLX01"]=$(val).data("gglx");
			json["date_s"]=$("input[name='date_s']").val();//+" "+$("input[name='date_s']").attr("sjqj");
			json["date_e"]=$("input[name='date_e']").val();//+" "+$("input[name='date_e']").attr("sjqj");
			json["sjqj_s"]=$("input[name='date_s']").attr("sjqj");
			json["sjqj_e"]=$("input[name='date_e']").attr("sjqj");

			XmlData.push(json)
			//alert(JSON.stringify(XmlData));
			eval("Oper_SYGGW."+$(val).data("ggtype")+"("+JSON.stringify(XmlData)+");");
		});	
		},"LBT":function(XmlData){
			/*var e=$("div.ggtype[data-ggtype='LBT'][data-gglx='"+XmlData[0].GGLX01+"']");
			var url="/oper_GGGL/selectGGWs.action?XmlData="+JSON.stringify(XmlData);
			ajaxQ({
				   "url" : url,
				   "callback" : function(rData){
					   var ggws=rData.mapList;
					   $(e).empty();
					   $.each(ggws,function(i,val){
							$(e).append('<ul class="banner_main" data-type=LBT_'+val.GGLXZ03+'></ul><ul class="banner_nav"></ul>');
							var str1 = '';
							var str2 = '';
							$.each(val.leftggwsp,function(j,ggw){
								str1='<li class="w12 color_01 xuan" title="不可以投放广告"><i class="fa fa-times-circle-o"></i></li>';
								$(e).find("div[data-type='LBT_"+val.GGLXZ03+"']").find("ul.banner_main").append(str1);
								str2='<li class="w02 xuan"><span>1</span></li>';
								$(e).find("div[data-type='LBT_"+val.GGLXZ03+"']").find("ul.banner_nav").append(str2);
							})
					   })
				   },
				   "error":function(){}
			})*/

		},"LCFL":function(XmlData){
			var e=$("div.ggtype[data-ggtype='LCFL'][data-gglx='"+XmlData[0].GGLX01+"']");
			var url="/oper_GGGL/selectGGWs.action?XmlData="+JSON.stringify(XmlData);
			ajaxQ({
				   "url" : url,
				   "callback" : function(rData){
					    var ggws=rData.mapList;
						/*$(e).append('<div class="w12 adv_index_floor" data-type=LCFL_'+ggws[0].GGLXZ03+'>'+
							'<h3 class="w12">'+ggws[0].GGLXZ03+'F '+ggws[0].GGLXZ02+'</h3>'+
							'<div class="w12 adve_main"></div></div>');*/
						$(e).empty();
						$.each(ggws,function(i,val){
							$(e).append('<div class="w12 adv_index_floor" data-type=LCFL_'+val.GGLXZ03+'>'+
							'<h3 class="w12">'+val.GGLXZ03+'F '+val.GGLXZ02+'</h3>'+
							'<div class="w12 adve_main"></div></div>');
							console.log(val.rightggwsp);
							var str1 = '';
							$.each(val.leftggwsp,function(j,ggw){
								if(j==0){
									//data-index="'+j+'" data-code="'+ggw.GGW01+'" data-data='+JSON.stringify(ggw)+'
										if(ggw.SPMC!=null){
											if(ggw.SQR==usercookie.XTCZY01){
												str1+='<div class="w03 floor_adve color_02 ggw" data-index="'+j+'" data-code="'+ggw.GGW01+'" data-data=\''+JSON.stringify(ggw)+'\' title="\u6211\u6295\u653e\u7684\u5e7f\u544a">';
												str1+='<i class="fa fa-times-circle-o"></i>';
											}else{
												str1+='<div class="w03 floor_adve color_01 ggw" data-index="'+j+'" data-code="'+ggw.GGW01+'" data-data=\''+JSON.stringify(ggw)+'\' title="\u4e0d\u53ef\u4ee5\u6295\u653e\u5e7f\u544a">';
												str1+='<i class="fa fa-times-circle-o"></i>';
											}
										}else{
											str1+='<div class="w03 floor_adve color_03 ggw" data-index="'+j+'" data-code="'+ggw.GGW01+'" data-data=\''+JSON.stringify(ggw)+'\' title="\u53ef\u4ee5\u6295\u653e\u5e7f\u544a">';
											str1+='<i class="fa fa-check-circle-o"></i>';
										}
									str1 +='</div><ul class="w09 floor_pro"></ul>';
									console.log(str1);
									$(e).find("div[data-type='LCFL_"+val.GGLXZ03+"']").find("div.adve_main").append(str1);
								}else{
									var str='<li class="w03">';
										if(ggw.SPMC!=null){
											if(ggw.SQR==usercookie.XTCZY01){
												str+='<div class="w12 pro_main color_02 ggw" data-index="'+j+'" data-code="'+ggw.GGW01+'" data-data=\''+JSON.stringify(ggw)+'\' title="\u6211\u6295\u653e\u7684\u5e7f\u544a">';
												str+='<i class="fa fa-clock-o"></i>';
											}else{
												str+='<div class="w12 pro_main color_01 ggw" data-index="'+j+'" data-code="'+ggw.GGW01+'" data-data=\''+JSON.stringify(ggw)+'\' title="\u4e0d\u53ef\u4ee5\u6295\u653e\u5e7f\u544a">';
												str+='<i class="fa fa-times-circle-o"></i>';
											}
										}else{
											str+='<div class="w12 pro_main color_03 ggw" data-index="'+j+'" data-code="'+ggw.GGW01+'" data-data=\''+JSON.stringify(ggw)+'\' title="\u53ef\u4ee5\u6295\u653e\u5e7f\u544a">';
											str+='<i class="fa fa-check-circle-o"></i>';
										}
										str+='</div>';
										str+='</li>';
									$(e).find("div[data-type='LCFL_"+val.GGLXZ03+"']").find("div.adve_main").find("ul.floor_pro").append(str);
								}
								$(e).find("div[data-type='LCFL_"+val.GGLXZ03+"']").find("div.ggw[data-code="+ggw.GGW01+"]").bind('click', function(){
									var date1=new Date($(this).data("data").GGQJ02);
									var date2=new Date($(this).data("data").GGQJ03);
									var str=date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate()+" "+date1.getHours()+"-"+date2.getHours();
									if($(this).hasClass("color_01")){//???λ?????
										return false;
									}/*else if($(this).hasClass("color_02")){//?????λ
										//$(this).removeClass("color_02");
										$(this).removeClass("bian");
										//$(this).find("i").removeClass("fa-times-circle-o");
										//$(this).addClass("color_03");
										//$(this).find("i").addClass("fa-check-circle-o");
										$(".adv_jg").find("ul").eq(0).find("li").eq(1).find("dl[data-code="+ggw.GGW01+"]").remove();
									}*/else if(/*$(this).hasClass("color_03")&&*/$(this).hasClass("bian")){
										$(this).removeClass("bian");
										$(".adv_jg").find("ul").eq(0).find("li").eq(1).find("div.adv_sol").find("dl[data-code="+ggw.GGW01+"]").remove();
									}else if(/*$(this).hasClass("color_03")&&*/!$(this).hasClass("bian")){//??????λ
										//$(this).removeClass("color_03");
										//$(this).find("i").removeClass("fa-times-circle-o");
										$(this).addClass("bian");
										//$(this).find("i").addClass("fa-check-circle-o");
										var json={};
										var json1={};
										json=val;
										json1=val;
										delete json["leftggwsp"];
										//delete json1["leftggwsp"];
										$.extend(json,ggw);
										/*$(".adv_jg").find("ul").eq(0).find("li").eq(1).find("div.adv_sol").append('<dl class="w12 public_table_main" data-code='+ggw.GGW01+' data-data=\''+JSON.stringify(json)+'\'>'+
										'<dt class="w01"><input type="checkbox" name="ggwcheck"><input type="hidden" name="ggwsp" value=\''+JSON.stringify(json)+'\'></input></dt>'+
										'<dd class="w03">'+str+'</dd>'+
										'<dd class="w02">'+val.GGLXZ03+'F-'+(j+1)+'</dd>'+
										'<dd class="w02">有</dd>'+
										'<dd class="w02">3,999.00</dd>'+
										'<dd class="w02"><i class="fa fa-trash-o"></i><i class="fa fa-eye"></i>'+
										'</dd></dl>');
										$(".adv_jg").find("ul").eq(0).find("li").eq(1).find("div.adv_sol").find("dd>i.fa-trash-o").bind('click',function(){
											$(this).parent().parent().remove();
										});*/
										var XmlData1=[];
										json["date_s"]=$("input[name='date_s']").val();
										json["date_e"]=$("input[name='date_e']").val();
										json["sjqj_s"]=$("input[name='date_s']").attr("sjqj");
										json["sjqj_e"]=$("input[name='date_e']").attr("sjqj");
										XmlData1.push(json);
										var url="/oper_GGGL/selectRightGGWs.action?XmlData="+JSON.stringify(XmlData1);
										ajaxQ({
											   "url" : url,
											   "callback" : function(rData){
													var ggws=rData.mapList;
													$.each(ggws,function(index,temp){
														console.log("################"+JSON.stringify(temp));
														var date1=new Date(temp.GGQJ02);
														var date2=new Date(temp.GGQJ03);
														var KC="有";
														if(temp.SPMC!=null){
															KC="无";
														}
														var str=date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate()+" "+date1.getHours()+"-"+date2.getHours();
														$.extend(json,temp);
														$(".adv_jg").find("ul").eq(0).find("li").eq(1).find("div.adv_sol").append('<dl class="w12 public_table_main" data-code='+temp.GGW01+' data-data=\''+JSON.stringify(json)+'\'>'+
															'<dt class="w01"><input type="checkbox" name="ggwcheck"><input type="hidden" name="ggwsp" value=\''+JSON.stringify(json)+'\'></input></dt>'+
															'<dd class="w03">'+str+'</dd>'+
															'<dd class="w02">'+val.GGLXZ03+'F-'+(j+1)+'</dd>'+
															'<dd class="w02">'+KC+'</dd>'+
															'<dd class="w02">3,999.00</dd>'+
															'<dd class="w02"><i class="fa fa-trash-o"></i><i class="fa fa-eye"></i>'+
															'</dd></dl>');
														$(".adv_jg").find("ul").eq(0).find("li").eq(1).find("div.adv_sol").find("dd>i.fa-trash-o").bind('click',function(){
															$(this).parent().parent().remove();
														});
														$(".adv_jg").find("ul").eq(0).find("li").eq(1).find("div.adv_sol").find("dd>i.fa-eye").bind('click',function(){
															var GGLXZ01=$(this).parent().parent().data("data").GGLXZ01;
															$(e).find("div[data-type='LCFL_"+GGLXZ01+"']").find("div.ggw").removeClass("bian");
															$(e).find("div[data-type='LCFL_"+GGLXZ01+"']").find("div.ggw[data-code="+$(this).parent().parent().data("code")+"]").addClass("bian");
														});
													});
											   },
											   "error":function(){}
										})
									}
									
								})
							})
							/*if(i==0){
								$(e).find("div [data-type=LCFL_"+ggw.GGLXZ03+"]").find("div.adve_main").append(
								'<div class="w03 floor_adve color_03" title="?????????">'+
								'<i class="fa fa-check-circle-o"></i>'+
								'</div><ul class="w09 floor_pro"></ul>');
							}else{
								$(e).find("div [data-type=LCFL_"+ggw.GGLXZ03+"]").find("div.adve_main").find("ul.floor_pro").append(
									'<li class="w03">'+
										'<div class="w12 pro_main color_03" title="?????????">'+
											'<i class="fa fa-check-circle-o"></i>'+
										'</div>'+
									'</li>')
							}*/
						});
				   },
				   "error":function(){}
				 });
		},
		"spellDetailsInput":function(){
			var XmlData=[];
			$("input[name='ggwcheck']:checked").each(function(i,val){
				XmlData.push($(val).parent().find("input[name='ggwsp']").val());
				
			});
			return XmlData;
		},
		"backFun":function(parameters){
			var DDGL = Oper_SYGGW.getPlugin()["DDGL"];
			DDGL["param"] = {"ZCXX01":usercookie.ZCXX01,"W_DJZX02":Oper_SYGGW.getTab().find("[name='W_DJZX02']").val(),"date_s":Oper_SYGGW.getTab().find("[name='date_s']").val(),"date_e":Oper_SYGGW.getTab().find("[name='date_e']").val()};
			Oper_SYGGW.reloadPlugin("DDGL",DDGL);
		}
	});
Oper_SYGGW.init();
