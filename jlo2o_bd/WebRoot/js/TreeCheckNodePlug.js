/* 带有CheckBox的树的选中的插件  
 * author:jn_nian  
 * createTime:2010-10-24 21:46  
 * usage: Ext3使用 plugins : ['treecheck']或plugins:[new Ext.ux.TreePanelCheck()]  
 * Ext2使用 ：plugins:[new Ext.ux.TreePanelCheck()]  
*******************************************************************/ 

Ext.ux.TreePanelCheck = Ext.extend(Ext.tree.TreePanel,{   
    init : function(treePanel){   
        var rootNode = treePanel.getRootNode();   
           
        treePanel.on('expandnode',this.doLazyCheck,rootNode);   
        treePanel.on('checkchange',this.handlerCheck,this);   
    },   
       
       
    //检查子结点选中的情况   
    doChildHasChecked : function(node){   
        var childNodes = node.childNodes;   
        var checkedNum = 0;   
        if(childNodes || childNodes.length>0){   
            for(var i=0;i<childNodes.length;i++){   
                if(childNodes[i].getUI().checkbox.checked){   
                    checkedNum = checkedNum + 1;   
                }   
            }   
        }   
        return checkedNum;   
    },   
       
    //父节点选中   
    doParentCheck : function(node ,checked){   
        var checkbox = node.getUI().checkbox;   
        if(typeof checkbox == 'undefined') return false;   
        node.getUI().checkbox.indeterminate = false;   
        node.getUI().checkbox.checked = checked;   
           
        node.attributes.checked = checked;   
        var childChecked = this.doChildHasChecked(node);   
        if(childChecked == node.childNodes.length){   
            node.getUI().checkbox.checked = true;   
            node.getUI().checkbox.indeterminate = false;   
        }else if(childChecked == 0){   
            var indeterminate = false;   
            node.eachChild(function(child){        
                if(child.getUI().checkbox.indeterminate){   
                    indeterminate = true;   
                    return false;   
                }   
            });    
            node.getUI().checkbox.checked = false;   
            node.getUI().checkbox.indeterminate = indeterminate;   
        }else{   
            node.getUI().checkbox.checked = false;   
            node.getUI().checkbox.indeterminate = true; //半选中状态   
        }   
           
        node.getOwnerTree().fireEvent('check', node, checked);   
        var parentNode = node.parentNode;   
        if( parentNode !== null){   
            this.doParentCheck(parentNode,checked);   
        }   
    },   
       
    handlerCheck : function(node,checked){   
        var parentNode = node.parentNode;   
        if(!Ext.isEmpty(parentNode)) {      
            this.doParentCheck(parentNode,checked);      
        }   
        node.attributes.checked = checked;   
//      node.expandChildNodes(true);   
        node.eachChild(function(child){        
            child.ui.toggleCheck(checked);       
            child.attributes.checked = checked;        
            child.fireEvent('checkchange', child, checked);   
        });   
    },   
       
    //延迟加载选中   
    doLazyCheck : function(node){   
        if(!Ext.isEmpty(node.parentNode)){   
            var nodeChecked = node.getUI().checkbox.checked;   
            //node.expandChildNodes(true);   
            node.eachChild(function(child){   
                child.getUI().checkbox.checked = nodeChecked;   
            });   
        }   
    },   
       
    getPType : function(){   
        return this.ptype;   
    }   
 });   
