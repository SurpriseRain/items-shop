<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/ext-all.css" />
<script type="text/javascript" src="/js/ext-base.js"></script>
<script type="text/javascript" src="/js/ext-all.js"></script>
<style type="text/css">
.new {
  background-image:url(<%=request.getContextPath()%>/img/new.gif)!important;
}
.nonew {
  background-image:url(<%=request.getContextPath()%>/img/nonew.gif)!important;
}
.update {
  background-image:url(<%=request.getContextPath()%>/img/update.gif)!important;
}
.noupdate {
  background-image:url(<%=request.getContextPath()%>/img/noupdate.gif)!important;
}
.delete {
  background-image:url(<%=request.getContextPath()%>/img/delete.gif)!important;
}
.nodelete {
  background-image:url(<%=request.getContextPath()%>/img/nodelete.gif)!important;
}
.error {
  background-image:url(<%=request.getContextPath()%>/img/error.png)!important;
}
.noerror {
  background-image:url(<%=request.getContextPath()%>/img/noerror.png)!important;
}
.cancel {
  background-image:url(<%=request.getContextPath()%>/img/cancel.gif)!important;
}
.nocancel {
  background-image:url(<%=request.getContextPath()%>/img/nocancel.gif)!important;
}
.ok {
  background-image:url(<%=request.getContextPath()%>/img/ok.gif)!important;
}
.nook {
  background-image:url(<%=request.getContextPath()%>/img/nook.gif)!important;
}
.query {
  background-image:url(<%=request.getContextPath()%>/img/query.gif)!important;
}
.noquery {
  background-image:url(<%=request.getContextPath()%>/img/noquery.gif)!important;
}
.fresh {
  background-image:url(<%=request.getContextPath()%>/img/fresh.gif)!important;
}
.nofresh {
  background-image:url(<%=request.getContextPath()%>/img/nofresh.gif)!important;
}
.check{
  background-image:url(<%=request.getContextPath()%>/img/check.gif)!important;
}
.nocheck{
  background-image:url(<%=request.getContextPath()%>/img/nocheck.gif)!important;
}
.stop{
  background-image:url(<%=request.getContextPath()%>/img/stop.gif)!important;
}
.nostop{
  background-image:url(<%=request.getContextPath()%>/img/nostop.gif)!important;
}
.back{
  background-image:url(<%=request.getContextPath()%>/img/back.gif)!important;
}
.noback{
  background-image:url(<%=request.getContextPath()%>/img/noback.gif)!important;
}
.acceptance{
  background-image:url(<%=request.getContextPath()%>/img/acceptance.gif)!important;
}
.noacceptance{
  background-image:url(<%=request.getContextPath()%>/img/noacceptance.gif)!important;
}
.confirmation{
  background-image:url(<%=request.getContextPath()%>/img/Confirmation.gif)!important;
}
.noconfirmation{
  background-image:url(<%=request.getContextPath()%>/img/Confirmation1.gif)!important;
}
.receive{
  background-image:url(<%=request.getContextPath()%>/img/receive.gif)!important;
}
.noreceive{
  background-image:url(<%=request.getContextPath()%>/img/noreceive.gif)!important;
}
.Receivables{
  background-image:url(<%=request.getContextPath()%>/img/Receivables.gif)!important;
}
.noReceivables{
  background-image:url(<%=request.getContextPath()%>/img/Receivables1.gif)!important;
}
.Revoke{
  background-image:url(<%=request.getContextPath()%>/img/Revoke.gif)!important;
}
.noRevoke{
  background-image:url(<%=request.getContextPath()%>/img/Revoke1.gif)!important;
}
.inadmissible{
background-image:url(<%=request.getContextPath()%>/img/Inadmissible1.gif)!important;
}
.noinadmissible{
background-image:url(<%=request.getContextPath()%>/img/Inadmissible.gif)!important;
}
.delivery{
background-image:url(<%=request.getContextPath()%>/img/delivery.gif)!important;
}
.nodelivery{
background-image:url(<%=request.getContextPath()%>/img/nodelivery.gif)!important;
}
.Scrap{
background-image:url(<%=request.getContextPath()%>/img/Scrap.gif)!important;
}
.noScrap{
background-image:url(<%=request.getContextPath()%>/img/Scrap_1.gif)!important;
}
.Deliver_goods{
background-image:url(<%=request.getContextPath()%>/img/Deliver_goods.gif)!important;
}
.noDeliver_goods{
background-image:url(<%=request.getContextPath()%>/img/Deliver_goods_1.gif)!important;
}
.Goods_receipt{
background-image:url(<%=request.getContextPath()%>/img/Goods_receipt.gif)!important;
}
.noGoods_receipt{
background-image:url(<%=request.getContextPath()%>/img/Goods_receipt_1.gif)!important;
}
.Appraisal{
background-image:url(<%=request.getContextPath()%>/img/Appraisal.gif)!important;
}
.noAppraisal{
background-image:url(<%=request.getContextPath()%>/img/Appraisal_1.gif)!important;
}
.Refund{
background-image:url(<%=request.getContextPath()%>/img/Refund.gif)!important;
}
.noRefund{
background-image:url(<%=request.getContextPath()%>/img/Refund_1.gif)!important;
}
.fresh{
background-image:url(<%=request.getContextPath()%>/img/fresh.gif)!important;
}
.nofresh{
background-image:url(<%=request.getContextPath()%>/img/nofresh.gif)!important;
}
.Car{
background-image:url(<%=request.getContextPath()%>/img/Car.gif)!important;
}
.noCar{
background-image:url(<%=request.getContextPath()%>/img/Car_1.gif)!important;
}
.EndSingle{
background-image:url(<%=request.getContextPath()%>/img/EndSingle.gif)!important;
}
.noEndSingle{
background-image:url(<%=request.getContextPath()%>/img/EndSingle_1.gif)!important;
}
.grwork{
background-image:url(<%=request.getContextPath()%>/img/Grwork.gif)!important;
}
.nogrwork{
background-image:url(<%=request.getContextPath()%>/img/Grwork1.gif)!important;
}
.snetwork{
background-image:url(<%=request.getContextPath()%>/img/Snetwork.gif)!important;
}
.nosnetwork{
background-image:url(<%=request.getContextPath()%>/img/Snetwork1.gif)!important;
}
.ExceptionHandling{
background-image:url(<%=request.getContextPath()%>/img/ExceptionHandling.gif)!important;
}
.noExceptionHandling{
background-image:url(<%=request.getContextPath()%>/img/ExceptionHandling_1.gif)!important;
}

