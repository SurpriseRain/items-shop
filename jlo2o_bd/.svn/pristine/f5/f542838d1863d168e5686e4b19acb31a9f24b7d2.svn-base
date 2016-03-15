var JLGrid = {};
JLGrid.version = 2;
JLGrid.config = {};
JLGrid.GridArray = {};

JLGrid.setValue = function(json,value){
	value = JL.formatObject(value);
	JLGrid.getGrid(json["zdid"]).loadData(value);
}

JLGrid.init = function(json){
	this.config = JL.isNull(json.config) ? {} : json.config;
	json = $.extend(json, this.config);

	var aaa = [];
	try{
		
		$.each(json.config["item"],function(key,value){
			var args = {};
			args["id"] = key;
			args["name"] = value;
			if(!JL.isNull(json["config"]["fieldHidden"]) && !JL.isNull(json["config"]["fieldHidden"][key])){
				args["hidden"] = true;
			}
			if(!JL.isNull(json["config"]["fieldWidth"]) && !JL.isNull(json["config"]["fieldWidth"][key])){
				args["width"] = json["config"]["fieldWidth"][key];
			}
			args["editor"] = {"type":"text"};
			var filedType = json["config"]["fieldType"];
			if(!JL.isNull(filedType) && !JL.isNull(filedType[key])){
				var editor = $.extend(args["editor"],filedType[key]);
				args["editor"] = editor;
			}
			if(!JL.isNull(json["INITFIELD"]) && $.inArray(json.zdid+"."+key,json["INITFIELD"]) != -1){
				args["editor"]["disabled"] = false;
			}
			aaa.push(args);
		})
	}catch(e){
		
	}
	
	json["headers"] = aaa;
	json["height"] = JL.isNull(json.config["gridHeight"])? 300 :json.config["gridHeight"];
	json["tittles"] = json.config["tittles"];
	json["groupField"] = json.config["groupField"];
	if(JSON.stringify(json["INITFIELD"]).indexOf(json["zdid"]+".") > 0){
		var buttons = [{
			text:"查询",
			func:function(){
				if(!JL.isNull(json.config["beforeopen"]) && json.config["beforeopen"]() == true){
					return false;
				}
				JLQuery.show(json["tab"],json["config"]);
			}
		}, {
			text:"新增",
			func:function(){
				JLGrid.getGrid(json.zdid).addRow({});
			}
		}, {
			text:"删除",
			func:function(){
				var grid = JLGrid.getGrid(json.zdid);
				var selected = grid.getSelectedIndex();
				for(var i=0;i<selected.length;i++){
					grid.removeRow(selected[i]);
				}
			}
		}, {
			text:"清空",
			func:function(){
				JLGrid.getGrid(json.zdid).removeAll();
			}
		}];
		json["buttons"] = []; 
		$.each(buttons,function(i,row){
			if($.inArray(i,json.config["buttonHide"]) == -1 ){
				json["buttons"].push(row);
			}
		});

		if(!JL.isNull(json["config"]["listener"])){
			json["listener"] = json["config"]["listener"];
		}
	}
	
	this.write(json);
}


JLGrid.write = function(json){
	var grid = new JLGrid.Grid(json);
	if(!JL.isNull(json["value"])){
		grid.loadData(json["value"]);
	}
	
	var tab = $(json.obj)
	
	this.GridArray[json.zdid] = grid;
}
JLGrid.getGrid = function(zdid){
	return this.GridArray[zdid];
}

