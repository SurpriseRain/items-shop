
 var extGrid = {};

extGrid.init = function(o,cID,initField,hidefield,record,disabled) {
    extGrid.load($(o).attr('url'),record[cID]);
    extGrid.write(o,cID,initField,hidefield,record,disabled);
}

//取后台数据
extGrid.load = function(url, data){
	extGrid.data = data==undefined?[]:data;
}
 

//加载Grid
extGrid.write = function(o,cID,initField,hidefield,record,disabled) {
		//获取需要填充Grid div的id
		var fieldID = $(o).attr('id').split("d_")[1];
		//配置列头的Json数据 必须转成JSON对象
		var columnsData  =  JSON.parse($(o).attr("item"));
		var options  = JL.isNull($(o).attr("options")) ? {} : JSON.parse($(o).attr("options"));
		//jlQuery 查询回填使用
		var jlq = $(o).attr("cloumn");
		var jlqTemp = jlq;
		var jlqKey=[]; 
		if(jlq!=undefined){
			jlqTemp = JSON.parse(jlqTemp)[0];
			$.each(jlqTemp,function (key,value){
				jlqKey.push(key);
			});
		}else{
			jlq="[{}]";
		}

		//获取控制编辑按钮显示的bqx值
		var	bqx = $(o).attr('bqx');
		var buttonQX = [];
		//判断bqx是否为空并转换为数组格式
		if(bqx == null || bqx == "" || typeof(bqx) == "undefined"){
			buttonQX = [];
		}else{
			buttonQX = JSON.parse(bqx);
		}
		
		//获取编辑框的内容 必须转成JSON对象
		var fieldWidth = JL.isNull($(o).attr('fieldWidth'))?{}:JSON.parse($(o).attr('fieldWidth'));
		var fieldType = JL.isNull($(o).attr('fieldType'))?{}:JSON.parse($(o).attr('fieldType'));
		var summaryType = JL.isNull($(o).attr('summaryType'))?{}:JSON.parse($(o).attr('summaryType'));
		
		//传入的参数，控制字段是否可以编辑
		//var initField=["z1","z2","z3","z4","z5","z6","z7","z8","z9","z10","z41.f2","z41.f4","z41.f14","z41.f22"];

		//声明Checkbox
        var sm = new Ext.grid.CheckboxSelectionModel();
		//动态拼接列头字符串
		var cmString = "  var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, ";
		for(var key in columnsData){
			var width = JL.isNull(eval("fieldWidth['"+key+"']"))?80:eval("fieldWidth['"+key+"']"); 
			cmString += "{ header:'"+columnsData[key]+"',dataIndex: '"+key+"', width:"+width+", renderer: function(data, metadata, record, rowIndex, columnIndex, store) { var tips=JL.isNull(data) ? '' : data;  metadata.attr = ' ext:qtip=\"' + tips + '\"';return data;}"
			if(!JL.isNull(eval("summaryType['"+key+"']"))){
				cmString += ",summaryType: '"+summaryType[key]+"'"; 
			}
			var cmCss = ",css:''";
			if(fieldType[key] == "number"){
				cmCss = cmCss.replace(",css:'",",css:'text-align: right;");
			}
			//判断是否可以编辑
			if(initField.contains( fieldID+"."+key)&&disabled==false){
				cmCss = cmCss.replace(",css:'",",css:'background:#FCFCFC;color:#C40;");
				if( fieldType != null){
					//判断字段类型，初始化不同的编辑格式
					if(  fieldType[key] != null && fieldType[key] == "number" ){
						cmString = cmString +",editor:new Ext.form.NumberField({})";
					}else if( fieldType[key] != null && fieldType[key] == "date" ){
						cmString = cmString +",editor:new Ext.form.DateField({format: 'Y-m-d'})";
					}else if( fieldType[key] != null && fieldType[key] == "datetime"){
						cmString = cmString +",editor:new Ext.form.DateField({format: 'Y-m-d H:i:s',menu:new DatetimeMenu()})";
					}else if(jlqKey.contains(key)){
						cmString = cmString + ",editor:new Ext.form.ComboBox({triggerClass : 'x-form-search-trigger',onTriggerClick :function(){JLQuery.open('d_"+fieldID+"','"+key+"');}})";
					}else if(jlqKey.contains(key)&&1!=1){
						cmString = cmString + ",editor:new Ext.form.ComboBox({store:store,triggerAction:'all',mode:'local',displayField:'chinese',selectedClass:'x-combo-list-small'})";
					}else if( fieldType[key] != null && fieldType[key] == "select" ){
						var options  = JL.isNull($(o).attr("options")) ? [] : JSON.parse($(o).attr("options"))[key];
						cmString = cmString + ",editor:new Ext.form.ComboBox({store:new Ext.data.SimpleStore({fields:['KEY','VALUE'],data:"+JSON.stringify(options)+",autoLoad:true}),triggerAction:'all',mode:'local',editable:false, readOnly:true,valueField: 'KEY',displayField:'VALUE',selectedClass:'x-combo-list-small'})";
					}else{
						cmString = cmString + ",editor: new Ext.form.TextField({})";
					}
				}else{
					cmString = cmString + ",editor: new Ext.form.TextField({})";
				}
			}
			if(hidefield.contains(fieldID+"."+key)){
				cmString = cmString + ",hidden:true";
			}
			cmString += cmCss;
			cmString = cmString + " },";
		}
		cmString = cmString.substring(0,cmString.length-1);
		cmString = cmString+ "]);";
		eval(cmString);
		//点击列头可以实现排序
        cm.defaultSortable = true;

		//生成fields
		var fields =[];				
		for(var key in columnsData){ 
			fields.push(key);
		};
        var reader = new Ext.data.JsonReader({
            fields: fields	//取fields
        });
		
		//获取分组字段
		var groupField = !JL.isNull($(o).attr('fz')) ? $(o).attr('fz') : $(o).attr('groupField');
		var storeString = "var store = new Ext.data.GroupingStore({ reader: reader, data: extGrid.data"
		if( groupField != undefined || groupField != null ){
			storeString = storeString +", sortInfo:{ field: '"+groupField+"',direction: 'ASC'},groupField: '"+groupField+"'";
		}
		eval(storeString+"});");
		
		eval("var summary = new Ext.grid.GroupSummary();");

		var gridHeight = JL.isNull($(o).attr('gridHeight')) ? 350 : $(o).attr('gridHeight');
		var  fieldIDstring = fieldID + " = new Ext.grid.EditorGridPanel({"
							+"  jlq:"+jlq
							+"  ,sm: sm,"
							+"  store: store,"
							+"  cm: cm,"
							+"  plugins: summary,"
							+"  view: new Ext.grid.GroupingView({"
							+"  showGroupName: true,"
							+"  enableNoGroups: true,"
							+"  hideGroupedColumn: true}),"
							+"  renderTo: d_" + fieldID + ","
							+"  bodyStyle: 'width:100%;height:100%',"
							+"  autoWidth: true,"
							+"  height: "+gridHeight+","
							+"  clicksToEdit: 1,"
							+"  listeners: {"
							+"  'afterEdit': {"
							+"  fn: afterEdit,"
							+"  scope: this}}," 
							+"	tbar: [";
		if(!JL.isNull($(o).attr('title'))){
			fieldIDstring += "'"+$(o).attr('title')+"',";
		}
		if(!disabled){
			fieldIDstring+="{"
				+"  text: '查询',"
				+"  handler: function() {"
				+"  JLQuery.open('d_"+fieldID+"');},";
			if($.inArray(0,buttonQX)!=-1){
				fieldIDstring += "hidden: true}";
			}else{
				fieldIDstring += "hidden: false},'-'";
			}

			fieldIDstring+=",{"
				+"  text: '新增',"
				+"  handler: function() {"
				+"  jlGridAdd(store, "+fieldID+");"
				+fieldID+".getView().refresh();},";
			if($.inArray(1,buttonQX)!=-1){
				fieldIDstring += "hidden: true}";}else{
					fieldIDstring += "hidden: false},'-'";}
			
			fieldIDstring += ",{text: '删除',"
				+"  handler: function() {"
				+"   jlGridDelete("+fieldID+".getSelectionModel().getSelections(), store);"
				+fieldID+".getView().refresh(); },";
			if($.inArray(2,buttonQX)!=-1){
				fieldIDstring += "hidden: true}";}else{
					fieldIDstring += "hidden: false},'-'";}
			
			fieldIDstring +=",{ text: '复制',"
				+"  handler: function() {"
				+"   jlGridCopy("+fieldID+".getSelectionModel().getSelections());},";
			if($.inArray(3,buttonQX)!=-1){
				fieldIDstring += "hidden: true}";}else{
					fieldIDstring += "hidden: false},'-'";}
			
			fieldIDstring +=",{text: '粘贴',";
			if($.inArray(4,buttonQX)!=-1){
				fieldIDstring += "hidden: true,";}else{
					fieldIDstring += "hidden: false,";}
			
			fieldIDstring +=" handler: function() {"
				+"  jlGridPaste(store, "+fieldID+".getStore().recordType);"
				+fieldID+".store.groupBy('"+groupField+"', true);"
				+fieldID+".getView().refresh();}}]";
		}else{
			fieldIDstring +=" ]";
		}
		fieldIDstring +="});"; 
        eval(fieldIDstring);

        Ext.QuickTips.init();
        Ext.apply(Ext.QuickTips.getQuickTip(), {		//取消Tip自动隐藏
            dismissDelay: 0
        });
    }



