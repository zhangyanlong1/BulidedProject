<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>沦落人-${article.title}</title>
<script type="text/javascript" src="/resource/js/jquery-3.2.1/jquery.js" ></script>
<link href="/resource/bootstrap-4.3.1/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/localization/messages_zh.js"></script>
</head>
<body>
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
	<div class="container">
		<div class="row justify-content-center" >
			<h3>${article.title}</h3>
		</div>
		<div class="row justify-content-center">
			<h5>
			作者：${article.user.username} &nbsp;&nbsp;&nbsp;
			栏目：${article.channel.name}  &nbsp;&nbsp;&nbsp;
			分类：${article.category.name}&nbsp;&nbsp;&nbsp;
			发表时间：<fmt:formatDate value="${article.created}" pattern="yyyy-MM-dd"/> 
			</h5>
			
		</div>
		<div style="margin-top:30px">
			${article.content}
		</div>
		<div>
			<nav aria-label="...">
					  <ul class="pagination">
					    <li class="page-item ">
					      <a class="page-link" href="#" tabindex="-1" >上一篇</a>
					    </li>
					    <li class="page-item">
					      <a class="page-link" href="#">下一篇</a>
					    </li>
					  </ul>
					</nav>
		</div>
		<div>
			<!-- 发布评论 -->
			<textarea rows="5" cols="160" id="commentText"></textarea>
			<input type="button" class="btn btn-primary" onclick="addComment()" value="发表评论">
		</div>
		<div id="comment">
			
		</div>
	</div>
<script type="text/javascript">
		//前往评论分页
		function gopage(page){
			showComment(page)
		}
		//将comments页面加载到本页
		function showComment(page){
			$("#comment").load("/article/comments?id=${article.id}&page="+page)
		}
		//预加载第一条评论
		$(document).ready(function(){
			// 显示第一页的评论
			showComment(1)
		})
		
		//实现发布评论
		function addComment(){
			
	
				$.post("/article/postcomment",
						{articleId:'${article.id}',content:$("#commentText").val()},
					function(msg){
						if(msg.code==1){
							alert('发布成功')
							$("#commentText").empty()
						}else{
							alert(msg.error)
						}
					},
					"json") 
			}
			 
		
	</script>

</body>
</html>