JLGrid.Grid = function(json){
	var initials = {
		obj: null,
		datas: [],
		sort: {},
		head: {},
		lastActive: null,
		headers: {},
		currentPage: 1,
		maxPage: 1,
		listener:{}
	};
	$.extend(initials, json);
	var height = JL.isNull(json.height)? 200: json.height;
	var tittles = JL.isNull(json["tittles"])? "": json["tittles"];
	var html = "<div class='table_content'>" 
			 + "	<div class='table_title'><span>"+tittles+"</span><div class='btn_maigin_left'></div><i class='fa fa-chevron fa-chevron-up' style='line-height: 42px;'></i></div>"
			 + "	<div class='table_show' style='height:"+height+"px;'>"
			 + "		<div class='table_main'>"
			 + "			<dl class='table title'><dt><input type='checkbox'></dt></dl>"
			 + "			<div></div>"
			 + "		</div>"
			 + "	</div>"
			 + "	<div class='table_control display_none'><div class='width_55'></div><div class='width_35'></div></div>"
			 + "</div>";
	$(json.obj).html(html);
	var grid = this;
	
	if(JL.isNull(json["buttons"]) && JL.isNull(json["tittles"])){
		$(initials["obj"]).find(".table_content > .table_title").hide();
	}
	if(!JL.isNull(json["print"]) && json["print"] == true){
		var fa_print = $("<i class='fa fa-print' title='打印'></i>").appendTo($(initials["obj"]).find(".table_content > .table_title"));
		fa_print.click(function(){
			var dybh = initials["obj"].attr("dybh");
			var selected = grid.getSelected();
			if(!JL.isNull(dybh) && selected.length > 0){
				JL.print(dybh,selected);
			}
		});
	}
	if(!JL.isNull(json["excel"]) && json["excel"] == true){
		var fa_excel = $("<i class='fa fa-file-excel-o' title='导出'></i>").appendTo($(initials["obj"]).find(".table_content > .table_title"));
		fa_excel.click(function(){
			if(!JL.isNull(initials["filename"])){
				var data = {};
				data["fileName"] = grid.getFileName();
				data["lastPage"] = grid.getMaxPage();
				data["columnName"] = JSON.stringify(grid.getHead());
				console.info(data);
				JL.download("excelHandler/excelExport.do", data);
			}else{
				JL.tip("请先查询需要导出的结果");
			}
		});
	}

	this.define = function(json){
		var obj = this;
		$.each(json,function(key,value){
			obj[key] = value;
		})
	}
	
	this.define({
		"init": function(){
			this.loadHead();
			this.loadButton();
			if(!JL.isNull(json.paging)){
				this.loadPaging();
			}
			this.loadEvents();
		}, "loadHead": function(){
			var headers = initials.headers;
			var headHTML = "";
			var initTableWidth = 50;
			for(var i=0;i<headers.length;i++){
				var width = JL.isNull(headers[i]["width"])? 80 : headers[i]["width"];
				var hidden = JL.isNull(headers[i]["hidden"])? "" : "display: none;";
				initTableWidth += JL.isNull(hidden)? width: 0;
				headers[i]["width"] = width;
				width = "width: " + width + "px;"
				initials["head"][headers[i].id] = headers[i].name; 
				headHTML += "<dd style='"+width+hidden+"' data-id='"+headers[i].id+"' data-index='"+i+"'>"+headers[i].name+"<i></i></dd>";
			}
			var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main")
			table_main.width(initTableWidth);
			table_main.find(".table.title").append(headHTML);
			
			if(!JL.isNull(initials["radio"])){
				table_main.find("dt").hide();
			}

			var X = 0,canMove = false,current = null;
			table_main.find(".table.title > dd").click(function(){
				
				if(event.target.tagName == "I"){
					return false;
				}
				var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main")
				var DLS = table_main.find(".table:not(.title)");
				var DD_id = $(this).attr("data-id")
				
				var sort = null;
				if(JL.isNull(initials["sort"][DD_id]) || initials["sort"][DD_id]){
					sort = DLS.sort(function(a,b){
						var DL1 = $(a).attr("data-index");
						var DL2 = $(b).attr("data-index");
						var value = initials["datas"][DL1][DD_id];
						var value1 = initials["datas"][DL2][DD_id];
						return value > value1 ? 1 : -1;
					});
					initials["sort"][DD_id] = false;
				}else{
					sort = DLS.sort(function(a,b){
						var DL1 = $(a).attr("data-index");
						var DL2 = $(b).attr("data-index");
						var value = initials["datas"][DL1][DD_id];
						var value1 = initials["datas"][DL2][DD_id];
						return value > value1 ? -1 : 1;
					});
					initials["sort"][DD_id] = true;
				}
				DLS.remove();
				table_main.append(sort);
			});
			table_main.find(".table.title > dd > i").mousedown(function(event){ 
				X = event.clientX;
				canMove = true;
				current = this; 
				return false; 
			}); 
			$(document).mousemove(function(event){
				if(canMove){
					var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main")
					var moved_X = event.clientX - X;
					X = event.clientX;
					var dd = $(current).parent("dd");
					var ddWidth = dd.width()*1 + moved_X;
					if(ddWidth > 0){
						var dd_index = dd.attr("data-index");
						initials.headers[dd_index]["width"] = ddWidth+22;
						var tableWidth = table_main.width()*1 + moved_X;
						table_main.find("dd[data-index='"+dd_index+"']").width(ddWidth);
						if(table_main.width() > 0){
							table_main.width(tableWidth);
						}
					}
				}
				//return false; 
			}); 
			$(document).mouseup(function(e) { 
				canMove = false;
				current = null; 
			})
		}, "getHead": function(){
			return initials["head"];
		}, "getHeaders": function(){
			return initials["headers"];
		}, "setRowClick": function(fn){
			initials["listener"]["rowclick"] = fn;
		}, "setHeight": function(height){
			$(json["obj"]).find(".table_content > .table_show").height(height);
		}, "setPage": function(current){
			var table_control = $(json.obj).find(".table_content > .table_control");
			table_control.find("li:eq(0) span").html(current+"/"+initials["maxPage"]);
		}, "setMaxPage": function(max){
			initials["maxPage"] = max;
			this.setPage(1);
		}, "getMaxPage": function(){
			return initials["maxPage"];
		}, "setFileName": function(filename){
			initials["filename"] = filename;
		}, "getFileName": function(filename){
			return initials["filename"];
		}, "loadPaging": function(){
			var table_control = $(json.obj).find(".table_content > .table_control");
			table_control.show();
			
			var paging = "<ul class='jl_paging02'>"
				+  "	<li><span></span></li>"
				+  "	<li><i class='fa fa-fast-backward' title='首页'></i></li>"
				+  "	<li><i class='fa fa-chevron-left' title='上一页'></i></li>"
				+  "	<li><span>跳转到</span></li>"
				+  "	<li><input type='text'></li>"
				+  "	<li><input type='button' class='jl_btn btn_white' value='确定'></li>"
				+  "	<li><i class='fa fa-chevron-right' title='下一页'></i></li>"
				+  "	<li><i class='fa fa-fast-forward' title='尾页'></i></li>"
				+  "</ul>";
			
			paging = $(paging);
			paging.appendTo(table_control.find(".width_35"));
		}, "changePaging": function(filename){
			var ajaxJson = {};
			ajaxJson["contentType"] = "application/x-www-form-urlencoded;charset=utf-8";
			ajaxJson["src"] = pubJson["PagingUrl"]+"?filename="+filename+"-LASTPAGE.xml";
			var resultData = JL.ajax(ajaxJson);
			var lastPage = JL.isNull(resultData) ? 1 : resultData;
			this.setMaxPage(lastPage);
			this.setFileName(filename);

			var table_control = $(json.obj).find(".table_content > .table_control");
			table_control.show();
			var ul = table_control.find(".jl_paging02");
			
			var page_func = function(){
				var ajaxJson = {};
				ajaxJson["contentType"] = "application/x-www-form-urlencoded;charset=utf-8";
				ajaxJson["src"] = pubJson["PagingUrl"]+"?filename="+initials["filename"]+"-"+initials["currentPage"]+".xml";
				var resultData = JL.ajax(ajaxJson);
				resultData = JL.isNull(resultData)? []: resultData;
				grid.loadData(resultData);
				grid.setPage(initials["currentPage"]);
			}
			
			ul.find("li:eq(1) i").unbind().click(function(){
				initials["currentPage"] = 1;
				ul.find("li:eq(4) :text").val("");
				page_func();
			});
			ul.find("li:eq(2) i").unbind().click(function(){
				if(initials["currentPage"] <= 1){
					alert("已经是第一页了");
					return false;
				}
				initials["currentPage"]--;
				ul.find("li:eq(4) :text").val("");
				page_func();
			});
			ul.find("li:eq(5) :button").unbind().click(function(){
				var text_val = ul.find("li:eq(4) :text").val();
				if(text_val < 1 || text_val > initials["maxPage"]){
					alert("请输入正确的页码");
					return false;
				}
				initials["currentPage"] = text_val;
				page_func();
			});
			ul.find("li:eq(6) i").unbind().click(function(){
				if(initials["currentPage"] >= initials["maxPage"]){
					alert("已经是最后一页了");
					return false;
				}
				initials["currentPage"]++;
				ul.find("li:eq(4) :text").val("");
				page_func();
			});
			ul.find("li:eq(7) i").unbind().click(function(){
				initials["currentPage"] = initials["maxPage"];
				ul.find("li:eq(4) :text").val("");
				page_func();
			});
		}, "loadButton": function(){
			if(JL.isNull(json.buttons)){
				return false;
			}
			var table_control = $(json.obj).find(".table_content > .table_title > div");
			
			var buttons = json.buttons;
			var buttonHTML = ""
			for(var i=0;i<buttons.length;i++){
				var text = JL.isNull(buttons[i]["text"])? "": buttons[i]["text"];
				var button = $("<input type='button' class='jl_btn btn_white btn_maigin_right' value='"+text+"'>");
				button.appendTo(table_control);
				button.click(buttons[i]["func"]);
			}
		}, "loadEvents": function(){
			var grid = this;
			
			$(initials["obj"]).delegate(".table_content > .table_title > i.fa-chevron", "click",function(){
				if($(this).parent().next().is(":hidden")){
					JL.changeClass($(this), "fa-chevron-down", "fa-chevron-up");
				}else{
					JL.changeClass($(this), "fa-chevron-up", "fa-chevron-down");
				}
				$(this).parent().next().slideToggle();
			});
			
			$(initials["obj"].selector + " > .table_content > .table_show").scroll(function(){
				var width = $(this).width()-16;
				var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main");
				var width_main = table_main.width();
				var div = null;
				var title = $(table_main.selector + " > .table.title");
				if($(this).find("[data-position]").length == 0){
					div = $("<div>");
					div.attr("data-position", true);
					div.css({"width": width+"px", "overflow": "hidden", "position": "absolute", "z-index": "100", "min-height": "40px"});
					var div_inner = $("<div>");
					div_inner.css({"width": width_main+"px", "margin-left": "0px"});
					div_inner.append(title.clone());
					div_inner.appendTo(div);
					title.after(div);
				}else{
					div = $(this).find("[data-position]");
					div_inner = $(this).find("[data-position] > div");
					var margin_left = $(this).scrollLeft()*-1;
					div_inner.css({"margin-left": margin_left+"px"});
					div_inner.attr("margin-left", margin_left+"px");
				}
				if($(this).scrollTop() > 1){
					div.show();
				}else{
					div.hide();
				}
			});

			$(initials["obj"].selector + " > .table_content > .table_show").dblclick(function(){
				if(!JL.isNull(initials["listener"]["tabledblclick"])){
					initials["listener"]["tabledblclick"](grid);
				}
			});

			var table_content = $(initials["obj"].selector + " > .table_content");
			var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main");
			$(table_main.selector+" > .table.title > dt > :checkbox").click(function(){
				var checkbox = table_main.find(".table:not(.title) > dt > :checkbox");
				if($(this).is(":checked")){
					checkbox.attr("checked","checked");
					for(var i=0;i<checkbox.length;i++){
						checkbox[i].checked = true;
					}
					table_main.find("div[data-groupfield] i.fa-plus").parent().click();
				}else{
					checkbox.removeAttr("checked");
				}
				$(this).next().slideToggle();
			});
			
			$(document).off("click", table_main.selector +" > .table:not(.title) > dt > :checkbox");
			$(document).on("click", table_main.selector +" > .table:not(.title) > dt > :checkbox", function(){
				if($(this).is(":checked")){
					$(this).closest("dl").addClass("tablehover");
					$(this)[0].checked = true;
				}else{
					$(this).closest("dl").removeClass("tablehover");
				}
			});
			
			$(document).off("click", table_main.selector +" > div[data-groupfield]");
			$(document).on("click", table_main.selector +" > div[data-groupfield]", function(){
				if($(this).next().is(":hidden")){
					JL.changeClass($(this).find("i"), "fa-plus", "fa-minus");
				}else{
					JL.changeClass($(this).find("i"), "fa-minus", "fa-plus");
				}
				$(this).next().slideToggle();
			});
			
			$(document).off("click", table_main.selector +" > dl.table:not(.title)");
			$(document).on("click", table_main.selector +" > dl.table:not(.title)", function(event){
				initials.lastActive = $(this).attr("data-index") ;
				if(event.target.tagName != "DT" && $(this).find("dt").has(event.target).length == 0){
					table_main.find("dt > :checkbox").removeAttr("checked");
					$(this).find("dt > :checkbox").attr("checked","checked");
					$(this).find("dt > :checkbox")[0].checked = true;

					table_main.find("dl.tablehover").removeClass("tablehover");
					$(this).addClass("tablehover");
				}
				
				if(!JL.isNull(initials["listener"]["rowclick"])){
					initials["listener"]["rowclick"](initials["datas"][$(this).attr("data-index")],grid);
				}
			});
			
			$(document).off("dblclick", table_main.selector +" > dl.table:not(.title)");
			$(document).on("dblclick", table_main.selector +" > dl.table:not(.title)", function(event){
				initials.lastActive = $(this).attr("data-index") ;
				if(event.target.tagName != "DT" && $(this).find("dt").has(event.target).length == 0){
					table_main.find("dt > :checkbox").removeAttr("checked");
					$(this).find("dt > :checkbox").attr("checked","checked");
					$(this).find("dt > :checkbox")[0].checked = true;
				}
				if(!JL.isNull(initials["listener"]["rowdblclick"])){
					initials["listener"]["rowdblclick"]();
				}
			});
			
			$(document).off("click", table_main.selector + " > dl.table:not(.title) > dd");
			$(document).on("click", table_main.selector + " > dl.table:not(.title) > dd", function(event){
				if(event.target.tagName != "DD" 
					|| $(this).closest(".jl_poplayer").length>0
					|| $(this).has(".jl_poplayer").length>0){
					return false;
				}
				var x = $(this).closest("dl").attr("data-index");
				var y = $(this).attr("data-index");
				var editor = initials.headers[y]["editor"];
				if(!JL.isNull(editor) && editor["disabled"] == false){
					var id = initials.headers[y]["id"];
					var gridData = grid.getDatas();
					var value = JL.isNull(gridData[x][id])? "" :gridData[x][id];
					var selector = null; 
					if(editor["type"] == "text"){
						var table_change = $("<div class='table_change'><input type='text' data-name='"+id+"' value='"+value+"'><i class='fa fa-ellipsis-h' style='display:none;'></i></div>");
						var jl_poplayer = $("<div class='jl_poplayer display_none'></div>");
						jl_poplayer.appendTo(table_change);
						table_change.appendTo($(this));
						
						selector = $(this).find(".table_change :text");
						selector.focus();

						if(editor["jlid"] == "JLGridQuery"){
							table_change.find(".fa-ellipsis-h").show();
							if(JL.isNull(editor["beforeopen"]) || editor["beforeopen"]() != true){
								JLQuery.gridShow(jl_poplayer, editor);
								jl_poplayer.slideDown();
							}else{
								table_change.remove();
							}
							$("body").click(function(e){
								if($(e.target).closest(table_change).length == 0){
									table_change.remove();
									$(jl_poplayer).slideUp();
								}
							});
						}else{
							selector.blur(function(){
								var x = $(this).closest("dl").attr("data-index");
								var y = $(this).closest("dd").attr("data-index");
								grid.setCell($(this).val(), x, y);
								$(this).parent().remove();
								if(!JL.isNull(initials["afterEdit"])){
									initials["afterEdit"](grid, x, y, value, $(this).val());
								}
							});
						}
					}else if(editor["type"] == "select"){
						$(this).empty();
						var table_change = $("<div class='table_change'></div>").appendTo(this);
						var div_select = $("<div class='select'></div>").appendTo(table_change);
						var select = $("<select></select>").appendTo(div_select);
						$.each(editor["options"],function(key,val){
							var option = $("<option value='"+key+"'>"+val+"</option>").appendTo(select);
							if(key == value){
								option.attr("selected","selected");
							}
						});
						select.change(function(){
							var x = $(this).closest("dl").attr("data-index");
							var y = $(this).closest("dd").attr("data-index");
							var option = $(this).find(":selected");
							grid.setCell(option.val(), x, y, option.html());
						});
					}
				}
			});
		}, "setAfterEdit": function(fn){
			initials["afterEdit"] = fn;
		}, "loadData": function(data){
			this.removeAll();
			initials["datas"] = JL.isNull(data)? []: data;
			for(var i=0;i<initials["datas"].length;i++){
				this.addRow(initials["datas"][i], i);
			}
			if(!JL.isNull(initials["radio"])){
				var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main")
				table_main.find("dt").hide();
			}
			this.selectFirst();
		}, "getDatas": function(index){
			return initials["datas"];
		}, "getLastActive": function(index){
			return initials["lastActive"];
		}, "selectFirst": function(){
			var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main")
			$(table_main.selector + " > .table:not(.title):first").click();
		}, "getSelected": function(index){
			var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main")
			var checkboxs = table_main.find("dl.table:not(.title) :checkbox:checked");
			var Arr = [];
			for(var i=0;i<checkboxs.length;i++){
				var checkbox = $(checkboxs[i]);
				var index = checkbox.closest("dl").attr("data-index");
				Arr.push(initials["datas"][index]);
			}
			return Arr;
		}, "getSelectedIndex": function(index){
			var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main")
			var checkboxs = table_main.find("dl.table:not(.title) :checkbox:checked");
			var Arr = [];
			for(var i=0;i<checkboxs.length;i++){
				var checkbox = $(checkboxs[i]);
				var index = checkbox.closest("dl").attr("data-index");
				Arr.push(index);
			}
			return Arr;
		}, "removeRow": function(index){
			delete initials["datas"][index];
			var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main")
			var dl = table_main.find("dl[data-index='"+index+"']");
			dl.remove();
			if(dl.parent().find(".table_case dl").length == 0 ){
				table_main.find(".table_case").prev().remove();
				table_main.find(".table_case").remove();
			}
		}, "removeAll": function(){
			var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main")
			table_main.find("div.table_case,div.table_nav,dl.table:not(.title)").remove();
			initials["datas"] = [];
		}, "getRow": function(index){
			return initials["datas"][index];
		}, "getRowIndexByID": function(id){
			var headers = this.getHeaders();
			for(var i=0; i<headers.length; i++){
				if(headers[i]["id"] == id){
					return i;
				}
			}
		}, "setCell": function(value, x, y ,html){
			initials["datas"][x][initials["headers"][y]["id"]] = value;
			var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main")
			var DD = table_main.find("dl[data-index='"+x+"'] dd[data-index='"+y+"']");
			if(JL.isNull(html)){
				DD.html(value);
			}
		}, "setRow": function(data,index){
			initials["datas"][index] = $.extend(initials["datas"][index], data);
			var row = data;
			var headers = initials.headers;

			var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main")
			var rowDL = table_main.find("dl[data-index='"+index+"']");
			rowDL.find("dd").remove();

			var dds = $(table_main.selector + " > .table.title > dd");
			for(var j=0;j<dds.length;j++){
				var id = dds.eq(j).attr("data-id");
				var value = JL.isNull(row[id])? "&nbsp;": row[id];
				var rowDD = $("<dd style='width:"+headers[j]["width"]+"px;' data-index='"+j+"'>"+value+"</dd>");
				if(!JL.isNull(headers[j]["hidden"])){
					rowDD.hide();
				}
				rowDD.appendTo(rowDL);
			}
		}, "addRow": function(data,index){
			if(JL.isNull(index)){
				initials["datas"].push(data);
				index = initials["datas"].length - 1;
			}
			var row = data;
			var headers = initials.headers;
			
			var table_main = $(initials["obj"].selector + " > .table_content > .table_show > .table_main")
			var dds = $(table_main.selector + " > .table.title > dd");
			
			var rowDL = $("<dl class='table' data-index='"+index+"'><dt><input type='checkbox'></dt></dl>");
			if(!JL.isNull(initials["groupField"])){
				if(table_main.find("[data-groupfield='"+row[initials["groupField"]]+"']").length == 0){
					table_main.append("<div class='table_nav' data-groupfield='"+row[initials["groupField"]]+"'><i class='fa fa-minus' title='收起'></i>"+initials["head"][initials["groupField"]]+" : "+row[initials["groupField"]]+"</div><div class='table_case'></div>");
				}
				table_main.find("[data-groupfield='"+row[initials["groupField"]]+"']").next(".table_case").append(rowDL);
			}else{
				rowDL.appendTo(table_main);
			}
			for(var j=0;j<dds.length;j++){
				var id = dds.eq(j).attr("data-id");
				var value = JL.isNull(row[id])? "&nbsp;": row[id];
				if(typeof value == "object" && !JL.isNull(value["key"]) && !JL.isNull(value["value"])){
					value = value["value"];
				}else if(typeof value == "string" && value.indexOf("key")!=-1 && value.indexOf("value")!=-1){
					if(value.indexOf("[")!=-1 && value.lastIndexOf("]")!=-1){
						var array = JSON.parse(value);
						value = "";
						for(var i=0;i<array.length;i++){
							value += array[i]["value"];
							if(i<array.length-1){
								value += ",";
							}
						}
					}else{
						value = JSON.parse(value["value"]);
					}
				}
				
				var editor = initials["headers"][j]["editor"];
				if(!JL.isNull(editor) && editor["type"] == "select" && !JL.isNull(editor["options"][value])){
					value = editor["options"][value];
				}
				
				var rowDD = $("<dd style='width:"+headers[j].width+"px;' data-index='"+j+"'>"+value+"</dd>");
				if(!JL.isNull(headers[j]["hidden"])){
					rowDD.hide();
				}
				rowDD.appendTo(rowDL);
			}
		}
	})
	
	this.init();
	//this.loadData([{"abc":"123","abcd":123},{"abc":"12311","abcd":1232},{"abc":"123","abcd":123},{"abc":"12311","abcd":1232},{"abc":"12311","abcd":1232},{"abc":"123","abcd":123},{"abc":"12311","abcd":1232},{"abc":"12311","abcd":1232},{"abc":"123","abcd":123},{"abc":"12311","abcd":1232},{"abc":"123","abcd":123},{"abc":"12311","abcd":1232}]);
}