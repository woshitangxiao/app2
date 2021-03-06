<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.Push"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	Push push = (Push) request.getAttribute("push");
	Object user = session.getAttribute("user");
	if (user == null) {
		response.getWriter().println("<script>window.top.location.href='" + basePath + "';</script>");
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=yes" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>推送详情添加</title>


<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/laydate.js"></script>


<script type="text/javascript">
!function(){
	laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
	laydate({elem: '#demo'});//绑定元素
}();
</script>


<script type="text/javascript">
	function checkForm() {
		if (document.getElementById("push.pushTitle").value == "") {
			window.alert('请输入标题!');
			return false;
		}
		if (document.getElementById("push.pushEditor").value == "") {
			window.alert('请输入编辑者!');
			return false;
		}
		if (document.getElementById("push.pushKeyWord").value == "") {
			window.alert('请输入关键字!');
			return false;
		}
		if (document.getElementById("push.pushUrl").value =="") {
			window.alert('请输入URl!');
			return false;
		}
		if (document.getElementById("push.pushStatus").value == "") {
			window.alert('请选择推送状态!');
			return false;
		}
		if (document.getElementById("push.pushContext").value == "") {
			window.alert('请输入推送内容!');
			return false;
		}
		return true;
	}
</script>
</head>

<body>
	<form action="<%=basePath%>push/push_doAdd.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
			<li1><a href="<%=basePath%>mainindex.jsp">首页</a></li1>
				<li1><a href="<%=basePath%>push.jsp">推送查找</a></li1>
				<li1><a href="<%=basePath%>push/push_doFind.action">推送列表</a></li1>
				<li1><a style="color:blue;"  href="<%=basePath%>pushadd.jsp">添加推送</a></li1>
				<li1><a onClick="history.back(-1)">返回</a></li1>
			</ul>
		</div>
		<div class="formbody">
			<div class="formtitle">
				<span>推送内容</span>
			</div>
			<ul class="forminfo">
				<li><label>添加标题</label> <input name="push.pushTitle" id="push.pushTitle" type="text" value="" class="dfinput" /> <i></i></li>
				<li><label>编 辑 者</label> <input name="push.pushEditor" id="push.pushEditor" type="text" value="" class="dfinput" /> <i></i></li>
				<li><label>关 键 字</label> <input name="push.pushKeyWord" id="push.pushKeyWord" type="text" value="" class="dfinput" /> <i></i></li>
				<li><label>添加时间</label> <input name="push.pushDate" id="push.pushDate" type="text" value="" class="laydate-icon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" /> <i></i></li>
				<li><label>推送 URL</label> <input name="push.pushUrl" id="push.pushUrl" type="text" value="" class="dfinput" /> <i></i></li>
				<li><label>推送状态</label> <cite> <input name="push.PushStatus" id="push.PushStatus" type="radio" value="wait" /> 待推送&nbsp;&nbsp;&nbsp;&nbsp; <input name="push.PushStatus" id="push.PushStatus" type="radio" value="send" /> 已推送
				</cite></li>
				<li><label>推送内容</label> <textarea name="push.PushContext" id="push.PushContext" class="textinput"></textarea></li>

				<li><label>&nbsp;</label> <input type="submit" name="button" class="btn" value="确认" /> <input type="button" name="button" class="btn" value="取消" onClick="javascript:location.href='<%=basePath %>push/push_doFind.action'" /></li>
			</ul>
		</div>
	</form>
</body>
</html>
