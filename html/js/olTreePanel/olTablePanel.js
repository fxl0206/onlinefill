Ext.tree.olTablePanel = Ext.extend(Ext.tree.TreePanel, {
    initComponent: function(){
        var loader = new Ext.tree.TreeLoader({
            dataUrl: 'resources/tabledir/gettree',
            requestMethod:'GET'
        });
        var root = new Ext.tree.AsyncTreeNode({
            text: '表格管理',
            expanded: true
        });
        Ext.apply(this, {
            loader: loader,
           rootVisible: false,
            root: root,
            border: false,
            contextmenu: new Ext.tree.olTableMenu()
        });
        Ext.tree.olTablePanel.superclass.initComponent.call(this);
        this.on("contextmenu", function(node, e){
            this.contextmenu.showAt(e.getXY());
            this.contextmenu.node = node;
        });
    }
});
