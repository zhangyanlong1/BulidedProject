<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib  prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
     <%@  taglib  prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cms官网</title>
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


<style type="text/css">
	.menuselected {
		background:blue;
	}
	.mymenuselected li:hover {
		background:blue;
	}
</style>
</head>
<body>
<!-- 头开始 -->
<div class="pos-f-t">
  <div class="collapse" id="navbarToggleExternalContent">
    <div class="bg-dark p-4">
      <h5 class="text-white h4">Cms  官网</h5>
      <span class="text-muted">
					一个好的官网
      </span>
     	</div>
  </div>
  <nav class="navbar navbar-dark bg-dark">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="btn text-white">cms官网</span>
    </button>
      <a href="/index/index" class="text-white"  style="margin-left: -50%">首页</a>
     <div >
    
    		<ul class="nav">
    		<li class="nav-item nav-link"><input  type="text"></li>
    		<li class="nav-item nav-link"> <img width="35px" height="35px" src="/resource/images/guest.jpg"></li>
    		<li class="nav-item nav-link text-white">
				<div class="dropdown mr-1">
					    <button type="button" class="btn btn-secondary dropdown-toggle" id="dropdownMenuOffset" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" data-offset="10,20">
					     	${loing_session_key.username }
					    </button>
					    <div class="dropdown-menu" aria-labelledby="dropdownMenuOffset">
					      <a class="dropdown-item" href="/user/personCenter">个人中心</a>
					      <a class="dropdown-item" href="#">个人设置</a>
					      <a class="dropdown-item" href="/user/outUser">退出登录</a>
					    </div>
					  </div>
			</li>
    		</ul>
     </div>
  </nav>
  <!--  头结束-->
    <div style="width: 80%;height:  800px;position: relative;margin: 100px auto;border:solid 1px;"  >
	   			  <ul class="nav  mymenuselected" >
					  <li class="nav-item">
					    <a class="nav-link active"  id="articles"  href="#"    onclick="showWork($(this),'/admin/articles')">文章管理</a>
					  </li>
					  <li class="nav-item">
					        <a class="nav-link"  id="putupArticle" href="#"    onclick="showWork($(this),'/admin/comments')">论坛管理</a>
					  </li>
					  <li class="nav-item">
					    <a class="nav-link"   id="comment"   href="#"    >个人设置</a>
					  </li>
					  <li class="nav-item">
					    <a class="nav-link"   id="userSetting"   href="#"      >个人设置</a>
					  </li>
					</ul>
					<div class="col-md-10 "  id="workcontent"    style="height: 400px;position: relative;margin: auto;margin-top: 30px">
						
					</div>	
		</div>
    <!--  未尾开始-->
  <nav class="nav fixed-bottom justify-content-center  text-white"  style="background:black" height="50px"> 
	       这是一个官方网站
</nav>
<script type="text/javascript">
	

	 KindEditor.ready(function(K) {
			window.editor1 = K.create('#contentId', {
				cssPath : '/resource/kindeditor/plugins/code/prettify.css',
				//uploadJson : '/resource/kindeditor/jsp/upload_json.jsp',
				uploadJson:'/file/upload'// );
			   })
			prettyPrint();
		});
		
	$(function(){
		$("#articles").parent().addClass("menuselected");
		$("#workcontent").load("/admin/articles");
	})
	
	function showWork(obj,url){
		$(".mymenuselected li").removeClass("menuselected");
		obj.parent().addClass("menuselected")		
		$("#workcontent").load(url);
	}
	
</script>
</body>
</html>