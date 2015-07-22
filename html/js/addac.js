//f7853bab2fb34b07012fb34b07340000
function myte() {
	var form = new Ext.form.FormPanel( {
		labelWidth : 60,
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		autoWidth : true,
		defaultType : 'textfield',
		items : [ {
			fieldLabel : '旧密码',
			name : 'oldpwd',
			allowBlank : false
		}, {
			fieldLabel : '新密码',
			name : 'newpwd',
			allowBlank : false
		}, {
			fieldLabel : '再次输入',
			name : 'repick',
			allowBlank : false
		} ],
		buttons : [
				{
					text : '修改',
					handler : function() {
						if (form.getForm().isValid()) {
							var values = form.getForm().getValues();
							if (values['newpwd'] != values['repick']) {
								alert("两次输入的新密码不正确，重新输入");
								return;
							}
							var json = {
								oldpwd : values['oldpwd'],
								newpwd : values['newpwd'],
								repick : values['repick']
							};
							Ext.Ajax.request( {
								url : 'resources/acounts/modpwd',
								jsonData : json,
								success : function(response, options) {
									var json = Ext.util.JSON
											.decode(response.responseText);
									Ext.Msg.alert('操作提示', json.msg);
									if (json.success) {
										win.close();
									}
								},
								method : 'POST',
								scope : this
							});
						}
					},
					scope : this

				}, {
					handler : function() {
						win.close();
					},
					text : '取消'
				} ]
	});
	win = new Ext.Window( {
		title : '修改密码',
		closable : true,
		width : 250,
		autoHeight : true,
		border : false,
		plain : true,
		modal : true,
		items : [ form ]
	});
	win.show();
}
function acshow(inputvar) {

	// 定义一个Panel
	var Tree = Ext.tree;
	var tree = new Tree.TreePanel( {
		autoScroll : true,
		region : 'west',
		animate : true,
		enableDD : true,
		width : 160,
		rootVisible : false,
		containerScroll : true,
		loader : new Tree.TreeLoader( {
			dataUrl : 'resources/orgsource/tree',
			requestMethod : 'GET'
		})
	});

	var root = new Tree.AsyncTreeNode( {
		text : 'Ext JS',
		draggable : false,
		id : 'source',
		expanded : true
	});
	tree.on( {
		'click' : function(node, event) {
			Ext.Ajax.request( {
				url : 'resources/acounts/getbyorgid',
				method : 'GET',
				params : {
					orgid : node.id
				},
				success : function(response, options) {
					grid.store.loadData(Ext.util.JSON
							.decode(response.responseText));
				},
				scopse : this
			});
		}
	}, this);
	tree.setRootNode(root);
	var store = new Ext.data.JsonStore( {
		root : 'acounts',
		fields : [ {
			name : 'acount'
		}, {
			name : 'pwd'
		}, {
			name : 'owner'
		}, {
			name : 'email'
		}, {
			name : 'tel'
		}, {
			name : 'credate'
		}, {
			name : 'sta'
		}, {
			name : 'detail'
		} ]
	});
	var grid = new Ext.grid.GridPanel( {
		title : '账号列表',
		region : 'center', // 放在西边，即左侧
		plit : true,
		width : 200,
		height : 200,
		border : false,
		store : store,
		columns : [ {
			id : 'acount',
			header : '账户名',
			width : 100,
			sortable : true,
			dataIndex : 'acount'
		}, {
			header : '持有者',
			width : 100,
			sortable : true,
			dataIndex : 'owner'
		}, {
			header : '电话号码',
			width : 100,
			sortable : true,
			dataIndex : 'tel'
		}, {
			id : 'cdetail',
			dataIndex : 'detail',
			header : '备注'
		} ],
		bbar : [ {
			iconCls : 'acountadd',
			text : '添加到下面',
			handler : function() {
				var myMask = new Ext.LoadMask(win.getEl(), {
					msg : '正在处理请稍候...'
				});
				myMask.show();
				this.s = grid.getSelectionModel().getSelections();
				this.arr = new Array();
				for ( var i = 0, r; r = this.s[i]; i++) {
					var js = {
						acname : r.data.acount,
						taid : inputvar
					// acowner : r.data.owner,
					// acphone : r.data.tel,
					// acdetail : r.data.detail
					};
					this.arr[i] = js;
				}
				var fail = function() {
					myMask.hide();
					Ext.Msg.alert('操作失败', '<font color=red>资源服务器无法访问！</font>');
				};
				Ext.Ajax.request( {
					url : 'resources/deals/add',
					jsonData : this.arr,
					failure : fail,
					success : function(response, options) {
						var js = Ext.util.JSON.decode(response.responseText);
						if (js.success) {
							var tmp = '';
							for ( var i = 0, r; r = this.s[i]; i++) {
								if (js.sus[i] == 'ok') {
									grid2.store.insert(0, r);
									grid2.getView().refresh();
								} else {
									tmp += r.data.acount + '#重复！<br>'
								}
							}
							myMask.hide();
							Ext.Msg.alert('操作提示', '<font color=green>' + js.msg
									+ '</font><br>' + tmp);
						} else {
							myMask.hide();
							Ext.Msg.alert('操作提示',
									'<font color=green>' + js.msg + '</font>');
						}

					},
					scope : this
				});
				// alert(Ext.util.JSON.encode(this.arr));
			},
			scope : this
		} ]
	});
	var store2 = new Ext.data.JsonStore( {
		root : 'acounts',
		autoLoad : true,
		url : 'resources/deals/getlink?taid=' + inputvar,
		fields : [ {
			name : 'acount'
		}, {
			name : 'pwd'
		}, {
			name : 'owner'
		}, {
			name : 'email'
		}, {
			name : 'tel'
		}, {
			name : 'credate'
		}, {
			name : 'sta'
		}, {
			name : 'detail'
		} ]
	});
	var grid2 = new Ext.grid.GridPanel( {
		title : '安排填表账号',
		region : 'south', // 放在西边，即左侧
		plit : true,
		width : 200,
		height : 200,
		border : false,
		store : store2,
		columns : [ {
			id : 'acount',
			header : '账户名',
			width : 100,
			sortable : true,
			dataIndex : 'acount'
		}, {
			header : '持有者',
			width : 100,
			sortable : true,
			dataIndex : 'owner'
		}, {
			header : '电话号码',
			width : 100,
			sortable : true,
			dataIndex : 'tel'
		}, {
			id : 'cdetail',
			dataIndex : 'detail',
			header : '备注'
		} ],
		bbar : [ {
			iconCls : 'acountdelete',
			text : '删除所选',
			handler : function() {
				var option = {
					title : '删除确认',
					msg : '你真的要删除？',
					modal : true,
					buttons : Ext.Msg.OKCANCEL,
					icon : Ext.Msg.INFO,
					fn : function(b, t) {
						if (b == 'ok') {
							var myMask = new Ext.LoadMask(win.getEl(), {
								msg : '正在处理请稍候...'
							});
							myMask.show();
							this.q = grid2.getSelectionModel().getSelections();
							this.qarr = new Array();
							for ( var i = 0, r; r = this.q[i]; i++) {
								var js = {
									acname : r.data.acount,
									taid : inputvar
								// acowner : r.data.owner,
								// acphone : r.data.tel,
								// acdetail : r.data.detail
								};
								this.qarr[i] = js;
							}
							var fail=function()
							{
								myMask.hide();
								Ext.Msg.alert('操作失败','<font color=red>资源服务器无法访问！</font>');
								
							}
							Ext.Ajax.request( {
								url : 'resources/deals/delete',
								jsonData : this.qarr,
								success : function(response, options) {
									var js=Ext.util.JSON.decode(response.responseText);
									if(js.success)
									{
									for ( var i = 0, r; r = this.q[i]; i++) {
										grid2.store.remove(r);
									}
									myMask.hide();
									Ext.Msg.alert('操作提示','<font color=green>'+js.msg+'</font>');
									}
									else
									{
										myMask.hide();
										Ext.Msg.alert('操作失败','<font color=red>'+js.msg+'</font>');
									}
								},
								scope : this
							});
						}
					},
					scope : this
				}
				Ext.Msg.show(option);
			},
			scope : this
		} ]
	});
	var nav3 = new Ext.Panel( {
		title : '安排填表账号',
		region : 'south', // 放在西边，即左侧
		split : true,
		width : 200,
		height : 200,
		border : false
	});
	var nav1 = new Ext.Panel( {
		region : 'center', // 放在西边，即左侧
		split : true,
		width : 200,
		layout : 'border',
		margins : '3 0 3 3',
		cmargins : '3 3 3 3',
		items : [ grid, grid2 ]
	});
	var win = null;
	// 如果窗口第一次被打开时才创建
	if (!win) {
		win = new Ext.Window( {
			title : '安排填表任务',
			closable : true,
			width : 600,
			height : 400,
			border : false,
			plain : true,
			modal : true,
			maximizable : true,
			layout : 'border',
			items : [ tree, nav1 ]
		// 把上面创建的panel和TabPanel放在window中，并采用border方式布局
				});
	}
	win.show();
	return false;
}
function actable(taid) {
	Ext.Ajax.request( {
		url : 'resources/tablelist/gettab',
		method : 'GET',
		params : {
			taid : taid
		},
		success : function(response, options) {

			var rtjs = Ext.util.JSON.decode(response.responseText);
			var record = Ext.data.Record.create(rtjs.feilds);
			var store = new Ext.data.JsonStore( {
				root : 'rows',
				fields : rtjs.feilds
			});
			var editor = new Ext.ux.grid.RowEditor( {
				url : "resources/tablelist/fill",
				saveText : '同步',
				cancelText:'取消',
				tabid : taid
			});
			var grid = new Ext.grid.GridPanel( {
				region : 'center',
				plugins : [ editor ],
				plit : true,
				border : false,
				store : store,
				columns : rtjs.columns,
				tbar : [ {
					iconCls:'dealadd',
					text : '添加一行',
					handler : function() {
						s = new record();
						// alert(grid.store);
					count = grid.store.getCount();
					// alert(count);
					grid.store.insert(count, s);
					editor.startEditing(count, false);
				},
				scope : this
				} ]
			});
			grid.store.loadData(rtjs.rows);
			// grid.store.insert(0,new record());
		var win = null;
		// 如果窗口第一次被打开时才创建
		if (!win) {
			win = new Ext.Window( {
				title : '<div align="center">此处你可以添加或修改表格内容</div>',
				closable : true,
				width : 800,
				height : 600,
				border : false,
				plain : true,
				modal : true,
				// maximizable:true,
				maximized : true,
				layout : 'border',
				items : [ grid ]
			// 把上面创建的panel和TabPanel放在window中，并采用border方式布局
					});
		}
		win.show();
	}
	});
	return false;
}
function wordshow(inputvar)
{	
	Ext.grid.sefstore= new Ext.data.JsonStore( {
		root : 'rows',
		autoLoad : true,
		url : 'resources/tablelist/getwordlist?taid=' + inputvar,
		fields : [ {
			name : 'rowid'
		}, {
			name : 'acname'
		}, {
			name : 'owner'
		}]
	});
	function showbt(val) {

		return '<img src="extjs/ext/images/table_open.gif" style="cursor:hand" onclick="return openwordbyid(\''
				+ val
				+ '\',\''+inputvar+'\')">&nbsp&nbsp&nbsp&nbsp'
				+ '<img src="extjs/ext/images/worddelete.gif" style="cursor:hand" onclick="return '
				+'worddelete(\''
				+ val
				+ '\',\''+inputvar+'\')">&nbsp&nbsp&nbsp&nbsp'
				+ '<a href="servlet/DownloadServlet?tabid='
				+ inputvar
				+ '&rowid='+val+'" target="_blank"><img src="extjs/ext/images/tabledown.gif"></a>';
	};
	var grid2 = new Ext.grid.GridPanel( {
	//	title : '安排填表账号',
		region : 'center', // 放在西边，即左侧
		plit : true,
		//width : 200,
		//height : 200,
		border : false,
		store : Ext.grid.sefstore,
		columns : [ {
			id : 'rowid',
			header : 'ID标识',
			width : 100,
			sortable : true,
			dataIndex : 'rowid'
		}, {
			header : '账号',
			width : 100,
			sortable : true,
			dataIndex : 'acname'
		}, {
			header : '姓名',
			width : 100,
			sortable : true,
			dataIndex : 'owner'
		}, {
			dataIndex : 'rowid',
			renderer : showbt,
			header : '操作'
		} ]
	});
	var win = null;
	// 如果窗口第一次被打开时才创建
	if (!win) {
		win = new Ext.Window( {
			title : '查看已完成成情况',
			closable : true,
			width : 600,
			height : 400,
			border : false,
			plain : true,
			modal : true,
			maximizable : true,
			layout : 'border',
			items : [grid2]
		// 把上面创建的panel和TabPanel放在window中，并采用border方式布局
				});
	}
	win.show();
	return false;
}
function openwordbyid(wordid,taid)
{
	var myMask = new Ext.LoadMask(
			Ext
					.getBody(),
			{
				msg : '正在加载数据请稍候...'
			});
	myMask.show();
	Ext.Ajax.request( {
		url : '/resources/tablelist/getwordrow',
		method:'POST',
		jsonData:{rowid:wordid,taid:taid},
		success : function(response, options) {
			myMask.hide();			
			//Ext.Msg.alert("成功加载");	
			win = new Ext.Window( {
				title : '<div align="center">添加word文档</div>',
				closable : true,
				width : 800,
				height : 600,
				tbar : [ {
					iconCls:'dealadd',
					text : '保存到服务器',
					handler : function() {
					alert("确定提交？...");													
					var myMask = new Ext.LoadMask(
							Ext
									.getBody(),
							{
								msg : '正在提交数据请稍候...'
							});
					myMask.show();
					
							Ext.Ajax.request( {
								url : '/resources/tablelist/updatewordrow',
								method:'POST',
								jsonData:{taid:taid,rowid:wordid,content:KE.html('content1')},
								success : function(response, options) {
									myMask.hide();
									//store.reload();
									Ext.Msg.alert("处理结果",response.responseText);
									//win.close();
								},
								scope:this
							});
				},
				scope : this
				} ],
				border : false,
				plain : true,
				modal : true,
				// maximizable:true,
				maximized : true,
				autoScroll:true,
				html:'<textarea id="content1" name="content" style="width:100%;height:600px;visibility:hidden;">'+response.responseText+'</textarea>'
					});
			win.show();  											
			KE.init({
				id : 'content1',
				//items : ['hello'],
				//height:'100%',
				afterCreate : function(id) {
				KE.util.focus(id);
				}
			});		
			KE.create('content1');
		},
		scope:this
	});	
}
function openwordbyuser(inputvar)
{
	Ext.grid.sefstore= new Ext.data.JsonStore( {
		root : 'rows',
		autoLoad : true,
		url : 'resources/tablelist/getwordlist1?taid=' + inputvar,
		fields : [ {
			name : 'rowid'
		}]
	});
	function showbt(val) {

		return '<img src="extjs/ext/images/table_open.gif" style="cursor:hand" onclick="return openwordbyid(\''
				+ val
				+ '\',\''+inputvar+'\')">&nbsp&nbsp&nbsp&nbsp'
				+ '<img src="extjs/ext/images/worddelete.gif" style="cursor:hand" onclick="return '
				+'worddelete(\''
				+ val
				+ '\',\''+inputvar+'\')">&nbsp&nbsp&nbsp&nbsp'
				+ '<a href="servlet/DownloadServlet?tabid='
				+ inputvar
				+ '&rowid='+val+'" target="_blank"><img src="extjs/ext/images/tabledown.gif"></a>';
	};
	var grid2 = new Ext.grid.GridPanel( {
	//	title : '安排填表账号',
		region : 'center', // 放在西边，即左侧
		plit : true,
		//width : 200,
		//height : 200,
		border : false,
		store : Ext.grid.sefstore,
		tbar:[
		      {
		    	  text:'添加',
		    	  handler:function(){
		    		var myMask = new Ext.LoadMask(
					Ext
							.getBody(),
					{
						msg : '正在加载数据请稍候...'
					});
			myMask.show();
			Ext.Ajax.request( {
				url : '/resources/tablelist/getwordbyuser',
				method:'POST',
				jsonData:{taid:inputvar},
				success : function(response, options) {
					myMask.hide();			
					//Ext.Msg.alert("成功加载");	
					swin = new Ext.Window( {
						title : '<div align="center">添加word文档</div>',
						closable : true,
						width : 800,
						height : 600,
						tbar : [ {
							iconCls:'dealadd',
							text : '保存到服务器',
							handler : function() {
							alert("确定提交？...");													
							var myMask = new Ext.LoadMask(
									Ext
											.getBody(),
									{
										msg : '正在提交数据请稍候...'
									});
							myMask.show();
							
									Ext.Ajax.request( {
										url : '/resources/tablelist/userdealword',
										method:'POST',
										jsonData:{taid:inputvar,content:KE.html('content1')},
										success : function(response, options) {
											myMask.hide();
											Ext.grid.sefstore.reload();
											Ext.Msg.alert("处理结果",response.responseText);
											//win.close();
										},
										scope:this
									});
						},
						scope : this
						} ],
						border : false,
						plain : true,
						modal : true,
						// maximizable:true,
						maximized : true,
						autoScroll:true,
						html:'<textarea id="content1" name="content" style="width:100%;height:600px;visibility:hidden;">'+response.responseText+'</textarea>'
							});
					swin.show();  											
					KE.init({
						id : 'content1',
						//items : ['hello'],
						//height:'100%',
						afterCreate : function(id) {
						KE.util.focus(id);
						}
					});		
					KE.create('content1');
				},
				scope:this
			});	
		      },
		    	  scope:this
		      }
		      ],
		columns : [ {
			id : 'rowid',
			header : 'ID标识',
			width : 100,
			sortable : true,
			dataIndex : 'rowid'
		},{
			dataIndex : 'rowid',
			renderer : showbt,
			header : '操作'
		} ]
	});
	var win = null;
	// 如果窗口第一次被打开时才创建
	if (!win) {
		win = new Ext.Window( {
			title : '已填写',
			closable : true,
			width : 600,
			height : 400,
			border : false,
			plain : true,
			modal : true,
			maximizable : true,
			layout : 'border',
			items : [grid2]
		// 把上面创建的panel和TabPanel放在window中，并采用border方式布局
				});
	}
	win.show();
	return false;
//	//alert(taid);
//	//return ;
//	var myMask = new Ext.LoadMask(
//			Ext
//					.getBody(),
//			{
//				msg : '正在加载数据请稍候...'
//			});
//	myMask.show();
//	Ext.Ajax.request( {
//		url : '/resources/tablelist/getwordbyuser',
//		method:'POST',
//		jsonData:{taid:taid},
//		success : function(response, options) {
//			myMask.hide();			
//			//Ext.Msg.alert("成功加载");	
//			win = new Ext.Window( {
//				title : '<div align="center">添加word文档</div>',
//				closable : true,
//				width : 800,
//				height : 600,
//				tbar : [ {
//					iconCls:'dealadd',
//					text : '保存到服务器',
//					handler : function() {
//					alert("确定提交？...");													
//					var myMask = new Ext.LoadMask(
//							Ext
//									.getBody(),
//							{
//								msg : '正在提交数据请稍候...'
//							});
//					myMask.show();
//					
//							Ext.Ajax.request( {
//								url : '/resources/tablelist/userdealword',
//								method:'POST',
//								jsonData:{taid:taid,content:KE.html('content1')},
//								success : function(response, options) {
//									myMask.hide();
//									//store.reload();
//									Ext.Msg.alert("处理结果",response.responseText);
//									//win.close();
//								},
//								scope:this
//							});
//				},
//				scope : this
//				} ],
//				border : false,
//				plain : true,
//				modal : true,
//				// maximizable:true,
//				maximized : true,
//				autoScroll:true,
//				html:'<textarea id="content1" name="content" style="width:100%;height:600px;visibility:hidden;">'+response.responseText+'</textarea>'
//					});
//			win.show();  											
//			KE.init({
//				id : 'content1',
//				//items : ['hello'],
//				//height:'100%',
//				afterCreate : function(id) {
//				KE.util.focus(id);
//				}
//			});		
//			KE.create('content1');
//		},
//		scope:this
//	});	
}
function worddelete(rowid,taid)
{
	var myMask = new Ext.LoadMask(
			Ext
					.getBody(),
			{
				msg : '正在提交数据请稍候...'
			});
	myMask.show();
	Ext.Ajax.request( {
		url : '/resources/tablelist/deletewordrow',
		method:'POST',
		jsonData:{taid:taid,rowid:rowid},
		success : function(response, options) {
			myMask.hide();
			Ext.grid.sefstore.reload();
			Ext.Msg.alert('处理结果',response.responseText);
			//win.close();
		},
		scope:this
	});
}