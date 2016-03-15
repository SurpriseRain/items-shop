//添加跟路径
var ContextRootPath;
function setRootCtx(ctx){
   this.ContextRootPath = ctx;
}

//单行单击事件
grid.addListener('rowclick', rowdblclickFn);

function rowdblclickFn(grid,rowindex,e){
  if(typeof(parent.backFill) == "function"){
    parent.backFill(rowindex,grid);
  }
}

//双击单击事件
grid.addListener('rowdblclick', rowclickFn);

function rowclickFn(grid,rowindex,e){
  if(typeof(parent.dblBackFill) == "function"){
    parent.dblBackFill(rowindex,grid);
  }
}

grid.addListener('cellclick', celldblclickFn);
function celldblclickFn(grid, rowIndex, columnIndex, e)
{
 if(typeof(parent.cellFill)=="function")
  {
   parent.cellFill(columnIndex,rowIndex,grid);
  }
}



//新增编辑后事件 obj.column修改列;obj.originalValue原始值;obj.value修改后的值;
grid.on("afteredit",afterEdit,grid);
function afterEdit(obj){
  var rObj = obj.record;                 //获取当前行
  var cellName = obj.field;              //获取当前列名
  var cellValue = rObj.get(cellName);    //获取当前cell
  if(typeof(parent.overFill)=="function"){
     parent.overFill(rObj,cellName,cellValue,obj);
  }
}

//新增编辑前事件
grid.on("beforeedit",beforeEdit,grid);
function beforeEdit(obj){
  var rObj = obj.record;                 //获取当前行
  var cellName = obj.field;              //获取当前列名
  var cellValue = rObj.get(cellName);    //获取当前cell
  if(typeof(parent.beforeEdit)=="function"){
     if(parent.beforeEdit(obj,grid,cellName)){
       obj.cancel = true;
       return false;
     }
  }
  if(typeof(parent.beforeFill)=="function"){
     parent.beforeFill(rObj,cellName,cellValue,obj);
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

//checkbox的勾选事件
grid.addListener('rowselect', selectCheckBox);
function selectCheckBox(grid,rowindex,e){
  if(typeof(parent.selectEvent) == "function"){
    parent.selectEvent(rowindex,grid);
  }
}

//checkbox的去勾选事件
grid.addListener('rowdeselect', deselectCheckBox);
function deselectCheckBox(grid,rowindex,e){
  if(typeof(parent.deselectEvent) == "function"){
    parent.deselectEvent(rowindex,grid);
  }
}


//键盘事件
function handleKeyDown(myElement,p){
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
      if(p != undefined){
         if(typeof(eval('parent.'+p)) == "function"){
           addRows(eval('parent.'+p+'()'));
           getFocus(rowNum+1,cellNum);
         }
      }else{
        if(typeof(parent.setDetailsColumnValue) == "function"){
         addRows(parent.setDetailsColumnValue());
         getFocus(rowNum+1,cellNum);
        }
      }
    }
  }
  //按delete键
  if(keyCode == 46){
    if(totalRowNum > 1){
      //编辑区域失去焦点
      grid.activeEditor.completeEdit();
      var columnValue = grid.getStore().getAt(rowNum).data[fields[cellNum].name];
      var columnName = fields[cellNum].name;
      //delRepeatRows删除gird中关键字重复行
      if(typeof(parent.delRepeatRows)=="function"&&columnValue!="")
        {
          parent.delRepeatRows(grid,columnName,columnValue);
        }else if(typeof(parent.delSomeRows)=="function"&&columnValue!=""){
        //delSomeRows删除一些特定的需要在页面上用到的行
          parent.delSomeRows(grid,rowNum);
        }else
        {
          delRows(rowNum);
        }
    }else
    {
      //编辑区域失去焦点
      grid.activeEditor.completeEdit();
      var columnValue = grid.getStore().getAt(rowNum).data[fields[cellNum].name];
      var columnName = fields[cellNum].name;
      if(typeof(parent.delSomeRows)=="function"&&columnValue!=""){
        //delSomeRows删除一些特定的需要在页面上用到的行
         parent.delSomeRows(grid,rowNum);
      }
    }
  }
  //按回车后执行事件
  if(keyCode == 13){
    if(typeof(parent.handleKeyDown) == "function"){
      parent.handleKeyDown(rowNum,cellNum);
    }
  }
};

//敲回车增加行
function enterAddRowsHandleKeyDown(myElement,p){
  var c = grid.selModel.getSelectedCell();
  var rowNum = c[0];
  var cellNum = c[1];
  var keyCode = myElement.getKey();

  var totalRowNum = grid.getStore().getCount();
  if(keyCode == 13){
   //add
    var columnValue = grid.getStore().getAt(rowNum).data[fields[cellNum].name];
    if(rowNum == (totalRowNum - 1)&&columnValue!=""){
      if(p != undefined){
         if(typeof(eval('parent.'+p)) == "function"){
           addRows(eval('parent.'+p+'()'));
           //getFocus(rowNum+1,0);
           getFocus(rowNum+1,cellNum);
         }
      }else{
        if(typeof(parent.setDetailsColumnValue) == "function"){
         addRows(parent.setDetailsColumnValue());
         //getFocus(rowNum+1,0);
         getFocus(rowNum+1,cellNum);
        }
      }
    }
  }

  //--------------add--------------------
  //按delete键
  if(keyCode == 46){
    if(totalRowNum > 1){
      //编辑区域失去焦点
      grid.activeEditor.completeEdit();
      var columnValue = grid.getStore().getAt(rowNum).data[fields[cellNum].name];
      var columnName = fields[cellNum].name;
      //delRepeatRows删除gird中关键字重复行
      if(typeof(parent.delRepeatRows)=="function"&&columnValue!="")
        {
          parent.delRepeatRows(grid,columnName,columnValue);
        }else if(typeof(parent.delSomeRows)=="function"&&columnValue!=""){
        //delSomeRows删除一些特定的需要在页面上用到的行
          parent.delSomeRows(grid,rowNum);
        }else
        {
          delRows(rowNum);
        }
    }
  }
}