.TerminationRevocation{
background-image:url(<%=request.getContextPath()%>/img/TerminationRevocation.gif)!important;
}
.noTerminationRevocation{
background-image:url(<%=request.getContextPath()%>/img/TerminationRevocation_1.gif)!important;
}
.feedbackProcess{
background-image:url(<%=request.getContextPath()%>/img/FeedbackProcess.gif)!important;
}
.nofeedbackProcess{
background-image:url(<%=request.getContextPath()%>/img/FeedbackProcess_1.gif)!important;
}
.approval{
background-image:url(<%=request.getContextPath()%>/img/approval.gif)!important;
}
.noapproval{
background-image:url(<%=request.getContextPath()%>/img/noapproval.gif)!important;
}
.Exception{
background-image:url(<%=request.getContextPath()%>/img/Exception.gif)!important;
}
.noException{
background-image:url(<%=request.getContextPath()%>/img/noException.gif)!important;
}
.recover{
background-image:url(<%=request.getContextPath()%>/img/recover.gif)!important;
}
.norecover{
background-image:url(<%=request.getContextPath()%>/img/norecover.gif)!important;
}
.linkOA{
background-image:url(<%=request.getContextPath()%>/img/linkOA.gif)!important;
}
.nolinkOA{
background-image:url(<%=request.getContextPath()%>/img/nolinkOA.gif)!important;
}
.print{
background-image:url(<%=request.getContextPath()%>/img/print.gif)!important;
}
.noprint{
background-image:url(<%=request.getContextPath()%>/img/noprint.gif)!important;
}
.chargeback{
background-image:url(<%=request.getContextPath()%>/img/chargeback.gif)!important;
}
.nochargeback{
background-image:url(<%=request.getContextPath()%>/img/chargeback1.gif)!important;
}
.runlist{
background-image:url(<%=request.getContextPath()%>/img/runlist.gif)!important;
}
.norunlist{
background-image:url(<%=request.getContextPath()%>/img/runlist_1.gif)!important;
}
.invalidate{
background-image:url(<%=request.getContextPath()%>/img/invalidate.gif)!important;
}
.noinvalidate{
background-image:url(<%=request.getContextPath()%>/img/invalidate_1.gif)!important;
}
.QryFWGD{
background-image:url(<%=request.getContextPath()%>/img/QryFWGD.gif)!important;
}
.noQryFWGD{
background-image:url(<%=request.getContextPath()%>/img/QryFWGD_1.gif)!important;
}
.QryGCFK{
background-image:url(<%=request.getContextPath()%>/img/QryGCFK.gif)!important;
}
.noQryGCFK{
background-image:url(<%=request.getContextPath()%>/img/QryGCFK_1.gif)!important;
}
.QryPCD{
background-image:url(<%=request.getContextPath()%>/img/QryPCD.gif)!important;
}
.noQryPCD{
background-image:url(<%=request.getContextPath()%>/img/QryPCD_1.gif)!important;
}
</style>
</head>
<body>
<div id="main-ct" style="font-family:'宋体';width:100%;height:25px;"></div>
</body>
<script type="text/javascript">
var tb;
function showButton(){
  // turn on quick tips
  Ext.QuickTips.init();

  var cview = Ext.DomHelper.append('main-ct',
  {cn:[{id:'main-tb'}]}
  );//toolbar的载入点

  // create the primary toolbar
  tb = new Ext.Toolbar('main-tb');
  tb.add({
    id:'new',//引用
    text:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',//文本显示
    disabled:true,//禁用
    handler:newclick,//onclick方法
    iconCls:'x-btn-text-icon nonew',//样式引入图片
    tooltip:'新增'//提示
  }, {
    id:'update',
    text:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
    disabled:true,//禁用
    handler:updateclick,
    iconCls:'noupdate',
    tooltip:'修改'
  },{
    id:'delete',
    text:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
    disabled:true,
    handler:delclick,
    iconCls:'nodelete',
    tooltip:'删除'
  },{
    id:'cancel',
    text:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
    disabled:true,
    handler:cancelclick,
    iconCls:'nocancel',
    tooltip:'取消'
  },{
    id:'ok',
    text:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
    disabled:true,
    handler:okclick,
    iconCls:'nook',
    tooltip:'保存'
  });
}

