<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link  href="/resource/bootstrap-4.3.1/css/bootstrap.css"  rel="stylesheet">
<script type="text/javascript" src="/resource/js/jquery-3.2.1/jquery.js" ></script>
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/localization/messages_zh.js"></script>

	<link rel="stylesheet" href="/resource/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="/resource/kindeditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="/resource/kindeditor/plugins/code/prettify.js"></script>
	<script charset="utf-8" src="/resource/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="/resource/kindeditor/lang/zh-CN.js"></script>
</head>
<body>
我的收藏<br> 
<form id="form">
	标题:<input type="text" name="text"><br>
	地址:<input type="text" name="url"><br>
	<input type="hidden" name="user_id" value="${loing_session_key.id }">
	<input type="button" value="添加" onclick="add(${loing_session_key.id })">
</form>


<c:forEach items="${list }" var="l">
	${l.text }<br>
	时间:${l.created } <a onclick="delDomain(${l.id },${l.user_id })">删除</a><br>
</c:forEach>



<script type="text/javascript">
	function delDomain(id,userid){
		$.post("delDomain",{id:id},function(result){
			if(result){
				alert("删除成功")
				$(".mymenuselected li").removeClass("menuselected");
				$("#comment").parent().addClass("menuselected")
				$("#workcontent").load("/user/comments?id="+userid);
				
			}else{
				alert("删除失败")
			}
		},"json")
	}

	function add(userid){
		var data = $("#form").serialize();
		alert(data)
		$.post("addDomain",data,function(result){
			if(result==1){
				alert("添加成功地址合法")
				$(".mymenuselected li").removeClass("menuselected");
				$("#comment").parent().addClass("menuselected")
				$("#workcontent").load("/user/comments?id="+userid);
			}else if(result==2){
				alert("地址格式不合法")
			}
			
		},"json")
	}
</script>
</body>
</html>