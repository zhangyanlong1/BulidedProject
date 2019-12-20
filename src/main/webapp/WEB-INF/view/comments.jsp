<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:forEach items="${commentPage.list}" var="comment">
	<div class="row" style="margin-top:50px">
		${comment.content}
		<br>
		${comment.userName} 发表于 <fmt:formatDate value="${comment.created}" pattern="yyyy-MM-dd" />
		</div> 
</c:forEach>  

<div class="row"> 
				<div class="collapse" id="collapseExample1">
			  <div class="card card-body">
			  	<ul class="nav">
			    	<c:forEach begin="1" end="${commentPage.pages}" varStatus="i">
			    		
			    		<c:if test="${i.index<(commentPage.pageNum-1)}">
			    			<li class="nav-item">
					   		<input type="button"  class="btn btn-primary"  href="#" onclick="gopage(${i.index})"  value="${i.index}">
					   		</li>
					   	</c:if>
					   	
					</c:forEach>
				</ul>
			  </div>
			</div>
			<nav aria-label="Page navigation example"  >
			  <ul class="pagination justify-content-center">
			    <li class="page-item">
			      <a class="page-link" href="#" aria-label="Previous"  onclick="gopage(${commentPage.prePage==0?1:commentPage.prePage})">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    	<c:if test="${commentPage.lastPage>3&&(commentPage.pageNum-2)>0 }">
			    		<li class="page-item">
			    			<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample1" role="button" aria-expanded="false" aria-controls="collapseExample1"  >...</a>
			    		</li>
			    	</c:if>
			     	<c:if test="${commentPage.pageNum!=commentPage.firstPage }"><li class="page-item"><a class="page-link" href="#"  onclick="gopage(${commentPage.pageNum-1})">${commentPage.pageNum-1 }</a></li></c:if>
			   	 	<li class="page-item"><a class="page-link" href="#"  style="background-color:  yellow" onclick="gopage(${commentPage.pageNum})">${commentPage.pageNum }</a></li>
			  		<c:if test="${commentPage.pageNum!=commentPage.lastPage }"><li class="page-item"><a class="page-link" href="#"  onclick="gopage(${commentPage.pageNum+1})">${commentPage.pageNum+1 }</a></li></c:if>
			  		<c:if test="${commentPage.lastPage>3&&(commentPage.lastPage-commentPage.pageNum)>2 }">
			  			<li class="page-item">
			  				<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample2" role="button" aria-expanded="false" aria-controls="collapseExample2"  >...</a>
			  			</li>
			  		</c:if>
			    <li class="page-item">
			      <a class="page-link" href="#" aria-label="Next"  onclick="gopage(${commentPage.nextPage==0?commentPage.lastPage:commentPage.nextPage})">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
			</nav>
			
			<div class="collapse" id="collapseExample2">
			  <div class="card card-body">
			 	 <ul class="nav">
			    	<c:forEach begin="1" end="${commentPage.pages}" varStatus="i">
			    		
			    		<c:if test="${i.index>(commentPage.pageNum+1)}">
			    			<li class="nav-item">
					   			<input type="button"  class="btn btn-primary"   href="#" onclick="gopage(${i.index})"  value="${i.index}">
						  	</li>					   
						</c:if>
					 
					</c:forEach>
					</ul>
			  </div>
			</div>
</div>
</body>
</html>