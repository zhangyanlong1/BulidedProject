<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib  prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table class="table">
  <thead class="thead-dark">
    <tr>
      <th scope="col">id</th>
      <th scope="col">标题</th>
      <th scope="col">栏目</th>
      <th scope="col">分类</th>
      <th scope="col">发布时间</th>
      <th scope="col">状态</th>
      <th scope="col">操作</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${page.list }"  var="articleList">
    <tr>
      <th scope="row">${articleList.id }</th>
      <td>${articleList.title }</td>
      <td>${articleList.channel.name }</td>
      <td>${articleList.category.name}</td>
      <td><fmt:formatDate value="${articleList.created}" pattern="yyyy年MM月dd日"/></td>
      <td>
			<c:choose>
        					<c:when test="${articleList.status==0}"> 待审核</c:when>
        					<c:when test="${articleList.status==1}"> 审核通过</c:when>
        					<c:when test="${articleList.status==2}"> 审核被拒</c:when>
        					<c:otherwise>
        						未知
        					</c:otherwise>
        	</c:choose>
	  </td>
	  <td>修改 / <input type="button" value="删除"  class="btn btn-danger" onclick="del(${articleList.id})"></td>
    </tr>
    </c:forEach>  
</table>

 <nav aria-label="Page navigation example"  >
  <ul class="pagination justify-content-center">
    <li class="page-item">
      <a class="page-link" href="#" aria-label="Previous"  onclick="gopage(${page.prePage==0?1:page.prePage})">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    <c:forEach begin="1"  end="${page.pages }"  var="p"  varStatus="i">
    <li class="page-item"><a class="page-link" href="#"  onclick="gopage(${i.index})">${i.index }</a></li>
  </c:forEach>
    <li class="page-item">
      <a class="page-link" href="#" aria-label="Next"  onclick="gopage(${page.nextPage==0?page.pages:page.nextPage})">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>

<script type="text/javascript">
/**
* 翻页
*/
function gopage(pageNum){
	$("#workcontent").load("/user/articles?pageNum="+pageNum);
}

/**
 * 删除
 */
	function del(id){
		if(!confirm("您确认删除么？"))
			return;
		
		$.post('/user/deletearticle',{id:id},
				function(data){
					if(data==true){
						alert("刪除成功")
						//location.href="#"
						$("#workcontent").load("/user/articles");
					}else{
						alert("刪除失敗")
					}
					
		},"json"				
		)
	}
	
</script>
</body>
</html>