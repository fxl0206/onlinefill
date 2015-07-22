Ext.ns("Morik", "Morik.Office", "Morik.Util", "Morik.Office.Department");
Morik.Office.LeftMenu = function(config){
    var d = Ext.apply({// default set
        width: 200,
        split: true,
        region: 'west',
        collapseMode: 'mini',
        defaults: {
            border: false
        },
        layoutConfig: {
            animate: true
        }
    }, config || {});
    
    config = Ext.apply(d, {
        layout: 'accordion',
        collapsible: true
    });
    
    Morik.Office.LeftMenu.superclass.constructor.call(this, config);
    
    //改进，并为增加了个配置项tree!
    for (var i=0;i<this.trees.length;i++) 
        this.add({
            title: this.trees[i].getRootNode().text,
            items: [this.trees[i]]
        });
    
    // 事件处理
    this.addEvents('nodeClick');
    this.addEvents('addtask');
    this.addEvents('mytask');
    this.initTreeEvent();
}

Ext.extend(Morik.Office.LeftMenu, Ext.Panel, {
    initTreeEvent: function(){
        if (!this.items) 
            return;
        else {
            var p = this.items.itemAt(1);
            if (p) 
                var t = p.items.itemAt(0);
            if (t) 
                t.on({
                    'click': function(node, event){
                        event.stopEvent();
                        this.fireEvent('nodeClick', node.attributes);
                    },
                    scope: this
                });
            p = this.items.itemAt(0);
            if (p) 
                t = p.items.itemAt(0);
            if (t) 
                t.on({
                    'click': function(node, event){
                        event.stopEvent();
                        this.fireEvent('addtask', node.attributes);
                    },
                    scope: this
                });
            p = this.items.itemAt(2);
            if (p) 
                t = p.items.itemAt(0);
            if (t) 
                t.on({
                    'click': function(node, event){
                            event.stopEvent();
                            this.fireEvent('mytask', node.attributes);
                    },
                    scope: this
                });
        }
    }
})