//按ctrl+delete删除
function delRowsHandleKeyDown(myElement,p){
  var c = grid.selModel.getSelectedCell();
  if(c != null){
    var rowNum = c[0];
    var cellNum = c[1];
    var keyCode = myElement.getKey();

    var totalRowNum = grid.getStore().getCount();
    if(event.ctrlKey && keyCode == 46){
      if(totalRowNum > 1){
        //编辑区域失去焦点
        grid.activeEditor.completeEdit();
        var columnValue = grid.getStore().getAt(rowNum).data[fields[cellNum].name];
        var columnName = fields[cellNum].name;
        //delRepeatRows删除gird中关键字重复行
        if(typeof(parent.delRepeatRows)=="function"&&columnValue!=""){
          parent.delRepeatRows(grid,columnName,columnValue);
        }else if(typeof(parent.delSomeRows)=="function"&&columnValue!=""){
         //add delSomeRows删除一些特定的需要在页面上用到的行
          parent.delSomeRows(grid,rowNum);
        }else{
          delRows(rowNum);
        }
      }else
      {
      //编辑区域失去焦点
        grid.activeEditor.completeEdit();
        var columnValue = grid.getStore().getAt(rowNum).data[fields[cellNum].name];
        var columnName = fields[cellNum].name;
        if(typeof(parent.delSomeRows)=="function"&&columnValue!=""){
          //delSomeRows删除一些特定的需要在页面上用到的行
           parent.delSomeRows(grid,rowNum);
        }
      }
    }else if(keyCode == 13){
      if(totalRowNum >= 1){
        var columnValue = grid.getStore().getAt(rowNum).data[fields[cellNum].name];
        var columnName = fields[cellNum].name;
        //delRepeatRows删除gird中关键字重复行
        if(typeof(parent.lenoKeyDown)=="function" && columnValue!=""){
          parent.lenoKeyDown(grid,columnName,columnValue);
        }
      }
    }
  }
}

/*
   获取所在行
   var r = obj.record;
   store.indexOf(r);
   修改单元格字体颜色
   grid.getView().getCell(0,1).style.color = "red";
   grid.getView().getRow(1).style.backgroundColor="red";
   对指定单元格进行重新赋值
   grid.store.getAt(0).set("RYXX02" , "aaaaa");
   得到焦点
   grid.startEditing(0,0);
   失去焦点
   grid.activeEditor.completeEdit();

   alert(detailsFrame.grid.getCell(rowNum,cellNum));

   var model = grid.getSelectionModel();
   model.selectAll();//选择所有行
   model.selectFirstRow();//选择第一行
   grid.getSelectionModel().selectLastRow();//选择最后一行
   model.selectLastRow([flag]);//选择最后一行,flag为正的话保持当前已经选中的行数,不填则默认false
   model.selectNext();//选择下一行
   model.selectPrevious();//选择上一行
   model.selectRange(tartRow,ndRow, [Boolean keepExisting] );//选择范围间的行
   model.selectRow(row);//选择某一行
   model.selectRows(rows);//选择指定一些行,传递数组如[1,3,5],则分别选择1,3,5行

  model.clearSelections();//清空所有选择
  model.deselectRange( startRow, endRow );//取消从startrow到endrow的记录的选择状态
  model.deselectRow(row);//取消指定行的记录
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

//数据另存为Excel
function checkout(){
  srchCheckout();
//  try{
//    var oXL = new ActiveXObject("Excel.Application");
//    var oWB = oXL.Workbooks.Add();
//    var oSheet = oWB.ActiveSheet;
//
//    var sl=1,curr_row = null;
//    for(var j=0;j<columns.length;j++){
//      curr_row = columns[j];
//      if(curr_row.hidden != true){
//        oSheet.Cells(1,sl).Value = curr_row.header;
//        sl++;
//      }
//    }
//    for(var i=0;i<allData.length;i++){
//      sl=1;
//      for(var j=0;j<columns.length;j++){
//        curr_row = columns[j];
//        if(curr_row.hidden != true){
//          if(curr_row.renderer){
//            oSheet.Cells(i+2,sl).Value = curr_row.renderer(allData[i][j]);
//          }else{
//            oSheet.Cells(i+2,sl).Value = allData[i][j];
//          }
//          sl++;
//        }
//      }
//    }
//    oSheet.Columns.AutoFit;
//    oXL.Visible = true;
//    oXL.UserControl = true;
//  } catch(e){
//    alert('IE\u5de5\u5177->Internet\u9009\u9879->\u5b89\u5168->\u81ea\u5b9a\u4e49\u7ea7\u522b->\u542f\u7528\u5bf9\u672a\u6807\u8bb0\u4e3a\u53ef\u5b89\u5168\u6267\u884c\u811a\u672c\u7684ActiveX\u63a7\u4ef6\u521d\u59cb\u5316\u5e76\u6267\u884c\u811a\u672c');
//  }
}

//查询数据库导出
function srchCheckout(){
  if(multPages==1){
    var form = document.forms[0];
    form.action = ContextRootPath+"/downLoadExcelAction.do";
    form.submit();
  }else{
    document.forms[0].submit();
  }
}

//分页脚本
function getCurrPage(){
  return parseInt(document.getElementById("currPage").value);
}

function getTotalPage(){
  return parseInt(document.getElementById("totalPage").value);
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