/**************************** grid相关操作 *****************************/

jlGridClipbrd = null;

//新增
function jlGridAdd(s,g){
	var grid=g;
	grid.getView().refresh();
	var keys=grid.getStore().fields.keys;
	var p={};
	var line=0;
	if(s.groupField!=undefined){
		if(grid.getSelectionModel().getSelected()==undefined){
			alert("请选择正确的行");
			return false;
		}else{
			$.each(keys, function(j,k){
				if(s.groupField==k)
					p[k]=s.getAt(grid.getSelectionModel().lastActive).get(s.groupField);
				else      
					p[k]='';
			}); 
			line=grid.getSelectionModel().lastActive+1;
		}	
	}else{
		line=grid.getStore().getCount();
		$.each(keys, function(j,k){      
			p[k]='';
		}); 
	}

	var recode = grid.getStore().recordType;
    var p2 = new recode(p);
	grid.stopEditing();
	grid.getStore().insert(line,p2);
	grid.getView().refresh();
}

//删除
function jlGridDelete(r,s){
	
	jlGridClipbrd = [];
	for(var i=0;i<r.length;i++){
		jlGridClipbrd.push(r[i].data);
		s.remove(r[i]);
	}
	if(typeof(gridAfterDelete)=='function'){
		gridAfterDelete(r,s);
	}
}

//复制
function jlGridCopy(r){
jlGridClipbrd = [];
for(var i=0;i<r.length;i++){
jlGridClipbrd.push(r[i].data);
}
}

//粘帖
function jlGridPaste(s,r){//alert(JSON.stringify(jlGridClipbrd));
for(var i=0;i<jlGridClipbrd.length;i++){
s.add(new r(jlGridClipbrd[i]));
}
}

//数组是否含元素
Array.prototype.contains = function(obj) {
	var i = this.length - 1;
	while (i >= 0) {
		if (this[i] === obj) {
			return true;
		}
		i--;
	}
	return false;
}

function afterEdit(e) {
	if(typeof(gridAfterEdit)=='function'){
		gridAfterEdit(e);
	}
}