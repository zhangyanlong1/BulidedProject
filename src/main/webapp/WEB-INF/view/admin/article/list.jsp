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
      <th scope="col">热门状态</th>
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
	  <td>${articleList.hot==1?"热门":"不是热门" }</td>
	  <td> 
	  			<input type="button" value="审核"  class="btn btn-warning"   onclick="check(${articleList.id})"> 
	  			<input type="button" value="删除"  class="btn btn-danger" onclick="del(${articleList.id})">
<%-- 	  	  		<input type="button" value="管理投诉"  class="btn btn-warning"  onclick="complainList(${articleList.id})" >
 --%>	  </td>
	
    </tr>
    </c:forEach>  
</table>

<!-- 分页 -->
<div  class="row  justify-content-center">
<div class="collapse" id="collapseExample1">
  <div class="card card-body">
  		<ul class="nav">
    	<c:forEach begin="1" end="${page.pages}" varStatus="i">
    		<c:if test="${i.index<(page.pageNum-1)}">
    			<li class="nav-item">
		   			<input type="button"  class="btn btn-primary"  href="#" onclick="gopage(${i.index})"  value="${i.index}">
		   		</li>
		   		<c:if test="${i.index%10==0}"><br></c:if>
		   	</c:if>
		</c:forEach>
		</ul>
  </div>
</div>
 <nav aria-label="Page navigation example"  >
  <ul class="pagination justify-content-center">
    <li class="page-item">
      <a class="page-link" href="#" aria-label="Previous"  onclick="gopage(${page.prePage==0?1:page.prePage})">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    	<c:if test="${page.pages>3&&(page.pageNum-2)>0 }">
    		<li class="page-item">
    			<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample1" role="button" aria-expanded="false" aria-controls="collapseExample1"  >...</a>
    		</li>
    	</c:if>
     	<c:if test="${page.pageNum!=page.firstPage }"><li class="page-item"><a class="page-link" href="#"  onclick="gopage(${page.pageNum-1})">${page.pageNum-1 }</a></li></c:if>
   	 	<li class="page-item"><a class="page-link" href="#"  style="background-color:  yellow" onclick="gopage(${page.pageNum})">${page.pageNum }</a></li>
  		<c:if test="${page.pageNum!=page.lastPage }"><li class="page-item"><a class="page-link" href="#"  onclick="gopage(${page.pageNum+1})">${page.pageNum+1 }</a></li></c:if>
  		<c:if test="${page.pages>3&&(page.pages-page.pageNum)>2 }">
  			<li class="page-item">
  				<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample2" role="button" aria-expanded="false" aria-controls="collapseExample2"  >...</a>
  			</li>
  		</c:if>
    <li class="page-item">
      <a class="page-link" href="#" aria-label="Next"  onclick="gopage(${page.nextPage==0?page.pages:page.nextPage})">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>
<div class="collapse" id="collapseExample2">
  <div class="card card-body">
  	<ul class="nav">
    	<c:forEach begin="1" end="${page.pages}" varStatus="i">
    		<c:if test="${i.index>(page.pageNum+1)}">
    			<li class="nav-item ">
		   			<input type="button"  class="btn btn-primary"   href="#" onclick="gopage(${i.index})"  value="${i.index}">
		   		</li>
		   	</c:if>
		</c:forEach>
	</ul>
  </div>
</div>
</div>
<!-- 分页 -->

<div class="modal fade bs-example-modal-sm"   id="articleContent" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true"   >
  <div class="modal-dialog"  role="document"   >
    <div class="modal-content"  style="width: 800px">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">文章审核</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body ">
         	<div class="row" id="divTitle"></div>
         	<div class="row" id="divOptions" ></div>
         	<div class="row" id="divContent"></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="setStatus(1)">审核通过</button>
        <button type="button" class="btn btn-primary" onclick="setStatus(2)">审核拒绝</button>
        <button type="button" class="btn btn-primary" onclick="setHot(1)">设置热门</button>
        <button type="button" class="btn btn-primary" onclick="setHot(0)">取消热门</button>
      </div>
    </div>
  </div>
</div>



<!-- 查看投书 -->
<div class="modal fade"   id="complainModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" role="document" style="margin-left:100px;">
    <div class="modal-content" style="width:1200px;" >
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">文章审核</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" id="complainListDiv">
         
         		
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary"  onclick="setStatus(1)">审核通过</button>
        <button type="button" class="btn btn-primary"  onclick="setStatus(2)">审核拒绝</button>
       
      </div>
    </div>
  </div>
</div>


<script type="text/javascript">
/**
* 翻页
*/
function gopage(pageNum){
	$("#workcontent").load("/admin/articles?page="+pageNum);
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
						$("#workcontent").load("/admin/articles?page=${page.pageNum}");
					}else{
						alert("刪除失敗")
					}
					
		},"json"				
		)
	}
	
	/*点击生成模态框  */
	function check(id){
		
     	$.post("/article/getDetail",{id:id},function(msg){
     		if(msg.code==1){
     			//
     			$("#divTitle").html(msg.data.title);
     			//
     			$("#divOptions").html("栏目：" +msg.data.channel.name + 
     					" 分类："+msg.data.category.name + " 作者：" + msg.data.user.username );
     			//
     			$("#divContent").html("文章内容:<br>"+msg.data.content);
     			$('#articleContent').modal('show')
     			//文章id保存到全局变量当中
     			global_article_id=msg.data.id;
     			return;
     		}
     		alert(msg.error);
     	},"json");
		
		//$("#workcontent").load("/user/updateArticle?id="+id);
	}
	
	/**
	*  status 0  待审核  1 通过    2 拒绝 
	*/
	function setStatus(status){
		var id = global_article_id;
		var tag=false;
		$.post("/admin/setArticeStatus",{id:id,status:status},function(msg){
			if(msg.code==1){
				alert('操作成功')
				//隐藏当前的模态框
				$('#articleContent').modal('hide')
				$("#complainModal").modal('hide')
				//刷新当前的页面
				refreshPage();
			}
		},
		"json")
		
	}
	
	/**
	 0 非热门
	 1 热门
	*/
	function setHot(status){
		
		var id = global_article_id;// 文章id
		$.post("/admin/setArticeHot",{id:id,status:status},function(msg){
			if(msg.code==1){
				alert('操作成功')
				//隐藏当前的模态框
				$('#articleContent').modal('hide')
				//刷新当前的页面
					refreshPage();
				return;
			}
		},
		"json")
	}
	
	function refreshPage(){
		//关闭弹框遗留遮罩层 删除遮罩层样式
		/* $(".modal-backdrop").remove();
		$("#workcontent").load("/admin/articles?page=${page.pageNum}");
		$("body").removeClass('modal-open'); */
		
 		setTimeout(function(){//延时加载页面
			$("#workcontent").load("/admin/articles?page=${page.pageNum}");
		},500); 
	}
	
	/**
	* 查看文章的投诉
	*/
	function complainList(id){
		global_article_id=id;
		$("#complainModal").modal('show')
		$("#complainListDiv").load("/article/complains?articleId="+id);
		
	}
</script>
</body>
</html>