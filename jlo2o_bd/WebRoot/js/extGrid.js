//单行双击事件
grid.addListener('rowclick', rowdblclickFn);

function rowdblclickFn(grid,rowindex,e){
  if(typeof(parent.backFill) == "function"){
    parent.backFill(rowindex);
  }
}

//新增编辑后事件
grid.on("afteredit",afterEdit,grid);
function afterEdit(obj){
  var rObj = obj.record;               //获取当前行
  var cellName = obj.field;            //获取当前列名
  var cellValue = rObj.get(cellName);  //获取当前cell
  if(typeof(parent.overFill)=="function"){
     parent.overFill(rObj,cellName,cellValue,obj);
  }
}

//checkbox的勾选事件
grid.getSelectionModel().on('rowselect', selectCheckBox);
function selectCheckBox(grid,rowindex,e){
  if(typeof(parent.selectEvent) == "function"){
    parent.selectEvent(rowindex,grid);
  }
}

//checkbox的去勾选事件
grid.getSelectionModel().on('rowdeselect', deselectCheckBox);
function deselectCheckBox(grid,rowindex,e){
  if(typeof(parent.deselectEvent) == "function"){
    parent.deselectEvent(rowindex,grid);
  }
}

//新增编辑前事件
grid.on("beforeedit",beforeEdit,grid);
function beforeEdit(obj){
  if(typeof(parent.beforeEdit)=="function"){
     parent.beforeEdit();
  }
}

//键盘事件
function handleKeyDown(myElement){
  var c = grid.selModel.getSelectedCell();
  var rowNum = c[0];
  var cellNum = c[1];
  var keyCode = myElement.getKey();

  var totalRowNum = grid.getStore().getCount();
  //按↓方向键
  if(keyCode == 40){
    //获取选定单元格值
    var columnValue = grid.getStore().getAt(rowNum).data[fields[cellNum].name];
    if(rowNum == (totalRowNum - 1) && columnValue != ""){
      if(typeof(parent.setDetailsColumnValue) == "function"){
         addRows(parent.setDetailsColumnValue());
         getFocus(rowNum+1,cellNum);
      }
    }
  }
  //按delete键
  if(keyCode == 46){
    if(totalRowNum > 1){
      //编辑区域失去焦点
      grid.activeEditor.completeEdit();
      delRows(rowNum);
    }
  }
  //按回车后执行事件
  if(keyCode == 13){
    if(typeof(parent.handleKeyDown) == "function"){
      parent.handleKeyDown(rowNum,cellNum);
    }
  }
};

/*
   获取所在行
   var r = obj.record;
   store.indexOf(r);
   修改单元格字体颜色
   grid.getView().getCell(0,1).style.color = "red";
   对指定单元格进行重新赋值
   grid.store.getAt(0).set("RYXX02" , "aaaaa");
   得到焦点
   grid.startEditing(0,0);
   失去焦点
   grid.activeEditor.completeEdit();

   alert(detailsFrame.grid.getCell(rowNum,cellNum));
*/

//获取列值 rowindex行数；columnKey列值
function getColumnValue(rowindex,columnKey){
  var columnValue="";
  var record = grid.getStore().getAt(rowindex);
  /**for(var i=0;i<fields.length;i++){
    if(fields[i].name == columnKey){
      columnValue = record.data[fields[i].name];
      break;
    }
  }*/
  columnValue = record.data[columnKey];
  columnValue = (columnValue==null)?"":columnValue;
  return columnValue;
}

//单元格获取焦点
function getFocus(rowID,cellID){
  grid.startEditing(rowID,cellID);
}

//增加行
function addRows(jsonr,rowNum){
  var view = grid.getView();
  var n = (rowNum == undefined)?(grid.getStore().getCount()):rowNum;
  var p = new Ext.data.Record(jsonr);
  grid.store.insert(n, p);
  view.refresh();
}

//删除行 rowindex行数
function delRows(rowindex){
  var ds = grid.getStore();
  var selectedRow = ds.getAt(rowindex);
  if (selectedRow){
    ds.remove(selectedRow);
  }
}

function gofirstpage(){
  goPage(1);
}

function goprevpage(){
  goPage(getCurrPage()-1);
}

function gonextpage(){
  goPage(getCurrPage()+1);
}

function golastpage(){
  goPage(getTotalPage());
}
