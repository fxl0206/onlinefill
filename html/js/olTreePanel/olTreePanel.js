Ext.tree.olTreePanel=Ext.extend(Ext.tree.TreePanel,{
	initComponent:function(){
        var loader = new Ext.tree.TreeLoader({
            dataUrl: 'resources/orgsource/tree',
            requestMethod:'GET'
        });
        var root = new Ext.tree.AsyncTreeNode({
        	id:'admin',
            text: '机构设置',
            expanded:true
        });
		  Ext.apply(this,{
		 loader:loader,
		 rootVisible:false,
		 root:root,
		 border:false,
		 contextmenu: new Ext.tree.olTreeRightMenu()
		});
        Ext.tree.olTreePanel.superclass.initComponent.call(this);
        this.on("contextmenu", function(node, e){
            this.contextmenu.showAt(e.getXY());
            this.contextmenu.node = node;
        });       
	}
});