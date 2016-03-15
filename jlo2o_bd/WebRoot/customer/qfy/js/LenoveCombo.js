/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://extjs.com/license
 */

/**
 * @class Ext.form.ComboBox
 * @extends Ext.form.TriggerField
 * A combobox control with support for autocomplete, remote-loading, paging and many other features.
 * @constructor
 * Create a new ComboBox.
 * @param {Object} config Configuration options
 */
Ext.form.SkyCombo = Ext.extend(Ext.form.ComboBox, {
 triggerClass : 'x-form-search-trigger',
 loadingText: '查找中...'
});
Ext.reg('skycombo', Ext.form.SkyCombo);

var LenoveXX = new Ext.data.Store({
  proxy: new Ext.data.ScriptTagProxy({
  url: ''
}),

reader: new Ext.data.JsonReader({
  root: 'topics',
  totalProperty: 'totalCount'
  }, [
   {name: 'dm', mapping: 'dm'},
   {name: 'mc', mapping: 'mc'}
   ])
});

LenoveXX.on({"beforeload":function (){
 if(typeof(parent.setGridLenoveUrl)=='function')parent.setGridLenoveUrl(this);
}});