//将按钮置为可用
function setButtonEnable(buttonId){
  tb.items.get(buttonId).enable();
  Ext.getCmp(buttonId).setIconClass(buttonId);
}

//将按钮置为不可用
function setButtonNotEnable(buttonId){
  Ext.getCmp(buttonId).setIconClass("no"+buttonId);
  tb.items.get(buttonId).disable();
}

//增加一个按钮
function addButton(buttonId,titleName,textValue){
  var tempTextValue = (textValue != undefined)?textValue:"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
  tb.add({
    id:buttonId,
    text:tempTextValue,
    disabled:true,
    handler:eval(buttonId+"click"),
    iconCls:"no"+buttonId,
    tooltip:titleName
  });
}

//删除一个按钮
function removeButton(buttonId){
  var button = Ext.getCmp('main-tb');
  Ext.fly(button.items.get(buttonId).getEl().dom.parentNode).remove();
  button.items.removeAt(buttonId);
}

//增加一个按钮
function addButtonW(buttonId,titleName,textValue){
  var tempTextValue = (textValue != undefined)?textValue:"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
  tb.add({
    id:buttonId,
    text:tempTextValue,
    disabled:true,
    handler:eval(buttonId+"click"),
    iconCls:"no"+buttonId,
    tooltip:titleName
  });
}
</script>
</html>
