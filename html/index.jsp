<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
<%
	if (request.getSession().getAttribute("username") == null)
		response.sendRedirect("login.jsp");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css"
			href="extjs/resources/css/ext-all.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css"
			href="extjs/ext/css/onlineext.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="/css/grid-examples.css" />
		<!-- Common Styles for the examples -->
		<link rel="stylesheet" type="text/css" href="/css/RowEditor.css" />
		<link rel="stylesheet" type="text/css" href="/css/fileuploadfield.css" />
		<script type="text/javascript"><%out.print("var pruser='"
					+ request.getSession().getAttribute("name") + "';");%></script>
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all-debug.js"></script>
		<script type="text/javascript"
			src="js/olTreePanel/olmenu/olTreeRightMenu.js"></script>
		<script type="text/javascript" src="js/olTreePanel/olTreePanel.js"></script>
		<script type="text/javascript"
			src="js/olTreePanel/olmenu/olTableMenu.js"></script>
		<script type="text/javascript" src="js/olTreePanel/olTablePanel.js"></script>
		<script type="text/javascript" src="js/gen-names.js"></script>
		<script type="text/javascript" src="js/RowEditor.js"></script>
		<script type="text/javascript" src="js/LeftMenu.js"></script>
		<script type="text/javascript" src="js/CompanyPanel.js"></script>
		<script type="text/javascript" src="js/adminPanel.js"></script>
		<script type="text/javascript" src="js/MainPanel.js"></script>
		<script type="text/javascript" src="js/FileUploadField.js"></script>
		<script type="text/javascript" src="js/addac.js"></script>
		<script type="text/javascript" src="js/Main.js"></script>
	<script type="text/javascript" src="kindeditor-min.js"></script>
		<title>欢迎使用在线填表系统</title>
		<Script Language="JavaScript"></Script>
	</head>
	<body>
		<div id='deskTree'></div>
		<div id='mainTree'></div>
		<div id='sysTree'></div>
		<div id='my-tree'></div>
	</body>
</html>