<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<form  id="articleform"  name="articleform">
  <input type="hidden" name="id" value="${article.id}">
	 <div class="title1">
    <label for="title1">标题</label>
	<input class="form-control form-control-lg" type="text" placeholder="标题"   name="title"   value="${article.title }" >
	</div>
	 <div class="form-group">
    <label for="channel">栏目</label>
    <select class="form-control" id="channel"  name="channelId">
       <option value="0">请选择</option>
      <c:forEach items="${channels}" var="cat">
      		<!-- 下拉框的回显 -->	
      		<option value="${cat.id}" ${article.channelId==cat.id?"selected":""}>${cat.name}</option>	
      </c:forEach>
    </select>
  </div>
   <div class="form-group">
    <label for="category">分类</label>
    <select class="form-control" id="category"  name="categoryId">
    </select>
  </div>
  
  <div class="form-group">
    <label for="file">附加文件</label>
    <input type="file" class="form-control-file" id="file"  name="file">
  </div>

  <div class="form-group">
    <label for="content1">文章内容</label>
    <textarea class="form-control" id="contentId"  name="content1" rows="20"  style="height: 300px;width: 100%"></textarea>
  </div>
  <div class="form-group  text-center">
      <input type="button"  class="btn btn-primary mb-2"  value="发布文章"  onclick="putUpArticle()">
   </div>
  </form>
 <script>
 function channelChange(){

		console.log("选中的数据是 " + $("#channel").val())
		$.post("/user/getCategoris",{cid:$("#channel").val()},
				function(data){
					$("#category").empty();
					
					for ( var i in data) {
						if(data[i].id=='${article.categoryId}'){
							$("#category").append("<option selected value='"+ data[i].id+"'>"+data[i].name+"</option>")	
						}
						else{
							$("#category").append("<option value='"+ data[i].id+"'>"+data[i].name+"</option>")
						}	
					}
		})
	
	}
	
 
 	$("#channel").change(function(){
 		channelChange();
	})
	
	
	/* 
		富文本
	*/
	
	 $(document).ready( function(){
		 channelChange();
			KindEditor.ready(function(K) {
				//    textarea[name="content1"]
				editor = K.create('#contentId', {
				cssPath : '/resource/kindeditor/plugins/code/prettify.css',
				//uploadJson : '/resource/kindeditor/jsp/upload_json.jsp',
				//uploadJson:'/file/upload.do',
				uploadJson:'/file/uploads.do',
				fileManagerJson:'/file/manager',
				//fileManagerJson : '/resource/kindeditor/jsp/file_manager_json.jsp',
				allowFileManager : true,
					afterCreate : function() {
						var self = this;
						K.ctrl(document, 13, function() {
							self.sync();
							document.forms['example'].submit();
						});
						K.ctrl(self.edit.doc, 13, function() {
							self.sync();
							document.forms['example'].submit();
						});
					}
				});
				prettyPrint();
			});
	      
		 }); 
	
	function putUpArticle(){
			  alert(editor.html());
			  
			//  var formdata = new FormData($("#articleform"))
			// 生成formData  异步提交的数据包含附件  
			  var formData = new FormData($( "#articleform" )[0]);
			  
			console.log("11111111")
			
			   // 把文章内容存放到formData 中
			  formData.set("content",editor.html());
			console.log("222222222222")
			 
			  $.ajax({url:"updateArticle",
				  dataType:"json",
				  data:formData,
				  // 让jQuery 不要再提交数据之前进行处理
				  processData : false,
				  // 提交的数据不能加消息头
				  contentType : false,
				  // 提交的方式 
				  type:"post",
				  // 成功后的回调函数
				  success:function(data){
					  //$("#workcontent").load("/user/articles")
					  //  
					  showWork($("#articles"),"/user/articles")
				  }
				  })
		  }
			
	
	
	
</script>
</body>
</html>