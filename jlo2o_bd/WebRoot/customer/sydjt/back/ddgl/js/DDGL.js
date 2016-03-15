var MyCookie = $.cookie('userInfo');
var usercookie = JSON.parse(MyCookie)
	var Oper_DDGL = JL.JLForm(); 
	Oper_DDGL.setPlugin({
				"DDGL" : {
				"jlid" : "DefStaPer",
				"sqlid" : "com.jlsoft.o2o.sql.order.Srch_Order",
				"url"  : "/jlquery/selecto2o.action",
				"param" : {"ZCXX01":usercookie.ZCXX01,"W_DJZX02":$("#W_DJZX02").val(),"date_s":$("#date_s").val(),"date_e":$("#date_e").val()},
				"newclos" : "4",
				"multiPage" : "true",
				"columnsArr" : [
									{"property":"XSDD01","header":"订单号","type":"lable","width":"196px"},
									{"property":"SHR","header":"收货人","type":"lable","width":"100px"},
									{"property":"XSDD02","header":"订单价格","type":"lable","width":"60px"},
									{"property":"XSDDI05","header":"商品数量","type":"lable","width":"60px"},
									{"property":"W_DJZX02","header":"订单状态","type":"number","width":"70px"},
									{"property":"XSDD04","header":"订单时间","type":"lable","width":"160px"},
									{"property":"W_DJZX02","header":"操作","type":"ddsh","width":"30px"}
								],
				"afterUrl" : "/customer/sydjt/back/ddgl/Oper_DDGL_item.html?xsdd01="
				}
			});
	Oper_DDGL.setEvents(function() {
		Oper_DDGL.getTab().delegate("[name='date_s']", "click", function(event) {
			WdatePicker();
		});
		Oper_DDGL.getTab().delegate("[name='date_e']", "click", function(event) {
			WdatePicker();
		});
		Oper_DDGL.getTab().delegate("[name='seach']", "click", function(event) {
			/*DHD.resetDHD();
				var KHDZ = DHD.getPlugin()["KHDZ"];
				KHDZ["param"] = {"WLDW01":DHD.getTab().find("[name='WLDW01']").val()};
				DHD.setPlugin({"KHDZ":KHDZ});
				DHD.reloadPlugin("KHDZ", KHDZ);
				DHD01.removeAttr("disabled");
				$("input[name='CZY01']").val(userInfo.RYMC);
				*/
			var DDGL = Oper_DDGL.getPlugin()["DDGL"];
			
			DDGL["param"] = {"ZCXX01":usercookie.ZCXX01,"W_DJZX02":Oper_DDGL.getTab().find("[name='W_DJZX02']").val(),"date_s":Oper_DDGL.getTab().find("[name='date_s']").val(),"date_e":Oper_DDGL.getTab().find("[name='date_e']").val()};
			Oper_DDGL.reloadPlugin("DDGL",DDGL);
		});
	});
	Oper_DDGL.define({
		"initData":function(){
			var MyCookie = $.cookie('userInfo');
			 var usercookie = JSON.parse(MyCookie);
			 var XmlData=[{'sqlid':'com.jlsoft.o2o.sql.order.Srch_Order','QryType':'Report','dataType':'Json','ZCXX01':usercookie.ZCXX01,'W_DJZX02':$('#W_DJZX02').val(),'date_s':$('#date_s').val(),'date_e':$('#date_e').val()}];
			return visitGrid("/jlquery/selecto2o.action",JSON.stringify(XmlData));
		},
		"backFun":function(parameters){
			var DDGL = Oper_DDGL.getPlugin()["DDGL"];
			DDGL["param"] = {"ZCXX01":usercookie.ZCXX01,"W_DJZX02":Oper_DDGL.getTab().find("[name='W_DJZX02']").val(),"date_s":Oper_DDGL.getTab().find("[name='date_s']").val(),"date_e":Oper_DDGL.getTab().find("[name='date_e']").val()};
			Oper_DDGL.reloadPlugin("DDGL",DDGL);
		}
	});