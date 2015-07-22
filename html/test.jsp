<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="/css/RowEditor.css" />
		<link rel="stylesheet" type="text/css"
			href="extjs/resources/css/ext-all.css" rel="stylesheet" />
		<script type="text/javascript" src="kindeditor-min.js"></script>
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all-debug.js"></script>
		<script type="text/javascript" src="js/RowEditor.js"></script>
		<title>欢迎使用在线填表系统</title>
	</head>
	<body>
		<script type="text/javascript">
Ext.onReady(function(){
			win = new Ext.Window( {
				title : '<div align="center">此处你可以添加或修改表格内容</div>',
				closable : true,
				width : 800,
				height : 600,
				bbar : [ {
					iconCls:'dealadd',
					text : '保存到服务器',
					handler : function() {
					//alert(KE.html('content1'));
							Ext.Ajax.request( {
								url : 'http://localhost/test.jsp',
								success : function(response, options) {
									alert("xxxx");
								},
								scope : this
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
				html:'<textarea id="content1" name="content" style="width:100%;height:500px;visibility:hidden;"></textarea>'
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
});
</script>
	</body>
</html>