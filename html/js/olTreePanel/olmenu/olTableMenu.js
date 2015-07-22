Ext.tree.olTableMenu = Ext.extend(Ext.menu.Menu, {
	id : 'tablemenu',
	node : {},
	form : {},
	win : {},
	minWidth : 100,
	initComponent : function() {
		this.items = [ {
			id : 'addNode',
			text : '添加',
			iconCls : 'orgadd',
			handler : this.olNodeAdd,
			scope : this
		}, {
			id : 'modifNode',
			text : '修改',
			iconCls : 'orgedit',
			handler : this.olNodeEdit,
			scope : this
		}, '-', {
			id : 'delNode',
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
		Ext.tree.olTableMenu.superclass.initComponent.call(this);
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
				fieldLabel : "目录名称",
				id : "olname",
				allowBlank : false,
				blankText : "不能为空",
				name : "UserName",
				value:this.node.text,
				anchor : "100%"
			} ],
			buttons : [
					{
						text : "修改",
						handler : function() {
							if(form.getForm().isValid())
							{
							win.hide();
							var myMask=new Ext.LoadMask(Ext.getBody(),{
								msg:'正在处理请稍候...'
							});
							myMask.show();
							var oltext = Ext.getCmp('olname').getValue();
							var olid = this.node.id;
							function dook(response, options) {
								try {
									var organize = Ext.util.JSON.decode(response.responseText);
									if(organize.success)
									{
									this.node.setText(organize.text);
									win.close();
									myMask.hide();
									Ext.Msg.alert('操作提示','<font color=green>'+organize.msg+'</font>');
									}
									else
									{
										win.close();
										myMask.hide();
										Ext.Msg.alert('操作失败','<font color=red>'+organize.msg+'</font>');
									}
								} catch (e) {
									alert("wrong");
								}
							}
							var fail=function(){
								win.close();
								myMask.hide();
								Ext.Msg.alert('操作失败','<font color=red>资源服务器无法访问！</font>');
							};
							Ext.Ajax.request( {
								url : 'resources/tabledir/update',
								jsonData : {
									id : olid,
									text : oltext
								},
								method : 'POST',
								success : dook,
								failure:fail,
								scope : this
							});
							}
						},
						scope : this
					}, {
						text : "取消",
						handler : function() {
							win.close();
						},
						scope : this
					} ]

		});
		win = new Ext.Window( {
			width : 250,
			title : '修改目录',
			items : [ form ]
		});
		win.show();
	},
	olNodeAdd : function() {
		if (this.node.isLeaf()) {
			return;
		}
		form=new Ext.form.FormPanel( {
			frame : true,
			border : false,
			width : 'auto',
			layout : "form",
			labelWidth : 70,
			labelAlign : "left",
			items : [ {
				xtype : "textfield",
				fieldLabel : "目录名称",
				allowBlank : false,
				id : 'olname',
				blankText : "不能为空",
				name : "UserName",
				anchor : "100%"
			} ],
			buttons : [
					{
						text : "添加",
						handler : function() {
						if (form.getForm().isValid()) {
						var olleaf = false;
						var myMask=new Ext.LoadMask(Ext.getBody(),{msg:'正在处理请稍候...'});
						win.hide();
						myMask.show();
						var oltext = Ext.getCmp('olname').getValue();
						var olpid = this.node.id;
						function dook(response, options) {
							try {
								var organize = Ext.util.JSON
										.decode(response.responseText);
								if(organize.success)
								{
								var varnode = new Ext.tree.TreeNode( {
									id : organize.id,
									text : organize.text,
									leaf : organize.leaf
								});
								this.node.appendChild(varnode);
								myMask.hide();
								Ext.Msg.alert('操作提示','<font color=green>'+organize.msg+'</font>');
								win.close();
								}
								else
								{
									myMask.hide();
									Ext.Msg.alert('操作失败','<font color=red'>+organize.msg+'</font>');
									win.close();
									
								}
							} catch (e) {
								alert("wrong");
							}
							win.close();
						}
						var fail=function(){
							myMask.hide();
							Ext.Msg.alert('操作失败','<font color=red>资源服务器无法访问！</font>');
							win.close();
						};
						Ext.Ajax.request( {
							url : 'resources/tabledir/add',
							jsonData : {
								text : oltext,
								pid : olpid,
								leaf : olleaf
							},
							method : 'POST',
							success : dook,
							failure:fail,
							scope : this
						});
					}
				},
				scope : this
					}, {
						text : "取消",
						handler : function() {
							win.close();
						},
						scope : this
					} ]

		});
		win = new Ext.Window( {
			width : 250,
			title : '添加目录',
			items : [ form ]
		});
		win.show();
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
						removeMask : true,
						msg : "正在处理..."
					});
					myMask.show();
					var tsuccess = function(response,options) {
						Ext.Msg.alert(response.responseText);
						var js=Ext.util.JSON.decode(response.responseText);
						if(js.success)
						{
						this.node.remove();
						myMask.hide();
						Ext.Msg.alert('操作提示','<font color=green>'+js.msg+'</font>');
						}
						else
						{
							myMask.hide();
							Ext.Msg.alert('操作失败','<font color=red>'+js.msg+'</font>');
						}
					};
					var tFn = function() {
						myMask.hide();
						Ext.Msg.alert('操作失败','<font color=red>资源服务器无法访问！</font>');
					};
					Ext.Ajax.request( {
						url : 'resources/tabledir/delete',
						jsonData : {
							id : this.node.id,
							text : this.node.text,
							leaf : this.node.leaf
						},
						success : tsuccess,
						failure : tFn,
						method : 'POST',
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
