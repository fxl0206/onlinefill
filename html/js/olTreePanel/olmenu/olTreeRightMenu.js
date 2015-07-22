Ext.tree.olTreeRightMenu = Ext.extend(Ext.menu.Menu, {
	id : 'thContextMenu',
	node : {},
	form : {},
	win : {},
	minWidth:80,
	initComponent : function() {
		this.items = [ {
			id : 'saddNode',
			text : '添加',
			iconCls:'orgadd',
			handler : this.olNodeAdd,
			scope : this
		}, {
			id : 'smodifNode',
			text : '修改',
			iconCls : 'orgedit',
			handler : this.olNodeEdit,
			scope : this
		}, {
			id : 'sdelNode',
			text : '删除',
			iconCls : 'orgdelete',
			handler : this.olNodeDelete,
			scope : this
		} ];
		// Ext.apply(this, {
	// items : [ {
	// id : 'addNode',
	// text : '���'
	// }, {
	// id : 'modifNode',
	// text : '�޸�',
	// iconCls : 'edit'
	// }, '-', {
	// id : 'delNode',
	// text : 'ɾ��',
	// iconCls : 'remove'
	// } ]
	// });
	Ext.tree.olTreeRightMenu.superclass.initComponent.call(this);
},
olNodeEdit : function() {
	var tcheck = true;
	if (!this.node.isLeaf())
		tcheck = false;
	form = new Ext.form.FormPanel( {
		frame : true,
		border : false,
		width : 'auto',
		layout : "form",
		labelWidth : 70,
		labelAlign : "left",
		items : [ {
			xtype : "textfield",
			fieldLabel : "机构名称",
			id : "olname",
			allowBlank : false,
			blankText : "不能为空",
			name : "UserName",			
			value: this.node.text,
			anchor : "100%"
		} ],
		buttons : [ {
			text : "修改",
			handler : function() {
			if(form.getForm().isValid())
			{
			this.win.hide();
			var oltext = Ext.getCmp('olname').getValue();
			var olid = this.node.id;
	        var myMask = new Ext.LoadMask(Ext.getBody(), {   

                msg : "正在处理请稍候..."  

            });  
			var fail=function()
			{
				myMask.hide();
				this.win.close();
				Ext.Msg.alert("修改失败",'<font color=red>提示：资源服务器无法访问</font>');
			};
	        myMask.show();
			Ext.Ajax.request( {
				url : 'resources/orgsource/edit',
				jsonData : {
					id:olid,
					text : oltext
				},
				method : 'POST',
				success:dook,
				failure:fail,
				scope : this
			});
			function dook(response,options) {
				try{
					myMask.hide();
					var organize=Ext.util.JSON.decode(response.responseText);
					if(organize.success)
					{
					this.node.setText(organize.text);
					Ext.Msg.alert("操作提示",'<font color=green>'+organize.msg+'</font>');					
					}
					else{
						Ext.Msg.alert("修改失败",'<font color=red>提示：'+organize.msg+'</font>');	
					}
				}catch(e)
				{
					alert("wrong");
				}
				this.win.close();
			}
			}
			},
			scope : this
		}, {
			text : "取消",
			handler : function() {
				this.win.close();
			},
			scope : this
		} ]

	});
	this.win = new Ext.Window( {
		width : 250,
		title : '修改机构',
		modal : true,
		iconCls:'orgedit',
		items : [form ]
	});
	this.win.show();
},
olNodeAdd : function() {
	if (this.node.isLeaf()) {
		return;
	}
	this.form = new Ext.form.FormPanel( {
		frame : true,
		border : false,
		width : 'auto',
		layout : "form",
		labelWidth : 70,
		labelAlign : "left",
		items : [ {
			xtype : "textfield",
			fieldLabel : "机构名称",
			allowBlank : false,
			id : 'olname',
			blankText : "不能为空",
			name : "UserName",
			emptyText:'请输入机构名称',
			anchor : "100%"
		}
//		, {
//			xtype : "radiogroup",
//			id : "olreaf",
//			fieldLabel : '叶节点',
//			name : "SmallName",
//			anchor : "90%",
//			border : false,
//			items : [ {
//				xtype : 'radio',
//				checked : true,
//				name : "borrow",
//				inputValue : true, // �ύʱ���͵Ĳ���ֵ
//				boxLabel : '是'
//			}, {
//				xtype : 'radio',
//				inputValue : false,
//				name : "borrow",
//				boxLabel : '否'
//			} ]
//		}
		],
		buttons : [ {
			text : "添加",
			handler : function() {
			if(this.form.getForm().isValid())
			{
				this.win.hide();
				var olleaf = false;
//				if (this.form.getForm().getValues()['borrow'] == "true")
//					olleaf = true;
				var oltext = Ext.getCmp('olname').getValue();
				var olpid = this.node.id;
		        var myMask = new Ext.LoadMask(Ext.getBody(), {   

                    msg : "正在处理请稍候..."  

                });  
				var fail=function()
				{
					myMask.hide();
					this.win.close();
					Ext.Msg.alert('操作失败','<font color=red>资源服务器无响应！</font>');
				}				
				Ext.Ajax.request( {
					url : 'resources/orgsource/add',
					jsonData : {
						text : oltext,
						pid : olpid,
						leaf : olleaf
					},
					method : 'POST',
					success:dook,
					failure:fail,
					scope : this
				});
				myMask.show();
				function dook(response,options) {
					try{
						myMask.hide();
						var organize=Ext.util.JSON.decode(response.responseText);
						if(organize.success)
						{
						var varnode=new Ext.tree.TreeNode({
							id:organize.id,
							text:organize.text,
							leaf:organize.leaf
						});
						this.node.appendChild(varnode);
						Ext.Msg.alert('操作提示','<font color=green>'+organize.msg+'</font>');
						}
						else
						{
							Ext.Msg.alert('添加失败','<font color=red>提示:'+organize.msg+'</font>');	
						}
					}catch(e)
					{
						alert("wrong");
					}
					this.win.close();
				}
			}
			},
			scope : this
		}, {
			text : "取消",
			handler : function() {
				this.win.close();
			},
			scope : this
		} ]

	});
	this.win = new Ext.Window( {
		width : 250,
		title : '添加机构',
		iconCls:'orgadd',
		modal : true,
		items : [ this.form ]
	});
	this.win.show();
},
olNodeDelete : function() {
	var option = {
		title : '删除确认',
		msg : '你真的要删除？',
		modal : true,
		buttons : Ext.Msg.OKCANCEL,
		icon : Ext.Msg.INFO,
		fn : function(b, t) {
			if (b == 'ok') {
		        var myMask = new Ext.LoadMask(Ext.getBody(), {   

                    msg : "正在处理请稍候..."  

                });  
		        myMask.show();
				var tsuccess = function(response,options) {
					myMask.hide();
					var js=Ext.util.JSON.decode(response.responseText);
					if(js.success)
					{
					this.node.remove();					
					Ext.Msg.alert('操作提示','<font color=green>'+js.msg+'</font>');
					}
					else
					{
						Ext.Msg.alert('删除失败','<font color=red>'+js.msg+'</font>');
					}
				};
				var tFn = function() {
					myMask.hide();
					Ext.Msg.alert('删除失败','<font color=red>提示：资源服务器无法访问</font>');
				};
				Ext.Ajax.request( {
					url : 'resources/orgsource/delete',
					jsonData:{id:this.node.id},
					success : tsuccess,
					failure : tFn,
					method:'POST',
					scope : this
				});
			}
			// this.getOwnerTree();
	},
	scope : this
	};
	Ext.Msg.show(option);
}
});
// Ext.reg('mycomponent', MyComponent);
