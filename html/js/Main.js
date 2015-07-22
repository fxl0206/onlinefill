Ext
		.onReady(function() {

			Ext.BLANK_IMAGE_URL = 'pic/s.gif';
			Ext.QuickTips.init();
			Ext.lib.Ajax.defaultPostHeader += ";charset=utf-8";
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			var mainTab = new Morik.Office.MainingPanel(
					{
						style : 'padding:0 6px 0 0',
						autoScroll : true,
						region : 'center',
						// deferredRender : false,
						activeTab : 0,
						resizeTabs : true,
						inTabWidth : 100,
						tabWidth : 90,
						enableTabScroll : true,
						items : [ {
							title : '我的首页',
							html : '<div style="background:url(pic/main.gif) no-repeat center middle;width:508px; height:508px;position:relative"></div>'
						} ]
					});

			// 1、创建head部分
			var head = new Ext.Panel(
					{
						region : 'north',
						border : false,
						html : '<TABLE cellSpacing=0 cellPadding=0 width="100%" background="images/header_bg.jpg" border=0> '
								+ '<TR height=56> <TD width=260><IMG height=56 src="images/header_left.jpg"  width=260></TD> '
								+ '<TD style="FONT-WEIGHT: bold; COLOR: #fff; PADDING-TOP: 20px;font-size:14px"  align=middle>'
								+ pruser
								+ '&nbsp;&nbsp;你好！&nbsp;&nbsp; '
								+ '<a style="COLOR:#fff;font-size:14px;" href="#" onclick="eval(\'myte()\');return false;"  id="modpwd">修改密码</a> &nbsp;&nbsp;'
								+ '<A style="COLOR:#fff;font-size:14px" href="servlet/login" >退出系统</A></TD>'
								+ '<TD align=right width=268><IMG height=56  src="images/header_right.jpg" width=268></TD></TR></TABLE>'
								+ '<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0><TR bgColor=#1c5db6 height=4><TD></TD></TR></TABLE>'
					});

			var t3root = new Ext.tree.TreeNode( {
				text : '填表操作',
				expanded : true
			})
			t3root.appendChild(new Ext.tree.TreeNode( {
				text : '浏览表格'
			}));
//			t3root.appendChild(new Ext.tree.TreeNode( {
//				text : '已处理'
//			}));
			var t1 = new Ext.tree.olTreePanel();
			var t2 = new Ext.tree.olTablePanel();
			var t3 = new Ext.tree.TreePanel( {
				 border:false,
				rootVisible : false,
				root : t3root
			});
			var leftmenu = new Morik.Office.LeftMenu( {
				title : '操作菜单栏',
				trees : [ t2, t1, t3 ]
			});

			leftmenu.on("nodeClick", function(nodeAttr) {
				mainTab.test(nodeAttr);
			});
			leftmenu.on("addtask", function(nodeAttr) {
				mainTab.test1(nodeAttr);
			});
			leftmenu.on("mytask", function(nodeAttr) {
				mainTab.myfill(nodeAttr);
				// alert("mytask");
				});
			var viewport = new Ext.Viewport( {
				layout : 'border',
				style : 'border:#024459 2px solid;',
				items : [ head, leftmenu, mainTab ]
			});
			//Ext.getDom("modpwd").attachEvent("onclick",pwdmod); 
		});
