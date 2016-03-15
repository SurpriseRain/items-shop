var bmxx = 0;
var saveArray = [];
var obj;// 当前input对象 ...$("#ppfl")
var hidobj;// 隐藏框对象,为后台传值
var index;// div框的下标

function jl_add_bmmc_jl(o, str, n) {
	
	if(obj!=$("#" + o)){
		saveArray=[];
	}
	
	obj = $("#" + o);
	hidobj = $("#" + str);
	index = n;
	clear();

	/*var arr = [];
	var o = {};
	o["cd"] = '2';
	arr.push(o);*/
	var data = selectBMInfomation();
	var bm00 = showBM(data, 'bm_01');// 查询第一级的部门
	// 初次加载
	initlist();
	if (obj.attr('jl_add') == '0') {
		obj.attr('jl_add', '1');
		$("#jl_bmxx" + index)
				.append(
						'<ul id="bm00" class="bm00">'
								+ bm00
								+ '</ul>'
								+ '<ul id="bm01" class="bm01"></ul>'
								+ '<ul id="bm02" class="bm02"></ul>'
								+ '<ul id="bm03" class="bm03"></ul>'
								+ '<div class="cz"><div class="yxzbm">您当前选择：</div>'
								+ '<div class="bm"></div><a  href="javascript:void(0);" class="button" id="return_bm" onclick="return_bm()">确定</a></div>');
	} else {
		$(".bm00").append(bm00);
		//initlist();
		showdiv();
	}
	$("#jl_bmxx" + index).css( {
		"display" : "block"
	});
	obj.parent("td").mouseleave(function() {
		$(this).children(".tcc").css( {
			"display" : "none"
		});
	})
	$(".cz > .button").click(function() {
		$(this).parent(".cz").parent(".tcc").css( {
			"display" : "none"
		});
	})
}

function initlist(){
	if(hidobj.val()!=null){
		// 加载数组 从INPUT里面将字串加载到数组中
		var dm = hidobj.val().split(',');
		var mc = obj.val().split(',');
		//var ooo={"bmmc":"","bmdm":""};
		for ( var int = 0; int < mc.length; int++) {
			var ooo={"bmmc":"","bmdm":""};
			if(dm != null && dm !=''){//因为首次加载时input框有一个空值
				ooo.bmmc=mc[int];
				ooo.bmdm=dm[int];
				saveArray[int]=ooo;
			}
			else{}
		}
	}
}

function return_bm() {
	var bmmc;
	var bmdm;
	len = saveArray.length;
	var list = [];
	var n = 0;
	for ( var i = 0; i < len; i++) {
		if (saveArray[i].bmmc != null && saveArray[i].bmmc != 'null') {
			list[n++] = saveArray[i];
		}
	}
	for ( var j = 0; j < list.length; j++) {
		if (j == 0) {
			bmdm = list[j].bmdm;
			bmmc = list[j].bmmc;
		} else {
			bmdm += ("," + list[j].bmdm);
			bmmc += ("," + list[j].bmmc);
		}
	}
	hidobj.val(bmdm);
	obj.val(bmmc);
}

function bm_01(dm) {
	// 查询第二级的部门
	/*var arr = [];
	var o = {};
	o["cd"] = '4';
	o["dm"] = '';
	arr.push(o);*///以后写通用查询时要用到
	var bm01 = null;
	var data = selectBMInfomation();
	$(data).each(function(i,obj){
		if(obj.FLCODE==dm){
			bm01 = showBM(obj.PPFL02list, 'bm_02');
		}
	
	})
	$(".bm01").empty();
	$(".bm02").empty();
	$(".bm03").empty();
	$(".bm01").append(bm01);
}

function bm_02(dm) {
	// 查询第三级的部门
	/*var arr = [];
	var o = {};
	o["cd"] = '6';
	o["dm"] = dm;
	arr.push(o);*/
	var bm02 = null;
	var data = selectBMInfomation();
	$(data).each(function(i,obj){
		$(obj.PPFL02list).each(function(j,obj2){
			if(obj2.FLCODE==dm){
				bm02 = showBM(obj2.PPFL03list, 'bm_03');
			}
		})	
	})
	$(".bm02").empty();
	$(".bm03").empty();
	$(".bm02").append(bm02);
}

function bm_03(dm) {
	// 查询第四级的部门
	/*var arr = [];
	var o = {};
	o["cd"] = '8';
	o["dm"] = dm;
	arr.push(o);*/
	var bm03 = null;
	var data = selectBMInfomation();
	$(data).each(function(i,obj){
		$(obj.PPFL02list).each(function(j,obj2){
			$(obj2.PPFL03list).each(function(j,obj3){
				if(obj3.FLCODE==dm){
					bm03 = showBM(obj3.SPFL04List, '');
				}
			})	
		})	
	})
	$(".bm03").empty();
	$(".bm03").append(bm03);
}

function check(dm,nowobj) {
	var saveo = {};
	var h;
	var $nowobj=$(nowobj);
	saveo["bmdm"] = dm;
	saveo["bmmc"] = $nowobj.next().html();
	if ($nowobj.attr("checked") == 'checked') {
		// 单选
		if (obj.attr('jl_xz') == 0) {
			len = saveArray.length;
			for ( var i = 0; i < len; i++) {
				if (saveArray[i]['bmdm'] != null) {
					$(".bm_" + saveArray[i]['bmdm']).prop("checked", false); ;
				}
				saveArray[i]['bmdm'] = null;
				saveArray[i]["bmmc"] = null;
			}
		}
		saveArray.push(saveo);
	} 
	else {
		len = saveArray.length;
		for ( var i = 0; i < len; i++) {
			if (saveArray[i]['bmdm'] == dm) {
				saveArray[i]['bmdm'] = null;
				saveArray[i]["bmmc"] = null;
			}
		}
	}
	showdiv();
}

