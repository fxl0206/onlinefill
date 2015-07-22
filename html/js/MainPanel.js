Ext.ns("Morik", "Morik.Office", "Morik.Util", "Morik.Office.Department");
Morik.Office.MainingPanel = Ext
		.extend(
				Ext.TabPanel,
				{
					initComponent : function() {
						// 一些初始化工作
						Morik.Office.MainingPanel.superclass.initComponent
								.call(this);
						this._cache = {};

					},
					myfill : function(node) {
						var n = this.getComponent(node.id);
						// alert(n);
						if (n) {
							this.setActiveTab(n);
						} else {

							n = this.getmyfill(node);
							this.add(n);
							this.setActiveTab(n);
						}
					},
					test : function(node) {
						var n = this.getComponent(node.id);
						// alert(n);
						if (n) {
							this.setActiveTab(n);
						} else {

							n = this.getMy(node);
							this.add(n);
							this.setActiveTab(n);
						}
					},
					test1 : function(node) {
						var n = this.getComponent(node.id);
						// alert(n);
						if (n) {
							this.setActiveTab(n);
						} else {

							n = this.fexec(node);
							this.add(n);
							this.setActiveTab(n);
						}
					},
					fexec : function(node) {
						function change(val) {
							if (val > 0) {
								return '<span style="color:green;">' + val + '</span>';
							} else if (val < 0) {
								return '<span style="color:red;">' + val + '</span>';
							}
							return val;
						}
						function pctChange(val) {
							if (val > 0) {
								return '<span style="color:green;">' + val + '%</span>';
							} else if (val < 0) {
								return '<span style="color:red;">' + val + '%</span>';
							}
							return val;
						}
						function showbt(val) {
							// return val;
							var tmp=val.split('#');
							val=tmp[0];
							var fcname='actable';
							var tget='';
							if(tmp[1]=="word")
							{
								fcname='wordshow';
								tget='target="_blank"';
							}
							return '<img src="extjs/ext/images/settings.gif" style="cursor:hand" onclick="return acshow(\''
									+ val
									+ '\')">&nbsp&nbsp&nbsp&nbsp'
									+ '<img src="extjs/ext/images/table_open.gif" style="cursor:hand" onclick="return '
									+fcname+'(\''
									+ val
									+ '\')">&nbsp&nbsp&nbsp&nbsp'
									+ '<a href="servlet/DownloadServlet?tabid='
									+ val
									+ '" '+tget+'><img src="extjs/ext/images/tabledown.gif"></a>';
						}
						// create the data store
						var Employee = new Ext.data.Record.create( [ {
							name : 'taskid',
							type : 'string'
						}, {
							name : "taskname",
							type : "string"
						}, {
							name : "startdate",
							type : "string"
						}, {
							name : "enddate",
							type : "string"
						}, {
							name : "adddate",
							type : "string"
						}, {
							name : "contentcount",
							type : "string"
						} ]);
						var store = new Ext.data.JsonStore(
								{
									autoLoad : true,
									url : 'resources/tablelist/findbydirid?dirid=' + node.id,
									root : 'tasks',
									fields : [ {
										name : 'taskid',
										type : 'string'
									}, {
										name : "taskname",
										type : "string"
									}, {
										name : "startdate",
										type : "string"
									}, {
										name : "enddate",
										type : "string"
									}, {
										name : "adddate",
										type : "string"
									}, {
										name : "contentcount",
										type : "string"
									} ]
								});
						var editor = new Ext.ux.grid.RowEditor( {
							url : "resources/tablelist/edit",
							saveText : '修改',
							cancelText : '取消'
						});
						// create the Grid
						var grid = new Ext.grid.GridPanel(
								{
									id : node.id,
									store : store,
									plugins : [ editor ],

									tbar : [
											{
												iconCls : 'tableadd',
												text : '添加Excel表格',
												handler : function() {
													form = new Ext.FormPanel(
															{
																labelWidth : 75,
																frame : true,
																fileUpload : true,
																bodyStyle : 'padding:5px 5px 0',
																defaults : {
																	width : 230
																},
																autoHeight : true,
																autoWidth : true,
																defaultType : 'textfield',
																sm : new Ext.grid.RowSelectionModel(
																		{
																			singleSelect : true
																		}),
																items : [
																		{
																			xtype : 'hidden',
																			name : 'dirid',
																			value : node.id
																		},
																		{
																			fieldLabel : '表格名称',
																			name : 'taname',
																			allowBlank : false,
																			blankText : '默认为上传表格名称'
																		},
																		{
																			xtype : 'fileuploadfield',
																			name : 'tafile',
																			emptyText : '请选择要上传的excel文件',
																			buttonText : '选择文件',
																			fieldLabel : '选择文件'
																		},
																		{
																			xtype : 'datefield',
																			fieldLabel : '起始日期',
																			showTaday : true,
																			format : 'Y-m-d',
																			name : 'tastdate'
																		},
																		{
																			xtype : 'datefield',
																			fieldLabel : '截止日期',
																			format : 'Y-m-d',
																			name : 'taendate'
																		}

																],

																buttons : [
																		{
																			text : '提交',
																			handler : function() {
																				if (form
																						.getForm()
																						.isValid()) {
																					var values = form
																							.getForm()
																							.getValues();
																					form
																							.getForm()
																							.submit(
																									{
																										url : 'resources/tablelist/add',
																										method : 'POST',
																										waitMsg : '正在上传，请稍候...',
																										success : function(
																												fp,
																												o) {
																											o.result.newtask.taskname = values['taname'];
																											var e = new Employee(
																													o.result.newtask);
																											editor
																													.stopEditing();
																											store
																													.insert(
																															0,
																															e);
																											grid
																													.getView()
																													.refresh();
																											grid
																													.getSelectionModel()
																													.selectRow(
																															0);
																											Ext.Msg
																													.alert(
																															'操作提示',
																															'<font color=green>上传成功！</font>');
																											// editor.startEditing(0);
																											// Ext.aaaaaaaaaaaaaaaaaa=o;
																											// alert(o.result.newtask);
																											this.win
																													.close();
																										},
																										// scope
																										// :
																										// this,
																										failure : function(
																												fp,
																												o) {
																											Ext.Msg
																													.alert(
																															'操作失败',
																															'<font color=red>上传失败！</font>');
																										},
																										scope : this
																									});
																				}

																			},
																			scope : this
																		},
																		{
																			text : '取消',
																			handler : function() {
																				this.win
																						.close();
																			},
																			scope:this
																		} ]
															});
													this.win = new Ext.Window( {
														width : 350,
														title : '添加表格',
														items : [ form ]
													});
													this.win.show();
												},
												scope : this
											},
											{
												iconCls : 'tableadd',
												text:'添加word表格',
												handler:function(){
												
												win = new Ext.Window( {
													title : '<div align="center">添加word文档</div>',
													closable : true,
													width : 800,
													height : 600,
													bbar : [ {
														iconCls:'dealadd',
														text : '保存到服务器',
														handler : function() {
														alert("确定提交？...");
														try{
															tname=document.getElementById("tn");
															tabdirid=document.getElementById("tabdirid");	
															}
															catch(e)
															{
																//alert(e);
															}
														var xtname=tname.value;
														var xtabdirid=tabdirid.value;														
														var myMask = new Ext.LoadMask(
																Ext
																		.getBody(),
																{
																	msg : '正在提交数据请稍候...'
																});
														myMask.show();
														
																Ext.Ajax.request( {
																	url : '/resources/tablelist/addword',
																	method:'POST',
																	jsonData:{content:KE.html('content1'),tn:xtname,dirid:xtabdirid},
																	success : function(response, options) {
																		myMask.hide();
																		store.reload();
																		Ext.Msg.alert("处理结果","添加成功");
																		win.close();
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
													html:'表格名称:<input type="text" id="tn" name="tn" ><input type="hidden" id="tabdirid" name="tabdirid" value="'+node.id+'"><br><textarea id="content1" name="content" style="width:100%;height:500px;visibility:hidden;"></textarea>'
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
											},
											{
												ref : '../removeBtn',
												iconCls : 'tabledelete',
												text : '删除表格',
												disabled : true,
												handler : function() {
													editor.stopEditing();
													var option = {
														title : '删除确认',
														msg : '你真的要删除？',
														modal : true,
														buttons : Ext.Msg.OKCANCEL,
														icon : Ext.Msg.INFO,
														fn : function(b, t) {
															if (b == 'ok') {
																var s = grid
																		.getSelectionModel()
																		.getSelections();
																var myMask = new Ext.LoadMask(
																		Ext
																				.getBody(),
																		{
																			msg : '正在处理请稍候...'
																		});
																myMask.show();
																var fail = function() {
																	myMask
																			.hide();
																	Ext.Msg
																			.alert(
																					'操作失败',
																					'<font color=green>资源服务器无法访问！</font>');
																};
																for ( var i = 0, r; r = s[i]; i++) {
																	Ext.data.r = r;
																	Ext.Ajax
																			.request( {
																				url : "resources/tablelist/delete?taid="
																						+ r.data.taskid,
																				method : 'GET',
																				failure : fail,
																				success : function(
																						response,
																						options) {
																					var js = Ext.util.JSON
																							.decode(response.responseText);
																					if (js.success) {
																						myMask
																								.hide();
																						store
																								.remove(Ext.data.r);
																						Ext.Msg
																								.alert(
																										'操作提示',
																										'<font color=green>' + js.msg + '</font>');
																					} else {
																						myMask
																								.hide();
																						Ext.Msg
																								.alert(
																										'操作提示',
																										'<font color=red>' + js.msg + '</font>');
																					}
																				},
																				scope : this
																			});
																	break;
																}
															}
														},
														scope : this
													};
													Ext.Msg.show(option);
												},
												scope : this
											} ],
									columns : [ {
										//id : 'task2id',
										header : 'ID标识',
										width : 150,
										hidden:true,
										dataIndex : 'taskid'
									}, {
										id : 'taskname',
										header : '表格名称',
										width : 100,
										sortable : true,
										dataIndex : 'taskname',
										editor : {
											xtype : 'textfield',
											allowBlank : false
										}
									}, {
										header : '起始时间',
										width : 100,
										sortable : true,
										dataIndex : 'startdate',
										editor : {
											xtype : 'textfield',
											allowBlank : false
										}
									}, {
										header : '终止时间',
										width : 100,
										sortable : true,
										dataIndex : 'enddate',
										editor : {
											xtype : 'textfield',
											allowBlank : false
										}
									}, {
										header : '发布时间',
										width : 100,
										sortable : true,
										dataIndex : 'adddate'

									}, {
										header : '已填人数',
										width : 100,
										sortable : true,
										dataIndex : 'contentcount'
									// {acount:'2008212',pwd:'20078212',owner:'廖雪峰',email:'cassenl@126.com',tel:'13890658239',credate:'2011-03-21',sta:'true'},
											}, {
												width : 100,
												renderer : showbt,
												dataIndex : 'taskid'
											} ],
									stripeRows : true,
									autoExpandColumn : 'taskname',
									height : 350,
									title : node.text,
									stateful : true,
									closable : true,
									stateId : 'task2id'
								});
						grid.getSelectionModel().on(
								'selectionchange',
								function(sm) {
									grid.removeBtn
											.setDisabled(sm.getCount() < 1);
								});
						return (grid);
					},
					getMy : function(node) {
						function change(val) {
							if (val > 0) {
								return '<span style="color:green;">' + val + '</span>';
							} else if (val < 0) {
								return '<span style="color:red;">' + val + '</span>';
							}
							return val;
						}

						/**
						 * Custom function used for column renderer
						 * 
						 * @param {Object}
						 *            val
						 */
						function pctChange(val) {
							if (val > 0) {
								return '<span style="color:green;">' + val + '%</span>';
							} else if (val < 0) {
								return '<span style="color:red;">' + val + '%</span>';
							}
							return val;
						}

						// create the data store
						var Employee = new Ext.data.Record.create( [ {
							name : "acount",
							type : "string"
						}, {
							name : "pwd",
							type : "string"
						}, {
							name : "owner",
							type : "string"
						}, {
							name : "email",
							type : "string"
						}, {
							name : "tel",
							type : "string"
						}, {
							name : "cdate",
							type : "string"
						}, {
							name : "sta",
							type : "string"
						}, {
							name : "detail",
							type : "string"
						} ]);
						var store = new Ext.data.JsonStore(
								{
									autoLoad : true,
									url : 'resources/acounts/getbyorgid?orgid=' + node.id,
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
						var editor = new Ext.ux.grid.RowEditor( {
							saveText : '修改',
							cancelText : '取消'
						});
						// create the Grid
						var grid = new Ext.grid.GridPanel(
								{
									id : node.id,
									store : store,
									plugins : [ editor ],

									tbar : [
											{
												iconCls : 'acountadd',
												text : '添加账户',
												handler : function() {
													this.form = new Ext.FormPanel(
															{
																labelWidth : 75, // label
																// settings
																// here
																// cascade
																// unless
																// overridden
																url : 'save-form.php',
																frame : true,
																bodyStyle : 'padding:5px 5px 0',
																width : 350,
																defaults : {
																	width : 230
																},
																defaultType : 'textfield',

																items : [
																		{
																			xtype : 'hidden',
																			name : 'acorgid',
																			value : node.id
																		},
																		{
																			fieldLabel : '用户账号',
																			name : 'acname',
																			allowBlank : false,
																			blankText : '不能为空'
																		},
																		{
																			fieldLabel : '用户密码',
																			name : 'acpwd',
																			allowBlank : false,
																			blankText : '不能为空'
																		},
																		{
																			fieldLabel : '持所有人',
																			name : 'acowner',
																			allowBlank : false,
																			blankText : '不能为空'
																		},
																		{
																			fieldLabel : '电子邮箱',
																			name : 'acemail',
																			vtype : 'email'
																		},
																		{
																			fieldLabel : '联系电话',
																			name : 'acphone'
																		},
																		{
																			xtype : 'checkbox',
																			fieldLabel : '启用状态',
																			name : 'acstatu',
																			value : 'true'
																		},
																		{
																			fieldLabel : '账号备注',
																			name : 'acdetail'
																		} ],

																buttons : [
																		{
																			text : '提交',
																			handler : function() {
																				var myMask = new Ext.LoadMask(
																						Ext
																								.getBody(),
																						{

																							msg : "正在处理请稍候..."

																						});
																				if (this.form
																						.getForm()
																						.isValid()) {
																					win
																							.hide();
																					myMask
																							.show();
																					var values = this.form
																							.getForm()
																							.getValues();
																					var json = {
																						acorgid : values['acorgid'],
																						acname : values['acname'],
																						acpwd : values['acpwd'],
																						acowner : values['acowner'],
																						acemail : values['acemail'],
																						acphone : values['acphone'],
																						acdetail : values['acdetail']
																					};
																					var fail = function() {
																						myMask
																								.hide();
																						win
																								.close();
																						Ext.Msg
																								.alert(
																										'添加错误',
																										'<font color=red>提示：资源服务器无法访问！</font>');
																					}
																					Ext.Ajax
																							.request( {
																								url : 'resources/acounts/add',
																								jsonData : json,
																								failure : fail,
																								success : function(
																										response,
																										options) {
																									var json = Ext.util.JSON
																											.decode(response.responseText);
																									if (json.success) {
																										var e = new Employee(
																												{
																													acount : json.acname,
																													pwd : json.acpwd,
																													owner : json.acowner,
																													email : json.acemail,
																													tel : json.acphone,
																													credate : json.credate,
																													sta : 'true',
																													detail : json.acdetail
																												});
																										editor
																												.stopEditing();
																										store
																												.insert(
																														0,
																														e);
																										grid
																												.getView()
																												.refresh();
																										grid
																												.getSelectionModel()
																												.selectRow(
																														0);
																										myMask
																												.hide();
																										win
																												.close();
																										Ext.Msg
																												.alert(
																														'操作提示',
																														'<font color=green>' + json.msg + '</font>');
																									} else {
																										myMask
																												.hide();
																										win
																												.close();
																										Ext.Msg
																												.alert(
																														'添加错误',
																														'<font color=red>提示：' + json.msg + '</font>');

																									}
																									// editor.startEditing(0);
																								},
																								method : 'POST',
																								scope : this
																							});
																					// alert(Ext.util.JSON.encode(json));

																				}
																			},
																			scope : this

																		},
																		{
																			handler : function() {
																				win
																						.close();
																			},
																			text : '取消'
																		} ]
															});
													win = new Ext.Window( {
														width : 350,
														title : '添加账号',
														items : [ this.form ]
													});
													win.show();
													// editor.stopEditing();
													// store.insert(0, e);
													// grid.getView().refresh();
													// grid.getSelectionModel().selectRow(0);
													// editor.startEditing(0);
												}
											},
											{
												ref : '../removeBtn',
												iconCls : 'acountdelete',
												text : '删除账户',
												disabled : true,
												handler : function() {
													editor.stopEditing();
													var option = {
														title : '删除确认',
														msg : '此操作将删除你选择所有账号，你真的要删除？',
														modal : true,
														buttons : Ext.Msg.OKCANCEL,
														icon : Ext.Msg.INFO,
														fn : function(b, t) {
															if (b == 'ok') {
																var myMask = new Ext.LoadMask(
																		Ext
																				.getBody(),
																		{

																			msg : "正在处理请稍候..."

																		});
																myMask.show();
																this.s = grid
																		.getSelectionModel()
																		.getSelections();
																var tsuccess = function(
																		response,
																		options) {
																	myMask
																			.hide();
																	var js = Ext.util.JSON
																			.decode(response.responseText);
																	if (js.success) {
																		for ( var i = 0, r; r = this.s[i]; i++) {
																			grid.store
																					.remove(r);
																		}
																		Ext.Msg
																				.alert(
																						'操作提示',
																						'<font color=green>' + js.msg + '</font>');
																	} else {
																		Ext.Msg
																				.alert(
																						'删除失败',
																						'<font color=red>' + js.msg + '</font>');
																	}
																};
																var tFn = function() {
																	myMask
																			.hide();
																	Ext.Msg
																			.alert(
																					'删除失败',
																					'<font color=red>提示：资源服务器无法访问</font>');
																};
																var acounts = new Array();
																for ( var i = 0, r; r = this.s[i]; i++) {
																	acounts[i] = r.data.acount;
																}
																Ext.Ajax
																		.request( {
																			url : "resources/acounts/delete",
																			jsonData : acounts,
																			method : 'POST',
																			success : tsuccess,
																			failure : tFn,
																			scope : this
																		});
															}
															// this.getOwnerTree();
														},
														scope : this
													};
													Ext.Msg.show(option);
												},
												scope : this
											},
											{
												iconCls : 'acounttemplate',
												text : '下载模板',
												handler : function() {
													// window.open('onlinedb/acounts.xlsx');
												window.location.href = 'onlinedb/acounts.xlsx';
											}

											},
											{
												iconCls : 'acountbat',
												text : '批量添加',
												handler : function() {
													form = new Ext.FormPanel(
															{
																labelWidth : 75,
																frame : true,
																fileUpload : true,
																bodyStyle : 'padding:5px 5px 0',
																defaults : {
																	width : 230
																},
																autoHeight : true,
																autoWidth : true,
																defaultType : 'textfield',

																items : [
																		{
																			xtype : 'hidden',
																			name : 'orgid',
																			value : node.id
																		},
																		{
																			xtype : 'fileuploadfield',
																			name : 'dbfile',
																			emptyText : 'Select an image',
																			buttonText : '选择文件',
																			fieldLabel : '选择文件'
																		} ],

																buttons : [
																		{
																			text : '上传',
																			handler : function() {
																				if (form
																						.getForm()
																						.isValid()) {
																					win
																							.hide();
																					var values = form
																							.getForm()
																							.getValues();
																					form
																							.getForm()
																							.submit(
																									{
																										url : 'resources/acounts/batadd',
																										method : 'POST',
																										waitMsg : '正在上传，请稍候...',
																										success : function(
																												fp,
																												o) {
																											// Ext.aaaaaaaaaaaaaaaaaaaaaa=o.result;
																											if (o.result.msg.length > 0) {
																												var st = '';
																												for ( var i = 0; i < o.result.msg.length; i++) {
																													st += o.result.msg[i];
																												}
																												Ext.Msg
																														.alert(
																																'处理结果',
																																'<br>' + st + '<br><br><font color="red">添加异常（常见问题：该账号已存在或必填资料为空）</font>');
																											} else {
																												Ext.Msg
																														.alert(
																																'处理结果',
																																'<font color=green>批量上传成功！</font>');
																											}
																											grid.store
																													.reload();
																											win
																													.close();
																										},
																										failure : function() {
																											Ext.Msg
																													.alert(
																															'处理结果',
																															'<font color=red>对不起，批量添加失败！</font>');
																											win
																													.close();
																										},
																										scope : this
																									});
																				}

																			},
																			scope : this
																		},
																		{
																			text : '取消',
																			handler : function() {
																				win
																						.close();
																			},
																			scope : this
																		} ]
															});
													win = new Ext.Window( {
														width : 350,
														modal : true,
														title : '批量添加账号',
														items : [ form ]
													});
													win.show();
												},
												scope : this
											} ],
									columns : [ {
										id : 'acount',
										header : '账户名',
										width : 100,
										sortable : true,
										dataIndex : 'acount'
									}, {
										header : '密码',
										width : 100,
										sortable : true,
										dataIndex : 'pwd',
										editor : {
											xtype : 'textfield',
											allowBlank : false
										}
									}, {
										header : '持有者',
										width : 100,
										sortable : true,
										dataIndex : 'owner',
										editor : {
											xtype : 'textfield',
											allowBlank : false
										}
									}, {
										header : '联系邮箱',
										width : 100,
										sortable : true,
										dataIndex : 'email',
										editor : {
											xtype : 'textfield',
											allowBlank : true
										}
									}, {
										header : '电话号码',
										width : 100,
										sortable : true,
										dataIndex : 'tel',
										editor : {
											xtype : 'textfield',
											allowBlank : true
										}
									// {acount:'2008212',pwd:'20078212',owner:'廖雪峰',email:'cassenl@126.com',tel:'13890658239',credate:'2011-03-21',sta:'true'},
											}, {
												header : '创建日期',
												width : 100,
												sortable : true,
												dataIndex : 'credate'

											}, {
												xtype : 'booleancolumn',
												header : '激活状态',
												width : 100,
												sortable : true,
												renderer : pctChange,
												dataIndex : 'sta',
												trueText : 'Yes',
												falseText : 'No',
												editor : {
													xtype : 'checkbox'
												}

											}, {
												id : 'cdetail',
												dataIndex : 'detail',
												header : '备注',
												editor : {
													xtype : 'textfield'
												}
											} ],
									stripeRows : true,
									autoExpandColumn : 'cdetail',
									height : 350,
									title : node.text,
									// config options for stateful behavior
									stateful : true,
									closable : true,
									stateId : 'grid'
								});
						grid.getSelectionModel().on(
								'selectionchange',
								function(sm) {
									grid.removeBtn
											.setDisabled(sm.getCount() < 1);
								});
						return (grid);
					},
					getmyfill : function(node) {
						function change(val) {
							if (val > 0) {
								return '<span style="color:green;">' + val + '</span>';
							} else if (val < 0) {
								return '<span style="color:red;">' + val + '</span>';
							}
							return val;
						}
						function pctChange(val) {
							if (val > 0) {
								return '<span style="color:green;">' + val + '%</span>';
							} else if (val < 0) {
								return '<span style="color:red;">' + val + '%</span>';
							}
							return val;
						}
						function showbt(val) {
							// return val;
							var tmp=val.split('#');
							val=tmp[0];
							var fcname='actable';
							var tget='';
							if(tmp[1]=='word')
							{
								fcname='openwordbyuser';
								tget='target="_blank"';
							}
							return '<img src="extjs/ext/images/table_open.gif" style="cursor:hand" onclick="return '
							+fcname
							+'(\''
									+ val
									+ '\')">&nbsp&nbsp&nbsp&nbsp'
									+ '<a href="servlet/userdownServlet?tabid='
									+ val
									+ '" '+tget+'><img src="extjs/ext/images/tabledown.gif"></a>';
						}
//						function showbt(val) {
//							// return val;
//							var tmp=val.split('#');
//							val=tmp[0];
//							var fcname='actable';
//							var tget='';
//							if(tmp[1]=="word")
//							{
//								fcname='wordshow';
//								tget='target="_blank"';
//							}
//							return '<img src="extjs/ext/images/settings.gif" style="cursor:hand" onclick="return acshow(\''
//									+ val
//									+ '\')">&nbsp&nbsp&nbsp&nbsp'
//									+ '<img src="extjs/ext/images/table_open.gif" style="cursor:hand" onclick="return '
//									+fcname+'(\''
//									+ val
//									+ '\')">&nbsp&nbsp&nbsp&nbsp'
//									+ '<a href="servlet/DownloadServlet?tabid='
//									+ val
//									+ '" '+tget+'><img src="extjs/ext/images/tabledown.gif"></a>';
//						}
						var store = new Ext.data.JsonStore( {
							autoLoad : true,
							url : 'resources/tablelist/findbyacname',
							root : 'tasks',
							fields : [ {
								name : 'taskid',
								type : 'string'
							}, {
								name : "taskname",
								type : "string"
							}, {
								name : "startdate",
								type : "string"
							}, {
								name : "enddate",
								type : "string"
							}, {
								name : "adddate",
								type : "string"
							}, {
								name : "contentcount",
								type : "string"
							} ]
						});
						var grid = new Ext.grid.GridPanel( {
							id : node.id,
							store : store,
							columns : [ {
								id : 'taskname',
								header : '表格名称',
								width : 100,
								sortable : true,
								dataIndex : 'taskname'
							}, {
								header : '起始时间',
								width : 100,
								sortable : true,
								dataIndex : 'startdate'
							}, {
								header : '终止时间',
								width : 100,
								sortable : true,
								dataIndex : 'enddate'
							}, {
								header : '发布时间',
								width : 100,
								sortable : true,
								dataIndex : 'adddate'

							}, {
								header : '已填人数',
								width : 100,
								sortable : true,
								dataIndex : 'contentcount'
							}, {
								width : 100,
								renderer : showbt,
								dataIndex : 'taskid'
							} ],
							stripeRows : true,
							autoExpandColumn : 'taskname',
							height : 350,
							title : node.text,
							stateful : true,
							closable : true,
							stateId : 'task2id'
						});
						grid.getSelectionModel().on(
								'selectionchange',
								function(sm) {
									grid.removeBtn
											.setDisabled(sm.getCount() < 1);
								});
						return (grid);
					},
					findPanel : function(name) {
						var ret = this._cache[name];
						if (!ret) {
							var pn = (this.ns ? this.ns : 'Morik.Office') + "."
									+ name + 'Panel';
							var ret = eval(pn);
						}
						return ret;
					},
					addPanel : function(name, panel) {
						if (!this._cache)
							this._cache = {};
						this._cache[name] = panel;
					}
				});
