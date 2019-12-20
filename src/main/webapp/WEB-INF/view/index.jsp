<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cms</title>
<script type="text/javascript" src="/resource/js/jquery-3.2.1/jquery.js" ></script>
<link href="/resource/bootstrap-4.3.1/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/localization/messages_zh.js"></script>
<style type="text/css">

 body {
   font-family: PingFang SC,Hiragino Sans GB,Microsoft YaHei,WenQuanYi Micro Hei,Helvetica Neue,Arial,sans-serif;
  font-size: 22px;
 }

.menu {
	display: block;
	width: 110px;
	height: 40px;
	line-height: 40px;
	text-align: center;
	color: #444;
	border-radius: 4px;
	margin-bottom: 2px;
	transition-property: color,background-color;
}

.menu:hover {
    animation-name: hvr-back-pulse;
    animation-duration: .2s;
    animation-timing-function: linear;
    animation-iteration-count: 1;
    background-color: 
	#ed4040;
	color:
	    #fff;
}

.ex {
		overflow: hidden;
		text-overflow:ellipsis;
		white-space: nowrap;
	}

</style>

</head>
<body>
<input type="text"  name="channelId">
 <!-- 导航条 -->
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
     <div class="align-center">
    		<ul class="nav">
    		<li class="nav-item nav-link"><input  type="text"></li>
    		<li class="nav-item nav-link"> <img width="35px" height="35px" src="/resource/images/guest.jpg"> </li>
    		<li class="nav-item nav-link text-white">
					  <c:if test="${loing_session_key.username==null }">
					<a class="text-white" href="/user/login">登录</a>
				</c:if>
    			<c:if test="${loing_session_key.username!=null }">
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
    			</c:if>
    		</li>
    		</ul>
     </div>
  </nav>
<div class="container-fluid" style="margin-top:20px">
	<div class="row">
		<!-- 左侧栏目 -->
		<div class="col-md-2">
			<div>沦落人</div>
			<div>
				<ul class="nav flex-column" >
					<c:forEach items="${channels}" var="channel">
						<li  class="nav-item ">
						  <a class="nav-link menu" href="/index/channel?channelId=${channel.id}"> ${channel.name} </a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		
		<!-- 中间内容列表 -->
		<div class="col-md-6">
			<!-- 轮播图 -->
			<div>
				<div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
					  <ol class="carousel-indicators">
					  	<c:forEach items="${slides}" var="slide" varStatus="index">
					    	<li data-target="#carouselExampleCaptions" data-slide-to="${index.index}" class='${index.index==0?"active":""}'></li>
					  	</c:forEach>
					  </ol>
					  
					  <div class="carousel-inner">
					    <c:forEach items="${slides}" var="slide" varStatus="index">
					    <div class="carousel-item ${index.index==0?'active':''}">
					      <img src="/pic/${slide.picture}" class="d-block w-100" alt="${slide.title}"  width="615px"  height="400px">
					      <div class="carousel-caption d-none d-md-block">
					        <h5>${slide.title} </h5>
					        <p>${slide.title}</p>
					      </div>
					    </div>
					    </c:forEach>
					  </div>
					  
					  <a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">
					    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
					    <span class="sr-only">Previous</span>
					  </a>
					  <a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">
					    <span class="carousel-control-next-icon" aria-hidden="true"></span>
					    <span class="sr-only">Next</span>
					  </a>
					</div>
			</div>
			<!-- 文章的列表 -->
			<div style="margin-top:20px">
				<c:forEach items="${articlePage.list}" var="article">
					<div class="row" style="margin-top:5px">
						<div class="col-md-3">
							<img src="/pic/${article.picture}"  width="150px" height="150px" 
							  onerror="this.src='/resource/images/guest.jpg'"
							  class="rounded" style="border-radius:12px!important;"
							 >
						</div>
						<div class="col-md-9">
							<a href="/article/detail?id=${article.id}&hotId=1" target="_blank">${article.title}</a>
							<br>
							作者：${article.user.username}
							<br>
							栏目：<a> ${article.channel.name} </a>&nbsp;&nbsp;&nbsp;&nbsp; 分类：<a>${article.category.name}</a>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<!-- 右侧 -->
		<div class="col-md-4">
				
				<div class="card">
					  <div class="card-header">
					    最新文章
					  </div>
					  <div class="card-body">
					     <ol>
					     	<c:forEach items="${lastArticles}" var="article" varStatus="index">
					     		<li class="ex"> ${index.index+1}. <a href="/article/detail?id=${article.id}" target="_blank" >${article.title}</a></li>
					     	</c:forEach>
					     	
					     </ol>
					  </div>
				</div>	
				
					
			  <div class="card" style="margin-top:50px">
					  <div class="card-header">
					    公告
					  </div>
					  <div class="card-body">
					     <ul>
					     	<li>1</li>
					     	<li>2</li>
					     	<li>3</li>
					     </ul>
					  </div>
				</div>			
		</div>
	</div>
</div>


</body>
</html>