function del_check(dm) {
	var span_text = '';
	len = saveArray.length;
	for ( var i = 0; i < len; i++) {
		if (saveArray[i]['bmdm'] == dm) {
			saveArray[i]['bmdm'] = null;
			saveArray[i]["bmmc"] = null;
		}
	}
	$(".bm_" + dm).prop("checked", false);;
	showdiv();
}

function showBM(data, ckname) {
	var sbm = '';
	var g = 0;
	len = saveArray.length;
	for ( var i = 0; i < data.length; i++) {
		var bmdmi=data[i].FLCODE;
		g = 0;
		if (len == 0) {
			if (sbm == '') {
				sbm = '<li><input type="checkbox" class="bm_' + bmdmi
						+ '" onclick="check(\'' + bmdmi + '\',this)"/>'
						+ '<span class="bmmc_' + bmdmi + '" onclick="'
						+ ckname + '(\'' + bmdmi + '\',this)" title="'
						+ data[i].FLNAME + '">' + data[i].FLNAME + '</span></li>';
			} else {
				sbm = sbm + '<li><input type="checkbox" class="bm_' + bmdmi
						+ '" onclick="check(\'' + bmdmi + '\',this)"/>'
						+ '<span class="bmmc_' + bmdmi + '" onclick="'
						+ ckname + '(\'' + bmdmi + '\',this)" title="'
						+ data[i].FLNAME + '">' + data[i].FLNAME + '</span></li>';
			}
		} else {
			for ( var j = 0; j < len; j++) {
				if (saveArray[j]['bmdm'] != null) {
					if (sbm == '') {
						if (saveArray[j]['bmdm'] == bmdmi) {
							g = 1;
							sbm = '<li><input type="checkbox" class="bm_'
									+ bmdmi + '" onclick="check(\''
									+ bmdmi + '\',this)" checked="checked"/>'
									+ '<span class="bmmc_' + bmdmi
									+ '" onclick="' + ckname + '(\''
									+ bmdmi + '\',this)" title="'
									+ data[i].FLNAME + '">' + data[i].FLNAME
									+ '</span></li>';
						}
					} 
					else {
						if (saveArray[j]['bmdm'] == bmdmi) {
							g = 1;
							sbm = sbm + '<li><input type="checkbox" class="bm_'
									+ bmdmi + '" onclick="check(\''
									+ bmdmi + '\',this)" checked="checked"/>'
									+ '<span class="bmmc_' + bmdmi
									+ '" onclick="' + ckname + '(\''
									+ bmdmi + '\',this)" title="'
									+ data[i].FLNAME + '">' + data[i].FLNAME
									+ '</span></li>';
						}
					}
				}
			}
			if (g == 0) {
				if (sbm == '') {
					sbm = '<li><input type="checkbox" class="bm_' + bmdmi
							+ '" onclick="check(\'' + bmdmi + '\',this)"/>'
							+ '<span class="bmmc_' + bmdmi + '" onclick="'
							+ ckname + '(\'' + bmdmi + '\',this)" title="'
							+ data[i].FLNAME + '">' + data[i].FLNAME
							+ '</span></li>';
				} else {
					sbm = sbm + '<li><input type="checkbox" class="bm_'
							+ bmdmi + '" onclick="check(\''
							+ bmdmi + '\',this)"/>' + '<span class="bmmc_'
							+ bmdmi + '" onclick="' + ckname + '(\''
							+ bmdmi + '\',this)" title="' + data[i].FLNAME
							+ '">' + data[i].FLNAME + '</span></li>';
				}
			}
		}
	}
	return sbm;
}

function clear() {
	len = saveArray.length;
	for ( var i = 0; i < len; i++) {
		if (saveArray[i]['bmdm'] = null) {
			$(".bm_" + saveArray[i]['bmdm']).prop("checked",false);
		}
		saveArray[i]['bmdm'] = null;
		saveArray[i]["bmmc"] = null;
	}

	$(".bm00").empty();
	$(".bm01").empty();
	$(".bm02").empty();
	$(".bm03").empty();
}

function showdiv() {
	var span_text = '';
	len = saveArray.length;
	$(".cz > .bm").empty();
	for ( var i = 0; i < len; i++) {
		if (saveArray[i]["bmdm"] != null) {
			if (span_text == null ||span_text == '') {
				span_text = '<a>' + saveArray[i]["bmmc"]
						+ '<label title="删除" onclick="del_check(\''
						+ saveArray[i]["bmdm"] + '\')">×</label></a>';
			} 
			else {
				span_text = span_text + '<a>' + saveArray[i]["bmmc"]
						+ '<label title="删除" onclick="del_check(\''
						+ saveArray[i]["bmdm"] + '\')">×</label></a>';
			}
		}
	}
	if (span_text != null) {
		$(".cz > .bm").append(span_text);
	}
}

function selectBMInfomation(XmlData) {
	var url = "/showGoods/selectFL.action?s=" + Math.random();
	var allData = visit(url, XmlData);
	return allData.fl;
}