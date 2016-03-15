var bmxx = 0;
var saveArray = [];
var obj;// 当前input对象 ...$("#jl_add_bmmc")
var hidobj;// 隐藏框对象,为后台传值
var index;// div框的下标

function jl_add_bmmc(o, str, n) {
	
	if(obj!=$("#" + o)){
		saveArray=[];
	}
	
	obj = $("#" + o);
	hidobj = $("#" + str);
	index = n;
	clear();

	var arr = [];
	var o = {};
	o["cd"] = '2';
	arr.push(o);
	var data = selectBMInfomation(JSON.stringify(arr));
	var bm00 = showBM(data, 'bm_01');
	// 初次加载
	initlist();
	if (obj.attr('jl_add') == '0') {
		// bmxx = 1;
		obj.attr('jl_add', '1');
		// 查询第一级的部门
		// var data = selectBMInfomation(JSON.stringify(arr));
		// var bm00 = showBM(data, 'bm_01');
		$("#jl_bmxx" + index)
				.append(
						'<ul id="bm00" class="bm00">'
								+ bm00
								+ '</ul>'
								+ '<ul id="bm01" class="bm01"></ul>'
								+ '<ul id="bm02" class="bm02"></ul>'
								+ '<ul id="bm03" class="bm03"></ul>'
								+ '<ul id="bm04" class="bm04"></ul>'
								+ '<div class="cz"><div class="yxzbm">您当前选择：</div>'
								+ '<div class="bm"></div><a class="bot" id="return_bm" onclick="return_bm()">确定</a></div>');
	} else {
		$(".bm00").append(bm00);
		initlist();
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
	$(".cz > .bot").click(function() {
		$(this).parent(".cz").parent(".tcc").css( {
			"display" : "none"
		});
	})
}

function initlist(){
	if(hidobj.val()!=null){
		// 加载数组 从INPUT里面将字串加载到数组中
		var dm= hidobj.val().split(',');
		var mc= obj.val().split(',');
		var ooo={"bmmc":"","bmdm":""};
		for ( var int = 0; int < mc.length; int++) {
			ooo.bmmc=mc[int];
			ooo.bmdm=dm[int];
			saveArray[int]=ooo;
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
	var arr = [];
	var o = {};
	o["cd"] = '4';
	o["dm"] = '';
	arr.push(o);
	var data = selectBMInfomation(JSON.stringify(arr));
	var bm01 = showBM(data, 'bm_02');
	$(".bm01").empty();
	$(".bm02").empty();
	$(".bm03").empty();
	$(".bm01").append(bm01);
}

function bm_02(dm) {
	// 查询第三级的部门
	var arr = [];
	var o = {};
	o["cd"] = '6';
	o["dm"] = dm;
	arr.push(o);
	var data = selectBMInfomation(JSON.stringify(arr));
	var bm02 = showBM(data, 'bm_03');
	$(".bm02").empty();
	$(".bm03").empty();
	$(".bm02").append(bm02);
}

function bm_03(dm) {
	// 查询第四级的部门
	var arr = [];
	var o = {};
	o["cd"] = '8';
	o["dm"] = dm;
	arr.push(o);
	var data = selectBMInfomation(JSON.stringify(arr));
	var bm03 = showBM(data, '');
	$(".bm03").empty();
	$(".bm03").append(bm03);
}

function bm_04(dm) {
	// 查询第四级的部门
	var arr = [];
	var o = {};
	o["cd"] = '8';
	o["dm"] = dm;
	arr.push(o);
	var data = selectBMInfomation(JSON.stringify(arr));
	var bm04 = showBM(data, 'bm_03');
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
	} else {
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
		var bmdmi=data[i].BMDM;
		g = 0;
		if (len == 0) {
			if (sbm == '') {
				sbm = '<li><input type="checkbox" class="bm_' + bmdmi
						+ '" onclick="check(\'' + bmdmi + '\',this)"/>'
						+ '<span class="bmmc_' + bmdmi + '" onclick="'
						+ ckname + '(\'' + bmdmi + '\',this)" title="'
						+ data[i].BMMC + '">' + data[i].BMMC + '</span></li>';
			} else {
				sbm = sbm + '<li><input type="checkbox" class="bm_' + bmdmi
						+ '" onclick="check(\'' + bmdmi + '\',this)"/>'
						+ '<span class="bmmc_' + bmdmi + '" onclick="'
						+ ckname + '(\'' + bmdmi + '\',this)" title="'
						+ data[i].BMMC + '">' + data[i].BMMC + '</span></li>';
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
									+ data[i].BMMC + '">' + data[i].BMMC
									+ '</span></li>';
						}
					} else {
						if (saveArray[j]['bmdm'] == bmdmi) {
							g = 1;
							sbm = sbm + '<li><input type="checkbox" class="bm_'
									+ bmdmi + '" onclick="check(\''
									+ bmdmi + '\',this)" checked="checked"/>'
									+ '<span class="bmmc_' + bmdmi
									+ '" onclick="' + ckname + '(\''
									+ bmdmi + '\',this)" title="'
									+ data[i].BMMC + '">' + data[i].BMMC
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
							+ data[i].BMMC + '">' + data[i].BMMC
							+ '</span></li>';
				} else {
					sbm = sbm + '<li><input type="checkbox" class="bm_'
							+ bmdmi + '" onclick="check(\''
							+ bmdmi + '\',this)"/>' + '<span class="bmmc_'
							+ bmdmi + '" onclick="' + ckname + '(\''
							+ bmdmi + '\',this)" title="' + data[i].BMMC
							+ '">' + data[i].BMMC + '</span></li>';
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
			if (span_text == null) {
				span_text = '<a>' + saveArray[i]["bmmc"]
						+ '<label title="删除" onclick="del_check(\''
						+ saveArray[i]["bmdm"] + '\')">×</label></a>';
			} else {
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

	//var url = getBasePath() + "/dataInfoAction/selectBM.do?s=" + Math.random();
	var url = pubJson.PcrmUrl + "/dataInfoAction/selectBM.do?s=" + Math.random();
	var allData = visit(url, XmlData);

	return allData.list;
}
